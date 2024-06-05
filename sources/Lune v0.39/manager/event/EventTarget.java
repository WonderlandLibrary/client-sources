package me.superskidder.lune.manager.event;

import java.lang.annotation.*;

/**
 * @author: QianXia
 * @description: event target
 * @create: 2021/01/12-19:53
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventTarget {
}
