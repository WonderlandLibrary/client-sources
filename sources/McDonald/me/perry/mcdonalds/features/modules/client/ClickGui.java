// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.client;

import net.minecraft.client.gui.GuiScreen;
import me.perry.mcdonalds.util.Util;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.perry.mcdonalds.features.command.Command;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.perry.mcdonalds.McDonalds;
import me.perry.mcdonalds.event.events.ClientEvent;
import net.minecraft.client.settings.GameSettings;
import me.perry.mcdonalds.features.gui.McDonaldsGui;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class ClickGui extends Module
{
    private static ClickGui INSTANCE;
    public Setting<String> prefix;
    public Setting<Boolean> customFov;
    public Setting<Float> fov;
    public Setting<Integer> red;
    public Setting<Integer> green;
    public Setting<Integer> blue;
    public Setting<Integer> hoverAlpha;
    public Setting<Integer> topRed;
    public Setting<Integer> topGreen;
    public Setting<Integer> topBlue;
    public Setting<Integer> alpha;
    public Setting<Boolean> rainbow;
    public Setting<rainbowMode> rainbowModeHud;
    public Setting<rainbowModeArray> rainbowModeA;
    public Setting<Integer> rainbowHue;
    public Setting<Float> rainbowBrightness;
    public Setting<Float> rainbowSaturation;
    private McDonaldsGui click;
    
    public ClickGui() {
        super("Clickgui", "Clickgui", Category.CLIENT, true, false, false);
        this.prefix = (Setting<String>)this.register(new Setting("Prefix", (T)":"));
        this.customFov = (Setting<Boolean>)this.register(new Setting("CustomFov", (T)false));
        this.fov = (Setting<Float>)this.register(new Setting("Fov", (T)150.0f, (T)(-180.0f), (T)180.0f));
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)255, (T)0, (T)255));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)255, (T)0, (T)255));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)0, (T)0, (T)255));
        this.hoverAlpha = (Setting<Integer>)this.register(new Setting("Alpha", (T)240, (T)0, (T)240));
        this.topRed = (Setting<Integer>)this.register(new Setting("SecondRed", (T)255, (T)0, (T)255));
        this.topGreen = (Setting<Integer>)this.register(new Setting("SecondGreen", (T)255, (T)0, (T)255));
        this.topBlue = (Setting<Integer>)this.register(new Setting("SecondBlue", (T)0, (T)0, (T)255));
        this.alpha = (Setting<Integer>)this.register(new Setting("HoverAlpha", (T)240, (T)0, (T)240));
        this.rainbow = (Setting<Boolean>)this.register(new Setting("Rainbow", (T)false));
        this.rainbowModeHud = (Setting<rainbowMode>)this.register(new Setting("HRainbowMode", (T)rainbowMode.Static, v -> this.rainbow.getValue()));
        this.rainbowModeA = (Setting<rainbowModeArray>)this.register(new Setting("ARainbowMode", (T)rainbowModeArray.Static, v -> this.rainbow.getValue()));
        this.rainbowHue = (Setting<Integer>)this.register(new Setting("Delay", (T)240, (T)0, (T)600, v -> this.rainbow.getValue()));
        this.rainbowBrightness = (Setting<Float>)this.register(new Setting("Brightness ", (T)150.0f, (T)1.0f, (T)255.0f, v -> this.rainbow.getValue()));
        this.rainbowSaturation = (Setting<Float>)this.register(new Setting("Saturation", (T)150.0f, (T)1.0f, (T)255.0f, v -> this.rainbow.getValue()));
        this.setBind(24);
        this.setDrawn(false);
        ClickGui.INSTANCE = this;
    }
    
    public static ClickGui getInstance() {
        if (ClickGui.INSTANCE == null) {
            ClickGui.INSTANCE = new ClickGui();
        }
        return ClickGui.INSTANCE;
    }
    
    private void setInstance() {
        ClickGui.INSTANCE = this;
    }
    
    @Override
    public void onUpdate() {
        if (this.customFov.getValue()) {
            ClickGui.mc.gameSettings.setOptionFloatValue(GameSettings.Options.FOV, (float)this.fov.getValue());
        }
    }
    
    @SubscribeEvent
    public void onSettingChange(final ClientEvent event) {
        if (event.getStage() == 2 && event.getSetting().getFeature().equals(this)) {
            if (event.getSetting().equals(this.prefix)) {
                McDonalds.commandManager.setPrefix(this.prefix.getPlannedValue());
                Command.sendMessage("Prefix set to " + ChatFormatting.DARK_GRAY + McDonalds.commandManager.getPrefix());
            }
            McDonalds.colorManager.setColor(this.red.getPlannedValue(), this.green.getPlannedValue(), this.blue.getPlannedValue(), this.hoverAlpha.getPlannedValue());
        }
    }
    
    @Override
    public void onEnable() {
        Util.mc.displayGuiScreen((GuiScreen)McDonaldsGui.getClickGui());
    }
    
    @Override
    public void onLoad() {
        McDonalds.colorManager.setColor(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.hoverAlpha.getValue());
        McDonalds.commandManager.setPrefix(this.prefix.getValue());
    }
    
    @Override
    public void onTick() {
        if (!(ClickGui.mc.currentScreen instanceof McDonaldsGui)) {
            this.disable();
        }
    }
    
    static {
        ClickGui.INSTANCE = new ClickGui();
    }
    
    public enum rainbowModeArray
    {
        Static, 
        Up;
    }
    
    public enum rainbowMode
    {
        Static, 
        Sideway;
    }
}
