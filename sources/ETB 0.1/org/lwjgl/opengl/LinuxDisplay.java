package org.lwjgl.opengl;

import java.awt.Canvas;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.MemoryUtil;




















































final class LinuxDisplay
  implements DisplayImplementation
{
  public static final int CurrentTime = 0;
  public static final int GrabSuccess = 0;
  public static final int AutoRepeatModeOff = 0;
  public static final int AutoRepeatModeOn = 1;
  public static final int AutoRepeatModeDefault = 2;
  public static final int None = 0;
  private static final int KeyPressMask = 1;
  private static final int KeyReleaseMask = 2;
  private static final int ButtonPressMask = 4;
  private static final int ButtonReleaseMask = 8;
  private static final int NotifyAncestor = 0;
  private static final int NotifyNonlinear = 3;
  private static final int NotifyPointer = 5;
  private static final int NotifyPointerRoot = 6;
  private static final int NotifyDetailNone = 7;
  private static final int SetModeInsert = 0;
  private static final int SaveSetRoot = 1;
  private static final int SaveSetUnmap = 1;
  private static final int X_SetInputFocus = 42;
  private static final int FULLSCREEN_LEGACY = 1;
  private static final int FULLSCREEN_NETWM = 2;
  private static final int WINDOWED = 3;
  private static int current_window_mode = 3;
  private static final int XRANDR = 10;
  private static final int XF86VIDMODE = 11;
  private static final int NONE = 12;
  private static long display;
  private static long current_window;
  private static long saved_error_handler;
  private static int display_connection_usage_count;
  private final LinuxEvent event_buffer;
  private final LinuxEvent tmp_event_buffer;
  private int current_displaymode_extension;
  private long delete_atom;
  
  LinuxDisplay()
  {
    event_buffer = new LinuxEvent();
    tmp_event_buffer = new LinuxEvent();
    

    current_displaymode_extension = 12;
    
























    mouseInside = true;
    













    last_window_focus = 0L;
    





    focus_listener = new FocusListener() {
      public void focusGained(FocusEvent e) {
        synchronized (GlobalLock.lock) {
          parent_focused = true;
          parent_focus_changed = true;
        }
      }
      
      public void focusLost(FocusEvent e) { synchronized (GlobalLock.lock) {
          parent_focused = false;
          parent_focus_changed = true;
        }
      }
    };
  }
  
  /* Error */
  private static ByteBuffer getCurrentGammaRamp()
    throws LWJGLException
  {
    // Byte code:
    //   0: invokestatic 146	org/lwjgl/opengl/LinuxDisplay:lockAWT	()V
    //   3: invokestatic 149	org/lwjgl/opengl/LinuxDisplay:incDisplay	()V
    //   6: invokestatic 153	org/lwjgl/opengl/LinuxDisplay:isXF86VidModeSupported	()Z
    //   9: ifeq +21 -> 30
    //   12: invokestatic 157	org/lwjgl/opengl/LinuxDisplay:getDisplay	()J
    //   15: invokestatic 161	org/lwjgl/opengl/LinuxDisplay:getDefaultScreen	()I
    //   18: invokestatic 165	org/lwjgl/opengl/LinuxDisplay:nGetCurrentGammaRamp	(JI)Ljava/nio/ByteBuffer;
    //   21: astore_0
    //   22: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   25: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   28: aload_0
    //   29: areturn
    //   30: aconst_null
    //   31: astore_0
    //   32: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   35: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   38: aload_0
    //   39: areturn
    //   40: astore_1
    //   41: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   44: aload_1
    //   45: athrow
    //   46: astore_2
    //   47: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   50: aload_2
    //   51: athrow
    // Line number table:
    //   Java source line #181	-> byte code offset #0
    //   Java source line #183	-> byte code offset #3
    //   Java source line #185	-> byte code offset #6
    //   Java source line #186	-> byte code offset #12
    //   Java source line #190	-> byte code offset #22
    //   Java source line #193	-> byte code offset #25
    //   Java source line #188	-> byte code offset #30
    //   Java source line #190	-> byte code offset #32
    //   Java source line #193	-> byte code offset #35
    //   Java source line #190	-> byte code offset #40
    //   Java source line #193	-> byte code offset #46
    // Local variable table:
    //   start	length	slot	name	signature
    //   21	18	0	localByteBuffer	ByteBuffer
    //   40	5	1	localObject1	Object
    //   46	5	2	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   6	22	40	finally
    //   30	32	40	finally
    //   40	41	40	finally
    //   3	25	46	finally
    //   30	35	46	finally
    //   40	47	46	finally
  }
  
  private static native ByteBuffer nGetCurrentGammaRamp(long paramLong, int paramInt)
    throws LWJGLException;
  
  private static int getBestDisplayModeExtension()
  {
    int result;
    int result;
    if (isXrandrSupported()) {
      LWJGLUtil.log("Using Xrandr for display mode switching");
      result = 10; } else { int result;
      if (isXF86VidModeSupported()) {
        LWJGLUtil.log("Using XF86VidMode for display mode switching");
        result = 11;
      } else {
        LWJGLUtil.log("No display mode extensions available");
        result = 12;
      } }
    return result;
  }
  
  /* Error */
  private static boolean isXrandrSupported()
  {
    // Byte code:
    //   0: ldc -66
    //   2: invokestatic 196	org/lwjgl/opengl/Display:getPrivilegedBoolean	(Ljava/lang/String;)Z
    //   5: ifeq +5 -> 10
    //   8: iconst_0
    //   9: ireturn
    //   10: invokestatic 146	org/lwjgl/opengl/LinuxDisplay:lockAWT	()V
    //   13: invokestatic 149	org/lwjgl/opengl/LinuxDisplay:incDisplay	()V
    //   16: invokestatic 157	org/lwjgl/opengl/LinuxDisplay:getDisplay	()J
    //   19: invokestatic 200	org/lwjgl/opengl/LinuxDisplay:nIsXrandrSupported	(J)Z
    //   22: istore_0
    //   23: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   26: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   29: iload_0
    //   30: ireturn
    //   31: astore_1
    //   32: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   35: aload_1
    //   36: athrow
    //   37: astore_0
    //   38: new 202	java/lang/StringBuilder
    //   41: dup
    //   42: invokespecial 203	java/lang/StringBuilder:<init>	()V
    //   45: ldc -51
    //   47: invokevirtual 209	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   50: aload_0
    //   51: invokevirtual 212	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   54: invokevirtual 216	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   57: invokestatic 183	org/lwjgl/LWJGLUtil:log	(Ljava/lang/CharSequence;)V
    //   60: iconst_0
    //   61: istore_1
    //   62: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   65: iload_1
    //   66: ireturn
    //   67: astore_2
    //   68: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   71: aload_2
    //   72: athrow
    // Line number table:
    //   Java source line #214	-> byte code offset #0
    //   Java source line #215	-> byte code offset #8
    //   Java source line #216	-> byte code offset #10
    //   Java source line #218	-> byte code offset #13
    //   Java source line #220	-> byte code offset #16
    //   Java source line #222	-> byte code offset #23
    //   Java source line #228	-> byte code offset #26
    //   Java source line #222	-> byte code offset #31
    //   Java source line #224	-> byte code offset #37
    //   Java source line #225	-> byte code offset #38
    //   Java source line #226	-> byte code offset #60
    //   Java source line #228	-> byte code offset #62
    // Local variable table:
    //   start	length	slot	name	signature
    //   22	8	0	bool1	boolean
    //   37	14	0	e	LWJGLException
    //   31	5	1	localObject1	Object
    //   61	5	1	bool2	boolean
    //   67	5	2	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   16	23	31	finally
    //   31	32	31	finally
    //   13	26	37	org/lwjgl/LWJGLException
    //   31	37	37	org/lwjgl/LWJGLException
    //   13	26	67	finally
    //   31	62	67	finally
    //   67	68	67	finally
  }
  
  private static native boolean nIsXrandrSupported(long paramLong)
    throws LWJGLException;
  
  /* Error */
  private static boolean isXF86VidModeSupported()
  {
    // Byte code:
    //   0: invokestatic 146	org/lwjgl/opengl/LinuxDisplay:lockAWT	()V
    //   3: invokestatic 149	org/lwjgl/opengl/LinuxDisplay:incDisplay	()V
    //   6: invokestatic 157	org/lwjgl/opengl/LinuxDisplay:getDisplay	()J
    //   9: invokestatic 221	org/lwjgl/opengl/LinuxDisplay:nIsXF86VidModeSupported	(J)Z
    //   12: istore_0
    //   13: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   16: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   19: iload_0
    //   20: ireturn
    //   21: astore_1
    //   22: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   25: aload_1
    //   26: athrow
    //   27: astore_0
    //   28: new 202	java/lang/StringBuilder
    //   31: dup
    //   32: invokespecial 203	java/lang/StringBuilder:<init>	()V
    //   35: ldc -33
    //   37: invokevirtual 209	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   40: aload_0
    //   41: invokevirtual 212	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   44: invokevirtual 216	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   47: invokestatic 183	org/lwjgl/LWJGLUtil:log	(Ljava/lang/CharSequence;)V
    //   50: iconst_0
    //   51: istore_1
    //   52: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   55: iload_1
    //   56: ireturn
    //   57: astore_2
    //   58: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   61: aload_2
    //   62: athrow
    // Line number table:
    //   Java source line #234	-> byte code offset #0
    //   Java source line #236	-> byte code offset #3
    //   Java source line #238	-> byte code offset #6
    //   Java source line #240	-> byte code offset #13
    //   Java source line #246	-> byte code offset #16
    //   Java source line #240	-> byte code offset #21
    //   Java source line #242	-> byte code offset #27
    //   Java source line #243	-> byte code offset #28
    //   Java source line #244	-> byte code offset #50
    //   Java source line #246	-> byte code offset #52
    // Local variable table:
    //   start	length	slot	name	signature
    //   12	8	0	bool1	boolean
    //   27	14	0	e	LWJGLException
    //   21	5	1	localObject1	Object
    //   51	5	1	bool2	boolean
    //   57	5	2	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   6	13	21	finally
    //   21	22	21	finally
    //   3	16	27	org/lwjgl/LWJGLException
    //   21	27	27	org/lwjgl/LWJGLException
    //   3	16	57	finally
    //   21	52	57	finally
    //   57	58	57	finally
  }
  
  private static native boolean nIsXF86VidModeSupported(long paramLong)
    throws LWJGLException;
  
  /* Error */
  private static boolean isNetWMFullscreenSupported()
    throws LWJGLException
  {
    // Byte code:
    //   0: ldc -30
    //   2: invokestatic 196	org/lwjgl/opengl/Display:getPrivilegedBoolean	(Ljava/lang/String;)Z
    //   5: ifeq +5 -> 10
    //   8: iconst_0
    //   9: ireturn
    //   10: invokestatic 146	org/lwjgl/opengl/LinuxDisplay:lockAWT	()V
    //   13: invokestatic 149	org/lwjgl/opengl/LinuxDisplay:incDisplay	()V
    //   16: invokestatic 157	org/lwjgl/opengl/LinuxDisplay:getDisplay	()J
    //   19: invokestatic 161	org/lwjgl/opengl/LinuxDisplay:getDefaultScreen	()I
    //   22: invokestatic 230	org/lwjgl/opengl/LinuxDisplay:nIsNetWMFullscreenSupported	(JI)Z
    //   25: istore_0
    //   26: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   29: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   32: iload_0
    //   33: ireturn
    //   34: astore_1
    //   35: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   38: aload_1
    //   39: athrow
    //   40: astore_0
    //   41: new 202	java/lang/StringBuilder
    //   44: dup
    //   45: invokespecial 203	java/lang/StringBuilder:<init>	()V
    //   48: ldc -24
    //   50: invokevirtual 209	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   53: aload_0
    //   54: invokevirtual 212	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   57: invokevirtual 216	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   60: invokestatic 183	org/lwjgl/LWJGLUtil:log	(Ljava/lang/CharSequence;)V
    //   63: iconst_0
    //   64: istore_1
    //   65: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   68: iload_1
    //   69: ireturn
    //   70: astore_2
    //   71: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   74: aload_2
    //   75: athrow
    // Line number table:
    //   Java source line #252	-> byte code offset #0
    //   Java source line #253	-> byte code offset #8
    //   Java source line #254	-> byte code offset #10
    //   Java source line #256	-> byte code offset #13
    //   Java source line #258	-> byte code offset #16
    //   Java source line #260	-> byte code offset #26
    //   Java source line #266	-> byte code offset #29
    //   Java source line #260	-> byte code offset #34
    //   Java source line #262	-> byte code offset #40
    //   Java source line #263	-> byte code offset #41
    //   Java source line #264	-> byte code offset #63
    //   Java source line #266	-> byte code offset #65
    // Local variable table:
    //   start	length	slot	name	signature
    //   25	8	0	bool1	boolean
    //   40	14	0	e	LWJGLException
    //   34	5	1	localObject1	Object
    //   64	5	1	bool2	boolean
    //   70	5	2	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   16	26	34	finally
    //   34	35	34	finally
    //   13	29	40	org/lwjgl/LWJGLException
    //   34	40	40	org/lwjgl/LWJGLException
    //   13	29	70	finally
    //   34	65	70	finally
    //   70	71	70	finally
  }
  
  private static native boolean nIsNetWMFullscreenSupported(long paramLong, int paramInt)
    throws LWJGLException;
  
  static void lockAWT()
  {
    try
    {
      
    }
    catch (LWJGLException e)
    {
      LWJGLUtil.log("Caught exception while locking AWT: " + e);
    }
  }
  
  private static native void nLockAWT() throws LWJGLException;
  
  static void unlockAWT() {
    try {
      
    } catch (LWJGLException e) { LWJGLUtil.log("Caught exception while unlocking AWT: " + e);
    }
  }
  
  private static native void nUnlockAWT()
    throws LWJGLException;
  
  static void incDisplay() throws LWJGLException
  {
    if (display_connection_usage_count == 0)
    {
      try {
        GLContext.loadOpenGLLibrary();
        org.lwjgl.opengles.GLContext.loadOpenGLLibrary();
      }
      catch (Throwable t) {}
      saved_error_handler = setErrorHandler();
      display = openDisplay();
    }
    
    display_connection_usage_count += 1; }
  
  private static native int callErrorHandler(long paramLong1, long paramLong2, long paramLong3);
  
  private static native long setErrorHandler();
  
  private static native long resetErrorHandler(long paramLong);
  private static native void synchronize(long paramLong, boolean paramBoolean);
  private static int globalErrorHandler(long display, long event_ptr, long error_display, long serial, long error_code, long request_code, long minor_code) throws LWJGLException { if ((xembedded) && (request_code == 42L)) { return 0;
    }
    if (display == getDisplay()) {
      String error_msg = getErrorText(display, error_code);
      throw new LWJGLException("X Error - disp: 0x" + Long.toHexString(error_display) + " serial: " + serial + " error: " + error_msg + " request_code: " + request_code + " minor_code: " + minor_code); }
    if (saved_error_handler != 0L)
      return callErrorHandler(saved_error_handler, display, event_ptr);
    return 0;
  }
  


  private static native String getErrorText(long paramLong1, long paramLong2);
  


  static void decDisplay() {}
  


  static native long openDisplay()
    throws LWJGLException;
  


  static native void closeDisplay(long paramLong);
  


  private int getWindowMode(boolean fullscreen)
    throws LWJGLException
  {
    if (fullscreen) {
      if ((current_displaymode_extension == 10) && (isNetWMFullscreenSupported())) {
        LWJGLUtil.log("Using NetWM for fullscreen window");
        return 2;
      }
      LWJGLUtil.log("Using legacy mode for fullscreen window");
      return 1;
    }
    
    return 3;
  }
  
  static long getDisplay() {
    if (display_connection_usage_count <= 0)
      throw new InternalError("display_connection_usage_count = " + display_connection_usage_count);
    return display;
  }
  
  static int getDefaultScreen() {
    return nGetDefaultScreen(getDisplay());
  }
  
  static native int nGetDefaultScreen(long paramLong);
  
  static long getWindow() { return current_window; }
  
  private void ungrabKeyboard()
  {
    if (keyboard_grabbed) {
      nUngrabKeyboard(getDisplay());
      keyboard_grabbed = false;
    }
  }
  
  static native int nUngrabKeyboard(long paramLong);
  
  private void grabKeyboard() { if (!keyboard_grabbed) {
      int res = nGrabKeyboard(getDisplay(), getWindow());
      if (res == 0)
        keyboard_grabbed = true;
    }
  }
  
  static native int nGrabKeyboard(long paramLong1, long paramLong2);
  
  private void grabPointer() { if (!pointer_grabbed) {
      int result = nGrabPointer(getDisplay(), getWindow(), 0L);
      if (result == 0) {
        pointer_grabbed = true;
        
        if (isLegacyFullscreen())
          nSetViewPort(getDisplay(), getWindow(), getDefaultScreen());
      }
    }
  }
  
  static native int nGrabPointer(long paramLong1, long paramLong2, long paramLong3);
  
  private static native void nSetViewPort(long paramLong1, long paramLong2, int paramInt);
  
  private void ungrabPointer() { if (pointer_grabbed) {
      pointer_grabbed = false;
      nUngrabPointer(getDisplay());
    }
  }
  
  static native int nUngrabPointer(long paramLong);
  
  private static boolean isFullscreen() { return (current_window_mode == 1) || (current_window_mode == 2); }
  
  private boolean shouldGrab()
  {
    return (!input_released) && (grab) && (mouse != null);
  }
  
  private void updatePointerGrab() {
    if ((isFullscreen()) || (shouldGrab())) {
      grabPointer();
    } else {
      ungrabPointer();
    }
    updateCursor();
  }
  
  private void updateCursor() { long cursor;
    long cursor;
    if (shouldGrab()) {
      cursor = blank_cursor;
    } else {
      cursor = current_cursor;
    }
    nDefineCursor(getDisplay(), getWindow(), cursor);
  }
  
  private static native void nDefineCursor(long paramLong1, long paramLong2, long paramLong3);
  
  private static boolean isLegacyFullscreen() { return current_window_mode == 1; }
  
  private void updateKeyboardGrab()
  {
    if (isLegacyFullscreen()) {
      grabKeyboard();
    } else
      ungrabKeyboard();
  }
  
  public void createWindow(DrawableLWJGL drawable, DisplayMode mode, Canvas parent, int x, int y) throws LWJGLException {
    
    try {
      incDisplay();
      try {
        if ((drawable instanceof DrawableGLES)) {
          peer_info = new LinuxDisplayPeerInfo();
        }
        ByteBuffer handle = peer_info.lockAndGetHandle();
        try {
          current_window_mode = getWindowMode(Display.isFullscreen());
          


          if (current_window_mode != 3) {
            Compiz.setLegacyFullscreenSupport(true);
          }
          



          boolean undecorated = (Display.getPrivilegedBoolean("org.lwjgl.opengl.Window.undecorated")) || ((current_window_mode != 3) && (Display.getPrivilegedBoolean("org.lwjgl.opengl.Window.undecorated_fs")));
          
          this.parent = parent;
          parent_window = (parent != null ? getHandle(parent) : getRootWindow(getDisplay(), getDefaultScreen()));
          resizable = Display.isResizable();
          resized = false;
          window_x = x;
          window_y = y;
          window_width = mode.getWidth();
          window_height = mode.getHeight();
          



          if ((mode.isFullscreenCapable()) && (current_displaymode_extension == 10)) {
            XRandR.Screen primaryScreen = XRandR.DisplayModetoScreen(Display.getDisplayMode());
            x = xPos;
            y = yPos;
          }
          
          current_window = nCreateWindow(getDisplay(), getDefaultScreen(), handle, mode, current_window_mode, x, y, undecorated, parent_window, resizable);
          

          wm_class = Display.getPrivilegedString("LWJGL_WM_CLASS");
          if (wm_class == null) wm_class = Display.getTitle();
          setClassHint(Display.getTitle(), wm_class);
          
          mapRaised(getDisplay(), current_window);
          xembedded = (parent != null) && (isAncestorXEmbedded(parent_window));
          blank_cursor = createBlankCursor();
          current_cursor = 0L;
          focused = false;
          input_released = false;
          pointer_grabbed = false;
          keyboard_grabbed = false;
          close_requested = false;
          grab = false;
          minimized = false;
          dirty = true;
          
          if ((drawable instanceof DrawableGLES)) {
            ((DrawableGLES)drawable).initialize(current_window, getDisplay(), 4, (org.lwjgl.opengles.PixelFormat)drawable.getPixelFormat());
          }
          if (parent != null) {
            parent.addFocusListener(focus_listener);
            parent_focused = parent.isFocusOwner();
            parent_focus_changed = true;
          }
        } finally {
          peer_info.unlock();
        }
      }
      catch (LWJGLException e) {
        throw e;
      }
    } finally {
      unlockAWT(); } }
  
  private static native long nCreateWindow(long paramLong1, int paramInt1, ByteBuffer paramByteBuffer, DisplayMode paramDisplayMode, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, long paramLong2, boolean paramBoolean2) throws LWJGLException;
  
  private static native long getRootWindow(long paramLong, int paramInt);
  
  private static native boolean hasProperty(long paramLong1, long paramLong2, long paramLong3);
  
  private static native long getParentWindow(long paramLong1, long paramLong2) throws LWJGLException;
  private static native int getChildCount(long paramLong1, long paramLong2) throws LWJGLException;
  private static native void mapRaised(long paramLong1, long paramLong2);
  private static native void reparentWindow(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2);
  private static native long nGetInputFocus(long paramLong) throws LWJGLException;
  private static native void nSetInputFocus(long paramLong1, long paramLong2, long paramLong3);
  private static native void nSetWindowSize(long paramLong1, long paramLong2, int paramInt1, int paramInt2, boolean paramBoolean);
  private static native int nGetX(long paramLong1, long paramLong2);
  private static native int nGetY(long paramLong1, long paramLong2);
  private static native int nGetWidth(long paramLong1, long paramLong2);
  private static native int nGetHeight(long paramLong1, long paramLong2);
  private static boolean isAncestorXEmbedded(long window) throws LWJGLException { long xembed_atom = internAtom("_XEMBED_INFO", true);
    if (xembed_atom != 0L) {
      long w = window;
      while (w != 0L) {
        if (hasProperty(getDisplay(), w, xembed_atom))
          return true;
        w = getParentWindow(getDisplay(), w);
      }
    }
    return false;
  }
  
  private static long getHandle(Canvas parent) throws LWJGLException {
    AWTCanvasImplementation awt_impl = AWTGLCanvas.createImplementation();
    LinuxPeerInfo parent_peer_info = (LinuxPeerInfo)awt_impl.createPeerInfo(parent, null, null);
    ByteBuffer parent_peer_info_handle = parent_peer_info.lockAndGetHandle();
    try {
      return parent_peer_info.getDrawable();
    } finally {
      parent_peer_info.unlock();
    }
  }
  
  private void updateInputGrab() {
    updatePointerGrab();
    updateKeyboardGrab();
  }
  
  public void destroyWindow() {
    
    try {
      if (parent != null) {
        parent.removeFocusListener(focus_listener);
      }
      try {
        setNativeCursor(null);
      } catch (LWJGLException e) {
        LWJGLUtil.log("Failed to reset cursor: " + e.getMessage());
      }
      nDestroyCursor(getDisplay(), blank_cursor);
      blank_cursor = 0L;
      ungrabKeyboard();
      nDestroyWindow(getDisplay(), getWindow());
      decDisplay();
      
      if (current_window_mode != 3)
        Compiz.setLegacyFullscreenSupport(false);
    } finally {
      unlockAWT();
    }
  }
  
  static native void nDestroyWindow(long paramLong1, long paramLong2);
  
  public void switchDisplayMode(DisplayMode mode) throws LWJGLException {
    
    try { switchDisplayModeOnTmpDisplay(mode);
      current_mode = mode;
    } finally {
      unlockAWT();
    }
  }
  
  private void switchDisplayModeOnTmpDisplay(DisplayMode mode) throws LWJGLException {
    if (current_displaymode_extension == 10)
    {
      XRandR.setConfiguration(false, new XRandR.Screen[] { XRandR.DisplayModetoScreen(mode) });
    } else {
      incDisplay();
      try {
        nSwitchDisplayMode(getDisplay(), getDefaultScreen(), current_displaymode_extension, mode);
      } finally {
        decDisplay();
      }
    }
  }
  
  private static native void nSwitchDisplayMode(long paramLong, int paramInt1, int paramInt2, DisplayMode paramDisplayMode) throws LWJGLException;
  
  private static long internAtom(String atom_name, boolean only_if_exists) throws LWJGLException {
    
    try { return nInternAtom(getDisplay(), atom_name, only_if_exists);
    } finally {
      decDisplay();
    }
  }
  
  static native long nInternAtom(long paramLong, String paramString, boolean paramBoolean);
  
  public void resetDisplayMode() {
    
    try { if (current_displaymode_extension == 10)
      {
        AccessController.doPrivileged(new PrivilegedAction() {
          public Object run() {
            XRandR.restoreConfiguration();
            return null;
          }
          

        });
      } else {
        switchDisplayMode(saved_mode);
      }
      if (isXF86VidModeSupported()) {
        doSetGamma(saved_gamma);
      }
      Compiz.setLegacyFullscreenSupport(false);
    } catch (LWJGLException e) {
      LWJGLUtil.log("Caught exception while resetting mode: " + e);
    } finally {
      unlockAWT();
    }
  }
  
  /* Error */
  public int getGammaRampLength()
  {
    // Byte code:
    //   0: invokestatic 153	org/lwjgl/opengl/LinuxDisplay:isXF86VidModeSupported	()Z
    //   3: ifne +5 -> 8
    //   6: iconst_0
    //   7: ireturn
    //   8: invokestatic 146	org/lwjgl/opengl/LinuxDisplay:lockAWT	()V
    //   11: invokestatic 149	org/lwjgl/opengl/LinuxDisplay:incDisplay	()V
    //   14: invokestatic 157	org/lwjgl/opengl/LinuxDisplay:getDisplay	()J
    //   17: invokestatic 161	org/lwjgl/opengl/LinuxDisplay:getDefaultScreen	()I
    //   20: invokestatic 679	org/lwjgl/opengl/LinuxDisplay:nGetGammaRampLength	(JI)I
    //   23: istore_1
    //   24: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   27: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   30: iload_1
    //   31: ireturn
    //   32: astore_1
    //   33: new 202	java/lang/StringBuilder
    //   36: dup
    //   37: invokespecial 203	java/lang/StringBuilder:<init>	()V
    //   40: ldc_w 681
    //   43: invokevirtual 209	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   46: aload_1
    //   47: invokevirtual 212	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   50: invokevirtual 216	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   53: invokestatic 183	org/lwjgl/LWJGLUtil:log	(Ljava/lang/CharSequence;)V
    //   56: iconst_0
    //   57: istore_2
    //   58: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   61: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   64: iload_2
    //   65: ireturn
    //   66: astore_3
    //   67: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   70: aload_3
    //   71: athrow
    //   72: astore_1
    //   73: new 202	java/lang/StringBuilder
    //   76: dup
    //   77: invokespecial 203	java/lang/StringBuilder:<init>	()V
    //   80: ldc_w 683
    //   83: invokevirtual 209	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   86: aload_1
    //   87: invokevirtual 212	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   90: invokevirtual 216	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   93: invokestatic 183	org/lwjgl/LWJGLUtil:log	(Ljava/lang/CharSequence;)V
    //   96: iconst_0
    //   97: istore_2
    //   98: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   101: iload_2
    //   102: ireturn
    //   103: astore 4
    //   105: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   108: aload 4
    //   110: athrow
    // Line number table:
    //   Java source line #666	-> byte code offset #0
    //   Java source line #667	-> byte code offset #6
    //   Java source line #668	-> byte code offset #8
    //   Java source line #671	-> byte code offset #11
    //   Java source line #673	-> byte code offset #14
    //   Java source line #678	-> byte code offset #24
    //   Java source line #685	-> byte code offset #27
    //   Java source line #674	-> byte code offset #32
    //   Java source line #675	-> byte code offset #33
    //   Java source line #676	-> byte code offset #56
    //   Java source line #678	-> byte code offset #58
    //   Java source line #685	-> byte code offset #61
    //   Java source line #678	-> byte code offset #66
    //   Java source line #680	-> byte code offset #72
    //   Java source line #681	-> byte code offset #73
    //   Java source line #682	-> byte code offset #96
    //   Java source line #685	-> byte code offset #98
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	111	0	this	LinuxDisplay
    //   23	8	1	i	int
    //   32	15	1	e	LWJGLException
    //   72	15	1	e	LWJGLException
    //   57	45	2	j	int
    //   66	5	3	localObject1	Object
    //   103	6	4	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   14	24	32	org/lwjgl/LWJGLException
    //   14	24	66	finally
    //   32	58	66	finally
    //   66	67	66	finally
    //   11	27	72	org/lwjgl/LWJGLException
    //   32	61	72	org/lwjgl/LWJGLException
    //   66	72	72	org/lwjgl/LWJGLException
    //   11	27	103	finally
    //   32	61	103	finally
    //   66	98	103	finally
    //   103	105	103	finally
  }
  
  private static native int nGetGammaRampLength(long paramLong, int paramInt)
    throws LWJGLException;
  
  public void setGammaRamp(FloatBuffer gammaRamp)
    throws LWJGLException
  {
    if (!isXF86VidModeSupported())
      throw new LWJGLException("No gamma ramp support (Missing XF86VM extension)");
    doSetGamma(convertToNativeRamp(gammaRamp));
  }
  
  private void doSetGamma(ByteBuffer native_gamma) throws LWJGLException {
    
    try {
      setGammaRampOnTmpDisplay(native_gamma);
      current_gamma = native_gamma;
    } finally {
      unlockAWT();
    }
  }
  
  private static void setGammaRampOnTmpDisplay(ByteBuffer native_gamma) throws LWJGLException {
    
    try {
      nSetGammaRamp(getDisplay(), getDefaultScreen(), native_gamma);
    } finally {
      decDisplay();
    }
  }
  
  private static native void nSetGammaRamp(long paramLong, int paramInt, ByteBuffer paramByteBuffer) throws LWJGLException;
  
  private static ByteBuffer convertToNativeRamp(FloatBuffer ramp) throws LWJGLException { return nConvertToNativeRamp(ramp, ramp.position(), ramp.remaining()); }
  
  private static native ByteBuffer nConvertToNativeRamp(FloatBuffer paramFloatBuffer, int paramInt1, int paramInt2) throws LWJGLException;
  
  public String getAdapter() {
    return null;
  }
  
  public String getVersion() {
    return null;
  }
  
  public DisplayMode init() throws LWJGLException {
    
    try {
      Compiz.init();
      
      delete_atom = internAtom("WM_DELETE_WINDOW", false);
      current_displaymode_extension = getBestDisplayModeExtension();
      if (current_displaymode_extension == 12)
        throw new LWJGLException("No display mode extension is available");
      DisplayMode[] modes = getAvailableDisplayModes();
      if ((modes == null) || (modes.length == 0))
        throw new LWJGLException("No modes available");
      switch (current_displaymode_extension) {
      case 10: 
        saved_mode = ((DisplayMode)AccessController.doPrivileged(new PrivilegedAction() {
          public DisplayMode run() {
            XRandR.saveConfiguration();
            return XRandR.ScreentoDisplayMode(XRandR.getConfiguration());
          }
        }));
        break;
      case 11: 
        saved_mode = modes[0];
        break;
      default: 
        throw new LWJGLException("Unknown display mode extension: " + current_displaymode_extension);
      }
      current_mode = saved_mode;
      saved_gamma = getCurrentGammaRamp();
      current_gamma = saved_gamma;
      return saved_mode;
    } finally {
      unlockAWT();
    }
  }
  
  /* Error */
  private static DisplayMode getCurrentXRandrMode()
    throws LWJGLException
  {
    // Byte code:
    //   0: invokestatic 146	org/lwjgl/opengl/LinuxDisplay:lockAWT	()V
    //   3: invokestatic 149	org/lwjgl/opengl/LinuxDisplay:incDisplay	()V
    //   6: invokestatic 157	org/lwjgl/opengl/LinuxDisplay:getDisplay	()J
    //   9: invokestatic 161	org/lwjgl/opengl/LinuxDisplay:getDefaultScreen	()I
    //   12: invokestatic 747	org/lwjgl/opengl/LinuxDisplay:nGetCurrentXRandrMode	(JI)Lorg/lwjgl/opengl/DisplayMode;
    //   15: astore_0
    //   16: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   19: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   22: aload_0
    //   23: areturn
    //   24: astore_1
    //   25: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   28: aload_1
    //   29: athrow
    //   30: astore_2
    //   31: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   34: aload_2
    //   35: athrow
    // Line number table:
    //   Java source line #766	-> byte code offset #0
    //   Java source line #768	-> byte code offset #3
    //   Java source line #770	-> byte code offset #6
    //   Java source line #772	-> byte code offset #16
    //   Java source line #775	-> byte code offset #19
    //   Java source line #772	-> byte code offset #24
    //   Java source line #775	-> byte code offset #30
    // Local variable table:
    //   start	length	slot	name	signature
    //   15	8	0	localDisplayMode	DisplayMode
    //   24	5	1	localObject1	Object
    //   30	5	2	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   6	16	24	finally
    //   24	25	24	finally
    //   3	19	30	finally
    //   24	31	30	finally
  }
  
  private static native DisplayMode nGetCurrentXRandrMode(long paramLong, int paramInt)
    throws LWJGLException;
  
  public void setTitle(String title)
  {
    
    try
    {
      ByteBuffer titleText = MemoryUtil.encodeUTF8(title);
      nSetTitle(getDisplay(), getWindow(), MemoryUtil.getAddress(titleText), titleText.remaining() - 1);
    } finally {
      unlockAWT();
    }
  }
  
  private static native void nSetTitle(long paramLong1, long paramLong2, long paramLong3, int paramInt);
  
  private void setClassHint(String wm_name, String wm_class) {
    
    try {
      ByteBuffer nameText = MemoryUtil.encodeUTF8(wm_name);
      ByteBuffer classText = MemoryUtil.encodeUTF8(wm_class);
      
      nSetClassHint(getDisplay(), getWindow(), MemoryUtil.getAddress(nameText), MemoryUtil.getAddress(classText));
    } finally {
      unlockAWT();
    }
  }
  
  private static native void nSetClassHint(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  public boolean isCloseRequested() { boolean result = close_requested;
    close_requested = false;
    return result;
  }
  
  public boolean isVisible() {
    return !minimized;
  }
  
  public boolean isActive() {
    return (focused) || (isLegacyFullscreen());
  }
  
  public boolean isDirty() {
    boolean result = dirty;
    dirty = false;
    return result;
  }
  
  public PeerInfo createPeerInfo(PixelFormat pixel_format, ContextAttribs attribs) throws LWJGLException {
    peer_info = new LinuxDisplayPeerInfo(pixel_format);
    return peer_info;
  }
  
  private void relayEventToParent(LinuxEvent event_buffer, int event_mask) {
    tmp_event_buffer.copyFrom(event_buffer);
    tmp_event_buffer.setWindow(parent_window);
    tmp_event_buffer.sendEvent(getDisplay(), parent_window, true, event_mask);
  }
  
  private void relayEventToParent(LinuxEvent event_buffer) {
    if (parent == null)
      return;
    switch (event_buffer.getType()) {
    case 2: 
      relayEventToParent(event_buffer, 1);
      break;
    case 3: 
      relayEventToParent(event_buffer, 1);
      break;
    case 4: 
      if ((xembedded) || (!focused)) relayEventToParent(event_buffer, 1);
      break;
    case 5: 
      if ((xembedded) || (!focused)) { relayEventToParent(event_buffer, 1);
      }
      break;
    }
  }
  
  private void processEvents()
  {
    while (LinuxEvent.getPending(getDisplay()) > 0) {
      event_buffer.nextEvent(getDisplay());
      long event_window = event_buffer.getWindow();
      relayEventToParent(event_buffer);
      if ((event_window == getWindow()) && (!event_buffer.filterEvent(event_window)) && ((mouse == null) || (!mouse.filterEvent(grab, shouldWarpPointer(), event_buffer))) && ((keyboard == null) || (!keyboard.filterEvent(event_buffer))))
      {


        switch (event_buffer.getType()) {
        case 9: 
          setFocused(true, event_buffer.getFocusDetail());
          break;
        case 10: 
          setFocused(false, event_buffer.getFocusDetail());
          break;
        case 33: 
          if ((event_buffer.getClientFormat() == 32) && (event_buffer.getClientData(0) == delete_atom))
            close_requested = true;
          break;
        case 19: 
          dirty = true;
          minimized = false;
          break;
        case 18: 
          dirty = true;
          minimized = true;
          break;
        case 12: 
          dirty = true;
          break;
        case 22: 
          int x = nGetX(getDisplay(), getWindow());
          int y = nGetY(getDisplay(), getWindow());
          
          int width = nGetWidth(getDisplay(), getWindow());
          int height = nGetHeight(getDisplay(), getWindow());
          
          window_x = x;
          window_y = y;
          
          if ((window_width != width) || (window_height != height)) {
            resized = true;
            window_width = width;
            window_height = height;
          }
          
          break;
        case 7: 
          mouseInside = true;
          break;
        case 8: 
          mouseInside = false;
        }
      }
    }
  }
  
  public void update()
  {
    
    try
    {
      processEvents();
      checkInput();
    } finally {
      unlockAWT();
    }
  }
  
  public void reshape(int x, int y, int width, int height) {
    
    try {
      nReshape(getDisplay(), getWindow(), x, y, width, height);
    } finally {
      unlockAWT();
    }
  }
  
  private static native void nReshape(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  /* Error */
  public DisplayMode[] getAvailableDisplayModes()
    throws LWJGLException
  {
    // Byte code:
    //   0: invokestatic 146	org/lwjgl/opengl/LinuxDisplay:lockAWT	()V
    //   3: invokestatic 149	org/lwjgl/opengl/LinuxDisplay:incDisplay	()V
    //   6: aload_0
    //   7: getfield 128	org/lwjgl/opengl/LinuxDisplay:current_displaymode_extension	I
    //   10: bipush 10
    //   12: if_icmpne +109 -> 121
    //   15: invokestatic 157	org/lwjgl/opengl/LinuxDisplay:getDisplay	()J
    //   18: invokestatic 161	org/lwjgl/opengl/LinuxDisplay:getDefaultScreen	()I
    //   21: aload_0
    //   22: getfield 128	org/lwjgl/opengl/LinuxDisplay:current_displaymode_extension	I
    //   25: invokestatic 874	org/lwjgl/opengl/LinuxDisplay:nGetAvailableDisplayModes	(JII)[Lorg/lwjgl/opengl/DisplayMode;
    //   28: astore_1
    //   29: bipush 24
    //   31: istore_2
    //   32: aload_1
    //   33: arraylength
    //   34: ifle +10 -> 44
    //   37: aload_1
    //   38: iconst_0
    //   39: aaload
    //   40: invokevirtual 877	org/lwjgl/opengl/DisplayMode:getBitsPerPixel	()I
    //   43: istore_2
    //   44: invokestatic 881	org/lwjgl/opengl/XRandR:getScreenNames	()[Ljava/lang/String;
    //   47: iconst_0
    //   48: aaload
    //   49: invokestatic 885	org/lwjgl/opengl/XRandR:getResolutions	(Ljava/lang/String;)[Lorg/lwjgl/opengl/XRandR$Screen;
    //   52: astore_3
    //   53: aload_3
    //   54: arraylength
    //   55: anewarray 456	org/lwjgl/opengl/DisplayMode
    //   58: astore 4
    //   60: iconst_0
    //   61: istore 5
    //   63: iload 5
    //   65: aload 4
    //   67: arraylength
    //   68: if_icmpge +43 -> 111
    //   71: aload 4
    //   73: iload 5
    //   75: new 456	org/lwjgl/opengl/DisplayMode
    //   78: dup
    //   79: aload_3
    //   80: iload 5
    //   82: aaload
    //   83: getfield 887	org/lwjgl/opengl/XRandR$Screen:width	I
    //   86: aload_3
    //   87: iload 5
    //   89: aaload
    //   90: getfield 889	org/lwjgl/opengl/XRandR$Screen:height	I
    //   93: iload_2
    //   94: aload_3
    //   95: iload 5
    //   97: aaload
    //   98: getfield 892	org/lwjgl/opengl/XRandR$Screen:freq	I
    //   101: invokespecial 894	org/lwjgl/opengl/DisplayMode:<init>	(IIII)V
    //   104: aastore
    //   105: iinc 5 1
    //   108: goto -45 -> 63
    //   111: aload 4
    //   113: astore 5
    //   115: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   118: aload 5
    //   120: areturn
    //   121: invokestatic 157	org/lwjgl/opengl/LinuxDisplay:getDisplay	()J
    //   124: invokestatic 161	org/lwjgl/opengl/LinuxDisplay:getDefaultScreen	()I
    //   127: aload_0
    //   128: getfield 128	org/lwjgl/opengl/LinuxDisplay:current_displaymode_extension	I
    //   131: invokestatic 874	org/lwjgl/opengl/LinuxDisplay:nGetAvailableDisplayModes	(JII)[Lorg/lwjgl/opengl/DisplayMode;
    //   134: astore_1
    //   135: aload_1
    //   136: astore_2
    //   137: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   140: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   143: aload_2
    //   144: areturn
    //   145: astore 6
    //   147: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   150: aload 6
    //   152: athrow
    //   153: astore 7
    //   155: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   158: aload 7
    //   160: athrow
    // Line number table:
    //   Java source line #940	-> byte code offset #0
    //   Java source line #942	-> byte code offset #3
    //   Java source line #943	-> byte code offset #6
    //   Java source line #945	-> byte code offset #15
    //   Java source line #946	-> byte code offset #29
    //   Java source line #947	-> byte code offset #32
    //   Java source line #948	-> byte code offset #37
    //   Java source line #951	-> byte code offset #44
    //   Java source line #952	-> byte code offset #53
    //   Java source line #953	-> byte code offset #60
    //   Java source line #954	-> byte code offset #71
    //   Java source line #953	-> byte code offset #105
    //   Java source line #956	-> byte code offset #111
    //   Java source line #966	-> byte code offset #115
    //   Java source line #959	-> byte code offset #121
    //   Java source line #960	-> byte code offset #135
    //   Java source line #962	-> byte code offset #137
    //   Java source line #966	-> byte code offset #140
    //   Java source line #962	-> byte code offset #145
    //   Java source line #966	-> byte code offset #153
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	161	0	this	LinuxDisplay
    //   28	10	1	nDisplayModes	DisplayMode[]
    //   134	2	1	modes	DisplayMode[]
    //   31	113	2	bpp	int
    //   52	43	3	resolutions	XRandR.Screen[]
    //   58	54	4	modes	DisplayMode[]
    //   61	58	5	i	int
    //   145	6	6	localObject1	Object
    //   153	6	7	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   121	137	145	finally
    //   145	147	145	finally
    //   3	115	153	finally
    //   121	140	153	finally
    //   145	155	153	finally
  }
  
  private static native DisplayMode[] nGetAvailableDisplayModes(long paramLong, int paramInt1, int paramInt2)
    throws LWJGLException;
  
  public boolean hasWheel()
  {
    return true;
  }
  
  public int getButtonCount() {
    return mouse.getButtonCount();
  }
  
  public void createMouse() throws LWJGLException {
    
    try {
      mouse = new LinuxMouse(getDisplay(), getWindow(), getWindow());
    } finally {
      unlockAWT();
    }
  }
  
  public void destroyMouse() {
    mouse = null;
    updateInputGrab();
  }
  
  public void pollMouse(IntBuffer coord_buffer, ByteBuffer buttons) {
    
    try {
      mouse.poll(grab, coord_buffer, buttons);
    } finally {
      unlockAWT();
    }
  }
  
  public void readMouse(ByteBuffer buffer) {
    
    try {
      mouse.read(buffer);
    } finally {
      unlockAWT();
    }
  }
  
  public void setCursorPosition(int x, int y) {
    
    try {
      mouse.setCursorPosition(x, y);
    } finally {
      unlockAWT();
    }
  }
  
  private void checkInput() {
    if (parent == null) { return;
    }
    if (xembedded) {
      long current_focus_window = 0L;
      
      if ((last_window_focus != current_focus_window) || (parent_focused != focused)) {
        if (isParentWindowActive(current_focus_window)) {
          if (parent_focused) {
            nSetInputFocus(getDisplay(), current_window, 0L);
            last_window_focus = current_window;
            focused = true;
          }
          else
          {
            nSetInputFocus(getDisplay(), parent_proxy_focus_window, 0L);
            last_window_focus = parent_proxy_focus_window;
            focused = false;
          }
        }
        else {
          last_window_focus = current_focus_window;
          focused = false;
        }
        
      }
    }
    else if ((parent_focus_changed) && (parent_focused)) {
      setInputFocusUnsafe(getWindow());
      parent_focus_changed = false;
    }
  }
  
  private void setInputFocusUnsafe(long window)
  {
    try {
      nSetInputFocus(getDisplay(), window, 0L);
      nSync(getDisplay(), false);
    }
    catch (LWJGLException e) {
      LWJGLUtil.log("Got exception while trying to focus: " + e);
    }
  }
  
  private PeerInfo peer_info;
  private ByteBuffer saved_gamma;
  private ByteBuffer current_gamma;
  private DisplayMode saved_mode;
  private DisplayMode current_mode;
  private boolean keyboard_grabbed;
  private boolean pointer_grabbed;
  private boolean input_released;
  private boolean grab;
  private boolean focused;
  private boolean minimized;
  private static native void nSync(long paramLong, boolean paramBoolean) throws LWJGLException;
  
  private boolean isParentWindowActive(long window) {
    try {
      if (window == current_window) { return true;
      }
      
      if (getChildCount(getDisplay(), window) != 0) { return false;
      }
      
      long parent_window = getParentWindow(getDisplay(), window);
      

      if (parent_window == 0L) { return false;
      }
      
      long w = current_window;
      
      while (w != 0L) {
        w = getParentWindow(getDisplay(), w);
        if (w == parent_window) {
          parent_proxy_focus_window = window;
          return true;
        }
      }
    } catch (LWJGLException e) {
      LWJGLUtil.log("Failed to detect if parent window is active: " + e.getMessage());
      return true;
    }
    
    return false;
  }
  
  private void setFocused(boolean got_focus, int focus_detail) {
    if ((focused == got_focus) || (focus_detail == 7) || (focus_detail == 5) || (focus_detail == 6) || (xembedded))
      return;
    focused = got_focus;
    
    if (focused) {
      acquireInput();
    }
    else {
      releaseInput();
    }
  }
  
  private void releaseInput() {
    if ((isLegacyFullscreen()) || (input_released))
      return;
    if (keyboard != null)
      keyboard.releaseAll();
    input_released = true;
    updateInputGrab();
    if (current_window_mode == 2) {
      nIconifyWindow(getDisplay(), getWindow(), getDefaultScreen());
      try {
        if (current_displaymode_extension == 10)
        {
          AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
              XRandR.restoreConfiguration();
              return null;
            }
            

          });
        } else {
          switchDisplayModeOnTmpDisplay(saved_mode);
        }
        setGammaRampOnTmpDisplay(saved_gamma);
      } catch (LWJGLException e) {
        LWJGLUtil.log("Failed to restore saved mode: " + e.getMessage());
      }
    }
  }
  
  private static native void nIconifyWindow(long paramLong1, long paramLong2, int paramInt);
  
  private void acquireInput() { if ((isLegacyFullscreen()) || (!input_released))
      return;
    input_released = false;
    updateInputGrab();
    if (current_window_mode == 2) {
      try {
        switchDisplayModeOnTmpDisplay(current_mode);
        setGammaRampOnTmpDisplay(current_gamma);
      } catch (LWJGLException e) {
        LWJGLUtil.log("Failed to restore mode: " + e.getMessage());
      }
    }
  }
  
  public void grabMouse(boolean new_grab) {
    
    try {
      if (new_grab != grab) {
        grab = new_grab;
        updateInputGrab();
        mouse.changeGrabbed(grab, shouldWarpPointer());
      }
    } finally {
      unlockAWT();
    }
  }
  
  private boolean shouldWarpPointer() {
    return (pointer_grabbed) && (shouldGrab());
  }
  
  /* Error */
  public int getNativeCursorCapabilities()
  {
    // Byte code:
    //   0: invokestatic 146	org/lwjgl/opengl/LinuxDisplay:lockAWT	()V
    //   3: invokestatic 149	org/lwjgl/opengl/LinuxDisplay:incDisplay	()V
    //   6: invokestatic 157	org/lwjgl/opengl/LinuxDisplay:getDisplay	()J
    //   9: invokestatic 976	org/lwjgl/opengl/LinuxDisplay:nGetNativeCursorCapabilities	(J)I
    //   12: istore_1
    //   13: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   16: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   19: iload_1
    //   20: ireturn
    //   21: astore_2
    //   22: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   25: aload_2
    //   26: athrow
    //   27: astore_1
    //   28: new 978	java/lang/RuntimeException
    //   31: dup
    //   32: aload_1
    //   33: invokespecial 981	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   36: athrow
    //   37: astore_3
    //   38: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   41: aload_3
    //   42: athrow
    // Line number table:
    //   Java source line #1186	-> byte code offset #0
    //   Java source line #1188	-> byte code offset #3
    //   Java source line #1190	-> byte code offset #6
    //   Java source line #1192	-> byte code offset #13
    //   Java source line #1197	-> byte code offset #16
    //   Java source line #1192	-> byte code offset #21
    //   Java source line #1194	-> byte code offset #27
    //   Java source line #1195	-> byte code offset #28
    //   Java source line #1197	-> byte code offset #37
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	43	0	this	LinuxDisplay
    //   12	8	1	i	int
    //   27	6	1	e	LWJGLException
    //   21	5	2	localObject1	Object
    //   37	5	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   6	13	21	finally
    //   21	22	21	finally
    //   3	16	27	org/lwjgl/LWJGLException
    //   21	27	27	org/lwjgl/LWJGLException
    //   3	16	37	finally
    //   21	38	37	finally
  }
  
  private static native int nGetNativeCursorCapabilities(long paramLong)
    throws LWJGLException;
  
  public void setNativeCursor(Object handle)
    throws LWJGLException
  {
    current_cursor = getCursorHandle(handle);
    lockAWT();
    try {
      updateCursor();
    } finally {
      unlockAWT();
    }
  }
  
  /* Error */
  public int getMinCursorSize()
  {
    // Byte code:
    //   0: invokestatic 146	org/lwjgl/opengl/LinuxDisplay:lockAWT	()V
    //   3: invokestatic 149	org/lwjgl/opengl/LinuxDisplay:incDisplay	()V
    //   6: invokestatic 157	org/lwjgl/opengl/LinuxDisplay:getDisplay	()J
    //   9: invokestatic 348	org/lwjgl/opengl/LinuxDisplay:getWindow	()J
    //   12: invokestatic 990	org/lwjgl/opengl/LinuxDisplay:nGetMinCursorSize	(JJ)I
    //   15: istore_1
    //   16: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   19: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   22: iload_1
    //   23: ireturn
    //   24: astore_2
    //   25: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   28: aload_2
    //   29: athrow
    //   30: astore_1
    //   31: new 202	java/lang/StringBuilder
    //   34: dup
    //   35: invokespecial 203	java/lang/StringBuilder:<init>	()V
    //   38: ldc_w 992
    //   41: invokevirtual 209	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   44: aload_1
    //   45: invokevirtual 212	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   48: invokevirtual 216	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   51: invokestatic 183	org/lwjgl/LWJGLUtil:log	(Ljava/lang/CharSequence;)V
    //   54: iconst_0
    //   55: istore_2
    //   56: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   59: iload_2
    //   60: ireturn
    //   61: astore_3
    //   62: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   65: aload_3
    //   66: athrow
    // Line number table:
    //   Java source line #1213	-> byte code offset #0
    //   Java source line #1215	-> byte code offset #3
    //   Java source line #1217	-> byte code offset #6
    //   Java source line #1219	-> byte code offset #16
    //   Java source line #1225	-> byte code offset #19
    //   Java source line #1219	-> byte code offset #24
    //   Java source line #1221	-> byte code offset #30
    //   Java source line #1222	-> byte code offset #31
    //   Java source line #1223	-> byte code offset #54
    //   Java source line #1225	-> byte code offset #56
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	67	0	this	LinuxDisplay
    //   15	8	1	i	int
    //   30	15	1	e	LWJGLException
    //   24	5	2	localObject1	Object
    //   55	5	2	j	int
    //   61	5	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   6	16	24	finally
    //   24	25	24	finally
    //   3	19	30	org/lwjgl/LWJGLException
    //   24	30	30	org/lwjgl/LWJGLException
    //   3	19	61	finally
    //   24	56	61	finally
    //   61	62	61	finally
  }
  
  private static native int nGetMinCursorSize(long paramLong1, long paramLong2);
  
  /* Error */
  public int getMaxCursorSize()
  {
    // Byte code:
    //   0: invokestatic 146	org/lwjgl/opengl/LinuxDisplay:lockAWT	()V
    //   3: invokestatic 149	org/lwjgl/opengl/LinuxDisplay:incDisplay	()V
    //   6: invokestatic 157	org/lwjgl/opengl/LinuxDisplay:getDisplay	()J
    //   9: invokestatic 348	org/lwjgl/opengl/LinuxDisplay:getWindow	()J
    //   12: invokestatic 996	org/lwjgl/opengl/LinuxDisplay:nGetMaxCursorSize	(JJ)I
    //   15: istore_1
    //   16: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   19: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   22: iload_1
    //   23: ireturn
    //   24: astore_2
    //   25: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   28: aload_2
    //   29: athrow
    //   30: astore_1
    //   31: new 202	java/lang/StringBuilder
    //   34: dup
    //   35: invokespecial 203	java/lang/StringBuilder:<init>	()V
    //   38: ldc_w 998
    //   41: invokevirtual 209	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   44: aload_1
    //   45: invokevirtual 212	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   48: invokevirtual 216	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   51: invokestatic 183	org/lwjgl/LWJGLUtil:log	(Ljava/lang/CharSequence;)V
    //   54: iconst_0
    //   55: istore_2
    //   56: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   59: iload_2
    //   60: ireturn
    //   61: astore_3
    //   62: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   65: aload_3
    //   66: athrow
    // Line number table:
    //   Java source line #1231	-> byte code offset #0
    //   Java source line #1233	-> byte code offset #3
    //   Java source line #1235	-> byte code offset #6
    //   Java source line #1237	-> byte code offset #16
    //   Java source line #1243	-> byte code offset #19
    //   Java source line #1237	-> byte code offset #24
    //   Java source line #1239	-> byte code offset #30
    //   Java source line #1240	-> byte code offset #31
    //   Java source line #1241	-> byte code offset #54
    //   Java source line #1243	-> byte code offset #56
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	67	0	this	LinuxDisplay
    //   15	8	1	i	int
    //   30	15	1	e	LWJGLException
    //   24	5	2	localObject1	Object
    //   55	5	2	j	int
    //   61	5	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   6	16	24	finally
    //   24	25	24	finally
    //   3	19	30	org/lwjgl/LWJGLException
    //   24	30	30	org/lwjgl/LWJGLException
    //   3	19	61	finally
    //   24	56	61	finally
    //   61	62	61	finally
  }
  
  private static native int nGetMaxCursorSize(long paramLong1, long paramLong2);
  
  public void createKeyboard()
    throws LWJGLException
  {
    
    try
    {
      keyboard = new LinuxKeyboard(getDisplay(), getWindow());
    } finally {
      unlockAWT();
    }
  }
  
  public void destroyKeyboard() {
    
    try {
      keyboard.destroy(getDisplay());
      keyboard = null;
    } finally {
      unlockAWT();
    }
  }
  
  public void pollKeyboard(ByteBuffer keyDownBuffer) {
    
    try {
      keyboard.poll(keyDownBuffer);
    } finally {
      unlockAWT();
    }
  }
  
  public void readKeyboard(ByteBuffer buffer) {
    
    try {
      keyboard.read(buffer);
    } finally {
      unlockAWT();
    }
  }
  
  private static native long nCreateCursor(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, IntBuffer paramIntBuffer1, int paramInt6, IntBuffer paramIntBuffer2, int paramInt7) throws LWJGLException;
  
  private static long createBlankCursor() {
    return nCreateBlankCursor(getDisplay(), getWindow());
  }
  
  static native long nCreateBlankCursor(long paramLong1, long paramLong2);
  
  /* Error */
  public Object createCursor(int width, int height, int xHotspot, int yHotspot, int numImages, IntBuffer images, IntBuffer delays)
    throws LWJGLException
  {
    // Byte code:
    //   0: invokestatic 146	org/lwjgl/opengl/LinuxDisplay:lockAWT	()V
    //   3: invokestatic 149	org/lwjgl/opengl/LinuxDisplay:incDisplay	()V
    //   6: invokestatic 157	org/lwjgl/opengl/LinuxDisplay:getDisplay	()J
    //   9: iload_1
    //   10: iload_2
    //   11: iload_3
    //   12: iload 4
    //   14: iload 5
    //   16: aload 6
    //   18: aload 6
    //   20: invokevirtual 1021	java/nio/IntBuffer:position	()I
    //   23: aload 7
    //   25: aload 7
    //   27: ifnull +11 -> 38
    //   30: aload 7
    //   32: invokevirtual 1021	java/nio/IntBuffer:position	()I
    //   35: goto +4 -> 39
    //   38: iconst_m1
    //   39: invokestatic 1023	org/lwjgl/opengl/LinuxDisplay:nCreateCursor	(JIIIIILjava/nio/IntBuffer;ILjava/nio/IntBuffer;I)J
    //   42: lstore 8
    //   44: lload 8
    //   46: invokestatic 1027	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   49: astore 10
    //   51: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   54: aload 10
    //   56: areturn
    //   57: astore 8
    //   59: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   62: aload 8
    //   64: athrow
    //   65: astore 11
    //   67: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   70: aload 11
    //   72: athrow
    // Line number table:
    //   Java source line #1294	-> byte code offset #0
    //   Java source line #1296	-> byte code offset #3
    //   Java source line #1298	-> byte code offset #6
    //   Java source line #1299	-> byte code offset #44
    //   Java source line #1305	-> byte code offset #51
    //   Java source line #1300	-> byte code offset #57
    //   Java source line #1301	-> byte code offset #59
    //   Java source line #1302	-> byte code offset #62
    //   Java source line #1305	-> byte code offset #65
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	73	0	this	LinuxDisplay
    //   0	73	1	width	int
    //   0	73	2	height	int
    //   0	73	3	xHotspot	int
    //   0	73	4	yHotspot	int
    //   0	73	5	numImages	int
    //   0	73	6	images	IntBuffer
    //   0	73	7	delays	IntBuffer
    //   42	3	8	cursor	long
    //   57	6	8	e	LWJGLException
    //   49	6	10	localLong	Long
    //   65	6	11	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   6	51	57	org/lwjgl/LWJGLException
    //   3	51	65	finally
    //   57	67	65	finally
  }
  
  private static long getCursorHandle(Object cursor_handle)
  {
    return cursor_handle != null ? ((Long)cursor_handle).longValue() : 0L;
  }
  
  public void destroyCursor(Object cursorHandle) {
    
    try {
      nDestroyCursor(getDisplay(), getCursorHandle(cursorHandle));
      decDisplay();
    } finally {
      unlockAWT();
    }
  }
  
  static native void nDestroyCursor(long paramLong1, long paramLong2);
  
  /* Error */
  public int getPbufferCapabilities()
  {
    // Byte code:
    //   0: invokestatic 146	org/lwjgl/opengl/LinuxDisplay:lockAWT	()V
    //   3: invokestatic 149	org/lwjgl/opengl/LinuxDisplay:incDisplay	()V
    //   6: invokestatic 157	org/lwjgl/opengl/LinuxDisplay:getDisplay	()J
    //   9: invokestatic 161	org/lwjgl/opengl/LinuxDisplay:getDefaultScreen	()I
    //   12: invokestatic 1042	org/lwjgl/opengl/LinuxDisplay:nGetPbufferCapabilities	(JI)I
    //   15: istore_1
    //   16: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   19: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   22: iload_1
    //   23: ireturn
    //   24: astore_2
    //   25: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   28: aload_2
    //   29: athrow
    //   30: astore_1
    //   31: new 202	java/lang/StringBuilder
    //   34: dup
    //   35: invokespecial 203	java/lang/StringBuilder:<init>	()V
    //   38: ldc_w 1044
    //   41: invokevirtual 209	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   44: aload_1
    //   45: invokevirtual 212	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   48: invokevirtual 216	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   51: invokestatic 183	org/lwjgl/LWJGLUtil:log	(Ljava/lang/CharSequence;)V
    //   54: iconst_0
    //   55: istore_2
    //   56: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   59: iload_2
    //   60: ireturn
    //   61: astore_3
    //   62: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   65: aload_3
    //   66: athrow
    // Line number table:
    //   Java source line #1325	-> byte code offset #0
    //   Java source line #1327	-> byte code offset #3
    //   Java source line #1329	-> byte code offset #6
    //   Java source line #1331	-> byte code offset #16
    //   Java source line #1337	-> byte code offset #19
    //   Java source line #1331	-> byte code offset #24
    //   Java source line #1333	-> byte code offset #30
    //   Java source line #1334	-> byte code offset #31
    //   Java source line #1335	-> byte code offset #54
    //   Java source line #1337	-> byte code offset #56
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	67	0	this	LinuxDisplay
    //   15	8	1	i	int
    //   30	15	1	e	LWJGLException
    //   24	5	2	localObject1	Object
    //   55	5	2	j	int
    //   61	5	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   6	16	24	finally
    //   24	25	24	finally
    //   3	19	30	org/lwjgl/LWJGLException
    //   24	30	30	org/lwjgl/LWJGLException
    //   3	19	61	finally
    //   24	56	61	finally
    //   61	62	61	finally
  }
  
  private static native int nGetPbufferCapabilities(long paramLong, int paramInt);
  
  public boolean isBufferLost(PeerInfo handle)
  {
    return false;
  }
  
  public PeerInfo createPbuffer(int width, int height, PixelFormat pixel_format, ContextAttribs attribs, IntBuffer pixelFormatCaps, IntBuffer pBufferAttribs)
    throws LWJGLException
  {
    return new LinuxPbufferPeerInfo(width, height, pixel_format);
  }
  
  public void setPbufferAttrib(PeerInfo handle, int attrib, int value) {
    throw new UnsupportedOperationException();
  }
  
  public void bindTexImageToPbuffer(PeerInfo handle, int buffer) {
    throw new UnsupportedOperationException();
  }
  
  public void releaseTexImageFromPbuffer(PeerInfo handle, int buffer) {
    throw new UnsupportedOperationException();
  }
  

  private boolean dirty;
  
  private boolean close_requested;
  private long current_cursor;
  private long blank_cursor;
  private boolean mouseInside;
  
  private static ByteBuffer convertIcons(ByteBuffer[] icons)
  {
    int bufferSize = 0;
    

    for (ByteBuffer icon : icons) {
      int size = icon.limit() / 4;
      int dimension = (int)Math.sqrt(size);
      if (dimension > 0) {
        bufferSize += 8;
        bufferSize += dimension * dimension * 4;
      }
    }
    
    if (bufferSize == 0) { return null;
    }
    ByteBuffer icon_argb = BufferUtils.createByteBuffer(bufferSize);
    icon_argb.order(ByteOrder.BIG_ENDIAN);
    
    for (ByteBuffer icon : icons) {
      int size = icon.limit() / 4;
      int dimension = (int)Math.sqrt(size);
      
      icon_argb.putInt(dimension);
      icon_argb.putInt(dimension);
      
      for (int y = 0; y < dimension; y++) {
        for (int x = 0; x < dimension; x++)
        {
          byte r = icon.get(x * 4 + y * dimension * 4);
          byte g = icon.get(x * 4 + y * dimension * 4 + 1);
          byte b = icon.get(x * 4 + y * dimension * 4 + 2);
          byte a = icon.get(x * 4 + y * dimension * 4 + 3);
          
          icon_argb.put(a);
          icon_argb.put(r);
          icon_argb.put(g);
          icon_argb.put(b);
        }
      }
    }
    
    return icon_argb;
  }
  
  private boolean resizable;
  private boolean resized;
  private int window_x;
  private int window_y;
  private int window_width;
  private int window_height;
  /* Error */
  public int setIcon(ByteBuffer[] icons)
  {
    // Byte code:
    //   0: invokestatic 146	org/lwjgl/opengl/LinuxDisplay:lockAWT	()V
    //   3: invokestatic 149	org/lwjgl/opengl/LinuxDisplay:incDisplay	()V
    //   6: aload_1
    //   7: invokestatic 1122	org/lwjgl/opengl/LinuxDisplay:convertIcons	([Ljava/nio/ByteBuffer;)Ljava/nio/ByteBuffer;
    //   10: astore_2
    //   11: aload_2
    //   12: ifnonnull +13 -> 25
    //   15: iconst_0
    //   16: istore_3
    //   17: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   20: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   23: iload_3
    //   24: ireturn
    //   25: invokestatic 157	org/lwjgl/opengl/LinuxDisplay:getDisplay	()J
    //   28: invokestatic 348	org/lwjgl/opengl/LinuxDisplay:getWindow	()J
    //   31: aload_2
    //   32: aload_2
    //   33: invokevirtual 1125	java/nio/ByteBuffer:capacity	()I
    //   36: invokestatic 1129	org/lwjgl/opengl/LinuxDisplay:nSetWindowIcon	(JJLjava/nio/ByteBuffer;I)V
    //   39: aload_1
    //   40: arraylength
    //   41: istore_3
    //   42: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   45: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   48: iload_3
    //   49: ireturn
    //   50: astore 4
    //   52: invokestatic 168	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   55: aload 4
    //   57: athrow
    //   58: astore_2
    //   59: new 202	java/lang/StringBuilder
    //   62: dup
    //   63: invokespecial 203	java/lang/StringBuilder:<init>	()V
    //   66: ldc_w 1131
    //   69: invokevirtual 209	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   72: aload_2
    //   73: invokevirtual 212	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   76: invokevirtual 216	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   79: invokestatic 183	org/lwjgl/LWJGLUtil:log	(Ljava/lang/CharSequence;)V
    //   82: iconst_0
    //   83: istore_3
    //   84: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   87: iload_3
    //   88: ireturn
    //   89: astore 5
    //   91: invokestatic 171	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   94: aload 5
    //   96: athrow
    // Line number table:
    //   Java source line #1430	-> byte code offset #0
    //   Java source line #1432	-> byte code offset #3
    //   Java source line #1435	-> byte code offset #6
    //   Java source line #1436	-> byte code offset #11
    //   Java source line #1440	-> byte code offset #17
    //   Java source line #1446	-> byte code offset #20
    //   Java source line #1437	-> byte code offset #25
    //   Java source line #1438	-> byte code offset #39
    //   Java source line #1440	-> byte code offset #42
    //   Java source line #1446	-> byte code offset #45
    //   Java source line #1440	-> byte code offset #50
    //   Java source line #1442	-> byte code offset #58
    //   Java source line #1443	-> byte code offset #59
    //   Java source line #1444	-> byte code offset #82
    //   Java source line #1446	-> byte code offset #84
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	97	0	this	LinuxDisplay
    //   0	97	1	icons	ByteBuffer[]
    //   10	23	2	icons_data	ByteBuffer
    //   58	15	2	e	LWJGLException
    //   16	72	3	i	int
    //   50	6	4	localObject1	Object
    //   89	6	5	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   6	17	50	finally
    //   25	42	50	finally
    //   50	52	50	finally
    //   3	20	58	org/lwjgl/LWJGLException
    //   25	45	58	org/lwjgl/LWJGLException
    //   50	58	58	org/lwjgl/LWJGLException
    //   3	20	89	finally
    //   25	45	89	finally
    //   50	84	89	finally
    //   89	91	89	finally
  }
  
  private static native void nSetWindowIcon(long paramLong1, long paramLong2, ByteBuffer paramByteBuffer, int paramInt);
  
  public int getX()
  {
    return window_x;
  }
  
  public int getY() {
    return window_y;
  }
  
  public int getWidth() {
    return window_width;
  }
  
  public int getHeight() {
    return window_height;
  }
  
  public boolean isInsideWindow() {
    return mouseInside;
  }
  
  public void setResizable(boolean resizable) {
    if (this.resizable == resizable) {
      return;
    }
    
    this.resizable = resizable;
    nSetWindowSize(getDisplay(), getWindow(), window_width, window_height, resizable);
  }
  
  public boolean wasResized() {
    if (resized) {
      resized = false;
      return true;
    }
    
    return false;
  }
  
  public float getPixelScaleFactor() {
    return 1.0F;
  }
  
  private Canvas parent;
  private long parent_window;
  private static boolean xembedded;
  private long parent_proxy_focus_window;
  private boolean parent_focused;
  private boolean parent_focus_changed;
  private long last_window_focus;
  private LinuxKeyboard keyboard;
  private LinuxMouse mouse;
  private String wm_class;
  private final FocusListener focus_listener;
  private static final class Compiz {
    private static boolean applyFix;
    private static Provider provider;
    
    private Compiz() {}
    
    static void init() {
      if (Display.getPrivilegedBoolean("org.lwjgl.opengl.Window.nocompiz_lfs")) {
        return;
      }
      AccessController.doPrivileged(new PrivilegedAction()
      {
        public Object run() {
          try {
            if (!LinuxDisplay.Compiz.isProcessActive("compiz")) {
              Object localObject1 = null;
              
























































































              return null;
            }
            LinuxDisplay.Compiz.access$302(null);
            
            String providerName = null;
            

            if (LinuxDisplay.Compiz.isProcessActive("dbus-daemon")) {
              providerName = "Dbus";
              LinuxDisplay.Compiz.access$302(new LinuxDisplay.Compiz.Provider()
              {
                private static final String KEY = "/org/freedesktop/compiz/workarounds/allscreens/legacy_fullscreen";
                
                public boolean hasLegacyFullscreenSupport() throws LWJGLException {
                  List output = LinuxDisplay.Compiz.run(new String[] { "dbus-send", "--print-reply", "--type=method_call", "--dest=org.freedesktop.compiz", "/org/freedesktop/compiz/workarounds/allscreens/legacy_fullscreen", "org.freedesktop.compiz.get" });
                  


                  if ((output == null) || (output.size() < 2)) {
                    throw new LWJGLException("Invalid Dbus reply.");
                  }
                  String line = (String)output.get(0);
                  
                  if (!line.startsWith("method return")) {
                    throw new LWJGLException("Invalid Dbus reply.");
                  }
                  line = ((String)output.get(1)).trim();
                  if ((!line.startsWith("boolean")) || (line.length() < 12)) {
                    throw new LWJGLException("Invalid Dbus reply.");
                  }
                  return "true".equalsIgnoreCase(line.substring("boolean".length() + 1));
                }
                
                public void setLegacyFullscreenSupport(boolean state) throws LWJGLException {
                  if (LinuxDisplay.Compiz.run(new String[] { "dbus-send", "--type=method_call", "--dest=org.freedesktop.compiz", "/org/freedesktop/compiz/workarounds/allscreens/legacy_fullscreen", "org.freedesktop.compiz.set", "boolean:" + Boolean.toString(state) }) == null)
                  {

                    throw new LWJGLException("Failed to apply Compiz LFS workaround.");
                  }
                }
              });
            } else {
              try {
                Runtime.getRuntime().exec("gconftool");
                
                providerName = "gconftool";
                LinuxDisplay.Compiz.access$302(new LinuxDisplay.Compiz.Provider()
                {
                  private static final String KEY = "/apps/compiz/plugins/workarounds/allscreens/options/legacy_fullscreen";
                  
                  public boolean hasLegacyFullscreenSupport() throws LWJGLException {
                    List output = LinuxDisplay.Compiz.run(new String[] { "gconftool", "-g", "/apps/compiz/plugins/workarounds/allscreens/options/legacy_fullscreen" });
                    


                    if ((output == null) || (output.size() == 0)) {
                      throw new LWJGLException("Invalid gconftool reply.");
                    }
                    return Boolean.parseBoolean(((String)output.get(0)).trim());
                  }
                  
                  public void setLegacyFullscreenSupport(boolean state) throws LWJGLException {
                    if (LinuxDisplay.Compiz.run(new String[] { "gconftool", "-s", "/apps/compiz/plugins/workarounds/allscreens/options/legacy_fullscreen", "-s", Boolean.toString(state), "-t", "bool" }) == null)
                    {

                      throw new LWJGLException("Failed to apply Compiz LFS workaround.");
                    }
                    if (state)
                    {
                      try
                      {
                        Thread.sleep(200L);
                      } catch (InterruptedException e) {
                        e.printStackTrace();
                      }
                    }
                  }
                });
              }
              catch (IOException e) {}
            }
            

            if ((LinuxDisplay.Compiz.provider != null) && (!LinuxDisplay.Compiz.provider.hasLegacyFullscreenSupport())) {
              LinuxDisplay.Compiz.access$502(true);
              LWJGLUtil.log("Using " + providerName + " to apply Compiz LFS workaround.");
            }
            


            return null;
          }
          catch (LWJGLException e)
          {
            e = 
            

              e;return null; } finally {} return null;
        }
      });
    }
    
    static void setLegacyFullscreenSupport(boolean enabled)
    {
      if (!applyFix) {
        return;
      }
      AccessController.doPrivileged(new PrivilegedAction() {
        public Object run() {
          try {
            LinuxDisplay.Compiz.provider.setLegacyFullscreenSupport(val$enabled);
          } catch (LWJGLException e) {
            LWJGLUtil.log("Failed to change Compiz Legacy Fullscreen Support. Reason: " + e.getMessage());
          }
          return null;
        }
      });
    }
    
    private static List<String> run(String... command) throws LWJGLException {
      List<String> output = new ArrayList();
      try
      {
        Process p = Runtime.getRuntime().exec(command);
        try {
          int exitValue = p.waitFor();
          if (exitValue != 0)
            return null;
        } catch (InterruptedException e) {
          throw new LWJGLException("Process interrupted.", e);
        }
        
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        
        String line;
        while ((line = br.readLine()) != null) {
          output.add(line);
        }
        br.close();
      } catch (IOException e) {
        throw new LWJGLException("Process failed.", e);
      }
      
      return output;
    }
    
    private static boolean isProcessActive(String processName) throws LWJGLException {
      List<String> output = run(new String[] { "ps", "-C", processName });
      if (output == null) {
        return false;
      }
      for (String line : output) {
        if (line.contains(processName)) {
          return true;
        }
      }
      return false;
    }
    
    private static abstract interface Provider
    {
      public abstract boolean hasLegacyFullscreenSupport()
        throws LWJGLException;
      
      public abstract void setLegacyFullscreenSupport(boolean paramBoolean)
        throws LWJGLException;
    }
  }
}
