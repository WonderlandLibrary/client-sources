package ninja.leaping.configurate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.reflect.TypeToken;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import net.X9;
import net.acE;
import ninja.leaping.configurate.ConfigValue;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.ListConfigValue;
import ninja.leaping.configurate.MapConfigValue;
import ninja.leaping.configurate.NullConfigValue;
import ninja.leaping.configurate.ValueType;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public class SimpleConfigurationNode implements ConfigurationNode {
   private final ConfigurationOptions options;
   volatile boolean attached;
   volatile Object key;
   private SimpleConfigurationNode parent;
   private volatile ConfigValue value;

   public static SimpleConfigurationNode root() {
      return root(ConfigurationOptions.defaults());
   }

   public static SimpleConfigurationNode root(ConfigurationOptions var0) {
      return new SimpleConfigurationNode((Object)null, (SimpleConfigurationNode)null, var0);
   }

   protected SimpleConfigurationNode(Object var1, SimpleConfigurationNode var2, ConfigurationOptions var3) {
      ValueType.b();
      Objects.requireNonNull(var3, "options");
      this.key = var1;
      this.options = var3;
      this.parent = var2;
      this.value = new NullConfigValue(this);
      if(var2 == null) {
         this.attached = true;
      }

   }

   protected SimpleConfigurationNode(SimpleConfigurationNode var1, SimpleConfigurationNode var2) {
      this.options = var2.options;
      this.attached = true;
      this.key = var2.key;
      this.parent = var1;
      this.value = var2.value.copy(this);
   }

   private Object storeDefault(Object var1) {
      acE[] var2 = ValueType.b();
      if(var1 != null && this.getOptions().shouldCopyDefaults()) {
         this.setValue(var1);
      }

      return var1;
   }

   private Object storeDefault(TypeToken var1, Object var2) throws X9 {
      acE[] var3 = ValueType.b();
      if(var2 != null && this.getOptions().shouldCopyDefaults()) {
         this.a(var1, var2);
      }

      return var2;
   }

   public Object getValue(Object var1) {
      ValueType.b();
      Object var3 = this.value.getValue();
      return var3 == null?this.storeDefault(var1):var3;
   }

   public Object getValue(Supplier var1) {
      ValueType.b();
      Object var3 = this.value.getValue();
      return var3 == null?this.storeDefault(var1.get()):var3;
   }

   public Object getValue(Function var1, Object var2) {
      ValueType.b();
      Object var4 = var1.apply(this.getValue());
      return var4 == null?this.storeDefault(var2):var4;
   }

   public Object getValue(Function var1, Supplier var2) {
      ValueType.b();
      Object var4 = var1.apply(this.getValue());
      return var4 == null?this.storeDefault(var2.get()):var4;
   }

   public List getList(Function var1) {
      ValueType.b();
      Builder var3 = ImmutableList.builder();
      ConfigValue var4 = this.value;
      if(var4 instanceof ListConfigValue) {
         Iterator var5 = var4.iterateChildren().iterator();
         if(var5.hasNext()) {
            SimpleConfigurationNode var6 = (SimpleConfigurationNode)var5.next();
            Object var7 = var1.apply(var6.getValue());
            var3.add(var7);
         }
      }

      Object var8 = var1.apply(var4.getValue());
      var3.add(var8);
      return var3.build();
   }

   public List getList(Function var1, List var2) {
      ValueType.b();
      List var4 = this.getList(var1);
      return var4.isEmpty()?(List)this.storeDefault(var2):var4;
   }

   public List getList(Function var1, Supplier var2) {
      ValueType.b();
      List var4 = this.getList(var1);
      return var4.isEmpty()?(List)this.storeDefault(var2.get()):var4;
   }

   public List getList(TypeToken var1, List var2) throws X9 {
      ValueType.b();
      List var4 = (List)this.getValue((TypeToken)var1, (Object)var2);
      return var4.isEmpty()?(List)this.storeDefault(var2):var4;
   }

   public List getList(TypeToken var1, Supplier var2) throws X9 {
      ValueType.b();
      List var4 = (List)this.getValue(var1, var2);
      return var4.isEmpty()?(List)this.storeDefault(var2.get()):var4;
   }

   public Object getValue(TypeToken var1, Object var2) throws X9 {
      ValueType.b();
      Object var4 = this.getValue();
      if(var4 == null) {
         return this.storeDefault(var1, var2);
      } else {
         TypeSerializer var5 = this.getOptions().f().a(var1);
         return var5 == null?(var1.getRawType().isInstance(var4)?var1.getRawType().cast(var4):this.storeDefault(var1, var2)):var5.deserialize(var1, this);
      }
   }

   public Object getValue(TypeToken var1, Supplier var2) throws X9 {
      ValueType.b();
      Object var4 = this.getValue();
      if(var4 == null) {
         return this.storeDefault(var1, var2.get());
      } else {
         TypeSerializer var5 = this.getOptions().f().a(var1);
         return var5 == null?(var1.getRawType().isInstance(var4)?var1.getRawType().cast(var4):this.storeDefault(var1, var2.get())):var5.deserialize(var1, this);
      }
   }

   public SimpleConfigurationNode setValue(Object param1) {
      // $FF: Couldn't be decompiled
   }

   private void insertNewValue(Object param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public ConfigurationNode mergeValuesFrom(ConfigurationNode param1) {
      // $FF: Couldn't be decompiled
   }

   public SimpleConfigurationNode getNode(Object... var1) {
      ValueType.b();
      SimpleConfigurationNode var3 = this;
      int var5 = var1.length;
      int var6 = 0;
      if(var6 < var5) {
         Object var7 = var1[var6];
         var3 = this.getChild(var7, false);
         ++var6;
      }

      return var3;
   }

   public boolean isVirtual() {
      acE[] var1 = ValueType.b();
      return !this.attached;
   }

   public ValueType getValueType() {
      return this.value.getType();
   }

   public List getChildrenList() {
      ValueType.b();
      ConfigValue var2 = this.value;
      return (List)(var2 instanceof ListConfigValue?ImmutableList.copyOf((Collection)((ListConfigValue)var2).values.get()):Collections.emptyList());
   }

   public Map getChildrenMap() {
      ValueType.b();
      ConfigValue var2 = this.value;
      return (Map)(var2 instanceof MapConfigValue?ImmutableMap.copyOf(((MapConfigValue)var2).values):Collections.emptyMap());
   }

   protected SimpleConfigurationNode getChild(Object var1, boolean var2) {
      ValueType.b();
      SimpleConfigurationNode var4 = this.value.getChild(var1);
      if(var4 == null) {
         this.attachIfNecessary();
         SimpleConfigurationNode var5 = this.value.putChildIfAbsent(var1, var4 = this.createNode(var1));
         if(var5 != null) {
            var4 = var5;
         }

         this.attachChild(var4);
         var4 = this.createNode(var1);
      }

      return var4;
   }

   public boolean removeChild(Object var1) {
      return detachIfNonNull(this.value.putChild(var1, (SimpleConfigurationNode)null)) != null;
   }

   private static SimpleConfigurationNode detachIfNonNull(SimpleConfigurationNode var0) {
      acE[] var1 = ValueType.b();
      if(var0 != null) {
         var0.attached = false;
         var0.clear();
      }

      return var0;
   }

   public SimpleConfigurationNode getAppendedNode() {
      return this.getChild(Integer.valueOf(-1), false);
   }

   public Object getKey() {
      return this.key;
   }

   public Object[] getPath() {
      ValueType.b();
      LinkedList var2 = new LinkedList();
      Object var3 = this;
      if(this.getParent() == null) {
         return new Object[]{this.getKey()};
      } else {
         while(true) {
            var2.addFirst(((ConfigurationNode)var3).getKey());
            if(((ConfigurationNode)(var3 = ((ConfigurationNode)var3).getParent())).getParent() == null) {
               break;
            }
         }

         return var2.toArray();
      }
   }

   public SimpleConfigurationNode getParent() {
      return this.parent;
   }

   public ConfigurationOptions getOptions() {
      return this.options;
   }

   public SimpleConfigurationNode copy() {
      return this.copy((SimpleConfigurationNode)null);
   }

   protected SimpleConfigurationNode copy(SimpleConfigurationNode var1) {
      return new SimpleConfigurationNode(var1, this);
   }

   SimpleConfigurationNode getParentEnsureAttached() {
      ValueType.b();
      SimpleConfigurationNode var2 = this.parent;
      if(var2.isVirtual()) {
         var2 = var2.getParentEnsureAttached().attachChildIfAbsent(var2);
      }

      return this.parent = var2;
   }

   protected void attachIfNecessary() {
      acE[] var1 = ValueType.b();
      if(!this.attached) {
         this.getParentEnsureAttached().attachChild(this);
      }

   }

   protected SimpleConfigurationNode createNode(Object var1) {
      return new SimpleConfigurationNode(var1, this, this.options);
   }

   protected SimpleConfigurationNode attachChildIfAbsent(SimpleConfigurationNode var1) {
      return this.attachChild(var1, true);
   }

   private void attachChild(SimpleConfigurationNode var1) {
      this.attachChild(var1, false);
   }

   private SimpleConfigurationNode attachChild(SimpleConfigurationNode param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   protected void clear() {
      // $FF: Couldn't be decompiled
   }

   public boolean equals(Object var1) {
      acE[] var2 = ValueType.b();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof SimpleConfigurationNode)) {
         return false;
      } else {
         SimpleConfigurationNode var3 = (SimpleConfigurationNode)var1;
         return Objects.equals(this.key, var3.key) && Objects.equals(this.value, var3.value);
      }
   }

   public int hashCode() {
      return Objects.hashCode(this.key) ^ Objects.hashCode(this.value);
   }

   public String toString() {
      return "SimpleConfigurationNode{key=" + this.key + ", value=" + this.value + '}';
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
