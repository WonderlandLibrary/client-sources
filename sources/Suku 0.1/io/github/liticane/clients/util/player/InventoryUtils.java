package io.github.liticane.clients.util.player;

import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

import java.util.Iterator;
import java.util.Map;

public class InventoryUtils {
    public static float getItemDamage(ItemStack itemStack) {
        Iterator iterator;
        Multimap multimap = itemStack.getAttributeModifiers();
        if (!multimap.isEmpty() && (iterator = multimap.entries().iterator()).hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            AttributeModifier attributeModifier = (AttributeModifier)entry.getValue();
            double damage = attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2 ? attributeModifier.getAmount() : attributeModifier.getAmount() * 100.0;
            return attributeModifier.getAmount() > 1.0 ? 1.0f + (float)damage : 1.0f;
        }
        return 1.0f;
    }
}
