/*  1:   */ package org.newdawn.slick;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import javax.xml.parsers.DocumentBuilder;
/*  5:   */ import javax.xml.parsers.DocumentBuilderFactory;
/*  6:   */ import org.newdawn.slick.util.ResourceLoader;
/*  7:   */ import org.w3c.dom.Document;
/*  8:   */ import org.w3c.dom.Element;
/*  9:   */ import org.w3c.dom.NodeList;
/* 10:   */ 
/* 11:   */ public class XMLPackedSheet
/* 12:   */ {
/* 13:   */   private Image image;
/* 14:22 */   private HashMap sprites = new HashMap();
/* 15:   */   
/* 16:   */   public XMLPackedSheet(String imageRef, String xmlRef)
/* 17:   */     throws SlickException
/* 18:   */   {
/* 19:33 */     this.image = new Image(imageRef, false, 2);
/* 20:   */     try
/* 21:   */     {
/* 22:36 */       DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
/* 23:37 */       Document doc = builder.parse(ResourceLoader.getResourceAsStream(xmlRef));
/* 24:   */       
/* 25:39 */       NodeList list = doc.getElementsByTagName("sprite");
/* 26:40 */       for (int i = 0; i < list.getLength(); i++)
/* 27:   */       {
/* 28:41 */         Element element = (Element)list.item(i);
/* 29:   */         
/* 30:43 */         String name = element.getAttribute("name");
/* 31:44 */         int x = Integer.parseInt(element.getAttribute("x"));
/* 32:45 */         int y = Integer.parseInt(element.getAttribute("y"));
/* 33:46 */         int width = Integer.parseInt(element.getAttribute("width"));
/* 34:47 */         int height = Integer.parseInt(element.getAttribute("height"));
/* 35:   */         
/* 36:49 */         this.sprites.put(name, this.image.getSubImage(x, y, width, height));
/* 37:   */       }
/* 38:   */     }
/* 39:   */     catch (Exception e)
/* 40:   */     {
/* 41:52 */       throw new SlickException("Failed to parse sprite sheet XML", e);
/* 42:   */     }
/* 43:   */   }
/* 44:   */   
/* 45:   */   public Image getSprite(String name)
/* 46:   */   {
/* 47:63 */     return (Image)this.sprites.get(name);
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.XMLPackedSheet
 * JD-Core Version:    0.7.0.1
 */