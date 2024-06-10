/*     */ package org.spongepowered.asm.lib;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
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
/*     */ public class Type
/*     */ {
/*     */   public static final int VOID = 0;
/*     */   public static final int BOOLEAN = 1;
/*     */   public static final int CHAR = 2;
/*     */   public static final int BYTE = 3;
/*     */   public static final int SHORT = 4;
/*     */   public static final int INT = 5;
/*     */   public static final int FLOAT = 6;
/*     */   public static final int LONG = 7;
/*     */   public static final int DOUBLE = 8;
/*     */   public static final int ARRAY = 9;
/*     */   public static final int OBJECT = 10;
/*     */   public static final int METHOD = 11;
/* 107 */   public static final Type VOID_TYPE = new Type(0, null, 1443168256, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   public static final Type BOOLEAN_TYPE = new Type(1, null, 1509950721, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   public static final Type CHAR_TYPE = new Type(2, null, 1124075009, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   public static final Type BYTE_TYPE = new Type(3, null, 1107297537, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 131 */   public static final Type SHORT_TYPE = new Type(4, null, 1392510721, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   public static final Type INT_TYPE = new Type(5, null, 1224736769, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 143 */   public static final Type FLOAT_TYPE = new Type(6, null, 1174536705, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public static final Type LONG_TYPE = new Type(7, null, 1241579778, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   public static final Type DOUBLE_TYPE = new Type(8, null, 1141048066, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int sort;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final char[] buf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int off;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int len;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Type(int sort, char[] buf, int off, int len) {
/* 203 */     this.sort = sort;
/* 204 */     this.buf = buf;
/* 205 */     this.off = off;
/* 206 */     this.len = len;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getType(String typeDescriptor) {
/* 217 */     return getType(typeDescriptor.toCharArray(), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getObjectType(String internalName) {
/* 228 */     char[] buf = internalName.toCharArray();
/* 229 */     return new Type((buf[0] == '[') ? 9 : 10, buf, 0, buf.length);
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
/*     */   public static Type getMethodType(String methodDescriptor) {
/* 241 */     return getType(methodDescriptor.toCharArray(), 0);
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
/*     */   public static Type getMethodType(Type returnType, Type... argumentTypes) {
/* 257 */     return getType(getMethodDescriptor(returnType, argumentTypes));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getType(Class<?> c) {
/* 268 */     if (c.isPrimitive()) {
/* 269 */       if (c == int.class)
/* 270 */         return INT_TYPE; 
/* 271 */       if (c == void.class)
/* 272 */         return VOID_TYPE; 
/* 273 */       if (c == boolean.class)
/* 274 */         return BOOLEAN_TYPE; 
/* 275 */       if (c == byte.class)
/* 276 */         return BYTE_TYPE; 
/* 277 */       if (c == char.class)
/* 278 */         return CHAR_TYPE; 
/* 279 */       if (c == short.class)
/* 280 */         return SHORT_TYPE; 
/* 281 */       if (c == double.class)
/* 282 */         return DOUBLE_TYPE; 
/* 283 */       if (c == float.class) {
/* 284 */         return FLOAT_TYPE;
/*     */       }
/* 286 */       return LONG_TYPE;
/*     */     } 
/*     */     
/* 289 */     return getType(getDescriptor(c));
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
/*     */   public static Type getType(Constructor<?> c) {
/* 301 */     return getType(getConstructorDescriptor(c));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getType(Method m) {
/* 312 */     return getType(getMethodDescriptor(m));
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
/*     */   public static Type[] getArgumentTypes(String methodDescriptor) {
/* 325 */     char[] buf = methodDescriptor.toCharArray();
/* 326 */     int off = 1;
/* 327 */     int size = 0;
/*     */     while (true) {
/* 329 */       char car = buf[off++];
/* 330 */       if (car == ')')
/*     */         break; 
/* 332 */       if (car == 'L') {
/* 333 */         while (buf[off++] != ';');
/*     */         
/* 335 */         size++; continue;
/* 336 */       }  if (car != '[') {
/* 337 */         size++;
/*     */       }
/*     */     } 
/* 340 */     Type[] args = new Type[size];
/* 341 */     off = 1;
/* 342 */     size = 0;
/* 343 */     while (buf[off] != ')') {
/* 344 */       args[size] = getType(buf, off);
/* 345 */       off += (args[size]).len + (((args[size]).sort == 10) ? 2 : 0);
/* 346 */       size++;
/*     */     } 
/* 348 */     return args;
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
/*     */   public static Type[] getArgumentTypes(Method method) {
/* 361 */     Class<?>[] classes = method.getParameterTypes();
/* 362 */     Type[] types = new Type[classes.length];
/* 363 */     for (int i = classes.length - 1; i >= 0; i--) {
/* 364 */       types[i] = getType(classes[i]);
/*     */     }
/* 366 */     return types;
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
/*     */   public static Type getReturnType(String methodDescriptor) {
/* 379 */     char[] buf = methodDescriptor.toCharArray();
/* 380 */     int off = 1;
/*     */     while (true) {
/* 382 */       char car = buf[off++];
/* 383 */       if (car == ')')
/* 384 */         return getType(buf, off); 
/* 385 */       if (car == 'L') {
/* 386 */         while (buf[off++] != ';');
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
/*     */   public static Type getReturnType(Method method) {
/* 402 */     return getType(method.getReturnType());
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
/*     */   public static int getArgumentsAndReturnSizes(String desc) {
/* 417 */     int n = 1;
/* 418 */     int c = 1;
/*     */     while (true) {
/* 420 */       char car = desc.charAt(c++);
/* 421 */       if (car == ')') {
/* 422 */         car = desc.charAt(c);
/* 423 */         return n << 2 | ((car == 'V') ? 0 : ((car == 'D' || car == 'J') ? 2 : 1));
/*     */       } 
/* 425 */       if (car == 'L') {
/* 426 */         while (desc.charAt(c++) != ';');
/*     */         
/* 428 */         n++; continue;
/* 429 */       }  if (car == '[') {
/* 430 */         while ((car = desc.charAt(c)) == '[') {
/* 431 */           c++;
/*     */         }
/* 433 */         if (car == 'D' || car == 'J')
/* 434 */           n--;  continue;
/*     */       } 
/* 436 */       if (car == 'D' || car == 'J') {
/* 437 */         n += 2; continue;
/*     */       } 
/* 439 */       n++;
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
/*     */   private static Type getType(char[] buf, int off) {
/*     */     int len;
/* 457 */     switch (buf[off]) {
/*     */       case 'V':
/* 459 */         return VOID_TYPE;
/*     */       case 'Z':
/* 461 */         return BOOLEAN_TYPE;
/*     */       case 'C':
/* 463 */         return CHAR_TYPE;
/*     */       case 'B':
/* 465 */         return BYTE_TYPE;
/*     */       case 'S':
/* 467 */         return SHORT_TYPE;
/*     */       case 'I':
/* 469 */         return INT_TYPE;
/*     */       case 'F':
/* 471 */         return FLOAT_TYPE;
/*     */       case 'J':
/* 473 */         return LONG_TYPE;
/*     */       case 'D':
/* 475 */         return DOUBLE_TYPE;
/*     */       case '[':
/* 477 */         len = 1;
/* 478 */         while (buf[off + len] == '[') {
/* 479 */           len++;
/*     */         }
/* 481 */         if (buf[off + len] == 'L') {
/* 482 */           len++;
/* 483 */           while (buf[off + len] != ';') {
/* 484 */             len++;
/*     */           }
/*     */         } 
/* 487 */         return new Type(9, buf, off, len + 1);
/*     */       case 'L':
/* 489 */         len = 1;
/* 490 */         while (buf[off + len] != ';') {
/* 491 */           len++;
/*     */         }
/* 493 */         return new Type(10, buf, off + 1, len - 1);
/*     */     } 
/*     */     
/* 496 */     return new Type(11, buf, off, buf.length - off);
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
/*     */   public int getSort() {
/* 514 */     return this.sort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDimensions() {
/* 524 */     int i = 1;
/* 525 */     while (this.buf[this.off + i] == '[') {
/* 526 */       i++;
/*     */     }
/* 528 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getElementType() {
/* 538 */     return getType(this.buf, this.off + getDimensions());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClassName() {
/*     */     StringBuilder sb;
/*     */     int i;
/* 548 */     switch (this.sort) {
/*     */       case 0:
/* 550 */         return "void";
/*     */       case 1:
/* 552 */         return "boolean";
/*     */       case 2:
/* 554 */         return "char";
/*     */       case 3:
/* 556 */         return "byte";
/*     */       case 4:
/* 558 */         return "short";
/*     */       case 5:
/* 560 */         return "int";
/*     */       case 6:
/* 562 */         return "float";
/*     */       case 7:
/* 564 */         return "long";
/*     */       case 8:
/* 566 */         return "double";
/*     */       case 9:
/* 568 */         sb = new StringBuilder(getElementType().getClassName());
/* 569 */         for (i = getDimensions(); i > 0; i--) {
/* 570 */           sb.append("[]");
/*     */         }
/* 572 */         return sb.toString();
/*     */       case 10:
/* 574 */         return (new String(this.buf, this.off, this.len)).replace('/', '.');
/*     */     } 
/* 576 */     return null;
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
/*     */   public String getInternalName() {
/* 589 */     return new String(this.buf, this.off, this.len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type[] getArgumentTypes() {
/* 599 */     return getArgumentTypes(getDescriptor());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getReturnType() {
/* 609 */     return getReturnType(getDescriptor());
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
/*     */   public int getArgumentsAndReturnSizes() {
/* 624 */     return getArgumentsAndReturnSizes(getDescriptor());
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
/*     */   public String getDescriptor() {
/* 637 */     StringBuilder buf = new StringBuilder();
/* 638 */     getDescriptor(buf);
/* 639 */     return buf.toString();
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
/*     */   public static String getMethodDescriptor(Type returnType, Type... argumentTypes) {
/* 655 */     StringBuilder buf = new StringBuilder();
/* 656 */     buf.append('(');
/* 657 */     for (int i = 0; i < argumentTypes.length; i++) {
/* 658 */       argumentTypes[i].getDescriptor(buf);
/*     */     }
/* 660 */     buf.append(')');
/* 661 */     returnType.getDescriptor(buf);
/* 662 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getDescriptor(StringBuilder buf) {
/* 673 */     if (this.buf == null) {
/*     */ 
/*     */       
/* 676 */       buf.append((char)((this.off & 0xFF000000) >>> 24));
/* 677 */     } else if (this.sort == 10) {
/* 678 */       buf.append('L');
/* 679 */       buf.append(this.buf, this.off, this.len);
/* 680 */       buf.append(';');
/*     */     } else {
/* 682 */       buf.append(this.buf, this.off, this.len);
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
/*     */   public static String getInternalName(Class<?> c) {
/* 701 */     return c.getName().replace('.', '/');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDescriptor(Class<?> c) {
/* 712 */     StringBuilder buf = new StringBuilder();
/* 713 */     getDescriptor(buf, c);
/* 714 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getConstructorDescriptor(Constructor<?> c) {
/* 725 */     Class<?>[] parameters = c.getParameterTypes();
/* 726 */     StringBuilder buf = new StringBuilder();
/* 727 */     buf.append('(');
/* 728 */     for (int i = 0; i < parameters.length; i++) {
/* 729 */       getDescriptor(buf, parameters[i]);
/*     */     }
/* 731 */     return buf.append(")V").toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMethodDescriptor(Method m) {
/* 742 */     Class<?>[] parameters = m.getParameterTypes();
/* 743 */     StringBuilder buf = new StringBuilder();
/* 744 */     buf.append('(');
/* 745 */     for (int i = 0; i < parameters.length; i++) {
/* 746 */       getDescriptor(buf, parameters[i]);
/*     */     }
/* 748 */     buf.append(')');
/* 749 */     getDescriptor(buf, m.getReturnType());
/* 750 */     return buf.toString();
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
/*     */   private static void getDescriptor(StringBuilder buf, Class<?> c) {
/* 762 */     Class<?> d = c;
/*     */     while (true) {
/* 764 */       if (d.isPrimitive()) {
/*     */         char car;
/* 766 */         if (d == int.class) {
/* 767 */           car = 'I';
/* 768 */         } else if (d == void.class) {
/* 769 */           car = 'V';
/* 770 */         } else if (d == boolean.class) {
/* 771 */           car = 'Z';
/* 772 */         } else if (d == byte.class) {
/* 773 */           car = 'B';
/* 774 */         } else if (d == char.class) {
/* 775 */           car = 'C';
/* 776 */         } else if (d == short.class) {
/* 777 */           car = 'S';
/* 778 */         } else if (d == double.class) {
/* 779 */           car = 'D';
/* 780 */         } else if (d == float.class) {
/* 781 */           car = 'F';
/*     */         } else {
/* 783 */           car = 'J';
/*     */         } 
/* 785 */         buf.append(car); return;
/*     */       } 
/* 787 */       if (d.isArray()) {
/* 788 */         buf.append('[');
/* 789 */         d = d.getComponentType(); continue;
/*     */       }  break;
/* 791 */     }  buf.append('L');
/* 792 */     String name = d.getName();
/* 793 */     int len = name.length();
/* 794 */     for (int i = 0; i < len; i++) {
/* 795 */       char car = name.charAt(i);
/* 796 */       buf.append((car == '.') ? 47 : car);
/*     */     } 
/* 798 */     buf.append(';');
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
/*     */   public int getSize() {
/* 817 */     return (this.buf == null) ? (this.off & 0xFF) : 1;
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
/*     */   public int getOpcode(int opcode) {
/* 833 */     if (opcode == 46 || opcode == 79)
/*     */     {
/*     */       
/* 836 */       return opcode + ((this.buf == null) ? ((this.off & 0xFF00) >> 8) : 4);
/*     */     }
/*     */ 
/*     */     
/* 840 */     return opcode + ((this.buf == null) ? ((this.off & 0xFF0000) >> 16) : 4);
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
/*     */   public boolean equals(Object o) {
/* 857 */     if (this == o) {
/* 858 */       return true;
/*     */     }
/* 860 */     if (!(o instanceof Type)) {
/* 861 */       return false;
/*     */     }
/* 863 */     Type t = (Type)o;
/* 864 */     if (this.sort != t.sort) {
/* 865 */       return false;
/*     */     }
/* 867 */     if (this.sort >= 9) {
/* 868 */       if (this.len != t.len) {
/* 869 */         return false;
/*     */       }
/* 871 */       for (int i = this.off, j = t.off, end = i + this.len; i < end; i++, j++) {
/* 872 */         if (this.buf[i] != t.buf[j]) {
/* 873 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 877 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 887 */     int hc = 13 * this.sort;
/* 888 */     if (this.sort >= 9) {
/* 889 */       for (int i = this.off, end = i + this.len; i < end; i++) {
/* 890 */         hc = 17 * (hc + this.buf[i]);
/*     */       }
/*     */     }
/* 893 */     return hc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 903 */     return getDescriptor();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\Type.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */