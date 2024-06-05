package net.shoreline.client.impl.module.movement;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.network.PlayerUpdateEvent;
import net.shoreline.client.impl.event.network.SetCurrentHandEvent;
import net.shoreline.client.init.Managers;
import net.shoreline.client.util.player.MovementUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.shoreline.client.util.Globals;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class TickShiftModule extends ToggleModule
{
    // Basically auto timer for NCP
    //
    Config<Integer> ticksConfig = new NumberConfig<>("MaxTicks", "Maximum " +
            "charge ticks", 1, 20, 40);
    Config<Integer> packetsConfig = new NumberConfig<>("Packets", "Packets" +
            " to release from storage every tick", 1, 1, 5);
    Config<Integer> chargeSpeedConfig = new NumberConfig<>("ChargeSpeed",
            "The speed to charge the stored packets", 1, 1, 5);
    Config<Boolean> consumablesConfig = new BooleanConfig("Consumables",
            "Allows player to consume items faster", false);
    //
    private int packets;

    /**
     *
     */
    public TickShiftModule()
    {
        super("TickShift", "Exploits NCP to speed up ticks",
                ModuleCategory.MOVEMENT);
    }

    /**
     *
     * @return
     */
    @Override
    public String getModuleData()
    {
        return String.valueOf(packets);
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onPlayerUpdate(PlayerUpdateEvent event)
    {
        if (event.getStage() != EventStage.PRE)
        {
            return;
        }
        if (MovementUtil.isMoving() || !mc.player.isOnGround())
        {
            packets -= packetsConfig.getValue();
            if (packets <= 0)
            {
                packets = 0;
                Managers.TICK.setClientTick(1.0f);
                return;
            }
            Managers.TICK.setClientTick(packetsConfig.getValue() + 1.0f);
        }
        else
        {
            packets += chargeSpeedConfig.getValue();
            if (packets > ticksConfig.getValue())
            {
                packets = ticksConfig.getValue();
            }
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onSetCurrentHand(SetCurrentHandEvent event)
    {
        if (consumablesConfig.getValue())
        {
            ItemStack stack = event.getStackInHand();
            if (!stack.getItem().isFood() && !(stack.getItem() instanceof PotionItem))
            {
                return;
            }
            int maxUseTime = stack.getMaxUseTime();
            if (packets < maxUseTime)
            {
                return;
            }
            for (int i = 0; i < maxUseTime; i++)
            {
                Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(),
                        mc.player.getY(), mc.player.getZ(), mc.player.isOnGround()));
                event.cancel();
                stack.getItem().finishUsing(stack, mc.world, mc.player);
                packets -= maxUseTime;
            }
        }
    }
}
