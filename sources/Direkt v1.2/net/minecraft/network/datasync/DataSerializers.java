package net.minecraft.network.datasync;

import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Optional;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.text.ITextComponent;

public class DataSerializers {
	private static final IntIdentityHashBiMap<DataSerializer<?>> REGISTRY = new IntIdentityHashBiMap(16);
	public static final DataSerializer<Byte> BYTE = new DataSerializer<Byte>() {
		@Override
		public void write(PacketBuffer buf, Byte value) {
			buf.writeByte(value.byteValue());
		}

		@Override
		public Byte read(PacketBuffer buf) {
			return Byte.valueOf(buf.readByte());
		}

		@Override
		public DataParameter<Byte> createKey(int id) {
			return new DataParameter(id, this);
		}
	};
	public static final DataSerializer<Integer> VARINT = new DataSerializer<Integer>() {
		@Override
		public void write(PacketBuffer buf, Integer value) {
			buf.writeVarIntToBuffer(value.intValue());
		}

		@Override
		public Integer read(PacketBuffer buf) {
			return Integer.valueOf(buf.readVarIntFromBuffer());
		}

		@Override
		public DataParameter<Integer> createKey(int id) {
			return new DataParameter(id, this);
		}
	};
	public static final DataSerializer<Float> FLOAT = new DataSerializer<Float>() {
		@Override
		public void write(PacketBuffer buf, Float value) {
			buf.writeFloat(value.floatValue());
		}

		@Override
		public Float read(PacketBuffer buf) {
			return Float.valueOf(buf.readFloat());
		}

		@Override
		public DataParameter<Float> createKey(int id) {
			return new DataParameter(id, this);
		}
	};
	public static final DataSerializer<String> STRING = new DataSerializer<String>() {
		@Override
		public void write(PacketBuffer buf, String value) {
			buf.writeString(value);
		}

		@Override
		public String read(PacketBuffer buf) {
			return buf.readStringFromBuffer(32767);
		}

		@Override
		public DataParameter<String> createKey(int id) {
			return new DataParameter(id, this);
		}
	};
	public static final DataSerializer<ITextComponent> TEXT_COMPONENT = new DataSerializer<ITextComponent>() {
		@Override
		public void write(PacketBuffer buf, ITextComponent value) {
			buf.writeTextComponent(value);
		}

		@Override
		public ITextComponent read(PacketBuffer buf) throws java.io.IOException {
			return buf.readTextComponent();
		}

		@Override
		public DataParameter<ITextComponent> createKey(int id) {
			return new DataParameter(id, this);
		}
	};
	public static final DataSerializer<Optional<ItemStack>> OPTIONAL_ITEM_STACK = new DataSerializer<Optional<ItemStack>>() {
		@Override
		public void write(PacketBuffer buf, Optional<ItemStack> value) {
			buf.writeItemStackToBuffer(value.orNull());
		}

		@Override
		public Optional<ItemStack> read(PacketBuffer buf) throws java.io.IOException {
			return Optional.<ItemStack> fromNullable(buf.readItemStackFromBuffer());
		}

		@Override
		public DataParameter<Optional<ItemStack>> createKey(int id) {
			return new DataParameter(id, this);
		}
	};
	public static final DataSerializer<Optional<IBlockState>> OPTIONAL_BLOCK_STATE = new DataSerializer<Optional<IBlockState>>() {
		@Override
		public void write(PacketBuffer buf, Optional<IBlockState> value) {
			if (value.isPresent()) {
				buf.writeVarIntToBuffer(Block.getStateId(value.get()));
			} else {
				buf.writeVarIntToBuffer(0);
			}
		}

		@Override
		public Optional<IBlockState> read(PacketBuffer buf) {
			int i = buf.readVarIntFromBuffer();
			return i == 0 ? Optional.<IBlockState> absent() : Optional.of(Block.getStateById(i));
		}

		@Override
		public DataParameter<Optional<IBlockState>> createKey(int id) {
			return new DataParameter(id, this);
		}
	};
	public static final DataSerializer<Boolean> BOOLEAN = new DataSerializer<Boolean>() {
		@Override
		public void write(PacketBuffer buf, Boolean value) {
			buf.writeBoolean(value.booleanValue());
		}

		@Override
		public Boolean read(PacketBuffer buf) {
			return Boolean.valueOf(buf.readBoolean());
		}

		@Override
		public DataParameter<Boolean> createKey(int id) {
			return new DataParameter(id, this);
		}
	};
	public static final DataSerializer<Rotations> ROTATIONS = new DataSerializer<Rotations>() {
		@Override
		public void write(PacketBuffer buf, Rotations value) {
			buf.writeFloat(value.getX());
			buf.writeFloat(value.getY());
			buf.writeFloat(value.getZ());
		}

		@Override
		public Rotations read(PacketBuffer buf) {
			return new Rotations(buf.readFloat(), buf.readFloat(), buf.readFloat());
		}

		@Override
		public DataParameter<Rotations> createKey(int id) {
			return new DataParameter(id, this);
		}
	};
	public static final DataSerializer<BlockPos> BLOCK_POS = new DataSerializer<BlockPos>() {
		@Override
		public void write(PacketBuffer buf, BlockPos value) {
			buf.writeBlockPos(value);
		}

		@Override
		public BlockPos read(PacketBuffer buf) {
			return buf.readBlockPos();
		}

		@Override
		public DataParameter<BlockPos> createKey(int id) {
			return new DataParameter(id, this);
		}
	};
	public static final DataSerializer<Optional<BlockPos>> OPTIONAL_BLOCK_POS = new DataSerializer<Optional<BlockPos>>() {
		@Override
		public void write(PacketBuffer buf, Optional<BlockPos> value) {
			buf.writeBoolean(value.isPresent());

			if (value.isPresent()) {
				buf.writeBlockPos(value.get());
			}
		}

		@Override
		public Optional<BlockPos> read(PacketBuffer buf) {
			return !buf.readBoolean() ? Optional.<BlockPos> absent() : Optional.of(buf.readBlockPos());
		}

		@Override
		public DataParameter<Optional<BlockPos>> createKey(int id) {
			return new DataParameter(id, this);
		}
	};
	public static final DataSerializer<EnumFacing> FACING = new DataSerializer<EnumFacing>() {
		@Override
		public void write(PacketBuffer buf, EnumFacing value) {
			buf.writeEnumValue(value);
		}

		@Override
		public EnumFacing read(PacketBuffer buf) {
			return buf.readEnumValue(EnumFacing.class);
		}

		@Override
		public DataParameter<EnumFacing> createKey(int id) {
			return new DataParameter(id, this);
		}
	};
	public static final DataSerializer<Optional<UUID>> OPTIONAL_UNIQUE_ID = new DataSerializer<Optional<UUID>>() {
		@Override
		public void write(PacketBuffer buf, Optional<UUID> value) {
			buf.writeBoolean(value.isPresent());

			if (value.isPresent()) {
				buf.writeUuid(value.get());
			}
		}

		@Override
		public Optional<UUID> read(PacketBuffer buf) {
			return !buf.readBoolean() ? Optional.<UUID> absent() : Optional.of(buf.readUuid());
		}

		@Override
		public DataParameter<Optional<UUID>> createKey(int id) {
			return new DataParameter(id, this);
		}
	};

	public static void registerSerializer(DataSerializer<?> serializer) {
		REGISTRY.add(serializer);
	}

	@Nullable
	public static DataSerializer<?> getSerializer(int id) {
		return REGISTRY.get(id);
	}

	public static int getSerializerId(DataSerializer<?> serializer) {
		return REGISTRY.getId(serializer);
	}

	static {
		registerSerializer(BYTE);
		registerSerializer(VARINT);
		registerSerializer(FLOAT);
		registerSerializer(STRING);
		registerSerializer(TEXT_COMPONENT);
		registerSerializer(OPTIONAL_ITEM_STACK);
		registerSerializer(BOOLEAN);
		registerSerializer(ROTATIONS);
		registerSerializer(BLOCK_POS);
		registerSerializer(OPTIONAL_BLOCK_POS);
		registerSerializer(FACING);
		registerSerializer(OPTIONAL_UNIQUE_ID);
		registerSerializer(OPTIONAL_BLOCK_STATE);
	}
}
