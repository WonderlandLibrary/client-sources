package org.lwjgl.opengl;

import java.nio.IntBuffer;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLUtil;






































































public final class ContextAttribs
{
  public static final int CONTEXT_MAJOR_VERSION_ARB = 8337;
  public static final int CONTEXT_MINOR_VERSION_ARB = 8338;
  public static final int CONTEXT_PROFILE_MASK_ARB = 37158;
  public static final int CONTEXT_CORE_PROFILE_BIT_ARB = 1;
  public static final int CONTEXT_COMPATIBILITY_PROFILE_BIT_ARB = 2;
  public static final int CONTEXT_ES2_PROFILE_BIT_EXT = 4;
  public static final int CONTEXT_FLAGS_ARB = 8340;
  public static final int CONTEXT_DEBUG_BIT_ARB = 1;
  public static final int CONTEXT_FORWARD_COMPATIBLE_BIT_ARB = 2;
  public static final int CONTEXT_ROBUST_ACCESS_BIT_ARB = 4;
  public static final int CONTEXT_RESET_ISOLATION_BIT_ARB = 8;
  public static final int CONTEXT_RESET_NOTIFICATION_STRATEGY_ARB = 33366;
  public static final int NO_RESET_NOTIFICATION_ARB = 33377;
  public static final int LOSE_CONTEXT_ON_RESET_ARB = 33362;
  public static final int CONTEXT_RELEASE_BEHABIOR_ARB = 8343;
  public static final int CONTEXT_RELEASE_BEHAVIOR_NONE_ARB = 0;
  public static final int CONTEXT_RELEASE_BEHAVIOR_FLUSH_ARB = 8344;
  public static final int CONTEXT_LAYER_PLANE_ARB = 8339;
  private int majorVersion;
  private int minorVersion;
  private int profileMask;
  private int contextFlags;
  private int contextResetNotificationStrategy = 33377;
  private int contextReleaseBehavior = 8344;
  

  private int layerPlane;
  

  public ContextAttribs()
  {
    this(1, 0);
  }
  
  public ContextAttribs(int majorVersion, int minorVersion)
  {
    this(majorVersion, minorVersion, 0, 0);
  }
  






  public ContextAttribs(int majorVersion, int minorVersion, int profileMask)
  {
    this(majorVersion, minorVersion, 0, profileMask);
  }
  







  public ContextAttribs(int majorVersion, int minorVersion, int profileMask, int contextFlags)
  {
    if ((majorVersion < 0) || (4 < majorVersion) || (minorVersion < 0) || ((majorVersion == 4) && (5 < minorVersion)) || ((majorVersion == 3) && (3 < minorVersion)) || ((majorVersion == 2) && (1 < minorVersion)) || ((majorVersion == 1) && (5 < minorVersion)))
    {




      throw new IllegalArgumentException("Invalid OpenGL version specified: " + majorVersion + '.' + minorVersion);
    }
    if (LWJGLUtil.CHECKS) {
      if ((1 < Integer.bitCount(profileMask)) || (4 < profileMask)) {
        throw new IllegalArgumentException("Invalid profile mask specified: " + Integer.toBinaryString(profileMask));
      }
      if (15 < contextFlags) {
        throw new IllegalArgumentException("Invalid context flags specified: " + Integer.toBinaryString(profileMask));
      }
    }
    this.majorVersion = majorVersion;
    this.minorVersion = minorVersion;
    
    this.profileMask = profileMask;
    this.contextFlags = contextFlags;
  }
  
  private ContextAttribs(ContextAttribs other)
  {
    majorVersion = majorVersion;
    minorVersion = minorVersion;
    
    profileMask = profileMask;
    contextFlags = contextFlags;
    
    contextResetNotificationStrategy = contextResetNotificationStrategy;
    contextReleaseBehavior = contextReleaseBehavior;
    
    layerPlane = layerPlane;
  }
  


  public int getMajorVersion()
  {
    return majorVersion;
  }
  
  public int getMinorVersion()
  {
    return minorVersion;
  }
  
