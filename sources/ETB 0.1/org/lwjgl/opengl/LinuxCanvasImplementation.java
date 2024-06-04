package org.lwjgl.opengl;

import java.awt.Canvas;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
































final class LinuxCanvasImplementation
  implements AWTCanvasImplementation
{
  LinuxCanvasImplementation() {}
  
  static int getScreenFromDevice(GraphicsDevice device)
    throws LWJGLException
  {
    try
    {
      Method getScreen_method = (Method)AccessController.doPrivileged(new PrivilegedExceptionAction() {
        public Method run() throws Exception {
          return val$device.getClass().getMethod("getScreen", new Class[0]);
        }
      });
      Integer screen = (Integer)getScreen_method.invoke(device, new Object[0]);
      return screen.intValue();
    } catch (Exception e) {
      throw new LWJGLException(e);
    }
  }
  
  private static int getVisualIDFromConfiguration(GraphicsConfiguration configuration) throws LWJGLException {
    try {
      Method getVisual_method = (Method)AccessController.doPrivileged(new PrivilegedExceptionAction() {
        public Method run() throws Exception {
          return val$configuration.getClass().getMethod("getVisual", new Class[0]);
        }
      });
      Integer visual = (Integer)getVisual_method.invoke(configuration, new Object[0]);
      return visual.intValue();
    } catch (Exception e) {
      throw new LWJGLException(e);
    }
  }
  
  public PeerInfo createPeerInfo(Canvas component, PixelFormat pixel_format, ContextAttribs attribs) throws LWJGLException {
    return new LinuxAWTGLCanvasPeerInfo(component);
  }
  


  public GraphicsConfiguration findConfiguration(GraphicsDevice device, PixelFormat pixel_format)
    throws LWJGLException
  {
    try
    {
      int screen = getScreenFromDevice(device);
      int visual_id_matching_format = findVisualIDFromFormat(screen, pixel_format);
      GraphicsConfiguration[] configurations = device.getConfigurations();
      for (GraphicsConfiguration configuration : configurations) {
        int visual_id = getVisualIDFromConfiguration(configuration);
        if (visual_id == visual_id_matching_format)
          return configuration;
      }
    } catch (LWJGLException e) {
      LWJGLUtil.log("Got exception while trying to determine configuration: " + e);
    }
    return null;
  }
  
  /* Error */
  private static int findVisualIDFromFormat(int screen, PixelFormat pixel_format)
    throws LWJGLException
  {
    // Byte code:
    //   0: invokestatic 129	org/lwjgl/opengl/LinuxDisplay:lockAWT	()V
    //   3: invokestatic 134	org/lwjgl/opengl/GLContext:loadOpenGLLibrary	()V
    //   6: invokestatic 137	org/lwjgl/opengl/LinuxDisplay:incDisplay	()V
    //   9: invokestatic 141	org/lwjgl/opengl/LinuxDisplay:getDisplay	()J
    //   12: iload_0
    //   13: aload_1
    //   14: invokestatic 145	org/lwjgl/opengl/LinuxCanvasImplementation:nFindVisualIDFromFormat	(JILorg/lwjgl/opengl/PixelFormat;)I
    //   17: istore_2
    //   18: invokestatic 148	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   21: invokestatic 151	org/lwjgl/opengl/GLContext:unloadOpenGLLibrary	()V
    //   24: invokestatic 154	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   27: iload_2
    //   28: ireturn
    //   29: astore_3
    //   30: invokestatic 148	org/lwjgl/opengl/LinuxDisplay:decDisplay	()V
    //   33: aload_3
    //   34: athrow
    //   35: astore 4
    //   37: invokestatic 151	org/lwjgl/opengl/GLContext:unloadOpenGLLibrary	()V
    //   40: aload 4
    //   42: athrow
    //   43: astore 5
    //   45: invokestatic 154	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   48: aload 5
    //   50: athrow
    // Line number table:
    //   Java source line #106	-> byte code offset #0
    //   Java source line #108	-> byte code offset #3
    //   Java source line #110	-> byte code offset #6
    //   Java source line #111	-> byte code offset #9
    //   Java source line #113	-> byte code offset #18
    //   Java source line #116	-> byte code offset #21
    //   Java source line #119	-> byte code offset #24
    //   Java source line #113	-> byte code offset #29
    //   Java source line #116	-> byte code offset #35
    //   Java source line #119	-> byte code offset #43
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	51	0	screen	int
    //   0	51	1	pixel_format	PixelFormat
    //   17	11	2	i	int
    //   29	5	3	localObject1	Object
    //   35	6	4	localObject2	Object
    //   43	6	5	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   6	18	29	finally
    //   29	30	29	finally
    //   3	21	35	finally
    //   29	37	35	finally
    //   0	24	43	finally
    //   29	45	43	finally
  }
  
  private static native int nFindVisualIDFromFormat(long paramLong, int paramInt, PixelFormat paramPixelFormat)
    throws LWJGLException;
}
