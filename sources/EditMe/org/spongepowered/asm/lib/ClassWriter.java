package org.spongepowered.asm.lib;

public class ClassWriter extends ClassVisitor {
   public static final int COMPUTE_MAXS = 1;
   public static final int COMPUTE_FRAMES = 2;
   static final int ACC_SYNTHETIC_ATTRIBUTE = 262144;
   static final int TO_ACC_SYNTHETIC = 64;
   static final int NOARG_INSN = 0;
   static final int SBYTE_INSN = 1;
   static final int SHORT_INSN = 2;
   static final int VAR_INSN = 3;
   static final int IMPLVAR_INSN = 4;
   static final int TYPE_INSN = 5;
   static final int FIELDORMETH_INSN = 6;
   static final int ITFMETH_INSN = 7;
   static final int INDYMETH_INSN = 8;
   static final int LABEL_INSN = 9;
   static final int LABELW_INSN = 10;
   static final int LDC_INSN = 11;
   static final int LDCW_INSN = 12;
   static final int IINC_INSN = 13;
   static final int TABL_INSN = 14;
   static final int LOOK_INSN = 15;
   static final int MANA_INSN = 16;
   static final int WIDE_INSN = 17;
   static final int ASM_LABEL_INSN = 18;
   static final int F_INSERT = 256;
   static final byte[] TYPE;
   static final int CLASS = 7;
   static final int FIELD = 9;
   static final int METH = 10;
   static final int IMETH = 11;
   static final int STR = 8;
   static final int INT = 3;
   static final int FLOAT = 4;
   static final int LONG = 5;
   static final int DOUBLE = 6;
   static final int NAME_TYPE = 12;
   static final int UTF8 = 1;
   static final int MTYPE = 16;
   static final int HANDLE = 15;
   static final int INDY = 18;
   static final int HANDLE_BASE = 20;
   static final int TYPE_NORMAL = 30;
   static final int TYPE_UNINIT = 31;
   static final int TYPE_MERGED = 32;
   static final int BSM = 33;
   ClassReader cr;
   int version;
   int index;
   final ByteVector pool;
   Item[] items;
   int threshold;
   final Item key;
   final Item key2;
   final Item key3;
   final Item key4;
   Item[] typeTable;
   private short typeCount;
   private int access;
   private int name;
   String thisName;
   private int signature;
   private int superName;
   private int interfaceCount;
   private int[] interfaces;
   private int sourceFile;
   private ByteVector sourceDebug;
   private int enclosingMethodOwner;
   private int enclosingMethod;
   private AnnotationWriter anns;
   private AnnotationWriter ianns;
   private AnnotationWriter tanns;
   private AnnotationWriter itanns;
   private Attribute attrs;
   private int innerClassesCount;
   private ByteVector innerClasses;
   int bootstrapMethodsCount;
   ByteVector bootstrapMethods;
   FieldWriter firstField;
   FieldWriter lastField;
   MethodWriter firstMethod;
   MethodWriter lastMethod;
   private int compute;
   boolean hasAsmInsns;

   public ClassWriter(int var1) {
      super(327680);
      this.index = 1;
      this.pool = new ByteVector();
      this.items = new Item[256];
      this.threshold = (int)(0.75D * (double)this.items.length);
      this.key = new Item();
      this.key2 = new Item();
      this.key3 = new Item();
      this.key4 = new Item();
      this.compute = (var1 & 2) != 0 ? 0 : ((var1 & 1) != 0 ? 2 : 3);
   }

   public ClassWriter(ClassReader var1, int var2) {
      this(var2);
      var1.copyPool(this);
      this.cr = var1;
   }

   public final void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      this.version = var1;
      this.access = var2;
      this.name = this.newClass(var3);
      this.thisName = var3;
      if (var4 != null) {
         this.signature = this.newUTF8(var4);
      }

