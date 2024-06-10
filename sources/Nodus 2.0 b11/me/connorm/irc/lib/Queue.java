/*   1:    */ package me.connorm.irc.lib;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ 
/*   5:    */ public class Queue
/*   6:    */ {
/*   7:    */   public void add(Object o)
/*   8:    */   {
/*   9: 54 */     synchronized (this._queue)
/*  10:    */     {
/*  11: 55 */       this._queue.addElement(o);
/*  12: 56 */       this._queue.notify();
/*  13:    */     }
/*  14:    */   }
/*  15:    */   
/*  16:    */   public void addFront(Object o)
/*  17:    */   {
/*  18: 67 */     synchronized (this._queue)
/*  19:    */     {
/*  20: 68 */       this._queue.insertElementAt(o, 0);
/*  21: 69 */       this._queue.notify();
/*  22:    */     }
/*  23:    */   }
/*  24:    */   
/*  25:    */   public Object next()
/*  26:    */   {
/*  27: 84 */     Object o = null;
/*  28: 87 */     synchronized (this._queue)
/*  29:    */     {
/*  30: 88 */       if (this._queue.size() == 0) {
/*  31:    */         try
/*  32:    */         {
/*  33: 90 */           this._queue.wait();
/*  34:    */         }
/*  35:    */         catch (InterruptedException e)
/*  36:    */         {
/*  37: 93 */           return null;
/*  38:    */         }
/*  39:    */       }
/*  40:    */       try
/*  41:    */       {
/*  42: 99 */         o = this._queue.firstElement();
/*  43:100 */         this._queue.removeElementAt(0);
/*  44:    */       }
/*  45:    */       catch (ArrayIndexOutOfBoundsException e)
/*  46:    */       {
/*  47:103 */         throw new InternalError("Race hazard in Queue object.");
/*  48:    */       }
/*  49:    */     }
/*  50:107 */     return o;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean hasNext()
/*  54:    */   {
/*  55:120 */     return size() != 0;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void clear()
/*  59:    */   {
/*  60:128 */     synchronized (this._queue)
/*  61:    */     {
/*  62:129 */       this._queue.removeAllElements();
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int size()
/*  67:    */   {
/*  68:140 */     return this._queue.size();
/*  69:    */   }
/*  70:    */   
/*  71:144 */   private Vector _queue = new Vector();
/*  72:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.irc.lib.Queue
 * JD-Core Version:    0.7.0.1
 */