package net.minecraft.realms;

import bmv;

public class RealmsVertexFormatElement {
  private bmv v;
  
  public RealmsVertexFormatElement(bmv ☃) {
    v = ☃;
  }
  
  public bmv getVertexFormatElement() {
    return v;
  }
  
  public boolean isPosition() {
    return v.f();
  }
  
  public int getIndex() {
    return v.d();
  }
  
  public int getByteSize() {
    return v.e();
  }
  
  public int getCount() {
    return v.c();
  }
  
  public int hashCode()
  {
    return v.hashCode();
  }
  
  public boolean equals(Object ☃)
  {
    return v.equals(☃);
  }
  
  public String toString()
  {
    return v.toString();
  }
}
