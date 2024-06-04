package org.lwjgl.util.mapped;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.MemoryUtil;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.BasicValue;
import org.objectweb.asm.tree.analysis.Frame;
import org.objectweb.asm.tree.analysis.SimpleVerifier;
import org.objectweb.asm.util.TraceClassVisitor;
import sun.misc.Unsafe;

public class MappedObjectTransformer
{
  static final boolean PRINT_ACTIVITY;
  static final boolean PRINT_TIMING;
  static final boolean PRINT_BYTECODE;
  static final Map<String, MappedSubtypeInfo> className_to_subtype;
  static final String MAPPED_OBJECT_JVM;
  static final String MAPPED_HELPER_JVM;
  static final String MAPPEDSET_PREFIX;
  static final String MAPPED_SET2_JVM;
  static final String MAPPED_SET3_JVM;
  static final String MAPPED_SET4_JVM;
  static final String CACHE_LINE_PAD_JVM;
  static final String VIEWADDRESS_METHOD_NAME = "getViewAddress";
  static final String NEXT_METHOD_NAME = "next";
  static final String ALIGN_METHOD_NAME = "getAlign";
  static final String SIZEOF_METHOD_NAME = "getSizeof";
  static final String CAPACITY_METHOD_NAME = "capacity";
  static final String VIEW_CONSTRUCTOR_NAME = "constructView$LWJGL";
  static final Map<Integer, String> OPCODE_TO_NAME;
  static final Map<Integer, String> INSNTYPE_TO_NAME;
  static boolean is_currently_computing_frames;
  
  static
  {
    PRINT_ACTIVITY = (LWJGLUtil.DEBUG) && (LWJGLUtil.getPrivilegedBoolean("org.lwjgl.util.mapped.PrintActivity"));
    PRINT_TIMING = (PRINT_ACTIVITY) && (LWJGLUtil.getPrivilegedBoolean("org.lwjgl.util.mapped.PrintTiming"));
    PRINT_BYTECODE = (LWJGLUtil.DEBUG) && (LWJGLUtil.getPrivilegedBoolean("org.lwjgl.util.mapped.PrintBytecode"));
    


    MAPPED_OBJECT_JVM = jvmClassName(MappedObject.class);
    MAPPED_HELPER_JVM = jvmClassName(MappedHelper.class);
    
    MAPPEDSET_PREFIX = jvmClassName(MappedSet.class);
    MAPPED_SET2_JVM = jvmClassName(MappedSet2.class);
    MAPPED_SET3_JVM = jvmClassName(MappedSet3.class);
    MAPPED_SET4_JVM = jvmClassName(MappedSet4.class);
    
    CACHE_LINE_PAD_JVM = "L" + jvmClassName(CacheLinePad.class) + ";";
    










    OPCODE_TO_NAME = new HashMap();
    INSNTYPE_TO_NAME = new HashMap();
    



    getClassEnums(Opcodes.class, OPCODE_TO_NAME, new String[] { "V1_", "ACC_", "T_", "F_", "MH_" });
    getClassEnums(AbstractInsnNode.class, INSNTYPE_TO_NAME, new String[0]);
    
    className_to_subtype = new HashMap();
    















    className_to_subtype.put(MAPPED_OBJECT_JVM, new MappedSubtypeInfo(MAPPED_OBJECT_JVM, null, -1, -1, -1, false));
    

    String vmName = System.getProperty("java.vm.name");
    if ((vmName != null) && (!vmName.contains("Server"))) {
      System.err.println("Warning: " + MappedObject.class.getSimpleName() + "s have inferiour performance on Client VMs, please consider switching to a Server VM.");
    }
  }
  





  public static void register(Class<? extends MappedObject> type)
  {
    if (MappedObjectClassLoader.FORKED) {
      return;
    }
    MappedType mapped = (MappedType)type.getAnnotation(MappedType.class);
    
    if ((mapped != null) && (mapped.padding() < 0)) {
      throw new ClassFormatError("Invalid mapped type padding: " + mapped.padding());
    }
    if ((type.getEnclosingClass() != null) && (!Modifier.isStatic(type.getModifiers()))) {
      throw new InternalError("only top-level or static inner classes are allowed");
    }
    String className = jvmClassName(type);
    Map<String, FieldInfo> fields = new HashMap();
    
    long sizeof = 0L;
    for (Field field : type.getDeclaredFields()) {
      FieldInfo fieldInfo = registerField((mapped == null) || (mapped.autoGenerateOffsets()), className, sizeof, field);
      if (fieldInfo != null)
      {

        fields.put(field.getName(), fieldInfo);
        
        sizeof = Math.max(sizeof, offset + lengthPadded);
      }
    }
    int align = 4;
    int padding = 0;
    boolean cacheLinePadded = false;
    
    if (mapped != null) {
      align = mapped.align();
      if (mapped.cacheLinePadding()) {
        if (mapped.padding() != 0) {
          throw new ClassFormatError("Mapped type padding cannot be specified together with cacheLinePadding.");
        }
        int cacheLineMod = (int)(sizeof % CacheUtil.getCacheLineSize());
        if (cacheLineMod != 0) {
          padding = CacheUtil.getCacheLineSize() - cacheLineMod;
        }
        cacheLinePadded = true;
      } else {
        padding = mapped.padding();
      }
    }
    sizeof += padding;
    
    MappedSubtypeInfo mappedType = new MappedSubtypeInfo(className, fields, (int)sizeof, align, padding, cacheLinePadded);
    if (className_to_subtype.put(className, mappedType) != null)
      throw new InternalError("duplicate mapped type: " + className);
  }
  
