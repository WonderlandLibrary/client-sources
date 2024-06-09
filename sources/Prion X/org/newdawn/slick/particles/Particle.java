package org.newdawn.slick.particles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;






public class Particle
{
  protected static SGL GL = ;
  

  public static final int INHERIT_POINTS = 1;
  

  public static final int USE_POINTS = 2;
  
  public static final int USE_QUADS = 3;
  
  protected float x;
  
  protected float y;
  
  protected float velx;
  
  protected float vely;
  
  protected float size = 10.0F;
  
  protected Color color = Color.white;
  
  protected float life;
  
  protected float originalLife;
  
  private ParticleSystem engine;
  
  private ParticleEmitter emitter;
  
  protected Image image;
  
  protected int type;
  
  protected int usePoints = 1;
  
  protected boolean oriented = false;
  
  protected float scaleY = 1.0F;
  





  public Particle(ParticleSystem engine)
  {
    this.engine = engine;
  }
  




  public float getX()
  {
    return x;
  }
  




  public float getY()
  {
    return y;
  }
  





  public void move(float x, float y)
  {
    this.x += x;
    this.y += y;
  }
  




  public float getSize()
  {
    return size;
  }
  




  public Color getColor()
  {
    return color;
  }
  





  public void setImage(Image image)
  {
    this.image = image;
  }
  




  public float getOriginalLife()
  {
    return originalLife;
  }
  




  public float getLife()
  {
    return life;
  }
  




  public boolean inUse()
  {
    return life > 0.0F;
  }
  


  public void render()
  {
    if (((engine.usePoints()) && (usePoints == 1)) || 
      (usePoints == 2)) {
      TextureImpl.bindNone();
      GL.glEnable(2832);
      GL.glPointSize(size / 2.0F);
      color.bind();
      GL.glBegin(0);
      GL.glVertex2f(x, y);
      GL.glEnd();
    } else if ((oriented) || (scaleY != 1.0F)) {
      GL.glPushMatrix();
      
      GL.glTranslatef(x, y, 0.0F);
      
      if (oriented) {
        float angle = (float)(Math.atan2(y, x) * 180.0D / 3.141592653589793D);
        GL.glRotatef(angle, 0.0F, 0.0F, 1.0F);
      }
      

      GL.glScalef(1.0F, scaleY, 1.0F);
      
      image.draw((int)-(size / 2.0F), (int)-(size / 2.0F), (int)size, 
        (int)size, color);
      GL.glPopMatrix();
    } else {
      color.bind();
      image.drawEmbedded((int)(x - size / 2.0F), (int)(y - size / 2.0F), 
        (int)size, (int)size);
    }
  }
  





  public void update(int delta)
  {
    emitter.updateParticle(this, delta);
    life -= delta;
    
    if (life > 0.0F) {
      x += delta * velx;
      y += delta * vely;
    } else {
      engine.release(this);
    }
  }
  







  public void init(ParticleEmitter emitter, float life)
  {
    x = 0.0F;
    this.emitter = emitter;
    y = 0.0F;
    velx = 0.0F;
    vely = 0.0F;
    size = 10.0F;
    type = 0;
    originalLife = (this.life = life);
    oriented = false;
    scaleY = 1.0F;
  }
  





  public void setType(int type)
  {
    this.type = type;
  }
  








  public void setUsePoint(int usePoints)
  {
    this.usePoints = usePoints;
  }
  




  public int getType()
  {
    return type;
  }
  





  public void setSize(float size)
  {
    this.size = size;
  }
  





  public void adjustSize(float delta)
  {
    size += delta;
    size = Math.max(0.0F, size);
  }
  





  public void setLife(float life)
  {
    this.life = life;
  }
  





  public void adjustLife(float delta)
  {
    life += delta;
  }
  



  public void kill()
  {
    life = 1.0F;
  }
  











  public void setColor(float r, float g, float b, float a)
  {
    if (color == Color.white) {
      color = new Color(r, g, b, a);
    } else {
      color.r = r;
      color.g = g;
      color.b = b;
      color.a = a;
    }
  }
  







  public void setPosition(float x, float y)
  {
    this.x = x;
    this.y = y;
  }
  









  public void setVelocity(float dirx, float diry, float speed)
  {
    velx = (dirx * speed);
    vely = (diry * speed);
  }
  




  public void setSpeed(float speed)
  {
    float currentSpeed = (float)Math.sqrt(velx * velx + vely * vely);
    velx *= speed;
    vely *= speed;
    velx /= currentSpeed;
    vely /= currentSpeed;
  }
  





  public void setVelocity(float velx, float vely)
  {
    setVelocity(velx, vely, 1.0F);
  }
  







  public void adjustPosition(float dx, float dy)
  {
    x += dx;
    y += dy;
  }
  











  public void adjustColor(float r, float g, float b, float a)
  {
    if (color == Color.white) {
      color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
    }
    color.r += r;
    color.g += g;
    color.b += b;
    color.a += a;
  }
  











  public void adjustColor(int r, int g, int b, int a)
  {
    if (color == Color.white) {
      color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
    }
    
    color.r += r / 255.0F;
    color.g += g / 255.0F;
    color.b += b / 255.0F;
    color.a += a / 255.0F;
  }
  







  public void adjustVelocity(float dx, float dy)
  {
    velx += dx;
    vely += dy;
  }
  




  public ParticleEmitter getEmitter()
  {
    return emitter;
  }
  


  public String toString()
  {
    return super.toString() + " : " + life;
  }
  




  public boolean isOriented()
  {
    return oriented;
  }
  




  public void setOriented(boolean oriented)
  {
    this.oriented = oriented;
  }
  




  public float getScaleY()
  {
    return scaleY;
  }
  




  public void setScaleY(float scaleY)
  {
    this.scaleY = scaleY;
  }
}
