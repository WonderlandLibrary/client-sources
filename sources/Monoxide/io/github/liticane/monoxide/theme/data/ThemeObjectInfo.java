package io.github.liticane.monoxide.theme.data;

import io.github.liticane.monoxide.theme.data.enums.ScreenType;
import io.github.liticane.monoxide.theme.data.enums.ThemeObjectType;
import io.github.liticane.monoxide.theme.data.enums.ElementType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(java.lang.annotation.ElementType.TYPE)
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface ThemeObjectInfo {

    String name();
    ThemeObjectType themeObjectType();
    ElementType elementType() default ElementType.NOT;
    ScreenType screenType() default ScreenType.NOT;

}
