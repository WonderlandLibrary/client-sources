package lunadevs.luna.events;


public class EventPlayerMeme
  extends Event
{
  private EventType type;
  
  public EventPlayerMeme(EventType type)
  {
    this.type = type;
  }
  
  public EventType getType()
  {
    return this.type;
  }
}
