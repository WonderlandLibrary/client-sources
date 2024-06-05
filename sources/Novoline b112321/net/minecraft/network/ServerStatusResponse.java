package net.minecraft.network;

import net.ary;
import net.at_;
import net.minecraft.util.IChatComponent;

public class ServerStatusResponse {
   private IChatComponent serverMotd;
   private ary a;
   private at_ c;
   private String favicon;

   public IChatComponent getServerDescription() {
      return this.serverMotd;
   }

   public void setServerDescription(IChatComponent var1) {
      this.serverMotd = var1;
   }

   public ary c() {
      return this.a;
   }

   public void a(ary var1) {
      this.a = var1;
   }

   public at_ d() {
      return this.c;
   }

   public void a(at_ var1) {
      this.c = var1;
   }

   public void setFavicon(String var1) {
      this.favicon = var1;
   }

   public String getFavicon() {
      return this.favicon;
   }
}
