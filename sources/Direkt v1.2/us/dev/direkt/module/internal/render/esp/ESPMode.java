package us.dev.direkt.module.internal.render.esp;

import us.dev.api.interfaces.Labeled;
import us.dev.api.property.Property;
import us.dev.api.property.multi.MultiProperty;
import us.dev.direkt.Direkt;

/**
 * @author Foundry
 */
public abstract class ESPMode implements Labeled {
    private final String label;

    protected ESPMode(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    protected MultiProperty<Boolean> getTargetTypes() {
        return Direkt.getInstance().getModuleManager().getModule(ESP.class).targetTypes;
    }

    protected Property<Boolean> shouldRenderItems() {
        return Direkt.getInstance().getModuleManager().getModule(ESP.class).items;
    }

    public void onEnable() {}
    public void onDisable() {}
}
