/*   1:    */ package org.newdawn.slick.util.pathfinding.navmesh;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import org.newdawn.slick.util.pathfinding.Mover;
/*   5:    */ import org.newdawn.slick.util.pathfinding.PathFindingContext;
/*   6:    */ import org.newdawn.slick.util.pathfinding.TileBasedMap;
/*   7:    */ 
/*   8:    */ public class NavMeshBuilder
/*   9:    */   implements PathFindingContext
/*  10:    */ {
/*  11:    */   private int sx;
/*  12:    */   private int sy;
/*  13: 21 */   private float smallestSpace = 0.2F;
/*  14:    */   private boolean tileBased;
/*  15:    */   
/*  16:    */   public NavMesh build(TileBasedMap map)
/*  17:    */   {
/*  18: 33 */     return build(map, true);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public NavMesh build(TileBasedMap map, boolean tileBased)
/*  22:    */   {
/*  23: 45 */     this.tileBased = tileBased;
/*  24:    */     
/*  25: 47 */     ArrayList spaces = new ArrayList();
/*  26:    */     Space space;
/*  27: 49 */     if (tileBased) {
/*  28: 50 */       for (int x = 0; x < map.getWidthInTiles(); x++) {
/*  29: 51 */         for (int y = 0; y < map.getHeightInTiles(); y++) {
/*  30: 52 */           if (!map.blocked(this, x, y)) {
/*  31: 53 */             spaces.add(new Space(x, y, 1.0F, 1.0F));
/*  32:    */           }
/*  33:    */         }
/*  34:    */       }
/*  35:    */     } else {
/*  36: 58 */       space = new Space(0.0F, 0.0F, map.getWidthInTiles(), map.getHeightInTiles());
/*  37:    */     }
/*  38: 63 */     while (mergeSpaces(spaces)) {}
/*  39: 64 */     linkSpaces(spaces);
/*  40:    */     
/*  41: 66 */     return new NavMesh(spaces);
/*  42:    */   }
/*  43:    */   
/*  44:    */   private boolean mergeSpaces(ArrayList spaces)
/*  45:    */   {
/*  46: 78 */     for (int source = 0; source < spaces.size(); source++)
/*  47:    */     {
/*  48: 79 */       Space a = (Space)spaces.get(source);
/*  49: 81 */       for (int target = source + 1; target < spaces.size(); target++)
/*  50:    */       {
/*  51: 82 */         Space b = (Space)spaces.get(target);
/*  52: 84 */         if (a.canMerge(b))
/*  53:    */         {
/*  54: 85 */           spaces.remove(a);
/*  55: 86 */           spaces.remove(b);
/*  56: 87 */           spaces.add(a.merge(b));
/*  57: 88 */           return true;
/*  58:    */         }
/*  59:    */       }
/*  60:    */     }
/*  61: 93 */     return false;
/*  62:    */   }
/*  63:    */   
/*  64:    */   private void linkSpaces(ArrayList spaces)
/*  65:    */   {
/*  66:102 */     for (int source = 0; source < spaces.size(); source++)
/*  67:    */     {
/*  68:103 */       Space a = (Space)spaces.get(source);
/*  69:105 */       for (int target = source + 1; target < spaces.size(); target++)
/*  70:    */       {
/*  71:106 */         Space b = (Space)spaces.get(target);
/*  72:108 */         if (a.hasJoinedEdge(b))
/*  73:    */         {
/*  74:109 */           a.link(b);
/*  75:110 */           b.link(a);
/*  76:    */         }
/*  77:    */       }
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean clear(TileBasedMap map, Space space)
/*  82:    */   {
/*  83:124 */     if (this.tileBased) {
/*  84:125 */       return true;
/*  85:    */     }
/*  86:128 */     float x = 0.0F;
/*  87:129 */     boolean donex = false;
/*  88:131 */     while (x < space.getWidth())
/*  89:    */     {
/*  90:132 */       float y = 0.0F;
/*  91:133 */       boolean doney = false;
/*  92:135 */       while (y < space.getHeight())
/*  93:    */       {
/*  94:136 */         this.sx = ((int)(space.getX() + x));
/*  95:137 */         this.sy = ((int)(space.getY() + y));
/*  96:139 */         if (map.blocked(this, this.sx, this.sy)) {
/*  97:140 */           return false;
/*  98:    */         }
/*  99:143 */         y += 0.1F;
/* 100:144 */         if ((y > space.getHeight()) && (!doney))
/* 101:    */         {
/* 102:145 */           y = space.getHeight();
/* 103:146 */           doney = true;
/* 104:    */         }
/* 105:    */       }
/* 106:151 */       x += 0.1F;
/* 107:152 */       if ((x > space.getWidth()) && (!donex))
/* 108:    */       {
/* 109:153 */         x = space.getWidth();
/* 110:154 */         donex = true;
/* 111:    */       }
/* 112:    */     }
/* 113:158 */     return true;
/* 114:    */   }
/* 115:    */   
/* 116:    */   private void subsection(TileBasedMap map, Space space, ArrayList spaces)
/* 117:    */   {
/* 118:170 */     if (!clear(map, space))
/* 119:    */     {
/* 120:171 */       float width2 = space.getWidth() / 2.0F;
/* 121:172 */       float height2 = space.getHeight() / 2.0F;
/* 122:174 */       if ((width2 < this.smallestSpace) && (height2 < this.smallestSpace)) {
/* 123:175 */         return;
/* 124:    */       }
/* 125:178 */       subsection(map, new Space(space.getX(), space.getY(), width2, height2), spaces);
/* 126:179 */       subsection(map, new Space(space.getX(), space.getY() + height2, width2, height2), spaces);
/* 127:180 */       subsection(map, new Space(space.getX() + width2, space.getY(), width2, height2), spaces);
/* 128:181 */       subsection(map, new Space(space.getX() + width2, space.getY() + height2, width2, height2), spaces);
/* 129:    */     }
/* 130:    */     else
/* 131:    */     {
/* 132:183 */       spaces.add(space);
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Mover getMover()
/* 137:    */   {
/* 138:193 */     return null;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public int getSearchDistance()
/* 142:    */   {
/* 143:202 */     return 0;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public int getSourceX()
/* 147:    */   {
/* 148:211 */     return this.sx;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public int getSourceY()
/* 152:    */   {
/* 153:220 */     return this.sy;
/* 154:    */   }
/* 155:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.pathfinding.navmesh.NavMeshBuilder
 * JD-Core Version:    0.7.0.1
 */