  private static FieldInfo registerField(boolean autoGenerateOffsets, String className, long advancingOffset, Field field) {
    if (Modifier.isStatic(field.getModifiers())) {
      return null;
    }
    
    if ((!field.getType().isPrimitive()) && (field.getType() != ByteBuffer.class)) {
      throw new ClassFormatError("field '" + className + "." + field.getName() + "' not supported: " + field.getType());
    }
    MappedField meta = (MappedField)field.getAnnotation(MappedField.class);
    if ((meta == null) && (!autoGenerateOffsets)) {
      throw new ClassFormatError("field '" + className + "." + field.getName() + "' missing annotation " + MappedField.class.getName() + ": " + className);
    }
    Pointer pointer = (Pointer)field.getAnnotation(Pointer.class);
    if ((pointer != null) && (field.getType() != Long.TYPE)) {
      throw new ClassFormatError("The @Pointer annotation can only be used on long fields. @Pointer field found: " + className + "." + field.getName() + ": " + field.getType());
    }
    if ((Modifier.isVolatile(field.getModifiers())) && ((pointer != null) || (field.getType() == ByteBuffer.class))) {
      throw new ClassFormatError("The volatile keyword is not supported for @Pointer or ByteBuffer fields. Volatile field found: " + className + "." + field.getName() + ": " + field.getType());
    }
    
    long byteLength;
    if ((field.getType() == Long.TYPE) || (field.getType() == Double.TYPE)) { long byteLength;
      if (pointer == null) {
        byteLength = 8L;
      } else
        byteLength = MappedObjectUnsafe.INSTANCE.addressSize(); } else { long byteLength;
      if (field.getType() == Double.TYPE) {
        byteLength = 8L; } else { long byteLength;
        if ((field.getType() == Integer.TYPE) || (field.getType() == Float.TYPE)) {
          byteLength = 4L; } else { long byteLength;
          if ((field.getType() == Character.TYPE) || (field.getType() == Short.TYPE)) {
            byteLength = 2L; } else { long byteLength;
            if (field.getType() == Byte.TYPE) {
              byteLength = 1L;
            } else if (field.getType() == ByteBuffer.class) {
              long byteLength = meta.byteLength();
              if (byteLength < 0L)
                throw new IllegalStateException("invalid byte length for mapped ByteBuffer field: " + className + "." + field.getName() + " [length=" + byteLength + "]");
            } else {
              throw new ClassFormatError(field.getType().getName()); } } } } }
    long byteLength;
    if ((field.getType() != ByteBuffer.class) && (advancingOffset % byteLength != 0L)) {
      throw new IllegalStateException("misaligned mapped type: " + className + "." + field.getName());
    }
    CacheLinePad pad = (CacheLinePad)field.getAnnotation(CacheLinePad.class);
    
    long byteOffset = advancingOffset;
    if ((meta != null) && (meta.byteOffset() != -1L)) {
      if (meta.byteOffset() < 0L)
        throw new ClassFormatError("Invalid field byte offset: " + className + "." + field.getName() + " [byteOffset=" + meta.byteOffset() + "]");
      if (pad != null) {
        throw new ClassFormatError("A field byte offset cannot be specified together with cache-line padding: " + className + "." + field.getName());
      }
      byteOffset = meta.byteOffset();
    }
    
    long byteLengthPadded = byteLength;
    if (pad != null)
    {
      if ((pad.before()) && (byteOffset % CacheUtil.getCacheLineSize() != 0L)) {
        byteOffset += CacheUtil.getCacheLineSize() - (byteOffset & CacheUtil.getCacheLineSize() - 1);
      }
      
      if ((pad.after()) && ((byteOffset + byteLength) % CacheUtil.getCacheLineSize() != 0L)) {
        byteLengthPadded += CacheUtil.getCacheLineSize() - (byteOffset + byteLength) % CacheUtil.getCacheLineSize();
      }
      assert ((!pad.before()) || (byteOffset % CacheUtil.getCacheLineSize() == 0L));
      assert ((!pad.after()) || ((byteOffset + byteLengthPadded) % CacheUtil.getCacheLineSize() == 0L));
    }
    
    if (PRINT_ACTIVITY) {
      LWJGLUtil.log(MappedObjectTransformer.class.getSimpleName() + ": " + className + "." + field.getName() + " [type=" + field.getType().getSimpleName() + ", offset=" + byteOffset + "]");
    }
    return new FieldInfo(byteOffset, byteLength, byteLengthPadded, Type.getType(field.getType()), Modifier.isVolatile(field.getModifiers()), pointer != null);
  }
  
  static byte[] transformMappedObject(byte[] bytecode)
  {
    ClassWriter cw = new ClassWriter(0);
    
    ClassVisitor cv = new ClassAdapter(cw)
    {
      private final String[] DEFINALIZE_LIST = { "getViewAddress", "next", "getAlign", "getSizeof", "capacity" };
      





      public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
      {
        for (String method : DEFINALIZE_LIST) {
          if (name.equals(method)) {
            access &= 0xFFFFFFEF;
            break;
          }
        }
        return super.visitMethod(access, name, desc, signature, exceptions);
      }
      
    };
    new ClassReader(bytecode).accept(cv, 0);
    return cw.toByteArray();
  }
  
  static byte[] transformMappedAPI(String className, byte[] bytecode) {
    ClassWriter cw = new ClassWriter(2)
    {

      protected String getCommonSuperClass(String a, String b)
      {
        if (((MappedObjectTransformer.is_currently_computing_frames) && (!a.startsWith("java/"))) || (!b.startsWith("java/"))) {
          return "java/lang/Object";
        }
        return super.getCommonSuperClass(a, b);
      }
      

    };
    TransformationAdapter ta = new TransformationAdapter(cw, className);
    
    ClassVisitor cv = ta;
    if (className_to_subtype.containsKey(className)) {
      cv = getMethodGenAdapter(className, cv);
    }
    new ClassReader(bytecode).accept(cv, 4);
    
    if (!transformed) {
      return bytecode;
    }
    bytecode = cw.toByteArray();
    if (PRINT_BYTECODE) {
      printBytecode(bytecode);
    }
    return bytecode;
  }
  
