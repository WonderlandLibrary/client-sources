package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.Render3DEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.ColorProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.RotationUtil;
import java.awt.Color;
import java.util.EnumSet;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition$MovingObjectType;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

public class BlockOutline extends AbstractModule {
   @Property("color")
   private final ColorProperty y = PropertyFactory.createColor(Integer.valueOf((new Color(152, 217, 0)).getRGB()));
   @Property("vector")
   private final BooleanProperty x = PropertyFactory.booleanFalse();

   public BlockOutline(@NotNull ModuleManager var1) {
      super(var1, "BlocksOutline", "Blocks Outline", EnumModuleType.VISUALS, "Outlines the block you\'re looking at");
      Manager.put(new Setting("BO_COLOR", "Outline Color", SettingType.COLOR_PICKER, this, this.y, (EnumSet)null));
      Manager.put(new Setting("BO_VECTOR", "Infinite", SettingType.CHECKBOX, this, this.x));
   }

   public MovingObjectPosition a() {
      HUD.h();
      float var2 = this.mc.player.getCollisionBorderSize();
      float var3 = ((Boolean)this.x.get()).booleanValue()?250.0F:this.mc.at.i();
      Vec3 var4 = RotationUtil.getPositionEyes(1.0F);
      Vec3 var5 = RotationUtil.getVectorForRotation(this.mc.player.rotationPitch, this.mc.player.rotationYaw);
      Vec3 var6 = var4.addVector(var5.xCoord * (double)var3, var5.yCoord * (double)var3, var5.zCoord * (double)var3);
      return this.mc.world.rayTraceBlocks(var4, var6, false, false, false);
   }

   @EventTarget
   public void onRender3D(Render3DEvent var1) {
      int var2 = HUD.h();
      if(this.a() != null && this.a() != null && this.a().typeOfHit == MovingObjectPosition$MovingObjectType.BLOCK) {
         BlockPos var3 = this.a().getBlockPos();
         RenderUtils.pre3D();
         RenderUtils.setColor(this.y.getAwtColor());
         double var4 = (double)var3.getX() - this.mc.getRenderManager().renderPosX;
         double var6 = (double)var3.getY() - this.mc.getRenderManager().renderPosY;
         double var8 = (double)var3.getZ() - this.mc.getRenderManager().renderPosZ;
         double var10 = this.mc.world.getBlockState(var3).getBlock().getBlockBoundsMaxY() - this.mc.world.getBlockState(var3).getBlock().getBlockBoundsMinY();
         GL11.glLineWidth(1.0F);
         GL11.glBegin(3);
         GL11.glVertex3d(var4, var6, var8);
         GL11.glVertex3d(var4, var6 + var10, var8);
         GL11.glEnd();
         GL11.glBegin(3);
         GL11.glVertex3d(var4 + 1.0D, var6, var8);
         GL11.glVertex3d(var4 + 1.0D, var6 + var10, var8);
         GL11.glEnd();
         GL11.glBegin(3);
         GL11.glVertex3d(var4 + 1.0D, var6, var8 + 1.0D);
         GL11.glVertex3d(var4 + 1.0D, var6 + var10, var8 + 1.0D);
         GL11.glEnd();
         GL11.glBegin(3);
         GL11.glVertex3d(var4, var6, var8 + 1.0D);
         GL11.glVertex3d(var4, var6 + var10, var8 + 1.0D);
         GL11.glEnd();
         GL11.glBegin(3);
         GL11.glVertex3d(var4, var6, var8);
         GL11.glVertex3d(var4 + 1.0D, var6, var8);
         GL11.glEnd();
         GL11.glBegin(3);
         GL11.glVertex3d(var4, var6 + var10, var8);
         GL11.glVertex3d(var4 + 1.0D, var6 + var10, var8);
         GL11.glEnd();
         GL11.glBegin(3);
         GL11.glVertex3d(var4, var6, var8);
         GL11.glVertex3d(var4, var6, var8 + 1.0D);
         GL11.glEnd();
         GL11.glBegin(3);
         GL11.glVertex3d(var4, var6 + var10, var8);
         GL11.glVertex3d(var4, var6 + var10, var8 + 1.0D);
         GL11.glEnd();
         GL11.glBegin(3);
         GL11.glVertex3d(var4 + 1.0D, var6, var8 + 1.0D);
         GL11.glVertex3d(var4 + 1.0D, var6, var8 + 1.0D);
         GL11.glEnd();
         GL11.glBegin(3);
         GL11.glVertex3d(var4 + 1.0D, var6 + var10, var8 + 1.0D);
         GL11.glVertex3d(var4 + 1.0D, var6 + var10, var8 + 1.0D);
         GL11.glEnd();
         GL11.glBegin(3);
         GL11.glVertex3d(var4 + 1.0D, var6, var8 + 1.0D);
         GL11.glVertex3d(var4 + 1.0D, var6, var8);
         GL11.glEnd();
         GL11.glBegin(3);
         GL11.glVertex3d(var4 + 1.0D, var6 + var10, var8 + 1.0D);
         GL11.glVertex3d(var4 + 1.0D, var6 + var10, var8);
         GL11.glEnd();
         GL11.glBegin(3);
         GL11.glVertex3d(var4, var6, var8 + 1.0D);
         GL11.glVertex3d(var4 + 1.0D, var6, var8 + 1.0D);
         GL11.glEnd();
         GL11.glBegin(3);
         GL11.glVertex3d(var4, var6 + var10, var8 + 1.0D);
         GL11.glVertex3d(var4 + 1.0D, var6 + var10, var8 + 1.0D);
         GL11.glEnd();
         RenderUtils.post3D();
      }

   }
}
