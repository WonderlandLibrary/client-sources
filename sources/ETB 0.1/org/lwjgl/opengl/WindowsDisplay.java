package org.lwjgl.opengl;

import java.awt.Canvas;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.SwingUtilities;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.MemoryUtil;
import org.lwjgl.input.Mouse;













































final class WindowsDisplay
  implements DisplayImplementation
{
  private static final int GAMMA_LENGTH = 256;
  private static final int WM_WINDOWPOSCHANGED = 71;
  private static final int WM_MOVE = 3;
  private static final int WM_CANCELMODE = 31;
  private static final int WM_MOUSEMOVE = 512;
  private static final int WM_LBUTTONDOWN = 513;
  private static final int WM_LBUTTONUP = 514;
  private static final int WM_LBUTTONDBLCLK = 515;
  private static final int WM_RBUTTONDOWN = 516;
  private static final int WM_RBUTTONUP = 517;
  private static final int WM_RBUTTONDBLCLK = 518;
  private static final int WM_MBUTTONDOWN = 519;
  private static final int WM_MBUTTONUP = 520;
  private static final int WM_MBUTTONDBLCLK = 521;
  private static final int WM_XBUTTONDOWN = 523;
  private static final int WM_XBUTTONUP = 524;
  private static final int WM_XBUTTONDBLCLK = 525;
  private static final int WM_MOUSEWHEEL = 522;
  private static final int WM_CAPTURECHANGED = 533;
  private static final int WM_MOUSELEAVE = 675;
  private static final int WM_ENTERSIZEMOVE = 561;
  private static final int WM_EXITSIZEMOVE = 562;
  private static final int WM_SIZING = 532;
  private static final int WM_KEYDOWN = 256;
  private static final int WM_KEYUP = 257;
  private static final int WM_SYSKEYUP = 261;
  private static final int WM_SYSKEYDOWN = 260;
  private static final int WM_SYSCHAR = 262;
  private static final int WM_CHAR = 258;
  private static final int WM_GETICON = 127;
  private static final int WM_SETICON = 128;
  private static final int WM_SETCURSOR = 32;
  private static final int WM_MOUSEACTIVATE = 33;
  private static final int WM_QUIT = 18;
  private static final int WM_SYSCOMMAND = 274;
  private static final int WM_PAINT = 15;
  private static final int WM_KILLFOCUS = 8;
  private static final int WM_SETFOCUS = 7;
  private static final int SC_SIZE = 61440;
  private static final int SC_MOVE = 61456;
  private static final int SC_MINIMIZE = 61472;
  private static final int SC_MAXIMIZE = 61488;
  private static final int SC_NEXTWINDOW = 61504;
  private static final int SC_PREVWINDOW = 61520;
  private static final int SC_CLOSE = 61536;
  private static final int SC_VSCROLL = 61552;
  private static final int SC_HSCROLL = 61568;
  private static final int SC_MOUSEMENU = 61584;
  private static final int SC_KEYMENU = 61696;
  private static final int SC_ARRANGE = 61712;
  private static final int SC_RESTORE = 61728;
  private static final int SC_TASKLIST = 61744;
  private static final int SC_SCREENSAVE = 61760;
  private static final int SC_HOTKEY = 61776;
  private static final int SC_DEFAULT = 61792;
  private static final int SC_MONITORPOWER = 61808;
  private static final int SC_CONTEXTHELP = 61824;
  private static final int SC_SEPARATOR = 61455;
  static final int SM_CXCURSOR = 13;
  static final int SM_CYCURSOR = 14;
  static final int SM_CMOUSEBUTTONS = 43;
  static final int SM_MOUSEWHEELPRESENT = 75;
  private static final int SIZE_RESTORED = 0;
  private static final int SIZE_MINIMIZED = 1;
  private static final int SIZE_MAXIMIZED = 2;
  private static final int WM_SIZE = 5;
  private static final int WM_ACTIVATE = 6;
  private static final int WA_INACTIVE = 0;
  private static final int WA_ACTIVE = 1;
  private static final int WA_CLICKACTIVE = 2;
  private static final int SW_NORMAL = 1;
  private static final int SW_SHOWMINNOACTIVE = 7;
  private static final int SW_SHOWDEFAULT = 10;
  private static final int SW_RESTORE = 9;
  private static final int SW_MAXIMIZE = 3;
  private static final int ICON_SMALL = 0;
  private static final int ICON_BIG = 1;
  private static final IntBuffer rect_buffer = BufferUtils.createIntBuffer(4);
  private static final Rect rect = new Rect(null);
  
  private static final long HWND_TOP = 0L;
  
  private static final long HWND_BOTTOM = 1L;
  
  private static final long HWND_TOPMOST = -1L;
  
  private static final long HWND_NOTOPMOST = -2L;
  
  private static final int SWP_NOSIZE = 1;
  
  private static final int SWP_NOMOVE = 2;
  
  private static final int SWP_NOZORDER = 4;
  
  private static final int SWP_FRAMECHANGED = 32;
  
  private static final int GWL_STYLE = -16;
  
  private static final int GWL_EXSTYLE = -20;
  
  private static final int WS_THICKFRAME = 262144;
  
  private static final int WS_MAXIMIZEBOX = 65536;
  
  private static final int HTCLIENT = 1;
  
  private static final int MK_XBUTTON1 = 32;
  
  private static final int MK_XBUTTON2 = 64;
  
  private static final int XBUTTON1 = 1;
  
  private static final int XBUTTON2 = 2;
  private static WindowsDisplay current_display;
  private static boolean cursor_clipped;
  private WindowsDisplayPeerInfo peer_info;
  private Object current_cursor;
  private static boolean hasParent;
  private Canvas parent;
  private long parent_hwnd;
  private FocusAdapter parent_focus_tracker;
  private AtomicBoolean parent_focused;
  private WindowsKeyboard keyboard;
  private WindowsMouse mouse;
  private boolean close_requested;
  private boolean is_dirty;
  private ByteBuffer current_gamma;
  private ByteBuffer saved_gamma;
  private DisplayMode current_mode;
  private boolean mode_set;
  private boolean isMinimized;
  private boolean isFocused;
  private boolean redoMakeContextCurrent;
  private boolean inAppActivate;
  private boolean resized;
  private boolean resizable;
  private int x;
  private int y;
  private int width;
  private int height;
  private long hwnd;
  private long hdc;
  private long small_icon;
  private long large_icon;
  private boolean iconsLoaded;
  private int captureMouse = -1;
  private boolean mouseInside;
  
  static {
    try {
      Method windowProc = WindowsDisplay.class.getDeclaredMethod("handleMessage", new Class[] { Long.TYPE, Integer.TYPE, Long.TYPE, Long.TYPE, Long.TYPE });
      setWindowProc(windowProc);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }
  
  WindowsDisplay() {
    current_display = this;
  }
  
  public void createWindow(DrawableLWJGL drawable, DisplayMode mode, Canvas parent, int x, int y) throws LWJGLException {
    this.parent = parent;
    hasParent = parent != null;
    parent_hwnd = (parent != null ? getHwnd(parent) : 0L);
    hwnd = nCreateWindow(x, y, mode.getWidth(), mode.getHeight(), (Display.isFullscreen()) || (isUndecorated()), parent != null, parent_hwnd);
    if ((Display.isResizable()) && (parent == null)) {
      setResizable(true);
    }
    
    if (hwnd == 0L) {
      throw new LWJGLException("Failed to create window");
    }
    hdc = getDC(hwnd);
    if (hdc == 0L) {
      nDestroyWindow(hwnd);
      throw new LWJGLException("Failed to get dc");
    }
    try
    {
      if ((drawable instanceof DrawableGL)) {
        int format = WindowsPeerInfo.choosePixelFormat(getHdc(), 0, 0, (PixelFormat)drawable.getPixelFormat(), null, true, true, false, true);
        WindowsPeerInfo.setPixelFormat(getHdc(), format);
      } else {
        peer_info = new WindowsDisplayPeerInfo(true);
        ((DrawableGLES)drawable).initialize(hwnd, hdc, 4, (org.lwjgl.opengles.PixelFormat)drawable.getPixelFormat());
      }
      peer_info.initDC(getHwnd(), getHdc());
      showWindow(getHwnd(), 10);
      
      updateWidthAndHeight();
      
      if (parent == null) {
        setForegroundWindow(getHwnd());
      } else {
        parent_focused = new AtomicBoolean(false);
        parent.addFocusListener(this.parent_focus_tracker = new FocusAdapter() {
          public void focusGained(FocusEvent e) {
            parent_focused.set(true);
            WindowsDisplay.this.clearAWTFocus();
          }
        });
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            WindowsDisplay.this.clearAWTFocus();
          }
        });
      }
      grabFocus();
    } catch (LWJGLException e) {
      nReleaseDC(hwnd, hdc);
      nDestroyWindow(hwnd);
      throw e;
    }
  }
  
  private void updateWidthAndHeight() {
    getClientRect(hwnd, rect_buffer);
    rect.copyFromBuffer(rect_buffer);
    width = (rectright - rectleft);
    height = (rectbottom - recttop);
  }
  

  private static boolean isUndecorated()
  {
    return Display.getPrivilegedBoolean("org.lwjgl.opengl.Window.undecorated");
  }
  
  private static long getHwnd(Canvas parent) throws LWJGLException {
    AWTCanvasImplementation awt_impl = AWTGLCanvas.createImplementation();
    WindowsPeerInfo parent_peer_info = (WindowsPeerInfo)awt_impl.createPeerInfo(parent, null, null);
    ByteBuffer parent_peer_info_handle = parent_peer_info.lockAndGetHandle();
    try {
      return parent_peer_info.getHwnd();
    } finally {
      parent_peer_info.unlock();
    }
  }
  
  public void destroyWindow() {
    if (parent != null) {
      parent.removeFocusListener(parent_focus_tracker);
      parent_focus_tracker = null;
    }
    
    nReleaseDC(hwnd, hdc);
    nDestroyWindow(hwnd);
    freeLargeIcon();
    freeSmallIcon();
    resetCursorClipping();
    

    close_requested = false;
    is_dirty = false;
    isMinimized = false;
    isFocused = false;
    redoMakeContextCurrent = false;
    mouseInside = false;
  }
  
  static void resetCursorClipping()
  {
    if (cursor_clipped) {
      try {
        clipCursor(null);
      } catch (LWJGLException e) {
        LWJGLUtil.log("Failed to reset cursor clipping: " + e);
      }
      cursor_clipped = false;
    }
  }
  
  private static void getGlobalClientRect(long hwnd, Rect rect) {
    rect_buffer.put(0, 0).put(1, 0);
    clientToScreen(hwnd, rect_buffer);
    int offset_x = rect_buffer.get(0);
    int offset_y = rect_buffer.get(1);
    getClientRect(hwnd, rect_buffer);
    rect.copyFromBuffer(rect_buffer);
    rect.offset(offset_x, offset_y);
  }
  
  static void setupCursorClipping(long hwnd) throws LWJGLException {
    cursor_clipped = true;
    getGlobalClientRect(hwnd, rect);
    rect.copyToBuffer(rect_buffer);
    clipCursor(rect_buffer);
  }
  
  public void switchDisplayMode(DisplayMode mode) throws LWJGLException
  {
    nSwitchDisplayMode(mode);
    current_mode = mode;
    mode_set = true;
  }
  



  private void appActivate(boolean active, long millis)
  {
    if (inAppActivate) {
      return;
    }
    inAppActivate = true;
    isFocused = active;
    if (active) {
      if (Display.isFullscreen()) {
        restoreDisplayMode();
      }
      if (parent == null) {
        setForegroundWindow(getHwnd());
      }
      setFocus(getHwnd());
      redoMakeContextCurrent = true;
    } else {
      if (keyboard != null)
        keyboard.releaseAll(millis);
      if (Display.isFullscreen()) {
        showWindow(getHwnd(), 7);
        resetDisplayMode();
      }
    }
    updateCursor();
    inAppActivate = false;
  }
  



  private void clearAWTFocus()
  {
    parent.setFocusable(false);
    parent.setFocusable(true);
    

    KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
  }
  
  private void grabFocus() {
    if (parent == null) {
      setFocus(getHwnd());
    } else
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          parent.requestFocus();
        }
      });
  }
  
  private void restoreDisplayMode() {
    try {
      doSetGammaRamp(current_gamma);
    } catch (LWJGLException e) {
      LWJGLUtil.log("Failed to restore gamma: " + e.getMessage());
    }
    
    if (!mode_set) {
      mode_set = true;
      try {
        nSwitchDisplayMode(current_mode);
      } catch (LWJGLException e) {
        LWJGLUtil.log("Failed to restore display mode: " + e.getMessage());
      }
    }
  }
  
  public void resetDisplayMode() {
    try {
      doSetGammaRamp(saved_gamma);
    } catch (LWJGLException e) {
      LWJGLUtil.log("Failed to reset gamma ramp: " + e.getMessage());
    }
    current_gamma = saved_gamma;
    if (mode_set) {
      mode_set = false;
      nResetDisplayMode();
    }
    resetCursorClipping();
  }
  
  public int getGammaRampLength()
  {
    return 256;
  }
  
  public void setGammaRamp(FloatBuffer gammaRamp) throws LWJGLException {
    doSetGammaRamp(convertToNativeRamp(gammaRamp));
  }
  
  private void doSetGammaRamp(ByteBuffer native_gamma)
    throws LWJGLException
  {
    nSetGammaRamp(native_gamma);
    current_gamma = native_gamma;
  }
  
  public String getAdapter()
  {
    try {
      String maxObjNo = WindowsRegistry.queryRegistrationKey(3, "HARDWARE\\DeviceMap\\Video", "MaxObjectNumber");
      


      int maxObjectNumber = maxObjNo.charAt(0);
      String vga_driver_value = "";
      for (int i = 0; i < maxObjectNumber; i++) {
        String adapter_string = WindowsRegistry.queryRegistrationKey(3, "HARDWARE\\DeviceMap\\Video", "\\Device\\Video" + i);
        


        String root_key = "\\registry\\machine\\";
        if (adapter_string.toLowerCase().startsWith(root_key)) {
          String driver_value = WindowsRegistry.queryRegistrationKey(3, adapter_string.substring(root_key.length()), "InstalledDisplayDrivers");
          


          if (driver_value.toUpperCase().startsWith("VGA")) {
            vga_driver_value = driver_value;
          } else if ((!driver_value.toUpperCase().startsWith("RDP")) && (!driver_value.toUpperCase().startsWith("NMNDD"))) {
            return driver_value;
          }
        }
      }
      if (!vga_driver_value.equals("")) {
        return vga_driver_value;
      }
    } catch (LWJGLException e) {
      LWJGLUtil.log("Exception occurred while querying registry: " + e);
    }
    return null;
  }
  
  public String getVersion() {
    String driver = getAdapter();
    if (driver != null) {
      String[] drivers = driver.split(",");
      if (drivers.length > 0) {
        WindowsFileVersion version = nGetVersion(drivers[0] + ".dll");
        if (version != null)
          return version.toString();
      }
    }
    return null;
  }
  
  public DisplayMode init() throws LWJGLException
  {
    current_gamma = (this.saved_gamma = getCurrentGammaRamp());
    return this.current_mode = getCurrentDisplayMode();
  }
  
  public void setTitle(String title)
  {
    ByteBuffer buffer = MemoryUtil.encodeUTF16(title);
    nSetTitle(hwnd, MemoryUtil.getAddress0(buffer));
  }
  
  public boolean isCloseRequested()
  {
    boolean saved = close_requested;
    close_requested = false;
    return saved;
  }
  
  public boolean isVisible() {
    return !isMinimized;
  }
  
  public boolean isActive() {
    return isFocused;
  }
  
  public boolean isDirty() {
    boolean saved = is_dirty;
    is_dirty = false;
    return saved;
  }
  
  public PeerInfo createPeerInfo(PixelFormat pixel_format, ContextAttribs attribs) throws LWJGLException {
    peer_info = new WindowsDisplayPeerInfo(false);
    return peer_info;
  }
  
  public void update()
  {
    
    if ((!isFocused) && (parent != null) && (parent_focused.compareAndSet(true, false))) {
      setFocus(getHwnd());
    }
    
    if (redoMakeContextCurrent) {
      redoMakeContextCurrent = false;
      



      try
      {
        Context context = ((DrawableLWJGL)Display.getDrawable()).getContext();
        if ((context != null) && (context.isCurrent()))
          context.makeCurrent();
      } catch (LWJGLException e) {
        LWJGLUtil.log("Exception occurred while trying to make context current: " + e);
      }
    }
  }
  
  public void reshape(int x, int y, int width, int height)
  {
    nReshape(getHwnd(), x, y, width, height, (Display.isFullscreen()) || (isUndecorated()), parent != null);
  }
  


  public boolean hasWheel()
  {
    return mouse.hasWheel();
  }
  
  public int getButtonCount() {
    return mouse.getButtonCount();
  }
  
  public void createMouse() throws LWJGLException {
    mouse = new WindowsMouse(getHwnd());
  }
  
  public void destroyMouse() {
    if (mouse != null)
      mouse.destroy();
    mouse = null;
  }
  
  public void pollMouse(IntBuffer coord_buffer, ByteBuffer buttons) {
    mouse.poll(coord_buffer, buttons, this);
  }
  
  public void readMouse(ByteBuffer buffer) {
    mouse.read(buffer);
  }
  
  public void grabMouse(boolean grab) {
    mouse.grab(grab);
    updateCursor();
  }
  
  public int getNativeCursorCapabilities() {
    return 1;
  }
  
  public void setCursorPosition(int x, int y) {
    getGlobalClientRect(getHwnd(), rect);
    int transformed_x = rectleft + x;
    int transformed_y = rectbottom - 1 - y;
    nSetCursorPosition(transformed_x, transformed_y);
    setMousePosition(x, y);
  }
  
  public void setNativeCursor(Object handle) throws LWJGLException
  {
    current_cursor = handle;
    updateCursor();
  }
  
  private void updateCursor() {
    try {
      if ((mouse != null) && (shouldGrab())) {
        centerCursor(hwnd);
        nSetNativeCursor(getHwnd(), mouse.getBlankCursor());
      } else {
        nSetNativeCursor(getHwnd(), current_cursor);
      }
    } catch (LWJGLException e) { LWJGLUtil.log("Failed to update cursor: " + e);
    }
    updateClipping();
  }
  
  public int getMinCursorSize()
  {
    return getSystemMetrics(13);
  }
  
  public int getMaxCursorSize() {
    return getSystemMetrics(13);
  }
  



  private long getHwnd()
  {
    return hwnd;
  }
  
  private long getHdc() {
    return hdc;
  }
  



  static void centerCursor(long hwnd)
  {
    if ((getForegroundWindow() != hwnd) && (!hasParent))
      return;
    getGlobalClientRect(hwnd, rect);
    int local_offset_x = rectleft;
    int local_offset_y = recttop;
    



    int center_x = (rectleft + rectright) / 2;
    int center_y = (recttop + rectbottom) / 2;
    nSetCursorPosition(center_x, center_y);
    int local_x = center_x - local_offset_x;
    int local_y = center_y - local_offset_y;
    if (current_display != null)
      current_display.setMousePosition(local_x, transformY(hwnd, local_y));
  }
  
  private void setMousePosition(int x, int y) {
    if (mouse != null) {
      mouse.setPosition(x, y);
    }
  }
  
  public void createKeyboard() throws LWJGLException {
    keyboard = new WindowsKeyboard();
  }
  
  public void destroyKeyboard() {
    keyboard = null;
  }
  
  public void pollKeyboard(ByteBuffer keyDownBuffer) {
    keyboard.poll(keyDownBuffer);
  }
  
  public void readKeyboard(ByteBuffer buffer) {
    keyboard.read(buffer);
  }
  


  public Object createCursor(int width, int height, int xHotspot, int yHotspot, int numImages, IntBuffer images, IntBuffer delays)
    throws LWJGLException
  {
    return doCreateCursor(width, height, xHotspot, yHotspot, numImages, images, delays);
  }
  
  static Object doCreateCursor(int width, int height, int xHotspot, int yHotspot, int numImages, IntBuffer images, IntBuffer delays) throws LWJGLException {
    return nCreateCursor(width, height, xHotspot, yHotspot, numImages, images, images.position(), delays, delays != null ? delays.position() : -1);
  }
  
  public void destroyCursor(Object cursorHandle) {
    doDestroyCursor(cursorHandle);
  }
  
  public int getPbufferCapabilities()
  {
    try
    {
      return nGetPbufferCapabilities(new PixelFormat(0, 0, 0, 0, 0, 0, 0, 0, false));
    } catch (LWJGLException e) {
      LWJGLUtil.log("Exception occurred while determining pbuffer capabilities: " + e); }
    return 0;
  }
  

  public boolean isBufferLost(PeerInfo handle)
  {
    return ((WindowsPbufferPeerInfo)handle).isBufferLost();
  }
  
  public PeerInfo createPbuffer(int width, int height, PixelFormat pixel_format, ContextAttribs attribs, IntBuffer pixelFormatCaps, IntBuffer pBufferAttribs)
    throws LWJGLException
  {
    return new WindowsPbufferPeerInfo(width, height, pixel_format, pixelFormatCaps, pBufferAttribs);
  }
  
  public void setPbufferAttrib(PeerInfo handle, int attrib, int value) {
    ((WindowsPbufferPeerInfo)handle).setPbufferAttrib(attrib, value);
  }
  
  public void bindTexImageToPbuffer(PeerInfo handle, int buffer) {
    ((WindowsPbufferPeerInfo)handle).bindTexImageToPbuffer(buffer);
  }
  
  public void releaseTexImageFromPbuffer(PeerInfo handle, int buffer) {
    ((WindowsPbufferPeerInfo)handle).releaseTexImageFromPbuffer(buffer);
  }
  
  private void freeSmallIcon() {
    if (small_icon != 0L) {
      destroyIcon(small_icon);
      small_icon = 0L;
    }
  }
  
  private void freeLargeIcon() {
    if (large_icon != 0L) {
      destroyIcon(large_icon);
      large_icon = 0L;
    }
  }
  











  public int setIcon(ByteBuffer[] icons)
  {
    boolean done_small = false;
    boolean done_large = false;
    int used = 0;
    
    int small_icon_size = 16;
    int large_icon_size = 32;
    for (ByteBuffer icon : icons) {
      int size = icon.limit() / 4;
      
      if (((int)Math.sqrt(size) == small_icon_size) && (!done_small)) {
        long small_new_icon = createIcon(small_icon_size, small_icon_size, icon.asIntBuffer());
        sendMessage(hwnd, 128L, 0L, small_new_icon);
        freeSmallIcon();
        small_icon = small_new_icon;
        used++;
        done_small = true;
      }
      if (((int)Math.sqrt(size) == large_icon_size) && (!done_large)) {
        long large_new_icon = createIcon(large_icon_size, large_icon_size, icon.asIntBuffer());
        sendMessage(hwnd, 128L, 1L, large_new_icon);
        freeLargeIcon();
        large_icon = large_new_icon;
        used++;
        done_large = true;
        




        iconsLoaded = false;
        

        long time = System.nanoTime();
        long MAX_WAIT = 500000000L;
        for (;;) {
          nUpdate();
          if ((iconsLoaded) || (MAX_WAIT < System.nanoTime() - time)) {
            break;
          }
          Thread.yield();
        }
      }
    }
    
    return used;
  }
  





  private void handleMouseButton(int button, int state, long millis)
  {
    if (mouse != null) {
      mouse.handleMouseButton((byte)button, (byte)state, millis);
      

      if ((captureMouse == -1) && (button != -1) && (state == 1)) {
        captureMouse = button;
        nSetCapture(hwnd);
      }
      

      if ((captureMouse != -1) && (button == captureMouse) && (state == 0)) {
        captureMouse = -1;
        nReleaseCapture();
      }
    }
  }
  
  private boolean shouldGrab() {
    return (!isMinimized) && (isFocused) && (Mouse.isGrabbed());
  }
  


  private void handleMouseScrolled(int amount, long millis)
  {
    if (mouse != null) {
      mouse.handleMouseScrolled(amount, millis);
    }
  }
  
  private void handleChar(long wParam, long lParam, long millis)
  {
    byte previous_state = (byte)(int)(lParam >>> 30 & 1L);
    byte state = (byte)(int)(1L - (lParam >>> 31 & 1L));
    boolean repeat = state == previous_state;
    if (keyboard != null)
      keyboard.handleChar((int)(wParam & 0xFFFF), millis, repeat);
  }
  
  private void handleKeyButton(long wParam, long lParam, long millis) {
    if (keyboard == null) {
      return;
    }
    byte previous_state = (byte)(int)(lParam >>> 30 & 1L);
    byte state = (byte)(int)(1L - (lParam >>> 31 & 1L));
    boolean repeat = state == previous_state;
    byte extended = (byte)(int)(lParam >>> 24 & 1L);
    int scan_code = (int)(lParam >>> 16 & 0xFF);
    
    keyboard.handleKey((int)wParam, scan_code, extended != 0, state, millis, repeat);
  }
  
  private static int transformY(long hwnd, int y) {
    getClientRect(hwnd, rect_buffer);
    rect.copyFromBuffer(rect_buffer);
    return rectbottom - recttop - 1 - y;
  }
  



  private static long handleMessage(long hwnd, int msg, long wParam, long lParam, long millis)
  {
    if (current_display != null) {
      return current_display.doHandleMessage(hwnd, msg, wParam, lParam, millis);
    }
    return defWindowProc(hwnd, msg, wParam, lParam);
  }
  

  private void updateClipping()
  {
    if (((Display.isFullscreen()) || ((mouse != null) && (mouse.isGrabbed()))) && (!isMinimized) && (isFocused) && ((getForegroundWindow() == getHwnd()) || (hasParent))) {
      try {
        setupCursorClipping(getHwnd());
      } catch (LWJGLException e) {
        LWJGLUtil.log("setupCursorClipping failed: " + e.getMessage());
      }
    } else {
      resetCursorClipping();
    }
  }
  
  private void setMinimized(boolean m) {
    if (m != isMinimized) {
      isMinimized = m;
      updateClipping();
    }
  }
  









  private long doHandleMessage(long hwnd, int msg, long wParam, long lParam, long millis)
  {
    if ((parent != null) && (!isFocused)) {
      switch (msg) {
      case 513: 
      case 516: 
      case 519: 
      case 523: 
        sendMessage(parent_hwnd, msg, wParam, lParam);
      }
      
    }
    switch (msg)
    {









    case 6: 
      return 0L;
    case 5: 
      switch ((int)wParam) {
      case 0: 
      case 2: 
        resized = true;
        updateWidthAndHeight();
        setMinimized(false);
        break;
      case 1: 
        setMinimized(true);
      }
      
      break;
    case 532: 
      resized = true;
      updateWidthAndHeight();
      break;
    case 8: 
      appActivate(false, millis);
      return 0L;
    case 7: 
      appActivate(true, millis);
      return 0L;
    case 33: 
      if (parent != null) {
        if (!isFocused)
          grabFocus();
        return 3L;
      }
      break;
    case 512: 
      if (mouse != null) {
        int xPos = (short)(int)(lParam & 0xFFFF);
        int yPos = transformY(getHwnd(), (short)(int)(lParam >>> 16));
        mouse.handleMouseMoved(xPos, yPos, millis);
      }
      if (!mouseInside) {
        mouseInside = true;
        updateCursor();
        nTrackMouseEvent(hwnd);
      }
      return 0L;
    case 522: 
      int dwheel = (short)(int)(wParam >> 16 & 0xFFFF);
      handleMouseScrolled(dwheel, millis);
      return 0L;
    case 513: 
      handleMouseButton(0, 1, millis);
      return 0L;
    case 514: 
      handleMouseButton(0, 0, millis);
      return 0L;
    case 516: 
      handleMouseButton(1, 1, millis);
      return 0L;
    case 517: 
      handleMouseButton(1, 0, millis);
      return 0L;
    case 519: 
      handleMouseButton(2, 1, millis);
      return 0L;
    case 520: 
      handleMouseButton(2, 0, millis);
      return 0L;
    case 524: 
      if (wParam >> 16 == 1L) {
        handleMouseButton(3, 0, millis);
      } else {
        handleMouseButton(4, 0, millis);
      }
      return 1L;
    case 523: 
      if ((wParam & 0xFF) == 32L) {
        handleMouseButton(3, 1, millis);
      } else {
        handleMouseButton(4, 1, millis);
      }
      return 1L;
    case 258: 
    case 262: 
      handleChar(wParam, lParam, millis);
      return 0L;
    
    case 261: 
      if ((wParam == 18L) || (wParam == 121L)) {
        handleKeyButton(wParam, lParam, millis);
        return 0L;
      }
    

    case 257: 
      if ((wParam == 44L) && (keyboard != null) && (!keyboard.isKeyDown(183)))
      {

        long fake_lparam = lParam & 0x7FFFFFFF;
        
        fake_lparam &= 0xFFFFFFFFBFFFFFFF;
        handleKeyButton(wParam, fake_lparam, millis);
      }
    

    case 256: 
    case 260: 
      handleKeyButton(wParam, lParam, millis);
      break;
    case 18: 
      close_requested = true;
      return 0L;
    case 274: 
      switch ((int)(wParam & 0xFFF0)) {
      case 61760: 
      case 61808: 
        return 0L;
      case 61536: 
        close_requested = true;
        return 0L;
      }
      break;
    case 15: 
      is_dirty = true;
      break;
    case 675: 
      mouseInside = false;
      break;
    case 31: 
      nReleaseCapture();
    
    case 533: 
      if (captureMouse != -1) {
        handleMouseButton(captureMouse, 0, millis);
        captureMouse = -1;
      }
      return 0L;
    case 71: 
      if (getWindowRect(hwnd, rect_buffer)) {
        rect.copyFromBuffer(rect_buffer);
        x = rectleft;
        y = recttop;
      } else {
        LWJGLUtil.log("WM_WINDOWPOSCHANGED: Unable to get window rect");
      }
      break;
    case 127: 
      iconsLoaded = true;
    }
    
    
    return defWindowProc(hwnd, msg, wParam, lParam);
  }
  

  public int getX()
  {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public int getWidth() {
    return width;
  }
  
  public int getHeight() {
    return height;
  }
  

  public boolean isInsideWindow()
  {
    return mouseInside;
  }
  
  public void setResizable(boolean resizable) {
    if (this.resizable == resizable) {
      return;
    }
    resized = false;
    this.resizable = resizable;
    
    int style = (int)getWindowLongPtr(hwnd, -16);
    int styleex = (int)getWindowLongPtr(hwnd, -20);
    

    setWindowLongPtr(hwnd, -16, style &= 0xFFFAFFFF);
    








    getGlobalClientRect(hwnd, rect);
    rect.copyToBuffer(rect_buffer);
    adjustWindowRectEx(rect_buffer, style, false, styleex);
    rect.copyFromBuffer(rect_buffer);
    

    setWindowPos(hwnd, 0L, rectleft, recttop, rectright - rectleft, rectbottom - recttop, 36L);
    







    updateWidthAndHeight();
  }
  

  public boolean wasResized()
  {
    if (resized) {
      resized = false;
      return true;
    }
    return false;
  }
  
  public float getPixelScaleFactor() {
    return 1.0F;
  }
  
  private static native long nCreateWindow(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2, long paramLong) throws LWJGLException;
  
  private static native void nReleaseDC(long paramLong1, long paramLong2);
  
  private static final class Rect
  {
    private Rect() {}
    
    public void copyToBuffer(IntBuffer buffer) {
      buffer.put(0, left).put(1, top).put(2, right).put(3, bottom);
    }
    
    public int left;
    public int top;
    public int right;
    public int bottom;
    public void copyFromBuffer(IntBuffer buffer) {
      left = buffer.get(0);
      top = buffer.get(1);
      right = buffer.get(2);
      bottom = buffer.get(3);
    }
    
    public void offset(int offset_x, int offset_y) {
      left += offset_x;
      top += offset_y;
      right += offset_x;
      bottom += offset_y;
    }
    
    public static void intersect(Rect r1, Rect r2, Rect dst) {
      left = Math.max(left, left);
      top = Math.max(top, top);
      right = Math.min(right, right);
      bottom = Math.min(bottom, bottom);
    }
    
    public String toString() {
      return "Rect: left = " + left + " top = " + top + " right = " + right + " bottom = " + bottom + ", width: " + (right - left) + ", height: " + (bottom - top);
    }
  }
  
  private static native void nDestroyWindow(long paramLong);
  
  private static native void clipCursor(IntBuffer paramIntBuffer)
    throws LWJGLException;
  
  private static native void nSwitchDisplayMode(DisplayMode paramDisplayMode)
    throws LWJGLException;
  
  private static native void showWindow(long paramLong, int paramInt);
  
  private static native void setForegroundWindow(long paramLong);
  
  private static native void setFocus(long paramLong);
  
  private static native void nResetDisplayMode();
  
  private static native ByteBuffer convertToNativeRamp(FloatBuffer paramFloatBuffer)
    throws LWJGLException;
  
  private static native ByteBuffer getCurrentGammaRamp()
    throws LWJGLException;
  
  private static native void nSetGammaRamp(ByteBuffer paramByteBuffer)
    throws LWJGLException;
  
  private native WindowsFileVersion nGetVersion(String paramString);
  
  private static native DisplayMode getCurrentDisplayMode()
    throws LWJGLException;
  
  private static native void nSetTitle(long paramLong1, long paramLong2);
  
  private static native void nUpdate();
  
  private static native void nReshape(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2);
  
  public native DisplayMode[] getAvailableDisplayModes()
    throws LWJGLException;
  
  private static native void nSetCursorPosition(int paramInt1, int paramInt2);
  
  static native void nSetNativeCursor(long paramLong, Object paramObject)
    throws LWJGLException;
  
  static native int getSystemMetrics(int paramInt);
  
  private static native long getDllInstance();
  
  private static native long getDC(long paramLong);
  
  private static native long getDesktopWindow();
  
  private static native long getForegroundWindow();
  
  public static native ByteBuffer nCreateCursor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, IntBuffer paramIntBuffer1, int paramInt6, IntBuffer paramIntBuffer2, int paramInt7)
    throws LWJGLException;
  
  static native void doDestroyCursor(Object paramObject);
  
  private native int nGetPbufferCapabilities(PixelFormat paramPixelFormat)
    throws LWJGLException;
  
  private static native long createIcon(int paramInt1, int paramInt2, IntBuffer paramIntBuffer);
  
  private static native void destroyIcon(long paramLong);
  
  private static native long sendMessage(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
  
  private static native long setWindowLongPtr(long paramLong1, int paramInt, long paramLong2);
  
  private static native long getWindowLongPtr(long paramLong, int paramInt);
  
  private static native boolean setWindowPos(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong3);
  
  private static native long nSetCapture(long paramLong);
  
  private static native boolean nReleaseCapture();
  
  private static native void getClientRect(long paramLong, IntBuffer paramIntBuffer);
  
  private static native void clientToScreen(long paramLong, IntBuffer paramIntBuffer);
  
  private static native void setWindowProc(Method paramMethod);
  
  private static native long defWindowProc(long paramLong1, int paramInt, long paramLong2, long paramLong3);
  
  private native boolean getWindowRect(long paramLong, IntBuffer paramIntBuffer);
  
  private native boolean nTrackMouseEvent(long paramLong);
  
  private native boolean adjustWindowRectEx(IntBuffer paramIntBuffer, int paramInt1, boolean paramBoolean, int paramInt2);
}
