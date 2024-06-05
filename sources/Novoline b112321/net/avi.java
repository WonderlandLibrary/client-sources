package net;

import cc.novoline.modules.misc.WindowedFullscreen;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import net.BT;
import net.UW;
import net.VN;
import net.a2V;
import net.a6d;
import net.aEs;
import net.aEu;
import net.adZ;
import net.ae9;
import net.agu;
import net.as0;
import net.axu;
import net.uO;
import net.minecraft.client.network.NetworkPlayerInfo;

public final class avi extends as0 {
   public List y;
   @VN("local-player-name")
   private final aEs z;
   @VN("local-player")
   private final aEu A;
   @VN("scrambled-enemies")
   private final aEu x;
   @VN("hide-server-id")
   private final aEu B;

   public avi(UW var1) {
      WindowedFullscreen.a();
      super(var1, "Streamer", a2V.MISC, "");
      this.y = new CopyOnWriteArrayList();
      this.z = axu.a("User");
      this.A = axu.g();
      this.x = axu.g();
      this.B = axu.g();
      ae9.a(new adZ("LOCALSCRAMBLE", "Change your name", a6d.CHECKBOX, this, this.A));
      a6d var10004 = a6d.TEXTBOX;
      aEs var10007 = this.z;
      aEu var10008 = this.A;
      this.A.getClass();
      ae9.a(new adZ("LOCAL", "Local name", var10004, this, "New name", var10007, var10008::a));
      ae9.a(new adZ("SCRAMBLEENEMIES", "Hide others", a6d.CHECKBOX, this, this.x));
      ae9.a(new adZ("HIDEGAMEID", "Hide server ID", a6d.CHECKBOX, this, this.B));
      if(PacketRemapper.b() == null) {
         WindowedFullscreen.b(new String[3]);
      }

   }

   @agu
   public void a(BT var1) {
      WindowedFullscreen.a();
      Iterator var3 = this.w.getNetHandler().getPlayerInfoMap().iterator();
      if(var3.hasNext()) {
         NetworkPlayerInfo var4 = (NetworkPlayerInfo)var3.next();
         String var5 = var4.getGameProfile().getName();
         if(!this.y.contains(var5)) {
            this.y.add(var5);
         }
      }

   }

   @agu
   public void a(uO var1) {
      String[] var2 = WindowedFullscreen.a();
      if(!this.y.isEmpty()) {
         this.y.clear();
      }

   }

   public void c() {
      String[] var1 = WindowedFullscreen.a();
      if(!this.y.isEmpty()) {
         this.y.clear();
      }

   }

   public aEs a() {
      return this.z;
   }

   public aEu c() {
      return this.A;
   }

   public aEu d() {
      return this.x;
   }

   public aEu b() {
      return this.B;
   }
}
