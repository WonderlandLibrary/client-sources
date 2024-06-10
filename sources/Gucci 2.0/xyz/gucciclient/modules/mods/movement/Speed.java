package xyz.gucciclient.modules.mods.movement;

import xyz.gucciclient.modules.*;
import xyz.gucciclient.values.*;
import net.minecraftforge.fml.common.gameevent.*;
import xyz.gucciclient.utils.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Speed extends Module
{
    private NumberValue vanillaspeed;
    private BooleanValue janitor;
    private BooleanValue Guardian;
    private BooleanValue vanilla;
    private BooleanValue gcheat;
    private BooleanValue velt;
    
    public Speed() {
        super(Modules.Speed.name(), 0, Category.MOVEMENT);
        this.vanillaspeed = new NumberValue("Vanilla speed", 1.2, 0.1, 2.0);
        this.janitor = new BooleanValue("Janitor", false);
        this.Guardian = new BooleanValue("Guardian", false);
        this.vanilla = new BooleanValue("Vanilla", false);
        this.gcheat = new BooleanValue("Gcheat", false);
        this.velt = new BooleanValue("Velt", false);
        this.addBoolean(this.vanilla);
        this.addBoolean(this.Guardian);
        this.addBoolean(this.janitor);
        this.addBoolean(this.gcheat);
        this.addBoolean(this.velt);
        this.addValue(this.vanillaspeed);
    }
    
    @Override
    public void onDisable() {
        MovementUtils.setSpeed(1.0);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (!this.getState()) {
            return;
        }
        if (Wrapper.getPlayer() == null) {
            return;
        }
        if (Wrapper.getWorld() == null) {
            return;
        }
        if (!MovementUtils.isMoving()) {
            return;
        }
        if (Wrapper.getPlayer().isSneaking()) {
            return;
        }
        if (Wrapper.getPlayer().isInWater()) {
            return;
        }
        if (this.Guardian.getState()) {
            if (MovementUtils.isMoving() && Wrapper.getPlayer().onGround && !Wrapper.getPlayer().isEating()) {
                MovementUtils.setSpeed(0.5199999809265137);
                Wrapper.getPlayer().motionY = 0.2;
            }
            else {
                MovementUtils.setSpeed(0.24);
                final EntityPlayerSP player2;
                final EntityPlayerSP player = player2 = Wrapper.getPlayer();
                --player2.motionY;
            }
        }
        if (this.vanilla.getState()) {
            MovementUtils.setSpeed(this.vanillaspeed.getValue());
        }
        if (this.janitor.getState()) {
            final boolean ticks = Wrapper.getPlayer().ticksExisted % 2 == 0;
            MovementUtils.setSpeed(MovementUtils.getSpeed() * (ticks ? 0.0f : 5.0f));
        }
        if (this.gcheat.getState()) {
            if (Wrapper.getPlayer().onGround) {
                MovementUtils.setSpeed(0.499315221);
                Wrapper.getPlayer().motionY = 0.39936;
            }
            else {
                MovementUtils.setSpeed(0.3894613);
            }
        }
        if (this.velt.getState() && Wrapper.getPlayer().onGround) {
            MovementUtils.setSpeed(1.2);
        }
    }
}
