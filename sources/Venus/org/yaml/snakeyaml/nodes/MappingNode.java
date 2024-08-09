/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.nodes;

import java.util.List;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.nodes.CollectionNode;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;

public class MappingNode
extends CollectionNode<NodeTuple> {
    private List<NodeTuple> value;
    private boolean merged = false;

    public MappingNode(Tag tag, boolean bl, List<NodeTuple> list, Mark mark, Mark mark2, DumperOptions.FlowStyle flowStyle) {
        super(tag, mark, mark2, flowStyle);
        if (list == null) {
            throw new NullPointerException("value in a Node is required.");
        }
        this.value = list;
        this.resolved = bl;
    }

    public MappingNode(Tag tag, List<NodeTuple> list, DumperOptions.FlowStyle flowStyle) {
        this(tag, true, list, null, null, flowStyle);
    }

    @Override
    public NodeId getNodeId() {
        return NodeId.mapping;
    }

    @Override
    public List<NodeTuple> getValue() {
        return this.value;
    }

    public void setValue(List<NodeTuple> list) {
        this.value = list;
    }

    public void setOnlyKeyType(Class<? extends Object> clazz) {
        for (NodeTuple nodeTuple : this.value) {
            nodeTuple.getKeyNode().setType(clazz);
        }
    }

    public void setTypes(Class<? extends Object> clazz, Class<? extends Object> clazz2) {
        for (NodeTuple nodeTuple : this.value) {
            nodeTuple.getValueNode().setType(clazz2);
            nodeTuple.getKeyNode().setType(clazz);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (NodeTuple nodeTuple : this.getValue()) {
            stringBuilder.append("{ key=");
            stringBuilder.append(nodeTuple.getKeyNode());
            stringBuilder.append("; value=");
            if (nodeTuple.getValueNode() instanceof CollectionNode) {
                stringBuilder.append(System.identityHashCode(nodeTuple.getValueNode()));
            } else {
                stringBuilder.append(nodeTuple);
            }
            stringBuilder.append(" }");
        }
        String string = stringBuilder.toString();
        return "<" + this.getClass().getName() + " (tag=" + this.getTag() + ", values=" + string + ")>";
    }

    public void setMerged(boolean bl) {
        this.merged = bl;
    }

    public boolean isMerged() {
        return this.merged;
    }
}

