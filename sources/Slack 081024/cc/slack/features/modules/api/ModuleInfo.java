// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.api;

import org.lwjgl.input.Keyboard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    String name();

    String displayName() default "";

    Category category();

    int key() default Keyboard.KEY_NONE;
}
