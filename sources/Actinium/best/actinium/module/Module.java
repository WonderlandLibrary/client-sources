package best.actinium.module;

import best.actinium.util.IAccess;
import best.actinium.Actinium;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.Property;
import best.actinium.util.render.animations.Animation;
import best.actinium.util.render.animations.DecelerateAnimation;
import best.actinium.util.render.animations.Direction;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Module extends ListenerAdapter implements IAccess {

    private final String name, description;
    public final ModuleCategory category;

    private int keyBind;
    private boolean enabled, visible;

    private String suffix;

    private List<Property> properties = new ArrayList<>();
    private final Animation animation = new DecelerateAnimation(250, 1).setDirection(Direction.BACKWARDS);
    private boolean shouldRunAnimation;

    public Module() {
        ModuleInfo moduleInfo = this.getClass().getAnnotation(ModuleInfo.class);

        if (moduleInfo == null)
            throw new RuntimeException("Module annotation not present for Module class " + this.getClass().getSimpleName());

        this.name = moduleInfo.name();
        this.description = moduleInfo.description();
        this.category = moduleInfo.category();
        this.suffix = "";
        this.keyBind = moduleInfo.keyBind();

        this.setEnabled(moduleInfo.autoEnabled());
        this.setVisible(moduleInfo.autoVisible());
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (isEnabled()) {
            if (mc.thePlayer != null) onEnable();
            Actinium.INSTANCE.getEventManager().subscribe(this);
        } else {
            Actinium.INSTANCE.getEventManager().unsubscribe(this);
            if (mc.thePlayer != null) onDisable();
        }
    }

    public int getCategoryColor() {
        ModuleCategory category = getCategory();

        switch (category) {
            case COMBAT:
                return new Color(255, 100, 120).getRGB();
            case VISUAL:
                return new Color(255, 180, 90).getRGB();
            case MOVEMENT:
                return new Color(135, 160, 220).getRGB();
            case WORLD:
                return new Color(220, 220, 220).getRGB();
            case PLAYER:
                return new Color(200, 200, 200).getRGB();
            case GHOST:
                return new Color(70, 180, 70).getRGB();
            default:
                return 0;
        }
    }

    public <T extends Property> T getValueByName(String name) {
        return (T) properties.stream().filter(value -> value.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public String getDisplayName(boolean space) {
        String name = space ? getName().replace(" ","") : getName();

        if (!suffix.isEmpty() )
            name += " " + Formatting.GRAY + suffix;

        return name;
    }

    public void toggle() {
        setEnabled(!isEnabled());
    }

    public void onEnable() {
        shouldRunAnimation = true;
        animation.reset();
    }
    public void onDisable() {
        shouldRunAnimation = false;
        animation.reset();
    }
}