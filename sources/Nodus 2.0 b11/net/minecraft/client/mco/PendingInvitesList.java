/*  1:   */ package net.minecraft.client.mco;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Lists;
/*  4:   */ import com.google.gson.JsonArray;
/*  5:   */ import com.google.gson.JsonElement;
/*  6:   */ import com.google.gson.JsonIOException;
/*  7:   */ import com.google.gson.JsonObject;
/*  8:   */ import com.google.gson.JsonParser;
/*  9:   */ import com.google.gson.JsonSyntaxException;
/* 10:   */ import java.util.Iterator;
/* 11:   */ import java.util.List;
/* 12:   */ import net.minecraft.util.ValueObject;
/* 13:   */ 
/* 14:   */ public class PendingInvitesList
/* 15:   */   extends ValueObject
/* 16:   */ {
/* 17:15 */   public List field_148768_a = Lists.newArrayList();
/* 18:   */   private static final String __OBFID = "CL_00001171";
/* 19:   */   
/* 20:   */   public static PendingInvitesList func_148767_a(String p_148767_0_)
/* 21:   */   {
/* 22:20 */     PendingInvitesList var1 = new PendingInvitesList();
/* 23:   */     try
/* 24:   */     {
/* 25:24 */       JsonParser var2 = new JsonParser();
/* 26:25 */       JsonObject var3 = var2.parse(p_148767_0_).getAsJsonObject();
/* 27:27 */       if (var3.get("invites").isJsonArray())
/* 28:   */       {
/* 29:29 */         Iterator var4 = var3.get("invites").getAsJsonArray().iterator();
/* 30:31 */         while (var4.hasNext()) {
/* 31:33 */           var1.field_148768_a.add(PendingInvite.func_148773_a(((JsonElement)var4.next()).getAsJsonObject()));
/* 32:   */         }
/* 33:   */       }
/* 34:   */     }
/* 35:   */     catch (JsonIOException localJsonIOException) {}catch (JsonSyntaxException localJsonSyntaxException) {}
/* 36:46 */     return var1;
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.mco.PendingInvitesList
 * JD-Core Version:    0.7.0.1
 */