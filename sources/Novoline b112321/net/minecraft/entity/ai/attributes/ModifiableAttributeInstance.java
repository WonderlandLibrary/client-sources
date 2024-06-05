package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

public class ModifiableAttributeInstance implements IAttributeInstance {
   private final BaseAttributeMap attributeMap;
   private final IAttribute genericAttribute;
   private final Map mapByOperation = Maps.newHashMap();
   private final Map mapByName = Maps.newHashMap();
   private final Map mapByUUID = Maps.newHashMap();
   private double baseValue;
   private boolean needsUpdate = true;
   private double cachedValue;

   public ModifiableAttributeInstance(BaseAttributeMap var1, IAttribute var2) {
      this.attributeMap = var1;
      this.genericAttribute = var2;
      this.baseValue = var2.getDefaultValue();

      for(int var3 = 0; var3 < 3; ++var3) {
         this.mapByOperation.put(Integer.valueOf(var3), Sets.newHashSet());
      }

   }

   public IAttribute getAttribute() {
      return this.genericAttribute;
   }

   public double getBaseValue() {
      return this.baseValue;
   }

   public void setBaseValue(double var1) {
      if(var1 != this.getBaseValue()) {
         this.baseValue = var1;
         this.flagForUpdate();
      }

   }

   public Collection getModifiersByOperation(int var1) {
      return (Collection)this.mapByOperation.get(Integer.valueOf(var1));
   }

   public Collection func_111122_c() {
      HashSet var1 = Sets.newHashSet();

      for(int var2 = 0; var2 < 3; ++var2) {
         var1.addAll(this.getModifiersByOperation(var2));
      }

      return var1;
   }

   public AttributeModifier getModifier(UUID var1) {
      return (AttributeModifier)this.mapByUUID.get(var1);
   }

   public boolean hasModifier(AttributeModifier var1) {
      return this.mapByUUID.get(var1.getID()) != null;
   }

   public void applyModifier(AttributeModifier var1) {
      if(this.getModifier(var1.getID()) != null) {
         throw new IllegalArgumentException("Modifier is already applied on this attribute!");
      } else {
         Set var2 = (Set)this.mapByName.get(var1.getName());
         HashSet var3 = Sets.newHashSet();
         this.mapByName.put(var1.getName(), var3);
         ((Set)this.mapByOperation.get(Integer.valueOf(var1.getOperation()))).add(var1);
         var3.add(var1);
         this.mapByUUID.put(var1.getID(), var1);
         this.flagForUpdate();
      }
   }

   protected void flagForUpdate() {
      this.needsUpdate = true;
      this.attributeMap.func_180794_a(this);
   }

   public void removeModifier(AttributeModifier var1) {
      for(int var2 = 0; var2 < 3; ++var2) {
         Set var3 = (Set)this.mapByOperation.get(Integer.valueOf(var2));
         var3.remove(var1);
      }

      Set var4 = (Set)this.mapByName.get(var1.getName());
      var4.remove(var1);
      if(var4.isEmpty()) {
         this.mapByName.remove(var1.getName());
      }

      this.mapByUUID.remove(var1.getID());
      this.flagForUpdate();
   }

   public void removeAllModifiers() {
      Collection var1 = this.func_111122_c();

      for(AttributeModifier var3 : Lists.newArrayList(var1)) {
         this.removeModifier(var3);
      }

   }

   public double getAttributeValue() {
      if(this.needsUpdate) {
         this.cachedValue = this.computeValue();
         this.needsUpdate = false;
      }

      return this.cachedValue;
   }

   private double computeValue() {
      double var1 = this.getBaseValue();

      for(AttributeModifier var4 : this.func_180375_b(0)) {
         var1 += var4.getAmount();
      }

      double var7 = var1;

      for(AttributeModifier var6 : this.func_180375_b(1)) {
         var7 += var1 * var6.getAmount();
      }

      for(AttributeModifier var9 : this.func_180375_b(2)) {
         var7 *= 1.0D + var9.getAmount();
      }

      return this.genericAttribute.clampValue(var7);
   }

   private Collection func_180375_b(int var1) {
      HashSet var2 = Sets.newHashSet(this.getModifiersByOperation(var1));
      IAttribute var3 = this.genericAttribute.func_180372_d();

      while(true) {
         IAttributeInstance var4 = this.attributeMap.getAttributeInstance(var3);
         var2.addAll(var4.getModifiersByOperation(var1));
         var3 = var3.func_180372_d();
      }
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
