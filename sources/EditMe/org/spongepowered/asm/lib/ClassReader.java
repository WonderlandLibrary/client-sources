package org.spongepowered.asm.lib;

import java.io.IOException;
import java.io.InputStream;

public class ClassReader {
   static final boolean SIGNATURES = true;
   static final boolean ANNOTATIONS = true;
   static final boolean FRAMES = true;
   static final boolean WRITER = true;
   static final boolean RESIZE = true;
   public static final int SKIP_CODE = 1;
   public static final int SKIP_DEBUG = 2;
   public static final int SKIP_FRAMES = 4;
   public static final int EXPAND_FRAMES = 8;
   static final int EXPAND_ASM_INSNS = 256;
   public final byte[] b;
   private final int[] items;
   private final String[] strings;
   private final int maxStringLength;
   public final int header;

   public ClassReader(byte[] var1) {
      this(var1, 0, var1.length);
   }

   public ClassReader(byte[] var1, int var2, int var3) {
      this.b = var1;
      if (this.readShort(var2 + 6) > 52) {
         throw new IllegalArgumentException();
      } else {
         this.items = new int[this.readUnsignedShort(var2 + 8)];
         int var4 = this.items.length;
         this.strings = new String[var4];
         int var5 = 0;
         int var6 = var2 + 10;

         for(int var7 = 1; var7 < var4; ++var7) {
            this.items[var7] = var6 + 1;
            int var8;
            switch(var1[var6]) {
            case 1:
               var8 = 3 + this.readUnsignedShort(var6 + 1);
               if (var8 > var5) {
                  var5 = var8;
               }
               break;
            case 2:
            case 7:
            case 8:
            case 13:
            case 14:
            case 16:
            case 17:
            default:
               var8 = 3;
               break;
            case 3:
            case 4:
            case 9:
            case 10:
            case 11:
            case 12:
            case 18:
               var8 = 5;
               break;
            case 5:
            case 6:
               var8 = 9;
               ++var7;
               break;
            case 15:
               var8 = 4;
            }

            var6 += var8;
         }

         this.maxStringLength = var5;
         this.header = var6;
      }
   }

   public int getAccess() {
      return this.readUnsignedShort(this.header);
   }

   public String getClassName() {
      return this.readClass(this.header + 2, new char[this.maxStringLength]);
   }

   public String getSuperName() {
      return this.readClass(this.header + 4, new char[this.maxStringLength]);
   }

   public String[] getInterfaces() {
      int var1 = this.header + 6;
      int var2 = this.readUnsignedShort(var1);
      String[] var3 = new String[var2];
      if (var2 > 0) {
         char[] var4 = new char[this.maxStringLength];

         for(int var5 = 0; var5 < var2; ++var5) {
            var1 += 2;
            var3[var5] = this.readClass(var1, var4);
         }
      }

      return var3;
   }

   void copyPool(ClassWriter var1) {
      char[] var2 = new char[this.maxStringLength];
      int var3 = this.items.length;
      Item[] var4 = new Item[var3];

      int var5;
      for(var5 = 1; var5 < var3; ++var5) {
         int var6 = this.items[var5];
         byte var7 = this.b[var6 - 1];
         Item var8 = new Item(var5);
         int var9;
         int var10;
         switch(var7) {
         case 1:
            String var11 = this.strings[var5];
            if (var11 == null) {
               var6 = this.items[var5];
               var11 = this.strings[var5] = this.readUTF(var6 + 2, this.readUnsignedShort(var6), var2);
            }

            var8.set(var7, var11, (String)null, (String)null);
            break;
         case 2:
         case 7:
         case 8:
         case 13:
         case 14:
         case 16:
         case 17:
         default:
            var8.set(var7, this.readUTF8(var6, var2), (String)null, (String)null);
            break;
         case 3:
            var8.set(this.readInt(var6));
            break;
         case 4:
            var8.set(Float.intBitsToFloat(this.readInt(var6)));
            break;
         case 5:
            var8.set(this.readLong(var6));
            ++var5;
            break;
         case 6:
            var8.set(Double.longBitsToDouble(this.readLong(var6)));
            ++var5;
            break;
         case 9:
         case 10:
         case 11:
            var9 = this.items[this.readUnsignedShort(var6 + 2)];
            var8.set(var7, this.readClass(var6, var2), this.readUTF8(var9, var2), this.readUTF8(var9 + 2, var2));
            break;
         case 12:
            var8.set(var7, this.readUTF8(var6, var2), this.readUTF8(var6 + 2, var2), (String)null);
            break;
         case 15:
            var10 = this.items[this.readUnsignedShort(var6 + 1)];
            var9 = this.items[this.readUnsignedShort(var10 + 2)];
            var8.set(20 + this.readByte(var6), this.readClass(var10, var2), this.readUTF8(var9, var2), this.readUTF8(var9 + 2, var2));
            break;
         case 18:
            if (var1.bootstrapMethods == null) {
               this.copyBootstrapMethods(var1, var4, var2);
            }

            var9 = this.items[this.readUnsignedShort(var6 + 2)];
            var8.set(this.readUTF8(var9, var2), this.readUTF8(var9 + 2, var2), this.readUnsignedShort(var6));
         }

         var10 = var8.hashCode % var4.length;
         var8.next = var4[var10];
         var4[var10] = var8;
      }

      var5 = this.items[1] - 1;
      var1.pool.putByteArray(this.b, var5, this.header - var5);
      var1.items = var4;
      var1.threshold = (int)(0.75D * (double)var3);
      var1.index = var3;
   }

   private void copyBootstrapMethods(ClassWriter var1, Item[] var2, char[] var3) {
      int var4 = this.getAttributes();
      boolean var5 = false;

      int var6;
      for(var6 = this.readUnsignedShort(var4); var6 > 0; --var6) {
         String var7 = this.readUTF8(var4 + 2, var3);
         if ("BootstrapMethods".equals(var7)) {
            var5 = true;
            break;
         }

         var4 += 6 + this.readInt(var4 + 4);
      }

      if (var5) {
         var6 = this.readUnsignedShort(var4 + 8);
         int var13 = 0;

         for(int var8 = var4 + 10; var13 < var6; ++var13) {
            int var9 = var8 - var4 - 10;
            int var10 = this.readConst(this.readUnsignedShort(var8), var3).hashCode();

            for(int var11 = this.readUnsignedShort(var8 + 2); var11 > 0; --var11) {
               var10 ^= this.readConst(this.readUnsignedShort(var8 + 4), var3).hashCode();
               var8 += 2;
            }

            var8 += 4;
            Item var15 = new Item(var13);
            var15.set(var9, var10 & Integer.MAX_VALUE);
            int var12 = var15.hashCode % var2.length;
            var15.next = var2[var12];
            var2[var12] = var15;
         }

         var13 = this.readInt(var4 + 4);
         ByteVector var14 = new ByteVector(var13 + 62);
         var14.putByteArray(this.b, var4 + 10, var13 - 2);
         var1.bootstrapMethodsCount = var6;
         var1.bootstrapMethods = var14;
      }
   }

   public ClassReader(InputStream var1) throws IOException {
      this(readClass(var1, false));
   }

   public ClassReader(String var1) throws IOException {
      this(readClass(ClassLoader.getSystemResourceAsStream(var1.replace('.', '/') + ".class"), true));
   }

   private static byte[] readClass(InputStream var0, boolean var1) throws IOException {
      if (var0 == null) {
         throw new IOException("Class not found");
      } else {
         try {
            byte[] var2 = new byte[var0.available()];
            int var3 = 0;

            while(true) {
               int var4 = var0.read(var2, var3, var2.length - var3);
               if (var4 == -1) {
                  byte[] var10;
                  if (var3 < var2.length) {
                     var10 = new byte[var3];
                     System.arraycopy(var2, 0, var10, 0, var3);
                     var2 = var10;
                  }

                  var10 = var2;
                  return var10;
               }

               var3 += var4;
               if (var3 == var2.length) {
                  int var5 = var0.read();
                  byte[] var6;
                  if (var5 < 0) {
                     var6 = var2;
                     return var6;
                  }

                  var6 = new byte[var2.length + 1000];
                  System.arraycopy(var2, 0, var6, 0, var3);
                  var6[var3++] = (byte)var5;
                  var2 = var6;
               }
            }
         } finally {
            if (var1) {
               var0.close();
            }

         }
      }
   }

   public void accept(ClassVisitor var1, int var2) {
      this.accept(var1, new Attribute[0], var2);
   }

   public void accept(ClassVisitor var1, Attribute[] var2, int var3) {
      int var4 = this.header;
      char[] var5 = new char[this.maxStringLength];
      Context var6 = new Context();
      var6.attrs = var2;
      var6.flags = var3;
      var6.buffer = var5;
      int var7 = this.readUnsignedShort(var4);
      String var8 = this.readClass(var4 + 2, var5);
      String var9 = this.readClass(var4 + 4, var5);
      String[] var10 = new String[this.readUnsignedShort(var4 + 6)];
      var4 += 8;

      for(int var11 = 0; var11 < var10.length; ++var11) {
         var10[var11] = this.readClass(var4, var5);
         var4 += 2;
      }

      String var28 = null;
      String var12 = null;
      String var13 = null;
      String var14 = null;
      String var15 = null;
      String var16 = null;
      int var17 = 0;
      int var18 = 0;
      int var19 = 0;
      int var20 = 0;
      int var21 = 0;
      Attribute var22 = null;
      var4 = this.getAttributes();

      int var23;
      for(var23 = this.readUnsignedShort(var4); var23 > 0; --var23) {
         String var24 = this.readUTF8(var4 + 2, var5);
         if ("SourceFile".equals(var24)) {
            var12 = this.readUTF8(var4 + 8, var5);
         } else if ("InnerClasses".equals(var24)) {
            var21 = var4 + 8;
         } else {
            int var31;
            if ("EnclosingMethod".equals(var24)) {
               var14 = this.readClass(var4 + 8, var5);
               var31 = this.readUnsignedShort(var4 + 10);
               if (var31 != 0) {
                  var15 = this.readUTF8(this.items[var31], var5);
                  var16 = this.readUTF8(this.items[var31] + 2, var5);
               }
            } else if ("Signature".equals(var24)) {
               var28 = this.readUTF8(var4 + 8, var5);
            } else if ("RuntimeVisibleAnnotations".equals(var24)) {
               var17 = var4 + 8;
            } else if ("RuntimeVisibleTypeAnnotations".equals(var24)) {
               var19 = var4 + 8;
            } else if ("Deprecated".equals(var24)) {
               var7 |= 131072;
            } else if ("Synthetic".equals(var24)) {
               var7 |= 266240;
            } else if ("SourceDebugExtension".equals(var24)) {
               var31 = this.readInt(var4 + 4);
               var13 = this.readUTF(var4 + 8, var31, new char[var31]);
            } else if ("RuntimeInvisibleAnnotations".equals(var24)) {
               var18 = var4 + 8;
            } else if ("RuntimeInvisibleTypeAnnotations".equals(var24)) {
               var20 = var4 + 8;
            } else if (!"BootstrapMethods".equals(var24)) {
               Attribute var30 = this.readAttribute(var2, var24, var4 + 8, this.readInt(var4 + 4), var5, -1, (Label[])null);
               if (var30 != null) {
                  var30.next = var22;
                  var22 = var30;
               }
            } else {
               int[] var25 = new int[this.readUnsignedShort(var4 + 8)];
               int var26 = 0;

               for(int var27 = var4 + 10; var26 < var25.length; ++var26) {
                  var25[var26] = var27;
                  var27 += 2 + this.readUnsignedShort(var27 + 2) << 1;
               }

               var6.bootstrapMethods = var25;
            }
         }

         var4 += 6 + this.readInt(var4 + 4);
      }

      var1.visit(this.readInt(this.items[1] - 7), var7, var8, var28, var9, var10);
      if ((var3 & 2) == 0 && (var12 != null || var13 != null)) {
         var1.visitSource(var12, var13);
      }

      if (var14 != null) {
         var1.visitOuterClass(var14, var15, var16);
      }

      int var29;
      if (var17 != 0) {
         var23 = this.readUnsignedShort(var17);

         for(var29 = var17 + 2; var23 > 0; --var23) {
            var29 = this.readAnnotationValues(var29 + 2, var5, true, var1.visitAnnotation(this.readUTF8(var29, var5), true));
         }
      }

      if (var18 != 0) {
         var23 = this.readUnsignedShort(var18);

         for(var29 = var18 + 2; var23 > 0; --var23) {
            var29 = this.readAnnotationValues(var29 + 2, var5, true, var1.visitAnnotation(this.readUTF8(var29, var5), false));
         }
      }

      if (var19 != 0) {
         var23 = this.readUnsignedShort(var19);

         for(var29 = var19 + 2; var23 > 0; --var23) {
            var29 = this.readAnnotationTarget(var6, var29);
            var29 = this.readAnnotationValues(var29 + 2, var5, true, var1.visitTypeAnnotation(var6.typeRef, var6.typePath, this.readUTF8(var29, var5), true));
         }
      }

      if (var20 != 0) {
         var23 = this.readUnsignedShort(var20);

         for(var29 = var20 + 2; var23 > 0; --var23) {
            var29 = this.readAnnotationTarget(var6, var29);
            var29 = this.readAnnotationValues(var29 + 2, var5, true, var1.visitTypeAnnotation(var6.typeRef, var6.typePath, this.readUTF8(var29, var5), false));
         }
      }

      while(var22 != null) {
         Attribute var32 = var22.next;
         var22.next = null;
         var1.visitAttribute(var22);
         var22 = var32;
      }

      if (var21 != 0) {
         var23 = var21 + 2;

         for(var29 = this.readUnsignedShort(var21); var29 > 0; --var29) {
            var1.visitInnerClass(this.readClass(var23, var5), this.readClass(var23 + 2, var5), this.readUTF8(var23 + 4, var5), this.readUnsignedShort(var23 + 6));
            var23 += 8;
         }
      }

      var4 = this.header + 10 + 2 * var10.length;

      for(var23 = this.readUnsignedShort(var4 - 2); var23 > 0; --var23) {
         var4 = this.readField(var1, var6, var4);
      }

      var4 += 2;

      for(var23 = this.readUnsignedShort(var4 - 2); var23 > 0; --var23) {
         var4 = this.readMethod(var1, var6, var4);
      }

      var1.visitEnd();
   }

