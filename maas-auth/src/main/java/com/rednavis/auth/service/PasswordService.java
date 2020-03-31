package com.rednavis.auth.service;

import java.util.Collections;
import java.util.List;
import com.rednavis.auth.exception.CannotPerformOperationException;
import com.rednavis.auth.exception.InvalidHashException;
import com.rednavis.auth.exception.MaasAuthException;
import com.rednavis.auth.util.PasswordUtils;
import edu.vt.middleware.password.Password;
import edu.vt.middleware.password.PasswordData;
import edu.vt.middleware.password.PasswordValidator;
import edu.vt.middleware.password.RuleResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for work with passwords. Checking the password for validity. Generate a token for the password.
 *
 * <p>Validation of the token for a given password
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordService {

  private final PasswordValidator passwordValidator;

  /**
   * checkPasswordComplexity.
   *
   * @param password password
   * @return
   */
  public List<String> checkPasswordComplexity(String password) {
    PasswordData passwordData = new PasswordData(new Password(password));
    RuleResult result = passwordValidator.validate(passwordData);
    if (!result.isValid()) {
      return passwordValidator.getMessages(result);
    }
    return Collections.emptyList();
  }

  /**
   * generatePasswordHash.
   *
   * @param password password
   * @return
   */
  public String generatePasswordHash(String password) {
    try {
      return PasswordUtils.createHash(password);
    } catch (CannotPerformOperationException e) {
      log.error("Can't generate password [password: {}]", password, e);
      throw new MaasAuthException("Can't generate password [password: " + password + "]");
    }
  }

  /**
   * validatePassword.
   *
   * @param passwordDb passwordDb
   * @param password   password
   * @return
   */
  public boolean validatePassword(String passwordDb, String password) {
    try {
      return PasswordUtils.verifyPassword(password, passwordDb);
    } catch (CannotPerformOperationException | InvalidHashException e) {
      log.error("Can't validate password [password: {}]", password, e);
      throw new MaasAuthException("Can't validate [password: " + password + "]");
    }
  }
}
