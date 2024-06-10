/*   1:    */ package net.minecraft.pathfinding;
/*   2:    */ 
/*   3:    */ public class Path
/*   4:    */ {
/*   5:  6 */   private PathPoint[] pathPoints = new PathPoint[1024];
/*   6:    */   private int count;
/*   7:    */   private static final String __OBFID = "CL_00000573";
/*   8:    */   
/*   9:    */   public PathPoint addPoint(PathPoint par1PathPoint)
/*  10:    */   {
/*  11: 17 */     if (par1PathPoint.index >= 0) {
/*  12: 19 */       throw new IllegalStateException("OW KNOWS!");
/*  13:    */     }
/*  14: 23 */     if (this.count == this.pathPoints.length)
/*  15:    */     {
/*  16: 25 */       PathPoint[] var2 = new PathPoint[this.count << 1];
/*  17: 26 */       System.arraycopy(this.pathPoints, 0, var2, 0, this.count);
/*  18: 27 */       this.pathPoints = var2;
/*  19:    */     }
/*  20: 30 */     this.pathPoints[this.count] = par1PathPoint;
/*  21: 31 */     par1PathPoint.index = this.count;
/*  22: 32 */     sortBack(this.count++);
/*  23: 33 */     return par1PathPoint;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void clearPath()
/*  27:    */   {
/*  28: 42 */     this.count = 0;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public PathPoint dequeue()
/*  32:    */   {
/*  33: 50 */     PathPoint var1 = this.pathPoints[0];
/*  34: 51 */     this.pathPoints[0] = this.pathPoints[(--this.count)];
/*  35: 52 */     this.pathPoints[this.count] = null;
/*  36: 54 */     if (this.count > 0) {
/*  37: 56 */       sortForward(0);
/*  38:    */     }
/*  39: 59 */     var1.index = -1;
/*  40: 60 */     return var1;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void changeDistance(PathPoint par1PathPoint, float par2)
/*  44:    */   {
/*  45: 68 */     float var3 = par1PathPoint.distanceToTarget;
/*  46: 69 */     par1PathPoint.distanceToTarget = par2;
/*  47: 71 */     if (par2 < var3) {
/*  48: 73 */       sortBack(par1PathPoint.index);
/*  49:    */     } else {
/*  50: 77 */       sortForward(par1PathPoint.index);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void sortBack(int par1)
/*  55:    */   {
/*  56: 86 */     PathPoint var2 = this.pathPoints[par1];
/*  57:    */     int var4;
/*  58: 89 */     for (float var3 = var2.distanceToTarget; par1 > 0; par1 = var4)
/*  59:    */     {
/*  60: 91 */       var4 = par1 - 1 >> 1;
/*  61: 92 */       PathPoint var5 = this.pathPoints[var4];
/*  62: 94 */       if (var3 >= var5.distanceToTarget) {
/*  63:    */         break;
/*  64:    */       }
/*  65: 99 */       this.pathPoints[par1] = var5;
/*  66:100 */       var5.index = par1;
/*  67:    */     }
/*  68:103 */     this.pathPoints[par1] = var2;
/*  69:104 */     var2.index = par1;
/*  70:    */   }
/*  71:    */   
/*  72:    */   private void sortForward(int par1)
/*  73:    */   {
/*  74:112 */     PathPoint var2 = this.pathPoints[par1];
/*  75:113 */     float var3 = var2.distanceToTarget;
/*  76:    */     for (;;)
/*  77:    */     {
/*  78:117 */       int var4 = 1 + (par1 << 1);
/*  79:118 */       int var5 = var4 + 1;
/*  80:120 */       if (var4 >= this.count) {
/*  81:    */         break;
/*  82:    */       }
/*  83:125 */       PathPoint var6 = this.pathPoints[var4];
/*  84:126 */       float var7 = var6.distanceToTarget;
/*  85:    */       float var9;
/*  86:    */       PathPoint var8;
/*  87:    */       float var9;
/*  88:130 */       if (var5 >= this.count)
/*  89:    */       {
/*  90:132 */         PathPoint var8 = null;
/*  91:133 */         var9 = (1.0F / 1.0F);
/*  92:    */       }
/*  93:    */       else
/*  94:    */       {
/*  95:137 */         var8 = this.pathPoints[var5];
/*  96:138 */         var9 = var8.distanceToTarget;
/*  97:    */       }
/*  98:141 */       if (var7 < var9)
/*  99:    */       {
/* 100:143 */         if (var7 >= var3) {
/* 101:    */           break;
/* 102:    */         }
/* 103:148 */         this.pathPoints[par1] = var6;
/* 104:149 */         var6.index = par1;
/* 105:150 */         par1 = var4;
/* 106:    */       }
/* 107:    */       else
/* 108:    */       {
/* 109:154 */         if (var9 >= var3) {
/* 110:    */           break;
/* 111:    */         }
/* 112:159 */         this.pathPoints[par1] = var8;
/* 113:160 */         var8.index = par1;
/* 114:161 */         par1 = var5;
/* 115:    */       }
/* 116:    */     }
/* 117:165 */     this.pathPoints[par1] = var2;
/* 118:166 */     var2.index = par1;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean isPathEmpty()
/* 122:    */   {
/* 123:174 */     return this.count == 0;
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.pathfinding.Path
 * JD-Core Version:    0.7.0.1
 */