   private int readField(ClassVisitor var1, Context var2, int var3) {
      char[] var4 = var2.buffer;
      int var5 = this.readUnsignedShort(var3);
      String var6 = this.readUTF8(var3 + 2, var4);
      String var7 = this.readUTF8(var3 + 4, var4);
      var3 += 6;
      String var8 = null;
      int var9 = 0;
      int var10 = 0;
      int var11 = 0;
      int var12 = 0;
      Object var13 = null;
      Attribute var14 = null;

      int var17;
      for(int var15 = this.readUnsignedShort(var3); var15 > 0; --var15) {
         String var16 = this.readUTF8(var3 + 2, var4);
         if ("ConstantValue".equals(var16)) {
            var17 = this.readUnsignedShort(var3 + 8);
            var13 = var17 == 0 ? null : this.readConst(var17, var4);
         } else if ("Signature".equals(var16)) {
            var8 = this.readUTF8(var3 + 8, var4);
         } else if ("Deprecated".equals(var16)) {
            var5 |= 131072;
         } else if ("Synthetic".equals(var16)) {
            var5 |= 266240;
         } else if ("RuntimeVisibleAnnotations".equals(var16)) {
            var9 = var3 + 8;
         } else if ("RuntimeVisibleTypeAnnotations".equals(var16)) {
            var11 = var3 + 8;
         } else if ("RuntimeInvisibleAnnotations".equals(var16)) {
            var10 = var3 + 8;
         } else if ("RuntimeInvisibleTypeAnnotations".equals(var16)) {
            var12 = var3 + 8;
         } else {
            Attribute var20 = this.readAttribute(var2.attrs, var16, var3 + 8, this.readInt(var3 + 4), var4, -1, (Label[])null);
            if (var20 != null) {
               var20.next = var14;
               var14 = var20;
            }
         }

         var3 += 6 + this.readInt(var3 + 4);
      }

      var3 += 2;
      FieldVisitor var18 = var1.visitField(var5, var6, var7, var8, var13);
      if (var18 == null) {
         return var3;
      } else {
         int var19;
         if (var9 != 0) {
            var19 = this.readUnsignedShort(var9);

            for(var17 = var9 + 2; var19 > 0; --var19) {
               var17 = this.readAnnotationValues(var17 + 2, var4, true, var18.visitAnnotation(this.readUTF8(var17, var4), true));
            }
         }

         if (var10 != 0) {
            var19 = this.readUnsignedShort(var10);

            for(var17 = var10 + 2; var19 > 0; --var19) {
               var17 = this.readAnnotationValues(var17 + 2, var4, true, var18.visitAnnotation(this.readUTF8(var17, var4), false));
            }
         }

         if (var11 != 0) {
            var19 = this.readUnsignedShort(var11);

            for(var17 = var11 + 2; var19 > 0; --var19) {
               var17 = this.readAnnotationTarget(var2, var17);
               var17 = this.readAnnotationValues(var17 + 2, var4, true, var18.visitTypeAnnotation(var2.typeRef, var2.typePath, this.readUTF8(var17, var4), true));
            }
         }

         if (var12 != 0) {
            var19 = this.readUnsignedShort(var12);

            for(var17 = var12 + 2; var19 > 0; --var19) {
               var17 = this.readAnnotationTarget(var2, var17);
               var17 = this.readAnnotationValues(var17 + 2, var4, true, var18.visitTypeAnnotation(var2.typeRef, var2.typePath, this.readUTF8(var17, var4), false));
            }
         }

         while(var14 != null) {
            Attribute var21 = var14.next;
            var14.next = null;
            var18.visitAttribute(var14);
            var14 = var21;
         }

         var18.visitEnd();
         return var3;
      }
   }

   private int readMethod(ClassVisitor var1, Context var2, int var3) {
      char[] var4 = var2.buffer;
      var2.access = this.readUnsignedShort(var3);
      var2.name = this.readUTF8(var3 + 2, var4);
      var2.desc = this.readUTF8(var3 + 4, var4);
      var3 += 6;
      int var5 = 0;
      int var6 = 0;
      String[] var7 = null;
      String var8 = null;
      int var9 = 0;
      int var10 = 0;
      int var11 = 0;
      int var12 = 0;
      int var13 = 0;
      int var14 = 0;
      int var15 = 0;
      int var16 = 0;
      int var17 = var3;
      Attribute var18 = null;

      int var26;
      for(int var19 = this.readUnsignedShort(var3); var19 > 0; --var19) {
         String var20 = this.readUTF8(var3 + 2, var4);
         if ("Code".equals(var20)) {
            if ((var2.flags & 1) == 0) {
               var5 = var3 + 8;
            }
         } else if ("Exceptions".equals(var20)) {
            var7 = new String[this.readUnsignedShort(var3 + 8)];
            var6 = var3 + 10;

            for(var26 = 0; var26 < var7.length; ++var26) {
               var7[var26] = this.readClass(var6, var4);
               var6 += 2;
            }
         } else if ("Signature".equals(var20)) {
            var8 = this.readUTF8(var3 + 8, var4);
         } else if ("Deprecated".equals(var20)) {
            var2.access |= 131072;
         } else if ("RuntimeVisibleAnnotations".equals(var20)) {
            var10 = var3 + 8;
         } else if ("RuntimeVisibleTypeAnnotations".equals(var20)) {
            var12 = var3 + 8;
         } else if ("AnnotationDefault".equals(var20)) {
            var14 = var3 + 8;
         } else if ("Synthetic".equals(var20)) {
            var2.access |= 266240;
         } else if ("RuntimeInvisibleAnnotations".equals(var20)) {
            var11 = var3 + 8;
         } else if ("RuntimeInvisibleTypeAnnotations".equals(var20)) {
            var13 = var3 + 8;
         } else if ("RuntimeVisibleParameterAnnotations".equals(var20)) {
            var15 = var3 + 8;
         } else if ("RuntimeInvisibleParameterAnnotations".equals(var20)) {
            var16 = var3 + 8;
         } else if ("MethodParameters".equals(var20)) {
            var9 = var3 + 8;
         } else {
            Attribute var21 = this.readAttribute(var2.attrs, var20, var3 + 8, this.readInt(var3 + 4), var4, -1, (Label[])null);
            if (var21 != null) {
               var21.next = var18;
               var18 = var21;
            }
         }

         var3 += 6 + this.readInt(var3 + 4);
      }

      var3 += 2;
      MethodVisitor var23 = var1.visitMethod(var2.access, var2.name, var2.desc, var8, var7);
      if (var23 == null) {
         return var3;
      } else {
         if (var23 instanceof MethodWriter) {
            MethodWriter var24 = (MethodWriter)var23;
            if (var24.cw.cr == this && var8 == var24.signature) {
               boolean var28 = false;
               if (var7 == null) {
                  var28 = var24.exceptionCount == 0;
               } else if (var7.length == var24.exceptionCount) {
                  var28 = true;

                  for(int var22 = var7.length - 1; var22 >= 0; --var22) {
                     var6 -= 2;
                     if (var24.exceptions[var22] != this.readUnsignedShort(var6)) {
                        var28 = false;
                        break;
                     }
                  }
               }

               if (var28) {
                  var24.classReaderOffset = var17;
                  var24.classReaderLength = var3 - var17;
                  return var3;
               }
            }
         }

         int var25;
         if (var9 != 0) {
            var25 = this.b[var9] & 255;

            for(var26 = var9 + 1; var25 > 0; var26 += 4) {
               var23.visitParameter(this.readUTF8(var26, var4), this.readUnsignedShort(var26 + 2));
               --var25;
            }
         }

         if (var14 != 0) {
            AnnotationVisitor var27 = var23.visitAnnotationDefault();
            this.readAnnotationValue(var14, var4, (String)null, var27);
            if (var27 != null) {
               var27.visitEnd();
            }
         }

         if (var10 != 0) {
            var25 = this.readUnsignedShort(var10);

            for(var26 = var10 + 2; var25 > 0; --var25) {
               var26 = this.readAnnotationValues(var26 + 2, var4, true, var23.visitAnnotation(this.readUTF8(var26, var4), true));
            }
         }

         if (var11 != 0) {
            var25 = this.readUnsignedShort(var11);

            for(var26 = var11 + 2; var25 > 0; --var25) {
               var26 = this.readAnnotationValues(var26 + 2, var4, true, var23.visitAnnotation(this.readUTF8(var26, var4), false));
            }
         }

         if (var12 != 0) {
            var25 = this.readUnsignedShort(var12);

            for(var26 = var12 + 2; var25 > 0; --var25) {
               var26 = this.readAnnotationTarget(var2, var26);
               var26 = this.readAnnotationValues(var26 + 2, var4, true, var23.visitTypeAnnotation(var2.typeRef, var2.typePath, this.readUTF8(var26, var4), true));
            }
         }

         if (var13 != 0) {
            var25 = this.readUnsignedShort(var13);

            for(var26 = var13 + 2; var25 > 0; --var25) {
               var26 = this.readAnnotationTarget(var2, var26);
               var26 = this.readAnnotationValues(var26 + 2, var4, true, var23.visitTypeAnnotation(var2.typeRef, var2.typePath, this.readUTF8(var26, var4), false));
            }
         }

         if (var15 != 0) {
            this.readParameterAnnotations(var23, var2, var15, true);
         }

         if (var16 != 0) {
            this.readParameterAnnotations(var23, var2, var16, false);
         }

         while(var18 != null) {
            Attribute var29 = var18.next;
            var18.next = null;
            var23.visitAttribute(var18);
            var18 = var29;
         }

         if (var5 != 0) {
            var23.visitCode();
            this.readCode(var23, var2, var5);
         }

         var23.visitEnd();
         return var3;
      }
   }

   private void readCode(MethodVisitor var1, Context var2, int var3) {
      byte[] var4 = this.b;
      char[] var5 = var2.buffer;
      int var6 = this.readUnsignedShort(var3);
      int var7 = this.readUnsignedShort(var3 + 2);
      int var8 = this.readInt(var3 + 4);
      var3 += 8;
      int var9 = var3;
      int var10 = var3 + var8;
      Label[] var11 = var2.labels = new Label[var8 + 2];
      this.readLabel(var8 + 1, var11);

      while(true) {
         int var12;
         int var14;
         while(var3 < var10) {
            var12 = var3 - var9;
            int var13 = var4[var3] & 255;
            switch(ClassWriter.TYPE[var13]) {
            case 0:
            case 4:
               ++var3;
               break;
            case 1:
            case 3:
            case 11:
               var3 += 2;
               break;
            case 2:
            case 5:
            case 6:
            case 12:
            case 13:
               var3 += 3;
               break;
            case 7:
            case 8:
               var3 += 5;
               break;
            case 9:
               this.readLabel(var12 + this.readShort(var3 + 1), var11);
               var3 += 3;
               break;
            case 10:
               this.readLabel(var12 + this.readInt(var3 + 1), var11);
               var3 += 5;
               break;
            case 14:
               var3 = var3 + 4 - (var12 & 3);
               this.readLabel(var12 + this.readInt(var3), var11);

               for(var14 = this.readInt(var3 + 8) - this.readInt(var3 + 4) + 1; var14 > 0; --var14) {
                  this.readLabel(var12 + this.readInt(var3 + 12), var11);
                  var3 += 4;
               }

               var3 += 12;
               break;
            case 15:
               var3 = var3 + 4 - (var12 & 3);
               this.readLabel(var12 + this.readInt(var3), var11);

               for(var14 = this.readInt(var3 + 4); var14 > 0; --var14) {
                  this.readLabel(var12 + this.readInt(var3 + 12), var11);
                  var3 += 8;
               }

               var3 += 8;
               break;
            case 16:
            default:
               var3 += 4;
               break;
            case 17:
               var13 = var4[var3 + 1] & 255;
               if (var13 == 132) {
                  var3 += 6;
               } else {
                  var3 += 4;
               }
               break;
            case 18:
               this.readLabel(var12 + this.readUnsignedShort(var3 + 1), var11);
               var3 += 3;
            }
         }

         for(var12 = this.readUnsignedShort(var3); var12 > 0; --var12) {
            Label var39 = this.readLabel(this.readUnsignedShort(var3 + 2), var11);
            Label var42 = this.readLabel(this.readUnsignedShort(var3 + 4), var11);
            Label var15 = this.readLabel(this.readUnsignedShort(var3 + 6), var11);
            String var16 = this.readUTF8(this.items[this.readUnsignedShort(var3 + 8)], var5);
            var1.visitTryCatchBlock(var39, var42, var15, var16);
            var3 += 8;
         }

         var3 += 2;
         int[] var38 = null;
         int[] var40 = null;
         var14 = 0;
         int var41 = 0;
         int var43 = -1;
         int var17 = -1;
         int var18 = 0;
         int var19 = 0;
         boolean var20 = true;
         boolean var21 = (var2.flags & 8) != 0;
         int var22 = 0;
         int var23 = 0;
         int var24 = 0;
         Context var25 = null;
         Attribute var26 = null;

         int var27;
         int var29;
         int var31;
         Label var32;
         int var46;
         for(var27 = this.readUnsignedShort(var3); var27 > 0; --var27) {
            String var28 = this.readUTF8(var3 + 2, var5);
            Label var10000;
            if ("LocalVariableTable".equals(var28)) {
               if ((var2.flags & 2) == 0) {
                  var18 = var3 + 8;
                  var29 = this.readUnsignedShort(var3 + 8);

                  for(var46 = var3; var29 > 0; --var29) {
                     var31 = this.readUnsignedShort(var46 + 10);
                     if (var11[var31] == null) {
                        var10000 = this.readLabel(var31, var11);
                        var10000.status |= 1;
                     }

                     var31 += this.readUnsignedShort(var46 + 12);
                     if (var11[var31] == null) {
                        var10000 = this.readLabel(var31, var11);
                        var10000.status |= 1;
                     }

                     var46 += 10;
                  }
               }
            } else if ("LocalVariableTypeTable".equals(var28)) {
               var19 = var3 + 8;
            } else if ("LineNumberTable".equals(var28)) {
               if ((var2.flags & 2) == 0) {
                  var29 = this.readUnsignedShort(var3 + 8);

                  for(var46 = var3; var29 > 0; --var29) {
                     var31 = this.readUnsignedShort(var46 + 10);
                     if (var11[var31] == null) {
                        var10000 = this.readLabel(var31, var11);
                        var10000.status |= 1;
                     }

                     for(var32 = var11[var31]; var32.line > 0; var32 = var32.next) {
                        if (var32.next == null) {
                           var32.next = new Label();
                        }
                     }

                     var32.line = this.readUnsignedShort(var46 + 12);
                     var46 += 4;
                  }
               }
            } else if ("RuntimeVisibleTypeAnnotations".equals(var28)) {
               var38 = this.readTypeAnnotations(var1, var2, var3 + 8, true);
               var43 = var38.length != 0 && this.readByte(var38[0]) >= 67 ? this.readUnsignedShort(var38[0] + 1) : -1;
            } else if (!"RuntimeInvisibleTypeAnnotations".equals(var28)) {
               if ("StackMapTable".equals(var28)) {
                  if ((var2.flags & 4) == 0) {
                     var22 = var3 + 10;
                     var23 = this.readInt(var3 + 4);
                     var24 = this.readUnsignedShort(var3 + 8);
                  }
               } else if ("StackMap".equals(var28)) {
                  if ((var2.flags & 4) == 0) {
                     var20 = false;
                     var22 = var3 + 10;
                     var23 = this.readInt(var3 + 4);
                     var24 = this.readUnsignedShort(var3 + 8);
                  }
               } else {
                  for(var29 = 0; var29 < var2.attrs.length; ++var29) {
                     if (var2.attrs[var29].type.equals(var28)) {
                        Attribute var30 = var2.attrs[var29].read(this, var3 + 8, this.readInt(var3 + 4), var5, var9 - 8, var11);
                        if (var30 != null) {
                           var30.next = var26;
                           var26 = var30;
                        }
                     }
                  }
               }
            } else {
               var40 = this.readTypeAnnotations(var1, var2, var3 + 8, false);
               var17 = var40.length != 0 && this.readByte(var40[0]) >= 67 ? this.readUnsignedShort(var40[0] + 1) : -1;
            }

            var3 += 6 + this.readInt(var3 + 4);
         }

         var3 += 2;
         int var44;
         if (var22 != 0) {
            var25 = var2;
            var2.offset = -1;
            var2.mode = 0;
            var2.localCount = 0;
            var2.localDiff = 0;
            var2.stackCount = 0;
            var2.local = new Object[var7];
            var2.stack = new Object[var6];
            if (var21) {
               this.getImplicitFrame(var2);
            }

            for(var27 = var22; var27 < var22 + var23 - 2; ++var27) {
               if (var4[var27] == 8) {
                  var44 = this.readUnsignedShort(var27 + 1);
                  if (var44 >= 0 && var44 < var8 && (var4[var9 + var44] & 255) == 187) {
                     this.readLabel(var44, var11);
                  }
               }
            }
         }

         if ((var2.flags & 256) != 0) {
            var1.visitFrame(-1, var7, (Object[])null, 0, (Object[])null);
         }

         var27 = (var2.flags & 256) == 0 ? -33 : 0;
         var3 = var9;

         int var51;
         String var54;
         int var56;
         while(var3 < var10) {
            var44 = var3 - var9;
            Label var47 = var11[var44];
            if (var47 != null) {
               Label var49 = var47.next;
               var47.next = null;
               var1.visitLabel(var47);
               if ((var2.flags & 2) == 0 && var47.line > 0) {
                  var1.visitLineNumber(var47.line, var47);

                  while(var49 != null) {
                     var1.visitLineNumber(var49.line, var47);
                     var49 = var49.next;
                  }
               }
            }

            while(var25 != null && (var25.offset == var44 || var25.offset == -1)) {
               if (var25.offset != -1) {
                  if (var20 && !var21) {
                     var1.visitFrame(var25.mode, var25.localDiff, var25.local, var25.stackCount, var25.stack);
                  } else {
                     var1.visitFrame(-1, var25.localCount, var25.local, var25.stackCount, var25.stack);
                  }
               }

               if (var24 > 0) {
                  var22 = this.readFrame(var22, var20, var21, var25);
                  --var24;
               } else {
                  var25 = null;
               }
            }

            var46 = var4[var3] & 255;
            Label[] var34;
            int var35;
            switch(ClassWriter.TYPE[var46]) {
            case 0:
               var1.visitInsn(var46);
               ++var3;
               break;
            case 1:
               var1.visitIntInsn(var46, var4[var3 + 1]);
               var3 += 2;
               break;
            case 2:
               var1.visitIntInsn(var46, this.readShort(var3 + 1));
               var3 += 3;
               break;
            case 3:
               var1.visitVarInsn(var46, var4[var3 + 1] & 255);
               var3 += 2;
               break;
            case 4:
               if (var46 > 54) {
                  var46 -= 59;
                  var1.visitVarInsn(54 + (var46 >> 2), var46 & 3);
               } else {
                  var46 -= 26;
                  var1.visitVarInsn(21 + (var46 >> 2), var46 & 3);
               }

               ++var3;
               break;
            case 5:
               var1.visitTypeInsn(var46, this.readClass(var3 + 1, var5));
               var3 += 3;
               break;
            case 6:
            case 7:
               var31 = this.items[this.readUnsignedShort(var3 + 1)];
               boolean var55 = var4[var31 - 1] == 11;
               var54 = this.readClass(var31, var5);
               var31 = this.items[this.readUnsignedShort(var31 + 2)];
               String var57 = this.readUTF8(var31, var5);
               String var59 = this.readUTF8(var31 + 2, var5);
               if (var46 < 182) {
                  var1.visitFieldInsn(var46, var54, var57, var59);
               } else {
                  var1.visitMethodInsn(var46, var54, var57, var59, var55);
               }

               if (var46 == 185) {
                  var3 += 5;
               } else {
                  var3 += 3;
               }
               break;
            case 8:
               var31 = this.items[this.readUnsignedShort(var3 + 1)];
               var51 = var2.bootstrapMethods[this.readUnsignedShort(var31)];
               Handle var53 = (Handle)this.readConst(this.readUnsignedShort(var51), var5);
               var56 = this.readUnsignedShort(var51 + 2);
               Object[] var58 = new Object[var56];
               var51 += 4;

               for(int var36 = 0; var36 < var56; ++var36) {
                  var58[var36] = this.readConst(this.readUnsignedShort(var51), var5);
                  var51 += 2;
               }

               var31 = this.items[this.readUnsignedShort(var31 + 2)];
               String var60 = this.readUTF8(var31, var5);
               String var37 = this.readUTF8(var31 + 2, var5);
               var1.visitInvokeDynamicInsn(var60, var37, var53, var58);
               break;
            case 9:
               var1.visitJumpInsn(var46, var11[var44 + this.readShort(var3 + 1)]);
               var3 += 3;
               break;
            case 10:
               var1.visitJumpInsn(var46 + var27, var11[var44 + this.readInt(var3 + 1)]);
               var3 += 5;
               break;
            case 11:
               var1.visitLdcInsn(this.readConst(var4[var3 + 1] & 255, var5));
               var3 += 2;
               break;
            case 12:
               var1.visitLdcInsn(this.readConst(this.readUnsignedShort(var3 + 1), var5));
               var3 += 3;
               break;
            case 13:
               var1.visitIincInsn(var4[var3 + 1] & 255, var4[var3 + 2]);
               var3 += 3;
               break;
            case 14:
               var3 = var3 + 4 - (var44 & 3);
               var31 = var44 + this.readInt(var3);
               var51 = this.readInt(var3 + 4);
               int var52 = this.readInt(var3 + 8);
               var34 = new Label[var52 - var51 + 1];
               var3 += 12;

               for(var35 = 0; var35 < var34.length; ++var35) {
                  var34[var35] = var11[var44 + this.readInt(var3)];
                  var3 += 4;
               }

               var1.visitTableSwitchInsn(var51, var52, var11[var31], var34);
               break;
            case 15:
               var3 = var3 + 4 - (var44 & 3);
               var31 = var44 + this.readInt(var3);
               var51 = this.readInt(var3 + 4);
               int[] var33 = new int[var51];
               var34 = new Label[var51];
               var3 += 8;

               for(var35 = 0; var35 < var51; ++var35) {
                  var33[var35] = this.readInt(var3);
                  var34[var35] = var11[var44 + this.readInt(var3 + 4)];
                  var3 += 8;
               }

               var1.visitLookupSwitchInsn(var11[var31], var33, var34);
               break;
            case 16:
            default:
               var1.visitMultiANewArrayInsn(this.readClass(var3 + 1, var5), var4[var3 + 3] & 255);
               var3 += 4;
               break;
            case 17:
               var46 = var4[var3 + 1] & 255;
               if (var46 == 132) {
                  var1.visitIincInsn(this.readUnsignedShort(var3 + 2), this.readShort(var3 + 4));
                  var3 += 6;
               } else {
                  var1.visitVarInsn(var46, this.readUnsignedShort(var3 + 2));
                  var3 += 4;
               }
               break;
            case 18:
               var46 = var46 < 218 ? var46 - 49 : var46 - 20;
               Label var50 = var11[var44 + this.readUnsignedShort(var3 + 1)];
               if (var46 != 167 && var46 != 168) {
                  var46 = var46 <= 166 ? (var46 + 1 ^ 1) - 1 : var46 ^ 1;
                  var32 = new Label();
                  var1.visitJumpInsn(var46, var32);
                  var1.visitJumpInsn(200, var50);
                  var1.visitLabel(var32);
                  if (var22 != 0 && (var25 == null || var25.offset != var44 + 3)) {
                     var1.visitFrame(256, 0, (Object[])null, 0, (Object[])null);
                  }
               } else {
                  var1.visitJumpInsn(var46 + 33, var50);
               }

               var3 += 3;
            }

            for(var3 += 5; var38 != null && var14 < var38.length && var43 <= var44; var43 = var14 < var38.length && this.readByte(var38[var14]) >= 67 ? this.readUnsignedShort(var38[var14] + 1) : -1) {
               if (var43 == var44) {
                  var31 = this.readAnnotationTarget(var2, var38[var14]);
                  this.readAnnotationValues(var31 + 2, var5, true, var1.visitInsnAnnotation(var2.typeRef, var2.typePath, this.readUTF8(var31, var5), true));
               }

               ++var14;
            }

            while(var40 != null && var41 < var40.length && var17 <= var44) {
               if (var17 == var44) {
                  var31 = this.readAnnotationTarget(var2, var40[var41]);
                  this.readAnnotationValues(var31 + 2, var5, true, var1.visitInsnAnnotation(var2.typeRef, var2.typePath, this.readUTF8(var31, var5), false));
               }

               ++var41;
               var17 = var41 < var40.length && this.readByte(var40[var41]) >= 67 ? this.readUnsignedShort(var40[var41] + 1) : -1;
            }
         }

         if (var11[var8] != null) {
            var1.visitLabel(var11[var8]);
         }

         if ((var2.flags & 2) == 0 && var18 != 0) {
            int[] var45 = null;
            if (var19 != 0) {
               var3 = var19 + 2;
               var45 = new int[this.readUnsignedShort(var19) * 3];

               for(var29 = var45.length; var29 > 0; var3 += 10) {
                  --var29;
                  var45[var29] = var3 + 6;
                  --var29;
                  var45[var29] = this.readUnsignedShort(var3 + 8);
                  --var29;
                  var45[var29] = this.readUnsignedShort(var3);
               }
            }

            var3 = var18 + 2;

            for(var29 = this.readUnsignedShort(var18); var29 > 0; --var29) {
               var46 = this.readUnsignedShort(var3);
               var31 = this.readUnsignedShort(var3 + 2);
               var51 = this.readUnsignedShort(var3 + 8);
               var54 = null;
               if (var45 != null) {
                  for(var56 = 0; var56 < var45.length; var56 += 3) {
                     if (var45[var56] == var46 && var45[var56 + 1] == var51) {
                        var54 = this.readUTF8(var45[var56 + 2], var5);
                        break;
                     }
                  }
               }

               var1.visitLocalVariable(this.readUTF8(var3 + 4, var5), this.readUTF8(var3 + 6, var5), var54, var11[var46], var11[var46 + var31], var51);
               var3 += 10;
            }
         }

         if (var38 != null) {
            for(var44 = 0; var44 < var38.length; ++var44) {
               if (this.readByte(var38[var44]) >> 1 == 32) {
                  var29 = this.readAnnotationTarget(var2, var38[var44]);
                  this.readAnnotationValues(var29 + 2, var5, true, var1.visitLocalVariableAnnotation(var2.typeRef, var2.typePath, var2.start, var2.end, var2.index, this.readUTF8(var29, var5), true));
               }
            }
         }

         if (var40 != null) {
            for(var44 = 0; var44 < var40.length; ++var44) {
               if (this.readByte(var40[var44]) >> 1 == 32) {
                  var29 = this.readAnnotationTarget(var2, var40[var44]);
                  this.readAnnotationValues(var29 + 2, var5, true, var1.visitLocalVariableAnnotation(var2.typeRef, var2.typePath, var2.start, var2.end, var2.index, this.readUTF8(var29, var5), false));
               }
            }
         }

         while(var26 != null) {
            Attribute var48 = var26.next;
            var26.next = null;
            var1.visitAttribute(var26);
            var26 = var48;
         }

         var1.visitMaxs(var6, var7);
         return;
      }
   }

