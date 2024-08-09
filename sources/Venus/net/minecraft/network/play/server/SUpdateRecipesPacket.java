/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class SUpdateRecipesPacket
implements IPacket<IClientPlayNetHandler> {
    private List<IRecipe<?>> recipes;

    public SUpdateRecipesPacket() {
    }

    public SUpdateRecipesPacket(Collection<IRecipe<?>> collection) {
        this.recipes = Lists.newArrayList(collection);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleUpdateRecipes(this);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.recipes = Lists.newArrayList();
        int n = packetBuffer.readVarInt();
        for (int i = 0; i < n; ++i) {
            this.recipes.add(SUpdateRecipesPacket.func_218772_c(packetBuffer));
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.recipes.size());
        for (IRecipe<?> iRecipe : this.recipes) {
            SUpdateRecipesPacket.func_218771_a(iRecipe, packetBuffer);
        }
    }

    public List<IRecipe<?>> getRecipes() {
        return this.recipes;
    }

    public static IRecipe<?> func_218772_c(PacketBuffer packetBuffer) {
        ResourceLocation resourceLocation = packetBuffer.readResourceLocation();
        ResourceLocation resourceLocation2 = packetBuffer.readResourceLocation();
        return Registry.RECIPE_SERIALIZER.getOptional(resourceLocation).orElseThrow(() -> SUpdateRecipesPacket.lambda$func_218772_c$0(resourceLocation)).read(resourceLocation2, packetBuffer);
    }

    public static <T extends IRecipe<?>> void func_218771_a(T t, PacketBuffer packetBuffer) {
        packetBuffer.writeResourceLocation(Registry.RECIPE_SERIALIZER.getKey(t.getSerializer()));
        packetBuffer.writeResourceLocation(t.getId());
        t.getSerializer().write(packetBuffer, t);
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }

    private static IllegalArgumentException lambda$func_218772_c$0(ResourceLocation resourceLocation) {
        return new IllegalArgumentException("Unknown recipe serializer " + resourceLocation);
    }
}

