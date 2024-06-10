/*  1:   */ package net.minecraft.world.gen.structure;
/*  2:   */ 
/*  3:   */ import java.util.LinkedList;
/*  4:   */ import java.util.Random;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ 
/*  7:   */ public class StructureMineshaftStart
/*  8:   */   extends StructureStart
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00000450";
/* 11:   */   
/* 12:   */   public StructureMineshaftStart() {}
/* 13:   */   
/* 14:   */   public StructureMineshaftStart(World par1World, Random par2Random, int par3, int par4)
/* 15:   */   {
/* 16:14 */     super(par3, par4);
/* 17:15 */     StructureMineshaftPieces.Room var5 = new StructureMineshaftPieces.Room(0, par2Random, (par3 << 4) + 2, (par4 << 4) + 2);
/* 18:16 */     this.components.add(var5);
/* 19:17 */     var5.buildComponent(var5, this.components, par2Random);
/* 20:18 */     updateBoundingBox();
/* 21:19 */     markAvailableHeight(par1World, par2Random, 10);
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.structure.StructureMineshaftStart
 * JD-Core Version:    0.7.0.1
 */