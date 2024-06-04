package org.lwjgl.opengl;

import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.HashMap;





































final class MacOSXNativeKeyboard
  extends EventQueue
{
  private final byte[] key_states = new byte['Ā'];
  

  private final ByteBuffer event = ByteBuffer.allocate(18);
  
  private ByteBuffer window_handle;
  
  private boolean has_deferred_event;
  private long deferred_nanos;
  private int deferred_key_code;
  private byte deferred_key_state;
  private int deferred_character;
  private HashMap<Short, Integer> nativeToLwjglMap;
  
  MacOSXNativeKeyboard(ByteBuffer window_handle)
  {
    super(18);
    nativeToLwjglMap = new HashMap();
    initKeyboardMappings();
    this.window_handle = window_handle;
  }
  
  private native void nRegisterKeyListener(ByteBuffer paramByteBuffer);
  
  private native void nUnregisterKeyListener(ByteBuffer paramByteBuffer);
  
  private void initKeyboardMappings()
  {
    nativeToLwjglMap.put(Short.valueOf((short)29), Integer.valueOf(11));
    nativeToLwjglMap.put(Short.valueOf((short)18), Integer.valueOf(2));
    nativeToLwjglMap.put(Short.valueOf((short)19), Integer.valueOf(3));
    nativeToLwjglMap.put(Short.valueOf((short)20), Integer.valueOf(4));
    nativeToLwjglMap.put(Short.valueOf((short)21), Integer.valueOf(5));
    nativeToLwjglMap.put(Short.valueOf((short)23), Integer.valueOf(6));
    nativeToLwjglMap.put(Short.valueOf((short)22), Integer.valueOf(7));
    nativeToLwjglMap.put(Short.valueOf((short)26), Integer.valueOf(8));
    nativeToLwjglMap.put(Short.valueOf((short)28), Integer.valueOf(9));
    nativeToLwjglMap.put(Short.valueOf((short)25), Integer.valueOf(10));
    nativeToLwjglMap.put(Short.valueOf((short)0), Integer.valueOf(30));
    nativeToLwjglMap.put(Short.valueOf((short)11), Integer.valueOf(48));
    nativeToLwjglMap.put(Short.valueOf((short)8), Integer.valueOf(46));
    nativeToLwjglMap.put(Short.valueOf((short)2), Integer.valueOf(32));
    nativeToLwjglMap.put(Short.valueOf((short)14), Integer.valueOf(18));
    nativeToLwjglMap.put(Short.valueOf((short)3), Integer.valueOf(33));
    nativeToLwjglMap.put(Short.valueOf((short)5), Integer.valueOf(34));
    nativeToLwjglMap.put(Short.valueOf((short)4), Integer.valueOf(35));
    nativeToLwjglMap.put(Short.valueOf((short)34), Integer.valueOf(23));
    nativeToLwjglMap.put(Short.valueOf((short)38), Integer.valueOf(36));
    nativeToLwjglMap.put(Short.valueOf((short)40), Integer.valueOf(37));
    nativeToLwjglMap.put(Short.valueOf((short)37), Integer.valueOf(38));
    nativeToLwjglMap.put(Short.valueOf((short)46), Integer.valueOf(50));
    nativeToLwjglMap.put(Short.valueOf((short)45), Integer.valueOf(49));
    nativeToLwjglMap.put(Short.valueOf((short)31), Integer.valueOf(24));
    nativeToLwjglMap.put(Short.valueOf((short)35), Integer.valueOf(25));
    nativeToLwjglMap.put(Short.valueOf((short)12), Integer.valueOf(16));
    nativeToLwjglMap.put(Short.valueOf((short)15), Integer.valueOf(19));
    nativeToLwjglMap.put(Short.valueOf((short)1), Integer.valueOf(31));
    nativeToLwjglMap.put(Short.valueOf((short)17), Integer.valueOf(20));
    nativeToLwjglMap.put(Short.valueOf((short)32), Integer.valueOf(22));
    nativeToLwjglMap.put(Short.valueOf((short)9), Integer.valueOf(47));
    nativeToLwjglMap.put(Short.valueOf((short)13), Integer.valueOf(17));
    nativeToLwjglMap.put(Short.valueOf((short)7), Integer.valueOf(45));
    nativeToLwjglMap.put(Short.valueOf((short)16), Integer.valueOf(21));
    nativeToLwjglMap.put(Short.valueOf((short)6), Integer.valueOf(44));
    
    nativeToLwjglMap.put(Short.valueOf((short)42), Integer.valueOf(43));
    nativeToLwjglMap.put(Short.valueOf((short)43), Integer.valueOf(51));
    nativeToLwjglMap.put(Short.valueOf((short)24), Integer.valueOf(13));
    nativeToLwjglMap.put(Short.valueOf((short)33), Integer.valueOf(26));
    nativeToLwjglMap.put(Short.valueOf((short)27), Integer.valueOf(12));
    nativeToLwjglMap.put(Short.valueOf((short)39), Integer.valueOf(40));
    nativeToLwjglMap.put(Short.valueOf((short)30), Integer.valueOf(27));
    nativeToLwjglMap.put(Short.valueOf((short)41), Integer.valueOf(39));
    nativeToLwjglMap.put(Short.valueOf((short)44), Integer.valueOf(53));
    nativeToLwjglMap.put(Short.valueOf((short)47), Integer.valueOf(52));
    nativeToLwjglMap.put(Short.valueOf((short)50), Integer.valueOf(41));
    
    nativeToLwjglMap.put(Short.valueOf((short)65), Integer.valueOf(83));
    nativeToLwjglMap.put(Short.valueOf((short)67), Integer.valueOf(55));
    nativeToLwjglMap.put(Short.valueOf((short)69), Integer.valueOf(78));
    nativeToLwjglMap.put(Short.valueOf((short)71), Integer.valueOf(218));
    nativeToLwjglMap.put(Short.valueOf((short)75), Integer.valueOf(181));
    nativeToLwjglMap.put(Short.valueOf((short)76), Integer.valueOf(156));
    nativeToLwjglMap.put(Short.valueOf((short)78), Integer.valueOf(74));
    nativeToLwjglMap.put(Short.valueOf((short)81), Integer.valueOf(141));
    
    nativeToLwjglMap.put(Short.valueOf((short)82), Integer.valueOf(82));
    nativeToLwjglMap.put(Short.valueOf((short)83), Integer.valueOf(79));
    nativeToLwjglMap.put(Short.valueOf((short)84), Integer.valueOf(80));
    nativeToLwjglMap.put(Short.valueOf((short)85), Integer.valueOf(81));
    nativeToLwjglMap.put(Short.valueOf((short)86), Integer.valueOf(75));
    nativeToLwjglMap.put(Short.valueOf((short)87), Integer.valueOf(76));
    nativeToLwjglMap.put(Short.valueOf((short)88), Integer.valueOf(77));
    nativeToLwjglMap.put(Short.valueOf((short)89), Integer.valueOf(71));
    nativeToLwjglMap.put(Short.valueOf((short)91), Integer.valueOf(72));
    nativeToLwjglMap.put(Short.valueOf((short)92), Integer.valueOf(73));
    

    nativeToLwjglMap.put(Short.valueOf((short)36), Integer.valueOf(28));
    nativeToLwjglMap.put(Short.valueOf((short)48), Integer.valueOf(15));
    nativeToLwjglMap.put(Short.valueOf((short)49), Integer.valueOf(57));
    nativeToLwjglMap.put(Short.valueOf((short)51), Integer.valueOf(14));
    nativeToLwjglMap.put(Short.valueOf((short)53), Integer.valueOf(1));
    nativeToLwjglMap.put(Short.valueOf((short)54), Integer.valueOf(220));
    nativeToLwjglMap.put(Short.valueOf((short)55), Integer.valueOf(219));
    nativeToLwjglMap.put(Short.valueOf((short)56), Integer.valueOf(42));
    nativeToLwjglMap.put(Short.valueOf((short)57), Integer.valueOf(58));
    nativeToLwjglMap.put(Short.valueOf((short)58), Integer.valueOf(56));
    nativeToLwjglMap.put(Short.valueOf((short)59), Integer.valueOf(29));
    nativeToLwjglMap.put(Short.valueOf((short)60), Integer.valueOf(54));
    nativeToLwjglMap.put(Short.valueOf((short)61), Integer.valueOf(184));
    nativeToLwjglMap.put(Short.valueOf((short)62), Integer.valueOf(157));
    
    nativeToLwjglMap.put(Short.valueOf((short)63), Integer.valueOf(196));
    nativeToLwjglMap.put(Short.valueOf((short)119), Integer.valueOf(207));
    
    nativeToLwjglMap.put(Short.valueOf((short)122), Integer.valueOf(59));
    nativeToLwjglMap.put(Short.valueOf((short)120), Integer.valueOf(60));
    nativeToLwjglMap.put(Short.valueOf((short)99), Integer.valueOf(61));
    nativeToLwjglMap.put(Short.valueOf((short)118), Integer.valueOf(62));
    nativeToLwjglMap.put(Short.valueOf((short)96), Integer.valueOf(63));
    nativeToLwjglMap.put(Short.valueOf((short)97), Integer.valueOf(64));
    nativeToLwjglMap.put(Short.valueOf((short)98), Integer.valueOf(65));
    nativeToLwjglMap.put(Short.valueOf((short)100), Integer.valueOf(66));
    nativeToLwjglMap.put(Short.valueOf((short)101), Integer.valueOf(67));
    nativeToLwjglMap.put(Short.valueOf((short)109), Integer.valueOf(68));
    nativeToLwjglMap.put(Short.valueOf((short)103), Integer.valueOf(87));
    nativeToLwjglMap.put(Short.valueOf((short)111), Integer.valueOf(88));
    nativeToLwjglMap.put(Short.valueOf((short)105), Integer.valueOf(100));
    nativeToLwjglMap.put(Short.valueOf((short)107), Integer.valueOf(101));
    nativeToLwjglMap.put(Short.valueOf((short)113), Integer.valueOf(102));
    nativeToLwjglMap.put(Short.valueOf((short)106), Integer.valueOf(103));
    nativeToLwjglMap.put(Short.valueOf((short)64), Integer.valueOf(104));
    nativeToLwjglMap.put(Short.valueOf((short)79), Integer.valueOf(105));
    nativeToLwjglMap.put(Short.valueOf((short)80), Integer.valueOf(113));
    

    nativeToLwjglMap.put(Short.valueOf((short)117), Integer.valueOf(211));
    nativeToLwjglMap.put(Short.valueOf((short)114), Integer.valueOf(210));
    nativeToLwjglMap.put(Short.valueOf((short)115), Integer.valueOf(199));
    
    nativeToLwjglMap.put(Short.valueOf((short)121), Integer.valueOf(209));
    nativeToLwjglMap.put(Short.valueOf((short)116), Integer.valueOf(201));
    

    nativeToLwjglMap.put(Short.valueOf((short)123), Integer.valueOf(203));
    nativeToLwjglMap.put(Short.valueOf((short)124), Integer.valueOf(205));
    nativeToLwjglMap.put(Short.valueOf((short)125), Integer.valueOf(208));
    nativeToLwjglMap.put(Short.valueOf((short)126), Integer.valueOf(200));
    
    nativeToLwjglMap.put(Short.valueOf((short)10), Integer.valueOf(167));
    
    nativeToLwjglMap.put(Short.valueOf((short)110), Integer.valueOf(221));
    nativeToLwjglMap.put(Short.valueOf((short)297), Integer.valueOf(146));
  }
  
  public void register() {
    nRegisterKeyListener(window_handle);
  }
  
  public void unregister() {
    nUnregisterKeyListener(window_handle);
  }
  
  public void putKeyboardEvent(int key_code, byte state, int character, long nanos, boolean repeat) {
    event.clear();
    event.putInt(key_code).put(state).putInt(character).putLong(nanos).put((byte)(repeat ? 1 : 0));
    event.flip();
    putEvent(event);
  }
  
  public synchronized void poll(ByteBuffer key_down_buffer) {
    flushDeferredEvent();
    int old_position = key_down_buffer.position();
    key_down_buffer.put(key_states);
    key_down_buffer.position(old_position);
  }
  
  public synchronized void copyEvents(ByteBuffer dest) {
    flushDeferredEvent();
    super.copyEvents(dest);
  }
  
  private synchronized void handleKey(int key_code, byte state, int character, long nanos) {
    if (character == 65535)
      character = 0;
    if (state == 1) {
      boolean repeat = false;
      if (has_deferred_event)
        if ((nanos == deferred_nanos) && (deferred_key_code == key_code)) {
          has_deferred_event = false;
          repeat = true;
        } else {
          flushDeferredEvent();
        }
      putKeyEvent(key_code, state, character, nanos, repeat);
    } else {
      flushDeferredEvent();
      has_deferred_event = true;
      deferred_nanos = nanos;
      deferred_key_code = key_code;
      deferred_key_state = state;
      deferred_character = character;
    }
  }
  
  private void flushDeferredEvent() {
    if (has_deferred_event) {
      putKeyEvent(deferred_key_code, deferred_key_state, deferred_character, deferred_nanos, false);
      has_deferred_event = false;
    }
  }
  
  public void putKeyEvent(int key_code, byte state, int character, long nanos, boolean repeat)
  {
    int mapped_code = getMappedKeyCode((short)key_code);
    if (mapped_code < 0) {
      System.out.println("Unrecognized keycode: " + key_code);
      
      return;
    }
    if (key_states[mapped_code] == state)
      repeat = true;
    key_states[mapped_code] = state;
    int key_int_char = character & 0xFFFF;
    putKeyboardEvent(mapped_code, state, key_int_char, nanos, repeat);
  }
  
  private int getMappedKeyCode(short key_code) {
    if (nativeToLwjglMap.containsKey(Short.valueOf(key_code))) {
      return ((Integer)nativeToLwjglMap.get(Short.valueOf(key_code))).intValue();
    }
    return -1;
  }
  
  public void keyPressed(int key_code, String chars, long nanos)
  {
    int character = (chars == null) || (chars.length() == 0) ? '\000' : chars.charAt(0);
    handleKey(key_code, (byte)1, character, nanos);
  }
  
  public void keyReleased(int key_code, String chars, long nanos)
  {
    int character = (chars == null) || (chars.length() == 0) ? '\000' : chars.charAt(0);
    handleKey(key_code, (byte)0, character, nanos);
  }
  
  public void keyTyped(KeyEvent e) {}
}
