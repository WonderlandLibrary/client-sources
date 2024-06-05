package net;

import cc.novoline.Novoline;
import cc.novoline.events.events.Render2DEvent;
import cc.novoline.modules.visual.ESP;
import cc.novoline.modules.visual.HUD;
import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class yS {
   private Minecraft c = Minecraft.getInstance();
   private int a = ARBShaderObjects.glCreateProgramObjectARB();
   private int b = this.a("void main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}", '謱');
   private int e = this.a("#version 120\nuniform sampler2D texture;\nuniform sampler2D texture2;\nuniform vec2 texelSize;\nuniform vec2 direction;\nuniform float alpha;\nuniform vec3 color;\nuniform int radius;\n\nfloat gaussian(float x, float sigma) {\n    float power_2 = x / sigma;\n    return (1.0 / (sigma * 2.50662827463)) * exp(-0.5 * (power_2 * power_2));\n}\n\nvoid main() {\n    vec2 texCoord = gl_TexCoord[0].st;\n\n    if (direction.y == 1)\n        if (texture2D(texture2, texCoord).a != 0.0) return;\n\n    vec4 blurred_color = vec4(0.0);\n\n    for (float r = -radius; r <= radius; r++) {\n        blurred_color += texture2D(texture, texCoord + r * texelSize * direction) * gaussian(r, radius / 2);\n    }\n\n    if (blurred_color.a > 0) {\n        if (direction.x == 0) {\n            gl_FragColor = vec4(color.rgb / 255.0, blurred_color.a * alpha);\n        } else {\n            gl_FragColor = vec4(color.rgb / 255.0, blurred_color.a);\n        }\n    }\n}", '謰');
   private HashMap g = new HashMap();
   Framebuffer d;
   Framebuffer f;

   private int a(String var1, int var2) {
      int var3 = ARBShaderObjects.glCreateShaderObjectARB(var2);
      ARBShaderObjects.glShaderSourceARB(var3, var1);
      ARBShaderObjects.glCompileShaderARB(var3);
      return var3;
   }

   public yS() {
      OpenGlHelper.glAttachShader(this.a, this.b);
      OpenGlHelper.glAttachShader(this.a, this.e);
      OpenGlHelper.glLinkProgram(this.a);
      this.g.put("texture", Integer.valueOf(GL20.glGetUniformLocation(this.a, "texture")));
      this.g.put("texture2", Integer.valueOf(GL20.glGetUniformLocation(this.a, "texture2")));
      this.g.put("texelSize", Integer.valueOf(GL20.glGetUniformLocation(this.a, "texelSize")));
      this.g.put("direction", Integer.valueOf(GL20.glGetUniformLocation(this.a, "direction")));
      this.g.put("alpha", Integer.valueOf(GL20.glGetUniformLocation(this.a, "alpha")));
      this.g.put("radius", Integer.valueOf(GL20.glGetUniformLocation(this.a, "radius")));
      this.g.put("color", Integer.valueOf(GL20.glGetUniformLocation(this.a, "color")));
   }

   private Framebuffer a(Framebuffer var1) {
      int var2 = HUD.e();
      if(var1 != null) {
         var1.deleteFramebuffer();
      }

      var1 = new Framebuffer(this.c.displayWidth, this.c.displayHeight, false);
      return var1;
   }

   private void a(Framebuffer var1, ScaledResolution var2) {
      GL11.glBindTexture(3553, var1.framebufferTexture);
      GL11.glBegin(7);
      GL11.glTexCoord2d(0.0D, 1.0D);
      GL11.glVertex2d(0.0D, 0.0D);
      GL11.glTexCoord2d(0.0D, 0.0D);
      GL11.glVertex2d(0.0D, (double)var2.getScaledHeight());
      GL11.glTexCoord2d(1.0D, 0.0D);
      GL11.glVertex2d((double)var2.getScaledWidth(), (double)var2.getScaledHeight());
      GL11.glTexCoord2d(1.0D, 1.0D);
      GL11.glVertex2d((double)var2.getScaledWidth(), 0.0D);
      GL11.glEnd();
   }

   private void a(ESP var1) {
      GL20.glUniform1i(((Integer)this.g.get("texture")).intValue(), 0);
      HUD.e();
      GL20.glUniform1i(((Integer)this.g.get("texture2")).intValue(), 8);
      GL20.glUniform2f(((Integer)this.g.get("texelSize")).intValue(), 1.0F / (float)this.c.displayWidth, 1.0F / (float)this.c.displayHeight);
      GL20.glUniform1f(((Integer)this.g.get("alpha")).intValue(), 0.011764706F * (float)((Integer)var1.a().get()).intValue());
      GL20.glUniform1i(((Integer)this.g.get("radius")).intValue(), ((Integer)var1.g().get()).intValue());
      int[] var3 = var1.c().equals("Static")?var1.j():(var1.c().equals("Rainbow")?var1.f():var1.h());
      GL20.glUniform3f(((Integer)this.g.get("color")).intValue(), (float)var3[0], (float)var3[1], (float)var3[2]);
   }

   private void a(int var1, int var2, int var3) {
      GL20.glUniform1i(((Integer)this.g.get("texture")).intValue(), 0);
      GL20.glUniform1i(((Integer)this.g.get("texture2")).intValue(), 8);
      GL20.glUniform2f(((Integer)this.g.get("texelSize")).intValue(), 1.0F / (float)this.c.displayWidth, 1.0F / (float)this.c.displayHeight);
      GL20.glUniform1f(((Integer)this.g.get("alpha")).intValue(), 3.0F);
      GL20.glUniform1i(((Integer)this.g.get("radius")).intValue(), 15);
      GL20.glUniform3f(((Integer)this.g.get("color")).intValue(), (float)var1, (float)var2, (float)var3);
   }

   public void a(ESP var1, Render2DEvent var2) {
      HUD.e();
      GL11.glDisable(3042);
      GlStateManager.pushMatrix();
      GlStateManager.pushAttrib();
      this.d = this.a(this.d);
      this.f = this.a(this.f);
      this.d.bindFramebuffer(true);
      this.c.entityRenderer.setupCameraTransform(this.c.timer.renderPartialTicks, 0);
      Stream var10000 = this.c.world.getLoadedEntityList().stream();
      var1.getClass();
      Iterator var4 = ((List)var10000.filter(var1::b).collect(Collectors.toList())).iterator();
      if(var4.hasNext()) {
         Entity var5 = (Entity)var4.next();
         this.c.getRenderManager().b(var5, this.c.timer.renderPartialTicks);
      }

      this.c.entityRenderer.setupOverlayRendering();
      GL11.glEnable(3042);
      GL11.glDisable(3008);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      this.f.bindFramebuffer(true);
      OpenGlHelper.glUseProgram(this.a);
      this.a(var1);
      GL20.glUniform2f(((Integer)this.g.get("direction")).intValue(), 1.0F, 0.0F);
      this.a(this.d, var2.getResolution());
      OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit + 7);
      this.a(this.d, var2.getResolution());
      OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
      this.c.getFramebuffer().bindFramebuffer(true);
      GL20.glUniform2f(((Integer)this.g.get("direction")).intValue(), 0.0F, 1.0F);
      this.a(this.f, var2.getResolution());
      OpenGlHelper.glUseProgram(0);
      GlStateManager.popMatrix();
      GlStateManager.popAttrib();
      GL11.glEnable(3008);
   }

   public void a(Render2DEvent var1, List var2, Color var3) {
      GL11.glDisable(3042);
      GlStateManager.pushMatrix();
      HUD.h();
      GlStateManager.pushAttrib();
      this.d = this.a(this.d);
      this.f = this.a(this.f);
      this.d.bindFramebuffer(true);
      this.c.entityRenderer.setupCameraTransform(this.c.timer.renderPartialTicks, 0);
      Iterator var5 = var2.iterator();
      if(var5.hasNext()) {
         EntityPlayer var6 = (EntityPlayer)var5.next();
         this.c.getRenderManager().b(var6, this.c.timer.renderPartialTicks);
      }

      this.c.entityRenderer.setupOverlayRendering();
      GL11.glEnable(3042);
      GL11.glDisable(3008);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      this.f.bindFramebuffer(true);
      OpenGlHelper.glUseProgram(this.a);
      this.a(var3.getRed(), var3.getGreen(), var3.getBlue());
      GL20.glUniform2f(((Integer)this.g.get("direction")).intValue(), 1.0F, 0.0F);
      this.a(this.d, var1.getResolution());
      OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit + 7);
      this.a(this.d, var1.getResolution());
      OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
      this.c.getFramebuffer().bindFramebuffer(true);
      GL20.glUniform2f(((Integer)this.g.get("direction")).intValue(), 0.0F, 1.0F);
      this.a(this.f, var1.getResolution());
      OpenGlHelper.glUseProgram(0);
      GlStateManager.popMatrix();
      GlStateManager.popAttrib();
      GL11.glEnable(3008);
   }

   public static boolean a() {
      int var0 = HUD.e();
      return !Novoline.getInstance().isAnythingNull() && ((ESP)Novoline.getInstance().getModuleManager().getModule(ESP.class)).isEnabled() && ((ESP)Novoline.getInstance().getModuleManager().getModule(ESP.class)).i().equals("Shader");
   }
}
