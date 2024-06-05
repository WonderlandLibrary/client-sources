package net.minecraft.entity.ai.attributes;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.server.management.LowerStringMap;

public abstract class BaseAttributeMap {
   protected final Multimap field_180377_c = HashMultimap.create();
   protected final Map attributes = Maps.newHashMap();
   private static final String __OBFID = "CL_00001566";
   protected final Map attributesByName = new LowerStringMap();

   public IAttributeInstance getAttributeInstanceByName(String var1) {
      return (IAttributeInstance)this.attributesByName.get(var1);
   }

   public Collection getAllAttributes() {
      return this.attributesByName.values();
   }

   public IAttributeInstance getAttributeInstance(IAttribute var1) {
      return (IAttributeInstance)this.attributes.get(var1);
   }

   public void func_180794_a(IAttributeInstance var1) {
   }

   public IAttributeInstance registerAttribute(IAttribute var1) {
      if (this.attributesByName.containsKey(var1.getAttributeUnlocalizedName())) {
         throw new IllegalArgumentException("Attribute is already registered!");
      } else {
         IAttributeInstance var2 = this.func_180376_c(var1);
         this.attributesByName.put(var1.getAttributeUnlocalizedName(), var2);
         this.attributes.put(var1, var2);

         for(IAttribute var3 = var1.func_180372_d(); var3 != null; var3 = var3.func_180372_d()) {
            this.field_180377_c.put(var3, var1);
         }

         return var2;
      }
   }

   protected abstract IAttributeInstance func_180376_c(IAttribute var1);

   public void applyAttributeModifiers(Multimap var1) {
      Iterator var2 = var1.entries().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         IAttributeInstance var4 = this.getAttributeInstanceByName((String)var3.getKey());
         if (var4 != null) {
            var4.removeModifier((AttributeModifier)var3.getValue());
            var4.applyModifier((AttributeModifier)var3.getValue());
         }
      }

   }

   public void removeAttributeModifiers(Multimap var1) {
      Iterator var2 = var1.entries().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         IAttributeInstance var4 = this.getAttributeInstanceByName((String)var3.getKey());
         if (var4 != null) {
            var4.removeModifier((AttributeModifier)var3.getValue());
         }
      }

   }
}
