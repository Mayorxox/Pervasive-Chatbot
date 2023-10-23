package com.tartu.sensorbot.message;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.google.android.material.snackbar.Snackbar;
import com.tartu.sensorbot.R;
import com.tartu.sensorbot.chat.ChatAction;
import com.tartu.sensorbot.util.BatteryDrainerUtil;
import com.tartu.sensorbot.util.DialogUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MessageStep {

  private final int timeInMinutes;
  private final String instruction;
  private final ChatAction chatAction;
  private final List<MessageStep> additionalSteps = new ArrayList<>();

  private final CheckboxEnabler checkboxEnabler;
  private View clickableElement;

  public MessageStep(int timeInMinutes, String instruction, ChatAction chatAction,
      CheckboxEnabler checkboxEnabler) {
    this.timeInMinutes = timeInMinutes;
    this.instruction = instruction;
    this.chatAction = chatAction;
    this.checkboxEnabler = checkboxEnabler;
  }

  public MessageStep(int timeInMinutes, String instruction, ChatAction chatAction,
      List<MessageStep> additionalSteps) {
    this(timeInMinutes, instruction, chatAction, CheckboxEnabler.NONE);
    this.additionalSteps.addAll(additionalSteps);
  }

  public int getTimeInMinutes() {
    return timeInMinutes;
  }

  public String getTime() {
    return timeInMinutes + " min";
  }

  public String getInstruction() {
    return instruction;
  }

  public ChatAction getChatAction() {
    return chatAction;
  }

  public List<MessageStep> getAdditionalSteps() {
    return additionalSteps;
  }

  public void setClickableElement(Context context, boolean isButton) {
    if (isButton) {
      this.clickableElement = getMessageButton(context);
    } else {
      this.clickableElement = getMessageCheckout(context);
    }
  }

  public View getClickableElement() {
    return clickableElement;
  }

  private View getMessageButton(Context context) {
    int viewId = View.generateViewId();
    Button button = new Button(context);
    button.setId(viewId);
    button.setText(R.string.activate);
    button.setBackgroundResource(R.drawable.button_state_list);
    button.setTextColor(ContextCompat.getColor(context, android.R.color.white));
    setupClickListener(context, button);
    return button;
  }

  private View getMessageCheckout(Context context) {
    int viewId = View.generateViewId();
    CheckBox checkBox = new CheckBox(context);
    checkBox.setId(viewId);
    checkBox.setEnabled(additionalSteps.isEmpty());

    if (checkboxEnabler.equals(CheckboxEnabler.BLUETOOTH_ON)) {
      checkBox.setEnabled(false);
      updateCheckBoxBasedOnBluetoothState(checkBox, true);

      IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
      BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
          updateCheckBoxBasedOnBluetoothState(checkBox, true);
        }
      };
      context.registerReceiver(receiver, filter);
    } else if (checkboxEnabler.equals(CheckboxEnabler.BLUETOOTH_CONNECTED)) {
      checkBox.setEnabled(false);
      final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
          switch (intent.getAction()) {
            case BluetoothDevice.ACTION_ACL_CONNECTED -> checkBox.setChecked(true);
            case BluetoothDevice.ACTION_ACL_DISCONNECTED -> checkBox.setChecked(false);
          }
        }
      };

      IntentFilter filter = new IntentFilter();
      filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
      filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
      context.registerReceiver(receiver, filter);

      checkBox.setChecked(isAnyDeviceConnected());
    } else if (checkboxEnabler.equals(CheckboxEnabler.BLUETOOTH_OFF)) {
      checkBox.setEnabled(false);
      updateCheckBoxBasedOnBluetoothState(checkBox, false);

      IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
      BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
          updateCheckBoxBasedOnBluetoothState(checkBox, false);
        }
      };
      context.registerReceiver(receiver, filter);
    }

    return checkBox;
  }

  private void setupClickListener(Context context, Button stepButton) {
    stepButton.setOnClickListener(v -> {
      if (ChatAction.CLOSE_APPS.equals(getChatAction())) {
        DialogUtil.showLoadingDialog(context, 700, () -> {
          showSnackbar(v, "Background apps are closed");
        });
      } else if (ChatAction.ACTIVATE_SAVING_MODE.equals(getChatAction())) {
        DialogUtil.showLoadingDialog(context, 800, () -> {
          showSnackbar(v, "Energy saving mode are activated");
        });
      } else if (ChatAction.MIGRATE_COMPUTATION.equals(getChatAction())) {
        DialogUtil.showLoadingDialog(context, 3000, () -> {
          BatteryDrainerUtil.stop();
          showSnackbar(v, "Tasks are migrated successfully");
        });
      }
      stepButton.setEnabled(false);
    });
  }

  private void showSnackbar(View view, String message) {
    Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
    snackbar.setAction("OK", v -> snackbar.dismiss());
    snackbar.show();

    // Dismiss the Snackbar automatically after 5 seconds
    new Handler().postDelayed(snackbar::dismiss, 5000);
  }

  private void updateCheckBoxBasedOnBluetoothState(CheckBox checkBox, boolean toOn) {
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (bluetoothAdapter != null) {
      boolean checked = toOn == bluetoothAdapter.isEnabled();
      checkBox.setChecked(checked);
    }
  }

  private boolean isAnyDeviceConnected() {
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
      return false;
    }

    try {
      Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
      return !pairedDevices.isEmpty();
    } catch (SecurityException e) {
      // Handle the exception according to your needs
      return false;
    }
  }

  @NonNull
  @Override
  public String toString() {
    return "MessageStep{" +
        "timeInMinutes=" + timeInMinutes +
        ", instruction='" + instruction + '\'' +
        ", chatAction='" + chatAction + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    MessageStep that = (MessageStep) o;

    if (timeInMinutes != that.timeInMinutes) {
      return false;
    }
    if (chatAction != that.chatAction) {
      return false;
    }
    return Objects.equals(instruction, that.instruction);
  }

  @Override
  public int hashCode() {
    int result = timeInMinutes;
    result = 31 * result + Objects.hashCode(chatAction);
    result = 31 * result + Objects.hashCode(instruction);
    return result;
  }
}
