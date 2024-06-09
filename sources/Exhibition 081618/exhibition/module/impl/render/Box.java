package exhibition.module.impl.render;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventRender3D;
import exhibition.management.ColorManager;
import exhibition.management.friend.FriendManager;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.util.RenderingUtil;
import exhibition.util.TeamUtils;
import exhibition.util.render.Colors;
import java.util.Iterator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class Box extends Module {
   private String ESPMODE = "FILLED";
   private String TEAM = "TEAM";

   public Box(ModuleData data) {
      super(data);
      this.settings.put(this.ESPMODE, new Setting(this.ESPMODE, false, "Fill in box."));
      this.settings.put(this.TEAM, new Setting(this.TEAM, false, "Teams shit."));
      this.settings.put("PLAYERS", new Setting("PLAYERS", true, "Render on Players."));
      this.settings.put("ANIMALS", new Setting("ANIMALS", false, "Render on Animals."));
      this.settings.put("MOBS", new Setting("MOBS", false, "Render on Mobs."));
   }

   @RegisterEvent(
      events = {EventRender3D.class}
   )
   public void onEvent(Event event) {
      EventRender3D er = (EventRender3D)event;
      Iterator var3 = mc.theWorld.getLoadedEntityList().iterator();

      while(var3.hasNext()) {
         Object o = var3.next();
         if (o instanceof EntityLivingBase && o != mc.thePlayer) {
            EntityLivingBase player = (EntityLivingBase)o;
            if (this.isValid(player)) {
               GL11.glPushMatrix();
               RenderingUtil.pre3D();
               mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);
               double x = player.prevPosX + (player.posX - player.prevPosX) * (double)er.renderPartialTicks - RenderManager.renderPosX;
               double y = player.prevPosY + (player.posY - player.prevPosY) * (double)er.renderPartialTicks - RenderManager.renderPosY;
               double z = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)er.renderPartialTicks - RenderManager.renderPosZ;
               int color = ColorManager.getEnemyVisible().getColorInt();
               if (FriendManager.isFriend(player.getName())) {
                  color = mc.thePlayer.canEntityBeSeen(player) ? ColorManager.getFriendlyVisible().getColorInt() : ColorManager.getFriendlyInvisible().getColorInt();
               } else if (!mc.thePlayer.canEntityBeSeen(player)) {
                  color = ColorManager.getEnemyInvisible().getColorInt();
               }

               if (((Boolean)((Setting)this.settings.get(this.TEAM)).getValue()).booleanValue() && player instanceof EntityPlayer) {
                  if (TeamUtils.isTeam(mc.thePlayer, (EntityPlayer)player)) {
                     color = Colors.getColor(20, 40, 255, 100);
                  } else {
                     color = Colors.getColor(255, 40, 20, 100);
                  }
               }

               GlStateManager.translate(x, y, z);
               GlStateManager.rotate(-(player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * er.renderPartialTicks), 0.0F, 1.0F, 0.0F);
               float var5;
               if (player.deathTime > 0) {
                  var5 = ((float)player.deathTime + er.renderPartialTicks - 1.0F) / 20.0F * 1.6F;
                  var5 = MathHelper.sqrt_float(var5);
                  if (var5 > 1.0F) {
                     var5 = 1.0F;
                  }

                  GlStateManager.rotate(-(var5 * 90.0F), 0.0F, 0.0F, 1.0F);
               }

               var5 = player.getCollisionBorderSize();
               AxisAlignedBB var11 = player.getEntityBoundingBox().expand((double)var5, (double)var5, (double)var5);
               AxisAlignedBB var12 = new AxisAlignedBB(var11.minX - player.posX, var11.minY - player.posY, var11.minZ - player.posZ, var11.maxX - player.posX, var11.maxY - player.posY, var11.maxZ - player.posZ);
               if (((Boolean)((Setting)this.settings.get(this.ESPMODE)).getValue()).booleanValue()) {
                  RenderingUtil.filledBox(var12, color, true);
                  RenderingUtil.glColor(color);
                  GL11.glLineWidth(2.0F);
                  RenderingUtil.drawOutlinedBoundingBox(var12);
               } else {
                  RenderingUtil.glColor(color);
                  GL11.glLineWidth(2.0F);
                  RenderingUtil.drawOutlinedBoundingBox(var12);
               }

               RenderingUtil.post3D();
               if (!GL11.glIsEnabled(2896)) {
                  GL11.glEnable(2896);
               }

               GL11.glPopMatrix();
            }
         }
      }

   }

   private boolean isValid(EntityLivingBase player) {
      boolean var10000;
      if (player.isInvisible() || (!(player instanceof EntityPlayer) || player == mc.thePlayer || !((Boolean)((Setting)this.settings.get("PLAYERS")).getValue()).booleanValue()) && (!(player instanceof EntityMob) && !(player instanceof EntitySlime) || !((Boolean)((Setting)this.settings.get("MOBS")).getValue()).booleanValue()) && (!(player instanceof EntityAnimal) || !((Boolean)((Setting)this.settings.get("ANIMALS")).getValue()).booleanValue())) {
         var10000 = false;
      } else {
         var10000 = true;
      }

      return var10000;
   }
}
