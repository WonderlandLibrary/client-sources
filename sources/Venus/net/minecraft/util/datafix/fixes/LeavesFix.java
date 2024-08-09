/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.ArbitraryBitLengthIntArray;
import net.minecraft.util.datafix.TypeReferences;

public class LeavesFix
extends DataFix {
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0, 0}, {1, 0, 0}, {0, -1, 0}, {0, 1, 0}, {0, 0, -1}, {0, 0, 1}};
    private static final Object2IntMap<String> LEAVES = DataFixUtils.make(new Object2IntOpenHashMap(), LeavesFix::lambda$static$0);
    private static final Set<String> LOGS = ImmutableSet.of("minecraft:acacia_bark", "minecraft:birch_bark", "minecraft:dark_oak_bark", "minecraft:jungle_bark", "minecraft:oak_bark", "minecraft:spruce_bark", "minecraft:acacia_log", "minecraft:birch_log", "minecraft:dark_oak_log", "minecraft:jungle_log", "minecraft:oak_log", "minecraft:spruce_log", "minecraft:stripped_acacia_log", "minecraft:stripped_birch_log", "minecraft:stripped_dark_oak_log", "minecraft:stripped_jungle_log", "minecraft:stripped_oak_log", "minecraft:stripped_spruce_log");

    public LeavesFix(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.CHUNK);
        OpticFinder<?> opticFinder = type.findField("Level");
        OpticFinder<?> opticFinder2 = opticFinder.type().findField("Sections");
        Type<?> type2 = opticFinder2.type();
        if (!(type2 instanceof List.ListType)) {
            throw new IllegalStateException("Expecting sections to be a list.");
        }
        Type type3 = ((List.ListType)type2).getElement();
        OpticFinder opticFinder3 = DSL.typeFinder(type3);
        return this.fixTypeEverywhereTyped("Leaves fix", type, arg_0 -> this.lambda$makeRule$7(opticFinder, opticFinder2, opticFinder3, arg_0));
    }

    public static int getIndex(int n, int n2, int n3) {
        return n2 << 8 | n3 << 4 | n;
    }

    private int getX(int n) {
        return n & 0xF;
    }

    private int getY(int n) {
        return n >> 8 & 0xFF;
    }

    private int getZ(int n) {
        return n >> 4 & 0xF;
    }

    public static int getSideMask(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        int n = 0;
        if (bl3) {
            n = bl2 ? (n |= 2) : (bl ? (n |= 0x80) : (n |= 1));
        } else if (bl4) {
            n = bl ? (n |= 0x20) : (bl2 ? (n |= 8) : (n |= 0x10));
        } else if (bl2) {
            n |= 4;
        } else if (bl) {
            n |= 0x40;
        }
        return n;
    }

    private Typed lambda$makeRule$7(OpticFinder opticFinder, OpticFinder opticFinder2, OpticFinder opticFinder3, Typed typed) {
        return typed.updateTyped(opticFinder, arg_0 -> this.lambda$makeRule$6(opticFinder2, opticFinder3, arg_0));
    }

    private Typed lambda$makeRule$6(OpticFinder opticFinder, OpticFinder opticFinder2, Typed typed) {
        int[] nArray = new int[]{0};
        Typed<?> typed2 = typed.updateTyped(opticFinder, arg_0 -> this.lambda$makeRule$4(opticFinder2, nArray, arg_0));
        if (nArray[0] != 0) {
            typed2 = typed2.update(DSL.remainderFinder(), arg_0 -> LeavesFix.lambda$makeRule$5(nArray, arg_0));
        }
        return typed2;
    }

    private static Dynamic lambda$makeRule$5(int[] nArray, Dynamic dynamic) {
        Dynamic dynamic2 = DataFixUtils.orElse(dynamic.get("UpgradeData").result(), dynamic.emptyMap());
        return dynamic.set("UpgradeData", dynamic2.set("Sides", dynamic.createByte((byte)(dynamic2.get("Sides").asByte((byte)0) | nArray[0]))));
    }

    private Typed lambda$makeRule$4(OpticFinder opticFinder, int[] nArray, Typed typed) {
        int n;
        int n2;
        Int2ObjectOpenHashMap<LeavesSection> int2ObjectOpenHashMap = new Int2ObjectOpenHashMap<LeavesSection>(typed.getAllTyped(opticFinder).stream().map(this::lambda$makeRule$1).collect(Collectors.toMap(Section::getIndex, LeavesFix::lambda$makeRule$2)));
        if (int2ObjectOpenHashMap.values().stream().allMatch(Section::isSkippable)) {
            return typed;
        }
        ArrayList<IntOpenHashSet> arrayList = Lists.newArrayList();
        for (int i = 0; i < 7; ++i) {
            arrayList.add(new IntOpenHashSet());
        }
        for (Object object : int2ObjectOpenHashMap.values()) {
            if (((Section)object).isSkippable()) continue;
            for (int i = 0; i < 4096; ++i) {
                int n3 = ((Section)object).getBlock(i);
                if (((LeavesSection)object).isLog(n3)) {
                    ((IntSet)arrayList.get(0)).add(((Section)object).getIndex() << 12 | i);
                    continue;
                }
                if (!((LeavesSection)object).isLeaf(n3)) continue;
                n2 = this.getX(i);
                n = this.getZ(i);
                nArray[0] = nArray[0] | LeavesFix.getSideMask(n2 == 0, n2 == 15, n == 0, n == 15);
            }
        }
        for (int i = 1; i < 7; ++i) {
            Object object;
            object = (IntSet)arrayList.get(i - 1);
            IntSet intSet = (IntSet)arrayList.get(i);
            IntIterator intIterator = object.iterator();
            while (intIterator.hasNext()) {
                n2 = intIterator.nextInt();
                n = this.getX(n2);
                int n4 = this.getY(n2);
                int n5 = this.getZ(n2);
                for (int[] nArray2 : DIRECTIONS) {
                    int n6;
                    int n7;
                    int n8;
                    LeavesSection leavesSection;
                    int n9 = n + nArray2[0];
                    int n10 = n4 + nArray2[1];
                    int n11 = n5 + nArray2[2];
                    if (n9 < 0 || n9 > 15 || n11 < 0 || n11 > 15 || n10 < 0 || n10 > 255 || (leavesSection = (LeavesSection)int2ObjectOpenHashMap.get(n10 >> 4)) == null || leavesSection.isSkippable() || !leavesSection.isLeaf(n8 = leavesSection.getBlock(n7 = LeavesFix.getIndex(n9, n10 & 0xF, n11))) || (n6 = leavesSection.getDistance(n8)) <= i) continue;
                    leavesSection.setDistance(n7, n8, i);
                    intSet.add(LeavesFix.getIndex(n9, n10, n11));
                }
            }
        }
        return typed.updateTyped(opticFinder, arg_0 -> LeavesFix.lambda$makeRule$3(int2ObjectOpenHashMap, arg_0));
    }

    private static Typed lambda$makeRule$3(Int2ObjectMap int2ObjectMap, Typed typed) {
        return ((LeavesSection)int2ObjectMap.get(typed.get(DSL.remainderFinder()).get("Y").asInt(0))).write(typed);
    }

    private static LeavesSection lambda$makeRule$2(LeavesSection leavesSection) {
        return leavesSection;
    }

    private LeavesSection lambda$makeRule$1(Typed typed) {
        return new LeavesSection(typed, this.getInputSchema());
    }

    private static void lambda$static$0(Object2IntOpenHashMap object2IntOpenHashMap) {
        object2IntOpenHashMap.put("minecraft:acacia_leaves", 0);
        object2IntOpenHashMap.put("minecraft:birch_leaves", 1);
        object2IntOpenHashMap.put("minecraft:dark_oak_leaves", 2);
        object2IntOpenHashMap.put("minecraft:jungle_leaves", 3);
        object2IntOpenHashMap.put("minecraft:oak_leaves", 4);
        object2IntOpenHashMap.put("minecraft:spruce_leaves", 5);
    }

    public static final class LeavesSection
    extends Section {
        @Nullable
        private IntSet field_212523_f;
        @Nullable
        private IntSet field_212524_g;
        @Nullable
        private Int2IntMap field_212525_h;

        public LeavesSection(Typed<?> typed, Schema schema) {
            super(typed, schema);
        }

        @Override
        protected boolean func_212508_a() {
            this.field_212523_f = new IntOpenHashSet();
            this.field_212524_g = new IntOpenHashSet();
            this.field_212525_h = new Int2IntOpenHashMap();
            for (int i = 0; i < this.palette.size(); ++i) {
                Dynamic dynamic = (Dynamic)this.palette.get(i);
                String string = dynamic.get("Name").asString("");
                if (LEAVES.containsKey(string)) {
                    boolean bl = Objects.equals(dynamic.get("Properties").get("decayable").asString(""), "false");
                    this.field_212523_f.add(i);
                    this.field_212525_h.put(this.getStateId(string, bl, 0), i);
                    this.palette.set(i, this.makeLeafTag(dynamic, string, bl, 0));
                }
                if (!LOGS.contains(string)) continue;
                this.field_212524_g.add(i);
            }
            return this.field_212523_f.isEmpty() && this.field_212524_g.isEmpty();
        }

        private Dynamic<?> makeLeafTag(Dynamic<?> dynamic, String string, boolean bl, int n) {
            Dynamic dynamic2 = dynamic.emptyMap();
            dynamic2 = dynamic2.set("persistent", dynamic2.createString(bl ? "true" : "false"));
            dynamic2 = dynamic2.set("distance", dynamic2.createString(Integer.toString(n)));
            Dynamic dynamic3 = dynamic.emptyMap();
            dynamic3 = dynamic3.set("Properties", dynamic2);
            return dynamic3.set("Name", dynamic3.createString(string));
        }

        public boolean isLog(int n) {
            return this.field_212524_g.contains(n);
        }

        public boolean isLeaf(int n) {
            return this.field_212523_f.contains(n);
        }

        private int getDistance(int n) {
            return this.isLog(n) ? 0 : Integer.parseInt(((Dynamic)this.palette.get(n)).get("Properties").get("distance").asString(""));
        }

        private void setDistance(int n, int n2, int n3) {
            int n4;
            boolean bl;
            Dynamic dynamic = (Dynamic)this.palette.get(n2);
            String string = dynamic.get("Name").asString("");
            int n5 = this.getStateId(string, bl = Objects.equals(dynamic.get("Properties").get("persistent").asString(""), "true"), n3);
            if (!this.field_212525_h.containsKey(n5)) {
                n4 = this.palette.size();
                this.field_212523_f.add(n4);
                this.field_212525_h.put(n5, n4);
                this.palette.add(this.makeLeafTag(dynamic, string, bl, n3));
            }
            n4 = this.field_212525_h.get(n5);
            if (1 << this.storage.func_233050_b_() <= n4) {
                ArbitraryBitLengthIntArray arbitraryBitLengthIntArray = new ArbitraryBitLengthIntArray(this.storage.func_233050_b_() + 1, 4096);
                for (int i = 0; i < 4096; ++i) {
                    arbitraryBitLengthIntArray.func_233049_a_(i, this.storage.func_233048_a_(i));
                }
                this.storage = arbitraryBitLengthIntArray;
            }
            this.storage.func_233049_a_(n, n4);
        }
    }

    public static abstract class Section {
        private final Type<Pair<String, Dynamic<?>>> blockStateType = DSL.named(TypeReferences.BLOCK_STATE.typeName(), DSL.remainderType());
        protected final OpticFinder<List<Pair<String, Dynamic<?>>>> paletteFinder = DSL.fieldFinder("Palette", DSL.list(this.blockStateType));
        protected final List palette;
        protected final int index;
        @Nullable
        protected ArbitraryBitLengthIntArray storage;

        public Section(Typed<?> typed, Schema schema) {
            if (!Objects.equals(schema.getType(TypeReferences.BLOCK_STATE), this.blockStateType)) {
                throw new IllegalStateException("Block state type is not what was expected.");
            }
            Optional<List<Pair<String, Dynamic<?>>>> optional = typed.getOptional(this.paletteFinder);
            this.palette = optional.map(Section::lambda$new$0).orElse(ImmutableList.of());
            Dynamic<?> dynamic = typed.get(DSL.remainderFinder());
            this.index = dynamic.get("Y").asInt(0);
            this.func_212507_a(dynamic);
        }

        protected void func_212507_a(Dynamic<?> dynamic) {
            if (this.func_212508_a()) {
                this.storage = null;
            } else {
                long[] lArray = dynamic.get("BlockStates").asLongStream().toArray();
                int n = Math.max(4, DataFixUtils.ceillog2(this.palette.size()));
                this.storage = new ArbitraryBitLengthIntArray(n, 4096, lArray);
            }
        }

        public Typed<?> write(Typed<?> typed) {
            return this.isSkippable() ? typed : typed.update(DSL.remainderFinder(), this::lambda$write$1).set(this.paletteFinder, this.palette.stream().map(Section::lambda$write$2).collect(Collectors.toList()));
        }

        public boolean isSkippable() {
            return this.storage == null;
        }

        public int getBlock(int n) {
            return this.storage.func_233048_a_(n);
        }

        protected int getStateId(String string, boolean bl, int n) {
            return LEAVES.get(string) << 5 | (bl ? 16 : 0) | n;
        }

        int getIndex() {
            return this.index;
        }

        protected abstract boolean func_212508_a();

        private static Object lambda$write$2(Object object) {
            return Pair.of(TypeReferences.BLOCK_STATE.typeName(), object);
        }

        private Dynamic lambda$write$1(Dynamic dynamic) {
            return dynamic.set("BlockStates", dynamic.createLongList(Arrays.stream(this.storage.func_233047_a_())));
        }

        private static List lambda$new$0(List list) {
            return list.stream().map(Pair::getSecond).collect(Collectors.toList());
        }
    }
}

