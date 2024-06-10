package me.kaimson.melonclient.mixins.client;

import org.spongepowered.asm.mixin.injection.callback.*;
import me.kaimson.melonclient.*;
import org.lwjgl.input.*;
import org.spongepowered.asm.mixin.injection.*;
import me.kaimson.melonclient.features.*;
import org.lwjgl.opengl.*;
import org.lwjgl.*;
import java.nio.*;
import java.io.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ ave.class })
public abstract class MixinMinecraft
{
    @Shadow
    @Final
    private bna aB;
    @Shadow
    private boolean T;
    @Shadow
    public int d;
    @Shadow
    public int e;
    
    @Inject(method = { "startGame" }, at = { @At("HEAD") })
    private void startGame(final CallbackInfo ci) {
        Client.INSTANCE.onPreInit();
    }
    
    @Inject(method = { "startGame" }, at = { @At("RETURN") })
    private void postStartGame(final CallbackInfo ci) {
        Client.INSTANCE.onPostInit();
    }
    
    @Inject(method = { "shutdownMinecraftApplet" }, at = { @At("HEAD") })
    private void shutdownMinecraftApplet(final CallbackInfo ci) {
        Client.INSTANCE.onShutdown();
    }
    
    @Inject(method = { "dispatchKeypresses" }, at = { @At("HEAD") })
    private void dispatchKeypresses(final CallbackInfo ci) {
        final int i = (Keyboard.getEventKey() == 0) ? Keyboard.getEventCharacter() : Keyboard.getEventKey();
        if (i != 0 && !Keyboard.isRepeatEvent() && (!(ave.A().m instanceof ayj) || (((ayj)ave.A().m).g <= ave.J() - 20L && Keyboard.getEventKeyState()))) {
            Client.INSTANCE.onKeyPress(i);
        }
    }
    
    @ModifyArg(method = { "createDisplay" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V", remap = false))
    private String getDisplayTitle(final String title) {
        return "Melon Client 1.8.9";
    }
    
    @Inject(method = { "toggleFullscreen" }, at = { @At(value = "INVOKE", remap = false, target = "Lorg/lwjgl/opengl/Display;setVSyncEnabled(Z)V", shift = At.Shift.AFTER) })
    private void toggleFullscreen(final CallbackInfo ci) throws LWJGLException {
        if (SettingsManager.INSTANCE.borderlessWindow.getBoolean()) {
            if (this.T) {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
                Display.setDisplayMode(Display.getDesktopDisplayMode());
                Display.setLocation(0, 0);
                Display.setFullscreen(false);
            }
            else {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
                Display.setDisplayMode(new DisplayMode(this.d, this.e));
            }
        }
        else {
            Display.setFullscreen(this.T);
            System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
        }
        Display.setResizable(false);
        Display.setResizable(true);
    }
    
    @Overwrite
    private void ar() {
        if (g.a() != g.a.d) {
            try {
                InputStream inputStream = MixinMinecraft.class.getResourceAsStream("/assets/minecraft/melonclient/icons/windows/logo16.png");
                InputStream inputStream2 = MixinMinecraft.class.getResourceAsStream("/assets/minecraft/melonclient/icons/windows/logo32.png");
                if (inputStream == null) {
                    inputStream = this.aB.c(new jy("icons/icon_16x16.png"));
                }
                if (inputStream2 == null) {
                    inputStream2 = this.aB.c(new jy("icons/icon_32x32.png"));
                }
                Display.setIcon(new ByteBuffer[] { this.a(inputStream), this.a(inputStream2) });
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Shadow
    protected abstract ByteBuffer a(final InputStream p0);
    
    @Overwrite
    public int j() {
        return (ave.A().f == null && ave.A().m != null) ? 60 : ave.A().t.g;
    }
}
