/*    */ package org.newdawn.slick.util.xml;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import javax.xml.parsers.DocumentBuilder;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
/*    */ import org.newdawn.slick.SlickException;
/*    */ import org.newdawn.slick.util.ResourceLoader;
/*    */ import org.w3c.dom.Document;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XMLParser
/*    */ {
/*    */   private static DocumentBuilderFactory factory;
/*    */   
/*    */   public XMLElement parse(String ref) throws SlickException {
/* 38 */     return parse(ref, ResourceLoader.getResourceAsStream(ref));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLElement parse(String name, InputStream in) throws SlickXMLException {
/*    */     try {
/* 52 */       if (factory == null) {
/* 53 */         factory = DocumentBuilderFactory.newInstance();
/*    */       }
/* 55 */       DocumentBuilder builder = factory.newDocumentBuilder();
/* 56 */       Document doc = builder.parse(in);
/*    */       
/* 58 */       return new XMLElement(doc.getDocumentElement());
/* 59 */     } catch (Exception e) {
/* 60 */       throw new SlickXMLException("Failed to parse document: " + name, e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\xml\XMLParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */