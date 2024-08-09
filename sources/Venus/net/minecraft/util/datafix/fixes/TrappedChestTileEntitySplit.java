/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.AddNewChoices;
import net.minecraft.util.datafix.fixes.LeavesFix;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrappedChestTileEntitySplit
extends DataFix {
    private static final Logger LOGGER = LogManager.getLogger();

    public TrappedChestTileEntitySplit(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        Type<?> type = this.getOutputSchema().getType(TypeReferences.CHUNK);
        Type<?> type2 = type.findFieldType("Level");
        Type<?> type3 = type2.findFieldType("TileEntities");
        if (!(type3 instanceof List.ListType)) {
            throw new IllegalStateException("Tile entity type is not a list type.");
        }
        List.ListType listType = (List.ListType)type3;
        OpticFinder opticFinder = DSL.fieldFinder("TileEntities", listType);
        Type<?> type4 = this.getInputSchema().getType(TypeReferences.CHUNK);
        OpticFinder<?> opticFinder2 = type4.findField("Level");
        OpticFinder<?> opticFinder3 = opticFinder2.type().findField("Sections");
        Type<?> type5 = opticFinder3.type();
        if (!(type5 instanceof List.ListType)) {
            throw new IllegalStateException("Expecting sections to be a list.");
        }
        Type type6 = ((List.ListType)type5).getElement();
        OpticFinder opticFinder4 = DSL.typeFinder(type6);
        return TypeRewriteRule.seq(new AddNewChoices(this.getOutputSchema(), "AddTrappedChestFix", TypeReferences.BLOCK_ENTITY).makeRule(), this.fixTypeEverywhereTyped("Trapped Chest fix", type4, arg_0 -> this.lambda$makeRule$5(opticFinder2, opticFinder3, opticFinder4, opticFinder, arg_0)));
    }

    private Typed lambda$makeRule$5(OpticFinder opticFinder, OpticFinder opticFinder2, OpticFinder opticFinder3, OpticFinder opticFinder4, Typed typed) {
        return typed.updateTyped(opticFinder, arg_0 -> this.lambda$makeRule$4(opticFinder2, opticFinder3, opticFinder4, arg_0));
    }

    private Typed lambda$makeRule$4(OpticFinder opticFinder, OpticFinder opticFinder2, OpticFinder opticFinder3, Typed typed) {
        Optional optional = typed.getOptionalTyped(opticFinder);
        if (!optional.isPresent()) {
            return typed;
        }
        List list = optional.get().getAllTyped(opticFinder2);
        IntOpenHashSet intOpenHashSet = new IntOpenHashSet();
        for (Typed typed2 : list) {
            Section section = new Section(typed2, this.getInputSchema());
            if (section.isSkippable()) continue;
            for (int i = 0; i < 4096; ++i) {
                int n = section.getBlock(i);
                if (!section.func_212511_a(n)) continue;
                intOpenHashSet.add(section.getIndex() << 12 | i);
            }
        }
        Dynamic<?> dynamic = typed.get(DSL.remainderFinder());
        int n = dynamic.get("xPos").asInt(0);
        int n2 = dynamic.get("zPos").asInt(0);
        TaggedChoice.TaggedChoiceType<?> taggedChoiceType = this.getInputSchema().findChoiceType(TypeReferences.BLOCK_ENTITY);
        return typed.updateTyped(opticFinder3, arg_0 -> TrappedChestTileEntitySplit.lambda$makeRule$3(taggedChoiceType, n, n2, intOpenHashSet, arg_0));
    }

    private static Typed lambda$makeRule$3(TaggedChoice.TaggedChoiceType taggedChoiceType, int n, int n2, IntSet intSet, Typed typed) {
        return typed.updateTyped(taggedChoiceType.finder(), arg_0 -> TrappedChestTileEntitySplit.lambda$makeRule$2(n, n2, intSet, taggedChoiceType, arg_0));
    }

    private static Typed lambda$makeRule$2(int n, int n2, IntSet intSet, TaggedChoice.TaggedChoiceType taggedChoiceType, Typed typed) {
        int n3;
        int n4;
        Dynamic<?> dynamic = typed.getOrCreate(DSL.remainderFinder());
        int n5 = dynamic.get("x").asInt(0) - (n << 4);
        return intSet.contains(LeavesFix.getIndex(n5, n4 = dynamic.get("y").asInt(0), n3 = dynamic.get("z").asInt(0) - (n2 << 4))) ? typed.update(taggedChoiceType.finder(), TrappedChestTileEntitySplit::lambda$makeRule$1) : typed;
    }

    private static Pair lambda$makeRule$1(Pair pair) {
        return pair.mapFirst(TrappedChestTileEntitySplit::lambda$makeRule$0);
    }

    private static String lambda$makeRule$0(String string) {
        if (!Objects.equals(string, "minecraft:chest")) {
            LOGGER.warn("Block Entity was expected to be a chest");
        }
        return "minecraft:trapped_chest";
    }

    public static final class Section
    extends LeavesFix.Section {
        @Nullable
        private IntSet field_212512_f;

        public Section(Typed<?> typed, Schema schema) {
            super(typed, schema);
        }

        @Override
        protected boolean func_212508_a() {
            this.field_212512_f = new IntOpenHashSet();
            for (int i = 0; i < this.palette.size(); ++i) {
                Dynamic dynamic = (Dynamic)this.palette.get(i);
                String string = dynamic.get("Name").asString("");
                if (!Objects.equals(string, "minecraft:trapped_chest")) continue;
                this.field_212512_f.add(i);
            }
            return this.field_212512_f.isEmpty();
        }

        public boolean func_212511_a(int n) {
            return this.field_212512_f.contains(n);
        }
    }
}

