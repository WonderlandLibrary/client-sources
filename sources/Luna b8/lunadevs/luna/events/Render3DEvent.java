package lunadevs.luna.events;

public class Render3DEvent extends Event
{
    public float partialTicks;
    
    public Render3DEvent(float partialTicks)
    {
	this.partialTicks = partialTicks;
    }
}
