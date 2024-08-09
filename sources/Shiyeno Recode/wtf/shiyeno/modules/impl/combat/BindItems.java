package wtf.shiyeno.modules.impl.combat;

import net.minecraft.item.Items;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.game.EventKey;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BindSetting;
import wtf.shiyeno.util.misc.TimerUtil;

@FunctionAnnotation(
        name = "BindItems",
        type = Type.Combat
)
public class BindItems extends Function {
    private BindSetting trap = new BindSetting("Бинд трапы", 0);
    private BindSetting diz = new BindSetting("Бинд дезориентации", 0);
    private BindSetting boatle = new BindSetting("Бинд бутыльков опыта", 0);
    private final TimerUtil timerUtil = new TimerUtil();

    public BindItems() {
        this.addSettings(new Setting[]{this.trap, this.diz, this.boatle});
    }

    public void onEvent(Event event) {
        if (event instanceof EventKey e) {
            int i;
            if (e.key == this.trap.getKey()) {
                for(i = 0; i < 9; ++i) {
                    if (mc.player.inventory.getStackInSlot(i).getDisplayName().getString().contains("Трапка") && mc.player.inventory.getStackInSlot(i).getItem() == Items.NETHERITE_SCRAP) {
                        mc.player.connection.sendPacket(new CHeldItemChangePacket(i));
                        mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                        mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                    }
                }
            }

            if (e.key == this.diz.getKey()) {
                for(i = 0; i < 9; ++i) {
                    if (mc.player.inventory.getStackInSlot(i).getDisplayName().getString().contains("Дезориентация") && mc.player.inventory.getStackInSlot(i).getItem() == Items.ENDER_EYE) {
                        mc.player.connection.sendPacket(new CHeldItemChangePacket(i));
                        mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                        mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                    }
                }
            }

            if (e.key == this.boatle.getKey()) {
                for(i = 0; i < 9; ++i) {
                    if (mc.player.inventory.getStackInSlot(i).getItem() == Items.EXPERIENCE_BOTTLE) {
                        mc.player.connection.sendPacket(new CHeldItemChangePacket(i));
                        mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                        mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                    }
                }
            }
        }
    }
}