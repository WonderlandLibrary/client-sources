/*
 * Decompiled with CFR 0.152.
 */
package org.yaml.snakeyaml.representer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.SafeRepresenter;

public class Representer
extends SafeRepresenter {
    protected Map<Class<? extends Object>, TypeDescription> typeDefinitions = Collections.emptyMap();

    public Representer() {
        this.representers.put(null, new RepresentJavaBean());
    }

    public Representer(DumperOptions options) {
        super(options);
        this.representers.put(null, new RepresentJavaBean());
    }

    public TypeDescription addTypeDescription(TypeDescription td) {
        if (Collections.EMPTY_MAP == this.typeDefinitions) {
            this.typeDefinitions = new HashMap<Class<? extends Object>, TypeDescription>();
        }
        if (td.getTag() != null) {
            this.addClassTag((Class)td.getType(), td.getTag());
        }
        td.setPropertyUtils(this.getPropertyUtils());
        return this.typeDefinitions.put(td.getType(), td);
    }

    @Override
    public void setPropertyUtils(PropertyUtils propertyUtils) {
        super.setPropertyUtils(propertyUtils);
        Collection<TypeDescription> tds = this.typeDefinitions.values();
        Iterator<TypeDescription> i$ = tds.iterator();
        while (i$.hasNext()) {
            TypeDescription typeDescription = i$.next();
            typeDescription.setPropertyUtils(propertyUtils);
        }
    }

    protected MappingNode representJavaBean(Set<Property> properties, Object javaBean) {
        ArrayList<NodeTuple> value = new ArrayList<NodeTuple>(properties.size());
        Tag customTag = (Tag)this.classTags.get(javaBean.getClass());
        Tag tag = customTag != null ? customTag : new Tag(javaBean.getClass());
        MappingNode node = new MappingNode(tag, value, DumperOptions.FlowStyle.AUTO);
        this.representedObjects.put(javaBean, node);
        DumperOptions.FlowStyle bestStyle = DumperOptions.FlowStyle.FLOW;
        Iterator<Property> i$ = properties.iterator();
        while (i$.hasNext()) {
            Node nodeValue;
            Property property;
            Object memberValue;
            Tag customPropertyTag = (memberValue = (property = i$.next()).get(javaBean)) == null ? null : (Tag)this.classTags.get(memberValue.getClass());
            NodeTuple tuple = this.representJavaBeanProperty(javaBean, property, memberValue, customPropertyTag);
            if (tuple == null) continue;
            if (!((ScalarNode)tuple.getKeyNode()).isPlain()) {
                bestStyle = DumperOptions.FlowStyle.BLOCK;
            }
            if (!((nodeValue = tuple.getValueNode()) instanceof ScalarNode) || !((ScalarNode)nodeValue).isPlain()) {
                bestStyle = DumperOptions.FlowStyle.BLOCK;
            }
            value.add(tuple);
        }
        if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
            node.setFlowStyle(this.defaultFlowStyle);
            return node;
        }
        node.setFlowStyle(bestStyle);
        return node;
    }

    protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag) {
        ScalarNode nodeKey = (ScalarNode)this.representData(property.getName());
        boolean hasAlias = this.representedObjects.containsKey(propertyValue);
        Node nodeValue = this.representData(propertyValue);
        if (propertyValue == null) return new NodeTuple(nodeKey, nodeValue);
        if (hasAlias) return new NodeTuple(nodeKey, nodeValue);
        NodeId nodeId = nodeValue.getNodeId();
        if (customTag != null) return new NodeTuple(nodeKey, nodeValue);
        if (nodeId == NodeId.scalar) {
            if (property.getType() == Enum.class) return new NodeTuple(nodeKey, nodeValue);
            if (!(propertyValue instanceof Enum)) return new NodeTuple(nodeKey, nodeValue);
            nodeValue.setTag(Tag.STR);
            return new NodeTuple(nodeKey, nodeValue);
        }
        if (nodeId == NodeId.mapping && property.getType() == propertyValue.getClass() && !(propertyValue instanceof Map) && !nodeValue.getTag().equals(Tag.SET)) {
            nodeValue.setTag(Tag.MAP);
        }
        this.checkGlobalTag(property, nodeValue, propertyValue);
        return new NodeTuple(nodeKey, nodeValue);
    }

    protected void checkGlobalTag(Property property, Node node, Object object) {
        if (object.getClass().isArray() && object.getClass().getComponentType().isPrimitive()) {
            return;
        }
        Class<?>[] arguments = property.getActualTypeArguments();
        if (arguments == null) return;
        if (node.getNodeId() == NodeId.sequence) {
            Class<?> t = arguments[0];
            SequenceNode snode = (SequenceNode)node;
            Iterable<Object> memberList = Collections.EMPTY_LIST;
            if (object.getClass().isArray()) {
                memberList = Arrays.asList((Object[])object);
            } else if (object instanceof Iterable) {
                memberList = (Iterable)object;
            }
            Iterator iter = memberList.iterator();
            if (!iter.hasNext()) return;
            Iterator<Node> i$ = snode.getValue().iterator();
            while (i$.hasNext()) {
                Node childNode = i$.next();
                Object member = iter.next();
                if (member == null || !t.equals(member.getClass()) || childNode.getNodeId() != NodeId.mapping) continue;
                childNode.setTag(Tag.MAP);
            }
            return;
        }
        if (object instanceof Set) {
            Class<?> t = arguments[0];
            MappingNode mnode = (MappingNode)node;
            Iterator<NodeTuple> iter = mnode.getValue().iterator();
            Set set = (Set)object;
            Iterator i$ = set.iterator();
            while (i$.hasNext()) {
                Object member = i$.next();
                NodeTuple tuple = iter.next();
                Node keyNode = tuple.getKeyNode();
                if (!t.equals(member.getClass()) || keyNode.getNodeId() != NodeId.mapping) continue;
                keyNode.setTag(Tag.MAP);
            }
            return;
        }
        if (!(object instanceof Map)) return;
        Class<?> keyType = arguments[0];
        Class<?> valueType = arguments[1];
        MappingNode mnode = (MappingNode)node;
        Iterator<NodeTuple> i$ = mnode.getValue().iterator();
        while (i$.hasNext()) {
            NodeTuple tuple = i$.next();
            this.resetTag(keyType, tuple.getKeyNode());
            this.resetTag(valueType, tuple.getValueNode());
        }
    }

    private void resetTag(Class<? extends Object> type, Node node) {
        Tag tag = node.getTag();
        if (!tag.matches(type)) return;
        if (Enum.class.isAssignableFrom(type)) {
            node.setTag(Tag.STR);
            return;
        }
        node.setTag(Tag.MAP);
    }

    protected Set<Property> getProperties(Class<? extends Object> type) {
        if (!this.typeDefinitions.containsKey(type)) return this.getPropertyUtils().getProperties(type);
        return this.typeDefinitions.get(type).getProperties();
    }

    protected class RepresentJavaBean
    implements Represent {
        protected RepresentJavaBean() {
        }

        @Override
        public Node representData(Object data) {
            return Representer.this.representJavaBean(Representer.this.getProperties(data.getClass()), data);
        }
    }
}

