package org.yaml.snakeyaml.constructor;

import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.composer.Composer;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.nodes.CollectionNode;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

public abstract class BaseConstructor {
   protected final Map yamlClassConstructors = new EnumMap(NodeId.class);
   protected final Map yamlConstructors = new HashMap();
   protected final Map yamlMultiConstructors = new HashMap();
   protected Composer composer;
   final Map constructedObjects = new HashMap();
   private final Set recursiveObjects = new HashSet();
   private final ArrayList maps2fill = new ArrayList();
   private final ArrayList sets2fill = new ArrayList();
   protected Tag rootTag = null;
   private PropertyUtils propertyUtils;
   private boolean explicitPropertyUtils = false;
   private boolean allowDuplicateKeys = true;
   protected final Map typeDefinitions = new HashMap();
   protected final Map typeTags = new HashMap();

   public BaseConstructor() {
      this.typeDefinitions.put(SortedMap.class, new TypeDescription(SortedMap.class, Tag.OMAP, TreeMap.class));
      this.typeDefinitions.put(SortedSet.class, new TypeDescription(SortedSet.class, Tag.SET, TreeSet.class));
   }

   public void setComposer(Composer var1) {
      this.composer = var1;
   }

   public boolean checkData() {
      return this.composer.checkNode();
   }

   public Object getData() {
      this.composer.checkNode();
      Node var1 = this.composer.getNode();
      if (this.rootTag != null) {
         var1.setTag(this.rootTag);
      }

      return this.constructDocument(var1);
   }

   public Object getSingleData(Class var1) {
      Node var2 = this.composer.getSingleNode();
      if (var2 != null && !Tag.NULL.equals(var2.getTag())) {
         if (Object.class != var1) {
            var2.setTag(new Tag(var1));
         } else if (this.rootTag != null) {
            var2.setTag(this.rootTag);
         }

         return this.constructDocument(var2);
      } else {
         return null;
      }
   }

   protected final Object constructDocument(Node var1) {
      Object var2 = this.constructObject(var1);
      this.fillRecursive();
      this.constructedObjects.clear();
      this.recursiveObjects.clear();
      return var2;
   }

   private void fillRecursive() {
      Iterator var1;
      BaseConstructor.RecursiveTuple var2;
      if (!this.maps2fill.isEmpty()) {
         var1 = this.maps2fill.iterator();

         while(var1.hasNext()) {
            var2 = (BaseConstructor.RecursiveTuple)var1.next();
            BaseConstructor.RecursiveTuple var3 = (BaseConstructor.RecursiveTuple)var2._2();
            ((Map)var2._1()).put(var3._1(), var3._2());
         }

         this.maps2fill.clear();
      }

      if (!this.sets2fill.isEmpty()) {
         var1 = this.sets2fill.iterator();

         while(var1.hasNext()) {
            var2 = (BaseConstructor.RecursiveTuple)var1.next();
            ((Set)var2._1()).add(var2._2());
         }

         this.sets2fill.clear();
      }

   }

   protected Object constructObject(Node var1) {
      return this.constructedObjects.containsKey(var1) ? this.constructedObjects.get(var1) : this.constructObjectNoCheck(var1);
   }

   protected Object constructObjectNoCheck(Node var1) {
      if (this.recursiveObjects.contains(var1)) {
         throw new ConstructorException((String)null, (Mark)null, "found unconstructable recursive node", var1.getStartMark());
      } else {
         this.recursiveObjects.add(var1);
         Construct var2 = this.getConstructor(var1);
         Object var3 = this.constructedObjects.containsKey(var1) ? this.constructedObjects.get(var1) : var2.construct(var1);
         this.finalizeConstruction(var1, var3);
         this.constructedObjects.put(var1, var3);
         this.recursiveObjects.remove(var1);
         if (var1.isTwoStepsConstruction()) {
            var2.construct2ndStep(var1, var3);
         }

         return var3;
      }
   }

   protected Construct getConstructor(Node var1) {
      if (var1.useClassConstructor()) {
         return (Construct)this.yamlClassConstructors.get(var1.getNodeId());
      } else {
         Construct var2 = (Construct)this.yamlConstructors.get(var1.getTag());
         if (var2 == null) {
            Iterator var3 = this.yamlMultiConstructors.keySet().iterator();

            String var4;
            do {
               if (!var3.hasNext()) {
                  return (Construct)this.yamlConstructors.get((Object)null);
               }

               var4 = (String)var3.next();
            } while(!var1.getTag().startsWith(var4));

            return (Construct)this.yamlMultiConstructors.get(var4);
         } else {
            return var2;
         }
      }
   }

