// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Render;

import net.minecraft.client.network.badlion.memes.EventTarget;
import java.util.Iterator;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.Badlion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.network.badlion.Utils.RenderUtils;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.network.badlion.Utils.ModeUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.network.badlion.Events.EventRender3D;
import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.network.badlion.Mod.Mod;

public class ESP extends Mod
{
    public ESP() {
        super("ESP", Category.RENDER);
        this.setName("ESP");
        this.setBind(0);
    }
    
    @Override
    public void onEnable() {
        EventManager.register(this);
    }
    
    @Override
    public void onDisable() {
        EventManager.unregister(this);
    }
    
    @EventTarget
    public void onRender3D(final EventRender3D event) {
        for (final Object obj : this.mc.theWorld.loadedEntityList) {
            if (obj instanceof EntityLivingBase) {
                final EntityLivingBase entity = (EntityLivingBase)obj;
                if (entity == this.mc.thePlayer) {
                    continue;
                }
                if (!ModeUtils.isValidForESP(entity)) {
                    continue;
                }
                GL11.glLoadIdentity();
                this.mc.entityRenderer.orientCamera(event.partialTicks);
                this.mc.getRenderManager();
                final double posX = entity.posX - RenderManager.renderPosX;
                this.mc.getRenderManager();
                final double posY = entity.posY - RenderManager.renderPosY;
                this.mc.getRenderManager();
                final double posZ = entity.posZ - RenderManager.renderPosZ;
                if (entity.hurtTime > 5) {
                    RenderUtils.drawEntityESP(posX, posY, posZ, entity.width / 2.5f, entity.height, 0.9f, 0.0f, 0.0f, 0.1f, 0.9f, 0.0f, 0.0f, 0.9f, 0.9f);
                }
                else if (entity instanceof EntityPlayer && !Badlion.getWinter().friendUtils.isFriend(entity.getName())) {
                    RenderUtils.drawEntityESP(posX, posY, posZ, entity.width / 2.5f, entity.height, 0.9f, 0.9f, 0.9f, 0.1f, 0.9f, 0.9f, 0.9f, 0.9f, 0.9f);
                }
                else if ((entity instanceof EntityMob || entity instanceof EntitySlime) && !Badlion.getWinter().friendUtils.isFriend(entity.getName())) {
                    RenderUtils.drawEntityESP(posX, posY, posZ, entity.width / 2.5f, entity.height, 0.9f, 0.0f, 0.0f, 0.1f, 0.9f, 0.0f, 0.0f, 0.9f, 0.9f);
                }
                else if ((entity instanceof EntityCreature || entity instanceof EntityBat || entity instanceof EntitySquid || entity instanceof EntityVillager) && !Badlion.getWinter().friendUtils.isFriend(entity.getName())) {
                    RenderUtils.drawEntityESP(posX, posY, posZ, entity.width / 2.5f, entity.height, 0.0f, 0.9f, 0.2f, 0.1f, 0.0f, 0.9f, 0.0f, 0.9f, 0.9f);
                }
                else {
                    if (!Badlion.getWinter().friendUtils.isFriend(entity.getName())) {
                        continue;
                    }
                    RenderUtils.drawEntityESP(posX, posY, posZ, entity.width / 2.5f, entity.height, 0.0f, 0.9f, 0.9f, 0.1f, 0.0f, 0.9f, 0.9f, 0.9f, 0.9f);
                }
            }
        }
    }
}
