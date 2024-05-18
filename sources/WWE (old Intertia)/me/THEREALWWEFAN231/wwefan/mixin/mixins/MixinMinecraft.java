package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import net.minecraft.client.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.multiplayer.*;
import org.lwjgl.opengl.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.lwjgl.input.*;
import wwe.modules.render.*;
import wwe.modules.player.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.gui.*;
import wwe.*;

@Mixin(value = { Minecraft.class }, priority = 9999)
public class MixinMinecraft
{
    @Shadow
    public RayTraceResult field_71476_x;
    @Shadow
    public EntityPlayerSP field_71439_g;
    @Shadow
    public WorldClient field_71441_e;
    
    public MixinMinecraft() {
        super();
    }
    
    @Redirect(method = { "createDisplay" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V"))
    public void display(final String s) {
        Display.setTitle("WWE Version 2.7.2");
    }
    
    @Inject(method = { "runTickKeyboard" }, at = { @At(value = "INVOKE", remap = false, target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", ordinal = 0, shift = At.Shift.BEFORE) }, cancellable = true)
    private void onKeyboard(final CallbackInfo callbackInfo) {
        final int n = (Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + '\u0100') : Keyboard.getEventKey();
        if (!Keyboard.isKeyDown(61) && Keyboard.getEventKeyState()) {
            for (final qMIe qmIe : vlwv.hNxW.ijZl) {
                if (n == qmIe.APjp()) {
                    qmIe.GDVp();
                }
            }
            if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).IuiN() && vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).tabGui.OnGi()) {
                vlwv.VFOG.MSFO(n);
            }
            vlwv.hNxW.qHPF((Class<Announcer>)xbUc.class).wZaB(n);
            if (Character.toString(Keyboard.getEventCharacter()).equals(DhFe.itOx)) {
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiChat(DhFe.itOx));
                callbackInfo.cancel();
            }
        }
    }
    
    @Inject(method = { "runTick()V" }, at = { @At("RETURN") })
    public void runTick(final CallbackInfo callbackInfo) {
        if (ssGO.jMLq && Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new qdlI());
        }
        else if (!ssGO.jMLq && Minecraft.getMinecraft().currentScreen instanceof qdlI) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiMainMenu());
        }
    }
    
    @Inject(method = { "middleClickMouse" }, at = { @At("HEAD") })
    private void middleClickMouse(final CallbackInfo callbackInfo) {
        vlwv.hNxW.KSAz();
    }
    
    @Inject(method = { "rightClickMouse" }, at = { @At("HEAD") })
    private void rightClickMouse(final CallbackInfo callbackInfo) {
        final KhbB khbB = new KhbB();
        hSNE.Seqo(khbB);
        if (khbB.CEMd()) {
            callbackInfo.cancel();
        }
    }
}
