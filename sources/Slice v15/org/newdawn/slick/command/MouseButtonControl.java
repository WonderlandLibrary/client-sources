package org.newdawn.slick.command;





public class MouseButtonControl
  implements Control
{
  private int button;
  




  public MouseButtonControl(int button)
  {
    this.button = button;
  }
  


  public boolean equals(Object o)
  {
    if ((o instanceof MouseButtonControl))
    {
      return button == button;
    }
    
    return false;
  }
  


  public int hashCode()
  {
    return button;
  }
}
