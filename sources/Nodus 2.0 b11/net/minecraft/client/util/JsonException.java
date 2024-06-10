/*  1:   */ package net.minecraft.client.util;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Lists;
/*  4:   */ import java.io.FileNotFoundException;
/*  5:   */ import java.io.IOException;
/*  6:   */ import java.util.List;
/*  7:   */ import org.apache.commons.lang3.StringUtils;
/*  8:   */ 
/*  9:   */ public class JsonException
/* 10:   */   extends IOException
/* 11:   */ {
/* 12:11 */   private final List field_151383_a = Lists.newArrayList();
/* 13:   */   private final String field_151382_b;
/* 14:   */   private static final String __OBFID = "CL_00001414";
/* 15:   */   
/* 16:   */   public JsonException(String p_i45279_1_)
/* 17:   */   {
/* 18:17 */     this.field_151383_a.add(new Entry(null));
/* 19:18 */     this.field_151382_b = p_i45279_1_;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public JsonException(String p_i45280_1_, Throwable p_i45280_2_)
/* 23:   */   {
/* 24:23 */     super(p_i45280_2_);
/* 25:24 */     this.field_151383_a.add(new Entry(null));
/* 26:25 */     this.field_151382_b = p_i45280_1_;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void func_151380_a(String p_151380_1_)
/* 30:   */   {
/* 31:30 */     ((Entry)this.field_151383_a.get(0)).func_151373_a(p_151380_1_);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void func_151381_b(String p_151381_1_)
/* 35:   */   {
/* 36:35 */     ((Entry)this.field_151383_a.get(0)).field_151376_a = p_151381_1_;
/* 37:36 */     this.field_151383_a.add(0, new Entry(null));
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String getMessage()
/* 41:   */   {
/* 42:41 */     return "Invalid " + ((Entry)this.field_151383_a.get(this.field_151383_a.size() - 1)).toString() + ": " + this.field_151382_b;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public static JsonException func_151379_a(Exception p_151379_0_)
/* 46:   */   {
/* 47:46 */     if ((p_151379_0_ instanceof JsonException)) {
/* 48:48 */       return (JsonException)p_151379_0_;
/* 49:   */     }
/* 50:52 */     String var1 = p_151379_0_.getMessage();
/* 51:54 */     if ((p_151379_0_ instanceof FileNotFoundException)) {
/* 52:56 */       var1 = "File not found";
/* 53:   */     }
/* 54:59 */     return new JsonException(var1, p_151379_0_);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public static class Entry
/* 58:   */   {
/* 59:   */     private String field_151376_a;
/* 60:   */     private final List field_151375_b;
/* 61:   */     private static final String __OBFID = "CL_00001416";
/* 62:   */     
/* 63:   */     private Entry()
/* 64:   */     {
/* 65:71 */       this.field_151376_a = null;
/* 66:72 */       this.field_151375_b = Lists.newArrayList();
/* 67:   */     }
/* 68:   */     
/* 69:   */     private void func_151373_a(String p_151373_1_)
/* 70:   */     {
/* 71:77 */       this.field_151375_b.add(0, p_151373_1_);
/* 72:   */     }
/* 73:   */     
/* 74:   */     public String func_151372_b()
/* 75:   */     {
/* 76:82 */       return StringUtils.join(this.field_151375_b, "->");
/* 77:   */     }
/* 78:   */     
/* 79:   */     public String toString()
/* 80:   */     {
/* 81:87 */       return !this.field_151375_b.isEmpty() ? "(Unknown file) " + func_151372_b() : this.field_151376_a != null ? this.field_151376_a : !this.field_151375_b.isEmpty() ? this.field_151376_a + " " + func_151372_b() : "(Unknown file)";
/* 82:   */     }
/* 83:   */     
/* 84:   */     Entry(Object p_i45278_1_)
/* 85:   */     {
/* 86:92 */       this();
/* 87:   */     }
/* 88:   */   }
/* 89:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.util.JsonException
 * JD-Core Version:    0.7.0.1
 */