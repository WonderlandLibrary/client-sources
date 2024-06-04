package org.lwjgl.openal;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;






















































public final class ALCcontext
{
  final long context;
  private boolean valid;
  
  ALCcontext(long context)
  {
    this.context = context;
    valid = true;
  }
  


  public boolean equals(Object context)
  {
    if ((context instanceof ALCcontext)) {
      return context == this.context;
    }
    return super.equals(context);
  }
  






  static IntBuffer createAttributeList(int contextFrequency, int contextRefresh, int contextSynchronized)
  {
    IntBuffer attribList = BufferUtils.createIntBuffer(7);
    
    attribList.put(4103);
    attribList.put(contextFrequency);
    attribList.put(4104);
    attribList.put(contextRefresh);
    attribList.put(4105);
    attribList.put(contextSynchronized);
    attribList.put(0);
    
    return attribList;
  }
  



  void setInvalid()
  {
    valid = false;
  }
  


  public boolean isValid()
  {
    return valid;
  }
}
