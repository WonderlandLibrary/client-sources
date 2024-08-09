/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.composer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.comments.CommentEventsCollector;
import org.yaml.snakeyaml.comments.CommentLine;
import org.yaml.snakeyaml.comments.CommentType;
import org.yaml.snakeyaml.composer.ComposerException;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.events.AliasEvent;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.MappingStartEvent;
import org.yaml.snakeyaml.events.NodeEvent;
import org.yaml.snakeyaml.events.ScalarEvent;
import org.yaml.snakeyaml.events.SequenceStartEvent;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.parser.Parser;
import org.yaml.snakeyaml.resolver.Resolver;

public class Composer {
    protected final Parser parser;
    private final Resolver resolver;
    private final Map<String, Node> anchors;
    private final Set<Node> recursiveNodes;
    private int nonScalarAliasesCount = 0;
    private final LoaderOptions loadingConfig;
    private final CommentEventsCollector blockCommentsCollector;
    private final CommentEventsCollector inlineCommentsCollector;
    private int nestingDepth = 0;
    private final int nestingDepthLimit;

    public Composer(Parser parser, Resolver resolver, LoaderOptions loaderOptions) {
        if (parser == null) {
            throw new NullPointerException("Parser must be provided");
        }
        if (resolver == null) {
            throw new NullPointerException("Resolver must be provided");
        }
        if (loaderOptions == null) {
            throw new NullPointerException("LoaderOptions must be provided");
        }
        this.parser = parser;
        this.resolver = resolver;
        this.anchors = new HashMap<String, Node>();
        this.recursiveNodes = new HashSet<Node>();
        this.loadingConfig = loaderOptions;
        this.blockCommentsCollector = new CommentEventsCollector(parser, CommentType.BLANK_LINE, CommentType.BLOCK);
        this.inlineCommentsCollector = new CommentEventsCollector(parser, CommentType.IN_LINE);
        this.nestingDepthLimit = loaderOptions.getNestingDepthLimit();
    }

    public boolean checkNode() {
        if (this.parser.checkEvent(Event.ID.StreamStart)) {
            this.parser.getEvent();
        }
        return !this.parser.checkEvent(Event.ID.StreamEnd);
    }

    public Node getNode() {
        this.blockCommentsCollector.collectEvents();
        if (this.parser.checkEvent(Event.ID.StreamEnd)) {
            List<CommentLine> list = this.blockCommentsCollector.consume();
            Mark mark = list.get(0).getStartMark();
            List<NodeTuple> list2 = Collections.emptyList();
            MappingNode mappingNode = new MappingNode(Tag.COMMENT, false, list2, mark, null, DumperOptions.FlowStyle.BLOCK);
            mappingNode.setBlockComments(list);
            return mappingNode;
        }
        this.parser.getEvent();
        Node node = this.composeNode(null);
        this.blockCommentsCollector.collectEvents();
        if (!this.blockCommentsCollector.isEmpty()) {
            node.setEndComments(this.blockCommentsCollector.consume());
        }
        this.parser.getEvent();
        this.anchors.clear();
        this.recursiveNodes.clear();
        return node;
    }

    public Node getSingleNode() {
        this.parser.getEvent();
        Node node = null;
        if (!this.parser.checkEvent(Event.ID.StreamEnd)) {
            node = this.getNode();
        }
        if (!this.parser.checkEvent(Event.ID.StreamEnd)) {
            Event event = this.parser.getEvent();
            Mark mark = node != null ? node.getStartMark() : null;
            throw new ComposerException("expected a single document in the stream", mark, "but found another document", event.getStartMark());
        }
        this.parser.getEvent();
        return node;
    }