  private static ClassAdapter getMethodGenAdapter(final String className, ClassVisitor cv) {
    new ClassAdapter(cv)
    {
      public void visitEnd()
      {
        MappedObjectTransformer.MappedSubtypeInfo mappedSubtype = (MappedObjectTransformer.MappedSubtypeInfo)MappedObjectTransformer.className_to_subtype.get(className);
        
        generateViewAddressGetter();
        generateCapacity();
        generateAlignGetter(mappedSubtype);
        generateSizeofGetter();
        generateNext();
        
        for (String fieldName : fields.keySet()) {
          MappedObjectTransformer.FieldInfo field = (MappedObjectTransformer.FieldInfo)fields.get(fieldName);
          
          if (type.getDescriptor().length() > 1) {
            generateByteBufferGetter(fieldName, field);
          } else {
            generateFieldGetter(fieldName, field);
            generateFieldSetter(fieldName, field);
          }
        }
        
        super.visitEnd();
      }
      
      private void generateViewAddressGetter() {
        MethodVisitor mv = super.visitMethod(1, "getViewAddress", "(I)J", null, null);
        mv.visitCode();
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, MappedObjectTransformer.MAPPED_OBJECT_JVM, "baseAddress", "J");
        mv.visitVarInsn(21, 1);
        mv.visitFieldInsn(178, className, "SIZEOF", "I");
        mv.visitInsn(104);
        mv.visitInsn(133);
        mv.visitInsn(97);
        if (MappedObject.CHECKS) {
          mv.visitInsn(92);
          mv.visitVarInsn(25, 0);
          mv.visitMethodInsn(184, MappedObjectTransformer.MAPPED_HELPER_JVM, "checkAddress", "(JL" + MappedObjectTransformer.MAPPED_OBJECT_JVM + ";)V");
        }
        mv.visitInsn(173);
        mv.visitMaxs(3, 2);
        mv.visitEnd();
      }
      
      private void generateCapacity()
      {
        MethodVisitor mv = super.visitMethod(1, "capacity", "()I", null, null);
        mv.visitCode();
        mv.visitVarInsn(25, 0);
        mv.visitMethodInsn(182, MappedObjectTransformer.MAPPED_OBJECT_JVM, "backingByteBuffer", "()L" + MappedObjectTransformer.jvmClassName(ByteBuffer.class) + ";");
        mv.visitInsn(89);
        mv.visitMethodInsn(182, MappedObjectTransformer.jvmClassName(ByteBuffer.class), "capacity", "()I");
        mv.visitInsn(95);
        mv.visitMethodInsn(184, MappedObjectTransformer.jvmClassName(MemoryUtil.class), "getAddress0", "(L" + MappedObjectTransformer.jvmClassName(Buffer.class) + ";)J");
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, MappedObjectTransformer.MAPPED_OBJECT_JVM, "baseAddress", "J");
        mv.visitInsn(101);
        mv.visitInsn(136);
        mv.visitInsn(96);
        mv.visitFieldInsn(178, className, "SIZEOF", "I");
        mv.visitInsn(108);
        mv.visitInsn(172);
        mv.visitMaxs(3, 1);
        mv.visitEnd();
      }
      
      private void generateAlignGetter(MappedObjectTransformer.MappedSubtypeInfo mappedSubtype) {
        MethodVisitor mv = super.visitMethod(1, "getAlign", "()I", null, null);
        mv.visitCode();
        MappedObjectTransformer.visitIntNode(mv, sizeof);
        mv.visitInsn(172);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
      }
      
      private void generateSizeofGetter() {
        MethodVisitor mv = super.visitMethod(1, "getSizeof", "()I", null, null);
        mv.visitCode();
        mv.visitFieldInsn(178, className, "SIZEOF", "I");
        mv.visitInsn(172);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
      }
      
