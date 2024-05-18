package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.tileentity.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.modules.player.*;
import wwe.modules.world.*;
import wwe.*;
import java.text.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.gui.*;
import java.io.*;
import net.minecraft.util.text.*;

@Mixin(value = { GuiEditSign.class }, priority = 9999)
public class MixinGuiEditSign extends GuiScreen
{
    @Final
    @Shadow
    private TileEntitySign field_146848_f;
    @Shadow
    private int field_146851_h;
    public xhCH sign;
    
    public MixinGuiEditSign() {
        super();
    }
    
    @Inject(method = { "initGui" }, at = { @At("RETURN") }, cancellable = true)
    public void initGui(final CallbackInfo callbackInfo) {
        if (vlwv.hNxW.qHPF((Class<AutoSign>)BTPB.class).IuiN() && this.tileSign.signText[0] != null && this.tileSign.signText[1] != null && this.tileSign.signText[2] != null && this.tileSign.signText[3] != null) {
            this.tileSign.signText[0] = AutoSign.ALQZ[0];
            this.tileSign.signText[1] = AutoSign.ALQZ[1];
            this.tileSign.signText[2] = AutoSign.ALQZ[2];
            this.tileSign.signText[3] = AutoSign.ALQZ[3];
        }
        if (vlwv.hNxW.qHPF((Class<TimeStamp>)uLER.class).IuiN()) {
            this.tileSign.signText[3] = (ITextComponent)new TextComponentString(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
        }
        this.sign = new xhCH(this.tileSign);
        if (this.sign != null) {
            this.sign.vhfY();
        }
    }
    
    @Inject(method = { "drawScreen" }, at = { @At("RETURN") }, cancellable = true)
    public void drawScreen(final int n, final int n2, final float n3, final CallbackInfo callbackInfo) {
        if (this.sign != null) {
            this.sign.KqvF(n, n2, n3);
        }
    }
    
    @Inject(method = { "updateScreen" }, at = { @At("RETURN") }, cancellable = true)
    public void updateScreen(final CallbackInfo callbackInfo) {
        if (this.sign != null) {
            this.sign.Txia();
        }
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At("RETURN") }, cancellable = true)
    public void actionPerformed(final GuiButton guiButton, final CallbackInfo callbackInfo) throws IOException {
        if (this.sign != null) {
            this.sign.Rwph(guiButton);
        }
    }
    
    @Inject(method = { "keyTyped" }, at = { @At("RETURN") }, cancellable = true)
    public void keyTyped(final char c, final int n, final CallbackInfo callbackInfo) throws IOException {
        if (this.sign != null) {
            this.sign.dufy(c, n);
        }
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        if (this.sign != null) {
            this.sign.boNl(n, n2, n3, this.editLine);
        }
        super.mouseClicked(n, n2, n3);
    }
    
    public void func_146274_d() throws IOException {
        if (this.sign != null) {
            this.sign.uPlD();
        }
        super.handleMouseInput();
    }
}
