package com.polarware.module;

import com.polarware.Client;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.render.ClickGUIModule;
import com.polarware.module.impl.render.InterfaceModule;
import com.polarware.event.impl.other.ModuleToggleEvent;
import com.polarware.util.interfaces.InstanceAccess;
import com.polarware.util.sound.SoundUtil;
import com.polarware.value.Value;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.ModeValue;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick
 * @since 10/19/2021
 */
@Getter
@Setter
public abstract class Module implements InstanceAccess {

    private final String displayName;
    private final List<Value<?>> values = new ArrayList<>();
    private ModuleInfo moduleInfo;
    private boolean hidden;
    private boolean enabled;
    private int keyCode;

    public Module() {
        if (this.getClass().isAnnotationPresent(ModuleInfo.class)) {
            this.moduleInfo = this.getClass().getAnnotation(ModuleInfo.class);

            this.displayName = this.moduleInfo.name();
            this.keyCode = this.moduleInfo.keyBind();
            this.hidden = moduleInfo.hidden();
        } else {
            throw new RuntimeException("ModuleInfo annotation not found on " + this.getClass().getSimpleName());
        }
    }

    public Module(final ModuleInfo info) {
        this.moduleInfo = info;

        this.displayName = this.moduleInfo.name();
        this.keyCode = this.moduleInfo.keyBind();
    }

    public void toggle() {
        this.setEnabled(!enabled);
    }

    public void setEnabled(final boolean enabled) {
        if (this.enabled == enabled || (!this.moduleInfo.allowDisable() && !enabled)) {
            return;
        }

        this.enabled = enabled;

        Client.INSTANCE.getEventBus().handle(new ModuleToggleEvent(this));

        SoundUtil.toggleSound(enabled);

        if (enabled) {
            superEnable();
        } else {
            superDisable();
        }
    }

    /**
     * Called when a module gets enabled
     * -> important: whenever you override this method in a subclass
     * keep the super.onEnable()
     */
    public final void superEnable() {
        Client.INSTANCE.getEventBus().register(this);

        this.values.stream()
                .filter(value -> value instanceof ModeValue)
                .forEach(value -> ((ModeValue) value).getValue().register());

        this.values.stream()
                .filter(value -> value instanceof BooleanValue)
                .forEach(value -> {
                    final BooleanValue booleanValue = (BooleanValue) value;
                    if (booleanValue.getMode() != null && booleanValue.getValue()) {
                        booleanValue.getMode().register();
                    }
                });

        if (mc.thePlayer != null) this.onEnable();
    }

    /**
     * Called when a module gets disabled
     * -> important: whenever you override this method in a subclass
     * keep the super.onDisable()
     */
    public final void superDisable() {
        Client.INSTANCE.getEventBus().unregister(this);

        this.values.stream()
                .filter(value -> value instanceof ModeValue)
                .forEach(value -> ((ModeValue) value).getValue().unregister());

        this.values.stream()
                .filter(value -> value instanceof BooleanValue)
                .forEach(value -> {
                    final BooleanValue booleanValue = (BooleanValue) value;
                    if (booleanValue.getMode() != null) {
                        booleanValue.getMode().unregister();
                    }
                });

        if (mc.thePlayer != null) this.onDisable();
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }

    public List<Value<?>> getAllValues() {
        ArrayList<Value<?>> allValues = new ArrayList<>();

        values.forEach(value -> {
            List<Value<?>> subValues = value.getSubValues();

            allValues.add(value);

            if (subValues != null) {
                allValues.addAll(subValues);
            }
        });

        return allValues;
    }

    public boolean shouldDisplay(InterfaceModule instance) {
        if (this instanceof ClickGUIModule) return false;
        if (!this.getModuleInfo().allowDisable()) return false;

        switch (instance.getModulesToShow().getValue().getName()) {
            case "All": {
                return true;
            }
            case "Exclude render": {
                return !this.getModuleInfo().category().equals(Category.RENDER);
            }
            case "Only bound": {
                return this.getKeyCode() != Keyboard.KEY_NONE;
            }
        }
        return true;
    }
}