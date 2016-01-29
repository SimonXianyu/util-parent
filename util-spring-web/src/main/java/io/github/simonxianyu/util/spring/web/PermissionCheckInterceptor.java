package io.github.simonxianyu.util.spring.web;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * This interceptor check authorizing.
 * Created by Simon Xianyu on 2016/1/10 0010.
 */
public class PermissionCheckInterceptor extends HandlerInterceptorAdapter {

  private ConcurrentMap<Method, String> permMap = new ConcurrentHashMap<>();

  private FuncPermissionChecker checker;

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
            permissionName = classAnnotation.domain()+"."+methodAnnotation.name();
          } else {
            permissionName = methodAnnotation.domain()+"."+methodAnnotation.name();
          }
          permMap.put(method, permissionName);
        }
      }
      if (null != permissionName) {
        if (!checker.isLogined(request, response)) {
          return false;
        }
        if (checker.isPermitted(permissionName, request)) {
          return true;
        } else {
          return false;
        }
      }
    }
    return true;
  }

  public FuncPermissionChecker getChecker() {
    return checker;
  }

  public void setChecker(FuncPermissionChecker checker) {
    this.checker = checker;
  }
}
