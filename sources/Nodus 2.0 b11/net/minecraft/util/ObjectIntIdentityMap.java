/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import com.google.common.base.Predicates;
/*  4:   */ import com.google.common.collect.Iterators;
/*  5:   */ import gnu.trove.map.hash.TIntIntHashMap;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.Iterator;
/*  8:   */ import java.util.List;
/*  9:   */ 
/* 10:   */ public class ObjectIntIdentityMap
/* 11:   */   implements IObjectIntIterable
/* 12:   */ {
/* 13:12 */   private TIntIntHashMap field_148749_a = new TIntIntHashMap(256, 0.5F, -1, -1);
/* 14:13 */   private List field_148748_b = new ArrayList();
/* 15:   */   private static final String __OBFID = "CL_00001203";
/* 16:   */   
/* 17:   */   public void func_148746_a(Object p_148746_1_, int p_148746_2_)
/* 18:   */   {
/* 19:18 */     this.field_148749_a.put(System.identityHashCode(p_148746_1_), p_148746_2_);
/* 20:20 */     while (this.field_148748_b.size() <= p_148746_2_) {
/* 21:22 */       this.field_148748_b.add(null);
/* 22:   */     }
/* 23:25 */     this.field_148748_b.set(p_148746_2_, p_148746_1_);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int func_148747_b(Object p_148747_1_)
/* 27:   */   {
/* 28:30 */     return this.field_148749_a.get(System.identityHashCode(p_148747_1_));
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Object func_148745_a(int p_148745_1_)
/* 32:   */   {
/* 33:35 */     return (p_148745_1_ >= 0) && (p_148745_1_ < this.field_148748_b.size()) ? this.field_148748_b.get(p_148745_1_) : null;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Iterator iterator()
/* 37:   */   {
/* 38:40 */     return Iterators.filter(this.field_148748_b.iterator(), Predicates.notNull());
/* 39:   */   }
/* 40:   */   
/* 41:   */   public boolean func_148744_b(int p_148744_1_)
/* 42:   */   {
/* 43:45 */     return func_148745_a(p_148744_1_) != null;
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.ObjectIntIdentityMap
 * JD-Core Version:    0.7.0.1
 */