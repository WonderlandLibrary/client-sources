package io.github.liticane.clients.feature.module;

import io.github.liticane.clients.util.interfaces.IMethods;
import io.github.liticane.clients.feature.property.Property;
import io.github.liticane.clients.Client;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.Formatting;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Module implements IMethods {
    public Info info = this.getClass().getAnnotation(Info.class);

    public String name = info.name();

    public Category category = info.category();

    public int keyBind = info.keyBind();

    public String suffix = "";

    public boolean autoEnabled = info.autoEnabled(), toggled;

    private final List<Property> properties = new ArrayList<>();

    public void setToggled(final boolean toggled) {
        this.toggled = toggled;

        if (this.toggled) {
            if (mc.player != null) onEnable();
            Client.INSTANCE.getEventManager().register(this);
        } else {
            Client.INSTANCE.getEventManager().unregister(this);
            if (mc.player != null) onDisable();
        }
    }


    public <T extends Property> T getValueByName(String name) {
        return (T) properties.stream().filter(value -> value.name.equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public String getDisplayName() {
        String name = getName();

        if (!suffix.isEmpty() || !suffix.equals(""))
            name += " " + Formatting.GRAY + suffix;

        return name;
    }

    protected void onEnable() { }
    protected void onDisable() { }

    public void toggle() {
        this.setToggled(!toggled);
    }

    @Getter
    public enum Category {
        COMBAT("Combat"),
        MOVEMENT("Movement"),
        PLAYER("Player"),
        EXPLOIT("Exploit"),
        VISUAL("Visual"),
        GHOST("Ghost"),
        SCRIPTS("Scripts");

        private final String name;

        Category(String name) {
            this.name = name;
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Info {
        String name();

        Category category();

        int keyBind() default 0;

        boolean autoEnabled() default false;
    }
}
