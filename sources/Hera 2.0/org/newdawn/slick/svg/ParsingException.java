/*    */ package org.newdawn.slick.svg;
/*    */ 
/*    */ import org.newdawn.slick.SlickException;
/*    */ import org.w3c.dom.Element;
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
/*    */ public class ParsingException
/*    */   extends SlickException
/*    */ {
/*    */   public ParsingException(String nodeID, String message, Throwable cause) {
/* 21 */     super("(" + nodeID + ") " + message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ParsingException(Element element, String message, Throwable cause) {
/* 32 */     super("(" + element.getAttribute("id") + ") " + message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ParsingException(String nodeID, String message) {
/* 42 */     super("(" + nodeID + ") " + message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ParsingException(Element element, String message) {
/* 52 */     super("(" + element.getAttribute("id") + ") " + message);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\svg\ParsingException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */