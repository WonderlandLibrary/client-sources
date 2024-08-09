package ru.FecuritySQ.module.сражение;

import net.minecraft.item.Items;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventItemEat;
import ru.FecuritySQ.event.imp.EventItemFoodRightClick;
import ru.FecuritySQ.event.imp.EventPacket;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionNumric;

public class GAppleCooldown extends Module {

    public OptionNumric appleCooldown = new OptionNumric("Задержка", 4.5f, 0, 10, 0.1f);

    public GAppleCooldown() {
        super(Category.Сражение, GLFW.GLFW_KEY_0);
        addOption(appleCooldown);
    }

    @Override
    public void event(Event event) {
        if(!isEnabled()) return;
        if(event instanceof EventItemEat eventItemEat){
            if (eventItemEat.stack.getItem() == Items.GOLDEN_APPLE) {
                mc.player.getCooldownTracker().setCooldown(eventItemEat.stack.getItem(), (int) (appleCooldown.get() * 20));
            }
        }
        if(event instanceof EventItemFoodRightClick eventItemFoodRightClick){
            if (eventItemFoodRightClick.stack.getItem() == Items.GOLDEN_APPLE) {
                if(mc.player.getCooldownTracker().hasCooldown(eventItemFoodRightClick.stack.getItem())){
                    eventItemFoodRightClick.cancel = true;
                    eventItemFoodRightClick.cancelUsingT2o = true;
                }
            }
        }
    }
}
