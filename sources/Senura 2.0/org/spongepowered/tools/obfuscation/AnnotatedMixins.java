/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.processing.Filer;
/*     */ import javax.annotation.processing.Messager;
/*     */ import javax.annotation.processing.ProcessingEnvironment;
/*     */ import javax.annotation.processing.RoundEnvironment;
/*     */ import javax.lang.model.element.AnnotationMirror;
/*     */ import javax.lang.model.element.AnnotationValue;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.PackageElement;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import javax.lang.model.type.DeclaredType;
/*     */ import javax.lang.model.type.TypeMirror;
/*     */ import javax.lang.model.util.Elements;
/*     */ import javax.tools.Diagnostic;
/*     */ import javax.tools.FileObject;
/*     */ import javax.tools.StandardLocation;
/*     */ import org.spongepowered.asm.mixin.Overwrite;
/*     */ import org.spongepowered.asm.mixin.gen.Accessor;
/*     */ import org.spongepowered.asm.mixin.gen.Invoker;
/*     */ import org.spongepowered.asm.util.ITokenProvider;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IJavadocProvider;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.ITypeHandleProvider;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandleSimulated;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeReference;
/*     */ import org.spongepowered.tools.obfuscation.struct.InjectorRemap;
/*     */ import org.spongepowered.tools.obfuscation.validation.ParentValidator;
/*     */ import org.spongepowered.tools.obfuscation.validation.TargetValidator;
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
/*     */ final class AnnotatedMixins
/*     */   implements IMixinAnnotationProcessor, ITokenProvider, ITypeHandleProvider, IJavadocProvider
/*     */ {
/*     */   private static final String MAPID_SYSTEM_PROPERTY = "mixin.target.mapid";
/*  89 */   private static Map<ProcessingEnvironment, AnnotatedMixins> instances = new HashMap<ProcessingEnvironment, AnnotatedMixins>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final IMixinAnnotationProcessor.CompilerEnvironment env;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ProcessingEnvironment processingEnv;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   private final Map<String, AnnotatedMixin> mixins = new HashMap<String, AnnotatedMixin>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private final List<AnnotatedMixin> mixinsForPass = new ArrayList<AnnotatedMixin>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final IObfuscationManager obf;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<IMixinValidator> validators;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   private final Map<String, Integer> tokenCache = new HashMap<String, Integer>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final TargetMap targets;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Properties properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AnnotatedMixins(ProcessingEnvironment processingEnv) {
/* 141 */     this.env = detectEnvironment(processingEnv);
/* 142 */     this.processingEnv = processingEnv;
/*     */     
/* 144 */     printMessage(Diagnostic.Kind.NOTE, "SpongePowered MIXIN Annotation Processor Version=0.7.11");
/*     */     
/* 146 */     this.targets = initTargetMap();
/* 147 */     this.obf = new ObfuscationManager(this);
/* 148 */     this.obf.init();
/*     */     
/* 150 */     this.validators = (List<IMixinValidator>)ImmutableList.of(new ParentValidator(this), new TargetValidator(this));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     initTokenCache(getOption("tokens"));
/*     */   }
/*     */   
/*     */   protected TargetMap initTargetMap() {
/* 159 */     TargetMap targets = TargetMap.create(System.getProperty("mixin.target.mapid"));
/* 160 */     System.setProperty("mixin.target.mapid", targets.getSessionId());
/* 161 */     String targetsFileName = getOption("dependencyTargetsFile");
/* 162 */     if (targetsFileName != null) {
/*     */       try {
/* 164 */         targets.readImports(new File(targetsFileName));
/* 165 */       } catch (IOException ex) {
/* 166 */         printMessage(Diagnostic.Kind.WARNING, "Could not read from specified imports file: " + targetsFileName);
/*     */       } 
/*     */     }
/* 169 */     return targets;
/*     */   }
/*     */   
/*     */   private void initTokenCache(String tokens) {
/* 173 */     if (tokens != null) {
/* 174 */       Pattern tokenPattern = Pattern.compile("^([A-Z0-9\\-_\\.]+)=([0-9]+)$");
/*     */       
/* 176 */       String[] tokenValues = tokens.replaceAll("\\s", "").toUpperCase().split("[;,]");
/* 177 */       for (String tokenValue : tokenValues) {
/* 178 */         Matcher tokenMatcher = tokenPattern.matcher(tokenValue);
/* 179 */         if (tokenMatcher.matches()) {
/* 180 */           this.tokenCache.put(tokenMatcher.group(1), Integer.valueOf(Integer.parseInt(tokenMatcher.group(2))));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ITypeHandleProvider getTypeProvider() {
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ITokenProvider getTokenProvider() {
/* 193 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public IObfuscationManager getObfuscationManager() {
/* 198 */     return this.obf;
/*     */   }
/*     */ 
/*     */   
/*     */   public IJavadocProvider getJavadocProvider() {
/* 203 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ProcessingEnvironment getProcessingEnvironment() {
/* 208 */     return this.processingEnv;
/*     */   }
/*     */ 
/*     */   
/*     */   public IMixinAnnotationProcessor.CompilerEnvironment getCompilerEnvironment() {
/* 213 */     return this.env;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getToken(String token) {
/* 218 */     if (this.tokenCache.containsKey(token)) {
/* 219 */       return this.tokenCache.get(token);
/*     */     }
/*     */     
/* 222 */     String option = getOption(token);
/* 223 */     Integer value = null;
/*     */     try {
/* 225 */       value = Integer.valueOf(Integer.parseInt(option));
/* 226 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 230 */     this.tokenCache.put(token, value);
/* 231 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getOption(String option) {
/* 236 */     if (option == null) {
/* 237 */       return null;
/*     */     }
/*     */     
/* 240 */     String value = this.processingEnv.getOptions().get(option);
/* 241 */     if (value != null) {
/* 242 */       return value;
/*     */     }
/*     */     
/* 245 */     return getProperties().getProperty(option);
/*     */   }
/*     */   
/*     */   public Properties getProperties() {
/* 249 */     if (this.properties == null) {
/* 250 */       this.properties = new Properties();
/*     */       
/*     */       try {
/* 253 */         Filer filer = this.processingEnv.getFiler();
/* 254 */         FileObject propertyFile = filer.getResource(StandardLocation.SOURCE_PATH, "", "mixin.properties");
/* 255 */         if (propertyFile != null) {
/* 256 */           InputStream inputStream = propertyFile.openInputStream();
/* 257 */           this.properties.load(inputStream);
/* 258 */           inputStream.close();
/*     */         } 
/* 260 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 265 */     return this.properties;
/*     */   }
/*     */   
/*     */   private IMixinAnnotationProcessor.CompilerEnvironment detectEnvironment(ProcessingEnvironment processingEnv) {
/* 269 */     if (processingEnv.getClass().getName().contains("jdt")) {
/* 270 */       return IMixinAnnotationProcessor.CompilerEnvironment.JDT;
/*     */     }
/*     */     
/* 273 */     return IMixinAnnotationProcessor.CompilerEnvironment.JAVAC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeMappings() {
/* 280 */     this.obf.writeMappings();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeReferences() {
/* 287 */     this.obf.writeReferences();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 294 */     this.mixins.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerMixin(TypeElement mixinType) {
/* 301 */     String name = mixinType.getQualifiedName().toString();
/*     */     
/* 303 */     if (!this.mixins.containsKey(name)) {
/* 304 */       AnnotatedMixin mixin = new AnnotatedMixin(this, mixinType);
/* 305 */       this.targets.registerTargets(mixin);
/* 306 */       mixin.runValidators(IMixinValidator.ValidationPass.EARLY, this.validators);
/* 307 */       this.mixins.put(name, mixin);
/* 308 */       this.mixinsForPass.add(mixin);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotatedMixin getMixin(TypeElement mixinType) {
/* 316 */     return getMixin(mixinType.getQualifiedName().toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotatedMixin getMixin(String mixinType) {
/* 323 */     return this.mixins.get(mixinType);
/*     */   }
/*     */   
/*     */   public Collection<TypeMirror> getMixinsTargeting(TypeMirror targetType) {
/* 327 */     return getMixinsTargeting((TypeElement)((DeclaredType)targetType).asElement());
/*     */   }
/*     */   
/*     */   public Collection<TypeMirror> getMixinsTargeting(TypeElement targetType) {
/* 331 */     List<TypeMirror> minions = new ArrayList<TypeMirror>();
/*     */     
/* 333 */     for (TypeReference mixin : this.targets.getMixinsTargeting(targetType)) {
/* 334 */       TypeHandle handle = mixin.getHandle(this.processingEnv);
/* 335 */       if (handle != null) {
/* 336 */         minions.add(handle.getType());
/*     */       }
/*     */     } 
/*     */     
/* 340 */     return minions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerAccessor(TypeElement mixinType, ExecutableElement method) {
/* 350 */     AnnotatedMixin mixinClass = getMixin(mixinType);
/* 351 */     if (mixinClass == null) {
/* 352 */       printMessage(Diagnostic.Kind.ERROR, "Found @Accessor annotation on a non-mixin method", method);
/*     */       
/*     */       return;
/*     */     } 
/* 356 */     AnnotationHandle accessor = AnnotationHandle.of(method, Accessor.class);
/* 357 */     mixinClass.registerAccessor(method, accessor, shouldRemap(mixinClass, accessor));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerInvoker(TypeElement mixinType, ExecutableElement method) {
/* 367 */     AnnotatedMixin mixinClass = getMixin(mixinType);
/* 368 */     if (mixinClass == null) {
/* 369 */       printMessage(Diagnostic.Kind.ERROR, "Found @Accessor annotation on a non-mixin method", method);
/*     */       
/*     */       return;
/*     */     } 
/* 373 */     AnnotationHandle invoker = AnnotationHandle.of(method, Invoker.class);
/* 374 */     mixinClass.registerInvoker(method, invoker, shouldRemap(mixinClass, invoker));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerOverwrite(TypeElement mixinType, ExecutableElement method) {
/* 384 */     AnnotatedMixin mixinClass = getMixin(mixinType);
/* 385 */     if (mixinClass == null) {
/* 386 */       printMessage(Diagnostic.Kind.ERROR, "Found @Overwrite annotation on a non-mixin method", method);
/*     */       
/*     */       return;
/*     */     } 
/* 390 */     AnnotationHandle overwrite = AnnotationHandle.of(method, Overwrite.class);
/* 391 */     mixinClass.registerOverwrite(method, overwrite, shouldRemap(mixinClass, overwrite));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerShadow(TypeElement mixinType, VariableElement field, AnnotationHandle shadow) {
/* 402 */     AnnotatedMixin mixinClass = getMixin(mixinType);
/* 403 */     if (mixinClass == null) {
/* 404 */       printMessage(Diagnostic.Kind.ERROR, "Found @Shadow annotation on a non-mixin field", field);
/*     */       
/*     */       return;
/*     */     } 
/* 408 */     mixinClass.registerShadow(field, shadow, shouldRemap(mixinClass, shadow));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerShadow(TypeElement mixinType, ExecutableElement method, AnnotationHandle shadow) {
/* 419 */     AnnotatedMixin mixinClass = getMixin(mixinType);
/* 420 */     if (mixinClass == null) {
/* 421 */       printMessage(Diagnostic.Kind.ERROR, "Found @Shadow annotation on a non-mixin method", method);
/*     */       
/*     */       return;
/*     */     } 
/* 425 */     mixinClass.registerShadow(method, shadow, shouldRemap(mixinClass, shadow));
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
/*     */   public void registerInjector(TypeElement mixinType, ExecutableElement method, AnnotationHandle inject) {
/* 437 */     AnnotatedMixin mixinClass = getMixin(mixinType);
/* 438 */     if (mixinClass == null) {
/* 439 */       printMessage(Diagnostic.Kind.ERROR, "Found " + inject + " annotation on a non-mixin method", method);
/*     */       
/*     */       return;
/*     */     } 
/* 443 */     InjectorRemap remap = new InjectorRemap(shouldRemap(mixinClass, inject));
/* 444 */     mixinClass.registerInjector(method, inject, remap);
/* 445 */     remap.dispatchPendingMessages((Messager)this);
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
/*     */   public void registerSoftImplements(TypeElement mixin, AnnotationHandle implementsAnnotation) {
/* 457 */     AnnotatedMixin mixinClass = getMixin(mixin);
/* 458 */     if (mixinClass == null) {
/* 459 */       printMessage(Diagnostic.Kind.ERROR, "Found @Implements annotation on a non-mixin class");
/*     */       
/*     */       return;
/*     */     } 
/* 463 */     mixinClass.registerSoftImplements(implementsAnnotation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPassStarted() {
/* 471 */     this.mixinsForPass.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPassCompleted(RoundEnvironment roundEnv) {
/* 478 */     if (!"true".equalsIgnoreCase(getOption("disableTargetExport"))) {
/* 479 */       this.targets.write(true);
/*     */     }
/*     */     
/* 482 */     for (AnnotatedMixin mixin : roundEnv.processingOver() ? (Object<?>)this.mixins.values() : (Object<?>)this.mixinsForPass) {
/* 483 */       mixin.runValidators(roundEnv.processingOver() ? IMixinValidator.ValidationPass.FINAL : IMixinValidator.ValidationPass.LATE, this.validators);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean shouldRemap(AnnotatedMixin mixinClass, AnnotationHandle annotation) {
/* 488 */     return annotation.getBoolean("remap", mixinClass.remap());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printMessage(Diagnostic.Kind kind, CharSequence msg) {
/* 496 */     if (this.env == IMixinAnnotationProcessor.CompilerEnvironment.JAVAC || kind != Diagnostic.Kind.NOTE) {
/* 497 */       this.processingEnv.getMessager().printMessage(kind, msg);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element element) {
/* 506 */     this.processingEnv.getMessager().printMessage(kind, msg, element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element element, AnnotationMirror annotation) {
/* 514 */     this.processingEnv.getMessager().printMessage(kind, msg, element, annotation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element element, AnnotationMirror annotation, AnnotationValue value) {
/* 522 */     this.processingEnv.getMessager().printMessage(kind, msg, element, annotation, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeHandle getTypeHandle(String name) {
/* 531 */     name = name.replace('/', '.');
/*     */     
/* 533 */     Elements elements = this.processingEnv.getElementUtils();
/* 534 */     TypeElement element = elements.getTypeElement(name);
/* 535 */     if (element != null) {
/*     */       try {
/* 537 */         return new TypeHandle(element);
/* 538 */       } catch (NullPointerException nullPointerException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 543 */     int lastDotPos = name.lastIndexOf('.');
/* 544 */     if (lastDotPos > -1) {
/* 545 */       String pkg = name.substring(0, lastDotPos);
/* 546 */       PackageElement packageElement = elements.getPackageElement(pkg);
/* 547 */       if (packageElement != null) {
/* 548 */         return new TypeHandle(packageElement, name);
/*     */       }
/*     */     } 
/*     */     
/* 552 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeHandle getSimulatedHandle(String name, TypeMirror simulatedTarget) {
/* 560 */     name = name.replace('/', '.');
/* 561 */     int lastDotPos = name.lastIndexOf('.');
/* 562 */     if (lastDotPos > -1) {
/* 563 */       String pkg = name.substring(0, lastDotPos);
/* 564 */       PackageElement packageElement = this.processingEnv.getElementUtils().getPackageElement(pkg);
/* 565 */       if (packageElement != null) {
/* 566 */         return (TypeHandle)new TypeHandleSimulated(packageElement, name, simulatedTarget);
/*     */       }
/*     */     } 
/*     */     
/* 570 */     return (TypeHandle)new TypeHandleSimulated(name, simulatedTarget);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJavadoc(Element element) {
/* 579 */     Elements elements = this.processingEnv.getElementUtils();
/* 580 */     return elements.getDocComment(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AnnotatedMixins getMixinsForEnvironment(ProcessingEnvironment processingEnv) {
/* 587 */     AnnotatedMixins mixins = instances.get(processingEnv);
/* 588 */     if (mixins == null) {
/* 589 */       mixins = new AnnotatedMixins(processingEnv);
/* 590 */       instances.put(processingEnv, mixins);
/*     */     } 
/* 592 */     return mixins;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\AnnotatedMixins.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */