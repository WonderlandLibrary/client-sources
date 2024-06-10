/*  1:   */ package net.minecraft.world.gen.structure;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.Map;
/*  5:   */ import java.util.Map.Entry;
/*  6:   */ import java.util.Random;
/*  7:   */ import java.util.Set;
/*  8:   */ import net.minecraft.util.MathHelper;
/*  9:   */ 
/* 10:   */ public class MapGenMineshaft
/* 11:   */   extends MapGenStructure
/* 12:   */ {
/* 13:10 */   private double field_82673_e = 0.004D;
/* 14:   */   private static final String __OBFID = "CL_00000443";
/* 15:   */   
/* 16:   */   public MapGenMineshaft() {}
/* 17:   */   
/* 18:   */   public String func_143025_a()
/* 19:   */   {
/* 20:17 */     return "Mineshaft";
/* 21:   */   }
/* 22:   */   
/* 23:   */   public MapGenMineshaft(Map par1Map)
/* 24:   */   {
/* 25:22 */     Iterator var2 = par1Map.entrySet().iterator();
/* 26:24 */     while (var2.hasNext())
/* 27:   */     {
/* 28:26 */       Map.Entry var3 = (Map.Entry)var2.next();
/* 29:28 */       if (((String)var3.getKey()).equals("chance")) {
/* 30:30 */         this.field_82673_e = MathHelper.parseDoubleWithDefault((String)var3.getValue(), this.field_82673_e);
/* 31:   */       }
/* 32:   */     }
/* 33:   */   }
/* 34:   */   
/* 35:   */   protected boolean canSpawnStructureAtCoords(int par1, int par2)
/* 36:   */   {
/* 37:37 */     return (this.rand.nextDouble() < this.field_82673_e) && (this.rand.nextInt(80) < Math.max(Math.abs(par1), Math.abs(par2)));
/* 38:   */   }
/* 39:   */   
/* 40:   */   protected StructureStart getStructureStart(int par1, int par2)
/* 41:   */   {
/* 42:42 */     return new StructureMineshaftStart(this.worldObj, this.rand, par1, par2);
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.structure.MapGenMineshaft
 * JD-Core Version:    0.7.0.1
 */