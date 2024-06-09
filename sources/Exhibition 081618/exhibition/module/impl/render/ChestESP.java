package exhibition.module.impl.render;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventRender3D;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.RenderingUtil;
import exhibition.util.render.GLUtil;
import java.util.Iterator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class ChestESP extends Module {
   public ChestESP(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventRender3D.class}
   )
   public void onEvent(Event event) {
      EventRender3D e = (EventRender3D)event;
      Iterator var3 = mc.theWorld.loadedTileEntityList.iterator();

      while(var3.hasNext()) {
         Object o = var3.next();
         if (o instanceof TileEntityChest) {
            TileEntityLockable storage = (TileEntityLockable)o;
            this.drawESPOnStorage(storage, (double)storage.getPos().getX(), (double)storage.getPos().getY(), (double)storage.getPos().getZ());
         }
      }

   }

   public void drawESPOnStorage(TileEntityLockable storage, double x, double y, double z) {
      if (!storage.isLocked()) {
         TileEntityChest chest = (TileEntityChest)storage;
         Vec3 vec;
         Vec3 vec2;
         if (chest.adjacentChestZNeg != null) {
            vec = new Vec3(x + 0.0625D, y, z - 0.9375D);
            vec2 = new Vec3(x + 0.9375D, y + 0.875D, z + 0.9375D);
         } else if (chest.adjacentChestXNeg != null) {
            vec = new Vec3(x + 0.9375D, y, z + 0.0625D);
            vec2 = new Vec3(x - 0.9375D, y + 0.875D, z + 0.9375D);
         } else {
            if (chest.adjacentChestXPos != null || chest.adjacentChestZPos != null) {
               return;
            }

            vec = new Vec3(x + 0.0625D, y, z + 0.0625D);
            vec2 = new Vec3(x + 0.9375D, y + 0.875D, z + 0.9375D);
         }

         GL11.glPushMatrix();
         RenderingUtil.pre3D();
         mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);
         if (((TileEntityChest)storage).getChestType() != 0) {
            GL11.glColor4d(0.9D, 0.3D, 0.3D, 0.3D);
         } else if (chest.isEmpty) {
            GL11.glColor4d(0.3D, 0.3D, 0.3D, 0.3D);
         } else if (!chest.isEmpty) {
            GL11.glColor4d(0.35D, 0.5D, 0.9D, 0.3D);
         }

         RenderingUtil.drawBoundingBox(new AxisAlignedBB(vec.xCoord - RenderManager.renderPosX, vec.yCoord - RenderManager.renderPosY, vec.zCoord - RenderManager.renderPosZ, vec2.xCoord - RenderManager.renderPosX, vec2.yCoord - RenderManager.renderPosY, vec2.zCoord - RenderManager.renderPosZ));
         GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
         RenderingUtil.post3D();
         GL11.glPopMatrix();
         if (!GL11.glIsEnabled(2896)) {
            GL11.glEnable(2896);
         }

      }
   }

   public void drawESP(double x, double y, double z, double r, double g, double b) {
      GL11.glPushMatrix();
      GLUtil.setGLCap(3042, true);
      GL11.glBlendFunc(770, 771);
      GLUtil.setGLCap(2896, false);
      GLUtil.setGLCap(3553, false);
      GLUtil.setGLCap(2848, true);
      GLUtil.setGLCap(2929, false);
      GL11.glDepthMask(false);
      AxisAlignedBB boundingBox = new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D);
      GL11.glColor4d(r, g, b, 0.14000000059604645D);
      RenderingUtil.drawBoundingBox(boundingBox.contract(0.025D, 0.025D, 0.025D));
      GL11.glColor4d(r, g, b, 0.33000001311302185D);
      GL11.glLineWidth(1.0F);
      RenderingUtil.drawOutlinedBoundingBox(boundingBox);
      GL11.glLineWidth(1.4F);
      RenderingUtil.drawLines(boundingBox);
      GL11.glLineWidth(2.0F);
      GLUtil.revertAllCaps();
      GL11.glDepthMask(true);
      GL11.glPopMatrix();
   }
}
