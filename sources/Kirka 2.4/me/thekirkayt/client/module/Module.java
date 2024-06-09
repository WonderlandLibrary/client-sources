/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module;

import java.awt.Font;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import me.thekirkayt.client.module.ModuleManager;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.client.option.OptionManager;
import me.thekirkayt.client.threads.Killswitch;
import me.thekirkayt.event.EventManager;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.UnicodeFontRenderer;

public class Module {
    private String id;
    private String displayName;
    private String tag;
    private boolean enabled;
    private Category category;
    private int keybind;
    private String suffix;
    private boolean shown;
    private UnicodeFontRenderer arrayText;
    private static /* synthetic */ int[] $SWITCH_TABLE$me$thekirkayt$client$module$Module$Category;

    public void setProperties(String id, String displayName, Category type, int keybind, String suffix, boolean shown) {
        this.arrayText = new UnicodeFontRenderer(new Font("Verdana", 0, 20));
        this.id = id;
        this.displayName = displayName;
        this.tag = displayName;
        this.category = type;
        this.keybind = keybind;
        this.suffix = suffix;
        this.shown = shown;
    }

    public void preInitialize() {
    }

    public static Module getModByName(String name) {
        for (Module mod : ModuleManager.getModules()) {
            if (!mod.getDisplayName().trim().equalsIgnoreCase(name.trim()) && !mod.toString().trim().equalsIgnoreCase(name.trim())) continue;
            return mod;
        }
        return null;
    }

    public void toggle() {
        if (this.enabled) {
            this.disable();
            new Killswitch().start();
        } else {
            this.enable();
            new Killswitch().start();
        }
    }

    public void enable() {
        this.enabled = true;
        EventManager.register(this);
    }

    public void disable() {
        this.enabled = false;
        EventManager.unregister(this);
    }

    public List<Option> getOptions() {
        ArrayList<Option> optionList = new ArrayList<Option>();
        for (Option option : OptionManager.getOptionList()) {
            if (!option.getModule().equals(this)) continue;
            optionList.add(option);
        }
        return optionList;
    }

    public String getId() {
        return this.id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getTag() {
        return this.tag;
    }

    public boolean drawDisplayName(float x, float y, int color) {
        if (this.enabled && this.shown) {
            ClientUtils.clientFont().drawStringWithShadow(String.format("%s" + (this.suffix.length() > 0 ? " \u00a77[%s]" : ""), this.displayName, this.suffix), x, y, color);
            return true;
        }
        return false;
    }

    public boolean drawDisplayName(float x, float y) {
        if (this.enabled && this.shown) {
            ClientUtils.clientFont().drawStringWithShadow(String.format("%s" + (this.suffix.length() > 0 ? " \u00a77[%s]" : ""), this.displayName, this.suffix), x, y, this.getColor());
            return true;
        }
        return false;
    }

    public int getColor() {
        switch (Module.$SWITCH_TABLE$me$thekirkayt$client$module$Module$Category()[this.category.ordinal()]) {
            case 1: {
                return -4042164;
            }
            case 2: {
                return -1781619;
            }
            case 3: {
                return -4927508;
            }
            case 5: {
                return -8921737;
            }
            case 4: {
                return -8921737;
            }
            case 6: {
                return -13911383;
            }
        }
        return -1;
    }

    public Category getCategory() {
        return this.category;
    }

    public int getKeybind() {
        return this.keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public boolean isShown() {
        return this.shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Module getInstance() {
        for (Module mod : ModuleManager.getModules()) {
            if (!mod.getClass().equals(this.getClass())) continue;
            return mod;
        }
        return null;
    }

    public void postInitialize() {
    }

    static /* synthetic */ int[] $SWITCH_TABLE$me$thekirkayt$client$module$Module$Category() {
        if ($SWITCH_TABLE$me$thekirkayt$client$module$Module$Category != null) {
            int[] arrn;
            return arrn;
        }
        int[] arrn = new int[Category.values().length];
        try {
            arrn[Category.Bypass.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Category.Combat.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Category.Misc.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Category.Movement.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Category.Player.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Category.Render.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$me$thekirkayt$client$module$Module$Category = arrn;
        return $SWITCH_TABLE$me$thekirkayt$client$module$Module$Category;
    }

    public static enum Category {
        Combat("Combat", 0),
        Movement("Movement", 0),
        Render("Render", 0),
        Player("Player", 0),
        Speed("Speed", 0),
        World("World", 0),
        Client("Client", 0),
        Other("Other", 0),
        Auto("Auto", 0),
        Fun("Fun", 0),
        Longjump("Longjump", 0),
        Bypass("Bypass", 0);
        

        private Category(String s, int n2) {
        }
    }

    @Target(value={ElementType.TYPE})
    @Retention(value=RetentionPolicy.RUNTIME)
    public static @interface Mod {
        public String displayName() default "null";

        public boolean enabled() default false;

        public int keybind() default -1;

        public boolean shown() default true;

        public String suffix() default "";
    }

}

