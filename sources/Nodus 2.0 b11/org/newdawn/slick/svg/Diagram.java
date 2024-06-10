/*   1:    */ package org.newdawn.slick.svg;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Set;
/*   6:    */ import org.newdawn.slick.geom.Shape;
/*   7:    */ 
/*   8:    */ public class Diagram
/*   9:    */ {
/*  10: 13 */   private ArrayList figures = new ArrayList();
/*  11: 15 */   private HashMap patterns = new HashMap();
/*  12: 17 */   private HashMap gradients = new HashMap();
/*  13: 19 */   private HashMap figureMap = new HashMap();
/*  14:    */   private float width;
/*  15:    */   private float height;
/*  16:    */   
/*  17:    */   public Diagram(float width, float height)
/*  18:    */   {
/*  19: 33 */     this.width = width;
/*  20: 34 */     this.height = height;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public float getWidth()
/*  24:    */   {
/*  25: 43 */     return this.width;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public float getHeight()
/*  29:    */   {
/*  30: 52 */     return this.height;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void addPatternDef(String name, String href)
/*  34:    */   {
/*  35: 62 */     this.patterns.put(name, href);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void addGradient(String name, Gradient gradient)
/*  39:    */   {
/*  40: 72 */     this.gradients.put(name, gradient);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getPatternDef(String name)
/*  44:    */   {
/*  45: 82 */     return (String)this.patterns.get(name);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Gradient getGradient(String name)
/*  49:    */   {
/*  50: 92 */     return (Gradient)this.gradients.get(name);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String[] getPatternDefNames()
/*  54:    */   {
/*  55:101 */     return (String[])this.patterns.keySet().toArray(new String[0]);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Figure getFigureByID(String id)
/*  59:    */   {
/*  60:111 */     return (Figure)this.figureMap.get(id);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void addFigure(Figure figure)
/*  64:    */   {
/*  65:120 */     this.figures.add(figure);
/*  66:121 */     this.figureMap.put(figure.getData().getAttribute("id"), figure);
/*  67:    */     
/*  68:123 */     String fillRef = figure.getData().getAsReference("fill");
/*  69:124 */     Gradient gradient = getGradient(fillRef);
/*  70:125 */     if ((gradient != null) && 
/*  71:126 */       (gradient.isRadial())) {
/*  72:127 */       for (int i = 0; i < InkscapeLoader.RADIAL_TRIANGULATION_LEVEL; i++) {
/*  73:128 */         figure.getShape().increaseTriangulation();
/*  74:    */       }
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getFigureCount()
/*  79:    */   {
/*  80:140 */     return this.figures.size();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Figure getFigure(int index)
/*  84:    */   {
/*  85:150 */     return (Figure)this.figures.get(index);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void removeFigure(Figure figure)
/*  89:    */   {
/*  90:159 */     this.figures.remove(figure);
/*  91:160 */     this.figureMap.remove(figure.getData().getAttribute("id"));
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.svg.Diagram
 * JD-Core Version:    0.7.0.1
 */