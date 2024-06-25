package cc.slack.features.modules.impl.player;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.player.BlinkUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.Vec3;

@ModuleInfo(
   name = "Antivoid",
   category = Category.PLAYER
)
public class AntiVoid extends Module {
   private final ModeValue<String> antivoidMode = new ModeValue(new String[]{"Universal", "Self TP", "Polar"});
   private double groundX = 0.0D;
   private double groundY = 0.0D;
   private double groundZ = 0.0D;
   private boolean universalStarted = false;
   private boolean universalFlag = false;
   private boolean triedTP = false;

   public AntiVoid() {
      this.addSettings(new Value[]{this.antivoidMode});
   }

   public void onEnable() {
      this.universalStarted = false;
   }

   public void onDisable() {
      if (this.antivoidMode.getValue() == "Universal") {
         BlinkUtil.disable();
      }

   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      String var2 = ((String)this.antivoidMode.getValue()).toLowerCase();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -409534901:
         if (var2.equals("universal")) {
            var3 = 0;
         }
         break;
      case 1978065872:
         if (var2.equals("self tp")) {
            var3 = 1;
         }
      }

      switch(var3) {
      case 0:
         if (this.universalStarted) {
            if (mc.getPlayer().onGround) {
               BlinkUtil.disable();
               this.universalStarted = false;
               this.universalFlag = false;
            } else if (mc.getPlayer().fallDistance > 8.0F && !this.universalFlag) {
               this.universalFlag = true;
               mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.groundX, this.groundY + 1.0D, this.groundZ, false));
            }
         } else if (mc.getPlayer().fallDistance > 0.0F && !mc.getPlayer().onGround && mc.getPlayer().motionY < 0.0D && this.isOverVoid()) {
            this.universalStarted = true;
            this.universalFlag = false;
            BlinkUtil.enable(false, true);
            this.groundX = mc.getPlayer().posX;
            this.groundY = mc.getPlayer().posY;
            this.groundZ = mc.getPlayer().posZ;
         }
         break;
      case 1:
         if (mc.getPlayer().onGround) {
            this.groundX = mc.getPlayer().posX;
            this.groundY = mc.getPlayer().posY;
            this.groundZ = mc.getPlayer().posZ;
            this.triedTP = false;
         } else if (mc.getPlayer().fallDistance > 5.0F && !this.triedTP) {
            mc.getPlayer().setPosition(this.groundX, this.groundY, this.groundZ);
            mc.getPlayer().fallDistance = 0.0F;
            mc.getPlayer().motionX = 0.0D;
            mc.getPlayer().motionY = 0.0D;
            mc.getPlayer().motionZ = 0.0D;
            this.triedTP = true;
         }
      }

   }

   @Listen
   public void onPacket(PacketEvent event) {
      String var2 = ((String)this.antivoidMode.getValue()).toLowerCase();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -409534901:
         if (var2.equals("universal")) {
            var3 = 0;
         }
      default:
         switch(var3) {
         case 0:
            if (event.getPacket() instanceof S08PacketPlayerPosLook && ((S08PacketPlayerPosLook)event.getPacket()).getX() == this.groundX && ((S08PacketPlayerPosLook)event.getPacket()).getY() == this.groundY && ((S08PacketPlayerPosLook)event.getPacket()).getZ() == this.groundZ) {
               BlinkUtil.disable(false);
               mc.getPlayer().setPosition(this.groundX, this.groundY, this.groundZ);
               this.universalFlag = false;
               this.universalStarted = false;
            }
         default:
         }
      }
   }

   private boolean isOverVoid() {
      return mc.getWorld().rayTraceBlocks(new Vec3(mc.getPlayer().posX, mc.getPlayer().posY, mc.getPlayer().posZ), new Vec3(mc.getPlayer().posX, mc.getPlayer().posY - 40.0D, mc.getPlayer().posZ), true, true, false) == null;
   }
}
