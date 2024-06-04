package net.java.games.input;





final class RawMouseEvent
{
  private static final int WHEEL_SCALE = 120;
  



  private long millis;
  



  private int flags;
  



  private int button_flags;
  



  private int button_data;
  



  private long raw_buttons;
  



  private long last_x;
  



  private long last_y;
  



  private long extra_information;
  




  RawMouseEvent() {}
  




  public final void set(long millis, int flags, int button_flags, int button_data, long raw_buttons, long last_x, long last_y, long extra_information)
  {
    this.millis = millis;
    this.flags = flags;
    this.button_flags = button_flags;
    this.button_data = button_data;
    this.raw_buttons = raw_buttons;
    this.last_x = last_x;
    this.last_y = last_y;
    this.extra_information = extra_information;
  }
  
  public final void set(RawMouseEvent event) {
    set(millis, flags, button_flags, button_data, raw_buttons, last_x, last_y, extra_information);
  }
  
  public final int getWheelDelta() {
    return button_data / 120;
  }
  
  private final int getButtonData() {
    return button_data;
  }
  
  public final int getFlags() {
    return flags;
  }
  
  public final int getButtonFlags() {
    return button_flags;
  }
  
  public final int getLastX() {
    return (int)last_x;
  }
  
  public final int getLastY() {
    return (int)last_y;
  }
  
  public final long getRawButtons() {
    return raw_buttons;
  }
  
  public final long getNanos() {
    return millis * 1000000L;
  }
}
