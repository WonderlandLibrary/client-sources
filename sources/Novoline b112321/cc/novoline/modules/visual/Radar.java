package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.Render2DEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.PlayerManager$EnumPlayerType;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.DoubleProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.PlayerUtils;
import cc.novoline.utils.RotationUtil;
import cc.novoline.utils.ScaleUtils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import net.J3;
import net.aHM;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public final class Radar extends AbstractModule {
   private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
   @Property("scale")
   private final DoubleProperty scale = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(0.85D)).minimum(Double.valueOf(0.25D))).maximum(Double.valueOf(5.0D));
   @Property("x")
   private final IntProperty x;
   @Property("y")
   private final IntProperty y;
   @Property("size")
   private final IntProperty size;

   public Radar(ModuleManager var1) {
      super(var1, "Radar", EnumModuleType.VISUALS, "Useful for games like uhc/sg");
      this.x = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(1)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf((int)(this.screenSize.getWidth() / 2.0D)));
      this.y = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(152)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf((int)(this.screenSize.getHeight() / 2.0D)));
      this.size = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(120)).minimum(Integer.valueOf(50))).maximum(Integer.valueOf(200));
      Manager.put(new Setting("RADAR_SCALE", "Scale", SettingType.SLIDER, this, this.scale, 0.05D));
      Manager.put(new Setting("RADAR_X_POS", "X", SettingType.SLIDER, this, this.x, 5.0D));
      Manager.put(new Setting("RADAR_Y_POS", "Y", SettingType.SLIDER, this, this.y, 5.0D));
      Manager.put(new Setting("RADAR_SIZE", "Size", SettingType.SLIDER, this, this.size, 5.0D));
   }

   @EventTarget
   public void radar(Render2DEvent var1) {
      int var2 = HUD.h();
      if(!(this.mc.currentScreen instanceof aHM)) {
         this.renderRadar();
      }

   }

   public void renderRadar() {
      GL11.glPushMatrix();
      ScaleUtils.scale(this.mc);
      HUD.e();
      Gui.drawRect(((Integer)this.x.get()).intValue() - 1, ((Integer)this.y.get()).intValue() - 1, ((Integer)this.x.get()).intValue() + ((Integer)this.size.get()).intValue() + 1, ((Integer)this.y.get()).intValue() + ((Integer)this.size.get()).intValue() + 1, (new Color(29, 29, 29, 255)).getRGB());
      Gui.drawRect(((Integer)this.x.get()).intValue(), ((Integer)this.y.get()).intValue(), ((Integer)this.x.get()).intValue() + ((Integer)this.size.get()).intValue(), ((Integer)this.y.get()).intValue() + ((Integer)this.size.get()).intValue(), (new Color(40, 40, 40, 255)).getRGB());
      Gui.drawRect((double)(((Integer)this.x.get()).intValue() + ((Integer)this.size.get()).intValue() / 2) - 0.3D + 1.0D, (double)((Integer)this.y.get()).intValue(), (double)(((Integer)this.x.get()).intValue() + ((Integer)this.size.get()).intValue() / 2) + 0.3D + 1.0D, (double)(((Integer)this.y.get()).intValue() + ((Integer)this.size.get()).intValue()), (new Color(255, 255, 255, 50)).getRGB());
      Gui.drawRect((double)((Integer)this.x.get()).intValue(), (double)(((Integer)this.y.get()).intValue() + ((Integer)this.size.get()).intValue() / 2) - 0.3D, (double)(((Integer)this.x.get()).intValue() + ((Integer)this.size.get()).intValue()), (double)(((Integer)this.y.get()).intValue() + ((Integer)this.size.get()).intValue() / 2) + 0.3D, (new Color(255, 255, 255, 50)).getRGB());

      for(Entity var3 : this.mc.world.getLoadedEntityList()) {
         if(var3 != this.mc.player) {
            if(var3 instanceof EntityPlayer && !var3.isInvisible()) {
               float var4 = RotationUtil.getYawToPoint(var3.posX, var3.posZ);
               float var5 = -(var4 - this.mc.player.rotationYaw + 180.0F);
               double var6 = Math.toRadians((double)var5);
               double var8 = Math.abs(this.mc.player.posX - var3.posX);
               double var10 = Math.abs(this.mc.player.posZ - var3.posZ);
               double var12 = Math.sqrt(var8 * var8 + var10 * var10) / ((Double)this.scale.get()).doubleValue();
               int var14 = -1;
               if(this.novoline.playerManager.hasType(var3.getName(), PlayerManager$EnumPlayerType.TARGET)) {
                  var14 = this.b.getRGB();
               }

               if(this.novoline.playerManager.hasType(var3.getName(), PlayerManager$EnumPlayerType.FRIEND)) {
                  var14 = this.c.getRGB();
               }

               var14 = PlayerUtils.c(var3).getRGB();
               if(Math.sin(var6) > 0.0D && Math.cos(var6) < 0.0D && Math.sin(var6) * var12 < (double)(((Integer)this.size.get()).intValue() / 2) && -Math.cos(var6) * var12 < (double)(((Integer)this.size.get()).intValue() / 2) || Math.sin(var6) < 0.0D && Math.cos(var6) < 0.0D && -Math.sin(var6) * var12 < (double)(((Integer)this.size.get()).intValue() / 2) && -Math.cos(var6) * var12 < (double)(((Integer)this.size.get()).intValue() / 2) || Math.sin(var6) > 0.0D && Math.cos(var6) > 0.0D && Math.sin(var6) * var12 < (double)(((Integer)this.size.get()).intValue() / 2) && Math.cos(var6) * var12 < (double)(((Integer)this.size.get()).intValue() / 2) || Math.sin(var6) < 0.0D && Math.cos(var6) > 0.0D && -Math.sin(var6) * var12 < (double)(((Integer)this.size.get()).intValue() / 2) && Math.cos(var6) * var12 < (double)(((Integer)this.size.get()).intValue() / 2)) {
                  J3.a((double)(((Integer)this.x.get()).intValue() + ((Integer)this.size.get()).intValue() / 2) + Math.sin(var6) * var12 - 1.0D, (double)(((Integer)this.y.get()).intValue() + ((Integer)this.size.get()).intValue() / 2) + Math.cos(var6) * var12 - 1.0D, (double)(((Integer)this.x.get()).intValue() + ((Integer)this.size.get()).intValue() / 2) + Math.sin(var6) * var12 + 1.0D, (double)(((Integer)this.y.get()).intValue() + ((Integer)this.size.get()).intValue() / 2) + Math.cos(var6) * var12 + 1.0D, 0.1F, var14, var14);
               }
            }
            break;
         }
      }

      GL11.glPopMatrix();
   }

   public IntProperty getSize() {
      return this.size;
   }

   public IntProperty getX() {
      return this.x;
   }

   public IntProperty getY() {
      return this.y;
   }
}
