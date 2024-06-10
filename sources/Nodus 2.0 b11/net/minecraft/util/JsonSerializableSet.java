/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.ForwardingSet;
/*  4:   */ import com.google.common.collect.Sets;
/*  5:   */ import com.google.gson.JsonArray;
/*  6:   */ import com.google.gson.JsonElement;
/*  7:   */ import com.google.gson.JsonPrimitive;
/*  8:   */ import java.util.Iterator;
/*  9:   */ import java.util.Set;
/* 10:   */ 
/* 11:   */ public class JsonSerializableSet
/* 12:   */   extends ForwardingSet
/* 13:   */   implements IJsonSerializable
/* 14:   */ {
/* 15:15 */   private final Set underlyingSet = Sets.newHashSet();
/* 16:   */   private static final String __OBFID = "CL_00001482";
/* 17:   */   
/* 18:   */   public JsonElement getSerializableElement()
/* 19:   */   {
/* 20:23 */     JsonArray var1 = new JsonArray();
/* 21:24 */     Iterator var2 = iterator();
/* 22:26 */     while (var2.hasNext())
/* 23:   */     {
/* 24:28 */       String var3 = (String)var2.next();
/* 25:29 */       var1.add(new JsonPrimitive(var3));
/* 26:   */     }
/* 27:32 */     return var1;
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected Set delegate()
/* 31:   */   {
/* 32:37 */     return this.underlyingSet;
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.JsonSerializableSet
 * JD-Core Version:    0.7.0.1
 */