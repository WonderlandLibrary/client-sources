package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;









































final class WindowsMouse
{
  private final long hwnd;
  private final int mouse_button_count;
  private final boolean has_wheel;
  private final EventQueue event_queue = new EventQueue(22);
  
  private final ByteBuffer mouse_event = ByteBuffer.allocate(22);
  private final Object blank_cursor;
  private boolean mouse_grabbed;
  private byte[] button_states;
  private int accum_dx;
  private int accum_dy;
  private int accum_dwheel;
  private int last_x;
  private int last_y;
  
  WindowsMouse(long hwnd) throws LWJGLException
  {
    this.hwnd = hwnd;
    mouse_button_count = Math.min(5, WindowsDisplay.getSystemMetrics(43));
    has_wheel = (WindowsDisplay.getSystemMetrics(75) != 0);
    blank_cursor = createBlankCursor();
    button_states = new byte[mouse_button_count];
  }
  
  private Object createBlankCursor() throws LWJGLException {
    int width = WindowsDisplay.getSystemMetrics(13);
    int height = WindowsDisplay.getSystemMetrics(14);
    IntBuffer pixels = BufferUtils.createIntBuffer(width * height);
    return WindowsDisplay.doCreateCursor(width, height, 0, 0, 1, pixels, null);
  }
  
  public boolean isGrabbed() {
    return mouse_grabbed;
  }
  
  public boolean hasWheel() {
    return has_wheel;
  }
  
  public int getButtonCount() {
    return mouse_button_count;
  }
  
  public void poll(IntBuffer coord_buffer, ByteBuffer buttons, WindowsDisplay display) {
    for (int i = 0; i < coord_buffer.remaining(); i++)
      coord_buffer.put(coord_buffer.position() + i, 0);
    int num_buttons = mouse_button_count;
    coord_buffer.put(coord_buffer.position() + 2, accum_dwheel);
    if (num_buttons > button_states.length)
      num_buttons = button_states.length;
    for (int j = 0; j < num_buttons; j++) {
      buttons.put(buttons.position() + j, button_states[j]);
    }
    if (isGrabbed()) {
      coord_buffer.put(coord_buffer.position() + 0, accum_dx);
      coord_buffer.put(coord_buffer.position() + 1, accum_dy);
      
      if ((display.isActive()) && (display.isVisible()) && ((accum_dx != 0) || (accum_dy != 0)))
        WindowsDisplay.centerCursor(hwnd);
    } else {
      coord_buffer.put(coord_buffer.position() + 0, last_x);
      coord_buffer.put(coord_buffer.position() + 1, last_y);
    }
    accum_dx = (this.accum_dy = this.accum_dwheel = 0);
  }
  
  private void putMouseEventWithCoords(byte button, byte state, int coord1, int coord2, int dz, long nanos) {
    mouse_event.clear();
    mouse_event.put(button).put(state).putInt(coord1).putInt(coord2).putInt(dz).putLong(nanos);
    mouse_event.flip();
    event_queue.putEvent(mouse_event);
  }
  
  private void putMouseEvent(byte button, byte state, int dz, long nanos) {
    if (mouse_grabbed) {
      putMouseEventWithCoords(button, state, 0, 0, dz, nanos);
    } else
      putMouseEventWithCoords(button, state, last_x, last_y, dz, nanos);
  }
  
  public void read(ByteBuffer buffer) {
    event_queue.copyEvents(buffer);
  }
  
  public Object getBlankCursor() {
    return blank_cursor;
  }
  
  public void grab(boolean grab) {
    mouse_grabbed = grab;
    event_queue.clearEvents();
  }
  
  public void handleMouseScrolled(int event_dwheel, long millis) {
    accum_dwheel += event_dwheel;
    putMouseEvent((byte)-1, (byte)0, event_dwheel, millis * 1000000L);
  }
  
  public void setPosition(int x, int y) {
    last_x = x;
    last_y = y;
  }
  
  public void destroy() {
    WindowsDisplay.doDestroyCursor(blank_cursor);
  }
  
  public void handleMouseMoved(int x, int y, long millis) {
    int dx = x - last_x;
    int dy = y - last_y;
    if ((dx != 0) || (dy != 0)) {
      accum_dx += dx;
      accum_dy += dy;
      last_x = x;
      last_y = y;
      long nanos = millis * 1000000L;
      if (mouse_grabbed) {
        putMouseEventWithCoords((byte)-1, (byte)0, dx, dy, 0, nanos);
      } else {
        putMouseEventWithCoords((byte)-1, (byte)0, x, y, 0, nanos);
      }
    }
  }
  
  public void handleMouseButton(byte button, byte state, long millis) {
    putMouseEvent(button, state, 0, millis * 1000000L);
    if (button < button_states.length) {
      button_states[button] = (state != 0 ? 1 : 0);
    }
  }
}
