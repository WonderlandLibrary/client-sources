package club.mega.module;

import club.mega.Mega;
import club.mega.interfaces.MinecraftInterface;
import club.mega.module.impl.hud.ModuleList;
import club.mega.module.impl.hud.Sounds;
import club.mega.module.setting.Setting;
import club.mega.util.AudioUtil;
import club.mega.util.RenderUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;

public class Module implements MinecraftInterface {

    private final ModuleInfo moduleInfo = getClass().getAnnotation(ModuleInfo.class);
    private final String name = moduleInfo.name(), description = moduleInfo.description();
    private final Category category = moduleInfo.category();
    private boolean toggled;
    private int key;
    private double currentWidth, currentHeight, targetHeight, targetWidth;

    private final ArrayList<Setting> settings = new ArrayList<>();

    public void onEnable() {
        Mega.INSTANCE.getPubSub().subscribe(this);

        if (MC.theWorld != null) {
            targetWidth = RenderUtil.getScaledResolution().getScaledWidth() - Mega.INSTANCE.getFontManager().getFont("Arial 22").getWidth(name);
            targetHeight = 13;
        }
        if (Mega.INSTANCE.getModuleManager().isToggled(Sounds.class))
            AudioUtil.playSound("enable");
    }

    public void onDisable() {
        Mega.INSTANCE.getPubSub().unsubscribe(this);

        if (MC.theWorld != null) {
            targetWidth = RenderUtil.getScaledResolution().getScaledWidth();
            targetHeight = 0;
        }
        if (Mega.INSTANCE.getModuleManager().isToggled(Sounds.class))
            AudioUtil.playSound("disable");
    }

    public final void toggle() {
        toggled = !toggled;
        if (toggled)
            onEnable();
        else
            onDisable();
    }

    public final boolean isToggled() {
        return toggled;
    }

    public final void setToggled(final boolean toggled) {
        if (this.toggled != toggled)
            toggle();
    }

    public final String getName() {
        return name;
    }

    public final String getDescription() {
        return description;
    }

    public final Category getCategory() {
        return category;
    }

    public final ArrayList<Setting> getSettings() {
        return settings;
    }

    public final Setting getSetting(final String name) {
        return settings.stream().filter(setting -> setting.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public final int getKey() {
        return key;
    }

    public final void setKey(final int key) {
        this.key = key;
    }

    public final double getCurrentWidth() {
        return currentWidth;
    }

    public final double getCurrentHeight() {
        return currentHeight;
    }

    public final void setCurrentWidth(final double currentWidth) {
        this.currentWidth = currentWidth;
    }

    public final void setCurrentHeight(final double current) {
        this.currentHeight = current;
    }

    public final double getTargetHeight() {
        return targetHeight;
    }

    public final double getTargetWidth() {
        return targetWidth;
    }

    public final void setTargetHeight(final double targetHeight) {
        this.targetHeight = targetHeight;
    }

    public final void setTargetWidth(final double target) {
        this.targetWidth = target;
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ModuleInfo {

        String name();
        String description();
        Category category();

    }

}
