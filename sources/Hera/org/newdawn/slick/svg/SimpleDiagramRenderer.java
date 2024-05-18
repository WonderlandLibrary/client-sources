/*     */ package org.newdawn.slick.svg;
/*     */ 
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.geom.Shape;
/*     */ import org.newdawn.slick.geom.ShapeRenderer;
/*     */ import org.newdawn.slick.geom.TexCoordGenerator;
/*     */ import org.newdawn.slick.opengl.TextureImpl;
/*     */ import org.newdawn.slick.opengl.renderer.Renderer;
/*     */ import org.newdawn.slick.opengl.renderer.SGL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleDiagramRenderer
/*     */ {
/*  20 */   protected static SGL GL = Renderer.get();
/*     */ 
/*     */   
/*     */   public Diagram diagram;
/*     */   
/*  25 */   public int list = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleDiagramRenderer(Diagram diagram) {
/*  33 */     this.diagram = diagram;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Graphics g) {
/*  43 */     if (this.list == -1) {
/*  44 */       this.list = GL.glGenLists(1);
/*  45 */       GL.glNewList(this.list, 4864);
/*  46 */       render(g, this.diagram);
/*  47 */       GL.glEndList();
/*     */     } 
/*     */     
/*  50 */     GL.glCallList(this.list);
/*     */     
/*  52 */     TextureImpl.bindNone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void render(Graphics g, Diagram diagram) {
/*  62 */     for (int i = 0; i < diagram.getFigureCount(); i++) {
/*  63 */       Figure figure = diagram.getFigure(i);
/*     */       
/*  65 */       if (figure.getData().isFilled()) {
/*  66 */         if (figure.getData().isColor("fill")) {
/*  67 */           g.setColor(figure.getData().getAsColor("fill"));
/*  68 */           g.fill(diagram.getFigure(i).getShape());
/*  69 */           g.setAntiAlias(true);
/*  70 */           g.draw(diagram.getFigure(i).getShape());
/*  71 */           g.setAntiAlias(false);
/*     */         } 
/*     */         
/*  74 */         String fill = figure.getData().getAsReference("fill");
/*  75 */         if (diagram.getPatternDef(fill) != null) {
/*  76 */           System.out.println("PATTERN");
/*     */         }
/*  78 */         if (diagram.getGradient(fill) != null) {
/*  79 */           Gradient gradient = diagram.getGradient(fill);
/*  80 */           Shape shape = diagram.getFigure(i).getShape();
/*  81 */           TexCoordGenerator fg = null;
/*  82 */           if (gradient.isRadial()) {
/*  83 */             fg = new RadialGradientFill(shape, diagram.getFigure(i).getTransform(), gradient);
/*     */           } else {
/*  85 */             fg = new LinearGradientFill(shape, diagram.getFigure(i).getTransform(), gradient);
/*     */           } 
/*     */           
/*  88 */           Color.white.bind();
/*  89 */           ShapeRenderer.texture(shape, gradient.getImage(), fg);
/*     */         } 
/*     */       } 
/*     */       
/*  93 */       if (figure.getData().isStroked() && 
/*  94 */         figure.getData().isColor("stroke")) {
/*  95 */         g.setColor(figure.getData().getAsColor("stroke"));
/*  96 */         g.setLineWidth(figure.getData().getAsFloat("stroke-width"));
/*  97 */         g.setAntiAlias(true);
/*  98 */         g.draw(diagram.getFigure(i).getShape());
/*  99 */         g.setAntiAlias(false);
/* 100 */         g.resetLineWidth();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\svg\SimpleDiagramRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */