package javassist.bytecode.stackmap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;

public abstract class TypeData {
   public static TypeData[] make(int var0) {
      TypeData[] var1 = new TypeData[var0];

      for(int var2 = 0; var2 < var0; ++var2) {
         var1[var2] = TypeTag.TOP;
      }

      return var1;
   }

   protected TypeData() {
   }

   private static void setType(TypeData var0, String var1, ClassPool var2) throws BadBytecode {
      var0.setType(var1, var2);
   }

   public abstract int getTypeTag();

   public abstract int getTypeData(ConstPool var1);

   public TypeData join() {
      return new TypeData.TypeVar(this);
   }

   public abstract TypeData.BasicType isBasicType();

   public abstract boolean is2WordType();

   public boolean isNullType() {
      return false;
   }

   public boolean isUninit() {
      return false;
   }

   public abstract boolean eq(TypeData var1);

   public abstract String getName();

   public abstract void setType(String var1, ClassPool var2) throws BadBytecode;

   public abstract TypeData getArrayType(int var1) throws NotFoundException;

   public int dfs(ArrayList var1, int var2, ClassPool var3) throws NotFoundException {
      return var2;
   }

   protected TypeData.TypeVar toTypeVar(int var1) {
      return null;
   }

   public void constructorCalled(int var1) {
   }

   public String toString() {
      return super.toString() + "(" + this.toString2(new HashSet()) + ")";
   }

   abstract String toString2(HashSet var1);

   public static CtClass commonSuperClassEx(CtClass var0, CtClass var1) throws NotFoundException {
      if (var0 == var1) {
         return var0;
      } else if (var0.isArray() && var1.isArray()) {
         CtClass var2 = var0.getComponentType();
         CtClass var3 = var1.getComponentType();
         CtClass var4 = commonSuperClassEx(var2, var3);
         if (var4 == var2) {
            return var0;
         } else {
            return var4 == var3 ? var1 : var0.getClassPool().get(var4 == null ? "java.lang.Object" : var4.getName() + "[]");
         }
      } else if (!var0.isPrimitive() && !var1.isPrimitive()) {
         return !var0.isArray() && !var1.isArray() ? commonSuperClass(var0, var1) : var0.getClassPool().get("java.lang.Object");
      } else {
         return null;
      }
   }

   public static CtClass commonSuperClass(CtClass var0, CtClass var1) throws NotFoundException {
      CtClass var2 = var0;
      CtClass var3 = var1;
      CtClass var5 = var0;

      while(true) {
         if (eq(var2, var3) && var2.getSuperclass() != null) {
            return var2;
         }

         CtClass var6 = var2.getSuperclass();
         CtClass var7 = var3.getSuperclass();
         if (var7 == null) {
            var3 = var1;
            break;
         }

         if (var6 == null) {
            var5 = var1;
            var2 = var3;
            var3 = var0;
            break;
         }

         var2 = var6;
         var3 = var7;
      }

      while(true) {
         var2 = var2.getSuperclass();
         if (var2 == null) {
            for(var2 = var5; !eq(var2, var3); var3 = var3.getSuperclass()) {
               var2 = var2.getSuperclass();
            }

            return var2;
         }

         var5 = var5.getSuperclass();
      }
   }

   static boolean eq(CtClass var0, CtClass var1) {
      return var0 == var1 || var0 != null && var1 != null && var0.getName().equals(var1.getName());
   }

   public static void aastore(TypeData var0, TypeData var1, ClassPool var2) throws BadBytecode {
      if (var0 instanceof TypeData.AbsTypeVar && !var1.isNullType()) {
         ((TypeData.AbsTypeVar)var0).merge(TypeData.ArrayType.make(var1));
      }

      if (var1 instanceof TypeData.AbsTypeVar) {
         if (var0 instanceof TypeData.AbsTypeVar) {
            TypeData.ArrayElement.make(var0);
         } else {
            if (!(var0 instanceof TypeData.ClassName)) {
               throw new BadBytecode("bad AASTORE: " + var0);
            }

            if (!var0.isNullType()) {
               String var3 = TypeData.ArrayElement.typeName(var0.getName());
               var1.setType(var3, var2);
            }
         }
      }

   }

