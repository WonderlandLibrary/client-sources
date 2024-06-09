/*     */ package org.newdawn.slick.svg;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.newdawn.slick.geom.MorphShape;
/*     */ import org.newdawn.slick.geom.Shape;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SVGMorph
/*     */   extends Diagram
/*     */ {
/*  14 */   private ArrayList figures = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SVGMorph(Diagram diagram) {
/*  22 */     super(diagram.getWidth(), diagram.getHeight());
/*     */     
/*  24 */     for (int i = 0; i < diagram.getFigureCount(); i++) {
/*  25 */       Figure figure = diagram.getFigure(i);
/*  26 */       Figure copy = new Figure(figure.getType(), (Shape)new MorphShape(figure.getShape()), figure.getData(), figure.getTransform());
/*     */       
/*  28 */       this.figures.add(copy);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStep(Diagram diagram) {
/*  38 */     if (diagram.getFigureCount() != this.figures.size()) {
/*  39 */       throw new RuntimeException("Mismatched diagrams, missing ids");
/*     */     }
/*  41 */     for (int i = 0; i < diagram.getFigureCount(); i++) {
/*  42 */       Figure figure = diagram.getFigure(i);
/*  43 */       String id = figure.getData().getMetaData();
/*     */       
/*  45 */       for (int j = 0; j < this.figures.size(); j++) {
/*  46 */         Figure existing = this.figures.get(j);
/*  47 */         if (existing.getData().getMetaData().equals(id)) {
/*  48 */           MorphShape morph = (MorphShape)existing.getShape();
/*  49 */           morph.addShape(figure.getShape());
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExternalDiagram(Diagram diagram) {
/*  64 */     for (int i = 0; i < this.figures.size(); i++) {
/*  65 */       Figure figure = this.figures.get(i);
/*     */       
/*  67 */       for (int j = 0; j < diagram.getFigureCount(); j++) {
/*  68 */         Figure newBase = diagram.getFigure(j);
/*  69 */         if (newBase.getData().getMetaData().equals(figure.getData().getMetaData())) {
/*  70 */           MorphShape shape = (MorphShape)figure.getShape();
/*  71 */           shape.setExternalFrame(newBase.getShape());
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMorphTime(float delta) {
/*  84 */     for (int i = 0; i < this.figures.size(); i++) {
/*  85 */       Figure figure = this.figures.get(i);
/*  86 */       MorphShape shape = (MorphShape)figure.getShape();
/*  87 */       shape.updateMorphTime(delta);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMorphTime(float time) {
/*  98 */     for (int i = 0; i < this.figures.size(); i++) {
/*  99 */       Figure figure = this.figures.get(i);
/* 100 */       MorphShape shape = (MorphShape)figure.getShape();
/* 101 */       shape.setMorphTime(time);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFigureCount() {
/* 109 */     return this.figures.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Figure getFigure(int index) {
/* 116 */     return this.figures.get(index);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\svg\SVGMorph.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */