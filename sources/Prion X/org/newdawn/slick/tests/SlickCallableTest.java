package org.newdawn.slick.tests;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.SlickCallable;












public class SlickCallableTest
  extends BasicGame
{
  private Image image;
  private Image back;
  private float rot;
  private AngelCodeFont font;
  private Animation homer;
  
  public SlickCallableTest()
  {
    super("Slick Callable Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    image = new Image("testdata/rocket.png");
    back = new Image("testdata/sky.jpg");
    font = new AngelCodeFont("testdata/hiero.fnt", "testdata/hiero.png");
    SpriteSheet sheet = new SpriteSheet("testdata/homeranim.png", 36, 65);
    homer = new Animation(sheet, 0, 0, 7, 0, true, 150, true);
  }
  

  public void render(GameContainer container, Graphics g)
    throws SlickException
  {
    g.scale(2.0F, 2.0F);
    g.fillRect(0.0F, 0.0F, 800.0F, 600.0F, back, 0.0F, 0.0F);
    g.resetTransform();
    
    g.drawImage(image, 100.0F, 100.0F);
    image.draw(100.0F, 200.0F, 80.0F, 200.0F);
    
    font.drawString(100.0F, 200.0F, "Text Drawn before the callable");
    
    SlickCallable callable = new SlickCallable() {
      protected void performGLOperations() throws SlickException {
        renderGL();
      }
    };
    callable.call();
    
    homer.draw(450.0F, 250.0F, 80.0F, 200.0F);
    font.drawString(150.0F, 300.0F, "Text Drawn after the callable");
  }
  




  public void renderGL()
  {
    FloatBuffer pos = BufferUtils.createFloatBuffer(4);
    pos.put(new float[] { 5.0F, 5.0F, 10.0F, 0.0F }).flip();
    FloatBuffer red = BufferUtils.createFloatBuffer(4);
    red.put(new float[] { 0.8F, 0.1F, 0.0F, 1.0F }).flip();
    
    GL11.glLight(16384, 4611, pos);
    GL11.glEnable(16384);
    
    GL11.glEnable(2884);
    GL11.glEnable(2929);
    GL11.glEnable(2896);
    
    GL11.glMatrixMode(5889);
    GL11.glLoadIdentity();
    float h = 0.75F;
    GL11.glFrustum(-1.0D, 1.0D, -h, h, 5.0D, 60.0D);
    GL11.glMatrixMode(5888);
    GL11.glLoadIdentity();
    GL11.glTranslatef(0.0F, 0.0F, -40.0F);
    GL11.glRotatef(rot, 0.0F, 1.0F, 1.0F);
    
    GL11.glMaterial(1028, 5634, red);
    gear(0.5F, 2.0F, 2.0F, 10, 0.7F);
  }
  













  private void gear(float inner_radius, float outer_radius, float width, int teeth, float tooth_depth)
  {
    float r0 = inner_radius;
    float r1 = outer_radius - tooth_depth / 2.0F;
    float r2 = outer_radius + tooth_depth / 2.0F;
    
    float da = 6.2831855F / teeth / 4.0F;
    
    GL11.glShadeModel(7424);
    
    GL11.glNormal3f(0.0F, 0.0F, 1.0F);
    

    GL11.glBegin(8);
    for (int i = 0; i <= teeth; i++) {
      float angle = i * 2.0F * 3.1415927F / teeth;
      GL11.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), width * 0.5F);
      GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), width * 0.5F);
      if (i < teeth) {
        GL11.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), width * 0.5F);
        GL11.glVertex3f(r1 * (float)Math.cos(angle + 3.0F * da), r1 * (float)Math.sin(angle + 3.0F * da), 
          width * 0.5F);
      }
    }
    GL11.glEnd();
    

    GL11.glBegin(7);
    for (i = 0; i < teeth; i++) {
      float angle = i * 2.0F * 3.1415927F / teeth;
      GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), width * 0.5F);
      GL11.glVertex3f(r2 * (float)Math.cos(angle + da), r2 * (float)Math.sin(angle + da), width * 0.5F);
      GL11.glVertex3f(r2 * (float)Math.cos(angle + 2.0F * da), r2 * (float)Math.sin(angle + 2.0F * da), width * 0.5F);
      GL11.glVertex3f(r1 * (float)Math.cos(angle + 3.0F * da), r1 * (float)Math.sin(angle + 3.0F * da), width * 0.5F);
    }
    GL11.glEnd();
    

    GL11.glNormal3f(0.0F, 0.0F, -1.0F);
    GL11.glBegin(8);
    for (i = 0; i <= teeth; i++) {
      float angle = i * 2.0F * 3.1415927F / teeth;
      GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), -width * 0.5F);
      GL11.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), -width * 0.5F);
      GL11.glVertex3f(r1 * (float)Math.cos(angle + 3.0F * da), r1 * (float)Math.sin(angle + 3.0F * da), -width * 0.5F);
      GL11.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), -width * 0.5F);
    }
    GL11.glEnd();
    

    GL11.glBegin(7);
    for (i = 0; i < teeth; i++) {
      float angle = i * 2.0F * 3.1415927F / teeth;
      GL11.glVertex3f(r1 * (float)Math.cos(angle + 3.0F * da), r1 * (float)Math.sin(angle + 3.0F * da), -width * 0.5F);
      GL11.glVertex3f(r2 * (float)Math.cos(angle + 2.0F * da), r2 * (float)Math.sin(angle + 2.0F * da), -width * 0.5F);
      GL11.glVertex3f(r2 * (float)Math.cos(angle + da), r2 * (float)Math.sin(angle + da), -width * 0.5F);
      GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), -width * 0.5F);
    }
    GL11.glEnd();
    GL11.glNormal3f(0.0F, 0.0F, 1.0F);
    

    GL11.glBegin(8);
    for (i = 0; i < teeth; i++) {
      float angle = i * 2.0F * 3.1415927F / teeth;
      GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), width * 0.5F);
      GL11.glVertex3f(r1 * (float)Math.cos(angle), r1 * (float)Math.sin(angle), -width * 0.5F);
      float u = r2 * (float)Math.cos(angle + da) - r1 * (float)Math.cos(angle);
      float v = r2 * (float)Math.sin(angle + da) - r1 * (float)Math.sin(angle);
      float len = (float)Math.sqrt(u * u + v * v);
      u /= len;
      v /= len;
      GL11.glNormal3f(v, -u, 0.0F);
      GL11.glVertex3f(r2 * (float)Math.cos(angle + da), r2 * (float)Math.sin(angle + da), width * 0.5F);
      GL11.glVertex3f(r2 * (float)Math.cos(angle + da), r2 * (float)Math.sin(angle + da), -width * 0.5F);
      GL11.glNormal3f((float)Math.cos(angle), (float)Math.sin(angle), 0.0F);
      GL11.glVertex3f(r2 * (float)Math.cos(angle + 2.0F * da), r2 * (float)Math.sin(angle + 2.0F * da), width * 0.5F);
      GL11.glVertex3f(r2 * (float)Math.cos(angle + 2.0F * da), r2 * (float)Math.sin(angle + 2.0F * da), -width * 0.5F);
      u = r1 * (float)Math.cos(angle + 3.0F * da) - r2 * (float)Math.cos(angle + 2.0F * da);
      v = r1 * (float)Math.sin(angle + 3.0F * da) - r2 * (float)Math.sin(angle + 2.0F * da);
      GL11.glNormal3f(v, -u, 0.0F);
      GL11.glVertex3f(r1 * (float)Math.cos(angle + 3.0F * da), r1 * (float)Math.sin(angle + 3.0F * da), width * 0.5F);
      GL11.glVertex3f(r1 * (float)Math.cos(angle + 3.0F * da), r1 * (float)Math.sin(angle + 3.0F * da), -width * 0.5F);
      GL11.glNormal3f((float)Math.cos(angle), (float)Math.sin(angle), 0.0F);
    }
    GL11.glVertex3f(r1 * (float)Math.cos(0.0D), r1 * (float)Math.sin(0.0D), width * 0.5F);
    GL11.glVertex3f(r1 * (float)Math.cos(0.0D), r1 * (float)Math.sin(0.0D), -width * 0.5F);
    GL11.glEnd();
    
    GL11.glShadeModel(7425);
    

    GL11.glBegin(8);
    for (i = 0; i <= teeth; i++) {
      float angle = i * 2.0F * 3.1415927F / teeth;
      GL11.glNormal3f(-(float)Math.cos(angle), -(float)Math.sin(angle), 0.0F);
      GL11.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), -width * 0.5F);
      GL11.glVertex3f(r0 * (float)Math.cos(angle), r0 * (float)Math.sin(angle), width * 0.5F);
    }
    GL11.glEnd();
  }
  


  public void update(GameContainer container, int delta)
  {
    rot += delta * 0.1F;
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new SlickCallableTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}
