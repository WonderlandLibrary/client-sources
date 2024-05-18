package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import java.text.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.util.text.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.*;
import wwe.utils.*;
import wwe.*;
import net.minecraft.client.multiplayer.*;

@Mixin(value = { GuiDisconnected.class }, priority = 9999)
public class MixinGuiDisconnected extends GuiScreen
{
    @Shadow
    private int field_175353_i;
    private int wweTimer;
    private DecimalFormat format;
    
    public MixinGuiDisconnected() {
        super();
        this.format = new DecimalFormat("#");
    }
    
    @Inject(method = { "initGui" }, at = { @At("RETURN") })
    public void init(final CallbackInfo callbackInfo) {
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 48, "Auto Reconnect " + (vlwv.lHpz ? (TextFormatting.GREEN + "On") : (TextFormatting.RED + "Off"))));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 24, "Reconnect"));
        if (vlwv.lHpz) {
            this.wweTimer = 100;
        }
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At("HEAD") })
    public void actionPerformed(final GuiButton guiButton, final CallbackInfo callbackInfo) {
        if (guiButton.id == 1) {
            vlwv.lHpz = !vlwv.lHpz;
            Wrapper.ypJK().OsgT();
            if (vlwv.lHpz) {
                this.wweTimer = 100;
            }
            this.buttonList.clear();
            this.initGui();
        }
        if (guiButton.id == 2 && vlwv.FjUS != null) {
            this.mc.displayGuiScreen((GuiScreen)new GuiConnecting(yYGD.qgYJ(GuiDisconnected.class.cast(this)), this.mc, vlwv.FjUS));
        }
    }
    
    public void func_73876_c() {
        if (vlwv.lHpz) {
            --this.wweTimer;
            if (this.wweTimer / 20 <= 0 && vlwv.FjUS != null) {
                this.mc.displayGuiScreen((GuiScreen)new GuiConnecting(yYGD.qgYJ(GuiDisconnected.class.cast(this)), this.mc, vlwv.FjUS));
                this.wweTimer = 100;
            }
            if (this.buttonList.size() >= 1 && this.buttonList.get(1) != null) {
                this.buttonList.get(1).displayString = "AutoReconnect " + this.format.format(this.wweTimer / 20);
            }
        }
    }
}
