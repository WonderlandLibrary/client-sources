package net.minecraft.block.state;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.*;

import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStateContainer {
	private static final Pattern NAME_PATTERN = Pattern.compile("^[a-z0-9_]+$");
	private static final Function<IProperty<?>, String> GET_NAME_FUNC = new Function<IProperty<?>, String>() {
		@Override
		@Nullable
		public String apply(@Nullable IProperty<?> p_apply_1_) {
			return p_apply_1_ == null ? "<NULL>" : p_apply_1_.getName();
		}
	};
	private final Block block;
	private final ImmutableSortedMap<String, IProperty<?>> properties;
	private final ImmutableList<IBlockState> validStates;

	public BlockStateContainer(Block blockIn, IProperty<?>... properties) {
		this.block = blockIn;
		Map<String, IProperty<?>> map = Maps.<String, IProperty<?>> newHashMap();

		for (IProperty<?> iproperty : properties) {
			validateProperty(blockIn, iproperty);
			map.put(iproperty.getName(), iproperty);
		}

		this.properties = ImmutableSortedMap.<String, IProperty<?>> copyOf(map);
		Map<Map<IProperty<?>, Comparable<?>>, BlockStateContainer.StateImplementation> map2 = Maps.<Map<IProperty<?>, Comparable<?>>, BlockStateContainer.StateImplementation> newLinkedHashMap();
		List<BlockStateContainer.StateImplementation> list1 = Lists.<BlockStateContainer.StateImplementation> newArrayList();

		for (List<Comparable<?>> list : Cartesian.cartesianProduct(this.getAllowedValues())) {
			Map<IProperty<?>, Comparable<?>> map1 = MapPopulator.<IProperty<?>, Comparable<?>> createMap(this.properties.values(), list);
			BlockStateContainer.StateImplementation blockstatecontainer$stateimplementation = new BlockStateContainer.StateImplementation(blockIn, ImmutableMap.copyOf(map1));
			map2.put(map1, blockstatecontainer$stateimplementation);
			list1.add(blockstatecontainer$stateimplementation);
		}

		for (BlockStateContainer.StateImplementation blockstatecontainer$stateimplementation1 : list1) {
			blockstatecontainer$stateimplementation1.buildPropertyValueTable(map2);
		}

		this.validStates = ImmutableList.<IBlockState> copyOf(list1);
	}

	public static <T extends Comparable<T>> String validateProperty(Block block, IProperty<T> property) {
		String s = property.getName();

		if (!NAME_PATTERN.matcher(s).matches()) {
			throw new IllegalArgumentException("Block: " + block.getClass() + " has invalidly named property: " + s);
		} else {
			for (T t : property.getAllowedValues()) {
				String s1 = property.getName(t);

				if (!NAME_PATTERN.matcher(s1).matches()) { throw new IllegalArgumentException("Block: " + block.getClass() + " has property: " + s + " with invalidly named value: " + s1); }
			}

			return s;
		}
	}

	public ImmutableList<IBlockState> getValidStates() {
		return this.validStates;
	}

	private List<Iterable<Comparable<?>>> getAllowedValues() {
		List<Iterable<Comparable<?>>> list = Lists.<Iterable<Comparable<?>>> newArrayList();

		for (IProperty<?> iproperty : this.properties.values()) {
			list.add(((IProperty) iproperty).getAllowedValues());
		}

		return list;
	}

	public IBlockState getBaseState() {
		return this.validStates.get(0);
	}

	public Block getBlock() {
		return this.block;
	}

	public Collection<IProperty<?>> getProperties() {
		return this.properties.values();
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("block", Block.REGISTRY.getNameForObject(this.block)).add("properties", Iterables.transform(this.properties.values(), GET_NAME_FUNC)).toString();
	}

	@Nullable
	public IProperty<?> getProperty(String propertyName) {
		return this.properties.get(propertyName);
	}

	static class StateImplementation extends BlockStateBase {
		private final Block block;
		private final ImmutableMap<IProperty<?>, Comparable<?>> properties;
		private ImmutableTable<IProperty<?>, Comparable<?>, IBlockState> propertyValueTable;

		private StateImplementation(Block blockIn, ImmutableMap<IProperty<?>, Comparable<?>> propertiesIn) {
			this.block = blockIn;
			this.properties = propertiesIn;
		}

		@Override
		public Collection<IProperty<?>> getPropertyNames() {
			return Collections.<IProperty<?>> unmodifiableCollection(this.properties.keySet());
		}

		@Override
		public <T extends Comparable<T>> T getValue(IProperty<T> property) {
			Comparable<?> comparable = this.properties.get(property);

			if (comparable == null) {
				throw new IllegalArgumentException("Cannot get property " + property + " as it does not exist in " + this.block.getBlockState());
			} else {
				return (T) ((Comparable) property.getValueClass().cast(comparable));
			}
		}

		@Override
		public <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> property, Comparable<?> value) {
			Comparable<?> comparable = this.properties.get(property);

			if (comparable == null) {
				throw new IllegalArgumentException("Cannot set property " + property + " as it does not exist in " + this.block.getBlockState());
			} else if (comparable == value) {
				return this;
			} else {
				IBlockState iblockstate = this.propertyValueTable.get(property, value);

				if (iblockstate == null) {
					throw new IllegalArgumentException(
							"Cannot set property " + property + " to " + value + " on block " + Block.REGISTRY.getNameForObject(this.block) + ", it is not an allowed value");
				} else {
					return iblockstate;
				}
			}
		}

		@Override
		public ImmutableMap<IProperty<?>, Comparable<?>> getProperties() {
			return this.properties;
		}

		@Override
		public Block getBlock() {
			return this.block;
		}

		@Override
		public boolean equals(Object p_equals_1_) {
			return this == p_equals_1_;
		}

		@Override
		public int hashCode() {
			return this.properties.hashCode();
		}

		public void buildPropertyValueTable(Map<Map<IProperty<?>, Comparable<?>>, BlockStateContainer.StateImplementation> map) {
			if (this.propertyValueTable != null) {
				throw new IllegalStateException();
			} else {
				Table<IProperty<?>, Comparable<?>, IBlockState> table = HashBasedTable.<IProperty<?>, Comparable<?>, IBlockState> create();

				for (Entry<IProperty<?>, Comparable<?>> entry : this.properties.entrySet()) {
					IProperty<?> iproperty = entry.getKey();

					for (Comparable<?> comparable : iproperty.getAllowedValues()) {
						if (comparable != entry.getValue()) {
							table.put(iproperty, comparable, map.get(this.getPropertiesWithValue(iproperty, comparable)));
						}
					}
				}

				this.propertyValueTable = ImmutableTable.<IProperty<?>, Comparable<?>, IBlockState> copyOf(table);
			}
		}

		private Map<IProperty<?>, Comparable<?>> getPropertiesWithValue(IProperty<?> property, Comparable<?> value) {
			Map<IProperty<?>, Comparable<?>> map = Maps.<IProperty<?>, Comparable<?>> newHashMap(this.properties);
			map.put(property, value);
			return map;
		}

		@Override
		public Material getMaterial() {
			return this.block.getMaterial(this);
		}

		@Override
		public boolean isFullBlock() {
			return this.block.isFullBlock(this);
		}

		@Override
		public boolean func_189884_a(Entity p_189884_1_) {
			return this.block.func_189872_a(this, p_189884_1_);
		}

		@Override
		public int getLightOpacity() {
			return this.block.getLightOpacity(this);
		}

		@Override
		public int getLightValue() {
			return this.block.getLightValue(this);
		}

		@Override
		public boolean isTranslucent() {
			return this.block.isTranslucent(this);
		}

		@Override
		public boolean useNeighborBrightness() {
			return this.block.getUseNeighborBrightness(this);
		}

		@Override
		public MapColor getMapColor() {
			return this.block.getMapColor(this);
		}

		@Override
		public IBlockState withRotation(Rotation rot) {
			return this.block.withRotation(this, rot);
		}

		@Override
		public IBlockState withMirror(Mirror mirrorIn) {
			return this.block.withMirror(this, mirrorIn);
		}

		@Override
		public boolean isFullCube() {
			return this.block.isFullCube(this);
		}

		@Override
		public EnumBlockRenderType getRenderType() {
			return this.block.getRenderType(this);
		}

		@Override
		public int getPackedLightmapCoords(IBlockAccess source, BlockPos pos) {
			return this.block.getPackedLightmapCoords(this, source, pos);
		}

		@Override
		public float getAmbientOcclusionLightValue() {
			return this.block.getAmbientOcclusionLightValue(this);
		}

		@Override
		public boolean isBlockNormalCube() {
			return this.block.isBlockNormalCube(this);
		}

		@Override
		public boolean isNormalCube() {
			return this.block.isNormalCube(this);
		}

		@Override
		public boolean canProvidePower() {
			return this.block.canProvidePower(this);
		}

		@Override
		public int getWeakPower(IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
			return this.block.getWeakPower(this, blockAccess, pos, side);
		}

		@Override
		public boolean hasComparatorInputOverride() {
			return this.block.hasComparatorInputOverride(this);
		}

		@Override
		public int getComparatorInputOverride(World worldIn, BlockPos pos) {
			return this.block.getComparatorInputOverride(this, worldIn, pos);
		}

		@Override
		public float getBlockHardness(World worldIn, BlockPos pos) {
			return this.block.getBlockHardness(this, worldIn, pos);
		}

		@Override
		public float getPlayerRelativeBlockHardness(EntityPlayer player, World worldIn, BlockPos pos) {
			return this.block.getPlayerRelativeBlockHardness(this, player, worldIn, pos);
		}

		@Override
		public int getStrongPower(IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
			return this.block.getStrongPower(this, blockAccess, pos, side);
		}

		@Override
		public EnumPushReaction getMobilityFlag() {
			return this.block.getMobilityFlag(this);
		}

		@Override
		public IBlockState getActualState(IBlockAccess blockAccess, BlockPos pos) {
			return this.block.getActualState(this, blockAccess, pos);
		}

		@Override
		public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
			return this.block.getSelectedBoundingBox(this, worldIn, pos);
		}

		@Override
		public boolean shouldSideBeRendered(IBlockAccess blockAccess, BlockPos pos, EnumFacing facing) {
			return this.block.shouldSideBeRendered(this, blockAccess, pos, facing);
		}

		@Override
		public boolean isOpaqueCube() {
			return this.block.isOpaqueCube(this);
		}

		@Override
		@Nullable
		public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos) {
			return this.block.getCollisionBoundingBox(this, worldIn, pos);
		}

		@Override
		public void addCollisionBoxToList(World worldIn, BlockPos pos, AxisAlignedBB p_185908_3_, List<AxisAlignedBB> p_185908_4_, @Nullable Entity p_185908_5_) {
			this.block.addCollisionBoxToList(this, worldIn, pos, p_185908_3_, p_185908_4_, p_185908_5_);
		}

		@Override
		public AxisAlignedBB getBoundingBox(IBlockAccess blockAccess, BlockPos pos) {
			return this.block.getBoundingBox(this, blockAccess, pos);
		}

		@Override
		public RayTraceResult collisionRayTrace(World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
			return this.block.collisionRayTrace(this, worldIn, pos, start, end);
		}

		@Override
		public boolean isFullyOpaque() {
			return this.block.isFullyOpaque(this);
		}

		@Override
		public boolean func_189547_a(World p_189547_1_, BlockPos p_189547_2_, int p_189547_3_, int p_189547_4_) {
			return this.block.func_189539_a(this, p_189547_1_, p_189547_2_, p_189547_3_, p_189547_4_);
		}

		@Override
		public void func_189546_a(World p_189546_1_, BlockPos p_189546_2_, Block p_189546_3_) {
			this.block.func_189540_a(this, p_189546_1_, p_189546_2_, p_189546_3_);
		}
	}
}
