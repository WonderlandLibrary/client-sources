package org.newdawn.slick.command;






public class KeyControl
  implements Control
{
  private int keycode;
  




  public KeyControl(int keycode)
  {
    this.keycode = keycode;
  }
  


  public boolean equals(Object o)
  {
    if ((o instanceof KeyControl)) {
      return keycode == keycode;
    }
    
    return false;
  }
  


  public int hashCode()
  {
    return keycode;
  }
}
