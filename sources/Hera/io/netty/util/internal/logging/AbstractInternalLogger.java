/*     */ package io.netty.util.internal.logging;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractInternalLogger
/*     */   implements InternalLogger, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6382972526573193470L;
/*     */   private final String name;
/*     */   
/*     */   protected AbstractInternalLogger(String name) {
/*  38 */     if (name == null) {
/*  39 */       throw new NullPointerException("name");
/*     */     }
/*  41 */     this.name = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/*  46 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled(InternalLogLevel level) {
/*  51 */     switch (level) {
/*     */       case TRACE:
/*  53 */         return isTraceEnabled();
/*     */       case DEBUG:
/*  55 */         return isDebugEnabled();
/*     */       case INFO:
/*  57 */         return isInfoEnabled();
/*     */       case WARN:
/*  59 */         return isWarnEnabled();
/*     */       case ERROR:
/*  61 */         return isErrorEnabled();
/*     */     } 
/*  63 */     throw new Error();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, String msg, Throwable cause) {
/*  69 */     switch (level) {
/*     */       case TRACE:
/*  71 */         trace(msg, cause);
/*     */         return;
/*     */       case DEBUG:
/*  74 */         debug(msg, cause);
/*     */         return;
/*     */       case INFO:
/*  77 */         info(msg, cause);
/*     */         return;
/*     */       case WARN:
/*  80 */         warn(msg, cause);
/*     */         return;
/*     */       case ERROR:
/*  83 */         error(msg, cause);
/*     */         return;
/*     */     } 
/*  86 */     throw new Error();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, String msg) {
/*  92 */     switch (level) {
/*     */       case TRACE:
/*  94 */         trace(msg);
/*     */         return;
/*     */       case DEBUG:
/*  97 */         debug(msg);
/*     */         return;
/*     */       case INFO:
/* 100 */         info(msg);
/*     */         return;
/*     */       case WARN:
/* 103 */         warn(msg);
/*     */         return;
/*     */       case ERROR:
/* 106 */         error(msg);
/*     */         return;
/*     */     } 
/* 109 */     throw new Error();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, String format, Object arg) {
/* 115 */     switch (level) {
/*     */       case TRACE:
/* 117 */         trace(format, arg);
/*     */         return;
/*     */       case DEBUG:
/* 120 */         debug(format, arg);
/*     */         return;
/*     */       case INFO:
/* 123 */         info(format, arg);
/*     */         return;
/*     */       case WARN:
/* 126 */         warn(format, arg);
/*     */         return;
/*     */       case ERROR:
/* 129 */         error(format, arg);
/*     */         return;
/*     */     } 
/* 132 */     throw new Error();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, String format, Object argA, Object argB) {
/* 138 */     switch (level) {
/*     */       case TRACE:
/* 140 */         trace(format, argA, argB);
/*     */         return;
/*     */       case DEBUG:
/* 143 */         debug(format, argA, argB);
/*     */         return;
/*     */       case INFO:
/* 146 */         info(format, argA, argB);
/*     */         return;
/*     */       case WARN:
/* 149 */         warn(format, argA, argB);
/*     */         return;
/*     */       case ERROR:
/* 152 */         error(format, argA, argB);
/*     */         return;
/*     */     } 
/* 155 */     throw new Error();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void log(InternalLogLevel level, String format, Object... arguments) {
/* 161 */     switch (level) {
/*     */       case TRACE:
/* 163 */         trace(format, arguments);
/*     */         return;
/*     */       case DEBUG:
/* 166 */         debug(format, arguments);
/*     */         return;
/*     */       case INFO:
/* 169 */         info(format, arguments);
/*     */         return;
/*     */       case WARN:
/* 172 */         warn(format, arguments);
/*     */         return;
/*     */       case ERROR:
/* 175 */         error(format, arguments);
/*     */         return;
/*     */     } 
/* 178 */     throw new Error();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object readResolve() throws ObjectStreamException {
/* 183 */     return InternalLoggerFactory.getInstance(name());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 188 */     return StringUtil.simpleClassName(this) + '(' + name() + ')';
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\internal\logging\AbstractInternalLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */