package koks.modules;

import koks.Koks;
import koks.event.Event;
import koks.files.impl.KeyBindFile;
import koks.modules.impl.visuals.ClearTag;
import koks.utilities.AnimationModule;
import koks.utilities.value.Value;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.io.FileWriter;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:08
 */
public abstract class Module {

    public Minecraft mc = Minecraft.getMinecraft();

    private String moduleName;
    private String displayName;
    private Category moduleCategory;
    private String moduleInfo = "";
    private String moduleDescription;
    private boolean visible = true, enabled, bypassed, legit;
    private int keyBind;
    private AnimationModule animationModule = new AnimationModule();

    public Module(String moduleName, String moduleDescription, Category moduleCategory) {
        this.moduleName = moduleName;
        this.moduleDescription = moduleDescription;
        this.moduleCategory = moduleCategory;
        this.displayName = moduleName;
        this.animationModule.setUp();
    }

    public enum Category {
        COMBAT, MOVEMENT, PLAYER, UTILITIES, VISUALS, WORLD;
    }

    public abstract void onEvent(Event event);
    public abstract void onEnable(); //Fixxen das es richtig funktioniert
    public abstract void onDisable();

    public void addValue(Value value) {
        Koks.getKoks().valueManager.addValue(value);
    }

    public void toggle() {
        if (enabled) {
            onDisable();
            enabled = false;
        } else {
            this.animationModule.setUp();
            onEnable();
            enabled = true;
        }
    }

    public void setToggled(boolean enabled) {
        if (!enabled) {
            if(mc.thePlayer != null) {
                onDisable();
            }
            this.enabled = false;
        } else {
            if(mc.thePlayer != null) {
                onEnable();
            }
            this.enabled = true;
        }

    }

    public boolean isBypassed() {
        return bypassed;
    }

    public void setBypassed(boolean bypassed) {
        this.bypassed = bypassed;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Category getModuleCategory() {
        return moduleCategory;
    }

    public void setModuleCategory(Category moduleCategory) {
        this.moduleCategory = moduleCategory;
    }

    public String getModuleInfo() {
        return moduleInfo;
    }

    public void setModuleInfo(String moduleInfo) {
        this.moduleInfo = moduleInfo;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isToggled() {
        return enabled;
    }

    public int getKeyBind() {
        return keyBind;
    }

    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public AnimationModule getAnimationModule() {
        return animationModule;
    }

    public void setAnimationModule(AnimationModule animationModule) {
        this.animationModule = animationModule;
    }

    public String getNameForArrayList() {
        return displayName + (moduleInfo.equals("") ? "" : " §7" + moduleInfo);
    }

    public String getNameForArrayList(String color) {
        return displayName + (moduleInfo.equals("") ? "" : " " + color + moduleInfo);
    }

    public String getFinalNameForArrayList() {
        return Koks.getKoks().moduleManager.getModule(ClearTag.class).isToggled() ? displayName : displayName + (moduleInfo.equals("") ? "" : " §7" + moduleInfo);
    }

    public boolean isLegit() {
        return legit;
    }

    public void setLegit(boolean legit) {
        this.legit = legit;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }

}