   protected Object constructScalar(ScalarNode var1) {
      return var1.getValue();
   }

   protected List createDefaultList(int var1) {
      return new ArrayList(var1);
   }

   protected Set createDefaultSet(int var1) {
      return new LinkedHashSet(var1);
   }

   protected Map createDefaultMap() {
      return new LinkedHashMap();
   }

   protected Set createDefaultSet() {
      return new LinkedHashSet();
   }

   protected Object createArray(Class var1, int var2) {
      return Array.newInstance(var1.getComponentType(), var2);
   }

   protected Object finalizeConstruction(Node var1, Object var2) {
      Class var3 = var1.getType();
      return this.typeDefinitions.containsKey(var3) ? ((TypeDescription)this.typeDefinitions.get(var3)).finalizeConstruction(var2) : var2;
   }

   protected Object newInstance(Node var1) {
      try {
         return this.newInstance(Object.class, var1);
      } catch (InstantiationException var3) {
         throw new YAMLException(var3);
      }
   }

   protected final Object newInstance(Class var1, Node var2) throws InstantiationException {
      return this.newInstance(var1, var2, true);
   }

   protected Object newInstance(Class var1, Node var2, boolean var3) throws InstantiationException {
      Class var4 = var2.getType();
      if (this.typeDefinitions.containsKey(var4)) {
         TypeDescription var5 = (TypeDescription)this.typeDefinitions.get(var4);
         Object var6 = var5.newInstance(var2);
         if (var6 != null) {
            return var6;
         }
      }

      if (var3 && var1.isAssignableFrom(var4) && !Modifier.isAbstract(var4.getModifiers())) {
         try {
            java.lang.reflect.Constructor var9 = var4.getDeclaredConstructor();
            var9.setAccessible(true);
            return var9.newInstance();
         } catch (NoSuchMethodException var7) {
            throw new InstantiationException("NoSuchMethodException:" + var7.getLocalizedMessage());
         } catch (Exception var8) {
            throw new YAMLException(var8);
         }
      } else {
         throw new InstantiationException();
      }
   }

   protected Set newSet(CollectionNode var1) {
      try {
         return (Set)this.newInstance(Set.class, var1);
      } catch (InstantiationException var3) {
         return this.createDefaultSet(var1.getValue().size());
      }
   }

   protected List newList(SequenceNode var1) {
      try {
         return (List)this.newInstance(List.class, var1);
      } catch (InstantiationException var3) {
         return this.createDefaultList(var1.getValue().size());
      }
   }

   protected Map newMap(MappingNode var1) {
      try {
         return (Map)this.newInstance(Map.class, var1);
      } catch (InstantiationException var3) {
         return this.createDefaultMap();
      }
   }

   protected List constructSequence(SequenceNode var1) {
      List var2 = this.newList(var1);
      this.constructSequenceStep2(var1, var2);
      return var2;
   }

   protected Set constructSet(SequenceNode var1) {
      Set var2 = this.newSet(var1);
      this.constructSequenceStep2(var1, var2);
      return var2;
   }

   protected Object constructArray(SequenceNode var1) {
      return this.constructArrayStep2(var1, this.createArray(var1.getType(), var1.getValue().size()));
   }

   protected void constructSequenceStep2(SequenceNode var1, Collection var2) {
      Iterator var3 = var1.getValue().iterator();

      while(var3.hasNext()) {
         Node var4 = (Node)var3.next();
         var2.add(this.constructObject(var4));
      }

   }

   protected Object constructArrayStep2(SequenceNode var1, Object var2) {
      Class var3 = var1.getType().getComponentType();
      int var4 = 0;

      for(Iterator var5 = var1.getValue().iterator(); var5.hasNext(); ++var4) {
         Node var6 = (Node)var5.next();
         if (var6.getType() == Object.class) {
            var6.setType(var3);
         }

         Object var7 = this.constructObject(var6);
         if (var3.isPrimitive()) {
            if (var7 == null) {
               throw new NullPointerException("Unable to construct element value for " + var6);
            }

            if (Byte.TYPE.equals(var3)) {
               Array.setByte(var2, var4, ((Number)var7).byteValue());
            } else if (Short.TYPE.equals(var3)) {
               Array.setShort(var2, var4, ((Number)var7).shortValue());
            } else if (Integer.TYPE.equals(var3)) {
               Array.setInt(var2, var4, ((Number)var7).intValue());
            } else if (Long.TYPE.equals(var3)) {
               Array.setLong(var2, var4, ((Number)var7).longValue());
            } else if (Float.TYPE.equals(var3)) {
               Array.setFloat(var2, var4, ((Number)var7).floatValue());
            } else if (Double.TYPE.equals(var3)) {
               Array.setDouble(var2, var4, ((Number)var7).doubleValue());
            } else if (Character.TYPE.equals(var3)) {
               Array.setChar(var2, var4, (Character)var7);
            } else {
               if (!Boolean.TYPE.equals(var3)) {
                  throw new YAMLException("unexpected primitive type");
               }

               Array.setBoolean(var2, var4, (Boolean)var7);
            }
         } else {
            Array.set(var2, var4, var7);
         }
      }

      return var2;
   }

