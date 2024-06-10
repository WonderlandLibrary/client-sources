/*  1:   */ package org.newdawn.slick.util.xml;
/*  2:   */ 
/*  3:   */ import java.io.InputStream;
/*  4:   */ import javax.xml.parsers.DocumentBuilder;
/*  5:   */ import javax.xml.parsers.DocumentBuilderFactory;
/*  6:   */ import org.newdawn.slick.SlickException;
/*  7:   */ import org.newdawn.slick.util.ResourceLoader;
/*  8:   */ import org.w3c.dom.Document;
/*  9:   */ 
/* 10:   */ public class XMLParser
/* 11:   */ {
/* 12:   */   private static DocumentBuilderFactory factory;
/* 13:   */   
/* 14:   */   public XMLElement parse(String ref)
/* 15:   */     throws SlickException
/* 16:   */   {
/* 17:38 */     return parse(ref, ResourceLoader.getResourceAsStream(ref));
/* 18:   */   }
/* 19:   */   
/* 20:   */   public XMLElement parse(String name, InputStream in)
/* 21:   */     throws SlickXMLException
/* 22:   */   {
/* 23:   */     try
/* 24:   */     {
/* 25:52 */       if (factory == null) {
/* 26:53 */         factory = DocumentBuilderFactory.newInstance();
/* 27:   */       }
/* 28:55 */       DocumentBuilder builder = factory.newDocumentBuilder();
/* 29:56 */       Document doc = builder.parse(in);
/* 30:   */       
/* 31:58 */       return new XMLElement(doc.getDocumentElement());
/* 32:   */     }
/* 33:   */     catch (Exception e)
/* 34:   */     {
/* 35:60 */       throw new SlickXMLException("Failed to parse document: " + name, e);
/* 36:   */     }
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.xml.XMLParser
 * JD-Core Version:    0.7.0.1
 */