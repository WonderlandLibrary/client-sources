package de.florianmichael.vialoadingbase.command.impl;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LeakDetectSubCommand extends ViaSubCommand {
   @Override
   public String name() {
      return "leakdetect";
   }

   @Override
   public String description() {
      return "Sets ResourceLeakDetector level";
   }

   @Override
   public boolean execute(ViaCommandSender viaCommandSender, String[] strings) {
      if (strings.length == 1) {
         try {
            Level level = Level.valueOf(strings[0]);
            ResourceLeakDetector.setLevel(level);
            viaCommandSender.sendMessage("Set leak detector level to " + level);
         } catch (IllegalArgumentException var4) {
            viaCommandSender.sendMessage("Invalid level (" + Arrays.toString((Object[])Level.values()) + ")");
         }
      } else {
         viaCommandSender.sendMessage("Current leak detection level is " + ResourceLeakDetector.getLevel());
      }

      return true;
   }

   @Override
   public List<String> onTabComplete(ViaCommandSender sender, String[] args) {
      return args.length == 1
         ? Arrays.stream(Level.values()).map(Enum::name).filter(it -> it.startsWith(args[0])).collect(Collectors.toList())
         : super.onTabComplete(sender, args);
   }
}
