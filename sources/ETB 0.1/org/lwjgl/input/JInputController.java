package org.lwjgl.input;

import java.util.ArrayList;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Component.Identifier.Button;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import net.java.games.input.Rumbler;








































class JInputController
  implements Controller
{
  private net.java.games.input.Controller target;
  private int index;
  private ArrayList<Component> buttons = new ArrayList();
  
  private ArrayList<Component> axes = new ArrayList();
  
  private ArrayList<Component> pov = new ArrayList();
  
  private Rumbler[] rumblers;
  
  private boolean[] buttonState;
  
  private float[] povValues;
  
  private float[] axesValue;
  
  private float[] axesMax;
  
  private float[] deadZones;
  
  private int xaxis = -1;
  
  private int yaxis = -1;
  
  private int zaxis = -1;
  
  private int rxaxis = -1;
  
  private int ryaxis = -1;
  
  private int rzaxis = -1;
  






  JInputController(int index, net.java.games.input.Controller target)
  {
    this.target = target;
    this.index = index;
    
    Component[] sourceAxes = target.getComponents();
    
    for (Component sourceAxis : sourceAxes) {
      if ((sourceAxis.getIdentifier() instanceof Component.Identifier.Button)) {
        buttons.add(sourceAxis);
      } else if (sourceAxis.getIdentifier().equals(Component.Identifier.Axis.POV)) {
        pov.add(sourceAxis);
      } else {
        axes.add(sourceAxis);
      }
    }
    
    buttonState = new boolean[buttons.size()];
    povValues = new float[pov.size()];
    axesValue = new float[axes.size()];
    int buttonsCount = 0;
    int axesCount = 0;
    

    for (Component sourceAxis : sourceAxes) {
      if ((sourceAxis.getIdentifier() instanceof Component.Identifier.Button)) {
        buttonState[buttonsCount] = (sourceAxis.getPollData() != 0.0F ? 1 : false);
        buttonsCount++;
      } else if (!sourceAxis.getIdentifier().equals(Component.Identifier.Axis.POV))
      {


        axesValue[axesCount] = sourceAxis.getPollData();
        if (sourceAxis.getIdentifier().equals(Component.Identifier.Axis.X)) {
          xaxis = axesCount;
        }
        if (sourceAxis.getIdentifier().equals(Component.Identifier.Axis.Y)) {
          yaxis = axesCount;
        }
        if (sourceAxis.getIdentifier().equals(Component.Identifier.Axis.Z)) {
          zaxis = axesCount;
        }
        if (sourceAxis.getIdentifier().equals(Component.Identifier.Axis.RX)) {
          rxaxis = axesCount;
        }
        if (sourceAxis.getIdentifier().equals(Component.Identifier.Axis.RY)) {
          ryaxis = axesCount;
        }
        if (sourceAxis.getIdentifier().equals(Component.Identifier.Axis.RZ)) {
          rzaxis = axesCount;
        }
        
        axesCount++;
      }
    }
    
    axesMax = new float[axes.size()];
    deadZones = new float[axes.size()];
    
    for (int i = 0; i < axesMax.length; i++) {
      axesMax[i] = 1.0F;
      deadZones[i] = 0.05F;
    }
    
    rumblers = target.getRumblers();
  }
  


  public String getName()
  {
    String name = target.getName();
    return name;
  }
  


  public int getIndex()
  {
    return index;
  }
  


  public int getButtonCount()
  {
    return buttons.size();
  }
  


  public String getButtonName(int index)
  {
    return ((Component)buttons.get(index)).getName();
  }
  


  public boolean isButtonPressed(int index)
  {
    return buttonState[index];
  }
  


  public void poll()
  {
    target.poll();
    
    Event event = new Event();
    EventQueue queue = target.getEventQueue();
    
    while (queue.getNextEvent(event))
    {
      if (buttons.contains(event.getComponent())) {
        Component button = event.getComponent();
        int buttonIndex = buttons.indexOf(button);
        buttonState[buttonIndex] = (event.getValue() != 0.0F ? 1 : false);
        

        Controllers.addEvent(new ControllerEvent(this, event.getNanos(), 1, buttonIndex, buttonState[buttonIndex], false, false, 0.0F, 0.0F));
      }
      


      if (pov.contains(event.getComponent())) {
        Component povComponent = event.getComponent();
        int povIndex = pov.indexOf(povComponent);
        float prevX = getPovX();
        float prevY = getPovY();
        povValues[povIndex] = event.getValue();
        
        if (prevX != getPovX()) {
          Controllers.addEvent(new ControllerEvent(this, event.getNanos(), 3, 0, false, false));
        }
        if (prevY != getPovY()) {
          Controllers.addEvent(new ControllerEvent(this, event.getNanos(), 4, 0, false, false));
        }
      }
      

      if (axes.contains(event.getComponent())) {
        Component axis = event.getComponent();
        int axisIndex = axes.indexOf(axis);
        float value = axis.getPollData();
        float xaxisValue = 0.0F;
        float yaxisValue = 0.0F;
        

        if (Math.abs(value) < deadZones[axisIndex]) {
          value = 0.0F;
        }
        if (Math.abs(value) < axis.getDeadZone()) {
          value = 0.0F;
        }
        if (Math.abs(value) > axesMax[axisIndex]) {
          axesMax[axisIndex] = Math.abs(value);
        }
        

        value /= axesMax[axisIndex];
        
        if (axisIndex == xaxis) {
          xaxisValue = value;
        }
        if (axisIndex == yaxis) {
          yaxisValue = value;
        }
        

        Controllers.addEvent(new ControllerEvent(this, event.getNanos(), 2, axisIndex, false, axisIndex == xaxis, axisIndex == yaxis, xaxisValue, yaxisValue));
        
        axesValue[axisIndex] = value;
      }
    }
  }
  


  public int getAxisCount()
  {
    return axes.size();
  }
  


  public String getAxisName(int index)
  {
    return ((Component)axes.get(index)).getName();
  }
  


  public float getAxisValue(int index)
  {
    return axesValue[index];
  }
  


  public float getXAxisValue()
  {
    if (xaxis == -1) {
      return 0.0F;
    }
    
    return getAxisValue(xaxis);
  }
  


  public float getYAxisValue()
  {
    if (yaxis == -1) {
      return 0.0F;
    }
    
    return getAxisValue(yaxis);
  }
  


  public float getXAxisDeadZone()
  {
    if (xaxis == -1) {
      return 0.0F;
    }
    
    return getDeadZone(xaxis);
  }
  


  public float getYAxisDeadZone()
  {
    if (yaxis == -1) {
      return 0.0F;
    }
    
    return getDeadZone(yaxis);
  }
  


  public void setXAxisDeadZone(float zone)
  {
    setDeadZone(xaxis, zone);
  }
  


  public void setYAxisDeadZone(float zone)
  {
    setDeadZone(yaxis, zone);
  }
  


  public float getDeadZone(int index)
  {
    return deadZones[index];
  }
  


  public void setDeadZone(int index, float zone)
  {
    deadZones[index] = zone;
  }
  


  public float getZAxisValue()
  {
    if (zaxis == -1) {
      return 0.0F;
    }
    
    return getAxisValue(zaxis);
  }
  


  public float getZAxisDeadZone()
  {
    if (zaxis == -1) {
      return 0.0F;
    }
    
    return getDeadZone(zaxis);
  }
  


  public void setZAxisDeadZone(float zone)
  {
    setDeadZone(zaxis, zone);
  }
  


  public float getRXAxisValue()
  {
    if (rxaxis == -1) {
      return 0.0F;
    }
    
    return getAxisValue(rxaxis);
  }
  


  public float getRXAxisDeadZone()
  {
    if (rxaxis == -1) {
      return 0.0F;
    }
    
    return getDeadZone(rxaxis);
  }
  


  public void setRXAxisDeadZone(float zone)
  {
    setDeadZone(rxaxis, zone);
  }
  


  public float getRYAxisValue()
  {
    if (ryaxis == -1) {
      return 0.0F;
    }
    
    return getAxisValue(ryaxis);
  }
  


  public float getRYAxisDeadZone()
  {
    if (ryaxis == -1) {
      return 0.0F;
    }
    
    return getDeadZone(ryaxis);
  }
  


  public void setRYAxisDeadZone(float zone)
  {
    setDeadZone(ryaxis, zone);
  }
  


  public float getRZAxisValue()
  {
    if (rzaxis == -1) {
      return 0.0F;
    }
    
    return getAxisValue(rzaxis);
  }
  


  public float getRZAxisDeadZone()
  {
    if (rzaxis == -1) {
      return 0.0F;
    }
    
    return getDeadZone(rzaxis);
  }
  


  public void setRZAxisDeadZone(float zone)
  {
    setDeadZone(rzaxis, zone);
  }
  


  public float getPovX()
  {
    if (pov.size() == 0) {
      return 0.0F;
    }
    
    float value = povValues[0];
    
    if ((value == 0.875F) || (value == 0.125F) || (value == 1.0F))
    {

      return -1.0F;
    }
    if ((value == 0.625F) || (value == 0.375F) || (value == 0.5F))
    {

      return 1.0F;
    }
    
    return 0.0F;
  }
  


  public float getPovY()
  {
    if (pov.size() == 0) {
      return 0.0F;
    }
    
    float value = povValues[0];
    
    if ((value == 0.875F) || (value == 0.625F) || (value == 0.75F))
    {

      return 1.0F;
    }
    if ((value == 0.125F) || (value == 0.375F) || (value == 0.25F))
    {

      return -1.0F;
    }
    
    return 0.0F;
  }
  
  public int getRumblerCount() {
    return rumblers.length;
  }
  
  public String getRumblerName(int index) {
    return rumblers[index].getAxisName();
  }
  
  public void setRumblerStrength(int index, float strength) {
    rumblers[index].rumble(strength);
  }
}
