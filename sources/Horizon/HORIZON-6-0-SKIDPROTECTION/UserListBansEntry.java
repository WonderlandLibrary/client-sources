package HORIZON-6-0-SKIDPROTECTION;

import java.util.UUID;
import com.google.gson.JsonObject;
import java.util.Date;
import com.mojang.authlib.GameProfile;

public class UserListBansEntry extends BanEntry
{
    private static final String Ó = "CL_00001872";
    
    public UserListBansEntry(final GameProfile p_i1134_1_) {
        this(p_i1134_1_, null, null, null, null);
    }
    
    public UserListBansEntry(final GameProfile p_i1135_1_, final Date p_i1135_2_, final String p_i1135_3_, final Date p_i1135_4_, final String p_i1135_5_) {
        super(p_i1135_1_, p_i1135_4_, p_i1135_3_, p_i1135_4_, p_i1135_5_);
    }
    
    public UserListBansEntry(final JsonObject p_i1136_1_) {
        super(Â(p_i1136_1_), p_i1136_1_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final JsonObject data) {
        if (this.Ø­áŒŠá() != null) {
            data.addProperty("uuid", (((GameProfile)this.Ø­áŒŠá()).getId() == null) ? "" : ((GameProfile)this.Ø­áŒŠá()).getId().toString());
            data.addProperty("name", ((GameProfile)this.Ø­áŒŠá()).getName());
            super.HorizonCode_Horizon_È(data);
        }
    }
    
    private static GameProfile Â(final JsonObject p_152648_0_) {
        if (p_152648_0_.has("uuid") && p_152648_0_.has("name")) {
            final String var1 = p_152648_0_.get("uuid").getAsString();
            UUID var2;
            try {
                var2 = UUID.fromString(var1);
            }
            catch (Throwable var3) {
                return null;
            }
            return new GameProfile(var2, p_152648_0_.get("name").getAsString());
        }
        return null;
    }
}
