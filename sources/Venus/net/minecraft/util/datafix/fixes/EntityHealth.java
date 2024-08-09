/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Sets;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.Set;
import net.minecraft.util.datafix.TypeReferences;

public class EntityHealth
extends DataFix {
    private static final Set<String> ENTITY_LIST = Sets.newHashSet("ArmorStand", "Bat", "Blaze", "CaveSpider", "Chicken", "Cow", "Creeper", "EnderDragon", "Enderman", "Endermite", "EntityHorse", "Ghast", "Giant", "Guardian", "LavaSlime", "MushroomCow", "Ozelot", "Pig", "PigZombie", "Rabbit", "Sheep", "Shulker", "Silverfish", "Skeleton", "Slime", "SnowMan", "Spider", "Squid", "Villager", "VillagerGolem", "Witch", "WitherBoss", "Wolf", "Zombie");

    public EntityHealth(Schema schema, boolean bl) {
        super(schema, bl);
    }

    public Dynamic<?> fixTag(Dynamic<?> dynamic) {
        float f;
        Optional<Number> optional = dynamic.get("HealF").asNumber().result();
        Optional<Number> optional2 = dynamic.get("Health").asNumber().result();
        if (optional.isPresent()) {
            f = optional.get().floatValue();
            dynamic = dynamic.remove("HealF");
        } else {
            if (!optional2.isPresent()) {
                return dynamic;
            }
            f = optional2.get().floatValue();
        }
        return dynamic.set("Health", dynamic.createFloat(f));
    }

    @Override
    public TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("EntityHealthFix", this.getInputSchema().getType(TypeReferences.ENTITY), this::lambda$makeRule$0);
    }

    private Typed lambda$makeRule$0(Typed typed) {
        return typed.update(DSL.remainderFinder(), this::fixTag);
    }
}

