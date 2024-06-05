package me.vaziak.sensation.client.api.event.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//I fucked up and did source 3rst, I swear thes weed si good
@Target({ElementType.METHOD})//Might add feilds or packages later, irdc rn
public @interface Collect {}
