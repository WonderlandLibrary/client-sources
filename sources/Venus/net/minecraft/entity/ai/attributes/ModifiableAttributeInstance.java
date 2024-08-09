/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.attributes;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.registry.Registry;

public class ModifiableAttributeInstance {
    private final Attribute genericAttribute;
    private final Map<AttributeModifier.Operation, Set<AttributeModifier>> mapByOperation = Maps.newEnumMap(AttributeModifier.Operation.class);
    private final Map<UUID, AttributeModifier> instanceMap = new Object2ObjectArrayMap<UUID, AttributeModifier>();
    private final Set<AttributeModifier> mapByUUID = new ObjectArraySet<AttributeModifier>();
    private double base;
    private boolean requiresComputation = true;
    private double modifiedValue;
    private final Consumer<ModifiableAttributeInstance> modifiedValueConsumer;

    public ModifiableAttributeInstance(Attribute attribute, Consumer<ModifiableAttributeInstance> consumer) {
        this.genericAttribute = attribute;
        this.modifiedValueConsumer = consumer;
        this.base = attribute.getDefaultValue();
    }

    public Attribute getAttribute() {
        return this.genericAttribute;
    }

    public double getBaseValue() {
        return this.base;
    }

    public void setBaseValue(double d) {
        if (d != this.base) {
            this.base = d;
            this.compute();
        }
    }

    public Set<AttributeModifier> getOrCreateModifiersByOperation(AttributeModifier.Operation operation) {
        return this.mapByOperation.computeIfAbsent(operation, ModifiableAttributeInstance::lambda$getOrCreateModifiersByOperation$0);
    }

    public Set<AttributeModifier> getModifierListCopy() {
        return ImmutableSet.copyOf(this.instanceMap.values());
    }

    @Nullable
    public AttributeModifier getModifier(UUID uUID) {
        return this.instanceMap.get(uUID);
    }

    public boolean hasModifier(AttributeModifier attributeModifier) {
        return this.instanceMap.get(attributeModifier.getID()) != null;
    }

    private void applyModifier(AttributeModifier attributeModifier) {
        AttributeModifier attributeModifier2 = this.instanceMap.putIfAbsent(attributeModifier.getID(), attributeModifier);
        if (attributeModifier2 != null) {
            throw new IllegalArgumentException("Modifier is already applied on this attribute!");
        }
        this.getOrCreateModifiersByOperation(attributeModifier.getOperation()).add(attributeModifier);
        this.compute();
    }

    public void applyNonPersistentModifier(AttributeModifier attributeModifier) {
        this.applyModifier(attributeModifier);
    }

    public void applyPersistentModifier(AttributeModifier attributeModifier) {
        this.applyModifier(attributeModifier);
        this.mapByUUID.add(attributeModifier);
    }

    protected void compute() {
        this.requiresComputation = true;
        this.modifiedValueConsumer.accept(this);
    }

    public void removeModifier(AttributeModifier attributeModifier) {
        this.getOrCreateModifiersByOperation(attributeModifier.getOperation()).remove(attributeModifier);
        this.instanceMap.remove(attributeModifier.getID());
        this.mapByUUID.remove(attributeModifier);
        this.compute();
    }

    public void removeModifier(UUID uUID) {
        AttributeModifier attributeModifier = this.getModifier(uUID);
        if (attributeModifier != null) {
            this.removeModifier(attributeModifier);
        }
    }

    public boolean removePersistentModifier(UUID uUID) {
        AttributeModifier attributeModifier = this.getModifier(uUID);
        if (attributeModifier != null && this.mapByUUID.contains(attributeModifier)) {
            this.removeModifier(attributeModifier);
            return false;
        }
        return true;
    }

    public void removeAllModifiers() {
        for (AttributeModifier attributeModifier : this.getModifierListCopy()) {
            this.removeModifier(attributeModifier);
        }
    }

    public double getValue() {
        if (this.requiresComputation) {
            this.modifiedValue = this.computeValue();
            this.requiresComputation = false;
        }
        return this.modifiedValue;
    }

    private double computeValue() {
        double d = this.getBaseValue();
        for (AttributeModifier attributeModifier : this.getModifiersByOperation(AttributeModifier.Operation.ADDITION)) {
            d += attributeModifier.getAmount();
        }
        double d2 = d;
        for (AttributeModifier attributeModifier : this.getModifiersByOperation(AttributeModifier.Operation.MULTIPLY_BASE)) {
            d2 += d * attributeModifier.getAmount();
        }
        for (AttributeModifier attributeModifier : this.getModifiersByOperation(AttributeModifier.Operation.MULTIPLY_TOTAL)) {
            d2 *= 1.0 + attributeModifier.getAmount();
        }
        return this.genericAttribute.clampValue(d2);
    }

    private Collection<AttributeModifier> getModifiersByOperation(AttributeModifier.Operation operation) {
        return this.mapByOperation.getOrDefault((Object)operation, Collections.emptySet());
    }

    public void copyValuesFromInstance(ModifiableAttributeInstance modifiableAttributeInstance) {
        this.base = modifiableAttributeInstance.base;
        this.instanceMap.clear();
        this.instanceMap.putAll(modifiableAttributeInstance.instanceMap);
        this.mapByUUID.clear();
        this.mapByUUID.addAll(modifiableAttributeInstance.mapByUUID);
        this.mapByOperation.clear();
        modifiableAttributeInstance.mapByOperation.forEach(this::lambda$copyValuesFromInstance$1);
        this.compute();
    }

    public CompoundNBT writeInstances() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("Name", Registry.ATTRIBUTE.getKey(this.genericAttribute).toString());
        compoundNBT.putDouble("Base", this.base);
        if (!this.mapByUUID.isEmpty()) {
            ListNBT listNBT = new ListNBT();
            for (AttributeModifier attributeModifier : this.mapByUUID) {
                listNBT.add(attributeModifier.write());
            }
            compoundNBT.put("Modifiers", listNBT);
        }
        return compoundNBT;
    }

    public void readInstances(CompoundNBT compoundNBT) {
        this.base = compoundNBT.getDouble("Base");
        if (compoundNBT.contains("Modifiers", 0)) {
            ListNBT listNBT = compoundNBT.getList("Modifiers", 10);
            for (int i = 0; i < listNBT.size(); ++i) {
                AttributeModifier attributeModifier = AttributeModifier.read(listNBT.getCompound(i));
                if (attributeModifier == null) continue;
                this.instanceMap.put(attributeModifier.getID(), attributeModifier);
                this.getOrCreateModifiersByOperation(attributeModifier.getOperation()).add(attributeModifier);
                this.mapByUUID.add(attributeModifier);
            }
        }
        this.compute();
    }

    private void lambda$copyValuesFromInstance$1(AttributeModifier.Operation operation, Set set) {
        this.getOrCreateModifiersByOperation(operation).addAll(set);
    }

    private static Set lambda$getOrCreateModifiersByOperation$0(AttributeModifier.Operation operation) {
        return Sets.newHashSet();
    }
}

