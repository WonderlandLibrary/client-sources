package me.sleepyfish.smok.rats.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Class from SMok Client by SleepyFish
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SmokEvent {
   byte value() default 2;
}
