package com.tartu.sensorbot.chat;

import android.widget.Button;
import android.widget.CheckBox;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class CheckManagerTest {

  private CheckManager checkManager;
  private Button mockButton;
  private CheckBox mockCheckBox1;
  private CheckBox mockCheckBox2;

  @Before
  public void setUp() {
    mockButton = Mockito.mock(Button.class);
    mockCheckBox1 = Mockito.mock(CheckBox.class);
    mockCheckBox2 = Mockito.mock(CheckBox.class);

    checkManager = new CheckManager(mockButton);
  }

  @Test
  public void whenAllCheckBoxesAreChecked_buttonShouldBeEnabled() {
    Mockito.when(mockCheckBox1.isChecked()).thenReturn(true);
    Mockito.when(mockCheckBox2.isChecked()).thenReturn(true);

    checkManager.addCheckBox(mockCheckBox1);
    checkManager.addCheckBox(mockCheckBox2);

    Mockito.verify(mockButton, Mockito.times(2)).setEnabled(true);
  }

  @Test
  public void whenAnyCheckBoxIsUnchecked_buttonShouldBeDisabled() {
    Mockito.when(mockCheckBox1.isChecked()).thenReturn(true);
    Mockito.when(mockCheckBox2.isChecked()).thenReturn(false);

    checkManager.addCheckBox(mockCheckBox1);
    checkManager.addCheckBox(mockCheckBox2);

    Mockito.verify(mockButton).setEnabled(false);
  }

  @Test
  public void whenCheckBoxStateChanges_updateButtonShouldBeCalled() {
    Mockito.when(mockCheckBox1.isChecked()).thenReturn(true);
    checkManager.addCheckBox(mockCheckBox1);
    Mockito.verify(mockButton).setEnabled(true);

    Mockito.when(mockCheckBox1.isChecked()).thenReturn(false);
    checkManager.addCheckBox(mockCheckBox1);
    Mockito.verify(mockButton).setEnabled(false);
  }
}
