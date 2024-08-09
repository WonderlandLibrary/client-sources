/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.datasync;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Pose;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.Direction;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;

public class DataSerializers {
    private static final IntIdentityHashBiMap<IDataSerializer<?>> REGISTRY = new IntIdentityHashBiMap(16);
    public static final IDataSerializer<Byte> BYTE = new IDataSerializer<Byte>(){

        @Override
        public void write(PacketBuffer packetBuffer, Byte by) {
            packetBuffer.writeByte(by.byteValue());
        }

        @Override
        public Byte read(PacketBuffer packetBuffer) {
            return packetBuffer.readByte();
        }

        @Override
        public Byte copyValue(Byte by) {
            return by;
        }

        @Override
        public Object copyValue(Object object) {
            return this.copyValue((Byte)object);
        }

        @Override
        public Object read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(PacketBuffer packetBuffer, Object object) {
            this.write(packetBuffer, (Byte)object);
        }
    };
    public static final IDataSerializer<Integer> VARINT = new IDataSerializer<Integer>(){

        @Override
        public void write(PacketBuffer packetBuffer, Integer n) {
            packetBuffer.writeVarInt(n);
        }

        @Override
        public Integer read(PacketBuffer packetBuffer) {
            return packetBuffer.readVarInt();
        }

        @Override
        public Integer copyValue(Integer n) {
            return n;
        }

        @Override
        public Object copyValue(Object object) {
            return this.copyValue((Integer)object);
        }

        @Override
        public Object read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(PacketBuffer packetBuffer, Object object) {
            this.write(packetBuffer, (Integer)object);
        }
    };
    public static final IDataSerializer<Float> FLOAT = new IDataSerializer<Float>(){

        @Override
        public void write(PacketBuffer packetBuffer, Float f) {
            packetBuffer.writeFloat(f.floatValue());
        }

        @Override
        public Float read(PacketBuffer packetBuffer) {
            return Float.valueOf(packetBuffer.readFloat());
        }

        @Override
        public Float copyValue(Float f) {
            return f;
        }

        @Override
        public Object copyValue(Object object) {
            return this.copyValue((Float)object);
        }

        @Override
        public Object read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(PacketBuffer packetBuffer, Object object) {
            this.write(packetBuffer, (Float)object);
        }
    };
    public static final IDataSerializer<String> STRING = new IDataSerializer<String>(){

        @Override
        public void write(PacketBuffer packetBuffer, String string) {
            packetBuffer.writeString(string);
        }

        @Override
        public String read(PacketBuffer packetBuffer) {
            return packetBuffer.readString(Short.MAX_VALUE);
        }

        @Override
        public String copyValue(String string) {
            return string;
        }

        @Override
        public Object copyValue(Object object) {
            return this.copyValue((String)object);
        }

        @Override
        public Object read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(PacketBuffer packetBuffer, Object object) {
            this.write(packetBuffer, (String)object);
        }
    };
    public static final IDataSerializer<ITextComponent> TEXT_COMPONENT = new IDataSerializer<ITextComponent>(){

        @Override
        public void write(PacketBuffer packetBuffer, ITextComponent iTextComponent) {
            packetBuffer.writeTextComponent(iTextComponent);
        }

        @Override
        public ITextComponent read(PacketBuffer packetBuffer) {
            return packetBuffer.readTextComponent();
        }

        @Override
        public ITextComponent copyValue(ITextComponent iTextComponent) {
            return iTextComponent;
        }

        @Override
        public Object copyValue(Object object) {
            return this.copyValue((ITextComponent)object);
        }

        @Override
        public Object read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(PacketBuffer packetBuffer, Object object) {
            this.write(packetBuffer, (ITextComponent)object);
        }
    };
    public static final IDataSerializer<Optional<ITextComponent>> OPTIONAL_TEXT_COMPONENT = new IDataSerializer<Optional<ITextComponent>>(){

        @Override
        public void write(PacketBuffer packetBuffer, Optional<ITextComponent> optional) {
            if (optional.isPresent()) {
                packetBuffer.writeBoolean(false);
                packetBuffer.writeTextComponent(optional.get());
            } else {
                packetBuffer.writeBoolean(true);
            }
        }

        @Override
        public Optional<ITextComponent> read(PacketBuffer packetBuffer) {
            return packetBuffer.readBoolean() ? Optional.of(packetBuffer.readTextComponent()) : Optional.empty();
        }

        @Override
        public Optional<ITextComponent> copyValue(Optional<ITextComponent> optional) {
            return optional;
        }

        @Override
        public Object copyValue(Object object) {
            return this.copyValue((Optional)object);
        }

        @Override
        public Object read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(PacketBuffer packetBuffer, Object object) {
            this.write(packetBuffer, (Optional)object);
        }
    };
    public static final IDataSerializer<ItemStack> ITEMSTACK = new IDataSerializer<ItemStack>(){

        @Override
        public void write(PacketBuffer packetBuffer, ItemStack itemStack) {
            packetBuffer.writeItemStack(itemStack);
        }

        @Override
        public ItemStack read(PacketBuffer packetBuffer) {
            return packetBuffer.readItemStack();
        }

        @Override
        public ItemStack copyValue(ItemStack itemStack) {
            return itemStack.copy();
        }

        @Override
        public Object copyValue(Object object) {
            return this.copyValue((ItemStack)object);
        }

        @Override
        public Object read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(PacketBuffer packetBuffer, Object object) {
            this.write(packetBuffer, (ItemStack)object);
        }
    };
    public static final IDataSerializer<Optional<BlockState>> OPTIONAL_BLOCK_STATE = new IDataSerializer<Optional<BlockState>>(){

        @Override
        public void write(PacketBuffer packetBuffer, Optional<BlockState> optional) {
            if (optional.isPresent()) {
                packetBuffer.writeVarInt(Block.getStateId(optional.get()));
            } else {
                packetBuffer.writeVarInt(0);
            }
        }

        @Override
        public Optional<BlockState> read(PacketBuffer packetBuffer) {
            int n = packetBuffer.readVarInt();
            return n == 0 ? Optional.empty() : Optional.of(Block.getStateById(n));
        }

        @Override
        public Optional<BlockState> copyValue(Optional<BlockState> optional) {
            return optional;
        }

        @Override
        public Object copyValue(Object object) {
            return this.copyValue((Optional)object);
        }

        @Override
        public Object read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(PacketBuffer packetBuffer, Object object) {
            this.write(packetBuffer, (Optional)object);
        }
    };
    public static final IDataSerializer<Boolean> BOOLEAN = new IDataSerializer<Boolean>(){

        @Override
        public void write(PacketBuffer packetBuffer, Boolean bl) {
            packetBuffer.writeBoolean(bl);
        }

        @Override
        public Boolean read(PacketBuffer packetBuffer) {
            return packetBuffer.readBoolean();
        }

        @Override
        public Boolean copyValue(Boolean bl) {
            return bl;
        }

        @Override
        public Object copyValue(Object object) {
            return this.copyValue((Boolean)object);
        }

        @Override
        public Object read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(PacketBuffer packetBuffer, Object object) {
            this.write(packetBuffer, (Boolean)object);
        }
    };
    public static final IDataSerializer<IParticleData> PARTICLE_DATA = new IDataSerializer<IParticleData>(){

        @Override
        public void write(PacketBuffer packetBuffer, IParticleData iParticleData) {
            packetBuffer.writeVarInt(Registry.PARTICLE_TYPE.getId(iParticleData.getType()));
            iParticleData.write(packetBuffer);
        }

        @Override
        public IParticleData read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer, (ParticleType)Registry.PARTICLE_TYPE.getByValue(packetBuffer.readVarInt()));
        }

        private <T extends IParticleData> T read(PacketBuffer packetBuffer, ParticleType<T> particleType) {
            return particleType.getDeserializer().read(particleType, packetBuffer);
        }

        @Override
        public IParticleData copyValue(IParticleData iParticleData) {
            return iParticleData;
        }

        @Override
        public Object copyValue(Object object) {
            return this.copyValue((IParticleData)object);
        }

        @Override
        public Object read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(PacketBuffer packetBuffer, Object object) {
            this.write(packetBuffer, (IParticleData)object);
        }
    };
    public static final IDataSerializer<Rotations> ROTATIONS = new IDataSerializer<Rotations>(){

        @Override
        public void write(PacketBuffer packetBuffer, Rotations rotations) {
            packetBuffer.writeFloat(rotations.getX());
            packetBuffer.writeFloat(rotations.getY());
            packetBuffer.writeFloat(rotations.getZ());
        }

        @Override
        public Rotations read(PacketBuffer packetBuffer) {
            return new Rotations(packetBuffer.readFloat(), packetBuffer.readFloat(), packetBuffer.readFloat());
        }

        @Override
        public Rotations copyValue(Rotations rotations) {
            return rotations;
        }

        @Override
        public Object copyValue(Object object) {
            return this.copyValue((Rotations)object);
        }

        @Override
        public Object read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(PacketBuffer packetBuffer, Object object) {
            this.write(packetBuffer, (Rotations)object);
        }
    };
    public static final IDataSerializer<BlockPos> BLOCK_POS = new IDataSerializer<BlockPos>(){

        @Override
        public void write(PacketBuffer packetBuffer, BlockPos blockPos) {
            packetBuffer.writeBlockPos(blockPos);
        }

        @Override
        public BlockPos read(PacketBuffer packetBuffer) {
            return packetBuffer.readBlockPos();
        }

        @Override
        public BlockPos copyValue(BlockPos blockPos) {
            return blockPos;
        }

        @Override
        public Object copyValue(Object object) {
            return this.copyValue((BlockPos)object);
        }

        @Override
        public Object read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(PacketBuffer packetBuffer, Object object) {
            this.write(packetBuffer, (BlockPos)object);
        }
    };
    public static final IDataSerializer<Optional<BlockPos>> OPTIONAL_BLOCK_POS = new IDataSerializer<Optional<BlockPos>>(){

        @Override
        public void write(PacketBuffer packetBuffer, Optional<BlockPos> optional) {
            packetBuffer.writeBoolean(optional.isPresent());
            if (optional.isPresent()) {
                packetBuffer.writeBlockPos(optional.get());
            }
        }

        @Override
        public Optional<BlockPos> read(PacketBuffer packetBuffer) {
            return !packetBuffer.readBoolean() ? Optional.empty() : Optional.of(packetBuffer.readBlockPos());
        }

        @Override
        public Optional<BlockPos> copyValue(Optional<BlockPos> optional) {
            return optional;
        }

        @Override
        public Object copyValue(Object object) {
            return this.copyValue((Optional)object);
        }

        @Override
        public Object read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(PacketBuffer packetBuffer, Object object) {
            this.write(packetBuffer, (Optional)object);
        }
    };
    public static final IDataSerializer<Direction> DIRECTION = new IDataSerializer<Direction>(){

        @Override
        public void write(PacketBuffer packetBuffer, Direction direction) {
            packetBuffer.writeEnumValue(direction);
        }

        @Override
        public Direction read(PacketBuffer packetBuffer) {
            return packetBuffer.readEnumValue(Direction.class);
        }

        @Override
        public Direction copyValue(Direction direction) {
            return direction;
        }

        @Override
        public Object copyValue(Object object) {
            return this.copyValue((Direction)object);
        }

        @Override
        public Object read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(PacketBuffer packetBuffer, Object object) {
            this.write(packetBuffer, (Direction)object);
        }
    };
    public static final IDataSerializer<Optional<UUID>> OPTIONAL_UNIQUE_ID = new IDataSerializer<Optional<UUID>>(){

        @Override
        public void write(PacketBuffer packetBuffer, Optional<UUID> optional) {
            packetBuffer.writeBoolean(optional.isPresent());
            if (optional.isPresent()) {
                packetBuffer.writeUniqueId(optional.get());
            }
        }

        @Override
        public Optional<UUID> read(PacketBuffer packetBuffer) {
            return !packetBuffer.readBoolean() ? Optional.empty() : Optional.of(packetBuffer.readUniqueId());
        }

        @Override
        public Optional<UUID> copyValue(Optional<UUID> optional) {
            return optional;
        }

        @Override
        public Object copyValue(Object object) {
            return this.copyValue((Optional)object);
        }

        @Override
        public Object read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(PacketBuffer packetBuffer, Object object) {
            this.write(packetBuffer, (Optional)object);
        }
    };
    public static final IDataSerializer<CompoundNBT> COMPOUND_NBT = new IDataSerializer<CompoundNBT>(){

        @Override
        public void write(PacketBuffer packetBuffer, CompoundNBT compoundNBT) {
            packetBuffer.writeCompoundTag(compoundNBT);
        }

        @Override
        public CompoundNBT read(PacketBuffer packetBuffer) {
            return packetBuffer.readCompoundTag();
        }

        @Override
        public CompoundNBT copyValue(CompoundNBT compoundNBT) {
            return compoundNBT.copy();
        }

        @Override
        public Object copyValue(Object object) {
            return this.copyValue((CompoundNBT)object);
        }

        @Override
        public Object read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(PacketBuffer packetBuffer, Object object) {
            this.write(packetBuffer, (CompoundNBT)object);
        }
    };
    public static final IDataSerializer<VillagerData> VILLAGER_DATA = new IDataSerializer<VillagerData>(){

        @Override
        public void write(PacketBuffer packetBuffer, VillagerData villagerData) {
            packetBuffer.writeVarInt(Registry.VILLAGER_TYPE.getId(villagerData.getType()));
            packetBuffer.writeVarInt(Registry.VILLAGER_PROFESSION.getId(villagerData.getProfession()));
            packetBuffer.writeVarInt(villagerData.getLevel());
        }

        @Override
        public VillagerData read(PacketBuffer packetBuffer) {
            return new VillagerData(Registry.VILLAGER_TYPE.getByValue(packetBuffer.readVarInt()), Registry.VILLAGER_PROFESSION.getByValue(packetBuffer.readVarInt()), packetBuffer.readVarInt());
        }

        @Override
        public VillagerData copyValue(VillagerData villagerData) {
            return villagerData;
        }

        @Override
        public Object copyValue(Object object) {
            return this.copyValue((VillagerData)object);
        }

        @Override
        public Object read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(PacketBuffer packetBuffer, Object object) {
            this.write(packetBuffer, (VillagerData)object);
        }
    };
    public static final IDataSerializer<OptionalInt> OPTIONAL_VARINT = new IDataSerializer<OptionalInt>(){

        @Override
        public void write(PacketBuffer packetBuffer, OptionalInt optionalInt) {
            packetBuffer.writeVarInt(optionalInt.orElse(-1) + 1);
        }

        @Override
        public OptionalInt read(PacketBuffer packetBuffer) {
            int n = packetBuffer.readVarInt();
            return n == 0 ? OptionalInt.empty() : OptionalInt.of(n - 1);
        }

        @Override
        public OptionalInt copyValue(OptionalInt optionalInt) {
            return optionalInt;
        }

        @Override
        public Object copyValue(Object object) {
            return this.copyValue((OptionalInt)object);
        }

        @Override
        public Object read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(PacketBuffer packetBuffer, Object object) {
            this.write(packetBuffer, (OptionalInt)object);
        }
    };
    public static final IDataSerializer<Pose> POSE = new IDataSerializer<Pose>(){

        @Override
        public void write(PacketBuffer packetBuffer, Pose pose) {
            packetBuffer.writeEnumValue(pose);
        }

        @Override
        public Pose read(PacketBuffer packetBuffer) {
            return packetBuffer.readEnumValue(Pose.class);
        }

        @Override
        public Pose copyValue(Pose pose) {
            return pose;
        }

        @Override
        public Object copyValue(Object object) {
            return this.copyValue((Pose)((Object)object));
        }

        @Override
        public Object read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(PacketBuffer packetBuffer, Object object) {
            this.write(packetBuffer, (Pose)((Object)object));
        }
    };

    public static void registerSerializer(IDataSerializer<?> iDataSerializer) {
        REGISTRY.add(iDataSerializer);
    }

    @Nullable
    public static IDataSerializer<?> getSerializer(int n) {
        return REGISTRY.getByValue(n);
    }

    public static int getSerializerId(IDataSerializer<?> iDataSerializer) {
        return REGISTRY.getId(iDataSerializer);
    }

    static {
        DataSerializers.registerSerializer(BYTE);
        DataSerializers.registerSerializer(VARINT);
        DataSerializers.registerSerializer(FLOAT);
        DataSerializers.registerSerializer(STRING);
        DataSerializers.registerSerializer(TEXT_COMPONENT);
        DataSerializers.registerSerializer(OPTIONAL_TEXT_COMPONENT);
        DataSerializers.registerSerializer(ITEMSTACK);
        DataSerializers.registerSerializer(BOOLEAN);
        DataSerializers.registerSerializer(ROTATIONS);
        DataSerializers.registerSerializer(BLOCK_POS);
        DataSerializers.registerSerializer(OPTIONAL_BLOCK_POS);
        DataSerializers.registerSerializer(DIRECTION);
        DataSerializers.registerSerializer(OPTIONAL_UNIQUE_ID);
        DataSerializers.registerSerializer(OPTIONAL_BLOCK_STATE);
        DataSerializers.registerSerializer(COMPOUND_NBT);
        DataSerializers.registerSerializer(PARTICLE_DATA);
        DataSerializers.registerSerializer(VILLAGER_DATA);
        DataSerializers.registerSerializer(OPTIONAL_VARINT);
        DataSerializers.registerSerializer(POSE);
    }
}

