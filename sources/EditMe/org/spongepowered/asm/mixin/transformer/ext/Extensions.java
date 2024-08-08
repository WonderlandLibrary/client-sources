package org.spongepowered.asm.mixin.transformer.ext;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.MixinTransformer;

public final class Extensions {
   private final MixinTransformer transformer;
   private final List extensions = new ArrayList();
   private final Map extensionMap = new HashMap();
   private final List generators = new ArrayList();
   private final List generatorsView;
   private final Map generatorMap;
   private List activeExtensions;

   public Extensions(MixinTransformer var1) {
      this.generatorsView = Collections.unmodifiableList(this.generators);
      this.generatorMap = new HashMap();
      this.activeExtensions = Collections.emptyList();
      this.transformer = var1;
   }

   public MixinTransformer getTransformer() {
      return this.transformer;
   }

   public void add(IExtension var1) {
      this.extensions.add(var1);
      this.extensionMap.put(var1.getClass(), var1);
   }

   public List getExtensions() {
      return Collections.unmodifiableList(this.extensions);
   }

   public List getActiveExtensions() {
      return this.activeExtensions;
   }

   public IExtension getExtension(Class var1) {
      return (IExtension)lookup(var1, this.extensionMap, this.extensions);
   }

   public void select(MixinEnvironment var1) {
      Builder var2 = ImmutableList.builder();
      Iterator var3 = this.extensions.iterator();

      while(var3.hasNext()) {
         IExtension var4 = (IExtension)var3.next();
         if (var4.checkActive(var1)) {
            var2.add(var4);
         }
      }

      this.activeExtensions = var2.build();
   }

   public void preApply(ITargetClassContext var1) {
      Iterator var2 = this.activeExtensions.iterator();

      while(var2.hasNext()) {
         IExtension var3 = (IExtension)var2.next();
         var3.preApply(var1);
      }

   }

   public void postApply(ITargetClassContext var1) {
      Iterator var2 = this.activeExtensions.iterator();

      while(var2.hasNext()) {
         IExtension var3 = (IExtension)var2.next();
         var3.postApply(var1);
      }

   }

   public void export(MixinEnvironment var1, String var2, boolean var3, byte[] var4) {
      Iterator var5 = this.activeExtensions.iterator();

      while(var5.hasNext()) {
         IExtension var6 = (IExtension)var5.next();
         var6.export(var1, var2, var3, var4);
      }

   }

   public void add(IClassGenerator var1) {
      this.generators.add(var1);
      this.generatorMap.put(var1.getClass(), var1);
   }

   public List getGenerators() {
      return this.generatorsView;
   }

   public IClassGenerator getGenerator(Class var1) {
      return (IClassGenerator)lookup(var1, this.generatorMap, this.generators);
   }

   private static Object lookup(Class var0, Map var1, List var2) {
      Object var3 = var1.get(var0);
      if (var3 == null) {
         Iterator var4 = var2.iterator();

         while(var4.hasNext()) {
            Object var5 = var4.next();
            if (var0.isAssignableFrom(var5.getClass())) {
               var3 = var5;
               break;
            }
         }

         if (var3 == null) {
            throw new IllegalArgumentException("Extension for <" + var0.getName() + "> could not be found");
         }

         var1.put(var0, var3);
      }

      return var3;
   }
}
