/*      */ package org.spongepowered.asm.util;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Deque;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.spongepowered.asm.lib.signature.SignatureReader;
/*      */ import org.spongepowered.asm.lib.signature.SignatureVisitor;
/*      */ import org.spongepowered.asm.lib.signature.SignatureWriter;
/*      */ import org.spongepowered.asm.lib.tree.ClassNode;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ClassSignature
/*      */ {
/*      */   protected static final String OBJECT = "java/lang/Object";
/*      */   
/*      */   static class Lazy
/*      */     extends ClassSignature
/*      */   {
/*      */     private final String sig;
/*      */     private ClassSignature generated;
/*      */     
/*      */     Lazy(String sig) {
/*   64 */       this.sig = sig;
/*      */     }
/*      */ 
/*      */     
/*      */     public ClassSignature wake() {
/*   69 */       if (this.generated == null) {
/*   70 */         this.generated = ClassSignature.of(this.sig);
/*      */       }
/*   72 */       return this.generated;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class TypeVar
/*      */     implements Comparable<TypeVar>
/*      */   {
/*      */     private final String originalName;
/*      */ 
/*      */ 
/*      */     
/*      */     private String currentName;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TypeVar(String name) {
/*   93 */       this.currentName = this.originalName = name;
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(TypeVar other) {
/*   98 */       return this.currentName.compareTo(other.currentName);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  103 */       return this.currentName;
/*      */     }
/*      */     
/*      */     String getOriginalName() {
/*  107 */       return this.originalName;
/*      */     }
/*      */     
/*      */     void rename(String name) {
/*  111 */       this.currentName = name;
/*      */     }
/*      */     
/*      */     public boolean matches(String originalName) {
/*  115 */       return this.originalName.equals(originalName);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/*  120 */       return this.currentName.equals(obj);
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  125 */       return this.currentName.hashCode();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static interface IToken
/*      */   {
/*      */     public static final String WILDCARDS = "+-";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String asType();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String asBound();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ClassSignature.Token asToken();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     IToken setArray(boolean param1Boolean);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     IToken setWildcard(char param1Char);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class Token
/*      */     implements IToken
/*      */   {
/*      */     static final String SYMBOLS = "+-*";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final boolean inner;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean array;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  202 */     private char symbol = Character.MIN_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String type;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private List<Token> classBound;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private List<Token> ifaceBound;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private List<ClassSignature.IToken> signature;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private List<ClassSignature.IToken> suffix;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Token tail;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Token() {
/*  238 */       this(false);
/*      */     }
/*      */     
/*      */     Token(String type) {
/*  242 */       this(type, false);
/*      */     }
/*      */     
/*      */     Token(char symbol) {
/*  246 */       this();
/*  247 */       this.symbol = symbol;
/*      */     }
/*      */     
/*      */     Token(boolean inner) {
/*  251 */       this(null, inner);
/*      */     }
/*      */     
/*      */     Token(String type, boolean inner) {
/*  255 */       this.inner = inner;
/*  256 */       this.type = type;
/*      */     }
/*      */     
/*      */     Token setSymbol(char symbol) {
/*  260 */       if (this.symbol == '\000' && "+-*".indexOf(symbol) > -1) {
/*  261 */         this.symbol = symbol;
/*      */       }
/*  263 */       return this;
/*      */     }
/*      */     
/*      */     Token setType(String type) {
/*  267 */       if (this.type == null) {
/*  268 */         this.type = type;
/*      */       }
/*  270 */       return this;
/*      */     }
/*      */     
/*      */     boolean hasClassBound() {
/*  274 */       return (this.classBound != null);
/*      */     }
/*      */     
/*      */     boolean hasInterfaceBound() {
/*  278 */       return (this.ifaceBound != null);
/*      */     }
/*      */ 
/*      */     
/*      */     public ClassSignature.IToken setArray(boolean array) {
/*  283 */       this.array |= array;
/*  284 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public ClassSignature.IToken setWildcard(char wildcard) {
/*  289 */       if ("+-".indexOf(wildcard) == -1) {
/*  290 */         return this;
/*      */       }
/*  292 */       return setSymbol(wildcard);
/*      */     }
/*      */     
/*      */     private List<Token> getClassBound() {
/*  296 */       if (this.classBound == null) {
/*  297 */         this.classBound = new ArrayList<Token>();
/*      */       }
/*  299 */       return this.classBound;
/*      */     }
/*      */     
/*      */     private List<Token> getIfaceBound() {
/*  303 */       if (this.ifaceBound == null) {
/*  304 */         this.ifaceBound = new ArrayList<Token>();
/*      */       }
/*  306 */       return this.ifaceBound;
/*      */     }
/*      */     
/*      */     private List<ClassSignature.IToken> getSignature() {
/*  310 */       if (this.signature == null) {
/*  311 */         this.signature = new ArrayList<ClassSignature.IToken>();
/*      */       }
/*  313 */       return this.signature;
/*      */     }
/*      */     
/*      */     private List<ClassSignature.IToken> getSuffix() {
/*  317 */       if (this.suffix == null) {
/*  318 */         this.suffix = new ArrayList<ClassSignature.IToken>();
/*      */       }
/*  320 */       return this.suffix;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ClassSignature.IToken addTypeArgument(char symbol) {
/*  330 */       if (this.tail != null) {
/*  331 */         return this.tail.addTypeArgument(symbol);
/*      */       }
/*      */       
/*  334 */       Token token = new Token(symbol);
/*  335 */       getSignature().add(token);
/*  336 */       return token;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ClassSignature.IToken addTypeArgument(String name) {
/*  346 */       if (this.tail != null) {
/*  347 */         return this.tail.addTypeArgument(name);
/*      */       }
/*      */       
/*  350 */       Token token = new Token(name);
/*  351 */       getSignature().add(token);
/*  352 */       return token;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ClassSignature.IToken addTypeArgument(Token token) {
/*  362 */       if (this.tail != null) {
/*  363 */         return this.tail.addTypeArgument(token);
/*      */       }
/*      */       
/*  366 */       getSignature().add(token);
/*  367 */       return token;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ClassSignature.IToken addTypeArgument(ClassSignature.TokenHandle token) {
/*  377 */       if (this.tail != null) {
/*  378 */         return this.tail.addTypeArgument(token);
/*      */       }
/*      */       
/*  381 */       ClassSignature.TokenHandle handle = token.clone();
/*  382 */       getSignature().add(handle);
/*  383 */       return handle;
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
/*      */     Token addBound(String bound, boolean classBound) {
/*  395 */       if (classBound) {
/*  396 */         return addClassBound(bound);
/*      */       }
/*      */       
/*  399 */       return addInterfaceBound(bound);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Token addClassBound(String bound) {
/*  409 */       Token token = new Token(bound);
/*  410 */       getClassBound().add(token);
/*  411 */       return token;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Token addInterfaceBound(String bound) {
/*  421 */       Token token = new Token(bound);
/*  422 */       getIfaceBound().add(token);
/*  423 */       return token;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Token addInnerClass(String name) {
/*  433 */       this.tail = new Token(name, true);
/*  434 */       getSuffix().add(this.tail);
/*  435 */       return this.tail;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  443 */       return asType();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String asBound() {
/*  451 */       StringBuilder sb = new StringBuilder();
/*      */       
/*  453 */       if (this.type != null) {
/*  454 */         sb.append(this.type);
/*      */       }
/*      */       
/*  457 */       if (this.classBound != null) {
/*  458 */         for (Token token : this.classBound) {
/*  459 */           sb.append(token.asType());
/*      */         }
/*      */       }
/*      */       
/*  463 */       if (this.ifaceBound != null) {
/*  464 */         for (Token token : this.ifaceBound) {
/*  465 */           sb.append(':').append(token.asType());
/*      */         }
/*      */       }
/*      */       
/*  469 */       return sb.toString();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String asType() {
/*  477 */       return asType(false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String asType(boolean raw) {
/*  488 */       StringBuilder sb = new StringBuilder();
/*      */       
/*  490 */       if (this.array) {
/*  491 */         sb.append('[');
/*      */       }
/*      */       
/*  494 */       if (this.symbol != '\000') {
/*  495 */         sb.append(this.symbol);
/*      */       }
/*      */       
/*  498 */       if (this.type == null) {
/*  499 */         return sb.toString();
/*      */       }
/*      */       
/*  502 */       if (!this.inner) {
/*  503 */         sb.append('L');
/*      */       }
/*      */       
/*  506 */       sb.append(this.type);
/*      */       
/*  508 */       if (!raw) {
/*  509 */         if (this.signature != null) {
/*  510 */           sb.append('<');
/*  511 */           for (ClassSignature.IToken token : this.signature) {
/*  512 */             sb.append(token.asType());
/*      */           }
/*  514 */           sb.append('>');
/*      */         } 
/*      */         
/*  517 */         if (this.suffix != null) {
/*  518 */           for (ClassSignature.IToken token : this.suffix) {
/*  519 */             sb.append('.').append(token.asType());
/*      */           }
/*      */         }
/*      */       } 
/*      */       
/*  524 */       if (!this.inner) {
/*  525 */         sb.append(';');
/*      */       }
/*      */       
/*  528 */       return sb.toString();
/*      */     }
/*      */     
/*      */     boolean isRaw() {
/*  532 */       return (this.signature == null);
/*      */     }
/*      */     
/*      */     String getClassType() {
/*  536 */       return (this.type != null) ? this.type : "java/lang/Object";
/*      */     }
/*      */ 
/*      */     
/*      */     public Token asToken() {
/*  541 */       return this;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class TokenHandle
/*      */     implements IToken
/*      */   {
/*      */     final ClassSignature.Token token;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean array;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     char wildcard;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TokenHandle() {
/*  570 */       this(new ClassSignature.Token());
/*      */     }
/*      */     
/*      */     TokenHandle(ClassSignature.Token token) {
/*  574 */       this.token = token;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ClassSignature.IToken setArray(boolean array) {
/*  583 */       this.array |= array;
/*  584 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ClassSignature.IToken setWildcard(char wildcard) {
/*  593 */       if ("+-".indexOf(wildcard) > -1) {
/*  594 */         this.wildcard = wildcard;
/*      */       }
/*  596 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String asBound() {
/*  604 */       return this.token.asBound();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String asType() {
/*  612 */       StringBuilder sb = new StringBuilder();
/*      */       
/*  614 */       if (this.wildcard > '\000') {
/*  615 */         sb.append(this.wildcard);
/*      */       }
/*      */       
/*  618 */       if (this.array) {
/*  619 */         sb.append('[');
/*      */       }
/*      */       
/*  622 */       return sb.append(ClassSignature.this.getTypeVar(this)).toString();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ClassSignature.Token asToken() {
/*  630 */       return this.token;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  638 */       return this.token.toString();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TokenHandle clone() {
/*  646 */       return new TokenHandle(this.token);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class SignatureParser
/*      */     extends SignatureVisitor
/*      */   {
/*      */     private FormalParamElement param;
/*      */ 
/*      */ 
/*      */     
/*      */     abstract class SignatureElement
/*      */       extends SignatureVisitor
/*      */     {
/*      */       public SignatureElement() {
/*  664 */         super(327680);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract class TokenElement
/*      */       extends SignatureElement
/*      */     {
/*      */       protected ClassSignature.Token token;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private boolean array;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public ClassSignature.Token getToken() {
/*  686 */         if (this.token == null) {
/*  687 */           this.token = new ClassSignature.Token();
/*      */         }
/*  689 */         return this.token;
/*      */       }
/*      */       
/*      */       protected void setArray() {
/*  693 */         this.array = true;
/*      */       }
/*      */       
/*      */       private boolean getArray() {
/*  697 */         boolean array = this.array;
/*  698 */         this.array = false;
/*  699 */         return array;
/*      */       }
/*      */ 
/*      */       
/*      */       public void visitClassType(String name) {
/*  704 */         getToken().setType(name);
/*      */       }
/*      */ 
/*      */       
/*      */       public SignatureVisitor visitClassBound() {
/*  709 */         getToken();
/*  710 */         return new ClassSignature.SignatureParser.BoundElement(this, true);
/*      */       }
/*      */ 
/*      */       
/*      */       public SignatureVisitor visitInterfaceBound() {
/*  715 */         getToken();
/*  716 */         return new ClassSignature.SignatureParser.BoundElement(this, false);
/*      */       }
/*      */ 
/*      */       
/*      */       public void visitInnerClassType(String name) {
/*  721 */         this.token.addInnerClass(name);
/*      */       }
/*      */ 
/*      */       
/*      */       public SignatureVisitor visitArrayType() {
/*  726 */         setArray();
/*  727 */         return this;
/*      */       }
/*      */ 
/*      */       
/*      */       public SignatureVisitor visitTypeArgument(char wildcard) {
/*  732 */         return new ClassSignature.SignatureParser.TypeArgElement(this, wildcard);
/*      */       }
/*      */       
/*      */       ClassSignature.Token addTypeArgument() {
/*  736 */         return this.token.addTypeArgument('*').asToken();
/*      */       }
/*      */       
/*      */       ClassSignature.IToken addTypeArgument(char symbol) {
/*  740 */         return this.token.addTypeArgument(symbol).setArray(getArray());
/*      */       }
/*      */       
/*      */       ClassSignature.IToken addTypeArgument(String name) {
/*  744 */         return this.token.addTypeArgument(name).setArray(getArray());
/*      */       }
/*      */       
/*      */       ClassSignature.IToken addTypeArgument(ClassSignature.Token token) {
/*  748 */         return this.token.addTypeArgument(token).setArray(getArray());
/*      */       }
/*      */       
/*      */       ClassSignature.IToken addTypeArgument(ClassSignature.TokenHandle token) {
/*  752 */         return this.token.addTypeArgument(token).setArray(getArray());
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     class FormalParamElement
/*      */       extends TokenElement
/*      */     {
/*      */       private final ClassSignature.TokenHandle handle;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       FormalParamElement(String param) {
/*  768 */         this.handle = ClassSignature.this.getType(param);
/*  769 */         this.token = this.handle.asToken();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     class TypeArgElement
/*      */       extends TokenElement
/*      */     {
/*      */       private final ClassSignature.SignatureParser.TokenElement type;
/*      */ 
/*      */ 
/*      */       
/*      */       private final char wildcard;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       TypeArgElement(ClassSignature.SignatureParser.TokenElement type, char wildcard) {
/*  790 */         this.type = type;
/*  791 */         this.wildcard = wildcard;
/*      */       }
/*      */ 
/*      */       
/*      */       public SignatureVisitor visitArrayType() {
/*  796 */         this.type.setArray();
/*  797 */         return this;
/*      */       }
/*      */ 
/*      */       
/*      */       public void visitBaseType(char descriptor) {
/*  802 */         this.token = this.type.addTypeArgument(descriptor).asToken();
/*      */       }
/*      */ 
/*      */       
/*      */       public void visitTypeVariable(String name) {
/*  807 */         ClassSignature.TokenHandle token = ClassSignature.this.getType(name);
/*  808 */         this.token = this.type.addTypeArgument(token).setWildcard(this.wildcard).asToken();
/*      */       }
/*      */ 
/*      */       
/*      */       public void visitClassType(String name) {
/*  813 */         this.token = this.type.addTypeArgument(name).setWildcard(this.wildcard).asToken();
/*      */       }
/*      */ 
/*      */       
/*      */       public void visitTypeArgument() {
/*  818 */         this.token.addTypeArgument('*');
/*      */       }
/*      */ 
/*      */       
/*      */       public SignatureVisitor visitTypeArgument(char wildcard) {
/*  823 */         return new TypeArgElement(this, wildcard);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void visitEnd() {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     class BoundElement
/*      */       extends TokenElement
/*      */     {
/*      */       private final ClassSignature.SignatureParser.TokenElement type;
/*      */ 
/*      */ 
/*      */       
/*      */       private final boolean classBound;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       BoundElement(ClassSignature.SignatureParser.TokenElement type, boolean classBound) {
/*  848 */         this.type = type;
/*  849 */         this.classBound = classBound;
/*      */       }
/*      */ 
/*      */       
/*      */       public void visitClassType(String name) {
/*  854 */         this.token = this.type.token.addBound(name, this.classBound);
/*      */       }
/*      */ 
/*      */       
/*      */       public void visitTypeArgument() {
/*  859 */         this.token.addTypeArgument('*');
/*      */       }
/*      */ 
/*      */       
/*      */       public SignatureVisitor visitTypeArgument(char wildcard) {
/*  864 */         return new ClassSignature.SignatureParser.TypeArgElement(this, wildcard);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     class SuperClassElement
/*      */       extends TokenElement
/*      */     {
/*      */       public void visitEnd() {
/*  876 */         ClassSignature.this.setSuperClass(this.token);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     class InterfaceElement
/*      */       extends TokenElement
/*      */     {
/*      */       public void visitEnd() {
/*  888 */         ClassSignature.this.addInterface(this.token);
/*      */       }
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
/*      */     SignatureParser() {
/*  901 */       super(327680);
/*      */     }
/*      */ 
/*      */     
/*      */     public void visitFormalTypeParameter(String name) {
/*  906 */       this.param = new FormalParamElement(name);
/*      */     }
/*      */ 
/*      */     
/*      */     public SignatureVisitor visitClassBound() {
/*  911 */       return this.param.visitClassBound();
/*      */     }
/*      */ 
/*      */     
/*      */     public SignatureVisitor visitInterfaceBound() {
/*  916 */       return this.param.visitInterfaceBound();
/*      */     }
/*      */ 
/*      */     
/*      */     public SignatureVisitor visitSuperclass() {
/*  921 */       return new SuperClassElement();
/*      */     }
/*      */ 
/*      */     
/*      */     public SignatureVisitor visitInterface() {
/*  926 */       return new InterfaceElement();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class SignatureRemapper
/*      */     extends SignatureWriter
/*      */   {
/*  936 */     private final Set<String> localTypeVars = new HashSet<String>();
/*      */ 
/*      */     
/*      */     public void visitFormalTypeParameter(String name) {
/*  940 */       this.localTypeVars.add(name);
/*  941 */       super.visitFormalTypeParameter(name);
/*      */     }
/*      */ 
/*      */     
/*      */     public void visitTypeVariable(String name) {
/*  946 */       if (!this.localTypeVars.contains(name)) {
/*  947 */         ClassSignature.TypeVar typeVar = ClassSignature.this.getTypeVar(name);
/*  948 */         if (typeVar != null) {
/*  949 */           super.visitTypeVariable(typeVar.toString());
/*      */           return;
/*      */         } 
/*      */       } 
/*  953 */       super.visitTypeVariable(name);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  962 */   private final Map<TypeVar, TokenHandle> types = new LinkedHashMap<TypeVar, TokenHandle>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  967 */   private Token superClass = new Token("java/lang/Object");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  972 */   private final List<Token> interfaces = new ArrayList<Token>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  977 */   private final Deque<String> rawInterfaces = new LinkedList<String>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ClassSignature read(String signature) {
/*  990 */     if (signature != null) {
/*      */       try {
/*  992 */         (new SignatureReader(signature)).accept(new SignatureParser());
/*  993 */       } catch (Exception ex) {
/*  994 */         ex.printStackTrace();
/*      */       } 
/*      */     }
/*  997 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TypeVar getTypeVar(String varName) {
/* 1007 */     for (TypeVar typeVar : this.types.keySet()) {
/* 1008 */       if (typeVar.matches(varName)) {
/* 1009 */         return typeVar;
/*      */       }
/*      */     } 
/* 1012 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TokenHandle getType(String varName) {
/* 1022 */     for (TypeVar typeVar : this.types.keySet()) {
/* 1023 */       if (typeVar.matches(varName)) {
/* 1024 */         return this.types.get(typeVar);
/*      */       }
/*      */     } 
/*      */     
/* 1028 */     TokenHandle handle = new TokenHandle();
/* 1029 */     this.types.put(new TypeVar(varName), handle);
/* 1030 */     return handle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getTypeVar(TokenHandle handle) {
/* 1041 */     for (Map.Entry<TypeVar, TokenHandle> type : this.types.entrySet()) {
/* 1042 */       TypeVar typeVar = type.getKey();
/* 1043 */       TokenHandle typeHandle = type.getValue();
/* 1044 */       if (handle == typeHandle || handle.asToken() == typeHandle.asToken()) {
/* 1045 */         return "T" + typeVar + ";";
/*      */       }
/*      */     } 
/* 1048 */     return handle.token.asType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addTypeVar(TypeVar typeVar, TokenHandle handle) throws IllegalArgumentException {
/* 1059 */     if (this.types.containsKey(typeVar)) {
/* 1060 */       throw new IllegalArgumentException("TypeVar " + typeVar + " is already present on " + this);
/*      */     }
/*      */     
/* 1063 */     this.types.put(typeVar, handle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setSuperClass(Token superClass) {
/* 1072 */     this.superClass = superClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSuperClass() {
/* 1081 */     return this.superClass.asType(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addInterface(Token iface) {
/* 1090 */     if (!iface.isRaw()) {
/* 1091 */       String raw = iface.asType(true);
/* 1092 */       for (ListIterator<Token> iter = this.interfaces.listIterator(); iter.hasNext(); ) {
/* 1093 */         Token intrface = iter.next();
/* 1094 */         if (intrface.isRaw() && intrface.asType(true).equals(raw)) {
/* 1095 */           iter.set(iface);
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1101 */     this.interfaces.add(iface);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addInterface(String iface) {
/* 1110 */     this.rawInterfaces.add(iface);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addRawInterface(String iface) {
/* 1119 */     Token token = new Token(iface);
/* 1120 */     String raw = token.asType(true);
/* 1121 */     for (Token intrface : this.interfaces) {
/* 1122 */       if (intrface.asType(true).equals(raw)) {
/*      */         return;
/*      */       }
/*      */     } 
/* 1126 */     this.interfaces.add(token);
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
/*      */   public void merge(ClassSignature other) {
/*      */     try {
/* 1140 */       Set<String> typeVars = new HashSet<String>();
/* 1141 */       for (TypeVar typeVar : this.types.keySet()) {
/* 1142 */         typeVars.add(typeVar.toString());
/*      */       }
/*      */       
/* 1145 */       other.conform(typeVars);
/* 1146 */     } catch (IllegalStateException ex) {
/*      */       
/* 1148 */       ex.printStackTrace();
/*      */       
/*      */       return;
/*      */     } 
/* 1152 */     for (Map.Entry<TypeVar, TokenHandle> type : other.types.entrySet()) {
/* 1153 */       addTypeVar(type.getKey(), type.getValue());
/*      */     }
/*      */     
/* 1156 */     for (Token iface : other.interfaces) {
/* 1157 */       addInterface(iface);
/*      */     }
/*      */   }
/*      */   
/*      */   private void conform(Set<String> typeVars) {
/* 1162 */     for (TypeVar typeVar : this.types.keySet()) {
/* 1163 */       String name = findUniqueName(typeVar.getOriginalName(), typeVars);
/* 1164 */       typeVar.rename(name);
/* 1165 */       typeVars.add(name);
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
/*      */   private String findUniqueName(String typeVar, Set<String> typeVars) {
/* 1178 */     if (!typeVars.contains(typeVar)) {
/* 1179 */       return typeVar;
/*      */     }
/*      */     
/* 1182 */     if (typeVar.length() == 1) {
/* 1183 */       String str = findOffsetName(typeVar.charAt(0), typeVars);
/* 1184 */       if (str != null) {
/* 1185 */         return str;
/*      */       }
/*      */     } 
/*      */     
/* 1189 */     String name = findOffsetName('T', typeVars, "", typeVar);
/* 1190 */     if (name != null) {
/* 1191 */       return name;
/*      */     }
/*      */     
/* 1194 */     name = findOffsetName('T', typeVars, typeVar, "");
/* 1195 */     if (name != null) {
/* 1196 */       return name;
/*      */     }
/*      */     
/* 1199 */     name = findOffsetName('T', typeVars, "T", typeVar);
/* 1200 */     if (name != null) {
/* 1201 */       return name;
/*      */     }
/*      */     
/* 1204 */     name = findOffsetName('T', typeVars, "", typeVar + "Type");
/* 1205 */     if (name != null) {
/* 1206 */       return name;
/*      */     }
/*      */     
/* 1209 */     throw new IllegalStateException("Failed to conform type var: " + typeVar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String findOffsetName(char c, Set<String> typeVars) {
/* 1220 */     return findOffsetName(c, typeVars, "", "");
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
/*      */   private String findOffsetName(char c, Set<String> typeVars, String prefix, String suffix) {
/* 1233 */     String name = String.format("%s%s%s", new Object[] { prefix, Character.valueOf(c), suffix });
/* 1234 */     if (!typeVars.contains(name)) {
/* 1235 */       return name;
/*      */     }
/*      */     
/* 1238 */     if (c > '@' && c < '[') {
/* 1239 */       int s; for (s = c - 64; s + 65 != c; s = ++s % 26) {
/* 1240 */         name = String.format("%s%s%s", new Object[] { prefix, Character.valueOf((char)(s + 65)), suffix });
/* 1241 */         if (!typeVars.contains(name)) {
/* 1242 */           return name;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1247 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SignatureVisitor getRemapper() {
/* 1256 */     return (SignatureVisitor)new SignatureRemapper();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1267 */     while (this.rawInterfaces.size() > 0) {
/* 1268 */       addRawInterface(this.rawInterfaces.remove());
/*      */     }
/*      */     
/* 1271 */     StringBuilder sb = new StringBuilder();
/*      */     
/* 1273 */     if (this.types.size() > 0) {
/* 1274 */       boolean valid = false;
/* 1275 */       StringBuilder types = new StringBuilder();
/* 1276 */       for (Map.Entry<TypeVar, TokenHandle> type : this.types.entrySet()) {
/* 1277 */         String bound = ((TokenHandle)type.getValue()).asBound();
/* 1278 */         if (!bound.isEmpty()) {
/* 1279 */           types.append(type.getKey()).append(':').append(bound);
/* 1280 */           valid = true;
/*      */         } 
/*      */       } 
/*      */       
/* 1284 */       if (valid) {
/* 1285 */         sb.append('<').append(types).append('>');
/*      */       }
/*      */     } 
/*      */     
/* 1289 */     sb.append(this.superClass.asType());
/*      */     
/* 1291 */     for (Token iface : this.interfaces) {
/* 1292 */       sb.append(iface.asType());
/*      */     }
/*      */     
/* 1295 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassSignature wake() {
/* 1302 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ClassSignature of(String signature) {
/* 1312 */     return (new ClassSignature()).read(signature);
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
/*      */   public static ClassSignature of(ClassNode classNode) {
/* 1324 */     if (classNode.signature != null) {
/* 1325 */       return of(classNode.signature);
/*      */     }
/*      */     
/* 1328 */     return generate(classNode);
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
/*      */   public static ClassSignature ofLazy(ClassNode classNode) {
/* 1341 */     if (classNode.signature != null) {
/* 1342 */       return new Lazy(classNode.signature);
/*      */     }
/*      */     
/* 1345 */     return generate(classNode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ClassSignature generate(ClassNode classNode) {
/* 1355 */     ClassSignature generated = new ClassSignature();
/* 1356 */     generated.setSuperClass(new Token((classNode.superName != null) ? classNode.superName : "java/lang/Object"));
/* 1357 */     for (String iface : classNode.interfaces) {
/* 1358 */       generated.addInterface(new Token(iface));
/*      */     }
/* 1360 */     return generated;
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\as\\util\ClassSignature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */