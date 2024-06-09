package exhibition.management.command.impl;

import exhibition.management.command.Command;
import exhibition.util.misc.ChatUtil;

public class PhaseMode extends Command {
   public static PhaseMode.Phase phase;

   public PhaseMode(String[] names, String description) {
      super(names, description);
   }

   public void fire(String[] args) {
      if (args == null) {
         ChatUtil.printChat("§4[§cE§4]§8 Current phase mode:§c " + phase.name());
         this.printUsage();
      } else if (args.length > 0) {
         PhaseMode.Phase[] var2 = PhaseMode.Phase.values();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            PhaseMode.Phase type = var2[var4];
            if (type.name().toLowerCase().contains(args[0].toLowerCase())) {
               phase = type;
               ChatUtil.printChat("§4[§cE§4]§8 Phase mode has been set to:§c " + type.name());
            }
         }

      }
   }

   public String getUsage() {
      return "<Spider, Skip, Normal, FullBlock, Silent>";
   }

   static {
      phase = PhaseMode.Phase.Normal;
   }

   public static enum Phase {
      Spider,
      Skip,
      Normal,
      FullBlock,
      Silent;
   }
}
