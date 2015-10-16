package io.github.simonxianyu.util.spring.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 拦截请求以便检查相关请求方法的 Func annotation
 * Created by simon on 2015/2/16.
 */
public class FuncInterceptor extends HandlerInterceptorAdapter {
    public static final String MODULE_CODE = "__ModuleCode";
    private final Logger log = LogManager.getLogger(getClass());

    private String loginUrl = "/loginform";

    private PermissionChecker permissionChecker;

    private List<String> ignores;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(MODULE_CODE, "");
        log.debug("[start]funcHandler:preHandler");
        String contextPath = request.getContextPath();
        String requestUri = request.getRequestURI().substring(contextPath.length());


        if (requestUri.startsWith(loginUrl) || startWithIgnores(requestUri) ) {
            return true;
        }
        request.setAttribute(MODULE_CODE, ""); // avoid null exception in ftl
        if (handler instanceof HandlerMethod) {
            log.debug("method is {}", ((HandlerMethod) handler).getMethod() );
            Method method = ((HandlerMethod) handler).getMethod();
            Func f = method.getAnnotation(Func.class);
            if (null != f) {
                request.setAttribute(MODULE_CODE, f.domain() + "." + f.module());
                log.debug("mcode {} , func {}", f.module(), f.func());
                if (f.enablePermission() ) {
                    return null != permissionChecker && permissionChecker.isPermitted(request , f);
// check shiro permission
//                    Subject subject = SecurityUtils.getSubject();
//                    if (!subject.isAuthenticated()) {
//                        WebUtils.saveRequest(request);
//                        WebUtils.issueRedirect(request, response, loginUrl);
////                        response.sendRedirect(contextPath+"/loginform");
//                        return false;
//                    }
//                    if (!subject.hasRole("1") && !subject.isPermitted(f.moduleCode()+":"+f.funcCode())) {
//                        response.sendRedirect(contextPath+"/unauthed");
//                        return false;
//                    }
                }
            }

        }
        return true;
    }

    private boolean startWithIgnores(String a) {
        if (ignores != null && ignores.size()>0) {
            for(String s:ignores) {
                if (a.startsWith(s)) {
                    return false;
                }
            }
        }
        return false;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public void setPermissionChecker(PermissionChecker permissionChecker) {
        this.permissionChecker = permissionChecker;
    }

    public void setIgnores(List<String> ignores) {
        this.ignores = ignores;
    }
}
