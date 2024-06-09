package com.client.glowclient.modules.render;

import net.minecraft.block.material.*;
import net.minecraftforge.client.*;
import org.lwjgl.opengl.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.sponge.mixinutils.*;
import net.minecraftforge.client.event.*;
import com.client.glowclient.events.*;
import com.client.glowclient.utils.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class NoRender extends ModuleContainer
{
    public static BooleanValue effectsHUD;
    public static BooleanValue scoreboard;
    public static BooleanValue hurtcam;
    public static BooleanValue bossOverlay;
    public static BooleanValue blockOverlay;
    public static BooleanValue skyLight;
    public static BooleanValue totemAnim;
    public static BooleanValue fog;
    
    @SubscribeEvent
    public void D(final EntityViewRenderEvent$FogDensity entityViewRenderEvent$FogDensity) {
        if (NoRender.fog.M() && (entityViewRenderEvent$FogDensity.getState().getMaterial().equals(Material.WATER) || entityViewRenderEvent$FogDensity.getState().getMaterial().equals(Material.LAVA))) {
            final boolean canceled = true;
            entityViewRenderEvent$FogDensity.setDensity(0.0f);
            entityViewRenderEvent$FogDensity.setCanceled(canceled);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void M(final RenderGameOverlayEvent renderGameOverlayEvent) {
        if (NoRender.bossOverlay.M()) {
            GuiIngameForge.renderBossHealth = false;
            GL11.glBlendFunc(770, 771);
        }
        else {
            GuiIngameForge.renderBossHealth = true;
        }
        if (NoRender.scoreboard.M()) {
            GuiIngameForge.renderObjective = false;
        }
        else {
            GuiIngameForge.renderObjective = true;
        }
        RenderGameOverlayEvent renderGameOverlayEvent2;
        if (NoRender.blockOverlay.M()) {
            GuiIngameForge.renderVignette = false;
            renderGameOverlayEvent2 = renderGameOverlayEvent;
        }
        else {
            GuiIngameForge.renderVignette = true;
            renderGameOverlayEvent2 = renderGameOverlayEvent;
        }
        if (renderGameOverlayEvent2.getType().equals((Object)RenderGameOverlayEvent$ElementType.HELMET) || renderGameOverlayEvent.getType().equals((Object)RenderGameOverlayEvent$ElementType.PORTAL)) {
            renderGameOverlayEvent.setCanceled(true);
        }
    }
    
    public NoRender() {
        super(Category.RENDER, "NoRender", false, -1, "Stops the rendering of some factors");
    }
    
    static {
        NoRender.totemAnim = ValueFactory.M("NoRender", "TotemAnim", "Disables totem animation", true);
        NoRender.effectsHUD = ValueFactory.M("NoRender", "EffectsHUD", "Disables effects hud", true);
        NoRender.bossOverlay = ValueFactory.M("NoRender", "BossOverlay", "Disables boss bars", true);
        NoRender.scoreboard = ValueFactory.M("NoRender", "Scoreboard", "Disables Scoreboard", true);
        NoRender.hurtcam = ValueFactory.M("NoRender", "Hurtcam", "Disables hurtcam", true);
        NoRender.blockOverlay = ValueFactory.M("NoRender", "BlockOverlay", "Disables Suffocation HUD", true);
        NoRender.fog = ValueFactory.M("NoRender", "Fog", "Disables world fog", true);
        NoRender.skyLight = ValueFactory.M("NoRender", "SkyLight", "Disables sky lighting updates", true);
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (NoRender.hurtcam.M()) {
            HookTranslator.v10 = true;
        }
        else {
            HookTranslator.v10 = false;
        }
        if (NoRender.effectsHUD.M()) {
            HookTranslator.v9 = true;
        }
        else {
            HookTranslator.v9 = false;
        }
        if (NoRender.skyLight.M()) {
            HookTranslator.v17 = true;
            return;
        }
        HookTranslator.v17 = false;
    }
    
    @Override
    public void E() {
        if (NoRender.hurtcam.M()) {
            HookTranslator.v10 = false;
        }
        if (NoRender.effectsHUD.M()) {
            HookTranslator.v9 = false;
        }
        if (NoRender.skyLight.M()) {
            HookTranslator.v17 = false;
        }
        GuiIngameForge.renderBossHealth = true;
        GuiIngameForge.renderObjective = true;
    }
    
    @SubscribeEvent
    public void M(final RenderBlockOverlayEvent renderBlockOverlayEvent) {
        if (NoRender.blockOverlay.M()) {
            renderBlockOverlayEvent.setCanceled(true);
        }
    }
    
    @Override
    public void D() {
        if (NoRender.hurtcam.M()) {
            HookTranslator.v10 = true;
        }
        else {
            HookTranslator.v10 = false;
        }
        if (NoRender.effectsHUD.M()) {
            HookTranslator.v9 = true;
        }
        else {
            HookTranslator.v9 = false;
        }
        if (NoRender.skyLight.M()) {
            HookTranslator.v17 = true;
            return;
        }
        HookTranslator.v17 = false;
    }
    
    @SubscribeEvent
    public void M(final EntityViewRenderEvent$FogDensity entityViewRenderEvent$FogDensity) {
        if (NoRender.fog.M()) {
            final boolean canceled = true;
            entityViewRenderEvent$FogDensity.setDensity(0.0f);
            entityViewRenderEvent$FogDensity.setCanceled(canceled);
        }
    }
    
    @SubscribeEvent
    public void M(final EventRenderWorld eventRenderWorld) {
        try {
            final ItemStack itemActivationItem = Wrapper.mc.entityRenderer.itemActivationItem;
            if (NoRender.totemAnim.M() && itemActivationItem != null && itemActivationItem.getItem() == Items.TOTEM_OF_UNDYING) {
                Wrapper.mc.entityRenderer.itemActivationItem = null;
            }
        }
        catch (Exception ex) {}
    }
}
