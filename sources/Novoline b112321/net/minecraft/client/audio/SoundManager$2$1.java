package net.minecraft.client.audio;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import net.rj;
import net.minecraft.client.Minecraft;

class SoundManager$2$1 extends URLConnection {
   final rj a;

   SoundManager$2$1(rj var1, URL var2) {
      super(var2);
      this.a = var1;
   }

   public void connect() throws IOException {
   }

   public InputStream getInputStream() throws IOException {
      return Minecraft.getInstance().getResourceManager().getResource(this.a.a).getInputStream();
   }
}
