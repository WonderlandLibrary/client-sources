package net.java.games.input;














































public abstract class Keyboard
  extends AbstractController
{
  protected Keyboard(String name, Component[] keys, Controller[] children, Rumbler[] rumblers)
  {
    super(name, keys, children, rumblers);
  }
  


  public Controller.Type getType()
  {
    return Controller.Type.KEYBOARD;
  }
  
  public final boolean isKeyDown(Component.Identifier.Key key_id) {
    Component key = getComponent(key_id);
    if (key == null)
      return false;
    return key.getPollData() != 0.0F;
  }
}
