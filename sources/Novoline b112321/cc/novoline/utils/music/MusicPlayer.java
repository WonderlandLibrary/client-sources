package cc.novoline.utils.music;

import cc.novoline.utils.music.WAVMusicPlayer;

public class MusicPlayer {
   private WAVMusicPlayer activePlayer = new WAVMusicPlayer();

   public void setup(String var1) {
      MusicPlayer var10000 = this;

      try {
         var10000.activePlayer.setup(var1);
      } catch (Throwable var3) {
         var3.printStackTrace();
      }

   }

   public void play() {
      this.activePlayer.playLooping();
   }

   public void stop() {
      this.activePlayer.stop();
   }
}
