package org.newdawn.slick.tests;

import java.io.IOException;
import java.io.PrintStream;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;















public class SoundTest
  extends BasicGame
{
  private GameContainer myContainer;
  private Sound sound;
  private Sound charlie;
  private Sound burp;
  private Music music;
  private Music musica;
  private Music musicb;
  private Audio engine;
  private int volume = 10;
  

  private int[] engines = new int[3];
  


  public SoundTest()
  {
    super("Sound And Music Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    SoundStore.get().setMaxSources(32);
    
    myContainer = container;
    sound = new Sound("testdata/restart.ogg");
    charlie = new Sound("testdata/cbrown01.wav");
    try {
      engine = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("testdata/engine.wav"));
    } catch (IOException e) {
      throw new SlickException("Failed to load engine", e);
    }
    music = (this.musica = new Music("testdata/SMB-X.XM"));
    
    musicb = new Music("testdata/kirby.ogg", true);
    burp = new Sound("testdata/burp.aif");
    
    music.play();
  }
  


  public void render(GameContainer container, Graphics g)
  {
    g.setColor(Color.white);
    g.drawString("The OGG loop is now streaming from the file, woot.", 100.0F, 60.0F);
    g.drawString("Press space for sound effect (OGG)", 100.0F, 100.0F);
    g.drawString("Press P to pause/resume music (XM)", 100.0F, 130.0F);
    g.drawString("Press E to pause/resume engine sound (WAV)", 100.0F, 190.0F);
    g.drawString("Press enter for charlie (WAV)", 100.0F, 160.0F);
    g.drawString("Press C to change music", 100.0F, 210.0F);
    g.drawString("Press B to burp (AIF)", 100.0F, 240.0F);
    g.drawString("Press + or - to change global volume of music", 100.0F, 270.0F);
    g.drawString("Press Y or X to change individual volume of music", 100.0F, 300.0F);
    g.drawString("Press N or M to change global volume of sound fx", 100.0F, 330.0F);
    g.setColor(Color.blue);
    g.drawString("Global Sound Volume Level: " + container.getSoundVolume(), 150.0F, 390.0F);
    g.drawString("Global Music Volume Level: " + container.getMusicVolume(), 150.0F, 420.0F);
    g.drawString("Current Music Volume Level: " + music.getVolume(), 150.0F, 450.0F);
  }
  



  public void update(GameContainer container, int delta) {}
  



  public void keyPressed(int key, char c)
  {
    if (key == 1) {
      System.exit(0);
    }
    if (key == 57) {
      sound.play();
    }
    if (key == 48) {
      burp.play();
    }
    if (key == 30) {
      sound.playAt(-1.0F, 0.0F, 0.0F);
    }
    if (key == 38) {
      sound.playAt(1.0F, 0.0F, 0.0F);
    }
    if (key == 28) {
      charlie.play(1.0F, 1.0F);
    }
    if (key == 25) {
      if (music.playing()) {
        music.pause();
      } else {
        music.resume();
      }
    }
    if (key == 46) {
      music.stop();
      if (music == musica) {
        music = musicb;
      } else {
        music = musica;
      }
      
      music.loop();
    }
    for (int i = 0; i < 3; i++) {
      if (key == 2 + i) {
        if (engines[i] != 0) {
          System.out.println("Stop " + i);
          SoundStore.get().stopSoundEffect(engines[i]);
          engines[i] = 0;
        } else {
          System.out.println("Start " + i);
          engines[i] = engine.playAsSoundEffect(1.0F, 1.0F, true);
        }
      }
    }
    
    if (c == '+') {
      volume += 1;
      setVolume();
    }
    
    if (c == '-') {
      volume -= 1;
      setVolume();
    }
    
    if (key == 21) {
      int vol = (int)(music.getVolume() * 10.0F);
      vol--;
      if (vol < 0) { vol = 0;
      }
      music.setVolume(vol / 10.0F);
    }
    if (key == 45) {
      int vol = (int)(music.getVolume() * 10.0F);
      vol++;
      if (vol > 10) { vol = 10;
      }
      music.setVolume(vol / 10.0F);
    }
    if (key == 49) {
      int vol = (int)(myContainer.getSoundVolume() * 10.0F);
      vol--;
      if (vol < 0) { vol = 0;
      }
      myContainer.setSoundVolume(vol / 10.0F);
    }
    if (key == 50) {
      int vol = (int)(myContainer.getSoundVolume() * 10.0F);
      vol++;
      if (vol > 10) { vol = 10;
      }
      myContainer.setSoundVolume(vol / 10.0F);
    }
  }
  




  private void setVolume()
  {
    if (volume > 10) {
      volume = 10;
    } else if (volume < 0) {
      volume = 0;
    }
    
    myContainer.setMusicVolume(volume / 10.0F);
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new SoundTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}
