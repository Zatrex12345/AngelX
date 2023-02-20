package me.xtasy.anticheat.check.data;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckData {
    String checkName();
    
    boolean autoBan() default false;
    
    boolean enabled() default true;
}
