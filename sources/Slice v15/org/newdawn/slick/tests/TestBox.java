package org.newdawn.slick.tests;

import java.util.ArrayList;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.util.Log;








public class TestBox
  extends BasicGame
{
  private ArrayList games = new ArrayList();
  

  private BasicGame currentGame;
  
  private int index;
  
  private AppGameContainer container;
  

  public TestBox()
  {
    super("Test Box");
  }
  




  public void addGame(Class game)
  {
    games.add(game);
  }
  


  private void nextGame()
  {
    if (index == -1) {
      return;
    }
    
    index += 1;
    if (index >= games.size()) {
      index = 0;
    }
    
    startGame();
  }
  

  private void startGame()
  {
    try
    {
      currentGame = ((BasicGame)((Class)games.get(index)).newInstance());
      container.getGraphics().setBackground(Color.black);
      currentGame.init(container);
      currentGame.render(container, container.getGraphics());
    } catch (Exception e) {
      Log.error(e);
    }
    
    container.setTitle(currentGame.getTitle());
  }
  

  public void init(GameContainer c)
    throws SlickException
  {
    if (games.size() == 0) {
      currentGame = new BasicGame("NULL") {
        public void init(GameContainer container) throws SlickException
        {}
        
        public void update(GameContainer container, int delta) throws SlickException
        {}
        
        public void render(GameContainer container, Graphics g) throws SlickException
        {}
      };
      currentGame.init(c);
      index = -1;
    } else {
      index = 0;
      container = ((AppGameContainer)c);
      startGame();
    }
  }
  

  public void update(GameContainer container, int delta)
    throws SlickException
  {
    currentGame.update(container, delta);
  }
  

  public void render(GameContainer container, Graphics g)
    throws SlickException
  {
    SlickCallable.enterSafeBlock();
    currentGame.render(container, g);
    SlickCallable.leaveSafeBlock();
  }
  


  public void controllerButtonPressed(int controller, int button)
  {
    currentGame.controllerButtonPressed(controller, button);
  }
  


  public void controllerButtonReleased(int controller, int button)
  {
    currentGame.controllerButtonReleased(controller, button);
  }
  


  public void controllerDownPressed(int controller)
  {
    currentGame.controllerDownPressed(controller);
  }
  


  public void controllerDownReleased(int controller)
  {
    currentGame.controllerDownReleased(controller);
  }
  


  public void controllerLeftPressed(int controller)
  {
    currentGame.controllerLeftPressed(controller);
  }
  


  public void controllerLeftReleased(int controller)
  {
    currentGame.controllerLeftReleased(controller);
  }
  


  public void controllerRightPressed(int controller)
  {
    currentGame.controllerRightPressed(controller);
  }
  


  public void controllerRightReleased(int controller)
  {
    currentGame.controllerRightReleased(controller);
  }
  


  public void controllerUpPressed(int controller)
  {
    currentGame.controllerUpPressed(controller);
  }
  


  public void controllerUpReleased(int controller)
  {
    currentGame.controllerUpReleased(controller);
  }
  


  public void keyPressed(int key, char c)
  {
    currentGame.keyPressed(key, c);
    
    if (key == 28) {
      nextGame();
    }
  }
  


  public void keyReleased(int key, char c)
  {
    currentGame.keyReleased(key, c);
  }
  


  public void mouseMoved(int oldx, int oldy, int newx, int newy)
  {
    currentGame.mouseMoved(oldx, oldy, newx, newy);
  }
  


  public void mousePressed(int button, int x, int y)
  {
    currentGame.mousePressed(button, x, y);
  }
  


  public void mouseReleased(int button, int x, int y)
  {
    currentGame.mouseReleased(button, x, y);
  }
  


  public void mouseWheelMoved(int change)
  {
    currentGame.mouseWheelMoved(change);
  }
  



  public static void main(String[] argv)
  {
    try
    {
      TestBox box = new TestBox();
      box.addGame(AnimationTest.class);
      box.addGame(AntiAliasTest.class);
      box.addGame(BigImageTest.class);
      box.addGame(ClipTest.class);
      box.addGame(DuplicateEmitterTest.class);
      box.addGame(FlashTest.class);
      box.addGame(FontPerformanceTest.class);
      box.addGame(FontTest.class);
      box.addGame(GeomTest.class);
      box.addGame(GradientTest.class);
      box.addGame(GraphicsTest.class);
      box.addGame(ImageBufferTest.class);
      box.addGame(ImageReadTest.class);
      box.addGame(ImageTest.class);
      box.addGame(KeyRepeatTest.class);
      box.addGame(MusicListenerTest.class);
      box.addGame(PackedSheetTest.class);
      box.addGame(PedigreeTest.class);
      box.addGame(PureFontTest.class);
      box.addGame(ShapeTest.class);
      box.addGame(SoundTest.class);
      box.addGame(SpriteSheetFontTest.class);
      box.addGame(TransparentColorTest.class);
      
      AppGameContainer container = new AppGameContainer(box);
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}
