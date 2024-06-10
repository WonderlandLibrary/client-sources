/*  1:   */ package org.newdawn.slick.svg;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.SlickException;
/*  4:   */ import org.w3c.dom.Element;
/*  5:   */ 
/*  6:   */ public class ParsingException
/*  7:   */   extends SlickException
/*  8:   */ {
/*  9:   */   public ParsingException(String nodeID, String message, Throwable cause)
/* 10:   */   {
/* 11:21 */     super("(" + nodeID + ") " + message, cause);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public ParsingException(Element element, String message, Throwable cause)
/* 15:   */   {
/* 16:32 */     super("(" + element.getAttribute("id") + ") " + message, cause);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public ParsingException(String nodeID, String message)
/* 20:   */   {
/* 21:42 */     super("(" + nodeID + ") " + message);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public ParsingException(Element element, String message)
/* 25:   */   {
/* 26:52 */     super("(" + element.getAttribute("id") + ") " + message);
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.svg.ParsingException
 * JD-Core Version:    0.7.0.1
 */