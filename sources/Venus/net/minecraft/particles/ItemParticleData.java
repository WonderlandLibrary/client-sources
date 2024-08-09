/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.command.arguments.ItemParser;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.registry.Registry;

public class ItemParticleData
implements IParticleData {
    public static final IParticleData.IDeserializer<ItemParticleData> DESERIALIZER = new IParticleData.IDeserializer<ItemParticleData>(){

        @Override
        public ItemParticleData deserialize(ParticleType<ItemParticleData> particleType, StringReader stringReader) throws CommandSyntaxException {
            stringReader.expect(' ');
            ItemParser itemParser = new ItemParser(stringReader, false).parse();
            ItemStack itemStack = new ItemInput(itemParser.getItem(), itemParser.getNbt()).createStack(1, true);
            return new ItemParticleData(particleType, itemStack);
        }

        @Override
        public ItemParticleData read(ParticleType<ItemParticleData> particleType, PacketBuffer packetBuffer) {
            return new ItemParticleData(particleType, packetBuffer.readItemStack());
        }

        @Override
        public IParticleData read(ParticleType particleType, PacketBuffer packetBuffer) {
            return this.read(particleType, packetBuffer);
        }

        @Override
        public IParticleData deserialize(ParticleType particleType, StringReader stringReader) throws CommandSyntaxException {
            return this.deserialize(particleType, stringReader);
        }
    };
    private final ParticleType<ItemParticleData> particleType;
    private final ItemStack itemStack;

    public static Codec<ItemParticleData> func_239809_a_(ParticleType<ItemParticleData> particleType) {
        return ItemStack.CODEC.xmap(arg_0 -> ItemParticleData.lambda$func_239809_a_$0(particleType, arg_0), ItemParticleData::lambda$func_239809_a_$1);
    }

    public ItemParticleData(ParticleType<ItemParticleData> particleType, ItemStack itemStack) {
        this.particleType = particleType;
        this.itemStack = itemStack;
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeItemStack(this.itemStack);
    }

    @Override
    public String getParameters() {
        return Registry.PARTICLE_TYPE.getKey(this.getType()) + " " + new ItemInput(this.itemStack.getItem(), this.itemStack.getTag()).serialize();
    }

    public ParticleType<ItemParticleData> getType() {
        return this.particleType;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    private static ItemStack lambda$func_239809_a_$1(ItemParticleData itemParticleData) {
        return itemParticleData.itemStack;
    }

    private static ItemParticleData lambda$func_239809_a_$0(ParticleType particleType, ItemStack itemStack) {
        return new ItemParticleData(particleType, itemStack);
    }
}

