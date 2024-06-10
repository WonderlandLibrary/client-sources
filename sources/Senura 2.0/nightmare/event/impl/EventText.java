/*    */ package nightmare.event.impl;
/*    */ 
/*    */ import nightmare.event.Event;
/*    */ 
/*    */ public class EventText
/*    */   extends Event {
/*    */   private String text;
/*    */   private String outputText;
/*    */   
/*    */   public EventText(String text) {
/* 11 */     this.text = text;
/* 12 */     this.outputText = text;
/*    */   }
/*    */   
/*    */   public String getText() {
/* 16 */     return this.text;
/*    */   }
/*    */   
/*    */   public String getOutputText() {
/* 20 */     return this.outputText;
/*    */   }
/*    */   
/*    */   public String replace(String src, String target) {
/* 24 */     this.outputText = this.text.replace(src, target);
/* 25 */     return this.outputText;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\event\impl\EventText.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */