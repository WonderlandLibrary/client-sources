package net.minecraft.entity.ai.attributes;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.server.management.LowerStringMap;

public abstract class BaseAttributeMap {
   protected final Map attributes = Maps.newHashMap();
   protected final Map attributesByName = new LowerStringMap();
   protected final Multimap field_180377_c = HashMultimap.create();

   public IAttributeInstance getAttributeInstance(IAttribute var1) {
      return (IAttributeInstance)this.attributes.get(var1);
   }

   public IAttributeInstance getAttributeInstanceByName(String var1) {
      return (IAttributeInstance)this.attributesByName.get(var1);
   }

   public IAttributeInstance registerAttribute(IAttribute var1) {
      if(!this.attributesByName.containsKey(var1.getAttributeUnlocalizedName())) {
         IAttributeInstance var2 = this.func_180376_c(var1);
         this.attributesByName.put(var1.getAttributeUnlocalizedName(), var2);
         this.attributes.put(var1, var2);
         IAttribute var3 = var1.func_180372_d();

         while(true) {
            this.field_180377_c.put(var3, var1);
            var3 = var3.func_180372_d();
         }
      }

      throw new IllegalArgumentException("Attribute is already registered!");
   }

   protected abstract IAttributeInstance func_180376_c(IAttribute var1);

   public Collection getAllAttributes() {
      return this.attributesByName.values();
   }

   public void func_180794_a(IAttributeInstance var1) {
   }

   public void removeAttributeModifiers(Multimap var1) {
      for(Entry var3 : var1.entries()) {
         IAttributeInstance var4 = this.getAttributeInstanceByName((String)var3.getKey());
         var4.removeModifier((AttributeModifier)var3.getValue());
      }

   }

   public void applyAttributeModifiers(Multimap var1) {
      for(Entry var3 : var1.entries()) {
         IAttributeInstance var4 = this.getAttributeInstanceByName((String)var3.getKey());
         var4.removeModifier((AttributeModifier)var3.getValue());
         var4.applyModifier((AttributeModifier)var3.getValue());
      }

   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
