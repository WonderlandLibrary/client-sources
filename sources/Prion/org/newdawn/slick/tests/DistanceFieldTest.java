package org.newdawn.slick.tests;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;








public class DistanceFieldTest
  extends BasicGame
{
  private AngelCodeFont font;
  
  public DistanceFieldTest()
  {
    super("DistanceMapTest Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    font = new AngelCodeFont("testdata/distance.fnt", "testdata/distance-dis.png");
    container.getGraphics().setBackground(Color.black);
  }
  



  public void update(GameContainer container, int delta)
    throws SlickException
  {}
  


  public void render(GameContainer container, Graphics g)
    throws SlickException
  {
    String text = "abc";
    font.drawString(610.0F, 100.0F, text);
    
    GL11.glDisable(3042);
    GL11.glEnable(3008);
    GL11.glAlphaFunc(518, 0.5F);
    font.drawString(610.0F, 150.0F, text);
    GL11.glDisable(3008);
    GL11.glEnable(3042);
    
    g.translate(-50.0F, -130.0F);
    g.scale(10.0F, 10.0F);
    font.drawString(0.0F, 0.0F, text);
    
    GL11.glDisable(3042);
    GL11.glEnable(3008);
    GL11.glAlphaFunc(518, 0.5F);
    font.drawString(0.0F, 26.0F, text);
    GL11.glDisable(3008);
    GL11.glEnable(3042);
    
    g.resetTransform();
    g.setColor(Color.lightGray);
    g.drawString("Original Size on Sheet", 620.0F, 210.0F);
    g.drawString("10x Scale Up", 40.0F, 575.0F);
    
    g.setColor(Color.darkGray);
    g.drawRect(40.0F, 40.0F, 560.0F, 530.0F);
    g.drawRect(610.0F, 105.0F, 150.0F, 100.0F);
    
    g.setColor(Color.white);
    g.drawString("512x512 Font Sheet", 620.0F, 300.0F);
    g.drawString("NEHE Charset", 620.0F, 320.0F);
    g.drawString("4096x4096 (8x) Source Image", 620.0F, 340.0F);
    g.drawString("ScanSize = 20", 620.0F, 360.0F);
  }
  




  public void keyPressed(int key, char c) {}
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new DistanceFieldTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}
