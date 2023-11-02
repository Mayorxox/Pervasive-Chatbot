package com.tartu.sensorbot.util;

import java.util.ArrayList;
import java.util.List;

public class BatteryDrainerUtil {

  private static final List<Thread> primeThreads = new ArrayList<>();
  private static final int NUM_THREADS = 16;
  public static boolean isStarted = false;

  public static void start() {
    isStarted = true;
    for (int i = 0; i < NUM_THREADS; i++) {
      Thread primeThread = new Thread(BatteryDrainerUtil::calculatePrimes);
      primeThreads.add(primeThread);
      primeThread.start();
    }
  }

  public static void stop() {
    System.out.println("Stopped");
    isStarted = false;
    for (Thread primeThread : primeThreads) {
      if (primeThread != null) {
        primeThread.interrupt();
      }
    }
    primeThreads.clear();
  }

  private static void calculatePrimes() {
    long number = 10000000000000L;
    while (isStarted && !Thread.currentThread().isInterrupted()) {
      isPrime(number);
      number += 1;
    }
  }

  protected static boolean isPrime(long n) {
    if (n <= 1) {
      return false;
    }
    if (n <= 3) {
      return true;
    }
    if (n % 2 == 0 || n % 3 == 0) {
      return false;
    }
    for (long i = 5; i * i <= n; i += 6) {
      if (n % i == 0 || n % (i + 2) == 0) {
        return false;
      }
    }
    return true;
  }
}
