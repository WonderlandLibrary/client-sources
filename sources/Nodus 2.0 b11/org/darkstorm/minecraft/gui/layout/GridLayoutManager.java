/*   1:    */ package org.darkstorm.minecraft.gui.layout;
/*   2:    */ 
/*   3:    */ import java.awt.Dimension;
/*   4:    */ import java.awt.Rectangle;
/*   5:    */ 
/*   6:    */ public class GridLayoutManager
/*   7:    */   implements LayoutManager
/*   8:    */ {
/*   9:    */   private int columns;
/*  10:    */   private int rows;
/*  11:    */   
/*  12:    */   public GridLayoutManager(int columns, int rows)
/*  13:    */   {
/*  14:  9 */     this.columns = columns;
/*  15: 10 */     this.rows = rows;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public void reposition(Rectangle area, Rectangle[] componentAreas, Constraint[][] constraints)
/*  19:    */   {
/*  20: 16 */     if (componentAreas.length == 0) {
/*  21:    */       return;
/*  22:    */     }
/*  23:    */     int componentsPerColumn;
/*  24:    */     int componentsPerRow;
/*  25:    */     int componentsPerColumn;
/*  26: 19 */     if (this.columns == 0)
/*  27:    */     {
/*  28: 20 */       if (this.rows == 0)
/*  29:    */       {
/*  30: 21 */         double square = Math.sqrt(componentAreas.length);
/*  31: 22 */         int componentsPerColumn = (int)square;
/*  32: 23 */         int componentsPerRow = (int)square;
/*  33: 24 */         if (square - (int)square > 0.0D) {
/*  34: 25 */           componentsPerColumn++;
/*  35:    */         }
/*  36:    */       }
/*  37:    */       else
/*  38:    */       {
/*  39: 27 */         int componentsPerRow = componentAreas.length / this.rows;
/*  40: 28 */         if (componentAreas.length % this.rows > 0) {
/*  41: 29 */           componentsPerRow++;
/*  42:    */         }
/*  43: 30 */         componentsPerColumn = this.rows;
/*  44:    */       }
/*  45:    */     }
/*  46:    */     else
/*  47:    */     {
/*  48:    */       int componentsPerRow;
/*  49: 32 */       if (this.rows == 0)
/*  50:    */       {
/*  51: 33 */         int componentsPerColumn = componentAreas.length / this.columns;
/*  52: 34 */         if (componentAreas.length % this.columns > 0) {
/*  53: 35 */           componentsPerColumn++;
/*  54:    */         }
/*  55: 36 */         componentsPerRow = this.columns;
/*  56:    */       }
/*  57:    */       else
/*  58:    */       {
/*  59: 38 */         componentsPerRow = this.columns;
/*  60: 39 */         componentsPerColumn = this.rows;
/*  61:    */       }
/*  62:    */     }
/*  63: 41 */     double elementWidth = area.width / componentsPerRow;
/*  64: 42 */     double elementHeight = area.height / 
/*  65: 43 */       componentsPerColumn;
/*  66: 44 */     for (int row = 0; row < componentsPerColumn; row++) {
/*  67: 45 */       for (int element = 0; element < componentsPerRow; element++)
/*  68:    */       {
/*  69: 46 */         int index = row * componentsPerRow + element;
/*  70: 47 */         if (index >= componentAreas.length) {
/*  71:    */           break;
/*  72:    */         }
/*  73: 49 */         Rectangle componentArea = componentAreas[index];
/*  74: 50 */         Constraint[] componentConstraints = constraints[index];
/*  75: 51 */         HorizontalGridConstraint horizontalAlign = HorizontalGridConstraint.LEFT;
/*  76: 52 */         VerticalGridConstraint verticalAlign = VerticalGridConstraint.CENTER;
/*  77: 53 */         for (Constraint constraint : componentConstraints) {
/*  78: 54 */           if ((constraint instanceof HorizontalGridConstraint)) {
/*  79: 55 */             horizontalAlign = (HorizontalGridConstraint)constraint;
/*  80: 56 */           } else if ((constraint instanceof VerticalGridConstraint)) {
/*  81: 57 */             verticalAlign = (VerticalGridConstraint)constraint;
/*  82:    */           }
/*  83:    */         }
/*  84: 59 */         switch (horizontalAlign)
/*  85:    */         {
/*  86:    */         case RIGHT: 
/*  87: 61 */           componentArea.width = ((int)elementWidth);
/*  88:    */         case FILL: 
/*  89: 63 */           componentArea.x = ((int)(area.x + element * elementWidth));
/*  90: 64 */           break;
/*  91:    */         case LEFT: 
/*  92: 66 */           componentArea.x = 
/*  93: 67 */             ((int)(area.x + (element + 1) * elementWidth - componentArea.width));
/*  94: 68 */           break;
/*  95:    */         case CENTER: 
/*  96: 70 */           componentArea.x = 
/*  97: 71 */             ((int)(area.x + element * elementWidth + elementWidth / 2.0D - componentArea.width / 2));
/*  98:    */         }
/*  99: 74 */         switch (verticalAlign)
/* 100:    */         {
/* 101:    */         case TOP: 
/* 102: 76 */           componentArea.height = ((int)elementHeight);
/* 103:    */         case CENTER: 
/* 104: 78 */           componentArea.y = ((int)(area.y + row * elementHeight));
/* 105: 79 */           break;
/* 106:    */         case FILL: 
/* 107: 81 */           componentArea.y = 
/* 108: 82 */             ((int)(area.y + (row + 1) * elementHeight - componentArea.height));
/* 109: 83 */           break;
/* 110:    */         case BOTTOM: 
/* 111: 85 */           componentArea.y = 
/* 112: 86 */             ((int)(area.y + row * elementHeight + elementHeight / 2.0D - componentArea.height / 2));
/* 113:    */         }
/* 114:    */       }
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Dimension getOptimalPositionedSize(Rectangle[] componentAreas, Constraint[][] constraints)
/* 119:    */   {
/* 120: 96 */     if (componentAreas.length == 0) {
/* 121: 97 */       return new Dimension(0, 0);
/* 122:    */     }
/* 123:    */     int componentsPerColumn;
/* 124:    */     int componentsPerRow;
/* 125:    */     int componentsPerColumn;
/* 126: 99 */     if (this.columns == 0)
/* 127:    */     {
/* 128:100 */       if (this.rows == 0)
/* 129:    */       {
/* 130:101 */         double square = Math.sqrt(componentAreas.length);
/* 131:102 */         int componentsPerColumn = (int)square;
/* 132:103 */         int componentsPerRow = (int)square;
/* 133:104 */         if (square - (int)square > 0.0D) {
/* 134:105 */           componentsPerColumn++;
/* 135:    */         }
/* 136:    */       }
/* 137:    */       else
/* 138:    */       {
/* 139:107 */         int componentsPerRow = componentAreas.length / this.rows;
/* 140:108 */         if (componentAreas.length % this.rows > 0) {
/* 141:109 */           componentsPerRow++;
/* 142:    */         }
/* 143:110 */         componentsPerColumn = this.rows;
/* 144:    */       }
/* 145:    */     }
/* 146:    */     else
/* 147:    */     {
/* 148:    */       int componentsPerRow;
/* 149:112 */       if (this.rows == 0)
/* 150:    */       {
/* 151:113 */         int componentsPerColumn = componentAreas.length / this.columns;
/* 152:114 */         if (componentAreas.length % this.columns > 0) {
/* 153:115 */           componentsPerColumn++;
/* 154:    */         }
/* 155:116 */         componentsPerRow = this.columns;
/* 156:    */       }
/* 157:    */       else
/* 158:    */       {
/* 159:118 */         componentsPerRow = this.columns;
/* 160:119 */         componentsPerColumn = this.rows;
/* 161:    */       }
/* 162:    */     }
/* 163:121 */     int maxElementWidth = 0;int maxElementHeight = 0;
/* 164:122 */     for (Rectangle component : componentAreas)
/* 165:    */     {
/* 166:123 */       maxElementWidth = Math.max(maxElementWidth, component.width);
/* 167:124 */       maxElementHeight = Math.max(maxElementHeight, component.height);
/* 168:    */     }
/* 169:126 */     return new Dimension(maxElementWidth * componentsPerRow, 
/* 170:127 */       maxElementHeight * componentsPerColumn);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public int getColumns()
/* 174:    */   {
/* 175:131 */     return this.columns;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public int getRows()
/* 179:    */   {
/* 180:135 */     return this.rows;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setColumns(int columns)
/* 184:    */   {
/* 185:139 */     this.columns = columns;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setRows(int rows)
/* 189:    */   {
/* 190:143 */     this.rows = rows;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public static enum HorizontalGridConstraint
/* 194:    */     implements Constraint
/* 195:    */   {
/* 196:147 */     CENTER,  LEFT,  RIGHT,  FILL;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public static enum VerticalGridConstraint
/* 200:    */     implements Constraint
/* 201:    */   {
/* 202:154 */     CENTER,  TOP,  BOTTOM,  FILL;
/* 203:    */   }
/* 204:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.layout.GridLayoutManager
 * JD-Core Version:    0.7.0.1
 */