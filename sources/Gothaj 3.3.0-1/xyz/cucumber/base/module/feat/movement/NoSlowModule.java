package xyz.cucumber.base.module.feat.movement;

import god.buddy.aot.BCompiler;
import java.util.ArrayList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventNoSlow;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.combat.KillAuraModule;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.utils.MovementUtils;
import xyz.cucumber.base.utils.Timer;

@ModuleInfo(
   category = Category.MOVEMENT,
   description = "Allows you to use items while running",
   name = "No Slow",
   key = 0
)
@BCompiler(
   aot = BCompiler.AOT.AGGRESSIVE
)
public class NoSlowModule extends Mod {
   public ArrayList<Packet> packets = new ArrayList<>();
   public ModeSettings mode = new ModeSettings("Mode", new String[]{"Vanilla", "Hypixel", "Intave new", "Intave spoof", "AAC5", "AAC4", "Switch"});
   public boolean attack;
   public boolean swing;
   public Timer timer = new Timer();

   public NoSlowModule() {
      this.addSettings(new ModuleSettings[]{this.mode});
   }

   @EventListener
   public void onNoSlow(EventNoSlow e) {
      e.setCancelled(true);
   }

   @EventListener
   public void onMotion(EventMotion e) {
      this.setInfo(this.mode.getMode());
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -889473228:
            if (var2.equals("switch")
               && MovementUtils.isMoving()
               && (this.mc.thePlayer.isUsingItem() || this.mc.thePlayer.isEating() || this.mc.thePlayer.isBlocking())
               && e.getType() == EventType.PRE) {
               this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C09PacketHeldItemChange((this.mc.thePlayer.inventory.currentItem + 1) % 9));
               this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
            }

            return;
         case -741919359:
            if (var2.equals("intave new") && e.getType() == EventType.PRE) {
               this.mc
                  .getNetHandler()
                  .getNetworkManager()
                  .sendPacketNoEvent(
                     new C07PacketPlayerDigging(
                        C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
                        new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.getPositionEyes(1.0F).yCoord, this.mc.thePlayer.posZ),
                        EnumFacing.DOWN
                     )
                  );
            }

            return;
         case -14991702:
            if (!var2.equals("intave spoof")) {
               return;
            }
            break;
         case 2986065:
            if (!var2.equals("aac4")) {
               return;
            }

            if (e.getType() == EventType.PRE
               && MovementUtils.isMoving()
               && (this.mc.thePlayer.isUsingItem() || this.mc.thePlayer.isEating() || this.mc.thePlayer.isBlocking())) {
               this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
            }
            break;
         case 2986066:
            if (var2.equals("aac5")
               && e.getType() == EventType.POST
               && (
                  MovementUtils.isMoving() && this.mc.thePlayer.isUsingItem()
                     || this.mc.gameSettings.keyBindUseItem.pressed
                     || this.mc.thePlayer.isEating()
                     || this.mc.thePlayer.isBlocking()
               )) {
               this.mc
                  .getNetHandler()
                  .getNetworkManager()
                  .sendPacketNoEvent(
                     new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, this.mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F)
                  );
            }

            return;
         case 233102203:
            if (!var2.equals("vanilla")) {
            }

            return;
         case 1381910549:
            if (var2.equals("hypixel")
               && this.mc.thePlayer.onGround
               && (this.mc.thePlayer.isUsingItem() || this.mc.thePlayer.isEating() || this.mc.thePlayer.isBlocking())) {
               this.mc.thePlayer.motionY += 1.0E-7;
            }

            return;
         default:
            return;
      }

      if (e.getType() == EventType.PRE
         && MovementUtils.isMoving()
         && (this.mc.thePlayer.isUsingItem() || this.mc.thePlayer.isEating() || this.mc.thePlayer.isBlocking())) {
         KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
         this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
         this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0DPacketCloseWindow(this.mc.thePlayer.openContainer.windowId));
      }
   }

   @EventListener
   public void onSendPacket(EventSendPacket e) {
      this.mode.getMode().toLowerCase().hashCode();
   }
}
