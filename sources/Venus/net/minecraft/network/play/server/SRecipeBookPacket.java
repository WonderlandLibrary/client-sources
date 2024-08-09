/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.item.crafting.RecipeBookStatus;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class SRecipeBookPacket
implements IPacket<IClientPlayNetHandler> {
    private State state;
    private List<ResourceLocation> recipes;
    private List<ResourceLocation> displayedRecipes;
    private RecipeBookStatus field_244301_d;

    public SRecipeBookPacket() {
    }

    public SRecipeBookPacket(State state, Collection<ResourceLocation> collection, Collection<ResourceLocation> collection2, RecipeBookStatus recipeBookStatus) {
        this.state = state;
        this.recipes = ImmutableList.copyOf(collection);
        this.displayedRecipes = ImmutableList.copyOf(collection2);
        this.field_244301_d = recipeBookStatus;
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleRecipeBook(this);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        int n;
        this.state = packetBuffer.readEnumValue(State.class);
        this.field_244301_d = RecipeBookStatus.func_242157_a(packetBuffer);
        int n2 = packetBuffer.readVarInt();
        this.recipes = Lists.newArrayList();
        for (n = 0; n < n2; ++n) {
            this.recipes.add(packetBuffer.readResourceLocation());
        }
        if (this.state == State.INIT) {
            n2 = packetBuffer.readVarInt();
            this.displayedRecipes = Lists.newArrayList();
            for (n = 0; n < n2; ++n) {
                this.displayedRecipes.add(packetBuffer.readResourceLocation());
            }
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.state);
        this.field_244301_d.func_242161_b(packetBuffer);
        packetBuffer.writeVarInt(this.recipes.size());
        for (ResourceLocation resourceLocation : this.recipes) {
            packetBuffer.writeResourceLocation(resourceLocation);
        }
        if (this.state == State.INIT) {
            packetBuffer.writeVarInt(this.displayedRecipes.size());
            for (ResourceLocation resourceLocation : this.displayedRecipes) {
                packetBuffer.writeResourceLocation(resourceLocation);
            }
        }
    }

    public List<ResourceLocation> getRecipes() {
        return this.recipes;
    }

    public List<ResourceLocation> getDisplayedRecipes() {
        return this.displayedRecipes;
    }

    public RecipeBookStatus func_244302_d() {
        return this.field_244301_d;
    }

    public State getState() {
        return this.state;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }

    public static enum State {
        INIT,
        ADD,
        REMOVE;

    }
}

