/*   1:    */ package net.minecraft.entity.ai.attributes;
/*   2:    */ 
/*   3:    */ import java.util.UUID;
/*   4:    */ import org.apache.commons.lang3.Validate;
/*   5:    */ 
/*   6:    */ public class AttributeModifier
/*   7:    */ {
/*   8:    */   private final double amount;
/*   9:    */   private final int operation;
/*  10:    */   private final String name;
/*  11:    */   private final UUID id;
/*  12:    */   private boolean isSaved;
/*  13:    */   private static final String __OBFID = "CL_00001564";
/*  14:    */   
/*  15:    */   public AttributeModifier(String par1Str, double par2, int par4)
/*  16:    */   {
/*  17: 21 */     this(UUID.randomUUID(), par1Str, par2, par4);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public AttributeModifier(UUID par1UUID, String par2Str, double par3, int par5)
/*  21:    */   {
/*  22: 26 */     this.isSaved = true;
/*  23: 27 */     this.id = par1UUID;
/*  24: 28 */     this.name = par2Str;
/*  25: 29 */     this.amount = par3;
/*  26: 30 */     this.operation = par5;
/*  27: 31 */     Validate.notEmpty(par2Str, "Modifier name cannot be empty", new Object[0]);
/*  28: 32 */     Validate.inclusiveBetween(Integer.valueOf(0), Integer.valueOf(2), Integer.valueOf(par5), "Invalid operation", new Object[0]);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public UUID getID()
/*  32:    */   {
/*  33: 37 */     return this.id;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getName()
/*  37:    */   {
/*  38: 42 */     return this.name;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int getOperation()
/*  42:    */   {
/*  43: 47 */     return this.operation;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public double getAmount()
/*  47:    */   {
/*  48: 52 */     return this.amount;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean isSaved()
/*  52:    */   {
/*  53: 60 */     return this.isSaved;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public AttributeModifier setSaved(boolean par1)
/*  57:    */   {
/*  58: 68 */     this.isSaved = par1;
/*  59: 69 */     return this;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean equals(Object par1Obj)
/*  63:    */   {
/*  64: 74 */     if (this == par1Obj) {
/*  65: 76 */       return true;
/*  66:    */     }
/*  67: 78 */     if ((par1Obj != null) && (getClass() == par1Obj.getClass()))
/*  68:    */     {
/*  69: 80 */       AttributeModifier var2 = (AttributeModifier)par1Obj;
/*  70: 82 */       if (this.id != null)
/*  71:    */       {
/*  72: 84 */         if (!this.id.equals(var2.id)) {
/*  73: 86 */           return false;
/*  74:    */         }
/*  75:    */       }
/*  76: 89 */       else if (var2.id != null) {
/*  77: 91 */         return false;
/*  78:    */       }
/*  79: 94 */       return true;
/*  80:    */     }
/*  81: 98 */     return false;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int hashCode()
/*  85:    */   {
/*  86:104 */     return this.id != null ? this.id.hashCode() : 0;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String toString()
/*  90:    */   {
/*  91:109 */     return "AttributeModifier{amount=" + this.amount + ", operation=" + this.operation + ", name='" + this.name + '\'' + ", id=" + this.id + ", serialize=" + this.isSaved + '}';
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.attributes.AttributeModifier
 * JD-Core Version:    0.7.0.1
 */