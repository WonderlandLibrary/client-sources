package org.newdawn.slick.particles;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.FastTrig;
import org.newdawn.slick.util.Log;









public class ConfigurableEmitter
  implements ParticleEmitter
{
  private static String relativePath = "";
  





  public static void setRelativePath(String path)
  {
    if (!path.endsWith("/")) {
      path = path + "/";
    }
    relativePath = path;
  }
  

  public Range spawnInterval = new Range(100.0F, 100.0F, null);
  
  public Range spawnCount = new Range(5.0F, 5.0F, null);
  
  public Range initialLife = new Range(1000.0F, 1000.0F, null);
  
  public Range initialSize = new Range(10.0F, 10.0F, null);
  
  public Range xOffset = new Range(0.0F, 0.0F, null);
  
  public Range yOffset = new Range(0.0F, 0.0F, null);
  
  public RandomValue spread = new RandomValue(360.0F, null);
  
  public SimpleValue angularOffset = new SimpleValue(0.0F, null);
  
  public Range initialDistance = new Range(0.0F, 0.0F, null);
  
  public Range speed = new Range(50.0F, 50.0F, null);
  
  public SimpleValue growthFactor = new SimpleValue(0.0F, null);
  
  public SimpleValue gravityFactor = new SimpleValue(0.0F, null);
  
  public SimpleValue windFactor = new SimpleValue(0.0F, null);
  
  public Range length = new Range(1000.0F, 1000.0F, null);
  




  public ArrayList colors = new ArrayList();
  
  public SimpleValue startAlpha = new SimpleValue(255.0F, null);
  
  public SimpleValue endAlpha = new SimpleValue(0.0F, null);
  

  public LinearInterpolator alpha;
  

  public LinearInterpolator size;
  
  public LinearInterpolator velocity;
  
  public LinearInterpolator scaleY;
  
  public Range emitCount = new Range(1000.0F, 1000.0F, null);
  
  public int usePoints = 1;
  

  public boolean useOriented = false;
  



  public boolean useAdditive = false;
  

  public String name;
  
  public String imageName = "";
  

  private Image image;
  
  private boolean updateImage;
  
  private boolean enabled = true;
  
  private float x;
  
  private float y;
  
  private int nextSpawn = 0;
  

  private int timeout;
  

  private int particleCount;
  
  private ParticleSystem engine;
  
  private int leftToEmit;
  
  protected boolean wrapUp = false;
  
  protected boolean completed = false;
  

  protected boolean adjust;
  

  protected float adjustx;
  

  protected float adjusty;
  


  public ConfigurableEmitter(String name)
  {
    this.name = name;
    leftToEmit = ((int)emitCount.random());
    timeout = ((int)length.random());
    
    colors.add(new ColorRecord(0.0F, Color.white));
    colors.add(new ColorRecord(1.0F, Color.red));
    
    ArrayList curve = new ArrayList();
    curve.add(new Vector2f(0.0F, 0.0F));
    curve.add(new Vector2f(1.0F, 255.0F));
    alpha = new LinearInterpolator(curve, 0, 255);
    
    curve = new ArrayList();
    curve.add(new Vector2f(0.0F, 0.0F));
    curve.add(new Vector2f(1.0F, 255.0F));
    size = new LinearInterpolator(curve, 0, 255);
    
    curve = new ArrayList();
    curve.add(new Vector2f(0.0F, 0.0F));
    curve.add(new Vector2f(1.0F, 1.0F));
    velocity = new LinearInterpolator(curve, 0, 1);
    
    curve = new ArrayList();
    curve.add(new Vector2f(0.0F, 0.0F));
    curve.add(new Vector2f(1.0F, 1.0F));
    scaleY = new LinearInterpolator(curve, 0, 1);
  }
  








  public void setImageName(String imageName)
  {
    if (imageName.length() == 0) {
      imageName = null;
    }
    
    this.imageName = imageName;
    if (imageName == null) {
      image = null;
    } else {
      updateImage = true;
    }
  }
  




  public String getImageName()
  {
    return imageName;
  }
  


  public String toString()
  {
    return "[" + name + "]";
  }
  







  public void setPosition(float x, float y)
  {
    setPosition(x, y, true);
  }
  









  public void setPosition(float x, float y, boolean moveParticles)
  {
    if (moveParticles) {
      adjust = true;
      adjustx -= this.x - x;
      adjusty -= this.y - y;
    }
    this.x = x;
    this.y = y;
  }
  




  public float getX()
  {
    return x;
  }
  




  public float getY()
  {
    return y;
  }
  


  public boolean isEnabled()
  {
    return enabled;
  }
  


  public void setEnabled(boolean enabled)
  {
    this.enabled = enabled;
  }
  



  public void update(ParticleSystem system, int delta)
  {
    engine = system;
    
    if (!adjust) {
      adjustx = 0.0F;
      adjusty = 0.0F;
    } else {
      adjust = false;
    }
    
    if (updateImage) {
      updateImage = false;
      try {
        image = new Image(relativePath + imageName);
      } catch (SlickException e) {
        image = null;
        Log.error(e);
      }
    }
    
    if (((wrapUp) || 
      ((length.isEnabled()) && (timeout < 0)) || (
      (emitCount.isEnabled()) && (leftToEmit <= 0))) && 
      (particleCount == 0)) {
      completed = true;
    }
    
    particleCount = 0;
    
    if (wrapUp) {
      return;
    }
    
    if (length.isEnabled()) {
      if (timeout < 0) {
        return;
      }
      timeout -= delta;
    }
    if ((emitCount.isEnabled()) && 
      (leftToEmit <= 0)) {
      return;
    }
    

    nextSpawn -= delta;
    if (nextSpawn < 0) {
      nextSpawn = ((int)spawnInterval.random());
      int count = (int)spawnCount.random();
      
      for (int i = 0; i < count; i++) {
        Particle p = system.getNewParticle(this, initialLife.random());
        p.setSize(initialSize.random());
        p.setPosition(x + xOffset.random(), y + yOffset.random());
        p.setVelocity(0.0F, 0.0F, 0.0F);
        
        float dist = initialDistance.random();
        float power = speed.random();
        if ((dist != 0.0F) || (power != 0.0F)) {
          float s = spread.getValue(0.0F);
          float ang = s + angularOffset.getValue(0.0F) - spread
            .getValue() / 2.0F - 90.0F;
          float xa = (float)FastTrig.cos(Math.toRadians(ang)) * dist;
          float ya = (float)FastTrig.sin(Math.toRadians(ang)) * dist;
          p.adjustPosition(xa, ya);
          
          float xv = (float)FastTrig.cos(Math.toRadians(ang));
          float yv = (float)FastTrig.sin(Math.toRadians(ang));
          p.setVelocity(xv, yv, power * 0.001F);
        }
        
        if (image != null) {
          p.setImage(image);
        }
        
        ColorRecord start = (ColorRecord)colors.get(0);
        p.setColor(col.r, col.g, col.b, startAlpha
          .getValue(0.0F) / 255.0F);
        p.setUsePoint(usePoints);
        p.setOriented(useOriented);
        
        if (emitCount.isEnabled()) {
          leftToEmit -= 1;
          if (leftToEmit <= 0) {
            break;
          }
        }
      }
    }
  }
  



  public void updateParticle(Particle particle, int delta)
  {
    particleCount += 1;
    

    x += adjustx;
    y += adjusty;
    
    particle.adjustVelocity(windFactor.getValue(0.0F) * 5.0E-5F * delta, gravityFactor
      .getValue(0.0F) * 5.0E-5F * delta);
    
    float offset = particle.getLife() / particle.getOriginalLife();
    float inv = 1.0F - offset;
    float colOffset = 0.0F;
    float colInv = 1.0F;
    
    Color startColor = null;
    Color endColor = null;
    for (int i = 0; i < colors.size() - 1; i++) {
      ColorRecord rec1 = (ColorRecord)colors.get(i);
      ColorRecord rec2 = (ColorRecord)colors.get(i + 1);
      
      if ((inv >= pos) && (inv <= pos)) {
        startColor = col;
        endColor = col;
        
        float step = pos - pos;
        colOffset = inv - pos;
        colOffset /= step;
        colOffset = 1.0F - colOffset;
        colInv = 1.0F - colOffset;
      }
    }
    
    if (startColor != null) {
      float r = r * colOffset + r * colInv;
      float g = g * colOffset + g * colInv;
      float b = b * colOffset + b * colInv;
      float a;
      float a;
      if (alpha.isActive()) {
        a = alpha.getValue(inv) / 255.0F;
      } else {
        a = startAlpha.getValue(0.0F) / 255.0F * offset + 
          endAlpha.getValue(0.0F) / 255.0F * inv;
      }
      particle.setColor(r, g, b, a);
    }
    
    if (size.isActive()) {
      float s = size.getValue(inv);
      particle.setSize(s);
    } else {
      particle.adjustSize(delta * growthFactor.getValue(0.0F) * 0.001F);
    }
    
    if (velocity.isActive()) {
      particle.setSpeed(velocity.getValue(inv));
    }
    
    if (scaleY.isActive()) {
      particle.setScaleY(scaleY.getValue(inv));
    }
  }
  




  public boolean completed()
  {
    if (engine == null) {
      return false;
    }
    
    if (length.isEnabled()) {
      if (timeout > 0) {
        return false;
      }
      return completed;
    }
    if (emitCount.isEnabled()) {
      if (leftToEmit > 0) {
        return false;
      }
      return completed;
    }
    
    if (wrapUp) {
      return completed;
    }
    
    return false;
  }
  


  public void replay()
  {
    reset();
    nextSpawn = 0;
    leftToEmit = ((int)emitCount.random());
    timeout = ((int)length.random());
  }
  


  public void reset()
  {
    completed = false;
    if (engine != null) {
      engine.releaseAll(this);
    }
  }
  


  public void replayCheck()
  {
    if ((completed()) && 
      (engine != null) && 
      (engine.getParticleCount() == 0)) {
      replay();
    }
  }
  






  public ConfigurableEmitter duplicate()
  {
    ConfigurableEmitter theCopy = null;
    try {
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      ParticleIO.saveEmitter(bout, this);
      ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
      theCopy = ParticleIO.loadEmitter(bin);
    } catch (IOException e) {
      Log.error("Slick: ConfigurableEmitter.duplicate(): caught exception " + e.toString());
      return null;
    }
    return theCopy;
  }
  









  public class SimpleValue
    implements ConfigurableEmitter.Value
  {
    private float value;
    







    private float next;
    








    private SimpleValue(float value)
    {
      this.value = value;
    }
    




    public float getValue(float time)
    {
      return value;
    }
    





    public void setValue(float value)
    {
      this.value = value;
    }
  }
  





  public class RandomValue
    implements ConfigurableEmitter.Value
  {
    private float value;
    




    private RandomValue(float value)
    {
      this.value = value;
    }
    




    public float getValue(float time)
    {
      return (float)(Math.random() * value);
    }
    





    public void setValue(float value)
    {
      this.value = value;
    }
    




    public float getValue()
    {
      return value;
    }
  }
  



  public class LinearInterpolator
    implements ConfigurableEmitter.Value
  {
    private ArrayList curve;
    


    private boolean active;
    


    private int min;
    

    private int max;
    


    public LinearInterpolator(ArrayList curve, int min, int max)
    {
      this.curve = curve;
      this.min = min;
      this.max = max;
      active = false;
    }
    




    public void setCurve(ArrayList curve)
    {
      this.curve = curve;
    }
    




    public ArrayList getCurve()
    {
      return curve;
    }
    






    public float getValue(float t)
    {
      Vector2f p0 = (Vector2f)curve.get(0);
      for (int i = 1; i < curve.size(); i++) {
        Vector2f p1 = (Vector2f)curve.get(i);
        
        if ((t >= p0.getX()) && (t <= p1.getX()))
        {
          float st = (t - p0.getX()) / (
            p1.getX() - p0.getX());
          float r = p0.getY() + st * (
            p1.getY() - p0.getY());
          


          return r;
        }
        
        p0 = p1;
      }
      return 0.0F;
    }
    




    public boolean isActive()
    {
      return active;
    }
    




    public void setActive(boolean active)
    {
      this.active = active;
    }
    




    public int getMax()
    {
      return max;
    }
    




    public void setMax(int max)
    {
      this.max = max;
    }
    




    public int getMin()
    {
      return min;
    }
    




    public void setMin(int min)
    {
      this.min = min;
    }
  }
  





  public class ColorRecord
  {
    public float pos;
    



    public Color col;
    




    public ColorRecord(float pos, Color col)
    {
      this.pos = pos;
      this.col = col;
    }
  }
  







  public void addColorPoint(float pos, Color col)
  {
    colors.add(new ColorRecord(pos, col));
  }
  



  public class Range
  {
    private float max;
    

    private float min;
    

    private boolean enabled = false;
    







    private Range(float min, float max)
    {
      this.min = min;
      this.max = max;
    }
    




    public float random()
    {
      return (float)(min + Math.random() * (max - min));
    }
    




    public boolean isEnabled()
    {
      return enabled;
    }
    





    public void setEnabled(boolean enabled)
    {
      this.enabled = enabled;
    }
    




    public float getMax()
    {
      return max;
    }
    





    public void setMax(float max)
    {
      this.max = max;
    }
    




    public float getMin()
    {
      return min;
    }
    





    public void setMin(float min)
    {
      this.min = min;
    }
  }
  
  public boolean useAdditive() {
    return useAdditive;
  }
  
  public boolean isOriented() {
    return useOriented;
  }
  
  public boolean usePoints(ParticleSystem system) {
    return ((usePoints == 1) && (system.usePoints())) || 
      (usePoints == 2);
  }
  
  public Image getImage() {
    return image;
  }
  
  public void wrapUp() {
    wrapUp = true;
  }
  
  public void resetState() {
    wrapUp = false;
    replay();
  }
  
  public static abstract interface Value
  {
    public abstract float getValue(float paramFloat);
  }
}
