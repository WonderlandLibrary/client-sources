/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.serializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.comments.CommentLine;
import org.yaml.snakeyaml.emitter.Emitable;
import org.yaml.snakeyaml.events.AliasEvent;
import org.yaml.snakeyaml.events.CommentEvent;
import org.yaml.snakeyaml.events.DocumentEndEvent;
import org.yaml.snakeyaml.events.DocumentStartEvent;
import org.yaml.snakeyaml.events.ImplicitTuple;
import org.yaml.snakeyaml.events.MappingEndEvent;
import org.yaml.snakeyaml.events.MappingStartEvent;
import org.yaml.snakeyaml.events.ScalarEvent;
import org.yaml.snakeyaml.events.SequenceEndEvent;
import org.yaml.snakeyaml.events.SequenceStartEvent;
import org.yaml.snakeyaml.events.StreamEndEvent;
import org.yaml.snakeyaml.events.StreamStartEvent;
import org.yaml.snakeyaml.nodes.AnchorNode;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.resolver.Resolver;
import org.yaml.snakeyaml.serializer.AnchorGenerator;
import org.yaml.snakeyaml.serializer.SerializerException;

public final class Serializer {
    private final Emitable emitter;
    private final Resolver resolver;
    private final boolean explicitStart;
    private final boolean explicitEnd;
    private DumperOptions.Version useVersion;
    private final Map<String, String> useTags;
    private final Set<Node> serializedNodes;
    private final Map<Node, String> anchors;
    private final AnchorGenerator anchorGenerator;
    private Boolean closed;
    private final Tag explicitRoot;

    public Serializer(Emitable emitable, Resolver resolver, DumperOptions dumperOptions, Tag tag) {
        if (emitable == null) {
            throw new NullPointerException("Emitter must  be provided");
        }
        if (resolver == null) {
            throw new NullPointerException("Resolver must  be provided");
        }
        if (dumperOptions == null) {
            throw new NullPointerException("DumperOptions must  be provided");
        }
        this.emitter = emitable;
        this.resolver = resolver;
        this.explicitStart = dumperOptions.isExplicitStart();
        this.explicitEnd = dumperOptions.isExplicitEnd();
        if (dumperOptions.getVersion() != null) {
            this.useVersion = dumperOptions.getVersion();
        }
        this.useTags = dumperOptions.getTags();
        this.serializedNodes = new HashSet<Node>();
        this.anchors = new HashMap<Node, String>();
        this.anchorGenerator = dumperOptions.getAnchorGenerator();
        this.closed = null;
        this.explicitRoot = tag;
    }

    public void open() throws IOException {
        if (this.closed != null) {
            if (Boolean.TRUE.equals(this.closed)) {
                throw new SerializerException("serializer is closed");
            }
            throw new SerializerException("serializer is already opened");
        }
        this.emitter.emit(new StreamStartEvent(null, null));
        this.closed = Boolean.FALSE;
    }

    public void close() throws IOException {
        if (this.closed == null) {
            throw new SerializerException("serializer is not opened");
        }
        if (!Boolean.TRUE.equals(this.closed)) {
            this.emitter.emit(new StreamEndEvent(null, null));
            this.closed = Boolean.TRUE;
            this.serializedNodes.clear();
            this.anchors.clear();
        }
    }

    public void serialize(Node node) throws IOException {
        if (this.closed == null) {
            throw new SerializerException("serializer is not opened");
        }
        if (this.closed.booleanValue()) {
            throw new SerializerException("serializer is closed");
        }
        this.emitter.emit(new DocumentStartEvent(null, null, this.explicitStart, this.useVersion, this.useTags));
        this.anchorNode(node);
        if (this.explicitRoot != null) {
            node.setTag(this.explicitRoot);
        }
        this.serializeNode(node, null);
        this.emitter.emit(new DocumentEndEvent(null, null, this.explicitEnd));
        this.serializedNodes.clear();
        this.anchors.clear();
    }

