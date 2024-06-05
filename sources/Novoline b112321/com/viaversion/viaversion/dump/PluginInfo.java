package com.viaversion.viaversion.dump;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.List;
import net.amo;

public class PluginInfo {
   private final boolean enabled;
   private final String name;
   private final String version;
   private final String main;
   private final List authors;

   public PluginInfo(boolean var1, String var2, String var3, String var4, List var5) {
      amo.b();
      super();
      this.enabled = var1;
      this.name = var2;
      this.version = var3;
      this.main = var4;
      this.authors = var5;
      if(PacketRemapper.b() == null) {
         amo.b(new PacketRemapper[4]);
      }

   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public String getName() {
      return this.name;
   }

   public String getVersion() {
      return this.version;
   }

   public String getMain() {
      return this.main;
   }

   public List getAuthors() {
      return this.authors;
   }
}