   private int[] readTypeAnnotations(MethodVisitor var1, Context var2, int var3, boolean var4) {
      char[] var5 = var2.buffer;
      int[] var6 = new int[this.readUnsignedShort(var3)];
      var3 += 2;

      for(int var7 = 0; var7 < var6.length; ++var7) {
         var6[var7] = var3;
         int var8 = this.readInt(var3);
         int var9;
         switch(var8 >>> 24) {
         case 0:
         case 1:
         case 22:
            var3 += 2;
            break;
         case 19:
         case 20:
         case 21:
            ++var3;
            break;
         case 64:
         case 65:
            for(var9 = this.readUnsignedShort(var3 + 1); var9 > 0; --var9) {
               int var10 = this.readUnsignedShort(var3 + 3);
               int var11 = this.readUnsignedShort(var3 + 5);
               this.readLabel(var10, var2.labels);
               this.readLabel(var10 + var11, var2.labels);
               var3 += 6;
            }

            var3 += 3;
            break;
         case 71:
         case 72:
         case 73:
         case 74:
         case 75:
            var3 += 4;
            break;
         default:
            var3 += 3;
         }

         var9 = this.readByte(var3);
         if (var8 >>> 24 == 66) {
            TypePath var12 = var9 == 0 ? null : new TypePath(this.b, var3);
            var3 += 1 + 2 * var9;
            var3 = this.readAnnotationValues(var3 + 2, var5, true, var1.visitTryCatchAnnotation(var8, var12, this.readUTF8(var3, var5), var4));
         } else {
            var3 = this.readAnnotationValues(var3 + 3 + 2 * var9, var5, true, (AnnotationVisitor)null);
         }
      }

      return var6;
   }

