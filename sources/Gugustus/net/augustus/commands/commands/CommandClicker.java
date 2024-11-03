package net.augustus.commands.commands;

import net.augustus.commands.Command;
import net.augustus.utils.ClickRecordingUtil;

public class CommandClicker extends Command {
   public final ClickRecordingUtil clickRecordingUtil = new ClickRecordingUtil();

   public CommandClicker() {
      super(".clicker");
   }

   @Override
   public void commandAction(String[] message) {
      super.commandAction(message);
      if (message.length > 1) {
         if (message[1].equalsIgnoreCase("start")) {
            this.clickRecordingUtil.startRecording();
            this.sendChat("Started Recording... Please press sneak to record");
            return;
         }

         if (message[1].equalsIgnoreCase("stop")) {
            this.clickRecordingUtil.stopRecording();
            this.sendChat("Stopped Recording... ");
            return;
         }
      }

      this.sendChat(".clicker [start/stop] ");
   }

   @Override
   public void helpMessage() {
      this.sendChat(".clicker (Start or stop the click recording)");
   }
}
