package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.lwjgl.LWJGLException;







































final class WindowsKeyboard
{
  private final byte[] key_down_buffer = new byte['Ā'];
  private final byte[] virt_key_down_buffer = new byte['Ā'];
  private final EventQueue event_queue = new EventQueue(18);
  private final ByteBuffer tmp_event = ByteBuffer.allocate(18);
  private boolean has_retained_event;
  private int retained_key_code;
  private byte retained_state;
  private int retained_char;
  private long retained_millis;
  private boolean retained_repeat;
  
  WindowsKeyboard() throws LWJGLException
  {}
  
  private static native boolean isWindowsNT();
  
  boolean isKeyDown(int lwjgl_keycode)
  {
    return key_down_buffer[lwjgl_keycode] == 1;
  }
  



  void poll(ByteBuffer keyDownBuffer)
  {
    if ((isKeyDown(42)) && (!isKeyPressedAsync(160))) {
      handleKey(16, 42, false, (byte)0, 0L, false);
    }
    if ((isKeyDown(54)) && (!isKeyPressedAsync(161))) {
      handleKey(16, 54, false, (byte)0, 0L, false);
    }
    int old_position = keyDownBuffer.position();
    keyDownBuffer.put(key_down_buffer);
    keyDownBuffer.position(old_position); }
  
  private static native int MapVirtualKey(int paramInt1, int paramInt2);
  
  private static native int ToUnicode(int paramInt1, int paramInt2, ByteBuffer paramByteBuffer, CharBuffer paramCharBuffer, int paramInt3, int paramInt4);
  
  private static native int ToAscii(int paramInt1, int paramInt2, ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2, int paramInt3);
  
  private static native int GetKeyboardState(ByteBuffer paramByteBuffer);
  private static native short GetKeyState(int paramInt);
  private static native short GetAsyncKeyState(int paramInt);
  private void putEvent(int keycode, byte state, int ch, long millis, boolean repeat) { tmp_event.clear();
    tmp_event.putInt(keycode).put(state).putInt(ch).putLong(millis * 1000000L).put((byte)(repeat ? 1 : 0));
    tmp_event.flip();
    event_queue.putEvent(tmp_event);
  }
  
  private static int translateExtended(int virt_key, int scan_code, boolean extended) {
    switch (virt_key) {
    case 16: 
      return scan_code == 54 ? 161 : 160;
    case 17: 
      return extended ? 163 : 162;
    case 18: 
      return extended ? 165 : 164;
    }
    return virt_key;
  }
  
  private void flushRetained()
  {
    if (has_retained_event) {
      has_retained_event = false;
      putEvent(retained_key_code, retained_state, retained_char, retained_millis, retained_repeat);
    }
  }
  
  private static boolean isKeyPressed(int state) {
    return (state & 0x1) == 1;
  }
  
  private static boolean isKeyPressedAsync(int virt_key) {
    return (GetAsyncKeyState(virt_key) & 0x8000) != 0;
  }
  



  void releaseAll(long millis)
  {
    for (int i = 0; i < virt_key_down_buffer.length; i++) {
      if (isKeyPressed(virt_key_down_buffer[i])) {
        handleKey(i, 0, false, (byte)0, millis, false);
      }
    }
  }
  
  void handleKey(int virt_key, int scan_code, boolean extended, byte event_state, long millis, boolean repeat) {
    virt_key = translateExtended(virt_key, scan_code, extended);
    if ((!repeat) && (isKeyPressed(event_state) == isKeyPressed(virt_key_down_buffer[virt_key]))) {
      return;
    }
    flushRetained();
    has_retained_event = true;
    int keycode = WindowsKeycodes.mapVirtualKeyToLWJGLCode(virt_key);
    if (keycode < key_down_buffer.length) {
      key_down_buffer[keycode] = event_state;
      repeat &= isKeyPressed(virt_key_down_buffer[virt_key]);
      virt_key_down_buffer[virt_key] = event_state;
    }
    retained_key_code = keycode;
    retained_state = event_state;
    retained_millis = millis;
    retained_char = 0;
    retained_repeat = repeat;
  }
  
  void handleChar(int event_char, long millis, boolean repeat) {
    if ((has_retained_event) && (retained_char != 0))
      flushRetained();
    if (!has_retained_event) {
      putEvent(0, (byte)0, event_char, millis, repeat);
    } else
      retained_char = event_char;
  }
  
  void read(ByteBuffer buffer) {
    flushRetained();
    event_queue.copyEvents(buffer);
  }
}