   protected Set constructSet(MappingNode var1) {
      Set var2 = this.newSet(var1);
      this.constructSet2ndStep(var1, var2);
      return var2;
   }

   protected Map constructMapping(MappingNode var1) {
      Map var2 = this.newMap(var1);
      this.constructMapping2ndStep(var1, var2);
      return var2;
   }

   protected void constructMapping2ndStep(MappingNode var1, Map var2) {
      List var3 = var1.getValue();
      Iterator var4 = var3.iterator();

      while(var4.hasNext()) {
         NodeTuple var5 = (NodeTuple)var4.next();
         Node var6 = var5.getKeyNode();
         Node var7 = var5.getValueNode();
         Object var8 = this.constructObject(var6);
         if (var8 != null) {
            try {
               var8.hashCode();
            } catch (Exception var10) {
               throw new ConstructorException("while constructing a mapping", var1.getStartMark(), "found unacceptable key " + var8, var5.getKeyNode().getStartMark(), var10);
            }
         }

         Object var9 = this.constructObject(var7);
         if (var6.isTwoStepsConstruction()) {
            this.maps2fill.add(0, new BaseConstructor.RecursiveTuple(var2, new BaseConstructor.RecursiveTuple(var8, var9)));
         } else {
            var2.put(var8, var9);
         }
      }

   }

   protected void constructSet2ndStep(MappingNode var1, Set var2) {
      List var3 = var1.getValue();
      Iterator var4 = var3.iterator();

      while(var4.hasNext()) {
         NodeTuple var5 = (NodeTuple)var4.next();
         Node var6 = var5.getKeyNode();
         Object var7 = this.constructObject(var6);
         if (var7 != null) {
            try {
               var7.hashCode();
            } catch (Exception var9) {
               throw new ConstructorException("while constructing a Set", var1.getStartMark(), "found unacceptable key " + var7, var5.getKeyNode().getStartMark(), var9);
            }
         }

         if (var6.isTwoStepsConstruction()) {
            this.sets2fill.add(0, new BaseConstructor.RecursiveTuple(var2, var7));
         } else {
            var2.add(var7);
         }
      }

   }

   public void setPropertyUtils(PropertyUtils var1) {
      this.propertyUtils = var1;
      this.explicitPropertyUtils = true;
      Collection var2 = this.typeDefinitions.values();
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         TypeDescription var4 = (TypeDescription)var3.next();
         var4.setPropertyUtils(var1);
      }

   }

   public final PropertyUtils getPropertyUtils() {
      if (this.propertyUtils == null) {
         this.propertyUtils = new PropertyUtils();
      }

      return this.propertyUtils;
   }

   public TypeDescription addTypeDescription(TypeDescription var1) {
      if (var1 == null) {
         throw new NullPointerException("TypeDescription is required.");
      } else {
         Tag var2 = var1.getTag();
         this.typeTags.put(var2, var1.getType());
         var1.setPropertyUtils(this.getPropertyUtils());
         return (TypeDescription)this.typeDefinitions.put(var1.getType(), var1);
      }
   }

   public final boolean isExplicitPropertyUtils() {
      return this.explicitPropertyUtils;
   }

   public boolean isAllowDuplicateKeys() {
      return this.allowDuplicateKeys;
   }

   public void setAllowDuplicateKeys(boolean var1) {
      this.allowDuplicateKeys = var1;
   }

   private static class RecursiveTuple {
      private final Object _1;
      private final Object _2;

      public RecursiveTuple(Object var1, Object var2) {
         this._1 = var1;
         this._2 = var2;
      }

      public Object _2() {
         return this._2;
      }

      public Object _1() {
         return this._1;
      }
   }
}
