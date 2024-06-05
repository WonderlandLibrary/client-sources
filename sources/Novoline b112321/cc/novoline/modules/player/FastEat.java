package cc.novoline.modules.player;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.exploits.Disabler;
import cc.novoline.modules.player.Freecam;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer$C06PacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

public class FastEat extends AbstractModule {
   public FastEat(@NotNull ModuleManager var1) {
      super(var1, EnumModuleType.PLAYER, "FastEat", "Fast Eat");
   }

   @EventTarget
   public void a(MotionUpdateEvent var1) {
      int[] var2 = Freecam.a();
      if(var1.getState() == EventState.PRE) {
         double var3 = this.mc.player.posX;
         double var5 = this.mc.player.posY;
         double var7 = this.mc.player.posZ;
         float var9 = this.mc.player.rotationYaw;
         float var10 = this.mc.player.rotationPitch;
         boolean var11 = this.mc.player.onGround;
         if(this.mc.player.isUsingItem() && (this.mc.player.getHeldItem().getItem() instanceof ItemPotion || this.mc.player.getHeldItem().getItem() instanceof ItemFood)) {
            if(this.isEnabled(Disabler.class) && ((Disabler)this.getModule(Disabler.class)).b().equals("Verus")) {
               this.sendPacketNoEvent(new C03PacketPlayer$C04PacketPlayerPosition(var3, var5, var7, var11));
               this.sendPacketNoEvent(new C03PacketPlayer$C06PacketPlayerPosLook(var3, var5, var7, var9, var10, var11));
               this.sendPacketNoEvent(new C03PacketPlayer$C04PacketPlayerPosition(var3, var5, var7, var11));
               this.sendPacketNoEvent(new C03PacketPlayer$C06PacketPlayerPosLook(var3, var5, var7, var9, var10, var11));
            }

            this.sendPacketNoEvent(new C03PacketPlayer(var11));
            this.sendPacketNoEvent(new C03PacketPlayer(var11));
            this.sendPacketNoEvent(new C03PacketPlayer(var11));
            this.sendPacketNoEvent(new C03PacketPlayer(var11));
         }
      }

   }

   @EventTarget
   public void a(TickUpdateEvent var1) {
      int[] var2 = Freecam.a();
      this.setSuffix(this.isEnabled(Disabler.class) && ((Disabler)this.getModule(Disabler.class)).b().equals("Verus")?"Verus":"NCP");
   }

   public void onEnable() {
      int[] var1 = Freecam.a();
      this.setSuffix(this.isEnabled(Disabler.class) && ((Disabler)this.getModule(Disabler.class)).b().equals("Verus")?"Verus":"NCP");
   }
}