   public static class UninitThis extends TypeData.UninitData {
      UninitThis(String var1) {
         super(-1, var1);
      }

      public TypeData.UninitData copy() {
         return new TypeData.UninitThis(this.getName());
      }

      public int getTypeTag() {
         return 6;
      }

      public int getTypeData(ConstPool var1) {
         return 0;
      }

      String toString2(HashSet var1) {
         return "uninit:this";
      }
   }

   public static class UninitData extends TypeData.ClassName {
      int offset;
      boolean initialized;

      UninitData(int var1, String var2) {
         super(var2);
         this.offset = var1;
         this.initialized = false;
      }

      public TypeData.UninitData copy() {
         return new TypeData.UninitData(this.offset, this.getName());
      }

      public int getTypeTag() {
         return 8;
      }

      public int getTypeData(ConstPool var1) {
         return this.offset;
      }

      public TypeData join() {
         return (TypeData)(this.initialized ? new TypeData.TypeVar(new TypeData.ClassName(this.getName())) : new TypeData.UninitTypeVar(this.copy()));
      }

      public boolean isUninit() {
         return true;
      }

      public boolean eq(TypeData var1) {
         if (!(var1 instanceof TypeData.UninitData)) {
            return false;
         } else {
            TypeData.UninitData var2 = (TypeData.UninitData)var1;
            return this.offset == var2.offset && this.getName().equals(var2.getName());
         }
      }

      public int offset() {
         return this.offset;
      }

      public void constructorCalled(int var1) {
         if (var1 == this.offset) {
            this.initialized = true;
         }

      }

      String toString2(HashSet var1) {
         return this.getName() + "," + this.offset;
      }
   }

   public static class NullType extends TypeData.ClassName {
      public NullType() {
         super("null-type");
      }

      public int getTypeTag() {
         return 5;
      }

      public boolean isNullType() {
         return true;
      }

      public int getTypeData(ConstPool var1) {
         return 0;
      }

      public TypeData getArrayType(int var1) {
         return this;
      }
   }

   public static class ClassName extends TypeData {
      private String name;

      public ClassName(String var1) {
         this.name = var1;
      }

      public String getName() {
         return this.name;
      }

      public TypeData.BasicType isBasicType() {
         return null;
      }

      public boolean is2WordType() {
         return false;
      }

      public int getTypeTag() {
         return 7;
      }

      public int getTypeData(ConstPool var1) {
         return var1.addClassInfo(this.getName());
      }

      public boolean eq(TypeData var1) {
         return this.name.equals(var1.getName());
      }

      public void setType(String var1, ClassPool var2) throws BadBytecode {
      }

      public TypeData getArrayType(int var1) throws NotFoundException {
         if (var1 == 0) {
            return this;
         } else if (var1 <= 0) {
            for(int var4 = 0; var4 < -var1; ++var4) {
               if (this.name.charAt(var4) != '[') {
                  throw new NotFoundException("no " + var1 + " dimensional array type: " + this.getName());
               }
            }

            char var5 = this.name.charAt(-var1);
            if (var5 == '[') {
               return new TypeData.ClassName(this.name.substring(-var1));
            } else if (var5 == 'L') {
               return new TypeData.ClassName(this.name.substring(-var1 + 1, this.name.length() - 1).replace('/', '.'));
            } else if (var5 == TypeTag.DOUBLE.decodedName) {
               return TypeTag.DOUBLE;
            } else if (var5 == TypeTag.FLOAT.decodedName) {
               return TypeTag.FLOAT;
            } else if (var5 == TypeTag.LONG.decodedName) {
               return TypeTag.LONG;
            } else {
               return TypeTag.INTEGER;
            }
         } else {
            char[] var2 = new char[var1];

            for(int var3 = 0; var3 < var1; ++var3) {
               var2[var3] = '[';
            }

            String var6 = this.getName();
            if (var6.charAt(0) != '[') {
               var6 = "L" + var6.replace('.', '/') + ";";
            }

            return new TypeData.ClassName(new String(var2) + var6);
         }
      }

      String toString2(HashSet var1) {
         return this.name;
      }
   }

   public static class UninitTypeVar extends TypeData.AbsTypeVar {
      protected TypeData type;

      public UninitTypeVar(TypeData.UninitData var1) {
         this.type = var1;
      }

      public int getTypeTag() {
         return this.type.getTypeTag();
      }

      public int getTypeData(ConstPool var1) {
         return this.type.getTypeData(var1);
      }

      public TypeData.BasicType isBasicType() {
         return this.type.isBasicType();
      }

      public boolean is2WordType() {
         return this.type.is2WordType();
      }

      public boolean isUninit() {
         return this.type.isUninit();
      }

      public boolean eq(TypeData var1) {
         return this.type.eq(var1);
      }

      public String getName() {
         return this.type.getName();
      }

      protected TypeData.TypeVar toTypeVar(int var1) {
         return null;
      }

      public TypeData join() {
         return this.type.join();
      }

      public void setType(String var1, ClassPool var2) throws BadBytecode {
         this.type.setType(var1, var2);
      }

      public void merge(TypeData var1) {
         if (!var1.eq(this.type)) {
            this.type = TypeTag.TOP;
         }

      }

      public void constructorCalled(int var1) {
         this.type.constructorCalled(var1);
      }

      public int offset() {
         if (this.type instanceof TypeData.UninitData) {
            return ((TypeData.UninitData)this.type).offset;
         } else {
            throw new RuntimeException("not available");
         }
      }

      public TypeData getArrayType(int var1) throws NotFoundException {
         return this.type.getArrayType(var1);
      }

      String toString2(HashSet var1) {
         return "";
      }
   }

   public static class ArrayElement extends TypeData.AbsTypeVar {
      private TypeData.AbsTypeVar array;

      private ArrayElement(TypeData.AbsTypeVar var1) {
         this.array = var1;
      }

      public static TypeData make(TypeData var0) throws BadBytecode {
         if (var0 instanceof TypeData.ArrayType) {
            return ((TypeData.ArrayType)var0).elementType();
         } else if (var0 instanceof TypeData.AbsTypeVar) {
            return new TypeData.ArrayElement((TypeData.AbsTypeVar)var0);
         } else if (var0 instanceof TypeData.ClassName && !var0.isNullType()) {
            return new TypeData.ClassName(typeName(var0.getName()));
         } else {
            throw new BadBytecode("bad AASTORE: " + var0);
         }
      }

      public void merge(TypeData var1) {
         try {
            if (!var1.isNullType()) {
               this.array.merge(TypeData.ArrayType.make(var1));
            }

         } catch (BadBytecode var3) {
            throw new RuntimeException("fatal: " + var3);
         }
      }

      public String getName() {
         return typeName(this.array.getName());
      }

      public TypeData.AbsTypeVar arrayType() {
         return this.array;
      }

      public TypeData.BasicType isBasicType() {
         return null;
      }

      public boolean is2WordType() {
         return false;
      }

      private static String typeName(String var0) {
         if (var0.length() > 1 && var0.charAt(0) == '[') {
            char var1 = var0.charAt(1);
            if (var1 == 'L') {
               return var0.substring(2, var0.length() - 1).replace('/', '.');
            }

            if (var1 == '[') {
               return var0.substring(1);
            }
         }

         return "java.lang.Object";
      }

      public void setType(String var1, ClassPool var2) throws BadBytecode {
         this.array.setType(TypeData.ArrayType.typeName(var1), var2);
      }

      protected TypeData.TypeVar toTypeVar(int var1) {
         return this.array.toTypeVar(var1 - 1);
      }

      public TypeData getArrayType(int var1) throws NotFoundException {
         return this.array.getArrayType(var1 - 1);
      }

      public int dfs(ArrayList var1, int var2, ClassPool var3) throws NotFoundException {
         return this.array.dfs(var1, var2, var3);
      }

