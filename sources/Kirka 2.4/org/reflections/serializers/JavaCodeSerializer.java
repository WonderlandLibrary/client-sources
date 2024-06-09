/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.serializers;

import com.google.common.base.Joiner;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import org.reflections.Store;
import org.reflections.scanners.TypeElementsScanner;
import org.reflections.serializers.Serializer;
import org.reflections.util.Utils;
import org.slf4j.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class JavaCodeSerializer
implements Serializer {
    private static final String pathSeparator = "_";
    private static final String doubleSeparator = "__";
    private static final String dotSeparator = ".";
    private static final String arrayDescriptor = "$$";
    private static final String tokenSeparator = "_";

    @Override
    public Reflections read(InputStream inputStream) {
        throw new UnsupportedOperationException("read is not implemented on JavaCodeSerializer");
    }

    @Override
    public File save(Reflections reflections, String name) {
        String className;
        String packageName;
        if (name.endsWith("/")) {
            name = name.substring(0, name.length() - 1);
        }
        String filename = name.replace('.', '/').concat(".java");
        File file = Utils.prepareFile(filename);
        int lastDot = name.lastIndexOf(46);
        if (lastDot == -1) {
            packageName = "";
            className = name.substring(name.lastIndexOf(47) + 1);
        } else {
            packageName = name.substring(name.lastIndexOf(47) + 1, lastDot);
            className = name.substring(lastDot + 1);
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("//generated using Reflections JavaCodeSerializer").append(" [").append(new Date()).append("]").append("\n");
            if (packageName.length() != 0) {
                sb.append("package ").append(packageName).append(";\n");
                sb.append("\n");
            }
            sb.append("public interface ").append(className).append(" {\n\n");
            sb.append(this.toString(reflections));
            sb.append("}\n");
            Files.write((CharSequence)sb.toString(), (File)new File(filename), (Charset)Charset.defaultCharset());
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
        return file;
    }

    @Override
    public String toString(Reflections reflections) {
        if (reflections.getStore().get(TypeElementsScanner.class.getSimpleName()).isEmpty() && Reflections.log != null) {
            Reflections.log.warn("JavaCodeSerializer needs TypeElementsScanner configured");
        }
        StringBuilder sb = new StringBuilder();
        ArrayList prevPaths = Lists.newArrayList();
        int indent = 1;
        ArrayList keys = Lists.newArrayList((Iterable)reflections.getStore().get(TypeElementsScanner.class.getSimpleName()).keySet());
        Collections.sort(keys);
        for (String fqn : keys) {
            int i;
            int j;
            ArrayList typePaths = Lists.newArrayList((Object[])fqn.split("\\."));
            for (i = 0; i < Math.min(typePaths.size(), prevPaths.size()) && ((String)typePaths.get(i)).equals(prevPaths.get(i)); ++i) {
            }
            for (j = prevPaths.size(); j > i; --j) {
                sb.append(Utils.repeat("\t", --indent)).append("}\n");
            }
            for (j = i; j < typePaths.size() - 1; ++j) {
                sb.append(Utils.repeat("\t", indent++)).append("public interface ").append(this.getNonDuplicateName((String)typePaths.get(j), typePaths, j)).append(" {\n");
            }
            String className = (String)typePaths.get(typePaths.size() - 1);
            ArrayList annotations = Lists.newArrayList();
            ArrayList fields = Lists.newArrayList();
            SetMultimap methods = Multimaps.newSetMultimap(new HashMap(), (Supplier)new Supplier<Set<String>>(){

                public Set<String> get() {
                    return Sets.newHashSet();
                }
            });
            for (String element : reflections.getStore().get(TypeElementsScanner.class.getSimpleName(), fqn)) {
                if (element.startsWith("@")) {
                    annotations.add(element.substring(1));
                    continue;
                }
                if (element.contains("(")) {
                    if (element.startsWith("<")) continue;
                    int i1 = element.indexOf(40);
                    String name = element.substring(0, i1);
                    String params = element.substring(i1 + 1, element.indexOf(")"));
                    String paramsDescriptor = "";
                    if (params.length() != 0) {
                        paramsDescriptor = "_" + params.replace(dotSeparator, "_").replace(", ", doubleSeparator).replace("[]", arrayDescriptor);
                    }
                    String normalized = name + paramsDescriptor;
                    methods.put((Object)name, (Object)normalized);
                    continue;
                }
                if (Utils.isEmpty(element)) continue;
                fields.add(element);
            }
            sb.append(Utils.repeat("\t", indent++)).append("public interface ").append(this.getNonDuplicateName(className, typePaths, typePaths.size() - 1)).append(" {\n");
            if (!fields.isEmpty()) {
                sb.append(Utils.repeat("\t", indent++)).append("public interface fields {\n");
                for (String field : fields) {
                    sb.append(Utils.repeat("\t", indent)).append("public interface ").append(this.getNonDuplicateName(field, typePaths)).append(" {}\n");
                }
                sb.append(Utils.repeat("\t", --indent)).append("}\n");
            }
            if (!methods.isEmpty()) {
                sb.append(Utils.repeat("\t", indent++)).append("public interface methods {\n");
                for (Map.Entry entry : methods.entries()) {
                    String simpleName = (String)entry.getKey();
                    String normalized = (String)entry.getValue();
                    String methodName = methods.get((Object)simpleName).size() == 1 ? simpleName : normalized;
                    methodName = this.getNonDuplicateName(methodName, fields);
                    sb.append(Utils.repeat("\t", indent)).append("public interface ").append(this.getNonDuplicateName(methodName, typePaths)).append(" {}\n");
                }
                sb.append(Utils.repeat("\t", --indent)).append("}\n");
            }
            if (!annotations.isEmpty()) {
                sb.append(Utils.repeat("\t", indent++)).append("public interface annotations {\n");
                Iterator<String> iterator = annotations.iterator();
                while (iterator.hasNext()) {
                    String annotation;
                    String nonDuplicateName = annotation = iterator.next();
                    nonDuplicateName = this.getNonDuplicateName(nonDuplicateName, typePaths);
                    sb.append(Utils.repeat("\t", indent)).append("public interface ").append(nonDuplicateName).append(" {}\n");
                }
                sb.append(Utils.repeat("\t", --indent)).append("}\n");
            }
            prevPaths = typePaths;
        }
        for (int j = prevPaths.size(); j >= 1; --j) {
            sb.append(Utils.repeat("\t", j)).append("}\n");
        }
        return sb.toString();
    }

    private String getNonDuplicateName(String candidate, List<String> prev, int offset) {
        String normalized = this.normalize(candidate);
        for (int i = 0; i < offset; ++i) {
            if (!normalized.equals(prev.get(i))) continue;
            return this.getNonDuplicateName(normalized + "_", prev, offset);
        }
        return normalized;
    }

    private String normalize(String candidate) {
        return candidate.replace(dotSeparator, "_");
    }

    private String getNonDuplicateName(String candidate, List<String> prev) {
        return this.getNonDuplicateName(candidate, prev, prev.size());
    }

    public static Class<?> resolveClassOf(Class element) throws ClassNotFoundException {
        LinkedList ognl = Lists.newLinkedList();
        for (Class<?> cursor = element; cursor != null; cursor = cursor.getDeclaringClass()) {
            ognl.addFirst(cursor.getSimpleName());
        }
        String classOgnl = Joiner.on((String)dotSeparator).join(ognl.subList(1, ognl.size())).replace(".$", "$");
        return Class.forName(classOgnl);
    }

    public static Class<?> resolveClass(Class aClass) {
        try {
            return JavaCodeSerializer.resolveClassOf(aClass);
        }
        catch (Exception e) {
            throw new ReflectionsException("could not resolve to class " + aClass.getName(), e);
        }
    }

    public static Field resolveField(Class aField) {
        try {
            String name = aField.getSimpleName();
            Class<?> declaringClass = aField.getDeclaringClass().getDeclaringClass();
            return JavaCodeSerializer.resolveClassOf(declaringClass).getDeclaredField(name);
        }
        catch (Exception e) {
            throw new ReflectionsException("could not resolve to field " + aField.getName(), e);
        }
    }

    public static Annotation resolveAnnotation(Class annotation) {
        try {
            String name = annotation.getSimpleName().replace("_", dotSeparator);
            Class<?> declaringClass = annotation.getDeclaringClass().getDeclaringClass();
            Class<?> aClass = JavaCodeSerializer.resolveClassOf(declaringClass);
            Class<?> aClass1 = ReflectionUtils.forName(name, new ClassLoader[0]);
            Object annotation1 = aClass.getAnnotation(aClass1);
            return annotation1;
        }
        catch (Exception e) {
            throw new ReflectionsException("could not resolve to annotation " + annotation.getName(), e);
        }
    }

    public static Method resolveMethod(Class aMethod) {
        String methodOgnl = aMethod.getSimpleName();
        try {
            Class[] paramTypes;
            String methodName;
            if (methodOgnl.contains("_")) {
                methodName = methodOgnl.substring(0, methodOgnl.indexOf("_"));
                String[] params = methodOgnl.substring(methodOgnl.indexOf("_") + 1).split(doubleSeparator);
                paramTypes = new Class[params.length];
                for (int i = 0; i < params.length; ++i) {
                    String typeName = params[i].replace(arrayDescriptor, "[]").replace("_", dotSeparator);
                    paramTypes[i] = ReflectionUtils.forName(typeName, new ClassLoader[0]);
                }
            } else {
                methodName = methodOgnl;
                paramTypes = null;
            }
            Class<?> declaringClass = aMethod.getDeclaringClass().getDeclaringClass();
            return JavaCodeSerializer.resolveClassOf(declaringClass).getDeclaredMethod(methodName, paramTypes);
        }
        catch (Exception e) {
            throw new ReflectionsException("could not resolve to method " + aMethod.getName(), e);
        }
    }

}

