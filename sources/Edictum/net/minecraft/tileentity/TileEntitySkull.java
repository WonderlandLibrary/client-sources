package net.minecraft.tileentity;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.BlockSkull;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.StringUtils;

public class TileEntitySkull extends TileEntity implements ITickable
{
    private int skullType;
    private int skullRotation;
    private GameProfile playerProfile;
    private int dragonAnimatedTicks;
    private boolean dragonAnimated;
    private static PlayerProfileCache profileCache;
    private static MinecraftSessionService sessionService;

    public static void setProfileCache(PlayerProfileCache profileCacheIn)
    {
        profileCache = profileCacheIn;
    }

    public static void setSessionService(MinecraftSessionService sessionServiceIn)
    {
        sessionService = sessionServiceIn;
    }

    public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_)
    {
        super.func_189515_b(p_189515_1_);
        p_189515_1_.setByte("SkullType", (byte)(this.skullType & 255));
        p_189515_1_.setByte("Rot", (byte)(this.skullRotation & 255));

        if (this.playerProfile != null)
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            NBTUtil.writeGameProfile(nbttagcompound, this.playerProfile);
            p_189515_1_.setTag("Owner", nbttagcompound);
        }

        return p_189515_1_;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.skullType = compound.getByte("SkullType");
        this.skullRotation = compound.getByte("Rot");

        if (this.skullType == 3)
        {
            if (compound.hasKey("Owner", 10))
            {
                this.playerProfile = NBTUtil.readGameProfileFromNBT(compound.getCompoundTag("Owner"));
            }
            else if (compound.hasKey("ExtraType", 8))
            {
                String s = compound.getString("ExtraType");

                if (!StringUtils.isNullOrEmpty(s))
                {
                    this.playerProfile = new GameProfile((UUID)null, s);
                    this.updatePlayerProfile();
                }
            }
        }
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    public void update()
    {
        if (this.skullType == 5)
        {
            if (this.worldObj.isBlockPowered(this.pos))
            {
                this.dragonAnimated = true;
                ++this.dragonAnimatedTicks;
            }
            else
            {
                this.dragonAnimated = false;
            }
        }
    }

    public float getAnimationProgress(float p_184295_1_)
    {
        return this.dragonAnimated ? (float)this.dragonAnimatedTicks + p_184295_1_ : (float)this.dragonAnimatedTicks;
    }

    @Nullable
    public GameProfile getPlayerProfile()
    {
        return this.playerProfile;
    }

    @Nullable
    public SPacketUpdateTileEntity func_189518_D_()
    {
        return new SPacketUpdateTileEntity(this.pos, 4, this.func_189517_E_());
    }

    public NBTTagCompound func_189517_E_()
    {
        return this.func_189515_b(new NBTTagCompound());
    }

    public void setType(int type)
    {
        this.skullType = type;
        this.playerProfile = null;
    }

    public void setPlayerProfile(@Nullable GameProfile playerProfile)
    {
        this.skullType = 3;
        this.playerProfile = playerProfile;
        this.updatePlayerProfile();
    }

    private void updatePlayerProfile()
    {
        this.playerProfile = updateGameprofile(this.playerProfile);
        this.markDirty();
    }

    public static GameProfile updateGameprofile(GameProfile input)
    {
        if (input != null && !StringUtils.isNullOrEmpty(input.getName()))
        {
            if (input.isComplete() && input.getProperties().containsKey("textures"))
            {
                return input;
            }
            else if (profileCache != null && sessionService != null)
            {
                GameProfile gameprofile = profileCache.getGameProfileForUsername(input.getName());

                if (gameprofile == null)
                {
                    return input;
                }
                else
                {
                    Property property = (Property)Iterables.getFirst(gameprofile.getProperties().get("textures"), null);

                    if (property == null)
                    {
                        gameprofile = sessionService.fillProfileProperties(gameprofile, true);
                    }

                    return gameprofile;
                }
            }
            else
            {
                return input;
            }
        }
        else
        {
            return input;
        }
    }

    public int getSkullType()
    {
        return this.skullType;
    }

    public int getSkullRotation()
    {
        return this.skullRotation;
    }

    public void setSkullRotation(int rotation)
    {
        this.skullRotation = rotation;
    }

    public void func_189668_a(Mirror p_189668_1_)
    {
        if (this.worldObj != null && this.worldObj.getBlockState(this.getPos()).getValue(BlockSkull.FACING) == EnumFacing.UP)
        {
            this.skullRotation = p_189668_1_.mirrorRotation(this.skullRotation, 16);
        }
    }

    public void func_189667_a(Rotation p_189667_1_)
    {
        if (this.worldObj != null && this.worldObj.getBlockState(this.getPos()).getValue(BlockSkull.FACING) == EnumFacing.UP)
        {
            this.skullRotation = p_189667_1_.rotate(this.skullRotation, 16);
        }
    }
}