      String toString2(HashSet var1) {
         return "*" + this.array.toString2(var1);
      }

      static String access$000(String var0) {
         return typeName(var0);
      }
   }

   public static class ArrayType extends TypeData.AbsTypeVar {
      private TypeData.AbsTypeVar element;

      private ArrayType(TypeData.AbsTypeVar var1) {
         this.element = var1;
      }

      static TypeData make(TypeData var0) throws BadBytecode {
         if (var0 instanceof TypeData.ArrayElement) {
            return ((TypeData.ArrayElement)var0).arrayType();
         } else if (var0 instanceof TypeData.AbsTypeVar) {
            return new TypeData.ArrayType((TypeData.AbsTypeVar)var0);
         } else if (var0 instanceof TypeData.ClassName && !var0.isNullType()) {
            return new TypeData.ClassName(typeName(var0.getName()));
         } else {
            throw new BadBytecode("bad AASTORE: " + var0);
         }
      }

      public void merge(TypeData var1) {
         try {
            if (!var1.isNullType()) {
               this.element.merge(TypeData.ArrayElement.make(var1));
            }

         } catch (BadBytecode var3) {
            throw new RuntimeException("fatal: " + var3);
         }
      }

      public String getName() {
         return typeName(this.element.getName());
      }

      public TypeData.AbsTypeVar elementType() {
         return this.element;
      }

      public TypeData.BasicType isBasicType() {
         return null;
      }

      public boolean is2WordType() {
         return false;
      }

      public static String typeName(String var0) {
         return var0.charAt(0) == '[' ? "[" + var0 : "[L" + var0.replace('.', '/') + ";";
      }

      public void setType(String var1, ClassPool var2) throws BadBytecode {
         this.element.setType(TypeData.ArrayElement.typeName(var1), var2);
      }

      protected TypeData.TypeVar toTypeVar(int var1) {
         return this.element.toTypeVar(var1 + 1);
      }

      public TypeData getArrayType(int var1) throws NotFoundException {
         return this.element.getArrayType(var1 + 1);
      }

      public int dfs(ArrayList var1, int var2, ClassPool var3) throws NotFoundException {
         return this.element.dfs(var1, var2, var3);
      }

      String toString2(HashSet var1) {
         return "[" + this.element.toString2(var1);
      }
   }

   public static class TypeVar extends TypeData.AbsTypeVar {
      protected ArrayList lowers = new ArrayList(2);
      protected ArrayList usedBy = new ArrayList(2);
      protected ArrayList uppers = null;
      protected String fixedType;
      private boolean is2WordType;
      private int visited = 0;
      private int smallest = 0;
      private boolean inList = false;
      private int dimension = 0;

      public TypeVar(TypeData var1) {
         this.merge(var1);
         this.fixedType = null;
         this.is2WordType = var1.is2WordType();
      }

      public String getName() {
         return this.fixedType == null ? ((TypeData)this.lowers.get(0)).getName() : this.fixedType;
      }

      public TypeData.BasicType isBasicType() {
         return this.fixedType == null ? ((TypeData)this.lowers.get(0)).isBasicType() : null;
      }

      public boolean is2WordType() {
         return this.fixedType == null ? this.is2WordType : false;
      }

      public boolean isNullType() {
         return this.fixedType == null ? ((TypeData)this.lowers.get(0)).isNullType() : false;
      }

      public boolean isUninit() {
         return this.fixedType == null ? ((TypeData)this.lowers.get(0)).isUninit() : false;
      }

      public void merge(TypeData var1) {
         this.lowers.add(var1);
         if (var1 instanceof TypeData.TypeVar) {
            ((TypeData.TypeVar)var1).usedBy.add(this);
         }

      }

      public int getTypeTag() {
         return this.fixedType == null ? ((TypeData)this.lowers.get(0)).getTypeTag() : super.getTypeTag();
      }

      public int getTypeData(ConstPool var1) {
         return this.fixedType == null ? ((TypeData)this.lowers.get(0)).getTypeData(var1) : super.getTypeData(var1);
      }

