package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Iterables;
import com.mojang.authlib.properties.Property;
import java.util.UUID;
import com.mojang.authlib.GameProfile;

public class TileEntitySkull extends TileEntity
{
    private int Âµá€;
    private int Ó;
    private GameProfile à;
    private static final String Ø = "CL_00000364";
    
    public TileEntitySkull() {
        this.à = null;
    }
    
    @Override
    public void Â(final NBTTagCompound compound) {
        super.Â(compound);
        compound.HorizonCode_Horizon_È("SkullType", (byte)(this.Âµá€ & 0xFF));
        compound.HorizonCode_Horizon_È("Rot", (byte)(this.Ó & 0xFF));
        if (this.à != null) {
            final NBTTagCompound var2 = new NBTTagCompound();
            NBTUtil.HorizonCode_Horizon_È(var2, this.à);
            compound.HorizonCode_Horizon_È("Owner", var2);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound compound) {
        super.HorizonCode_Horizon_È(compound);
        this.Âµá€ = compound.Ø­áŒŠá("SkullType");
        this.Ó = compound.Ø­áŒŠá("Rot");
        if (this.Âµá€ == 3) {
            if (compound.Â("Owner", 10)) {
                this.à = NBTUtil.HorizonCode_Horizon_È(compound.ˆÏ­("Owner"));
            }
            else if (compound.Â("ExtraType", 8)) {
                final String var2 = compound.áˆºÑ¢Õ("ExtraType");
                if (!StringUtils.Â(var2)) {
                    this.à = new GameProfile((UUID)null, var2);
                    this.Ø­áŒŠá();
                }
            }
        }
    }
    
    public GameProfile HorizonCode_Horizon_È() {
        return this.à;
    }
    
    @Override
    public Packet £á() {
        final NBTTagCompound var1 = new NBTTagCompound();
        this.Â(var1);
        return new S35PacketUpdateTileEntity(this.Â, 4, var1);
    }
    
    public void HorizonCode_Horizon_È(final int type) {
        this.Âµá€ = type;
        this.à = null;
    }
    
    public void HorizonCode_Horizon_È(final GameProfile playerProfile) {
        this.Âµá€ = 3;
        this.à = playerProfile;
        this.Ø­áŒŠá();
    }
    
    private void Ø­áŒŠá() {
        this.à = Â(this.à);
        this.ŠÄ();
    }
    
    public static GameProfile Â(final GameProfile input) {
        if (input == null || StringUtils.Â(input.getName())) {
            return input;
        }
        if (input.isComplete() && input.getProperties().containsKey((Object)"textures")) {
            return input;
        }
        if (MinecraftServer.áƒ() == null) {
            return input;
        }
        GameProfile var1 = MinecraftServer.áƒ().áŒŠÏ().HorizonCode_Horizon_È(input.getName());
        if (var1 == null) {
            return input;
        }
        final Property var2 = (Property)Iterables.getFirst((Iterable)var1.getProperties().get((Object)"textures"), (Object)null);
        if (var2 == null) {
            var1 = MinecraftServer.áƒ().áˆºÇŽØ().fillProfileProperties(var1, true);
        }
        return var1;
    }
    
    public int Â() {
        return this.Âµá€;
    }
    
    public int Ý() {
        return this.Ó;
    }
    
    public void Â(final int rotation) {
        this.Ó = rotation;
    }
}
