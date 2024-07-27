package dev.nexus.modules.impl.other;

import dev.nexus.events.bus.Listener;
import dev.nexus.events.bus.annotations.EventLink;
import dev.nexus.events.impl.EventMotionPre;
import dev.nexus.events.impl.EventRender2D;
import dev.nexus.modules.Module;
import dev.nexus.modules.ModuleCategory;
import dev.nexus.modules.setting.BooleanSetting;
import dev.nexus.utils.client.BedwarsUtils;
import dev.nexus.utils.client.StatsClass;
import dev.nexus.utils.game.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumChatFormatting;

public class BedwarsHelper extends Module {
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public final BooleanSetting statsOverlay = new BooleanSetting("Stats Overlay", false);

    public final BooleanSetting alerts = new BooleanSetting("Alerts", false);
    public final BooleanSetting armorAlert = new BooleanSetting("Armor Alerts", false);
    public final BooleanSetting bowAlert = new BooleanSetting("Bow Alerts", false);
    public final BooleanSetting invisibleAlert = new BooleanSetting("Invisible Alerts", false);
    public final BooleanSetting potionsAlert = new BooleanSetting("Potions Alerts", false);
    public final BooleanSetting swordsAlert = new BooleanSetting("Swords Alerts", false);
    public final BooleanSetting playSound = new BooleanSetting("Play sound", false);

    public BedwarsHelper() {
        super("BedwarsHelper", 0, ModuleCategory.OTHER);
        this.addSettings(statsOverlay, alerts, armorAlert, bowAlert, invisibleAlert, potionsAlert, swordsAlert, playSound);
        armorAlert.setDependency(alerts, true);
        bowAlert.setDependency(alerts, true);
        invisibleAlert.setDependency(alerts, true);
        potionsAlert.setDependency(alerts, true);
        swordsAlert.setDependency(alerts, true);
        playSound.setDependency(alerts, true);
    }

    private final List<EntityPlayer> iSword = new ArrayList<>();
    private final List<EntityPlayer> dSword = new ArrayList<>();
    private final List<EntityPlayer> invis = new ArrayList<>();
    private final List<EntityPlayer> bowList = new ArrayList<>();
    private final List<EntityPlayer> jumpyppl = new ArrayList<>();
    private final List<EntityPlayer> spedppl = new ArrayList<>();
    private final List<EntityPlayer> iArmor = new ArrayList<>();
    private final List<EntityPlayer> dArmor = new ArrayList<>();
    private final String prefix = "[BWH]: ";

    private final List<StatsClass> stats = new ArrayList<>();

    @EventLink
    public final Listener<EventRender2D> eventRender2DListener = event -> {
        if (isNull()) {
            return;
        }
        if (mc.gameSettings.keyBindPlayerList.isKeyDown()) {
            if (mc.gameSettings.keyBindPlayerList.isPressed()) {
                stats.clear();

                for (EntityPlayer en : getPlayerList()) {
                    if (en == null) {
                        continue;
                    }
                    executor.execute(() -> {
                        String[] stats2 = BedwarsUtils.getPlayerStats(en.getName());
                        stats.add(new StatsClass(en.getDisplayName().getUnformattedText(), stats2[0], stats2[1], stats2[2], stats2[3], stats2[4], stats2[5], stats2[6], stats2[7]));
                    });
                }

            }
            renderStats();
        }
    };

    // this doesnt load every player, need to fix
    public static ArrayList<EntityPlayer> getPlayerList() {
        Collection<NetworkPlayerInfo> playerInfoMap = Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap();
        ArrayList<EntityPlayer> list = new ArrayList<>();
        for (NetworkPlayerInfo networkPlayerInfo : playerInfoMap) {
            list.add(Minecraft.getMinecraft().theWorld.getPlayerEntityByName(networkPlayerInfo.getGameProfile().getName()));
        }
        return list;
    }

