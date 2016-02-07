package io.github.simonxianyu.util.spring.web;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * This interceptor check authorizing.
 * Created by Simon Xianyu on 2016/1/10 0010.
 */
public class PermissionCheckInterceptor extends HandlerInterceptorAdapter {
  public static final String MODULE_CODE = "__ModuleCode";
  public static final String DEFAULT_ENCODING_SCHEME = "UTF-8";

  private ConcurrentMap<Method, String> permMap = new ConcurrentHashMap<>();

  private FuncPermissionChecker checker;

  private String loginUrl;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (handler instanceof HandlerMethod) {
      Method method = ((HandlerMethod) handler).getMethod();
      String permissionName = null;
      if (permMap.containsKey(method)) {
        permissionName = permMap.get(method);
      } else {
        Class<?> controllerClass = method.getDeclaringClass();

        FuncPerm methodAnnotation = method.getAnnotation(FuncPerm.class);
        if (null != methodAnnotation) {
          if (null == methodAnnotation.domain() || "".equals(methodAnnotation.domain())) {
            FuncDomain classAnnotation = controllerClass.getAnnotation(FuncDomain.class);
            permissionName = classAnnotation.domain()+"."+methodAnnotation.module()+"."+methodAnnotation.name();
          } else {
            permissionName = methodAnnotation.domain()+"."+methodAnnotation.module()+"."+methodAnnotation.name();
          }
          permMap.put(method, permissionName);
        }
      }
      if (null != permissionName) {
        request.setAttribute(MODULE_CODE, permissionName);
        if (!checker.isLogined(request, response)) {
          // Redirect to login page.
          doRedirect(request, response, loginUrl);
          return false;
        } else if (!checker.isPermitted(permissionName, request)) {
          // Redirect to unauthorized warning page.
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
          return false;
        }
      }
    }
    return true;
  }

  public static void doRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
    doRedirect(request, response, url, null, true, true);
  }

  public static void doRedirect(HttpServletRequest request, HttpServletResponse response, String url, Map queryParams, boolean contextRelative, boolean http10Compatible) throws IOException {
    // Prepare name URL.
    StringBuilder targetUrl = new StringBuilder();
    if (contextRelative && url.startsWith("/")) {
      // Do not apply context path to relative URLs.
      targetUrl.append(request.getContextPath());
    }
    targetUrl.append(url);
    //change the following method to accept a StringBuilder instead of a StringBuilder for Shiro 2.x:
    appendQueryProperties(targetUrl,url, queryParams, DEFAULT_ENCODING_SCHEME);

    writeRedirectToResponse(response, targetUrl.toString(), http10Compatible);
  }

  protected static void writeRedirectToResponse(HttpServletResponse response,
                              String targetUrl, boolean http10Compatible) throws IOException {
    if (http10Compatible) {
      // Always send status code 302.
      response.sendRedirect(response.encodeRedirectURL(targetUrl));
    } else {
      // Correct HTTP status code is 303, in particular for POST requests.
      response.setStatus(303);
      response.setHeader("Location", response.encodeRedirectURL(targetUrl));
    }
  }

  protected static void appendQueryProperties(StringBuilder targetUrl,String url, Map model, String encodingScheme)
      throws UnsupportedEncodingException {

    // Extract anchor fragment, if any.
    // The following code does not use JDK 1.4's StringBuffer.indexOf(String)
    // method to retain JDK 1.3 compatibility.
    String fragment = null;
    int anchorIndex = targetUrl.toString().indexOf('#');
    if (anchorIndex > -1) {
      fragment = targetUrl.substring(anchorIndex);
      targetUrl.delete(anchorIndex, targetUrl.length());
    }

    // If there aren't already some parameters, we need a "?".
    boolean first = (url.indexOf('?') < 0);

    if (model != null) {
      for (Object o : model.entrySet()) {
        if (first) {
          targetUrl.append('?');
          first = false;
        } else {
          targetUrl.append('&');
        }
        Map.Entry entry = (Map.Entry) o;
        String encodedKey = URLEncoder.encode(entry.getKey().toString(), encodingScheme);
        String encodedValue =
            (entry.getValue() != null ? URLEncoder.encode(entry.getValue().toString(), encodingScheme) : "");
        targetUrl.append(encodedKey).append('=').append(encodedValue);
      }
    }

    // Append anchor fragment, if any, to end of URL.
    if (fragment != null) {
      targetUrl.append(fragment);
    }
  }

  public FuncPermissionChecker getChecker() {
    return checker;
  }

  public void setChecker(FuncPermissionChecker checker) {
    this.checker = checker;
  }

  public void setLoginUrl(String loginUrl) {
    this.loginUrl = loginUrl;
  }
}