    private Node composeNode(Node node) {
        Node node2;
        this.blockCommentsCollector.collectEvents();
        if (node != null) {
            this.recursiveNodes.add(node);
        }
        if (this.parser.checkEvent(Event.ID.Alias)) {
            AliasEvent aliasEvent = (AliasEvent)this.parser.getEvent();
            String string = aliasEvent.getAnchor();
            if (!this.anchors.containsKey(string)) {
                throw new ComposerException(null, null, "found undefined alias " + string, aliasEvent.getStartMark());
            }
            node2 = this.anchors.get(string);
            if (!(node2 instanceof ScalarNode)) {
                ++this.nonScalarAliasesCount;
                if (this.nonScalarAliasesCount > this.loadingConfig.getMaxAliasesForCollections()) {
                    throw new YAMLException("Number of aliases for non-scalar nodes exceeds the specified max=" + this.loadingConfig.getMaxAliasesForCollections());
                }
            }
            if (this.recursiveNodes.remove(node2)) {
                node2.setTwoStepsConstruction(false);
            }
            this.blockCommentsCollector.consume();
            this.inlineCommentsCollector.collectEvents().consume();
        } else {
            NodeEvent nodeEvent = (NodeEvent)this.parser.peekEvent();
            String string = nodeEvent.getAnchor();
            this.increaseNestingDepth();
            node2 = this.parser.checkEvent(Event.ID.Scalar) ? this.composeScalarNode(string, this.blockCommentsCollector.consume()) : (this.parser.checkEvent(Event.ID.SequenceStart) ? this.composeSequenceNode(string) : this.composeMappingNode(string));
            this.decreaseNestingDepth();
        }
        this.recursiveNodes.remove(node);
        return node2;
    }

    protected Node composeScalarNode(String string, List<CommentLine> list) {
        Tag tag;
        ScalarEvent scalarEvent = (ScalarEvent)this.parser.getEvent();
        String string2 = scalarEvent.getTag();
        boolean bl = false;
        if (string2 == null || string2.equals("!")) {
            tag = this.resolver.resolve(NodeId.scalar, scalarEvent.getValue(), scalarEvent.getImplicit().canOmitTagInPlainScalar());
            bl = true;
        } else {
            tag = new Tag(string2);
            if (tag.isCustomGlobal() && !this.loadingConfig.getTagInspector().isGlobalTagAllowed(tag)) {
                throw new ComposerException(null, null, "Global tag is not allowed: " + string2, scalarEvent.getStartMark());
            }
        }
        ScalarNode scalarNode = new ScalarNode(tag, bl, scalarEvent.getValue(), scalarEvent.getStartMark(), scalarEvent.getEndMark(), scalarEvent.getScalarStyle());
        if (string != null) {
            scalarNode.setAnchor(string);
            this.anchors.put(string, scalarNode);
        }
        scalarNode.setBlockComments(list);
        scalarNode.setInLineComments(this.inlineCommentsCollector.collectEvents().consume());
        return scalarNode;
    }

    protected Node composeSequenceNode(String string) {
        Tag tag;
        SequenceStartEvent sequenceStartEvent = (SequenceStartEvent)this.parser.getEvent();
        String string2 = sequenceStartEvent.getTag();
        boolean bl = false;
        if (string2 == null || string2.equals("!")) {
            tag = this.resolver.resolve(NodeId.sequence, null, sequenceStartEvent.getImplicit());
            bl = true;
        } else {
            tag = new Tag(string2);
            if (tag.isCustomGlobal() && !this.loadingConfig.getTagInspector().isGlobalTagAllowed(tag)) {
                throw new ComposerException(null, null, "Global tag is not allowed: " + string2, sequenceStartEvent.getStartMark());
            }
        }
        ArrayList<Node> arrayList = new ArrayList<Node>();
        SequenceNode sequenceNode = new SequenceNode(tag, bl, arrayList, sequenceStartEvent.getStartMark(), null, sequenceStartEvent.getFlowStyle());
        if (sequenceStartEvent.isFlow()) {
            sequenceNode.setBlockComments(this.blockCommentsCollector.consume());
        }
        if (string != null) {
            sequenceNode.setAnchor(string);
            this.anchors.put(string, sequenceNode);
        }
        while (!this.parser.checkEvent(Event.ID.SequenceEnd)) {
            this.blockCommentsCollector.collectEvents();
            if (this.parser.checkEvent(Event.ID.SequenceEnd)) break;
            arrayList.add(this.composeNode(sequenceNode));
        }
        if (sequenceStartEvent.isFlow()) {
            sequenceNode.setInLineComments(this.inlineCommentsCollector.collectEvents().consume());
        }
        Event event = this.parser.getEvent();
        sequenceNode.setEndMark(event.getEndMark());
        this.inlineCommentsCollector.collectEvents();
        if (!this.inlineCommentsCollector.isEmpty()) {
            sequenceNode.setInLineComments(this.inlineCommentsCollector.consume());
        }
        return sequenceNode;
    }

