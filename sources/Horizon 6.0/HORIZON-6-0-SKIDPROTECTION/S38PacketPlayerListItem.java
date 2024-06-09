package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.GameProfile;
import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.List;

public class S38PacketPlayerListItem implements Packet
{
    private HorizonCode_Horizon_È HorizonCode_Horizon_È;
    private final List Â;
    private static final String Ý = "CL_00001318";
    
    public S38PacketPlayerListItem() {
        this.Â = Lists.newArrayList();
    }
    
    public S38PacketPlayerListItem(final HorizonCode_Horizon_È p_i45967_1_, final EntityPlayerMP... p_i45967_2_) {
        this.Â = Lists.newArrayList();
        this.HorizonCode_Horizon_È = p_i45967_1_;
        for (final EntityPlayerMP var6 : p_i45967_2_) {
            this.Â.add(new Â(var6.áˆºà(), var6.Ø, var6.Ý.HorizonCode_Horizon_È(), var6.áˆºÉ()));
        }
    }
    
    public S38PacketPlayerListItem(final HorizonCode_Horizon_È p_i45968_1_, final Iterable p_i45968_2_) {
        this.Â = Lists.newArrayList();
        this.HorizonCode_Horizon_È = p_i45968_1_;
        for (final EntityPlayerMP var4 : p_i45968_2_) {
            this.Â.add(new Â(var4.áˆºà(), var4.Ø, var4.Ý.HorizonCode_Horizon_È(), var4.áˆºÉ()));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = (HorizonCode_Horizon_È)data.HorizonCode_Horizon_È(HorizonCode_Horizon_È.class);
        for (int var2 = data.Ø­áŒŠá(), var3 = 0; var3 < var2; ++var3) {
            GameProfile var4 = null;
            int var5 = 0;
            WorldSettings.HorizonCode_Horizon_È var6 = null;
            IChatComponent var7 = null;
            switch (S38PacketPlayerListItem.Ý.HorizonCode_Horizon_È[this.HorizonCode_Horizon_È.ordinal()]) {
                case 1: {
                    var4 = new GameProfile(data.Ó(), data.Ý(16));
                    for (int var8 = data.Ø­áŒŠá(), var9 = 0; var9 < var8; ++var9) {
                        final String var10 = data.Ý(32767);
                        final String var11 = data.Ý(32767);
                        if (data.readBoolean()) {
                            var4.getProperties().put((Object)var10, (Object)new Property(var10, var11, data.Ý(32767)));
                        }
                        else {
                            var4.getProperties().put((Object)var10, (Object)new Property(var10, var11));
                        }
                    }
                    var6 = WorldSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(data.Ø­áŒŠá());
                    var5 = data.Ø­áŒŠá();
                    if (data.readBoolean()) {
                        var7 = data.Ý();
                        break;
                    }
                    break;
                }
                case 2: {
                    var4 = new GameProfile(data.Ó(), (String)null);
                    var6 = WorldSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(data.Ø­áŒŠá());
                    break;
                }
                case 3: {
                    var4 = new GameProfile(data.Ó(), (String)null);
                    var5 = data.Ø­áŒŠá();
                    break;
                }
                case 4: {
                    var4 = new GameProfile(data.Ó(), (String)null);
                    if (data.readBoolean()) {
                        var7 = data.Ý();
                        break;
                    }
                    break;
                }
                case 5: {
                    var4 = new GameProfile(data.Ó(), (String)null);
                    break;
                }
            }
            this.Â.add(new Â(var4, var5, var6, var7));
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        data.Â(this.Â.size());
        for (final Â var3 : this.Â) {
            switch (S38PacketPlayerListItem.Ý.HorizonCode_Horizon_È[this.HorizonCode_Horizon_È.ordinal()]) {
                case 1: {
                    data.HorizonCode_Horizon_È(var3.HorizonCode_Horizon_È().getId());
                    data.HorizonCode_Horizon_È(var3.HorizonCode_Horizon_È().getName());
                    data.Â(var3.HorizonCode_Horizon_È().getProperties().size());
                    for (final Property var5 : var3.HorizonCode_Horizon_È().getProperties().values()) {
                        data.HorizonCode_Horizon_È(var5.getName());
                        data.HorizonCode_Horizon_È(var5.getValue());
                        if (var5.hasSignature()) {
                            data.writeBoolean(true);
                            data.HorizonCode_Horizon_È(var5.getSignature());
                        }
                        else {
                            data.writeBoolean(false);
                        }
                    }
                    data.Â(var3.Ý().HorizonCode_Horizon_È());
                    data.Â(var3.Â());
                    if (var3.Ø­áŒŠá() == null) {
                        data.writeBoolean(false);
                        continue;
                    }
                    data.writeBoolean(true);
                    data.HorizonCode_Horizon_È(var3.Ø­áŒŠá());
                    continue;
                }
                case 4: {
                    data.HorizonCode_Horizon_È(var3.HorizonCode_Horizon_È().getId());
                    if (var3.Ø­áŒŠá() == null) {
                        data.writeBoolean(false);
                        continue;
                    }
                    data.writeBoolean(true);
                    data.HorizonCode_Horizon_È(var3.Ø­áŒŠá());
                    continue;
                }
                default: {
                    continue;
                }
                case 2: {
                    data.HorizonCode_Horizon_È(var3.HorizonCode_Horizon_È().getId());
                    data.Â(var3.Ý().HorizonCode_Horizon_È());
                    continue;
                }
                case 3: {
                    data.HorizonCode_Horizon_È(var3.HorizonCode_Horizon_È().getId());
                    data.Â(var3.Â());
                    continue;
                }
                case 5: {
                    data.HorizonCode_Horizon_È(var3.HorizonCode_Horizon_È().getId());
                    continue;
                }
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180743_1_) {
        p_180743_1_.HorizonCode_Horizon_È(this);
    }
    
    public List HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public HorizonCode_Horizon_È Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("ADD_PLAYER", 0, "ADD_PLAYER", 0), 
        Â("UPDATE_GAME_MODE", 1, "UPDATE_GAME_MODE", 1), 
        Ý("UPDATE_LATENCY", 2, "UPDATE_LATENCY", 2), 
        Ø­áŒŠá("UPDATE_DISPLAY_NAME", 3, "UPDATE_DISPLAY_NAME", 3), 
        Âµá€("REMOVE_PLAYER", 4, "REMOVE_PLAYER", 4);
        
        private static final HorizonCode_Horizon_È[] Ó;
        private static final String à = "CL_00002295";
        
        static {
            Ø = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€ };
            Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€ };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45966_1_, final int p_i45966_2_) {
        }
    }
    
    public class Â
    {
        private final int Â;
        private final WorldSettings.HorizonCode_Horizon_È Ý;
        private final GameProfile Ø­áŒŠá;
        private final IChatComponent Âµá€;
        private static final String Ó = "CL_00002294";
        
        public Â(final GameProfile p_i45965_2_, final int p_i45965_3_, final WorldSettings.HorizonCode_Horizon_È p_i45965_4_, final IChatComponent p_i45965_5_) {
            this.Ø­áŒŠá = p_i45965_2_;
            this.Â = p_i45965_3_;
            this.Ý = p_i45965_4_;
            this.Âµá€ = p_i45965_5_;
        }
        
        public GameProfile HorizonCode_Horizon_È() {
            return this.Ø­áŒŠá;
        }
        
        public int Â() {
            return this.Â;
        }
        
        public WorldSettings.HorizonCode_Horizon_È Ý() {
            return this.Ý;
        }
        
        public IChatComponent Ø­áŒŠá() {
            return this.Âµá€;
        }
    }
    
    static final class Ý
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002296";
        
        static {
            HorizonCode_Horizon_È = new int[S38PacketPlayerListItem.HorizonCode_Horizon_È.values().length];
            try {
                Ý.HorizonCode_Horizon_È[S38PacketPlayerListItem.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Ý.HorizonCode_Horizon_È[S38PacketPlayerListItem.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                Ý.HorizonCode_Horizon_È[S38PacketPlayerListItem.HorizonCode_Horizon_È.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                Ý.HorizonCode_Horizon_È[S38PacketPlayerListItem.HorizonCode_Horizon_È.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                Ý.HorizonCode_Horizon_È[S38PacketPlayerListItem.HorizonCode_Horizon_È.Âµá€.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
        }
    }
}
