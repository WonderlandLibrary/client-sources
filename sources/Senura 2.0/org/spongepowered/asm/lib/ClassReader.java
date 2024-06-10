/*      */ package org.spongepowered.asm.lib;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ClassReader
/*      */ {
/*      */   static final boolean SIGNATURES = true;
/*      */   static final boolean ANNOTATIONS = true;
/*      */   static final boolean FRAMES = true;
/*      */   static final boolean WRITER = true;
/*      */   static final boolean RESIZE = true;
/*      */   public static final int SKIP_CODE = 1;
/*      */   public static final int SKIP_DEBUG = 2;
/*      */   public static final int SKIP_FRAMES = 4;
/*      */   public static final int EXPAND_FRAMES = 8;
/*      */   static final int EXPAND_ASM_INSNS = 256;
/*      */   public final byte[] b;
/*      */   private final int[] items;
/*      */   private final String[] strings;
/*      */   private final int maxStringLength;
/*      */   public final int header;
/*      */   
/*      */   public ClassReader(byte[] b) {
/*  168 */     this(b, 0, b.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassReader(byte[] b, int off, int len) {
/*  182 */     this.b = b;
/*      */     
/*  184 */     if (readShort(off + 6) > 52) {
/*  185 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/*  188 */     this.items = new int[readUnsignedShort(off + 8)];
/*  189 */     int n = this.items.length;
/*  190 */     this.strings = new String[n];
/*  191 */     int max = 0;
/*  192 */     int index = off + 10;
/*  193 */     for (int i = 1; i < n; i++) {
/*  194 */       int size; this.items[i] = index + 1;
/*      */       
/*  196 */       switch (b[index]) {
/*      */         case 3:
/*      */         case 4:
/*      */         case 9:
/*      */         case 10:
/*      */         case 11:
/*      */         case 12:
/*      */         case 18:
/*  204 */           size = 5;
/*      */           break;
/*      */         case 5:
/*      */         case 6:
/*  208 */           size = 9;
/*  209 */           i++;
/*      */           break;
/*      */         case 1:
/*  212 */           size = 3 + readUnsignedShort(index + 1);
/*  213 */           if (size > max) {
/*  214 */             max = size;
/*      */           }
/*      */           break;
/*      */         case 15:
/*  218 */           size = 4;
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         default:
/*  224 */           size = 3;
/*      */           break;
/*      */       } 
/*  227 */       index += size;
/*      */     } 
/*  229 */     this.maxStringLength = max;
/*      */     
/*  231 */     this.header = index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getAccess() {
/*  244 */     return readUnsignedShort(this.header);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getClassName() {
/*  256 */     return readClass(this.header + 2, new char[this.maxStringLength]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSuperName() {
/*  270 */     return readClass(this.header + 4, new char[this.maxStringLength]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getInterfaces() {
/*  283 */     int index = this.header + 6;
/*  284 */     int n = readUnsignedShort(index);
/*  285 */     String[] interfaces = new String[n];
/*  286 */     if (n > 0) {
/*  287 */       char[] buf = new char[this.maxStringLength];
/*  288 */       for (int i = 0; i < n; i++) {
/*  289 */         index += 2;
/*  290 */         interfaces[i] = readClass(index, buf);
/*      */       } 
/*      */     } 
/*  293 */     return interfaces;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void copyPool(ClassWriter classWriter) {
/*  304 */     char[] buf = new char[this.maxStringLength];
/*  305 */     int ll = this.items.length;
/*  306 */     Item[] items2 = new Item[ll];
/*  307 */     for (int i = 1; i < ll; i++) {
/*  308 */       int nameType; String s; int fieldOrMethodRef, index = this.items[i];
/*  309 */       int tag = this.b[index - 1];
/*  310 */       Item item = new Item(i);
/*      */       
/*  312 */       switch (tag) {
/*      */         case 9:
/*      */         case 10:
/*      */         case 11:
/*  316 */           nameType = this.items[readUnsignedShort(index + 2)];
/*  317 */           item.set(tag, readClass(index, buf), readUTF8(nameType, buf), 
/*  318 */               readUTF8(nameType + 2, buf));
/*      */           break;
/*      */         case 3:
/*  321 */           item.set(readInt(index));
/*      */           break;
/*      */         case 4:
/*  324 */           item.set(Float.intBitsToFloat(readInt(index)));
/*      */           break;
/*      */         case 12:
/*  327 */           item.set(tag, readUTF8(index, buf), readUTF8(index + 2, buf), null);
/*      */           break;
/*      */         
/*      */         case 5:
/*  331 */           item.set(readLong(index));
/*  332 */           i++;
/*      */           break;
/*      */         case 6:
/*  335 */           item.set(Double.longBitsToDouble(readLong(index)));
/*  336 */           i++;
/*      */           break;
/*      */         case 1:
/*  339 */           s = this.strings[i];
/*  340 */           if (s == null) {
/*  341 */             index = this.items[i];
/*  342 */             s = this.strings[i] = readUTF(index + 2, 
/*  343 */                 readUnsignedShort(index), buf);
/*      */           } 
/*  345 */           item.set(tag, s, null, null);
/*      */           break;
/*      */         
/*      */         case 15:
/*  349 */           fieldOrMethodRef = this.items[readUnsignedShort(index + 1)];
/*  350 */           nameType = this.items[readUnsignedShort(fieldOrMethodRef + 2)];
/*  351 */           item.set(20 + readByte(index), 
/*  352 */               readClass(fieldOrMethodRef, buf), 
/*  353 */               readUTF8(nameType, buf), readUTF8(nameType + 2, buf));
/*      */           break;
/*      */         
/*      */         case 18:
/*  357 */           if (classWriter.bootstrapMethods == null) {
/*  358 */             copyBootstrapMethods(classWriter, items2, buf);
/*      */           }
/*  360 */           nameType = this.items[readUnsignedShort(index + 2)];
/*  361 */           item.set(readUTF8(nameType, buf), readUTF8(nameType + 2, buf), 
/*  362 */               readUnsignedShort(index));
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         default:
/*  368 */           item.set(tag, readUTF8(index, buf), null, null);
/*      */           break;
/*      */       } 
/*      */       
/*  372 */       int index2 = item.hashCode % items2.length;
/*  373 */       item.next = items2[index2];
/*  374 */       items2[index2] = item;
/*      */     } 
/*      */     
/*  377 */     int off = this.items[1] - 1;
/*  378 */     classWriter.pool.putByteArray(this.b, off, this.header - off);
/*  379 */     classWriter.items = items2;
/*  380 */     classWriter.threshold = (int)(0.75D * ll);
/*  381 */     classWriter.index = ll;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void copyBootstrapMethods(ClassWriter classWriter, Item[] items, char[] c) {
/*  394 */     int u = getAttributes();
/*  395 */     boolean found = false;
/*  396 */     for (int i = readUnsignedShort(u); i > 0; i--) {
/*  397 */       String attrName = readUTF8(u + 2, c);
/*  398 */       if ("BootstrapMethods".equals(attrName)) {
/*  399 */         found = true;
/*      */         break;
/*      */       } 
/*  402 */       u += 6 + readInt(u + 4);
/*      */     } 
/*  404 */     if (!found) {
/*      */       return;
/*      */     }
/*      */     
/*  408 */     int boostrapMethodCount = readUnsignedShort(u + 8);
/*  409 */     for (int j = 0, v = u + 10; j < boostrapMethodCount; j++) {
/*  410 */       int position = v - u - 10;
/*  411 */       int hashCode = readConst(readUnsignedShort(v), c).hashCode();
/*  412 */       for (int k = readUnsignedShort(v + 2); k > 0; k--) {
/*  413 */         hashCode ^= readConst(readUnsignedShort(v + 4), c).hashCode();
/*  414 */         v += 2;
/*      */       } 
/*  416 */       v += 4;
/*  417 */       Item item = new Item(j);
/*  418 */       item.set(position, hashCode & Integer.MAX_VALUE);
/*  419 */       int index = item.hashCode % items.length;
/*  420 */       item.next = items[index];
/*  421 */       items[index] = item;
/*      */     } 
/*  423 */     int attrSize = readInt(u + 4);
/*  424 */     ByteVector bootstrapMethods = new ByteVector(attrSize + 62);
/*  425 */     bootstrapMethods.putByteArray(this.b, u + 10, attrSize - 2);
/*  426 */     classWriter.bootstrapMethodsCount = boostrapMethodCount;
/*  427 */     classWriter.bootstrapMethods = bootstrapMethods;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassReader(InputStream is) throws IOException {
/*  439 */     this(readClass(is, false));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassReader(String name) throws IOException {
/*  451 */     this(readClass(
/*  452 */           ClassLoader.getSystemResourceAsStream(name.replace('.', '/') + ".class"), true));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte[] readClass(InputStream is, boolean close) throws IOException {
/*  469 */     if (is == null) {
/*  470 */       throw new IOException("Class not found");
/*      */     }
/*      */     try {
/*  473 */       byte[] b = new byte[is.available()];
/*  474 */       int len = 0;
/*      */       while (true) {
/*  476 */         int n = is.read(b, len, b.length - len);
/*  477 */         if (n == -1) {
/*  478 */           if (len < b.length) {
/*  479 */             byte[] c = new byte[len];
/*  480 */             System.arraycopy(b, 0, c, 0, len);
/*  481 */             b = c;
/*      */           } 
/*  483 */           return b;
/*      */         } 
/*  485 */         len += n;
/*  486 */         if (len == b.length) {
/*  487 */           int last = is.read();
/*  488 */           if (last < 0) {
/*  489 */             return b;
/*      */           }
/*  491 */           byte[] c = new byte[b.length + 1000];
/*  492 */           System.arraycopy(b, 0, c, 0, len);
/*  493 */           c[len++] = (byte)last;
/*  494 */           b = c;
/*      */         } 
/*      */       } 
/*      */     } finally {
/*  498 */       if (close) {
/*  499 */         is.close();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void accept(ClassVisitor classVisitor, int flags) {
/*  521 */     accept(classVisitor, new Attribute[0], flags);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void accept(ClassVisitor classVisitor, Attribute[] attrs, int flags) {
/*  547 */     int u = this.header;
/*  548 */     char[] c = new char[this.maxStringLength];
/*      */     
/*  550 */     Context context = new Context();
/*  551 */     context.attrs = attrs;
/*  552 */     context.flags = flags;
/*  553 */     context.buffer = c;
/*      */ 
/*      */     
/*  556 */     int access = readUnsignedShort(u);
/*  557 */     String name = readClass(u + 2, c);
/*  558 */     String superClass = readClass(u + 4, c);
/*  559 */     String[] interfaces = new String[readUnsignedShort(u + 6)];
/*  560 */     u += 8;
/*  561 */     for (int i = 0; i < interfaces.length; i++) {
/*  562 */       interfaces[i] = readClass(u, c);
/*  563 */       u += 2;
/*      */     } 
/*      */ 
/*      */     
/*  567 */     String signature = null;
/*  568 */     String sourceFile = null;
/*  569 */     String sourceDebug = null;
/*  570 */     String enclosingOwner = null;
/*  571 */     String enclosingName = null;
/*  572 */     String enclosingDesc = null;
/*  573 */     int anns = 0;
/*  574 */     int ianns = 0;
/*  575 */     int tanns = 0;
/*  576 */     int itanns = 0;
/*  577 */     int innerClasses = 0;
/*  578 */     Attribute attributes = null;
/*      */     
/*  580 */     u = getAttributes(); int j;
/*  581 */     for (j = readUnsignedShort(u); j > 0; j--) {
/*  582 */       String attrName = readUTF8(u + 2, c);
/*      */ 
/*      */       
/*  585 */       if ("SourceFile".equals(attrName)) {
/*  586 */         sourceFile = readUTF8(u + 8, c);
/*  587 */       } else if ("InnerClasses".equals(attrName)) {
/*  588 */         innerClasses = u + 8;
/*  589 */       } else if ("EnclosingMethod".equals(attrName)) {
/*  590 */         enclosingOwner = readClass(u + 8, c);
/*  591 */         int item = readUnsignedShort(u + 10);
/*  592 */         if (item != 0) {
/*  593 */           enclosingName = readUTF8(this.items[item], c);
/*  594 */           enclosingDesc = readUTF8(this.items[item] + 2, c);
/*      */         } 
/*  596 */       } else if ("Signature".equals(attrName)) {
/*  597 */         signature = readUTF8(u + 8, c);
/*  598 */       } else if ("RuntimeVisibleAnnotations"
/*  599 */         .equals(attrName)) {
/*  600 */         anns = u + 8;
/*  601 */       } else if ("RuntimeVisibleTypeAnnotations"
/*  602 */         .equals(attrName)) {
/*  603 */         tanns = u + 8;
/*  604 */       } else if ("Deprecated".equals(attrName)) {
/*  605 */         access |= 0x20000;
/*  606 */       } else if ("Synthetic".equals(attrName)) {
/*  607 */         access |= 0x41000;
/*      */       }
/*  609 */       else if ("SourceDebugExtension".equals(attrName)) {
/*  610 */         int len = readInt(u + 4);
/*  611 */         sourceDebug = readUTF(u + 8, len, new char[len]);
/*  612 */       } else if ("RuntimeInvisibleAnnotations"
/*  613 */         .equals(attrName)) {
/*  614 */         ianns = u + 8;
/*  615 */       } else if ("RuntimeInvisibleTypeAnnotations"
/*  616 */         .equals(attrName)) {
/*  617 */         itanns = u + 8;
/*  618 */       } else if ("BootstrapMethods".equals(attrName)) {
/*  619 */         int[] bootstrapMethods = new int[readUnsignedShort(u + 8)];
/*  620 */         for (int k = 0, v = u + 10; k < bootstrapMethods.length; k++) {
/*  621 */           bootstrapMethods[k] = v;
/*  622 */           v += 2 + readUnsignedShort(v + 2) << 1;
/*      */         } 
/*  624 */         context.bootstrapMethods = bootstrapMethods;
/*      */       } else {
/*  626 */         Attribute attr = readAttribute(attrs, attrName, u + 8, 
/*  627 */             readInt(u + 4), c, -1, null);
/*  628 */         if (attr != null) {
/*  629 */           attr.next = attributes;
/*  630 */           attributes = attr;
/*      */         } 
/*      */       } 
/*  633 */       u += 6 + readInt(u + 4);
/*      */     } 
/*      */ 
/*      */     
/*  637 */     classVisitor.visit(readInt(this.items[1] - 7), access, name, signature, superClass, interfaces);
/*      */ 
/*      */ 
/*      */     
/*  641 */     if ((flags & 0x2) == 0 && (sourceFile != null || sourceDebug != null))
/*      */     {
/*  643 */       classVisitor.visitSource(sourceFile, sourceDebug);
/*      */     }
/*      */ 
/*      */     
/*  647 */     if (enclosingOwner != null) {
/*  648 */       classVisitor.visitOuterClass(enclosingOwner, enclosingName, enclosingDesc);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  653 */     if (anns != 0) {
/*  654 */       int v; for (j = readUnsignedShort(anns), v = anns + 2; j > 0; j--) {
/*  655 */         v = readAnnotationValues(v + 2, c, true, classVisitor
/*  656 */             .visitAnnotation(readUTF8(v, c), true));
/*      */       }
/*      */     } 
/*  659 */     if (ianns != 0) {
/*  660 */       int v; for (j = readUnsignedShort(ianns), v = ianns + 2; j > 0; j--) {
/*  661 */         v = readAnnotationValues(v + 2, c, true, classVisitor
/*  662 */             .visitAnnotation(readUTF8(v, c), false));
/*      */       }
/*      */     } 
/*  665 */     if (tanns != 0) {
/*  666 */       int v; for (j = readUnsignedShort(tanns), v = tanns + 2; j > 0; j--) {
/*  667 */         v = readAnnotationTarget(context, v);
/*  668 */         v = readAnnotationValues(v + 2, c, true, classVisitor
/*  669 */             .visitTypeAnnotation(context.typeRef, context.typePath, 
/*  670 */               readUTF8(v, c), true));
/*      */       } 
/*      */     } 
/*  673 */     if (itanns != 0) {
/*  674 */       int v; for (j = readUnsignedShort(itanns), v = itanns + 2; j > 0; j--) {
/*  675 */         v = readAnnotationTarget(context, v);
/*  676 */         v = readAnnotationValues(v + 2, c, true, classVisitor
/*  677 */             .visitTypeAnnotation(context.typeRef, context.typePath, 
/*  678 */               readUTF8(v, c), false));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  683 */     while (attributes != null) {
/*  684 */       Attribute attr = attributes.next;
/*  685 */       attributes.next = null;
/*  686 */       classVisitor.visitAttribute(attributes);
/*  687 */       attributes = attr;
/*      */     } 
/*      */ 
/*      */     
/*  691 */     if (innerClasses != 0) {
/*  692 */       int v = innerClasses + 2;
/*  693 */       for (int k = readUnsignedShort(innerClasses); k > 0; k--) {
/*  694 */         classVisitor.visitInnerClass(readClass(v, c), 
/*  695 */             readClass(v + 2, c), readUTF8(v + 4, c), 
/*  696 */             readUnsignedShort(v + 6));
/*  697 */         v += 8;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  702 */     u = this.header + 10 + 2 * interfaces.length;
/*  703 */     for (j = readUnsignedShort(u - 2); j > 0; j--) {
/*  704 */       u = readField(classVisitor, context, u);
/*      */     }
/*  706 */     u += 2;
/*  707 */     for (j = readUnsignedShort(u - 2); j > 0; j--) {
/*  708 */       u = readMethod(classVisitor, context, u);
/*      */     }
/*      */ 
/*      */     
/*  712 */     classVisitor.visitEnd();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int readField(ClassVisitor classVisitor, Context context, int u) {
/*  729 */     char[] c = context.buffer;
/*  730 */     int access = readUnsignedShort(u);
/*  731 */     String name = readUTF8(u + 2, c);
/*  732 */     String desc = readUTF8(u + 4, c);
/*  733 */     u += 6;
/*      */ 
/*      */     
/*  736 */     String signature = null;
/*  737 */     int anns = 0;
/*  738 */     int ianns = 0;
/*  739 */     int tanns = 0;
/*  740 */     int itanns = 0;
/*  741 */     Object value = null;
/*  742 */     Attribute attributes = null;
/*      */     
/*  744 */     for (int i = readUnsignedShort(u); i > 0; i--) {
/*  745 */       String attrName = readUTF8(u + 2, c);
/*      */ 
/*      */       
/*  748 */       if ("ConstantValue".equals(attrName)) {
/*  749 */         int item = readUnsignedShort(u + 8);
/*  750 */         value = (item == 0) ? null : readConst(item, c);
/*  751 */       } else if ("Signature".equals(attrName)) {
/*  752 */         signature = readUTF8(u + 8, c);
/*  753 */       } else if ("Deprecated".equals(attrName)) {
/*  754 */         access |= 0x20000;
/*  755 */       } else if ("Synthetic".equals(attrName)) {
/*  756 */         access |= 0x41000;
/*      */       }
/*  758 */       else if ("RuntimeVisibleAnnotations"
/*  759 */         .equals(attrName)) {
/*  760 */         anns = u + 8;
/*  761 */       } else if ("RuntimeVisibleTypeAnnotations"
/*  762 */         .equals(attrName)) {
/*  763 */         tanns = u + 8;
/*  764 */       } else if ("RuntimeInvisibleAnnotations"
/*  765 */         .equals(attrName)) {
/*  766 */         ianns = u + 8;
/*  767 */       } else if ("RuntimeInvisibleTypeAnnotations"
/*  768 */         .equals(attrName)) {
/*  769 */         itanns = u + 8;
/*      */       } else {
/*  771 */         Attribute attr = readAttribute(context.attrs, attrName, u + 8, 
/*  772 */             readInt(u + 4), c, -1, null);
/*  773 */         if (attr != null) {
/*  774 */           attr.next = attributes;
/*  775 */           attributes = attr;
/*      */         } 
/*      */       } 
/*  778 */       u += 6 + readInt(u + 4);
/*      */     } 
/*  780 */     u += 2;
/*      */ 
/*      */     
/*  783 */     FieldVisitor fv = classVisitor.visitField(access, name, desc, signature, value);
/*      */     
/*  785 */     if (fv == null) {
/*  786 */       return u;
/*      */     }
/*      */ 
/*      */     
/*  790 */     if (anns != 0) {
/*  791 */       for (int j = readUnsignedShort(anns), v = anns + 2; j > 0; j--) {
/*  792 */         v = readAnnotationValues(v + 2, c, true, fv
/*  793 */             .visitAnnotation(readUTF8(v, c), true));
/*      */       }
/*      */     }
/*  796 */     if (ianns != 0) {
/*  797 */       for (int j = readUnsignedShort(ianns), v = ianns + 2; j > 0; j--) {
/*  798 */         v = readAnnotationValues(v + 2, c, true, fv
/*  799 */             .visitAnnotation(readUTF8(v, c), false));
/*      */       }
/*      */     }
/*  802 */     if (tanns != 0) {
/*  803 */       for (int j = readUnsignedShort(tanns), v = tanns + 2; j > 0; j--) {
/*  804 */         v = readAnnotationTarget(context, v);
/*  805 */         v = readAnnotationValues(v + 2, c, true, fv
/*  806 */             .visitTypeAnnotation(context.typeRef, context.typePath, 
/*  807 */               readUTF8(v, c), true));
/*      */       } 
/*      */     }
/*  810 */     if (itanns != 0) {
/*  811 */       for (int j = readUnsignedShort(itanns), v = itanns + 2; j > 0; j--) {
/*  812 */         v = readAnnotationTarget(context, v);
/*  813 */         v = readAnnotationValues(v + 2, c, true, fv
/*  814 */             .visitTypeAnnotation(context.typeRef, context.typePath, 
/*  815 */               readUTF8(v, c), false));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  820 */     while (attributes != null) {
/*  821 */       Attribute attr = attributes.next;
/*  822 */       attributes.next = null;
/*  823 */       fv.visitAttribute(attributes);
/*  824 */       attributes = attr;
/*      */     } 
/*      */ 
/*      */     
/*  828 */     fv.visitEnd();
/*      */     
/*  830 */     return u;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int readMethod(ClassVisitor classVisitor, Context context, int u) {
/*  847 */     char[] c = context.buffer;
/*  848 */     context.access = readUnsignedShort(u);
/*  849 */     context.name = readUTF8(u + 2, c);
/*  850 */     context.desc = readUTF8(u + 4, c);
/*  851 */     u += 6;
/*      */ 
/*      */     
/*  854 */     int code = 0;
/*  855 */     int exception = 0;
/*  856 */     String[] exceptions = null;
/*  857 */     String signature = null;
/*  858 */     int methodParameters = 0;
/*  859 */     int anns = 0;
/*  860 */     int ianns = 0;
/*  861 */     int tanns = 0;
/*  862 */     int itanns = 0;
/*  863 */     int dann = 0;
/*  864 */     int mpanns = 0;
/*  865 */     int impanns = 0;
/*  866 */     int firstAttribute = u;
/*  867 */     Attribute attributes = null;
/*      */     
/*  869 */     for (int i = readUnsignedShort(u); i > 0; i--) {
/*  870 */       String attrName = readUTF8(u + 2, c);
/*      */ 
/*      */       
/*  873 */       if ("Code".equals(attrName)) {
/*  874 */         if ((context.flags & 0x1) == 0) {
/*  875 */           code = u + 8;
/*      */         }
/*  877 */       } else if ("Exceptions".equals(attrName)) {
/*  878 */         exceptions = new String[readUnsignedShort(u + 8)];
/*  879 */         exception = u + 10;
/*  880 */         for (int j = 0; j < exceptions.length; j++) {
/*  881 */           exceptions[j] = readClass(exception, c);
/*  882 */           exception += 2;
/*      */         } 
/*  884 */       } else if ("Signature".equals(attrName)) {
/*  885 */         signature = readUTF8(u + 8, c);
/*  886 */       } else if ("Deprecated".equals(attrName)) {
/*  887 */         context.access |= 0x20000;
/*  888 */       } else if ("RuntimeVisibleAnnotations"
/*  889 */         .equals(attrName)) {
/*  890 */         anns = u + 8;
/*  891 */       } else if ("RuntimeVisibleTypeAnnotations"
/*  892 */         .equals(attrName)) {
/*  893 */         tanns = u + 8;
/*  894 */       } else if ("AnnotationDefault".equals(attrName)) {
/*  895 */         dann = u + 8;
/*  896 */       } else if ("Synthetic".equals(attrName)) {
/*  897 */         context.access |= 0x41000;
/*      */       }
/*  899 */       else if ("RuntimeInvisibleAnnotations"
/*  900 */         .equals(attrName)) {
/*  901 */         ianns = u + 8;
/*  902 */       } else if ("RuntimeInvisibleTypeAnnotations"
/*  903 */         .equals(attrName)) {
/*  904 */         itanns = u + 8;
/*  905 */       } else if ("RuntimeVisibleParameterAnnotations"
/*  906 */         .equals(attrName)) {
/*  907 */         mpanns = u + 8;
/*  908 */       } else if ("RuntimeInvisibleParameterAnnotations"
/*  909 */         .equals(attrName)) {
/*  910 */         impanns = u + 8;
/*  911 */       } else if ("MethodParameters".equals(attrName)) {
/*  912 */         methodParameters = u + 8;
/*      */       } else {
/*  914 */         Attribute attr = readAttribute(context.attrs, attrName, u + 8, 
/*  915 */             readInt(u + 4), c, -1, null);
/*  916 */         if (attr != null) {
/*  917 */           attr.next = attributes;
/*  918 */           attributes = attr;
/*      */         } 
/*      */       } 
/*  921 */       u += 6 + readInt(u + 4);
/*      */     } 
/*  923 */     u += 2;
/*      */ 
/*      */     
/*  926 */     MethodVisitor mv = classVisitor.visitMethod(context.access, context.name, context.desc, signature, exceptions);
/*      */     
/*  928 */     if (mv == null) {
/*  929 */       return u;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  942 */     if (mv instanceof MethodWriter) {
/*  943 */       MethodWriter mw = (MethodWriter)mv;
/*  944 */       if (mw.cw.cr == this && signature == mw.signature) {
/*  945 */         boolean sameExceptions = false;
/*  946 */         if (exceptions == null) {
/*  947 */           sameExceptions = (mw.exceptionCount == 0);
/*  948 */         } else if (exceptions.length == mw.exceptionCount) {
/*  949 */           sameExceptions = true;
/*  950 */           for (int j = exceptions.length - 1; j >= 0; j--) {
/*  951 */             exception -= 2;
/*  952 */             if (mw.exceptions[j] != readUnsignedShort(exception)) {
/*  953 */               sameExceptions = false;
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*  958 */         if (sameExceptions) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  964 */           mw.classReaderOffset = firstAttribute;
/*  965 */           mw.classReaderLength = u - firstAttribute;
/*  966 */           return u;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  972 */     if (methodParameters != 0) {
/*  973 */       int v; for (int j = this.b[methodParameters] & 0xFF; j > 0; j--, v += 4) {
/*  974 */         mv.visitParameter(readUTF8(v, c), readUnsignedShort(v + 2));
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  979 */     if (dann != 0) {
/*  980 */       AnnotationVisitor dv = mv.visitAnnotationDefault();
/*  981 */       readAnnotationValue(dann, c, null, dv);
/*  982 */       if (dv != null) {
/*  983 */         dv.visitEnd();
/*      */       }
/*      */     } 
/*  986 */     if (anns != 0) {
/*  987 */       for (int j = readUnsignedShort(anns), v = anns + 2; j > 0; j--) {
/*  988 */         v = readAnnotationValues(v + 2, c, true, mv
/*  989 */             .visitAnnotation(readUTF8(v, c), true));
/*      */       }
/*      */     }
/*  992 */     if (ianns != 0) {
/*  993 */       for (int j = readUnsignedShort(ianns), v = ianns + 2; j > 0; j--) {
/*  994 */         v = readAnnotationValues(v + 2, c, true, mv
/*  995 */             .visitAnnotation(readUTF8(v, c), false));
/*      */       }
/*      */     }
/*  998 */     if (tanns != 0) {
/*  999 */       for (int j = readUnsignedShort(tanns), v = tanns + 2; j > 0; j--) {
/* 1000 */         v = readAnnotationTarget(context, v);
/* 1001 */         v = readAnnotationValues(v + 2, c, true, mv
/* 1002 */             .visitTypeAnnotation(context.typeRef, context.typePath, 
/* 1003 */               readUTF8(v, c), true));
/*      */       } 
/*      */     }
/* 1006 */     if (itanns != 0) {
/* 1007 */       for (int j = readUnsignedShort(itanns), v = itanns + 2; j > 0; j--) {
/* 1008 */         v = readAnnotationTarget(context, v);
/* 1009 */         v = readAnnotationValues(v + 2, c, true, mv
/* 1010 */             .visitTypeAnnotation(context.typeRef, context.typePath, 
/* 1011 */               readUTF8(v, c), false));
/*      */       } 
/*      */     }
/* 1014 */     if (mpanns != 0) {
/* 1015 */       readParameterAnnotations(mv, context, mpanns, true);
/*      */     }
/* 1017 */     if (impanns != 0) {
/* 1018 */       readParameterAnnotations(mv, context, impanns, false);
/*      */     }
/*      */ 
/*      */     
/* 1022 */     while (attributes != null) {
/* 1023 */       Attribute attr = attributes.next;
/* 1024 */       attributes.next = null;
/* 1025 */       mv.visitAttribute(attributes);
/* 1026 */       attributes = attr;
/*      */     } 
/*      */ 
/*      */     
/* 1030 */     if (code != 0) {
/* 1031 */       mv.visitCode();
/* 1032 */       readCode(mv, context, code);
/*      */     } 
/*      */ 
/*      */     
/* 1036 */     mv.visitEnd();
/*      */     
/* 1038 */     return u;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readCode(MethodVisitor mv, Context context, int u) {
/* 1053 */     byte[] b = this.b;
/* 1054 */     char[] c = context.buffer;
/* 1055 */     int maxStack = readUnsignedShort(u);
/* 1056 */     int maxLocals = readUnsignedShort(u + 2);
/* 1057 */     int codeLength = readInt(u + 4);
/* 1058 */     u += 8;
/*      */ 
/*      */     
/* 1061 */     int codeStart = u;
/* 1062 */     int codeEnd = u + codeLength;
/* 1063 */     Label[] labels = context.labels = new Label[codeLength + 2];
/* 1064 */     readLabel(codeLength + 1, labels);
/* 1065 */     while (u < codeEnd) {
/* 1066 */       int k, offset = u - codeStart;
/* 1067 */       int opcode = b[u] & 0xFF;
/* 1068 */       switch (ClassWriter.TYPE[opcode]) {
/*      */         case 0:
/*      */         case 4:
/* 1071 */           u++;
/*      */           continue;
/*      */         case 9:
/* 1074 */           readLabel(offset + readShort(u + 1), labels);
/* 1075 */           u += 3;
/*      */           continue;
/*      */         case 18:
/* 1078 */           readLabel(offset + readUnsignedShort(u + 1), labels);
/* 1079 */           u += 3;
/*      */           continue;
/*      */         case 10:
/* 1082 */           readLabel(offset + readInt(u + 1), labels);
/* 1083 */           u += 5;
/*      */           continue;
/*      */         case 17:
/* 1086 */           opcode = b[u + 1] & 0xFF;
/* 1087 */           if (opcode == 132) {
/* 1088 */             u += 6; continue;
/*      */           } 
/* 1090 */           u += 4;
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 14:
/* 1095 */           u = u + 4 - (offset & 0x3);
/*      */           
/* 1097 */           readLabel(offset + readInt(u), labels);
/* 1098 */           for (k = readInt(u + 8) - readInt(u + 4) + 1; k > 0; k--) {
/* 1099 */             readLabel(offset + readInt(u + 12), labels);
/* 1100 */             u += 4;
/*      */           } 
/* 1102 */           u += 12;
/*      */           continue;
/*      */         
/*      */         case 15:
/* 1106 */           u = u + 4 - (offset & 0x3);
/*      */           
/* 1108 */           readLabel(offset + readInt(u), labels);
/* 1109 */           for (k = readInt(u + 4); k > 0; k--) {
/* 1110 */             readLabel(offset + readInt(u + 12), labels);
/* 1111 */             u += 8;
/*      */           } 
/* 1113 */           u += 8;
/*      */           continue;
/*      */         case 1:
/*      */         case 3:
/*      */         case 11:
/* 1118 */           u += 2;
/*      */           continue;
/*      */         case 2:
/*      */         case 5:
/*      */         case 6:
/*      */         case 12:
/*      */         case 13:
/* 1125 */           u += 3;
/*      */           continue;
/*      */         case 7:
/*      */         case 8:
/* 1129 */           u += 5;
/*      */           continue;
/*      */       } 
/*      */       
/* 1133 */       u += 4;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1139 */     for (int i = readUnsignedShort(u); i > 0; i--) {
/* 1140 */       Label start = readLabel(readUnsignedShort(u + 2), labels);
/* 1141 */       Label end = readLabel(readUnsignedShort(u + 4), labels);
/* 1142 */       Label handler = readLabel(readUnsignedShort(u + 6), labels);
/* 1143 */       String type = readUTF8(this.items[readUnsignedShort(u + 8)], c);
/* 1144 */       mv.visitTryCatchBlock(start, end, handler, type);
/* 1145 */       u += 8;
/*      */     } 
/* 1147 */     u += 2;
/*      */ 
/*      */     
/* 1150 */     int[] tanns = null;
/* 1151 */     int[] itanns = null;
/* 1152 */     int tann = 0;
/* 1153 */     int itann = 0;
/* 1154 */     int ntoff = -1;
/* 1155 */     int nitoff = -1;
/* 1156 */     int varTable = 0;
/* 1157 */     int varTypeTable = 0;
/* 1158 */     boolean zip = true;
/* 1159 */     boolean unzip = ((context.flags & 0x8) != 0);
/* 1160 */     int stackMap = 0;
/* 1161 */     int stackMapSize = 0;
/* 1162 */     int frameCount = 0;
/* 1163 */     Context frame = null;
/* 1164 */     Attribute attributes = null;
/*      */     int j;
/* 1166 */     for (j = readUnsignedShort(u); j > 0; j--) {
/* 1167 */       String attrName = readUTF8(u + 2, c);
/* 1168 */       if ("LocalVariableTable".equals(attrName)) {
/* 1169 */         if ((context.flags & 0x2) == 0) {
/* 1170 */           varTable = u + 8;
/* 1171 */           for (int k = readUnsignedShort(u + 8), v = u; k > 0; k--) {
/* 1172 */             int label = readUnsignedShort(v + 10);
/* 1173 */             if (labels[label] == null) {
/* 1174 */               (readLabel(label, labels)).status |= 0x1;
/*      */             }
/* 1176 */             label += readUnsignedShort(v + 12);
/* 1177 */             if (labels[label] == null) {
/* 1178 */               (readLabel(label, labels)).status |= 0x1;
/*      */             }
/* 1180 */             v += 10;
/*      */           } 
/*      */         } 
/* 1183 */       } else if ("LocalVariableTypeTable".equals(attrName)) {
/* 1184 */         varTypeTable = u + 8;
/* 1185 */       } else if ("LineNumberTable".equals(attrName)) {
/* 1186 */         if ((context.flags & 0x2) == 0) {
/* 1187 */           for (int k = readUnsignedShort(u + 8), v = u; k > 0; k--) {
/* 1188 */             int label = readUnsignedShort(v + 10);
/* 1189 */             if (labels[label] == null) {
/* 1190 */               (readLabel(label, labels)).status |= 0x1;
/*      */             }
/* 1192 */             Label l = labels[label];
/* 1193 */             while (l.line > 0) {
/* 1194 */               if (l.next == null) {
/* 1195 */                 l.next = new Label();
/*      */               }
/* 1197 */               l = l.next;
/*      */             } 
/* 1199 */             l.line = readUnsignedShort(v + 12);
/* 1200 */             v += 4;
/*      */           } 
/*      */         }
/* 1203 */       } else if ("RuntimeVisibleTypeAnnotations"
/* 1204 */         .equals(attrName)) {
/* 1205 */         tanns = readTypeAnnotations(mv, context, u + 8, true);
/*      */         
/* 1207 */         ntoff = (tanns.length == 0 || readByte(tanns[0]) < 67) ? -1 : readUnsignedShort(tanns[0] + 1);
/* 1208 */       } else if ("RuntimeInvisibleTypeAnnotations"
/* 1209 */         .equals(attrName)) {
/* 1210 */         itanns = readTypeAnnotations(mv, context, u + 8, false);
/*      */         
/* 1212 */         nitoff = (itanns.length == 0 || readByte(itanns[0]) < 67) ? -1 : readUnsignedShort(itanns[0] + 1);
/* 1213 */       } else if ("StackMapTable".equals(attrName)) {
/* 1214 */         if ((context.flags & 0x4) == 0) {
/* 1215 */           stackMap = u + 10;
/* 1216 */           stackMapSize = readInt(u + 4);
/* 1217 */           frameCount = readUnsignedShort(u + 8);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1237 */       else if ("StackMap".equals(attrName)) {
/* 1238 */         if ((context.flags & 0x4) == 0) {
/* 1239 */           zip = false;
/* 1240 */           stackMap = u + 10;
/* 1241 */           stackMapSize = readInt(u + 4);
/* 1242 */           frameCount = readUnsignedShort(u + 8);
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1250 */         for (int k = 0; k < context.attrs.length; k++) {
/* 1251 */           if ((context.attrs[k]).type.equals(attrName)) {
/* 1252 */             Attribute attr = context.attrs[k].read(this, u + 8, 
/* 1253 */                 readInt(u + 4), c, codeStart - 8, labels);
/* 1254 */             if (attr != null) {
/* 1255 */               attr.next = attributes;
/* 1256 */               attributes = attr;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1261 */       u += 6 + readInt(u + 4);
/*      */     } 
/* 1263 */     u += 2;
/*      */ 
/*      */     
/* 1266 */     if (stackMap != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1272 */       frame = context;
/* 1273 */       frame.offset = -1;
/* 1274 */       frame.mode = 0;
/* 1275 */       frame.localCount = 0;
/* 1276 */       frame.localDiff = 0;
/* 1277 */       frame.stackCount = 0;
/* 1278 */       frame.local = new Object[maxLocals];
/* 1279 */       frame.stack = new Object[maxStack];
/* 1280 */       if (unzip) {
/* 1281 */         getImplicitFrame(context);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1294 */       for (j = stackMap; j < stackMap + stackMapSize - 2; j++) {
/* 1295 */         if (b[j] == 8) {
/* 1296 */           int v = readUnsignedShort(j + 1);
/* 1297 */           if (v >= 0 && v < codeLength && (
/* 1298 */             b[codeStart + v] & 0xFF) == 187) {
/* 1299 */             readLabel(v, labels);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1305 */     if ((context.flags & 0x100) != 0)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1317 */       mv.visitFrame(-1, maxLocals, null, 0, null);
/*      */     }
/*      */ 
/*      */     
/* 1321 */     int opcodeDelta = ((context.flags & 0x100) == 0) ? -33 : 0;
/* 1322 */     u = codeStart;
/* 1323 */     while (u < codeEnd) {
/* 1324 */       Label target; int label, cpIndex, min, len; boolean itf; int bsmIndex, max, keys[]; String iowner; Handle bsm; Label[] table, values; String iname; int bsmArgCount, k; String idesc; Object[] bsmArgs; int m; String str1, str2; int offset = u - codeStart;
/*      */ 
/*      */       
/* 1327 */       Label l = labels[offset];
/* 1328 */       if (l != null) {
/* 1329 */         Label next = l.next;
/* 1330 */         l.next = null;
/* 1331 */         mv.visitLabel(l);
/* 1332 */         if ((context.flags & 0x2) == 0 && l.line > 0) {
/* 1333 */           mv.visitLineNumber(l.line, l);
/* 1334 */           while (next != null) {
/* 1335 */             mv.visitLineNumber(next.line, l);
/* 1336 */             next = next.next;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1342 */       while (frame != null && (frame.offset == offset || frame.offset == -1)) {
/*      */ 
/*      */ 
/*      */         
/* 1346 */         if (frame.offset != -1) {
/* 1347 */           if (!zip || unzip) {
/* 1348 */             mv.visitFrame(-1, frame.localCount, frame.local, frame.stackCount, frame.stack);
/*      */           } else {
/*      */             
/* 1351 */             mv.visitFrame(frame.mode, frame.localDiff, frame.local, frame.stackCount, frame.stack);
/*      */           } 
/*      */         }
/*      */         
/* 1355 */         if (frameCount > 0) {
/* 1356 */           stackMap = readFrame(stackMap, zip, unzip, frame);
/* 1357 */           frameCount--; continue;
/*      */         } 
/* 1359 */         frame = null;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1364 */       int opcode = b[u] & 0xFF;
/* 1365 */       switch (ClassWriter.TYPE[opcode]) {
/*      */         case 0:
/* 1367 */           mv.visitInsn(opcode);
/* 1368 */           u++;
/*      */           break;
/*      */         case 4:
/* 1371 */           if (opcode > 54) {
/* 1372 */             opcode -= 59;
/* 1373 */             mv.visitVarInsn(54 + (opcode >> 2), opcode & 0x3);
/*      */           } else {
/*      */             
/* 1376 */             opcode -= 26;
/* 1377 */             mv.visitVarInsn(21 + (opcode >> 2), opcode & 0x3);
/*      */           } 
/* 1379 */           u++;
/*      */           break;
/*      */         case 9:
/* 1382 */           mv.visitJumpInsn(opcode, labels[offset + readShort(u + 1)]);
/* 1383 */           u += 3;
/*      */           break;
/*      */         case 10:
/* 1386 */           mv.visitJumpInsn(opcode + opcodeDelta, labels[offset + 
/* 1387 */                 readInt(u + 1)]);
/* 1388 */           u += 5;
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 18:
/* 1394 */           opcode = (opcode < 218) ? (opcode - 49) : (opcode - 20);
/* 1395 */           target = labels[offset + readUnsignedShort(u + 1)];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1401 */           if (opcode == 167 || opcode == 168) {
/* 1402 */             mv.visitJumpInsn(opcode + 33, target);
/*      */           } else {
/* 1404 */             opcode = (opcode <= 166) ? ((opcode + 1 ^ 0x1) - 1) : (opcode ^ 0x1);
/*      */             
/* 1406 */             Label endif = new Label();
/* 1407 */             mv.visitJumpInsn(opcode, endif);
/* 1408 */             mv.visitJumpInsn(200, target);
/* 1409 */             mv.visitLabel(endif);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1414 */             if (stackMap != 0 && (frame == null || frame.offset != offset + 3))
/*      */             {
/* 1416 */               mv.visitFrame(256, 0, null, 0, null);
/*      */             }
/*      */           } 
/* 1419 */           u += 3;
/*      */           break;
/*      */         
/*      */         case 17:
/* 1423 */           opcode = b[u + 1] & 0xFF;
/* 1424 */           if (opcode == 132) {
/* 1425 */             mv.visitIincInsn(readUnsignedShort(u + 2), readShort(u + 4));
/* 1426 */             u += 6; break;
/*      */           } 
/* 1428 */           mv.visitVarInsn(opcode, readUnsignedShort(u + 2));
/* 1429 */           u += 4;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 14:
/* 1434 */           u = u + 4 - (offset & 0x3);
/*      */           
/* 1436 */           label = offset + readInt(u);
/* 1437 */           min = readInt(u + 4);
/* 1438 */           max = readInt(u + 8);
/* 1439 */           table = new Label[max - min + 1];
/* 1440 */           u += 12;
/* 1441 */           for (k = 0; k < table.length; k++) {
/* 1442 */             table[k] = labels[offset + readInt(u)];
/* 1443 */             u += 4;
/*      */           } 
/* 1445 */           mv.visitTableSwitchInsn(min, max, labels[label], table);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 15:
/* 1450 */           u = u + 4 - (offset & 0x3);
/*      */           
/* 1452 */           label = offset + readInt(u);
/* 1453 */           len = readInt(u + 4);
/* 1454 */           keys = new int[len];
/* 1455 */           values = new Label[len];
/* 1456 */           u += 8;
/* 1457 */           for (k = 0; k < len; k++) {
/* 1458 */             keys[k] = readInt(u);
/* 1459 */             values[k] = labels[offset + readInt(u + 4)];
/* 1460 */             u += 8;
/*      */           } 
/* 1462 */           mv.visitLookupSwitchInsn(labels[label], keys, values);
/*      */           break;
/*      */         
/*      */         case 3:
/* 1466 */           mv.visitVarInsn(opcode, b[u + 1] & 0xFF);
/* 1467 */           u += 2;
/*      */           break;
/*      */         case 1:
/* 1470 */           mv.visitIntInsn(opcode, b[u + 1]);
/* 1471 */           u += 2;
/*      */           break;
/*      */         case 2:
/* 1474 */           mv.visitIntInsn(opcode, readShort(u + 1));
/* 1475 */           u += 3;
/*      */           break;
/*      */         case 11:
/* 1478 */           mv.visitLdcInsn(readConst(b[u + 1] & 0xFF, c));
/* 1479 */           u += 2;
/*      */           break;
/*      */         case 12:
/* 1482 */           mv.visitLdcInsn(readConst(readUnsignedShort(u + 1), c));
/* 1483 */           u += 3;
/*      */           break;
/*      */         case 6:
/*      */         case 7:
/* 1487 */           cpIndex = this.items[readUnsignedShort(u + 1)];
/* 1488 */           itf = (b[cpIndex - 1] == 11);
/* 1489 */           iowner = readClass(cpIndex, c);
/* 1490 */           cpIndex = this.items[readUnsignedShort(cpIndex + 2)];
/* 1491 */           iname = readUTF8(cpIndex, c);
/* 1492 */           idesc = readUTF8(cpIndex + 2, c);
/* 1493 */           if (opcode < 182) {
/* 1494 */             mv.visitFieldInsn(opcode, iowner, iname, idesc);
/*      */           } else {
/* 1496 */             mv.visitMethodInsn(opcode, iowner, iname, idesc, itf);
/*      */           } 
/* 1498 */           if (opcode == 185) {
/* 1499 */             u += 5; break;
/*      */           } 
/* 1501 */           u += 3;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 8:
/* 1506 */           cpIndex = this.items[readUnsignedShort(u + 1)];
/* 1507 */           bsmIndex = context.bootstrapMethods[readUnsignedShort(cpIndex)];
/* 1508 */           bsm = (Handle)readConst(readUnsignedShort(bsmIndex), c);
/* 1509 */           bsmArgCount = readUnsignedShort(bsmIndex + 2);
/* 1510 */           bsmArgs = new Object[bsmArgCount];
/* 1511 */           bsmIndex += 4;
/* 1512 */           for (m = 0; m < bsmArgCount; m++) {
/* 1513 */             bsmArgs[m] = readConst(readUnsignedShort(bsmIndex), c);
/* 1514 */             bsmIndex += 2;
/*      */           } 
/* 1516 */           cpIndex = this.items[readUnsignedShort(cpIndex + 2)];
/* 1517 */           str1 = readUTF8(cpIndex, c);
/* 1518 */           str2 = readUTF8(cpIndex + 2, c);
/* 1519 */           mv.visitInvokeDynamicInsn(str1, str2, bsm, bsmArgs);
/* 1520 */           u += 5;
/*      */           break;
/*      */         
/*      */         case 5:
/* 1524 */           mv.visitTypeInsn(opcode, readClass(u + 1, c));
/* 1525 */           u += 3;
/*      */           break;
/*      */         case 13:
/* 1528 */           mv.visitIincInsn(b[u + 1] & 0xFF, b[u + 2]);
/* 1529 */           u += 3;
/*      */           break;
/*      */         
/*      */         default:
/* 1533 */           mv.visitMultiANewArrayInsn(readClass(u + 1, c), b[u + 3] & 0xFF);
/* 1534 */           u += 4;
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/* 1539 */       while (tanns != null && tann < tanns.length && ntoff <= offset) {
/* 1540 */         if (ntoff == offset) {
/* 1541 */           int v = readAnnotationTarget(context, tanns[tann]);
/* 1542 */           readAnnotationValues(v + 2, c, true, mv
/* 1543 */               .visitInsnAnnotation(context.typeRef, context.typePath, 
/* 1544 */                 readUTF8(v, c), true));
/*      */         } 
/*      */         
/* 1547 */         ntoff = (++tann >= tanns.length || readByte(tanns[tann]) < 67) ? -1 : readUnsignedShort(tanns[tann] + 1);
/*      */       } 
/* 1549 */       while (itanns != null && itann < itanns.length && nitoff <= offset) {
/* 1550 */         if (nitoff == offset) {
/* 1551 */           int v = readAnnotationTarget(context, itanns[itann]);
/* 1552 */           readAnnotationValues(v + 2, c, true, mv
/* 1553 */               .visitInsnAnnotation(context.typeRef, context.typePath, 
/* 1554 */                 readUTF8(v, c), false));
/*      */         } 
/*      */ 
/*      */         
/* 1558 */         nitoff = (++itann >= itanns.length || readByte(itanns[itann]) < 67) ? -1 : readUnsignedShort(itanns[itann] + 1);
/*      */       } 
/*      */     } 
/* 1561 */     if (labels[codeLength] != null) {
/* 1562 */       mv.visitLabel(labels[codeLength]);
/*      */     }
/*      */ 
/*      */     
/* 1566 */     if ((context.flags & 0x2) == 0 && varTable != 0) {
/* 1567 */       int[] typeTable = null;
/* 1568 */       if (varTypeTable != 0) {
/* 1569 */         u = varTypeTable + 2;
/* 1570 */         typeTable = new int[readUnsignedShort(varTypeTable) * 3];
/* 1571 */         for (int m = typeTable.length; m > 0; ) {
/* 1572 */           typeTable[--m] = u + 6;
/* 1573 */           typeTable[--m] = readUnsignedShort(u + 8);
/* 1574 */           typeTable[--m] = readUnsignedShort(u);
/* 1575 */           u += 10;
/*      */         } 
/*      */       } 
/* 1578 */       u = varTable + 2;
/* 1579 */       for (int k = readUnsignedShort(varTable); k > 0; k--) {
/* 1580 */         int start = readUnsignedShort(u);
/* 1581 */         int length = readUnsignedShort(u + 2);
/* 1582 */         int index = readUnsignedShort(u + 8);
/* 1583 */         String vsignature = null;
/* 1584 */         if (typeTable != null) {
/* 1585 */           for (int m = 0; m < typeTable.length; m += 3) {
/* 1586 */             if (typeTable[m] == start && typeTable[m + 1] == index) {
/* 1587 */               vsignature = readUTF8(typeTable[m + 2], c);
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/* 1592 */         mv.visitLocalVariable(readUTF8(u + 4, c), readUTF8(u + 6, c), vsignature, labels[start], labels[start + length], index);
/*      */ 
/*      */         
/* 1595 */         u += 10;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1600 */     if (tanns != null) {
/* 1601 */       for (int k = 0; k < tanns.length; k++) {
/* 1602 */         if (readByte(tanns[k]) >> 1 == 32) {
/* 1603 */           int v = readAnnotationTarget(context, tanns[k]);
/* 1604 */           v = readAnnotationValues(v + 2, c, true, mv
/* 1605 */               .visitLocalVariableAnnotation(context.typeRef, context.typePath, context.start, context.end, context.index, 
/*      */                 
/* 1607 */                 readUTF8(v, c), true));
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1612 */     if (itanns != null) {
/* 1613 */       for (int k = 0; k < itanns.length; k++) {
/* 1614 */         if (readByte(itanns[k]) >> 1 == 32) {
/* 1615 */           int v = readAnnotationTarget(context, itanns[k]);
/* 1616 */           v = readAnnotationValues(v + 2, c, true, mv
/* 1617 */               .visitLocalVariableAnnotation(context.typeRef, context.typePath, context.start, context.end, context.index, 
/*      */                 
/* 1619 */                 readUTF8(v, c), false));
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1626 */     while (attributes != null) {
/* 1627 */       Attribute attr = attributes.next;
/* 1628 */       attributes.next = null;
/* 1629 */       mv.visitAttribute(attributes);
/* 1630 */       attributes = attr;
/*      */     } 
/*      */ 
/*      */     
/* 1634 */     mv.visitMaxs(maxStack, maxLocals);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] readTypeAnnotations(MethodVisitor mv, Context context, int u, boolean visible) {
/* 1655 */     char[] c = context.buffer;
/* 1656 */     int[] offsets = new int[readUnsignedShort(u)];
/* 1657 */     u += 2;
/* 1658 */     for (int i = 0; i < offsets.length; i++) {
/* 1659 */       int j; offsets[i] = u;
/* 1660 */       int target = readInt(u);
/* 1661 */       switch (target >>> 24) {
/*      */         case 0:
/*      */         case 1:
/*      */         case 22:
/* 1665 */           u += 2;
/*      */           break;
/*      */         case 19:
/*      */         case 20:
/*      */         case 21:
/* 1670 */           u++;
/*      */           break;
/*      */         case 64:
/*      */         case 65:
/* 1674 */           for (j = readUnsignedShort(u + 1); j > 0; j--) {
/* 1675 */             int start = readUnsignedShort(u + 3);
/* 1676 */             int length = readUnsignedShort(u + 5);
/* 1677 */             readLabel(start, context.labels);
/* 1678 */             readLabel(start + length, context.labels);
/* 1679 */             u += 6;
/*      */           } 
/* 1681 */           u += 3;
/*      */           break;
/*      */         case 71:
/*      */         case 72:
/*      */         case 73:
/*      */         case 74:
/*      */         case 75:
/* 1688 */           u += 4;
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         default:
/* 1700 */           u += 3;
/*      */           break;
/*      */       } 
/* 1703 */       int pathLength = readByte(u);
/* 1704 */       if (target >>> 24 == 66) {
/* 1705 */         TypePath path = (pathLength == 0) ? null : new TypePath(this.b, u);
/* 1706 */         u += 1 + 2 * pathLength;
/* 1707 */         u = readAnnotationValues(u + 2, c, true, mv
/* 1708 */             .visitTryCatchAnnotation(target, path, 
/* 1709 */               readUTF8(u, c), visible));
/*      */       } else {
/* 1711 */         u = readAnnotationValues(u + 3 + 2 * pathLength, c, true, null);
/*      */       } 
/*      */     } 
/* 1714 */     return offsets;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int readAnnotationTarget(Context context, int u) {
/* 1732 */     int n, i, target = readInt(u);
/* 1733 */     switch (target >>> 24) {
/*      */       case 0:
/*      */       case 1:
/*      */       case 22:
/* 1737 */         target &= 0xFFFF0000;
/* 1738 */         u += 2;
/*      */         break;
/*      */       case 19:
/*      */       case 20:
/*      */       case 21:
/* 1743 */         target &= 0xFF000000;
/* 1744 */         u++;
/*      */         break;
/*      */       case 64:
/*      */       case 65:
/* 1748 */         target &= 0xFF000000;
/* 1749 */         n = readUnsignedShort(u + 1);
/* 1750 */         context.start = new Label[n];
/* 1751 */         context.end = new Label[n];
/* 1752 */         context.index = new int[n];
/* 1753 */         u += 3;
/* 1754 */         for (i = 0; i < n; i++) {
/* 1755 */           int start = readUnsignedShort(u);
/* 1756 */           int length = readUnsignedShort(u + 2);
/* 1757 */           context.start[i] = readLabel(start, context.labels);
/* 1758 */           context.end[i] = readLabel(start + length, context.labels);
/* 1759 */           context.index[i] = readUnsignedShort(u + 4);
/* 1760 */           u += 6;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 71:
/*      */       case 72:
/*      */       case 73:
/*      */       case 74:
/*      */       case 75:
/* 1769 */         target &= 0xFF0000FF;
/* 1770 */         u += 4;
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       default:
/* 1782 */         target &= (target >>> 24 < 67) ? -256 : -16777216;
/* 1783 */         u += 3;
/*      */         break;
/*      */     } 
/* 1786 */     int pathLength = readByte(u);
/* 1787 */     context.typeRef = target;
/* 1788 */     context.typePath = (pathLength == 0) ? null : new TypePath(this.b, u);
/* 1789 */     return u + 1 + 2 * pathLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readParameterAnnotations(MethodVisitor mv, Context context, int v, boolean visible) {
/* 1808 */     int n = this.b[v++] & 0xFF;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1815 */     int synthetics = (Type.getArgumentTypes(context.desc)).length - n;
/*      */     int i;
/* 1817 */     for (i = 0; i < synthetics; i++) {
/*      */       
/* 1819 */       AnnotationVisitor av = mv.visitParameterAnnotation(i, "Ljava/lang/Synthetic;", false);
/* 1820 */       if (av != null) {
/* 1821 */         av.visitEnd();
/*      */       }
/*      */     } 
/* 1824 */     char[] c = context.buffer;
/* 1825 */     for (; i < n + synthetics; i++) {
/* 1826 */       int j = readUnsignedShort(v);
/* 1827 */       v += 2;
/* 1828 */       for (; j > 0; j--) {
/* 1829 */         AnnotationVisitor av = mv.visitParameterAnnotation(i, readUTF8(v, c), visible);
/* 1830 */         v = readAnnotationValues(v + 2, c, true, av);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int readAnnotationValues(int v, char[] buf, boolean named, AnnotationVisitor av) {
/* 1854 */     int i = readUnsignedShort(v);
/* 1855 */     v += 2;
/* 1856 */     if (named) {
/* 1857 */       for (; i > 0; i--) {
/* 1858 */         v = readAnnotationValue(v + 2, buf, readUTF8(v, buf), av);
/*      */       }
/*      */     } else {
/* 1861 */       for (; i > 0; i--) {
/* 1862 */         v = readAnnotationValue(v, buf, null, av);
/*      */       }
/*      */     } 
/* 1865 */     if (av != null) {
/* 1866 */       av.visitEnd();
/*      */     }
/* 1868 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int readAnnotationValue(int v, char[] buf, String name, AnnotationVisitor av) {
/*      */     int size;
/*      */     byte[] bv;
/*      */     int i;
/*      */     boolean[] zv;
/*      */     short[] sv;
/*      */     char[] cv;
/*      */     int[] iv;
/*      */     long[] lv;
/*      */     float[] fv;
/*      */     double[] dv;
/* 1890 */     if (av == null) {
/* 1891 */       switch (this.b[v] & 0xFF) {
/*      */         case 101:
/* 1893 */           return v + 5;
/*      */         case 64:
/* 1895 */           return readAnnotationValues(v + 3, buf, true, null);
/*      */         case 91:
/* 1897 */           return readAnnotationValues(v + 1, buf, false, null);
/*      */       } 
/* 1899 */       return v + 3;
/*      */     } 
/*      */     
/* 1902 */     switch (this.b[v++] & 0xFF) {
/*      */       case 68:
/*      */       case 70:
/*      */       case 73:
/*      */       case 74:
/* 1907 */         av.visit(name, readConst(readUnsignedShort(v), buf));
/* 1908 */         v += 2;
/*      */         break;
/*      */       case 66:
/* 1911 */         av.visit(name, Byte.valueOf((byte)readInt(this.items[readUnsignedShort(v)])));
/* 1912 */         v += 2;
/*      */         break;
/*      */       case 90:
/* 1915 */         av.visit(name, 
/* 1916 */             (readInt(this.items[readUnsignedShort(v)]) == 0) ? Boolean.FALSE : Boolean.TRUE);
/*      */         
/* 1918 */         v += 2;
/*      */         break;
/*      */       case 83:
/* 1921 */         av.visit(name, Short.valueOf((short)readInt(this.items[readUnsignedShort(v)])));
/* 1922 */         v += 2;
/*      */         break;
/*      */       case 67:
/* 1925 */         av.visit(name, Character.valueOf((char)readInt(this.items[readUnsignedShort(v)])));
/* 1926 */         v += 2;
/*      */         break;
/*      */       case 115:
/* 1929 */         av.visit(name, readUTF8(v, buf));
/* 1930 */         v += 2;
/*      */         break;
/*      */       case 101:
/* 1933 */         av.visitEnum(name, readUTF8(v, buf), readUTF8(v + 2, buf));
/* 1934 */         v += 4;
/*      */         break;
/*      */       case 99:
/* 1937 */         av.visit(name, Type.getType(readUTF8(v, buf)));
/* 1938 */         v += 2;
/*      */         break;
/*      */       case 64:
/* 1941 */         v = readAnnotationValues(v + 2, buf, true, av
/* 1942 */             .visitAnnotation(name, readUTF8(v, buf)));
/*      */         break;
/*      */       case 91:
/* 1945 */         size = readUnsignedShort(v);
/* 1946 */         v += 2;
/* 1947 */         if (size == 0) {
/* 1948 */           return readAnnotationValues(v - 2, buf, false, av
/* 1949 */               .visitArray(name));
/*      */         }
/* 1951 */         switch (this.b[v++] & 0xFF) {
/*      */           case 66:
/* 1953 */             bv = new byte[size];
/* 1954 */             for (i = 0; i < size; i++) {
/* 1955 */               bv[i] = (byte)readInt(this.items[readUnsignedShort(v)]);
/* 1956 */               v += 3;
/*      */             } 
/* 1958 */             av.visit(name, bv);
/* 1959 */             v--;
/*      */             break;
/*      */           case 90:
/* 1962 */             zv = new boolean[size];
/* 1963 */             for (i = 0; i < size; i++) {
/* 1964 */               zv[i] = (readInt(this.items[readUnsignedShort(v)]) != 0);
/* 1965 */               v += 3;
/*      */             } 
/* 1967 */             av.visit(name, zv);
/* 1968 */             v--;
/*      */             break;
/*      */           case 83:
/* 1971 */             sv = new short[size];
/* 1972 */             for (i = 0; i < size; i++) {
/* 1973 */               sv[i] = (short)readInt(this.items[readUnsignedShort(v)]);
/* 1974 */               v += 3;
/*      */             } 
/* 1976 */             av.visit(name, sv);
/* 1977 */             v--;
/*      */             break;
/*      */           case 67:
/* 1980 */             cv = new char[size];
/* 1981 */             for (i = 0; i < size; i++) {
/* 1982 */               cv[i] = (char)readInt(this.items[readUnsignedShort(v)]);
/* 1983 */               v += 3;
/*      */             } 
/* 1985 */             av.visit(name, cv);
/* 1986 */             v--;
/*      */             break;
/*      */           case 73:
/* 1989 */             iv = new int[size];
/* 1990 */             for (i = 0; i < size; i++) {
/* 1991 */               iv[i] = readInt(this.items[readUnsignedShort(v)]);
/* 1992 */               v += 3;
/*      */             } 
/* 1994 */             av.visit(name, iv);
/* 1995 */             v--;
/*      */             break;
/*      */           case 74:
/* 1998 */             lv = new long[size];
/* 1999 */             for (i = 0; i < size; i++) {
/* 2000 */               lv[i] = readLong(this.items[readUnsignedShort(v)]);
/* 2001 */               v += 3;
/*      */             } 
/* 2003 */             av.visit(name, lv);
/* 2004 */             v--;
/*      */             break;
/*      */           case 70:
/* 2007 */             fv = new float[size];
/* 2008 */             for (i = 0; i < size; i++) {
/* 2009 */               fv[i] = 
/* 2010 */                 Float.intBitsToFloat(readInt(this.items[readUnsignedShort(v)]));
/* 2011 */               v += 3;
/*      */             } 
/* 2013 */             av.visit(name, fv);
/* 2014 */             v--;
/*      */             break;
/*      */           case 68:
/* 2017 */             dv = new double[size];
/* 2018 */             for (i = 0; i < size; i++) {
/* 2019 */               dv[i] = 
/* 2020 */                 Double.longBitsToDouble(readLong(this.items[readUnsignedShort(v)]));
/* 2021 */               v += 3;
/*      */             } 
/* 2023 */             av.visit(name, dv);
/* 2024 */             v--;
/*      */             break;
/*      */         } 
/* 2027 */         v = readAnnotationValues(v - 3, buf, false, av.visitArray(name));
/*      */         break;
/*      */     } 
/* 2030 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getImplicitFrame(Context frame) {
/* 2041 */     String desc = frame.desc;
/* 2042 */     Object[] locals = frame.local;
/* 2043 */     int local = 0;
/* 2044 */     if ((frame.access & 0x8) == 0) {
/* 2045 */       if ("<init>".equals(frame.name)) {
/* 2046 */         locals[local++] = Opcodes.UNINITIALIZED_THIS;
/*      */       } else {
/* 2048 */         locals[local++] = readClass(this.header + 2, frame.buffer);
/*      */       } 
/*      */     }
/* 2051 */     int i = 1;
/*      */     while (true) {
/* 2053 */       int j = i;
/* 2054 */       switch (desc.charAt(i++)) {
/*      */         case 'B':
/*      */         case 'C':
/*      */         case 'I':
/*      */         case 'S':
/*      */         case 'Z':
/* 2060 */           locals[local++] = Opcodes.INTEGER;
/*      */           continue;
/*      */         case 'F':
/* 2063 */           locals[local++] = Opcodes.FLOAT;
/*      */           continue;
/*      */         case 'J':
/* 2066 */           locals[local++] = Opcodes.LONG;
/*      */           continue;
/*      */         case 'D':
/* 2069 */           locals[local++] = Opcodes.DOUBLE;
/*      */           continue;
/*      */         case '[':
/* 2072 */           while (desc.charAt(i) == '[') {
/* 2073 */             i++;
/*      */           }
/* 2075 */           if (desc.charAt(i) == 'L') {
/* 2076 */             i++;
/* 2077 */             while (desc.charAt(i) != ';') {
/* 2078 */               i++;
/*      */             }
/*      */           } 
/* 2081 */           locals[local++] = desc.substring(j, ++i);
/*      */           continue;
/*      */         case 'L':
/* 2084 */           while (desc.charAt(i) != ';') {
/* 2085 */             i++;
/*      */           }
/* 2087 */           locals[local++] = desc.substring(j + 1, i++);
/*      */           continue;
/*      */       } 
/*      */       
/*      */       break;
/*      */     } 
/* 2093 */     frame.localCount = local;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int readFrame(int stackMap, boolean zip, boolean unzip, Context frame) {
/*      */     int tag, delta;
/* 2112 */     char[] c = frame.buffer;
/* 2113 */     Label[] labels = frame.labels;
/*      */ 
/*      */     
/* 2116 */     if (zip) {
/* 2117 */       tag = this.b[stackMap++] & 0xFF;
/*      */     } else {
/* 2119 */       tag = 255;
/* 2120 */       frame.offset = -1;
/*      */     } 
/* 2122 */     frame.localDiff = 0;
/* 2123 */     if (tag < 64) {
/* 2124 */       delta = tag;
/* 2125 */       frame.mode = 3;
/* 2126 */       frame.stackCount = 0;
/* 2127 */     } else if (tag < 128) {
/* 2128 */       delta = tag - 64;
/* 2129 */       stackMap = readFrameType(frame.stack, 0, stackMap, c, labels);
/* 2130 */       frame.mode = 4;
/* 2131 */       frame.stackCount = 1;
/*      */     } else {
/* 2133 */       delta = readUnsignedShort(stackMap);
/* 2134 */       stackMap += 2;
/* 2135 */       if (tag == 247) {
/* 2136 */         stackMap = readFrameType(frame.stack, 0, stackMap, c, labels);
/* 2137 */         frame.mode = 4;
/* 2138 */         frame.stackCount = 1;
/* 2139 */       } else if (tag >= 248 && tag < 251) {
/*      */         
/* 2141 */         frame.mode = 2;
/* 2142 */         frame.localDiff = 251 - tag;
/* 2143 */         frame.localCount -= frame.localDiff;
/* 2144 */         frame.stackCount = 0;
/* 2145 */       } else if (tag == 251) {
/* 2146 */         frame.mode = 3;
/* 2147 */         frame.stackCount = 0;
/* 2148 */       } else if (tag < 255) {
/* 2149 */         int local = unzip ? frame.localCount : 0;
/* 2150 */         for (int i = tag - 251; i > 0; i--) {
/* 2151 */           stackMap = readFrameType(frame.local, local++, stackMap, c, labels);
/*      */         }
/*      */         
/* 2154 */         frame.mode = 1;
/* 2155 */         frame.localDiff = tag - 251;
/* 2156 */         frame.localCount += frame.localDiff;
/* 2157 */         frame.stackCount = 0;
/*      */       } else {
/* 2159 */         frame.mode = 0;
/* 2160 */         int n = readUnsignedShort(stackMap);
/* 2161 */         stackMap += 2;
/* 2162 */         frame.localDiff = n;
/* 2163 */         frame.localCount = n;
/* 2164 */         for (int local = 0; n > 0; n--) {
/* 2165 */           stackMap = readFrameType(frame.local, local++, stackMap, c, labels);
/*      */         }
/*      */         
/* 2168 */         n = readUnsignedShort(stackMap);
/* 2169 */         stackMap += 2;
/* 2170 */         frame.stackCount = n;
/* 2171 */         for (int stack = 0; n > 0; n--) {
/* 2172 */           stackMap = readFrameType(frame.stack, stack++, stackMap, c, labels);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2177 */     frame.offset += delta + 1;
/* 2178 */     readLabel(frame.offset, labels);
/* 2179 */     return stackMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int readFrameType(Object[] frame, int index, int v, char[] buf, Label[] labels) {
/* 2203 */     int type = this.b[v++] & 0xFF;
/* 2204 */     switch (type)
/*      */     { case 0:
/* 2206 */         frame[index] = Opcodes.TOP;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2234 */         return v;case 1: frame[index] = Opcodes.INTEGER; return v;case 2: frame[index] = Opcodes.FLOAT; return v;case 3: frame[index] = Opcodes.DOUBLE; return v;case 4: frame[index] = Opcodes.LONG; return v;case 5: frame[index] = Opcodes.NULL; return v;case 6: frame[index] = Opcodes.UNINITIALIZED_THIS; return v;case 7: frame[index] = readClass(v, buf); v += 2; return v; }  frame[index] = readLabel(readUnsignedShort(v), labels); v += 2; return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Label readLabel(int offset, Label[] labels) {
/* 2251 */     if (labels[offset] == null) {
/* 2252 */       labels[offset] = new Label();
/*      */     }
/* 2254 */     return labels[offset];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getAttributes() {
/* 2264 */     int u = this.header + 8 + readUnsignedShort(this.header + 6) * 2;
/*      */     int i;
/* 2266 */     for (i = readUnsignedShort(u); i > 0; i--) {
/* 2267 */       for (int j = readUnsignedShort(u + 8); j > 0; j--) {
/* 2268 */         u += 6 + readInt(u + 12);
/*      */       }
/* 2270 */       u += 8;
/*      */     } 
/* 2272 */     u += 2;
/* 2273 */     for (i = readUnsignedShort(u); i > 0; i--) {
/* 2274 */       for (int j = readUnsignedShort(u + 8); j > 0; j--) {
/* 2275 */         u += 6 + readInt(u + 12);
/*      */       }
/* 2277 */       u += 8;
/*      */     } 
/*      */     
/* 2280 */     return u + 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Attribute readAttribute(Attribute[] attrs, String type, int off, int len, char[] buf, int codeOff, Label[] labels) {
/* 2319 */     for (int i = 0; i < attrs.length; i++) {
/* 2320 */       if ((attrs[i]).type.equals(type)) {
/* 2321 */         return attrs[i].read(this, off, len, buf, codeOff, labels);
/*      */       }
/*      */     } 
/* 2324 */     return (new Attribute(type)).read(this, off, len, null, -1, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemCount() {
/* 2337 */     return this.items.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItem(int item) {
/* 2351 */     return this.items[item];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxStringLength() {
/* 2362 */     return this.maxStringLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int readByte(int index) {
/* 2375 */     return this.b[index] & 0xFF;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int readUnsignedShort(int index) {
/* 2388 */     byte[] b = this.b;
/* 2389 */     return (b[index] & 0xFF) << 8 | b[index + 1] & 0xFF;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short readShort(int index) {
/* 2402 */     byte[] b = this.b;
/* 2403 */     return (short)((b[index] & 0xFF) << 8 | b[index + 1] & 0xFF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int readInt(int index) {
/* 2416 */     byte[] b = this.b;
/* 2417 */     return (b[index] & 0xFF) << 24 | (b[index + 1] & 0xFF) << 16 | (b[index + 2] & 0xFF) << 8 | b[index + 3] & 0xFF;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long readLong(int index) {
/* 2431 */     long l1 = readInt(index);
/* 2432 */     long l0 = readInt(index + 4) & 0xFFFFFFFFL;
/* 2433 */     return l1 << 32L | l0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String readUTF8(int index, char[] buf) {
/* 2450 */     int item = readUnsignedShort(index);
/* 2451 */     if (index == 0 || item == 0) {
/* 2452 */       return null;
/*      */     }
/* 2454 */     String s = this.strings[item];
/* 2455 */     if (s != null) {
/* 2456 */       return s;
/*      */     }
/* 2458 */     index = this.items[item];
/* 2459 */     this.strings[item] = readUTF(index + 2, readUnsignedShort(index), buf); return readUTF(index + 2, readUnsignedShort(index), buf);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String readUTF(int index, int utfLen, char[] buf) {
/* 2475 */     int endIndex = index + utfLen;
/* 2476 */     byte[] b = this.b;
/* 2477 */     int strLen = 0;
/*      */     
/* 2479 */     int st = 0;
/* 2480 */     char cc = Character.MIN_VALUE;
/* 2481 */     while (index < endIndex) {
/* 2482 */       int c = b[index++];
/* 2483 */       switch (st) {
/*      */         case 0:
/* 2485 */           c &= 0xFF;
/* 2486 */           if (c < 128) {
/* 2487 */             buf[strLen++] = (char)c; continue;
/* 2488 */           }  if (c < 224 && c > 191) {
/* 2489 */             cc = (char)(c & 0x1F);
/* 2490 */             st = 1; continue;
/*      */           } 
/* 2492 */           cc = (char)(c & 0xF);
/* 2493 */           st = 2;
/*      */ 
/*      */ 
/*      */         
/*      */         case 1:
/* 2498 */           buf[strLen++] = (char)(cc << 6 | c & 0x3F);
/* 2499 */           st = 0;
/*      */ 
/*      */         
/*      */         case 2:
/* 2503 */           cc = (char)(cc << 6 | c & 0x3F);
/* 2504 */           st = 1;
/*      */       } 
/*      */     
/*      */     } 
/* 2508 */     return new String(buf, 0, strLen);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String readClass(int index, char[] buf) {
/* 2528 */     return readUTF8(this.items[readUnsignedShort(index)], buf);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object readConst(int item, char[] buf) {
/* 2546 */     int index = this.items[item];
/* 2547 */     switch (this.b[index - 1]) {
/*      */       case 3:
/* 2549 */         return Integer.valueOf(readInt(index));
/*      */       case 4:
/* 2551 */         return Float.valueOf(Float.intBitsToFloat(readInt(index)));
/*      */       case 5:
/* 2553 */         return Long.valueOf(readLong(index));
/*      */       case 6:
/* 2555 */         return Double.valueOf(Double.longBitsToDouble(readLong(index)));
/*      */       case 7:
/* 2557 */         return Type.getObjectType(readUTF8(index, buf));
/*      */       case 8:
/* 2559 */         return readUTF8(index, buf);
/*      */       case 16:
/* 2561 */         return Type.getMethodType(readUTF8(index, buf));
/*      */     } 
/* 2563 */     int tag = readByte(index);
/* 2564 */     int[] items = this.items;
/* 2565 */     int cpIndex = items[readUnsignedShort(index + 1)];
/* 2566 */     boolean itf = (this.b[cpIndex - 1] == 11);
/* 2567 */     String owner = readClass(cpIndex, buf);
/* 2568 */     cpIndex = items[readUnsignedShort(cpIndex + 2)];
/* 2569 */     String name = readUTF8(cpIndex, buf);
/* 2570 */     String desc = readUTF8(cpIndex + 2, buf);
/* 2571 */     return new Handle(tag, owner, name, desc, itf);
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\ClassReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */