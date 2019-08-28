package com.mvp.databind;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jack06.li on 2019-08-27.
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bind {
    String name() default "";
    //变化的控件id
    int id() default  0;
    BindType type() default BindType.input;
}
