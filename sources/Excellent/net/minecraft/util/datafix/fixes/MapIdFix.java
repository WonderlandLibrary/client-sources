package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.*;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import net.minecraft.util.datafix.TypeReferences;

import java.util.Optional;

public class MapIdFix extends DataFix
{
    public MapIdFix(Schema p_i50424_1_, boolean p_i50424_2_)
    {
        super(p_i50424_1_, p_i50424_2_);
    }

    protected TypeRewriteRule makeRule()
    {
        Type<?> type = this.getInputSchema().getType(TypeReferences.SAVED_DATA);
        OpticFinder<?> opticfinder = type.findField("data");
        return this.fixTypeEverywhereTyped("Map id fix", type, (p_219839_1_) ->
        {
            Optional <? extends Typed<? >> optional = p_219839_1_.getOptionalTyped(opticfinder);
            return optional.isPresent() ? p_219839_1_ : p_219839_1_.update(DSL.remainderFinder(), (p_219838_0_) -> {
                return p_219838_0_.createMap(ImmutableMap.of(p_219838_0_.createString("data"), p_219838_0_));
            });
        });
    }
}
