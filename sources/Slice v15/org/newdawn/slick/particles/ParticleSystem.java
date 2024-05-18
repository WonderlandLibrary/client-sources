package org.newdawn.slick.particles;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.Log;







public class ParticleSystem
{
  protected SGL GL = Renderer.get();
  

  public static final int BLEND_ADDITIVE = 1;
  

  public static final int BLEND_COMBINE = 2;
  

  private static final int DEFAULT_PARTICLES = 100;
  
  private ArrayList removeMe = new ArrayList();
  





  public static void setRelativePath(String path)
  {
    ConfigurableEmitter.setRelativePath(path);
  }
  





  private class ParticlePool
  {
    public Particle[] particles;
    



    public ArrayList available;
    




    public ParticlePool(ParticleSystem system, int maxParticles)
    {
      particles = new Particle[maxParticles];
      available = new ArrayList();
      
      for (int i = 0; i < particles.length; i++)
      {
        particles[i] = createParticle(system);
      }
      
      reset(system);
    }
    




    public void reset(ParticleSystem system)
    {
      available.clear();
      
      for (int i = 0; i < particles.length; i++)
      {
        available.add(particles[i]);
      }
    }
  }
  






  protected HashMap particlesByEmitter = new HashMap();
  

  protected int maxParticlesPerEmitter;
  
  protected ArrayList emitters = new ArrayList();
  

  protected Particle dummy;
  
  private int blendingMode = 2;
  
  private int pCount;
  
  private boolean usePoints;
  
  private float x;
  
  private float y;
  
  private boolean removeCompletedEmitters = true;
  

  private Image sprite;
  
  private boolean visible = true;
  

  private String defaultImageName;
  

  private Color mask;
  


  public ParticleSystem(Image defaultSprite)
  {
    this(defaultSprite, 100);
  }
  




  public ParticleSystem(String defaultSpriteRef)
  {
    this(defaultSpriteRef, 100);
  }
  


  public void reset()
  {
    Iterator pools = particlesByEmitter.values().iterator();
    while (pools.hasNext()) {
      ParticlePool pool = (ParticlePool)pools.next();
      pool.reset(this);
    }
    
    for (int i = 0; i < emitters.size(); i++) {
      ParticleEmitter emitter = (ParticleEmitter)emitters.get(i);
      emitter.resetState();
    }
  }
  





  public boolean isVisible()
  {
    return visible;
  }
  





  public void setVisible(boolean visible)
  {
    this.visible = visible;
  }
  




  public void setRemoveCompletedEmitters(boolean remove)
  {
    removeCompletedEmitters = remove;
  }
  




  public void setUsePoints(boolean usePoints)
  {
    this.usePoints = usePoints;
  }
  




  public boolean usePoints()
  {
    return usePoints;
  }
  





  public ParticleSystem(String defaultSpriteRef, int maxParticles)
  {
    this(defaultSpriteRef, maxParticles, null);
  }
  






  public ParticleSystem(String defaultSpriteRef, int maxParticles, Color mask)
  {
    maxParticlesPerEmitter = maxParticles;
    this.mask = mask;
    
    setDefaultImageName(defaultSpriteRef);
    dummy = createParticle(this);
  }
  





  public ParticleSystem(Image defaultSprite, int maxParticles)
  {
    maxParticlesPerEmitter = maxParticles;
    
    sprite = defaultSprite;
    dummy = createParticle(this);
  }
  




  public void setDefaultImageName(String ref)
  {
    defaultImageName = ref;
    sprite = null;
  }
  






  public int getBlendingMode()
  {
    return blendingMode;
  }
  






  protected Particle createParticle(ParticleSystem system)
  {
    return new Particle(system);
  }
  




  public void setBlendingMode(int mode)
  {
    blendingMode = mode;
  }
  




  public int getEmitterCount()
  {
    return emitters.size();
  }
  





  public ParticleEmitter getEmitter(int index)
  {
    return (ParticleEmitter)emitters.get(index);
  }
  




  public void addEmitter(ParticleEmitter emitter)
  {
    emitters.add(emitter);
    
    ParticlePool pool = new ParticlePool(this, maxParticlesPerEmitter);
    particlesByEmitter.put(emitter, pool);
  }
  




  public void removeEmitter(ParticleEmitter emitter)
  {
    emitters.remove(emitter);
    particlesByEmitter.remove(emitter);
  }
  


  public void removeAllEmitters()
  {
    for (int i = 0; i < emitters.size(); i++) {
      removeEmitter((ParticleEmitter)emitters.get(i));
      i--;
    }
  }
  




  public float getPositionX()
  {
    return x;
  }
  




  public float getPositionY()
  {
    return y;
  }
  






  public void setPosition(float x, float y)
  {
    this.x = x;
    this.y = y;
  }
  


  public void render()
  {
    render(x, y);
  }
  





