/*   1:    */ package net.minecraft.world.gen.structure;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Map;
/*   5:    */ import net.minecraft.nbt.NBTTagCompound;
/*   6:    */ import net.minecraft.world.World;
/*   7:    */ import org.apache.logging.log4j.LogManager;
/*   8:    */ import org.apache.logging.log4j.Logger;
/*   9:    */ 
/*  10:    */ public class MapGenStructureIO
/*  11:    */ {
/*  12: 12 */   private static final Logger logger = ;
/*  13: 13 */   private static Map field_143040_a = new HashMap();
/*  14: 14 */   private static Map field_143038_b = new HashMap();
/*  15: 15 */   private static Map field_143039_c = new HashMap();
/*  16: 16 */   private static Map field_143037_d = new HashMap();
/*  17:    */   private static final String __OBFID = "CL_00000509";
/*  18:    */   
/*  19:    */   private static void func_143034_b(Class par0Class, String par1Str)
/*  20:    */   {
/*  21: 21 */     field_143040_a.put(par1Str, par0Class);
/*  22: 22 */     field_143038_b.put(par0Class, par1Str);
/*  23:    */   }
/*  24:    */   
/*  25:    */   static void func_143031_a(Class par0Class, String par1Str)
/*  26:    */   {
/*  27: 27 */     field_143039_c.put(par1Str, par0Class);
/*  28: 28 */     field_143037_d.put(par0Class, par1Str);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static String func_143033_a(StructureStart par0StructureStart)
/*  32:    */   {
/*  33: 33 */     return (String)field_143038_b.get(par0StructureStart.getClass());
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static String func_143036_a(StructureComponent par0StructureComponent)
/*  37:    */   {
/*  38: 38 */     return (String)field_143037_d.get(par0StructureComponent.getClass());
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static StructureStart func_143035_a(NBTTagCompound par0NBTTagCompound, World par1World)
/*  42:    */   {
/*  43: 43 */     StructureStart var2 = null;
/*  44:    */     try
/*  45:    */     {
/*  46: 47 */       Class var3 = (Class)field_143040_a.get(par0NBTTagCompound.getString("id"));
/*  47: 49 */       if (var3 != null) {
/*  48: 51 */         var2 = (StructureStart)var3.newInstance();
/*  49:    */       }
/*  50:    */     }
/*  51:    */     catch (Exception var4)
/*  52:    */     {
/*  53: 56 */       logger.warn("Failed Start with id " + par0NBTTagCompound.getString("id"));
/*  54: 57 */       var4.printStackTrace();
/*  55:    */     }
/*  56: 60 */     if (var2 != null) {
/*  57: 62 */       var2.func_143020_a(par1World, par0NBTTagCompound);
/*  58:    */     } else {
/*  59: 66 */       logger.warn("Skipping Structure with id " + par0NBTTagCompound.getString("id"));
/*  60:    */     }
/*  61: 69 */     return var2;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static StructureComponent func_143032_b(NBTTagCompound par0NBTTagCompound, World par1World)
/*  65:    */   {
/*  66: 74 */     StructureComponent var2 = null;
/*  67:    */     try
/*  68:    */     {
/*  69: 78 */       Class var3 = (Class)field_143039_c.get(par0NBTTagCompound.getString("id"));
/*  70: 80 */       if (var3 != null) {
/*  71: 82 */         var2 = (StructureComponent)var3.newInstance();
/*  72:    */       }
/*  73:    */     }
/*  74:    */     catch (Exception var4)
/*  75:    */     {
/*  76: 87 */       logger.warn("Failed Piece with id " + par0NBTTagCompound.getString("id"));
/*  77: 88 */       var4.printStackTrace();
/*  78:    */     }
/*  79: 91 */     if (var2 != null) {
/*  80: 93 */       var2.func_143009_a(par1World, par0NBTTagCompound);
/*  81:    */     } else {
/*  82: 97 */       logger.warn("Skipping Piece with id " + par0NBTTagCompound.getString("id"));
/*  83:    */     }
/*  84:100 */     return var2;
/*  85:    */   }
/*  86:    */   
/*  87:    */   static
/*  88:    */   {
/*  89:105 */     func_143034_b(StructureMineshaftStart.class, "Mineshaft");
/*  90:106 */     func_143034_b(MapGenVillage.Start.class, "Village");
/*  91:107 */     func_143034_b(MapGenNetherBridge.Start.class, "Fortress");
/*  92:108 */     func_143034_b(MapGenStronghold.Start.class, "Stronghold");
/*  93:109 */     func_143034_b(MapGenScatteredFeature.Start.class, "Temple");
/*  94:110 */     StructureMineshaftPieces.func_143048_a();
/*  95:111 */     StructureVillagePieces.func_143016_a();
/*  96:112 */     StructureNetherBridgePieces.func_143049_a();
/*  97:113 */     StructureStrongholdPieces.func_143046_a();
/*  98:114 */     ComponentScatteredFeaturePieces.func_143045_a();
/*  99:    */   }
/* 100:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.structure.MapGenStructureIO
 * JD-Core Version:    0.7.0.1
 */