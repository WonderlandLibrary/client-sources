package org.lwjgl.input;






class ControllerEvent
{
  public static final int BUTTON = 1;
  




  public static final int AXIS = 2;
  



  public static final int POVX = 3;
  



  public static final int POVY = 4;
  



  private Controller source;
  



  private int index;
  



  private int type;
  



  private boolean buttonState;
  



  private boolean xaxis;
  



  private boolean yaxis;
  



  private long timeStamp;
  



  private float xaxisValue;
  



  private float yaxisValue;
  




  ControllerEvent(Controller source, long timeStamp, int type, int index, boolean xaxis, boolean yaxis)
  {
    this(source, timeStamp, type, index, false, xaxis, yaxis, 0.0F, 0.0F);
  }
  












  ControllerEvent(Controller source, long timeStamp, int type, int index, boolean buttonState, boolean xaxis, boolean yaxis, float xaxisValue, float yaxisValue)
  {
    this.source = source;
    this.timeStamp = timeStamp;
    this.type = type;
    this.index = index;
    this.buttonState = buttonState;
    this.xaxis = xaxis;
    this.yaxis = yaxis;
    this.xaxisValue = xaxisValue;
    this.yaxisValue = yaxisValue;
  }
  





  public long getTimeStamp()
  {
    return timeStamp;
  }
  




  public Controller getSource()
  {
    return source;
  }
  




  public int getControlIndex()
  {
    return index;
  }
  




  public boolean isButton()
  {
    return type == 1;
  }
  




  public boolean getButtonState()
  {
    return buttonState;
  }
  




  public boolean isAxis()
  {
    return type == 2;
  }
  




  public boolean isPovY()
  {
    return type == 4;
  }
  




  public boolean isPovX()
  {
    return type == 3;
  }
  




  public boolean isXAxis()
  {
    return xaxis;
  }
  




  public boolean isYAxis()
  {
    return yaxis;
  }
  




  public float getXAxisValue()
  {
    return xaxisValue;
  }
  




  public float getYAxisValue()
  {
    return yaxisValue;
  }
  


  public String toString()
  {
    return "[" + source + " type=" + type + " xaxis=" + xaxis + " yaxis=" + yaxis + "]";
  }
}
