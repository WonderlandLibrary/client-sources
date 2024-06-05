/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module;

import net.minecraft.client.Minecraft;
import digital.rbq.annotations.Label;
import digital.rbq.core.Autumn;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.keybinds.KeyboardKey;
import digital.rbq.module.option.Configurable;
import digital.rbq.module.option.impl.EnumOption;
import digital.rbq.utils.render.Translate;

public class Module
extends Configurable {
    protected static final Minecraft mc = Minecraft.getMinecraft();
    private final String label = this.getClass().getAnnotation(Label.class).value();
    private final ModuleCategory category = this.getClass().getAnnotation(Category.class).value();
    private final Translate translate = new Translate(0.0f, 0.0f);
    private final KeyboardKey keyBind = new KeyboardKey("");
    private String[] aliases = new String[]{this.label};
    private boolean enabled;
    private boolean hidden;

    private static String capitalizeThenLowercase(String str) {
        return Character.toTitleCase(str.charAt(0)) + str.substring(1).toLowerCase();
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }

    public KeyboardKey getKeyBind() {
        return this.keyBind;
    }

    public Translate getTranslate() {
        return this.translate;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            if (enabled) {
                this.onEnabled();
                Autumn.EVENT_BUS_REGISTRY.eventBus.subscribe(this);
            } else {
                Autumn.EVENT_BUS_REGISTRY.eventBus.unsubscribe(this);
                this.onDisabled();
            }
        }
    }

    public final String getLabel() {
        return this.label;
    }

    public final String getDisplayLabel() {
        EnumOption<?> mode = this.getMode();
        if (mode == null || mode.getValue() == null) {
            return this.label;
        }
        String modeValue = ((Enum)mode.getValue()).name();
        String formattedMode = Module.capitalizeThenLowercase(modeValue);
        return this.label + "\u00a77 " + formattedMode;
    }

    public final ModuleCategory getCategory() {
        return this.category;
    }

    public final void toggle() {
        this.setEnabled(!this.enabled);
    }

    public void onEnabled() {
    }

    public void onDisabled() {
    }
}

