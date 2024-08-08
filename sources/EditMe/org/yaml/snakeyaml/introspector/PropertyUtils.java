package org.yaml.snakeyaml.introspector;

import java.beans.FeatureDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.util.PlatformFeatureDetector;

public class PropertyUtils {
   private static final Logger log = Logger.getLogger(PropertyUtils.class.getPackage().getName());
   private final Map propertiesCache;
   private final Map readableProperties;
   private BeanAccess beanAccess;
   private boolean allowReadOnlyProperties;
   private boolean skipMissingProperties;
   private PlatformFeatureDetector platformFeatureDetector;
   private boolean transientMethodChecked;
   private Method isTransientMethod;

   public PropertyUtils() {
      this(new PlatformFeatureDetector());
   }

   PropertyUtils(PlatformFeatureDetector var1) {
      this.propertiesCache = new HashMap();
      this.readableProperties = new HashMap();
      this.beanAccess = BeanAccess.DEFAULT;
      this.allowReadOnlyProperties = false;
      this.skipMissingProperties = false;
      this.platformFeatureDetector = var1;
      if (var1.isRunningOnAndroid()) {
         this.beanAccess = BeanAccess.FIELD;
      }

   }

   protected Map getPropertiesMap(Class var1, BeanAccess var2) {
      if (this.propertiesCache.containsKey(var1)) {
         return (Map)this.propertiesCache.get(var1);
      } else {
         LinkedHashMap var3;
         boolean var4;
         var3 = new LinkedHashMap();
         var4 = false;
         Class var5;
         Field[] var6;
         int var7;
         int var8;
         Field var9;
         int var10;
         label115:
         switch(var2) {
         case FIELD:
            var5 = var1;

            while(true) {
               if (var5 == null) {
                  break label115;
               }

               var6 = var5.getDeclaredFields();
               var7 = var6.length;

               for(var8 = 0; var8 < var7; ++var8) {
                  var9 = var6[var8];
                  var10 = var9.getModifiers();
                  if (!Modifier.isStatic(var10) && !Modifier.isTransient(var10) && !var3.containsKey(var9.getName())) {
                     var3.put(var9.getName(), new FieldProperty(var9));
                  }
               }

               var5 = var5.getSuperclass();
            }
         default:
            try {
               PropertyDescriptor[] var12 = Introspector.getBeanInfo(var1).getPropertyDescriptors();
               int var13 = var12.length;

               for(var7 = 0; var7 < var13; ++var7) {
                  PropertyDescriptor var14 = var12[var7];
                  Method var15 = var14.getReadMethod();
                  if ((var15 == null || !var15.getName().equals("getClass")) && !this.isTransient(var14)) {
                     var3.put(var14.getName(), new MethodProperty(var14));
                  }
               }
            } catch (IntrospectionException var11) {
               throw new YAMLException(var11);
            }

            for(var5 = var1; var5 != null; var5 = var5.getSuperclass()) {
               var6 = var5.getDeclaredFields();
               var7 = var6.length;

               for(var8 = 0; var8 < var7; ++var8) {
                  var9 = var6[var8];
                  var10 = var9.getModifiers();
                  if (!Modifier.isStatic(var10) && !Modifier.isTransient(var10)) {
                     if (Modifier.isPublic(var10)) {
                        var3.put(var9.getName(), new FieldProperty(var9));
                     } else {
                        var4 = true;
                     }
                  }
               }
            }
         }

         if (var3.isEmpty() && var4) {
            throw new YAMLException("No JavaBean properties found in " + var1.getName());
         } else {
            this.propertiesCache.put(var1, var3);
            return var3;
         }
      }
   }

   private boolean isTransient(FeatureDescriptor var1) {
      if (!this.transientMethodChecked) {
         this.transientMethodChecked = true;

         try {
            this.isTransientMethod = FeatureDescriptor.class.getDeclaredMethod("isTransient");
            this.isTransientMethod.setAccessible(true);
         } catch (NoSuchMethodException var6) {
            log.fine("NoSuchMethod: FeatureDescriptor.isTransient(). Don't check it anymore.");
         } catch (SecurityException var7) {
            var7.printStackTrace();
            this.isTransientMethod = null;
         }
      }

      if (this.isTransientMethod != null) {
         try {
            return Boolean.TRUE.equals(this.isTransientMethod.invoke(var1));
         } catch (IllegalAccessException var3) {
            var3.printStackTrace();
         } catch (IllegalArgumentException var4) {
            var4.printStackTrace();
         } catch (InvocationTargetException var5) {
            var5.printStackTrace();
         }

         this.isTransientMethod = null;
      }

      return false;
   }

   public Set getProperties(Class var1) {
      return this.getProperties(var1, this.beanAccess);
   }

   public Set getProperties(Class var1, BeanAccess var2) {
      if (this.readableProperties.containsKey(var1)) {
         return (Set)this.readableProperties.get(var1);
      } else {
         Set var3 = this.createPropertySet(var1, var2);
         this.readableProperties.put(var1, var3);
         return var3;
      }
   }

   protected Set createPropertySet(Class var1, BeanAccess var2) {
      TreeSet var3 = new TreeSet();
      Collection var4 = this.getPropertiesMap(var1, var2).values();
      Iterator var5 = var4.iterator();

      while(true) {
         Property var6;
         do {
            do {
               if (!var5.hasNext()) {
                  return var3;
               }

               var6 = (Property)var5.next();
            } while(!var6.isReadable());
         } while(!this.allowReadOnlyProperties && !var6.isWritable());

         var3.add(var6);
      }
   }

   public Property getProperty(Class var1, String var2) {
      return this.getProperty(var1, var2, this.beanAccess);
   }

   public Property getProperty(Class var1, String var2, BeanAccess var3) {
      Map var4 = this.getPropertiesMap(var1, var3);
      Object var5 = (Property)var4.get(var2);
      if (var5 == null && this.skipMissingProperties) {
         var5 = new MissingProperty(var2);
      }

      if (var5 == null) {
         throw new YAMLException("Unable to find property '" + var2 + "' on class: " + var1.getName());
      } else {
         return (Property)var5;
      }
   }

   public void setBeanAccess(BeanAccess var1) {
      if (this.platformFeatureDetector.isRunningOnAndroid() && var1 != BeanAccess.FIELD) {
         throw new IllegalArgumentException("JVM is Android - only BeanAccess.FIELD is available");
      } else {
         if (this.beanAccess != var1) {
            this.beanAccess = var1;
            this.propertiesCache.clear();
            this.readableProperties.clear();
         }

      }
   }

   public void setAllowReadOnlyProperties(boolean var1) {
      if (this.allowReadOnlyProperties != var1) {
         this.allowReadOnlyProperties = var1;
         this.readableProperties.clear();
      }

   }

   public boolean isAllowReadOnlyProperties() {
      return this.allowReadOnlyProperties;
   }

   public void setSkipMissingProperties(boolean var1) {
      if (this.skipMissingProperties != var1) {
         this.skipMissingProperties = var1;
         this.readableProperties.clear();
      }

   }

   public boolean isSkipMissingProperties() {
      return this.skipMissingProperties;
   }
}
