package net.minecraft.nbt;

import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;

public final class NBTUtil {
	@Nullable

	/**
	 * Reads and returns a GameProfile that has been saved to the passed in NBTTagCompound
	 */
	public static GameProfile readGameProfileFromNBT(NBTTagCompound compound) {
		String s = null;
		String s1 = null;

		if (compound.hasKey("Name", 8)) {
			s = compound.getString("Name");
		}

		if (compound.hasKey("Id", 8)) {
			s1 = compound.getString("Id");
		}

		if (StringUtils.isNullOrEmpty(s) && StringUtils.isNullOrEmpty(s1)) {
			return null;
		} else {
			UUID uuid;

			try {
				uuid = UUID.fromString(s1);
			} catch (Throwable var12) {
				uuid = null;
			}

			GameProfile gameprofile = new GameProfile(uuid, s);

			if (compound.hasKey("Properties", 10)) {
				NBTTagCompound nbttagcompound = compound.getCompoundTag("Properties");

				for (String s2 : nbttagcompound.getKeySet()) {
					NBTTagList nbttaglist = nbttagcompound.getTagList(s2, 10);

					for (int i = 0; i < nbttaglist.tagCount(); ++i) {
						NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
						String s3 = nbttagcompound1.getString("Value");

						if (nbttagcompound1.hasKey("Signature", 8)) {
							gameprofile.getProperties().put(s2, new Property(s2, s3, nbttagcompound1.getString("Signature")));
						} else {
							gameprofile.getProperties().put(s2, new Property(s2, s3));
						}
					}
				}
			}

			return gameprofile;
		}
	}

	/**
	 * Writes a GameProfile to an NBTTagCompound.
	 */
	public static NBTTagCompound writeGameProfile(NBTTagCompound tagCompound, GameProfile profile) {
		if (!StringUtils.isNullOrEmpty(profile.getName())) {
			tagCompound.setString("Name", profile.getName());
		}

		if (profile.getId() != null) {
			tagCompound.setString("Id", profile.getId().toString());
		}

		if (!profile.getProperties().isEmpty()) {
			NBTTagCompound nbttagcompound = new NBTTagCompound();

			for (String s : profile.getProperties().keySet()) {
				NBTTagList nbttaglist = new NBTTagList();

				for (Property property : profile.getProperties().get(s)) {
					NBTTagCompound nbttagcompound1 = new NBTTagCompound();
					nbttagcompound1.setString("Value", property.getValue());

					if (property.hasSignature()) {
						nbttagcompound1.setString("Signature", property.getSignature());
					}

					nbttaglist.appendTag(nbttagcompound1);
				}

				nbttagcompound.setTag(s, nbttaglist);
			}

			tagCompound.setTag("Properties", nbttagcompound);
		}

		return tagCompound;
	}

	@VisibleForTesting
	public static boolean areNBTEquals(NBTBase nbt1, NBTBase nbt2, boolean compareTagList) {
		if (nbt1 == nbt2) {
			return true;
		} else if (nbt1 == null) {
			return true;
		} else if (nbt2 == null) {
			return false;
		} else if (!nbt1.getClass().equals(nbt2.getClass())) {
			return false;
		} else if (nbt1 instanceof NBTTagCompound) {
			NBTTagCompound nbttagcompound = (NBTTagCompound) nbt1;
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbt2;

			for (String s : nbttagcompound.getKeySet()) {
				NBTBase nbtbase1 = nbttagcompound.getTag(s);

				if (!areNBTEquals(nbtbase1, nbttagcompound1.getTag(s), compareTagList)) { return false; }
			}

			return true;
		} else if ((nbt1 instanceof NBTTagList) && compareTagList) {
			NBTTagList nbttaglist = (NBTTagList) nbt1;
			NBTTagList nbttaglist1 = (NBTTagList) nbt2;

			if (nbttaglist.tagCount() == 0) {
				return nbttaglist1.tagCount() == 0;
			} else {
				for (int i = 0; i < nbttaglist.tagCount(); ++i) {
					NBTBase nbtbase = nbttaglist.get(i);
					boolean flag = false;

					for (int j = 0; j < nbttaglist1.tagCount(); ++j) {
						if (areNBTEquals(nbtbase, nbttaglist1.get(j), compareTagList)) {
							flag = true;
							break;
						}
					}

					if (!flag) { return false; }
				}

				return true;
			}
		} else {
			return nbt1.equals(nbt2);
		}
	}

