package org.yaml.snakeyaml.serializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.emitter.Emitable;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.events.AliasEvent;
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
import org.yaml.snakeyaml.nodes.CollectionNode;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.resolver.Resolver;

public final class Serializer {
   private final Emitable emitter;
   private final Resolver resolver;
   private boolean explicitStart;
   private boolean explicitEnd;
   private DumperOptions.Version useVersion;
   private Map useTags;
   private Set serializedNodes;
   private Map anchors;
   private AnchorGenerator anchorGenerator;
   private Boolean closed;
   private Tag explicitRoot;

   public Serializer(Emitable var1, Resolver var2, DumperOptions var3, Tag var4) {
      this.emitter = var1;
      this.resolver = var2;
      this.explicitStart = var3.isExplicitStart();
      this.explicitEnd = var3.isExplicitEnd();
      if (var3.getVersion() != null) {
         this.useVersion = var3.getVersion();
      }

      this.useTags = var3.getTags();
      this.serializedNodes = new HashSet();
      this.anchors = new HashMap();
      this.anchorGenerator = var3.getAnchorGenerator();
      this.closed = null;
      this.explicitRoot = var4;
   }

   public void open() throws IOException {
      if (this.closed == null) {
         this.emitter.emit(new StreamStartEvent((Mark)null, (Mark)null));
         this.closed = Boolean.FALSE;
      } else if (Boolean.TRUE.equals(this.closed)) {
         throw new SerializerException("serializer is closed");
      } else {
         throw new SerializerException("serializer is already opened");
      }
   }

   public void close() throws IOException {
      if (this.closed == null) {
         throw new SerializerException("serializer is not opened");
      } else {
         if (!Boolean.TRUE.equals(this.closed)) {
            this.emitter.emit(new StreamEndEvent((Mark)null, (Mark)null));
            this.closed = Boolean.TRUE;
         }

      }
   }

   public void serialize(Node var1) throws IOException {
      if (this.closed == null) {
         throw new SerializerException("serializer is not opened");
      } else if (this.closed) {
         throw new SerializerException("serializer is closed");
      } else {
         this.emitter.emit(new DocumentStartEvent((Mark)null, (Mark)null, this.explicitStart, this.useVersion, this.useTags));
         this.anchorNode(var1);
         if (this.explicitRoot != null) {
            var1.setTag(this.explicitRoot);
         }

         this.serializeNode(var1, (Node)null);
         this.emitter.emit(new DocumentEndEvent((Mark)null, (Mark)null, this.explicitEnd));
         this.serializedNodes.clear();
         this.anchors.clear();
      }
   }

   private void anchorNode(Node var1) {
      if (var1.getNodeId() == NodeId.anchor) {
         var1 = ((AnchorNode)var1).getRealNode();
      }

      if (this.anchors.containsKey(var1)) {
         String var2 = (String)this.anchors.get(var1);
         if (null == var2) {
            var2 = this.anchorGenerator.nextAnchor(var1);
            this.anchors.put(var1, var2);
         }
      } else {
         this.anchors.put(var1, (Object)null);
         switch(var1.getNodeId()) {
         case sequence:
            SequenceNode var10 = (SequenceNode)var1;
            List var3 = var10.getValue();
            Iterator var11 = var3.iterator();

            while(var11.hasNext()) {
               Node var12 = (Node)var11.next();
               this.anchorNode(var12);
            }

            return;
         case mapping:
            MappingNode var4 = (MappingNode)var1;
            List var5 = var4.getValue();
            Iterator var6 = var5.iterator();

            while(var6.hasNext()) {
               NodeTuple var7 = (NodeTuple)var6.next();
               Node var8 = var7.getKeyNode();
               Node var9 = var7.getValueNode();
               this.anchorNode(var8);
               this.anchorNode(var9);
            }
         }
      }

   }

   private void serializeNode(Node var1, Node var2) throws IOException {
      if (var1.getNodeId() == NodeId.anchor) {
         var1 = ((AnchorNode)var1).getRealNode();
      }

      String var3 = (String)this.anchors.get(var1);
      if (this.serializedNodes.contains(var1)) {
         this.emitter.emit(new AliasEvent(var3, (Mark)null, (Mark)null));
      } else {
         this.serializedNodes.add(var1);
         switch(var1.getNodeId()) {
         case sequence:
            SequenceNode var9 = (SequenceNode)var1;
            boolean var10 = var1.getTag().equals(this.resolver.resolve(NodeId.sequence, (String)null, true));
            this.emitter.emit(new SequenceStartEvent(var3, var1.getTag().getValue(), var10, (Mark)null, (Mark)null, var9.getFlowStyle()));
            List var11 = var9.getValue();
            Iterator var12 = var11.iterator();

            while(var12.hasNext()) {
               Node var13 = (Node)var12.next();
               this.serializeNode(var13, var1);
            }

            this.emitter.emit(new SequenceEndEvent((Mark)null, (Mark)null));
            break;
         case scalar:
            ScalarNode var4 = (ScalarNode)var1;
            Tag var5 = this.resolver.resolve(NodeId.scalar, var4.getValue(), true);
            Tag var6 = this.resolver.resolve(NodeId.scalar, var4.getValue(), false);
            ImplicitTuple var7 = new ImplicitTuple(var1.getTag().equals(var5), var1.getTag().equals(var6));
            ScalarEvent var8 = new ScalarEvent(var3, var1.getTag().getValue(), var7, var4.getValue(), (Mark)null, (Mark)null, var4.getStyle());
            this.emitter.emit(var8);
            break;
         default:
            Tag var20 = this.resolver.resolve(NodeId.mapping, (String)null, true);
            boolean var21 = var1.getTag().equals(var20);
            this.emitter.emit(new MappingStartEvent(var3, var1.getTag().getValue(), var21, (Mark)null, (Mark)null, ((CollectionNode)var1).getFlowStyle()));
            MappingNode var14 = (MappingNode)var1;
            List var15 = var14.getValue();
            Iterator var16 = var15.iterator();

            while(var16.hasNext()) {
               NodeTuple var17 = (NodeTuple)var16.next();
               Node var18 = var17.getKeyNode();
               Node var19 = var17.getValueNode();
               this.serializeNode(var18, var14);
               this.serializeNode(var19, var14);
            }

            this.emitter.emit(new MappingEndEvent((Mark)null, (Mark)null));
         }
      }

   }
}
