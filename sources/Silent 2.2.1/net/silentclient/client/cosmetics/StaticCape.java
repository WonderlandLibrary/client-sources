package net.silentclient.client.cosmetics;

import org.lwjgl.opengl.GL11;

import net.silentclient.client.cosmetics.dynamiccurved.Box;

public class StaticCape {
	private final float curvePoints;
	  
	  private final float horizCurve;
	  
	  private final float vertCurve;
	  
	  private Integer staticCloakCallList = null;
	  
	  public StaticCape(float paramFloat1, float paramFloat2, float paramFloat3) {
	    this.curvePoints = paramFloat1;
	    this.horizCurve = paramFloat2;
	    this.vertCurve = paramFloat3;
	  }
	  
	  public void init() {
	    this.staticCloakCallList = Integer.valueOf(GL11.glGenLists(1));
	    float f1 = 22.0F;
	    float f2 = 23.0F;
	    float f3 = 1.0F / f2;
	    float f4 = 17.0F / f2;
	    float f5 = 1.0F / f1;
	    float f6 = 11.0F / f1;
	    float f7 = 12.0F / f1;
	    float f8 = 22.0F / f1;
	    float f9 = 21.0F / f1;
	    Box b1 = new Box(0.0F, 0.0F, 5.0F);
	    Box b2 = new Box(0.0F + this.horizCurve, -16.0F + this.vertCurve, 5.0F);
	    Box b3 = new Box(0.0F + this.horizCurve, -16.0F + this.vertCurve, -5.0F);
	    Box b4 = new Box(0.0F, 0.0F, -5.0F);
	    Box b5 = new Box(1.0F, 0.0F, 5.0F);
	    Box b6 = new Box(1.0F + this.horizCurve, -16.0F + this.vertCurve, 5.0F);
	    Box b7 = new Box(1.0F + this.horizCurve, -16.0F + this.vertCurve, -5.0F);
	    Box b8 = new Box(1.0F, 0.0F, -5.0F);
	    Box b9 = new Box(0.0F, -10.0F, -5.0F);
	    Box b10 = new Box(0.0F, -10.0F, -5.0F);
	    GL11.glNewList(this.staticCloakCallList.intValue(), 4864);
	    GL11.glBegin(5);
	    byte b;
	    for (b = 0; b <= this.curvePoints; b++) {
	      float f10 = b / this.curvePoints;
	      float f11 = (1.0F - f10) * (1.0F - f10) * b7.a + 2.0F * (1.0F - f10) * f10 * b9.a + f10 * f10 * b8.a;
	      float f12 = (1.0F - f10) * (1.0F - f10) * b7.b + 2.0F * (1.0F - f10) * f10 * b9.b + f10 * f10 * b8.b;
	      GL11.glTexCoord2f(f5, f4 - (f4 - f3) * f10);
	      GL11.glVertex3f(f11, f12, b5.c);
	      GL11.glTexCoord2f(f6, f4 - (f4 - f3) * f10);
	      GL11.glVertex3f(f11, f12, b8.c);
	    } 
	    GL11.glTexCoord2f(f5, f3);
	    GL11.glVertex3f(b5.a, b5.b, b5.c);
	    GL11.glTexCoord2f(f6, f3);
	    GL11.glVertex3f(b8.a, b8.b, b8.c);
	    GL11.glTexCoord2f(f5, 0.0F);
	    GL11.glVertex3f(b1.a, b1.b, b1.c);
	    GL11.glTexCoord2f(f6, 0.0F);
	    GL11.glVertex3f(b4.a, b4.b, b4.c);
	    for (b = 0; b <= this.curvePoints; b++) {
	      float f10 = b / this.curvePoints;
	      float f11 = (1.0F - f10) * (1.0F - f10) * b4.a + 2.0F * (1.0F - f10) * f10 * b10.a + f10 * f10 * b3.a;
	      float f12 = (1.0F - f10) * (1.0F - f10) * b4.b + 2.0F * (1.0F - f10) * f10 * b10.b + f10 * f10 * b3.b;
	      GL11.glTexCoord2f(f7, f3 + (f4 - f3) * f10);
	      GL11.glVertex3f(f11, f12, b8.c);
	      f11 = (1.0F - f10) * (1.0F - f10) * b8.a + 2.0F * (1.0F - f10) * f10 * b9.a + f10 * f10 * b7.a;
	      f12 = (1.0F - f10) * (1.0F - f10) * b8.b + 2.0F * (1.0F - f10) * f10 * b9.b + f10 * f10 * b7.b;
	      GL11.glTexCoord2f(f6, f3 + (f4 - f3) * f10);
	      GL11.glVertex3f(f11, f12, b8.c);
	    } 
	    GL11.glTexCoord2f(f9, 0.0F);
	    GL11.glVertex3f(b7.a, b7.b, b7.c);
	    GL11.glTexCoord2f(f6, 0.0F);
	    GL11.glVertex3f(b6.a, b6.b, b6.c);
	    GL11.glTexCoord2f(f9, f3);
	    GL11.glVertex3f(b3.a, b3.b, b3.c);
	    GL11.glTexCoord2f(f6, f3);
	    GL11.glVertex3f(b2.a, b2.b, b2.c);
	    for (b = 0; b <= this.curvePoints; b++) {
	      float f10 = b / this.curvePoints;
	      float f11 = (1.0F - f10) * (1.0F - f10) * b3.a + 2.0F * (1.0F - f10) * f10 * b10.a + f10 * f10 * b4.a;
	      float f12 = (1.0F - f10) * (1.0F - f10) * b3.b + 2.0F * (1.0F - f10) * f10 * b10.b + f10 * f10 * b4.b;
	      GL11.glTexCoord2f(f7, f4 - (f4 - f3) * f10);
	      GL11.glVertex3f(f11, f12, b8.c);
	      GL11.glTexCoord2f(f8, f4 - (f4 - f3) * f10);
	      GL11.glVertex3f(f11, f12, b5.c);
	    } 
	    for (b = 0; b <= this.curvePoints; b++) {
	      float f10 = b / this.curvePoints;
	      if (b == 0) {
	        float f13 = (1.0F - f10) * (1.0F - f10) * b4.a + 2.0F * (1.0F - f10) * f10 * b10.a + f10 * f10 * b3.a;
	        float f14 = (1.0F - f10) * (1.0F - f10) * b4.b + 2.0F * (1.0F - f10) * f10 * b10.b + f10 * f10 * b3.b;
	        GL11.glTexCoord2f(0.0F, f3 + (f4 - f3) * f10);
	        GL11.glVertex3f(f13, f14, b5.c);
	        GL11.glTexCoord2f(0.0F, f3 + (f4 - f3) * f10);
	        GL11.glVertex3f(f13, f14, b5.c);
	      } 
	      float f11 = (1.0F - f10) * (1.0F - f10) * b8.a + 2.0F * (1.0F - f10) * f10 * b9.a + f10 * f10 * b7.a;
	      float f12 = (1.0F - f10) * (1.0F - f10) * b8.b + 2.0F * (1.0F - f10) * f10 * b9.b + f10 * f10 * b7.b;
	      GL11.glTexCoord2f(f5, f3 + (f4 - f3) * f10);
	      GL11.glVertex3f(f11, f12, b5.c);
	      f11 = (1.0F - f10) * (1.0F - f10) * b4.a + 2.0F * (1.0F - f10) * f10 * b10.a + f10 * f10 * b3.a;
	      f12 = (1.0F - f10) * (1.0F - f10) * b4.b + 2.0F * (1.0F - f10) * f10 * b10.b + f10 * f10 * b3.b;
	      GL11.glTexCoord2f(0.0F, f3 + (f4 - f3) * f10);
	      GL11.glVertex3f(f11, f12, b5.c);
	    } 
	    GL11.glEnd();
	    GL11.glEndList();
	  }
	  
	  public void renderStaticCape() {
	    if (this.staticCloakCallList == null)
	      init(); 
	    GL11.glCallList(this.staticCloakCallList.intValue());
	  }
}
