package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.mojang.authlib.GameProfile;
import com.google.gson.JsonObject;
import java.io.File;

public class UserListBans extends UserList
{
    private static final String Ý = "CL_00001873";
    
    public UserListBans(final File bansFile) {
        super(bansFile);
    }
    
    @Override
    protected UserListEntry HorizonCode_Horizon_È(final JsonObject entryData) {
        return new UserListBansEntry(entryData);
    }
    
    public boolean HorizonCode_Horizon_È(final GameProfile profile) {
        return this.Ø­áŒŠá(profile);
    }
    
    @Override
    public String[] Â() {
        final String[] var1 = new String[this.Ý().size()];
        int var2 = 0;
        for (final UserListBansEntry var4 : this.Ý().values()) {
            var1[var2++] = ((GameProfile)var4.Ø­áŒŠá()).getName();
        }
        return var1;
    }
    
    protected String Â(final GameProfile profile) {
        return profile.getId().toString();
    }
    
    public GameProfile HorizonCode_Horizon_È(final String username) {
        for (final UserListBansEntry var3 : this.Ý().values()) {
            if (username.equalsIgnoreCase(((GameProfile)var3.Ø­áŒŠá()).getName())) {
                return (GameProfile)var3.Ø­áŒŠá();
            }
        }
        return null;
    }
    
    @Override
    protected String Ý(final Object obj) {
        return this.Â((GameProfile)obj);
    }
}
