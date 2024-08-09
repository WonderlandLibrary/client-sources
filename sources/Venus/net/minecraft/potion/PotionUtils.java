/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.potion;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.optifine.Config;
import net.optifine.CustomColors;

public class PotionUtils {
    private static final IFormattableTextComponent field_242400_a = new TranslationTextComponent("effect.none").mergeStyle(TextFormatting.GRAY);

    public static List<EffectInstance> getEffectsFromStack(ItemStack itemStack) {
        return PotionUtils.getEffectsFromTag(itemStack.getTag());
    }

    public static List<EffectInstance> mergeEffects(Potion potion, Collection<EffectInstance> collection) {
        ArrayList<EffectInstance> arrayList = Lists.newArrayList();
        arrayList.addAll(potion.getEffects());
        arrayList.addAll(collection);
        return arrayList;
    }

    public static List<EffectInstance> getEffectsFromTag(@Nullable CompoundNBT compoundNBT) {
        ArrayList<EffectInstance> arrayList = Lists.newArrayList();
        arrayList.addAll(PotionUtils.getPotionTypeFromNBT(compoundNBT).getEffects());
        PotionUtils.addCustomPotionEffectToList(compoundNBT, arrayList);
        return arrayList;
    }

    public static List<EffectInstance> getFullEffectsFromItem(ItemStack itemStack) {
        return PotionUtils.getFullEffectsFromTag(itemStack.getTag());
    }

    public static List<EffectInstance> getFullEffectsFromTag(@Nullable CompoundNBT compoundNBT) {
        ArrayList<EffectInstance> arrayList = Lists.newArrayList();
        PotionUtils.addCustomPotionEffectToList(compoundNBT, arrayList);
        return arrayList;
    }

    public static void addCustomPotionEffectToList(@Nullable CompoundNBT compoundNBT, List<EffectInstance> list) {
        if (compoundNBT != null && compoundNBT.contains("CustomPotionEffects", 0)) {
            ListNBT listNBT = compoundNBT.getList("CustomPotionEffects", 10);
            for (int i = 0; i < listNBT.size(); ++i) {
                CompoundNBT compoundNBT2 = listNBT.getCompound(i);
                EffectInstance effectInstance = EffectInstance.read(compoundNBT2);
                if (effectInstance == null) continue;
                list.add(effectInstance);
            }
        }
    }

    public static int getColor(ItemStack itemStack) {
        CompoundNBT compoundNBT = itemStack.getTag();
        if (compoundNBT != null && compoundNBT.contains("CustomPotionColor", 0)) {
            return compoundNBT.getInt("CustomPotionColor");
        }
        return PotionUtils.getPotionFromItem(itemStack) == Potions.EMPTY ? 0xF800F8 : PotionUtils.getPotionColorFromEffectList(PotionUtils.getEffectsFromStack(itemStack));
    }

    public static int getPotionColor(Potion potion) {
        return potion == Potions.EMPTY ? 0xF800F8 : PotionUtils.getPotionColorFromEffectList(potion.getEffects());
    }

    public static int getPotionColorFromEffectList(Collection<EffectInstance> collection) {
        int n = 3694022;
        if (collection.isEmpty()) {
            return Config.isCustomColors() ? CustomColors.getPotionColor(null, n) : 3694022;
        }
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        int n2 = 0;
        for (EffectInstance effectInstance : collection) {
            if (!effectInstance.doesShowParticles()) continue;
            int n3 = effectInstance.getPotion().getLiquidColor();
            if (Config.isCustomColors()) {
                n3 = CustomColors.getPotionColor(effectInstance.getPotion(), n3);
            }
            int n4 = effectInstance.getAmplifier() + 1;
            f += (float)(n4 * (n3 >> 16 & 0xFF)) / 255.0f;
            f2 += (float)(n4 * (n3 >> 8 & 0xFF)) / 255.0f;
            f3 += (float)(n4 * (n3 >> 0 & 0xFF)) / 255.0f;
            n2 += n4;
        }
        if (n2 == 0) {
            return 1;
        }
        f = f / (float)n2 * 255.0f;
        f2 = f2 / (float)n2 * 255.0f;
        f3 = f3 / (float)n2 * 255.0f;
        return (int)f << 16 | (int)f2 << 8 | (int)f3;
    }

