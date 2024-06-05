package zamorozka.ui;

public class Render3DEvent
{
	  private static float ticks;
	  
	  public Render3DEvent(float ticks) { this.ticks = ticks; }


	  
	  public static float getTicks() { return ticks; }


	  
	  public void setTicks(float ticks) { this.ticks = ticks; }
	}
