/*   1:    */ package org.newdawn.slick.util.pathfinding.navmesh;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ 
/*   5:    */ public class NavMesh
/*   6:    */ {
/*   7: 16 */   private ArrayList spaces = new ArrayList();
/*   8:    */   
/*   9:    */   public NavMesh() {}
/*  10:    */   
/*  11:    */   public NavMesh(ArrayList spaces)
/*  12:    */   {
/*  13: 31 */     this.spaces.addAll(spaces);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public int getSpaceCount()
/*  17:    */   {
/*  18: 40 */     return this.spaces.size();
/*  19:    */   }
/*  20:    */   
/*  21:    */   public Space getSpace(int index)
/*  22:    */   {
/*  23: 50 */     return (Space)this.spaces.get(index);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void addSpace(Space space)
/*  27:    */   {
/*  28: 59 */     this.spaces.add(space);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Space findSpace(float x, float y)
/*  32:    */   {
/*  33: 70 */     for (int i = 0; i < this.spaces.size(); i++)
/*  34:    */     {
/*  35: 71 */       Space space = getSpace(i);
/*  36: 72 */       if (space.contains(x, y)) {
/*  37: 73 */         return space;
/*  38:    */       }
/*  39:    */     }
/*  40: 77 */     return null;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public NavPath findPath(float sx, float sy, float tx, float ty, boolean optimize)
/*  44:    */   {
/*  45: 91 */     Space source = findSpace(sx, sy);
/*  46: 92 */     Space target = findSpace(tx, ty);
/*  47: 94 */     if ((source == null) || (target == null)) {
/*  48: 95 */       return null;
/*  49:    */     }
/*  50: 98 */     for (int i = 0; i < this.spaces.size(); i++) {
/*  51: 99 */       ((Space)this.spaces.get(i)).clearCost();
/*  52:    */     }
/*  53:101 */     target.fill(source, tx, ty, 0.0F);
/*  54:102 */     if (target.getCost() == 3.4028235E+38F) {
/*  55:103 */       return null;
/*  56:    */     }
/*  57:105 */     if (source.getCost() == 3.4028235E+38F) {
/*  58:106 */       return null;
/*  59:    */     }
/*  60:109 */     NavPath path = new NavPath();
/*  61:110 */     path.push(new Link(sx, sy, null));
/*  62:111 */     if (source.pickLowestCost(target, path))
/*  63:    */     {
/*  64:112 */       path.push(new Link(tx, ty, null));
/*  65:113 */       if (optimize) {
/*  66:114 */         optimize(path);
/*  67:    */       }
/*  68:116 */       return path;
/*  69:    */     }
/*  70:119 */     return null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   private boolean isClear(float x1, float y1, float x2, float y2, float step)
/*  74:    */   {
/*  75:133 */     float dx = x2 - x1;
/*  76:134 */     float dy = y2 - y1;
/*  77:135 */     float len = (float)Math.sqrt(dx * dx + dy * dy);
/*  78:136 */     dx *= step;
/*  79:137 */     dx /= len;
/*  80:138 */     dy *= step;
/*  81:139 */     dy /= len;
/*  82:140 */     int steps = (int)(len / step);
/*  83:142 */     for (int i = 0; i < steps; i++)
/*  84:    */     {
/*  85:143 */       float x = x1 + dx * i;
/*  86:144 */       float y = y1 + dy * i;
/*  87:146 */       if (findSpace(x, y) == null) {
/*  88:147 */         return false;
/*  89:    */       }
/*  90:    */     }
/*  91:151 */     return true;
/*  92:    */   }
/*  93:    */   
/*  94:    */   private void optimize(NavPath path)
/*  95:    */   {
/*  96:161 */     int pt = 0;
/*  97:163 */     while (pt < path.length() - 2)
/*  98:    */     {
/*  99:164 */       float sx = path.getX(pt);
/* 100:165 */       float sy = path.getY(pt);
/* 101:166 */       float nx = path.getX(pt + 2);
/* 102:167 */       float ny = path.getY(pt + 2);
/* 103:169 */       if (isClear(sx, sy, nx, ny, 0.1F)) {
/* 104:170 */         path.remove(pt + 1);
/* 105:    */       } else {
/* 106:172 */         pt++;
/* 107:    */       }
/* 108:    */     }
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.pathfinding.navmesh.NavMesh
 * JD-Core Version:    0.7.0.1
 */