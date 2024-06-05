// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.movement;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import gg.childtrafficking.smokex.utils.player.MovementUtils;
import gg.childtrafficking.smokex.event.events.player.EventStep;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import gg.childtrafficking.smokex.property.properties.EnumProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "Step", renderName = "Step", description = "What are you doing stepbro? (Goes up blocks faster)", category = ModuleCategory.MOVEMENT)
public final class StepModule extends Module
{
    private final double[] block_heights;
    private final double[] offsets;
    private final EnumProperty<Mode> modeEnumProperty;
    private final NumberProperty<Float> stepHeightProperty;
    private final EventListener<EventUpdate> updateEventListener;
    private final EventListener<EventStep> stepEventListener;
    
    public StepModule() {
        this.block_heights = new double[] { 1.5, 0.9375, 0.875, 0.8125, 0.75, 0.6875, 0.625, 0.59375, 0.5625, 0.5, 0.4375, 0.375, 0.3125, 0.25, 0.1875, 0.125, 0.09375, 0.0625 };
        this.offsets = new double[] { 0.41999998688697815, 0.7531999945640564 };
        this.modeEnumProperty = new EnumProperty<Mode>("Mode", Mode.NCP);
        this.stepHeightProperty = new NumberProperty<Float>("StepHeight", "Step Height", 1.0f, 1.0f, 2.5f, 0.5f);
        this.updateEventListener = (event -> this.setSuffix(this.modeEnumProperty.getValueAsString()));
        this.stepEventListener = (event -> {
            if (MovementUtils.isOnGround()) {
                if (event.isPre()) {
                    event.setStepHeight(this.stepHeightProperty.getValue());
                }
                else {
                    final double[] block_heights = this.block_heights;
                    int i = 0;
                    for (int length = block_heights.length; i < length; ++i) {
                        final double height = block_heights[i];
                        if (event.getHeightStepped() % height == 0.0) {
                            final double steppedHeight = event.getHeightStepped();
                            final double[] offsets = this.offsets;
                            int j = 0;
                            for (int length2 = offsets.length; j < length2; ++j) {
                                final double offset = offsets[j];
                                this.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + offset * steppedHeight, this.mc.thePlayer.posZ, false));
                            }
                        }
                        else {}
                    }
                }
            }
        });
    }
    
    private enum Mode
    {
        NCP;
    }
}
