package HORIZON-6-0-SKIDPROTECTION;

import java.util.UUID;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

public class UserListWhitelistEntry extends UserListEntry
{
    private static final String HorizonCode_Horizon_È = "CL_00001870";
    
    public UserListWhitelistEntry(final GameProfile p_i1129_1_) {
        super(p_i1129_1_);
    }
    
    public UserListWhitelistEntry(final JsonObject p_i1130_1_) {
        super(Â(p_i1130_1_), p_i1130_1_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final JsonObject data) {
        if (this.Ø­áŒŠá() != null) {
            data.addProperty("uuid", (((GameProfile)this.Ø­áŒŠá()).getId() == null) ? "" : ((GameProfile)this.Ø­áŒŠá()).getId().toString());
            data.addProperty("name", ((GameProfile)this.Ø­áŒŠá()).getName());
            super.HorizonCode_Horizon_È(data);
        }
    }
    
    private static GameProfile Â(final JsonObject p_152646_0_) {
        if (p_152646_0_.has("uuid") && p_152646_0_.has("name")) {
            final String var1 = p_152646_0_.get("uuid").getAsString();
            UUID var2;
            try {
                var2 = UUID.fromString(var1);
            }
            catch (Throwable var3) {
                return null;
            }
            return new GameProfile(var2, p_152646_0_.get("name").getAsString());
        }
        return null;
    }
}
