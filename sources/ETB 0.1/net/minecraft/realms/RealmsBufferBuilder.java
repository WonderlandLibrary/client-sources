package net.minecraft.realms;

import bfd;
import bfd.a;

public class RealmsBufferBuilder
{
  private bfd b;
  
  public RealmsBufferBuilder(bfd ☃)
  {
    b = ☃;
  }
  
  public RealmsBufferBuilder from(bfd ☃) {
    b = ☃;
    return this;
  }
  
  public void sortQuads(float ☃, float ☃, float ☃) {
    b.a(☃, ☃, ☃);
  }
  
  public void fixupQuadColor(int ☃) {
    b.a(☃);
  }
  
  public java.nio.ByteBuffer getBuffer() {
    return b.f();
  }
  
  public void postNormal(float ☃, float ☃, float ☃) {
    b.b(☃, ☃, ☃);
  }
  
  public int getDrawMode() {
    return b.i();
  }
  
  public void offset(double ☃, double ☃, double ☃) {
    b.c(☃, ☃, ☃);
  }
  
  public void restoreState(bfd.a ☃) {
    b.a(☃);
  }
  
  public void endVertex() {
    b.d();
  }
  
  public RealmsBufferBuilder normal(float ☃, float ☃, float ☃) {
    return from(b.c(☃, ☃, ☃));
  }
  
  public void end() {
    b.e();
  }
  
  public void begin(int ☃, bmu ☃) {
    b.a(☃, ☃);
  }
  
  public RealmsBufferBuilder color(int ☃, int ☃, int ☃, int ☃) {
    return from(b.b(☃, ☃, ☃, ☃));
  }
  
  public void faceTex2(int ☃, int ☃, int ☃, int ☃) {
    b.a(☃, ☃, ☃, ☃);
  }
  
  public void postProcessFacePosition(double ☃, double ☃, double ☃) {
    b.a(☃, ☃, ☃);
  }
  
  public void fixupVertexColor(float ☃, float ☃, float ☃, int ☃) {
    b.b(☃, ☃, ☃, ☃);
  }
  
  public RealmsBufferBuilder color(float ☃, float ☃, float ☃, float ☃) {
    return from(b.a(☃, ☃, ☃, ☃));
  }
  
  public RealmsVertexFormat getVertexFormat() {
    return new RealmsVertexFormat(b.g());
  }
  
  public void faceTint(float ☃, float ☃, float ☃, int ☃) {
    b.a(☃, ☃, ☃, ☃);
  }
  
  public RealmsBufferBuilder tex2(int ☃, int ☃) {
    return from(b.a(☃, ☃));
  }
  
  public void putBulkData(int[] ☃) {
    b.a(☃);
  }
  
  public RealmsBufferBuilder tex(double ☃, double ☃) {
    return from(b.a(☃, ☃));
  }
  
  public int getVertexCount() {
    return b.h();
  }
  
  public void clear() {
    b.b();
  }
  
  public RealmsBufferBuilder vertex(double ☃, double ☃, double ☃) {
    return from(b.b(☃, ☃, ☃));
  }
  
  public void fixupQuadColor(float ☃, float ☃, float ☃) {
    b.d(☃, ☃, ☃);
  }
  
  public void noColor() {
    b.c();
  }
}
