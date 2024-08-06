package club.strifeclient.module.implementations.visual;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.event.implementations.system.KeyEvent;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.implementations.DoubleSetting;
import club.strifeclient.util.player.ChatUtil;
import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MotionBlurResourceManager;
import net.minecraft.util.ResourceLocation;

import java.util.Map;
import java.util.function.Supplier;

@ModuleInfo(name = "MotionBlur", description = "Adds motion blur to your game.", category = Category.VISUAL)
public final class MotionBlur extends Module {
    private Map<String, FallbackResourceManager> domainResourceManagers;

    public final DoubleSetting strengthSetting = new DoubleSetting("Strength", 4, 1, 10, 1);

    public MotionBlur() {
        strengthSetting.addChangeCallback((old, change) -> loadShader(true));
    }

    @Override
    public Supplier<Object> getSuffix() {
        return strengthSetting::getValue;
    }

    @EventHandler
    private final Listener<KeyEvent> keyEventListener = e -> {
        if (mc.thePlayer != null && GameSettings.isKeyDown(mc.gameSettings.keyBindTogglePerspective))
            loadShader(true);
    };

    public void onTick() {
        if (!isEnabled()) return;
        if (mc.gameSettings.ofFastRender) {
            ChatUtil.sendMessageWithPrefix("Fast Render is not compatible with Motion Blur. Please disable it to use this module.");
            setEnabled(false);
            return;
        }
        if (domainResourceManagers != null) {
            if (!domainResourceManagers.containsKey("motionblur"))
                domainResourceManagers.put("motionblur", new MotionBlurResourceManager(mc.metadataSerializer_, this));
            if (!mc.entityRenderer.isShaderActive() && mc.thePlayer != null && mc.theWorld != null && !mc.gameSettings.ofFastRender)
                loadShader(false);
        } else
            domainResourceManagers = ((SimpleReloadableResourceManager) mc.getResourceManager()).getDomainResourceManagers();
    }

    private void loadShader(boolean reload) {
        if (mc.entityRenderer.getShaderGroup() != null && reload) {
            mc.entityRenderer.stopUseShader();
        }
        mc.entityRenderer.loadShader(new ResourceLocation("motionblur", "motionblur"));
        if (mc.entityRenderer.getShaderGroup() != null)
            mc.entityRenderer.getShaderGroup().createBindFramebuffers(mc.displayWidth, mc.displayHeight);
    }

    @Override
    protected void onEnable() {
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        if (mc.entityRenderer.getShaderGroup() != null) {
            mc.entityRenderer.stopUseShader();
        }
        super.onDisable();
    }
}
