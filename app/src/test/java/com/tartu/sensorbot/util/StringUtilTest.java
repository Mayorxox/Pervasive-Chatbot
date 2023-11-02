package com.tartu.sensorbot.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import junit.framework.TestCase;

public class StringUtilTest extends TestCase {

  public void testEscapeForCsvWithList() {
    // Test with null list
    String result = StringUtil.escapeForCsv((List<CharSequence>) null);
    assertThat(result).isEmpty();

    // Test with empty list
    result = StringUtil.escapeForCsv(Collections.emptyList());
    assertThat(result).isEmpty();

    // Test with list containing nulls
    List<CharSequence> input = Arrays.asList("Hello", null, "World");
    result = StringUtil.escapeForCsv(input);
    assertThat(result).isEqualTo("[\"Hello\"\"World\"]");

    // Test with list containing special characters
    input = Arrays.asList("He\"llo", "Wo\"rld");
    result = StringUtil.escapeForCsv(input);
    assertThat(result).isEqualTo("[\"He\"\"llo\"\"Wo\"\"rld\"]");
  }

  public void testEscapeForCsvWithCharSequence() {
    // Test with null charsequence
    String result = StringUtil.escapeForCsv((CharSequence) null);
    assertThat(result).isEmpty();

    // Test with valid charsequence
    CharSequence input = "Hello\"World";
    result = StringUtil.escapeForCsv(input);
    assertThat(result).isEqualTo("\"Hello\"\"World\"");
  }

  public void testEscapeForCsvWithString() {
    // Test with null string
    String result = StringUtil.escapeForCsv((String) null);
    assertThat(result).isEmpty();

    // Test with empty string
    result = StringUtil.escapeForCsv("");
    assertThat(result).isEqualTo("\"\"");

    // Test with valid string without special characters
    result = StringUtil.escapeForCsv("Hello");
    assertThat(result).isEqualTo("\"Hello\"");

    // Test with valid string with special characters
    result = StringUtil.escapeForCsv("Hello\"World");
    assertThat(result).isEqualTo("\"Hello\"\"World\"");
  }

  public void testIsNotEmpty() {
    // Test with null string
    assertThat(StringUtil.isNotEmpty(null)).isFalse();

    // Test with empty string
    assertThat(StringUtil.isNotEmpty("")).isFalse();

    // Test with string containing only spaces
    assertThat(StringUtil.isNotEmpty("   ")).isFalse();

    // Test with valid string
    assertThat(StringUtil.isNotEmpty("Hello")).isTrue();
  }
}
