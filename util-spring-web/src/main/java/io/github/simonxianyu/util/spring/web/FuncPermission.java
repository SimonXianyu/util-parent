package io.github.simonxianyu.util.spring.web;

import java.lang.annotation.*;

/**
 *
 * Created by Simon Xianyu on 2016/1/10 0010.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface FuncPermission {
  String domain() default "";
  String name();
}