    public static Potion getPotionFromItem(ItemStack itemStack) {
        return PotionUtils.getPotionTypeFromNBT(itemStack.getTag());
    }

    public static Potion getPotionTypeFromNBT(@Nullable CompoundNBT compoundNBT) {
        return compoundNBT == null ? Potions.EMPTY : Potion.getPotionTypeForName(compoundNBT.getString("Potion"));
    }

    public static ItemStack addPotionToItemStack(ItemStack itemStack, Potion potion) {
        ResourceLocation resourceLocation = Registry.POTION.getKey(potion);
        if (potion == Potions.EMPTY) {
            itemStack.removeChildTag("Potion");
        } else {
            itemStack.getOrCreateTag().putString("Potion", resourceLocation.toString());
        }
        return itemStack;
    }

    public static ItemStack appendEffects(ItemStack itemStack, Collection<EffectInstance> collection) {
        if (collection.isEmpty()) {
            return itemStack;
        }
        CompoundNBT compoundNBT = itemStack.getOrCreateTag();
        ListNBT listNBT = compoundNBT.getList("CustomPotionEffects", 9);
        for (EffectInstance effectInstance : collection) {
            listNBT.add(effectInstance.write(new CompoundNBT()));
        }
        compoundNBT.put("CustomPotionEffects", listNBT);
        return itemStack;
    }

    public static void addPotionTooltip(ItemStack itemStack, List<ITextComponent> list, float f) {
        Object object;
        List<EffectInstance> list2 = PotionUtils.getEffectsFromStack(itemStack);
        ArrayList<Pair<Attribute, AttributeModifier>> arrayList = Lists.newArrayList();
        if (list2.isEmpty()) {
            list.add(field_242400_a);
        } else {
            for (EffectInstance object2 : list2) {
                object = new TranslationTextComponent(object2.getEffectName());
                Effect effect = object2.getPotion();
                Map<Attribute, AttributeModifier> map = effect.getAttributeModifierMap();
                if (!map.isEmpty()) {
                    for (Map.Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
                        AttributeModifier attributeModifier = entry.getValue();
                        AttributeModifier attributeModifier2 = new AttributeModifier(attributeModifier.getName(), effect.getAttributeModifierAmount(object2.getAmplifier(), attributeModifier), attributeModifier.getOperation());
                        arrayList.add(new Pair<Attribute, AttributeModifier>(entry.getKey(), attributeModifier2));
                    }
                }
                if (object2.getAmplifier() > 0) {
                    object = new TranslationTextComponent("potion.withAmplifier", object, new TranslationTextComponent("potion.potency." + object2.getAmplifier()));
                }
                if (object2.getDuration() > 20) {
                    object = new TranslationTextComponent("potion.withDuration", object, EffectUtils.getPotionDurationString(object2, f));
                }
                list.add(object.mergeStyle(effect.getEffectType().getColor()));
            }
        }
        if (!arrayList.isEmpty()) {
            list.add(StringTextComponent.EMPTY);
            list.add(new TranslationTextComponent("potion.whenDrank").mergeStyle(TextFormatting.DARK_PURPLE));
            for (Pair pair : arrayList) {
                object = (AttributeModifier)pair.getSecond();
                double d = ((AttributeModifier)object).getAmount();
                double d2 = ((AttributeModifier)object).getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && ((AttributeModifier)object).getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL ? ((AttributeModifier)object).getAmount() : ((AttributeModifier)object).getAmount() * 100.0;
                if (d > 0.0) {
                    list.add(new TranslationTextComponent("attribute.modifier.plus." + ((AttributeModifier)object).getOperation().getId(), ItemStack.DECIMALFORMAT.format(d2), new TranslationTextComponent(((Attribute)pair.getFirst()).getAttributeName())).mergeStyle(TextFormatting.BLUE));
                    continue;
                }
                if (!(d < 0.0)) continue;
                list.add(new TranslationTextComponent("attribute.modifier.take." + ((AttributeModifier)object).getOperation().getId(), ItemStack.DECIMALFORMAT.format(d2 *= -1.0), new TranslationTextComponent(((Attribute)pair.getFirst()).getAttributeName())).mergeStyle(TextFormatting.RED));
            }
        }
    }
}

