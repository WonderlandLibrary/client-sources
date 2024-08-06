package com.shroomclient.shroomclientnextgen.modules.impl.player;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import com.shroomclient.shroomclientnextgen.util.PacketUtil;
import java.time.Instant;
import java.util.BitSet;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.message.ArgumentSignatureDataMap;
import net.minecraft.network.message.LastSeenMessageList;
import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket;

@RegisterModule(
    name = "Groomer",
    uniqueId = "groomer",
    description = "Grooms People In Chat",
    category = ModuleCategory.Player
)
public class Groomer extends Module {

    @ConfigOption(
        name = "Ticks",
        description = "Ticks Between Messages",
        min = 0,
        max = 100,
        order = 2
    )
    public Integer ticks = 30;

    @ConfigOption(name = "Message", description = "Does /msg", order = 1)
    public Boolean msg = true;

    int yay = 0;
    String[] commandmsgs = {
        "You're So Cute!",
        "Come To Daddy",
        "Who's Been A Good Boy? You Have!",
        "nya!~",
        "Have You Been Good For Daddy..?",
        "You Need To Be Punished",
        "Hey Little Kitten",
        "Pull Your Pants Down Young Man!!!",
    };
    String[] msgs = {
        "Is So Cute!",
        "Is A Bad Boy...",
        "Is A Good Boy!",
        "Have You Been Good For Daddy..?",
        "Has Been A Bad Boy..",
        "Needs To Be Punished",
        "Is So Cute And Petite",
        "Is My Little Kitten",
        "Are You Ready For My Payload..?",
    };

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @SubscribeEvent
    public void onMotionPre(MotionEvent.Pre e) {
        yay++;
        if (yay > C.mc.getNetworkHandler().getPlayerList().toArray().length) {
            yay = 0;
        }
        if (MovementUtil.ticks % ticks == 0) {
            if (msg) {
                PacketUtil.sendPacket(
                    new CommandExecutionC2SPacket(
                        "msg" +
                        " " +
                        ((PlayerListEntry) C.mc
                                .getNetworkHandler()
                                .getPlayerList()
                                .toArray()[yay]).getProfile()
                            .getName() +
                        " " +
                        commandmsgs[(int) Math.floor(
                                Math.random() * commandmsgs.length
                            )],
                        Instant.now(),
                        System.currentTimeMillis(),
                        ArgumentSignatureDataMap.EMPTY,
                        new LastSeenMessageList.Acknowledgment(0, new BitSet())
                    ),
                    false
                );
            } else {
                C.mc
                    .getNetworkHandler()
                    .sendChatMessage(
                        ((PlayerListEntry) C.mc
                                .getNetworkHandler()
                                .getPlayerList()
                                .toArray()[yay]).getProfile()
                            .getName() +
                        " " +
                        msgs[(int) Math.floor(Math.random() * msgs.length)]
                    );
            }
        }
    }
}