  public int getProfileMask()
  {
    return profileMask;
  }
  
  private boolean hasMask(int mask) {
    return profileMask == mask;
  }
  
  public boolean isProfileCore()
  {
    return hasMask(1);
  }
  
  public boolean isProfileCompatibility()
  {
    return hasMask(2);
  }
  
  public boolean isProfileES()
  {
    return hasMask(4);
  }
  
  public int getContextFlags()
  {
    return contextFlags;
  }
  
  private boolean hasFlag(int flag) {
    return (contextFlags & flag) != 0;
  }
  
  public boolean isDebug()
  {
    return hasFlag(1);
  }
  
  public boolean isForwardCompatible()
  {
    return hasFlag(2);
  }
  
  public boolean isRobustAccess() {
    return hasFlag(4);
  }
  
  public boolean isContextResetIsolation() {
    return hasFlag(8);
  }
  
  public int getContextResetNotificationStrategy()
  {
    return contextResetNotificationStrategy;
  }
  
  /**
   * @deprecated
   */
  public boolean isLoseContextOnReset()
  {
    return contextResetNotificationStrategy == 33362;
  }
  
  public int getContextReleaseBehavior() {
    return contextReleaseBehavior;
  }
  
  public int getLayerPlane()
  {
    return layerPlane;
  }
  

  private ContextAttribs toggleMask(int mask, boolean value)
  {
    if (value == hasMask(mask)) {
      return this;
    }
    ContextAttribs attribs = new ContextAttribs(this);
    profileMask = (value ? mask : 0);
    return attribs;
  }
  



  public ContextAttribs withProfileCore(boolean profileCore)
  {
    if ((majorVersion < 3) || ((majorVersion == 3) && (minorVersion < 2))) {
      throw new IllegalArgumentException("Profiles are only supported on OpenGL version 3.2 or higher.");
    }
    return toggleMask(1, profileCore);
  }
  



  public ContextAttribs withProfileCompatibility(boolean profileCompatibility)
  {
    if ((majorVersion < 3) || ((majorVersion == 3) && (minorVersion < 2))) {
      throw new IllegalArgumentException("Profiles are only supported on OpenGL version 3.2 or higher.");
    }
    return toggleMask(2, profileCompatibility);
  }
  



  public ContextAttribs withProfileES(boolean profileES)
  {
    if ((majorVersion != 2) || (minorVersion != 0)) {
      throw new IllegalArgumentException("The OpenGL ES profile is only supported on OpenGL version 2.0.");
    }
    return toggleMask(4, profileES);
  }
  
  private ContextAttribs toggleFlag(int flag, boolean value) {
    if (value == hasFlag(flag)) {
      return this;
    }
    ContextAttribs attribs = new ContextAttribs(this);
    contextFlags ^= flag;
    return attribs;
  }
  
  public ContextAttribs withDebug(boolean debug) {
    return toggleFlag(1, debug);
  }
  
  public ContextAttribs withForwardCompatible(boolean forwardCompatible) { return toggleFlag(2, forwardCompatible); }
  
  public ContextAttribs withRobustAccess(boolean robustAccess) {
    return toggleFlag(4, robustAccess);
  }
  
  public ContextAttribs withContextResetIsolation(boolean contextResetIsolation) { return toggleFlag(8, contextResetIsolation); }
  







  public ContextAttribs withResetNotificationStrategy(int strategy)
  {
    if (strategy == contextResetNotificationStrategy) {
      return this;
    }
    if ((LWJGLUtil.CHECKS) && (strategy != 33377) && (strategy != 33362)) {
      throw new IllegalArgumentException("Invalid context reset notification strategy specified: 0x" + LWJGLUtil.toHexString(strategy));
    }
    ContextAttribs attribs = new ContextAttribs(this);
    contextResetNotificationStrategy = strategy;
    return attribs;
  }
  






  /**
   * @deprecated
   */
  public ContextAttribs withLoseContextOnReset(boolean loseContextOnReset)
  {
    return withResetNotificationStrategy(loseContextOnReset ? 33362 : 33377);
  }
  







