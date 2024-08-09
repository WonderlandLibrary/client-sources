/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.command.arguments.BlockStateParser;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.registry.Registry;

public class BlockParticleData
implements IParticleData {
    public static final IParticleData.IDeserializer<BlockParticleData> DESERIALIZER = new IParticleData.IDeserializer<BlockParticleData>(){

        @Override
        public BlockParticleData deserialize(ParticleType<BlockParticleData> particleType, StringReader stringReader) throws CommandSyntaxException {
            stringReader.expect(' ');
            return new BlockParticleData(particleType, new BlockStateParser(stringReader, false).parse(true).getState());
        }

        @Override
        public BlockParticleData read(ParticleType<BlockParticleData> particleType, PacketBuffer packetBuffer) {
            return new BlockParticleData(particleType, Block.BLOCK_STATE_IDS.getByValue(packetBuffer.readVarInt()));
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
    private final ParticleType<BlockParticleData> particleType;
    private final BlockState blockState;

    public static Codec<BlockParticleData> func_239800_a_(ParticleType<BlockParticleData> particleType) {
        return BlockState.CODEC.xmap(arg_0 -> BlockParticleData.lambda$func_239800_a_$0(particleType, arg_0), BlockParticleData::lambda$func_239800_a_$1);
    }

    public BlockParticleData(ParticleType<BlockParticleData> particleType, BlockState blockState) {
        this.particleType = particleType;
        this.blockState = blockState;
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeVarInt(Block.BLOCK_STATE_IDS.getId(this.blockState));
    }

    @Override
    public String getParameters() {
        return Registry.PARTICLE_TYPE.getKey(this.getType()) + " " + BlockStateParser.toString(this.blockState);
    }

    public ParticleType<BlockParticleData> getType() {
        return this.particleType;
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    private static BlockState lambda$func_239800_a_$1(BlockParticleData blockParticleData) {
        return blockParticleData.blockState;
    }

    private static BlockParticleData lambda$func_239800_a_$0(ParticleType particleType, BlockState blockState) {
        return new BlockParticleData(particleType, blockState);
    }
}

