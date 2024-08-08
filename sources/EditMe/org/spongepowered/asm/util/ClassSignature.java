package org.spongepowered.asm.util;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.spongepowered.asm.lib.signature.SignatureReader;
import org.spongepowered.asm.lib.signature.SignatureVisitor;
import org.spongepowered.asm.lib.signature.SignatureWriter;
import org.spongepowered.asm.lib.tree.ClassNode;

public class ClassSignature {
   protected static final String OBJECT = "java/lang/Object";
   private final Map types = new LinkedHashMap();
   private ClassSignature.Token superClass = new ClassSignature.Token("java/lang/Object");
   private final List interfaces = new ArrayList();
   private final Deque rawInterfaces = new LinkedList();

   ClassSignature() {
   }

   private ClassSignature read(String var1) {
      if (var1 != null) {
         try {
            (new SignatureReader(var1)).accept(new ClassSignature.SignatureParser(this));
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }

      return this;
   }

   protected ClassSignature.TypeVar getTypeVar(String var1) {
      Iterator var2 = this.types.keySet().iterator();

      ClassSignature.TypeVar var3;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = (ClassSignature.TypeVar)var2.next();
      } while(!var3.matches(var1));

      return var3;
   }

   protected ClassSignature.TokenHandle getType(String var1) {
      Iterator var2 = this.types.keySet().iterator();

      ClassSignature.TypeVar var3;
      do {
         if (!var2.hasNext()) {
            ClassSignature.TokenHandle var4 = new ClassSignature.TokenHandle(this);
            this.types.put(new ClassSignature.TypeVar(var1), var4);
            return var4;
         }

         var3 = (ClassSignature.TypeVar)var2.next();
      } while(!var3.matches(var1));

      return (ClassSignature.TokenHandle)this.types.get(var3);
   }

   protected String getTypeVar(ClassSignature.TokenHandle var1) {
      Iterator var2 = this.types.entrySet().iterator();

      ClassSignature.TypeVar var4;
      ClassSignature.TokenHandle var5;
      do {
         if (!var2.hasNext()) {
            return var1.token.asType();
         }

         Entry var3 = (Entry)var2.next();
         var4 = (ClassSignature.TypeVar)var3.getKey();
         var5 = (ClassSignature.TokenHandle)var3.getValue();
      } while(var1 != var5 && var1.asToken() != var5.asToken());

      return "T" + var4 + ";";
   }

   protected void addTypeVar(ClassSignature.TypeVar var1, ClassSignature.TokenHandle var2) throws IllegalArgumentException {
      if (this.types.containsKey(var1)) {
         throw new IllegalArgumentException("TypeVar " + var1 + " is already present on " + this);
      } else {
         this.types.put(var1, var2);
      }
   }

   protected void setSuperClass(ClassSignature.Token var1) {
      this.superClass = var1;
   }

   public String getSuperClass() {
      return this.superClass.asType(true);
   }

   protected void addInterface(ClassSignature.Token var1) {
      if (!var1.isRaw()) {
         String var2 = var1.asType(true);
         ListIterator var3 = this.interfaces.listIterator();

         while(var3.hasNext()) {
            ClassSignature.Token var4 = (ClassSignature.Token)var3.next();
            if (var4.isRaw() && var4.asType(true).equals(var2)) {
               var3.set(var1);
               return;
            }
         }
      }

      this.interfaces.add(var1);
   }

   public void addInterface(String var1) {
      this.rawInterfaces.add(var1);
   }

   protected void addRawInterface(String var1) {
      ClassSignature.Token var2 = new ClassSignature.Token(var1);
      String var3 = var2.asType(true);
      Iterator var4 = this.interfaces.iterator();

      ClassSignature.Token var5;
      do {
         if (!var4.hasNext()) {
            this.interfaces.add(var2);
            return;
         }

         var5 = (ClassSignature.Token)var4.next();
      } while(!var5.asType(true).equals(var3));

   }

