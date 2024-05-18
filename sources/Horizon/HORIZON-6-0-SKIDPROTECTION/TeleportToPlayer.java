package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.Collection;
import com.google.common.collect.ComparisonChain;
import java.util.Comparator;
import java.util.List;
import com.google.common.collect.Ordering;

public class TeleportToPlayer implements ISpectatorMenuView, ISpectatorMenuObject
{
    private static final Ordering HorizonCode_Horizon_È;
    private final List Â;
    private static final String Ý = "CL_00001922";
    
    static {
        HorizonCode_Horizon_È = Ordering.from((Comparator)new Comparator() {
            private static final String HorizonCode_Horizon_È = "CL_00001921";
            
            public int HorizonCode_Horizon_È(final NetworkPlayerInfo p_178746_1_, final NetworkPlayerInfo p_178746_2_) {
                return ComparisonChain.start().compare((Comparable)p_178746_1_.HorizonCode_Horizon_È().getId(), (Comparable)p_178746_2_.HorizonCode_Horizon_È().getId()).result();
            }
            
            @Override
            public int compare(final Object p_compare_1_, final Object p_compare_2_) {
                return this.HorizonCode_Horizon_È((NetworkPlayerInfo)p_compare_1_, (NetworkPlayerInfo)p_compare_2_);
            }
        });
    }
    
    public TeleportToPlayer() {
        this(TeleportToPlayer.HorizonCode_Horizon_È.sortedCopy((Iterable)Minecraft.áŒŠà().µÕ().Ý()));
    }
    
    public TeleportToPlayer(final Collection p_i45493_1_) {
        this.Â = Lists.newArrayList();
        for (final NetworkPlayerInfo var3 : TeleportToPlayer.HorizonCode_Horizon_È.sortedCopy((Iterable)p_i45493_1_)) {
            if (var3.Â() != WorldSettings.HorizonCode_Horizon_È.Âµá€) {
                this.Â.add(new PlayerMenuObject(var3.HorizonCode_Horizon_È()));
            }
        }
    }
    
    @Override
    public List HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    @Override
    public IChatComponent Â() {
        return new ChatComponentText("Select a player to teleport to");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final SpectatorMenu p_178661_1_) {
        p_178661_1_.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public IChatComponent Ý() {
        return new ChatComponentText("Teleport to player");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_178663_1_, final int p_178663_2_) {
        Minecraft.áŒŠà().¥à().HorizonCode_Horizon_È(GuiSpectator.HorizonCode_Horizon_È);
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, 0.0f, 0.0f, 16, 16, 256.0f, 256.0f);
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        return !this.Â.isEmpty();
    }
}
