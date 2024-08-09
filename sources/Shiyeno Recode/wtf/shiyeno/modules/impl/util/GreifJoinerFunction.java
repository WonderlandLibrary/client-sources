package wtf.shiyeno.modules.impl.util;

import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.network.play.server.SJoinGamePacket;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextFormatting;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.packet.EventPacket;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.util.ClientUtil;
import wtf.shiyeno.util.JoinerUtil;
import wtf.shiyeno.util.SoundUtil;
import wtf.shiyeno.util.misc.TimerUtil;

@FunctionAnnotation(
        name = "RWJoiner",
        type = Type.Util
)
public class GreifJoinerFunction extends Function {
    private final SliderSetting griefSelection = new SliderSetting("Номер грифа", 1.0F, 1.0F, 39.0F, 1.0F);
    private final TimerUtil timerUtil = new TimerUtil();

    public GreifJoinerFunction() {
        this.addSettings(new Setting[]{this.griefSelection});
    }

    protected void onEnable() {
        JoinerUtil.selectCompass();
        mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
        super.onEnable();
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            this.handleEventUpdate();
        }

        if (event instanceof EventPacket eventPacket) {
            if (eventPacket.getPacket() instanceof SJoinGamePacket) {
                try {
                    if (mc.ingameGUI.getTabList().header == null) {
                        return;
                    }

                    String string = TextFormatting.getTextWithoutFormattingCodes(mc.ingameGUI.getTabList().header.getString());
                    if (!string.contains("Lobby")) {
                        return;
                    }

                    Object string2 = "Вы успешно зашли на " + this.griefSelection.getValue().intValue() + " гриф!";
                    ClientUtil.sendMesage((String)string2);
                    SoundUtil.playSound(1.0F, 1.0F);
                    this.toggle();
                } catch (Exception var7) {
                }
            }

            SChatPacket packet;
            String message;
            IPacket string2;
            if ((string2 = eventPacket.getPacket()) instanceof SChatPacket && ((message = TextFormatting.getTextWithoutFormattingCodes((packet = (SChatPacket)string2).getChatComponent().getString())).contains("К сожалению сервер переполнен") || message.contains("Подождите 20 секунд!") || message.contains("большой поток игроков"))) {
                JoinerUtil.selectCompass();
                mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            }
        }
    }

    private void handleEventUpdate() {
        if (mc.currentScreen == null) {
            if (mc.player.ticksExisted < 5) {
                mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            }
        } else if (mc.currentScreen instanceof ChestScreen) {
            try {
                int numberGrief = this.griefSelection.getValue().intValue();
                ContainerScreen container = (ContainerScreen)mc.currentScreen;

                for(int i = 0; i < container.getContainer().inventorySlots.size(); ++i) {
                    String s = ((Slot)container.getContainer().inventorySlots.get(i)).getStack().getDisplayName().getString();
                    if (ClientUtil.isConnectedToServer("reallyworld") && s.contains("ГРИФЕРСКОЕ ВЫЖИВАНИЕ") && this.timerUtil.hasTimeElapsed(50L)) {
                        mc.playerController.windowClick(mc.player.openContainer.windowId, i, 0, ClickType.PICKUP, mc.player);
                        this.timerUtil.reset();
                    }

                    if (s.contains("ГРИФ #" + numberGrief + " (1.16.5-1.20.4)") && this.timerUtil.hasTimeElapsed(50L)) {
                        mc.playerController.windowClick(mc.player.openContainer.windowId, i, 0, ClickType.PICKUP, mc.player);
                        this.timerUtil.reset();
                    }
                }
            } catch (Exception var5) {
            }
        }
    }
}