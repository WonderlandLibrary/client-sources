/*   1:    */ package org.newdawn.slick.svg;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import javax.xml.parsers.DocumentBuilder;
/*   8:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*   9:    */ import org.newdawn.slick.SlickException;
/*  10:    */ import org.newdawn.slick.geom.Transform;
/*  11:    */ import org.newdawn.slick.svg.inkscape.DefsProcessor;
/*  12:    */ import org.newdawn.slick.svg.inkscape.ElementProcessor;
/*  13:    */ import org.newdawn.slick.svg.inkscape.EllipseProcessor;
/*  14:    */ import org.newdawn.slick.svg.inkscape.GroupProcessor;
/*  15:    */ import org.newdawn.slick.svg.inkscape.LineProcessor;
/*  16:    */ import org.newdawn.slick.svg.inkscape.PathProcessor;
/*  17:    */ import org.newdawn.slick.svg.inkscape.PolygonProcessor;
/*  18:    */ import org.newdawn.slick.svg.inkscape.RectProcessor;
/*  19:    */ import org.newdawn.slick.svg.inkscape.UseProcessor;
/*  20:    */ import org.newdawn.slick.util.ResourceLoader;
/*  21:    */ import org.w3c.dom.Document;
/*  22:    */ import org.w3c.dom.Element;
/*  23:    */ import org.w3c.dom.NodeList;
/*  24:    */ import org.xml.sax.EntityResolver;
/*  25:    */ import org.xml.sax.InputSource;
/*  26:    */ import org.xml.sax.SAXException;
/*  27:    */ 
/*  28:    */ public class InkscapeLoader
/*  29:    */   implements Loader
/*  30:    */ {
/*  31: 40 */   public static int RADIAL_TRIANGULATION_LEVEL = 1;
/*  32: 43 */   private static ArrayList processors = new ArrayList();
/*  33:    */   private Diagram diagram;
/*  34:    */   
/*  35:    */   static
/*  36:    */   {
/*  37: 49 */     addElementProcessor(new RectProcessor());
/*  38: 50 */     addElementProcessor(new EllipseProcessor());
/*  39: 51 */     addElementProcessor(new PolygonProcessor());
/*  40: 52 */     addElementProcessor(new PathProcessor());
/*  41: 53 */     addElementProcessor(new LineProcessor());
/*  42: 54 */     addElementProcessor(new GroupProcessor());
/*  43: 55 */     addElementProcessor(new DefsProcessor());
/*  44: 56 */     addElementProcessor(new UseProcessor());
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static void addElementProcessor(ElementProcessor proc)
/*  48:    */   {
/*  49: 66 */     processors.add(proc);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static Diagram load(String ref, boolean offset)
/*  53:    */     throws SlickException
/*  54:    */   {
/*  55: 82 */     return load(ResourceLoader.getResourceAsStream(ref), offset);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static Diagram load(String ref)
/*  59:    */     throws SlickException
/*  60:    */   {
/*  61: 95 */     return load(ResourceLoader.getResourceAsStream(ref), false);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static Diagram load(InputStream in, boolean offset)
/*  65:    */     throws SlickException
/*  66:    */   {
/*  67:111 */     return new InkscapeLoader().loadDiagram(in, offset);
/*  68:    */   }
/*  69:    */   
/*  70:    */   private Diagram loadDiagram(InputStream in)
/*  71:    */     throws SlickException
/*  72:    */   {
/*  73:130 */     return loadDiagram(in, false);
/*  74:    */   }
/*  75:    */   
/*  76:    */   private Diagram loadDiagram(InputStream in, boolean offset)
/*  77:    */     throws SlickException
/*  78:    */   {
/*  79:    */     try
/*  80:    */     {
/*  81:147 */       DocumentBuilderFactory factory = 
/*  82:148 */         DocumentBuilderFactory.newInstance();
/*  83:149 */       factory.setValidating(false);
/*  84:150 */       factory.setNamespaceAware(true);
/*  85:    */       
/*  86:152 */       DocumentBuilder builder = factory.newDocumentBuilder();
/*  87:153 */       builder.setEntityResolver(new EntityResolver()
/*  88:    */       {
/*  89:    */         public InputSource resolveEntity(String publicId, String systemId)
/*  90:    */           throws SAXException, IOException
/*  91:    */         {
/*  92:156 */           return new InputSource(
/*  93:157 */             new ByteArrayInputStream(new byte[0]));
/*  94:    */         }
/*  95:160 */       });
/*  96:161 */       Document doc = builder.parse(in);
/*  97:162 */       Element root = doc.getDocumentElement();
/*  98:    */       
/*  99:164 */       String widthString = root.getAttribute("width");
/* 100:166 */       while (Character.isLetter(widthString.charAt(widthString.length() - 1))) {
/* 101:167 */         widthString = widthString.substring(0, widthString.length() - 1);
/* 102:    */       }
/* 103:170 */       String heightString = root.getAttribute("height");
/* 104:172 */       while (Character.isLetter(heightString.charAt(heightString.length() - 1))) {
/* 105:173 */         heightString = heightString.substring(0, heightString.length() - 1);
/* 106:    */       }
/* 107:176 */       float docWidth = Float.parseFloat(widthString);
/* 108:177 */       float docHeight = Float.parseFloat(heightString);
/* 109:    */       
/* 110:179 */       this.diagram = new Diagram(docWidth, docHeight);
/* 111:180 */       if (!offset) {
/* 112:181 */         docHeight = 0.0F;
/* 113:    */       }
/* 114:183 */       loadChildren(root, 
/* 115:184 */         Transform.createTranslateTransform(0.0F, -docHeight));
/* 116:    */       
/* 117:186 */       return this.diagram;
/* 118:    */     }
/* 119:    */     catch (Exception e)
/* 120:    */     {
/* 121:188 */       throw new SlickException("Failed to load inkscape document", e);
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void loadChildren(Element element, Transform t)
/* 126:    */     throws ParsingException
/* 127:    */   {
/* 128:198 */     NodeList list = element.getChildNodes();
/* 129:199 */     for (int i = 0; i < list.getLength(); i++) {
/* 130:200 */       if ((list.item(i) instanceof Element)) {
/* 131:201 */         loadElement((Element)list.item(i), t);
/* 132:    */       }
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   private void loadElement(Element element, Transform t)
/* 137:    */     throws ParsingException
/* 138:    */   {
/* 139:218 */     for (int i = 0; i < processors.size(); i++)
/* 140:    */     {
/* 141:219 */       ElementProcessor processor = (ElementProcessor)processors.get(i);
/* 142:221 */       if (processor.handles(element)) {
/* 143:222 */         processor.process(this, element, this.diagram, t);
/* 144:    */       }
/* 145:    */     }
/* 146:    */   }
/* 147:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.svg.InkscapeLoader
 * JD-Core Version:    0.7.0.1
 */