package net.java.games.input;

import java.io.IOException;



























































final class RawDevice
{
  public static final int RI_MOUSE_LEFT_BUTTON_DOWN = 1;
  public static final int RI_MOUSE_LEFT_BUTTON_UP = 2;
  public static final int RI_MOUSE_RIGHT_BUTTON_DOWN = 4;
  public static final int RI_MOUSE_RIGHT_BUTTON_UP = 8;
  public static final int RI_MOUSE_MIDDLE_BUTTON_DOWN = 16;
  public static final int RI_MOUSE_MIDDLE_BUTTON_UP = 32;
  public static final int RI_MOUSE_BUTTON_1_DOWN = 1;
  public static final int RI_MOUSE_BUTTON_1_UP = 2;
  public static final int RI_MOUSE_BUTTON_2_DOWN = 4;
  public static final int RI_MOUSE_BUTTON_2_UP = 8;
  public static final int RI_MOUSE_BUTTON_3_DOWN = 16;
  public static final int RI_MOUSE_BUTTON_3_UP = 32;
  public static final int RI_MOUSE_BUTTON_4_DOWN = 64;
  public static final int RI_MOUSE_BUTTON_4_UP = 128;
  public static final int RI_MOUSE_BUTTON_5_DOWN = 256;
  public static final int RI_MOUSE_BUTTON_5_UP = 512;
  public static final int RI_MOUSE_WHEEL = 1024;
  public static final int MOUSE_MOVE_RELATIVE = 0;
  public static final int MOUSE_MOVE_ABSOLUTE = 1;
  public static final int MOUSE_VIRTUAL_DESKTOP = 2;
  public static final int MOUSE_ATTRIBUTES_CHANGED = 4;
  public static final int RIM_TYPEHID = 2;
  public static final int RIM_TYPEKEYBOARD = 1;
  public static final int RIM_TYPEMOUSE = 0;
  public static final int WM_KEYDOWN = 256;
  public static final int WM_KEYUP = 257;
  public static final int WM_SYSKEYDOWN = 260;
  public static final int WM_SYSKEYUP = 261;
  private final RawInputEventQueue queue;
  private final long handle;
  private final int type;
  private DataQueue keyboard_events;
  private DataQueue mouse_events;
  private DataQueue processed_keyboard_events;
  private DataQueue processed_mouse_events;
  private final boolean[] button_states = new boolean[5];
  
  private int wheel;
  
  private int relative_x;
  
  private int relative_y;
  
  private int last_x;
  private int last_y;
  private int event_relative_x;
  private int event_relative_y;
  private int event_last_x;
  private int event_last_y;
  private final boolean[] key_states = new boolean['ÿ'];
  
  public RawDevice(RawInputEventQueue queue, long handle, int type) {
    this.queue = queue;
    this.handle = handle;
    this.type = type;
    setBufferSize(32);
  }
  
  public final synchronized void addMouseEvent(long millis, int flags, int button_flags, int button_data, long raw_buttons, long last_x, long last_y, long extra_information)
  {
    if (mouse_events.hasRemaining()) {
      RawMouseEvent event = (RawMouseEvent)mouse_events.get();
      event.set(millis, flags, button_flags, button_data, raw_buttons, last_x, last_y, extra_information);
    }
  }
  
  public final synchronized void addKeyboardEvent(long millis, int make_code, int flags, int vkey, int message, long extra_information)
  {
    if (keyboard_events.hasRemaining()) {
      RawKeyboardEvent event = (RawKeyboardEvent)keyboard_events.get();
      event.set(millis, make_code, flags, vkey, message, extra_information);
    }
  }
  
  public final synchronized void pollMouse() {
    relative_x = (this.relative_y = this.wheel = 0);
    mouse_events.flip();
    while (mouse_events.hasRemaining()) {
      RawMouseEvent event = (RawMouseEvent)mouse_events.get();
      boolean has_update = processMouseEvent(event);
      if ((has_update) && (processed_mouse_events.hasRemaining())) {
        RawMouseEvent processed_event = (RawMouseEvent)processed_mouse_events.get();
        processed_event.set(event);
      }
    }
    mouse_events.compact();
  }
  
  public final synchronized void pollKeyboard() {
    keyboard_events.flip();
    while (keyboard_events.hasRemaining()) {
      RawKeyboardEvent event = (RawKeyboardEvent)keyboard_events.get();
      boolean has_update = processKeyboardEvent(event);
      if ((has_update) && (processed_keyboard_events.hasRemaining())) {
        RawKeyboardEvent processed_event = (RawKeyboardEvent)processed_keyboard_events.get();
        processed_event.set(event);
      }
    }
    keyboard_events.compact();
  }
  
