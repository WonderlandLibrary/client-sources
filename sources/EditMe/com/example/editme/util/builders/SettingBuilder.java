package com.example.editme.util.builders;

import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsRegister;
import com.google.common.base.MoreObjects;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public abstract class SettingBuilder {
   protected Object initialValue;
   protected List predicateList = new ArrayList();
   protected BiConsumer consumer;
   protected String name;
   private Predicate visibilityPredicate;

   protected Predicate visibilityPredicate() {
      return (Predicate)MoreObjects.firstNonNull(this.visibilityPredicate, SettingBuilder::lambda$visibilityPredicate$3);
   }

   public final Setting buildAndRegister(String var1) {
      return register(this.build(), var1);
   }

   public SettingBuilder withValue(Object var1) {
      this.initialValue = var1;
      return this;
   }

   public SettingBuilder withName(String var1) {
      this.name = var1;
      return this;
   }

   public abstract Setting build();

   private boolean lambda$predicate$2(Object var1) {
      return this.predicateList.stream().allMatch(SettingBuilder::lambda$null$1);
   }

   public SettingBuilder withConsumer(BiConsumer var1) {
      this.consumer = var1;
      return this;
   }

   public SettingBuilder withRestriction(Predicate var1) {
      this.predicateList.add(var1);
      return this;
   }

   private static void lambda$consumer$4(Object var0, Object var1) {
   }

   protected Predicate predicate() {
      return this.predicateList.isEmpty() ? SettingBuilder::lambda$predicate$0 : this::lambda$predicate$2;
   }

   public SettingBuilder withVisibility(Predicate var1) {
      this.visibilityPredicate = var1;
      return this;
   }

   public static Setting register(Setting var0, String var1) {
      String var2 = var0.getName();
      if (var2 != null && !var2.isEmpty()) {
         SettingsRegister.register(String.valueOf((new StringBuilder()).append(var1).append(".").append(var2)), var0);
         return var0;
      } else {
         throw new RuntimeException("Can't register nameless setting");
      }
   }

   private static boolean lambda$null$1(Object var0, Predicate var1) {
      return var1.test(var0);
   }

   private static boolean lambda$visibilityPredicate$3(Object var0) {
      return true;
   }

   private static boolean lambda$predicate$0(Object var0) {
      return true;
   }

   protected BiConsumer consumer() {
      return (BiConsumer)MoreObjects.firstNonNull(this.consumer, SettingBuilder::lambda$consumer$4);
   }
}
