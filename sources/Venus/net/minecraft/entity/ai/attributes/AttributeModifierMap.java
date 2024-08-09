/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.attributes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.util.registry.Registry;

public class AttributeModifierMap {
    private final Map<Attribute, ModifiableAttributeInstance> attributeMap;

    public AttributeModifierMap(Map<Attribute, ModifiableAttributeInstance> map) {
        this.attributeMap = ImmutableMap.copyOf(map);
    }

    private ModifiableAttributeInstance getModifier(Attribute attribute) {
        ModifiableAttributeInstance modifiableAttributeInstance = this.attributeMap.get(attribute);
        if (modifiableAttributeInstance == null) {
            throw new IllegalArgumentException("Can't find attribute " + Registry.ATTRIBUTE.getKey(attribute));
        }
        return modifiableAttributeInstance;
    }

    public double getAttributeValue(Attribute attribute) {
        return this.getModifier(attribute).getValue();
    }

    public double getAttributeBaseValue(Attribute attribute) {
        return this.getModifier(attribute).getBaseValue();
    }

    public double getAttributeModifierValue(Attribute attribute, UUID uUID) {
        AttributeModifier attributeModifier = this.getModifier(attribute).getModifier(uUID);
        if (attributeModifier == null) {
            throw new IllegalArgumentException("Can't find modifier " + uUID + " on attribute " + Registry.ATTRIBUTE.getKey(attribute));
        }
        return attributeModifier.getAmount();
    }

    @Nullable
    public ModifiableAttributeInstance createImmutableAttributeInstance(Consumer<ModifiableAttributeInstance> consumer, Attribute attribute) {
        ModifiableAttributeInstance modifiableAttributeInstance = this.attributeMap.get(attribute);
        if (modifiableAttributeInstance == null) {
            return null;
        }
        ModifiableAttributeInstance modifiableAttributeInstance2 = new ModifiableAttributeInstance(attribute, consumer);
        modifiableAttributeInstance2.copyValuesFromInstance(modifiableAttributeInstance);
        return modifiableAttributeInstance2;
    }

    public static MutableAttribute createMutableAttribute() {
        return new MutableAttribute();
    }

    public boolean hasAttribute(Attribute attribute) {
        return this.attributeMap.containsKey(attribute);
    }

    public boolean hasModifier(Attribute attribute, UUID uUID) {
        ModifiableAttributeInstance modifiableAttributeInstance = this.attributeMap.get(attribute);
        return modifiableAttributeInstance != null && modifiableAttributeInstance.getModifier(uUID) != null;
    }

    public static class MutableAttribute {
        private final Map<Attribute, ModifiableAttributeInstance> attributeMap = Maps.newHashMap();
        private boolean edited;

        private ModifiableAttributeInstance createAttributeInstance(Attribute attribute) {
            ModifiableAttributeInstance modifiableAttributeInstance = new ModifiableAttributeInstance(attribute, arg_0 -> this.lambda$createAttributeInstance$0(attribute, arg_0));
            this.attributeMap.put(attribute, modifiableAttributeInstance);
            return modifiableAttributeInstance;
        }

        public MutableAttribute createMutableAttribute(Attribute attribute) {
            this.createAttributeInstance(attribute);
            return this;
        }

        public MutableAttribute createMutableAttribute(Attribute attribute, double d) {
            ModifiableAttributeInstance modifiableAttributeInstance = this.createAttributeInstance(attribute);
            modifiableAttributeInstance.setBaseValue(d);
            return this;
        }

        public AttributeModifierMap create() {
            this.edited = true;
            return new AttributeModifierMap(this.attributeMap);
        }

        private void lambda$createAttributeInstance$0(Attribute attribute, ModifiableAttributeInstance modifiableAttributeInstance) {
            if (this.edited) {
                throw new UnsupportedOperationException("Tried to change value for default attribute instance: " + Registry.ATTRIBUTE.getKey(attribute));
            }
        }
    }
}

