/*     */ package org.newdawn.slick.svg;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.geom.Transform;
/*     */ import org.newdawn.slick.svg.inkscape.DefsProcessor;
/*     */ import org.newdawn.slick.svg.inkscape.ElementProcessor;
/*     */ import org.newdawn.slick.svg.inkscape.EllipseProcessor;
/*     */ import org.newdawn.slick.svg.inkscape.GroupProcessor;
/*     */ import org.newdawn.slick.svg.inkscape.LineProcessor;
/*     */ import org.newdawn.slick.svg.inkscape.PathProcessor;
/*     */ import org.newdawn.slick.svg.inkscape.PolygonProcessor;
/*     */ import org.newdawn.slick.svg.inkscape.RectProcessor;
/*     */ import org.newdawn.slick.svg.inkscape.UseProcessor;
/*     */ import org.newdawn.slick.util.ResourceLoader;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InkscapeLoader
/*     */   implements Loader
/*     */ {
/*  40 */   public static int RADIAL_TRIANGULATION_LEVEL = 1;
/*     */ 
/*     */   
/*  43 */   private static ArrayList processors = new ArrayList();
/*     */   
/*     */   private Diagram diagram;
/*     */ 
/*     */   
/*     */   static {
/*  49 */     addElementProcessor((ElementProcessor)new RectProcessor());
/*  50 */     addElementProcessor((ElementProcessor)new EllipseProcessor());
/*  51 */     addElementProcessor((ElementProcessor)new PolygonProcessor());
/*  52 */     addElementProcessor((ElementProcessor)new PathProcessor());
/*  53 */     addElementProcessor((ElementProcessor)new LineProcessor());
/*  54 */     addElementProcessor((ElementProcessor)new GroupProcessor());
/*  55 */     addElementProcessor((ElementProcessor)new DefsProcessor());
/*  56 */     addElementProcessor((ElementProcessor)new UseProcessor());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addElementProcessor(ElementProcessor proc) {
/*  66 */     processors.add(proc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Diagram load(String ref, boolean offset) throws SlickException {
/*  82 */     return load(ResourceLoader.getResourceAsStream(ref), offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Diagram load(String ref) throws SlickException {
/*  95 */     return load(ResourceLoader.getResourceAsStream(ref), false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Diagram load(InputStream in, boolean offset) throws SlickException {
/* 111 */     return (new InkscapeLoader()).loadDiagram(in, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Diagram loadDiagram(InputStream in) throws SlickException {
/* 130 */     return loadDiagram(in, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Diagram loadDiagram(InputStream in, boolean offset) throws SlickException {
/*     */     try {
/* 147 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*     */       
/* 149 */       factory.setValidating(false);
/* 150 */       factory.setNamespaceAware(true);
/*     */       
/* 152 */       DocumentBuilder builder = factory.newDocumentBuilder();
/* 153 */       builder.setEntityResolver(new EntityResolver()
/*     */           {
/*     */             public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
/* 156 */               return new InputSource(new ByteArrayInputStream(new byte[0]));
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 161 */       Document doc = builder.parse(in);
/* 162 */       Element root = doc.getDocumentElement();
/*     */       
/* 164 */       String widthString = root.getAttribute("width");
/* 165 */       while (Character.isLetter(widthString.charAt(widthString.length() - 1)))
/*     */       {
/* 167 */         widthString = widthString.substring(0, widthString.length() - 1);
/*     */       }
/*     */       
/* 170 */       String heightString = root.getAttribute("height");
/* 171 */       while (Character.isLetter(heightString.charAt(heightString.length() - 1)))
/*     */       {
/* 173 */         heightString = heightString.substring(0, heightString.length() - 1);
/*     */       }
/*     */       
/* 176 */       float docWidth = Float.parseFloat(widthString);
/* 177 */       float docHeight = Float.parseFloat(heightString);
/*     */       
/* 179 */       this.diagram = new Diagram(docWidth, docHeight);
/* 180 */       if (!offset) {
/* 181 */         docHeight = 0.0F;
/*     */       }
/* 183 */       loadChildren(root, Transform.createTranslateTransform(0.0F, -docHeight));
/*     */ 
/*     */       
/* 186 */       return this.diagram;
/* 187 */     } catch (Exception e) {
/* 188 */       throw new SlickException("Failed to load inkscape document", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadChildren(Element element, Transform t) throws ParsingException {
/* 198 */     NodeList list = element.getChildNodes();
/* 199 */     for (int i = 0; i < list.getLength(); i++) {
/* 200 */       if (list.item(i) instanceof Element) {
/* 201 */         loadElement((Element)list.item(i), t);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadElement(Element element, Transform t) throws ParsingException {
/* 218 */     for (int i = 0; i < processors.size(); i++) {
/* 219 */       ElementProcessor processor = processors.get(i);
/*     */       
/* 221 */       if (processor.handles(element))
/* 222 */         processor.process(this, element, this.diagram, t); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\svg\InkscapeLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */