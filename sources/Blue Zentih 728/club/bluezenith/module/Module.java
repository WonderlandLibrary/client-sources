package club.bluezenith.module;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.impl.ModuleToggledEvent;
import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.types.ActionValue;
import club.bluezenith.module.value.types.ExtendedModeValue;
import club.bluezenith.module.value.types.ListValue;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fr.lavache.anime.Animate;
import fr.lavache.anime.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;
import static java.lang.String.format;
import static java.util.Comparator.comparingInt;

public class Module {
    protected final static Minecraft mc = Minecraft.getMinecraft();
    public static EntityPlayerSP player = mc.thePlayer;
    public static WorldClient world = mc.theWorld;

    private final String name;
    private final ModuleCategory category;

    private ExtendedModeValue extendedModeValue;

    protected final List<Value<?>> values = new ArrayList<>();
    protected boolean state;

    public final String[] aliases;
    public final Animate clickGuiAnim = new Animate().setEase(Easing.CIRC_IN_OUT).setSpeed(100); //300

    public float hoverAlpha, toggleAlpha, guiToggleProgress, arrayListHeight, alpha, width;
    public int exceptionThreshold, keyBind;
    public boolean isScript, showSettings, hidden;

    public float arrayListProgress, lineProgress;

    public String displayName, defaultDisplayName, dawnSuffix, tag;

    public void loadValues() {
        for(Field i : getClass().getDeclaredFields()) {

            i.setAccessible(true);

            Object uncastedValue = null;

            try { uncastedValue = i.get(this); } catch(IllegalAccessException ignored) {}

            if(uncastedValue instanceof Value) {
                final Value<?> value = (Value<?>) uncastedValue;

                if(value.getIndex() == 0) throw new IllegalStateException(format("Value \"%s\" in module \"%s\" has no index set.", value.name, this.name));

                values.add(value);

                if(value instanceof ExtendedModeValue) {
                    setExtendedModeValue((ExtendedModeValue) value);
                }
            }

        }
        values.sort(comparingInt(Value::getIndex));

        final float speed = this.clickGuiAnim.getSpeed();
        this.clickGuiAnim.setSpeed(speed + (15 * this.values.size()));
    }

    public List<Value<?>> getValues() {
        return this.values;
    }

    public Module(String name, ModuleCategory category, String... aliases) {
        this.state = false;
        this.name = name;
        this.defaultDisplayName = computeDisplayName(name);
        this.displayName = defaultDisplayName;
        this.category = category;
        this.aliases = aliases;
    }

    protected void setExtendedModeValue(ExtendedModeValue extendedModeValue) {
        this.extendedModeValue = extendedModeValue;
    }

    public void toggle() {
        state = !state;
        try {
            if (!state) {
                subscribeToEvents(false);

                onDisable();

                final ModuleToggledEvent moduleToggledEvent = new ModuleToggledEvent(this, false);

                if(this.extendedModeValue != null) this.extendedModeValue.onParentToggle(moduleToggledEvent);
                getBlueZenith().postEvent(moduleToggledEvent);
            } else {
                subscribeToEvents(true);

                onEnable();

                final ModuleToggledEvent moduleToggledEvent = new ModuleToggledEvent(this, true);

                if(this.extendedModeValue != null) this.extendedModeValue.onParentToggle(moduleToggledEvent);
                getBlueZenith().postEvent(moduleToggledEvent);
            }
        } catch(Exception exception) {
            System.out.println("An error has occured in module " + name + (!state ? " (onDisable)" : " (onEnable)"));
            exception.printStackTrace();
        }
    }

    protected void setTag(String newTag) {
        this.tag = newTag;
    }

    public void onDisable() {}
    public void onEnable() {}

    protected void subscribeToEvents(boolean shouldSubscribe) {
        if(shouldSubscribe) {
            getBlueZenith().register(this);

            if(this.extendedModeValue != null) getBlueZenith().register(this.extendedModeValue.get().getValue());
        } else {
            getBlueZenith().unregister(this);

            if(this.extendedModeValue != null) getBlueZenith().unregister(this.extendedModeValue.get().getValue());
        }
    }

    public void setKeybind(int bind) {
        keyBind = bind;
        BlueZenith.getBlueZenith().getNotificationPublisher().postInfo(
                getName(),
                "Bound " + name + " to " + Keyboard.getKeyName(bind),
                2000
        );
    }

    public final String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public final ModuleCategory getCategory() {
        return category;
    }

    public Module setState(boolean state) {
        if(state != this.state)
            this.toggle();

        return this;
    }

    public boolean getState() {
        return state;
    }

    public Value<?> getValue(String name) {
        return values.stream().filter(val -> val.name.equalsIgnoreCase(name)).findFirst()
                .orElse(values.stream().filter(val1 -> val1.name.replaceAll(" ", "").equalsIgnoreCase(name)).findFirst().orElse(null));
    }


    public final JsonObject serializeModuleData() {
        final JsonObject module = new JsonObject();
        module.add("toggled", new JsonPrimitive(state));
        module.add("hidden", new JsonPrimitive(hidden));
        module.add("displayName", new JsonPrimitive(displayName));
        module.add("keybind", new JsonPrimitive(Keyboard.getKeyName(keyBind)));
        if(values.isEmpty()) return module;

        final JsonObject settings = new JsonObject();
        values.forEach(value -> {
           if(value instanceof ActionValue) return;

           final String key = value.id != null ? value.id : value.name;
           if(value instanceof ListValue) settings.add(key, ((ListValue) value).toObject());
           else settings.add(key, value.getPrimitive());
        });
        module.add("settings", settings);
        addDataToConfig(module);
        return module;
    }

    protected void addDataToConfig(JsonObject configObject) {
        //overridden if needed
    }

    protected void getDataFromConfig(JsonObject configObject) {
        //overridden if needed
    }

    public final <T extends Module> T getCastedModule(Class<T> moduleClass) {
        return getBlueZenith().getModuleManager().getAndCast(moduleClass);
    }

    public final void deserializeModuleData(final JsonObject object, final boolean setKeybindings) {
        object.entrySet().forEach(entry -> {
           switch (entry.getKey()) {
               case "toggled":
                   setState(entry.getValue().getAsBoolean());
               break;

               case "hidden":
                   this.hidden = entry.getValue().getAsBoolean();
               break;

               case "displayName":
                   this.displayName = entry.getValue().getAsString();
               break;

               case "keybind":
                   if(setKeybindings)
                    this.keyBind = Keyboard.getKeyIndex(entry.getValue().getAsString());
               break;

               case "settings":
                   if(!entry.getValue().isJsonObject()) return;
                   final JsonObject settingsObj = entry.getValue().getAsJsonObject();

                   settingsObj.entrySet().forEach(entry2 -> {
                       final String key = entry2.getKey();
                       Value<?> value;

                       if(key.startsWith("id-"))
                           value = this.values.stream().filter(val -> val.id != null && val.id.equals(key)).findFirst().orElse(null);
                       else
                           value = this.values.stream().filter(val -> val.id == null && val.name.equals(key)).findFirst().orElse(null);

                       if (value == null) return;

                       if (value instanceof ListValue && entry2.getValue().isJsonObject())
                           ((ListValue) value).fromObject(entry2.getValue().getAsJsonObject());
                       else value.fromElement(entry2.getValue());
                   });
               break;
           }
        });

        getDataFromConfig(object);
    }

    private String computeDisplayName(String name) {
        int uppercaseChars = 0;
        final char[] arr = name.toCharArray();
        boolean containsLowercase = false;
        for (char c : arr) {
            if(isLowerCase(c)) {
                containsLowercase = true;
                break;
            }
        }
        StringBuilder builder = new StringBuilder();
        if(containsLowercase) {
            for (char c : arr) {
                if (isUpperCase(c)) {
                    if (uppercaseChars++ == 1) {
                        builder.append(" ").append(c);
                        continue;
                    }
                }
                builder.append(c);
            }
        } else builder.append(name);
        return builder.toString();
    }
}
