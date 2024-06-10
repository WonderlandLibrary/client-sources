/*     */ package org.spongepowered.asm.lib.signature;
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
/*     */ public class SignatureReader
/*     */ {
/*     */   private final String signature;
/*     */   
/*     */   public SignatureReader(String signature) {
/*  54 */     this.signature = signature;
/*     */   }
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
/*     */   public void accept(SignatureVisitor v) {
/*     */     int pos;
/*  73 */     String signature = this.signature;
/*  74 */     int len = signature.length();
/*     */ 
/*     */ 
/*     */     
/*  78 */     if (signature.charAt(0) == '<') {
/*  79 */       char c; pos = 2;
/*     */       do {
/*  81 */         int end = signature.indexOf(':', pos);
/*  82 */         v.visitFormalTypeParameter(signature.substring(pos - 1, end));
/*  83 */         pos = end + 1;
/*     */         
/*  85 */         c = signature.charAt(pos);
/*  86 */         if (c == 'L' || c == '[' || c == 'T') {
/*  87 */           pos = parseType(signature, pos, v.visitClassBound());
/*     */         }
/*     */         
/*  90 */         while ((c = signature.charAt(pos++)) == ':') {
/*  91 */           pos = parseType(signature, pos, v.visitInterfaceBound());
/*     */         }
/*  93 */       } while (c != '>');
/*     */     } else {
/*  95 */       pos = 0;
/*     */     } 
/*     */     
/*  98 */     if (signature.charAt(pos) == '(') {
/*  99 */       pos++;
/* 100 */       while (signature.charAt(pos) != ')') {
/* 101 */         pos = parseType(signature, pos, v.visitParameterType());
/*     */       }
/* 103 */       pos = parseType(signature, pos + 1, v.visitReturnType());
/* 104 */       while (pos < len) {
/* 105 */         pos = parseType(signature, pos + 1, v.visitExceptionType());
/*     */       }
/*     */     } else {
/* 108 */       pos = parseType(signature, pos, v.visitSuperclass());
/* 109 */       while (pos < len) {
/* 110 */         pos = parseType(signature, pos, v.visitInterface());
/*     */       }
/*     */     } 
/*     */   }
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
/*     */   public void acceptType(SignatureVisitor v) {
/* 130 */     parseType(this.signature, 0, v);
/*     */   }
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
/*     */   private static int parseType(String signature, int pos, SignatureVisitor v) {
/*     */     int end;
/*     */     char c;
/* 151 */     switch (c = signature.charAt(pos++)) {
/*     */       case 'B':
/*     */       case 'C':
/*     */       case 'D':
/*     */       case 'F':
/*     */       case 'I':
/*     */       case 'J':
/*     */       case 'S':
/*     */       case 'V':
/*     */       case 'Z':
/* 161 */         v.visitBaseType(c);
/* 162 */         return pos;
/*     */       
/*     */       case '[':
/* 165 */         return parseType(signature, pos, v.visitArrayType());
/*     */       
/*     */       case 'T':
/* 168 */         end = signature.indexOf(';', pos);
/* 169 */         v.visitTypeVariable(signature.substring(pos, end));
/* 170 */         return end + 1;
/*     */     } 
/*     */     
/* 173 */     int start = pos;
/* 174 */     boolean visited = false;
/* 175 */     boolean inner = false; label36: while (true) {
/*     */       String name;
/* 177 */       switch (c = signature.charAt(pos++)) {
/*     */         case '.':
/*     */         case ';':
/* 180 */           if (!visited) {
/* 181 */             String str = signature.substring(start, pos - 1);
/* 182 */             if (inner) {
/* 183 */               v.visitInnerClassType(str);
/*     */             } else {
/* 185 */               v.visitClassType(str);
/*     */             } 
/*     */           } 
/* 188 */           if (c == ';') {
/* 189 */             v.visitEnd();
/* 190 */             return pos;
/*     */           } 
/* 192 */           start = pos;
/* 193 */           visited = false;
/* 194 */           inner = true;
/*     */ 
/*     */         
/*     */         case '<':
/* 198 */           name = signature.substring(start, pos - 1);
/* 199 */           if (inner) {
/* 200 */             v.visitInnerClassType(name);
/*     */           } else {
/* 202 */             v.visitClassType(name);
/*     */           } 
/* 204 */           visited = true;
/*     */           while (true) {
/* 206 */             switch (c = signature.charAt(pos)) {
/*     */               case '>':
/*     */                 continue label36;
/*     */               case '*':
/* 210 */                 pos++;
/* 211 */                 v.visitTypeArgument();
/*     */                 continue;
/*     */               case '+':
/*     */               case '-':
/* 215 */                 pos = parseType(signature, pos + 1, v
/* 216 */                     .visitTypeArgument(c));
/*     */                 continue;
/*     */             } 
/* 219 */             pos = parseType(signature, pos, v
/* 220 */                 .visitTypeArgument('='));
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\signature\SignatureReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */