package club.bluezenith.module.modules.misc;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.player.AutoArmor;
import club.bluezenith.module.modules.player.ChestStealer;
import club.bluezenith.module.modules.player.InvManager;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.IntegerValue;
import club.bluezenith.module.value.types.ListValue;
import club.bluezenith.module.value.types.StringValue;
import club.bluezenith.util.player.PacketUtil;
import club.bluezenith.util.client.ServerUtils;
import net.minecraft.event.ClickEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.TimeUnit;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.util.math.MathUtil.round;
import static java.lang.Math.min;

public class AutoPlay extends Module { //todo rewrite with multiple server support or smth

    private final IntegerValue delay = new IntegerValue("Delay", 500, 0, 3000, 100).showIf(() -> ServerUtils.hypixel).setIndex(-1);
    private final BooleanValue jew = new BooleanValue("AutoGG", false).setIndex(1);
    private final StringValue autoGG = new StringValue("Message", "").showIf(jew::get).setIndex(2);
    private final ListValue onGameEnd = new ListValue("Disable on Game End", "AutoArmor", "InvManager", "ChestStealer").setIndex(3);
    public AutoPlay() {
        super("AutoPlay", ModuleCategory.MISC);
    }

    boolean flag = false;

    @Listener
    public void onPacket(PacketEvent event) {
        if(event.packet instanceof S45PacketTitle && ServerUtils.isBlocksMC()) {
            final String the = ((S45PacketTitle) event.packet).getMessage().getUnformattedText();
            flag = the.endsWith("is the CHAMPION!") || the.contains("You are now a spectator!") || the.contains("CHAMPIONS!");
        } else if(event.packet instanceof S02PacketChat) { //clickEvent=ClickEvent{action=RUN_COMMAND, value='/play solo_insane'}
            final IChatComponent component = ((S02PacketChat)event.packet).getChatComponent();
            if(component.getSiblings().isEmpty()) return;

            for (IChatComponent sibling : component.getSiblings()) {
                final ChatStyle style = sibling.getChatStyle();
                if (style == null) return;
                final ClickEvent clickEvent = style.getChatClickEvent();
                if (clickEvent != null) {
                    if (clickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                        if (clickEvent.getValue() != null && clickEvent.getValue().startsWith("/play")) {
                            if(clickEvent.getValue().contains("skyblock"))
                                return; //hypixel suggests joining skyblock on first login
                            if(delay.get() > 1000) {
                                getBlueZenith().getNotificationPublisher().postSuccess(
                                        displayName,
                                        "Sending you to the next game in §7" + round(delay.get()/1000F, 1) + "s",
                                        min(2000, delay.get())
                                );
                            }
                            BlueZenith.scheduledExecutorService.schedule(() -> PacketUtil.send(new C01PacketChatMessage(clickEvent.getValue())), delay.get(), TimeUnit.MILLISECONDS);
                            flag = true;
                        }
                    }
                }
            }
        }
    }

    @Listener
    public void onUpdate(UpdatePlayerEvent event) {
        if(flag && event.isPre()) {
            boolean flag1 = false;
            if(onGameEnd.getOptionState("AutoArmor") && getCastedModule(AutoArmor.class).getState()) {
                getBlueZenith().getModuleManager().getModule(AutoArmor.class).setState(false);
                flag1 = true;
            }
            if(onGameEnd.getOptionState("InvManager") && getCastedModule(InvManager.class).getState()) {
                getBlueZenith().getModuleManager().getModule(InvManager.class).setState(false);
                flag1 = true;
            }
            if(onGameEnd.getOptionState("ChestStealer") && getCastedModule(ChestStealer.class).getState()) {
                getBlueZenith().getModuleManager().getModule(ChestStealer.class).setState(false);
                flag1 = true;
            }
            if(flag1) {
                BlueZenith.getBlueZenith().getNotificationPublisher().postWarning(displayName,
                        "Disabled some inventory modules due to game end",
                        1500
                );
                flag = false;
            }
            if(jew.get() && !autoGG.get().equals("")) {
                if(ServerUtils.isBlocksMC())
                mc.thePlayer.sendChatMessage("[" + RandomStringUtils.randomAlphanumeric(5) + "]" + autoGG.get());
                else mc.thePlayer.sendChatMessage(autoGG.get());
            }
            final ItemStack slimeBall = mc.thePlayer.inventory.getStackInSlot(8);
            final ItemStack paperItem = mc.thePlayer.inventory.getStackInSlot(7);
            if(slimeBall == null || paperItem == null || ServerUtils.hypixel) {
                flag = false;
                return;
            }
            final int slot = mc.thePlayer.inventory.currentItem;
            PacketUtil.send(new C09PacketHeldItemChange(7));
            PacketUtil.send(new C08PacketPlayerBlockPlacement(paperItem));
            PacketUtil.send(new C09PacketHeldItemChange(slot));
        }
    }
}
