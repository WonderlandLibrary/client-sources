package com.client.glowclient.sponge.mixin;

import net.minecraft.client.*;
import net.minecraft.crash.*;
import org.apache.logging.log4j.*;
import org.lwjgl.*;
import java.io.*;
import javax.annotation.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.client.glowclient.sponge.mixinutils.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ Minecraft.class })
public abstract class MixinMinecraft
{
    @Shadow
    public boolean field_71425_J;
    @Shadow
    public boolean field_71434_R;
    @Shadow
    public CrashReport field_71433_S;
    @Shadow
    @Final
    public static Logger field_147123_G;
    
    public MixinMinecraft() {
        super();
    }
    
    @Shadow
    public abstract void init() throws LWJGLException, IOException;
    
    @Shadow
    public abstract void displayCrashReport(final CrashReport p0);
    
    @Shadow
    public abstract CrashReport addGraphicsAndWorldToCrashReport(final CrashReport p0);
    
    @Shadow
    public abstract void runGameLoop() throws IOException;
    
    @Shadow
    public abstract void freeMemory();
    
    @Shadow
    public abstract void displayGuiScreen(@Nullable final GuiScreen p0);
    
    @Shadow
    public abstract void shutdownMinecraftApplet();
    
    @Shadow
    public abstract void shutdown();
    
    @Inject(method = { "shutdown()V" }, at = { @At("HEAD") }, cancellable = true)
    public void saveSettingsOnShutdown(final CallbackInfo callbackInfo) {
        HookTranslator.m62();
    }
    
    @Overwrite
    public void run() {
        this.running = true;
        try {
            this.init();
            try {
                if (!HookTranslator.m66()) {
                    this.running = false;
                    return;
                }
            }
            catch (Exception ex2) {}
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, "Initializing game");
            crashReport.makeCategory("Initialization");
            this.displayCrashReport(this.addGraphicsAndWorldToCrashReport(crashReport));
            return;
        }
        try {
            while (this.running) {
                if (this.hasCrashed) {
                    if (this.crashReporter != null) {
                        this.displayCrashReport(this.crashReporter);
                        continue;
                    }
                }
                try {
                    this.runGameLoop();
                }
                catch (OutOfMemoryError outOfMemoryError) {
                    this.freeMemory();
                    this.displayGuiScreen((GuiScreen)new GuiMemoryErrorScreen());
                    System.gc();
                }
            }
        }
        catch (MinecraftError minecraftError) {}
        catch (ReportedException ex) {
            this.addGraphicsAndWorldToCrashReport(ex.getCrashReport());
            this.freeMemory();
            MixinMinecraft.LOGGER.fatal("Reported exception thrown!", (Throwable)ex);
            this.displayCrashReport(ex.getCrashReport());
        }
        catch (Throwable t2) {
            final CrashReport addGraphicsAndWorldToCrashReport = this.addGraphicsAndWorldToCrashReport(new CrashReport("Unexpected error", t2));
            this.freeMemory();
            MixinMinecraft.LOGGER.fatal("Unreported exception thrown!", t2);
            this.displayCrashReport(addGraphicsAndWorldToCrashReport);
        }
        finally {
            this.shutdownMinecraftApplet();
        }
    }
}
