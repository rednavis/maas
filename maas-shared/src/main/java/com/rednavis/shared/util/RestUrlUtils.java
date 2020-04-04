package com.rednavis.shared.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RestUrlUtils {

  public static final String LIMIT = "limit";
  public static final String OFFSET = "offset";
  public static final String PASSWORD_DB = "passwordDB";
  public static final String PASSWORD = "password";

  public static final String ROOT_API_URL = "/api";
  public static final String BOOK_URL = ROOT_API_URL + "/book";
  public static final String USER_URL = ROOT_API_URL + "/user";
  public static final String AUTH_URL = ROOT_API_URL + "/auth";
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
  public static final String VALIDATE_PASSWORD_URL = "/validatePassword";

  public static final String AUTH_URL_VAADIN = "/api/auth";
  public static final String AUTH_URL_PATTERN = AUTH_URL_VAADIN + "/**";
  public static final String AUTH_URL_CURRENTUSER = "/currentUser";
  public static final String AUTH_URL_SIGNIN = "/signin";
  public static final String AUTH_URL_SIGNUP = "/signup";
  public static final String AUTH_URL_REFRESH_TOKEN = "/refreshToken";

  public static final String USER_URL_VAADIN = "/api/user";
  public static final String USER_URL_PATTERN = USER_URL_VAADIN + "/**";
  public static final String USER_URL_USER = "/user";
  public static final String USER_URL_ADMIN = "/admin";
  public static final String USER_URL_FINDALL = "/findAll";

  public static final String BOOK_URL_VAADIN = "/api/book";
  public static final String BOOK_URL_PATTERN = BOOK_URL_VAADIN + "/**";
  public static final String BOOK_URL_INSERT = "/insert";
  public static final String BOOK_URL_SAVE = "/save";
  public static final String BOOK_URL_FINDALL = "/findAll";
  public static final String BOOK_URL_COUNT = "/count";
  public static final String BOOK_URL_DELETE = "/delete";
}
