/*   1:    */ package org.newdawn.slick.svg;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import org.newdawn.slick.Color;
/*   5:    */ import org.newdawn.slick.Graphics;
/*   6:    */ import org.newdawn.slick.geom.Shape;
/*   7:    */ import org.newdawn.slick.geom.ShapeRenderer;
/*   8:    */ import org.newdawn.slick.geom.TexCoordGenerator;
/*   9:    */ import org.newdawn.slick.opengl.TextureImpl;
/*  10:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*  11:    */ import org.newdawn.slick.opengl.renderer.SGL;
/*  12:    */ 
/*  13:    */ public class SimpleDiagramRenderer
/*  14:    */ {
/*  15: 20 */   protected static SGL GL = ;
/*  16:    */   public Diagram diagram;
/*  17: 25 */   public int list = -1;
/*  18:    */   
/*  19:    */   public SimpleDiagramRenderer(Diagram diagram)
/*  20:    */   {
/*  21: 33 */     this.diagram = diagram;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void render(Graphics g)
/*  25:    */   {
/*  26: 43 */     if (this.list == -1)
/*  27:    */     {
/*  28: 44 */       this.list = GL.glGenLists(1);
/*  29: 45 */       GL.glNewList(this.list, 4864);
/*  30: 46 */       render(g, this.diagram);
/*  31: 47 */       GL.glEndList();
/*  32:    */     }
/*  33: 50 */     GL.glCallList(this.list);
/*  34:    */     
/*  35: 52 */     TextureImpl.bindNone();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static void render(Graphics g, Diagram diagram)
/*  39:    */   {
/*  40: 62 */     for (int i = 0; i < diagram.getFigureCount(); i++)
/*  41:    */     {
/*  42: 63 */       Figure figure = diagram.getFigure(i);
/*  43: 65 */       if (figure.getData().isFilled())
/*  44:    */       {
/*  45: 66 */         if (figure.getData().isColor("fill"))
/*  46:    */         {
/*  47: 67 */           g.setColor(figure.getData().getAsColor("fill"));
/*  48: 68 */           g.fill(diagram.getFigure(i).getShape());
/*  49: 69 */           g.setAntiAlias(true);
/*  50: 70 */           g.draw(diagram.getFigure(i).getShape());
/*  51: 71 */           g.setAntiAlias(false);
/*  52:    */         }
/*  53: 74 */         String fill = figure.getData().getAsReference("fill");
/*  54: 75 */         if (diagram.getPatternDef(fill) != null) {
/*  55: 76 */           System.out.println("PATTERN");
/*  56:    */         }
/*  57: 78 */         if (diagram.getGradient(fill) != null)
/*  58:    */         {
/*  59: 79 */           Gradient gradient = diagram.getGradient(fill);
/*  60: 80 */           Shape shape = diagram.getFigure(i).getShape();
/*  61: 81 */           TexCoordGenerator fg = null;
/*  62: 82 */           if (gradient.isRadial()) {
/*  63: 83 */             fg = new RadialGradientFill(shape, diagram.getFigure(i).getTransform(), gradient);
/*  64:    */           } else {
/*  65: 85 */             fg = new LinearGradientFill(shape, diagram.getFigure(i).getTransform(), gradient);
/*  66:    */           }
/*  67: 88 */           Color.white.bind();
/*  68: 89 */           ShapeRenderer.texture(shape, gradient.getImage(), fg);
/*  69:    */         }
/*  70:    */       }
/*  71: 93 */       if ((figure.getData().isStroked()) && 
/*  72: 94 */         (figure.getData().isColor("stroke")))
/*  73:    */       {
/*  74: 95 */         g.setColor(figure.getData().getAsColor("stroke"));
/*  75: 96 */         g.setLineWidth(figure.getData().getAsFloat("stroke-width"));
/*  76: 97 */         g.setAntiAlias(true);
/*  77: 98 */         g.draw(diagram.getFigure(i).getShape());
/*  78: 99 */         g.setAntiAlias(false);
/*  79:100 */         g.resetLineWidth();
/*  80:    */       }
/*  81:    */     }
/*  82:    */   }
/*  83:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.svg.SimpleDiagramRenderer
 * JD-Core Version:    0.7.0.1
 */