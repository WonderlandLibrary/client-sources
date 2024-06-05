package net.minecraft.client.gui.spectator.categories;

import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.spectator.*;
import net.minecraft.client.gui.*;
import java.util.*;
import com.google.common.collect.*;

public class TeleportToPlayer implements ISpectatorMenuView, ISpectatorMenuObject
{
    private static final Ordering field_178674_a;
    private final List field_178673_b;
    
    public TeleportToPlayer() {
        this(TeleportToPlayer.field_178674_a.sortedCopy((Iterable)Minecraft.getMinecraft().getNetHandler().func_175106_d()));
    }
    
    public TeleportToPlayer(final Collection p_i45493_1_) {
        this.field_178673_b = Lists.newArrayList();
        for (final NetworkPlayerInfo var3 : TeleportToPlayer.field_178674_a.sortedCopy((Iterable)p_i45493_1_)) {
            if (var3.getGameType() != WorldSettings.GameType.SPECTATOR) {
                this.field_178673_b.add(new PlayerMenuObject(var3.func_178845_a()));
            }
        }
    }
    
    @Override
    public List func_178669_a() {
        return this.field_178673_b;
    }
    
    @Override
    public IChatComponent func_178670_b() {
        return new ChatComponentText("Select a player to teleport to");
    }
    
    @Override
    public void func_178661_a(final SpectatorMenu p_178661_1_) {
        p_178661_1_.func_178647_a(this);
    }
    
    @Override
    public IChatComponent func_178664_z_() {
        return new ChatComponentText("Teleport to player");
    }
    
    @Override
    public void func_178663_a(final float p_178663_1_, final int p_178663_2_) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, 16, 16, 256.0f, 256.0f);
    }
    
    @Override
    public boolean func_178662_A_() {
        return !this.field_178673_b.isEmpty();
    }
    
    static {
        field_178674_a = Ordering.from((Comparator)new Comparator() {
            public int func_178746_a(final NetworkPlayerInfo p_178746_1_, final NetworkPlayerInfo p_178746_2_) {
                return ComparisonChain.start().compare((Comparable)p_178746_1_.func_178845_a().getId(), (Comparable)p_178746_2_.func_178845_a().getId()).result();
            }
            
            @Override
            public int compare(final Object p_compare_1_, final Object p_compare_2_) {
                return this.func_178746_a((NetworkPlayerInfo)p_compare_1_, (NetworkPlayerInfo)p_compare_2_);
            }
        });
    }
}
