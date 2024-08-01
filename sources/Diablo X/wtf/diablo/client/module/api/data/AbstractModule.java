package wtf.diablo.client.module.api.data;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.module.api.management.IModule;
import wtf.diablo.client.module.impl.render.ArrayListModule;
import wtf.diablo.client.module.impl.render.NotificationsModule;
import wtf.diablo.client.notification.Notification;
import wtf.diablo.client.notification.NotificationType;
import wtf.diablo.client.setting.api.AbstractSetting;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.util.math.time.Animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO: optimize the fuckin suffix as its doggy
public abstract class AbstractModule implements IModule {
    private final BooleanSetting isHidden = new BooleanSetting("Hide Module", false);

    protected final Minecraft mc;
    private final Animation animation = new Animation();
    private final ModuleMetaData metaData;
    private final List<AbstractSetting<?>> settingList;
    private final String name, description;
    private final ModuleCategoryEnum moduleCategoryEnum;
    private String suffix;
    private int key;
    private boolean enabled;

    protected AbstractModule() {
        this.mc = Minecraft.getMinecraft();
        this.settingList = new ArrayList<>();
        this.metaData = this.getClass().getAnnotation(ModuleMetaData.class);
        this.suffix = "";
        if (this.getClass().isAnnotationPresent(ModuleMetaData.class)) {
            this.name = metaData.name();
            this.description = metaData.description();
            this.moduleCategoryEnum = this.metaData.category();
            this.key = metaData.key();
        } else {
            throw new RuntimeException("Module Meta Data isn't provided!");
        }

        registerSettings();
    }

    protected final void registerSettings(final AbstractSetting<?>... settings) {
        this.settingList.clear();
        this.settingList.addAll(Arrays.asList(settings));
        this.settingList.add(isHidden);
    }

    @Override
    public final List<AbstractSetting<?>> getSettingList() {
        return this.settingList;
    }

    @Override
    public final AbstractSetting<?> getSettingByName(final String name) {
        return this.settingList.stream().filter(setting -> setting.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    protected void onEnable() {
        Diablo.getInstance().getEventBus().register(this);
    }

    protected void onDisable() {
        if (Diablo.getInstance().getEventBus().isRegistered(this)) {
            Diablo.getInstance().getEventBus().unregister(this);
        }
    }

    @Override
    public final void toggle() {
        toggle(!this.enabled);
    }

    @Override
    public final void toggle(final boolean value) {
        this.enabled = value;
        this.startAnimation();

        if (this.enabled) {
            this.onEnable();
        } else {
            this.onDisable();
        }

        final NotificationsModule notificationsModule = Diablo.getInstance().getModuleRepository().getModuleInstance(NotificationsModule.class);

        if (notificationsModule != null && notificationsModule.getToggleNotifications().getValue()) {
            Diablo.getInstance().getNotificationManager().addNotification(new Notification((this.isEnabled() ? "Enabled" : "Disabled") + " " + this.getName(), (this.isEnabled() ? "Toggled" : "Disabled") + " " + this.getName(), 1500, this.isEnabled() ?
                    NotificationType.SUCCESS : NotificationType.ERROR));
        }
    }

    @Override
    public final ModuleMetaData getData() {
        return this.metaData;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public void setKey(int key) {
        this.key = key;
    }

    public final String getSuffix() {
        return suffix;
    }

    public final void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

    @Override
    public final String getDisplayName() {
        String name = this.name;

        final ArrayListModule arrayListModule = Diablo.getInstance().getModuleRepository().getModuleInstance(ArrayListModule.class);

        if (!this.suffix.isEmpty()) {
            name += " " + ChatFormatting.GRAY;

            switch (arrayListModule.getSuffixMode()) {
                case NONE:
                    name += this.suffix;
                    break;
                case BRACKET:
                    name += "[" + this.suffix + "]";
                    break;
                case DASH:
                    name += "- " + this.suffix;
                    break;
                case COLON:
                    name += ": " + this.suffix;
                    break;
                case PARENTHESIS:
                    name += "(" + this.suffix + ")";
                    break;
            }
        }

        switch (arrayListModule.getCasingMode()) {
            case LOWERCASE:
                name = name.toLowerCase();
                break;
            case UPPERCASE:
                name = name.toUpperCase();
                break;
            default:
                break;
        }

        return name;
    }

    public final void updateRender() {
        animation.updateAnimation();
    }

    public final boolean isAnimating() {
        return !animation.hasFinished();
    }

    public final void startAnimation() {
        animation.setReverse(!enabled);
        animation.setSpeed(0.2);
        animation.setAmount(50);
        animation.start();
    }

    public Animation getAnimation() {
        return animation;
    }

    @Override
    public ModuleCategoryEnum getModuleCategoryEnum() {
        return moduleCategoryEnum;
    }

    public boolean isHidden() {
        return isHidden.getValue();
    }

    public void setHidden(final boolean value) {
        isHidden.setValue(value);
    }
}
