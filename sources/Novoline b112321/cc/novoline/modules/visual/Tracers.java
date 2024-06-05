package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.Render3DEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.PlayerManager$EnumPlayerType;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.RenderUtils;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.entity.player.EntityPlayer;

public class Tracers extends AbstractModule {
   @Property("only-tar")
   BooleanProperty only_tar = PropertyFactory.booleanFalse();

   public Tracers(ModuleManager var1) {
      super(var1, "Tracers", EnumModuleType.VISUALS);
      Manager.put(new Setting("TC_ONLY_TAR", "Targets Only", SettingType.CHECKBOX, this, this.only_tar));
   }

   @EventTarget
   public void on3DRender(Render3DEvent var1) {
      HUD.e();
      Iterator var3 = ((List)this.mc.world.getPlayerEntities().stream().filter(this::lambda$on3DRender$0).collect(Collectors.toList())).iterator();
      if(var3.hasNext()) {
         EntityPlayer var4 = (EntityPlayer)var3.next();
         if(var4.isEntityAlive() && var4 != this.mc.player && !var4.isInvisible()) {
            double var5 = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * (double)var1.getPartialTicks() - this.mc.getRenderManager().renderPosX;
            double var7 = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * (double)var1.getPartialTicks() - this.mc.getRenderManager().renderPosY;
            double var9 = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * (double)var1.getPartialTicks() - this.mc.getRenderManager().renderPosZ;
            boolean var11 = this.mc.gameSettings.viewBobbing;
            RenderUtils.startDrawing();
            this.mc.gameSettings.viewBobbing = false;
            this.mc.entityRenderer.setupCameraTransform(this.mc.timer.renderPartialTicks, 2);
            this.mc.gameSettings.viewBobbing = var11;
            boolean var12 = this.novoline.getPlayerManager().hasType(var4.getName(), PlayerManager$EnumPlayerType.TARGET);
            double[] var13 = var12?new double[]{1.0D, 0.0D, 0.0D}:new double[]{1.0D, 1.0D, 1.0D};
            RenderUtils.drawLine(var4, var13, var5, var7 + (double)var4.getEyeHeight(), var9);
            RenderUtils.stopDrawing();
         }
      }

   }

   private boolean lambda$on3DRender$0(EntityPlayer var1) {
      int var2 = HUD.e();
      return !((Boolean)this.only_tar.get()).booleanValue() || this.novoline.getPlayerManager().hasType(var1.getName(), PlayerManager$EnumPlayerType.TARGET);
   }
}
