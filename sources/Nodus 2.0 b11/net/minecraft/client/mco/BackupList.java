/*  1:   */ package net.minecraft.client.mco;
/*  2:   */ 
/*  3:   */ import com.google.gson.JsonArray;
/*  4:   */ import com.google.gson.JsonElement;
/*  5:   */ import com.google.gson.JsonIOException;
/*  6:   */ import com.google.gson.JsonObject;
/*  7:   */ import com.google.gson.JsonParser;
/*  8:   */ import com.google.gson.JsonSyntaxException;
/*  9:   */ import java.util.ArrayList;
/* 10:   */ import java.util.Iterator;
/* 11:   */ import java.util.List;
/* 12:   */ 
/* 13:   */ public class BackupList
/* 14:   */ {
/* 15:   */   public List theBackupList;
/* 16:   */   private static final String __OBFID = "CL_00001165";
/* 17:   */   
/* 18:   */   public static BackupList func_148796_a(String p_148796_0_)
/* 19:   */   {
/* 20:18 */     JsonParser var1 = new JsonParser();
/* 21:19 */     BackupList var2 = new BackupList();
/* 22:20 */     var2.theBackupList = new ArrayList();
/* 23:   */     try
/* 24:   */     {
/* 25:24 */       JsonElement var3 = var1.parse(p_148796_0_).getAsJsonObject().get("backups");
/* 26:26 */       if (var3.isJsonArray())
/* 27:   */       {
/* 28:28 */         Iterator var4 = var3.getAsJsonArray().iterator();
/* 29:30 */         while (var4.hasNext()) {
/* 30:32 */           var2.theBackupList.add(Backup.func_148777_a((JsonElement)var4.next()));
/* 31:   */         }
/* 32:   */       }
/* 33:   */     }
/* 34:   */     catch (JsonIOException localJsonIOException) {}catch (JsonSyntaxException localJsonSyntaxException) {}
/* 35:45 */     return var2;
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.mco.BackupList
 * JD-Core Version:    0.7.0.1
 */