  public ContextAttribs withContextReleaseBehavior(int behavior)
  {
    if (behavior == contextReleaseBehavior) {
      return this;
    }
    if ((LWJGLUtil.CHECKS) && (behavior != 8344) && (behavior != 0)) {
      throw new IllegalArgumentException("Invalid context release behavior specified: 0x" + LWJGLUtil.toHexString(behavior));
    }
    ContextAttribs attribs = new ContextAttribs(this);
    contextReleaseBehavior = behavior;
    return attribs;
  }
  
  public ContextAttribs withLayer(int layerPlane)
  {
    if (LWJGLUtil.getPlatform() != 3) {
      throw new IllegalArgumentException("The CONTEXT_LAYER_PLANE_ARB attribute is supported only on the Windows platform.");
    }
    if (layerPlane == this.layerPlane) {
      return this;
    }
    if (layerPlane < 0) {
      throw new IllegalArgumentException("Invalid layer plane specified: " + layerPlane);
    }
    ContextAttribs attribs = new ContextAttribs(this);
    layerPlane = layerPlane;
    return attribs;
  }
  
  IntBuffer getAttribList() {
    if (LWJGLUtil.getPlatform() == 2) {
      return null;
    }
    LinkedHashMap<Integer, Integer> map = new LinkedHashMap(8);
    
    if ((majorVersion != 1) || (minorVersion != 0)) {
      map.put(Integer.valueOf(8337), Integer.valueOf(majorVersion));
      map.put(Integer.valueOf(8338), Integer.valueOf(minorVersion));
    }
    
    if (contextFlags != 0) {
      map.put(Integer.valueOf(8340), Integer.valueOf(contextFlags));
    }
    if (profileMask != 0) {
      map.put(Integer.valueOf(37158), Integer.valueOf(profileMask));
    }
    if (contextResetNotificationStrategy != 33377) {
      map.put(Integer.valueOf(33366), Integer.valueOf(contextResetNotificationStrategy));
    }
    if (contextReleaseBehavior != 8344) {
      map.put(Integer.valueOf(8343), Integer.valueOf(contextReleaseBehavior));
    }
    if (layerPlane != 0) {
      map.put(Integer.valueOf(8339), Integer.valueOf(layerPlane));
    }
    if (map.isEmpty()) {
      return null;
    }
    IntBuffer attribs = BufferUtils.createIntBuffer(map.size() * 2 + 1);
    for (Map.Entry<Integer, Integer> attrib : map.entrySet()) {
      attribs.put(((Integer)attrib.getKey()).intValue()).put(((Integer)attrib.getValue()).intValue());
    }
    

    attribs.put(0);
    attribs.rewind();
    return attribs;
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder(32);
    
    sb.append("ContextAttribs:");
    sb.append(" Version=").append(majorVersion).append('.').append(minorVersion);
    
    if (profileMask != 0) {
      sb.append(", Profile=");
      if (hasMask(1)) {
        sb.append("CORE");
      } else if (hasMask(2)) {
        sb.append("COMPATIBLITY");
      } else if (hasMask(4)) {
        sb.append("ES2");
      } else {
        sb.append("*unknown*");
      }
    }
    if (contextFlags != 0) {
      if (hasFlag(1))
        sb.append(", DEBUG");
      if (hasFlag(2))
        sb.append(", FORWARD_COMPATIBLE");
      if (hasFlag(4))
        sb.append(", ROBUST_ACCESS");
      if (hasFlag(8)) {
        sb.append(", RESET_ISOLATION");
      }
    }
    if (contextResetNotificationStrategy != 33377)
      sb.append(", LOSE_CONTEXT_ON_RESET");
    if (contextReleaseBehavior != 8344) {
      sb.append(", RELEASE_BEHAVIOR_NONE");
    }
    if (layerPlane != 0) {
      sb.append(", Layer=").append(layerPlane);
    }
    return sb.toString();
  }
}
