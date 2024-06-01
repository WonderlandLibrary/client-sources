package best.actinium.module.impl.combat;

import best.actinium.Actinium;
import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.UpdateEvent;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.module.impl.movement.scaffold.ScaffoldWalkModule;
import best.actinium.property.impl.BooleanProperty;
import best.actinium.property.impl.ModeProperty;
import best.actinium.property.impl.NumberProperty;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

/**
 * @author Nyghtfull
 * @since 1/13/2024
 */
@ModuleInfo(
        name = "Regen",
        description = "Regens U And works as a fast use on grim.",
        category = ModuleCategory.COMBAT
)
public class RegenModule extends Module {
    private ModeProperty mode = new ModeProperty("Mode", this, new String[] {"Vanilla","Grim"}, "Grim");
    private NumberProperty c06Amount = new NumberProperty("C06 Amount",this,1,30,80,1);
    private BooleanProperty advanced = new BooleanProperty("Advanced",this,false);
    private NumberProperty c06AmountLower = new NumberProperty("C06 Amount When HP Is Lower",this,1,30,80,1).
            setHidden(() -> !advanced.isEnabled());

    @Callback
    public void onMotionEvent(UpdateEvent event) {
        if(event.getType() == EventType.POST) {
            return;
        }

        this.setSuffix(mode.getMode());

        if(mc.thePlayer.isUsingItem()) {
            mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0, 0.5f, 0));
        }

        switch (mode.getMode()) {
            case "Grim":
                if(Actinium.INSTANCE.getModuleManager().get(ScaffoldWalkModule.class).isEnabled()) {
                    return;
                }
                //for (int i = 0; i < (mc.thePlayer.getHealth() > 20 ? 10 : 30); i++) {
                for (int i = 0; i < 10 ; i++) {
                    mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.onGround));
                }
                break;
        }
    }
}