   public void merge(ClassSignature var1) {
      try {
         HashSet var2 = new HashSet();
         Iterator var3 = this.types.keySet().iterator();

         while(true) {
            if (!var3.hasNext()) {
               var1.conform(var2);
               break;
            }

            ClassSignature.TypeVar var4 = (ClassSignature.TypeVar)var3.next();
            var2.add(var4.toString());
         }
      } catch (IllegalStateException var5) {
         var5.printStackTrace();
         return;
      }

      Iterator var6 = var1.types.entrySet().iterator();

      while(var6.hasNext()) {
         Entry var7 = (Entry)var6.next();
         this.addTypeVar((ClassSignature.TypeVar)var7.getKey(), (ClassSignature.TokenHandle)var7.getValue());
      }

      var6 = var1.interfaces.iterator();

      while(var6.hasNext()) {
         ClassSignature.Token var8 = (ClassSignature.Token)var6.next();
         this.addInterface(var8);
      }

   }

   private void conform(Set var1) {
      Iterator var2 = this.types.keySet().iterator();

      while(var2.hasNext()) {
         ClassSignature.TypeVar var3 = (ClassSignature.TypeVar)var2.next();
         String var4 = this.findUniqueName(var3.getOriginalName(), var1);
         var3.rename(var4);
         var1.add(var4);
      }

   }

   private String findUniqueName(String var1, Set var2) {
      if (!var2.contains(var1)) {
         return var1;
      } else {
         String var3;
         if (var1.length() == 1) {
            var3 = this.findOffsetName(var1.charAt(0), var2);
            if (var3 != null) {
               return var3;
            }
         }

         var3 = this.findOffsetName('T', var2, "", var1);
         if (var3 != null) {
            return var3;
         } else {
            var3 = this.findOffsetName('T', var2, var1, "");
            if (var3 != null) {
               return var3;
            } else {
               var3 = this.findOffsetName('T', var2, "T", var1);
               if (var3 != null) {
                  return var3;
               } else {
                  var3 = this.findOffsetName('T', var2, "", var1 + "Type");
                  if (var3 != null) {
                     return var3;
                  } else {
                     throw new IllegalStateException("Failed to conform type var: " + var1);
                  }
               }
            }
         }
      }
   }

   private String findOffsetName(char var1, Set var2) {
      return this.findOffsetName(var1, var2, "", "");
   }

   private String findOffsetName(char var1, Set var2, String var3, String var4) {
      String var5 = String.format("%s%s%s", var3, var1, var4);
      if (!var2.contains(var5)) {
         return var5;
      } else {
         if (var1 > '@' && var1 < '[') {
            for(int var6 = var1 - 64; var6 + 65 != var1; var6 %= 26) {
               var5 = String.format("%s%s%s", var3, (char)(var6 + 65), var4);
               if (!var2.contains(var5)) {
                  return var5;
               }

               ++var6;
            }
         }

         return null;
      }
   }

   public SignatureVisitor getRemapper() {
      return new ClassSignature.SignatureRemapper(this);
   }

   public String toString() {
      while(this.rawInterfaces.size() > 0) {
         this.addRawInterface((String)this.rawInterfaces.remove());
      }

      StringBuilder var1 = new StringBuilder();
      if (this.types.size() > 0) {
         boolean var2 = false;
         StringBuilder var3 = new StringBuilder();
         Iterator var4 = this.types.entrySet().iterator();

         while(var4.hasNext()) {
            Entry var5 = (Entry)var4.next();
            String var6 = ((ClassSignature.TokenHandle)var5.getValue()).asBound();
            if (!var6.isEmpty()) {
               var3.append(var5.getKey()).append(':').append(var6);
               var2 = true;
            }
         }

         if (var2) {
            var1.append('<').append(var3).append('>');
         }
      }

      var1.append(this.superClass.asType());
      Iterator var7 = this.interfaces.iterator();

      while(var7.hasNext()) {
         ClassSignature.Token var8 = (ClassSignature.Token)var7.next();
         var1.append(var8.asType());
      }

      return var1.toString();
   }

   public ClassSignature wake() {
      return this;
   }

   public static ClassSignature of(String var0) {
      return (new ClassSignature()).read(var0);
   }

