package net.java.games.input;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

























public class WinTabComponent
  extends AbstractComponent
{
  public static final int XAxis = 1;
  public static final int YAxis = 2;
  public static final int ZAxis = 3;
  public static final int NPressureAxis = 4;
  public static final int TPressureAxis = 5;
  public static final int OrientationAxis = 6;
  public static final int RotationAxis = 7;
  private int min;
  private int max;
  protected float lastKnownValue;
  private boolean analog;
  
  protected WinTabComponent(WinTabContext context, int parentDevice, String name, Component.Identifier id, int min, int max)
  {
    super(name, id);
    this.min = min;
    this.max = max;
    analog = true;
  }
  
  protected WinTabComponent(WinTabContext context, int parentDevice, String name, Component.Identifier id) {
    super(name, id);
    min = 0;
    max = 1;
    analog = false;
  }
  
  protected float poll() throws IOException {
    return lastKnownValue;
  }
  
  public boolean isAnalog() {
    return analog;
  }
  
  public boolean isRelative()
  {
    return false;
  }
  
  public static List createComponents(WinTabContext context, int parentDevice, int axisId, int[] axisRanges) {
    List components = new ArrayList();
    Component.Identifier id;
    switch (axisId) {
    case 1: 
      id = Component.Identifier.Axis.X;
      components.add(new WinTabComponent(context, parentDevice, id.getName(), id, axisRanges[0], axisRanges[1]));
      break;
    case 2: 
      id = Component.Identifier.Axis.Y;
      components.add(new WinTabComponent(context, parentDevice, id.getName(), id, axisRanges[0], axisRanges[1]));
      break;
    case 3: 
      id = Component.Identifier.Axis.Z;
      components.add(new WinTabComponent(context, parentDevice, id.getName(), id, axisRanges[0], axisRanges[1]));
      break;
    case 4: 
      id = Component.Identifier.Axis.X_FORCE;
      components.add(new WinTabComponent(context, parentDevice, id.getName(), id, axisRanges[0], axisRanges[1]));
      break;
    case 5: 
      id = Component.Identifier.Axis.Y_FORCE;
      components.add(new WinTabComponent(context, parentDevice, id.getName(), id, axisRanges[0], axisRanges[1]));
      break;
    case 6: 
      id = Component.Identifier.Axis.RX;
      components.add(new WinTabComponent(context, parentDevice, id.getName(), id, axisRanges[0], axisRanges[1]));
      id = Component.Identifier.Axis.RY;
      components.add(new WinTabComponent(context, parentDevice, id.getName(), id, axisRanges[2], axisRanges[3]));
      id = Component.Identifier.Axis.RZ;
      components.add(new WinTabComponent(context, parentDevice, id.getName(), id, axisRanges[4], axisRanges[5]));
      break;
    case 7: 
      id = Component.Identifier.Axis.RX;
      components.add(new WinTabComponent(context, parentDevice, id.getName(), id, axisRanges[0], axisRanges[1]));
      id = Component.Identifier.Axis.RY;
      components.add(new WinTabComponent(context, parentDevice, id.getName(), id, axisRanges[2], axisRanges[3]));
      id = Component.Identifier.Axis.RZ;
      components.add(new WinTabComponent(context, parentDevice, id.getName(), id, axisRanges[4], axisRanges[5]));
    }
    
    
    return components;
  }
  
  public static Collection createButtons(WinTabContext context, int deviceIndex, int numberOfButtons) {
    List buttons = new ArrayList();
    

    for (int i = 0; i < numberOfButtons; i++) {
      try {
        Class buttonIdClass = Component.Identifier.Button.class;
        Field idField = buttonIdClass.getField("_" + i);
        Component.Identifier id = (Component.Identifier)idField.get(null);
        buttons.add(new WinTabButtonComponent(context, deviceIndex, id.getName(), id, i));
      }
      catch (SecurityException e) {
        e.printStackTrace();
      }
      catch (NoSuchFieldException e) {
        e.printStackTrace();
      }
      catch (IllegalArgumentException e) {
        e.printStackTrace();
      }
      catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    
    return buttons;
  }
  
  public Event processPacket(WinTabPacket packet)
  {
    float newValue = lastKnownValue;
    
    if (getIdentifier() == Component.Identifier.Axis.X) {
      newValue = normalise(PK_X);
    }
    if (getIdentifier() == Component.Identifier.Axis.Y) {
      newValue = normalise(PK_Y);
    }
    if (getIdentifier() == Component.Identifier.Axis.Z) {
      newValue = normalise(PK_Z);
    }
    if (getIdentifier() == Component.Identifier.Axis.X_FORCE) {
      newValue = normalise(PK_NORMAL_PRESSURE);
    }
    if (getIdentifier() == Component.Identifier.Axis.Y_FORCE) {
      newValue = normalise(PK_TANGENT_PRESSURE);
    }
    if (getIdentifier() == Component.Identifier.Axis.RX) {
      newValue = normalise(PK_ORIENTATION_ALT);
    }
    if (getIdentifier() == Component.Identifier.Axis.RY) {
      newValue = normalise(PK_ORIENTATION_AZ);
    }
    if (getIdentifier() == Component.Identifier.Axis.RZ) {
      newValue = normalise(PK_ORIENTATION_TWIST);
    }
    if (newValue != getPollData()) {
      lastKnownValue = newValue;
      

      Event newEvent = new Event();
      newEvent.set(this, newValue, PK_TIME * 1000L);
      return newEvent;
    }
    
    return null;
  }
  
  private float normalise(float value) {
    if (max == min) return value;
    float bottom = max - min;
    return (value - min) / bottom;
  }
  
  public static Collection createCursors(WinTabContext context, int deviceIndex, String[] cursorNames)
  {
    List cursors = new ArrayList();
    
    for (int i = 0; i < cursorNames.length; i++) { Component.Identifier id;
      Component.Identifier id; if (cursorNames[i].matches("Puck")) {
        id = Component.Identifier.Button.TOOL_FINGER; } else { Component.Identifier id;
        if (cursorNames[i].matches("Eraser.*")) {
          id = Component.Identifier.Button.TOOL_RUBBER;
        } else
          id = Component.Identifier.Button.TOOL_PEN;
      }
      cursors.add(new WinTabCursorComponent(context, deviceIndex, id.getName(), id, i));
    }
    
    return cursors;
  }
}
