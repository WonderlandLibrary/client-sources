package org.lwjgl.opengl;



public final class ARBES31Compatibility
{
  private ARBES31Compatibility() {}
  


  public static void glMemoryBarrierByRegion(int barriers)
  {
    GL45.glMemoryBarrierByRegion(barriers);
  }
}
