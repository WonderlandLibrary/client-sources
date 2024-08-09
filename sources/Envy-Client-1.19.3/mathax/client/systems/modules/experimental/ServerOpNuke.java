package mathax.client.systems.modules.experimental;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.game.GameLeftEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.settings.StringSetting;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.Jebus.ChatUtils;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static mathax.client.MatHax.LOG;

public class ServerOpNuke extends Module {
    private final Setting<Boolean> autoDisable;
    private final Setting<Boolean> opAlt;
    private final Setting<Boolean> stopServer;
    private final Setting<Boolean> deopAllPlayers;
    private final Setting<Boolean> clearAllPlayersInv;
    private final Setting<Boolean> banAllPlayers;

    private final Setting<String> altNameToOp;

    public ServerOpNuke() {
        super(Categories.Experimental, Items.WARDEN_SPAWN_EGG, "Server Operator Nuke","For trolling once you get operator on a server.");
        SettingGroup sgGeneral = settings.getDefaultGroup();
        autoDisable = sgGeneral.add(new BoolSetting.Builder()
            .name("Auto disable")
            .description("Automatically disables the module on server kick.")
            .defaultValue(true)
            .build()
        );
        banAllPlayers = sgGeneral.add(new BoolSetting.Builder()
            .name("Ban all players")
            .description("Automatically bans all online players except yourself.")
            .defaultValue(false)
            .build()
        );
        opAlt = sgGeneral.add(new BoolSetting.Builder()
            .name("Op alt")
            .description("Give your alt account operator.")
            .defaultValue(true)
            .build()
        );
        stopServer = sgGeneral.add(new BoolSetting.Builder()
            .name("Stop server")
            .description("Runs the /stop command.")
            .defaultValue(false)
            .build()
        );
        clearAllPlayersInv = sgGeneral.add(new BoolSetting.Builder()
            .name("Clear all players inventory")
            .description("Will clear all online players inventories.")
            .defaultValue(false)
            .build()
        );
        deopAllPlayers = sgGeneral.add(new BoolSetting.Builder()
            .name("Deop all players")
            .description("Removes all online players operator status'.")
            .defaultValue(true)
            .build()
        );

        altNameToOp = sgGeneral.add(new StringSetting.Builder()
            .name("Alt account username.")
            .description("The name of your alt to op.")
            .defaultValue("XSS6")
            .visible(opAlt::get)
            .build()
        );
    }

    public ArrayList<PlayerListEntry> getPlayerList() {
        ArrayList<PlayerListEntry> playerList = new ArrayList<>();

        if (mc.world != null) {
            Collection<PlayerListEntry> players = Objects.requireNonNull(mc.getNetworkHandler()).getPlayerList();
            playerList.addAll(players);
        }

        return playerList;
    }


    @EventHandler
    public boolean onActivate() {
        try {
            ChatUtils.info("Started nuker.");
            if(!mc.isInSingleplayer()) {
                if(Objects.requireNonNull(mc.player).hasPermissionLevel(4)) {
                    if (deopAllPlayers.get()) {
                        if (mc.player != null && mc.world != null) {
                            List<PlayerListEntry> playerList = getPlayerList();
                            for (PlayerListEntry player : playerList) {
                                String playerName = player.getProfile().getName();
                                if(!Objects.equals(mc.player.getName().toString(), playerName)) {
                                    ChatUtils.sendPlayerMsg(String.valueOf("/deop " + playerName));
                                    ChatUtils.info("Attempted to deop user: " + playerName);
                                }
                            }
                        }

                    }

                    if (opAlt.get()) {
                        if (!mc.isInSingleplayer()) {
                            if (mc.player != null) {
                                if (mc.world != null) {
                                    ChatUtils.sendPlayerMsg(String.valueOf("/op " + altNameToOp.get()));
                                    ChatUtils.info("Attempted to op user: " + altNameToOp.get());
                                } else {
                                    error("Failed to op alt, as the world is null, toggling.");
                                    toggle();
                                }
                            } else {
                                error("Failed to op alt, as the player is null, toggling.");
                                toggle();
                            }
                        }
                    }

                    if(clearAllPlayersInv.get()) {
                        if (!mc.isInSingleplayer()) {
                            if(mc.player != null) {
                                if(mc.world != null) {
                                    List<PlayerListEntry> playerList = getPlayerList();
                                    for (PlayerListEntry player : playerList) {
                                        String playerName = player.getProfile().getName();
                                        if(!Objects.equals(mc.player.getName().toString(), playerName)) {
                                            ChatUtils.sendPlayerMsg(String.valueOf("/clear " + playerName));
                                            ChatUtils.info("Attempted to clear inventory of user: " + playerName);
                                        }
                                    }
                                } else {
                                    error("Failed to clear player inventories, as the world is null, toggling.");
                                    toggle();
                                }
                            } else {
                                error("Failed to clear player inventories, as the player is null, toggling.");
                                toggle();
                            }
                        }
                    }

                    if (banAllPlayers.get()) {
                        if (!mc.isInSingleplayer()) {
                            if (deopAllPlayers.get()) {
                                if (mc.world != null)
                                    if (mc.player != null) {
                                        List<PlayerListEntry> playerList = getPlayerList();
                                        for (PlayerListEntry player : playerList) {
                                            if (!Objects.equals(player.getProfile().getName(), mc.player.getName().toString()) || !Objects.equals(player.getProfile().getName(), altNameToOp.get())) {
                                                String playerName = player.getProfile().getName();
                                                ChatUtils.sendPlayerMsg(String.valueOf("/ban " + playerName));
                                                ChatUtils.info("Attempted to ban user: " + playerName);
                                            }
                                        }
                                    }
                            }
                        }
                    }

                    if (stopServer.get()) {
                        ChatUtils.sendPlayerMsg(String.valueOf(Text.of("/stop")));
                    }
                    toggle();
                } else {
                    info("You must be an operator.");
                    toggle();
                }
            } else if(mc.isInSingleplayer()){
                info("Cannot nuke server in singleplayer, toggling.");
                toggle();
            }
        } catch (NullPointerException NPE) {
            LOG.error("NullPointerException in the onGameJoinEvent, " + NPE);
            toggle();
        }
        return false;
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (!autoDisable.get()) {
            return;
        }
        toggle();
    }
}
