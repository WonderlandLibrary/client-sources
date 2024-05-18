package net.SliceClient.Utils;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.util.MathHelper;

public class ParticleGenerator
{
  private int count;
  private int width;
  private int height;
  private ArrayList<Particle> particles = new ArrayList();
  private Random random = new Random();
  private Timer timer = new Timer();
  int state = 0;
  int a = 255;
  int r = 255;
  int g = 0;
  int b = 0;
  
  public ParticleGenerator(int count, int width, int height)
  {
    this.count = count;
    this.width = width;
    this.height = height;
    for (int i = 0; i < count; i++) {
      particles.add(new Particle(random.nextInt(width), random.nextInt(height)));
    }
  }
  
  public void drawParticles()
  {
    for (Particle p : particles)
    {
      if (reset)
      {
        p.resetPosSize();
        reset = false;
      }
      p.draw();
    }
  }
  
  public class Particle
  {
    private int x;
    private int y;
    private int k;
    private float size;
    private boolean reset;
    private Random random = new Random();
    private Timer timer = new Timer();
    
    public Particle(int x, int y)
    {
      this.x = x;
      this.y = y;
      size = genRandom(1.0F, 3.0F);
    }
    
    public void draw()
    {
      if (size <= 0.0F) {
        reset = true;
      }
      size -= 0.05F;
      k += 1;
      int xx = (int)(MathHelper.cos(0.1F * (x + k)) * 5.0F);
      
      GuiUtils.drawBorderedCircle(x + xx, y, size, 0, 553648127);
    }
    
    public void resetPosSize()
    {
      x = random.nextInt(width);
      
      size = genRandom(1.0F, 3.0F);
    }
    
    public float genRandom(float min, float max)
    {
      return (float)(min + Math.random() * (max - min + 1.0F));
    }
  }
}
