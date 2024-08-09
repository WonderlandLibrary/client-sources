/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.state.StateContainer;
import net.minecraft.state.StateHolder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.StringUtils;
import net.minecraft.util.UUIDCodec;
import net.minecraft.util.datafix.DefaultTypeReferences;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class NBTUtil {
    private static final Logger LOGGER = LogManager.getLogger();

    @Nullable
    public static GameProfile readGameProfile(CompoundNBT compoundNBT) {
        String string = null;
        UUID uUID = null;
        if (compoundNBT.contains("Name", 1)) {
            string = compoundNBT.getString("Name");
        }
        if (compoundNBT.hasUniqueId("Id")) {
            uUID = compoundNBT.getUniqueId("Id");
        }
        try {
            GameProfile gameProfile = new GameProfile(uUID, string);
            if (compoundNBT.contains("Properties", 1)) {
                CompoundNBT compoundNBT2 = compoundNBT.getCompound("Properties");
                for (String string2 : compoundNBT2.keySet()) {
                    ListNBT listNBT = compoundNBT2.getList(string2, 10);
                    for (int i = 0; i < listNBT.size(); ++i) {
                        CompoundNBT compoundNBT3 = listNBT.getCompound(i);
                        String string3 = compoundNBT3.getString("Value");
                        if (compoundNBT3.contains("Signature", 1)) {
                            gameProfile.getProperties().put(string2, new Property(string2, string3, compoundNBT3.getString("Signature")));
                            continue;
                        }
                        gameProfile.getProperties().put(string2, new Property(string2, string3));
                    }
                }
            }
            return gameProfile;
        } catch (Throwable throwable) {
            return null;
        }
    }

    public static CompoundNBT writeGameProfile(CompoundNBT compoundNBT, GameProfile gameProfile) {
        if (!StringUtils.isNullOrEmpty(gameProfile.getName())) {
            compoundNBT.putString("Name", gameProfile.getName());
        }
        if (gameProfile.getId() != null) {
            compoundNBT.putUniqueId("Id", gameProfile.getId());
        }
        if (!gameProfile.getProperties().isEmpty()) {
            CompoundNBT compoundNBT2 = new CompoundNBT();
            for (String string : gameProfile.getProperties().keySet()) {
                ListNBT listNBT = new ListNBT();
                for (Property property : gameProfile.getProperties().get(string)) {
                    CompoundNBT compoundNBT3 = new CompoundNBT();
                    compoundNBT3.putString("Value", property.getValue());
                    if (property.hasSignature()) {
                        compoundNBT3.putString("Signature", property.getSignature());
                    }
                    listNBT.add(compoundNBT3);
                }
                compoundNBT2.put(string, listNBT);
            }
            compoundNBT.put("Properties", compoundNBT2);
        }
        return compoundNBT;
    }

    @VisibleForTesting
    public static boolean areNBTEquals(@Nullable INBT iNBT, @Nullable INBT iNBT2, boolean bl) {
        if (iNBT == iNBT2) {
            return false;
        }
        if (iNBT == null) {
            return false;
        }
        if (iNBT2 == null) {
            return true;
        }
        if (!iNBT.getClass().equals(iNBT2.getClass())) {
            return true;
        }
        if (iNBT instanceof CompoundNBT) {
            CompoundNBT compoundNBT = (CompoundNBT)iNBT;
            CompoundNBT compoundNBT2 = (CompoundNBT)iNBT2;
            for (String string : compoundNBT.keySet()) {
                INBT iNBT3 = compoundNBT.get(string);
                if (NBTUtil.areNBTEquals(iNBT3, compoundNBT2.get(string), bl)) continue;
                return true;
            }
            return false;
        }
        if (iNBT instanceof ListNBT && bl) {
            ListNBT listNBT = (ListNBT)iNBT;
            ListNBT listNBT2 = (ListNBT)iNBT2;
            if (listNBT.isEmpty()) {
                return listNBT2.isEmpty();
            }
            for (int i = 0; i < listNBT.size(); ++i) {
                INBT iNBT4 = listNBT.get(i);
                boolean bl2 = false;
                for (int j = 0; j < listNBT2.size(); ++j) {
                    if (!NBTUtil.areNBTEquals(iNBT4, listNBT2.get(j), bl)) continue;
                    bl2 = true;
                    break;
                }
                if (bl2) continue;
                return true;
            }
            return false;
        }
        return iNBT.equals(iNBT2);
    }

    public static IntArrayNBT func_240626_a_(UUID uUID) {
        return new IntArrayNBT(UUIDCodec.encodeUUID(uUID));
    }

    public static UUID readUniqueId(INBT iNBT) {
        if (iNBT.getType() != IntArrayNBT.TYPE) {
            throw new IllegalArgumentException("Expected UUID-Tag to be of type " + IntArrayNBT.TYPE.getName() + ", but found " + iNBT.getType().getName() + ".");
        }
        int[] nArray = ((IntArrayNBT)iNBT).getIntArray();
        if (nArray.length != 4) {
            throw new IllegalArgumentException("Expected UUID-Array to be of length 4, but found " + nArray.length + ".");
        }
        return UUIDCodec.decodeUUID(nArray);
    }

    public static BlockPos readBlockPos(CompoundNBT compoundNBT) {
        return new BlockPos(compoundNBT.getInt("X"), compoundNBT.getInt("Y"), compoundNBT.getInt("Z"));
    }

    public static CompoundNBT writeBlockPos(BlockPos blockPos) {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("X", blockPos.getX());
        compoundNBT.putInt("Y", blockPos.getY());
        compoundNBT.putInt("Z", blockPos.getZ());
        return compoundNBT;
    }

    public static BlockState readBlockState(CompoundNBT compoundNBT) {
        if (!compoundNBT.contains("Name", 1)) {
            return Blocks.AIR.getDefaultState();
        }
        Block block = Registry.BLOCK.getOrDefault(new ResourceLocation(compoundNBT.getString("Name")));
        BlockState blockState = block.getDefaultState();
        if (compoundNBT.contains("Properties", 1)) {
            CompoundNBT compoundNBT2 = compoundNBT.getCompound("Properties");
            StateContainer<Block, BlockState> stateContainer = block.getStateContainer();
            for (String string : compoundNBT2.keySet()) {
                net.minecraft.state.Property<?> property = stateContainer.getProperty(string);
                if (property == null) continue;
                blockState = NBTUtil.setValueHelper(blockState, property, string, compoundNBT2, compoundNBT);
            }
        }
        return blockState;
    }

    private static <S extends StateHolder<?, S>, T extends Comparable<T>> S setValueHelper(S s, net.minecraft.state.Property<T> property, String string, CompoundNBT compoundNBT, CompoundNBT compoundNBT2) {
        Optional<T> optional = property.parseValue(compoundNBT.getString(string));
        if (optional.isPresent()) {
            return (S)((StateHolder)s.with(property, (Comparable)((Comparable)optional.get())));
        }
        LOGGER.warn("Unable to read property: {} with value: {} for blockstate: {}", (Object)string, (Object)compoundNBT.getString(string), (Object)compoundNBT2.toString());
        return s;
    }

    public static CompoundNBT writeBlockState(BlockState blockState) {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("Name", Registry.BLOCK.getKey(blockState.getBlock()).toString());
        ImmutableMap<net.minecraft.state.Property<?>, Comparable<?>> immutableMap = blockState.getValues();
        if (!immutableMap.isEmpty()) {
            CompoundNBT compoundNBT2 = new CompoundNBT();
            for (Map.Entry entry : immutableMap.entrySet()) {
                net.minecraft.state.Property property = (net.minecraft.state.Property)entry.getKey();
                compoundNBT2.putString(property.getName(), NBTUtil.getName(property, (Comparable)entry.getValue()));
            }
            compoundNBT.put("Properties", compoundNBT2);
        }
        return compoundNBT;
    }

    private static <T extends Comparable<T>> String getName(net.minecraft.state.Property<T> property, Comparable<?> comparable) {
        return property.getName(comparable);
    }

    public static CompoundNBT update(DataFixer dataFixer, DefaultTypeReferences defaultTypeReferences, CompoundNBT compoundNBT, int n) {
        return NBTUtil.update(dataFixer, defaultTypeReferences, compoundNBT, n, SharedConstants.getVersion().getWorldVersion());
    }

    public static CompoundNBT update(DataFixer dataFixer, DefaultTypeReferences defaultTypeReferences, CompoundNBT compoundNBT, int n, int n2) {
        return dataFixer.update(defaultTypeReferences.getTypeReference(), new Dynamic<CompoundNBT>(NBTDynamicOps.INSTANCE, compoundNBT), n, n2).getValue();
    }
}

