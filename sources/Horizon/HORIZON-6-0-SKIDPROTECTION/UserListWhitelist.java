package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.mojang.authlib.GameProfile;
import com.google.gson.JsonObject;
import java.io.File;

public class UserListWhitelist extends UserList
{
    private static final String Ý = "CL_00001871";
    
    public UserListWhitelist(final File p_i1132_1_) {
        super(p_i1132_1_);
    }
    
    @Override
    protected UserListEntry HorizonCode_Horizon_È(final JsonObject entryData) {
        return new UserListWhitelistEntry(entryData);
    }
    
    @Override
    public String[] Â() {
        final String[] var1 = new String[this.Ý().size()];
        int var2 = 0;
        for (final UserListWhitelistEntry var4 : this.Ý().values()) {
            var1[var2++] = ((GameProfile)var4.Ø­áŒŠá()).getName();
        }
        return var1;
    }
    
    protected String HorizonCode_Horizon_È(final GameProfile p_152704_1_) {
        return p_152704_1_.getId().toString();
    }
    
    public GameProfile HorizonCode_Horizon_È(final String p_152706_1_) {
        for (final UserListWhitelistEntry var3 : this.Ý().values()) {
            if (p_152706_1_.equalsIgnoreCase(((GameProfile)var3.Ø­áŒŠá()).getName())) {
                return (GameProfile)var3.Ø­áŒŠá();
            }
        }
        return null;
    }
    
    @Override
    protected String Ý(final Object obj) {
        return this.HorizonCode_Horizon_È((GameProfile)obj);
    }
}