    protected Node composeMappingNode(String string) {
        Tag tag;
        MappingStartEvent mappingStartEvent = (MappingStartEvent)this.parser.getEvent();
        String string2 = mappingStartEvent.getTag();
        boolean bl = false;
        if (string2 == null || string2.equals("!")) {
            tag = this.resolver.resolve(NodeId.mapping, null, mappingStartEvent.getImplicit());
            bl = true;
        } else {
            tag = new Tag(string2);
            if (tag.isCustomGlobal() && !this.loadingConfig.getTagInspector().isGlobalTagAllowed(tag)) {
                throw new ComposerException(null, null, "Global tag is not allowed: " + string2, mappingStartEvent.getStartMark());
            }
        }
        ArrayList<NodeTuple> arrayList = new ArrayList<NodeTuple>();
        MappingNode mappingNode = new MappingNode(tag, bl, arrayList, mappingStartEvent.getStartMark(), null, mappingStartEvent.getFlowStyle());
        if (mappingStartEvent.isFlow()) {
            mappingNode.setBlockComments(this.blockCommentsCollector.consume());
        }
        if (string != null) {
            mappingNode.setAnchor(string);
            this.anchors.put(string, mappingNode);
        }
        while (!this.parser.checkEvent(Event.ID.MappingEnd)) {
            this.blockCommentsCollector.collectEvents();
            if (this.parser.checkEvent(Event.ID.MappingEnd)) break;
            this.composeMappingChildren(arrayList, mappingNode);
        }
        if (mappingStartEvent.isFlow()) {
            mappingNode.setInLineComments(this.inlineCommentsCollector.collectEvents().consume());
        }
        Event event = this.parser.getEvent();
        mappingNode.setEndMark(event.getEndMark());
        this.inlineCommentsCollector.collectEvents();
        if (!this.inlineCommentsCollector.isEmpty()) {
            mappingNode.setInLineComments(this.inlineCommentsCollector.consume());
        }
        return mappingNode;
    }

    protected void composeMappingChildren(List<NodeTuple> list, MappingNode mappingNode) {
        Node node = this.composeKeyNode(mappingNode);
        if (node.getTag().equals(Tag.MERGE)) {
            mappingNode.setMerged(false);
        }
        Node node2 = this.composeValueNode(mappingNode);
        list.add(new NodeTuple(node, node2));
    }

    protected Node composeKeyNode(MappingNode mappingNode) {
        return this.composeNode(mappingNode);
    }

    protected Node composeValueNode(MappingNode mappingNode) {
        return this.composeNode(mappingNode);
    }

    private void increaseNestingDepth() {
        if (this.nestingDepth > this.nestingDepthLimit) {
            throw new YAMLException("Nesting Depth exceeded max " + this.nestingDepthLimit);
        }
        ++this.nestingDepth;
    }

    private void decreaseNestingDepth() {
        if (this.nestingDepth > 0) {
            --this.nestingDepth;
        } else {
            throw new YAMLException("Nesting Depth cannot be negative");
        }
    }
}

