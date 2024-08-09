package src.Wiksi.functions.impl.misc;


import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventPacket;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChatPacket;
import java.util.Locale;
@FunctionRegister(name = "AutoEventGps", type = Category.Misc)
public class AutoEventGps extends Function {
    public AutoEventGps() {
    }

    @Subscribe
    private void onPacket(EventPacket packetEvent) {
        IPacket var3 = packetEvent.getPacket();
        if (var3 instanceof SChatPacket chatPacket) {
            String chatMessage = chatPacket.getChatComponent().getString().toLowerCase(Locale.ROOT);
            if (chatMessage.contains("[мистический сундук]") || chatMessage.contains("[вулкан]") || chatMessage.contains("[метеорит]") || chatMessage.contains("[маяк]")) {
                String coordinatesLine = this.getCoordinatesLine(chatMessage);
                if (!coordinatesLine.isEmpty()) {
                    this.print("Coordinates line: " + coordinatesLine);
                    int[] coordinates = this.extractCoordinates(coordinatesLine);
                    if (coordinates != null) {
                        this.print("Extracted coordinates: " + coordinates[0] + ", " + coordinates[1] + ", " + coordinates[2]);
                        String lootLevel = this.getLootLevel(chatMessage);
                        if (!lootLevel.isEmpty()) {
                            this.print("Loot level: " + lootLevel);
                            mc.player.sendChatMessage(".gps add " + lootLevel + " " + coordinates[0] + " " + coordinates[1] + " " + coordinates[2]);
                        }
                    }
                }
            }
        }

    }

    private String getCoordinatesLine(String message) {
        String[] lines = message.split("\n");
        String[] var3 = lines;
        int var4 = lines.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String line = var3[var5];
            if (line.contains("Появился на координатах:")) {
                return line;
            }
        }

        return "";
    }

    private int[] extractCoordinates(String line) {
        String[] parts = line.split("[\\[;\\]]");
        if (parts.length >= 4) {
            try {
                int x = Integer.parseInt(parts[1].trim());
                int y = Integer.parseInt(parts[2].trim());
                int z = Integer.parseInt(parts[3].trim());
                return new int[]{x, y, z};
            } catch (NumberFormatException var6) {
                var6.printStackTrace();
            }
        }

        return null;
    }

    private String getLootLevel(String message) {
        String lootLevel = "";
        String[] lines = message.split("\n");
        String[] var4 = lines;
        int var5 = lines.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            String line = var4[var6];
            if (line.contains("Уровень лута:")) {
                lootLevel = line.substring(line.indexOf(":") + 1).trim();
                break;
            }
        }

        return lootLevel;
    }
}