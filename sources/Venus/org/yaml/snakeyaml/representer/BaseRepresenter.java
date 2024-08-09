/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.representer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.nodes.AnchorNode;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;

public abstract class BaseRepresenter {
    protected final Map<Class<?>, Represent> representers = new HashMap();
    protected Represent nullRepresenter;
    protected final Map<Class<?>, Represent> multiRepresenters = new LinkedHashMap();
    protected DumperOptions.ScalarStyle defaultScalarStyle = DumperOptions.ScalarStyle.PLAIN;
    protected DumperOptions.FlowStyle defaultFlowStyle = DumperOptions.FlowStyle.AUTO;
    protected final Map<Object, Node> representedObjects = new IdentityHashMap<Object, Node>(this){
        private static final long serialVersionUID = -5576159264232131854L;
        final BaseRepresenter this$0;
        {
            this.this$0 = baseRepresenter;
        }

        @Override
        public Node put(Object object, Node node) {
            return super.put(object, new AnchorNode(node));
        }

        @Override
        public Object put(Object object, Object object2) {
            return this.put(object, (Node)object2);
        }
    };
    protected Object objectToRepresent;
    private PropertyUtils propertyUtils;
    private boolean explicitPropertyUtils = false;

    public Node represent(Object object) {
        Node node = this.representData(object);
        this.representedObjects.clear();
        this.objectToRepresent = null;
        return node;
    }

    protected final Node representData(Object object) {
        Node node;
        this.objectToRepresent = object;
        if (this.representedObjects.containsKey(this.objectToRepresent)) {
            Node node2 = this.representedObjects.get(this.objectToRepresent);
            return node2;
        }
        if (object == null) {
            Node node3 = this.nullRepresenter.representData(null);
            return node3;
        }
        Class<?> clazz = object.getClass();
        if (this.representers.containsKey(clazz)) {
            Represent represent = this.representers.get(clazz);
            node = represent.representData(object);
        } else {
            Object object2;
            for (Class<?> clazz2 : this.multiRepresenters.keySet()) {
                if (clazz2 == null || !clazz2.isInstance(object)) continue;
                Represent represent = this.multiRepresenters.get(clazz2);
                Node node4 = represent.representData(object);
                return node4;
            }
            if (this.multiRepresenters.containsKey(null)) {
                object2 = this.multiRepresenters.get(null);
                node = object2.representData(object);
            } else {
                object2 = this.representers.get(null);
                node = object2.representData(object);
            }
        }
        return node;
    }

    protected Node representScalar(Tag tag, String string, DumperOptions.ScalarStyle scalarStyle) {
        if (scalarStyle == null) {
            scalarStyle = this.defaultScalarStyle;
        }
        ScalarNode scalarNode = new ScalarNode(tag, string, null, null, scalarStyle);
        return scalarNode;
    }

    protected Node representScalar(Tag tag, String string) {
        return this.representScalar(tag, string, this.defaultScalarStyle);
    }

    protected Node representSequence(Tag tag, Iterable<?> iterable, DumperOptions.FlowStyle flowStyle) {
        int n = 10;
        if (iterable instanceof List) {
            n = ((List)iterable).size();
        }
        ArrayList<Node> arrayList = new ArrayList<Node>(n);
        SequenceNode sequenceNode = new SequenceNode(tag, arrayList, flowStyle);
        this.representedObjects.put(this.objectToRepresent, sequenceNode);
        DumperOptions.FlowStyle flowStyle2 = DumperOptions.FlowStyle.FLOW;
        for (Object obj : iterable) {
            Node node = this.representData(obj);
            if (!(node instanceof ScalarNode) || !((ScalarNode)node).isPlain()) {
                flowStyle2 = DumperOptions.FlowStyle.BLOCK;
            }
            arrayList.add(node);
        }
        if (flowStyle == DumperOptions.FlowStyle.AUTO) {
            if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
                sequenceNode.setFlowStyle(this.defaultFlowStyle);
            } else {
                sequenceNode.setFlowStyle(flowStyle2);
            }
        }
        return sequenceNode;
    }

    protected Node representMapping(Tag tag, Map<?, ?> map, DumperOptions.FlowStyle flowStyle) {
        ArrayList<NodeTuple> arrayList = new ArrayList<NodeTuple>(map.size());
        MappingNode mappingNode = new MappingNode(tag, arrayList, flowStyle);
        this.representedObjects.put(this.objectToRepresent, mappingNode);
        DumperOptions.FlowStyle flowStyle2 = DumperOptions.FlowStyle.FLOW;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Node node = this.representData(entry.getKey());
            Node node2 = this.representData(entry.getValue());
            if (!(node instanceof ScalarNode) || !((ScalarNode)node).isPlain()) {
                flowStyle2 = DumperOptions.FlowStyle.BLOCK;
            }
            if (!(node2 instanceof ScalarNode) || !((ScalarNode)node2).isPlain()) {
                flowStyle2 = DumperOptions.FlowStyle.BLOCK;
            }
            arrayList.add(new NodeTuple(node, node2));
        }
        if (flowStyle == DumperOptions.FlowStyle.AUTO) {
            if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
                mappingNode.setFlowStyle(this.defaultFlowStyle);
            } else {
                mappingNode.setFlowStyle(flowStyle2);
            }
        }
        return mappingNode;
    }

    public void setDefaultScalarStyle(DumperOptions.ScalarStyle scalarStyle) {
        this.defaultScalarStyle = scalarStyle;
    }

    public DumperOptions.ScalarStyle getDefaultScalarStyle() {
        if (this.defaultScalarStyle == null) {
            return DumperOptions.ScalarStyle.PLAIN;
        }
        return this.defaultScalarStyle;
    }

    public void setDefaultFlowStyle(DumperOptions.FlowStyle flowStyle) {
        this.defaultFlowStyle = flowStyle;
    }

    public DumperOptions.FlowStyle getDefaultFlowStyle() {
        return this.defaultFlowStyle;
    }

    public void setPropertyUtils(PropertyUtils propertyUtils) {
        this.propertyUtils = propertyUtils;
        this.explicitPropertyUtils = true;
    }

    public final PropertyUtils getPropertyUtils() {
        if (this.propertyUtils == null) {
            this.propertyUtils = new PropertyUtils();
        }
        return this.propertyUtils;
    }

    public final boolean isExplicitPropertyUtils() {
        return this.explicitPropertyUtils;
    }
}

