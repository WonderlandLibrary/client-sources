/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.BlockStateFlatteningMap;
import net.minecraft.util.datafix.fixes.ItemStackDataFlattening;
import org.apache.commons.lang3.StringUtils;

public class StatsRenaming
extends DataFix {
    private static final Set<String> field_209682_a = ((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)ImmutableSet.builder().add("stat.craftItem.minecraft.spawn_egg")).add("stat.useItem.minecraft.spawn_egg")).add("stat.breakItem.minecraft.spawn_egg")).add("stat.pickup.minecraft.spawn_egg")).add("stat.drop.minecraft.spawn_egg")).build();
    private static final Map<String, String> field_209683_b = ImmutableMap.builder().put("stat.leaveGame", "minecraft:leave_game").put("stat.playOneMinute", "minecraft:play_one_minute").put("stat.timeSinceDeath", "minecraft:time_since_death").put("stat.sneakTime", "minecraft:sneak_time").put("stat.walkOneCm", "minecraft:walk_one_cm").put("stat.crouchOneCm", "minecraft:crouch_one_cm").put("stat.sprintOneCm", "minecraft:sprint_one_cm").put("stat.swimOneCm", "minecraft:swim_one_cm").put("stat.fallOneCm", "minecraft:fall_one_cm").put("stat.climbOneCm", "minecraft:climb_one_cm").put("stat.flyOneCm", "minecraft:fly_one_cm").put("stat.diveOneCm", "minecraft:dive_one_cm").put("stat.minecartOneCm", "minecraft:minecart_one_cm").put("stat.boatOneCm", "minecraft:boat_one_cm").put("stat.pigOneCm", "minecraft:pig_one_cm").put("stat.horseOneCm", "minecraft:horse_one_cm").put("stat.aviateOneCm", "minecraft:aviate_one_cm").put("stat.jump", "minecraft:jump").put("stat.drop", "minecraft:drop").put("stat.damageDealt", "minecraft:damage_dealt").put("stat.damageTaken", "minecraft:damage_taken").put("stat.deaths", "minecraft:deaths").put("stat.mobKills", "minecraft:mob_kills").put("stat.animalsBred", "minecraft:animals_bred").put("stat.playerKills", "minecraft:player_kills").put("stat.fishCaught", "minecraft:fish_caught").put("stat.talkedToVillager", "minecraft:talked_to_villager").put("stat.tradedWithVillager", "minecraft:traded_with_villager").put("stat.cakeSlicesEaten", "minecraft:eat_cake_slice").put("stat.cauldronFilled", "minecraft:fill_cauldron").put("stat.cauldronUsed", "minecraft:use_cauldron").put("stat.armorCleaned", "minecraft:clean_armor").put("stat.bannerCleaned", "minecraft:clean_banner").put("stat.brewingstandInteraction", "minecraft:interact_with_brewingstand").put("stat.beaconInteraction", "minecraft:interact_with_beacon").put("stat.dropperInspected", "minecraft:inspect_dropper").put("stat.hopperInspected", "minecraft:inspect_hopper").put("stat.dispenserInspected", "minecraft:inspect_dispenser").put("stat.noteblockPlayed", "minecraft:play_noteblock").put("stat.noteblockTuned", "minecraft:tune_noteblock").put("stat.flowerPotted", "minecraft:pot_flower").put("stat.trappedChestTriggered", "minecraft:trigger_trapped_chest").put("stat.enderchestOpened", "minecraft:open_enderchest").put("stat.itemEnchanted", "minecraft:enchant_item").put("stat.recordPlayed", "minecraft:play_record").put("stat.furnaceInteraction", "minecraft:interact_with_furnace").put("stat.craftingTableInteraction", "minecraft:interact_with_crafting_table").put("stat.chestOpened", "minecraft:open_chest").put("stat.sleepInBed", "minecraft:sleep_in_bed").put("stat.shulkerBoxOpened", "minecraft:open_shulker_box").build();
    private static final Map<String, String> field_199189_b = ImmutableMap.builder().put("stat.craftItem", "minecraft:crafted").put("stat.useItem", "minecraft:used").put("stat.breakItem", "minecraft:broken").put("stat.pickup", "minecraft:picked_up").put("stat.drop", "minecraft:dropped").build();
    private static final Map<String, String> field_209684_d = ImmutableMap.builder().put("stat.entityKilledBy", "minecraft:killed_by").put("stat.killEntity", "minecraft:killed").build();
    private static final Map<String, String> field_209685_e = ImmutableMap.builder().put("Bat", "minecraft:bat").put("Blaze", "minecraft:blaze").put("CaveSpider", "minecraft:cave_spider").put("Chicken", "minecraft:chicken").put("Cow", "minecraft:cow").put("Creeper", "minecraft:creeper").put("Donkey", "minecraft:donkey").put("ElderGuardian", "minecraft:elder_guardian").put("Enderman", "minecraft:enderman").put("Endermite", "minecraft:endermite").put("EvocationIllager", "minecraft:evocation_illager").put("Ghast", "minecraft:ghast").put("Guardian", "minecraft:guardian").put("Horse", "minecraft:horse").put("Husk", "minecraft:husk").put("Llama", "minecraft:llama").put("LavaSlime", "minecraft:magma_cube").put("MushroomCow", "minecraft:mooshroom").put("Mule", "minecraft:mule").put("Ozelot", "minecraft:ocelot").put("Parrot", "minecraft:parrot").put("Pig", "minecraft:pig").put("PolarBear", "minecraft:polar_bear").put("Rabbit", "minecraft:rabbit").put("Sheep", "minecraft:sheep").put("Shulker", "minecraft:shulker").put("Silverfish", "minecraft:silverfish").put("SkeletonHorse", "minecraft:skeleton_horse").put("Skeleton", "minecraft:skeleton").put("Slime", "minecraft:slime").put("Spider", "minecraft:spider").put("Squid", "minecraft:squid").put("Stray", "minecraft:stray").put("Vex", "minecraft:vex").put("Villager", "minecraft:villager").put("VindicationIllager", "minecraft:vindication_illager").put("Witch", "minecraft:witch").put("WitherSkeleton", "minecraft:wither_skeleton").put("Wolf", "minecraft:wolf").put("ZombieHorse", "minecraft:zombie_horse").put("PigZombie", "minecraft:zombie_pigman").put("ZombieVillager", "minecraft:zombie_villager").put("Zombie", "minecraft:zombie").build();

    public StatsRenaming(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        Type<?> type = this.getOutputSchema().getType(TypeReferences.STATS);
        return this.fixTypeEverywhereTyped("StatsCounterFix", this.getInputSchema().getType(TypeReferences.STATS), type, arg_0 -> this.lambda$makeRule$3(type, arg_0));
    }

    @Nullable
    protected String upgradeItem(String string) {
        return ItemStackDataFlattening.updateItem(string, 0);
    }

    protected String upgradeBlock(String string) {
        return BlockStateFlatteningMap.updateName(string);
    }

    private Typed lambda$makeRule$3(Type type, Typed typed) {
        Dynamic<?> dynamic = typed.get(DSL.remainderFinder());
        HashMap hashMap = Maps.newHashMap();
        Optional<Map<Dynamic<?>, Dynamic<?>>> optional = dynamic.getMapValues().result();
        if (optional.isPresent()) {
            Iterator<Map.Entry<Dynamic<?>, Dynamic<?>>> iterator2 = optional.get().entrySet().iterator();
            while (true) {
                String string;
                String string2;
                Object object;
                if (!iterator2.hasNext()) {
                    return type.readTyped(dynamic.emptyMap().set("stats", dynamic.createMap(hashMap))).result().orElseThrow(StatsRenaming::lambda$makeRule$0).getFirst();
                }
                Map.Entry<Dynamic<?>, Dynamic<?>> entry = iterator2.next();
                if (!entry.getValue().asNumber().result().isPresent() || field_209682_a.contains(object = entry.getKey().asString(""))) continue;
                if (field_209683_b.containsKey(object)) {
                    string2 = "minecraft:custom";
                    string = field_209683_b.get(object);
                } else {
                    int n = StringUtils.ordinalIndexOf((CharSequence)object, ".", 2);
                    if (n < 0) continue;
                    String string3 = ((String)object).substring(0, n);
                    if ("stat.mineBlock".equals(string3)) {
                        string2 = "minecraft:mined";
                        string = this.upgradeBlock(((String)object).substring(n + 1).replace('.', ':'));
                    } else if (field_199189_b.containsKey(string3)) {
                        string2 = field_199189_b.get(string3);
                        var13_14 = ((String)object).substring(n + 1).replace('.', ':');
                        String string4 = this.upgradeItem(var13_14);
                        string = string4 == null ? var13_14 : string4;
                    } else {
                        if (!field_209684_d.containsKey(string3)) continue;
                        string2 = field_209684_d.get(string3);
                        var13_14 = ((String)object).substring(n + 1).replace('.', ':');
                        string = field_209685_e.getOrDefault(var13_14, var13_14);
                    }
                }
                object = dynamic.createString(string2);
                Dynamic dynamic2 = hashMap.computeIfAbsent(object, arg_0 -> StatsRenaming.lambda$makeRule$1(dynamic, arg_0));
                hashMap.put(object, dynamic2.set(string, entry.getValue()));
            }
        }
        return type.readTyped(dynamic.emptyMap().set("stats", dynamic.createMap(hashMap))).result().orElseThrow(StatsRenaming::lambda$makeRule$2).getFirst();
    }

    private static IllegalStateException lambda$makeRule$2() {
        return new IllegalStateException("Could not parse new stats object.");
    }

    private static Dynamic lambda$makeRule$1(Dynamic dynamic, Dynamic dynamic2) {
        return dynamic.emptyMap();
    }

    private static IllegalStateException lambda$makeRule$0() {
        return new IllegalStateException("Could not parse new stats object.");
    }
}

