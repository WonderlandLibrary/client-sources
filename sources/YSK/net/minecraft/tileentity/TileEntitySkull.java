package net.minecraft.tileentity;

import com.mojang.authlib.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.server.*;
import com.mojang.authlib.properties.*;
import com.google.common.collect.*;
import java.util.*;

public class TileEntitySkull extends TileEntity
{
    private int skullRotation;
    private int skullType;
    private static final String[] I;
    private GameProfile playerProfile;
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.pos, 0x83 ^ 0x87, nbtTagCompound);
    }
    
    private static void I() {
        (I = new String[0xB1 ^ 0xBA])["".length()] = I(">#-'-91(.", "mHXKA");
        TileEntitySkull.I[" ".length()] = I("?6:", "mYNnb");
        TileEntitySkull.I["  ".length()] = I("(\u0004\u0005-\u0003", "gskHq");
        TileEntitySkull.I["   ".length()] = I("<:8\u0001\";(=\b", "oQMmN");
        TileEntitySkull.I[0x9E ^ 0x9A] = I("\u0017\f&", "EcRFT");
        TileEntitySkull.I[0x30 ^ 0x35] = I("\u001a\u0001!\u0004\u0018", "UvOaj");
        TileEntitySkull.I[0x3C ^ 0x3A] = I("!6\u001e38", "nApVJ");
        TileEntitySkull.I[0x3E ^ 0x39] = I(",\n8\"\u0013=\u000b<5", "irLPr");
        TileEntitySkull.I[0x24 ^ 0x2C] = I("\u0006-3+;\u0017,7<", "CUGYZ");
        TileEntitySkull.I[0x29 ^ 0x20] = I("\f'\t<\u0011\n'\u0002", "xBqHd");
        TileEntitySkull.I[0x35 ^ 0x3F] = I("\r6\u001c.\u0019\u000b6\u0017", "ySdZl");
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setByte(TileEntitySkull.I["".length()], (byte)(this.skullType & 68 + 76 - 107 + 218));
        nbtTagCompound.setByte(TileEntitySkull.I[" ".length()], (byte)(this.skullRotation & 46 + 224 - 205 + 190));
        if (this.playerProfile != null) {
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            NBTUtil.writeGameProfile(nbtTagCompound2, this.playerProfile);
            nbtTagCompound.setTag(TileEntitySkull.I["  ".length()], nbtTagCompound2);
        }
    }
    
    public int getSkullRotation() {
        return this.skullRotation;
    }
    
    public void setPlayerProfile(final GameProfile playerProfile) {
        this.skullType = "   ".length();
        this.playerProfile = playerProfile;
        this.updatePlayerProfile();
    }
    
    public static GameProfile updateGameprofile(final GameProfile gameProfile) {
        if (gameProfile == null || StringUtils.isNullOrEmpty(gameProfile.getName())) {
            return gameProfile;
        }
        if (gameProfile.isComplete() && gameProfile.getProperties().containsKey((Object)TileEntitySkull.I[0x6E ^ 0x67])) {
            return gameProfile;
        }
        if (MinecraftServer.getServer() == null) {
            return gameProfile;
        }
        GameProfile gameProfile2 = MinecraftServer.getServer().getPlayerProfileCache().getGameProfileForUsername(gameProfile.getName());
        if (gameProfile2 == null) {
            return gameProfile;
        }
        if (Iterables.getFirst((Iterable)gameProfile2.getProperties().get((Object)TileEntitySkull.I[0x1E ^ 0x14]), (Object)null) == null) {
            gameProfile2 = MinecraftServer.getServer().getMinecraftSessionService().fillProfileProperties(gameProfile2, (boolean)(" ".length() != 0));
        }
        return gameProfile2;
    }
    
    public GameProfile getPlayerProfile() {
        return this.playerProfile;
    }
    
    public void setType(final int skullType) {
        this.skullType = skullType;
        this.playerProfile = null;
    }
    
    static {
        I();
    }
    
    public TileEntitySkull() {
        this.playerProfile = null;
    }
    
    public void setSkullRotation(final int skullRotation) {
        this.skullRotation = skullRotation;
    }
    
    private void updatePlayerProfile() {
        this.playerProfile = updateGameprofile(this.playerProfile);
        this.markDirty();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getSkullType() {
        return this.skullType;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.skullType = nbtTagCompound.getByte(TileEntitySkull.I["   ".length()]);
        this.skullRotation = nbtTagCompound.getByte(TileEntitySkull.I[0xAF ^ 0xAB]);
        if (this.skullType == "   ".length()) {
            if (nbtTagCompound.hasKey(TileEntitySkull.I[0x1A ^ 0x1F], 0x97 ^ 0x9D)) {
                this.playerProfile = NBTUtil.readGameProfileFromNBT(nbtTagCompound.getCompoundTag(TileEntitySkull.I[0x68 ^ 0x6E]));
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else if (nbtTagCompound.hasKey(TileEntitySkull.I[0xBE ^ 0xB9], 0x41 ^ 0x49)) {
                final String string = nbtTagCompound.getString(TileEntitySkull.I[0x67 ^ 0x6F]);
                if (!StringUtils.isNullOrEmpty(string)) {
                    this.playerProfile = new GameProfile((UUID)null, string);
                    this.updatePlayerProfile();
                }
            }
        }
    }
}
