package com.viaversion.viaversion.util;

import com.google.common.collect.Maps;
import com.viaversion.viaversion.util.Config;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public final class ReflectionUtil$ClassReflection {
   private final Class handle;
   private final Map fields;
   private final Map methods;

   public ReflectionUtil$ClassReflection(Class var1) {
      this(var1, true);
   }

   public ReflectionUtil$ClassReflection(Class var1, boolean var2) {
      this.fields = Maps.newConcurrentMap();
      this.methods = Maps.newConcurrentMap();
      this.handle = var1;
      this.scanFields(var1, var2);
      this.scanMethods(var1, var2);
   }

   private void scanFields(Class var1, boolean var2) {
      String var3 = Config.c();
      if(var1.getSuperclass() != null) {
         this.scanFields(var1.getSuperclass(), true);
      }

      Field[] var4 = var1.getDeclaredFields();
      int var5 = var4.length;
      int var6 = 0;
      if(var6 < var5) {
         Field var7 = var4[var6];
         var7.setAccessible(true);
         this.fields.put(var7.getName(), var7);
         ++var6;
      }

   }

   private void scanMethods(Class var1, boolean var2) {
      String var3 = Config.c();
      if(var1.getSuperclass() != null) {
         this.scanMethods(var1.getSuperclass(), true);
      }

      Method[] var4 = var1.getDeclaredMethods();
      int var5 = var4.length;
      int var6 = 0;
      if(var6 < var5) {
         Method var7 = var4[var6];
         var7.setAccessible(true);
         this.methods.put(var7.getName(), var7);
         ++var6;
      }

   }

   public Object newInstance() throws IllegalAccessException, InstantiationException {
      return this.handle.newInstance();
   }

   public Field getField(String var1) {
      return (Field)this.fields.get(var1);
   }

   public void setFieldValue(String var1, Object var2, Object var3) throws IllegalAccessException {
      this.getField(var1).set(var2, var3);
   }

   public Object getFieldValue(String var1, Object var2, Class var3) throws IllegalAccessException {
      return var3.cast(this.getField(var1).get(var2));
   }

   public Object invokeMethod(Class var1, String var2, Object var3, Object... var4) throws InvocationTargetException, IllegalAccessException {
      return var1.cast(this.getMethod(var2).invoke(var3, var4));
   }

   public Method getMethod(String var1) {
      return (Method)this.methods.get(var1);
   }

   public Collection getFields() {
      return Collections.unmodifiableCollection(this.fields.values());
   }

   public Collection getMethods() {
      return Collections.unmodifiableCollection(this.methods.values());
   }
}
