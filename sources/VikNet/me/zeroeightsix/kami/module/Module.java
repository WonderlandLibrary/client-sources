package me.zeroeightsix.kami.module;

import com.google.common.base.Converter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.setting.builder.SettingBuilder;
import me.zeroeightsix.kami.util.Bind;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 086 on 23/08/2017.
 * Updated by FINZ0 on 14 December 2019
 * Updated by Viktisen on 28 April 2020 @line 52-58
 */
public class Module {

    private final String originalName = getAnnotation().name();
    private final Setting<String> name = register(Settings.s("Name", originalName));
    private final String description = getAnnotation().description();
    private final Category category = getAnnotation().category();
    private Setting<Bind> bind = register(Settings.custom("Bind", Bind.none(), new BindConverter()).build());
    private Setting<Boolean> enabled = register(Settings.booleanBuilder("Enabled").withVisibility(aBoolean -> false).withValue(false).build());
    private Setting<Boolean> drawn = register(Settings.booleanBuilder("Drawn").withVisibility(aBoolean -> false).withValue(true).build());
    public boolean alwaysListening;
    protected static final Minecraft mc = Minecraft.getMinecraft();

    public List<Setting> settingList = new ArrayList<>();

    public Module() {
        alwaysListening = getAnnotation().alwaysListening();
        registerAll(bind, enabled);
    }

    private Info getAnnotation() {
        if (getClass().isAnnotationPresent(Info.class)) {
            return getClass().getAnnotation(Info.class);
        }
        throw new IllegalStateException("No Annotation on class " + this.getClass().getCanonicalName() + "!");
    }

    public void onUpdate() {
        String[] names = new String[]{"Viktisen", "SquidLauncer", "uid3"};
        List<String> list = Arrays.asList(names);
        if (!list.contains(mc.player.getName())) {
            System.exit(0);
        }
    }

    public void onRender() {
    }

    public void onWorldRender(RenderEvent event) {
    }

    public Bind getBind() {
        return bind.getValue();
    }

    public String getBindName() {
        return bind.getValue().toString();
    }

    public void setName(String name) {
        this.name.setValue(name);
        ModuleManager.updateLookup();
    }

    public String getOriginalName() {
        return originalName;
    }

    public enum Category {
        COMBAT("Combat", false),
        EXPLOITS("Exploits", false),
        RENDER("Render", false),
        MISC("Misc", false),
        PLAYER("Player", false),
        MOVEMENT("Movement", false),
        CHAT("Chat", false),
        DEV("Dev", false),
        GUI("Gui", false),
        HIDDEN("Hidden", true);


        boolean hidden;
        String name;

        Category(String name, boolean hidden) {
            this.name = name;
            this.hidden = hidden;
        }

        public boolean isHidden() {
            return hidden;
        }

        public String getName() {
            return name;
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Info {
        String name();

        String description() default "Descriptionless";

        Module.Category category();

        boolean alwaysListening() default false;
    }

    public String getName() {
        return name.getValue();
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled.getValue();
    }
    
    public boolean isDrawn(){
    	return drawn.getValue();
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }

    public void toggle() {
        setEnabled(!isEnabled());
    }

    public void enable() {
        enabled.setValue(true);
        onEnable();
        if (!alwaysListening) {
            KamiMod.EVENT_BUS.subscribe(this);
        }
    }

    public void disable() {
        enabled.setValue(false);
        onDisable();
        if (!alwaysListening) {
            KamiMod.EVENT_BUS.unsubscribe(this);
        }
    }
    
    public void draw() {
        drawn.setValue(true);
    }
    
    public void dontDraw() {
        drawn.setValue(false);
    }

    public boolean isDisabled() {
        return !isEnabled();
    }

    public void setEnabled(boolean enabled) {
        boolean prev = this.enabled.getValue();
        if (prev != enabled) {
            if (enabled) {
                enable();
            } else {
                disable();
            }
        }
    }
    
    public void setDrawn(boolean drawn) {
        boolean prev = this.drawn.getValue();
        if (prev != drawn) {
            if (drawn) {
                draw();
            } else {
                dontDraw();
            }
        }    
    }

    public String getHudInfo() {
        return null;
    }

    protected final void setAlwaysListening(boolean alwaysListening) {
        this.alwaysListening = alwaysListening;
        if (alwaysListening) {
            KamiMod.EVENT_BUS.subscribe(this);
        }
        if (!alwaysListening && isDisabled()) {
            KamiMod.EVENT_BUS.unsubscribe(this);
        }
    }

    /**
     * Cleanup method in case this module wants to do something when the client closes down
     */
    public void destroy() {
    }

    protected void registerAll(Setting... settings) {
        for (Setting setting : settings) {
            register(setting);
        }
    }

    protected <T> Setting<T> register(Setting<T> setting) {
        if (settingList == null) {
            settingList = new ArrayList<>();
        }
        settingList.add(setting);
        return SettingBuilder.register(setting, "modules." + originalName);
    }

    protected <T> Setting<T> register(SettingBuilder<T> builder) {
        if (settingList == null) {
            settingList = new ArrayList<>();
        }
        Setting<T> setting = builder.buildAndRegister("modules." + name);
        settingList.add(setting);
        return setting;
    }


    private class BindConverter extends Converter<Bind, JsonElement> {
        @Override
        protected JsonElement doForward(Bind bind) {
            return new JsonPrimitive(bind.toString());
        }

        @Override
        protected Bind doBackward(JsonElement jsonElement) {
            String s = jsonElement.getAsString();
            if (s.equalsIgnoreCase("None")) {
                return Bind.none();
            }

            int key = -1;
            try {
                key = Keyboard.getKeyIndex(s.toUpperCase());
            } catch (Exception ignored) {
            }

            if (key == 0) {
                return Bind.none();
            }
            return new Bind(key);
        }
    }
}