   private int readAnnotationTarget(Context var1, int var2) {
      int var3;
      int var4;
      var3 = this.readInt(var2);
      label53:
      switch(var3 >>> 24) {
      case 0:
      case 1:
      case 22:
         var3 &= -65536;
         var2 += 2;
         break;
      case 19:
      case 20:
      case 21:
         var3 &= -16777216;
         ++var2;
         break;
      case 64:
      case 65:
         var3 &= -16777216;
         var4 = this.readUnsignedShort(var2 + 1);
         var1.start = new Label[var4];
         var1.end = new Label[var4];
         var1.index = new int[var4];
         var2 += 3;
         int var5 = 0;

         while(true) {
            if (var5 >= var4) {
               break label53;
            }

            int var6 = this.readUnsignedShort(var2);
            int var7 = this.readUnsignedShort(var2 + 2);
            var1.start[var5] = this.readLabel(var6, var1.labels);
            var1.end[var5] = this.readLabel(var6 + var7, var1.labels);
            var1.index[var5] = this.readUnsignedShort(var2 + 4);
            var2 += 6;
            ++var5;
         }
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
         var3 &= -16776961;
         var2 += 4;
         break;
      default:
         var3 &= var3 >>> 24 < 67 ? -256 : -16777216;
         var2 += 3;
      }

      var4 = this.readByte(var2);
      var1.typeRef = var3;
      var1.typePath = var4 == 0 ? null : new TypePath(this.b, var2);
      return var2 + 1 + 2 * var4;
   }

   private void readParameterAnnotations(MethodVisitor var1, Context var2, int var3, boolean var4) {
      int var5 = this.b[var3++] & 255;
      int var6 = Type.getArgumentTypes(var2.desc).length - var5;

      int var7;
      AnnotationVisitor var8;
      for(var7 = 0; var7 < var6; ++var7) {
         var8 = var1.visitParameterAnnotation(var7, "Ljava/lang/Synthetic;", false);
         if (var8 != null) {
            var8.visitEnd();
         }
      }

      for(char[] var9 = var2.buffer; var7 < var5 + var6; ++var7) {
         int var10 = this.readUnsignedShort(var3);

         for(var3 += 2; var10 > 0; --var10) {
            var8 = var1.visitParameterAnnotation(var7, this.readUTF8(var3, var9), var4);
            var3 = this.readAnnotationValues(var3 + 2, var9, true, var8);
         }
      }

   }

   private int readAnnotationValues(int var1, char[] var2, boolean var3, AnnotationVisitor var4) {
      int var5 = this.readUnsignedShort(var1);
      var1 += 2;
      if (var3) {
         while(var5 > 0) {
            var1 = this.readAnnotationValue(var1 + 2, var2, this.readUTF8(var1, var2), var4);
            --var5;
         }
      } else {
         while(var5 > 0) {
            var1 = this.readAnnotationValue(var1, var2, (String)null, var4);
            --var5;
         }
      }

      if (var4 != null) {
         var4.visitEnd();
      }

      return var1;
   }

