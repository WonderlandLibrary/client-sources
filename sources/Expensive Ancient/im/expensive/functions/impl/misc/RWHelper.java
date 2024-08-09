package im.expensive.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventPacket;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.BooleanSetting;
import im.expensive.functions.settings.impl.ModeListSetting;
import im.expensive.utils.math.StopWatch;
import net.minecraft.network.play.client.CChatMessagePacket;
import net.minecraft.network.play.server.SOpenWindowPacket;
import net.minecraft.network.play.server.SRespawnPacket;
import net.minecraft.network.play.server.SUpdateBossInfoPacket;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.util.UUID;

@FunctionRegister(name = "RWHelper", type = Category.Misc)
public class    RWHelper extends Function {
    boolean joined;
    StopWatch stopWatch = new StopWatch();

    private final ModeListSetting s = new ModeListSetting("Функции",
            new BooleanSetting("Блокировать запрещенные слова", true),
            new BooleanSetting("Закрывать меню", true),
            new BooleanSetting("Авто точка", true),
            new BooleanSetting("Уведомления", true));

    private UUID uuid;
    int x = -1, z = -1;
    private TrayIcon trayIcon;

    public RWHelper() {
        addSettings(s);
    }

    String[] banWords = new String[]{
            "экспа", "экспенсив",
            "еблан тупой", "маму ебал",
            "чит буст", "чит буст",
            "ТЫ ЕБЛАН ТУПОЙ МАМЫ У ТЯ НЕТУ", "нету мамы у  тебя",
            "ххахахахах expensive бустит", "хахахах буст",
            "экспой", "нуриком",
            "целкой", "нурлан",
            "нурсултан", "целестиал",
            "целка", "нурик",
            "атернос", "expa",
            "celka", "nurik",
            "expensive", "celestial",
            "nursultan", "фанпей",
            "funpay", "fluger",
            "акриен", "akrien",
            "фантайм", "ft",
            "funtime", "безмамный",
            "rich", "рич",
            "без мамный", "wild",
            "вилд", "excellent",
            "экселлент", "hvh",
            "хвх", "matix",
            "impact", "матикс",
            "импакт", "wurst", "рикер"};

    @Subscribe
    private void onPacket(EventPacket e) {
        if (e.isSend()) {
            if (e.getPacket() instanceof CChatMessagePacket p) {
                boolean contains = false;
                for (String str : banWords) {
                    if (!p.getMessage().toLowerCase().contains(str)) {
                        continue;
                    }
                    contains = true;
                    break;
                }
                if (contains) {
                    print("RW Helper |" + TextFormatting.RED + " Обнаружены запрещенные слова в вашем сообщении. " +
                            "Отправка отменена, чтобы избежать бана на ReallyWorld.");
                    e.cancel();
                }
            }
        }
        if (e.isReceive()) {
            if (e.getPacket() instanceof SUpdateBossInfoPacket packet) {
                if (s.getValueByName("Авто точка").get()) {
                    updateBossInfo(packet);
                }
            }
            if (s.getValueByName("Закрывать меню").get()) {
                if (e.getPacket() instanceof SRespawnPacket p) {
                    joined = true;
                    stopWatch.reset();
                }
                if (e.getPacket() instanceof SOpenWindowPacket w) {
                    if (w.getTitle().getString().contains("Меню") && joined && !stopWatch.isReached(2000)) {
                        mc.player.closeScreen();
                        e.cancel();
                        joined = false;
                    }
                }
            }
        }
    }

    public void updateBossInfo(SUpdateBossInfoPacket packet) {
        if (packet.getOperation() == SUpdateBossInfoPacket.Operation.ADD) {
            String name = packet.getName().getString().toLowerCase().replaceAll("\\s+", " ");

            if (name.contains("аирдроп")) {
                parseAirDrop(name);
                uuid = packet.getUniqueId();
            } else if (name.contains("талисман")) {
                parseMascot(name);
                uuid = packet.getUniqueId();
            } else if (name.contains("скрудж")) {
                parseScrooge(name);
                uuid = packet.getUniqueId();
            }
        } else if (packet.getOperation() == SUpdateBossInfoPacket.Operation.REMOVE) {
            if (packet.getUniqueId().equals(uuid)) {
                resetCoordinatesAndRemoveWaypoints();
            }
        }
    }

    private void parseAirDrop(String name) {
        x = extractCoordinate(name, "x: ");
        z = extractCoordinate(name, "z: ");
        if (s.getValueByName("Уведомление").get()) {
            windows("RWHelper", "Появился аирдроп!", false);
        }
        mc.player.sendChatMessage(".way add АирДроп " + x + " " + 100 + " " + z);
    }

    private void parseMascot(String name) {
        String[] words = name.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            if (isInteger(words[i]) && i + 1 < words.length && isInteger(words[i + 1])) {
                x = Integer.parseInt(words[i]);
                z = Integer.parseInt(words[i + 1]);
                if (s.getValueByName("Уведомление").get()) {
                    windows("RWHelper", "Появился талисман!", false);
                }
                mc.player.sendChatMessage(".way add Талисман " + x + " " + 100 + " " + z);
            }
        }
    }

    private void parseScrooge(String name) {
        int startIndex = name.indexOf("Координаты");
        if (startIndex == -1) {
            return;
        }
        String coordinatesSubstring = name.substring(startIndex + "Координаты".length()).trim();

        String[] words = coordinatesSubstring.split("\\s+");

        if (words.length >= 2) {
            x = Integer.parseInt(words[0]);
            z = Integer.parseInt(words[1]);
            if (s.getValueByName("Уведомление").get()) {
                windows("RWHelper", "Появился скрудж!", false);
            }
            mc.player.sendChatMessage(".way add Скрудж " + x + " " + 100 + " " + z);
        }
    }

    private void resetCoordinatesAndRemoveWaypoints() {
        x = 0;
        z = 0;
        mc.player.sendChatMessage(".way remove АирДроп");
        mc.player.sendChatMessage(".way remove Талисман");
        mc.player.sendChatMessage(".way remove Скрудж");
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int extractCoordinate(String text, String coordinateIdentifier) {
        int coordinateStartIndex = text.indexOf(coordinateIdentifier);
        if (coordinateStartIndex != -1) {
            int coordinateValueStart = coordinateStartIndex + coordinateIdentifier.length();
            int coordinateValueEnd = text.indexOf(" ", coordinateValueStart);
            if (coordinateValueEnd == -1) {
                coordinateValueEnd = text.length();
            }
            String coordinateValueString = text.substring(coordinateValueStart, coordinateValueEnd);
            return Integer.parseInt(coordinateValueString.trim());
        }
        return 0;
    }

    private void windows(String name, String desc, boolean error) {
        print(desc);
        if (SystemTray.isSupported()) {
            try {
                if (trayIcon == null) {
                    SystemTray systemTray = SystemTray.getSystemTray();
                    Image image = Toolkit.getDefaultToolkit().createImage("");
                    trayIcon = new TrayIcon(image, "Baritone");
                    trayIcon.setImageAutoSize(true);
                    trayIcon.setToolTip(name);
                    systemTray.add(trayIcon);
                }
                trayIcon.displayMessage(name, desc, error ? TrayIcon.MessageType.ERROR : TrayIcon.MessageType.INFO);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
