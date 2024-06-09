package com.client.glowclient.modules.render;

import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.utils.*;
import net.minecraft.entity.*;
import net.minecraftforge.client.event.*;

public class Chams extends ModuleContainer
{
    @SubscribeEvent
    public void M(final RenderLivingEvent$Pre renderLivingEvent$Pre) {
        GL11.glEnable(32823);
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1000000.0f);
    }
    
    public Chams() {
        super(Category.RENDER, "Chams", false, -1, "View models through walls");
    }
    
    public boolean M(final EntityLivingBase entityLivingBase) {
        return !entityLivingBase.equals((Object)Wrapper.mc.player) && !entityLivingBase.isDead && (EntityUtils.A((Entity)entityLivingBase) || EntityUtils.e((Entity)entityLivingBase) || EntityUtils.K((Entity)entityLivingBase));
    }
    
    @SubscribeEvent
    public void M(final RenderLivingEvent$Post renderLivingEvent$Post) {
        GL11.glDisable(32823);
        GlStateManager.doPolygonOffset(1.0f, 1000000.0f);
        GlStateManager.disablePolygonOffset();
    }
}
