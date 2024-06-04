package org.lwjgl.util.jinput;

import java.io.IOException;
import net.java.games.input.AbstractComponent;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Component.Identifier.Button;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.Rumbler;


































final class LWJGLMouse
  extends net.java.games.input.Mouse
{
  private static final int EVENT_X = 1;
  private static final int EVENT_Y = 2;
  private static final int EVENT_WHEEL = 3;
  private static final int EVENT_BUTTON = 4;
  private static final int EVENT_DONE = 5;
  private int event_state = 5;
  
  LWJGLMouse() {
    super("LWJGLMouse", createComponents(), new Controller[0], new Rumbler[0]);
  }
  
  private static Component[] createComponents() {
    return new Component[] { new Axis(Component.Identifier.Axis.X), new Axis(Component.Identifier.Axis.Y), new Axis(Component.Identifier.Axis.Z), new Button(Component.Identifier.Button.LEFT), new Button(Component.Identifier.Button.MIDDLE), new Button(Component.Identifier.Button.RIGHT) };
  }
  



  public synchronized void pollDevice()
    throws IOException
  {
    if (!org.lwjgl.input.Mouse.isCreated())
      return;
    org.lwjgl.input.Mouse.poll();
    for (int i = 0; i < 3; i++)
      setButtonState(i);
  }
  
  private Button map(int lwjgl_button) {
    switch (lwjgl_button) {
    case 0: 
      return (Button)getLeft();
    case 1: 
      return (Button)getRight();
    case 2: 
      return (Button)getMiddle();
    }
    return null;
  }
  
  private void setButtonState(int lwjgl_button)
  {
    Button button = map(lwjgl_button);
    if (button != null)
      button.setValue(org.lwjgl.input.Mouse.isButtonDown(lwjgl_button) ? 1.0F : 0.0F);
  }
  
  protected synchronized boolean getNextDeviceEvent(Event event) throws IOException {
    if (!org.lwjgl.input.Mouse.isCreated())
      return false;
    for (;;) {
      long nanos = org.lwjgl.input.Mouse.getEventNanoseconds();
      switch (event_state) {
      case 1: 
        event_state = 2;
        int dx = org.lwjgl.input.Mouse.getEventDX();
        if (dx != 0) {
          event.set(getX(), dx, nanos);
          return true;
        }
        break;
      case 2: 
        event_state = 3;
        


        int dy = -org.lwjgl.input.Mouse.getEventDY();
        if (dy != 0) {
          event.set(getY(), dy, nanos);
          return true;
        }
        break;
      case 3: 
        event_state = 4;
        int dwheel = org.lwjgl.input.Mouse.getEventDWheel();
        if (dwheel != 0) {
          event.set(getWheel(), dwheel, nanos);
          return true;
        }
        break;
      case 4: 
        event_state = 5;
        int lwjgl_button = org.lwjgl.input.Mouse.getEventButton();
        if (lwjgl_button != -1) {
          Button button = map(lwjgl_button);
          if (button != null) {
            event.set(button, org.lwjgl.input.Mouse.getEventButtonState() ? 1.0F : 0.0F, nanos);
            return true;
          } }
        break;
      
      case 5: 
        if (!org.lwjgl.input.Mouse.next())
          return false;
        event_state = 1;
      }
    }
  }
  
  static final class Axis
    extends AbstractComponent
  {
    Axis(Component.Identifier.Axis axis_id)
    {
      super(axis_id);
    }
    
    public boolean isRelative() {
      return true;
    }
    
    protected float poll() throws IOException {
      return 0.0F;
    }
    
    public boolean isAnalog() {
      return true;
    }
  }
  
  static final class Button extends AbstractComponent {
    private float value;
    
    Button(Component.Identifier.Button button_id) {
      super(button_id);
    }
    
    void setValue(float value) {
      this.value = value;
    }
    
    protected float poll() throws IOException {
      return value;
    }
    
    public boolean isRelative() {
      return false;
    }
    
    public boolean isAnalog() {
      return false;
    }
  }
}
