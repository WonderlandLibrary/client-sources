/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.TypeAdapterFactory;
/*    */ import com.google.gson.reflect.TypeToken;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonToken;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class EnumTypeAdapterFactory
/*    */   implements TypeAdapterFactory
/*    */ {
/*    */   public <T> TypeAdapter<T> create(Gson p_create_1_, TypeToken<T> p_create_2_) {
/* 19 */     Class<T> oclass = p_create_2_.getRawType();
/*    */     
/* 21 */     if (!oclass.isEnum())
/*    */     {
/* 23 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 27 */     final Map<String, T> map = Maps.newHashMap(); byte b; int i;
/*    */     T[] arrayOfT;
/* 29 */     for (i = (arrayOfT = oclass.getEnumConstants()).length, b = 0; b < i; ) { T t = arrayOfT[b];
/*    */       
/* 31 */       map.put(func_151232_a(t), t);
/*    */       b++; }
/*    */     
/* 34 */     return new TypeAdapter<T>()
/*    */       {
/*    */         public void write(JsonWriter p_write_1_, T p_write_2_) throws IOException
/*    */         {
/* 38 */           if (p_write_2_ == null) {
/*    */             
/* 40 */             p_write_1_.nullValue();
/*    */           }
/*    */           else {
/*    */             
/* 44 */             p_write_1_.value(EnumTypeAdapterFactory.this.func_151232_a(p_write_2_));
/*    */           } 
/*    */         }
/*    */         
/*    */         public T read(JsonReader p_read_1_) throws IOException {
/* 49 */           if (p_read_1_.peek() == JsonToken.NULL) {
/*    */             
/* 51 */             p_read_1_.nextNull();
/* 52 */             return null;
/*    */           } 
/*    */ 
/*    */           
/* 56 */           return (T)map.get(p_read_1_.nextString());
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private String func_151232_a(Object p_151232_1_) {
/* 65 */     return (p_151232_1_ instanceof Enum) ? ((Enum)p_151232_1_).name().toLowerCase(Locale.US) : p_151232_1_.toString().toLowerCase(Locale.US);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\EnumTypeAdapterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */