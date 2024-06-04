package org.lwjgl.opengl;

import java.util.HashMap;
import java.util.Map;






































final class CallbackUtil
{
  private static final Map<ContextCapabilities, Long> contextUserParamsARB = new HashMap();
  
  private static final Map<ContextCapabilities, Long> contextUserParamsAMD = new HashMap();
  
  private static final Map<ContextCapabilities, Long> contextUserParamsKHR = new HashMap();
  



  private CallbackUtil() {}
  



  static long createGlobalRef(Object obj)
  {
    return obj == null ? 0L : ncreateGlobalRef(obj);
  }
  







  private static native long ncreateGlobalRef(Object paramObject);
  







  private static native void deleteGlobalRef(long paramLong);
  






  private static void registerContextCallback(long userParam, Map<ContextCapabilities, Long> contextUserData)
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    if (caps == null) {
      deleteGlobalRef(userParam);
      throw new IllegalStateException("No context is current.");
    }
    
    Long userParam_old = (Long)contextUserData.remove(caps);
    if (userParam_old != null) {
      deleteGlobalRef(userParam_old.longValue());
    }
    if (userParam != 0L) {
      contextUserData.put(caps, Long.valueOf(userParam));
    }
  }
  




  static void unregisterCallbacks(Object context)
  {
    ContextCapabilities caps = GLContext.getCapabilities(context);
    
    Long userParam = (Long)contextUserParamsARB.remove(caps);
    if (userParam != null) {
      deleteGlobalRef(userParam.longValue());
    }
    userParam = (Long)contextUserParamsAMD.remove(caps);
    if (userParam != null) {
      deleteGlobalRef(userParam.longValue());
    }
    userParam = (Long)contextUserParamsKHR.remove(caps);
    if (userParam != null) {
      deleteGlobalRef(userParam.longValue());
    }
  }
  






  static native long getDebugOutputCallbackARB();
  






  static void registerContextCallbackARB(long userParam)
  {
    registerContextCallback(userParam, contextUserParamsARB);
  }
  







  static native long getDebugOutputCallbackAMD();
  






  static void registerContextCallbackAMD(long userParam)
  {
    registerContextCallback(userParam, contextUserParamsAMD);
  }
  







  static native long getDebugCallbackKHR();
  






  static void registerContextCallbackKHR(long userParam)
  {
    registerContextCallback(userParam, contextUserParamsKHR);
  }
}
