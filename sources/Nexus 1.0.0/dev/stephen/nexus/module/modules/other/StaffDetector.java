package dev.stephen.nexus.module.modules.other;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.network.EventPacket;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.setting.impl.ModeSetting;
import dev.stephen.nexus.utils.render.notifications.impl.Notification;
import dev.stephen.nexus.utils.render.notifications.impl.NotificationMoode;
import dev.stephen.nexus.utils.timer.MillisTimer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;

import java.util.EnumSet;

public class StaffDetector extends Module {
    public static final ModeSetting staffMode = new ModeSetting("Server", "BlocksMC", "BlocksMC", "Hypixel", "Cubecraft");

    public StaffDetector() {
        super("StaffDetector", "Detects staff", 0, ModuleCategory.OTHER);
        this.addSettings(staffMode);
    }

    private final MillisTimer timer = new MillisTimer();
    private final MillisTimer timer2 = new MillisTimer();

    private static final long NOTIFICATION_COOLDOWN = 10000L;

    private boolean didCock = false;

    @EventLink
    public final Listener<EventPacket> eventPacketListener = event -> {
        if (isNull()) {
            return;
        }

        Packet<?> packet = event.getPacket();
        if (packet instanceof PlayerListS2CPacket) {
            EnumSet<PlayerListS2CPacket.Action> actions = ((PlayerListS2CPacket) packet).getActions();
            if (actions.contains(PlayerListS2CPacket.Action.UPDATE_LATENCY)) {
                if (((PlayerListS2CPacket) packet).getEntries().size() != mc.getNetworkHandler().getPlayerList().size()) {
                    if (timer.hasElapsed(NOTIFICATION_COOLDOWN)) {
                        Client.INSTANCE.getNotificationManager().addNewNotification(new Notification("staff detected (player list)", 3000, NotificationMoode.WARNING));
                        timer.reset();
                    }
                    didCock = true;
                } else if (didCock) {
                    didCock = false;
                    Client.INSTANCE.getNotificationManager().addNewNotification(new Notification("staff left", 3000, NotificationMoode.WARNING));
                }
            }
        }
    };

    @EventLink
    public final Listener<EventTickPre> eventTickListener = event -> {
        if (isNull()) {
            return;
        }

        mc.world.getPlayers().forEach(player -> {
            String playerName = player.getGameProfile().getName();
            if (isStaff(playerName, getStaffList())) {
                if (timer2.hasElapsed(NOTIFICATION_COOLDOWN)) {
                    Client.INSTANCE.getNotificationManager().addNewNotification(new Notification("Staff found: " + playerName, 3000, NotificationMoode.WARNING));
                    timer2.reset();
                }
            }
        });
    };

    @Override
    public void onEnable() {
        timer.reset();
        timer2.reset();
        super.onEnable();
    }

    private String[] getStaffList() {
        return switch (staffMode.getMode()) {
            case "BlocksMC" -> blocksMCStaff;
            case "Hypixel" -> hypixelStaff;
            case "Cubecraft" -> cubecraftStaff;
            default -> null;
        };
    }

    private boolean isStaff(String playerName, String[] staffNames) {
        for (String staffName : staffNames) {
            if (playerName.equals(staffName)) {
                return true;
            }
        }
        return false;
    }

    private final String[] cubecraftStaff = {
            "S4nne",
            "Fesa",
            "99th_DutchScary",
            "Soulless_Unity",
            "EggyWarz",
            "Efcluke94",
            "SanCookie",
            "CKelting262",
            "rubik_cube_man",
            "ToonBer",
            "Kloska",
            "xThunder_",
            "CapitanGato",
            "SuperSuccubus",
            "Otherworlden",
            "ssunsett",
            "Hqteful",
            "Alexp192",
            "4Moonlightt",
            "ALM",
            "xSchulzis",
            "Muhx",
            "Goofy_Unity",
            "ThijsBlocker",
            "Redned",
            "Lyriie",
            "JustNathie",
            "JokeKaedee",
            "Elivat",
            "MDeann",
            "Basketman13",
            "AnolTongi",
            "Alemiz",
            "Camezonda"
    };

    private final String[] hypixelStaff = {
            "MCVisuals",
            "Centranos",
            "LeBrillant",
            "Aerh",
            "Gerbor12",
            "mrkeith",
            "Rhune",
            "SnowyPai",
            "Smoarzified",
            "DeluxeRose",
            "Greeenn",
            "jamzs",
            "JordWG",
            "Phaige",
            "Quack",
            "Pensul",
            "LadyBleu",
            "Fr0z3n",
            "Citria",
            "TheBirmanator",
            "TorWolf",
            "Minikloon",
            "Rozsa",
            "The_Darthonian"
    };

    private final String[] blocksMCStaff = {
            "vFahad_",
            "mufazzz",
            "arbde",
            "Webmasters",
            "Vampirec",
            "zAtTiLq",
            "DirectPlan",
            "PeerfectRod_",
            "1F5aMH___3oo",
            "Aliiyah",
            "deficency",
            "EyesO_Diamond",
            "F2rris",
            "1flyn",
            "Nadaa",
            "Morninng",
            "KinderBueno__",
            "bestleso",
            "Reflxctively",
            "ImMEHDI_",
            "Ixfaris_0",
            "Aymann_",
            "xfahadq",
            "BoMshary",
            "1Adam__",
            "ALMARDAWI",
            "Postme",
            "xL2d",
            "DrugsOverdose",
            "Om2r",
            "0ayt",
            "5ald_KSA",
            "GsMrxDJ",
            "GsOMAR",
            "TryLat3rjAs",
            "Ruwq",
            "teddynicelol",
            "MightyM7MD",
            "itsjust24",
            "Jrx7",
            "Mwl4",
            "m7mdxjw",
            "Jxicide",
            "Vengeant",
            "kieax",
            "Mr_1990",
            "JustRois_",
            "CASHFL0WW",
            "A3loosh",
            "Neeres",
            "luqqr",
            "_1Dark",
            "Werthly",
            "1Khalid",
            "LovelyMaram",
            "dxs7",
            "1Az_",
            "0Strong",
            "Y2sF",
            "Morgan_So",
            "z1HypersXz",
            "MILG511",
            "tHeViZi",
            "_Lsantoss",
            "MVP11",
            "MightyFiras",
            "xVerif",
            "1Sharlock",
            "whahahahahahaha",
            "wallacce",
            "BPEJ",
            "GymIsTherapy",
            "0Leqf",
            "Ditraghxel",
            "Claegness",
            "catbisou",
            "Attraactive",
            "M5CS_",
            "Xx_ZeroMc_xX",
            "_IxM",
            "yyodey",
            "K2reem",
            "Dfivz",
            "KingH0",
            "Time98",
            "rosulate",
            "glowingsunsets",
            "Yarin",
            "OnlyK1nq",
            "YOUVA",
            "I_Shling",
            "0PvP_",
            "FANOR_SYR",
            "NamikazeMin4to",
            "Bradenton",
            "Watchdog",
            "2lbk",
            "m7xar",
            "1LoST_",
            "Rikoshy",
            "GsYousef",
            "iidouble",
            "5bzzz",
            "Just_XYZ",
            "TruthZ_",
            "Ovqrn",
            "9lrv",
            "_iMuslim_",
            "5loo",
            "PavleDjajic",
            "D7iiem",
            "SweetyAlice",
            "7amze__",
            "D_1V",
            "Abo_3losh",
            "Lyrnxx",
            "1Pre",
            "Peree",
            "Everlqst",
            "Gaboo6",
            "FastRank",
            "Erosiion",
            "EgaSS",
            "Vanitas_0",
            "PoisonL",
            "1Prometheus_",
            "Pogor",
            "ixstorm_",
            "1MSA",
            "AnotherHero",
            "D3vi1Joex",
            "38l_ba6n",
            "izLORDeX",
            "RAGHAVV",
            "Impassivelly",
            "Reixo",
            "NotriousAsser",
            "DasPukas_",
            "1Abd2llah",
            "swhq",
            "1ith",
            "ArabPixel",
            "Majd83",
            "Mxhesh",
            "LIONMohamed",
            "4T0_",
            "Mc_Suchter",
            "Mo3Az1",
            "B7rl",
            "TheCre4tor",
            "ln5b",
            "BasilFoto",
            "einmeterhecht",
            "BeFriends",
            "1Sweet",
            "Ev2n",
            "xMz7",
            "xiDayzer",
            "mohmad_q8",
            "Ceulla",
            "Nshme",
            "Firas",
            "e9_",
            "1LaB",
            "Bunkrat",
            "0HqSon_",
            "0bowskills",
            "Iv2a",
            "_sadeq",
            "1Meran",
            "A2boD"
    };
}
