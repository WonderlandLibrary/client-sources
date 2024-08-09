package fun.ellant.functions.impl.player;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.mojang.authlib.GameProfile;
import fun.ellant.events.EventPacket;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.BooleanSetting;
import fun.ellant.functions.settings.impl.ModeListSetting;
import fun.ellant.utils.client.Counter;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChatPacket;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@FunctionRegister(name = "AutoDuel", type = Category.PLAYER, desc = "Кидает дуэли игрокам")
public class AutoDuel extends Function {

    private static final Pattern pattern = Pattern.compile("^\\w{3,16}$");

    private final ModeListSetting mode = new ModeListSetting("Вид дуэлей",
            new BooleanSetting("Шары", true),
            new BooleanSetting("Щит", false),
            new BooleanSetting("Шипы 3", false),
            new BooleanSetting("Незеритка", false),
            new BooleanSetting("Читерский рай", false),
            new BooleanSetting("Лук", false),
            new BooleanSetting("Классик", false),
            new BooleanSetting("Тотемы", false),
            new BooleanSetting("Нодебафф", false)
    );
    private double lastPosX, lastPosY, lastPosZ;
    public AutoDuel() {
        addSettings(mode);
    }


    private final List<String> sent = Lists.newArrayList();

    private final Counter counter = Counter.create();
    private final Counter counter2 = Counter.create();
    private final Counter counterChoice = Counter.create();
    private final Counter counterTo = Counter.create();


    @Subscribe
    public void onUpdate(EventUpdate e) {
        final List<String> players = getOnlinePlayers();

        double distance = Math.sqrt(Math.pow(lastPosX - mc.player.getPosX(), 2)
                + Math.pow(lastPosY - mc.player.getPosY(), 2)
                + Math.pow(lastPosZ - mc.player.getPosZ(), 2));

        if (distance > 500) {
            toggle();
        }

        lastPosX = mc.player.getPosX();
        lastPosY = mc.player.getPosY();
        lastPosZ = mc.player.getPosZ();



        if (counter2.hasReached(800L * players.size())) {
            sent.clear();
            counter2.reset();
        }

        for (final String player : players) {
            if (!sent.contains(player) && !player.equals(mc.session.getProfile().getName())) {
                if (counter.hasReached(1000)) {
                    mc.player.sendChatMessage("/duel " + player);
                    sent.add(player);
                    counter.reset();
                }
            }
        }


        if (mc.player.openContainer instanceof ChestContainer chest) {
            if (mc.currentScreen.getTitle().getString().contains("Выбор набора (1/1)")) {
                for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
                    if (counterTo.hasReached(150)) {
                        mc.playerController.windowClick(chest.windowId,1,0,ClickType.QUICK_MOVE,mc.player);
                    }
                }
            } else if (mc.currentScreen.getTitle().getString().contains("Настройка поединка")) {
                if (counterTo.hasReached(150)) {
                    mc.playerController.windowClick(chest.windowId, 0, 0, ClickType.QUICK_MOVE, mc.player);
                    counterTo.reset();
                }
            }
        }
    };

    @Subscribe
    private void onPacket(EventPacket eventPacket) {
        if (eventPacket.isReceive()) {
            IPacket<?> packet = eventPacket.getPacket();

            if (packet instanceof SChatPacket chat) {
                final String text = chat.getChatComponent().getString().toLowerCase();
                if ((text.contains("начало") && text.contains("через") && text.contains("секунд!")) || (text.equals("дуэли » во время поединка запрещено использовать команды"))) {
                    toggle();
                }
            }
        }
    };

    private List<String> getOnlinePlayers() {
        return mc.player.connection.getPlayerInfoMap().stream()
                .map(NetworkPlayerInfo::getGameProfile)
                .map(GameProfile::getName)
                .filter(profileName -> pattern.matcher(profileName).matches())
                .collect(Collectors.toList());
    }

    @Override
    public boolean onEnable() {
        super.onEnable();
        return false;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}