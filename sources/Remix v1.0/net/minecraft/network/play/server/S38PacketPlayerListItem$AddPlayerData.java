package net.minecraft.network.play.server;

import net.minecraft.world.*;
import com.mojang.authlib.*;
import net.minecraft.util.*;

public class AddPlayerData
{
    private final int field_179966_b;
    private final WorldSettings.GameType field_179967_c;
    private final GameProfile field_179964_d;
    private final IChatComponent field_179965_e;
    
    public AddPlayerData(final GameProfile p_i45965_2_, final int p_i45965_3_, final WorldSettings.GameType p_i45965_4_, final IChatComponent p_i45965_5_) {
        this.field_179964_d = p_i45965_2_;
        this.field_179966_b = p_i45965_3_;
        this.field_179967_c = p_i45965_4_;
        this.field_179965_e = p_i45965_5_;
    }
    
    public GameProfile func_179962_a() {
        return this.field_179964_d;
    }
    
    public int func_179963_b() {
        return this.field_179966_b;
    }
    
    public WorldSettings.GameType func_179960_c() {
        return this.field_179967_c;
    }
    
    public IChatComponent func_179961_d() {
        return this.field_179965_e;
    }
}
