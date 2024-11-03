package de.florianmichael.vialoadingbase.platform.viaversion;

import com.viaversion.viaversion.commands.ViaCommandHandler;
import de.florianmichael.vialoadingbase.command.impl.LeakDetectSubCommand;

public class VLBViaCommandHandler extends ViaCommandHandler {
   public VLBViaCommandHandler() {
      this.registerVLBDefaults();
   }

   public void registerVLBDefaults() {
      this.registerSubCommand(new LeakDetectSubCommand());
   }
}
