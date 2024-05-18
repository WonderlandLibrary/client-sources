/*
 * Decompiled with CFR 0.143.
 */
package org.reflections;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.reflections.Configuration;
import org.reflections.ReflectionUtils;
import org.reflections.ReflectionsException;
import org.reflections.Store;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MemberUsageScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterNamesScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.Scanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.serializers.Serializer;
import org.reflections.serializers.XmlSerializer;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.reflections.util.Utils;
import org.reflections.vfs.Vfs;
import org.slf4j.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Reflections {
    @Nullable
    public static Logger log = Utils.findLogger(Reflections.class);
    protected final transient Configuration configuration;
    protected Store store;

    public Reflections(Configuration configuration) {
        this.configuration = configuration;
        this.store = new Store(configuration);
        if (configuration.getScanners() != null && !configuration.getScanners().isEmpty()) {
            for (Scanner scanner : configuration.getScanners()) {
                scanner.setConfiguration(configuration);
                scanner.setStore(this.store.getOrCreate(scanner.getClass().getSimpleName()));
            }
            this.scan();
        }
    }

    public Reflections(String prefix, @Nullable Scanner ... scanners) {
        this(new Object[]{prefix, scanners});
    }

    public Reflections(Object ... params) {
        this(ConfigurationBuilder.build(params));
    }

    protected Reflections() {
        this.configuration = new ConfigurationBuilder();
        this.store = new Store(this.configuration);
    }

    protected void scan() {
        if (this.configuration.getUrls() == null || this.configuration.getUrls().isEmpty()) {
            if (log != null) {
                log.warn("given scan urls are empty. set urls in the configuration");
            }
            return;
        }
        if (log != null && log.isDebugEnabled()) {
            log.debug("going to scan these urls:\n" + Joiner.on((String)"\n").join(this.configuration.getUrls()));
        }
        long time = System.currentTimeMillis();
        int scannedUrls = 0;
        ExecutorService executorService = this.configuration.getExecutorService();
        ArrayList futures = Lists.newArrayList();
        for (final URL url : this.configuration.getUrls()) {
            try {
                if (executorService != null) {
                    futures.add(executorService.submit(new Runnable(){

                        public void run() {
                            if (log != null && log.isDebugEnabled()) {
                                log.debug("[" + Thread.currentThread().toString() + "] scanning " + url);
                            }
                            Reflections.this.scan(url);
                        }
                    }));
                } else {
                    this.scan(url);
                }
                ++scannedUrls;
            }
            catch (ReflectionsException e) {
                if (log == null || !log.isWarnEnabled()) continue;
                log.warn("could not create Vfs.Dir from url. ignoring the exception and continuing", (Throwable)e);
            }
        }
        if (executorService != null) {
            for (Future future : futures) {
                try {
                    future.get();
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        time = System.currentTimeMillis() - time;
        if (log != null) {
            int keys = 0;
            int values = 0;
            for (String index : this.store.keySet()) {
                keys += this.store.get(index).keySet().size();
                values += this.store.get(index).size();
            }
            Object[] arrobject = new Object[5];
            arrobject[0] = time;
            arrobject[1] = scannedUrls;
            arrobject[2] = keys;
            arrobject[3] = values;
            arrobject[4] = executorService != null && executorService instanceof ThreadPoolExecutor ? String.format("[using %d cores]", ((ThreadPoolExecutor)executorService).getMaximumPoolSize()) : "";
            log.info(String.format("Reflections took %d ms to scan %d urls, producing %d keys and %d values %s", arrobject));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void scan(URL url) {
        Vfs.Dir dir = Vfs.fromURL(url);
        try {
            for (Vfs.File file : dir.getFiles()) {
                Predicate<String> inputsFilter = this.configuration.getInputsFilter();
                String path = file.getRelativePath();
                String fqn = path.replace('/', '.');
                if (inputsFilter != null && !inputsFilter.apply((Object)path) && !inputsFilter.apply((Object)fqn)) continue;
                Object classObject = null;
                for (Scanner scanner : this.configuration.getScanners()) {
                    try {
                        if (!scanner.acceptsInput(path) && !scanner.acceptResult(fqn)) continue;
                        classObject = scanner.scan(file, classObject);
                    }
                    catch (Exception e) {
                        if (log == null || !log.isDebugEnabled()) continue;
                        log.debug("could not scan file " + file.getRelativePath() + " in url " + url.toExternalForm() + " with scanner " + scanner.getClass().getSimpleName(), (Object)e.getMessage());
                    }
                }
            }
        }
        finally {
            dir.close();
        }
    }

    public static Reflections collect() {
        return Reflections.collect("META-INF/reflections/", new FilterBuilder().include(".*-reflections.xml"), new Serializer[0]);
    }

    public static Reflections collect(String packagePrefix, Predicate<String> resourceNameFilter, @Nullable Serializer ... optionalSerializer) {
        XmlSerializer serializer = optionalSerializer != null && optionalSerializer.length == 1 ? optionalSerializer[0] : new XmlSerializer();
        Collection<URL> urls = ClasspathHelper.forPackage(packagePrefix, new ClassLoader[0]);
        if (urls.isEmpty()) {
            return null;
        }
        long start = System.currentTimeMillis();
        Reflections reflections = new Reflections();
        Iterable<Vfs.File> files = Vfs.findFiles(urls, packagePrefix, resourceNameFilter);
        for (Vfs.File file : files) {
            InputStream inputStream = null;
            try {
                inputStream = file.openInputStream();
                reflections.merge(serializer.read(inputStream));
            }
            catch (IOException e) {
                throw new ReflectionsException("could not merge " + file, e);
            }
            finally {
                Utils.close(inputStream);
            }
        }
        if (log != null) {
            Store store = reflections.getStore();
            int keys = 0;
            int values = 0;
            for (String index : store.keySet()) {
                keys += store.get(index).keySet().size();
                values += store.get(index).size();
            }
            log.info(String.format("Reflections took %d ms to collect %d url%s, producing %d keys and %d values [%s]", System.currentTimeMillis() - start, urls.size(), urls.size() > 1 ? "s" : "", keys, values, Joiner.on((String)", ").join(urls)));
        }
        return reflections;
    }

    public Reflections collect(InputStream inputStream) {
        try {
            this.merge(this.configuration.getSerializer().read(inputStream));
            if (log != null) {
                log.info("Reflections collected metadata from input stream using serializer " + this.configuration.getSerializer().getClass().getName());
            }
        }
        catch (Exception ex) {
            throw new ReflectionsException("could not merge input stream", ex);
        }
        return this;
    }

    public Reflections collect(File file) {
        Reflections reflections;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            reflections = this.collect(inputStream);
        }
        catch (FileNotFoundException e) {
            try {
                throw new ReflectionsException("could not obtain input stream from file " + file, e);
            }
            catch (Throwable throwable) {
                Utils.close(inputStream);
                throw throwable;
            }
        }
        Utils.close(inputStream);
        return reflections;
    }

    public Reflections merge(Reflections reflections) {
        if (reflections.store != null) {
            for (String indexName : reflections.store.keySet()) {
                Multimap<String, String> index = reflections.store.get(indexName);
                for (String key : index.keySet()) {
                    for (String string : index.get((Object)key)) {
                        this.store.getOrCreate(indexName).put((Object)key, (Object)string);
                    }
                }
            }
        }
        return this;
    }

    public <T> Set<Class<? extends T>> getSubTypesOf(Class<T> type) {
        return Sets.newHashSet(ReflectionUtils.forNames(this.store.getAll(Reflections.index(SubTypesScanner.class), Arrays.asList(type.getName())), this.loaders()));
    }

    public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
        return this.getTypesAnnotatedWith(annotation, false);
    }

    public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation, boolean honorInherited) {
        Iterable<String> annotated = this.store.get(Reflections.index(TypeAnnotationsScanner.class), annotation.getName());
        Iterable<String> classes = this.getAllAnnotated(annotated, annotation.isAnnotationPresent(Inherited.class), honorInherited);
        return Sets.newHashSet((Iterable)Iterables.concat(ReflectionUtils.forNames(annotated, this.loaders()), ReflectionUtils.forNames(classes, this.loaders())));
    }

    public Set<Class<?>> getTypesAnnotatedWith(Annotation annotation) {
        return this.getTypesAnnotatedWith(annotation, false);
    }

    public Set<Class<?>> getTypesAnnotatedWith(Annotation annotation, boolean honorInherited) {
        Iterable<String> annotated = this.store.get(Reflections.index(TypeAnnotationsScanner.class), annotation.annotationType().getName());
        Set<Class<?>> filter = ReflectionUtils.filter(ReflectionUtils.forNames(annotated, this.loaders()), ReflectionUtils.withAnnotation(annotation));
        Iterable<String> classes = this.getAllAnnotated(Utils.names(filter), annotation.annotationType().isAnnotationPresent(Inherited.class), honorInherited);
        return Sets.newHashSet((Iterable)Iterables.concat(filter, ReflectionUtils.forNames(ReflectionUtils.filter(classes, Predicates.not((Predicate)Predicates.in((Collection)Sets.newHashSet(annotated)))), this.loaders())));
    }

    protected Iterable<String> getAllAnnotated(Iterable<String> annotated, boolean inherited, boolean honorInherited) {
        if (honorInherited) {
            if (inherited) {
                Iterable<String> subTypes = this.store.get(Reflections.index(SubTypesScanner.class), ReflectionUtils.filter(annotated, new Predicate<String>(){

                    public boolean apply(@Nullable String input) {
                        return !ReflectionUtils.forName(input, Reflections.this.loaders()).isInterface();
                    }
                }));
                return Iterables.concat(subTypes, this.store.getAll(Reflections.index(SubTypesScanner.class), subTypes));
            }
            return annotated;
        }
        Iterable subTypes = Iterables.concat(annotated, this.store.getAll(Reflections.index(TypeAnnotationsScanner.class), annotated));
        return Iterables.concat((Iterable)subTypes, this.store.getAll(Reflections.index(SubTypesScanner.class), subTypes));
    }

    public Set<Method> getMethodsAnnotatedWith(Class<? extends Annotation> annotation) {
        Iterable<String> methods = this.store.get(Reflections.index(MethodAnnotationsScanner.class), annotation.getName());
        return Utils.getMethodsFromDescriptors(methods, this.loaders());
    }

    public Set<Method> getMethodsAnnotatedWith(Annotation annotation) {
        return ReflectionUtils.filter(this.getMethodsAnnotatedWith(annotation.annotationType()), ReflectionUtils.withAnnotation(annotation));
    }

    public Set<Method> getMethodsMatchParams(Class<?> ... types) {
        return Utils.getMethodsFromDescriptors(this.store.get(Reflections.index(MethodParameterScanner.class), Utils.names(types).toString()), this.loaders());
    }

    public Set<Method> getMethodsReturn(Class returnType) {
        return Utils.getMethodsFromDescriptors(this.store.get(Reflections.index(MethodParameterScanner.class), Utils.names(returnType)), this.loaders());
    }

    public Set<Method> getMethodsWithAnyParamAnnotated(Class<? extends Annotation> annotation) {
        return Utils.getMethodsFromDescriptors(this.store.get(Reflections.index(MethodParameterScanner.class), annotation.getName()), this.loaders());
    }

    public Set<Method> getMethodsWithAnyParamAnnotated(Annotation annotation) {
        return ReflectionUtils.filter(this.getMethodsWithAnyParamAnnotated(annotation.annotationType()), ReflectionUtils.withAnyParameterAnnotation(annotation));
    }

    public Set<Constructor> getConstructorsAnnotatedWith(Class<? extends Annotation> annotation) {
        Iterable<String> methods = this.store.get(Reflections.index(MethodAnnotationsScanner.class), annotation.getName());
        return Utils.getConstructorsFromDescriptors(methods, this.loaders());
    }

    public Set<Constructor> getConstructorsAnnotatedWith(Annotation annotation) {
        return ReflectionUtils.filter(this.getConstructorsAnnotatedWith(annotation.annotationType()), ReflectionUtils.withAnnotation(annotation));
    }

    public Set<Constructor> getConstructorsMatchParams(Class<?> ... types) {
        return Utils.getConstructorsFromDescriptors(this.store.get(Reflections.index(MethodParameterScanner.class), Utils.names(types).toString()), this.loaders());
    }

    public Set<Constructor> getConstructorsWithAnyParamAnnotated(Class<? extends Annotation> annotation) {
        return Utils.getConstructorsFromDescriptors(this.store.get(Reflections.index(MethodParameterScanner.class), annotation.getName()), this.loaders());
    }

    public Set<Constructor> getConstructorsWithAnyParamAnnotated(Annotation annotation) {
        return ReflectionUtils.filter(this.getConstructorsWithAnyParamAnnotated(annotation.annotationType()), ReflectionUtils.withAnyParameterAnnotation(annotation));
    }

    public Set<Field> getFieldsAnnotatedWith(Class<? extends Annotation> annotation) {
        HashSet result = Sets.newHashSet();
        for (String annotated : this.store.get(Reflections.index(FieldAnnotationsScanner.class), annotation.getName())) {
            result.add(Utils.getFieldFromString(annotated, this.loaders()));
        }
        return result;
    }

    public Set<Field> getFieldsAnnotatedWith(Annotation annotation) {
        return ReflectionUtils.filter(this.getFieldsAnnotatedWith(annotation.annotationType()), ReflectionUtils.withAnnotation(annotation));
    }

    public Set<String> getResources(Predicate<String> namePredicate) {
        Iterable resources = Iterables.filter((Iterable)this.store.get(Reflections.index(ResourcesScanner.class)).keySet(), namePredicate);
        return Sets.newHashSet(this.store.get(Reflections.index(ResourcesScanner.class), resources));
    }

    public Set<String> getResources(final Pattern pattern) {
        return this.getResources(new Predicate<String>(){

            public boolean apply(String input) {
                return pattern.matcher(input).matches();
            }
        });
    }

    public List<String> getMethodParamNames(Method method) {
        Iterable<String> names = this.store.get(Reflections.index(MethodParameterNamesScanner.class), Utils.name(method));
        return !Iterables.isEmpty(names) ? Arrays.asList(((String)Iterables.getOnlyElement(names)).split(", ")) : Arrays.asList(new String[0]);
    }

    public List<String> getConstructorParamNames(Constructor constructor) {
        Iterable<String> names = this.store.get(Reflections.index(MethodParameterNamesScanner.class), Utils.name(constructor));
        return !Iterables.isEmpty(names) ? Arrays.asList(((String)Iterables.getOnlyElement(names)).split(", ")) : Arrays.asList(new String[0]);
    }

    public Set<Member> getFieldUsage(Field field) {
        return Utils.getMembersFromDescriptors(this.store.get(Reflections.index(MemberUsageScanner.class), Utils.name(field)), new ClassLoader[0]);
    }

    public Set<Member> getMethodUsage(Method method) {
        return Utils.getMembersFromDescriptors(this.store.get(Reflections.index(MemberUsageScanner.class), Utils.name(method)), new ClassLoader[0]);
    }

    public Set<Member> getConstructorUsage(Constructor constructor) {
        return Utils.getMembersFromDescriptors(this.store.get(Reflections.index(MemberUsageScanner.class), Utils.name(constructor)), new ClassLoader[0]);
    }

    public Set<String> getAllTypes() {
        HashSet allTypes = Sets.newHashSet(this.store.getAll(Reflections.index(SubTypesScanner.class), Object.class.getName()));
        if (allTypes.isEmpty()) {
            throw new ReflectionsException("Couldn't find subtypes of Object. Make sure SubTypesScanner initialized to include Object class - new SubTypesScanner(false)");
        }
        return allTypes;
    }

    public Store getStore() {
        return this.store;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public File save(String filename) {
        return this.save(filename, this.configuration.getSerializer());
    }

    public File save(String filename, Serializer serializer) {
        File file = serializer.save(this, filename);
        if (log != null) {
            log.info("Reflections successfully saved in " + file.getAbsolutePath() + " using " + serializer.getClass().getSimpleName());
        }
        return file;
    }

    private static String index(Class<? extends Scanner> scannerClass) {
        return scannerClass.getSimpleName();
    }

    private ClassLoader[] loaders() {
        return this.configuration.getClassLoaders();
    }

}

