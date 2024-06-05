package cn.Hanabi.injection.mixins;

import cn.Hanabi.injection.interfaces.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import net.minecraft.client.main.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import cn.Hanabi.*;
import org.spongepowered.asm.mixin.injection.*;
import ClassSub.*;
import java.io.*;
import com.darkmagician6.eventapi.*;
import com.darkmagician6.eventapi.events.*;
import org.lwjgl.input.*;
import cn.Hanabi.events.*;

@SideOnly(Side.CLIENT)
@Mixin({ Minecraft.class })
public abstract class MixinMinecraft implements IMinecraft
{
    @Shadow
    public int rightClickDelayTimer;
    long lastFrame;
    @Shadow
    public GuiScreen currentScreen;
    @Shadow
    @Mutable
    @Final
    private Session session;
    @Shadow
    private LanguageManager mcLanguageManager;
    @Shadow
    private Timer timer;
    @Shadow
    private int leftClickCounter;
    
    @Shadow
    protected abstract void clickMouse();
    
    @Override
    public void runCrinkMouse() {
        this.clickMouse();
    }
    
    @Override
    public void setClickCounter(final int a) {
        this.leftClickCounter = a;
    }
    
    @Inject(method = { "<init>" }, at = { @At("RETURN") })
    private void minecraftConstructor(final GameConfiguration gameConfig, final CallbackInfo ci) {
        if (Integer.valueOf(System.getProperty("java.version").split("_")[1]) >= 180) {
            Class334.username = "";
            Class334.password = "";
            Class334.prepare();
            if (Class211.c4n && Class211.cr4ckm3 && Class211.If && Class211.y0u) {
                new Hanabi();
            }
            return;
        }
        Class64.showMessageBox("启动失败。请更新Java�?");
        while (true) {}
    }
    
    @Inject(method = { "startGame" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;ingameGUI:Lnet/minecraft/client/gui/GuiIngame;", shift = At.Shift.AFTER) })
    private void startGame(final CallbackInfo ci) {
        if (Class211.c4n && Class211.cr4ckm3 && Class211.If && Class211.y0u) {
            Hanabi.INSTANCE.startClient();
        }
    }
    
    @Inject(method = { "runGameLoop" }, at = { @At("HEAD") })
    private void runGameLoop(final CallbackInfo ci) throws IOException {
        final long i = System.nanoTime();
        final long thisFrame = System.currentTimeMillis();
        Class246.delta = (thisFrame - this.lastFrame) / 1000.0f;
        this.lastFrame = thisFrame;
        Class334.onGameLoop();
    }
    
    @Inject(method = { "clickMouse" }, at = { @At("HEAD") })
    private void clickMouse(final CallbackInfo ci) {
        EventManager.call(new EventClickMouse());
    }
    
    @Inject(method = { "runTick" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V", shift = At.Shift.AFTER) })
    private void onKey(final CallbackInfo ci) {
        if (Keyboard.getEventKeyState() && this.currentScreen == null) {
            EventManager.call(new EventKey((Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + 'Ā') : Keyboard.getEventKey()));
        }
    }
    
    @Inject(method = { "shutdown" }, at = { @At("HEAD") })
    private void onShutdown(final CallbackInfo ci) {
        Hanabi.INSTANCE.stopClient();
    }
    
    @Override
    public Session getSession() {
        return this.session;
    }
    
    @Override
    public void setSession(final Session session) {
        this.session = session;
    }
    
    @Override
    public Timer getTimer() {
        return this.timer;
    }
    
    @Override
    public LanguageManager getLanguageManager() {
        return this.mcLanguageManager;
    }
    
    @Override
    public void setRightClickDelayTimer(final int i) {
        this.rightClickDelayTimer = i;
    }
}
