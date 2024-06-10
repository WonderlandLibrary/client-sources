/*     */ package org.spongepowered.asm.mixin.gen;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.FieldNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.gen.throwables.InvalidAccessorException;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
/*     */ import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
/*     */ import org.spongepowered.asm.util.Annotations;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AccessorInfo
/*     */   extends SpecialMethodInfo
/*     */ {
/*     */   public enum AccessorType
/*     */   {
/*  63 */     FIELD_GETTER((String)ImmutableSet.of("get", "is"))
/*     */     {
/*     */       AccessorGenerator getGenerator(AccessorInfo info) {
/*  66 */         return new AccessorGeneratorFieldGetter(info);
/*     */       }
/*     */     },
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     FIELD_SETTER((String)ImmutableSet.of("set"))
/*     */     {
/*     */       AccessorGenerator getGenerator(AccessorInfo info) {
/*  77 */         return new AccessorGeneratorFieldSetter(info);
/*     */       }
/*     */     },
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     METHOD_PROXY((String)ImmutableSet.of("call", "invoke"))
/*     */     {
/*     */       AccessorGenerator getGenerator(AccessorInfo info) {
/*  87 */         return new AccessorGeneratorMethodProxy(info);
/*     */       }
/*     */     };
/*     */     
/*     */     private final Set<String> expectedPrefixes;
/*     */     
/*     */     AccessorType(Set<String> expectedPrefixes) {
/*  94 */       this.expectedPrefixes = expectedPrefixes;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isExpectedPrefix(String prefix) {
/* 105 */       return this.expectedPrefixes.contains(prefix);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getExpectedPrefixes() {
/* 116 */       return this.expectedPrefixes.toString();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     abstract AccessorGenerator getGenerator(AccessorInfo param1AccessorInfo);
/*     */   }
/*     */ 
/*     */   
/* 125 */   protected static final Pattern PATTERN_ACCESSOR = Pattern.compile("^(get|set|is|invoke|call)(([A-Z])(.*?))(_\\$md.*)?$");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Type[] argTypes;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Type returnType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AccessorType type;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Type targetFieldType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final MemberInfo target;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FieldNode targetField;
/*     */ 
/*     */ 
/*     */   
/*     */   protected MethodNode targetMethod;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessorInfo(MixinTargetContext mixin, MethodNode method) {
/* 165 */     this(mixin, method, (Class)Accessor.class);
/*     */   }
/*     */   
/*     */   protected AccessorInfo(MixinTargetContext mixin, MethodNode method, Class<? extends Annotation> annotationClass) {
/* 169 */     super(mixin, method, Annotations.getVisible(method, annotationClass));
/* 170 */     this.argTypes = Type.getArgumentTypes(method.desc);
/* 171 */     this.returnType = Type.getReturnType(method.desc);
/* 172 */     this.type = initType();
/* 173 */     this.targetFieldType = initTargetFieldType();
/* 174 */     this.target = initTarget();
/*     */   }
/*     */   
/*     */   protected AccessorType initType() {
/* 178 */     if (this.returnType.equals(Type.VOID_TYPE)) {
/* 179 */       return AccessorType.FIELD_SETTER;
/*     */     }
/* 181 */     return AccessorType.FIELD_GETTER;
/*     */   }
/*     */   
/*     */   protected Type initTargetFieldType() {
/* 185 */     switch (this.type) {
/*     */       case FIELD_GETTER:
/* 187 */         if (this.argTypes.length > 0) {
/* 188 */           throw new InvalidAccessorException(this.mixin, this + " must take exactly 0 arguments, found " + this.argTypes.length);
/*     */         }
/* 190 */         return this.returnType;
/*     */       
/*     */       case FIELD_SETTER:
/* 193 */         if (this.argTypes.length != 1) {
/* 194 */           throw new InvalidAccessorException(this.mixin, this + " must take exactly 1 argument, found " + this.argTypes.length);
/*     */         }
/* 196 */         return this.argTypes[0];
/*     */     } 
/*     */     
/* 199 */     throw new InvalidAccessorException(this.mixin, "Computed unsupported accessor type " + this.type + " for " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected MemberInfo initTarget() {
/* 204 */     MemberInfo target = new MemberInfo(getTargetName(), null, this.targetFieldType.getDescriptor());
/* 205 */     this.annotation.visit("target", target.toString());
/* 206 */     return target;
/*     */   }
/*     */   
/*     */   protected String getTargetName() {
/* 210 */     String name = (String)Annotations.getValue(this.annotation);
/* 211 */     if (Strings.isNullOrEmpty(name)) {
/* 212 */       String inflectedTarget = inflectTarget();
/* 213 */       if (inflectedTarget == null) {
/* 214 */         throw new InvalidAccessorException(this.mixin, "Failed to inflect target name for " + this + ", supported prefixes: [get, set, is]");
/*     */       }
/* 216 */       return inflectedTarget;
/*     */     } 
/* 218 */     return (MemberInfo.parse(name, (IMixinContext)this.mixin)).name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String inflectTarget() {
/* 228 */     return inflectTarget(this.method.name, this.type, toString(), (IMixinContext)this.mixin, this.mixin
/* 229 */         .getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE));
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static String inflectTarget(String accessorName, AccessorType accessorType, String accessorDescription, IMixinContext context, boolean verbose) {
/* 251 */     Matcher nameMatcher = PATTERN_ACCESSOR.matcher(accessorName);
/* 252 */     if (nameMatcher.matches()) {
/* 253 */       String prefix = nameMatcher.group(1);
/* 254 */       String firstChar = nameMatcher.group(3);
/* 255 */       String remainder = nameMatcher.group(4);
/*     */       
/* 257 */       String name = String.format("%s%s", new Object[] { toLowerCase(firstChar, !isUpperCase(remainder)), remainder });
/* 258 */       if (!accessorType.isExpectedPrefix(prefix) && verbose) {
/* 259 */         LogManager.getLogger("mixin").warn("Unexpected prefix for {}, found [{}] expecting {}", new Object[] { accessorDescription, prefix, accessorType
/* 260 */               .getExpectedPrefixes() });
/*     */       }
/* 262 */       return (MemberInfo.parse(name, context)).name;
/*     */     } 
/* 264 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final MemberInfo getTarget() {
/* 271 */     return this.target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Type getTargetFieldType() {
/* 278 */     return this.targetFieldType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final FieldNode getTargetField() {
/* 285 */     return this.targetField;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final MethodNode getTargetMethod() {
/* 292 */     return this.targetMethod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Type getReturnType() {
/* 299 */     return this.returnType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Type[] getArgTypes() {
/* 306 */     return this.argTypes;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 311 */     return String.format("%s->@%s[%s]::%s%s", new Object[] { this.mixin.toString(), Bytecode.getSimpleName(this.annotation), this.type.toString(), this.method.name, this.method.desc });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void locate() {
/* 321 */     this.targetField = findTargetField();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodNode generate() {
/* 332 */     MethodNode generatedAccessor = this.type.getGenerator(this).generate();
/* 333 */     Bytecode.mergeAnnotations(this.method, generatedAccessor);
/* 334 */     return generatedAccessor;
/*     */   }
/*     */   
/*     */   private FieldNode findTargetField() {
/* 338 */     return findTarget(this.classNode.fields);
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
/*     */   protected <TNode> TNode findTarget(List<TNode> nodes) {
/* 350 */     TNode exactMatch = null;
/* 351 */     List<TNode> candidates = new ArrayList<TNode>();
/*     */     
/* 353 */     for (TNode node : nodes) {
/* 354 */       String desc = getNodeDesc(node);
/* 355 */       if (desc == null || !desc.equals(this.target.desc)) {
/*     */         continue;
/*     */       }
/*     */       
/* 359 */       String name = getNodeName(node);
/* 360 */       if (name != null) {
/* 361 */         if (name.equals(this.target.name)) {
/* 362 */           exactMatch = node;
/*     */         }
/* 364 */         if (name.equalsIgnoreCase(this.target.name)) {
/* 365 */           candidates.add(node);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 370 */     if (exactMatch != null) {
/* 371 */       if (candidates.size() > 1) {
/* 372 */         LogManager.getLogger("mixin").debug("{} found an exact match for {} but other candidates were found!", new Object[] { this, this.target });
/*     */       }
/* 374 */       return exactMatch;
/*     */     } 
/*     */     
/* 377 */     if (candidates.size() == 1) {
/* 378 */       return candidates.get(0);
/*     */     }
/*     */     
/* 381 */     String number = (candidates.size() == 0) ? "No" : "Multiple";
/* 382 */     throw new InvalidAccessorException(this, number + " candidates were found matching " + this.target + " in " + this.classNode.name + " for " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <TNode> String getNodeDesc(TNode node) {
/* 387 */     return (node instanceof MethodNode) ? ((MethodNode)node).desc : ((node instanceof FieldNode) ? ((FieldNode)node).desc : null);
/*     */   }
/*     */   
/*     */   private static <TNode> String getNodeName(TNode node) {
/* 391 */     return (node instanceof MethodNode) ? ((MethodNode)node).name : ((node instanceof FieldNode) ? ((FieldNode)node).name : null);
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
/*     */   public static AccessorInfo of(MixinTargetContext mixin, MethodNode method, Class<? extends Annotation> type) {
/* 404 */     if (type == Accessor.class)
/* 405 */       return new AccessorInfo(mixin, method); 
/* 406 */     if (type == Invoker.class) {
/* 407 */       return new InvokerInfo(mixin, method);
/*     */     }
/* 409 */     throw new InvalidAccessorException(mixin, "Could not parse accessor for unknown type " + type.getName());
/*     */   }
/*     */   
/*     */   private static String toLowerCase(String string, boolean condition) {
/* 413 */     return condition ? string.toLowerCase() : string;
/*     */   }
/*     */   
/*     */   private static boolean isUpperCase(String string) {
/* 417 */     return string.toUpperCase().equals(string);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\gen\AccessorInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */