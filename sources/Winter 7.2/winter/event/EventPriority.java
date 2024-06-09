package winter.event;

public enum EventPriority
{
  LOWEST("LOWEST", 0),  LOW("LOW", 1),  NORMAL("NORMAL", 2),  HIGH("HIGH", 3),  HIGHEST("HIGHEST", 4);
  
  private EventPriority(String s, int n) {}
  
  public int getPriority()
  {
    switch (this)
    {
    case HIGH: 
      return 0;
    case HIGHEST: 
      return 1;
    case LOW: 
      return 2;
    case LOWEST: 
      return 3;
    case NORMAL: 
      return 4;
    }
    return 2;
  }
  
  public EventPriority getPriorityFromInt(int priority)
  {
    switch (priority)
    {
    case 0: 
      return LOWEST;
    case 1: 
      return LOW;
    case 2: 
      return NORMAL;
    case 3: 
      return HIGH;
    case 4: 
      return HIGHEST;
    }
    return NORMAL;
  }
}
