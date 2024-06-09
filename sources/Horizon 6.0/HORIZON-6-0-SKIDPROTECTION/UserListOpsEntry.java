package HORIZON-6-0-SKIDPROTECTION;

import java.util.UUID;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

public class UserListOpsEntry extends UserListEntry
{
    private final int HorizonCode_Horizon_È;
    private static final String Â = "CL_00001878";
    
    public UserListOpsEntry(final GameProfile p_i46328_1_, final int p_i46328_2_) {
        super(p_i46328_1_);
        this.HorizonCode_Horizon_È = p_i46328_2_;
    }
    
    public UserListOpsEntry(final JsonObject p_i1150_1_) {
        super(Â(p_i1150_1_), p_i1150_1_);
        this.HorizonCode_Horizon_È = (p_i1150_1_.has("level") ? p_i1150_1_.get("level").getAsInt() : 0);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final JsonObject data) {
        if (this.Ø­áŒŠá() != null) {
            data.addProperty("uuid", (((GameProfile)this.Ø­áŒŠá()).getId() == null) ? "" : ((GameProfile)this.Ø­áŒŠá()).getId().toString());
            data.addProperty("name", ((GameProfile)this.Ø­áŒŠá()).getName());
            super.HorizonCode_Horizon_È(data);
            data.addProperty("level", (Number)this.HorizonCode_Horizon_È);
        }
    }
    
    private static GameProfile Â(final JsonObject p_152643_0_) {
        if (p_152643_0_.has("uuid") && p_152643_0_.has("name")) {
            final String var1 = p_152643_0_.get("uuid").getAsString();
            UUID var2;
            try {
                var2 = UUID.fromString(var1);
            }
            catch (Throwable var3) {
                return null;
            }
            return new GameProfile(var2, p_152643_0_.get("name").getAsString());
        }
        return null;
    }
}
