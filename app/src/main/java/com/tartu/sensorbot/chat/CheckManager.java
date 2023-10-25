package com.tartu.sensorbot.chat;

import android.widget.Button;
import android.widget.CheckBox;
import java.util.ArrayList;
import java.util.List;

public class CheckManager {

  private final List<CheckBox> checkBoxes = new ArrayList<>();
  private final Button button;

  public CheckManager(Button button) {
    this.button = button;
  }

  public void addCheckBox(CheckBox checkBox) {
    checkBoxes.add(checkBox);
    checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateButton());
    updateButton();
  }

  private void updateButton() {
    for (CheckBox checkBox : checkBoxes) {
      if (!checkBox.isChecked()) {
        button.setEnabled(false);
        return;
      }
    }
    button.setEnabled(true);
  }
}
