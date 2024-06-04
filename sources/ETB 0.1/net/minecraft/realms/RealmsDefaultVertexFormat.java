package net.minecraft.realms;

import bmu;

public class RealmsDefaultVertexFormat { public RealmsDefaultVertexFormat() {}
  
  public static final RealmsVertexFormat BLOCK = new RealmsVertexFormat(new bmu());
  public static final RealmsVertexFormat BLOCK_NORMALS = new RealmsVertexFormat(new bmu());
  public static final RealmsVertexFormat ENTITY = new RealmsVertexFormat(new bmu());
  public static final RealmsVertexFormat PARTICLE = new RealmsVertexFormat(new bmu());
  public static final RealmsVertexFormat POSITION = new RealmsVertexFormat(new bmu());
  public static final RealmsVertexFormat POSITION_COLOR = new RealmsVertexFormat(new bmu());
  public static final RealmsVertexFormat POSITION_TEX = new RealmsVertexFormat(new bmu());
  public static final RealmsVertexFormat POSITION_NORMAL = new RealmsVertexFormat(new bmu());
  public static final RealmsVertexFormat POSITION_TEX_COLOR = new RealmsVertexFormat(new bmu());
  public static final RealmsVertexFormat POSITION_TEX_NORMAL = new RealmsVertexFormat(new bmu());
  public static final RealmsVertexFormat POSITION_TEX2_COLOR = new RealmsVertexFormat(new bmu());
  public static final RealmsVertexFormat POSITION_TEX_COLOR_NORMAL = new RealmsVertexFormat(new bmu());
  
  public static final RealmsVertexFormatElement ELEMENT_POSITION = new RealmsVertexFormatElement(new bmv(0, bmv.a.a, bmv.b.a, 3));
  public static final RealmsVertexFormatElement ELEMENT_COLOR = new RealmsVertexFormatElement(new bmv(0, bmv.a.b, bmv.b.c, 4));
  public static final RealmsVertexFormatElement ELEMENT_UV0 = new RealmsVertexFormatElement(new bmv(0, bmv.a.a, bmv.b.d, 2));
  public static final RealmsVertexFormatElement ELEMENT_UV1 = new RealmsVertexFormatElement(new bmv(1, bmv.a.e, bmv.b.d, 2));
  public static final RealmsVertexFormatElement ELEMENT_NORMAL = new RealmsVertexFormatElement(new bmv(0, bmv.a.c, bmv.b.b, 3));
  public static final RealmsVertexFormatElement ELEMENT_PADDING = new RealmsVertexFormatElement(new bmv(0, bmv.a.c, bmv.b.g, 1));
  
  static {
    BLOCK.addElement(ELEMENT_POSITION);
    BLOCK.addElement(ELEMENT_COLOR);
    BLOCK.addElement(ELEMENT_UV0);
    BLOCK.addElement(ELEMENT_UV1);
    
    BLOCK_NORMALS.addElement(ELEMENT_POSITION);
    BLOCK_NORMALS.addElement(ELEMENT_COLOR);
    BLOCK_NORMALS.addElement(ELEMENT_UV0);
    BLOCK_NORMALS.addElement(ELEMENT_NORMAL);
    BLOCK_NORMALS.addElement(ELEMENT_PADDING);
    
    ENTITY.addElement(ELEMENT_POSITION);
    ENTITY.addElement(ELEMENT_UV0);
    ENTITY.addElement(ELEMENT_NORMAL);
    ENTITY.addElement(ELEMENT_PADDING);
    
    PARTICLE.addElement(ELEMENT_POSITION);
    PARTICLE.addElement(ELEMENT_UV0);
    PARTICLE.addElement(ELEMENT_COLOR);
    PARTICLE.addElement(ELEMENT_UV1);
    
    POSITION.addElement(ELEMENT_POSITION);
    POSITION_COLOR.addElement(ELEMENT_POSITION);
    POSITION_COLOR.addElement(ELEMENT_COLOR);
    
    POSITION_TEX.addElement(ELEMENT_POSITION);
    POSITION_TEX.addElement(ELEMENT_UV0);
    
    POSITION_NORMAL.addElement(ELEMENT_POSITION);
    POSITION_NORMAL.addElement(ELEMENT_NORMAL);
    POSITION_NORMAL.addElement(ELEMENT_PADDING);
    
    POSITION_TEX_COLOR.addElement(ELEMENT_POSITION);
    POSITION_TEX_COLOR.addElement(ELEMENT_UV0);
    POSITION_TEX_COLOR.addElement(ELEMENT_COLOR);
    
    POSITION_TEX_NORMAL.addElement(ELEMENT_POSITION);
    POSITION_TEX_NORMAL.addElement(ELEMENT_UV0);
    POSITION_TEX_NORMAL.addElement(ELEMENT_NORMAL);
    POSITION_TEX_NORMAL.addElement(ELEMENT_PADDING);
    
    POSITION_TEX2_COLOR.addElement(ELEMENT_POSITION);
    POSITION_TEX2_COLOR.addElement(ELEMENT_UV0);
    POSITION_TEX2_COLOR.addElement(ELEMENT_UV1);
    POSITION_TEX2_COLOR.addElement(ELEMENT_COLOR);
    
    POSITION_TEX_COLOR_NORMAL.addElement(ELEMENT_POSITION);
    POSITION_TEX_COLOR_NORMAL.addElement(ELEMENT_UV0);
    POSITION_TEX_COLOR_NORMAL.addElement(ELEMENT_COLOR);
    POSITION_TEX_COLOR_NORMAL.addElement(ELEMENT_NORMAL);
    POSITION_TEX_COLOR_NORMAL.addElement(ELEMENT_PADDING);
  }
}
