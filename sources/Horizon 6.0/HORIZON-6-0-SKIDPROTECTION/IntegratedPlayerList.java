package HORIZON-6-0-SKIDPROTECTION;

import com.mojang.authlib.GameProfile;
import java.net.SocketAddress;

public class IntegratedPlayerList extends ServerConfigurationManager
{
    private NBTTagCompound Ø;
    private static final String áŒŠÆ = "CL_00001128";
    
    public IntegratedPlayerList(final IntegratedServer p_i1314_1_) {
        super(p_i1314_1_);
        this.HorizonCode_Horizon_È(10);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityPlayerMP playerIn) {
        if (playerIn.v_().equals(this.HorizonCode_Horizon_È().ŠÂµà())) {
            playerIn.Âµá€(this.Ø = new NBTTagCompound());
        }
        super.HorizonCode_Horizon_È(playerIn);
    }
    
    @Override
    public String HorizonCode_Horizon_È(final SocketAddress address, final GameProfile profile) {
        return (profile.getName().equalsIgnoreCase(this.HorizonCode_Horizon_È().ŠÂµà()) && this.HorizonCode_Horizon_È(profile.getName()) != null) ? "That name is already taken." : super.HorizonCode_Horizon_È(address, profile);
    }
    
    public IntegratedServer HorizonCode_Horizon_È() {
        return (IntegratedServer)super.Ý();
    }
    
    @Override
    public NBTTagCompound Â() {
        return this.Ø;
    }
    
    @Override
    public MinecraftServer Ý() {
        return this.HorizonCode_Horizon_È();
    }
}
