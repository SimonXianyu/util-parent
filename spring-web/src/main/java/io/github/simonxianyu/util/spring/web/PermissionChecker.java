package io.github.simonxianyu.util.spring.web;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface of permission checker.
 * Created by Simon Xianyu on 2015/7/23 0023.
 */
public interface PermissionChecker {

  /**
   * Check authentication and permission.
   * @param request http request
   * @param f function annotation.
   * @return true: permitted , false: not permitted.
   */
  boolean isPermitted(HttpServletRequest request, Func f);
}