    private void anchorNode(Node node) {
        if (node.getNodeId() == NodeId.anchor) {
            node = ((AnchorNode)node).getRealNode();
        }
        if (this.anchors.containsKey(node)) {
            String string = this.anchors.get(node);
            if (null == string) {
                string = this.anchorGenerator.nextAnchor(node);
                this.anchors.put(node, string);
            }
        } else {
            this.anchors.put(node, node.getAnchor() != null ? this.anchorGenerator.nextAnchor(node) : null);
            switch (1.$SwitchMap$org$yaml$snakeyaml$nodes$NodeId[node.getNodeId().ordinal()]) {
                case 1: {
                    SequenceNode sequenceNode = (SequenceNode)node;
                    List<Node> list = sequenceNode.getValue();
                    for (Node node2 : list) {
                        this.anchorNode(node2);
                    }
                    break;
                }
                case 2: {
                    MappingNode mappingNode = (MappingNode)node;
                    List<NodeTuple> list = mappingNode.getValue();
                    for (NodeTuple nodeTuple : list) {
                        Node node3 = nodeTuple.getKeyNode();
                        Node node4 = nodeTuple.getValueNode();
                        this.anchorNode(node3);
                        this.anchorNode(node4);
                    }
                    break;
                }
            }
        }
    }

    private void serializeNode(Node node, Node node2) throws IOException {
        if (node.getNodeId() == NodeId.anchor) {
            node = ((AnchorNode)node).getRealNode();
        }
        String string = this.anchors.get(node);
        if (this.serializedNodes.contains(node)) {
            this.emitter.emit(new AliasEvent(string, null, null));
        } else {
            this.serializedNodes.add(node);
            switch (1.$SwitchMap$org$yaml$snakeyaml$nodes$NodeId[node.getNodeId().ordinal()]) {
                case 3: {
                    ScalarNode scalarNode = (ScalarNode)node;
                    this.serializeComments(node.getBlockComments());
                    Tag tag = this.resolver.resolve(NodeId.scalar, scalarNode.getValue(), false);
                    Tag tag2 = this.resolver.resolve(NodeId.scalar, scalarNode.getValue(), true);
                    ImplicitTuple implicitTuple = new ImplicitTuple(node.getTag().equals(tag), node.getTag().equals(tag2));
                    ScalarEvent scalarEvent = new ScalarEvent(string, node.getTag().getValue(), implicitTuple, scalarNode.getValue(), null, null, scalarNode.getScalarStyle());
                    this.emitter.emit(scalarEvent);
                    this.serializeComments(node.getInLineComments());
                    this.serializeComments(node.getEndComments());
                    break;
                }
                case 1: {
                    SequenceNode sequenceNode = (SequenceNode)node;
                    this.serializeComments(node.getBlockComments());
                    boolean bl = node.getTag().equals(this.resolver.resolve(NodeId.sequence, null, false));
                    this.emitter.emit(new SequenceStartEvent(string, node.getTag().getValue(), bl, null, null, sequenceNode.getFlowStyle()));
                    List<Node> list = sequenceNode.getValue();
                    for (Node node3 : list) {
                        this.serializeNode(node3, node);
                    }
                    this.emitter.emit(new SequenceEndEvent(null, null));
                    this.serializeComments(node.getInLineComments());
                    this.serializeComments(node.getEndComments());
                    break;
                }
                default: {
                    this.serializeComments(node.getBlockComments());
                    Tag tag = this.resolver.resolve(NodeId.mapping, null, false);
                    boolean bl = node.getTag().equals(tag);
                    MappingNode mappingNode = (MappingNode)node;
                    List<NodeTuple> list = mappingNode.getValue();
                    if (mappingNode.getTag() == Tag.COMMENT) break;
                    this.emitter.emit(new MappingStartEvent(string, mappingNode.getTag().getValue(), bl, null, null, mappingNode.getFlowStyle()));
                    for (NodeTuple nodeTuple : list) {
                        Node node4 = nodeTuple.getKeyNode();
                        Node node5 = nodeTuple.getValueNode();
                        this.serializeNode(node4, mappingNode);
                        this.serializeNode(node5, mappingNode);
                    }
                    this.emitter.emit(new MappingEndEvent(null, null));
                    this.serializeComments(node.getInLineComments());
                    this.serializeComments(node.getEndComments());
                }
            }
        }
    }

    private void serializeComments(List<CommentLine> list) throws IOException {
        if (list == null) {
            return;
        }
        for (CommentLine commentLine : list) {
            CommentEvent commentEvent = new CommentEvent(commentLine.getCommentType(), commentLine.getValue(), commentLine.getStartMark(), commentLine.getEndMark());
            this.emitter.emit(commentEvent);
        }
    }
}

