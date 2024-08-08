package org.yaml.snakeyaml.composer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.yaml.snakeyaml.error.Mark;
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
   private final Map anchors;
   private final Set recursiveNodes;

   public Composer(Parser var1, Resolver var2) {
      this.parser = var1;
      this.resolver = var2;
      this.anchors = new HashMap();
      this.recursiveNodes = new HashSet();
   }

   public boolean checkNode() {
      if (this.parser.checkEvent(Event.ID.StreamStart)) {
         this.parser.getEvent();
      }

      return !this.parser.checkEvent(Event.ID.StreamEnd);
   }

   public Node getNode() {
      return !this.parser.checkEvent(Event.ID.StreamEnd) ? this.composeDocument() : null;
   }

   public Node getSingleNode() {
      this.parser.getEvent();
      Node var1 = null;
      if (!this.parser.checkEvent(Event.ID.StreamEnd)) {
         var1 = this.composeDocument();
      }

      if (!this.parser.checkEvent(Event.ID.StreamEnd)) {
         Event var2 = this.parser.getEvent();
         throw new ComposerException("expected a single document in the stream", var1.getStartMark(), "but found another document", var2.getStartMark());
      } else {
         this.parser.getEvent();
         return var1;
      }
   }

   private Node composeDocument() {
      this.parser.getEvent();
      Node var1 = this.composeNode((Node)null);
      this.parser.getEvent();
      this.anchors.clear();
      this.recursiveNodes.clear();
      return var1;
   }

   private Node composeNode(Node var1) {
      this.recursiveNodes.add(var1);
      Node var2 = null;
      String var4;
      if (this.parser.checkEvent(Event.ID.Alias)) {
         AliasEvent var3 = (AliasEvent)this.parser.getEvent();
         var4 = var3.getAnchor();
         if (!this.anchors.containsKey(var4)) {
            throw new ComposerException((String)null, (Mark)null, "found undefined alias " + var4, var3.getStartMark());
         }

         var2 = (Node)this.anchors.get(var4);
         if (this.recursiveNodes.remove(var2)) {
            var2.setTwoStepsConstruction(true);
         }
      } else {
         NodeEvent var5 = (NodeEvent)this.parser.peekEvent();
         var4 = null;
         var4 = var5.getAnchor();
         if (this.parser.checkEvent(Event.ID.Scalar)) {
            var2 = this.composeScalarNode(var4);
         } else if (this.parser.checkEvent(Event.ID.SequenceStart)) {
            var2 = this.composeSequenceNode(var4);
         } else {
            var2 = this.composeMappingNode(var4);
         }
      }

      this.recursiveNodes.remove(var1);
      return var2;
   }

   protected Node composeScalarNode(String var1) {
      ScalarEvent var2 = (ScalarEvent)this.parser.getEvent();
      String var3 = var2.getTag();
      boolean var4 = false;
      Tag var5;
      if (var3 != null && !var3.equals("!")) {
         var5 = new Tag(var3);
      } else {
         var5 = this.resolver.resolve(NodeId.scalar, var2.getValue(), var2.getImplicit().canOmitTagInPlainScalar());
         var4 = true;
      }

      ScalarNode var6 = new ScalarNode(var5, var4, var2.getValue(), var2.getStartMark(), var2.getEndMark(), var2.getStyle());
      if (var1 != null) {
         var6.setAnchor(var1);
         this.anchors.put(var1, var6);
      }

      return var6;
   }

   protected Node composeSequenceNode(String var1) {
      SequenceStartEvent var2 = (SequenceStartEvent)this.parser.getEvent();
      String var3 = var2.getTag();
      boolean var5 = false;
      Tag var4;
      if (var3 != null && !var3.equals("!")) {
         var4 = new Tag(var3);
      } else {
         var4 = this.resolver.resolve(NodeId.sequence, (String)null, var2.getImplicit());
         var5 = true;
      }

      ArrayList var6 = new ArrayList();
      SequenceNode var7 = new SequenceNode(var4, var5, var6, var2.getStartMark(), (Mark)null, var2.getFlowStyle());
      if (var1 != null) {
         var7.setAnchor(var1);
         this.anchors.put(var1, var7);
      }

      while(!this.parser.checkEvent(Event.ID.SequenceEnd)) {
         var6.add(this.composeNode(var7));
      }

      Event var8 = this.parser.getEvent();
      var7.setEndMark(var8.getEndMark());
      return var7;
   }

   protected Node composeMappingNode(String var1) {
      MappingStartEvent var2 = (MappingStartEvent)this.parser.getEvent();
      String var3 = var2.getTag();
      boolean var5 = false;
      Tag var4;
      if (var3 != null && !var3.equals("!")) {
         var4 = new Tag(var3);
      } else {
         var4 = this.resolver.resolve(NodeId.mapping, (String)null, var2.getImplicit());
         var5 = true;
      }

      ArrayList var6 = new ArrayList();
      MappingNode var7 = new MappingNode(var4, var5, var6, var2.getStartMark(), (Mark)null, var2.getFlowStyle());
      if (var1 != null) {
         var7.setAnchor(var1);
         this.anchors.put(var1, var7);
      }

      while(!this.parser.checkEvent(Event.ID.MappingEnd)) {
         this.composeMappingChildren(var6, var7);
      }

      Event var8 = this.parser.getEvent();
      var7.setEndMark(var8.getEndMark());
      return var7;
   }

   protected void composeMappingChildren(List var1, MappingNode var2) {
      Node var3 = this.composeKeyNode(var2);
      if (var3.getTag().equals(Tag.MERGE)) {
         var2.setMerged(true);
      }

      Node var4 = this.composeValueNode(var2);
      var1.add(new NodeTuple(var3, var4));
   }

   protected Node composeKeyNode(MappingNode var1) {
      return this.composeNode(var1);
   }

   protected Node composeValueNode(MappingNode var1) {
      return this.composeNode(var1);
   }
}
