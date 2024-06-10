/*   1:    */ package org.newdawn.slick.svg;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import org.newdawn.slick.geom.MorphShape;
/*   5:    */ 
/*   6:    */ public class SVGMorph
/*   7:    */   extends Diagram
/*   8:    */ {
/*   9: 14 */   private ArrayList figures = new ArrayList();
/*  10:    */   
/*  11:    */   public SVGMorph(Diagram diagram)
/*  12:    */   {
/*  13: 22 */     super(diagram.getWidth(), diagram.getHeight());
/*  14: 24 */     for (int i = 0; i < diagram.getFigureCount(); i++)
/*  15:    */     {
/*  16: 25 */       Figure figure = diagram.getFigure(i);
/*  17: 26 */       Figure copy = new Figure(figure.getType(), new MorphShape(figure.getShape()), figure.getData(), figure.getTransform());
/*  18:    */       
/*  19: 28 */       this.figures.add(copy);
/*  20:    */     }
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void addStep(Diagram diagram)
/*  24:    */   {
/*  25: 38 */     if (diagram.getFigureCount() != this.figures.size()) {
/*  26: 39 */       throw new RuntimeException("Mismatched diagrams, missing ids");
/*  27:    */     }
/*  28: 41 */     for (int i = 0; i < diagram.getFigureCount(); i++)
/*  29:    */     {
/*  30: 42 */       Figure figure = diagram.getFigure(i);
/*  31: 43 */       String id = figure.getData().getMetaData();
/*  32: 45 */       for (int j = 0; j < this.figures.size(); j++)
/*  33:    */       {
/*  34: 46 */         Figure existing = (Figure)this.figures.get(j);
/*  35: 47 */         if (existing.getData().getMetaData().equals(id))
/*  36:    */         {
/*  37: 48 */           MorphShape morph = (MorphShape)existing.getShape();
/*  38: 49 */           morph.addShape(figure.getShape());
/*  39: 50 */           break;
/*  40:    */         }
/*  41:    */       }
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setExternalDiagram(Diagram diagram)
/*  46:    */   {
/*  47: 64 */     for (int i = 0; i < this.figures.size(); i++)
/*  48:    */     {
/*  49: 65 */       Figure figure = (Figure)this.figures.get(i);
/*  50: 67 */       for (int j = 0; j < diagram.getFigureCount(); j++)
/*  51:    */       {
/*  52: 68 */         Figure newBase = diagram.getFigure(j);
/*  53: 69 */         if (newBase.getData().getMetaData().equals(figure.getData().getMetaData()))
/*  54:    */         {
/*  55: 70 */           MorphShape shape = (MorphShape)figure.getShape();
/*  56: 71 */           shape.setExternalFrame(newBase.getShape());
/*  57: 72 */           break;
/*  58:    */         }
/*  59:    */       }
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void updateMorphTime(float delta)
/*  64:    */   {
/*  65: 84 */     for (int i = 0; i < this.figures.size(); i++)
/*  66:    */     {
/*  67: 85 */       Figure figure = (Figure)this.figures.get(i);
/*  68: 86 */       MorphShape shape = (MorphShape)figure.getShape();
/*  69: 87 */       shape.updateMorphTime(delta);
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setMorphTime(float time)
/*  74:    */   {
/*  75: 98 */     for (int i = 0; i < this.figures.size(); i++)
/*  76:    */     {
/*  77: 99 */       Figure figure = (Figure)this.figures.get(i);
/*  78:100 */       MorphShape shape = (MorphShape)figure.getShape();
/*  79:101 */       shape.setMorphTime(time);
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getFigureCount()
/*  84:    */   {
/*  85:109 */     return this.figures.size();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Figure getFigure(int index)
/*  89:    */   {
/*  90:116 */     return (Figure)this.figures.get(index);
/*  91:    */   }
/*  92:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.svg.SVGMorph
 * JD-Core Version:    0.7.0.1
 */