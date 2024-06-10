/*  1:   */ package net.minecraft.client.renderer;
/*  2:   */ 
/*  3:   */ import java.nio.ByteBuffer;
/*  4:   */ import java.nio.ByteOrder;
/*  5:   */ import java.nio.FloatBuffer;
/*  6:   */ import java.nio.IntBuffer;
/*  7:   */ import java.util.ArrayList;
/*  8:   */ import java.util.HashMap;
/*  9:   */ import java.util.Iterator;
/* 10:   */ import java.util.List;
/* 11:   */ import java.util.Map;
/* 12:   */ import java.util.Map.Entry;
/* 13:   */ import java.util.Set;
/* 14:   */ import org.lwjgl.opengl.GL11;
/* 15:   */ 
/* 16:   */ public class GLAllocation
/* 17:   */ {
/* 18:17 */   private static final Map mapDisplayLists = new HashMap();
/* 19:18 */   private static final List listDummy = new ArrayList();
/* 20:   */   private static final String __OBFID = "CL_00000630";
/* 21:   */   
/* 22:   */   public static synchronized int generateDisplayLists(int par0)
/* 23:   */   {
/* 24:26 */     int var1 = GL11.glGenLists(par0);
/* 25:27 */     mapDisplayLists.put(Integer.valueOf(var1), Integer.valueOf(par0));
/* 26:28 */     return var1;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static synchronized void deleteDisplayLists(int par0)
/* 30:   */   {
/* 31:33 */     GL11.glDeleteLists(par0, ((Integer)mapDisplayLists.remove(Integer.valueOf(par0))).intValue());
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static synchronized void deleteTexturesAndDisplayLists()
/* 35:   */   {
/* 36:41 */     Iterator var0 = mapDisplayLists.entrySet().iterator();
/* 37:43 */     while (var0.hasNext())
/* 38:   */     {
/* 39:45 */       Map.Entry var1 = (Map.Entry)var0.next();
/* 40:46 */       GL11.glDeleteLists(((Integer)var1.getKey()).intValue(), ((Integer)var1.getValue()).intValue());
/* 41:   */     }
/* 42:49 */     mapDisplayLists.clear();
/* 43:   */   }
/* 44:   */   
/* 45:   */   public static synchronized ByteBuffer createDirectByteBuffer(int par0)
/* 46:   */   {
/* 47:57 */     return ByteBuffer.allocateDirect(par0).order(ByteOrder.nativeOrder());
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static IntBuffer createDirectIntBuffer(int par0)
/* 51:   */   {
/* 52:65 */     return createDirectByteBuffer(par0 << 2).asIntBuffer();
/* 53:   */   }
/* 54:   */   
/* 55:   */   public static FloatBuffer createDirectFloatBuffer(int par0)
/* 56:   */   {
/* 57:74 */     return createDirectByteBuffer(par0 << 2).asFloatBuffer();
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.GLAllocation
 * JD-Core Version:    0.7.0.1
 */