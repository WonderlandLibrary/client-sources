package org.yaml.snakeyaml;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertySubstitute;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

public class TypeDescription {
   private final Class type;
   private Class impl;
   private Tag tag;
   private transient Set dumpProperties;
   private transient PropertyUtils propertyUtils;
   private transient boolean delegatesChecked;
   private Map properties;
   protected Set excludes;
   protected String[] includes;
   protected BeanAccess beanAccess;

   public TypeDescription(Class var1, Tag var2) {
      this(var1, var2, (Class)null);
   }

   public TypeDescription(Class var1, Tag var2, Class var3) {
      this.properties = Collections.emptyMap();
      this.excludes = Collections.emptySet();
      this.includes = null;
      this.type = var1;
      this.tag = var2;
      this.impl = var3;
      this.beanAccess = null;
   }

   public TypeDescription(Class var1, String var2) {
      this(var1, new Tag(var2), (Class)null);
   }

   public TypeDescription(Class var1) {
      this(var1, (Tag)null, (Class)null);
   }

   public TypeDescription(Class var1, Class var2) {
      this(var1, (Tag)null, var2);
   }

   public Tag getTag() {
      return this.tag;
   }

   public void setTag(Tag var1) {
      this.tag = var1;
   }

   public void setTag(String var1) {
      this.setTag(new Tag(var1));
   }

   public Class getType() {
      return this.type;
   }

   /** @deprecated */
   @Deprecated
   public void putListPropertyType(String var1, Class var2) {
      this.addPropertyParameters(var1, var2);
   }

   /** @deprecated */
   @Deprecated
   public Class getListPropertyType(String var1) {
      if (this.properties.containsKey(var1)) {
         Class[] var2 = ((PropertySubstitute)this.properties.get(var1)).getActualTypeArguments();
         if (var2 != null && var2.length > 0) {
            return var2[0];
         }
      }

      return null;
   }

   /** @deprecated */
   @Deprecated
   public void putMapPropertyType(String var1, Class var2, Class var3) {
      this.addPropertyParameters(var1, var2, var3);
   }

   /** @deprecated */
   @Deprecated
   public Class getMapKeyType(String var1) {
      if (this.properties.containsKey(var1)) {
         Class[] var2 = ((PropertySubstitute)this.properties.get(var1)).getActualTypeArguments();
         if (var2 != null && var2.length > 0) {
            return var2[0];
         }
      }

      return null;
   }

   /** @deprecated */
   @Deprecated
   public Class getMapValueType(String var1) {
      if (this.properties.containsKey(var1)) {
         Class[] var2 = ((PropertySubstitute)this.properties.get(var1)).getActualTypeArguments();
         if (var2 != null && var2.length > 1) {
            return var2[1];
         }
      }

      return null;
   }

   public void addPropertyParameters(String var1, Class... var2) {
      if (!this.properties.containsKey(var1)) {
         this.substituteProperty(var1, (Class)null, (String)null, (String)null, var2);
      } else {
         PropertySubstitute var3 = (PropertySubstitute)this.properties.get(var1);
         var3.setActualTypeArguments(var2);
      }

   }

   public String toString() {
      return "TypeDescription for " + this.getType() + " (tag='" + this.getTag() + "')";
   }

   private void checkDelegates() {
      Collection var1 = this.properties.values();
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         PropertySubstitute var3 = (PropertySubstitute)var2.next();

         try {
            var3.setDelegate(this.discoverProperty(var3.getName()));
         } catch (YAMLException var5) {
         }
      }

      this.delegatesChecked = true;
   }

   private Property discoverProperty(String var1) {
      if (this.propertyUtils != null) {
         return this.beanAccess == null ? this.propertyUtils.getProperty(this.type, var1) : this.propertyUtils.getProperty(this.type, var1, this.beanAccess);
      } else {
         return null;
      }
   }

   public Property getProperty(String var1) {
      if (!this.delegatesChecked) {
         this.checkDelegates();
      }

      return this.properties.containsKey(var1) ? (Property)this.properties.get(var1) : this.discoverProperty(var1);
   }

   public void substituteProperty(String var1, Class var2, String var3, String var4, Class... var5) {
      this.substituteProperty(new PropertySubstitute(var1, var2, var3, var4, var5));
   }

   public void substituteProperty(PropertySubstitute var1) {
      if (Collections.EMPTY_MAP == this.properties) {
         this.properties = new LinkedHashMap();
      }

      var1.setTargetType(this.type);
      this.properties.put(var1.getName(), var1);
   }

   public void setPropertyUtils(PropertyUtils var1) {
      this.propertyUtils = var1;
   }

   public void setIncludes(String... var1) {
      this.includes = var1 != null && var1.length > 0 ? var1 : null;
   }

   public void setExcludes(String... var1) {
      if (var1 != null && var1.length > 0) {
         this.excludes = new HashSet();
         String[] var2 = var1;
         int var3 = var1.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String var5 = var2[var4];
            this.excludes.add(var5);
         }
      } else {
         this.excludes = Collections.emptySet();
      }

   }

   public Set getProperties() {
      if (this.dumpProperties != null) {
         return this.dumpProperties;
      } else if (this.propertyUtils != null) {
         if (this.includes != null) {
            this.dumpProperties = new LinkedHashSet();
            String[] var5 = this.includes;
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               String var4 = var5[var7];
               if (!this.excludes.contains(var4)) {
                  this.dumpProperties.add(this.getProperty(var4));
               }
            }

            return this.dumpProperties;
         } else {
            Set var1 = this.beanAccess == null ? this.propertyUtils.getProperties(this.type) : this.propertyUtils.getProperties(this.type, this.beanAccess);
            Iterator var2;
            Property var3;
            if (this.properties.isEmpty()) {
               if (this.excludes.isEmpty()) {
                  return this.dumpProperties = var1;
               } else {
                  this.dumpProperties = new LinkedHashSet();
                  var2 = var1.iterator();

                  while(var2.hasNext()) {
                     var3 = (Property)var2.next();
                     if (!this.excludes.contains(var3.getName())) {
                        this.dumpProperties.add(var3);
                     }
                  }

                  return this.dumpProperties;
               }
            } else {
               if (!this.delegatesChecked) {
                  this.checkDelegates();
               }

               this.dumpProperties = new LinkedHashSet();
               var2 = this.properties.values().iterator();

               while(var2.hasNext()) {
                  var3 = (Property)var2.next();
                  if (!this.excludes.contains(var3.getName()) && var3.isReadable()) {
                     this.dumpProperties.add(var3);
                  }
               }

               var2 = var1.iterator();

               while(var2.hasNext()) {
                  var3 = (Property)var2.next();
                  if (!this.excludes.contains(var3.getName())) {
                     this.dumpProperties.add(var3);
                  }
               }

               return this.dumpProperties;
            }
         }
      } else {
         return null;
      }
   }

   public boolean setupPropertyType(String var1, Node var2) {
      return false;
   }

   public boolean setProperty(Object var1, String var2, Object var3) throws Exception {
      return false;
   }

   public Object newInstance(Node var1) {
      if (this.impl != null) {
         try {
            Constructor var2 = this.impl.getDeclaredConstructor();
            var2.setAccessible(true);
            return var2.newInstance();
         } catch (Exception var3) {
            var3.printStackTrace();
            this.impl = null;
         }
      }

      return null;
   }

   public Object newInstance(String var1, Node var2) {
      return null;
   }

   public Object finalizeConstruction(Object var1) {
      return var1;
   }
}
