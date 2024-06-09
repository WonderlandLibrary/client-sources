package org.spongepowered.tools.obfuscation;

import org.spongepowered.asm.util.*;
import org.spongepowered.tools.obfuscation.interfaces.*;
import org.spongepowered.tools.obfuscation.validation.*;
import com.google.common.collect.*;
import java.util.regex.*;
import javax.tools.*;
import java.io.*;
import javax.lang.model.type.*;
import java.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.gen.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.tools.obfuscation.struct.*;
import javax.annotation.processing.*;
import javax.lang.model.util.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;

final class AnnotatedMixins implements IMixinAnnotationProcessor, ITokenProvider, ITypeHandleProvider, IJavadocProvider
{
    private static final String MAPID_SYSTEM_PROPERTY = "mixin.target.mapid";
    private static Map<ProcessingEnvironment, AnnotatedMixins> instances;
    private final CompilerEnvironment env;
    private final ProcessingEnvironment processingEnv;
    private final Map<String, AnnotatedMixin> mixins;
    private final List<AnnotatedMixin> mixinsForPass;
    private final IObfuscationManager obf;
    private final List<IMixinValidator> validators;
    private final Map<String, Integer> tokenCache;
    private final TargetMap targets;
    private Properties properties;
    
    private AnnotatedMixins(final ProcessingEnvironment processingEnv) {
        super();
        this.mixins = new HashMap<String, AnnotatedMixin>();
        this.mixinsForPass = new ArrayList<AnnotatedMixin>();
        this.tokenCache = new HashMap<String, Integer>();
        this.env = this.detectEnvironment(processingEnv);
        this.processingEnv = processingEnv;
        this.printMessage(Diagnostic.Kind.NOTE, "SpongePowered MIXIN Annotation Processor Version=0.7.11");
        this.targets = this.initTargetMap();
        (this.obf = new ObfuscationManager(this)).init();
        this.validators = (List<IMixinValidator>)ImmutableList.of(new ParentValidator(this), new TargetValidator(this));
        this.initTokenCache(this.getOption("tokens"));
    }
    
    protected TargetMap initTargetMap() {
        final TargetMap create = TargetMap.create(System.getProperty("mixin.target.mapid"));
        System.setProperty("mixin.target.mapid", create.getSessionId());
        final String option = this.getOption("dependencyTargetsFile");
        if (option != null) {
            try {
                create.readImports(new File(option));
            }
            catch (IOException ex) {
                this.printMessage(Diagnostic.Kind.WARNING, "Could not read from specified imports file: " + option);
            }
        }
        return create;
    }
    
    private void initTokenCache(final String s) {
        if (s != null) {
            final Pattern compile = Pattern.compile("^([A-Z0-9\\-_\\.]+)=([0-9]+)$");
            final String[] split = s.replaceAll("\\s", "").toUpperCase().split("[;,]");
            for (int length = split.length, i = 0; i < length; ++i) {
                final Matcher matcher = compile.matcher(split[i]);
                if (matcher.matches()) {
                    this.tokenCache.put(matcher.group(1), Integer.parseInt(matcher.group(2)));
                }
            }
        }
    }
    
    @Override
    public ITypeHandleProvider getTypeProvider() {
        return this;
    }
    
    @Override
    public ITokenProvider getTokenProvider() {
        return this;
    }
    
    @Override
    public IObfuscationManager getObfuscationManager() {
        return this.obf;
    }
    
    @Override
    public IJavadocProvider getJavadocProvider() {
        return this;
    }
    
    @Override
    public ProcessingEnvironment getProcessingEnvironment() {
        return this.processingEnv;
    }
    
    @Override
    public CompilerEnvironment getCompilerEnvironment() {
        return this.env;
    }
    
    @Override
    public Integer getToken(final String s) {
        if (this.tokenCache.containsKey(s)) {
            return this.tokenCache.get(s);
        }
        final String option = this.getOption(s);
        Integer value = null;
        try {
            value = Integer.parseInt(option);
        }
        catch (Exception ex) {}
        this.tokenCache.put(s, value);
        return value;
    }
    
    @Override
    public String getOption(final String s) {
        if (s == null) {
            return null;
        }
        final String s2 = this.processingEnv.getOptions().get(s);
        if (s2 != null) {
            return s2;
        }
        return this.getProperties().getProperty(s);
    }
    
