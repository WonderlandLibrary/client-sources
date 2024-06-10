/*    */ package nightmare.event;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import nightmare.Nightmare;
/*    */ 
/*    */ public abstract class Event
/*    */ {
/*    */   private boolean cancelled;
/*    */   
/*    */   public enum State
/*    */   {
/* 12 */     PRE("PRE", 0), POST("POST", 1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Event call() {
/* 18 */     this.cancelled = false;
/* 19 */     call(this);
/* 20 */     return this;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 24 */     return this.cancelled;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCancelled(boolean cancelled) {
/* 29 */     this.cancelled = cancelled;
/*    */   }
/*    */   
/*    */   private static void call(Event event) {
/* 33 */     ArrayHelper<Data> dataList = Nightmare.instance.eventManager.get((Class)event.getClass());
/* 34 */     if (dataList != null)
/* 35 */       for (Data data : dataList) {
/*    */         try {
/* 37 */           data.target.invoke(data.source, new Object[] { event });
/* 38 */         } catch (IllegalAccessException e) {
/* 39 */           e.printStackTrace();
/* 40 */         } catch (InvocationTargetException e) {
/* 41 */           e.printStackTrace();
/*    */         } 
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\event\Event.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */