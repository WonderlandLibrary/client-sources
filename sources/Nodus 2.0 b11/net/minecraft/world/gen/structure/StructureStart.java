/*   1:    */ package net.minecraft.world.gen.structure;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.LinkedList;
/*   5:    */ import java.util.Random;
/*   6:    */ import net.minecraft.nbt.NBTTagCompound;
/*   7:    */ import net.minecraft.nbt.NBTTagList;
/*   8:    */ import net.minecraft.world.World;
/*   9:    */ 
/*  10:    */ public abstract class StructureStart
/*  11:    */ {
/*  12: 13 */   protected LinkedList components = new LinkedList();
/*  13:    */   protected StructureBoundingBox boundingBox;
/*  14:    */   private int field_143024_c;
/*  15:    */   private int field_143023_d;
/*  16:    */   private static final String __OBFID = "CL_00000513";
/*  17:    */   
/*  18:    */   public StructureStart() {}
/*  19:    */   
/*  20:    */   public StructureStart(int par1, int par2)
/*  21:    */   {
/*  22: 23 */     this.field_143024_c = par1;
/*  23: 24 */     this.field_143023_d = par2;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public StructureBoundingBox getBoundingBox()
/*  27:    */   {
/*  28: 29 */     return this.boundingBox;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public LinkedList getComponents()
/*  32:    */   {
/*  33: 34 */     return this.components;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void generateStructure(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
/*  37:    */   {
/*  38: 42 */     Iterator var4 = this.components.iterator();
/*  39: 44 */     while (var4.hasNext())
/*  40:    */     {
/*  41: 46 */       StructureComponent var5 = (StructureComponent)var4.next();
/*  42: 48 */       if ((var5.getBoundingBox().intersectsWith(par3StructureBoundingBox)) && (!var5.addComponentParts(par1World, par2Random, par3StructureBoundingBox))) {
/*  43: 50 */         var4.remove();
/*  44:    */       }
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected void updateBoundingBox()
/*  49:    */   {
/*  50: 60 */     this.boundingBox = StructureBoundingBox.getNewBoundingBox();
/*  51: 61 */     Iterator var1 = this.components.iterator();
/*  52: 63 */     while (var1.hasNext())
/*  53:    */     {
/*  54: 65 */       StructureComponent var2 = (StructureComponent)var1.next();
/*  55: 66 */       this.boundingBox.expandTo(var2.getBoundingBox());
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public NBTTagCompound func_143021_a(int par1, int par2)
/*  60:    */   {
/*  61: 72 */     NBTTagCompound var3 = new NBTTagCompound();
/*  62: 73 */     var3.setString("id", MapGenStructureIO.func_143033_a(this));
/*  63: 74 */     var3.setInteger("ChunkX", par1);
/*  64: 75 */     var3.setInteger("ChunkZ", par2);
/*  65: 76 */     var3.setTag("BB", this.boundingBox.func_151535_h());
/*  66: 77 */     NBTTagList var4 = new NBTTagList();
/*  67: 78 */     Iterator var5 = this.components.iterator();
/*  68: 80 */     while (var5.hasNext())
/*  69:    */     {
/*  70: 82 */       StructureComponent var6 = (StructureComponent)var5.next();
/*  71: 83 */       var4.appendTag(var6.func_143010_b());
/*  72:    */     }
/*  73: 86 */     var3.setTag("Children", var4);
/*  74: 87 */     func_143022_a(var3);
/*  75: 88 */     return var3;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void func_143022_a(NBTTagCompound par1NBTTagCompound) {}
/*  79:    */   
/*  80:    */   public void func_143020_a(World par1World, NBTTagCompound par2NBTTagCompound)
/*  81:    */   {
/*  82: 95 */     this.field_143024_c = par2NBTTagCompound.getInteger("ChunkX");
/*  83: 96 */     this.field_143023_d = par2NBTTagCompound.getInteger("ChunkZ");
/*  84: 98 */     if (par2NBTTagCompound.hasKey("BB")) {
/*  85:100 */       this.boundingBox = new StructureBoundingBox(par2NBTTagCompound.getIntArray("BB"));
/*  86:    */     }
/*  87:103 */     NBTTagList var3 = par2NBTTagCompound.getTagList("Children", 10);
/*  88:105 */     for (int var4 = 0; var4 < var3.tagCount(); var4++) {
/*  89:107 */       this.components.add(MapGenStructureIO.func_143032_b(var3.getCompoundTagAt(var4), par1World));
/*  90:    */     }
/*  91:110 */     func_143017_b(par2NBTTagCompound);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void func_143017_b(NBTTagCompound par1NBTTagCompound) {}
/*  95:    */   
/*  96:    */   protected void markAvailableHeight(World par1World, Random par2Random, int par3)
/*  97:    */   {
/*  98:120 */     int var4 = 63 - par3;
/*  99:121 */     int var5 = this.boundingBox.getYSize() + 1;
/* 100:123 */     if (var5 < var4) {
/* 101:125 */       var5 += par2Random.nextInt(var4 - var5);
/* 102:    */     }
/* 103:128 */     int var6 = var5 - this.boundingBox.maxY;
/* 104:129 */     this.boundingBox.offset(0, var6, 0);
/* 105:130 */     Iterator var7 = this.components.iterator();
/* 106:132 */     while (var7.hasNext())
/* 107:    */     {
/* 108:134 */       StructureComponent var8 = (StructureComponent)var7.next();
/* 109:135 */       var8.getBoundingBox().offset(0, var6, 0);
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   protected void setRandomHeight(World par1World, Random par2Random, int par3, int par4)
/* 114:    */   {
/* 115:141 */     int var5 = par4 - par3 + 1 - this.boundingBox.getYSize();
/* 116:142 */     boolean var6 = true;
/* 117:    */     int var10;
/* 118:    */     int var10;
/* 119:145 */     if (var5 > 1) {
/* 120:147 */       var10 = par3 + par2Random.nextInt(var5);
/* 121:    */     } else {
/* 122:151 */       var10 = par3;
/* 123:    */     }
/* 124:154 */     int var7 = var10 - this.boundingBox.minY;
/* 125:155 */     this.boundingBox.offset(0, var7, 0);
/* 126:156 */     Iterator var8 = this.components.iterator();
/* 127:158 */     while (var8.hasNext())
/* 128:    */     {
/* 129:160 */       StructureComponent var9 = (StructureComponent)var8.next();
/* 130:161 */       var9.getBoundingBox().offset(0, var7, 0);
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean isSizeableStructure()
/* 135:    */   {
/* 136:170 */     return true;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public int func_143019_e()
/* 140:    */   {
/* 141:175 */     return this.field_143024_c;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public int func_143018_f()
/* 145:    */   {
/* 146:180 */     return this.field_143023_d;
/* 147:    */   }
/* 148:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.structure.StructureStart
 * JD-Core Version:    0.7.0.1
 */