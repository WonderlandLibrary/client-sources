// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.render;

import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import me.chrest.event.EventTarget;
import java.util.Iterator;
import me.chrest.utils.GuiUtils;
import net.minecraft.client.renderer.entity.RenderManager;
import me.chrest.client.friend.FriendManager;
import net.minecraft.entity.Entity;
import me.chrest.utils.RenderUtils;
import java.awt.Color;
import net.minecraft.entity.EntityLivingBase;
import me.chrest.utils.ClientUtils;
import me.chrest.event.events.Render3DEvent;
import me.chrest.client.option.Option;
import me.chrest.client.module.Module;

@Mod(shown = false, displayName = "ESP")
public class ESP extends Module
{
    @Option.Op
    private boolean players;
    @Option.Op
    private boolean monsters;
    @Option.Op
    private boolean animals;
    @Option.Op(name = "2D ESP")
    private boolean twoDimension;
    @Option.Op(name = "Hue", min = 0.1, max = 1.0, increment = 0.025)
    private double normalHue;
    @Option.Op(name = "Custom Color")
    private boolean customColor;
    private int state;
    private static float[] rainbowArray;
    
    public ESP() {
        this.players = true;
    }
    
    @EventTarget(0)
    private void onRender3D(final Render3DEvent event) {
        if (!this.twoDimension) {
            for (final Object o : ClientUtils.world().loadedEntityList) {
                if (o instanceof EntityLivingBase && o != ClientUtils.mc().thePlayer) {
                    final EntityLivingBase entity = (EntityLivingBase)o;
                    if (!this.checkValidity(entity)) {
                        continue;
                    }
                    final double color = Color.HSBtoRGB((float)this.normalHue, 0.9f, 0.9f);
                    if (!this.twoDimension) {
                        RenderUtils.drawEsp(entity, event.getPartialTicks(), -1, this.customColor ? ((entity.hurtTime != 0) ? 1224255388 : ((int)(color + 1.17440512E9))) : ((entity.hurtTime != 0) ? 1224255388 : 1174405120));
                    }
                    else {
                        if (!entity.isEntityAlive()) {
                            return;
                        }
                        continue;
                    }
                }
            }
        }
        else {
            for (final Object o : ClientUtils.world().loadedEntityList) {
                if (o instanceof EntityLivingBase && o != ClientUtils.mc().thePlayer) {
                    final EntityLivingBase entity = (EntityLivingBase)o;
                    if (entity == ClientUtils.player() || !this.checkValidity(entity) || !entity.isEntityAlive()) {
                        continue;
                    }
                    final float posX = (float)((float)entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * ClientUtils.mc().timer.renderPartialTicks);
                    final float posY = (float)((float)entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * ClientUtils.mc().timer.renderPartialTicks);
                    final float posZ = (float)((float)entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * ClientUtils.mc().timer.renderPartialTicks);
                    final float distance = ClientUtils.player().getDistanceToEntity(entity);
                    final float health = entity.getHealth();
                    if (!FriendManager.isFriend(entity.getName())) {
                        final float percent = health / 2.0f;
                        if (percent >= 6.0f) {
                            GuiUtils.draw2D(entity, posX - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ - RenderManager.renderPosZ, 0.1f, 1.0f, 0.1f, 255.0f);
                        }
                        if (percent < 6.0f) {
                            GuiUtils.draw2D(entity, posX - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ - RenderManager.renderPosZ, 1.0f, 0.5f, 0.0f, 255.0f);
                        }
                        if (percent >= 3.0f) {
                            continue;
                        }
                        GuiUtils.draw2D(entity, posX - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ - RenderManager.renderPosZ, 1.0f, 0.0f, 0.0f, 255.0f);
                    }
                    else {
                        GuiUtils.draw2D(entity, posX - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ - RenderManager.renderPosZ, 0.0f, 0.7f, 0.8f, 255.0f);
                    }
                }
            }
        }
    }
    
    private boolean checkValidity(final EntityLivingBase entity) {
        if (entity instanceof EntityOtherPlayerMP) {
            return this.players;
        }
        return (this.monsters && entity instanceof EntityMob) || (this.animals && entity instanceof EntityAnimal) || (this.animals && entity instanceof EntityBat);
    }
}
