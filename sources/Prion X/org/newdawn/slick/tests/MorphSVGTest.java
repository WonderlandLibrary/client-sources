package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.svg.Diagram;
import org.newdawn.slick.svg.InkscapeLoader;
import org.newdawn.slick.svg.SVGMorph;
import org.newdawn.slick.svg.SimpleDiagramRenderer;








public class MorphSVGTest
  extends BasicGame
{
  private SVGMorph morph;
  private Diagram base;
  private float time;
  private float x = -300.0F;
  


  public MorphSVGTest()
  {
    super("MorphShapeTest");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    base = InkscapeLoader.load("testdata/svg/walk1.svg");
    morph = new SVGMorph(base);
    morph.addStep(InkscapeLoader.load("testdata/svg/walk2.svg"));
    morph.addStep(InkscapeLoader.load("testdata/svg/walk3.svg"));
    morph.addStep(InkscapeLoader.load("testdata/svg/walk4.svg"));
    
    container.setVSync(true);
  }
  


  public void update(GameContainer container, int delta)
    throws SlickException
  {
    morph.updateMorphTime(delta * 0.003F);
    
    x += delta * 0.2F;
    if (x > 550.0F) {
      x = -450.0F;
    }
  }
  


  public void render(GameContainer container, Graphics g)
    throws SlickException
  {
    g.translate(x, 0.0F);
    SimpleDiagramRenderer.render(g, morph);
  }
  




  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(
        new MorphSVGTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}