   private int readAnnotationValue(int var1, char[] var2, String var3, AnnotationVisitor var4) {
      if (var4 == null) {
         switch(this.b[var1] & 255) {
         case 64:
            return this.readAnnotationValues(var1 + 3, var2, true, (AnnotationVisitor)null);
         case 91:
            return this.readAnnotationValues(var1 + 1, var2, false, (AnnotationVisitor)null);
         case 101:
            return var1 + 5;
         default:
            return var1 + 3;
         }
      } else {
         switch(this.b[var1++] & 255) {
         case 64:
            var1 = this.readAnnotationValues(var1 + 2, var2, true, var4.visitAnnotation(var3, this.readUTF8(var1, var2)));
         case 65:
         case 69:
         case 71:
         case 72:
         case 75:
         case 76:
         case 77:
         case 78:
         case 79:
         case 80:
         case 81:
         case 82:
         case 84:
         case 85:
         case 86:
         case 87:
         case 88:
         case 89:
         case 92:
         case 93:
         case 94:
         case 95:
         case 96:
         case 97:
         case 98:
         case 100:
         case 102:
         case 103:
         case 104:
         case 105:
         case 106:
         case 107:
         case 108:
         case 109:
         case 110:
         case 111:
         case 112:
         case 113:
         case 114:
         default:
            break;
         case 66:
            var4.visit(var3, (byte)this.readInt(this.items[this.readUnsignedShort(var1)]));
            var1 += 2;
            break;
         case 67:
            var4.visit(var3, (char)this.readInt(this.items[this.readUnsignedShort(var1)]));
            var1 += 2;
            break;
         case 68:
         case 70:
         case 73:
         case 74:
            var4.visit(var3, this.readConst(this.readUnsignedShort(var1), var2));
            var1 += 2;
            break;
         case 83:
            var4.visit(var3, (short)this.readInt(this.items[this.readUnsignedShort(var1)]));
            var1 += 2;
            break;
         case 90:
            var4.visit(var3, this.readInt(this.items[this.readUnsignedShort(var1)]) == 0 ? Boolean.FALSE : Boolean.TRUE);
            var1 += 2;
            break;
         case 91:
            int var5 = this.readUnsignedShort(var1);
            var1 += 2;
            if (var5 == 0) {
               return this.readAnnotationValues(var1 - 2, var2, false, var4.visitArray(var3));
            }

            int var7;
            switch(this.b[var1++] & 255) {
            case 66:
               byte[] var6 = new byte[var5];

               for(var7 = 0; var7 < var5; ++var7) {
                  var6[var7] = (byte)this.readInt(this.items[this.readUnsignedShort(var1)]);
                  var1 += 3;
               }

               var4.visit(var3, var6);
               --var1;
               return var1;
            case 67:
               char[] var10 = new char[var5];

               for(var7 = 0; var7 < var5; ++var7) {
                  var10[var7] = (char)this.readInt(this.items[this.readUnsignedShort(var1)]);
                  var1 += 3;
               }

               var4.visit(var3, var10);
               --var1;
               return var1;
            case 68:
               double[] var14 = new double[var5];

               for(var7 = 0; var7 < var5; ++var7) {
                  var14[var7] = Double.longBitsToDouble(this.readLong(this.items[this.readUnsignedShort(var1)]));
                  var1 += 3;
               }

               var4.visit(var3, var14);
               --var1;
               return var1;
            case 69:
            case 71:
            case 72:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            default:
               var1 = this.readAnnotationValues(var1 - 3, var2, false, var4.visitArray(var3));
               return var1;
            case 70:
               float[] var13 = new float[var5];

               for(var7 = 0; var7 < var5; ++var7) {
                  var13[var7] = Float.intBitsToFloat(this.readInt(this.items[this.readUnsignedShort(var1)]));
                  var1 += 3;
               }

               var4.visit(var3, var13);
               --var1;
               return var1;
            case 73:
               int[] var11 = new int[var5];

               for(var7 = 0; var7 < var5; ++var7) {
                  var11[var7] = this.readInt(this.items[this.readUnsignedShort(var1)]);
                  var1 += 3;
               }

               var4.visit(var3, var11);
               --var1;
               return var1;
            case 74:
               long[] var12 = new long[var5];

               for(var7 = 0; var7 < var5; ++var7) {
                  var12[var7] = this.readLong(this.items[this.readUnsignedShort(var1)]);
                  var1 += 3;
               }

               var4.visit(var3, var12);
               --var1;
               return var1;
            case 83:
               short[] var9 = new short[var5];

               for(var7 = 0; var7 < var5; ++var7) {
                  var9[var7] = (short)this.readInt(this.items[this.readUnsignedShort(var1)]);
                  var1 += 3;
               }

               var4.visit(var3, var9);
               --var1;
               return var1;
            case 90:
               boolean[] var8 = new boolean[var5];

               for(var7 = 0; var7 < var5; ++var7) {
                  var8[var7] = this.readInt(this.items[this.readUnsignedShort(var1)]) != 0;
                  var1 += 3;
               }

               var4.visit(var3, var8);
               --var1;
               return var1;
            }
         case 99:
            var4.visit(var3, Type.getType(this.readUTF8(var1, var2)));
            var1 += 2;
            break;
         case 101:
            var4.visitEnum(var3, this.readUTF8(var1, var2), this.readUTF8(var1 + 2, var2));
            var1 += 4;
            break;
         case 115:
            var4.visit(var3, this.readUTF8(var1, var2));
            var1 += 2;
         }

         return var1;
      }
   }

   private void getImplicitFrame(Context var1) {
      String var2 = var1.desc;
      Object[] var3 = var1.local;
      int var4 = 0;
      if ((var1.access & 8) == 0) {
         if ("<init>".equals(var1.name)) {
            var3[var4++] = Opcodes.UNINITIALIZED_THIS;
         } else {
            var3[var4++] = this.readClass(this.header + 2, var1.buffer);
         }
      }

      int var5 = 1;

      while(true) {
         int var6 = var5;
         switch(var2.charAt(var5++)) {
         case 'B':
         case 'C':
         case 'I':
         case 'S':
         case 'Z':
            var3[var4++] = Opcodes.INTEGER;
            break;
         case 'D':
            var3[var4++] = Opcodes.DOUBLE;
            break;
         case 'E':
         case 'G':
         case 'H':
         case 'K':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'T':
         case 'U':
         case 'V':
         case 'W':
         case 'X':
         case 'Y':
         default:
            var1.localCount = var4;
            return;
         case 'F':
            var3[var4++] = Opcodes.FLOAT;
            break;
         case 'J':
            var3[var4++] = Opcodes.LONG;
            break;
         case 'L':
            while(var2.charAt(var5) != ';') {
               ++var5;
            }

            var3[var4++] = var2.substring(var6 + 1, var5++);
            break;
         case '[':
            while(var2.charAt(var5) == '[') {
               ++var5;
            }

            if (var2.charAt(var5) == 'L') {
               ++var5;

               while(var2.charAt(var5) != ';') {
                  ++var5;
               }
            }

            int var10001 = var4++;
            ++var5;
            var3[var10001] = var2.substring(var6, var5);
         }
      }
   }

