package com.tartu.sensorbot.util;

import java.lang.reflect.Field;
import java.util.List;
import junit.framework.TestCase;
import org.assertj.core.api.Assertions;

public class BatteryDrainerUtilTest extends TestCase {

  public void setUp() {
    BatteryDrainerUtil.isStarted = false;
  }

  public void testStart() throws NoSuchFieldException, IllegalAccessException {
    BatteryDrainerUtil.start();

    Assertions.assertThat(BatteryDrainerUtil.isStarted).isTrue();

    // Check if all threads have started
    Field primeThreadsField = BatteryDrainerUtil.class.getDeclaredField("primeThreads");
    primeThreadsField.setAccessible(true);
    List<Thread> primeThreads = (List<Thread>) primeThreadsField.get(null);

    Assertions.assertThat(primeThreads).hasSize(16);

    // Cleaning up, stopping threads
    BatteryDrainerUtil.stop();
  }

  public void testStop() throws NoSuchFieldException, IllegalAccessException {
    BatteryDrainerUtil.start();
    BatteryDrainerUtil.stop();

    Assertions.assertThat(BatteryDrainerUtil.isStarted).isFalse();

    // Check if all threads have been cleared
    Field primeThreadsField = BatteryDrainerUtil.class.getDeclaredField("primeThreads");
    primeThreadsField.setAccessible(true);
    List<Thread> primeThreads = (List<Thread>) primeThreadsField.get(null);

    Assertions.assertThat(primeThreads).isEmpty();
  }

  public void testIsPrime() {
    Assertions.assertThat(BatteryDrainerUtil.isPrime(2)).isTrue();
    Assertions.assertThat(BatteryDrainerUtil.isPrime(3)).isTrue();
    Assertions.assertThat(BatteryDrainerUtil.isPrime(4)).isFalse();
    Assertions.assertThat(BatteryDrainerUtil.isPrime(5)).isTrue();
    Assertions.assertThat(BatteryDrainerUtil.isPrime(16)).isFalse();
    Assertions.assertThat(BatteryDrainerUtil.isPrime(17)).isTrue();
  }
}
