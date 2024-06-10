package me.kaimson.melonclient.gui.utils.blur;

import me.kaimson.melonclient.mixins.client.renderer.*;
import me.kaimson.melonclient.mixins.accessor.*;
import java.util.*;

public class BlurShader
{
    public static final BlurShader INSTANCE;
    private final ave mc;
    private final jy SHADER;
    private Map resourceManager;
    private long start;
    
    public BlurShader() {
        this.mc = ave.A();
        this.SHADER = new jy("melonclient", "shaders/post/fade_in_blur.json");
    }
    
    public void onGuiOpen(final float BLUR_RADIUS) {
        if (this.resourceManager == null) {
            this.resourceManager = ((SimpleReloadableResourceManagerAccessor)ave.A().Q()).getDomainResourceManagers();
        }
        if (!this.resourceManager.containsKey("melonclient")) {
            this.resourceManager.put("melonclient", new BlurResourceManager(BLUR_RADIUS));
        }
        if (this.mc.f != null && !this.mc.o.a()) {
            ((IMixinEntityRenderer)this.mc.o).callLoadShader(this.SHADER);
            this.start = System.currentTimeMillis();
        }
    }
    
    public void onGuiClose() {
        if (this.mc.f != null && this.mc.o.a()) {
            this.mc.o.b();
        }
    }
    
    public void onRenderTick() {
        if (this.mc.m != null && this.mc.o.a()) {
            for (final bls s : ((ShaderGroupAccessor)this.mc.o.f()).getListShaders()) {
                final blv su = s.c().a("Progress");
                if (su != null) {
                    su.a(this.getProgress());
                }
            }
        }
    }
    
    private float getProgress() {
        return Math.min((System.currentTimeMillis() - this.start) / 10.0f, 1.0f);
    }
    
    static {
        INSTANCE = new BlurShader();
    }
}
