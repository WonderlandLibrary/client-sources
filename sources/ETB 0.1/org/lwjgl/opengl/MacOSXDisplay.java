package org.lwjgl.opengl;

import java.awt.Canvas;
import java.awt.Robot;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.MemoryUtil;






















































final class MacOSXDisplay
  implements DisplayImplementation
{
  private static final int PBUFFER_HANDLE_SIZE = 24;
  private static final int GAMMA_LENGTH = 256;
  private Canvas canvas;
  private Robot robot;
  private MacOSXMouseEventQueue mouse_queue;
  private KeyboardEventQueue keyboard_queue;
  private DisplayMode requested_mode;
  private MacOSXNativeMouse mouse;
  private MacOSXNativeKeyboard keyboard;
  private ByteBuffer window;
  private ByteBuffer context;
  private boolean skipViewportValue = false;
  private static final IntBuffer current_viewport = BufferUtils.createIntBuffer(16);
  
  private boolean mouseInsideWindow;
  
  private boolean close_requested;
  
  private boolean native_mode = true;
  
  private boolean updateNativeCursor = false;
  
  private long currentNativeCursor = 0L;
  
  private boolean enableHighDPI = false;
  
  private float scaleFactor = 1.0F;
  
  MacOSXDisplay() {}
  
  private native ByteBuffer nCreateWindow(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2)
    throws LWJGLException;
  
  private native Object nGetCurrentDisplayMode();
  
  private native void nGetDisplayModes(Object paramObject);
  
  private native boolean nIsMiniaturized(ByteBuffer paramByteBuffer);
  
  private native boolean nIsFocused(ByteBuffer paramByteBuffer);
  
  private native void nSetResizable(ByteBuffer paramByteBuffer, boolean paramBoolean);
  
  private native void nResizeWindow(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  private native boolean nWasResized(ByteBuffer paramByteBuffer);
  
  private native int nGetX(ByteBuffer paramByteBuffer);
  
  private native int nGetY(ByteBuffer paramByteBuffer);
  
  private native int nGetWidth(ByteBuffer paramByteBuffer);
  
  private native int nGetHeight(ByteBuffer paramByteBuffer);
  
  private native boolean nIsNativeMode(ByteBuffer paramByteBuffer);
  
  private static boolean isUndecorated()
  {
    return Display.getPrivilegedBoolean("org.lwjgl.opengl.Window.undecorated");
  }
  
  public void createWindow(DrawableLWJGL drawable, DisplayMode mode, Canvas parent, int x, int y) throws LWJGLException {
    boolean fullscreen = Display.isFullscreen();
    boolean resizable = Display.isResizable();
    boolean parented = (parent != null) && (!fullscreen);
    

    boolean enableFullscreenModeAPI = (LWJGLUtil.isMacOSXEqualsOrBetterThan(10, 7)) && (parent == null) && (!Display.getPrivilegedBoolean("org.lwjgl.opengl.Display.disableOSXFullscreenModeAPI"));
    


    enableHighDPI = ((LWJGLUtil.isMacOSXEqualsOrBetterThan(10, 7)) && (parent == null) && ((Display.getPrivilegedBoolean("org.lwjgl.opengl.Display.enableHighDPI")) || (fullscreen)));
    

    if (parented) canvas = parent; else {
      canvas = null;
    }
    close_requested = false;
    
    DrawableGL gl_drawable = (DrawableGL)Display.getDrawable();
    PeerInfo peer_info = peer_info;
    ByteBuffer peer_handle = peer_info.lockAndGetHandle();
    ByteBuffer window_handle = parented ? window_handle : window;
    
    try
    {
      window = nCreateWindow(x, y, mode.getWidth(), mode.getHeight(), fullscreen, isUndecorated(), resizable, parented, enableFullscreenModeAPI, enableHighDPI, peer_handle, window_handle);
      


      if (fullscreen)
      {
        skipViewportValue = true;
        
        current_viewport.put(2, mode.getWidth());
        current_viewport.put(3, mode.getHeight());
      }
      
      native_mode = nIsNativeMode(peer_handle);
      
      if (!native_mode) {
        robot = AWTUtil.createRobot(canvas);
      }
    }
    catch (LWJGLException e)
    {
      throw e;
    } finally {
      peer_info.unlock();
    }
  }
  
  public void doHandleQuit() {
    synchronized (this) {
      close_requested = true;
    }
  }
  
  public void mouseInsideWindow(boolean inside) {
    synchronized (this) {
      mouseInsideWindow = inside;
    }
    updateNativeCursor = true;
  }
  
  public void setScaleFactor(float scale) {
    synchronized (this) {
      scaleFactor = scale;
    }
  }
  
  public native void nDestroyCALayer(ByteBuffer paramByteBuffer);
  
  public native void nDestroyWindow(ByteBuffer paramByteBuffer);
  
  public void destroyWindow()
  {
    if (!native_mode) {
      DrawableGL gl_drawable = (DrawableGL)Display.getDrawable();
      PeerInfo peer_info = peer_info;
      if (peer_info != null) {
        ByteBuffer peer_handle = peer_info.getHandle();
        nDestroyCALayer(peer_handle);
      }
      robot = null;
    }
    
    nDestroyWindow(window);
  }
  
  public int getGammaRampLength() {
    return 256;
  }
  
  public native void setGammaRamp(FloatBuffer paramFloatBuffer) throws LWJGLException;
  
  public String getAdapter() {
    return null;
  }
  
  public String getVersion() {
    return null;
  }
  
  private static boolean equals(DisplayMode mode1, DisplayMode mode2) {
    return (mode1.getWidth() == mode2.getWidth()) && (mode1.getHeight() == mode2.getHeight()) && (mode1.getBitsPerPixel() == mode2.getBitsPerPixel()) && (mode1.getFrequency() == mode2.getFrequency());
  }
  
  public void switchDisplayMode(DisplayMode mode) throws LWJGLException
  {
    DisplayMode[] modes = getAvailableDisplayModes();
    
    for (DisplayMode available_mode : modes) {
      if (equals(available_mode, mode)) {
        requested_mode = available_mode;
        return;
      }
    }
    
    throw new LWJGLException(mode + " is not supported");
  }
  
  public void resetDisplayMode() {
    requested_mode = null;
    restoreGamma();
  }
  
  private native void restoreGamma();
  
  public Object createDisplayMode(int width, int height, int bitsPerPixel, int refreshRate) {
    return new DisplayMode(width, height, bitsPerPixel, refreshRate);
  }
  
  public DisplayMode init() throws LWJGLException {
    return (DisplayMode)nGetCurrentDisplayMode();
  }
  
  public void addDisplayMode(Object modesList, int width, int height, int bitsPerPixel, int refreshRate) {
    List<DisplayMode> modes = (List)modesList;
    DisplayMode displayMode = new DisplayMode(width, height, bitsPerPixel, refreshRate);
    modes.add(displayMode);
  }
  
  public DisplayMode[] getAvailableDisplayModes() throws LWJGLException {
    List<DisplayMode> modes = new ArrayList();
    nGetDisplayModes(modes);
    modes.add(Display.getDesktopDisplayMode());
    return (DisplayMode[])modes.toArray(new DisplayMode[modes.size()]);
  }
  
  private native void nSetTitle(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2);
  
  public void setTitle(String title) {
    ByteBuffer buffer = MemoryUtil.encodeUTF8(title);
    nSetTitle(window, buffer);
  }
  
  public boolean isCloseRequested() {
    boolean result;
    synchronized (this) {
      result = close_requested;
      close_requested = false;
    }
    return result;
  }
  
  public boolean isVisible() {
    return true;
  }
  
  public boolean isActive() {
    if (native_mode) {
      return nIsFocused(window);
    }
    
    return Display.getParent().hasFocus();
  }
  
  public Canvas getCanvas()
  {
    return canvas;
  }
  
  public boolean isDirty() {
    return false;
  }
  
  public PeerInfo createPeerInfo(PixelFormat pixel_format, ContextAttribs attribs) throws LWJGLException {
    try {
      return new MacOSXDisplayPeerInfo(pixel_format, attribs, true);
    } catch (LWJGLException e) {}
    return new MacOSXDisplayPeerInfo(pixel_format, attribs, false);
  }
  
  public void update()
  {
    boolean should_update = true;
    
    DrawableGL drawable = (DrawableGL)Display.getDrawable();
    if (should_update)
    {


      if (skipViewportValue) skipViewportValue = false; else {
        GL11.glGetInteger(2978, current_viewport);
      }
      context.update();
      

      GL11.glViewport(current_viewport.get(0), current_viewport.get(1), current_viewport.get(2), current_viewport.get(3));
    }
    
    if ((native_mode) && (updateNativeCursor)) {
      updateNativeCursor = false;
      try {
        setNativeCursor(Long.valueOf(currentNativeCursor));
      } catch (LWJGLException e) {
        e.printStackTrace();
      }
    }
  }
  


  public void reshape(int x, int y, int width, int height) {}
  


  public boolean hasWheel()
  {
    return AWTUtil.hasWheel();
  }
  
  public int getButtonCount() {
    return AWTUtil.getButtonCount();
  }
  
  public void createMouse() throws LWJGLException {
    if (native_mode) {
      mouse = new MacOSXNativeMouse(this, window);
      mouse.register();
    }
    else {
      mouse_queue = new MacOSXMouseEventQueue(canvas);
      mouse_queue.register();
    }
  }
  
  public void destroyMouse() {
    if (native_mode)
    {
      try {
        MacOSXNativeMouse.setCursor(0L);
      }
      catch (LWJGLException e) {}
      
      grabMouse(false);
      
      if (mouse != null) {
        mouse.unregister();
      }
      mouse = null;
    }
    else {
      if (mouse_queue != null) {
        MacOSXMouseEventQueue.nGrabMouse(false);
        mouse_queue.unregister();
      }
      mouse_queue = null;
    }
  }
  
  public void pollMouse(IntBuffer coord_buffer, ByteBuffer buttons_buffer) {
    if (native_mode) {
      mouse.poll(coord_buffer, buttons_buffer);
    }
    else {
      mouse_queue.poll(coord_buffer, buttons_buffer);
    }
  }
  
  public void readMouse(ByteBuffer buffer) {
    if (native_mode) {
      mouse.copyEvents(buffer);
    }
    else {
      mouse_queue.copyEvents(buffer);
    }
  }
  
  public void grabMouse(boolean grab) {
    if (native_mode) {
      mouse.setGrabbed(grab);
    }
    else {
      mouse_queue.setGrabbed(grab);
    }
  }
  
  public int getNativeCursorCapabilities() {
    if (native_mode) {
      return 7;
    }
    
    return AWTUtil.getNativeCursorCapabilities();
  }
  
  public void setCursorPosition(int x, int y) {
    if ((native_mode) && 
      (mouse != null)) {
      mouse.setCursorPosition(x, y);
    }
  }
  


  public void setNativeCursor(Object handle)
    throws LWJGLException
  {
    if (native_mode) {
      currentNativeCursor = getCursorHandle(handle);
      if (Display.isCreated()) {
        if (mouseInsideWindow) MacOSXNativeMouse.setCursor(currentNativeCursor); else
          MacOSXNativeMouse.setCursor(0L);
      }
    }
  }
  
  public int getMinCursorSize() {
    return 1;
  }
  

  public int getMaxCursorSize()
  {
    DisplayMode dm = Display.getDesktopDisplayMode();
    return Math.min(dm.getWidth(), dm.getHeight()) / 2;
  }
  
  public void createKeyboard() throws LWJGLException
  {
    if (native_mode) {
      keyboard = new MacOSXNativeKeyboard(window);
      keyboard.register();
    }
    else {
      keyboard_queue = new KeyboardEventQueue(canvas);
      keyboard_queue.register();
    }
  }
  
  public void destroyKeyboard() {
    if (native_mode) {
      if (keyboard != null) {
        keyboard.unregister();
      }
      keyboard = null;
    }
    else {
      if (keyboard_queue != null) {
        keyboard_queue.unregister();
      }
      keyboard_queue = null;
    }
  }
  
  public void pollKeyboard(ByteBuffer keyDownBuffer) {
    if (native_mode) {
      keyboard.poll(keyDownBuffer);
    }
    else {
      keyboard_queue.poll(keyDownBuffer);
    }
  }
  
  public void readKeyboard(ByteBuffer buffer) {
    if (native_mode) {
      keyboard.copyEvents(buffer);
    }
    else {
      keyboard_queue.copyEvents(buffer);
    }
  }
  
  public Object createCursor(int width, int height, int xHotspot, int yHotspot, int numImages, IntBuffer images, IntBuffer delays) throws LWJGLException
  {
    if (native_mode) {
      long cursor = MacOSXNativeMouse.createCursor(width, height, xHotspot, yHotspot, numImages, images, delays);
      return Long.valueOf(cursor);
    }
    
    return AWTUtil.createCursor(width, height, xHotspot, yHotspot, numImages, images, delays);
  }
  
  public void destroyCursor(Object cursor_handle)
  {
    long handle = getCursorHandle(cursor_handle);
    

    if (currentNativeCursor == handle) {
      currentNativeCursor = 0L;
    }
    
    MacOSXNativeMouse.destroyCursor(handle);
  }
  
  private static long getCursorHandle(Object cursor_handle) {
    return cursor_handle != null ? ((Long)cursor_handle).longValue() : 0L;
  }
  
  public int getPbufferCapabilities() {
    return 1;
  }
  
  public boolean isBufferLost(PeerInfo handle) {
    return false;
  }
  
  public PeerInfo createPbuffer(int width, int height, PixelFormat pixel_format, ContextAttribs attribs, IntBuffer pixelFormatCaps, IntBuffer pBufferAttribs)
    throws LWJGLException
  {
    return new MacOSXPbufferPeerInfo(width, height, pixel_format, attribs);
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
  







































  public int setIcon(ByteBuffer[] icons)
  {
    return 0;
  }
  
  public int getX() {
    return nGetX(window);
  }
  
  public int getY() {
    return nGetY(window);
  }
  
  public int getWidth() {
    return nGetWidth(window);
  }
  
  public int getHeight() {
    return nGetHeight(window);
  }
  
  public boolean isInsideWindow() {
    return mouseInsideWindow;
  }
  
  public void setResizable(boolean resizable) {
    nSetResizable(window, resizable);
  }
  
  public boolean wasResized() {
    return nWasResized(window);
  }
  
  public float getPixelScaleFactor() {
    return (enableHighDPI) && (!Display.isFullscreen()) ? scaleFactor : 1.0F;
  }
}