  private final boolean updateButtonState(int button_id, int button_flags, int down_flag, int up_flag) {
    if (button_id >= button_states.length)
      return false;
    if ((button_flags & down_flag) != 0) {
      button_states[button_id] = true;
      return true; }
    if ((button_flags & up_flag) != 0) {
      button_states[button_id] = false;
      return true;
    }
    return false;
  }
  
  private final boolean processKeyboardEvent(RawKeyboardEvent event) {
    int message = event.getMessage();
    int vkey = event.getVKey();
    if (vkey >= key_states.length)
      return false;
    if ((message == 256) || (message == 260)) {
      key_states[vkey] = true;
      return true; }
    if ((message == 257) || (message == 261)) {
      key_states[vkey] = false;
      return true;
    }
    return false;
  }
  
  public final boolean isKeyDown(int vkey) {
    return key_states[vkey];
  }
  
  private final boolean processMouseEvent(RawMouseEvent event) {
    boolean has_update = false;
    int button_flags = event.getButtonFlags();
    has_update = (updateButtonState(0, button_flags, 1, 2)) || (has_update);
    has_update = (updateButtonState(1, button_flags, 4, 8)) || (has_update);
    has_update = (updateButtonState(2, button_flags, 16, 32)) || (has_update);
    has_update = (updateButtonState(3, button_flags, 64, 128)) || (has_update);
    has_update = (updateButtonState(4, button_flags, 256, 512)) || (has_update);
    int dx;
    int dy;
    if ((event.getFlags() & 0x1) != 0) {
      int dx = event.getLastX() - last_x;
      int dy = event.getLastY() - last_y;
      last_x = event.getLastX();
      last_y = event.getLastY();
    } else {
      dx = event.getLastX();
      dy = event.getLastY();
    }
    int dwheel = 0;
    if ((button_flags & 0x400) != 0)
      dwheel = event.getWheelDelta();
    relative_x += dx;
    relative_y += dy;
    wheel += dwheel;
    has_update = (dx != 0) || (dy != 0) || (dwheel != 0) || (has_update);
    return has_update;
  }
  
  public final int getWheel() {
    return wheel;
  }
  
  public final int getEventRelativeX() {
    return event_relative_x;
  }
  
  public final int getEventRelativeY() {
    return event_relative_y;
  }
  
  public final int getRelativeX() {
    return relative_x;
  }
  
  public final int getRelativeY() {
    return relative_y;
  }
  
  public final synchronized boolean getNextKeyboardEvent(RawKeyboardEvent event) {
    processed_keyboard_events.flip();
    if (!processed_keyboard_events.hasRemaining()) {
      processed_keyboard_events.compact();
      return false;
    }
    RawKeyboardEvent next_event = (RawKeyboardEvent)processed_keyboard_events.get();
    event.set(next_event);
    processed_keyboard_events.compact();
    return true;
  }
  
  public final synchronized boolean getNextMouseEvent(RawMouseEvent event) {
    processed_mouse_events.flip();
    if (!processed_mouse_events.hasRemaining()) {
      processed_mouse_events.compact();
      return false;
    }
    RawMouseEvent next_event = (RawMouseEvent)processed_mouse_events.get();
    if ((next_event.getFlags() & 0x1) != 0) {
      event_relative_x = (next_event.getLastX() - event_last_x);
      event_relative_y = (next_event.getLastY() - event_last_y);
      event_last_x = next_event.getLastX();
      event_last_y = next_event.getLastY();
    } else {
      event_relative_x = next_event.getLastX();
      event_relative_y = next_event.getLastY();
    }
    event.set(next_event);
    processed_mouse_events.compact();
    return true;
  }
  
  public final boolean getButtonState(int button_id) {
    if (button_id >= button_states.length)
      return false;
    return button_states[button_id];
  }
  
  public final void setBufferSize(int size) {
    keyboard_events = new DataQueue(size, RawKeyboardEvent.class);
    mouse_events = new DataQueue(size, RawMouseEvent.class);
    processed_keyboard_events = new DataQueue(size, RawKeyboardEvent.class);
    processed_mouse_events = new DataQueue(size, RawMouseEvent.class);
  }
  
  public final int getType() {
    return type;
  }
  
  public final long getHandle() {
    return handle;
  }
  
  public final String getName() throws IOException {
    return nGetName(handle);
  }
  
  private static final native String nGetName(long paramLong) throws IOException;
  
  public final RawDeviceInfo getInfo() throws IOException { return nGetInfo(this, handle); }
  
  private static final native RawDeviceInfo nGetInfo(RawDevice paramRawDevice, long paramLong)
    throws IOException;
}
