package xyz.gucciclient.modules.mods.combat;

import xyz.gucciclient.modules.*;
import xyz.gucciclient.values.*;
import net.minecraftforge.fml.common.gameevent.*;
import xyz.gucciclient.utils.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class DoxThreat extends Module
{
    private NumberValue HorizontalModifier;
    private NumberValue VerticalModifier;
    
    public DoxThreat() {
        super(Modules.Velocity.name(), 0, Category.COMBAT);
        this.HorizontalModifier = new NumberValue("Horizontal", 100.0, 0.0, 100.0);
        this.VerticalModifier = new NumberValue("Vertical", 100.0, 0.0, 100.0);
        this.addValue(this.HorizontalModifier);
        this.addValue(this.VerticalModifier);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent event) {
        if (Wrapper.getPlayer() == null) {
            return;
        }
        if (Wrapper.getWorld() == null) {
            return;
        }
        final double vertmodifier = this.VerticalModifier.getValue() / 100.0;
        final double horzmodifier = this.HorizontalModifier.getValue() / 100.0;
        if (Wrapper.getPlayer().hurtTime == Wrapper.getPlayer().maxHurtTime && Wrapper.getPlayer().maxHurtTime > 0) {
            final EntityPlayerSP player4;
            final EntityPlayerSP player = player4 = Wrapper.getPlayer();
            player4.motionX *= horzmodifier;
            final EntityPlayerSP player5;
            final EntityPlayerSP player2 = player5 = Wrapper.getPlayer();
            player5.motionZ *= horzmodifier;
            final EntityPlayerSP player6;
            final EntityPlayerSP player3 = player6 = Wrapper.getPlayer();
            player6.motionY *= vertmodifier;
        }
    }
}
