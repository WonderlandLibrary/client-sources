package net.andrewsnetwork.icarus.module.modules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.event.events.RenderIn3D;
import net.andrewsnetwork.icarus.module.Module;
import net.andrewsnetwork.icarus.module.Module.Category;
import net.andrewsnetwork.icarus.module.modules.Teams;
import net.andrewsnetwork.icarus.values.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

public class Tracers extends Module {
   private final Value animals = new Value("tracers_Animals", "animals", Boolean.valueOf(false), this);
   private final Value enderpearls = new Value("tracers_Enderpearls", "enderpearls", Boolean.valueOf(true), this);
   private final Value invisibles = new Value("tracers_Invisibles", "invisibles", Boolean.valueOf(true), this);
   private final Value items = new Value("tracers_Items", "items", Boolean.valueOf(false), this);
   private final Value mobs = new Value("tracers_Mobs", "mobs", Boolean.valueOf(false), this);
   private final Value players = new Value("tracers_Players", "players", Boolean.valueOf(true), this);
   private boolean shouldBob;
   private final List entities = new ArrayList();
   private int ticks = 0;

   public Tracers() {
      super("Tracers", -29696, Category.RENDER);
   }

   private boolean isValidTarget(Entity entity) {
      boolean valid = false;
      if(entity.isInvisible() && !((Boolean)this.invisibles.getValue()).booleanValue()) {
         return false;
      } else {
         if(entity instanceof EntityPlayer && ((Boolean)this.players.getValue()).booleanValue()) {
            valid = entity != null && entity != mc.thePlayer && entity.isEntityAlive() && entity.ticksExisted > 20;
         } else if(entity instanceof IMob && ((Boolean)this.mobs.getValue()).booleanValue()) {
            valid = entity != null && entity.isEntityAlive() && mc.thePlayer.getDistanceToEntity(entity) <= 30.0F && entity.ticksExisted > 20;
         } else if(entity instanceof IAnimals && !(entity instanceof IMob) && ((Boolean)this.animals.getValue()).booleanValue()) {
            valid = entity != null && entity.isEntityAlive() && mc.thePlayer.getDistanceToEntity(entity) <= 30.0F && entity.ticksExisted > 20;
         } else if(entity instanceof EntityEnderPearl && ((Boolean)this.enderpearls.getValue()).booleanValue()) {
            valid = entity != null && entity.isEntityAlive();
         } else if(entity instanceof EntityItem && ((Boolean)this.items.getValue()).booleanValue()) {
            valid = entity != null && entity.isEntityAlive() && mc.thePlayer.getDistanceToEntity(entity) <= 30.0F;
         }

         return valid;
      }
   }

   public void onEvent(Event event) {
      if(event instanceof RenderIn3D) {
         if(Minecraft.isGuiEnabled()) {
            RenderIn3D render = (RenderIn3D)event;
            Iterator entity = mc.theWorld.loadedEntityList.iterator();

            while(entity.hasNext()) {
               Entity fade = (Entity)entity.next();
               if(fade != null && fade.isEntityAlive()) {
                  this.entities.add(fade);
               }
            }

            GL11.glPushMatrix();
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(2929);
            GL11.glEnable(2848);
            GL11.glDepthMask(false);
            GL11.glLineWidth(1.2F);
            float var16 = 0.0F;
            ++this.ticks;

            for(int var17 = 0; var17 < 100; ++var17) {
               if(this.ticks >= var17) {
                  var16 += 0.01F;
               }
            }

            if(this.ticks > 100) {
               var16 = 1.0F;
            }

            if(mc.theWorld == null) {
               this.ticks = 0;
            }

            if(this.entities.isEmpty()) {
               this.ticks = 0;
            }

            Iterator var5 = mc.theWorld.loadedEntityList.iterator();

            while(true) {
               Entity var18;
               do {
                  if(!var5.hasNext()) {
                     GL11.glDepthMask(true);
                     GL11.glDisable(2848);
                     GL11.glEnable(2929);
                     GL11.glEnable(2896);
                     GL11.glDisable(3042);
                     GL11.glEnable(3553);
                     GL11.glPopMatrix();
                     return;
                  }

                  var18 = (Entity)var5.next();
               } while(!this.isValidTarget(var18));

               double x = var18.lastTickPosX + (var18.posX - var18.lastTickPosX) * (double)render.getPartialTicks() - RenderManager.renderPosX;
               double y = var18.lastTickPosY + (var18.posY - var18.lastTickPosY) * (double)render.getPartialTicks() - RenderManager.renderPosY;
               double z = var18.lastTickPosZ + (var18.posZ - var18.lastTickPosZ) * (double)render.getPartialTicks() - RenderManager.renderPosZ;
               float distance = mc.thePlayer.getDistanceToEntity(var18);
               GL11.glPushMatrix();
               Teams teams = (Teams)Icarus.getModuleManager().getModuleByName("teams");
               float[] color;
               if(Icarus.getFriendManager().isFriend(StringUtils.stripControlCodes(var18.getName()))) {
                  color = new float[]{0.0F, 2.55F, 2.55F};
               } else if(var18 instanceof EntityPlayer && teams.isEnabled() && teams.getTabName((EntityPlayer)var18).length() > 2 && teams.getTabName(mc.thePlayer).startsWith(teams.getTabName((EntityPlayer)var18).substring(0, 2))) {
                  color = new float[]{1.33F, 0.67F, 0.0F};
               } else if(var18 instanceof EntityLivingBase && ((EntityLivingBase)var18).hurtTime > 1) {
                  color = new float[]{2.55F, 0.0F, 0.0F};
               } else if(distance <= 64.0F) {
                  color = new float[]{0.9F, distance / 64.0F, 0.0F};
               } else {
                  color = new float[]{0.0F, 0.9F, 0.0F};
               }

               GL11.glColor4f(color[0], color[1], color[2], var16);
               boolean wasBobbing = mc.gameSettings.viewBobbing;
               mc.gameSettings.viewBobbing = false;
               mc.entityRenderer.setupCameraTransform(((RenderIn3D)event).getPartialTicks(), ((RenderIn3D)event).getPass());
               GL11.glBegin(1);
               GL11.glVertex3d(0.0D, (double)mc.thePlayer.getEyeHeight(), 0.0D);
               GL11.glVertex3d(x, y + (Icarus.getModuleManager().getModuleByName("esp").isEnabled()?1.0D:0.0D), z);
               GL11.glEnd();
               mc.gameSettings.viewBobbing = true;
               GL11.glPopMatrix();
            }
         }
      }
   }

   public void onEnabled() {
      super.onEnabled();
      if(mc.theWorld != null) {
         this.ticks = 0;
      }

   }
}