   private int readFrame(int var1, boolean var2, boolean var3, Context var4) {
      char[] var5 = var4.buffer;
      Label[] var6 = var4.labels;
      int var7;
      if (var2) {
         var7 = this.b[var1++] & 255;
      } else {
         var7 = 255;
         var4.offset = -1;
      }

      var4.localDiff = 0;
      int var8;
      if (var7 < 64) {
         var8 = var7;
         var4.mode = 3;
         var4.stackCount = 0;
      } else if (var7 < 128) {
         var8 = var7 - 64;
         var1 = this.readFrameType(var4.stack, 0, var1, var5, var6);
         var4.mode = 4;
         var4.stackCount = 1;
      } else {
         var8 = this.readUnsignedShort(var1);
         var1 += 2;
         if (var7 == 247) {
            var1 = this.readFrameType(var4.stack, 0, var1, var5, var6);
            var4.mode = 4;
            var4.stackCount = 1;
         } else if (var7 >= 248 && var7 < 251) {
            var4.mode = 2;
            var4.localDiff = 251 - var7;
            var4.localCount -= var4.localDiff;
            var4.stackCount = 0;
         } else if (var7 == 251) {
            var4.mode = 3;
            var4.stackCount = 0;
         } else {
            int var9;
            int var10;
            if (var7 < 255) {
               var9 = var3 ? var4.localCount : 0;

               for(var10 = var7 - 251; var10 > 0; --var10) {
                  var1 = this.readFrameType(var4.local, var9++, var1, var5, var6);
               }

               var4.mode = 1;
               var4.localDiff = var7 - 251;
               var4.localCount += var4.localDiff;
               var4.stackCount = 0;
            } else {
               var4.mode = 0;
               var9 = this.readUnsignedShort(var1);
               var1 += 2;
               var4.localDiff = var9;
               var4.localCount = var9;

               for(var10 = 0; var9 > 0; --var9) {
                  var1 = this.readFrameType(var4.local, var10++, var1, var5, var6);
               }

               var9 = this.readUnsignedShort(var1);
               var1 += 2;
               var4.stackCount = var9;

               for(var10 = 0; var9 > 0; --var9) {
                  var1 = this.readFrameType(var4.stack, var10++, var1, var5, var6);
               }
            }
         }
      }

      var4.offset += var8 + 1;
      this.readLabel(var4.offset, var6);
      return var1;
   }

   private int readFrameType(Object[] var1, int var2, int var3, char[] var4, Label[] var5) {
      int var6 = this.b[var3++] & 255;
      switch(var6) {
      case 0:
         var1[var2] = Opcodes.TOP;
         break;
      case 1:
         var1[var2] = Opcodes.INTEGER;
         break;
      case 2:
         var1[var2] = Opcodes.FLOAT;
         break;
      case 3:
         var1[var2] = Opcodes.DOUBLE;
         break;
      case 4:
         var1[var2] = Opcodes.LONG;
         break;
      case 5:
         var1[var2] = Opcodes.NULL;
         break;
      case 6:
         var1[var2] = Opcodes.UNINITIALIZED_THIS;
         break;
      case 7:
         var1[var2] = this.readClass(var3, var4);
         var3 += 2;
         break;
      default:
         var1[var2] = this.readLabel(this.readUnsignedShort(var3), var5);
         var3 += 2;
      }

      return var3;
   }

   protected Label readLabel(int var1, Label[] var2) {
      if (var2[var1] == null) {
         var2[var1] = new Label();
      }

      return var2[var1];
   }

   private int getAttributes() {
      int var1 = this.header + 8 + this.readUnsignedShort(this.header + 6) * 2;

      int var2;
      int var3;
      for(var2 = this.readUnsignedShort(var1); var2 > 0; --var2) {
         for(var3 = this.readUnsignedShort(var1 + 8); var3 > 0; --var3) {
            var1 += 6 + this.readInt(var1 + 12);
         }

         var1 += 8;
      }

      var1 += 2;

      for(var2 = this.readUnsignedShort(var1); var2 > 0; --var2) {
         for(var3 = this.readUnsignedShort(var1 + 8); var3 > 0; --var3) {
            var1 += 6 + this.readInt(var1 + 12);
         }

         var1 += 8;
      }

      return var1 + 2;
   }

   private Attribute readAttribute(Attribute[] var1, String var2, int var3, int var4, char[] var5, int var6, Label[] var7) {
      for(int var8 = 0; var8 < var1.length; ++var8) {
         if (var1[var8].type.equals(var2)) {
            return var1[var8].read(this, var3, var4, var5, var6, var7);
         }
      }

      return (new Attribute(var2)).read(this, var3, var4, (char[])null, -1, (Label[])null);
   }

   public int getItemCount() {
      return this.items.length;
   }

   public int getItem(int var1) {
      return this.items[var1];
   }

   public int getMaxStringLength() {
      return this.maxStringLength;
   }

   public int readByte(int var1) {
      return this.b[var1] & 255;
   }

   public int readUnsignedShort(int var1) {
      byte[] var2 = this.b;
      return (var2[var1] & 255) << 8 | var2[var1 + 1] & 255;
   }

   public short readShort(int var1) {
      byte[] var2 = this.b;
      return (short)((var2[var1] & 255) << 8 | var2[var1 + 1] & 255);
   }

   public int readInt(int var1) {
      byte[] var2 = this.b;
      return (var2[var1] & 255) << 24 | (var2[var1 + 1] & 255) << 16 | (var2[var1 + 2] & 255) << 8 | var2[var1 + 3] & 255;
   }

   public long readLong(int var1) {
      long var2 = (long)this.readInt(var1);
      long var4 = (long)this.readInt(var1 + 4) & 4294967295L;
      return var2 << 32 | var4;
   }

   public String readUTF8(int var1, char[] var2) {
      int var3 = this.readUnsignedShort(var1);
      if (var1 != 0 && var3 != 0) {
         String var4 = this.strings[var3];
         if (var4 != null) {
            return var4;
         } else {
            var1 = this.items[var3];
            return this.strings[var3] = this.readUTF(var1 + 2, this.readUnsignedShort(var1), var2);
         }
      } else {
         return null;
      }
   }

   private String readUTF(int var1, int var2, char[] var3) {
      int var4 = var1 + var2;
      byte[] var5 = this.b;
      int var6 = 0;
      byte var7 = 0;
      char var8 = 0;

      while(true) {
         while(var1 < var4) {
            byte var9 = var5[var1++];
            switch(var7) {
            case 0:
               int var10 = var9 & 255;
               if (var10 < 128) {
                  var3[var6++] = (char)var10;
               } else {
                  if (var10 < 224 && var10 > 191) {
                     var8 = (char)(var10 & 31);
                     var7 = 1;
                     continue;
                  }

                  var8 = (char)(var10 & 15);
                  var7 = 2;
               }
               break;
            case 1:
               var3[var6++] = (char)(var8 << 6 | var9 & 63);
               var7 = 0;
               break;
            case 2:
               var8 = (char)(var8 << 6 | var9 & 63);
               var7 = 1;
            }
         }

         return new String(var3, 0, var6);
      }
   }

   public String readClass(int var1, char[] var2) {
      return this.readUTF8(this.items[this.readUnsignedShort(var1)], var2);
   }

   public Object readConst(int var1, char[] var2) {
      int var3 = this.items[var1];
      switch(this.b[var3 - 1]) {
      case 3:
         return this.readInt(var3);
      case 4:
         return Float.intBitsToFloat(this.readInt(var3));
      case 5:
         return this.readLong(var3);
      case 6:
         return Double.longBitsToDouble(this.readLong(var3));
      case 7:
         return Type.getObjectType(this.readUTF8(var3, var2));
      case 8:
         return this.readUTF8(var3, var2);
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      default:
         int var4 = this.readByte(var3);
         int[] var5 = this.items;
         int var6 = var5[this.readUnsignedShort(var3 + 1)];
         boolean var7 = this.b[var6 - 1] == 11;
         String var8 = this.readClass(var6, var2);
         var6 = var5[this.readUnsignedShort(var6 + 2)];
         String var9 = this.readUTF8(var6, var2);
         String var10 = this.readUTF8(var6 + 2, var2);
         return new Handle(var4, var8, var9, var10, var7);
      case 16:
         return Type.getMethodType(this.readUTF8(var3, var2));
      }
   }
}
