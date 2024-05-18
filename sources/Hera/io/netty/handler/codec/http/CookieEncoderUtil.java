/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.util.internal.InternalThreadLocalMap;
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
/*    */ final class CookieEncoderUtil
/*    */ {
/*    */   static StringBuilder stringBuilder() {
/* 24 */     return InternalThreadLocalMap.get().stringBuilder();
/*    */   }
/*    */   
/*    */   static String stripTrailingSeparator(StringBuilder buf) {
/* 28 */     if (buf.length() > 0) {
/* 29 */       buf.setLength(buf.length() - 2);
/*    */     }
/* 31 */     return buf.toString();
/*    */   }
/*    */   
/*    */   static void add(StringBuilder sb, String name, String val) {
/* 35 */     if (val == null) {
/* 36 */       addQuoted(sb, name, "");
/*    */       
/*    */       return;
/*    */     } 
/* 40 */     for (int i = 0; i < val.length(); i++) {
/* 41 */       char c = val.charAt(i);
/* 42 */       switch (c) { case '\t': case ' ': case '"': case '(': case ')': case ',': case '/': case ':': case ';': case '<': case '=': case '>': case '?': case '@': case '[':
/*    */         case '\\':
/*    */         case ']':
/*    */         case '{':
/*    */         case '}':
/* 47 */           addQuoted(sb, name, val);
/*    */           return; }
/*    */ 
/*    */     
/*    */     } 
/* 52 */     addUnquoted(sb, name, val);
/*    */   }
/*    */   
/*    */   static void addUnquoted(StringBuilder sb, String name, String val) {
/* 56 */     sb.append(name);
/* 57 */     sb.append('=');
/* 58 */     sb.append(val);
/* 59 */     sb.append(';');
/* 60 */     sb.append(' ');
/*    */   }
/*    */   
/*    */   static void addQuoted(StringBuilder sb, String name, String val) {
/* 64 */     if (val == null) {
/* 65 */       val = "";
/*    */     }
/*    */     
/* 68 */     sb.append(name);
/* 69 */     sb.append('=');
/* 70 */     sb.append('"');
/* 71 */     sb.append(val.replace("\\", "\\\\").replace("\"", "\\\""));
/* 72 */     sb.append('"');
/* 73 */     sb.append(';');
/* 74 */     sb.append(' ');
/*    */   }
/*    */   
/*    */   static void add(StringBuilder sb, String name, long val) {
/* 78 */     sb.append(name);
/* 79 */     sb.append('=');
/* 80 */     sb.append(val);
/* 81 */     sb.append(';');
/* 82 */     sb.append(' ');
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\CookieEncoderUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */