package ru.FecuritySQ.module.сражение;

import net.minecraft.item.BowItem;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionNumric;

public class BowSpam extends Module {
    public OptionNumric ticks = new OptionNumric("Задержка", 3, 1, 10, 0.5F);

    public BowSpam() {
        super(Category.Сражение, GLFW.GLFW_KEY_0);
        addOption(ticks);
    }

    @Override
    public void event(Event e) {
        if(e instanceof EventUpdate && isEnabled()){
            if (mc.player.inventory.getCurrentItem().getItem() instanceof BowItem && mc.player.isHandActive()
                    && mc.player.getItemInUseMaxCount() >= ticks.get()) {
                mc.player.connection.sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.RELEASE_USE_ITEM,
                        BlockPos.ZERO, mc.player.getHorizontalFacing()));
                mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                mc.player.stopActiveHand();
            }
        }
    }
}
