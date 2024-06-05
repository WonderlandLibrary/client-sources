package net;

import cc.novoline.events.events.PacketDirection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.UW;
import net.WB;
import net.WL;
import net.a2V;
import net.agu;
import net.ap9;
import net.as0;
import net.as_;
import net.asx;
import net.avq;
import net.lS;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S18PacketEntityTeleport;

public final class asR extends as0 {
   public asR(UW var1) {
      super(var1, "AntiBot", "Anti Bot", a2V.COMBAT, "Removes server-sided anti-cheat bots to prevent you from getting banned");
   }

   @agu
   public void b(ap9 var1) {
      int[] var2 = asx.Q();
      if((!lS.c() || !lS.a(WL.LOBBY)) && var1.a().equals(PacketDirection.INCOMING)) {
         if(var1.d() instanceof S18PacketEntityTeleport) {
            S18PacketEntityTeleport var3 = (S18PacketEntityTeleport)var1.d();
            EntityPlayer var4 = (EntityPlayer)this.w.theWorld.removeEntityFromWorld(var3.getEntityId());
            if(var4 instanceof EntityPlayer && var4.isInvisible() && var4.ticksExisted > 3 && this.w.theWorld.K().contains(var4) && !this.a(var4)) {
               this.w.theWorld.removeEntity(var4);
            }
         }

         if(var1.d() instanceof S0CPacketSpawnPlayer) {
            S0CPacketSpawnPlayer var19 = (S0CPacketSpawnPlayer)var1.d();
            EntityPlayer var20 = (EntityPlayer)this.w.theWorld.removeEntityFromWorld(var19.getEntityID());
            double var5 = (double)var19.getX() / 32.0D;
            double var7 = (double)var19.getY() / 32.0D;
            double var9 = (double)var19.getZ() / 32.0D;
            double var11 = this.w.thePlayer.posX - var5;
            double var13 = this.w.thePlayer.posY - var7;
            double var15 = this.w.thePlayer.posZ - var9;
            double var17 = Math.sqrt(var11 * var11 + var13 * var13 + var15 * var15);
            if(this.w.theWorld.K().contains(var20) && var17 <= 17.0D && !var20.equals(this.w.thePlayer) && var5 != this.w.thePlayer.posX && var7 != this.w.thePlayer.posY && var9 != this.w.thePlayer.posZ) {
               this.w.theWorld.removeEntity(var20);
            }
         }
      }

   }

   private boolean a(EntityPlayer var1) {
      return this.w.ingameGUI.getTabList().c().contains(NetHandlerPlayClient.a(var1.getGameProfile().getId()));
   }

   private List a() {
      return (List)this.w.theWorld.K().stream().filter(this::lambda$entityPlayersList$0).collect(Collectors.toList());
   }

   @agu
   public void a(WB var1) {
      this.c(lS.c()?"Hypixel":"Packet");
   }

   public void n() {
      this.c(lS.c()?"Hypixel":"Packet");
   }

   private boolean lambda$entityPlayersList$0(EntityPlayer var1) {
      int[] var2 = asx.Q();
      return !var1.equals(this.w.thePlayer) && !var1.equals(((as_)this.b(as_.class)).c()) && !var1.equals(((avq)this.b(avq.class)).b());
   }
}
