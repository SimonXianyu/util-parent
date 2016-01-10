package io.github.simonxianyu.util.spring.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declare operation function.
 * Created by simon on 2015/2/6.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Func {
    String domain() default "";
    String module();
    String func() ;
    String content() default "";

    /**
     * 是否启用日志记录.
     *
     * @return true, if is enable log
     */
    boolean enableLog() default false;

    /**
     * 是否启用权限校验.
     *
     * @return true, if is enable permission
     */
    boolean enablePermission() default true;

    /**
     * 是否记录失败日志(默认否).
     *
     * @return true, if is log fail
     */
    boolean enableLogFail() default false;
}
