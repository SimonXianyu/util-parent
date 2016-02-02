package io.github.simonxianyu.util.spring.web;

import java.lang.annotation.*;

/**
 *
 * Created by Simon Xianyu on 2016/1/10 0010.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( ElementType.METHOD)
@Inherited
public @interface FuncPerm {
  String domain() default "";
  String module() default "basic";
  String name();
  boolean enableLog() default false;
  boolean enablePermission() default true;
  boolean enableLogFail() default false;
}