      public void setType(String var1, ClassPool var2) throws BadBytecode {
         if (this.uppers == null) {
            this.uppers = new ArrayList();
         }

         this.uppers.add(var1);
      }

      protected TypeData.TypeVar toTypeVar(int var1) {
         this.dimension = var1;
         return this;
      }

      public TypeData getArrayType(int var1) throws NotFoundException {
         if (var1 == 0) {
            return this;
         } else {
            TypeData.BasicType var2 = this.isBasicType();
            if (var2 == null) {
               return (TypeData)(this.isNullType() ? new TypeData.NullType() : (new TypeData.ClassName(this.getName())).getArrayType(var1));
            } else {
               return var2.getArrayType(var1);
            }
         }
      }

      public int dfs(ArrayList var1, int var2, ClassPool var3) throws NotFoundException {
         if (this.visited > 0) {
            return var2;
         } else {
            ++var2;
            this.visited = this.smallest = var2;
            var1.add(this);
            this.inList = true;
            int var4 = this.lowers.size();

            TypeData.TypeVar var6;
            for(int var5 = 0; var5 < var4; ++var5) {
               var6 = ((TypeData)this.lowers.get(var5)).toTypeVar(this.dimension);
               if (var6 != null) {
                  if (var6.visited == 0) {
                     var2 = var6.dfs(var1, var2, var3);
                     if (var6.smallest < this.smallest) {
                        this.smallest = var6.smallest;
                     }
                  } else if (var6.inList && var6.visited < this.smallest) {
                     this.smallest = var6.visited;
                  }
               }
            }

            if (this.visited == this.smallest) {
               ArrayList var7 = new ArrayList();

               do {
                  var6 = (TypeData.TypeVar)var1.remove(var1.size() - 1);
                  var6.inList = false;
                  var7.add(var6);
               } while(var6 != this);

               this.fixTypes(var7, var3);
            }

            return var2;
         }
      }

      private void fixTypes(ArrayList var1, ClassPool var2) throws NotFoundException {
         HashSet var3 = new HashSet();
         boolean var4 = false;
         Object var5 = null;
         int var6 = var1.size();

         for(int var7 = 0; var7 < var6; ++var7) {
            TypeData.TypeVar var8 = (TypeData.TypeVar)var1.get(var7);
            ArrayList var9 = var8.lowers;
            int var10 = var9.size();

            for(int var11 = 0; var11 < var10; ++var11) {
               TypeData var12 = (TypeData)var9.get(var11);
               TypeData var13 = var12.getArrayType(var8.dimension);
               TypeData.BasicType var14 = var13.isBasicType();
               if (var5 == null) {
                  if (var14 == null) {
                     var4 = false;
                     var5 = var13;
                     if (var13.isUninit()) {
                        break;
                     }
                  } else {
                     var4 = true;
                     var5 = var14;
                  }
               } else if (var14 == null && var4 || var14 != null && var5 != var14) {
                  var4 = true;
                  var5 = TypeTag.TOP;
                  break;
               }

               if (var14 == null && !var13.isNullType()) {
                  var3.add(var13.getName());
               }
            }
         }

         if (var4) {
            this.is2WordType = ((TypeData)var5).is2WordType();
            this.fixTypes1(var1, (TypeData)var5);
         } else {
            String var15 = this.fixTypes2(var1, var3, var2);
            this.fixTypes1(var1, new TypeData.ClassName(var15));
         }

      }

      private void fixTypes1(ArrayList var1, TypeData var2) throws NotFoundException {
         int var3 = var1.size();

         for(int var4 = 0; var4 < var3; ++var4) {
            TypeData.TypeVar var5 = (TypeData.TypeVar)var1.get(var4);
            TypeData var6 = var2.getArrayType(-var5.dimension);
            if (var6.isBasicType() == null) {
               var5.fixedType = var6.getName();
            } else {
               var5.lowers.clear();
               var5.lowers.add(var6);
               var5.is2WordType = var6.is2WordType();
            }
         }

      }

