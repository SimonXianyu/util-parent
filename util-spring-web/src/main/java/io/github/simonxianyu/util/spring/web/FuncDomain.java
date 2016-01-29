package io.github.simonxianyu.util.spring.web;

import java.lang.annotation.*;

/**
 *
 * Created by Simon Xianyu on 2016/1/10 0010.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface FuncDomain {
  String domain();
}
