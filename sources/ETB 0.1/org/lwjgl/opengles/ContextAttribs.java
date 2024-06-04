package org.lwjgl.opengles;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;






































public final class ContextAttribs
{
  private int version;
  
  public ContextAttribs()
  {
    this(2);
  }
  
  public ContextAttribs(int version) {
    if (3 < version) {
      throw new IllegalArgumentException("Invalid OpenGL ES version specified: " + version);
    }
    this.version = version;
  }
  
  private ContextAttribs(ContextAttribs attribs) {
    version = version;
  }
  
  public int getVersion() {
    return version;
  }
  
  public IntBuffer getAttribList() {
    int attribCount = 1;
    
    IntBuffer attribs = BufferUtils.createIntBuffer(attribCount * 2 + 1);
    
    attribs.put(12440).put(version);
    
    attribs.put(12344);
    attribs.rewind();
    return attribs;
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder(32);
    
    sb.append("ContextAttribs:");
    sb.append(" Version=").append(version);
    
    return sb.toString();
  }
}