      this.superName = var5 == null ? 0 : this.newClass(var5);
      if (var6 != null && var6.length > 0) {
         this.interfaceCount = var6.length;
         this.interfaces = new int[this.interfaceCount];

         for(int var7 = 0; var7 < this.interfaceCount; ++var7) {
            this.interfaces[var7] = this.newClass(var6[var7]);
         }
      }

   }

   public final void visitSource(String var1, String var2) {
      if (var1 != null) {
         this.sourceFile = this.newUTF8(var1);
      }

      if (var2 != null) {
         this.sourceDebug = (new ByteVector()).encodeUTF8(var2, 0, Integer.MAX_VALUE);
      }

   }

   public final void visitOuterClass(String var1, String var2, String var3) {
      this.enclosingMethodOwner = this.newClass(var1);
      if (var2 != null && var3 != null) {
         this.enclosingMethod = this.newNameType(var2, var3);
      }

   }

   public final AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      ByteVector var3 = new ByteVector();
      var3.putShort(this.newUTF8(var1)).putShort(0);
      AnnotationWriter var4 = new AnnotationWriter(this, true, var3, var3, 2);
      if (var2) {
         var4.next = this.anns;
         this.anns = var4;
      } else {
         var4.next = this.ianns;
         this.ianns = var4;
      }

      return var4;
   }

   public final AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      ByteVector var5 = new ByteVector();
      AnnotationWriter.putTarget(var1, var2, var5);
      var5.putShort(this.newUTF8(var3)).putShort(0);
      AnnotationWriter var6 = new AnnotationWriter(this, true, var5, var5, var5.length - 2);
      if (var4) {
         var6.next = this.tanns;
         this.tanns = var6;
      } else {
         var6.next = this.itanns;
         this.itanns = var6;
      }

      return var6;
   }

   public final void visitAttribute(Attribute var1) {
      var1.next = this.attrs;
      this.attrs = var1;
   }

   public final void visitInnerClass(String var1, String var2, String var3, int var4) {
      if (this.innerClasses == null) {
         this.innerClasses = new ByteVector();
      }

      Item var5 = this.newClassItem(var1);
      if (var5.intVal == 0) {
         ++this.innerClassesCount;
         this.innerClasses.putShort(var5.index);
         this.innerClasses.putShort(var2 == null ? 0 : this.newClass(var2));
         this.innerClasses.putShort(var3 == null ? 0 : this.newUTF8(var3));
         this.innerClasses.putShort(var4);
         var5.intVal = this.innerClassesCount;
      }

   }

   public final FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      return new FieldWriter(this, var1, var2, var3, var4, var5);
   }

   public final MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      return new MethodWriter(this, var1, var2, var3, var4, var5, this.compute);
   }

   public final void visitEnd() {
   }

   public byte[] toByteArray() {
      if (this.index > 65535) {
         throw new RuntimeException("Class file too large!");
      } else {
         int var1 = 24 + 2 * this.interfaceCount;
         int var2 = 0;

         FieldWriter var3;
         for(var3 = this.firstField; var3 != null; var3 = (FieldWriter)var3.fv) {
            ++var2;
            var1 += var3.getSize();
         }

         int var4 = 0;

         MethodWriter var5;
         for(var5 = this.firstMethod; var5 != null; var5 = (MethodWriter)var5.mv) {
            ++var4;
            var1 += var5.getSize();
         }

         int var6 = 0;
         if (this.bootstrapMethods != null) {
            ++var6;
            var1 += 8 + this.bootstrapMethods.length;
            this.newUTF8("BootstrapMethods");
         }

         if (this.signature != 0) {
            ++var6;
            var1 += 8;
            this.newUTF8("Signature");
         }

         if (this.sourceFile != 0) {
            ++var6;
            var1 += 8;
            this.newUTF8("SourceFile");
         }

         if (this.sourceDebug != null) {
            ++var6;
            var1 += this.sourceDebug.length + 6;
            this.newUTF8("SourceDebugExtension");
         }

         if (this.enclosingMethodOwner != 0) {
            ++var6;
            var1 += 10;
            this.newUTF8("EnclosingMethod");
         }

         if ((this.access & 131072) != 0) {
            ++var6;
            var1 += 6;
            this.newUTF8("Deprecated");
         }

         if ((this.access & 4096) != 0 && ((this.version & '\uffff') < 49 || (this.access & 262144) != 0)) {
            ++var6;
            var1 += 6;
            this.newUTF8("Synthetic");
         }

         if (this.innerClasses != null) {
            ++var6;
            var1 += 8 + this.innerClasses.length;
            this.newUTF8("InnerClasses");
         }

         if (this.anns != null) {
            ++var6;
            var1 += 8 + this.anns.getSize();
            this.newUTF8("RuntimeVisibleAnnotations");
         }

         if (this.ianns != null) {
            ++var6;
            var1 += 8 + this.ianns.getSize();
            this.newUTF8("RuntimeInvisibleAnnotations");
         }

         if (this.tanns != null) {
            ++var6;
            var1 += 8 + this.tanns.getSize();
            this.newUTF8("RuntimeVisibleTypeAnnotations");
         }

         if (this.itanns != null) {
            ++var6;
            var1 += 8 + this.itanns.getSize();
            this.newUTF8("RuntimeInvisibleTypeAnnotations");
         }

         if (this.attrs != null) {
            var6 += this.attrs.getCount();
            var1 += this.attrs.getSize(this, (byte[])null, 0, -1, -1);
         }

         var1 += this.pool.length;
         ByteVector var7 = new ByteVector(var1);
         var7.putInt(-889275714).putInt(this.version);
         var7.putShort(this.index).putByteArray(this.pool.data, 0, this.pool.length);
         int var8 = 393216 | (this.access & 262144) / 64;
         var7.putShort(this.access & ~var8).putShort(this.name).putShort(this.superName);
         var7.putShort(this.interfaceCount);

         int var9;
         for(var9 = 0; var9 < this.interfaceCount; ++var9) {
            var7.putShort(this.interfaces[var9]);
         }

         var7.putShort(var2);

         for(var3 = this.firstField; var3 != null; var3 = (FieldWriter)var3.fv) {
            var3.put(var7);
         }

         var7.putShort(var4);

         for(var5 = this.firstMethod; var5 != null; var5 = (MethodWriter)var5.mv) {
            var5.put(var7);
         }

         var7.putShort(var6);
         if (this.bootstrapMethods != null) {
            var7.putShort(this.newUTF8("BootstrapMethods"));
            var7.putInt(this.bootstrapMethods.length + 2).putShort(this.bootstrapMethodsCount);
            var7.putByteArray(this.bootstrapMethods.data, 0, this.bootstrapMethods.length);
         }

         if (this.signature != 0) {
            var7.putShort(this.newUTF8("Signature")).putInt(2).putShort(this.signature);
         }

         if (this.sourceFile != 0) {
            var7.putShort(this.newUTF8("SourceFile")).putInt(2).putShort(this.sourceFile);
         }

         if (this.sourceDebug != null) {
            var9 = this.sourceDebug.length;
            var7.putShort(this.newUTF8("SourceDebugExtension")).putInt(var9);
            var7.putByteArray(this.sourceDebug.data, 0, var9);
         }

         if (this.enclosingMethodOwner != 0) {
            var7.putShort(this.newUTF8("EnclosingMethod")).putInt(4);
            var7.putShort(this.enclosingMethodOwner).putShort(this.enclosingMethod);
         }

         if ((this.access & 131072) != 0) {
            var7.putShort(this.newUTF8("Deprecated")).putInt(0);
         }

         if ((this.access & 4096) != 0 && ((this.version & '\uffff') < 49 || (this.access & 262144) != 0)) {
            var7.putShort(this.newUTF8("Synthetic")).putInt(0);
         }

         if (this.innerClasses != null) {
            var7.putShort(this.newUTF8("InnerClasses"));
            var7.putInt(this.innerClasses.length + 2).putShort(this.innerClassesCount);
            var7.putByteArray(this.innerClasses.data, 0, this.innerClasses.length);
         }

         if (this.anns != null) {
            var7.putShort(this.newUTF8("RuntimeVisibleAnnotations"));
            this.anns.put(var7);
         }

         if (this.ianns != null) {
            var7.putShort(this.newUTF8("RuntimeInvisibleAnnotations"));
            this.ianns.put(var7);
         }

         if (this.tanns != null) {
            var7.putShort(this.newUTF8("RuntimeVisibleTypeAnnotations"));
            this.tanns.put(var7);
         }

         if (this.itanns != null) {
            var7.putShort(this.newUTF8("RuntimeInvisibleTypeAnnotations"));
            this.itanns.put(var7);
         }

         if (this.attrs != null) {
            this.attrs.put(this, (byte[])null, 0, -1, -1, var7);
         }

         if (this.hasAsmInsns) {
            this.anns = null;
            this.ianns = null;
            this.attrs = null;
            this.innerClassesCount = 0;
            this.innerClasses = null;
            this.firstField = null;
            this.lastField = null;
            this.firstMethod = null;
            this.lastMethod = null;
            this.compute = 1;
            this.hasAsmInsns = false;
            (new ClassReader(var7.data)).accept(this, 264);
            return this.toByteArray();
         } else {
            return var7.data;
         }
      }
   }

   Item newConstItem(Object var1) {
      int var8;
      if (var1 instanceof Integer) {
         var8 = (Integer)var1;
         return this.newInteger(var8);
      } else if (var1 instanceof Byte) {
         var8 = ((Byte)var1).intValue();
         return this.newInteger(var8);
      } else if (var1 instanceof Character) {
         char var10 = (Character)var1;
         return this.newInteger(var10);
      } else if (var1 instanceof Short) {
         var8 = ((Short)var1).intValue();
         return this.newInteger(var8);
      } else if (var1 instanceof Boolean) {
         var8 = (Boolean)var1 ? 1 : 0;
         return this.newInteger(var8);
      } else if (var1 instanceof Float) {
         float var7 = (Float)var1;
         return this.newFloat(var7);
      } else if (var1 instanceof Long) {
         long var9 = (Long)var1;
         return this.newLong(var9);
      } else if (var1 instanceof Double) {
         double var3 = (Double)var1;
         return this.newDouble(var3);
      } else if (var1 instanceof String) {
         return this.newString((String)var1);
      } else if (var1 instanceof Type) {
         Type var6 = (Type)var1;
         int var5 = var6.getSort();
         if (var5 == 10) {
            return this.newClassItem(var6.getInternalName());
         } else {
            return var5 == 11 ? this.newMethodTypeItem(var6.getDescriptor()) : this.newClassItem(var6.getDescriptor());
         }
      } else if (var1 instanceof Handle) {
         Handle var2 = (Handle)var1;
         return this.newHandleItem(var2.tag, var2.owner, var2.name, var2.desc, var2.itf);
      } else {
         throw new IllegalArgumentException("value " + var1);
      }
   }

   public int newConst(Object var1) {
      return this.newConstItem(var1).index;
   }

   public int newUTF8(String var1) {
      this.key.set(1, var1, (String)null, (String)null);
      Item var2 = this.get(this.key);
      if (var2 == null) {
         this.pool.putByte(1).putUTF8(var1);
         var2 = new Item(this.index++, this.key);
         this.put(var2);
      }

      return var2.index;
   }

   Item newClassItem(String var1) {
      this.key2.set(7, var1, (String)null, (String)null);
      Item var2 = this.get(this.key2);
      if (var2 == null) {
         this.pool.put12(7, this.newUTF8(var1));
         var2 = new Item(this.index++, this.key2);
         this.put(var2);
      }

      return var2;
   }

   public int newClass(String var1) {
      return this.newClassItem(var1).index;
   }

   Item newMethodTypeItem(String var1) {
      this.key2.set(16, var1, (String)null, (String)null);
      Item var2 = this.get(this.key2);
      if (var2 == null) {
         this.pool.put12(16, this.newUTF8(var1));
         var2 = new Item(this.index++, this.key2);
         this.put(var2);
      }

      return var2;
   }

   public int newMethodType(String var1) {
      return this.newMethodTypeItem(var1).index;
   }

   Item newHandleItem(int var1, String var2, String var3, String var4, boolean var5) {
      this.key4.set(20 + var1, var2, var3, var4);
      Item var6 = this.get(this.key4);
      if (var6 == null) {
         if (var1 <= 4) {
            this.put112(15, var1, this.newField(var2, var3, var4));
         } else {
            this.put112(15, var1, this.newMethod(var2, var3, var4, var5));
         }

         var6 = new Item(this.index++, this.key4);
         this.put(var6);
      }

      return var6;
   }

   /** @deprecated */
   @Deprecated
   public int newHandle(int var1, String var2, String var3, String var4) {
      return this.newHandle(var1, var2, var3, var4, var1 == 9);
   }

   public int newHandle(int var1, String var2, String var3, String var4, boolean var5) {
      return this.newHandleItem(var1, var2, var3, var4, var5).index;
   }

   Item newInvokeDynamicItem(String var1, String var2, Handle var3, Object... var4) {
      ByteVector var5 = this.bootstrapMethods;
      if (var5 == null) {
         var5 = this.bootstrapMethods = new ByteVector();
      }

      int var6 = var5.length;
      int var7 = var3.hashCode();
      var5.putShort(this.newHandle(var3.tag, var3.owner, var3.name, var3.desc, var3.isInterface()));
      int var8 = var4.length;
      var5.putShort(var8);

      for(int var9 = 0; var9 < var8; ++var9) {
         Object var10 = var4[var9];
         var7 ^= var10.hashCode();
         var5.putShort(this.newConst(var10));
      }

      byte[] var14 = var5.data;
      int var15 = 2 + var8 << 1;
      var7 &= Integer.MAX_VALUE;
      Item var11 = this.items[var7 % this.items.length];

      int var12;
      label78:
      while(var11 != null) {
         if (var11.type == 33 && var11.hashCode == var7) {
            var12 = var11.intVal;
            int var13 = 0;

            while(true) {
               if (var13 >= var15) {
                  break label78;
               }

               if (var14[var6 + var13] != var14[var12 + var13]) {
                  var11 = var11.next;
                  break;
               }

               ++var13;
            }
         } else {
            var11 = var11.next;
         }
      }

      if (var11 != null) {
         var12 = var11.index;
         var5.length = var6;
      } else {
         var12 = this.bootstrapMethodsCount++;
         var11 = new Item(var12);
         var11.set(var6, var7);
         this.put(var11);
      }

      this.key3.set(var1, var2, var12);
      var11 = this.get(this.key3);
      if (var11 == null) {
         this.put122(18, var12, this.newNameType(var1, var2));
         var11 = new Item(this.index++, this.key3);
         this.put(var11);
      }

      return var11;
   }

   public int newInvokeDynamic(String var1, String var2, Handle var3, Object... var4) {
      return this.newInvokeDynamicItem(var1, var2, var3, var4).index;
   }

   Item newFieldItem(String var1, String var2, String var3) {
      this.key3.set(9, var1, var2, var3);
      Item var4 = this.get(this.key3);
      if (var4 == null) {
         this.put122(9, this.newClass(var1), this.newNameType(var2, var3));
         var4 = new Item(this.index++, this.key3);
         this.put(var4);
      }

      return var4;
   }

   public int newField(String var1, String var2, String var3) {
      return this.newFieldItem(var1, var2, var3).index;
   }

   Item newMethodItem(String var1, String var2, String var3, boolean var4) {
      int var5 = var4 ? 11 : 10;
      this.key3.set(var5, var1, var2, var3);
      Item var6 = this.get(this.key3);
      if (var6 == null) {
         this.put122(var5, this.newClass(var1), this.newNameType(var2, var3));
         var6 = new Item(this.index++, this.key3);
         this.put(var6);
      }

      return var6;
   }

   public int newMethod(String var1, String var2, String var3, boolean var4) {
      return this.newMethodItem(var1, var2, var3, var4).index;
   }

   Item newInteger(int var1) {
      this.key.set(var1);
      Item var2 = this.get(this.key);
      if (var2 == null) {
         this.pool.putByte(3).putInt(var1);
         var2 = new Item(this.index++, this.key);
         this.put(var2);
      }

      return var2;
   }

   Item newFloat(float var1) {
      this.key.set(var1);
      Item var2 = this.get(this.key);
      if (var2 == null) {
         this.pool.putByte(4).putInt(this.key.intVal);
         var2 = new Item(this.index++, this.key);
         this.put(var2);
      }

      return var2;
   }

   Item newLong(long var1) {
      this.key.set(var1);
      Item var3 = this.get(this.key);
      if (var3 == null) {
         this.pool.putByte(5).putLong(var1);
         var3 = new Item(this.index, this.key);
         this.index += 2;
         this.put(var3);
      }

      return var3;
   }

   Item newDouble(double var1) {
      this.key.set(var1);
      Item var3 = this.get(this.key);
      if (var3 == null) {
         this.pool.putByte(6).putLong(this.key.longVal);
         var3 = new Item(this.index, this.key);
         this.index += 2;
         this.put(var3);
      }

      return var3;
   }

   private Item newString(String var1) {
      this.key2.set(8, var1, (String)null, (String)null);
      Item var2 = this.get(this.key2);
      if (var2 == null) {
         this.pool.put12(8, this.newUTF8(var1));
         var2 = new Item(this.index++, this.key2);
         this.put(var2);
      }

      return var2;
   }

   public int newNameType(String var1, String var2) {
      return this.newNameTypeItem(var1, var2).index;
   }

   Item newNameTypeItem(String var1, String var2) {
      this.key2.set(12, var1, var2, (String)null);
      Item var3 = this.get(this.key2);
      if (var3 == null) {
         this.put122(12, this.newUTF8(var1), this.newUTF8(var2));
         var3 = new Item(this.index++, this.key2);
         this.put(var3);
      }

      return var3;
   }

   int addType(String var1) {
      this.key.set(30, var1, (String)null, (String)null);
      Item var2 = this.get(this.key);
      if (var2 == null) {
         var2 = this.addType(this.key);
      }

      return var2.index;
   }

   int addUninitializedType(String var1, int var2) {
      this.key.type = 31;
      this.key.intVal = var2;
      this.key.strVal1 = var1;
      this.key.hashCode = Integer.MAX_VALUE & 31 + var1.hashCode() + var2;
      Item var3 = this.get(this.key);
      if (var3 == null) {
         var3 = this.addType(this.key);
      }

      return var3.index;
   }

   private Item addType(Item var1) {
      ++this.typeCount;
      Item var2 = new Item(this.typeCount, this.key);
      this.put(var2);
      if (this.typeTable == null) {
         this.typeTable = new Item[16];
      }

      if (this.typeCount == this.typeTable.length) {
         Item[] var3 = new Item[2 * this.typeTable.length];
         System.arraycopy(this.typeTable, 0, var3, 0, this.typeTable.length);
         this.typeTable = var3;
      }

      this.typeTable[this.typeCount] = var2;
      return var2;
   }

   int getMergedType(int var1, int var2) {
      this.key2.type = 32;
      this.key2.longVal = (long)var1 | (long)var2 << 32;
      this.key2.hashCode = Integer.MAX_VALUE & 32 + var1 + var2;
      Item var3 = this.get(this.key2);
      if (var3 == null) {
         String var4 = this.typeTable[var1].strVal1;
         String var5 = this.typeTable[var2].strVal1;
         this.key2.intVal = this.addType(this.getCommonSuperClass(var4, var5));
         var3 = new Item(0, this.key2);
         this.put(var3);
      }

      return var3.intVal;
   }

   protected String getCommonSuperClass(String var1, String var2) {
      ClassLoader var3 = this.getClass().getClassLoader();

      Class var4;
      Class var5;
      try {
         var4 = Class.forName(var1.replace('/', '.'), false, var3);
         var5 = Class.forName(var2.replace('/', '.'), false, var3);
      } catch (Exception var7) {
         throw new RuntimeException(var7.toString());
      }

      if (var4.isAssignableFrom(var5)) {
         return var1;
      } else if (var5.isAssignableFrom(var4)) {
         return var2;
      } else if (!var4.isInterface() && !var5.isInterface()) {
         do {
            var4 = var4.getSuperclass();
         } while(!var4.isAssignableFrom(var5));

         return var4.getName().replace('.', '/');
      } else {
         return "java/lang/Object";
      }
   }

   private Item get(Item var1) {
      Item var2;
      for(var2 = this.items[var1.hashCode % this.items.length]; var2 != null && (var2.type != var1.type || !var1.isEqualTo(var2)); var2 = var2.next) {
      }

      return var2;
   }

   private void put(Item var1) {
      int var2;
      if (this.index + this.typeCount > this.threshold) {
         var2 = this.items.length;
         int var3 = var2 * 2 + 1;
         Item[] var4 = new Item[var3];

         Item var8;
         for(int var5 = var2 - 1; var5 >= 0; --var5) {
            for(Item var6 = this.items[var5]; var6 != null; var6 = var8) {
               int var7 = var6.hashCode % var4.length;
               var8 = var6.next;
               var6.next = var4[var7];
               var4[var7] = var6;
            }
         }

         this.items = var4;
         this.threshold = (int)((double)var3 * 0.75D);
      }

      var2 = var1.hashCode % this.items.length;
      var1.next = this.items[var2];
      this.items[var2] = var1;
   }

   private void put122(int var1, int var2, int var3) {
      this.pool.put12(var1, var2).putShort(var3);
   }

   private void put112(int var1, int var2, int var3) {
      this.pool.put11(var1, var2).putShort(var3);
   }

   static {
      byte[] var0 = new byte[220];
      String var1 = "AAAAAAAAAAAAAAAABCLMMDDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAADDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAANAAAAAAAAAAAAAAAAAAAAJJJJJJJJJJJJJJJJDOPAAAAAAGGGGGGGHIFBFAAFFAARQJJKKSSSSSSSSSSSSSSSSSS";

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var0[var2] = (byte)(var1.charAt(var2) - 65);
      }

      TYPE = var0;
   }
}
