/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AttributeModifierManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<Attribute, ModifiableAttributeInstance> instanceMap = Maps.newHashMap();
    private final Set<ModifiableAttributeInstance> instanceSet = Sets.newHashSet();
    private final AttributeModifierMap attributeMap;

    public AttributeModifierManager(AttributeModifierMap attributeModifierMap) {
        this.attributeMap = attributeModifierMap;
    }

    private void addInstance(ModifiableAttributeInstance modifiableAttributeInstance) {
        if (modifiableAttributeInstance.getAttribute().getShouldWatch()) {
            this.instanceSet.add(modifiableAttributeInstance);
        }
    }

    public Set<ModifiableAttributeInstance> getInstances() {
        return this.instanceSet;
    }

    public Collection<ModifiableAttributeInstance> getWatchedInstances() {
        return this.instanceMap.values().stream().filter(AttributeModifierManager::lambda$getWatchedInstances$0).collect(Collectors.toList());
    }

    @Nullable
    public ModifiableAttributeInstance createInstanceIfAbsent(Attribute attribute) {
        return this.instanceMap.computeIfAbsent(attribute, this::lambda$createInstanceIfAbsent$1);
    }

    public boolean hasAttributeInstance(Attribute attribute) {
        return this.instanceMap.get(attribute) != null || this.attributeMap.hasAttribute(attribute);
    }

    public boolean hasModifier(Attribute attribute, UUID uUID) {
        ModifiableAttributeInstance modifiableAttributeInstance = this.instanceMap.get(attribute);
        return modifiableAttributeInstance != null ? modifiableAttributeInstance.getModifier(uUID) != null : this.attributeMap.hasModifier(attribute, uUID);
    }

    public double getAttributeValue(Attribute attribute) {
        ModifiableAttributeInstance modifiableAttributeInstance = this.instanceMap.get(attribute);
        return modifiableAttributeInstance != null ? modifiableAttributeInstance.getValue() : this.attributeMap.getAttributeValue(attribute);
    }

    public double getAttributeBaseValue(Attribute attribute) {
        ModifiableAttributeInstance modifiableAttributeInstance = this.instanceMap.get(attribute);
        return modifiableAttributeInstance != null ? modifiableAttributeInstance.getBaseValue() : this.attributeMap.getAttributeBaseValue(attribute);
    }

    public double getModifierValue(Attribute attribute, UUID uUID) {
        ModifiableAttributeInstance modifiableAttributeInstance = this.instanceMap.get(attribute);
        return modifiableAttributeInstance != null ? modifiableAttributeInstance.getModifier(uUID).getAmount() : this.attributeMap.getAttributeModifierValue(attribute, uUID);
    }

    public void removeModifiers(Multimap<Attribute, AttributeModifier> multimap) {
        multimap.asMap().forEach(this::lambda$removeModifiers$2);
    }

    public void reapplyModifiers(Multimap<Attribute, AttributeModifier> multimap) {
        multimap.forEach(this::lambda$reapplyModifiers$3);
    }

    public void refreshOnRespawn(AttributeModifierManager attributeModifierManager) {
        attributeModifierManager.instanceMap.values().forEach(this::lambda$refreshOnRespawn$4);
    }

    public ListNBT serialize() {
        ListNBT listNBT = new ListNBT();
        for (ModifiableAttributeInstance modifiableAttributeInstance : this.instanceMap.values()) {
            listNBT.add(modifiableAttributeInstance.writeInstances());
        }
        return listNBT;
    }

    public void deserialize(ListNBT listNBT) {
        for (int i = 0; i < listNBT.size(); ++i) {
            CompoundNBT compoundNBT = listNBT.getCompound(i);
            String string = compoundNBT.getString("Name");
            Util.acceptOrElse(Registry.ATTRIBUTE.getOptional(ResourceLocation.tryCreate(string)), arg_0 -> this.lambda$deserialize$5(compoundNBT, arg_0), () -> AttributeModifierManager.lambda$deserialize$6(string));
        }
    }

    private static void lambda$deserialize$6(String string) {
        LOGGER.warn("Ignoring unknown attribute '{}'", (Object)string);
    }

    private void lambda$deserialize$5(CompoundNBT compoundNBT, Attribute attribute) {
        ModifiableAttributeInstance modifiableAttributeInstance = this.createInstanceIfAbsent(attribute);
        if (modifiableAttributeInstance != null) {
            modifiableAttributeInstance.readInstances(compoundNBT);
        }
    }

    private void lambda$refreshOnRespawn$4(ModifiableAttributeInstance modifiableAttributeInstance) {
        ModifiableAttributeInstance modifiableAttributeInstance2 = this.createInstanceIfAbsent(modifiableAttributeInstance.getAttribute());
        if (modifiableAttributeInstance2 != null) {
            modifiableAttributeInstance2.copyValuesFromInstance(modifiableAttributeInstance);
        }
    }

    private void lambda$reapplyModifiers$3(Attribute attribute, AttributeModifier attributeModifier) {
        ModifiableAttributeInstance modifiableAttributeInstance = this.createInstanceIfAbsent(attribute);
        if (modifiableAttributeInstance != null) {
            modifiableAttributeInstance.removeModifier(attributeModifier);
            modifiableAttributeInstance.applyNonPersistentModifier(attributeModifier);
        }
    }

    private void lambda$removeModifiers$2(Attribute attribute, Collection collection) {
        ModifiableAttributeInstance modifiableAttributeInstance = this.instanceMap.get(attribute);
        if (modifiableAttributeInstance != null) {
            collection.forEach(modifiableAttributeInstance::removeModifier);
        }
    }

    private ModifiableAttributeInstance lambda$createInstanceIfAbsent$1(Attribute attribute) {
        return this.attributeMap.createImmutableAttributeInstance(this::addInstance, attribute);
    }

    private static boolean lambda$getWatchedInstances$0(ModifiableAttributeInstance modifiableAttributeInstance) {
        return modifiableAttributeInstance.getAttribute().getShouldWatch();
    }
}