    private void renderStats() {
        if (mc == null || mc.fontRendererObj == null || stats == null || stats.isEmpty()) {
            return;
        }

        FontRenderer fontRenderer = mc.fontRendererObj;
        int startX = 10;
        int startY = 10;
        int lineHeight = fontRenderer.FONT_HEIGHT + 2;
        int columnPadding = 5;
        int numColumns = 9;

        String[] headers = {"Name", "Wins", "Losses", "Games Played", "Win/Loss Ratio", "KDR", "FKDR", "BBLR", "WS"};
        int[] columnWidths = new int[numColumns];

        for (int i = 0; i < numColumns; i++) {
            columnWidths[i] = fontRenderer.getStringWidth(headers[i]);
        }

        for (StatsClass stat : stats) {
            if (stat == null) {
                continue;
            }
            String[] values = {
                    stat.getName(), String.valueOf(stat.getWins()), String.valueOf(stat.getLosses()), String.valueOf(stat.getGamesPlayed()),
                    String.valueOf(stat.getWinLossRatio()), String.valueOf(stat.getKdr()), String.valueOf(stat.getFkdr()),
                    String.valueOf(stat.getBblr()), String.valueOf(stat.getWs())
            };
            for (int i = 0; i < numColumns; i++) {
                if (values[i] != null) {
                    columnWidths[i] = Math.max(columnWidths[i], fontRenderer.getStringWidth(values[i]));
                }
            }
        }

        int boxWidth = 0;
        for (int width : columnWidths) {
            boxWidth += width;
        }
        boxWidth += (numColumns + 1) * columnPadding;

        int boxHeight = lineHeight * (stats.size() + 1) + 20;

        Gui.drawRect(startX, startY, startX + boxWidth, startY + boxHeight, new Color(0, 0, 0, 128).getRGB());

        int xOffset = startX + columnPadding;
        for (int i = 0; i < numColumns; i++) {
            fontRenderer.drawString(headers[i], xOffset, startY + columnPadding, -1);
            xOffset += columnWidths[i] + columnPadding;
        }

        int yOffset = startY + lineHeight + columnPadding;

        Iterator<StatsClass> iterator = stats.iterator();
        while (iterator.hasNext()) {
            StatsClass stat = iterator.next();
            if (stat == null || stat.getName() == null) {
                continue;
            }
            String[] values = {
                    stat.getName(), String.valueOf(stat.getWins()), String.valueOf(stat.getLosses()), String.valueOf(stat.getGamesPlayed()),
                    String.valueOf(stat.getWinLossRatio()), String.valueOf(stat.getKdr()), String.valueOf(stat.getFkdr()),
                    String.valueOf(stat.getBblr()), String.valueOf(stat.getWs())
            };
            xOffset = startX + columnPadding;
            for (int i = 0; i < numColumns; i++) {
                if (values[i] != null) {
                    fontRenderer.drawString(values[i], xOffset, yOffset, -1);
                }
                xOffset += columnWidths[i] + columnPadding;
            }
            yOffset += lineHeight;
        }
    }


