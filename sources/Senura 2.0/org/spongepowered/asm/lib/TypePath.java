/*     */ package org.spongepowered.asm.lib;
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
/*     */ public class TypePath
/*     */ {
/*     */   public static final int ARRAY_ELEMENT = 0;
/*     */   public static final int INNER_TYPE = 1;
/*     */   public static final int WILDCARD_BOUND = 2;
/*     */   public static final int TYPE_ARGUMENT = 3;
/*     */   byte[] b;
/*     */   int offset;
/*     */   
/*     */   TypePath(byte[] b, int offset) {
/*  85 */     this.b = b;
/*  86 */     this.offset = offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLength() {
/*  95 */     return this.b[this.offset];
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
/*     */   public int getStep(int index) {
/* 108 */     return this.b[this.offset + 2 * index + 1];
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
/*     */   public int getStepArgument(int index) {
/* 122 */     return this.b[this.offset + 2 * index + 2];
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
/*     */   public static TypePath fromString(String typePath) {
/* 135 */     if (typePath == null || typePath.length() == 0) {
/* 136 */       return null;
/*     */     }
/* 138 */     int n = typePath.length();
/* 139 */     ByteVector out = new ByteVector(n);
/* 140 */     out.putByte(0);
/* 141 */     for (int i = 0; i < n; ) {
/* 142 */       char c = typePath.charAt(i++);
/* 143 */       if (c == '[') {
/* 144 */         out.put11(0, 0); continue;
/* 145 */       }  if (c == '.') {
/* 146 */         out.put11(1, 0); continue;
/* 147 */       }  if (c == '*') {
/* 148 */         out.put11(2, 0); continue;
/* 149 */       }  if (c >= '0' && c <= '9') {
/* 150 */         int typeArg = c - 48;
/* 151 */         while (i < n && (c = typePath.charAt(i)) >= '0' && c <= '9') {
/* 152 */           typeArg = typeArg * 10 + c - 48;
/* 153 */           i++;
/*     */         } 
/* 155 */         if (i < n && typePath.charAt(i) == ';') {
/* 156 */           i++;
/*     */         }
/* 158 */         out.put11(3, typeArg);
/*     */       } 
/*     */     } 
/* 161 */     out.data[0] = (byte)(out.length / 2);
/* 162 */     return new TypePath(out.data, 0);
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
/*     */   public String toString() {
/* 174 */     int length = getLength();
/* 175 */     StringBuilder result = new StringBuilder(length * 2);
/* 176 */     for (int i = 0; i < length; i++) {
/* 177 */       switch (getStep(i)) {
/*     */         case 0:
/* 179 */           result.append('[');
/*     */           break;
/*     */         case 1:
/* 182 */           result.append('.');
/*     */           break;
/*     */         case 2:
/* 185 */           result.append('*');
/*     */           break;
/*     */         case 3:
/* 188 */           result.append(getStepArgument(i)).append(';');
/*     */           break;
/*     */         default:
/* 191 */           result.append('_'); break;
/*     */       } 
/*     */     } 
/* 194 */     return result.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\TypePath.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */