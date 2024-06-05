package cc.novoline.modules;

import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.configurations.holder.ModuleHolder;
import cc.novoline.modules.exceptions.UnregisteredModuleException;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ModuleArrayMap extends Object2ObjectArrayMap {
   public ModuleArrayMap() {
   }

   public ModuleArrayMap(Object2ObjectMap var1) {
      super(var1);
   }

   public ModuleArrayMap(int var1) {
      super(var1);
   }

   @NotNull
   public AbstractModule getByClass(@NotNull Class var1) {
      return (AbstractModule)this.entrySet().stream().filter(ModuleArrayMap::lambda$getByClass$0).findFirst().map(Entry::getValue).map(ModuleHolder::getModule).orElseThrow(ModuleArrayMap::lambda$getByClass$1);
   }

   @NotNull
   public List getByCategory(EnumModuleType var1) {
      return (List)this.values().stream().filter(ModuleArrayMap::lambda$getByCategory$2).map(ModuleHolder::getModule).collect(Collectors.toCollection(ObjectArrayList::<init>));
   }

   @NotNull
   public ModuleHolder getHolderByClass(@NotNull Class var1) {
      return (ModuleHolder)this.entrySet().stream().filter(ModuleArrayMap::lambda$getHolderByClass$3).findFirst().map(Entry::getValue).orElseThrow(ModuleArrayMap::lambda$getHolderByClass$4);
   }

   @Nullable
   public AbstractModule getByName(@Nullable String var1, boolean var2) {
      int[] var3 = AbstractModule.d();
      if(StringUtils.isBlank(var1)) {
         return null;
      } else {
         Stream var4 = this.values().stream();
         var4 = var4.filter(ModuleArrayMap::lambda$getByName$5);
         var4 = var4.filter(ModuleArrayMap::lambda$getByName$6);
         return (AbstractModule)var4.findAny().map(ModuleHolder::getModule).orElse((Object)null);
      }
   }

   @Nullable
   public AbstractModule getByNameIgnoreCase(@Nullable String var1) {
      return this.getByName(var1, true);
   }

   private static boolean lambda$getByName$6(String var0, ModuleHolder var1) {
      return var1.getModule().getName().equals(var0);
   }

   private static boolean lambda$getByName$5(String var0, ModuleHolder var1) {
      return var1.getModule().getName().equalsIgnoreCase(var0);
   }

   private static UnregisteredModuleException lambda$getHolderByClass$4(Class var0) {
      return new UnregisteredModuleException("Module " + var0.getCanonicalName() + " is not registered!");
   }

   private static boolean lambda$getHolderByClass$3(Class var0, Entry var1) {
      return ((ModuleHolder)var1.getValue()).getModule().getClass() == var0;
   }

   private static boolean lambda$getByCategory$2(EnumModuleType var0, ModuleHolder var1) {
      return var1.getModule().getType().equals(var0);
   }

   private static UnregisteredModuleException lambda$getByClass$1(Class var0) {
      return new UnregisteredModuleException("Module " + var0.getCanonicalName() + " is not registered!");
   }

   private static boolean lambda$getByClass$0(Class var0, Entry var1) {
      return ((ModuleHolder)var1.getValue()).getModule().getClass() == var0;
   }

   private static UnregisteredModuleException a(UnregisteredModuleException var0) {
      return var0;
   }
}
