// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.render;

import net.minecraft.util.MotionBlurResourceManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import xyz.niggfaclient.utils.other.Printer;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.TickEvent;
import xyz.niggfaclient.eventbus.Listener;
import net.minecraft.client.resources.FallbackResourceManager;
import java.util.Map;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "MotionBlur", description = "Smooth Camera", cat = Category.RENDER)
public class MotionBlur extends Module
{
    public static DoubleProperty blurStrength;
    private Map<String, FallbackResourceManager> domainResourceManagers;
    public double lastValue;
    @EventLink
    private final Listener<TickEvent> tickEventListener;
    
    public MotionBlur() {
        double currentValue;
        this.tickEventListener = (e -> {
            try {
                if (this.mc.gameSettings.ofFastRender) {
                    Printer.addMessage("Fast Render is not compatible with Motion Blur. Please disable it to use this module.");
                    this.toggle();
                }
                else {
                    currentValue = MotionBlur.blurStrength.getValue();
                    if (!this.mc.entityRenderer.isShaderActive() && this.mc.theWorld != null) {
                        this.mc.entityRenderer.loadShader(new ResourceLocation("motionblur", "motionblur"));
                    }
                    if (this.domainResourceManagers == null) {
                        this.domainResourceManagers = ((SimpleReloadableResourceManager)this.mc.mcResourceManager).getDomainResourceManagers();
                    }
                    if (!this.domainResourceManagers.containsKey("motionblur")) {
                        this.domainResourceManagers.put("motionblur", new MotionBlurResourceManager(this.mc.metadataSerializer_));
                    }
                    if (currentValue != this.lastValue) {
                        this.domainResourceManagers.remove("motionblur");
                        this.domainResourceManagers.put("motionblur", new MotionBlurResourceManager(this.mc.metadataSerializer_));
                        this.mc.entityRenderer.loadShader(new ResourceLocation("motionblur", "motionblur"));
                    }
                    this.lastValue = currentValue;
                }
            }
            catch (Throwable ex) {
                ex.printStackTrace();
            }
        });
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.mc.entityRenderer.getShaderGroup() != null) {
            this.mc.entityRenderer.stopUsingShader();
        }
    }
    
    static {
        MotionBlur.blurStrength = new DoubleProperty("Strength", 4.0, 1.0, 10.0, 0.1);
    }
}