      private String fixTypes2(ArrayList var1, HashSet var2, ClassPool var3) throws NotFoundException {
         Iterator var4 = var2.iterator();
         if (var2.size() == 0) {
            return null;
         } else if (var2.size() == 1) {
            return (String)var4.next();
         } else {
            CtClass var5;
            for(var5 = var3.get((String)var4.next()); var4.hasNext(); var5 = commonSuperClassEx(var5, var3.get((String)var4.next()))) {
            }

            if (var5.getSuperclass() == null || isObjectArray(var5)) {
               var5 = this.fixByUppers(var1, var3, new HashSet(), var5);
            }

            return var5.isArray() ? Descriptor.toJvmName(var5) : var5.getName();
         }
      }

      private static boolean isObjectArray(CtClass var0) throws NotFoundException {
         return var0.isArray() && var0.getComponentType().getSuperclass() == null;
      }

      private CtClass fixByUppers(ArrayList var1, ClassPool var2, HashSet var3, CtClass var4) throws NotFoundException {
         if (var1 == null) {
            return var4;
         } else {
            int var5 = var1.size();

            for(int var6 = 0; var6 < var5; ++var6) {
               TypeData.TypeVar var7 = (TypeData.TypeVar)var1.get(var6);
               if (!var3.add(var7)) {
                  return var4;
               }

               if (var7.uppers != null) {
                  int var8 = var7.uppers.size();

                  for(int var9 = 0; var9 < var8; ++var9) {
                     CtClass var10 = var2.get((String)var7.uppers.get(var9));
                     if (var10.subtypeOf(var4)) {
                        var4 = var10;
                     }
                  }
               }

               var4 = this.fixByUppers(var7.usedBy, var2, var3, var4);
            }

            return var4;
         }
      }

      String toString2(HashSet var1) {
         var1.add(this);
         if (this.lowers.size() > 0) {
            TypeData var2 = (TypeData)this.lowers.get(0);
            if (var2 != null && !var1.contains(var2)) {
               return var2.toString2(var1);
            }
         }

         return "?";
      }
   }

   public abstract static class AbsTypeVar extends TypeData {
      public abstract void merge(TypeData var1);

      public int getTypeTag() {
         return 7;
      }

      public int getTypeData(ConstPool var1) {
         return var1.addClassInfo(this.getName());
      }

      public boolean eq(TypeData var1) {
         return this.getName().equals(var1.getName());
      }
   }

   protected static class BasicType extends TypeData {
      private String name;
      private int typeTag;
      private char decodedName;

      public BasicType(String var1, int var2, char var3) {
         this.name = var1;
         this.typeTag = var2;
         this.decodedName = var3;
      }

      public int getTypeTag() {
         return this.typeTag;
      }

      public int getTypeData(ConstPool var1) {
         return 0;
      }

      public TypeData join() {
         return (TypeData)(this == TypeTag.TOP ? this : super.join());
      }

      public TypeData.BasicType isBasicType() {
         return this;
      }

      public boolean is2WordType() {
         return this.typeTag == 4 || this.typeTag == 3;
      }

      public boolean eq(TypeData var1) {
         return this == var1;
      }

      public String getName() {
         return this.name;
      }

      public char getDecodedName() {
         return this.decodedName;
      }

      public void setType(String var1, ClassPool var2) throws BadBytecode {
         throw new BadBytecode("conflict: " + this.name + " and " + var1);
      }

      public TypeData getArrayType(int var1) throws NotFoundException {
         if (this == TypeTag.TOP) {
            return this;
         } else if (var1 < 0) {
            throw new NotFoundException("no element type: " + this.name);
         } else if (var1 == 0) {
            return this;
         } else {
            char[] var2 = new char[var1 + 1];

            for(int var3 = 0; var3 < var1; ++var3) {
               var2[var3] = '[';
            }

            var2[var1] = this.decodedName;
            return new TypeData.ClassName(new String(var2));
         }
      }

      String toString2(HashSet var1) {
         return this.name;
      }

      static char access$100(TypeData.BasicType var0) {
         return var0.decodedName;
      }
   }
}
