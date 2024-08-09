package dev.excellent.client.module.api;

import dev.excellent.Excellent;
import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.client.module.impl.misc.Notifications;
import dev.excellent.client.module.impl.render.ClickGui;
import dev.excellent.client.notification.impl.InfoNotification;
import dev.excellent.impl.util.other.SoundUtil;
import dev.excellent.impl.value.Value;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.ModeValue;
import lombok.Data;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Data
public abstract class Module implements IAccess {

    private final String displayName;
    private final List<Value<?>> values = new java.util.ArrayList<>();
    private ModuleInfo moduleInfo;
    private boolean hidden;
    private boolean enabled;
    private int keyCode;

    public Module() {
        if (this.getClass().isAnnotationPresent(ModuleInfo.class)) {
            this.moduleInfo = this.getClass().getAnnotation(ModuleInfo.class);

            this.displayName = this.moduleInfo.name().replaceAll(" ", "");
            this.keyCode = this.moduleInfo.keyBind();
            this.hidden = moduleInfo.hidden();
            setup();
        } else {
            throw new NotImplementedException("@ModuleInfo annotation not found on " + this.getClass().getSimpleName());
        }
    }

    public Module(final ModuleInfo info) {
        this.moduleInfo = info;

        this.displayName = String.join(" ", StringUtils.splitByCharacterTypeCamelCase(this.moduleInfo.name()));
        this.keyCode = this.moduleInfo.keyBind();
    }

    public void toggle() {
        this.setEnabled(!enabled);
        CompletableFuture.runAsync(() -> excellent.getConfigManager().set());
    }

    public String getSuffix() {
        return "";
    }

    public void setEnabled(final boolean enabled) {
        setEnabled(enabled, true);
    }

    public void setEnabled(final boolean enabled, boolean notification) {
        if (this.enabled == enabled || (!this.moduleInfo.allowDisable() && !enabled)) {
            return;
        }
        this.enabled = enabled;
        if (enabled) {
            if (!this.isHidden() && notification && this != ClickGui.singleton.get() && Notifications.singleton.get().isEnabled()) {
                excellent.getNotificationManager().add(0, new InfoNotification(this.moduleInfo.name() + " " + TextFormatting.GREEN + " enabled", 1000));
                if (Notifications.singleton.get().getSound().getValue())
                    SoundUtil.playSound("enabled.wav", Notifications.singleton.get().getVolume().getValue().floatValue());
            }
            superEnable();
        } else {
            if (!this.isHidden() && notification && this != ClickGui.singleton.get() && Notifications.singleton.get().isEnabled()) {
                excellent.getNotificationManager().add(0, new InfoNotification(this.moduleInfo.name() + " " + TextFormatting.RED + " disabled", 1000));
                if (Notifications.singleton.get().getSound().getValue())
                    SoundUtil.playSound("disabled.wav", Notifications.singleton.get().getVolume().getValue().floatValue());
            }
            superDisable();
        }
    }

    /**
     * Called when a module gets enabled
     * -> important: whenever you override this method in a subclass
     * keep the super.onEnable()
     */
    public final void superEnable() {
        Excellent.getInst().getEventBus().register(this);
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

        if (mc.player != null) this.onEnable();
    }

    /**
     * Called when a module gets disabled
     * -> important: whenever you override this method in a subclass
     * keep the super.onDisable()
     */
    public final void superDisable() {
        Excellent.getInst().getEventBus().unregister(this);
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

        if (mc.player != null) this.onDisable();
    }

    protected void setup() {

    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }

    public void onThread() {
    }

    public List<Value<?>> getAllValues() {
        java.util.ArrayList<Value<?>> allValues = new java.util.ArrayList<>();

        values.forEach(value -> {
            List<Value<?>> subValues = value.getSubValues();

            allValues.add(value);

            if (subValues != null) {
                allValues.addAll(subValues);
            }
        });

        return allValues;
    }

    protected static <T extends Module> T link(final Class<T> clazz) {
        return Excellent.getInst().getModuleManager().get(clazz);
    }

}