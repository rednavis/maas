package com.rednavis.shared.util;

import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {

  public static final String SPACE = " ";
  public static final String BEARER = "Bearer";
  public static final String BEARER_SPACE = BEARER + SPACE;
  private static final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

  /**
   * isEmailValid.
   *
   * @param email email
   * @return
   */
  public static boolean isEmailValid(String email) {
    if (email == null || email.isBlank()) {
      return false;
    }
    Pattern pat = Pattern.compile(EMAIL_REGEX);
    return pat.matcher(email).matches();
  }

  public static boolean isNullOrBlank(String str) {
    return str == null || str.isBlank();
  }
}
