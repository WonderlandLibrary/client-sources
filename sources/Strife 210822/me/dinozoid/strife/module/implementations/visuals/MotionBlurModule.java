package me.dinozoid.strife.module.implementations.visuals;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.system.KeyEvent;
import me.dinozoid.strife.event.implementations.system.TickEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.property.Property;
import me.dinozoid.strife.property.implementations.DoubleProperty;
import me.dinozoid.strife.util.player.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MotionBlurResource;
import net.minecraft.util.MotionBlurResourceManager;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

@ModuleInfo(name = "MotionBlur", renderName = "MotionBlur", description = "Self explanatory.", category = Category.VISUALS)
public class MotionBlurModule extends Module {

    private DoubleProperty blurStrength = new DoubleProperty("Strength", 4, 1, 10, 1, Property.Representation.INT);

    private Map<String, FallbackResourceManager> domainResourceManagers;

    @Override
    public void init() {
        super.init();
        addValueChangeListener(blurStrength);
    }

    private void loadShader(boolean reload) {
        if (mc.entityRenderer.getShaderGroup() != null && reload) {
            mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
        mc.entityRenderer.loadShader(new ResourceLocation("motionblur", "motionblur"));
        mc.entityRenderer.getShaderGroup().createBindFramebuffers(mc.displayWidth, mc.displayHeight);
    }

    @Override
    public void onDisable() {
        if (mc.entityRenderer.getShaderGroup() != null) {
            mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            mc.entityRenderer.setShaderGroup(null);
        }
        super.onDisable();
    }

    @EventHandler
    private final Listener<TickEvent> tickListener = new Listener<>(event -> {
        if(mc.gameSettings.ofFastRender) {
            PlayerUtil.sendMessageWithPrefix("&7Fast Render is not compatible with Motion Blur. Please disable it to use this module.");
            toggle();
            return;
        }
        if (domainResourceManagers != null) {
            if (!domainResourceManagers.containsKey("motionblur"))
                domainResourceManagers.put("motionblur", new MotionBlurResourceManager(mc.metadataSerializer_));
            if(!mc.entityRenderer.isShaderActive() && mc.thePlayer != null && mc.theWorld != null && !mc.gameSettings.ofFastRender)
                loadShader(false);
        } else domainResourceManagers = ((SimpleReloadableResourceManager)mc.getResourceManager()).getDomainResourceManagers();
    });

    @EventHandler
    private final Listener<KeyEvent> keyListener = new Listener<>(event -> {
       if(mc.thePlayer != null && GameSettings.isKeyDown(mc.gameSettings.keyBindTogglePerspective))
           loadShader(true);
    });

    public static MotionBlurModule instance() {
        return StrifeClient.INSTANCE.moduleRepository().moduleBy(MotionBlurModule.class);
    }

    public DoubleProperty blurStrength() {
        return blurStrength;
    }
}
