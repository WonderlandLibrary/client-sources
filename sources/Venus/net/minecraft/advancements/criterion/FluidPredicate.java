/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.server.ServerWorld;

public class FluidPredicate {
    public static final FluidPredicate ANY = new FluidPredicate(null, null, StatePropertiesPredicate.EMPTY);
    @Nullable
    private final ITag<Fluid> fluidTag;
    @Nullable
    private final Fluid fluid;
    private final StatePropertiesPredicate stateCondition;

    public FluidPredicate(@Nullable ITag<Fluid> iTag, @Nullable Fluid fluid, StatePropertiesPredicate statePropertiesPredicate) {
        this.fluidTag = iTag;
        this.fluid = fluid;
        this.stateCondition = statePropertiesPredicate;
    }

    public boolean test(ServerWorld serverWorld, BlockPos blockPos) {
        if (this == ANY) {
            return false;
        }
        if (!serverWorld.isBlockPresent(blockPos)) {
            return true;
        }
        FluidState fluidState = serverWorld.getFluidState(blockPos);
        Fluid fluid = fluidState.getFluid();
        if (this.fluidTag != null && !this.fluidTag.contains(fluid)) {
            return true;
        }
        if (this.fluid != null && fluid != this.fluid) {
            return true;
        }
        return this.stateCondition.matches(fluidState);
    }

    public static FluidPredicate deserialize(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            Object object;
            ITag<Fluid> iTag;
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "fluid");
            Fluid fluid = null;
            if (jsonObject.has("fluid")) {
                iTag = new ResourceLocation(JSONUtils.getString(jsonObject, "fluid"));
                fluid = Registry.FLUID.getOrDefault((ResourceLocation)((Object)iTag));
            }
            iTag = null;
            if (jsonObject.has("tag")) {
                object = new ResourceLocation(JSONUtils.getString(jsonObject, "tag"));
                iTag = TagCollectionManager.getManager().getFluidTags().get((ResourceLocation)object);
                if (iTag == null) {
                    throw new JsonSyntaxException("Unknown fluid tag '" + (ResourceLocation)object + "'");
                }
            }
            object = StatePropertiesPredicate.deserializeProperties(jsonObject.get("state"));
            return new FluidPredicate(iTag, fluid, (StatePropertiesPredicate)object);
        }
        return ANY;
    }

    public JsonElement serialize() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        if (this.fluid != null) {
            jsonObject.addProperty("fluid", Registry.FLUID.getKey(this.fluid).toString());
        }
        if (this.fluidTag != null) {
            jsonObject.addProperty("tag", TagCollectionManager.getManager().getFluidTags().getValidatedIdFromTag(this.fluidTag).toString());
        }
        jsonObject.add("state", this.stateCondition.toJsonElement());
        return jsonObject;
    }
}

