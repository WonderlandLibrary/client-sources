/*
 * Decompiled with CFR 0.152.
 */
package org.yaml.snakeyaml.constructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.ConstructorException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

public class Constructor
extends SafeConstructor {
    public Constructor() {
        this(Object.class);
    }

    public Constructor(LoaderOptions loadingConfig) {
        this(Object.class, loadingConfig);
    }

    public Constructor(Class<? extends Object> theRoot) {
        this(new TypeDescription(Constructor.checkRoot(theRoot)));
    }

    public Constructor(Class<? extends Object> theRoot, LoaderOptions loadingConfig) {
        this(new TypeDescription(Constructor.checkRoot(theRoot)), loadingConfig);
    }

    private static Class<? extends Object> checkRoot(Class<? extends Object> theRoot) {
        if (theRoot != null) return theRoot;
        throw new NullPointerException("Root class must be provided.");
    }

    public Constructor(TypeDescription theRoot) {
        this(theRoot, null, new LoaderOptions());
    }

    public Constructor(TypeDescription theRoot, LoaderOptions loadingConfig) {
        this(theRoot, null, loadingConfig);
    }

    public Constructor(TypeDescription theRoot, Collection<TypeDescription> moreTDs) {
        this(theRoot, moreTDs, new LoaderOptions());
    }

    public Constructor(TypeDescription theRoot, Collection<TypeDescription> moreTDs, LoaderOptions loadingConfig) {
        super(loadingConfig);
        if (theRoot == null) {
            throw new NullPointerException("Root type must be provided.");
        }
        this.yamlConstructors.put(null, new ConstructYamlObject());
        if (!Object.class.equals(theRoot.getType())) {
            this.rootTag = new Tag(theRoot.getType());
        }
        this.yamlClassConstructors.put(NodeId.scalar, new ConstructScalar());
        this.yamlClassConstructors.put(NodeId.mapping, new ConstructMapping());
        this.yamlClassConstructors.put(NodeId.sequence, new ConstructSequence());
        this.addTypeDescription(theRoot);
        if (moreTDs == null) return;
        Iterator<TypeDescription> i$ = moreTDs.iterator();
        while (i$.hasNext()) {
            TypeDescription td = i$.next();
            this.addTypeDescription(td);
        }
    }

    public Constructor(String theRoot) throws ClassNotFoundException {
        this(Class.forName(Constructor.check(theRoot)));
    }

    public Constructor(String theRoot, LoaderOptions loadingConfig) throws ClassNotFoundException {
        this(Class.forName(Constructor.check(theRoot)), loadingConfig);
    }

    private static final String check(String s) {
        if (s == null) {
            throw new NullPointerException("Root type must be provided.");
        }
        if (s.trim().length() != 0) return s;
        throw new YAMLException("Root type must be provided.");
    }

    protected Class<?> getClassForNode(Node node) {
        Class<?> cl;
        Class classForTag = (Class)this.typeTags.get(node.getTag());
        if (classForTag != null) return classForTag;
        String name = node.getTag().getClassName();
        try {
            cl = this.getClassForName(name);
        }
        catch (ClassNotFoundException e) {
            throw new YAMLException("Class not found: " + name);
        }
        this.typeTags.put(node.getTag(), cl);
        return cl;
    }

    protected Class<?> getClassForName(String name) throws ClassNotFoundException {
        try {
            return Class.forName(name, true, Thread.currentThread().getContextClassLoader());
        }
        catch (ClassNotFoundException e) {
            return Class.forName(name);
        }
    }

    protected class ConstructSequence
    implements Construct {
        protected ConstructSequence() {
        }

        @Override
        public Object construct(Node node) {
            java.lang.reflect.Constructor c;
            boolean foundConstructor;
            int index;
            Object argumentList;
            SequenceNode snode = (SequenceNode)node;
            if (Set.class.isAssignableFrom(node.getType())) {
                if (!node.isTwoStepsConstruction()) return Constructor.this.constructSet(snode);
                throw new YAMLException("Set cannot be recursive.");
            }
            if (Collection.class.isAssignableFrom(node.getType())) {
                if (!node.isTwoStepsConstruction()) return Constructor.this.constructSequence(snode);
                return Constructor.this.newList(snode);
            }
            if (node.getType().isArray()) {
                if (!node.isTwoStepsConstruction()) return Constructor.this.constructArray(snode);
                return Constructor.this.createArray(node.getType(), snode.getValue().size());
            }
            ArrayList possibleConstructors = new ArrayList(snode.getValue().size());
            for (java.lang.reflect.Constructor<?> constructor : node.getType().getDeclaredConstructors()) {
                if (snode.getValue().size() != constructor.getParameterTypes().length) continue;
                possibleConstructors.add(constructor);
            }
            if (possibleConstructors.isEmpty()) throw new YAMLException("No suitable constructor with " + String.valueOf(snode.getValue().size()) + " arguments found for " + node.getType());
            if (possibleConstructors.size() == 1) {
                argumentList = new Object[snode.getValue().size()];
                java.lang.reflect.Constructor c2 = (java.lang.reflect.Constructor)possibleConstructors.get(0);
                index = 0;
                for (Node argumentNode : snode.getValue()) {
                    Class<?> type = c2.getParameterTypes()[index];
                    argumentNode.setType(type);
                    argumentList[index++] = Constructor.this.constructObject(argumentNode);
                }
                try {
                    c2.setAccessible(true);
                    return c2.newInstance((Object[])argumentList);
                }
                catch (Exception e) {
                    throw new YAMLException(e);
                }
            }
            argumentList = Constructor.this.constructSequence(snode);
            Class[] parameterTypes = new Class[argumentList.size()];
            index = 0;
            Iterator<Object> i$ = argumentList.iterator();
            while (i$.hasNext()) {
                Object parameter = i$.next();
                parameterTypes[index] = parameter.getClass();
                ++index;
            }
            i$ = possibleConstructors.iterator();
            block7: do {
                if (!i$.hasNext()) throw new YAMLException("No suitable constructor with " + String.valueOf(snode.getValue().size()) + " arguments found for " + node.getType());
                c = (java.lang.reflect.Constructor)i$.next();
                Class<?>[] argTypes = c.getParameterTypes();
                foundConstructor = true;
                for (int i = 0; i < argTypes.length; ++i) {
                    if (this.wrapIfPrimitive(argTypes[i]).isAssignableFrom(parameterTypes[i])) continue;
                    foundConstructor = false;
                    continue block7;
                }
            } while (!foundConstructor);
            try {
                c.setAccessible(true);
                return c.newInstance(argumentList.toArray());
            }
            catch (Exception e) {
                throw new YAMLException(e);
            }
        }

        private final Class<? extends Object> wrapIfPrimitive(Class<?> clazz) {
            if (!clazz.isPrimitive()) {
                return clazz;
            }
            if (clazz == Integer.TYPE) {
                return Integer.class;
            }
            if (clazz == Float.TYPE) {
                return Float.class;
            }
            if (clazz == Double.TYPE) {
                return Double.class;
            }
            if (clazz == Boolean.TYPE) {
                return Boolean.class;
            }
            if (clazz == Long.TYPE) {
                return Long.class;
            }
            if (clazz == Character.TYPE) {
                return Character.class;
            }
            if (clazz == Short.TYPE) {
                return Short.class;
            }
            if (clazz != Byte.TYPE) throw new YAMLException("Unexpected primitive " + clazz);
            return Byte.class;
        }

        @Override
        public void construct2ndStep(Node node, Object object) {
            SequenceNode snode = (SequenceNode)node;
            if (List.class.isAssignableFrom(node.getType())) {
                List list = (List)object;
                Constructor.this.constructSequenceStep2(snode, list);
                return;
            }
            if (!node.getType().isArray()) throw new YAMLException("Immutable objects cannot be recursive.");
            Constructor.this.constructArrayStep2(snode, object);
        }
    }

    protected class ConstructScalar
    extends AbstractConstruct {
        protected ConstructScalar() {
        }

        @Override
        public Object construct(Node nnode) {
            ScalarNode node = (ScalarNode)nnode;
            Class<? extends Object> type = node.getType();
            try {
                return Constructor.this.newInstance(type, node, false);
            }
            catch (InstantiationException e1) {
                Object argument;
                if (type.isPrimitive()) return this.constructStandardJavaInstance(type, node);
                if (type == String.class) return this.constructStandardJavaInstance(type, node);
                if (Number.class.isAssignableFrom(type)) return this.constructStandardJavaInstance(type, node);
                if (type == Boolean.class) return this.constructStandardJavaInstance(type, node);
                if (Date.class.isAssignableFrom(type)) return this.constructStandardJavaInstance(type, node);
                if (type == Character.class) return this.constructStandardJavaInstance(type, node);
                if (type == BigInteger.class) return this.constructStandardJavaInstance(type, node);
                if (type == BigDecimal.class) return this.constructStandardJavaInstance(type, node);
                if (Enum.class.isAssignableFrom(type)) return this.constructStandardJavaInstance(type, node);
                if (Tag.BINARY.equals(node.getTag())) return this.constructStandardJavaInstance(type, node);
                if (Calendar.class.isAssignableFrom(type)) return this.constructStandardJavaInstance(type, node);
                if (type == UUID.class) {
                    return this.constructStandardJavaInstance(type, node);
                }
                java.lang.reflect.Constructor<?>[] javaConstructors = type.getDeclaredConstructors();
                int oneArgCount = 0;
                java.lang.reflect.Constructor<Object> javaConstructor = null;
                for (java.lang.reflect.Constructor<?> c : javaConstructors) {
                    if (c.getParameterTypes().length != 1) continue;
                    ++oneArgCount;
                    javaConstructor = c;
                }
                if (javaConstructor == null) {
                    try {
                        return Constructor.this.newInstance(type, node, false);
                    }
                    catch (InstantiationException ie) {
                        throw new YAMLException("No single argument constructor found for " + type + " : " + ie.getMessage());
                    }
                }
                if (oneArgCount == 1) {
                    argument = this.constructStandardJavaInstance(javaConstructor.getParameterTypes()[0], node);
                } else {
                    argument = Constructor.this.constructScalar(node);
                    try {
                        javaConstructor = type.getDeclaredConstructor(String.class);
                    }
                    catch (Exception e) {
                        throw new YAMLException("Can't construct a java object for scalar " + node.getTag() + "; No String constructor found. Exception=" + e.getMessage(), e);
                    }
                }
                try {
                    javaConstructor.setAccessible(true);
                    return javaConstructor.newInstance(argument);
                }
                catch (Exception e) {
                    throw new ConstructorException(null, null, "Can't construct a java object for scalar " + node.getTag() + "; exception=" + e.getMessage(), node.getStartMark(), e);
                }
            }
        }

        private Object constructStandardJavaInstance(Class type, ScalarNode node) {
            Object result;
            if (type == String.class) {
                Construct stringConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.STR);
                return stringConstructor.construct(node);
            }
            if (type == Boolean.class || type == Boolean.TYPE) {
                Construct boolConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.BOOL);
                return boolConstructor.construct(node);
            }
            if (type == Character.class || type == Character.TYPE) {
                Construct charConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.STR);
                String ch = (String)charConstructor.construct(node);
                if (ch.length() == 0) {
                    return null;
                }
                if (ch.length() == 1) return Character.valueOf(ch.charAt(0));
                throw new YAMLException("Invalid node Character: '" + ch + "'; length: " + ch.length());
            }
            if (Date.class.isAssignableFrom(type)) {
                Construct dateConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.TIMESTAMP);
                Date date = (Date)dateConstructor.construct(node);
                if (type == Date.class) {
                    return date;
                }
                try {
                    java.lang.reflect.Constructor constr = type.getConstructor(Long.TYPE);
                    return constr.newInstance(date.getTime());
                }
                catch (RuntimeException e) {
                    throw e;
                }
                catch (Exception e) {
                    throw new YAMLException("Cannot construct: '" + type + "'");
                }
            }
            if (type == Float.class || type == Double.class || type == Float.TYPE || type == Double.TYPE || type == BigDecimal.class) {
                if (type == BigDecimal.class) {
                    return new BigDecimal(node.getValue());
                }
                Construct doubleConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.FLOAT);
                result = doubleConstructor.construct(node);
                if (type == Float.class) return Float.valueOf(((Double)result).floatValue());
                if (type != Float.TYPE) return result;
                return Float.valueOf(((Double)result).floatValue());
            }
            if (type == Byte.class || type == Short.class || type == Integer.class || type == Long.class || type == BigInteger.class || type == Byte.TYPE || type == Short.TYPE || type == Integer.TYPE || type == Long.TYPE) {
                Construct intConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.INT);
                result = intConstructor.construct(node);
                if (type == Byte.class) return Integer.valueOf(result.toString()).byteValue();
                if (type == Byte.TYPE) {
                    return Integer.valueOf(result.toString()).byteValue();
                }
                if (type == Short.class) return Integer.valueOf(result.toString()).shortValue();
                if (type == Short.TYPE) {
                    return Integer.valueOf(result.toString()).shortValue();
                }
                if (type == Integer.class) return Integer.parseInt(result.toString());
                if (type == Integer.TYPE) {
                    return Integer.parseInt(result.toString());
                }
                if (type == Long.class) return Long.valueOf(result.toString());
                if (type == Long.TYPE) return Long.valueOf(result.toString());
                return new BigInteger(result.toString());
            }
            if (Enum.class.isAssignableFrom(type)) {
                String enumValueName = node.getValue();
                try {
                    return Enum.valueOf(type, enumValueName);
                }
                catch (Exception ex) {
                    throw new YAMLException("Unable to find enum value '" + enumValueName + "' for enum class: " + type.getName());
                }
            }
            if (Calendar.class.isAssignableFrom(type)) {
                SafeConstructor.ConstructYamlTimestamp contr = new SafeConstructor.ConstructYamlTimestamp();
                contr.construct(node);
                return contr.getCalendar();
            }
            if (Number.class.isAssignableFrom(type)) {
                SafeConstructor.ConstructYamlFloat contr = new SafeConstructor.ConstructYamlFloat(Constructor.this);
                return contr.construct(node);
            }
            if (UUID.class == type) {
                return UUID.fromString(node.getValue());
            }
            if (!Constructor.this.yamlConstructors.containsKey(node.getTag())) throw new YAMLException("Unsupported class: " + type);
            return ((Construct)Constructor.this.yamlConstructors.get(node.getTag())).construct(node);
        }
    }

    protected class ConstructYamlObject
    implements Construct {
        protected ConstructYamlObject() {
        }

        private Construct getConstructor(Node node) {
            Class<?> cl = Constructor.this.getClassForNode(node);
            node.setType(cl);
            return (Construct)Constructor.this.yamlClassConstructors.get((Object)node.getNodeId());
        }

        @Override
        public Object construct(Node node) {
            try {
                return this.getConstructor(node).construct(node);
            }
            catch (ConstructorException e) {
                throw e;
            }
            catch (Exception e) {
                throw new ConstructorException(null, null, "Can't construct a java object for " + node.getTag() + "; exception=" + e.getMessage(), node.getStartMark(), e);
            }
        }

        @Override
        public void construct2ndStep(Node node, Object object) {
            try {
                this.getConstructor(node).construct2ndStep(node, object);
                return;
            }
            catch (Exception e) {
                throw new ConstructorException(null, null, "Can't construct a second step for a java object for " + node.getTag() + "; exception=" + e.getMessage(), node.getStartMark(), e);
            }
        }
    }

    protected class ConstructMapping
    implements Construct {
        protected ConstructMapping() {
        }

        @Override
        public Object construct(Node node) {
            MappingNode mnode = (MappingNode)node;
            if (Map.class.isAssignableFrom(node.getType())) {
                if (!node.isTwoStepsConstruction()) return Constructor.this.constructMapping(mnode);
                return Constructor.this.newMap(mnode);
            }
            if (Collection.class.isAssignableFrom(node.getType())) {
                if (!node.isTwoStepsConstruction()) return Constructor.this.constructSet(mnode);
                return Constructor.this.newSet(mnode);
            }
            Object obj = Constructor.this.newInstance(mnode);
            if (!node.isTwoStepsConstruction()) return this.constructJavaBean2ndStep(mnode, obj);
            return obj;
        }

        @Override
        public void construct2ndStep(Node node, Object object) {
            if (Map.class.isAssignableFrom(node.getType())) {
                Constructor.this.constructMapping2ndStep((MappingNode)node, (Map)object);
                return;
            }
            if (Set.class.isAssignableFrom(node.getType())) {
                Constructor.this.constructSet2ndStep((MappingNode)node, (Set)object);
                return;
            }
            this.constructJavaBean2ndStep((MappingNode)node, object);
        }

        protected Object constructJavaBean2ndStep(MappingNode node, Object object) {
            Constructor.this.flattenMapping(node);
            Class<? extends Object> beanType = node.getType();
            List<NodeTuple> nodeValue = node.getValue();
            Iterator<NodeTuple> i$ = nodeValue.iterator();
            while (i$.hasNext()) {
                NodeTuple tuple = i$.next();
                if (!(tuple.getKeyNode() instanceof ScalarNode)) throw new YAMLException("Keys must be scalars but found: " + tuple.getKeyNode());
                ScalarNode keyNode = (ScalarNode)tuple.getKeyNode();
                Node valueNode = tuple.getValueNode();
                keyNode.setType(String.class);
                String key = (String)Constructor.this.constructObject(keyNode);
                try {
                    Object value;
                    Class<?>[] arguments;
                    boolean typeDetected;
                    Property property;
                    TypeDescription memberDescription = (TypeDescription)Constructor.this.typeDefinitions.get(beanType);
                    Property property2 = property = memberDescription == null ? this.getProperty(beanType, key) : memberDescription.getProperty(key);
                    if (!property.isWritable()) {
                        throw new YAMLException("No writable property '" + key + "' on class: " + beanType.getName());
                    }
                    valueNode.setType(property.getType());
                    boolean bl = typeDetected = memberDescription != null ? memberDescription.setupPropertyType(key, valueNode) : false;
                    if (!typeDetected && valueNode.getNodeId() != NodeId.scalar && (arguments = property.getActualTypeArguments()) != null && arguments.length > 0) {
                        Class<?> t;
                        if (valueNode.getNodeId() == NodeId.sequence) {
                            t = arguments[0];
                            SequenceNode snode = (SequenceNode)valueNode;
                            snode.setListType(t);
                        } else if (Set.class.isAssignableFrom(valueNode.getType())) {
                            t = arguments[0];
                            MappingNode mnode = (MappingNode)valueNode;
                            mnode.setOnlyKeyType(t);
                            mnode.setUseClassConstructor(true);
                        } else if (Map.class.isAssignableFrom(valueNode.getType())) {
                            Class<?> keyType = arguments[0];
                            Class<?> valueType = arguments[1];
                            MappingNode mnode = (MappingNode)valueNode;
                            mnode.setTypes(keyType, valueType);
                            mnode.setUseClassConstructor(true);
                        }
                    }
                    Object object2 = value = memberDescription != null ? this.newInstance(memberDescription, key, valueNode) : Constructor.this.constructObject(valueNode);
                    if ((property.getType() == Float.TYPE || property.getType() == Float.class) && value instanceof Double) {
                        value = Float.valueOf(((Double)value).floatValue());
                    }
                    if (property.getType() == String.class && Tag.BINARY.equals(valueNode.getTag()) && value instanceof byte[]) {
                        value = new String((byte[])value);
                    }
                    if (memberDescription != null && memberDescription.setProperty(object, key, value)) continue;
                    property.set(object, value);
                }
                catch (DuplicateKeyException e) {
                    throw e;
                }
                catch (Exception e) {
                    throw new ConstructorException("Cannot create property=" + key + " for JavaBean=" + object, node.getStartMark(), e.getMessage(), valueNode.getStartMark(), e);
                }
            }
            return object;
        }

        private Object newInstance(TypeDescription memberDescription, String propertyName, Node node) {
            Object newInstance = memberDescription.newInstance(propertyName, node);
            if (newInstance == null) return Constructor.this.constructObject(node);
            Constructor.this.constructedObjects.put(node, newInstance);
            return Constructor.this.constructObjectNoCheck(node);
        }

        protected Property getProperty(Class<? extends Object> type, String name) {
            return Constructor.this.getPropertyUtils().getProperty(type, name);
        }
    }
}

