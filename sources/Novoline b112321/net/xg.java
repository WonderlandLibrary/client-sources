package net;

import com.viaversion.viaversion.util.Config;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.ago;
import net.bY;

public class xg extends Config implements ago {
   public xg(File var1) {
      bY.c();
      super(var1);
      this.reloadConfig();
   }

   public bY a() {
      return bY.valueOf(this.getString("cooldown-indicator", "TITLE").toUpperCase());
   }

   public boolean f() {
      return this.getBoolean("replace-adventure", false);
   }

   public boolean e() {
      return this.getBoolean("replace-particles", false);
   }

   public URL getDefaultConfigURL() {
      return this.getClass().getClassLoader().getResource("assets/viarewind/config.yml");
   }

   protected void handleConfig(Map var1) {
   }

   public List getUnsupportedOptions() {
      return Collections.emptyList();
   }
}
