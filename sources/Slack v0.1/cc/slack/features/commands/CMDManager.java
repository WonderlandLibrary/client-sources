package cc.slack.features.commands;

import cc.slack.features.commands.api.CMD;
import cc.slack.features.commands.impl.ConfigCMD;
import cc.slack.features.commands.impl.HelpCMD;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CMDManager {
   private final List<CMD> commands = new ArrayList();
   private final String prefix = ".";

   public void initialize() {
      try {
         this.addCommands(new ConfigCMD(), new HelpCMD());
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void addCommands(CMD... cmds) {
      this.commands.addAll(Arrays.asList(cmds));
   }

   public List<CMD> getCommands() {
      return this.commands;
   }

   public String getPrefix() {
      this.getClass();
      return ".";
   }
}
