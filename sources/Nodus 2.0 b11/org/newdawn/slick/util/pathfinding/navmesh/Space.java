/*   1:    */ package org.newdawn.slick.util.pathfinding.navmesh;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ 
/*   6:    */ public class Space
/*   7:    */ {
/*   8:    */   private float x;
/*   9:    */   private float y;
/*  10:    */   private float width;
/*  11:    */   private float height;
/*  12: 22 */   private HashMap links = new HashMap();
/*  13: 24 */   private ArrayList linksList = new ArrayList();
/*  14:    */   private float cost;
/*  15:    */   
/*  16:    */   public Space(float x, float y, float width, float height)
/*  17:    */   {
/*  18: 37 */     this.x = x;
/*  19: 38 */     this.y = y;
/*  20: 39 */     this.width = width;
/*  21: 40 */     this.height = height;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public float getWidth()
/*  25:    */   {
/*  26: 49 */     return this.width;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public float getHeight()
/*  30:    */   {
/*  31: 58 */     return this.height;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public float getX()
/*  35:    */   {
/*  36: 67 */     return this.x;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public float getY()
/*  40:    */   {
/*  41: 76 */     return this.y;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void link(Space other)
/*  45:    */   {
/*  46: 87 */     if ((inTolerance(this.x, other.x + other.width)) || (inTolerance(this.x + this.width, other.x)))
/*  47:    */     {
/*  48: 88 */       float linkx = this.x;
/*  49: 89 */       if (this.x + this.width == other.x) {
/*  50: 90 */         linkx = this.x + this.width;
/*  51:    */       }
/*  52: 93 */       float top = Math.max(this.y, other.y);
/*  53: 94 */       float bottom = Math.min(this.y + this.height, other.y + other.height);
/*  54: 95 */       float linky = top + (bottom - top) / 2.0F;
/*  55:    */       
/*  56: 97 */       Link link = new Link(linkx, linky, other);
/*  57: 98 */       this.links.put(other, link);
/*  58: 99 */       this.linksList.add(link);
/*  59:    */     }
/*  60:102 */     if ((inTolerance(this.y, other.y + other.height)) || (inTolerance(this.y + this.height, other.y)))
/*  61:    */     {
/*  62:103 */       float linky = this.y;
/*  63:104 */       if (this.y + this.height == other.y) {
/*  64:105 */         linky = this.y + this.height;
/*  65:    */       }
/*  66:108 */       float left = Math.max(this.x, other.x);
/*  67:109 */       float right = Math.min(this.x + this.width, other.x + other.width);
/*  68:110 */       float linkx = left + (right - left) / 2.0F;
/*  69:    */       
/*  70:112 */       Link link = new Link(linkx, linky, other);
/*  71:113 */       this.links.put(other, link);
/*  72:114 */       this.linksList.add(link);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   private boolean inTolerance(float a, float b)
/*  77:    */   {
/*  78:127 */     return a == b;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean hasJoinedEdge(Space other)
/*  82:    */   {
/*  83:138 */     if ((inTolerance(this.x, other.x + other.width)) || (inTolerance(this.x + this.width, other.x)))
/*  84:    */     {
/*  85:139 */       if ((this.y >= other.y) && (this.y <= other.y + other.height)) {
/*  86:140 */         return true;
/*  87:    */       }
/*  88:142 */       if ((this.y + this.height >= other.y) && (this.y + this.height <= other.y + other.height)) {
/*  89:143 */         return true;
/*  90:    */       }
/*  91:145 */       if ((other.y >= this.y) && (other.y <= this.y + this.height)) {
/*  92:146 */         return true;
/*  93:    */       }
/*  94:148 */       if ((other.y + other.height >= this.y) && (other.y + other.height <= this.y + this.height)) {
/*  95:149 */         return true;
/*  96:    */       }
/*  97:    */     }
/*  98:153 */     if ((inTolerance(this.y, other.y + other.height)) || (inTolerance(this.y + this.height, other.y)))
/*  99:    */     {
/* 100:154 */       if ((this.x >= other.x) && (this.x <= other.x + other.width)) {
/* 101:155 */         return true;
/* 102:    */       }
/* 103:157 */       if ((this.x + this.width >= other.x) && (this.x + this.width <= other.x + other.width)) {
/* 104:158 */         return true;
/* 105:    */       }
/* 106:160 */       if ((other.x >= this.x) && (other.x <= this.x + this.width)) {
/* 107:161 */         return true;
/* 108:    */       }
/* 109:163 */       if ((other.x + other.width >= this.x) && (other.x + other.width <= this.x + this.width)) {
/* 110:164 */         return true;
/* 111:    */       }
/* 112:    */     }
/* 113:168 */     return false;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Space merge(Space other)
/* 117:    */   {
/* 118:178 */     float minx = Math.min(this.x, other.x);
/* 119:179 */     float miny = Math.min(this.y, other.y);
/* 120:    */     
/* 121:181 */     float newwidth = this.width + other.width;
/* 122:182 */     float newheight = this.height + other.height;
/* 123:183 */     if (this.x == other.x) {
/* 124:184 */       newwidth = this.width;
/* 125:    */     } else {
/* 126:186 */       newheight = this.height;
/* 127:    */     }
/* 128:188 */     return new Space(minx, miny, newwidth, newheight);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean canMerge(Space other)
/* 132:    */   {
/* 133:199 */     if (!hasJoinedEdge(other)) {
/* 134:200 */       return false;
/* 135:    */     }
/* 136:203 */     if ((this.x == other.x) && (this.width == other.width)) {
/* 137:204 */       return true;
/* 138:    */     }
/* 139:206 */     if ((this.y == other.y) && (this.height == other.height)) {
/* 140:207 */       return true;
/* 141:    */     }
/* 142:210 */     return false;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int getLinkCount()
/* 146:    */   {
/* 147:219 */     return this.linksList.size();
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Link getLink(int index)
/* 151:    */   {
/* 152:229 */     return (Link)this.linksList.get(index);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public boolean contains(float xp, float yp)
/* 156:    */   {
/* 157:240 */     return (xp >= this.x) && (xp < this.x + this.width) && (yp >= this.y) && (yp < this.y + this.height);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void fill(Space target, float sx, float sy, float cost)
/* 161:    */   {
/* 162:252 */     if (cost >= this.cost) {
/* 163:253 */       return;
/* 164:    */     }
/* 165:255 */     this.cost = cost;
/* 166:256 */     if (target == this) {
/* 167:257 */       return;
/* 168:    */     }
/* 169:260 */     for (int i = 0; i < getLinkCount(); i++)
/* 170:    */     {
/* 171:261 */       Link link = getLink(i);
/* 172:262 */       float extraCost = link.distance2(sx, sy);
/* 173:263 */       float nextCost = cost + extraCost;
/* 174:264 */       link.getTarget().fill(target, link.getX(), link.getY(), nextCost);
/* 175:    */     }
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void clearCost()
/* 179:    */   {
/* 180:272 */     this.cost = 3.4028235E+38F;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public float getCost()
/* 184:    */   {
/* 185:281 */     return this.cost;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public boolean pickLowestCost(Space target, NavPath path)
/* 189:    */   {
/* 190:292 */     if (target == this) {
/* 191:293 */       return true;
/* 192:    */     }
/* 193:295 */     if (this.links.size() == 0) {
/* 194:296 */       return false;
/* 195:    */     }
/* 196:299 */     Link bestLink = null;
/* 197:300 */     for (int i = 0; i < getLinkCount(); i++)
/* 198:    */     {
/* 199:301 */       Link link = getLink(i);
/* 200:302 */       if ((bestLink == null) || (link.getTarget().getCost() < bestLink.getTarget().getCost())) {
/* 201:303 */         bestLink = link;
/* 202:    */       }
/* 203:    */     }
/* 204:307 */     path.push(bestLink);
/* 205:308 */     return bestLink.getTarget().pickLowestCost(target, path);
/* 206:    */   }
/* 207:    */   
/* 208:    */   public String toString()
/* 209:    */   {
/* 210:317 */     return "[Space " + this.x + "," + this.y + " " + this.width + "," + this.height + "]";
/* 211:    */   }
/* 212:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.pathfinding.navmesh.Space
 * JD-Core Version:    0.7.0.1
 */