package hawk.modules.render;

import hawk.events.Event;
import hawk.events.listeners.EventRender3D;
import hawk.modules.Module;
import hawk.util.RenderUtil;
import java.awt.Color;
import java.util.Iterator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

public class ESP extends Module {
   public void onDisable() {
   }

   public void onEvent(Event var1) {
      if (var1 instanceof EventRender3D) {
         Iterator var3 = this.mc.theWorld.loadedEntityList.iterator();

         while(var3.hasNext()) {
            Object var2 = var3.next();
            if (var2 instanceof EntityLivingBase) {
               EntityLivingBase var4 = (EntityLivingBase)var2;
               AxisAlignedBB var5 = new AxisAlignedBB(var4.boundingBox.minX - var4.posX + (var4.posX - this.mc.getRenderManager().renderPosX), var4.boundingBox.minY - var4.posY + (var4.posY - this.mc.getRenderManager().renderPosY), var4.boundingBox.minZ - var4.posZ + (var4.posZ - this.mc.getRenderManager().renderPosZ), var4.boundingBox.maxX - var4.posX + (var4.posX - this.mc.getRenderManager().renderPosX), var4.boundingBox.maxY - var4.posY + (var4.posY - this.mc.getRenderManager().renderPosY), var4.boundingBox.maxZ - var4.posZ + (var4.posZ - this.mc.getRenderManager().renderPosZ));
               if (var4 != this.mc.thePlayer && var4 instanceof EntityPlayer) {
                  float var6 = (float)(System.currentTimeMillis() % 4500L) / 4500.0F;
                  int var7 = Color.HSBtoRGB(var6, 1.0F, 1.0F);
                  Color var8 = new Color(var7);
                  float var9 = (float)var8.getRed();
                  float var10 = (float)var8.getGreen();
                  float var11 = (float)var8.getBlue();
                  float var12 = (float)var8.getAlpha();
                  RenderUtil.drawBoundingBox(var5, var9 / 255.0F, var10 / 255.0F, var11 / 255.0F, 0.75F);
               }
            }
         }
      }

   }

   public void onEnable() {
   }

   public ESP() {
      super("ESP", 0, Module.Category.RENDER);
   }
}