	/**
	 * Creates a new NBTTagCompound which stores a UUID.
	 */
	public static NBTTagCompound createUUIDTag(UUID uuid) {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setLong("M", uuid.getMostSignificantBits());
		nbttagcompound.setLong("L", uuid.getLeastSignificantBits());
		return nbttagcompound;
	}

	/**
	 * Reads a UUID from the passed NBTTagCompound.
	 */
	public static UUID getUUIDFromTag(NBTTagCompound tag) {
		return new UUID(tag.getLong("M"), tag.getLong("L"));
	}

	/**
	 * Creates a BlockPos object from the data stored in the passed NBTTagCompound.
	 */
	public static BlockPos getPosFromTag(NBTTagCompound tag) {
		return new BlockPos(tag.getInteger("X"), tag.getInteger("Y"), tag.getInteger("Z"));
	}

	/**
	 * Creates a new NBTTagCompound from a BlockPos.
	 */
	public static NBTTagCompound createPosTag(BlockPos pos) {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setInteger("X", pos.getX());
		nbttagcompound.setInteger("Y", pos.getY());
		nbttagcompound.setInteger("Z", pos.getZ());
		return nbttagcompound;
	}

	public static IBlockState func_190008_d(NBTTagCompound p_190008_0_) {
		if (!p_190008_0_.hasKey("Name", 8)) {
			return Blocks.AIR.getDefaultState();
		} else {
			Block block = Block.REGISTRY.getObject(new ResourceLocation(p_190008_0_.getString("Name")));
			IBlockState iblockstate = block.getDefaultState();

			if (p_190008_0_.hasKey("Properties", 10)) {
				NBTTagCompound nbttagcompound = p_190008_0_.getCompoundTag("Properties");
				BlockStateContainer blockstatecontainer = block.getBlockState();

				for (String s : nbttagcompound.getKeySet()) {
					IProperty<?> iproperty = blockstatecontainer.getProperty(s);

					if (iproperty != null) {
						iblockstate = func_190007_a(iblockstate, iproperty, nbttagcompound.getString(s));
					}
				}
			}

			return iblockstate;
		}
	}

	private static <T extends Comparable<T>> IBlockState func_190007_a(IBlockState p_190007_0_, IProperty<T> p_190007_1_, String p_190007_2_) {
		return p_190007_0_.withProperty(p_190007_1_, p_190007_1_.parseValue(p_190007_2_).get());
	}

	public static NBTTagCompound func_190009_a(NBTTagCompound p_190009_0_, IBlockState p_190009_1_) {
		p_190009_0_.setString("Name", Block.REGISTRY.getNameForObject(p_190009_1_.getBlock()).toString());

		if (!p_190009_1_.getProperties().isEmpty()) {
			NBTTagCompound nbttagcompound = new NBTTagCompound();

			for (Entry<IProperty<?>, Comparable<?>> entry : p_190009_1_.getProperties().entrySet()) {
				IProperty<?> iproperty = entry.getKey();
				nbttagcompound.setString(iproperty.getName(), func_190010_a(iproperty, (Comparable) entry.getValue()));
			}

			p_190009_0_.setTag("Properties", nbttagcompound);
		}

		return p_190009_0_;
	}

	@SuppressWarnings("unchecked")
	private static <T extends Comparable<T>> String func_190010_a(IProperty<T> p_190010_0_, Comparable<?> p_190010_1_) {
		return p_190010_0_.getName((T) p_190010_1_);
	}
}
