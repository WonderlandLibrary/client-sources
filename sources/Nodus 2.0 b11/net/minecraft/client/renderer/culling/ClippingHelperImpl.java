/*  1:   */ package net.minecraft.client.renderer.culling;
/*  2:   */ 
/*  3:   */ import java.nio.Buffer;
/*  4:   */ import java.nio.FloatBuffer;
/*  5:   */ import net.minecraft.client.renderer.GLAllocation;
/*  6:   */ import net.minecraft.util.MathHelper;
/*  7:   */ import org.lwjgl.opengl.GL11;
/*  8:   */ 
/*  9:   */ public class ClippingHelperImpl
/* 10:   */   extends ClippingHelper
/* 11:   */ {
/* 12:10 */   private static ClippingHelperImpl instance = new ClippingHelperImpl();
/* 13:11 */   private FloatBuffer projectionMatrixBuffer = GLAllocation.createDirectFloatBuffer(16);
/* 14:12 */   private FloatBuffer modelviewMatrixBuffer = GLAllocation.createDirectFloatBuffer(16);
/* 15:13 */   private FloatBuffer field_78564_h = GLAllocation.createDirectFloatBuffer(16);
/* 16:   */   private static final String __OBFID = "CL_00000975";
/* 17:   */   
/* 18:   */   public static ClippingHelper getInstance()
/* 19:   */   {
/* 20:21 */     instance.init();
/* 21:22 */     return instance;
/* 22:   */   }
/* 23:   */   
/* 24:   */   private void normalize(float[][] par1ArrayOfFloat, int par2)
/* 25:   */   {
/* 26:30 */     float var3 = MathHelper.sqrt_float(par1ArrayOfFloat[par2][0] * par1ArrayOfFloat[par2][0] + par1ArrayOfFloat[par2][1] * par1ArrayOfFloat[par2][1] + par1ArrayOfFloat[par2][2] * par1ArrayOfFloat[par2][2]);
/* 27:31 */     par1ArrayOfFloat[par2][0] /= var3;
/* 28:32 */     par1ArrayOfFloat[par2][1] /= var3;
/* 29:33 */     par1ArrayOfFloat[par2][2] /= var3;
/* 30:34 */     par1ArrayOfFloat[par2][3] /= var3;
/* 31:   */   }
/* 32:   */   
/* 33:   */   private void init()
/* 34:   */   {
/* 35:39 */     this.projectionMatrixBuffer.clear();
/* 36:40 */     this.modelviewMatrixBuffer.clear();
/* 37:41 */     this.field_78564_h.clear();
/* 38:42 */     GL11.glGetFloat(2983, this.projectionMatrixBuffer);
/* 39:43 */     GL11.glGetFloat(2982, this.modelviewMatrixBuffer);
/* 40:44 */     this.projectionMatrixBuffer.flip().limit(16);
/* 41:45 */     this.projectionMatrixBuffer.get(this.projectionMatrix);
/* 42:46 */     this.modelviewMatrixBuffer.flip().limit(16);
/* 43:47 */     this.modelviewMatrixBuffer.get(this.modelviewMatrix);
/* 44:48 */     this.clippingMatrix[0] = (this.modelviewMatrix[0] * this.projectionMatrix[0] + this.modelviewMatrix[1] * this.projectionMatrix[4] + this.modelviewMatrix[2] * this.projectionMatrix[8] + this.modelviewMatrix[3] * this.projectionMatrix[12]);
/* 45:49 */     this.clippingMatrix[1] = (this.modelviewMatrix[0] * this.projectionMatrix[1] + this.modelviewMatrix[1] * this.projectionMatrix[5] + this.modelviewMatrix[2] * this.projectionMatrix[9] + this.modelviewMatrix[3] * this.projectionMatrix[13]);
/* 46:50 */     this.clippingMatrix[2] = (this.modelviewMatrix[0] * this.projectionMatrix[2] + this.modelviewMatrix[1] * this.projectionMatrix[6] + this.modelviewMatrix[2] * this.projectionMatrix[10] + this.modelviewMatrix[3] * this.projectionMatrix[14]);
/* 47:51 */     this.clippingMatrix[3] = (this.modelviewMatrix[0] * this.projectionMatrix[3] + this.modelviewMatrix[1] * this.projectionMatrix[7] + this.modelviewMatrix[2] * this.projectionMatrix[11] + this.modelviewMatrix[3] * this.projectionMatrix[15]);
/* 48:52 */     this.clippingMatrix[4] = (this.modelviewMatrix[4] * this.projectionMatrix[0] + this.modelviewMatrix[5] * this.projectionMatrix[4] + this.modelviewMatrix[6] * this.projectionMatrix[8] + this.modelviewMatrix[7] * this.projectionMatrix[12]);
/* 49:53 */     this.clippingMatrix[5] = (this.modelviewMatrix[4] * this.projectionMatrix[1] + this.modelviewMatrix[5] * this.projectionMatrix[5] + this.modelviewMatrix[6] * this.projectionMatrix[9] + this.modelviewMatrix[7] * this.projectionMatrix[13]);
/* 50:54 */     this.clippingMatrix[6] = (this.modelviewMatrix[4] * this.projectionMatrix[2] + this.modelviewMatrix[5] * this.projectionMatrix[6] + this.modelviewMatrix[6] * this.projectionMatrix[10] + this.modelviewMatrix[7] * this.projectionMatrix[14]);
/* 51:55 */     this.clippingMatrix[7] = (this.modelviewMatrix[4] * this.projectionMatrix[3] + this.modelviewMatrix[5] * this.projectionMatrix[7] + this.modelviewMatrix[6] * this.projectionMatrix[11] + this.modelviewMatrix[7] * this.projectionMatrix[15]);
/* 52:56 */     this.clippingMatrix[8] = (this.modelviewMatrix[8] * this.projectionMatrix[0] + this.modelviewMatrix[9] * this.projectionMatrix[4] + this.modelviewMatrix[10] * this.projectionMatrix[8] + this.modelviewMatrix[11] * this.projectionMatrix[12]);
/* 53:57 */     this.clippingMatrix[9] = (this.modelviewMatrix[8] * this.projectionMatrix[1] + this.modelviewMatrix[9] * this.projectionMatrix[5] + this.modelviewMatrix[10] * this.projectionMatrix[9] + this.modelviewMatrix[11] * this.projectionMatrix[13]);
/* 54:58 */     this.clippingMatrix[10] = (this.modelviewMatrix[8] * this.projectionMatrix[2] + this.modelviewMatrix[9] * this.projectionMatrix[6] + this.modelviewMatrix[10] * this.projectionMatrix[10] + this.modelviewMatrix[11] * this.projectionMatrix[14]);
/* 55:59 */     this.clippingMatrix[11] = (this.modelviewMatrix[8] * this.projectionMatrix[3] + this.modelviewMatrix[9] * this.projectionMatrix[7] + this.modelviewMatrix[10] * this.projectionMatrix[11] + this.modelviewMatrix[11] * this.projectionMatrix[15]);
/* 56:60 */     this.clippingMatrix[12] = (this.modelviewMatrix[12] * this.projectionMatrix[0] + this.modelviewMatrix[13] * this.projectionMatrix[4] + this.modelviewMatrix[14] * this.projectionMatrix[8] + this.modelviewMatrix[15] * this.projectionMatrix[12]);
/* 57:61 */     this.clippingMatrix[13] = (this.modelviewMatrix[12] * this.projectionMatrix[1] + this.modelviewMatrix[13] * this.projectionMatrix[5] + this.modelviewMatrix[14] * this.projectionMatrix[9] + this.modelviewMatrix[15] * this.projectionMatrix[13]);
/* 58:62 */     this.clippingMatrix[14] = (this.modelviewMatrix[12] * this.projectionMatrix[2] + this.modelviewMatrix[13] * this.projectionMatrix[6] + this.modelviewMatrix[14] * this.projectionMatrix[10] + this.modelviewMatrix[15] * this.projectionMatrix[14]);
/* 59:63 */     this.clippingMatrix[15] = (this.modelviewMatrix[12] * this.projectionMatrix[3] + this.modelviewMatrix[13] * this.projectionMatrix[7] + this.modelviewMatrix[14] * this.projectionMatrix[11] + this.modelviewMatrix[15] * this.projectionMatrix[15]);
/* 60:64 */     this.frustum[0][0] = (this.clippingMatrix[3] - this.clippingMatrix[0]);
/* 61:65 */     this.frustum[0][1] = (this.clippingMatrix[7] - this.clippingMatrix[4]);
/* 62:66 */     this.frustum[0][2] = (this.clippingMatrix[11] - this.clippingMatrix[8]);
/* 63:67 */     this.frustum[0][3] = (this.clippingMatrix[15] - this.clippingMatrix[12]);
/* 64:68 */     normalize(this.frustum, 0);
/* 65:69 */     this.frustum[1][0] = (this.clippingMatrix[3] + this.clippingMatrix[0]);
/* 66:70 */     this.frustum[1][1] = (this.clippingMatrix[7] + this.clippingMatrix[4]);
/* 67:71 */     this.frustum[1][2] = (this.clippingMatrix[11] + this.clippingMatrix[8]);
/* 68:72 */     this.frustum[1][3] = (this.clippingMatrix[15] + this.clippingMatrix[12]);
/* 69:73 */     normalize(this.frustum, 1);
/* 70:74 */     this.frustum[2][0] = (this.clippingMatrix[3] + this.clippingMatrix[1]);
/* 71:75 */     this.frustum[2][1] = (this.clippingMatrix[7] + this.clippingMatrix[5]);
/* 72:76 */     this.frustum[2][2] = (this.clippingMatrix[11] + this.clippingMatrix[9]);
/* 73:77 */     this.frustum[2][3] = (this.clippingMatrix[15] + this.clippingMatrix[13]);
/* 74:78 */     normalize(this.frustum, 2);
/* 75:79 */     this.frustum[3][0] = (this.clippingMatrix[3] - this.clippingMatrix[1]);
/* 76:80 */     this.frustum[3][1] = (this.clippingMatrix[7] - this.clippingMatrix[5]);
/* 77:81 */     this.frustum[3][2] = (this.clippingMatrix[11] - this.clippingMatrix[9]);
/* 78:82 */     this.frustum[3][3] = (this.clippingMatrix[15] - this.clippingMatrix[13]);
/* 79:83 */     normalize(this.frustum, 3);
/* 80:84 */     this.frustum[4][0] = (this.clippingMatrix[3] - this.clippingMatrix[2]);
/* 81:85 */     this.frustum[4][1] = (this.clippingMatrix[7] - this.clippingMatrix[6]);
/* 82:86 */     this.frustum[4][2] = (this.clippingMatrix[11] - this.clippingMatrix[10]);
/* 83:87 */     this.frustum[4][3] = (this.clippingMatrix[15] - this.clippingMatrix[14]);
/* 84:88 */     normalize(this.frustum, 4);
/* 85:89 */     this.frustum[5][0] = (this.clippingMatrix[3] + this.clippingMatrix[2]);
/* 86:90 */     this.frustum[5][1] = (this.clippingMatrix[7] + this.clippingMatrix[6]);
/* 87:91 */     this.frustum[5][2] = (this.clippingMatrix[11] + this.clippingMatrix[10]);
/* 88:92 */     this.frustum[5][3] = (this.clippingMatrix[15] + this.clippingMatrix[14]);
/* 89:93 */     normalize(this.frustum, 5);
/* 90:   */   }
/* 91:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.culling.ClippingHelperImpl
 * JD-Core Version:    0.7.0.1
 */