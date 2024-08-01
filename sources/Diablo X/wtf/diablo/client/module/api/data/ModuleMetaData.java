package wtf.diablo.client.module.api.data;

import org.lwjgl.input.Keyboard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleMetaData {
    String name();
    String description() default "";
    ModuleCategoryEnum category();
    int key() default Keyboard.KEY_NONE;

}
