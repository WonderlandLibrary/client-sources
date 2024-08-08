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

public class Representer extends SafeRepresenter {
   protected Map typeDefinitions = Collections.emptyMap();

   public Representer() {
      this.representers.put((Object)null, new Representer.RepresentJavaBean(this));
   }

   public TypeDescription addTypeDescription(TypeDescription var1) {
      if (Collections.EMPTY_MAP == this.typeDefinitions) {
         this.typeDefinitions = new HashMap();
      }

      if (var1.getTag() != null) {
         this.addClassTag(var1.getType(), var1.getTag());
      }

      var1.setPropertyUtils(this.getPropertyUtils());
      return (TypeDescription)this.typeDefinitions.put(var1.getType(), var1);
   }

   public void setPropertyUtils(PropertyUtils var1) {
      super.setPropertyUtils(var1);
      Collection var2 = this.typeDefinitions.values();
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         TypeDescription var4 = (TypeDescription)var3.next();
         var4.setPropertyUtils(var1);
      }

   }

   protected MappingNode representJavaBean(Set var1, Object var2) {
      ArrayList var3 = new ArrayList(var1.size());
      Tag var5 = (Tag)this.classTags.get(var2.getClass());
      Tag var4 = var5 != null ? var5 : new Tag(var2.getClass());
      MappingNode var6 = new MappingNode(var4, var3, (Boolean)null);
      this.representedObjects.put(var2, var6);
      boolean var7 = true;
      Iterator var8 = var1.iterator();

      while(true) {
         NodeTuple var12;
         do {
            if (!var8.hasNext()) {
               if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
                  var6.setFlowStyle(this.defaultFlowStyle.getStyleBoolean());
               } else {
                  var6.setFlowStyle(var7);
               }

               return var6;
            }

            Property var9 = (Property)var8.next();
            Object var10 = var9.get(var2);
            Tag var11 = var10 == null ? null : (Tag)this.classTags.get(var10.getClass());
            var12 = this.representJavaBeanProperty(var2, var9, var10, var11);
         } while(var12 == null);

         if (((ScalarNode)var12.getKeyNode()).getStyle() != null) {
            var7 = false;
         }

         Node var13 = var12.getValueNode();
         if (!(var13 instanceof ScalarNode) || ((ScalarNode)var13).getStyle() != null) {
            var7 = false;
         }

         var3.add(var12);
      }
   }

   protected NodeTuple representJavaBeanProperty(Object var1, Property var2, Object var3, Tag var4) {
      ScalarNode var5 = (ScalarNode)this.representData(var2.getName());
      boolean var6 = this.representedObjects.containsKey(var3);
      Node var7 = this.representData(var3);
      if (var3 != null && !var6) {
         NodeId var8 = var7.getNodeId();
         if (var4 == null) {
            if (var8 == NodeId.scalar) {
               if (var2.getType() == var3.getClass() && var3 instanceof Enum) {
                  var7.setTag(Tag.STR);
               }
            } else {
               if (var8 == NodeId.mapping && var2.getType() == var3.getClass() && !(var3 instanceof Map) && !var7.getTag().equals(Tag.SET)) {
                  var7.setTag(Tag.MAP);
               }

               this.checkGlobalTag(var2, var7, var3);
            }
         }
      }

      return new NodeTuple(var5, var7);
   }

   protected void checkGlobalTag(Property var1, Node var2, Object var3) {
      if (!var3.getClass().isArray() || !var3.getClass().getComponentType().isPrimitive()) {
         Class[] var4 = var1.getActualTypeArguments();
         if (var4 != null) {
            Class var5;
            Iterator var8;
            Iterator var9;
            if (var2.getNodeId() == NodeId.sequence) {
               var5 = var4[0];
               SequenceNode var6 = (SequenceNode)var2;
               Object var7 = Collections.EMPTY_LIST;
               if (var3.getClass().isArray()) {
                  var7 = Arrays.asList((Object[])((Object[])var3));
               } else if (var3 instanceof Iterable) {
                  var7 = (Iterable)var3;
               }

               var8 = ((Iterable)var7).iterator();
               if (var8.hasNext()) {
                  var9 = var6.getValue().iterator();

                  while(var9.hasNext()) {
                     Node var10 = (Node)var9.next();
                     Object var11 = var8.next();
                     if (var11 != null && var5.equals(var11.getClass()) && var10.getNodeId() == NodeId.mapping) {
                        var10.setTag(Tag.MAP);
                     }
                  }
               }
            } else if (var3 instanceof Set) {
               var5 = var4[0];
               MappingNode var13 = (MappingNode)var2;
               Iterator var15 = var13.getValue().iterator();
               Set var16 = (Set)var3;
               var9 = var16.iterator();

               while(var9.hasNext()) {
                  Object var19 = var9.next();
                  NodeTuple var20 = (NodeTuple)var15.next();
                  Node var12 = var20.getKeyNode();
                  if (var5.equals(var19.getClass()) && var12.getNodeId() == NodeId.mapping) {
                     var12.setTag(Tag.MAP);
                  }
               }
            } else if (var3 instanceof Map) {
               var5 = var4[0];
               Class var14 = var4[1];
               MappingNode var17 = (MappingNode)var2;
               var8 = var17.getValue().iterator();

               while(var8.hasNext()) {
                  NodeTuple var18 = (NodeTuple)var8.next();
                  this.resetTag(var5, var18.getKeyNode());
                  this.resetTag(var14, var18.getValueNode());
               }
            }
         }

      }
   }

   private void resetTag(Class var1, Node var2) {
      Tag var3 = var2.getTag();
      if (var3.matches(var1)) {
         if (Enum.class.isAssignableFrom(var1)) {
            var2.setTag(Tag.STR);
         } else {
            var2.setTag(Tag.MAP);
         }
      }

   }

   protected Set getProperties(Class var1) {
      return this.typeDefinitions.containsKey(var1) ? ((TypeDescription)this.typeDefinitions.get(var1)).getProperties() : this.getPropertyUtils().getProperties(var1);
   }

   public void setTimeZone(TimeZone var1) {
      super.setTimeZone(var1);
   }

   public TimeZone getTimeZone() {
      return super.getTimeZone();
   }

   public Tag addClassTag(Class var1, Tag var2) {
      return super.addClassTag(var1, var2);
   }

   protected class RepresentJavaBean implements Represent {
      final Representer this$0;

      protected RepresentJavaBean(Representer var1) {
         this.this$0 = var1;
      }

      public Node representData(Object var1) {
         return this.this$0.representJavaBean(this.this$0.getProperties(var1.getClass()), var1);
      }
   }
}
