package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.util.ResourceLoader;














public class SoundURLTest
  extends BasicGame
{
  private Sound sound;
  private Sound charlie;
  private Sound burp;
  private Music music;
  private Music musica;
  private Music musicb;
  private Sound engine;
  private int volume = 1;
  


  public SoundURLTest()
  {
    super("Sound URL Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    sound = new Sound(ResourceLoader.getResource("testdata/restart.ogg"));
    charlie = new Sound(ResourceLoader.getResource("testdata/cbrown01.wav"));
    engine = new Sound(ResourceLoader.getResource("testdata/engine.wav"));
    
    music = (this.musica = new Music(ResourceLoader.getResource("testdata/restart.ogg"), false));
    musicb = new Music(ResourceLoader.getResource("testdata/kirby.ogg"), false);
    burp = new Sound(ResourceLoader.getResource("testdata/burp.aif"));
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
    g.drawString("Press + or - to change volume of music", 100.0F, 270.0F);
    g.setColor(Color.blue);
    g.drawString("Music Volume Level: " + volume / 10.0F, 150.0F, 300.0F);
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
    if (key == 18) {
      if (engine.playing()) {
        engine.stop();
      } else {
        engine.loop();
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
  }
  




  private void setVolume()
  {
    if (volume > 10) {
      volume = 10;
    } else if (volume < 0) {
      volume = 0;
    }
    
    music.setVolume(volume / 10.0F);
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new SoundURLTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}
