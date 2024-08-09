/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.StringUtils;

public class SkullTileEntity
extends TileEntity
implements ITickableTileEntity {
    @Nullable
    private static PlayerProfileCache profileCache;
    @Nullable
    private static MinecraftSessionService sessionService;
    @Nullable
    private GameProfile playerProfile;
    private int dragonAnimatedTicks;
    private boolean dragonAnimated;

    public SkullTileEntity() {
        super(TileEntityType.SKULL);
    }

    public static void setProfileCache(PlayerProfileCache playerProfileCache) {
        profileCache = playerProfileCache;
    }

    public static void setSessionService(MinecraftSessionService minecraftSessionService) {
        sessionService = minecraftSessionService;
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        if (this.playerProfile != null) {
            CompoundNBT compoundNBT2 = new CompoundNBT();
            NBTUtil.writeGameProfile(compoundNBT2, this.playerProfile);
            compoundNBT.put("SkullOwner", compoundNBT2);
        }
        return compoundNBT;
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        String string;
        super.read(blockState, compoundNBT);
        if (compoundNBT.contains("SkullOwner", 1)) {
            this.setPlayerProfile(NBTUtil.readGameProfile(compoundNBT.getCompound("SkullOwner")));
        } else if (compoundNBT.contains("ExtraType", 1) && !StringUtils.isNullOrEmpty(string = compoundNBT.getString("ExtraType"))) {
            this.setPlayerProfile(new GameProfile(null, string));
        }
    }

    @Override
    public void tick() {
        BlockState blockState = this.getBlockState();
        if (blockState.isIn(Blocks.DRAGON_HEAD) || blockState.isIn(Blocks.DRAGON_WALL_HEAD)) {
            if (this.world.isBlockPowered(this.pos)) {
                this.dragonAnimated = true;
                ++this.dragonAnimatedTicks;
            } else {
                this.dragonAnimated = false;
            }
        }
    }

    public float getAnimationProgress(float f) {
        return this.dragonAnimated ? (float)this.dragonAnimatedTicks + f : (float)this.dragonAnimatedTicks;
    }

    @Nullable
    public GameProfile getPlayerProfile() {
        return this.playerProfile;
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 4, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    public void setPlayerProfile(@Nullable GameProfile gameProfile) {
        this.playerProfile = gameProfile;
        this.updatePlayerProfile();
    }

    private void updatePlayerProfile() {
        this.playerProfile = SkullTileEntity.updateGameProfile(this.playerProfile);
        this.markDirty();
    }

    @Nullable
    public static GameProfile updateGameProfile(@Nullable GameProfile gameProfile) {
        if (gameProfile != null && !StringUtils.isNullOrEmpty(gameProfile.getName())) {
            if (gameProfile.isComplete() && gameProfile.getProperties().containsKey("textures")) {
                return gameProfile;
            }
            if (profileCache != null && sessionService != null) {
                GameProfile gameProfile2 = profileCache.getGameProfileForUsername(gameProfile.getName());
                if (gameProfile2 == null) {
                    return gameProfile;
                }
                Property property = Iterables.getFirst(gameProfile2.getProperties().get("textures"), null);
                if (property == null) {
                    gameProfile2 = sessionService.fillProfileProperties(gameProfile2, true);
                }
                return gameProfile2;
            }
            return gameProfile;
        }
        return gameProfile;
    }
}

