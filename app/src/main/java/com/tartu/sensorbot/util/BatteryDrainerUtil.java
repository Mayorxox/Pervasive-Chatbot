package com.tartu.sensorbot.util;

public class BatteryDrainerUtil {

  public static boolean isStarted = false;
  private static Thread primeThread;

  public static void calculatePrimes() {
    long number = 10000000000000L;
    while (isStarted) {
      boolean isPrime = isPrime(number);
      if (isPrime) {
        System.out.println("Prime: " + number);
      }
      number += 1;
    }
  }

  public static void start() {
    isStarted = true;
    primeThread = new Thread(BatteryDrainerUtil::calculatePrimes);
    primeThread.start();
  }

  public static void stop() {
    System.out.println("Stopped");
    isStarted = false;
    if (primeThread != null) {
      primeThread.interrupt();
    }
  }

  private static boolean isPrime(long n) {
    if (n <= 1) return false;
    if (n <= 3) return true;
    if (n % 2 == 0 || n % 3 == 0) return false;
    for (long i = 5; i * i <= n; i += 6) {
      if (n % i == 0 || n % (i + 2) == 0) return false;
    }
    return true;
  }
}
