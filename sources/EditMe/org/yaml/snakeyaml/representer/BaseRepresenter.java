package org.yaml.snakeyaml.representer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.nodes.AnchorNode;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

public abstract class BaseRepresenter {
   protected final Map representers = new HashMap();
   protected Represent nullRepresenter;
   protected final Map multiRepresenters = new LinkedHashMap();
   protected Character defaultScalarStyle;
   protected DumperOptions.FlowStyle defaultFlowStyle;
   protected final Map representedObjects;
   protected Object objectToRepresent;
   private PropertyUtils propertyUtils;
   private boolean explicitPropertyUtils;

   public BaseRepresenter() {
      this.defaultFlowStyle = DumperOptions.FlowStyle.AUTO;
      this.representedObjects = new IdentityHashMap(this) {
         private static final long serialVersionUID = -5576159264232131854L;
         final BaseRepresenter this$0;

         {
            this.this$0 = var1;
         }

         public Node put(Object var1, Node var2) {
            return (Node)super.put(var1, new AnchorNode(var2));
         }

         public Object put(Object var1, Object var2) {
            return this.put(var1, (Node)var2);
         }
      };
      this.explicitPropertyUtils = false;
   }

   public Node represent(Object var1) {
      Node var2 = this.representData(var1);
      this.representedObjects.clear();
      this.objectToRepresent = null;
      return var2;
   }

   protected final Node representData(Object var1) {
      this.objectToRepresent = var1;
      Node var2;
      if (this.representedObjects.containsKey(this.objectToRepresent)) {
         var2 = (Node)this.representedObjects.get(this.objectToRepresent);
         return var2;
      } else if (var1 == null) {
         var2 = this.nullRepresenter.representData((Object)null);
         return var2;
      } else {
         Class var3 = var1.getClass();
         Represent var4;
         if (this.representers.containsKey(var3)) {
            var4 = (Represent)this.representers.get(var3);
            var2 = var4.representData(var1);
         } else {
            Iterator var7 = this.multiRepresenters.keySet().iterator();

            while(var7.hasNext()) {
               Class var5 = (Class)var7.next();
               if (var5 != null && var5.isInstance(var1)) {
                  Represent var6 = (Represent)this.multiRepresenters.get(var5);
                  var2 = var6.representData(var1);
                  return var2;
               }
            }

            if (this.multiRepresenters.containsKey((Object)null)) {
               var4 = (Represent)this.multiRepresenters.get((Object)null);
               var2 = var4.representData(var1);
            } else {
               var4 = (Represent)this.representers.get((Object)null);
               var2 = var4.representData(var1);
            }
         }

         return var2;
      }
   }

   protected Node representScalar(Tag var1, String var2, Character var3) {
      if (var3 == null) {
         var3 = this.defaultScalarStyle;
      }

      ScalarNode var4 = new ScalarNode(var1, var2, (Mark)null, (Mark)null, var3);
      return var4;
   }

   protected Node representScalar(Tag var1, String var2) {
      return this.representScalar(var1, var2, (Character)null);
   }

   protected Node representSequence(Tag var1, Iterable var2, Boolean var3) {
      int var4 = 10;
      if (var2 instanceof List) {
         var4 = ((List)var2).size();
      }

      ArrayList var5 = new ArrayList(var4);
      SequenceNode var6 = new SequenceNode(var1, var5, var3);
      this.representedObjects.put(this.objectToRepresent, var6);
      boolean var7 = true;

      Node var10;
      for(Iterator var8 = var2.iterator(); var8.hasNext(); var5.add(var10)) {
         Object var9 = var8.next();
         var10 = this.representData(var9);
         if (!(var10 instanceof ScalarNode) || ((ScalarNode)var10).getStyle() != null) {
            var7 = false;
         }
      }

      if (var3 == null) {
         if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
            var6.setFlowStyle(this.defaultFlowStyle.getStyleBoolean());
         } else {
            var6.setFlowStyle(var7);
         }
      }

      return var6;
   }

   protected Node representMapping(Tag var1, Map var2, Boolean var3) {
      ArrayList var4 = new ArrayList(var2.size());
      MappingNode var5 = new MappingNode(var1, var4, var3);
      this.representedObjects.put(this.objectToRepresent, var5);
      boolean var6 = true;

      Node var9;
      Node var10;
      for(Iterator var7 = var2.entrySet().iterator(); var7.hasNext(); var4.add(new NodeTuple(var9, var10))) {
         Entry var8 = (Entry)var7.next();
         var9 = this.representData(var8.getKey());
         var10 = this.representData(var8.getValue());
         if (!(var9 instanceof ScalarNode) || ((ScalarNode)var9).getStyle() != null) {
            var6 = false;
         }

         if (!(var10 instanceof ScalarNode) || ((ScalarNode)var10).getStyle() != null) {
            var6 = false;
         }
      }

      if (var3 == null) {
         if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
            var5.setFlowStyle(this.defaultFlowStyle.getStyleBoolean());
         } else {
            var5.setFlowStyle(var6);
         }
      }

      return var5;
   }

   public void setDefaultScalarStyle(DumperOptions.ScalarStyle var1) {
      this.defaultScalarStyle = var1.getChar();
   }

   public DumperOptions.ScalarStyle getDefaultScalarStyle() {
      return DumperOptions.ScalarStyle.createStyle(this.defaultScalarStyle);
   }

   public void setDefaultFlowStyle(DumperOptions.FlowStyle var1) {
      this.defaultFlowStyle = var1;
   }

   public DumperOptions.FlowStyle getDefaultFlowStyle() {
      return this.defaultFlowStyle;
   }

   public void setPropertyUtils(PropertyUtils var1) {
      this.propertyUtils = var1;
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
