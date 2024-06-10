/*   1:    */ package net.minecraft.client.shader;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.vecmath.Matrix4f;
/*   7:    */ import net.minecraft.client.Minecraft;
/*   8:    */ import net.minecraft.client.renderer.Tessellator;
/*   9:    */ import net.minecraft.client.resources.IResourceManager;
/*  10:    */ import net.minecraft.client.util.JsonException;
/*  11:    */ import org.lwjgl.opengl.GL11;
/*  12:    */ 
/*  13:    */ public class Shader
/*  14:    */ {
/*  15:    */   private final ShaderManager manager;
/*  16:    */   public final Framebuffer framebufferIn;
/*  17:    */   public final Framebuffer framebufferOut;
/*  18: 18 */   private final List listAuxFramebuffers = Lists.newArrayList();
/*  19: 19 */   private final List listAuxNames = Lists.newArrayList();
/*  20: 20 */   private final List listAuxWidths = Lists.newArrayList();
/*  21: 21 */   private final List listAuxHeights = Lists.newArrayList();
/*  22:    */   private Matrix4f projectionMatrix;
/*  23:    */   private static final String __OBFID = "CL_00001042";
/*  24:    */   
/*  25:    */   public Shader(IResourceManager p_i45089_1_, String p_i45089_2_, Framebuffer p_i45089_3_, Framebuffer p_i45089_4_)
/*  26:    */     throws JsonException
/*  27:    */   {
/*  28: 27 */     this.manager = new ShaderManager(p_i45089_1_, p_i45089_2_);
/*  29: 28 */     this.framebufferIn = p_i45089_3_;
/*  30: 29 */     this.framebufferOut = p_i45089_4_;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void deleteShader()
/*  34:    */   {
/*  35: 34 */     this.manager.func_147988_a();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void addAuxFramebuffer(String p_148041_1_, Object p_148041_2_, int p_148041_3_, int p_148041_4_)
/*  39:    */   {
/*  40: 39 */     this.listAuxNames.add(this.listAuxNames.size(), p_148041_1_);
/*  41: 40 */     this.listAuxFramebuffers.add(this.listAuxFramebuffers.size(), p_148041_2_);
/*  42: 41 */     this.listAuxWidths.add(this.listAuxWidths.size(), Integer.valueOf(p_148041_3_));
/*  43: 42 */     this.listAuxHeights.add(this.listAuxHeights.size(), Integer.valueOf(p_148041_4_));
/*  44:    */   }
/*  45:    */   
/*  46:    */   private void preLoadShader()
/*  47:    */   {
/*  48: 47 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  49: 48 */     GL11.glDisable(3042);
/*  50: 49 */     GL11.glDisable(2929);
/*  51: 50 */     GL11.glDisable(3008);
/*  52: 51 */     GL11.glDisable(2912);
/*  53: 52 */     GL11.glDisable(2896);
/*  54: 53 */     GL11.glDisable(2903);
/*  55: 54 */     GL11.glEnable(3553);
/*  56: 55 */     GL11.glBindTexture(3553, 0);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setProjectionMatrix(Matrix4f p_148045_1_)
/*  60:    */   {
/*  61: 60 */     this.projectionMatrix = p_148045_1_;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void loadShader(float p_148042_1_)
/*  65:    */   {
/*  66: 65 */     preLoadShader();
/*  67: 66 */     this.framebufferIn.unbindFramebuffer();
/*  68: 67 */     float var2 = this.framebufferOut.framebufferTextureWidth;
/*  69: 68 */     float var3 = this.framebufferOut.framebufferTextureHeight;
/*  70: 69 */     GL11.glViewport(0, 0, (int)var2, (int)var3);
/*  71: 70 */     this.manager.func_147992_a("DiffuseSampler", this.framebufferIn);
/*  72: 72 */     for (int var4 = 0; var4 < this.listAuxFramebuffers.size(); var4++)
/*  73:    */     {
/*  74: 74 */       this.manager.func_147992_a((String)this.listAuxNames.get(var4), this.listAuxFramebuffers.get(var4));
/*  75: 75 */       this.manager.func_147984_b("AuxSize" + var4).func_148087_a(((Integer)this.listAuxWidths.get(var4)).intValue(), ((Integer)this.listAuxHeights.get(var4)).intValue());
/*  76:    */     }
/*  77: 78 */     this.manager.func_147984_b("ProjMat").func_148088_a(this.projectionMatrix);
/*  78: 79 */     this.manager.func_147984_b("InSize").func_148087_a(this.framebufferIn.framebufferTextureWidth, this.framebufferIn.framebufferTextureHeight);
/*  79: 80 */     this.manager.func_147984_b("OutSize").func_148087_a(var2, var3);
/*  80: 81 */     this.manager.func_147984_b("Time").func_148090_a(p_148042_1_);
/*  81: 82 */     Minecraft var8 = Minecraft.getMinecraft();
/*  82: 83 */     this.manager.func_147984_b("ScreenSize").func_148087_a(var8.displayWidth, var8.displayHeight);
/*  83: 84 */     this.manager.func_147995_c();
/*  84: 85 */     this.framebufferOut.framebufferClear();
/*  85: 86 */     this.framebufferOut.bindFramebuffer(false);
/*  86: 87 */     GL11.glDepthMask(false);
/*  87: 88 */     GL11.glColorMask(true, true, true, false);
/*  88: 89 */     Tessellator var5 = Tessellator.instance;
/*  89: 90 */     var5.startDrawingQuads();
/*  90: 91 */     var5.setColorOpaque_I(-1);
/*  91: 92 */     var5.addVertex(0.0D, var3, 500.0D);
/*  92: 93 */     var5.addVertex(var2, var3, 500.0D);
/*  93: 94 */     var5.addVertex(var2, 0.0D, 500.0D);
/*  94: 95 */     var5.addVertex(0.0D, 0.0D, 500.0D);
/*  95: 96 */     var5.draw();
/*  96: 97 */     GL11.glDepthMask(true);
/*  97: 98 */     GL11.glColorMask(true, true, true, true);
/*  98: 99 */     this.manager.func_147993_b();
/*  99:100 */     this.framebufferOut.unbindFramebuffer();
/* 100:101 */     this.framebufferIn.unbindFramebufferTexture();
/* 101:102 */     Iterator var6 = this.listAuxFramebuffers.iterator();
/* 102:104 */     while (var6.hasNext())
/* 103:    */     {
/* 104:106 */       Object var7 = var6.next();
/* 105:108 */       if ((var7 instanceof Framebuffer)) {
/* 106:110 */         ((Framebuffer)var7).unbindFramebufferTexture();
/* 107:    */       }
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public ShaderManager getShaderManager()
/* 112:    */   {
/* 113:117 */     return this.manager;
/* 114:    */   }
/* 115:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.shader.Shader
 * JD-Core Version:    0.7.0.1
 */