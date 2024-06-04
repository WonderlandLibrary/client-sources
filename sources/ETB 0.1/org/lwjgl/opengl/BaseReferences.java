package org.lwjgl.opengl;

import java.nio.Buffer;
import java.util.Arrays;





































class BaseReferences
{
  int elementArrayBuffer;
  int arrayBuffer;
  final Buffer[] glVertexAttribPointer_buffer;
  final Buffer[] glTexCoordPointer_buffer;
  int glClientActiveTexture;
  int vertexArrayObject;
  int pixelPackBuffer;
  int pixelUnpackBuffer;
  int indirectBuffer;
  
  BaseReferences(ContextCapabilities caps)
  {
    int max_vertex_attribs;
    int max_vertex_attribs;
    if ((OpenGL20) || (GL_ARB_vertex_shader)) {
      max_vertex_attribs = GL11.glGetInteger(34921);
    } else
      max_vertex_attribs = 0;
    glVertexAttribPointer_buffer = new Buffer[max_vertex_attribs];
    int max_texture_units;
    int max_texture_units;
    if (OpenGL20) {
      max_texture_units = GL11.glGetInteger(34930); } else { int max_texture_units;
      if ((OpenGL13) || (GL_ARB_multitexture)) {
        max_texture_units = GL11.glGetInteger(34018);
      } else
        max_texture_units = 1; }
    glTexCoordPointer_buffer = new Buffer[max_texture_units];
  }
  
  void clear() {
    elementArrayBuffer = 0;
    arrayBuffer = 0;
    glClientActiveTexture = 0;
    Arrays.fill(glVertexAttribPointer_buffer, null);
    Arrays.fill(glTexCoordPointer_buffer, null);
    
    vertexArrayObject = 0;
    
    pixelPackBuffer = 0;
    pixelUnpackBuffer = 0;
    
    indirectBuffer = 0;
  }
  
  void copy(BaseReferences references, int mask) {
    if ((mask & 0x2) != 0) {
      elementArrayBuffer = elementArrayBuffer;
      arrayBuffer = arrayBuffer;
      glClientActiveTexture = glClientActiveTexture;
      System.arraycopy(glVertexAttribPointer_buffer, 0, glVertexAttribPointer_buffer, 0, glVertexAttribPointer_buffer.length);
      System.arraycopy(glTexCoordPointer_buffer, 0, glTexCoordPointer_buffer, 0, glTexCoordPointer_buffer.length);
      
      vertexArrayObject = vertexArrayObject;
      
      indirectBuffer = indirectBuffer;
    }
    
    if ((mask & 0x1) != 0) {
      pixelPackBuffer = pixelPackBuffer;
      pixelUnpackBuffer = pixelUnpackBuffer;
    }
  }
}
