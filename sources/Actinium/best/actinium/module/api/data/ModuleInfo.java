package best.actinium.module.api.data;

import best.actinium.module.ModuleCategory;
import org.lwjglx.input.Keyboard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {

    String name();
    String description();
    ModuleCategory category();

    boolean autoEnabled() default false;
    boolean autoVisible() default true;

    int keyBind() default Keyboard.KEY_NONE;

}