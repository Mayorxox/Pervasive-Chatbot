package com.tartu.sensorbot.util;

import java.util.List;

public class StringUtil {

  public static String escapeForCsv(List<CharSequence> value) {
    if (value == null || value.isEmpty()) return "";
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    value.forEach(v -> sb.append(escapeForCsv(v)));
    sb.append("]");
    return sb.toString();
  }

  public static String escapeForCsv(CharSequence value) {
    if (value == null) return "";
    return escapeForCsv(value.toString());
  }

  public static String escapeForCsv(String value) {
    if (value == null) return "";
    return "\"" + value.replace("\"", "\"\"") + "\"";
  }

}
