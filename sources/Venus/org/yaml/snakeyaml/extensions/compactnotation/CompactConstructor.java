/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.extensions.compactnotation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.extensions.compactnotation.CompactData;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;

public class CompactConstructor
extends Constructor {
    private static final Pattern GUESS_COMPACT = Pattern.compile("\\p{Alpha}.*\\s*\\((?:,?\\s*(?:(?:\\w*)|(?:\\p{Alpha}\\w*\\s*=.+))\\s*)+\\)");
    private static final Pattern FIRST_PATTERN = Pattern.compile("(\\p{Alpha}.*)(\\s*)\\((.*?)\\)");
    private static final Pattern PROPERTY_NAME_PATTERN = Pattern.compile("\\s*(\\p{Alpha}\\w*)\\s*=(.+)");
    private Construct compactConstruct;

    public CompactConstructor(LoaderOptions loaderOptions) {
        super(loaderOptions);
    }

    public CompactConstructor() {
        super(new LoaderOptions());
    }

    protected Object constructCompactFormat(ScalarNode scalarNode, CompactData compactData) {
        try {
            Object object = this.createInstance(scalarNode, compactData);
            HashMap<String, Object> hashMap = new HashMap<String, Object>(compactData.getProperties());
            this.setProperties(object, hashMap);
            return object;
        } catch (Exception exception) {
            throw new YAMLException(exception);
        }
    }

    protected Object createInstance(ScalarNode scalarNode, CompactData compactData) throws Exception {
        Class<?> clazz = this.getClassForName(compactData.getPrefix());
        Class[] classArray = new Class[compactData.getArguments().size()];
        for (int i = 0; i < classArray.length; ++i) {
            classArray[i] = String.class;
        }
        java.lang.reflect.Constructor<?> constructor = clazz.getDeclaredConstructor(classArray);
        constructor.setAccessible(false);
        return constructor.newInstance(compactData.getArguments().toArray());
    }

    protected void setProperties(Object object, Map<String, Object> map) throws Exception {
        if (map == null) {
            throw new NullPointerException("Data for Compact Object Notation cannot be null.");
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String string = entry.getKey();
            Property property = this.getPropertyUtils().getProperty(object.getClass(), string);
            try {
                property.set(object, entry.getValue());
            } catch (IllegalArgumentException illegalArgumentException) {
                throw new YAMLException("Cannot set property='" + string + "' with value='" + map.get(string) + "' (" + map.get(string).getClass() + ") in " + object);
            }
        }
    }

    public CompactData getCompactData(String string) {
        if (!string.endsWith(")")) {
            return null;
        }
        if (string.indexOf(40) < 0) {
            return null;
        }
        Matcher matcher = FIRST_PATTERN.matcher(string);
        if (matcher.matches()) {
            String string2 = matcher.group(1).trim();
            String string3 = matcher.group(3);
            CompactData compactData = new CompactData(string2);
            if (string3.length() == 0) {
                return compactData;
            }
            String[] stringArray = string3.split("\\s*,\\s*");
            for (int i = 0; i < stringArray.length; ++i) {
                String string4 = stringArray[i];
                if (string4.indexOf(61) < 0) {
                    compactData.getArguments().add(string4);
                    continue;
                }
                Matcher matcher2 = PROPERTY_NAME_PATTERN.matcher(string4);
                if (matcher2.matches()) {
                    String string5 = matcher2.group(1);
                    String string6 = matcher2.group(2).trim();
                    compactData.getProperties().put(string5, string6);
                    continue;
                }
                return null;
            }
            return compactData;
        }
        return null;
    }

    private Construct getCompactConstruct() {
        if (this.compactConstruct == null) {
            this.compactConstruct = this.createCompactConstruct();
        }
        return this.compactConstruct;
    }

    protected Construct createCompactConstruct() {
        return new ConstructCompactObject(this);
    }

    @Override
    protected Construct getConstructor(Node node) {
        ScalarNode scalarNode;
        ScalarNode scalarNode2;
        NodeTuple nodeTuple;
        Node node2;
        MappingNode mappingNode;
        List<NodeTuple> list;
        if (node instanceof MappingNode ? (list = (mappingNode = (MappingNode)node).getValue()).size() == 1 && (node2 = (nodeTuple = list.get(0)).getKeyNode()) instanceof ScalarNode && GUESS_COMPACT.matcher((scalarNode2 = (ScalarNode)node2).getValue()).matches() : node instanceof ScalarNode && GUESS_COMPACT.matcher((scalarNode = (ScalarNode)node).getValue()).matches()) {
            return this.getCompactConstruct();
        }
        return super.getConstructor(node);
    }

    protected void applySequence(Object object, List<?> list) {
        try {
            Property property = this.getPropertyUtils().getProperty(object.getClass(), this.getSequencePropertyName(object.getClass()));
            property.set(object, list);
        } catch (Exception exception) {
            throw new YAMLException(exception);
        }
    }

    protected String getSequencePropertyName(Class<?> clazz) {
        Set<Property> set = this.getPropertyUtils().getProperties(clazz);
        Iterator<Property> iterator2 = set.iterator();
        while (iterator2.hasNext()) {
            Property property = iterator2.next();
            if (List.class.isAssignableFrom(property.getType())) continue;
            iterator2.remove();
        }
        if (set.size() == 0) {
            throw new YAMLException("No list property found in " + clazz);
        }
        if (set.size() > 1) {
            throw new YAMLException("Many list properties found in " + clazz + "; Please override getSequencePropertyName() to specify which property to use.");
        }
        return set.iterator().next().getName();
    }

    static List access$000(CompactConstructor compactConstructor, SequenceNode sequenceNode) {
        return compactConstructor.constructSequence(sequenceNode);
    }

    static String access$100(CompactConstructor compactConstructor, ScalarNode scalarNode) {
        return compactConstructor.constructScalar(scalarNode);
    }

    public class ConstructCompactObject
    extends Constructor.ConstructMapping {
        final CompactConstructor this$0;

        public ConstructCompactObject(CompactConstructor compactConstructor) {
            this.this$0 = compactConstructor;
            super(compactConstructor);
        }

        @Override
        public void construct2ndStep(Node node, Object object) {
            MappingNode mappingNode = (MappingNode)node;
            NodeTuple nodeTuple = mappingNode.getValue().iterator().next();
            Node node2 = nodeTuple.getValueNode();
            if (node2 instanceof MappingNode) {
                node2.setType(object.getClass());
                this.constructJavaBean2ndStep((MappingNode)node2, object);
            } else {
                this.this$0.applySequence(object, CompactConstructor.access$000(this.this$0, (SequenceNode)node2));
            }
        }

        @Override
        public Object construct(Node node) {
            ScalarNode scalarNode;
            Object object;
            if (node instanceof MappingNode) {
                object = (MappingNode)node;
                NodeTuple nodeTuple = ((MappingNode)object).getValue().iterator().next();
                node.setTwoStepsConstruction(false);
                scalarNode = (ScalarNode)nodeTuple.getKeyNode();
            } else {
                scalarNode = (ScalarNode)node;
            }
            object = this.this$0.getCompactData(scalarNode.getValue());
            if (object == null) {
                return CompactConstructor.access$100(this.this$0, scalarNode);
            }
            return this.this$0.constructCompactFormat(scalarNode, (CompactData)object);
        }
    }
}

