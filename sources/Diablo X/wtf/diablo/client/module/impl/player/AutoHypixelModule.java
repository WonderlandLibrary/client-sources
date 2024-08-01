package wtf.diablo.client.module.impl.player;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.network.play.server.S02PacketChat;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.impl.network.RecievePacketEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.notification.Notification;
import wtf.diablo.client.notification.NotificationType;
import wtf.diablo.client.setting.api.IMode;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.setting.impl.NumberSetting;

@ModuleMetaData(
        name = "Auto Hypixel",
        category = ModuleCategoryEnum.PLAYER
)
public final class AutoHypixelModule extends AbstractModule {
    private final ModeSetting<AutoHypixelModeEnum> mode = new ModeSetting<>("Mode", AutoHypixelModeEnum.SKYWARS_SOLO_INSANE);
    private final NumberSetting<Long> delay = new NumberSetting<>("Delay", 1000L, 0L, 10000L, 100L);

    public AutoHypixelModule() {
        this.registerSettings(mode, delay);
    }

    @EventHandler
    private final Listener<RecievePacketEvent> packetListener = e -> {
        this.setSuffix(mode.getValue().getName());

        if (!mc.getCurrentServerData().serverName.endsWith("hypixel.net"))
            if (e.getPacket() instanceof S02PacketChat) {
                final S02PacketChat packet = (S02PacketChat) e.getPacket();
                final String packetText = packet.getChatComponent().getFormattedText();

                if (packetText.startsWith("§cYou died! ") || packetText.equals("§r§a§lQueued! Use the bed to cancel!§r") || packetText.startsWith("§aYou won! ")) {
                    Diablo.getInstance().getNotificationManager().addNotification(new Notification("Auto Hypixel", "Sending you to " + mode.getValue().getName() + " in " + delay.getValue() + "ms", delay.getValue(), NotificationType.SUCCESS));
                    new Thread(() -> {
                        try {
                            Thread.sleep(delay.getValue());
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        mc.thePlayer.sendChatMessage("/play " + mode.getValue().getGameKey());
                    }).start();
                }
            }
    };

    public enum AutoHypixelModeEnum implements IMode {
        SKYWARS_RANKED("SkyWars Ranked", "ranked_normal"),
        SKYWARS_SOLO_NORMAL("SkyWars Solo Normal", "solo_normal"),
        SKYWARS_SOLO_INSANE("SkyWars Solo Insane", "solo_insane"),
        SKYWARS_DOUBLES_NORMAL("SkyWars Doubles Normal", "teams_normal"),
        SKYWARS_DOUBLES_INSANE("SkyWars Doubles Insane", "teams_insane"),
        SKYWARS_MEGA_NORMAL("SkyWars Mega Normal", "mega_normal"),
        SKYWARS_MEGA_DOUBLES("SkyWars Mega Doubles", "mega_doubles"),
        SKYWARS_SOLO_TNT_MADNESS("SkyWars Solo TNT Madness", "solo_insane_tnt_madness"),
        SKYWARS_DOUBLES_TNT_MADNESS("SkyWars Doubles TNT Madness", "teams_insane_tnt_madness"),
        SKYWARS_SOLO_RUSH("SkyWars Solo Rush", "solo_insane_rush"),
        SKYWARS_DOUBLES_RUSH("SkyWars Doubles Rush", "teams_insane_rush"),
        SKYWARS_SOLO_SLIME("SkyWars Solo Slime", "solo_insane_slime"),
        SKYWARS_DOUBLES_SLIME("SkyWars Doubles Slime", "teams_insane_slime"),
        SKYWARS_SOLO_LUCKY_BLOCKS("SkyWars Solo Lucky Blocks", "solo_insane_lucky"),
        SKYWARS_DOUBLES_LUCKY_BLOCKS("SkyWars Doubles Lucky Blocks", "teams_insane_lucky"),
        SKYWARS_HUNTER_VS_BEASTS("SkyWars Hunter vs Beasts", "hunter_vs_beasts"),
        BEDWARS_SOLO("Bed Wars Solo", "bedwars_eight_one"),
        BEDWARS_DOUBLES("Bed Wars Doubles", "bedwars_eight_two"),
        BEDWARS_3V3V3V3("Bed Wars 3v3v3v3", "bedwars_four_three"),
        BEDWARS_4V4V4V4("Bed Wars 4v4v4v4", "bedwars_four_four"),
        BEDWARS_CAPTURE("Bed Wars Capture", "bedwars_capture"),
        BEDWARS_RUSH_DOUBLES("Bed Wars Rush Doubles", "bedwars_eight_two_rush"),
        BEDWARS_RUSH_4V4("Bed Wars Rush 4v4", "bedwars_four_four_rush"),
        BEDWARS_ULTIMATE_DOUBLES("Bed Wars Ultimate Doubles", "bedwars_eight_two_ultimate"),
        BEDWARS_ULTIMATE_4V4("Bed Wars Ultimate 4v4", "bedwars_four_four_ultimate"),
        BEDWARS_CASTLES("Bed Wars Castles", "bedwars_castle"),
        BEDWARS_4V4("Bed Wars 4v4", "bedwars_two_four"),
        BEDWARS_VOIDLESS_DOUBLES("Bed Wars Voidless Doubles", "bedwars_eight_two_voidless"),
        BEDWARS_VOIDLESS_4V4("Bed Wars Voidless 4v4", "bedwars_four_four_voidless"),
        BEDWARS_ARMED_DOUBLES("Bed Wars Armed Doubles", "bedwars_eight two_armed"),
        BEDWARS_ARMED_4V4("Bed Wars Armed 4v4", "bedwars_four_four_armed"),
        BEDWARS_LUCKY_BLOCKS_DOUBLES("Bed Wars Lucky Blocks Doubles", "bedwars_eight two_lucky"),
        BEDWARS_LUCKY_BLOCKS_4V4("Bed Wars Lucky Blocks 4v4", "bedwars_four_four_lucky"),
        WOOL_WARS("Wool Wars", "wool_wool_wars_two_four"),
        UHC_SOLO("UHC Solo", "uhc_solo"),
        UHC_TEAMS("UHC Teams", "uhc_teams"),
        UHC_EVENTS("UHC Events", "uhc_events"),
        SPEED_UHC_SOLO_NORMAL("Speed UHC Solo Normal", "speed_solo_normal"),
        SPEED_UHC_TEAM_NORMAL("Speed UHC Team Normal", "speed_team_normal"),
        DUELS_SOLO_CLASSIC("Duels Solo Classic", "duels_classic_duel"),
        DUELS_SOLO_SKYWARS("Duels Solo SkyWars", "duels_sw_duel"),
        DUELS_DOUBLES_SKYWARS("Duels Doubles SkyWars", "duels_sw_doubles"),
        DUELS_SOLO_BOW("Duels Solo Bow", "duels_bow_duel"),
        DUELS_SOLO_UHC("Duels Solo UHC", "duels_uhc_duel"),
        DUELS_DOUBLES_UHC("Duels Doubles UHC", "duels_uhc_doubles"),
        DUELS_TEAMS_UHC("Duels Teams UHC", "duels_uhc_four"),
        DUELS_DEATHMATCH_UHC("Duels Deathmatch UHC", "duels_uhc_meetup"),
        DUELS_SOLO_NODEBUFFS("Duels Solo NoDebuffs", "duels_potion_duel"),
        DUELS_SOLO_COMBO("Duels Solo Combo", "duels_combo_duel"),
        DUELS_SOLO_POTION("Duels Solo Potion", "duels_potion_duel"),
        DUELS_SOLO_OP("Duels Solo OP", "duels_op_duel"),
        DUELS_DOUBLES_OP("Duels Doubles OP", "duels_op_doubles"),
        DUELS_SOLO_MEGA_WALLS("Duels Solo Mega Walls", "duels_mw_duel"),
        DUELS_DOUBLES_MEGA_WALLS("Duels Doubles Mega Walls", "duels_mw_doubles"),
        DUELS_SOLO_SUMO("Duels Solo Sumo", "duels_sumo_duel"),
        DUELS_SOLO_BLITZ("Duels Solo Blitz", "duels_blitz_duel"),
        DUELS_SOLO_BOW_SPLEEF("Duels Solo Bow Spleef", "duels_bowspleef_duel"),
        DUELS_BRIDGE_1V1("Duels Bridge 1v1", "duels_bridge_duel"),
        DUELS_BRIDGE_2V2("Duels Bridge 2v2", "duels_bridge_doubles"),
        DUELS_BRIDGE_3V3("Duels Bridge 3v3", "duels_bridge_threes"),
        DUELS_BRIDGE_4V4("Duels Bridge 4v4", "duels_bridge_four"),
        DUELS_BRIDGE_2V2V2V2("Duels Bridge 2v2v2v2", "duels_bridge_2v2v2v2"),
        DUELS_BRIDGE_3V3V3V3("Duels Bridge 3v3v3v3", "duels_bridge_3v3v3v3"),
        DUELS_BRIDGE_CTF("Duels Bridge CTF", "duels_capture_threes"),
        DUELS_BOXING_BRIDGE_2V2("Duels Boxing Bridge 2v2", "duels_boxing_duel"),
        DUELS_PARKOUR("Duels Parkour", "duels_parkour_eight"),
        DUELS_HYPIXEL_ARENA("Duels Hypixel Arena", "duels_duel_arena"),
        BLITZ_SOLO_NORMAL("Blitz Solo Normal", "blitz_solo_normal"),
        BLITZ_TEAMS_NORMAL("Blitz Teams Normal", "blitz_teams_normal");

        private final String name;
        private final String gameKey;

        AutoHypixelModeEnum(final String name, final String gameKey) {
            this.name = name;
            this.gameKey = gameKey;
        }

        @Override
        public String getName() {
            return name;
        }

        public String getGameKey() {
            return gameKey;
        }
    }
}