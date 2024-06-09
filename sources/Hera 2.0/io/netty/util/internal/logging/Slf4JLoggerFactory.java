/*    */ package io.netty.util.internal.logging;
/*    */ 
/*    */ import java.io.OutputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import org.slf4j.LoggerFactory;
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
/*    */ public class Slf4JLoggerFactory
/*    */   extends InternalLoggerFactory
/*    */ {
/*    */   public Slf4JLoggerFactory() {}
/*    */   
/*    */   Slf4JLoggerFactory(boolean failIfNOP) {
/* 36 */     assert failIfNOP;
/*    */ 
/*    */ 
/*    */     
/* 40 */     final StringBuffer buf = new StringBuffer();
/* 41 */     PrintStream err = System.err;
/*    */     try {
/* 43 */       System.setErr(new PrintStream(new OutputStream()
/*    */             {
/*    */               public void write(int b) {
/* 46 */                 buf.append((char)b);
/*    */               }
/*    */             },  true, "US-ASCII"));
/* 49 */     } catch (UnsupportedEncodingException e) {
/* 50 */       throw new Error(e);
/*    */     } 
/*    */     
/*    */     try {
/* 54 */       if (LoggerFactory.getILoggerFactory() instanceof org.slf4j.helpers.NOPLoggerFactory) {
/* 55 */         throw new NoClassDefFoundError(buf.toString());
/*    */       }
/* 57 */       err.print(buf);
/* 58 */       err.flush();
/*    */     } finally {
/*    */       
/* 61 */       System.setErr(err);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public InternalLogger newInstance(String name) {
/* 67 */     return new Slf4JLogger(LoggerFactory.getLogger(name));
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\internal\logging\Slf4JLoggerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */