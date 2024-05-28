package arsenic.module.impl.other;

import arsenic.main.Nexus;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.impl.exploit.Blink;
import arsenic.module.property.impl.BooleanProperty;
import arsenic.utils.minecraft.PlayerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.Collection;

@ModuleInfo(name = "AntiBot", category = ModuleCategory.Other)
public class AntiBot extends Module {
    public static BooleanProperty nameChecks = new BooleanProperty("Name Checks", false);
    public static BooleanProperty invisCheck = new BooleanProperty("Invis Checks", false);
    public static BooleanProperty tabChecks = new BooleanProperty("Tab Checks", false);
    public static BooleanProperty matrixBot = new BooleanProperty("Matrix Bot Preset", false);
    public static BooleanProperty noPushChecks = new BooleanProperty("NoPush Checks", false);
    public static BooleanProperty pingCheck = new BooleanProperty("Ping Checks", false);
    public static BooleanProperty twiceChecks = new BooleanProperty("Twice UUID Checks", false);
    public static BooleanProperty zeroHealthChecks = new BooleanProperty("Dead Checks", false);
    public static BooleanProperty alwaysClose = new BooleanProperty("AlwaysClose Checks", false);
    public static BooleanProperty DEBUG = new BooleanProperty("Debug", false);

    public boolean isBot(Entity entityPlayer) {
        // exception
        if (entityPlayer != mc.thePlayer) {
            if (Nexus.getNexus().getModuleManager().getModuleByClass(Blink.class).blinkEntity == entityPlayer) {
                return true;
            }
            if (isBotCustom(entityPlayer)) {
                return true;
            }
        }
        return false;
    }


    public static boolean isBotCustom(Entity en) {
        if (en == mc.thePlayer) {
            return false;
        }
        if (twiceChecks.getValue()) {
            if (!isPlayerTwiceInGame()) {
                if (DEBUG.getValue()) {
                    PlayerUtils.addWaterMarkedMessageToChat("§7Player: §4" + en.getName() + " §7failed check: " + "§aTWICE CHECK");
                }
                return true;
            }
        }

        if (invisCheck.getValue()) {
            if (en.isInvisibleToPlayer(mc.thePlayer)) {
                if (DEBUG.getValue()) {
                    PlayerUtils.addWaterMarkedMessageToChat("§7Player: §4" + en.getName() + " §7failed check: " + "§aINVIS CHECK");
                }
                return true;
            }
        }

        if (nameChecks.getValue()) {
            if (isBotName(en)) {
                if (DEBUG.getValue()) {
                    PlayerUtils.addWaterMarkedMessageToChat("§7Player: §4" + en.getName() + " §7failed check: " + "§aNAME CHECK");
                }
                return true;
            }
        }

        if (noPushChecks.getValue()) {
            if (!en.canBePushed()) {
                if (DEBUG.getValue()) {
                    PlayerUtils.addWaterMarkedMessageToChat("§7Player: §4" + en.getName() + " §7failed check: " + "§aNOPUSH CHECK");
                }
                return true;
            }
        }

        if (pingCheck.getValue()) {
            if (mc.getNetHandler() != null && en != null && en.getName() != null) {
                NetworkPlayerInfo playerInfo = mc.getNetHandler().getPlayerInfo(en.getName());
                if (playerInfo != null && playerInfo.getResponseTime() < 3) {
                    if (DEBUG.getValue()) {
                        PlayerUtils.addWaterMarkedMessageToChat("§7Player: §4" + en.getName() + " §7failed check: " + "§aPING CHECK");
                    }
                    return true;
                }
            }
        }


        if (zeroHealthChecks.getValue()) {
            if (((EntityLivingBase) en).getHealth() < 0.0F || en.isDead) {
                if (DEBUG.getValue()) {
                    PlayerUtils.addWaterMarkedMessageToChat("§7Player: §4" + en.getName() + " §7failed check: " + "§aDEAD CHECK");
                }
                return true;
            }
        }

        if (tabChecks.getValue()) {
            if (!inTab((EntityLivingBase) en)) {
                if (DEBUG.getValue()) {
                    PlayerUtils.addWaterMarkedMessageToChat("§7Player: §4" + en.getName() + " §7failed check: " + "§aTAB CHECK");
                }
                return true;
            }
        }
        
        if (alwaysClose.getValue()) {
            if (en.ticksExisted < 5 || en.isInvisible() || mc.thePlayer.getDistanceSq(en.posX, mc.thePlayer.posY, en.posZ) > 100 * 100) {
                return true;
            }
        }
        return false;
    }


    // UTILS
    public static ArrayList<EntityPlayer> getPlayerList() {
        Collection<NetworkPlayerInfo> playerInfoMap = Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap();
        ArrayList<EntityPlayer> list = new ArrayList<>();
        for (NetworkPlayerInfo networkPlayerInfo : playerInfoMap) {
            list.add(Minecraft.getMinecraft().theWorld.getPlayerEntityByName(networkPlayerInfo.getGameProfile().getName()));
        }
        return list;
    }

    public static boolean inTab(EntityLivingBase en) {
        if (!Minecraft.getMinecraft().isSingleplayer()) {
            for (NetworkPlayerInfo info : Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap()) {
                if (info != null && info.getGameProfile() != null && info.getGameProfile().getName().contains(en.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isPlayerTwiceInGame() {
        // Get the player list
        Collection<NetworkPlayerInfo> playerInfoList = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();

        // Check if the player list is not null and has at least one player
        if (playerInfoList != null && !playerInfoList.isEmpty()) {
            if (getPlayerList().get(0) != null) {
                String targetPlayerID = getPlayerList().get(0).getGameProfile().getId().toString();
                // Check if the target player ID is not null
                if (targetPlayerID != null) {
                    // Iterate through the player info list
                    for (NetworkPlayerInfo info : playerInfoList) {
                        String infoID = info.getGameProfile().getId().toString();

                        // Check if the info and infoID are not null
                        if (info != null && infoID != null) {
                            // Check if the infoID matches the target player ID
                            if (targetPlayerID.equals(infoID)) {
                                // Iterate through the player info list again to find a second occurrence
                                for (NetworkPlayerInfo info2 : playerInfoList) {
                                    String infoID2 = info2.getGameProfile().getId().toString();

                                    // Check if the info2 and infoID2 are not null
                                    if (info2 != null && infoID2 != null) {
                                        // Check if the infoID2 matches the target player ID and is not the same as the first occurrence
                                        if (targetPlayerID.equals(infoID2) && !info.equals(info2)) {
                                            return true; // Found a second occurrence
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return false; // No second occurrence found
    }


    private static boolean isBotName(Entity en) {
        String rawName = en.getDisplayName().getUnformattedText().toLowerCase();
        String forName = en.getDisplayName().getFormattedText().toLowerCase();
        if (forName.startsWith("§r§8[npc]")) {
            return true;
        }
        for (EntityPlayer list : mc.theWorld.playerEntities) {
            if (list != mc.thePlayer && !list.isDead && list.isInvisible() && getPlayerList().contains(list) && list.getCustomNameTag().length() >= 2) {
                mc.theWorld.removeEntity(list);
                return true;
            }
        }

        if (forName.contains("]")) {
            return true;
        }
        if (forName.contains("[")) {
            return true;
        }
        if (rawName.contains("-")) {
            return true;
        }
        if (rawName.contains(":")) {
            return true;
        }
        if (rawName.contains("+")) {
            return true;
        }
        if (rawName.startsWith("cit")) {
            return true;
        }
        if (rawName.startsWith("npc")) {
            return true;
        }

        return rawName.isEmpty() || rawName.contains(" ") || forName.isEmpty();
    }
}
