package com.rednavis.shared.util;

import java.util.Arrays;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EnumUtils {

  public static String[] getNames(Class<? extends Enum<?>> e) {
    return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
  }
}
