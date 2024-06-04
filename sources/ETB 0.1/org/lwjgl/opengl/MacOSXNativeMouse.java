package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;




















































final class MacOSXNativeMouse
  extends EventQueue
{
  private static final int WHEEL_SCALE = 120;
  private static final int NUM_BUTTONS = 3;
  private ByteBuffer window_handle;
  private MacOSXDisplay display;
  private boolean grabbed;
  private float accum_dx;
  private float accum_dy;
  private int accum_dz;
  private float last_x;
  private float last_y;
  private boolean saved_control_state;
  private final ByteBuffer event = ByteBuffer.allocate(22);
  private IntBuffer delta_buffer = BufferUtils.createIntBuffer(2);
  
  private int skip_event;
  private final byte[] buttons = new byte[3];
  
  MacOSXNativeMouse(MacOSXDisplay display, ByteBuffer window_handle) {
    super(22);
    this.display = display;
    this.window_handle = window_handle;
  }
  
  private native void nSetCursorPosition(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2);
  
  public static native void nGrabMouse(boolean paramBoolean);
  
  private native void nRegisterMouseListener(ByteBuffer paramByteBuffer);
  
  private native void nUnregisterMouseListener(ByteBuffer paramByteBuffer);
  
  private static native long nCreateCursor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, IntBuffer paramIntBuffer1, int paramInt6, IntBuffer paramIntBuffer2, int paramInt7) throws LWJGLException;
  
  private static native void nDestroyCursor(long paramLong);
  
  private static native void nSetCursor(long paramLong) throws LWJGLException;
  
  public synchronized void register() {
    nRegisterMouseListener(window_handle);
  }
  
  public static long createCursor(int width, int height, int xHotspot, int yHotspot, int numImages, IntBuffer images, IntBuffer delays) throws LWJGLException {
    try {
      return nCreateCursor(width, height, xHotspot, yHotspot, numImages, images, images.position(), delays, delays != null ? delays.position() : -1);
    } catch (LWJGLException e) {
      throw e;
    }
  }
  
  public static void destroyCursor(long cursor_handle) {
    nDestroyCursor(cursor_handle);
  }
  
  public static void setCursor(long cursor_handle) throws LWJGLException {
    try {
      nSetCursor(cursor_handle);
    } catch (LWJGLException e) {
      throw e;
    }
  }
  
  public synchronized void setCursorPosition(int x, int y) {
    nSetCursorPosition(window_handle, x, y);
  }
  
  public synchronized void unregister() {
    nUnregisterMouseListener(window_handle);
  }
  
  public synchronized void setGrabbed(boolean grabbed) {
    this.grabbed = grabbed;
    nGrabMouse(grabbed);
    skip_event = 1;
    accum_dx = (this.accum_dy = 0.0F);
  }
  
  public synchronized boolean isGrabbed() {
    return grabbed;
  }
  
  protected void resetCursorToCenter() {
    clearEvents();
    accum_dx = (this.accum_dy = 0.0F);
    if (display != null) {
      last_x = (display.getWidth() / 2);
      last_y = (display.getHeight() / 2);
    }
  }
  
  private void putMouseEvent(byte button, byte state, int dz, long nanos) {
    if (grabbed) {
      putMouseEventWithCoords(button, state, 0, 0, dz, nanos);
    } else
      putMouseEventWithCoords(button, state, (int)last_x, (int)last_y, dz, nanos);
  }
  
  protected void putMouseEventWithCoords(byte button, byte state, int coord1, int coord2, int dz, long nanos) {
    event.clear();
    event.put(button).put(state).putInt(coord1).putInt(coord2).putInt(dz).putLong(nanos);
    event.flip();
    putEvent(event);
  }
  
  public synchronized void poll(IntBuffer coord_buffer, ByteBuffer buttons_buffer) {
    if (grabbed) {
      coord_buffer.put(0, (int)accum_dx);
      coord_buffer.put(1, (int)accum_dy);
    } else {
      coord_buffer.put(0, (int)last_x);
      coord_buffer.put(1, (int)last_y);
    }
    coord_buffer.put(2, accum_dz);
    accum_dx = (this.accum_dy = this.accum_dz = 0);
    int old_position = buttons_buffer.position();
    buttons_buffer.put(buttons, 0, buttons.length);
    buttons_buffer.position(old_position);
  }
  
  private void setCursorPos(float x, float y, long nanos) {
    if (grabbed)
      return;
    float dx = x - last_x;
    float dy = y - last_y;
    addDelta(dx, dy);
    last_x = x;
    last_y = y;
    putMouseEventWithCoords((byte)-1, (byte)0, (int)x, (int)y, 0, nanos);
  }
  
  protected void addDelta(float dx, float dy) {
    accum_dx += dx;
    accum_dy += -dy;
  }
  
  public synchronized void setButton(int button, int state, long nanos) {
    buttons[button] = ((byte)state);
    putMouseEvent((byte)button, (byte)state, 0, nanos);
  }
  
  public synchronized void mouseMoved(float x, float y, float dx, float dy, float dz, long nanos) {
    if (skip_event > 0) {
      skip_event -= 1;
      if (skip_event == 0) {
        last_x = x;
        last_y = y;
      }
      return;
    }
    
    if (dz != 0.0F)
    {
      if (dy == 0.0F) { dy = dx;
      }
      int wheel_amount = (int)(dy * 120.0F);
      accum_dz += wheel_amount;
      putMouseEvent((byte)-1, (byte)0, wheel_amount, nanos);
    }
    else if (grabbed) {
      if ((dx != 0.0F) || (dy != 0.0F)) {
        putMouseEventWithCoords((byte)-1, (byte)0, (int)dx, (int)-dy, 0, nanos);
        addDelta(dx, dy);
      }
    } else {
      setCursorPos(x, y, nanos);
    }
  }
}
