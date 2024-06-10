/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.BiMap;
/*   4:    */ import com.google.common.collect.HashBiMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Map;
/*   7:    */ 
/*   8:    */ public class RegistryNamespaced
/*   9:    */   extends RegistrySimple
/*  10:    */   implements IObjectIntIterable
/*  11:    */ {
/*  12: 11 */   protected final ObjectIntIdentityMap underlyingIntegerMap = new ObjectIntIdentityMap();
/*  13:    */   protected final Map field_148758_b;
/*  14:    */   private static final String __OBFID = "CL_00001206";
/*  15:    */   
/*  16:    */   public RegistryNamespaced()
/*  17:    */   {
/*  18: 17 */     this.field_148758_b = ((BiMap)this.registryObjects).inverse();
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void addObject(int p_148756_1_, String p_148756_2_, Object p_148756_3_)
/*  22:    */   {
/*  23: 25 */     this.underlyingIntegerMap.func_148746_a(p_148756_3_, p_148756_1_);
/*  24: 26 */     putObject(ensureNamespaced(p_148756_2_), p_148756_3_);
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected Map createUnderlyingMap()
/*  28:    */   {
/*  29: 34 */     return HashBiMap.create();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Object getObject(String p_148751_1_)
/*  33:    */   {
/*  34: 39 */     return super.getObject(ensureNamespaced(p_148751_1_));
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getNameForObject(Object p_148750_1_)
/*  38:    */   {
/*  39: 47 */     return (String)this.field_148758_b.get(p_148750_1_);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean containsKey(String p_148752_1_)
/*  43:    */   {
/*  44: 55 */     return super.containsKey(ensureNamespaced(p_148752_1_));
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int getIDForObject(Object p_148757_1_)
/*  48:    */   {
/*  49: 63 */     return this.underlyingIntegerMap.func_148747_b(p_148757_1_);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Object getObjectForID(int p_148754_1_)
/*  53:    */   {
/*  54: 71 */     return this.underlyingIntegerMap.func_148745_a(p_148754_1_);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Iterator iterator()
/*  58:    */   {
/*  59: 76 */     return this.underlyingIntegerMap.iterator();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean containsID(int p_148753_1_)
/*  63:    */   {
/*  64: 85 */     return this.underlyingIntegerMap.func_148744_b(p_148753_1_);
/*  65:    */   }
/*  66:    */   
/*  67:    */   private static String ensureNamespaced(String p_148755_0_)
/*  68:    */   {
/*  69: 94 */     return p_148755_0_.indexOf(':') == -1 ? "minecraft:" + p_148755_0_ : p_148755_0_;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean containsKey(Object p_148741_1_)
/*  73:    */   {
/*  74:102 */     return containsKey((String)p_148741_1_);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Object getObject(Object par1Obj)
/*  78:    */   {
/*  79:107 */     return getObject((String)par1Obj);
/*  80:    */   }
/*  81:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.RegistryNamespaced
 * JD-Core Version:    0.7.0.1
 */