package net;

import cc.novoline.events.events.PacketDirection;
import cc.novoline.modules.misc.WindowedFullscreen;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import net.BT;
import net.C9;
import net.UW;
import net.VN;
import net.WL;
import net.a2V;
import net.a6d;
import net.aEu;
import net.adZ;
import net.ae9;
import net.agu;
import net.ap9;
import net.as0;
import net.asx;
import net.axu;
import net.d3;
import net.lS;
import net.uO;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S38PacketPlayerListItem$Action;
import net.minecraft.network.play.server.S38PacketPlayerListItem$AddPlayerData;
import net.minecraft.util.MathHelper;

public final class av9 extends as0 {
   private List B = new CopyOnWriteArrayList();
   private List A = new CopyOnWriteArrayList();
   private final d3 y = new d3();
   private Path x;
   private int C;
   private boolean D;
   @VN("random-order")
   private final aEu z;

   public av9(UW var1) {
      super(var1, "Killsults", "Killsults", a2V.MISC, "Insults your opponents");
      this.x = Paths.get(this.j.x() + "Killsults.novo", new String[0]);
      this.z = axu.g();
      ae9.a(new adZ("KS_RANDOM", "Random Order", a6d.CHECKBOX, this, this.z));
   }

   public void c() {
      String[] var1 = WindowedFullscreen.a();
      if(!this.B.isEmpty()) {
         this.B.clear();
      }

      this.D = false;
      this.C = 0;
   }

   public void n() {
      this.b();
   }

   public void b() {
      String[] var1 = WindowedFullscreen.a();

      try {
         BufferedReader var2 = new BufferedReader(new FileReader(this.x.toString()));
         List var3 = (List)var2.lines().collect(Collectors.toList());
         if(!var3.isEmpty()) {
            Iterator var4 = var3.iterator();
            if(var4.hasNext()) {
               String var5 = (String)var4.next();
               if(!this.A.contains(var5)) {
                  this.A.add(var5);
               }
            }
         }
      } catch (FileNotFoundException var6) {
         var6.printStackTrace();
      }

   }

   private int d() {
      WindowedFullscreen.a();
      String var2 = this.w.thePlayer.getDisplayName().getFormattedText();
      return !var2.contains("VIP") && !var2.contains("MVP")?3100:1100;
   }

   public int e() {
      return (int)((float)this.d() - MathHelper.a((float)(this.y.d() - this.y.c()), 0.0F, (float)this.d()));
   }

   private void a(EntityPlayer var1) {
      String[] var2 = WindowedFullscreen.a();
      if(((Boolean)this.z.a()).booleanValue()) {
         this.C = ThreadLocalRandom.current().nextInt(0, this.A.size());
      }

      ++this.C;
      if(this.C >= this.A.size()) {
         this.C = 0;
      }

      String var3 = (String)this.A.get(this.C);
      String var4 = var3.replace("§", "").replace("%s", var1.getName());
      char[] var5 = var4.toCharArray();
      StringBuilder var6 = new StringBuilder();
      int var8 = var5.length;
      int var9 = 0;
      if(var9 < var8) {
         char var10 = var5[var9];
         var6.append(var10);
         int var11 = 0;
         if(var11 < ThreadLocalRandom.current().nextInt(0, 3)) {
            var6.append('\u05fc');
            ++var11;
         }

         ++var9;
      }

      this.a((Packet)(new C01PacketChatMessage(var6.toString())));
      this.B.remove(var1);
      this.y.b();
   }

   @agu
   public void b(ap9 var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.a().equals(PacketDirection.INCOMING)) {
         if(var1.d() instanceof S2FPacketSetSlot) {
            S2FPacketSetSlot var3 = (S2FPacketSetSlot)var1.d();
            if(var3.func_149174_e().getDisplayName().contains("§") && var3.func_149174_e().getDisplayName().contains("Spectator")) {
               this.D = true;
            }
         }

         if(!lS.a(WL.PRE) && !lS.a(WL.LOBBY)) {
            if(this.D || !(var1.d() instanceof S38PacketPlayerListItem)) {
               return;
            }

            S38PacketPlayerListItem var7 = (S38PacketPlayerListItem)var1.d();
            Iterator var4 = var7.func_179767_a().iterator();
            if(var4.hasNext()) {
               S38PacketPlayerListItem$AddPlayerData var5 = (S38PacketPlayerListItem$AddPlayerData)var4.next();
               EntityPlayer var6 = this.w.theWorld.getPlayerEntityByUUID(var5.getProfile().getId());
               if(var7.func_179768_b().equals(S38PacketPlayerListItem$Action.REMOVE_PLAYER) && !var6.equals(this.w.thePlayer) && var5.getProfile().getName() == null && var6.getHealth() < var6.getMaxHealth() && this.B.contains(var6)) {
                  if(this.y.a((double)this.d())) {
                     this.a(var6);
                  }

                  this.j.y().a(new C9(this, this.e(), var6));
               }
            }
         }

         if(this.D) {
            this.D = false;
         }
      }

   }

   @agu
   public void a(uO var1) {
      String[] var2 = WindowedFullscreen.a();
      if(!this.B.isEmpty()) {
         this.B.clear();
      }

      this.D = false;
      this.C = 0;
   }

   @agu
   public void a(BT var1) {
      String[] var2 = WindowedFullscreen.a();
      if(this.a((Class)asx.class) && ((asx)this.b(asx.class)).P()) {
         asx var3 = (asx)this.b(asx.class);
         if(var3.n() != null && var3.n() instanceof EntityPlayer) {
            EntityPlayer var4 = (EntityPlayer)var3.n();
            if(!this.B.contains(var4)) {
               this.B.add(var4);
            }
         }
      }

   }

   public List c() {
      return this.A;
   }

   public Path f() {
      return this.x;
   }

   static void a(av9 var0, EntityPlayer var1) {
      var0.a(var1);
   }

   private static FileNotFoundException a(FileNotFoundException var0) {
      return var0;
   }
}
