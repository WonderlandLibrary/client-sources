package rina.turok.bope.bopemod.hacks.combat;

import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.network.play.client.CPacketUseEntity.Action;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.events.BopeEventPacket;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeCriticals extends BopeModule {
   BopeSetting event_mode = this.create("Event Mode", "CriticalsEventMode", "Packet", this.combobox(new String[]{"Packet", "Jump"}));
   @EventHandler
   private Listener<BopeEventPacket.SendPacket> listener = new Listener<>(event -> {
      if (event.get_packet() instanceof CPacketUseEntity) {
         CPacketUseEntity packet = (CPacketUseEntity)event.get_packet();
         if (packet.getAction() == Action.ATTACK && this.mc.player.onGround && !this.mc.gameSettings.keyBindJump.isKeyDown() && packet.getEntityFromWorld(this.mc.world) instanceof EntityLivingBase) {
            if (this.event_mode.in("Packet")) {
               this.mc.player.connection.sendPacket(new Position(this.mc.player.posX, this.mc.player.posY + 0.10000000149011612D, this.mc.player.posZ, false));
               this.mc.player.connection.sendPacket(new Position(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ, false));
            } else if (this.event_mode.in("Jump")) {
               this.mc.player.jump();
            }
         }
      }

   });

   public BopeCriticals() {
      super(BopeCategory.BOPE_COMBAT);
      this.name = "Criticals";
      this.tag = "Criticals";
      this.description = "You can hit with criticals when attack.";
      this.release("B.O.P.E - Module - B.O.P.E");
   }

   public String detail_option() {
      return this.event_mode.get_current_value();
   }
}