      private void generateNext() {
        MethodVisitor mv = super.visitMethod(1, "next", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(25, 0);
        mv.visitInsn(89);
        mv.visitFieldInsn(180, MappedObjectTransformer.MAPPED_OBJECT_JVM, "viewAddress", "J");
        mv.visitFieldInsn(178, className, "SIZEOF", "I");
        mv.visitInsn(133);
        mv.visitInsn(97);
        mv.visitMethodInsn(182, className, "setViewAddress", "(J)V");
        mv.visitInsn(177);
        mv.visitMaxs(3, 1);
        mv.visitEnd();
      }
      
      private void generateByteBufferGetter(String fieldName, MappedObjectTransformer.FieldInfo field) {
        MethodVisitor mv = super.visitMethod(9, MappedObjectTransformer.getterName(fieldName), "(L" + className + ";I)" + type.getDescriptor(), null, null);
        mv.visitCode();
        mv.visitVarInsn(25, 0);
        mv.visitVarInsn(21, 1);
        mv.visitMethodInsn(182, className, "getViewAddress", "(I)J");
        MappedObjectTransformer.visitIntNode(mv, (int)offset);
        mv.visitInsn(133);
        mv.visitInsn(97);
        MappedObjectTransformer.visitIntNode(mv, (int)length);
        mv.visitMethodInsn(184, MappedObjectTransformer.MAPPED_HELPER_JVM, "newBuffer", "(JI)L" + MappedObjectTransformer.jvmClassName(ByteBuffer.class) + ";");
        mv.visitInsn(176);
        mv.visitMaxs(3, 2);
        mv.visitEnd();
      }
      
      private void generateFieldGetter(String fieldName, MappedObjectTransformer.FieldInfo field) {
        MethodVisitor mv = super.visitMethod(9, MappedObjectTransformer.getterName(fieldName), "(L" + className + ";I)" + type.getDescriptor(), null, null);
        mv.visitCode();
        mv.visitVarInsn(25, 0);
        mv.visitVarInsn(21, 1);
        mv.visitMethodInsn(182, className, "getViewAddress", "(I)J");
        MappedObjectTransformer.visitIntNode(mv, (int)offset);
        mv.visitInsn(133);
        mv.visitInsn(97);
        mv.visitMethodInsn(184, MappedObjectTransformer.MAPPED_HELPER_JVM, field.getAccessType() + "get", "(J)" + type.getDescriptor());
        mv.visitInsn(type.getOpcode(172));
        mv.visitMaxs(3, 2);
        mv.visitEnd();
      }
      
      private void generateFieldSetter(String fieldName, MappedObjectTransformer.FieldInfo field) {
        MethodVisitor mv = super.visitMethod(9, MappedObjectTransformer.setterName(fieldName), "(L" + className + ";I" + type.getDescriptor() + ")V", null, null);
        mv.visitCode();
        int load = 0;
        switch (type.getSort()) {
        case 1: 
        case 2: 
        case 3: 
        case 4: 
        case 5: 
          load = 21;
          break;
        case 6: 
          load = 23;
          break;
        case 7: 
          load = 22;
          break;
        case 8: 
          load = 24;
        }
        
        mv.visitVarInsn(load, 2);
        mv.visitVarInsn(25, 0);
        mv.visitVarInsn(21, 1);
        mv.visitMethodInsn(182, className, "getViewAddress", "(I)J");
        MappedObjectTransformer.visitIntNode(mv, (int)offset);
        mv.visitInsn(133);
        mv.visitInsn(97);
        mv.visitMethodInsn(184, MappedObjectTransformer.MAPPED_HELPER_JVM, field.getAccessType() + "put", "(" + type.getDescriptor() + "J)V");
        mv.visitInsn(177);
        mv.visitMaxs(4, 4);
        mv.visitEnd();
      }
    };
  }
  
  private static class TransformationAdapter
    extends ClassAdapter
  {
    final String className;
    boolean transformed;
    
    TransformationAdapter(ClassVisitor cv, String className)
    {
      super();
      this.className = className;
    }
    

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value)
    {
      MappedObjectTransformer.MappedSubtypeInfo mappedSubtype = (MappedObjectTransformer.MappedSubtypeInfo)MappedObjectTransformer.className_to_subtype.get(className);
      if ((mappedSubtype != null) && (fields.containsKey(name))) {
        if (MappedObjectTransformer.PRINT_ACTIVITY)
          LWJGLUtil.log(MappedObjectTransformer.class.getSimpleName() + ": discarding field: " + className + "." + name + ":" + desc);
        return null;
      }
      
      if ((access & 0x8) == 0) {
        new FieldNode(access, name, desc, signature, value) {
          public void visitEnd() {
            if (visibleAnnotations == null) {
              accept(cv);
              return;
            }
            
            boolean before = false;
            boolean after = false;
            int byteLength = 0;
            for (AnnotationNode pad : visibleAnnotations) {
              if (MappedObjectTransformer.CACHE_LINE_PAD_JVM.equals(desc)) {
                if (("J".equals(desc)) || ("D".equals(desc))) {
                  byteLength = 8;
                } else if (("I".equals(desc)) || ("F".equals(desc))) {
                  byteLength = 4;
                } else if (("S".equals(desc)) || ("C".equals(desc))) {
                  byteLength = 2;
                } else if (("B".equals(desc)) || ("Z".equals(desc))) {
                  byteLength = 1;
                } else {
                  throw new ClassFormatError("The @CacheLinePad annotation cannot be used on non-primitive fields: " + className + "." + name);
                }
                transformed = true;
                
                after = true;
                if (values == null) break;
                for (int i = 0; i < values.size(); i += 2) {
                  boolean value = values.get(i + 1).equals(Boolean.TRUE);
                  if ("before".equals(values.get(i))) {
                    before = value;
                  } else {
                    after = value;
                  }
                }
                




                break;
              }
            }
            



            if (before) {
              int count = CacheUtil.getCacheLineSize() / byteLength - 1;
              for (int i = count; i >= 1; i--) {
                cv.visitField(access | 0x1 | 0x1000, name + "$PAD_" + i, desc, signature, null);
              }
            }
            accept(cv);
            
            if (after) {
              int count = CacheUtil.getCacheLineSize() / byteLength - 1;
              for (int i = 1; i <= count; i++)
                cv.visitField(access | 0x1 | 0x1000, name + "$PAD" + i, desc, signature, null);
            }
          }
        };
      }
      return super.visitField(access, name, desc, signature, value);
    }
    

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
    {
      if ("<init>".equals(name)) {
        MappedObjectTransformer.MappedSubtypeInfo mappedSubtype = (MappedObjectTransformer.MappedSubtypeInfo)MappedObjectTransformer.className_to_subtype.get(className);
        if (mappedSubtype != null) {
          if (!"()V".equals(desc)) {
            throw new ClassFormatError(className + " can only have a default constructor, found: " + desc);
          }
          MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
          mv.visitVarInsn(25, 0);
          mv.visitMethodInsn(183, MappedObjectTransformer.MAPPED_OBJECT_JVM, "<init>", "()V");
          mv.visitInsn(177);
          mv.visitMaxs(0, 0);
          

          name = "constructView$LWJGL";
        }
      }
      
      final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
      new MethodNode(access, name, desc, signature, exceptions)
      {
        boolean needsTransformation;
        

        public void visitMaxs(int a, int b)
        {
          try
          {
            MappedObjectTransformer.is_currently_computing_frames = true;
            super.visitMaxs(a, b);
          } finally {
            MappedObjectTransformer.is_currently_computing_frames = false;
          }
        }
        
        public void visitFieldInsn(int opcode, String owner, String name, String desc)
        {
          if ((MappedObjectTransformer.className_to_subtype.containsKey(owner)) || (owner.startsWith(MappedObjectTransformer.MAPPEDSET_PREFIX))) {
            needsTransformation = true;
          }
          super.visitFieldInsn(opcode, owner, name, desc);
        }
        
        public void visitMethodInsn(int opcode, String owner, String name, String desc)
        {
          if (MappedObjectTransformer.className_to_subtype.containsKey(owner)) {
            needsTransformation = true;
          }
          super.visitMethodInsn(opcode, owner, name, desc);
        }
        
        public void visitEnd()
        {
          if (needsTransformation)
          {
            transformed = true;
            try {
              transformMethod(analyse());
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          }
          

          accept(mv);
        }
        
        private Frame<BasicValue>[] analyse() throws AnalyzerException {
          Analyzer<BasicValue> a = new Analyzer(new SimpleVerifier());
          a.analyze(className, this);
          return a.getFrames();
        }
        
        private void transformMethod(Frame<BasicValue>[] frames) {
          InsnList instructions = this.instructions;
          
          Map<Integer, MappedObjectTransformer.MappedSubtypeInfo> arrayVars = new HashMap();
          





          Map<AbstractInsnNode, Frame<BasicValue>> frameMap = new HashMap();
          for (int i = 0; i < frames.length; i++) {
            frameMap.put(instructions.get(i), frames[i]);
          }
          for (int i = 0; i < instructions.size(); i++) {
            AbstractInsnNode instruction = instructions.get(i);
            


            switch (instruction.getType()) {
            case 2: 
              if (instruction.getOpcode() == 25) {
                VarInsnNode varInsn = (VarInsnNode)instruction;
                MappedObjectTransformer.MappedSubtypeInfo mappedSubtype = (MappedObjectTransformer.MappedSubtypeInfo)arrayVars.get(Integer.valueOf(var));
                if (mappedSubtype != null)
                  i = MappedObjectTransformer.transformArrayAccess(instructions, i, frameMap, varInsn, mappedSubtype, var); }
              break;
            
            case 4: 
              FieldInsnNode fieldInsn = (FieldInsnNode)instruction;
              
              InsnList list = MappedObjectTransformer.transformFieldAccess(fieldInsn);
              if (list != null) {
                i = MappedObjectTransformer.replace(instructions, i, instruction, list);
              }
              break;
            case 5: 
              MethodInsnNode methodInsn = (MethodInsnNode)instruction;
              MappedObjectTransformer.MappedSubtypeInfo mappedType = (MappedObjectTransformer.MappedSubtypeInfo)MappedObjectTransformer.className_to_subtype.get(owner);
              if (mappedType != null)
                i = MappedObjectTransformer.transformMethodCall(instructions, i, frameMap, methodInsn, mappedType, arrayVars);
              break;
            }
          }
        }
      };
    }
  }
  
  static int transformMethodCall(InsnList instructions, int i, Map<AbstractInsnNode, Frame<BasicValue>> frameMap, MethodInsnNode methodInsn, MappedSubtypeInfo mappedType, Map<Integer, MappedSubtypeInfo> arrayVars) {
    switch (methodInsn.getOpcode()) {
    case 182: 
      if (("asArray".equals(name)) && (desc.equals("()[L" + MAPPED_OBJECT_JVM + ";")))
      {
        AbstractInsnNode nextInstruction;
        

        checkInsnAfterIsArray(nextInstruction = methodInsn.getNext(), 192);
        checkInsnAfterIsArray(nextInstruction = nextInstruction.getNext(), 58);
        
        Frame<BasicValue> frame = (Frame)frameMap.get(nextInstruction);
        String targetType = ((BasicValue)frame.getStack(frame.getStackSize() - 1)).getType().getElementType().getInternalName();
        if (!owner.equals(targetType))
        {





          throw new ClassCastException("Source: " + owner + " - Target: " + targetType);
        }
        
        VarInsnNode varInstruction = (VarInsnNode)nextInstruction;
        
        arrayVars.put(Integer.valueOf(var), mappedType);
        
        instructions.remove(methodInsn.getNext());
        instructions.remove(methodInsn);
      }
      
      if (("dup".equals(name)) && (desc.equals("()L" + MAPPED_OBJECT_JVM + ";"))) {
        i = replace(instructions, i, methodInsn, generateDupInstructions(methodInsn));


      }
      else if (("slice".equals(name)) && (desc.equals("()L" + MAPPED_OBJECT_JVM + ";"))) {
        i = replace(instructions, i, methodInsn, generateSliceInstructions(methodInsn));


      }
      else if (("runViewConstructor".equals(name)) && ("()V".equals(desc))) {
        i = replace(instructions, i, methodInsn, generateRunViewConstructorInstructions(methodInsn));


      }
      else if (("copyTo".equals(name)) && (desc.equals("(L" + MAPPED_OBJECT_JVM + ";)V"))) {
        i = replace(instructions, i, methodInsn, generateCopyToInstructions(mappedType));


      }
      else if (("copyRange".equals(name)) && (desc.equals("(L" + MAPPED_OBJECT_JVM + ";I)V")))
        i = replace(instructions, i, methodInsn, generateCopyRangeInstructions(mappedType));
      break;
    



    case 183: 
      if ((owner.equals(MAPPED_OBJECT_JVM)) && ("<init>".equals(name)) && ("()V".equals(desc))) {
        instructions.remove(methodInsn.getPrevious());
        instructions.remove(methodInsn);
        
        i -= 2;
      }
      break;
    case 184: 
      boolean isMapDirectMethod = ("map".equals(name)) && (desc.equals("(JI)L" + MAPPED_OBJECT_JVM + ";"));
      boolean isMapBufferMethod = ("map".equals(name)) && (desc.equals("(Ljava/nio/ByteBuffer;)L" + MAPPED_OBJECT_JVM + ";"));
      boolean isMallocMethod = ("malloc".equals(name)) && (desc.equals("(I)L" + MAPPED_OBJECT_JVM + ";"));
      
      if ((isMapDirectMethod) || (isMapBufferMethod) || (isMallocMethod)) {
        i = replace(instructions, i, methodInsn, generateMapInstructions(mappedType, owner, isMapDirectMethod, isMallocMethod));
      }
      break;
    }
    return i;
  }
  
  private static InsnList generateCopyRangeInstructions(MappedSubtypeInfo mappedType) {
    InsnList list = new InsnList();
    

    list.add(getIntNode(sizeof));
    
    list.add(new InsnNode(104));
    
    list.add(new MethodInsnNode(184, MAPPED_HELPER_JVM, "copy", "(L" + MAPPED_OBJECT_JVM + ";L" + MAPPED_OBJECT_JVM + ";I)V"));
    

    return list;
  }
  
  private static InsnList generateCopyToInstructions(MappedSubtypeInfo mappedType) {
    InsnList list = new InsnList();
    

    list.add(getIntNode(sizeof - padding));
    
    list.add(new MethodInsnNode(184, MAPPED_HELPER_JVM, "copy", "(L" + MAPPED_OBJECT_JVM + ";L" + MAPPED_OBJECT_JVM + ";I)V"));
    

    return list;
  }
  
  private static InsnList generateRunViewConstructorInstructions(MethodInsnNode methodInsn) {
    InsnList list = new InsnList();
    

    list.add(new InsnNode(89));
    
    list.add(new MethodInsnNode(182, owner, "constructView$LWJGL", "()V"));
    

    return list;
  }
  
  private static InsnList generateSliceInstructions(MethodInsnNode methodInsn) {
    InsnList list = new InsnList();
    

    list.add(new TypeInsnNode(187, owner));
    
    list.add(new InsnNode(89));
    
    list.add(new MethodInsnNode(183, owner, "<init>", "()V"));
    
    list.add(new MethodInsnNode(184, MAPPED_HELPER_JVM, "slice", "(L" + MAPPED_OBJECT_JVM + ";L" + MAPPED_OBJECT_JVM + ";)L" + MAPPED_OBJECT_JVM + ";"));
    

    return list;
  }
  
  private static InsnList generateDupInstructions(MethodInsnNode methodInsn) {
    InsnList list = new InsnList();
    

    list.add(new TypeInsnNode(187, owner));
    
    list.add(new InsnNode(89));
    
    list.add(new MethodInsnNode(183, owner, "<init>", "()V"));
    
    list.add(new MethodInsnNode(184, MAPPED_HELPER_JVM, "dup", "(L" + MAPPED_OBJECT_JVM + ";L" + MAPPED_OBJECT_JVM + ";)L" + MAPPED_OBJECT_JVM + ";"));
    

    return list;
  }
  
  private static InsnList generateMapInstructions(MappedSubtypeInfo mappedType, String className, boolean mapDirectMethod, boolean mallocMethod) {
    InsnList trg = new InsnList();
    
    if (mallocMethod)
    {
      trg.add(getIntNode(sizeof));
      
      trg.add(new InsnNode(104));
      
      trg.add(new MethodInsnNode(184, cacheLinePadded ? jvmClassName(CacheUtil.class) : jvmClassName(BufferUtils.class), "createByteBuffer", "(I)L" + jvmClassName(ByteBuffer.class) + ";"));
    }
    else if (mapDirectMethod)
    {
      trg.add(new MethodInsnNode(184, MAPPED_HELPER_JVM, "newBuffer", "(JI)L" + jvmClassName(ByteBuffer.class) + ";"));
    }
    


    trg.add(new TypeInsnNode(187, className));
    
    trg.add(new InsnNode(89));
    
    trg.add(new MethodInsnNode(183, className, "<init>", "()V"));
    
    trg.add(new InsnNode(90));
    
    trg.add(new InsnNode(95));
    
    trg.add(getIntNode(align));
    
    trg.add(getIntNode(sizeof));
    
    trg.add(new MethodInsnNode(184, MAPPED_HELPER_JVM, "setup", "(L" + MAPPED_OBJECT_JVM + ";Ljava/nio/ByteBuffer;II)V"));
    

    return trg;
  }
  
  static InsnList transformFieldAccess(FieldInsnNode fieldInsn)
  {
    MappedSubtypeInfo mappedSubtype = (MappedSubtypeInfo)className_to_subtype.get(owner);
    if (mappedSubtype == null)
    {

      if (("view".equals(name)) && (owner.startsWith(MAPPEDSET_PREFIX))) {
        return generateSetViewInstructions(fieldInsn);
      }
      return null;
    }
    
    if ("SIZEOF".equals(name)) {
      return generateSIZEOFInstructions(fieldInsn, mappedSubtype);
    }
    if ("view".equals(name)) {
      return generateViewInstructions(fieldInsn, mappedSubtype);
    }
    if (("baseAddress".equals(name)) || ("viewAddress".equals(name))) {
      return generateAddressInstructions(fieldInsn);
    }
    
    FieldInfo field = (FieldInfo)fields.get(name);
    if (field == null) {
      return null;
    }
    
    if (desc.equals("L" + jvmClassName(ByteBuffer.class) + ";")) {
      return generateByteBufferInstructions(fieldInsn, mappedSubtype, offset);
    }
    
    return generateFieldInstructions(fieldInsn, field);
  }
  
  private static InsnList generateSetViewInstructions(FieldInsnNode fieldInsn) {
    if (fieldInsn.getOpcode() == 180)
      throwAccessErrorOnReadOnlyField(owner, name);
    if (fieldInsn.getOpcode() != 181) {
      throw new InternalError();
    }
    InsnList list = new InsnList();
    

    if (MAPPED_SET2_JVM.equals(owner)) {
      list.add(new MethodInsnNode(184, MAPPED_HELPER_JVM, "put_views", "(L" + MAPPED_SET2_JVM + ";I)V"));
    } else if (MAPPED_SET3_JVM.equals(owner)) {
      list.add(new MethodInsnNode(184, MAPPED_HELPER_JVM, "put_views", "(L" + MAPPED_SET3_JVM + ";I)V"));
    } else if (MAPPED_SET4_JVM.equals(owner)) {
      list.add(new MethodInsnNode(184, MAPPED_HELPER_JVM, "put_views", "(L" + MAPPED_SET4_JVM + ";I)V"));
    } else {
      throw new InternalError();
    }
    
    return list;
  }
  
  private static InsnList generateSIZEOFInstructions(FieldInsnNode fieldInsn, MappedSubtypeInfo mappedSubtype) {
    if (!"I".equals(desc)) {
      throw new InternalError();
    }
    InsnList list = new InsnList();
    
    if (fieldInsn.getOpcode() == 178) {
      list.add(getIntNode(sizeof));
      return list;
    }
    
    if (fieldInsn.getOpcode() == 179) {
      throwAccessErrorOnReadOnlyField(owner, name);
    }
    throw new InternalError();
  }
  
  private static InsnList generateViewInstructions(FieldInsnNode fieldInsn, MappedSubtypeInfo mappedSubtype) {
    if (!"I".equals(desc)) {
      throw new InternalError();
    }
    InsnList list = new InsnList();
    
    if (fieldInsn.getOpcode() == 180) {
      if (sizeof_shift != 0)
      {
        list.add(getIntNode(sizeof_shift));
        
        list.add(new MethodInsnNode(184, MAPPED_HELPER_JVM, "get_view_shift", "(L" + MAPPED_OBJECT_JVM + ";I)I"));
      }
      else
      {
        list.add(getIntNode(sizeof));
        
        list.add(new MethodInsnNode(184, MAPPED_HELPER_JVM, "get_view", "(L" + MAPPED_OBJECT_JVM + ";I)I"));
      }
      
      return list;
    }
    
    if (fieldInsn.getOpcode() == 181) {
      if (sizeof_shift != 0)
      {
        list.add(getIntNode(sizeof_shift));
        
        list.add(new MethodInsnNode(184, MAPPED_HELPER_JVM, "put_view_shift", "(L" + MAPPED_OBJECT_JVM + ";II)V"));
      }
      else
      {
        list.add(getIntNode(sizeof));
        
        list.add(new MethodInsnNode(184, MAPPED_HELPER_JVM, "put_view", "(L" + MAPPED_OBJECT_JVM + ";II)V"));
      }
      
      return list;
    }
    
    throw new InternalError();
  }
  
  private static InsnList generateAddressInstructions(FieldInsnNode fieldInsn) {
    if (!"J".equals(desc)) {
      throw new IllegalStateException();
    }
    if (fieldInsn.getOpcode() == 180) {
      return null;
    }
    if (fieldInsn.getOpcode() == 181) {
      throwAccessErrorOnReadOnlyField(owner, name);
    }
    throw new InternalError();
  }
  
  private static InsnList generateByteBufferInstructions(FieldInsnNode fieldInsn, MappedSubtypeInfo mappedSubtype, long fieldOffset) {
    if (fieldInsn.getOpcode() == 181) {
      throwAccessErrorOnReadOnlyField(owner, name);
    }
    if (fieldInsn.getOpcode() == 180) {
      InsnList list = new InsnList();
      

      list.add(new FieldInsnNode(180, className, "viewAddress", "J"));
      
      list.add(new LdcInsnNode(Long.valueOf(fieldOffset)));
      
      list.add(new InsnNode(97));
      
      list.add(new LdcInsnNode(Long.valueOf(fields.get(name)).length)));
      
      list.add(new InsnNode(136));
      
      list.add(new MethodInsnNode(184, MAPPED_HELPER_JVM, "newBuffer", "(JI)L" + jvmClassName(ByteBuffer.class) + ";"));
      

      return list;
    }
    
    throw new InternalError();
  }
  
  private static InsnList generateFieldInstructions(FieldInsnNode fieldInsn, FieldInfo field) {
    InsnList list = new InsnList();
    
    if (fieldInsn.getOpcode() == 181)
    {
      list.add(getIntNode((int)offset));
      
      list.add(new MethodInsnNode(184, MAPPED_HELPER_JVM, field.getAccessType() + "put", "(L" + MAPPED_OBJECT_JVM + ";" + desc + "I)V"));
      
      return list;
    }
    
    if (fieldInsn.getOpcode() == 180)
    {
      list.add(getIntNode((int)offset));
      
      list.add(new MethodInsnNode(184, MAPPED_HELPER_JVM, field.getAccessType() + "get", "(L" + MAPPED_OBJECT_JVM + ";I)" + desc));
      
      return list;
    }
    
    throw new InternalError();
  }
  
  static int transformArrayAccess(InsnList instructions, int i, Map<AbstractInsnNode, Frame<BasicValue>> frameMap, VarInsnNode loadInsn, MappedSubtypeInfo mappedSubtype, int var)
  {
    int loadStackSize = ((Frame)frameMap.get(loadInsn)).getStackSize() + 1;
    
    AbstractInsnNode nextInsn = loadInsn;
    for (;;)
    {
      nextInsn = nextInsn.getNext();
      if (nextInsn == null) {
        throw new InternalError();
      }
      Frame<BasicValue> frame = (Frame)frameMap.get(nextInsn);
      if (frame != null)
      {

        int stackSize = frame.getStackSize();
        
        if ((stackSize == loadStackSize + 1) && (nextInsn.getOpcode() == 50)) {
          AbstractInsnNode aaLoadInsn = nextInsn;
          do {
            for (;;) {
              nextInsn = nextInsn.getNext();
              if (nextInsn == null) {
                break label546;
              }
              frame = (Frame)frameMap.get(nextInsn);
              if (frame != null)
              {
                stackSize = frame.getStackSize();
                
                if ((stackSize == loadStackSize + 1) && (nextInsn.getOpcode() == 181)) {
                  FieldInsnNode fieldInsn = (FieldInsnNode)nextInsn;
                  

                  instructions.insert(nextInsn, new MethodInsnNode(184, className, setterName(name), "(L" + className + ";I" + desc + ")V"));
                  
                  instructions.remove(nextInsn);
                  break label546;
                }
                if ((stackSize == loadStackSize) && (nextInsn.getOpcode() == 180)) {
                  FieldInsnNode fieldInsn = (FieldInsnNode)nextInsn;
                  

                  instructions.insert(nextInsn, new MethodInsnNode(184, className, getterName(name), "(L" + className + ";I)" + desc));
                  
                  instructions.remove(nextInsn);
                  break label546;
                }
                if ((stackSize != loadStackSize) || (nextInsn.getOpcode() != 89) || (nextInsn.getNext().getOpcode() != 180))
                  break;
                FieldInsnNode fieldInsn = (FieldInsnNode)nextInsn.getNext();
                
                MethodInsnNode getter = new MethodInsnNode(184, className, getterName(name), "(L" + className + ";I)" + desc);
                

                instructions.insert(nextInsn, new InsnNode(92));
                
                instructions.insert(nextInsn.getNext(), getter);
                

                instructions.remove(nextInsn);
                instructions.remove(fieldInsn);
                
                nextInsn = getter;
              }
            } } while (stackSize >= loadStackSize);
          throw new ClassFormatError("Invalid " + className + " view array usage detected: " + getOpcodeName(nextInsn));
          
          label546:
          instructions.remove(aaLoadInsn);
          
          return i; }
        if ((stackSize == loadStackSize) && (nextInsn.getOpcode() == 190)) {
          if ((LWJGLUtil.DEBUG) && (loadInsn.getNext() != nextInsn)) {
            throw new InternalError();
          }
          instructions.remove(nextInsn);
          var = var;
          instructions.insert(loadInsn, new MethodInsnNode(182, className, "capacity", "()I"));
          
          return i + 1; }
        if (stackSize < loadStackSize)
          throw new ClassFormatError("Invalid " + className + " view array usage detected: " + getOpcodeName(nextInsn));
      }
    }
  }
  
  private static class FieldInfo {
    final long offset;
    final long length;
    final long lengthPadded;
    final Type type;
    final boolean isVolatile;
    final boolean isPointer;
    
    FieldInfo(long offset, long length, long lengthPadded, Type type, boolean isVolatile, boolean isPointer) {
      this.offset = offset;
      this.length = length;
      this.lengthPadded = lengthPadded;
      this.type = type;
      this.isVolatile = isVolatile;
      this.isPointer = isPointer;
    }
    
    String getAccessType() {
      return type.getDescriptor().toLowerCase() + (isVolatile ? "v" : "");
    }
  }
  

  private static class MappedSubtypeInfo
  {
    final String className;
    
    final int sizeof;
    final int sizeof_shift;
    final int align;
    final int padding;
    final boolean cacheLinePadded;
    final Map<String, MappedObjectTransformer.FieldInfo> fields;
    
    MappedSubtypeInfo(String className, Map<String, MappedObjectTransformer.FieldInfo> fields, int sizeof, int align, int padding, boolean cacheLinePadded)
    {
      this.className = className;
      
      this.sizeof = sizeof;
      if ((sizeof - 1 & sizeof) == 0) {
        sizeof_shift = getPoT(sizeof);
      } else
        sizeof_shift = 0;
      this.align = align;
      this.padding = padding;
      this.cacheLinePadded = cacheLinePadded;
      
      this.fields = fields;
    }
    
    private static int getPoT(int value) {
      int pot = -1;
      while (value > 0) {
        pot++;
        value >>= 1;
      }
      return pot;
    }
  }
  


  private static void getClassEnums(Class clazz, Map<Integer, String> map, String... prefixFilters)
  {
    try
    {
      label128:
      
      for (Field field : clazz.getFields())
        if ((Modifier.isStatic(field.getModifiers())) && (field.getType() == Integer.TYPE))
        {

          for (String filter : prefixFilters) {
            if (field.getName().startsWith(filter)) {
              break label128;
            }
          }
          if (map.put((Integer)field.get(null), field.getName()) != null)
            throw new IllegalStateException();
        }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  static String getOpcodeName(AbstractInsnNode insn) {
    String op = (String)OPCODE_TO_NAME.get(Integer.valueOf(insn.getOpcode()));
    return (String)INSNTYPE_TO_NAME.get(Integer.valueOf(insn.getType())) + ": " + insn.getOpcode() + (op == null ? "" : new StringBuilder().append(" [").append((String)OPCODE_TO_NAME.get(Integer.valueOf(insn.getOpcode()))).append("]").toString());
  }
  
  static String jvmClassName(Class<?> type) {
    return type.getName().replace('.', '/');
  }
  
  static String getterName(String fieldName) {
    return "get$" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1) + "$LWJGL";
  }
  
  static String setterName(String fieldName) {
    return "set$" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1) + "$LWJGL";
  }
  
  private static void checkInsnAfterIsArray(AbstractInsnNode instruction, int opcode) {
    if (instruction == null) {
      throw new ClassFormatError("Unexpected end of instructions after .asArray() method.");
    }
    if (instruction.getOpcode() != opcode)
      throw new ClassFormatError("The result of .asArray() must be stored to a local variable. Found: " + getOpcodeName(instruction));
  }
  
  static AbstractInsnNode getIntNode(int value) {
    if ((value <= 5) && (-1 <= value)) {
      return new InsnNode(2 + value + 1);
    }
    if ((value >= -128) && (value <= 127)) {
      return new IntInsnNode(16, value);
    }
    if ((value >= 32768) && (value <= 32767)) {
      return new IntInsnNode(17, value);
    }
    return new LdcInsnNode(Integer.valueOf(value));
  }
  
  static void visitIntNode(MethodVisitor mv, int value) {
    if ((value <= 5) && (-1 <= value)) {
      mv.visitInsn(2 + value + 1);
    } else if ((value >= -128) && (value <= 127)) {
      mv.visitIntInsn(16, value);
    } else if ((value >= 32768) && (value <= 32767)) {
      mv.visitIntInsn(17, value);
    } else {
      mv.visitLdcInsn(Integer.valueOf(value));
    }
  }
  
  static int replace(InsnList instructions, int i, AbstractInsnNode location, InsnList list) {
    int size = list.size();
    
    instructions.insert(location, list);
    instructions.remove(location);
    
    return i + (size - 1);
  }
  
  private static void throwAccessErrorOnReadOnlyField(String className, String fieldName) {
    throw new IllegalAccessError("The " + className + "." + fieldName + " field is final.");
  }
  
  private static void printBytecode(byte[] bytecode) {
    StringWriter sw = new StringWriter();
    ClassVisitor tracer = new TraceClassVisitor(new ClassWriter(0), new PrintWriter(sw));
    new ClassReader(bytecode).accept(tracer, 0);
    String dump = sw.toString();
    
    LWJGLUtil.log(dump);
  }
  
  public MappedObjectTransformer() {}
}
