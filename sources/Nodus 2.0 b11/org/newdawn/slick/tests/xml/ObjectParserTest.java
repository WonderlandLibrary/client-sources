/*  1:   */ package org.newdawn.slick.tests.xml;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.util.xml.ObjectTreeParser;
/*  4:   */ import org.newdawn.slick.util.xml.SlickXMLException;
/*  5:   */ 
/*  6:   */ public class ObjectParserTest
/*  7:   */ {
/*  8:   */   public static void main(String[] argv)
/*  9:   */     throws SlickXMLException
/* 10:   */   {
/* 11:22 */     ObjectTreeParser parser = new ObjectTreeParser("org.newdawn.slick.tests.xml");
/* 12:23 */     parser.addElementMapping("Bag", ItemContainer.class);
/* 13:   */     
/* 14:25 */     GameData parsedData = (GameData)parser.parse("testdata/objxmltest.xml");
/* 15:26 */     parsedData.dump("");
/* 16:   */   }
/* 17:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.xml.ObjectParserTest
 * JD-Core Version:    0.7.0.1
 */