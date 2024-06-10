/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import com.google.gson.Gson;
/*  4:   */ import com.google.gson.TypeAdapter;
/*  5:   */ import com.google.gson.TypeAdapterFactory;
/*  6:   */ import com.google.gson.reflect.TypeToken;
/*  7:   */ import com.google.gson.stream.JsonReader;
/*  8:   */ import com.google.gson.stream.JsonToken;
/*  9:   */ import com.google.gson.stream.JsonWriter;
/* 10:   */ import java.io.IOException;
/* 11:   */ import java.util.HashMap;
/* 12:   */ import java.util.Locale;
/* 13:   */ 
/* 14:   */ public class EnumTypeAdapterFactory
/* 15:   */   implements TypeAdapterFactory
/* 16:   */ {
/* 17:   */   private static final String __OBFID = "CL_00001494";
/* 18:   */   
/* 19:   */   public TypeAdapter create(Gson p_create_1_, TypeToken p_create_2_)
/* 20:   */   {
/* 21:20 */     Class var3 = p_create_2_.getRawType();
/* 22:22 */     if (!var3.isEnum()) {
/* 23:24 */       return null;
/* 24:   */     }
/* 25:28 */     final HashMap var4 = new HashMap();
/* 26:29 */     Object[] var5 = var3.getEnumConstants();
/* 27:30 */     int var6 = var5.length;
/* 28:32 */     for (int var7 = 0; var7 < var6; var7++)
/* 29:   */     {
/* 30:34 */       Object var8 = var5[var7];
/* 31:35 */       var4.put(func_151232_a(var8), var8);
/* 32:   */     }
/* 33:38 */     new TypeAdapter()
/* 34:   */     {
/* 35:   */       private static final String __OBFID = "CL_00001495";
/* 36:   */       
/* 37:   */       public void write(JsonWriter p_write_1_, Object p_write_2_)
/* 38:   */         throws IOException
/* 39:   */       {
/* 40:43 */         if (p_write_2_ == null) {
/* 41:45 */           p_write_1_.nullValue();
/* 42:   */         } else {
/* 43:49 */           p_write_1_.value(EnumTypeAdapterFactory.this.func_151232_a(p_write_2_));
/* 44:   */         }
/* 45:   */       }
/* 46:   */       
/* 47:   */       public Object read(JsonReader p_read_1_)
/* 48:   */         throws IOException
/* 49:   */       {
/* 50:54 */         if (p_read_1_.peek() == JsonToken.NULL)
/* 51:   */         {
/* 52:56 */           p_read_1_.nextNull();
/* 53:57 */           return null;
/* 54:   */         }
/* 55:61 */         return var4.get(p_read_1_.nextString());
/* 56:   */       }
/* 57:   */     };
/* 58:   */   }
/* 59:   */   
/* 60:   */   private String func_151232_a(Object p_151232_1_)
/* 61:   */   {
/* 62:70 */     return (p_151232_1_ instanceof Enum) ? ((Enum)p_151232_1_).name().toLowerCase(Locale.US) : p_151232_1_.toString().toLowerCase(Locale.US);
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.EnumTypeAdapterFactory
 * JD-Core Version:    0.7.0.1
 */