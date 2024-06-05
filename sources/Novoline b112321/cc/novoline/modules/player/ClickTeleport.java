package cc.novoline.modules.player;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.player.Freecam;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Mouse;

public class ClickTeleport extends AbstractModule {
   @Property("mouse-button")
   private final StringProperty mouse_button = PropertyFactory.createString("Side Front").acceptableValues(new String[]{"Left", "Right", "Middle", "Side Front", "Side Back"});
   private boolean down;

   public ClickTeleport(ModuleManager var1) {
      super(var1, EnumModuleType.PLAYER, "ClickTeleport", "Click Teleport");
      Manager.put(new Setting("CT_MOUSE_BUTTON", "Mouse", SettingType.COMBOBOX, this, this.mouse_button));
   }

   private int button() {
      int[] var1 = Freecam.a();
      return this.mouse_button.equals("Left")?0:(this.mouse_button.equals("Right")?1:(this.mouse_button.equals("Middle")?2:(this.mouse_button.equals("Side Back")?3:(this.mouse_button.equals("Side Front")?4:-1))));
   }

   @EventTarget
   private void onMotions(MotionUpdateEvent var1) {
      int[] var2 = Freecam.a();
      if(var1.getState().equals(EventState.PRE) && this.mc.currentScreen == null) {
         if(Mouse.isButtonDown(this.button())) {
            if(!this.down) {
               return;
            }

            if(this.rayTrace() != null) {
               int var3 = this.rayTrace().getBlockPos().getX();
               int var4 = this.rayTrace().getBlockPos().getY();
               int var5 = this.rayTrace().getBlockPos().getZ();
               this.mc.player.c(".tp " + var3 + " " + var4 + " " + var5);
            }

            this.down = false;
         }

         this.down = true;
      }

   }

   public MovingObjectPosition rayTrace() {
      Vec3 var1 = new Vec3(this.mc.player.posX, this.mc.player.posY + (double)this.mc.player.getEyeHeight(), this.mc.player.posZ);
      Vec3 var2 = this.getVectorForRotation(this.mc.player.rotationPitch, this.mc.player.rotationYaw);
      Vec3 var3 = var1.addVector(var2.xCoord * 500.0D, var2.yCoord * 500.0D, var2.zCoord * 500.0D);
      return this.mc.world.rayTraceBlocks(var1, var3, false, false, false);
   }

   protected final Vec3 getVectorForRotation(float var1, float var2) {
      float var3 = MathHelper.cos(Math.toRadians((double)(-var2)) - 3.1415927410125732D);
      float var4 = MathHelper.sin(Math.toRadians((double)(-var2)) - 3.1415927410125732D);
      float var5 = -MathHelper.cos(Math.toRadians((double)(-var1)));
      float var6 = MathHelper.sin(Math.toRadians((double)(-var1)));
      return new Vec3((double)(var4 * var5), (double)var6, (double)(var3 * var5));
   }
}
