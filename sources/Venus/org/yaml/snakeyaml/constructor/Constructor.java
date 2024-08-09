/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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
import org.yaml.snakeyaml.constructor.BaseConstructor;
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
import org.yaml.snakeyaml.util.EnumUtils;

public class Constructor
extends SafeConstructor {
    public Constructor(LoaderOptions loaderOptions) {
        this(Object.class, loaderOptions);
    }

    public Constructor(Class<? extends Object> clazz, LoaderOptions loaderOptions) {
        this(new TypeDescription(Constructor.checkRoot(clazz)), null, loaderOptions);
    }

    private static Class<? extends Object> checkRoot(Class<? extends Object> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Root class must be provided.");
        }
        return clazz;
    }

    public Constructor(TypeDescription typeDescription, LoaderOptions loaderOptions) {
        this(typeDescription, null, loaderOptions);
    }

    public Constructor(TypeDescription typeDescription, Collection<TypeDescription> collection, LoaderOptions loaderOptions) {
        super(loaderOptions);
        if (typeDescription == null) {
            throw new NullPointerException("Root type must be provided.");
        }
        this.yamlConstructors.put(null, new ConstructYamlObject(this));
        if (!Object.class.equals(typeDescription.getType())) {
            this.rootTag = new Tag(typeDescription.getType());
        }
        this.yamlClassConstructors.put(NodeId.scalar, new ConstructScalar(this));
        this.yamlClassConstructors.put(NodeId.mapping, new ConstructMapping(this));
        this.yamlClassConstructors.put(NodeId.sequence, new ConstructSequence(this));
        this.addTypeDescription(typeDescription);
        if (collection != null) {
            for (TypeDescription typeDescription2 : collection) {
                this.addTypeDescription(typeDescription2);
            }
        }
    }

    public Constructor(String string, LoaderOptions loaderOptions) throws ClassNotFoundException {
        this(Class.forName(Constructor.check(string)), loaderOptions);
    }

    private static String check(String string) {
        if (string == null) {
            throw new NullPointerException("Root type must be provided.");
        }
        if (string.trim().length() == 0) {
            throw new YAMLException("Root type must be provided.");
        }
        return string;
    }

    protected Class<?> getClassForNode(Node node) {
        Class clazz = (Class)this.typeTags.get(node.getTag());
        if (clazz == null) {
            Class<?> clazz2;
            String string = node.getTag().getClassName();
            try {
                clazz2 = this.getClassForName(string);
            } catch (ClassNotFoundException classNotFoundException) {
                throw new YAMLException("Class not found: " + string);
            }
            this.typeTags.put(node.getTag(), clazz2);
            return clazz2;
        }
        return clazz;
    }

    protected Class<?> getClassForName(String string) throws ClassNotFoundException {
        try {
            return Class.forName(string, true, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException classNotFoundException) {
            return Class.forName(string);
        }
    }

    protected class ConstructSequence
    implements Construct {
        final Constructor this$0;

        protected ConstructSequence(Constructor constructor) {
            this.this$0 = constructor;
        }

        @Override
        public Object construct(Node node) {
            SequenceNode sequenceNode = (SequenceNode)node;
            if (Set.class.isAssignableFrom(node.getType())) {
                if (node.isTwoStepsConstruction()) {
                    throw new YAMLException("Set cannot be recursive.");
                }
                return this.this$0.constructSet(sequenceNode);
            }
            if (Collection.class.isAssignableFrom(node.getType())) {
                if (node.isTwoStepsConstruction()) {
                    return this.this$0.newList(sequenceNode);
                }
                return this.this$0.constructSequence(sequenceNode);
            }
            if (node.getType().isArray()) {
                if (node.isTwoStepsConstruction()) {
                    return this.this$0.createArray(node.getType(), sequenceNode.getValue().size());
                }
                return this.this$0.constructArray(sequenceNode);
            }
            ArrayList arrayList = new ArrayList(sequenceNode.getValue().size());
            for (java.lang.reflect.Constructor<?> iterator2 : node.getType().getDeclaredConstructors()) {
                if (sequenceNode.getValue().size() != iterator2.getParameterTypes().length) continue;
                arrayList.add(iterator2);
            }
            if (!arrayList.isEmpty()) {
                int n;
                Object object;
                if (arrayList.size() == 1) {
                    object = new Object[sequenceNode.getValue().size()];
                    java.lang.reflect.Constructor constructor = (java.lang.reflect.Constructor)arrayList.get(0);
                    n = 0;
                    for (Node node2 : sequenceNode.getValue()) {
                        Class<?> clazz = constructor.getParameterTypes()[n];
                        node2.setType(clazz);
                        object[n++] = this.this$0.constructObject(node2);
                    }
                    try {
                        constructor.setAccessible(false);
                        return constructor.newInstance((Object[])object);
                    } catch (Exception exception) {
                        throw new YAMLException(exception);
                    }
                }
                object = this.this$0.constructSequence(sequenceNode);
                Class[] classArray = new Class[object.size()];
                n = 0;
                Iterator iterator2 = object.iterator();
                while (iterator2.hasNext()) {
                    Object e = iterator2.next();
                    classArray[n] = e.getClass();
                    ++n;
                }
                for (java.lang.reflect.Constructor constructor : arrayList) {
                    Class<?>[] classArray2 = constructor.getParameterTypes();
                    boolean bl = true;
                    for (int i = 0; i < classArray2.length; ++i) {
                        if (this.wrapIfPrimitive(classArray2[i]).isAssignableFrom(classArray[i])) continue;
                        bl = false;
                        break;
                    }
                    if (!bl) continue;
                    try {
                        constructor.setAccessible(false);
                        return constructor.newInstance(object.toArray());
                    } catch (Exception exception) {
                        throw new YAMLException(exception);
                    }
                }
            }
            throw new YAMLException("No suitable constructor with " + sequenceNode.getValue().size() + " arguments found for " + node.getType());
        }

        private Class<? extends Object> wrapIfPrimitive(Class<?> clazz) {
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
            if (clazz == Byte.TYPE) {
                return Byte.class;
            }
            throw new YAMLException("Unexpected primitive " + clazz);
        }

        @Override
        public void construct2ndStep(Node node, Object object) {
            SequenceNode sequenceNode = (SequenceNode)node;
            if (List.class.isAssignableFrom(node.getType())) {
                List list = (List)object;
                this.this$0.constructSequenceStep2(sequenceNode, list);
            } else if (node.getType().isArray()) {
                this.this$0.constructArrayStep2(sequenceNode, object);
            } else {
                throw new YAMLException("Immutable objects cannot be recursive.");
            }
        }
    }

    protected class ConstructScalar
    extends AbstractConstruct {
        final Constructor this$0;

        protected ConstructScalar(Constructor constructor) {
            this.this$0 = constructor;
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public Object construct(Node node) {
            Object object;
            ScalarNode scalarNode = (ScalarNode)node;
            Class<? extends Object> clazz = scalarNode.getType();
            Object object2 = this.this$0.newInstance(clazz, scalarNode, true);
            if (object2 != BaseConstructor.NOT_INSTANTIATED_OBJECT) {
                return object2;
            }
            if (clazz.isPrimitive() || clazz == String.class || Number.class.isAssignableFrom(clazz) || clazz == Boolean.class || Date.class.isAssignableFrom(clazz) || clazz == Character.class || clazz == BigInteger.class || clazz == BigDecimal.class || Enum.class.isAssignableFrom(clazz) || Tag.BINARY.equals(scalarNode.getTag()) || Calendar.class.isAssignableFrom(clazz) || clazz == UUID.class) {
                object = this.constructStandardJavaInstance(clazz, scalarNode);
            } else {
                java.lang.reflect.Constructor<?>[] constructorArray = clazz.getDeclaredConstructors();
                int n = 0;
                java.lang.reflect.Constructor<Object> constructor = null;
                for (java.lang.reflect.Constructor<?> constructor2 : constructorArray) {
                    if (constructor2.getParameterTypes().length != 1) continue;
                    ++n;
                    constructor = constructor2;
                }
                if (constructor == null) {
                    throw new YAMLException("No single argument constructor found for " + clazz);
                }
                if (n == 1) {
                    Object object3 = this.constructStandardJavaInstance(constructor.getParameterTypes()[0], scalarNode);
                } else {
                    String string = this.this$0.constructScalar(scalarNode);
                    try {
                        constructor = clazz.getDeclaredConstructor(String.class);
                    } catch (Exception exception) {
                        throw new YAMLException("Can't construct a java object for scalar " + scalarNode.getTag() + "; No String constructor found. Exception=" + exception.getMessage(), exception);
                    }
                }
                try {
                    void var9_12;
                    constructor.setAccessible(false);
                    object = constructor.newInstance(var9_12);
                } catch (Exception exception) {
                    throw new ConstructorException(null, null, "Can't construct a java object for scalar " + scalarNode.getTag() + "; exception=" + exception.getMessage(), scalarNode.getStartMark(), exception);
                }
            }
            return object;
        }

        private Object constructStandardJavaInstance(Class clazz, ScalarNode scalarNode) {
            Object object;
            if (clazz == String.class) {
                Construct construct = (Construct)this.this$0.yamlConstructors.get(Tag.STR);
                object = construct.construct(scalarNode);
            } else if (clazz == Boolean.class || clazz == Boolean.TYPE) {
                Construct construct = (Construct)this.this$0.yamlConstructors.get(Tag.BOOL);
                object = construct.construct(scalarNode);
            } else if (clazz == Character.class || clazz == Character.TYPE) {
                Construct construct = (Construct)this.this$0.yamlConstructors.get(Tag.STR);
                String string = (String)construct.construct(scalarNode);
                if (string.length() == 0) {
                    object = null;
                } else {
                    if (string.length() != 1) {
                        throw new YAMLException("Invalid node Character: '" + string + "'; length: " + string.length());
                    }
                    object = Character.valueOf(string.charAt(0));
                }
            } else if (Date.class.isAssignableFrom(clazz)) {
                Construct construct = (Construct)this.this$0.yamlConstructors.get(Tag.TIMESTAMP);
                Date date = (Date)construct.construct(scalarNode);
                if (clazz == Date.class) {
                    object = date;
                } else {
                    try {
                        java.lang.reflect.Constructor constructor = clazz.getConstructor(Long.TYPE);
                        object = constructor.newInstance(date.getTime());
                    } catch (RuntimeException runtimeException) {
                        throw runtimeException;
                    } catch (Exception exception) {
                        throw new YAMLException("Cannot construct: '" + clazz + "'");
                    }
                }
            } else if (clazz == Float.class || clazz == Double.class || clazz == Float.TYPE || clazz == Double.TYPE || clazz == BigDecimal.class) {
                if (clazz == BigDecimal.class) {
                    object = new BigDecimal(scalarNode.getValue());
                } else {
                    Construct construct = (Construct)this.this$0.yamlConstructors.get(Tag.FLOAT);
                    object = construct.construct(scalarNode);
                    if (clazz == Float.class || clazz == Float.TYPE) {
                        object = Float.valueOf(((Double)object).floatValue());
                    }
                }
            } else if (clazz == Byte.class || clazz == Short.class || clazz == Integer.class || clazz == Long.class || clazz == BigInteger.class || clazz == Byte.TYPE || clazz == Short.TYPE || clazz == Integer.TYPE || clazz == Long.TYPE) {
                Construct construct = (Construct)this.this$0.yamlConstructors.get(Tag.INT);
                object = construct.construct(scalarNode);
                object = clazz == Byte.class || clazz == Byte.TYPE ? (Number)Integer.valueOf(object.toString()).byteValue() : (Number)(clazz == Short.class || clazz == Short.TYPE ? (Number)Integer.valueOf(object.toString()).shortValue() : (Number)(clazz == Integer.class || clazz == Integer.TYPE ? (Number)Integer.parseInt(object.toString()) : (Number)(clazz == Long.class || clazz == Long.TYPE ? Long.valueOf(object.toString()) : new BigInteger(object.toString()))));
            } else if (Enum.class.isAssignableFrom(clazz)) {
                String string = scalarNode.getValue();
                try {
                    if (this.this$0.loadingConfig.isEnumCaseSensitive()) {
                        object = Enum.valueOf(clazz, string);
                    }
                    object = EnumUtils.findEnumInsensitiveCase(clazz, string);
                } catch (Exception exception) {
                    throw new YAMLException("Unable to find enum value '" + string + "' for enum class: " + clazz.getName());
                }
            } else if (Calendar.class.isAssignableFrom(clazz)) {
                SafeConstructor.ConstructYamlTimestamp constructYamlTimestamp = new SafeConstructor.ConstructYamlTimestamp();
                constructYamlTimestamp.construct(scalarNode);
                object = constructYamlTimestamp.getCalendar();
            } else if (Number.class.isAssignableFrom(clazz)) {
                SafeConstructor.ConstructYamlFloat constructYamlFloat = new SafeConstructor.ConstructYamlFloat(this.this$0);
                object = constructYamlFloat.construct(scalarNode);
            } else if (UUID.class == clazz) {
                object = UUID.fromString(scalarNode.getValue());
            } else if (this.this$0.yamlConstructors.containsKey(scalarNode.getTag())) {
                object = ((Construct)this.this$0.yamlConstructors.get(scalarNode.getTag())).construct(scalarNode);
            } else {
                throw new YAMLException("Unsupported class: " + clazz);
            }
            return object;
        }
    }

    protected class ConstructYamlObject
    implements Construct {
        final Constructor this$0;

        protected ConstructYamlObject(Constructor constructor) {
            this.this$0 = constructor;
        }

        private Construct getConstructor(Node node) {
            Class<?> clazz = this.this$0.getClassForNode(node);
            node.setType(clazz);
            Construct construct = (Construct)this.this$0.yamlClassConstructors.get((Object)node.getNodeId());
            return construct;
        }

        @Override
        public Object construct(Node node) {
            try {
                return this.getConstructor(node).construct(node);
            } catch (ConstructorException constructorException) {
                throw constructorException;
            } catch (Exception exception) {
                throw new ConstructorException(null, null, "Can't construct a java object for " + node.getTag() + "; exception=" + exception.getMessage(), node.getStartMark(), exception);
            }
        }

        @Override
        public void construct2ndStep(Node node, Object object) {
            try {
                this.getConstructor(node).construct2ndStep(node, object);
            } catch (Exception exception) {
                throw new ConstructorException(null, null, "Can't construct a second step for a java object for " + node.getTag() + "; exception=" + exception.getMessage(), node.getStartMark(), exception);
            }
        }
    }

    protected class ConstructMapping
    implements Construct {
        final Constructor this$0;

        protected ConstructMapping(Constructor constructor) {
            this.this$0 = constructor;
        }

        @Override
        public Object construct(Node node) {
            MappingNode mappingNode = (MappingNode)node;
            if (Map.class.isAssignableFrom(node.getType())) {
                if (node.isTwoStepsConstruction()) {
                    return this.this$0.newMap(mappingNode);
                }
                return this.this$0.constructMapping(mappingNode);
            }
            if (Collection.class.isAssignableFrom(node.getType())) {
                if (node.isTwoStepsConstruction()) {
                    return this.this$0.newSet(mappingNode);
                }
                return this.this$0.constructSet(mappingNode);
            }
            Object object = this.this$0.newInstance(mappingNode);
            if (object != BaseConstructor.NOT_INSTANTIATED_OBJECT) {
                if (node.isTwoStepsConstruction()) {
                    return object;
                }
                return this.constructJavaBean2ndStep(mappingNode, object);
            }
            throw new ConstructorException(null, null, "Can't create an instance for " + mappingNode.getTag(), node.getStartMark());
        }

        @Override
        public void construct2ndStep(Node node, Object object) {
            if (Map.class.isAssignableFrom(node.getType())) {
                this.this$0.constructMapping2ndStep((MappingNode)node, (Map)object);
            } else if (Set.class.isAssignableFrom(node.getType())) {
                this.this$0.constructSet2ndStep((MappingNode)node, (Set)object);
            } else {
                this.constructJavaBean2ndStep((MappingNode)node, object);
            }
        }

        protected Object constructJavaBean2ndStep(MappingNode mappingNode, Object object) {
            this.this$0.flattenMapping(mappingNode, false);
            Class<? extends Object> clazz = mappingNode.getType();
            List<NodeTuple> list = mappingNode.getValue();
            for (NodeTuple nodeTuple : list) {
                Node node = nodeTuple.getValueNode();
                String string = (String)this.this$0.constructObject(nodeTuple.getKeyNode());
                try {
                    Object object2;
                    boolean bl;
                    Property property;
                    TypeDescription typeDescription = (TypeDescription)this.this$0.typeDefinitions.get(clazz);
                    Property property2 = property = typeDescription == null ? this.getProperty(clazz, string) : typeDescription.getProperty(string);
                    if (!property.isWritable()) {
                        throw new YAMLException("No writable property '" + string + "' on class: " + clazz.getName());
                    }
                    node.setType(property.getType());
                    boolean bl2 = bl = typeDescription != null && typeDescription.setupPropertyType(string, node);
                    if (!bl && node.getNodeId() != NodeId.scalar && (object2 = property.getActualTypeArguments()) != null && ((Class<?>[])object2).length > 0) {
                        Object object3;
                        Class<?> clazz2;
                        if (node.getNodeId() == NodeId.sequence) {
                            clazz2 = object2[0];
                            object3 = (SequenceNode)node;
                            ((SequenceNode)object3).setListType(clazz2);
                        } else if (Map.class.isAssignableFrom(node.getType())) {
                            clazz2 = object2[0];
                            object3 = object2[5];
                            MappingNode mappingNode2 = (MappingNode)node;
                            mappingNode2.setTypes(clazz2, (Class<? extends Object>)object3);
                            mappingNode2.setUseClassConstructor(true);
                        } else if (Collection.class.isAssignableFrom(node.getType())) {
                            clazz2 = object2[0];
                            object3 = (MappingNode)node;
                            ((MappingNode)object3).setOnlyKeyType(clazz2);
                            ((Node)object3).setUseClassConstructor(true);
                        }
                    }
                    Object object4 = object2 = typeDescription != null ? this.newInstance(typeDescription, string, node) : this.this$0.constructObject(node);
                    if ((property.getType() == Float.TYPE || property.getType() == Float.class) && object2 instanceof Double) {
                        object2 = Float.valueOf(((Double)object2).floatValue());
                    }
                    if (property.getType() == String.class && Tag.BINARY.equals(node.getTag()) && object2 instanceof byte[]) {
                        object2 = new String((byte[])object2);
                    }
                    if (typeDescription != null && typeDescription.setProperty(object, string, object2)) continue;
                    property.set(object, object2);
                } catch (DuplicateKeyException duplicateKeyException) {
                    throw duplicateKeyException;
                } catch (Exception exception) {
                    throw new ConstructorException("Cannot create property=" + string + " for JavaBean=" + object, mappingNode.getStartMark(), exception.getMessage(), node.getStartMark(), exception);
                }
            }
            return object;
        }

        private Object newInstance(TypeDescription typeDescription, String string, Node node) {
            Object object = typeDescription.newInstance(string, node);
            if (object != null) {
                this.this$0.constructedObjects.put(node, object);
                return this.this$0.constructObjectNoCheck(node);
            }
            return this.this$0.constructObject(node);
        }

        protected Property getProperty(Class<? extends Object> clazz, String string) {
            return this.this$0.getPropertyUtils().getProperty(clazz, string);
        }
    }
}

