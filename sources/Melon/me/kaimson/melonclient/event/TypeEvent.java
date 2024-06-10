package me.kaimson.melonclient.event;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeEvent {
}
