package org.newdawn.slick.command;




abstract class ControllerControl
  implements Control
{
  protected static final int BUTTON_EVENT = 0;
  

  protected static final int LEFT_EVENT = 1;
  

  protected static final int RIGHT_EVENT = 2;
  

  protected static final int UP_EVENT = 3;
  

  protected static final int DOWN_EVENT = 4;
  

  private int event;
  

  private int button;
  

  private int controllerNumber;
  


  protected ControllerControl(int controllerNumber, int event, int button)
  {
    this.event = event;
    this.button = button;
    this.controllerNumber = controllerNumber;
  }
  


  public boolean equals(Object o)
  {
    if (o == null)
      return false;
    if (!(o instanceof ControllerControl)) {
      return false;
    }
    ControllerControl c = (ControllerControl)o;
    
    return (controllerNumber == controllerNumber) && (event == event) && (button == button);
  }
  


  public int hashCode()
  {
    return event + button + controllerNumber;
  }
}
