package io.github.simonxianyu.util.spring.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User should implement this interface to check permission.
 * Created by Simon Xianyu on 2016/1/10 0010.
 */
public interface FuncPermissionChecker {
  /**
   * Implement this to check login status. If not logined redirect to login page here.
   */
  boolean isLogined(HttpServletRequest request, HttpServletResponse response);

  /**
   * Implement this method to check permission.
   */
  boolean isPermitted(String permissionName, HttpServletRequest request);
}
