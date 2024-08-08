package org.spongepowered.tools.obfuscation;

import com.google.common.collect.ImmutableList;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import javax.tools.Diagnostic.Kind;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.util.ITokenProvider;
import org.spongepowered.tools.obfuscation.interfaces.IJavadocProvider;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.interfaces.ITypeHandleProvider;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandleSimulated;
import org.spongepowered.tools.obfuscation.mirror.TypeReference;
import org.spongepowered.tools.obfuscation.struct.InjectorRemap;
import org.spongepowered.tools.obfuscation.validation.ParentValidator;
import org.spongepowered.tools.obfuscation.validation.TargetValidator;

final class AnnotatedMixins implements IMixinAnnotationProcessor, ITokenProvider, ITypeHandleProvider, IJavadocProvider {
   private static final String MAPID_SYSTEM_PROPERTY = "mixin.target.mapid";
   private static Map instances = new HashMap();
   private final IMixinAnnotationProcessor.CompilerEnvironment env;
   private final ProcessingEnvironment processingEnv;
   private final Map mixins = new HashMap();
   private final List mixinsForPass = new ArrayList();
   private final IObfuscationManager obf;
   private final List validators;
   private final Map tokenCache = new HashMap();
   private final TargetMap targets;
   private Properties properties;

   private AnnotatedMixins(ProcessingEnvironment var1) {
      this.env = this.detectEnvironment(var1);
      this.processingEnv = var1;
      this.printMessage(Kind.NOTE, "SpongePowered MIXIN Annotation Processor Version=0.7.4");
      this.targets = this.initTargetMap();
      this.obf = new ObfuscationManager(this);
      this.obf.init();
      this.validators = ImmutableList.of(new ParentValidator(this), new TargetValidator(this));
      this.initTokenCache(this.getOption("tokens"));
   }

   protected TargetMap initTargetMap() {
      TargetMap var1 = TargetMap.create(System.getProperty("mixin.target.mapid"));
      System.setProperty("mixin.target.mapid", var1.getSessionId());
      String var2 = this.getOption("dependencyTargetsFile");
      if (var2 != null) {
         try {
            var1.readImports(new File(var2));
         } catch (IOException var4) {
            this.printMessage(Kind.WARNING, "Could not read from specified imports file: " + var2);
         }
      }

      return var1;
   }