  public void render(float x, float y)
  {
    if ((sprite == null) && (defaultImageName != null)) {
      loadSystemParticleImage();
    }
    
    if (!visible) {
      return;
    }
    
    GL.glTranslatef(x, y, 0.0F);
    
    if (blendingMode == 1) {
      GL.glBlendFunc(770, 1);
    }
    if (usePoints()) {
      GL.glEnable(2832);
      TextureImpl.bindNone();
    }
    

    for (int emitterIdx = 0; emitterIdx < emitters.size(); emitterIdx++)
    {

      ParticleEmitter emitter = (ParticleEmitter)emitters.get(emitterIdx);
      
      if (emitter.isEnabled())
      {



        if (emitter.useAdditive()) {
          GL.glBlendFunc(770, 1);
        }
        

        ParticlePool pool = (ParticlePool)particlesByEmitter.get(emitter);
        Image image = emitter.getImage();
        if (image == null) {
          image = sprite;
        }
        
        if ((!emitter.isOriented()) && (!emitter.usePoints(this))) {
          image.startUse();
        }
        
        for (int i = 0; i < particles.length; i++)
        {
          if (particles[i].inUse()) {
            particles[i].render();
          }
        }
        if ((!emitter.isOriented()) && (!emitter.usePoints(this))) {
          image.endUse();
        }
        

        if (emitter.useAdditive()) {
          GL.glBlendFunc(770, 771);
        }
      }
    }
    if (usePoints()) {
      GL.glDisable(2832);
    }
    if (blendingMode == 1) {
      GL.glBlendFunc(770, 771);
    }
    
    Color.white.bind();
    GL.glTranslatef(-x, -y, 0.0F);
  }
  


  private void loadSystemParticleImage()
  {
    AccessController.doPrivileged(new PrivilegedAction() {
      public Object run() {
        try {
          if (mask != null) {
            sprite = new Image(defaultImageName, mask);
          } else {
            sprite = new Image(defaultImageName);
          }
        } catch (SlickException e) {
          Log.error(e);
          defaultImageName = null;
        }
        return null;
      }
    });
  }
  




  public void update(int delta)
  {
    if ((sprite == null) && (defaultImageName != null)) {
      loadSystemParticleImage();
    }
    
    removeMe.clear();
    ArrayList emitters = new ArrayList(this.emitters);
    for (int i = 0; i < emitters.size(); i++) {
      ParticleEmitter emitter = (ParticleEmitter)emitters.get(i);
      if (emitter.isEnabled()) {
        emitter.update(this, delta);
        if ((removeCompletedEmitters) && 
          (emitter.completed())) {
          removeMe.add(emitter);
          particlesByEmitter.remove(emitter);
        }
      }
    }
    
    this.emitters.removeAll(removeMe);
    
    pCount = 0;
    
    if (!particlesByEmitter.isEmpty())
    {
      Iterator it = particlesByEmitter.keySet().iterator();
      while (it.hasNext())
      {
        ParticleEmitter emitter = (ParticleEmitter)it.next();
        if (emitter.isEnabled()) {
          ParticlePool pool = (ParticlePool)particlesByEmitter.get(emitter);
          for (int i = 0; i < particles.length; i++) {
            if (particles[i].life > 0.0F) {
              particles[i].update(delta);
              pCount += 1;
            }
          }
        }
      }
    }
  }
  




  public int getParticleCount()
  {
    return pCount;
  }
  








  public Particle getNewParticle(ParticleEmitter emitter, float life)
  {
    ParticlePool pool = (ParticlePool)particlesByEmitter.get(emitter);
    ArrayList available = available;
    if (available.size() > 0)
    {
      Particle p = (Particle)available.remove(available.size() - 1);
      p.init(emitter, life);
      p.setImage(sprite);
      
      return p;
    }
    
    Log.warn("Ran out of particles (increase the limit)!");
    return dummy;
  }
  




  public void release(Particle particle)
  {
    if (particle != dummy)
    {
      ParticlePool pool = (ParticlePool)particlesByEmitter.get(particle.getEmitter());
      available.add(particle);
    }
  }
  




  public void releaseAll(ParticleEmitter emitter)
  {
    if (!particlesByEmitter.isEmpty())
    {
      Iterator it = particlesByEmitter.values().iterator();
      ParticlePool pool; int i; for (; it.hasNext(); 
          

          i < particles.length)
      {
        pool = (ParticlePool)it.next();
        i = 0; continue;
        if ((particles[i].inUse()) && 
          (particles[i].getEmitter() == emitter)) {
          particles[i].setLife(-1.0F);
          release(particles[i]);
        }
        i++;
      }
    }
  }
  













  public void moveAll(ParticleEmitter emitter, float x, float y)
  {
    ParticlePool pool = (ParticlePool)particlesByEmitter.get(emitter);
    for (int i = 0; i < particles.length; i++) {
      if (particles[i].inUse()) {
        particles[i].move(x, y);
      }
    }
  }
  








  public ParticleSystem duplicate()
    throws SlickException
  {
    for (int i = 0; i < emitters.size(); i++) {
      if (!(emitters.get(i) instanceof ConfigurableEmitter)) {
        throw new SlickException("Only systems contianing configurable emitters can be duplicated");
      }
    }
    
    ParticleSystem theCopy = null;
    try {
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      ParticleIO.saveConfiguredSystem(bout, this);
      ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
      theCopy = ParticleIO.loadConfiguredSystem(bin);
    } catch (IOException e) {
      Log.error("Failed to duplicate particle system");
      throw new SlickException("Unable to duplicated particle system", e);
    }
    
    return theCopy;
  }
}
