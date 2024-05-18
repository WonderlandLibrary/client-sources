/*    */ package org.newdawn.slick.tests.xml;
/*    */ 
/*    */ import org.newdawn.slick.util.xml.ObjectTreeParser;
/*    */ import org.newdawn.slick.util.xml.SlickXMLException;
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
/*    */ public class ObjectParserTest
/*    */ {
/*    */   public static void main(String[] argv) throws SlickXMLException {
/* 22 */     ObjectTreeParser parser = new ObjectTreeParser("org.newdawn.slick.tests.xml");
/* 23 */     parser.addElementMapping("Bag", ItemContainer.class);
/*    */     
/* 25 */     GameData parsedData = (GameData)parser.parse("testdata/objxmltest.xml");
/* 26 */     parsedData.dump("");
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\xml\ObjectParserTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */