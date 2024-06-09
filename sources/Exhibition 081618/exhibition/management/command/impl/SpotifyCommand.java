package exhibition.management.command.impl;

import exhibition.management.command.Command;
import exhibition.module.impl.other.Spotify;
import exhibition.util.misc.ChatUtil;

public class SpotifyCommand extends Command {
   public SpotifyCommand(String[] names, String description) {
      super(names, description);
   }

   public String getUsage() {
      return "";
   }

   public void fire(String[] args) {
      if (args == null) {
         this.printUsage();
      } else {
         if (args[0].equalsIgnoreCase("pause")) {
            (new Thread(() -> {
               Spotify.spotifyManager.pauseSong(true);
            })).start();
         } else if (args[0].equalsIgnoreCase("play")) {
            (new Thread(() -> {
               Spotify.spotifyManager.pauseSong(false);
            })).start();
         } else {
            ChatUtil.printChat("§4[§cE§4]§8 ???");
         }

      }
   }
}
