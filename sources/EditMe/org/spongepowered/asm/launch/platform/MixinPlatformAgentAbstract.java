package org.spongepowered.asm.launch.platform;

import java.io.File;
import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class MixinPlatformAgentAbstract implements IMixinPlatformAgent {
   protected static final Logger logger = LogManager.getLogger("mixin");
   protected final MixinPlatformManager manager;
   protected final URI uri;
   protected final File container;
   protected final MainAttributes attributes;

   public MixinPlatformAgentAbstract(MixinPlatformManager var1, URI var2) {
      this.manager = var1;
      this.uri = var2;
      this.container = this.uri != null ? new File(this.uri) : null;
      this.attributes = MainAttributes.of(var2);
   }

   public String toString() {
      return String.format("PlatformAgent[%s:%s]", this.getClass().getSimpleName(), this.uri);
   }

   public String getPhaseProvider() {
      return null;
   }
}
