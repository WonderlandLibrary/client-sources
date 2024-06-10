/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import org.newdawn.slick.BasicGame;
/*   7:    */ import org.newdawn.slick.Color;
/*   8:    */ import org.newdawn.slick.GameContainer;
/*   9:    */ import org.newdawn.slick.Graphics;
/*  10:    */ import org.newdawn.slick.Input;
/*  11:    */ import org.newdawn.slick.SlickException;
/*  12:    */ import org.newdawn.slick.util.Bootstrap;
/*  13:    */ import org.newdawn.slick.util.ResourceLoader;
/*  14:    */ import org.newdawn.slick.util.pathfinding.Mover;
/*  15:    */ import org.newdawn.slick.util.pathfinding.PathFindingContext;
/*  16:    */ import org.newdawn.slick.util.pathfinding.TileBasedMap;
/*  17:    */ import org.newdawn.slick.util.pathfinding.navmesh.Link;
/*  18:    */ import org.newdawn.slick.util.pathfinding.navmesh.NavMesh;
/*  19:    */ import org.newdawn.slick.util.pathfinding.navmesh.NavMeshBuilder;
/*  20:    */ import org.newdawn.slick.util.pathfinding.navmesh.NavPath;
/*  21:    */ import org.newdawn.slick.util.pathfinding.navmesh.Space;
/*  22:    */ 
/*  23:    */ public class NavMeshTest
/*  24:    */   extends BasicGame
/*  25:    */   implements PathFindingContext
/*  26:    */ {
/*  27:    */   private NavMesh navMesh;
/*  28:    */   private NavMeshBuilder builder;
/*  29: 33 */   private boolean showSpaces = true;
/*  30: 35 */   private boolean showLinks = true;
/*  31:    */   private NavPath path;
/*  32:    */   private float sx;
/*  33:    */   private float sy;
/*  34:    */   private float ex;
/*  35:    */   private float ey;
/*  36:    */   private DataMap dataMap;
/*  37:    */   
/*  38:    */   public NavMeshTest()
/*  39:    */   {
/*  40: 54 */     super("Nav-mesh Test");
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void init(GameContainer container)
/*  44:    */     throws SlickException
/*  45:    */   {
/*  46: 63 */     container.setShowFPS(false);
/*  47:    */     try
/*  48:    */     {
/*  49: 66 */       this.dataMap = new DataMap("testdata/map.dat");
/*  50:    */     }
/*  51:    */     catch (IOException e)
/*  52:    */     {
/*  53: 68 */       throw new SlickException("Failed to load map data", e);
/*  54:    */     }
/*  55: 70 */     this.builder = new NavMeshBuilder();
/*  56: 71 */     this.navMesh = this.builder.build(this.dataMap);
/*  57:    */     
/*  58: 73 */     System.out.println("Navmesh shapes: " + this.navMesh.getSpaceCount());
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void update(GameContainer container, int delta)
/*  62:    */     throws SlickException
/*  63:    */   {
/*  64: 81 */     if (container.getInput().isKeyPressed(2)) {
/*  65: 82 */       this.showLinks = (!this.showLinks);
/*  66:    */     }
/*  67: 84 */     if (container.getInput().isKeyPressed(3)) {
/*  68: 85 */       this.showSpaces = (!this.showSpaces);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void render(GameContainer container, Graphics g)
/*  73:    */     throws SlickException
/*  74:    */   {
/*  75: 97 */     g.translate(50.0F, 50.0F);
/*  76: 98 */     for (int x = 0; x < 50; x++) {
/*  77: 99 */       for (int y = 0; y < 50; y++) {
/*  78:100 */         if (this.dataMap.blocked(this, x, y))
/*  79:    */         {
/*  80:101 */           g.setColor(Color.gray);
/*  81:102 */           g.fillRect(x * 10 + 1, y * 10 + 1, 8.0F, 8.0F);
/*  82:    */         }
/*  83:    */       }
/*  84:    */     }
/*  85:107 */     if (this.showSpaces) {
/*  86:108 */       for (int i = 0; i < this.navMesh.getSpaceCount(); i++)
/*  87:    */       {
/*  88:109 */         Space space = this.navMesh.getSpace(i);
/*  89:110 */         if (this.builder.clear(this.dataMap, space))
/*  90:    */         {
/*  91:111 */           g.setColor(new Color(1.0F, 1.0F, 0.0F, 0.5F));
/*  92:112 */           g.fillRect(space.getX() * 10.0F, space.getY() * 10.0F, space.getWidth() * 10.0F, space.getHeight() * 10.0F);
/*  93:    */         }
/*  94:114 */         g.setColor(Color.yellow);
/*  95:115 */         g.drawRect(space.getX() * 10.0F, space.getY() * 10.0F, space.getWidth() * 10.0F, space.getHeight() * 10.0F);
/*  96:117 */         if (this.showLinks)
/*  97:    */         {
/*  98:118 */           int links = space.getLinkCount();
/*  99:119 */           for (int j = 0; j < links; j++)
/* 100:    */           {
/* 101:120 */             Link link = space.getLink(j);
/* 102:121 */             g.setColor(Color.red);
/* 103:122 */             g.fillRect(link.getX() * 10.0F - 2.0F, link.getY() * 10.0F - 2.0F, 5.0F, 5.0F);
/* 104:    */           }
/* 105:    */         }
/* 106:    */       }
/* 107:    */     }
/* 108:128 */     if (this.path != null)
/* 109:    */     {
/* 110:129 */       g.setColor(Color.white);
/* 111:130 */       for (int i = 0; i < this.path.length() - 1; i++) {
/* 112:131 */         g.drawLine(this.path.getX(i) * 10.0F, this.path.getY(i) * 10.0F, this.path.getX(i + 1) * 10.0F, this.path.getY(i + 1) * 10.0F);
/* 113:    */       }
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Mover getMover()
/* 118:    */   {
/* 119:141 */     return null;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public int getSearchDistance()
/* 123:    */   {
/* 124:149 */     return 0;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public int getSourceX()
/* 128:    */   {
/* 129:157 */     return 0;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public int getSourceY()
/* 133:    */   {
/* 134:165 */     return 0;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void mousePressed(int button, int x, int y)
/* 138:    */   {
/* 139:173 */     float mx = (x - 50) / 10.0F;
/* 140:174 */     float my = (y - 50) / 10.0F;
/* 141:176 */     if (button == 0)
/* 142:    */     {
/* 143:177 */       this.sx = mx;
/* 144:178 */       this.sy = my;
/* 145:    */     }
/* 146:    */     else
/* 147:    */     {
/* 148:180 */       this.ex = mx;
/* 149:181 */       this.ey = my;
/* 150:    */     }
/* 151:184 */     this.path = this.navMesh.findPath(this.sx, this.sy, this.ex, this.ey, true);
/* 152:    */   }
/* 153:    */   
/* 154:    */   private class DataMap
/* 155:    */     implements TileBasedMap
/* 156:    */   {
/* 157:194 */     private byte[] map = new byte[2500];
/* 158:    */     
/* 159:    */     public DataMap(String ref)
/* 160:    */       throws IOException
/* 161:    */     {
/* 162:203 */       ResourceLoader.getResourceAsStream(ref).read(this.map);
/* 163:    */     }
/* 164:    */     
/* 165:    */     public boolean blocked(PathFindingContext context, int tx, int ty)
/* 166:    */     {
/* 167:211 */       if ((tx < 0) || (ty < 0) || (tx >= 50) || (ty >= 50)) {
/* 168:212 */         return false;
/* 169:    */       }
/* 170:215 */       return this.map[(tx + ty * 50)] != 0;
/* 171:    */     }
/* 172:    */     
/* 173:    */     public float getCost(PathFindingContext context, int tx, int ty)
/* 174:    */     {
/* 175:223 */       return 1.0F;
/* 176:    */     }
/* 177:    */     
/* 178:    */     public int getHeightInTiles()
/* 179:    */     {
/* 180:231 */       return 50;
/* 181:    */     }
/* 182:    */     
/* 183:    */     public int getWidthInTiles()
/* 184:    */     {
/* 185:239 */       return 50;
/* 186:    */     }
/* 187:    */     
/* 188:    */     public void pathFinderVisited(int x, int y) {}
/* 189:    */   }
/* 190:    */   
/* 191:    */   public static void main(String[] argv)
/* 192:    */   {
/* 193:256 */     Bootstrap.runAsApplication(new NavMeshTest(), 600, 600, false);
/* 194:    */   }
/* 195:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.NavMeshTest
 * JD-Core Version:    0.7.0.1
 */