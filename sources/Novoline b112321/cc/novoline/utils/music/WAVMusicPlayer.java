package cc.novoline.utils.music;

import cc.novoline.utils.music.IMusicPlayer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.FloatControl.Type;
import net.acE;

public class WAVMusicPlayer implements IMusicPlayer {
   private Clip clip;
   private static int[] b;

   public void setup(String var1) throws Throwable {
      System.out.println("Trying to get " + var1);
      b();
      AudioInputStream var3 = AudioSystem.getAudioInputStream(this.getClass().getResourceAsStream("/" + var1));
      this.clip = AudioSystem.getClip();
      this.clip.open(var3);
      FloatControl var4 = (FloatControl)this.clip.getControl(Type.MASTER_GAIN);
      var4.setValue(-15.0F);
   }

   public void play() {
      this.clip.start();
   }

   public void stop() {
      b();
      this.clip.loop(0);
      this.clip.setMicrosecondPosition(0L);
      this.clip.stop();
      if(acE.b() == null) {
         b(new int[2]);
      }

   }

   public void playLooping() {
      this.clip.loop(1337);
      this.play();
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }

   static {
      if(b() == null) {
         b(new int[3]);
      }

   }
}
