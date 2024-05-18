package org.newdawn.slick.opengl.renderer;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public interface SGL {
  public static final int GL_TEXTURE_2D = 3553;
  
  public static final int GL_RGBA = 6408;
  
  public static final int GL_RGB = 6407;
  
  public static final int GL_UNSIGNED_BYTE = 5121;
  
  public static final int GL_LINEAR = 9729;
  
  public static final int GL_NEAREST = 9728;
  
  public static final int GL_TEXTURE_MIN_FILTER = 10241;
  
  public static final int GL_TEXTURE_MAG_FILTER = 10240;
  
  public static final int GL_POINT_SMOOTH = 2832;
  
  public static final int GL_POLYGON_SMOOTH = 2881;
  
  public static final int GL_LINE_SMOOTH = 2848;
  
  public static final int GL_SCISSOR_TEST = 3089;
  
  public static final int GL_MODULATE = 8448;
  
  public static final int GL_TEXTURE_ENV = 8960;
  
  public static final int GL_TEXTURE_ENV_MODE = 8704;
  
  public static final int GL_QUADS = 7;
  
  public static final int GL_POINTS = 0;
  
  public static final int GL_LINES = 1;
  
  public static final int GL_LINE_STRIP = 3;
  
  public static final int GL_TRIANGLES = 4;
  
  public static final int GL_TRIANGLE_FAN = 6;
  
  public static final int GL_SRC_ALPHA = 770;
  
  public static final int GL_ONE = 1;
  
  public static final int GL_ONE_MINUS_DST_ALPHA = 773;
  
  public static final int GL_DST_ALPHA = 772;
  
  public static final int GL_ONE_MINUS_SRC_ALPHA = 771;
  
  public static final int GL_COMPILE = 4864;
  
  public static final int GL_MAX_TEXTURE_SIZE = 3379;
  
  public static final int GL_COLOR_BUFFER_BIT = 16384;
  
  public static final int GL_DEPTH_BUFFER_BIT = 256;
  
  public static final int GL_BLEND = 3042;
  
  public static final int GL_COLOR_CLEAR_VALUE = 3106;
  
  public static final int GL_LINE_WIDTH = 2849;
  
  public static final int GL_CLIP_PLANE0 = 12288;
  
  public static final int GL_CLIP_PLANE1 = 12289;
  
  public static final int GL_CLIP_PLANE2 = 12290;
  
  public static final int GL_CLIP_PLANE3 = 12291;
  
  public static final int GL_COMPILE_AND_EXECUTE = 4865;
  
  public static final int GL_RGBA8 = 6408;
  
  public static final int GL_RGBA16 = 32859;
  
  public static final int GL_BGRA = 32993;
  
  public static final int GL_MIRROR_CLAMP_TO_EDGE_EXT = 34627;
  
  public static final int GL_TEXTURE_WRAP_S = 10242;
  
  public static final int GL_TEXTURE_WRAP_T = 10243;
  
  public static final int GL_CLAMP = 10496;
  
  public static final int GL_COLOR_SUM_EXT = 33880;
  
  public static final int GL_ALWAYS = 519;
  
  public static final int GL_DEPTH_TEST = 2929;
  
  public static final int GL_NOTEQUAL = 517;
  
  public static final int GL_EQUAL = 514;
  
  public static final int GL_SRC_COLOR = 768;
  
  public static final int GL_ONE_MINUS_SRC_COLOR = 769;
  
  public static final int GL_MODELVIEW_MATRIX = 2982;
  
  void flush();
  
  void initDisplay(int paramInt1, int paramInt2);
  
  void enterOrtho(int paramInt1, int paramInt2);
  
  void glClearColor(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
  
  void glClipPlane(int paramInt, DoubleBuffer paramDoubleBuffer);
  
  void glScissor(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void glLineWidth(float paramFloat);
  
  void glClear(int paramInt);
  
  void glColorMask(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4);
  
  void glLoadIdentity();
  
  void glGetInteger(int paramInt, IntBuffer paramIntBuffer);
  
  void glGetFloat(int paramInt, FloatBuffer paramFloatBuffer);
  
  void glEnable(int paramInt);
  
  void glDisable(int paramInt);
  
  void glBindTexture(int paramInt1, int paramInt2);
  
  void glGetTexImage(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ByteBuffer paramByteBuffer);
  
  void glDeleteTextures(IntBuffer paramIntBuffer);
  
  void glColor4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
  
  void glTexCoord2f(float paramFloat1, float paramFloat2);
  
  void glVertex3f(float paramFloat1, float paramFloat2, float paramFloat3);
  
  void glVertex2f(float paramFloat1, float paramFloat2);
  
  void glRotatef(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
  
  void glTranslatef(float paramFloat1, float paramFloat2, float paramFloat3);
  
  void glBegin(int paramInt);
  
  void glEnd();
  
  void glTexEnvi(int paramInt1, int paramInt2, int paramInt3);
  
  void glPointSize(float paramFloat);
  
  void glScalef(float paramFloat1, float paramFloat2, float paramFloat3);
  
  void glPushMatrix();
  
  void glPopMatrix();
  
  void glBlendFunc(int paramInt1, int paramInt2);
  
  int glGenLists(int paramInt);
  
  void glNewList(int paramInt1, int paramInt2);
  
  void glEndList();
  
  void glCallList(int paramInt);
  
  void glCopyTexImage2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8);
  
  void glReadPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, ByteBuffer paramByteBuffer);
  
  void glTexParameteri(int paramInt1, int paramInt2, int paramInt3);
  
  float[] getCurrentColor();
  
  void glDeleteLists(int paramInt1, int paramInt2);
  
  void glDepthMask(boolean paramBoolean);
  
  void glClearDepth(float paramFloat);
  
  void glDepthFunc(int paramInt);
  
  void setGlobalAlphaScale(float paramFloat);
  
  void glLoadMatrix(FloatBuffer paramFloatBuffer);
  
  void glGenTextures(IntBuffer paramIntBuffer);
  
  void glGetError();
  
  void glTexImage2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, ByteBuffer paramByteBuffer);
  
  void glTexSubImage2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, ByteBuffer paramByteBuffer);
  
  boolean canTextureMirrorClamp();
  
  boolean canSecondaryColor();
  
  void glSecondaryColor3ubEXT(byte paramByte1, byte paramByte2, byte paramByte3);
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\renderer\SGL.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */