/*   1:    */ package net.minecraft.client.shader;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Maps;
/*   4:    */ import java.io.BufferedInputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.nio.ByteBuffer;
/*   7:    */ import java.util.Map;
/*   8:    */ import net.minecraft.client.resources.IResource;
/*   9:    */ import net.minecraft.client.resources.IResourceManager;
/*  10:    */ import net.minecraft.client.util.JsonException;
/*  11:    */ import net.minecraft.util.ResourceLocation;
/*  12:    */ import org.apache.commons.io.IOUtils;
/*  13:    */ import org.apache.commons.lang3.StringUtils;
/*  14:    */ import org.lwjgl.BufferUtils;
/*  15:    */ import org.lwjgl.opengl.GL20;
/*  16:    */ 
/*  17:    */ public class ShaderLoader
/*  18:    */ {
/*  19:    */   private final ShaderType field_148061_a;
/*  20:    */   private final String field_148059_b;
/*  21:    */   private int field_148060_c;
/*  22: 21 */   private int field_148058_d = 0;
/*  23:    */   private static final String __OBFID = "CL_00001043";
/*  24:    */   
/*  25:    */   private ShaderLoader(ShaderType p_i45091_1_, int p_i45091_2_, String p_i45091_3_)
/*  26:    */   {
/*  27: 26 */     this.field_148061_a = p_i45091_1_;
/*  28: 27 */     this.field_148060_c = p_i45091_2_;
/*  29: 28 */     this.field_148059_b = p_i45091_3_;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void func_148056_a(ShaderManager p_148056_1_)
/*  33:    */   {
/*  34: 33 */     this.field_148058_d += 1;
/*  35: 34 */     GL20.glAttachShader(p_148056_1_.func_147986_h(), this.field_148060_c);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void func_148054_b(ShaderManager p_148054_1_)
/*  39:    */   {
/*  40: 39 */     this.field_148058_d -= 1;
/*  41: 41 */     if (this.field_148058_d <= 0)
/*  42:    */     {
/*  43: 43 */       GL20.glDeleteShader(this.field_148060_c);
/*  44: 44 */       this.field_148061_a.func_148064_d().remove(this.field_148059_b);
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String func_148055_a()
/*  49:    */   {
/*  50: 50 */     return this.field_148059_b;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static ShaderLoader func_148057_a(IResourceManager p_148057_0_, ShaderType p_148057_1_, String p_148057_2_)
/*  54:    */     throws IOException
/*  55:    */   {
/*  56: 55 */     ShaderLoader var3 = (ShaderLoader)p_148057_1_.func_148064_d().get(p_148057_2_);
/*  57: 57 */     if (var3 == null)
/*  58:    */     {
/*  59: 59 */       ResourceLocation var4 = new ResourceLocation("shaders/program/" + p_148057_2_ + p_148057_1_.func_148063_b());
/*  60: 60 */       BufferedInputStream var5 = new BufferedInputStream(p_148057_0_.getResource(var4).getInputStream());
/*  61: 61 */       byte[] var6 = IOUtils.toByteArray(var5);
/*  62: 62 */       ByteBuffer var7 = BufferUtils.createByteBuffer(var6.length);
/*  63: 63 */       var7.put(var6);
/*  64: 64 */       var7.position(0);
/*  65: 65 */       int var8 = GL20.glCreateShader(p_148057_1_.func_148065_c());
/*  66: 66 */       GL20.glShaderSource(var8, var7);
/*  67: 67 */       GL20.glCompileShader(var8);
/*  68: 69 */       if (GL20.glGetShaderi(var8, 35713) == 0)
/*  69:    */       {
/*  70: 71 */         String var9 = StringUtils.trim(GL20.glGetShaderInfoLog(var8, 32768));
/*  71: 72 */         JsonException var10 = new JsonException("Couldn't compile " + p_148057_1_.func_148062_a() + " program: " + var9);
/*  72: 73 */         var10.func_151381_b(var4.getResourcePath());
/*  73: 74 */         throw var10;
/*  74:    */       }
/*  75: 77 */       var3 = new ShaderLoader(p_148057_1_, var8, p_148057_2_);
/*  76: 78 */       p_148057_1_.func_148064_d().put(p_148057_2_, var3);
/*  77:    */     }
/*  78: 81 */     return var3;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static enum ShaderType
/*  82:    */   {
/*  83: 86 */     VERTEX("VERTEX", 0, "vertex", ".vsh", 35633),  FRAGMENT("FRAGMENT", 1, "fragment", ".fsh", 35632);
/*  84:    */     
/*  85:    */     private final String field_148072_c;
/*  86:    */     private final String field_148069_d;
/*  87:    */     private final int field_148070_e;
/*  88: 91 */     private final Map field_148067_f = Maps.newHashMap();
/*  89: 93 */     private static final ShaderType[] $VALUES = { VERTEX, FRAGMENT };
/*  90:    */     private static final String __OBFID = "CL_00001044";
/*  91:    */     
/*  92:    */     private ShaderType(String p_i45090_1_, int p_i45090_2_, String p_i45090_3_, String p_i45090_4_, int p_i45090_5_)
/*  93:    */     {
/*  94: 98 */       this.field_148072_c = p_i45090_3_;
/*  95: 99 */       this.field_148069_d = p_i45090_4_;
/*  96:100 */       this.field_148070_e = p_i45090_5_;
/*  97:    */     }
/*  98:    */     
/*  99:    */     public String func_148062_a()
/* 100:    */     {
/* 101:105 */       return this.field_148072_c;
/* 102:    */     }
/* 103:    */     
/* 104:    */     protected String func_148063_b()
/* 105:    */     {
/* 106:110 */       return this.field_148069_d;
/* 107:    */     }
/* 108:    */     
/* 109:    */     protected int func_148065_c()
/* 110:    */     {
/* 111:115 */       return this.field_148070_e;
/* 112:    */     }
/* 113:    */     
/* 114:    */     protected Map func_148064_d()
/* 115:    */     {
/* 116:120 */       return this.field_148067_f;
/* 117:    */     }
/* 118:    */   }
/* 119:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.shader.ShaderLoader
 * JD-Core Version:    0.7.0.1
 */