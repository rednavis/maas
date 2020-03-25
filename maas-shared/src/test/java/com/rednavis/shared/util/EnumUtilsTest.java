package com.rednavis.shared.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class EnumUtilsTest {

  @Test
  void isEmailValid() {
    String[] expected = new String[] {"TEST1", "TEST2"};
    String[] enumString = EnumUtils.getNames(TestEnum.class);

    assertArrayEquals(expected, enumString);
  }

  enum TestEnum {
    TEST1,
    TEST2
  }
}