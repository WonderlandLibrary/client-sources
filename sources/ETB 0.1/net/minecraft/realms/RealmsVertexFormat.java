package net.minecraft.realms;

import bmu;
import bmv;
import java.util.List;

public class RealmsVertexFormat
{
  private bmu v;
  
  public RealmsVertexFormat(bmu ☃)
  {
    v = ☃;
  }
  
  public RealmsVertexFormat from(bmu ☃) {
    v = ☃;
    return this;
  }
  
  public bmu getVertexFormat() {
    return v;
  }
  
  public void clear() {
    v.a();
  }
  
  public int getUvOffset(int ☃) {
    return v.b(☃);
  }
  
  public int getElementCount() {
    return v.i();
  }
  
  public boolean hasColor() {
    return v.d();
  }
  
  public boolean hasUv(int ☃) {
    return v.a(☃);
  }
  
  public RealmsVertexFormatElement getElement(int ☃) {
    return new RealmsVertexFormatElement(v.c(☃));
  }
  
  public RealmsVertexFormat addElement(RealmsVertexFormatElement ☃) {
    return from(v.a(☃.getVertexFormatElement()));
  }
  
  public int getColorOffset() {
    return v.e();
  }
  
  public List<RealmsVertexFormatElement> getElements() {
    List<RealmsVertexFormatElement> ☃ = new java.util.ArrayList();
    
    for (bmv ☃ : v.h()) {
      ☃.add(new RealmsVertexFormatElement(☃));
    }
    
    return ☃;
  }
  
  public boolean hasNormal() {
    return v.b();
  }
  
  public int getVertexSize() {
    return v.g();
  }
  
  public int getOffset(int ☃) {
    return v.d(☃);
  }
  
  public int getNormalOffset() {
    return v.c();
  }
  
  public int getIntegerSize() {
    return v.f();
  }
  
  public boolean equals(Object ☃)
  {
    return v.equals(☃);
  }
  
  public int hashCode()
  {
    return v.hashCode();
  }
  
  public String toString()
  {
    return v.toString();
  }
}
