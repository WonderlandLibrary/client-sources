package im.expensive.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventPacket;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.BooleanSetting;
import im.expensive.utils.client.ClientUtil;
import im.expensive.utils.math.StopWatch;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.util.text.TextFormatting;

import java.util.Locale;

@FieldDefaults(level = AccessLevel.PRIVATE)
@FunctionRegister(name = "SaveInventory", type = Category.Misc)
public class SaveInventory extends Function {

    final BooleanSetting clanHome = new BooleanSetting("Клан хом", true);
    final BooleanSetting clanInvest = new BooleanSetting("Сохранить деньги", true);

    final StopWatch stopWatch = new StopWatch();
    int serverNumber = -1;
    boolean inHub, inClanHome;

    public SaveInventory() {
        addSettings(clanHome, clanInvest);
    }

    @Subscribe
    private void onPacket(EventPacket packetEvent) {
        if (!ClientUtil.isConnectedToServer("funtime")) return;

        if (packetEvent.getPacket() instanceof SChatPacket chatPacket) {
            String chatMessage = chatPacket.getChatComponent().getString().toLowerCase(Locale.ROOT);
            if (chatMessage.contains("успешная телепортация в базу клана!")) inClanHome = true;
        }
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        String tabHeader = mc.ingameGUI.getTabList().header.getString();

        if (!tabHeader.contains("Хаб")) {
            leaveInHub();
        } else {
            inHub = true;
        }

        if (serverNumber == -1) {
            return;
        }

        if (stopWatch.isReached(150) && inHub) {
            returnToAnarchyAndDropItem(tabHeader);
            stopWatch.reset();
        }

    }

    private void returnToAnarchyAndDropItem(String header) {
        if (!header.contains(String.valueOf(serverNumber))) {
            mc.player.sendChatMessage("/an" + serverNumber);
            return;
        }

        if (clanHome.get() && !inClanHome) {
            mc.player.sendChatMessage("/clan home");
        } else {
            dropItems();
        }
    }


    private void dropItems() {
        for (int i = 0; i < mc.player.container.getInventory().size(); ++i) {
            mc.playerController.windowClick(0, i, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, -999, 0, ClickType.PICKUP, mc.player);
        }
        if (isInventoryEmpty()) {
            toggle();
            reset();
        }
    }

    private boolean isInventoryEmpty() {
        for (int i = 0; i < mc.player.container.getInventory().size(); ++i) {
            if (!mc.player.inventory.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void leaveInHub() {
        System.gc();
        serverNumber = getAnarchyServerNumber();

        if (stopWatch.isReached(100) && !inHub) {
            mc.player.sendChatMessage("/hub");
            stopWatch.reset();
        }
    }

    private int getAnarchyServerNumber() {
        if (mc.ingameGUI.getTabList().header != null) {
            String serverHeader = TextFormatting.getTextWithoutFormattingCodes(mc.ingameGUI.getTabList().header.getString());
            if (serverHeader != null && serverHeader.contains("Анархия-")) {
                return Integer.parseInt(serverHeader.split("Анархия-")[1].trim());
            }
        }
        return -1;
    }

    private void reset() {
        inHub = false;
        inClanHome = false;
        serverNumber = -1;
    }

    @Override
    public void onDisable() {
        reset();
        super.onDisable();
    }
}
