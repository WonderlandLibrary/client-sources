package exhibition.management.command.impl;

import exhibition.management.command.Command;
import exhibition.util.StringConversions;

public class VClip extends Command {
   public VClip(String[] names, String description) {
      super(names, description);
   }

   public void fire(String[] args) {
      if (args == null) {
         this.printUsage();
      } else if (args.length == 1 && StringConversions.isNumeric(args[0])) {
         this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + Double.parseDouble(args[0]), this.mc.thePlayer.posZ);
      } else {
         this.printUsage();
      }
   }

   public String getUsage() {
      return "<Distance>";
   }
}
