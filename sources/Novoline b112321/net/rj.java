package net;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import net.minecraft.client.audio.SoundManager$2$1;
import net.minecraft.util.ResourceLocation;

final class rj extends URLStreamHandler {
   final ResourceLocation a;

   rj(ResourceLocation var1) {
      this.a = var1;
   }

   protected URLConnection openConnection(URL var1) {
      return new SoundManager$2$1(this, var1);
   }
}