    @EventLink
    public final Listener<EventMotionPre> eventMotionPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (alerts.getValue()) {
            for (EntityPlayer en : mc.theWorld.playerEntities) {
                if (hasNoArmor(en)) {
                    continue;
                }
                if (en.isDead) {
                    if (iSword.contains(en))
                        iSword.remove(en);
                    if (dSword.contains(en))
                        dSword.remove(en);
                    if (bowList.contains(en))
                        bowList.remove(en);
                    if (jumpyppl.contains(en))
                        jumpyppl.remove(en);
                    if (invis.contains(en))
                        invis.remove(en);
                }

                if (en.getHeldItem() != null) {
                    Item heldItem = en.getHeldItem().getItem();
                    if (heldItem instanceof ItemSword && swordsAlert.getValue()) {
                        String swordType = ((ItemSword) heldItem).getToolMaterialName().toLowerCase();
                        if (swordType.contains("iron")) {
                            if (!iSword.contains(en)) {
                                iSword.add(en);
                                ChatUtils.addMessageToChat(prefix + en.getName() + " has an " + gray + "Iron" + white + " Sword.");
                                playSound();
                            }
                        }
                        if (swordType.contains("emerald")) {
                            if (!dSword.contains(en)) {
                                dSword.add(en);
                                ChatUtils.addMessageToChat(prefix + en.getName() + " has a " + aqua + "Diamond" + white + " Sword.");
                                playSound();
                            }
                        }
                    }
                    if (heldItem instanceof ItemBow && bowAlert.getValue()) {
                        if (!bowList.contains(en)) {
                            bowList.add(en);
                            ChatUtils.addMessageToChat(prefix + en.getName() + " has a " + dark_red + "Bow.");
                            playSound();
                        }
                    }
                }
                if (en.getActivePotionEffect(Potion.invisibility) != null && invisibleAlert.getValue()) {
                    if (!invis.contains(en)) {
                        invis.add(en);
                        ChatUtils.addMessageToChat(prefix + en.getName() + " is now " + dark_purple + "Invisible.");
                        playSound();
                    }
                } else if (invis.contains(en)) {
                    invis.remove(en);
                    ChatUtils.addMessageToChat(prefix + en.getName() + " is now " + dark_purple + "Visible.");
                }
                if (en.getActivePotionEffect(Potion.jump) != null && potionsAlert.getValue()) {
                    if (!jumpyppl.contains(en)) {
                        jumpyppl.add(en);
                        ChatUtils.addMessageToChat(prefix + en.getName() + " now has " + green + "JUMP!");
                        playSound();
                    }
                } else if (jumpyppl.contains(en)) {
                    jumpyppl.remove(en);
                    ChatUtils.addMessageToChat(prefix + en.getName() + " no longer has " + green + "JUMP!");
                }
                if (en.getActivePotionEffect(Potion.moveSpeed) != null && potionsAlert.getValue()) {
                    if (!spedppl.contains(en)) {
                        spedppl.add(en);
                        ChatUtils.addMessageToChat(prefix + en.getName() + " now has " + yellow + "SPEEED");
                        playSound();
                    }
                } else if (spedppl.contains(en)) {
                    spedppl.remove(en);
                    ChatUtils.addMessageToChat(prefix + en.getName() + " no longer has " + yellow + "SPEEED");
                }
                for (int a = 0; a < 4; a++) {
                    if (en.getCurrentArmor(a) != null && armorAlert.getValue()) {
                        String armr = en.getCurrentArmor(a).getDisplayName().toLowerCase();
                        if (armr.contains("iron")) {
                            if (!iArmor.contains(en)) {
                                iArmor.add(en);
                                ChatUtils.addMessageToChat(prefix + en.getName() + " has " + gray + "Iron" + white + " armor.");
                                playSound();
                            }
                        }
                        if (armr.contains("diamond")) {
                            if (!dArmor.contains(en)) {
                                dArmor.add(en);
                                ChatUtils.addMessageToChat(prefix + en.getName() + " has " + aqua + "Diamond" + white + " armor.");
                                playSound();
                            }
                        }
                    }
                }
            }
        }
    };

    private void playSound() {
        if (playSound.getValue()) {
            mc.thePlayer.playSound("note.pling", 1.0F, 1.0F);
        }
    }

    public static boolean hasNoArmor(EntityPlayer en) {
        for (int x = 0; x < 4; x++) {
            if (en.getCurrentArmor(x) == null || !(en.getCurrentArmor(x).getItem() instanceof ItemArmor)) {
                return true;
            }
        }
        return false;
    }

    //colors
    public static final EnumChatFormatting red = EnumChatFormatting.RED;
    public static final EnumChatFormatting blue = EnumChatFormatting.BLUE;
    public static final EnumChatFormatting black = EnumChatFormatting.BLACK;
    public static final EnumChatFormatting yellow = EnumChatFormatting.YELLOW;
    public static final EnumChatFormatting green = EnumChatFormatting.GREEN;
    public static final EnumChatFormatting aqua = EnumChatFormatting.AQUA;
    public static final EnumChatFormatting gold = EnumChatFormatting.GOLD;
    public static final EnumChatFormatting white = EnumChatFormatting.WHITE;
    public static final EnumChatFormatting gray = EnumChatFormatting.GRAY;
    public static final EnumChatFormatting purple = EnumChatFormatting.LIGHT_PURPLE;
    public static final EnumChatFormatting dark_gray = EnumChatFormatting.DARK_GRAY;
    public static final EnumChatFormatting dark_red = EnumChatFormatting.DARK_RED;
    public static final EnumChatFormatting dark_blue = EnumChatFormatting.DARK_BLUE;
    public static final EnumChatFormatting dark_green = EnumChatFormatting.DARK_GREEN;
    public static final EnumChatFormatting dark_aqua = EnumChatFormatting.DARK_AQUA;
    public static final EnumChatFormatting dark_purple = EnumChatFormatting.DARK_PURPLE;

    //formatting
    public static final EnumChatFormatting bold = EnumChatFormatting.BOLD;
    public static final EnumChatFormatting italic = EnumChatFormatting.ITALIC;
    public static final EnumChatFormatting obf = EnumChatFormatting.OBFUSCATED;
    public static final EnumChatFormatting reset = EnumChatFormatting.RESET;
    public static final EnumChatFormatting strike = EnumChatFormatting.STRIKETHROUGH;
    public static final EnumChatFormatting underline = EnumChatFormatting.UNDERLINE;
}

