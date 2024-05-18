/*    */ package org.newdawn.slick;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import javax.xml.parsers.DocumentBuilder;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
/*    */ import org.newdawn.slick.util.ResourceLoader;
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.NodeList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XMLPackedSheet
/*    */ {
/*    */   private Image image;
/* 22 */   private HashMap sprites = new HashMap<Object, Object>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLPackedSheet(String imageRef, String xmlRef) throws SlickException {
/* 33 */     this.image = new Image(imageRef, false, 2);
/*    */     
/*    */     try {
/* 36 */       DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
/* 37 */       Document doc = builder.parse(ResourceLoader.getResourceAsStream(xmlRef));
/*    */       
/* 39 */       NodeList list = doc.getElementsByTagName("sprite");
/* 40 */       for (int i = 0; i < list.getLength(); i++) {
/* 41 */         Element element = (Element)list.item(i);
/*    */         
/* 43 */         String name = element.getAttribute("name");
/* 44 */         int x = Integer.parseInt(element.getAttribute("x"));
/* 45 */         int y = Integer.parseInt(element.getAttribute("y"));
/* 46 */         int width = Integer.parseInt(element.getAttribute("width"));
/* 47 */         int height = Integer.parseInt(element.getAttribute("height"));
/*    */         
/* 49 */         this.sprites.put(name, this.image.getSubImage(x, y, width, height));
/*    */       } 
/* 51 */     } catch (Exception e) {
/* 52 */       throw new SlickException("Failed to parse sprite sheet XML", e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Image getSprite(String name) {
/* 63 */     return (Image)this.sprites.get(name);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\XMLPackedSheet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */