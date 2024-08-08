package me.zeroeightsix.kami.module.modules.chat;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.event.events.GuiScreenEvent;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ChatTextUtils;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.network.play.server.SPacketUseBed;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by hub on 15 June 2019
 * Last Updated 17 June 2019 by hub
 * Updated by Viktisen 27th April 2020
 */
@Module.Info(name = "Annoyer", category = Module.Category.CHAT, description = "Annoyer")
public class Annoyer extends Module {

    private static boolean isFirstRun = true;

    private static Queue<String> messageQueue = new ConcurrentLinkedQueue<String>();

    private static Map<String, Integer> minedBlocks = new ConcurrentHashMap<String, Integer>();
    private static Map<String, Integer> placedBlocks = new ConcurrentHashMap<String, Integer>();
    private static Map<String, Integer> droppedItems = new ConcurrentHashMap<String, Integer>();
    private static Map<String, Integer> consumedItems = new ConcurrentHashMap<String, Integer>();

    private static DecimalFormat df = new DecimalFormat();
    private static TimerTask timerTask;
    private static Timer timer;

    private static PacketEvent.Receive lastEventReceive;
    private static PacketEvent.Send lastEventSend;
    private static LivingEntityUseItemEvent.Finish lastLivingEntityUseFinishEvent;
    private static GuiScreenEvent.Displayed lastGuiScreenDisplayedEvent;
    private static String lastmessage = "";

    private static Vec3d thisTickPos;
    private static Vec3d lastTickPos;
    private static double distanceTraveled = 0.0;

    private static float thisTickHealth;
    private static float lastTickHealth;
    private static float gainedHealth;
    private static float lostHealth;

    private Setting<Boolean> distance = register(Settings.b("Distance", true));
    private Setting<Integer> mindistance = register(Settings.integerBuilder("Min Distance").withRange(1, 100).withValue(10).build());
    private Setting<Integer> maxdistance = register(Settings.integerBuilder("Max Distance").withRange(100, 10000).withValue(150).build());
    private Setting<Boolean> blocks = register(Settings.b("Blocks", true));
    private Setting<Boolean> items = register(Settings.b("Items", true));
    private Setting<Boolean> playerheal = register(Settings.b("Player Heal", true));
    private Setting<Boolean> playerdamage = register(Settings.b("Player Damage", true));
    private Setting<Boolean> playerdeath = register(Settings.b("Death", true));
    private Setting<Boolean> greentext = register(Settings.b("Greentext", false));
    private Setting<Boolean> clientName = register(Settings.b("ClientName", false));
    private Setting<Integer> delay = register(Settings.integerBuilder("Send Delay").withRange(1, 10).withValue(2).build());
    private Setting<Integer> queuesize = register(Settings.integerBuilder("Queue Size").withRange(1, 100).withValue(5).build());
    private Setting<Boolean> clearqueue = register(Settings.b("Clear Queue", false));


    @EventHandler
    public Listener<GuiScreenEvent.Displayed> guiScreenEventDisplayedlistener = new Listener<>(event -> {
        if (isDisabled() || mc.player == null || ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }
        if (lastGuiScreenDisplayedEvent != null && lastGuiScreenDisplayedEvent.equals(event)) {
            return;
        }
        // Check for GameOver GUI
        if (playerdeath.getValue() && event.getScreen() instanceof GuiGameOver) {
            String message;
            if (clientName.getValue()) {
                message = "Jag dog ;( tack vare " + KamiMod.NAME_UNICODE + "!";
            } else {
                message = "I ded ;(";
            }
            queueMessage(message);
            return;
        }
        lastGuiScreenDisplayedEvent = event;
    });

    @EventHandler
    private Listener<PacketEvent.Receive> packetEventReceiveListener = new Listener<>(event -> {
        if (isDisabled() || mc.player == null || ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }
        if (lastEventReceive != null && lastEventReceive.equals(event)) {
            return;
        }
        // Check for Bed Usage
        if (event.getPacket() instanceof SPacketUseBed) {
            String message;
            if (clientName.getValue()) {
                message = "Jag sov i en s\u00e4ng, tack vare " + KamiMod.NAME_UNICODE + "!";
            } else {
                message = "Jag sov i en s\u00e4ng!";
            }
            queueMessage(message);
            lastEventReceive = event;
            return;
        }
    });

    @EventHandler
    private Listener<PacketEvent.Send> packetEventSendListener = new Listener<>(event -> {
        if (isDisabled() || mc.player == null || ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }
        if (lastEventSend != null && lastEventSend.equals(event)) {
            return;
        }
        // Mining and Item Drop
        if ((items.getValue() || blocks.getValue()) && event.getPacket() instanceof CPacketPlayerDigging) {
            CPacketPlayerDigging p = (CPacketPlayerDigging) event.getPacket();
            if (items.getValue()) {
                if (!(mc.player.getHeldItemMainhand().getItem() == Items.AIR) && (p.getAction().equals(CPacketPlayerDigging.Action.DROP_ITEM) || p.getAction().equals(CPacketPlayerDigging.Action.DROP_ALL_ITEMS))) {
                    String name = mc.player.inventory.getCurrentItem().getDisplayName();
                    if (droppedItems.containsKey(name)) {
                        droppedItems.put(name, droppedItems.get(name) + 1);
                    } else {
                        droppedItems.put(name, 1);
                    }
                    lastEventSend = event;
                    return;
                }
            }
            if (blocks.getValue()) {
                if (p.getAction().equals(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK)) {
                    String name = mc.world.getBlockState(p.getPosition()).getBlock().getLocalizedName();
                    if (minedBlocks.containsKey(name)) {
                        minedBlocks.put(name, minedBlocks.get(name) + 1);
                    } else {
                        minedBlocks.put(name, 1);
                    }
                    lastEventSend = event;
                    return;
                }
            }
        } else if (items.getValue() && event.getPacket() instanceof CPacketUpdateSign) {
            String message;
            if (clientName.getValue()) {
                message = "Jag placerade en skylt, tack vare " + KamiMod.NAME_UNICODE + "!";
            } else {
                message = "Jag placerade en skylt!";
            }
            queueMessage(message);
            lastEventSend = event;
            return;
        } else if (blocks.getValue() && event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
            ItemStack itemStack = mc.player.inventory.getCurrentItem();
            if (itemStack.isEmpty) {
                lastEventSend = event;
                return;
            }
            if (itemStack.getItem() instanceof ItemBlock) {
                String name = mc.player.inventory.getCurrentItem().getDisplayName();
                if (placedBlocks.containsKey(name)) {
                    placedBlocks.put(name, placedBlocks.get(name) + 1);
                } else {
                    placedBlocks.put(name, 1);
                }
                lastEventSend = event;
                return;
            }
        }
    });

    @EventHandler
    public Listener<LivingEntityUseItemEvent.Finish> listener = new Listener<>(event -> {
        if (event.getEntity().equals(mc.player) && event.getItem().getItem() instanceof ItemFood) {
            String name = event.getItem().getDisplayName();
            if (consumedItems.containsKey(name)) {
                consumedItems.put(name, consumedItems.get(name) + 1);
            } else {
                consumedItems.put(name, 1);
            }
            lastLivingEntityUseFinishEvent = event;
            return;
        }
    });

    @Override
    public void onEnable() {
        timer = new Timer();
        if (mc.player == null) {
            this.disable();
            return;
        }
        df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                sendMessageCycle();
            }
        };
        timer.schedule(timerTask, 0, delay.getValue() * 1000);
    }

    @Override
    public void onDisable() {
        timer.cancel();
        timer.purge();
        messageQueue.clear();
    }

    @Override
    public void onUpdate() {
        if (isDisabled() || mc.player == null || ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }
        if (clearqueue.getValue()) {
            clearqueue.setValue(false);
            messageQueue.clear();
        }
        getGameTickData();
    }

    private void getGameTickData() {
        // Player Movement Distance
        if (distance.getValue()) {
            lastTickPos = thisTickPos;
            thisTickPos = mc.player.getPositionVector();
            distanceTraveled = distanceTraveled + thisTickPos.distanceTo(lastTickPos);
        }
        // Player Health Data
        if (playerheal.getValue() || playerdamage.getValue()) {
            lastTickHealth = thisTickHealth;
            thisTickHealth = mc.player.getHealth() + mc.player.getAbsorptionAmount();
            float healthDiff = (thisTickHealth - lastTickHealth);
            if (healthDiff < 0) {
                lostHealth = lostHealth + (healthDiff * -1);
            } else {
                gainedHealth = gainedHealth + healthDiff;
            }
        }
    }

    private void composeGameTickData() {
        // Skip First run
        if (isFirstRun) {
            isFirstRun = false;
            clearTickData();
            return;
        }
        String suffix;
        if (clientName.getValue()) {
            suffix = ", tack vare " + KamiMod.NAME_UNICODE + "!";
        } else {
            suffix = "!";
        }
        // Player Movement Distance
        if (distance.getValue() && distanceTraveled >= 1) {
            if (distanceTraveled < delay.getValue() * mindistance.getValue()) {
                // Skip counting moves below min distance
                return;
            }
            if (distanceTraveled > delay.getValue() * maxdistance.getValue()) {
                // Dont spoil Position after death etc.
                distanceTraveled = 0;
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Jag flyttade mig ");
            sb.append((int) distanceTraveled);
            if ((int) distanceTraveled == 1) {
                sb.append(" Block");
            } else {
                sb.append(" Blocks");
            }
            sb.append(suffix);
            queueMessage(sb.toString());
            distanceTraveled = 0;
        }
        // Player Health Data
        if (playerdamage.getValue() && lostHealth != 0) {
            queueMessage("Jag f\u00f6rlorade " + df.format(lostHealth) + " H\u00e4lsa" + suffix);
            lostHealth = 0;
        }
        if (playerheal.getValue() && gainedHealth != 0) {
            queueMessage("Jag fick " + df.format(gainedHealth) + " H\u00e4lsa" + suffix);
            gainedHealth = 0;
        }

    }


    private void composeEventData() {
        String suffix;
        if (clientName.getValue()) {
            suffix = ", tack vare " + KamiMod.NAME_UNICODE + "!";
        } else {
            suffix = "!";
        }
        for (Map.Entry<String, Integer> kv : minedBlocks.entrySet()) {
            queueMessage("Jag gr\u00e4vde " + kv.getValue() + " " + kv.getKey() + suffix);
            minedBlocks.remove(kv.getKey());
        }
        for (Map.Entry<String, Integer> kv : placedBlocks.entrySet()) {
            queueMessage("Jag placerade " + kv.getValue() + " " + kv.getKey() + suffix);
            placedBlocks.remove(kv.getKey());
        }
        for (Map.Entry<String, Integer> kv : droppedItems.entrySet()) {
            queueMessage("Jag kastade " + kv.getValue() + " " + kv.getKey() + suffix);
            droppedItems.remove(kv.getKey());
        }
        for (Map.Entry<String, Integer> kv : consumedItems.entrySet()) {
            queueMessage("Jag \u00E5t " + kv.getValue() + " " + kv.getKey() + suffix);
            consumedItems.remove(kv.getKey());
        }
    }

    private void sendMessageCycle() {
        if (isDisabled() || mc.player == null || ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }
        composeGameTickData();
        composeEventData();
        for (String message : messageQueue) {
            sendMessage(message);
            messageQueue.remove(message);
            return;
        }
    }

    private void sendMessage(String s) {
        StringBuilder sb = new StringBuilder();
        if (greentext.getValue()) {
            sb.append("> ");
        }
        sb.append(s);
        mc.player.connection.sendPacket(new CPacketChatMessage(sb.toString().replaceAll(ChatTextUtils.SECTIONSIGN, "")));
    }

    private void clearTickData() {
        // Player Movement Distance
        Vec3d pos = mc.player.getPositionVector();
        lastTickPos = pos;
        thisTickPos = pos;
        distanceTraveled = 0;
        // Player Health Data
        float health = mc.player.getHealth() + mc.player.getAbsorptionAmount();
        lastTickHealth = health;
        thisTickHealth = health;
        lostHealth = 0;
        gainedHealth = 0;
    }

    private Block getBlock(BlockPos pos) {
        return mc.world.getBlockState(pos).getBlock();
    }

    private void queueMessage(String message) {
        if (messageQueue.size() > queuesize.getValue()) {
            return;
        }
        messageQueue.add(message);
    }

}
