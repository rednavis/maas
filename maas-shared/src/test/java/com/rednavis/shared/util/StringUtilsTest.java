package com.rednavis.shared.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class StringUtilsTest {

  private static <K> void incrementValue(Map<K, Integer> map, K key) {
    // containsKey() checks if this map contains a mapping for a key
    map.put(key, map.getOrDefault(key, 0) + 1);
  }

  @Test
  void isEmailValid() {
    List<String> emails = List.of(
        "user@domain.com",
        "user@domain.co.in",
        "user.name@domain.com",
        "user_name@domain.com",
        "username@yahoo.corporate.in",
        ".username@yahoo.com",
        "username@yahoo.com.",
        "username@yahoo..com",
        "username@yahoo.c",
        "username@yahoo.corporate");

    Map<Boolean, Integer> map = new HashMap<>();
    emails.forEach(email -> {
      boolean result = StringUtils.isEmailValid(email);
      incrementValue(map, result);
    });

    assertEquals(5, map.get(true));
    assertEquals(5, map.get(false));
  }
}