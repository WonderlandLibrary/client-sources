package org.lwjgl.opengl;

import java.nio.Buffer;

class References extends BaseReferences { Buffer ARB_matrix_palette_glMatrixIndexPointerARB_pPointer;
  
  References(ContextCapabilities caps) { super(caps); }
  

  Buffer ARB_vertex_blend_glWeightPointerARB_pPointer;
  Buffer EXT_fog_coord_glFogCoordPointerEXT_data;
  Buffer EXT_secondary_color_glSecondaryColorPointerEXT_pPointer;
  Buffer EXT_vertex_shader_glVariantPointerEXT_pAddr;
  Buffer EXT_vertex_weighting_glVertexWeightPointerEXT_pPointer;
  Buffer GL11_glColorPointer_pointer;
  Buffer GL11_glEdgeFlagPointer_pointer;
  Buffer GL11_glNormalPointer_pointer;
  Buffer GL11_glVertexPointer_pointer;
  Buffer GL14_glFogCoordPointer_data;
  void copy(References references, int mask)
  {
    super.copy(references, mask);
    if ((mask & 0x2) != 0) {
      ARB_matrix_palette_glMatrixIndexPointerARB_pPointer = ARB_matrix_palette_glMatrixIndexPointerARB_pPointer;
      ARB_vertex_blend_glWeightPointerARB_pPointer = ARB_vertex_blend_glWeightPointerARB_pPointer;
      EXT_fog_coord_glFogCoordPointerEXT_data = EXT_fog_coord_glFogCoordPointerEXT_data;
      EXT_secondary_color_glSecondaryColorPointerEXT_pPointer = EXT_secondary_color_glSecondaryColorPointerEXT_pPointer;
      EXT_vertex_shader_glVariantPointerEXT_pAddr = EXT_vertex_shader_glVariantPointerEXT_pAddr;
      EXT_vertex_weighting_glVertexWeightPointerEXT_pPointer = EXT_vertex_weighting_glVertexWeightPointerEXT_pPointer;
      GL11_glColorPointer_pointer = GL11_glColorPointer_pointer;
      GL11_glEdgeFlagPointer_pointer = GL11_glEdgeFlagPointer_pointer;
      GL11_glNormalPointer_pointer = GL11_glNormalPointer_pointer;
      GL11_glVertexPointer_pointer = GL11_glVertexPointer_pointer;
      GL14_glFogCoordPointer_data = GL14_glFogCoordPointer_data;
    }
  }
  
  void clear() { super.clear();
    ARB_matrix_palette_glMatrixIndexPointerARB_pPointer = null;
    ARB_vertex_blend_glWeightPointerARB_pPointer = null;
    EXT_fog_coord_glFogCoordPointerEXT_data = null;
    EXT_secondary_color_glSecondaryColorPointerEXT_pPointer = null;
    EXT_vertex_shader_glVariantPointerEXT_pAddr = null;
    EXT_vertex_weighting_glVertexWeightPointerEXT_pPointer = null;
    GL11_glColorPointer_pointer = null;
    GL11_glEdgeFlagPointer_pointer = null;
    GL11_glNormalPointer_pointer = null;
    GL11_glVertexPointer_pointer = null;
    GL14_glFogCoordPointer_data = null;
  }
}