   private void initTokenCache(String var1) {
      if (var1 != null) {
         Pattern var2 = Pattern.compile("^([A-Z0-9\\-_\\.]+)=([0-9]+)$");
         String[] var3 = var1.replaceAll("\\s", "").toUpperCase().split("[;,]");
         String[] var4 = var3;
         int var5 = var3.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String var7 = var4[var6];
            Matcher var8 = var2.matcher(var7);
            if (var8.matches()) {
               this.tokenCache.put(var8.group(1), Integer.parseInt(var8.group(2)));
            }
         }
      }

   }

   public ITypeHandleProvider getTypeProvider() {
      return this;
   }

   public ITokenProvider getTokenProvider() {
      return this;
   }

   public IObfuscationManager getObfuscationManager() {
      return this.obf;
   }

   public IJavadocProvider getJavadocProvider() {
      return this;
   }

   public ProcessingEnvironment getProcessingEnvironment() {
      return this.processingEnv;
   }

   public IMixinAnnotationProcessor.CompilerEnvironment getCompilerEnvironment() {
      return this.env;
   }

   public Integer getToken(String var1) {
      if (this.tokenCache.containsKey(var1)) {
         return (Integer)this.tokenCache.get(var1);
      } else {
         String var2 = this.getOption(var1);
         Integer var3 = null;

         try {
            var3 = Integer.parseInt(var2);
         } catch (Exception var5) {
         }

         this.tokenCache.put(var1, var3);
         return var3;
      }
   }

   public String getOption(String var1) {
      if (var1 == null) {
         return null;
      } else {
         String var2 = (String)this.processingEnv.getOptions().get(var1);
         return var2 != null ? var2 : this.getProperties().getProperty(var1);
      }
   }

   public Properties getProperties() {
      if (this.properties == null) {
         this.properties = new Properties();

         try {
            Filer var1 = this.processingEnv.getFiler();
            FileObject var2 = var1.getResource(StandardLocation.SOURCE_PATH, "", "mixin.properties");
            if (var2 != null) {
               InputStream var3 = var2.openInputStream();
               this.properties.load(var3);
               var3.close();
            }
         } catch (Exception var4) {
         }
      }

      return this.properties;
   }

   private IMixinAnnotationProcessor.CompilerEnvironment detectEnvironment(ProcessingEnvironment var1) {
      return var1.getClass().getName().contains("jdt") ? IMixinAnnotationProcessor.CompilerEnvironment.JDT : IMixinAnnotationProcessor.CompilerEnvironment.JAVAC;
   }

   public void writeMappings() {
      this.obf.writeMappings();
   }

   public void writeReferences() {
      this.obf.writeReferences();
   }

   public void clear() {
      this.mixins.clear();
   }

   public void registerMixin(TypeElement var1) {
      String var2 = var1.getQualifiedName().toString();
      if (!this.mixins.containsKey(var2)) {
         AnnotatedMixin var3 = new AnnotatedMixin(this, var1);
         this.targets.registerTargets(var3);
         var3.runValidators(IMixinValidator.ValidationPass.EARLY, this.validators);
         this.mixins.put(var2, var3);
         this.mixinsForPass.add(var3);
      }

   }

   public AnnotatedMixin getMixin(TypeElement var1) {
      return this.getMixin(var1.getQualifiedName().toString());
   }

   public AnnotatedMixin getMixin(String var1) {
      return (AnnotatedMixin)this.mixins.get(var1);
   }

   public Collection getMixinsTargeting(TypeMirror var1) {
      return this.getMixinsTargeting((TypeElement)((DeclaredType)var1).asElement());
   }

   public Collection getMixinsTargeting(TypeElement var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.targets.getMixinsTargeting(var1).iterator();

      while(var3.hasNext()) {
         TypeReference var4 = (TypeReference)var3.next();
         TypeHandle var5 = var4.getHandle(this.processingEnv);
         if (var5 != null) {
            var2.add(var5.getType());
         }
      }

      return var2;
   }

   public void registerAccessor(TypeElement var1, ExecutableElement var2) {
      AnnotatedMixin var3 = this.getMixin(var1);
      if (var3 == null) {
         this.printMessage(Kind.ERROR, "Found @Accessor annotation on a non-mixin method", var2);
      } else {
         AnnotationHandle var4 = AnnotationHandle.of(var2, Accessor.class);
         var3.registerAccessor(var2, var4, this.shouldRemap(var3, var4));
      }
   }

   public void registerInvoker(TypeElement var1, ExecutableElement var2) {
      AnnotatedMixin var3 = this.getMixin(var1);
      if (var3 == null) {
         this.printMessage(Kind.ERROR, "Found @Accessor annotation on a non-mixin method", var2);
      } else {
         AnnotationHandle var4 = AnnotationHandle.of(var2, Invoker.class);
         var3.registerInvoker(var2, var4, this.shouldRemap(var3, var4));
      }
   }

   public void registerOverwrite(TypeElement var1, ExecutableElement var2) {
      AnnotatedMixin var3 = this.getMixin(var1);
      if (var3 == null) {
         this.printMessage(Kind.ERROR, "Found @Overwrite annotation on a non-mixin method", var2);
      } else {
         AnnotationHandle var4 = AnnotationHandle.of(var2, Overwrite.class);
         var3.registerOverwrite(var2, var4, this.shouldRemap(var3, var4));
      }
   }

   public void registerShadow(TypeElement var1, VariableElement var2, AnnotationHandle var3) {
      AnnotatedMixin var4 = this.getMixin(var1);
      if (var4 == null) {
         this.printMessage(Kind.ERROR, "Found @Shadow annotation on a non-mixin field", var2);
      } else {
         var4.registerShadow(var2, var3, this.shouldRemap(var4, var3));
      }
   }

   public void registerShadow(TypeElement var1, ExecutableElement var2, AnnotationHandle var3) {
      AnnotatedMixin var4 = this.getMixin(var1);
      if (var4 == null) {
         this.printMessage(Kind.ERROR, "Found @Shadow annotation on a non-mixin method", var2);
      } else {
         var4.registerShadow(var2, var3, this.shouldRemap(var4, var3));
      }
   }

   public void registerInjector(TypeElement var1, ExecutableElement var2, AnnotationHandle var3) {
      AnnotatedMixin var4 = this.getMixin(var1);
      if (var4 == null) {
         this.printMessage(Kind.ERROR, "Found " + var3 + " annotation on a non-mixin method", var2);
      } else {
         InjectorRemap var5 = new InjectorRemap(this.shouldRemap(var4, var3));
         var4.registerInjector(var2, var3, var5);
         var5.dispatchPendingMessages(this);
      }
   }

   public void registerSoftImplements(TypeElement var1, AnnotationHandle var2) {
      AnnotatedMixin var3 = this.getMixin(var1);
      if (var3 == null) {
         this.printMessage(Kind.ERROR, "Found @Implements annotation on a non-mixin class");
      } else {
         var3.registerSoftImplements(var2);
      }
   }

   public void onPassStarted() {
      this.mixinsForPass.clear();
   }

   public void onPassCompleted(RoundEnvironment var1) {
      if (!"true".equalsIgnoreCase(this.getOption("disableTargetExport"))) {
         this.targets.write(true);
      }

      Iterator var2 = ((Collection)(var1.processingOver() ? this.mixins.values() : this.mixinsForPass)).iterator();

      while(var2.hasNext()) {
         AnnotatedMixin var3 = (AnnotatedMixin)var2.next();
         var3.runValidators(var1.processingOver() ? IMixinValidator.ValidationPass.FINAL : IMixinValidator.ValidationPass.LATE, this.validators);
      }

   }

   private boolean shouldRemap(AnnotatedMixin var1, AnnotationHandle var2) {
      return var2.getBoolean("remap", var1.remap());
   }

   public void printMessage(Kind var1, CharSequence var2) {
      if (this.env == IMixinAnnotationProcessor.CompilerEnvironment.JAVAC || var1 != Kind.NOTE) {
         this.processingEnv.getMessager().printMessage(var1, var2);
      }

   }

   public void printMessage(Kind var1, CharSequence var2, Element var3) {
      this.processingEnv.getMessager().printMessage(var1, var2, var3);
   }

   public void printMessage(Kind var1, CharSequence var2, Element var3, AnnotationMirror var4) {
      this.processingEnv.getMessager().printMessage(var1, var2, var3, var4);
   }

   public void printMessage(Kind var1, CharSequence var2, Element var3, AnnotationMirror var4, AnnotationValue var5) {
      this.processingEnv.getMessager().printMessage(var1, var2, var3, var4, var5);
   }

   public TypeHandle getTypeHandle(String var1) {
      var1 = var1.replace('/', '.');
      Elements var2 = this.processingEnv.getElementUtils();
      TypeElement var3 = var2.getTypeElement(var1);
      if (var3 != null) {
         try {
            return new TypeHandle(var3);
         } catch (NullPointerException var7) {
         }
      }

      int var4 = var1.lastIndexOf(46);
      if (var4 > -1) {
         String var5 = var1.substring(0, var4);
         PackageElement var6 = var2.getPackageElement(var5);
         if (var6 != null) {
            return new TypeHandle(var6, var1);
         }
      }

      return null;
   }

   public TypeHandle getSimulatedHandle(String var1, TypeMirror var2) {
      var1 = var1.replace('/', '.');
      int var3 = var1.lastIndexOf(46);
      if (var3 > -1) {
         String var4 = var1.substring(0, var3);
         PackageElement var5 = this.processingEnv.getElementUtils().getPackageElement(var4);
         if (var5 != null) {
            return new TypeHandleSimulated(var5, var1, var2);
         }
      }

      return new TypeHandleSimulated(var1, var2);
   }

   public String getJavadoc(Element var1) {
      Elements var2 = this.processingEnv.getElementUtils();
      return var2.getDocComment(var1);
   }

   public static AnnotatedMixins getMixinsForEnvironment(ProcessingEnvironment var0) {
      AnnotatedMixins var1 = (AnnotatedMixins)instances.get(var0);
      if (var1 == null) {
         var1 = new AnnotatedMixins(var0);
         instances.put(var0, var1);
      }

      return var1;
   }
}
