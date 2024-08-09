/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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
import java.util.TimeZone;
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

    public Representer(DumperOptions dumperOptions) {
        super(dumperOptions);
        this.representers.put(null, new RepresentJavaBean(this));
    }

    public TypeDescription addTypeDescription(TypeDescription typeDescription) {
        if (Collections.EMPTY_MAP == this.typeDefinitions) {
            this.typeDefinitions = new HashMap<Class<? extends Object>, TypeDescription>();
        }
        if (typeDescription.getTag() != null) {
            this.addClassTag((Class)typeDescription.getType(), typeDescription.getTag());
        }
        typeDescription.setPropertyUtils(this.getPropertyUtils());
        return this.typeDefinitions.put(typeDescription.getType(), typeDescription);
    }

    @Override
    public void setPropertyUtils(PropertyUtils propertyUtils) {
        super.setPropertyUtils(propertyUtils);
        Collection<TypeDescription> collection = this.typeDefinitions.values();
        for (TypeDescription typeDescription : collection) {
            typeDescription.setPropertyUtils(propertyUtils);
        }
    }

    protected MappingNode representJavaBean(Set<Property> set, Object object) {
        ArrayList<NodeTuple> arrayList = new ArrayList<NodeTuple>(set.size());
        Tag tag = (Tag)this.classTags.get(object.getClass());
        Tag tag2 = tag != null ? tag : new Tag(object.getClass());
        MappingNode mappingNode = new MappingNode(tag2, arrayList, DumperOptions.FlowStyle.AUTO);
        this.representedObjects.put(object, mappingNode);
        DumperOptions.FlowStyle flowStyle = DumperOptions.FlowStyle.FLOW;
        Iterator<Property> iterator2 = set.iterator();
        while (iterator2.hasNext()) {
            Node node;
            Property property;
            Object object2;
            Tag tag3 = (object2 = (property = iterator2.next()).get(object)) == null ? null : (Tag)this.classTags.get(object2.getClass());
            NodeTuple nodeTuple = this.representJavaBeanProperty(object, property, object2, tag3);
            if (nodeTuple == null) continue;
            if (!((ScalarNode)nodeTuple.getKeyNode()).isPlain()) {
                flowStyle = DumperOptions.FlowStyle.BLOCK;
            }
            if (!((node = nodeTuple.getValueNode()) instanceof ScalarNode) || !((ScalarNode)node).isPlain()) {
                flowStyle = DumperOptions.FlowStyle.BLOCK;
            }
            arrayList.add(nodeTuple);
        }
        if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
            mappingNode.setFlowStyle(this.defaultFlowStyle);
        } else {
            mappingNode.setFlowStyle(flowStyle);
        }
        return mappingNode;
    }

    protected NodeTuple representJavaBeanProperty(Object object, Property property, Object object2, Tag tag) {
        ScalarNode scalarNode = (ScalarNode)this.representData(property.getName());
        boolean bl = this.representedObjects.containsKey(object2);
        Node node = this.representData(object2);
        if (object2 != null && !bl) {
            NodeId nodeId = node.getNodeId();
            if (tag == null) {
                if (nodeId == NodeId.scalar) {
                    if (property.getType() != Enum.class && object2 instanceof Enum) {
                        node.setTag(Tag.STR);
                    }
                } else {
                    if (nodeId == NodeId.mapping && property.getType() == object2.getClass() && !(object2 instanceof Map) && !node.getTag().equals(Tag.SET)) {
                        node.setTag(Tag.MAP);
                    }
                    this.checkGlobalTag(property, node, object2);
                }
            }
        }
        return new NodeTuple(scalarNode, node);
    }

    protected void checkGlobalTag(Property property, Node node, Object object) {
        block10: {
            Class<?>[] classArray;
            block11: {
                if (object.getClass().isArray() && object.getClass().getComponentType().isPrimitive()) {
                    return;
                }
                classArray = property.getActualTypeArguments();
                if (classArray == null) break block10;
                if (node.getNodeId() != NodeId.sequence) break block11;
                Class<?> clazz = classArray[0];
                SequenceNode sequenceNode = (SequenceNode)node;
                Iterable<Object> iterable = Collections.emptyList();
                if (object.getClass().isArray()) {
                    iterable = Arrays.asList((Object[])object);
                } else if (object instanceof Iterable) {
                    iterable = (Iterable)object;
                }
                Iterator iterator2 = iterable.iterator();
                if (!iterator2.hasNext()) break block10;
                for (Node node2 : sequenceNode.getValue()) {
                    Object t = iterator2.next();
                    if (t == null || !clazz.equals(t.getClass()) || node2.getNodeId() != NodeId.mapping) continue;
                    node2.setTag(Tag.MAP);
                }
                break block10;
            }
            if (object instanceof Set) {
                Class<?> clazz = classArray[0];
                MappingNode mappingNode = (MappingNode)node;
                Iterator<NodeTuple> iterator3 = mappingNode.getValue().iterator();
                Set set = (Set)object;
                for (Object e : set) {
                    NodeTuple nodeTuple = iterator3.next();
                    Node node3 = nodeTuple.getKeyNode();
                    if (!clazz.equals(e.getClass()) || node3.getNodeId() != NodeId.mapping) continue;
                    node3.setTag(Tag.MAP);
                }
            } else if (object instanceof Map) {
                Class<?> clazz = classArray[0];
                Class<?> clazz2 = classArray[5];
                MappingNode mappingNode = (MappingNode)node;
                for (NodeTuple nodeTuple : mappingNode.getValue()) {
                    this.resetTag(clazz, nodeTuple.getKeyNode());
                    this.resetTag(clazz2, nodeTuple.getValueNode());
                }
            }
        }
    }

    private void resetTag(Class<? extends Object> clazz, Node node) {
        Tag tag = node.getTag();
        if (tag.matches(clazz)) {
            if (Enum.class.isAssignableFrom(clazz)) {
                node.setTag(Tag.STR);
            } else {
                node.setTag(Tag.MAP);
            }
        }
    }

    protected Set<Property> getProperties(Class<? extends Object> clazz) {
        if (this.typeDefinitions.containsKey(clazz)) {
            return this.typeDefinitions.get(clazz).getProperties();
        }
        return this.getPropertyUtils().getProperties(clazz);
    }

    @Override
    public void setTimeZone(TimeZone timeZone) {
        super.setTimeZone(timeZone);
    }

    @Override
    public TimeZone getTimeZone() {
        return super.getTimeZone();
    }

    public Tag addClassTag(Class clazz, Tag tag) {
        return super.addClassTag(clazz, tag);
    }

    protected class RepresentJavaBean
    implements Represent {
        final Representer this$0;

        protected RepresentJavaBean(Representer representer) {
            this.this$0 = representer;
        }

        @Override
        public Node representData(Object object) {
            return this.this$0.representJavaBean(this.this$0.getProperties(object.getClass()), object);
        }
    }
}

