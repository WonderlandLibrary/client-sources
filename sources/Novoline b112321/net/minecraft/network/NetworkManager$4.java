package net.minecraft.network;

import com.viaversion.viaversion.api.platform.ViaPlatform;
import net.WS;
import net.aLy;
import net.hZ;
import net.lc;

public final class NetworkManager$4 {
   private ViaPlatform b;
   private hZ a;
   private aLy d;
   private WS c;

   public NetworkManager$4 a(ViaPlatform var1) {
      this.b = var1;
      return this;
   }

   public NetworkManager$4 a(hZ var1) {
      this.a = var1;
      return this;
   }

   public NetworkManager$4 a(WS var1) {
      this.c = var1;
      return this;
   }

   public NetworkManager$4 a(aLy var1) {
      this.d = var1;
      return this;
   }

   public lc a() {
      return new lc(this.b, this.a, this.d, this.c);
   }
}
