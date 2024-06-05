package cc.novoline.modules.combat;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.combat.KillAura;
import cc.novoline.modules.exploits.Blink;
import cc.novoline.modules.player.Freecam;
import cc.novoline.utils.ServerUtils;
import cc.novoline.utils.Servers;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S18PacketEntityTeleport;

public final class AntiBot extends AbstractModule {
   public AntiBot(ModuleManager var1) {
      super(var1, "AntiBot", "Anti Bot", EnumModuleType.COMBAT, "Removes server-sided anti-cheat bots to prevent you from getting banned");
   }

   @EventTarget
   public void onPacket(PacketEvent var1) {
      int[] var2 = KillAura.Q();
      if((!ServerUtils.isHypixel() || !ServerUtils.serverIs(Servers.LOBBY)) && var1.getDirection().equals(PacketDirection.INCOMING)) {
         if(var1.getPacket() instanceof S18PacketEntityTeleport) {
            S18PacketEntityTeleport var3 = (S18PacketEntityTeleport)var1.getPacket();
            EntityPlayer var4 = (EntityPlayer)this.mc.world.removeEntityFromWorld(var3.getEntityId());
            if(var4 instanceof EntityPlayer && var4.isInvisible() && var4.ticksExisted > 3 && this.mc.world.getPlayerEntities().contains(var4) && !this.isInTabList(var4)) {
               this.mc.world.removeEntity(var4);
            }
         }

         if(var1.getPacket() instanceof S0CPacketSpawnPlayer) {
            S0CPacketSpawnPlayer var19 = (S0CPacketSpawnPlayer)var1.getPacket();
            EntityPlayer var20 = (EntityPlayer)this.mc.world.removeEntityFromWorld(var19.getEntityID());
            double var5 = (double)var19.getX() / 32.0D;
            double var7 = (double)var19.getY() / 32.0D;
            double var9 = (double)var19.getZ() / 32.0D;
            double var11 = this.mc.player.posX - var5;
            double var13 = this.mc.player.posY - var7;
            double var15 = this.mc.player.posZ - var9;
            double var17 = Math.sqrt(var11 * var11 + var13 * var13 + var15 * var15);
            if(this.mc.world.getPlayerEntities().contains(var20) && var17 <= 17.0D && !var20.equals(this.mc.player) && var5 != this.mc.player.posX && var7 != this.mc.player.posY && var9 != this.mc.player.posZ) {
               this.mc.world.removeEntity(var20);
            }
         }
      }

   }

   private boolean isInTabList(EntityPlayer var1) {
      return this.mc.ingameGUI.a().c().contains(NetHandlerPlayClient.getPlayerInfo(var1.getGameProfile().getId()));
   }

   private List entityPlayersList() {
      return (List)this.mc.world.getPlayerEntities().stream().filter(this::lambda$entityPlayersList$0).collect(Collectors.toList());
   }

   @EventTarget
   public void onTick(TickUpdateEvent var1) {
      this.setSuffix(ServerUtils.isHypixel()?"Hypixel":"Packet");
   }

   public void onEnable() {
      this.setSuffix(ServerUtils.isHypixel()?"Hypixel":"Packet");
   }

   private boolean lambda$entityPlayersList$0(EntityPlayer var1) {
      int[] var2 = KillAura.Q();
      return !var1.equals(this.mc.player) && !var1.equals(((Blink)this.getModule(Blink.class)).getBlinkEntity()) && !var1.equals(((Freecam)this.getModule(Freecam.class)).getFreecamEntity());
   }
}
