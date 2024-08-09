package ru.FecuritySQ.module.сражение;

import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventMiddleClick;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.module.общее.MidClickFriend;
import ru.FecuritySQ.option.imp.OptionMode;

public class KeyPearl extends Module {
    String[] modes = {"Бинд", "Колесо мыши"};
    public OptionMode mode = new OptionMode("Режим", modes, 0);

    public KeyPearl() {
        super(Category.Сражение, GLFW.GLFW_KEY_0);
        addOption(mode);
    }

    @Override
    public void enable() {
        if(mode.current().equals("Бинд")){
            throwPearl();
            toggle();
        }
        super.enable();
    }

    @Override
    public void event(Event e) {
        if(e instanceof EventMiddleClick && isEnabled() && mode.current().equals("Колесо мыши")){
            MidClickFriend midClickFriend = (MidClickFriend)FecuritySQ.get().getModule(MidClickFriend.class);
            if(midClickFriend.isEnabled()){
                if(mc.pointedEntity == null) {
                    throwPearl();
                }
            }else throwPearl();
        }
    }

    private void throwPearl(){
        for (int i = 0; i < 9; ++i) {
            Item item = mc.player.inventory.getStackInSlot(i).getItem();
            if (item instanceof EnderPearlItem && !mc.player.getCooldownTracker().hasCooldown(item)) {
                final int prevSlot = mc.player.inventory.currentItem;
                mc.player.connection.sendPacket(new CHeldItemChangePacket(i));
                mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                mc.player.connection.sendPacket(new CHeldItemChangePacket(prevSlot));
                break;
            }
        }
    }
}