    public Properties getProperties() {
        if (this.properties == null) {
            this.properties = new Properties();
            try {
                final FileObject resource = this.processingEnv.getFiler().getResource(StandardLocation.SOURCE_PATH, "", "mixin.properties");
                if (resource != null) {
                    final InputStream openInputStream = resource.openInputStream();
                    this.properties.load(openInputStream);
                    openInputStream.close();
                }
            }
            catch (Exception ex) {}
        }
        return this.properties;
    }
    
    private CompilerEnvironment detectEnvironment(final ProcessingEnvironment processingEnvironment) {
        if (processingEnvironment.getClass().getName().contains("jdt")) {
            return CompilerEnvironment.JDT;
        }
        return CompilerEnvironment.JAVAC;
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
    
    public void registerMixin(final TypeElement typeElement) {
        final String string = typeElement.getQualifiedName().toString();
        if (!this.mixins.containsKey(string)) {
            final AnnotatedMixin annotatedMixin = new AnnotatedMixin(this, typeElement);
            this.targets.registerTargets(annotatedMixin);
            annotatedMixin.runValidators(IMixinValidator.ValidationPass.EARLY, this.validators);
            this.mixins.put(string, annotatedMixin);
            this.mixinsForPass.add(annotatedMixin);
        }
    }
    
    public AnnotatedMixin getMixin(final TypeElement typeElement) {
        return this.getMixin(typeElement.getQualifiedName().toString());
    }
    
    public AnnotatedMixin getMixin(final String s) {
        return this.mixins.get(s);
    }
    
    public Collection<TypeMirror> getMixinsTargeting(final TypeMirror typeMirror) {
        return this.getMixinsTargeting((TypeElement)((DeclaredType)typeMirror).asElement());
    }
    
    public Collection<TypeMirror> getMixinsTargeting(final TypeElement typeElement) {
        final ArrayList<TypeMirror> list = new ArrayList<TypeMirror>();
        final Iterator<TypeReference> iterator = this.targets.getMixinsTargeting(typeElement).iterator();
        while (iterator.hasNext()) {
            final TypeHandle handle = iterator.next().getHandle(this.processingEnv);
            if (handle != null) {
                list.add(handle.getType());
            }
        }
        return list;
    }
    
    public void registerAccessor(final TypeElement typeElement, final ExecutableElement executableElement) {
        final AnnotatedMixin mixin = this.getMixin(typeElement);
        if (mixin == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found @Accessor annotation on a non-mixin method", executableElement);
            return;
        }
        final AnnotationHandle of = AnnotationHandle.of(executableElement, Accessor.class);
        mixin.registerAccessor(executableElement, of, this.shouldRemap(mixin, of));
    }
    
    public void registerInvoker(final TypeElement typeElement, final ExecutableElement executableElement) {
        final AnnotatedMixin mixin = this.getMixin(typeElement);
        if (mixin == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found @Accessor annotation on a non-mixin method", executableElement);
            return;
        }
        final AnnotationHandle of = AnnotationHandle.of(executableElement, Invoker.class);
        mixin.registerInvoker(executableElement, of, this.shouldRemap(mixin, of));
    }
    
    public void registerOverwrite(final TypeElement typeElement, final ExecutableElement executableElement) {
        final AnnotatedMixin mixin = this.getMixin(typeElement);
        if (mixin == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found @Overwrite annotation on a non-mixin method", executableElement);
            return;
        }
        final AnnotationHandle of = AnnotationHandle.of(executableElement, Overwrite.class);
        mixin.registerOverwrite(executableElement, of, this.shouldRemap(mixin, of));
    }
    
    public void registerShadow(final TypeElement typeElement, final VariableElement variableElement, final AnnotationHandle annotationHandle) {
        final AnnotatedMixin mixin = this.getMixin(typeElement);
        if (mixin == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found @Shadow annotation on a non-mixin field", variableElement);
            return;
        }
        mixin.registerShadow(variableElement, annotationHandle, this.shouldRemap(mixin, annotationHandle));
    }
    
    public void registerShadow(final TypeElement typeElement, final ExecutableElement executableElement, final AnnotationHandle annotationHandle) {
        final AnnotatedMixin mixin = this.getMixin(typeElement);
        if (mixin == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found @Shadow annotation on a non-mixin method", executableElement);
            return;
        }
        mixin.registerShadow(executableElement, annotationHandle, this.shouldRemap(mixin, annotationHandle));
    }
    
    public void registerInjector(final TypeElement typeElement, final ExecutableElement executableElement, final AnnotationHandle annotationHandle) {
        final AnnotatedMixin mixin = this.getMixin(typeElement);
        if (mixin == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found " + annotationHandle + " annotation on a non-mixin method", executableElement);
            return;
        }
        final InjectorRemap injectorRemap = new InjectorRemap(this.shouldRemap(mixin, annotationHandle));
        mixin.registerInjector(executableElement, annotationHandle, injectorRemap);
        injectorRemap.dispatchPendingMessages(this);
    }
    
    public void registerSoftImplements(final TypeElement typeElement, final AnnotationHandle annotationHandle) {
        final AnnotatedMixin mixin = this.getMixin(typeElement);
        if (mixin == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found @Implements annotation on a non-mixin class");
            return;
        }
        mixin.registerSoftImplements(annotationHandle);
    }
    
    public void onPassStarted() {
        this.mixinsForPass.clear();
    }
    
    public void onPassCompleted(final RoundEnvironment roundEnvironment) {
        if (!"true".equalsIgnoreCase(this.getOption("disableTargetExport"))) {
            this.targets.write(true);
        }
        final Iterator<AnnotatedMixin> iterator = (roundEnvironment.processingOver() ? this.mixins.values() : this.mixinsForPass).iterator();
        while (iterator.hasNext()) {
            iterator.next().runValidators(roundEnvironment.processingOver() ? IMixinValidator.ValidationPass.FINAL : IMixinValidator.ValidationPass.LATE, this.validators);
        }
    }
    
    private boolean shouldRemap(final AnnotatedMixin annotatedMixin, final AnnotationHandle annotationHandle) {
        return annotationHandle.getBoolean("remap", annotatedMixin.remap());
    }
    
    @Override
    public void printMessage(final Diagnostic.Kind kind, final CharSequence charSequence) {
        if (this.env == CompilerEnvironment.JAVAC || kind != Diagnostic.Kind.NOTE) {
            this.processingEnv.getMessager().printMessage(kind, charSequence);
        }
    }
    
    @Override
    public void printMessage(final Diagnostic.Kind kind, final CharSequence charSequence, final Element element) {
        this.processingEnv.getMessager().printMessage(kind, charSequence, element);
    }
    
    @Override
    public void printMessage(final Diagnostic.Kind kind, final CharSequence charSequence, final Element element, final AnnotationMirror annotationMirror) {
        this.processingEnv.getMessager().printMessage(kind, charSequence, element, annotationMirror);
    }
    
    @Override
    public void printMessage(final Diagnostic.Kind kind, final CharSequence charSequence, final Element element, final AnnotationMirror annotationMirror, final AnnotationValue annotationValue) {
        this.processingEnv.getMessager().printMessage(kind, charSequence, element, annotationMirror, annotationValue);
    }
    
    @Override
    public TypeHandle getTypeHandle(String replace) {
        replace = replace.replace('/', '.');
        final Elements elementUtils = this.processingEnv.getElementUtils();
        final TypeElement typeElement = elementUtils.getTypeElement(replace);
        if (typeElement != null) {
            try {
                return new TypeHandle(typeElement);
            }
            catch (NullPointerException ex) {}
        }
        final int lastIndex = replace.lastIndexOf(46);
        if (lastIndex > -1) {
            final PackageElement packageElement = elementUtils.getPackageElement(replace.substring(0, lastIndex));
            if (packageElement != null) {
                return new TypeHandle(packageElement, replace);
            }
        }
        return null;
    }
    
    @Override
    public TypeHandle getSimulatedHandle(String replace, final TypeMirror typeMirror) {
        replace = replace.replace('/', '.');
        final int lastIndex = replace.lastIndexOf(46);
        if (lastIndex > -1) {
            final PackageElement packageElement = this.processingEnv.getElementUtils().getPackageElement(replace.substring(0, lastIndex));
            if (packageElement != null) {
                return new TypeHandleSimulated(packageElement, replace, typeMirror);
            }
        }
        return new TypeHandleSimulated(replace, typeMirror);
    }
    
    @Override
    public String getJavadoc(final Element element) {
        return this.processingEnv.getElementUtils().getDocComment(element);
    }
    
    public static AnnotatedMixins getMixinsForEnvironment(final ProcessingEnvironment processingEnvironment) {
        AnnotatedMixins annotatedMixins = AnnotatedMixins.instances.get(processingEnvironment);
        if (annotatedMixins == null) {
            annotatedMixins = new AnnotatedMixins(processingEnvironment);
            AnnotatedMixins.instances.put(processingEnvironment, annotatedMixins);
        }
        return annotatedMixins;
    }
    
    static {
        AnnotatedMixins.instances = new HashMap<ProcessingEnvironment, AnnotatedMixins>();
    }
}
