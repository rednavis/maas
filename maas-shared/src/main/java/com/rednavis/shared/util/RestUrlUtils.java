package com.rednavis.shared.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RestUrlUtils {

  public static final String LIMIT = "limit";
  public static final String OFFSET = "offset";

  public static final String ROOT_API_URL = "/api";
  public static final String BOOK_URL = ROOT_API_URL + "/book";
  public static final String USER_URL = ROOT_API_URL + "/user";
  public static final String REFRESH_TOKEN_URL = ROOT_API_URL + "/refreshToken";
  public static final String PASSWORD_URL = ROOT_API_URL + "/password";

  public static final String INSERT_URL = "/insert";
  public static final String SAVE_URL = "/save";
  public static final String FINDALL_URL = "/findAll";
  public static final String FINDBYID_URL = "/findById/{id}";
  public static final String FINDBYEMAIL_URL = "/findByEmail/{email}";
  public static final String FINDBYUSERNAME_URL = "/findByUserName/{userName}";
  public static final String FINDBYREFRESHTOKEN_URL = "/findByRefreshToken/{refreshToken}";
  public static final String COUNT_URL = "/count";
  public static final String DELETEALL_URL = "/deleteAll";
  public static final String DELETEBYID_URL = "/deleteById/{id}";
  public static final String GENERATE_PASSWORD_HASH_URL = "/generatePasswordHash/{password}";
}
