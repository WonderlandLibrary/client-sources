/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.property.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.dietrichpaul.clientbase.feature.command.argument.EntityTypeArgumentType;
import de.dietrichpaul.clientbase.property.Property;
import de.dietrichpaul.clientbase.util.minecraft.ChatUtil;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanRBTreeMap;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityTypeProperty extends Property {

    private static List<EntityType<?>> notAttackable = Arrays.asList(
            EntityType.AREA_EFFECT_CLOUD,
            EntityType.ARROW,
            EntityType.EGG,
            EntityType.ENDER_PEARL,
            EntityType.EXPERIENCE_BOTTLE,
            EntityType.EXPERIENCE_ORB,
            EntityType.FALLING_BLOCK,
            EntityType.FIREWORK_ROCKET,
            EntityType.FISHING_BOBBER,
            EntityType.ITEM,
            EntityType.LIGHTNING_BOLT,
            EntityType.LLAMA_SPIT,
            EntityType.POTION,
            EntityType.TRIDENT,
            EntityType.SPECTRAL_ARROW
    );

    private Object2BooleanMap<EntityType<?>> entityTypes = new Object2BooleanRBTreeMap<>(Comparator.comparing(entityType
            -> I18n.translate(entityType.getTranslationKey())));

    public EntityTypeProperty(String name, boolean onlyAttackable, EntityType<?>... defaultEnabled) {
        super(name);
        Registries.ENTITY_TYPE.forEach(entityType -> {
            if (!onlyAttackable || !notAttackable.contains(entityType)) {
                entityTypes.put(entityType, ArrayUtils.contains(defaultEnabled, entityType));
            }
        });
    }

    public <T extends Entity> boolean filter(T entity) {
        return entityTypes.getBoolean(entity.getType());
    }

    @Override
    public JsonElement serialize() {
        JsonArray array = new JsonArray();
        entityTypes.object2BooleanEntrySet().stream()
                .filter(Object2BooleanMap.Entry::getBooleanValue)
                .forEach(type -> array.add(Registries.ENTITY_TYPE.getId(type.getKey()).toString()));
        return array;
    }

    @Override
    public void deserialize(JsonElement element) {
        if (element == null) return;
        JsonArray array = element.getAsJsonArray();
        for (JsonElement jsonElement : array) {
            EntityType<?> next = Registries.ENTITY_TYPE.get(new Identifier(jsonElement.getAsString()));
            entityTypes.removeBoolean(next);
            entityTypes.put(next, true);
        }
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root.then(
                literal("add")
                        .then(
                                argument("type", EntityTypeArgumentType.entityType(e -> entityTypes.containsKey(e) && !entityTypes.getBoolean(e)))
                                        .executes(context -> {
                                            EntityType<?> type = EntityTypeArgumentType.getEntityType(context, "type");
                                            entityTypes.removeBoolean(type);
                                            entityTypes.put(type, true);
                                            reportChanges();
                                            ChatUtil.sendI18n("command.property.add", Text.translatable(type.getTranslationKey()), getName());
                                            return 1;
                                        })
                        )
        ).then(
                literal("remove")
                        .then(
                                argument("type", EntityTypeArgumentType.entityType(e -> entityTypes.containsKey(e) && entityTypes.getBoolean(e)))
                                        .executes(context -> {
                                            EntityType<?> type = EntityTypeArgumentType.getEntityType(context, "type");
                                            entityTypes.removeBoolean(type);
                                            entityTypes.put(type, false);
                                            reportChanges();
                                            ChatUtil.sendI18n("command.property.remove", Text.translatable(type.getTranslationKey()), getName());
                                            return 1;
                                        })
                        )
        ).executes(context -> {
            ChatUtil.sendI18n("command.property.contains", getName());
            entityTypes.forEach((entityType, aBoolean) -> {
                if (aBoolean) {
                    ChatUtil.sendI18n("bullet_point", Text.translatable(entityType.getTranslationKey()));
                }
            });
            return 1;
        });
    }
}
