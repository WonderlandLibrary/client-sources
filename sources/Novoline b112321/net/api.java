package net;

import com.viaversion.viaversion.api.Via;
import java.io.File;
import java.util.logging.Logger;
import net.NO;
import net.VV;
import net.aiv;
import net.akw;
import net.minecraft.client.Minecraft;

public class api implements aiv {
   public api() {
      NO.c();
      VV.a(this, new akw(this));
      this.a(Minecraft.getMinecraft().mcDataDir);
   }

   public Logger a() {
      return Via.d().getLogger();
   }

   public void d() {
   }

   public boolean c() {
      return false;
   }

   public File b() {
      return Minecraft.getMinecraft().mcDataDir;
   }
}