   public static ClassSignature of(ClassNode var0) {
      return var0.signature != null ? of(var0.signature) : generate(var0);
   }

   public static ClassSignature ofLazy(ClassNode var0) {
      return (ClassSignature)(var0.signature != null ? new ClassSignature.Lazy(var0.signature) : generate(var0));
   }

   private static ClassSignature generate(ClassNode var0) {
      ClassSignature var1 = new ClassSignature();
      var1.setSuperClass(new ClassSignature.Token(var0.superName != null ? var0.superName : "java/lang/Object"));
      Iterator var2 = var0.interfaces.iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         var1.addInterface(new ClassSignature.Token(var3));
      }

      return var1;
   }

   class SignatureRemapper extends SignatureWriter {
      private final Set localTypeVars;
      final ClassSignature this$0;

      SignatureRemapper(ClassSignature var1) {
         this.this$0 = var1;
         this.localTypeVars = new HashSet();
      }

      public void visitFormalTypeParameter(String var1) {
         this.localTypeVars.add(var1);
         super.visitFormalTypeParameter(var1);
      }

      public void visitTypeVariable(String var1) {
         if (!this.localTypeVars.contains(var1)) {
            ClassSignature.TypeVar var2 = this.this$0.getTypeVar(var1);
            if (var2 != null) {
               super.visitTypeVariable(var2.toString());
               return;
            }
         }

         super.visitTypeVariable(var1);
      }
   }

   class SignatureParser extends SignatureVisitor {
      private ClassSignature.SignatureParser.FormalParamElement param;
      final ClassSignature this$0;

      SignatureParser(ClassSignature var1) {
         super(327680);
         this.this$0 = var1;
      }

      public void visitFormalTypeParameter(String var1) {
         this.param = new ClassSignature.SignatureParser.FormalParamElement(this, var1);
      }

      public SignatureVisitor visitClassBound() {
         return this.param.visitClassBound();
      }

      public SignatureVisitor visitInterfaceBound() {
         return this.param.visitInterfaceBound();
      }

      public SignatureVisitor visitSuperclass() {
         return new ClassSignature.SignatureParser.SuperClassElement(this);
      }

      public SignatureVisitor visitInterface() {
         return new ClassSignature.SignatureParser.InterfaceElement(this);
      }

      class InterfaceElement extends ClassSignature.SignatureParser.TokenElement {
         final ClassSignature.SignatureParser this$1;

         InterfaceElement(ClassSignature.SignatureParser var1) {
            super(var1);
            this.this$1 = var1;
         }

         public void visitEnd() {
            this.this$1.this$0.addInterface(this.token);
         }
      }

      class SuperClassElement extends ClassSignature.SignatureParser.TokenElement {
         final ClassSignature.SignatureParser this$1;

         SuperClassElement(ClassSignature.SignatureParser var1) {
            super(var1);
            this.this$1 = var1;
         }

         public void visitEnd() {
            this.this$1.this$0.setSuperClass(this.token);
         }
      }

      class BoundElement extends ClassSignature.SignatureParser.TokenElement {
         private final ClassSignature.SignatureParser.TokenElement type;
         private final boolean classBound;
         final ClassSignature.SignatureParser this$1;

         BoundElement(ClassSignature.SignatureParser var1, ClassSignature.SignatureParser.TokenElement var2, boolean var3) {
            super(var1);
            this.this$1 = var1;
            this.type = var2;
            this.classBound = var3;
         }

         public void visitClassType(String var1) {
            this.token = this.type.token.addBound(var1, this.classBound);
         }

         public void visitTypeArgument() {
            this.token.addTypeArgument('*');
         }

         public SignatureVisitor visitTypeArgument(char var1) {
            return this.this$1.new TypeArgElement(this.this$1, this, var1);
         }
      }

      class TypeArgElement extends ClassSignature.SignatureParser.TokenElement {
         private final ClassSignature.SignatureParser.TokenElement type;
         private final char wildcard;
         final ClassSignature.SignatureParser this$1;

         TypeArgElement(ClassSignature.SignatureParser var1, ClassSignature.SignatureParser.TokenElement var2, char var3) {
            super(var1);
            this.this$1 = var1;
            this.type = var2;
            this.wildcard = var3;
         }

         public SignatureVisitor visitArrayType() {
            this.type.setArray();
            return this;
         }

         public void visitBaseType(char var1) {
            this.token = this.type.addTypeArgument(var1).asToken();
         }

         public void visitTypeVariable(String var1) {
            ClassSignature.TokenHandle var2 = this.this$1.this$0.getType(var1);
            this.token = this.type.addTypeArgument(var2).setWildcard(this.wildcard).asToken();
         }

         public void visitClassType(String var1) {
            this.token = this.type.addTypeArgument(var1).setWildcard(this.wildcard).asToken();
         }

         public void visitTypeArgument() {
            this.token.addTypeArgument('*');
         }

         public SignatureVisitor visitTypeArgument(char var1) {
            return this.this$1.new TypeArgElement(this.this$1, this, var1);
         }

         public void visitEnd() {
         }
      }

      class FormalParamElement extends ClassSignature.SignatureParser.TokenElement {
         private final ClassSignature.TokenHandle handle;
         final ClassSignature.SignatureParser this$1;

         FormalParamElement(ClassSignature.SignatureParser var1, String var2) {
            super(var1);
            this.this$1 = var1;
            this.handle = var1.this$0.getType(var2);
            this.token = this.handle.asToken();
         }
      }

      abstract class TokenElement extends ClassSignature.SignatureParser.SignatureElement {
         protected ClassSignature.Token token;
         private boolean array;
         final ClassSignature.SignatureParser this$1;

         TokenElement(ClassSignature.SignatureParser var1) {
            super(var1);
            this.this$1 = var1;
         }

         public ClassSignature.Token getToken() {
            if (this.token == null) {
               this.token = new ClassSignature.Token();
            }

            return this.token;
         }

         protected void setArray() {
            this.array = true;
         }

         private boolean getArray() {
            boolean var1 = this.array;
            this.array = false;
            return var1;
         }

         public void visitClassType(String var1) {
            this.getToken().setType(var1);
         }

         public SignatureVisitor visitClassBound() {
            this.getToken();
            return this.this$1.new BoundElement(this.this$1, this, true);
         }

         public SignatureVisitor visitInterfaceBound() {
            this.getToken();
            return this.this$1.new BoundElement(this.this$1, this, false);
         }

         public void visitInnerClassType(String var1) {
            this.token.addInnerClass(var1);
         }

         public SignatureVisitor visitArrayType() {
            this.setArray();
            return this;
         }

         public SignatureVisitor visitTypeArgument(char var1) {
            return this.this$1.new TypeArgElement(this.this$1, this, var1);
         }

         ClassSignature.Token addTypeArgument() {
            return this.token.addTypeArgument('*').asToken();
         }

         ClassSignature.IToken addTypeArgument(char var1) {
            return this.token.addTypeArgument(var1).setArray(this.getArray());
         }

         ClassSignature.IToken addTypeArgument(String var1) {
            return this.token.addTypeArgument(var1).setArray(this.getArray());
         }

         ClassSignature.IToken addTypeArgument(ClassSignature.Token var1) {
            return this.token.addTypeArgument(var1).setArray(this.getArray());
         }

         ClassSignature.IToken addTypeArgument(ClassSignature.TokenHandle var1) {
            return this.token.addTypeArgument(var1).setArray(this.getArray());
         }
      }

      abstract class SignatureElement extends SignatureVisitor {
         final ClassSignature.SignatureParser this$1;

         public SignatureElement(ClassSignature.SignatureParser var1) {
            super(327680);
            this.this$1 = var1;
         }
      }
   }

   class TokenHandle implements ClassSignature.IToken {
      final ClassSignature.Token token;
      boolean array;
      char wildcard;
      final ClassSignature this$0;

      TokenHandle(ClassSignature var1) {
         this(var1, new ClassSignature.Token());
      }

      TokenHandle(ClassSignature var1, ClassSignature.Token var2) {
         this.this$0 = var1;
         this.token = var2;
      }

      public ClassSignature.IToken setArray(boolean var1) {
         this.array |= var1;
         return this;
      }

      public ClassSignature.IToken setWildcard(char var1) {
         if ("+-".indexOf(var1) > -1) {
            this.wildcard = var1;
         }

         return this;
      }

      public String asBound() {
         return this.token.asBound();
      }

      public String asType() {
         StringBuilder var1 = new StringBuilder();
         if (this.wildcard > 0) {
            var1.append(this.wildcard);
         }

         if (this.array) {
            var1.append('[');
         }

         return var1.append(this.this$0.getTypeVar(this)).toString();
      }

      public ClassSignature.Token asToken() {
         return this.token;
      }

      public String toString() {
         return this.token.toString();
      }

      public ClassSignature.TokenHandle clone() {
         return this.this$0.new TokenHandle(this.this$0, this.token);
      }

      public Object clone() throws CloneNotSupportedException {
         return this.clone();
      }
   }

   static class Token implements ClassSignature.IToken {
      static final String SYMBOLS = "+-*";
      private final boolean inner;
      private boolean array;
      private char symbol;
      private String type;
      private List classBound;
      private List ifaceBound;
      private List signature;
      private List suffix;
      private ClassSignature.Token tail;

      Token() {
         this(false);
      }

      Token(String var1) {
         this(var1, false);
      }

      Token(char var1) {
         this();
         this.symbol = var1;
      }

      Token(boolean var1) {
         this((String)null, var1);
      }

      Token(String var1, boolean var2) {
         this.symbol = 0;
         this.inner = var2;
         this.type = var1;
      }

      ClassSignature.Token setSymbol(char var1) {
         if (this.symbol == 0 && "+-*".indexOf(var1) > -1) {
            this.symbol = var1;
         }

         return this;
      }

      ClassSignature.Token setType(String var1) {
         if (this.type == null) {
            this.type = var1;
         }

         return this;
      }

      boolean hasClassBound() {
         return this.classBound != null;
      }

      boolean hasInterfaceBound() {
         return this.ifaceBound != null;
      }

      public ClassSignature.IToken setArray(boolean var1) {
         this.array |= var1;
         return this;
      }

      public ClassSignature.IToken setWildcard(char var1) {
         return "+-".indexOf(var1) == -1 ? this : this.setSymbol(var1);
      }

      private List getClassBound() {
         if (this.classBound == null) {
            this.classBound = new ArrayList();
         }

         return this.classBound;
      }

      private List getIfaceBound() {
         if (this.ifaceBound == null) {
            this.ifaceBound = new ArrayList();
         }

         return this.ifaceBound;
      }

      private List getSignature() {
         if (this.signature == null) {
            this.signature = new ArrayList();
         }

         return this.signature;
      }

      private List getSuffix() {
         if (this.suffix == null) {
            this.suffix = new ArrayList();
         }

         return this.suffix;
      }

      ClassSignature.IToken addTypeArgument(char var1) {
         if (this.tail != null) {
            return this.tail.addTypeArgument(var1);
         } else {
            ClassSignature.Token var2 = new ClassSignature.Token(var1);
            this.getSignature().add(var2);
            return var2;
         }
      }

      ClassSignature.IToken addTypeArgument(String var1) {
         if (this.tail != null) {
            return this.tail.addTypeArgument(var1);
         } else {
            ClassSignature.Token var2 = new ClassSignature.Token(var1);
            this.getSignature().add(var2);
            return var2;
         }
      }

      ClassSignature.IToken addTypeArgument(ClassSignature.Token var1) {
         if (this.tail != null) {
            return this.tail.addTypeArgument(var1);
         } else {
            this.getSignature().add(var1);
            return var1;
         }
      }

      ClassSignature.IToken addTypeArgument(ClassSignature.TokenHandle var1) {
         if (this.tail != null) {
            return this.tail.addTypeArgument(var1);
         } else {
            ClassSignature.TokenHandle var2 = var1.clone();
            this.getSignature().add(var2);
            return var2;
         }
      }

      ClassSignature.Token addBound(String var1, boolean var2) {
         return var2 ? this.addClassBound(var1) : this.addInterfaceBound(var1);
      }

      ClassSignature.Token addClassBound(String var1) {
         ClassSignature.Token var2 = new ClassSignature.Token(var1);
         this.getClassBound().add(var2);
         return var2;
      }

      ClassSignature.Token addInterfaceBound(String var1) {
         ClassSignature.Token var2 = new ClassSignature.Token(var1);
         this.getIfaceBound().add(var2);
         return var2;
      }

      ClassSignature.Token addInnerClass(String var1) {
         this.tail = new ClassSignature.Token(var1, true);
         this.getSuffix().add(this.tail);
         return this.tail;
      }

      public String toString() {
         return this.asType();
      }

      public String asBound() {
         StringBuilder var1 = new StringBuilder();
         if (this.type != null) {
            var1.append(this.type);
         }

         Iterator var2;
         ClassSignature.Token var3;
         if (this.classBound != null) {
            var2 = this.classBound.iterator();

            while(var2.hasNext()) {
               var3 = (ClassSignature.Token)var2.next();
               var1.append(var3.asType());
            }
         }

         if (this.ifaceBound != null) {
            var2 = this.ifaceBound.iterator();

            while(var2.hasNext()) {
               var3 = (ClassSignature.Token)var2.next();
               var1.append(':').append(var3.asType());
            }
         }

         return var1.toString();
      }

      public String asType() {
         return this.asType(false);
      }

      public String asType(boolean var1) {
         StringBuilder var2 = new StringBuilder();
         if (this.array) {
            var2.append('[');
         }

         if (this.symbol != 0) {
            var2.append(this.symbol);
         }

         if (this.type == null) {
            return var2.toString();
         } else {
            if (!this.inner) {
               var2.append('L');
            }

            var2.append(this.type);
            if (!var1) {
               Iterator var3;
               ClassSignature.IToken var4;
               if (this.signature != null) {
                  var2.append('<');
                  var3 = this.signature.iterator();

                  while(var3.hasNext()) {
                     var4 = (ClassSignature.IToken)var3.next();
                     var2.append(var4.asType());
                  }

                  var2.append('>');
               }

               if (this.suffix != null) {
                  var3 = this.suffix.iterator();

                  while(var3.hasNext()) {
                     var4 = (ClassSignature.IToken)var3.next();
                     var2.append('.').append(var4.asType());
                  }
               }
            }

            if (!this.inner) {
               var2.append(';');
            }

            return var2.toString();
         }
      }

      boolean isRaw() {
         return this.signature == null;
      }

      String getClassType() {
         return this.type != null ? this.type : "java/lang/Object";
      }

      public ClassSignature.Token asToken() {
         return this;
      }
   }

   interface IToken {
      String WILDCARDS = "+-";

      String asType();

      String asBound();

      ClassSignature.Token asToken();

      ClassSignature.IToken setArray(boolean var1);

      ClassSignature.IToken setWildcard(char var1);
   }

   static class TypeVar implements Comparable {
      private final String originalName;
      private String currentName;

      TypeVar(String var1) {
         this.currentName = this.originalName = var1;
      }

      public int compareTo(ClassSignature.TypeVar var1) {
         return this.currentName.compareTo(var1.currentName);
      }

      public String toString() {
         return this.currentName;
      }

      String getOriginalName() {
         return this.originalName;
      }

      void rename(String var1) {
         this.currentName = var1;
      }

      public boolean matches(String var1) {
         return this.originalName.equals(var1);
      }

      public boolean equals(Object var1) {
         return this.currentName.equals(var1);
      }

      public int hashCode() {
         return this.currentName.hashCode();
      }

      public int compareTo(Object var1) {
         return this.compareTo((ClassSignature.TypeVar)var1);
      }
   }

   static class Lazy extends ClassSignature {
      private final String sig;
      private ClassSignature generated;

      Lazy(String var1) {
         this.sig = var1;
      }

      public ClassSignature wake() {
         if (this.generated == null) {
            this.generated = ClassSignature.of(this.sig);
         }

         return this.generated;
      }
   }
}
