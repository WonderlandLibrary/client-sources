package me.travis.wurstplus.module.modules.chat;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;

import com.ibm.icu.math.BigDecimal;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.travis.wurstplus.event.events.GuiScreenEvent;
import me.travis.wurstplus.event.events.PacketEvent;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.network.play.server.SPacketUseBed;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

@Module.Info(name="Announcer", category=Module.Category.CHAT)
public class Announcer extends Module {
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
    private static String lastmessage;
    private static Vec3d thisTickPos;
    private static Vec3d lastTickPos;
    private static double distanceTraveled;
    private static float thisTickHealth;
    private static float lastTickHealth;
    private static float gainedHealth;
    private static float lostHealth;
    private Setting<Boolean> distance = this.register(Settings.b("Distance", true));
    private Setting<Integer> mindistance = this.register(Settings.integerBuilder("Min Distance").withRange(1, 100).withValue(10).build());
    private Setting<Integer> maxdistance = this.register(Settings.integerBuilder("Max Distance").withRange(100, 10000).withValue(150).build());
    private Setting<Boolean> blocks = this.register(Settings.b("Blocks", true));
    private Setting<Boolean> items = this.register(Settings.b("Items", true));
    private Setting<Boolean> playerheal = this.register(Settings.b("Player Heal", true));
    private Setting<Boolean> playerdamage = this.register(Settings.b("Player Damage", true));
    private Setting<Boolean> playerdeath = this.register(Settings.b("Death", true));
    private Setting<Boolean> greentext = this.register(Settings.b("Greentext", false));
    private Setting<Integer> delay = this.register(Settings.integerBuilder("Send Delay").withRange(1, 20).withValue(2).build());
    private Setting<Integer> queuesize = this.register(Settings.integerBuilder("Queue Size").withRange(1, 100).withValue(5).build());
    private Setting<Boolean> clearqueue = this.register(Settings.b("Clear Queue", false));
    private Setting<DistanceSetting> distanceSet = register(Settings.e("Unit of distance", DistanceSetting.Feet));
    private Setting<ChatSetting> mode = register(Settings.e("Mode", ChatSetting.Lata));
    @EventHandler
    public Listener<GuiScreenEvent.Displayed> guiScreenEventDisplayedlistener = new Listener<GuiScreenEvent.Displayed>(event -> {
        if (this.isDisabled() || mc.player == null) {
            return;
        }
        if (lastGuiScreenDisplayedEvent != null && lastGuiScreenDisplayedEvent.equals(event)) {
            return;
        }
        if (this.playerdeath.getValue().booleanValue() && event.getScreen() instanceof GuiGameOver) {
            this.queueMessage("I appear to be not living");
            return;
        }
        lastGuiScreenDisplayedEvent = event;
    }, new Predicate[0]);
    @EventHandler
    private Listener<PacketEvent.Receive> packetEventReceiveListener = new Listener<PacketEvent.Receive>(event -> {
        if (this.isDisabled() || mc.player == null) {
            return;
        }
        if (lastEventReceive != null && lastEventReceive.equals(event)) {
            return;
        }
        if (event.getPacket() instanceof SPacketUseBed) {
            this.queueMessage("Sleepy Time");
            lastEventReceive = event;
            return;
        }
    }, new Predicate[0]);
    @EventHandler
    private Listener<PacketEvent.Send> packetEventSendListener = new Listener<PacketEvent.Send>(event -> {
        if (this.isDisabled() || mc.player == null) {
            return;
        }
        if (lastEventSend != null && lastEventSend.equals(event)) {
            return;
        }
        if ((this.items.getValue().booleanValue() || this.blocks.getValue().booleanValue()) && event.getPacket() instanceof CPacketPlayerDigging) {
            CPacketPlayerDigging p = (CPacketPlayerDigging)event.getPacket();
            if (this.items.getValue().booleanValue() && mc.player.getHeldItemMainhand().getItem() != Items.AIR && (p.getAction().equals((Object)CPacketPlayerDigging.Action.DROP_ITEM) || p.getAction().equals((Object)CPacketPlayerDigging.Action.DROP_ALL_ITEMS))) {
                String name = mc.player.inventory.getCurrentItem().getDisplayName();
                if (droppedItems.containsKey(name)) {
                    droppedItems.put(name, droppedItems.get(name) + 1);
                } else {
                    droppedItems.put(name, 1);
                }
                lastEventSend = event;
                return;
            }
            if (this.blocks.getValue().booleanValue() && p.getAction().equals((Object)CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK)) {
                String name = mc.world.getBlockState(p.getPosition()).getBlock().getLocalizedName();
                if (minedBlocks.containsKey(name)) {
                    minedBlocks.put(name, minedBlocks.get(name) + 1);
                } else {
                    minedBlocks.put(name, 1);
                }
                lastEventSend = event;
                return;
            }
        } else {
            if (this.items.getValue().booleanValue() && event.getPacket() instanceof CPacketUpdateSign) {
                CPacketUpdateSign p = (CPacketUpdateSign)event.getPacket();
                this.queueMessage("We be placing signs and shit");
                lastEventSend = event;
                return;
            }
            if (this.blocks.getValue().booleanValue() && event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                CPacketPlayerTryUseItemOnBlock p = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
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
        }
    }, new Predicate[0]);
    @EventHandler
    public Listener<LivingEntityUseItemEvent.Finish> listener = new Listener<LivingEntityUseItemEvent.Finish>(event -> {
        if (event.getEntity().equals((Object)mc.player) && event.getItem().getItem() instanceof ItemFood) {
            String name = event.getItem().getDisplayName();
            if (consumedItems.containsKey(name)) {
                consumedItems.put(name, consumedItems.get(name) + 1);
            } else {
                consumedItems.put(name, 1);
            }
            lastLivingEntityUseFinishEvent = event;
            return;
        }
    }, new Predicate[0]);

    @Override
    public void onEnable() {
        timer = new Timer();
        if (mc.player == null) {
            this.disable();
            return;
        }
        df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        timerTask = new TimerTask(){

            @Override
            public void run() {
                sendMessageCycle();
            }
        };
        timer.schedule(timerTask, 0L, (long)(this.delay.getValue() * 1000));
    }

    @Override
    public void onDisable() {
        timer.cancel();
        timer.purge();
        messageQueue.clear();
    }

    @Override
    public void onUpdate() {
        if (this.isDisabled() || mc.player == null) {
            return;
        }
        if (this.clearqueue.getValue().booleanValue()) {
            this.clearqueue.setValue(false);
            messageQueue.clear();
        }
        this.getGameTickData();
    }

    private void getGameTickData() {
        if (this.distance.getValue().booleanValue()) {
            lastTickPos = thisTickPos;
            thisTickPos = mc.player.getPositionVector();
            distanceTraveled += thisTickPos.distanceTo(lastTickPos);
        }
        if (this.playerheal.getValue().booleanValue() || this.playerdamage.getValue().booleanValue()) {
            lastTickHealth = thisTickHealth;
            thisTickHealth = mc.player.getHealth() + mc.player.getAbsorptionAmount();
            float healthDiff = thisTickHealth - lastTickHealth;
            if (healthDiff < 0.0f) {
                lostHealth += healthDiff * -1.0f;
            } else {
                gainedHealth += healthDiff;
            }
        }
    }

    private void composeGameTickData() {
        CharSequence sb;
        if (isFirstRun) {
            isFirstRun = false;
            this.clearTickData();
            return;
        }
        if (this.distance.getValue().booleanValue() && distanceTraveled >= 1.0) {
            if (distanceTraveled < (double)(this.delay.getValue() * this.mindistance.getValue())) {
                return;
            }
            if (distanceTraveled > (double)(this.delay.getValue() * this.maxdistance.getValue())) {
                distanceTraveled = 0.0;
                return;
            }
            sb = new StringBuilder();
            if (mode.getValue() == ChatSetting.Lata) {
                ((StringBuilder)sb).append("leta bitch, I just traveled ");
            }
            else {
                ((StringBuilder)sb).append("aha x, I just traveled ");
            }
            if (distanceSet.getValue() == DistanceSetting.Feet) {
                ((StringBuilder)sb).append(round(distanceTraveled*3.2808,2));
                if ((int)distanceTraveled == (double) 1) {
                    ((StringBuilder)sb).append(" Foot :D");
                } else {
                    ((StringBuilder)sb).append(" Feet :D");
                }
            } else if (distanceSet.getValue() == DistanceSetting.Yards) {
                ((StringBuilder)sb).append(round(distanceTraveled*1.0936,2));
                if ((int)distanceTraveled == (double) 1) {
                    ((StringBuilder)sb).append(" Yard :D");
                } else {
                    ((StringBuilder)sb).append(" Yards :D");
                }
            } else if (distanceSet.getValue() == DistanceSetting.Inches) {
                ((StringBuilder)sb).append(round(distanceTraveled*39.37,2));
                if ((int)distanceTraveled == (double) 1) {
                    ((StringBuilder)sb).append(" Inch :D");
                } else {
                    ((StringBuilder)sb).append(" Inches :D");
                }
            } else {
                ((StringBuilder)sb).append(round(distanceTraveled,2));
                if ((int)distanceTraveled == (double) 1) {
                    ((StringBuilder)sb).append(" Block :D");
                } else {
                    ((StringBuilder)sb).append(" Blocks :D");
                }
            }
            this.queueMessage(((StringBuilder)sb).toString());
            distanceTraveled = 0.0;
        }
        if (this.playerdamage.getValue().booleanValue() && lostHealth != 0.0f) {
            sb = "HECK! I just lost " + df.format(lostHealth) + " health D:";
            this.queueMessage((String)sb);
            lostHealth = 0.0f;
        }
        if (this.playerheal.getValue().booleanValue() && gainedHealth != 0.0f) {
            sb = "Hell yeah, I now have " + df.format(gainedHealth) + " more health :D";
            this.queueMessage((String)sb);
            gainedHealth = 0.0f;
        }
    }

    private void composeEventData() {
        for (Map.Entry<String, Integer> kv : minedBlocks.entrySet()) {
            this.queueMessage("We be mining " + kv.getValue() + " " + kv.getKey() + " out here");
            minedBlocks.remove(kv.getKey());
        }
        for (Map.Entry<String, Integer> kv : placedBlocks.entrySet()) {
            this.queueMessage("We be placing " + kv.getValue() + " " + kv.getKey() + " out here");
            placedBlocks.remove(kv.getKey());
        }
        for (Map.Entry<String, Integer> kv : droppedItems.entrySet()) {
            this.queueMessage("I just dropped " + kv.getValue() + " " + kv.getKey() + ", whoops!");
            droppedItems.remove(kv.getKey());
        }
        for (Map.Entry<String, Integer> kv : consumedItems.entrySet()) {
            this.queueMessage("NOM NOM, I just ate " + kv.getValue() + " " + kv.getKey() + ", yummy");
            consumedItems.remove(kv.getKey());
        }
    }

    public void sendMessageCycle() {
        if (this.isDisabled() || mc.player == null) {
            return;
        }
        this.composeGameTickData();
        this.composeEventData();
        Iterator iterator = messageQueue.iterator();
        if (iterator.hasNext()) {
            String message = (String)iterator.next();
            this.sendMessage(message);
            messageQueue.remove(message);
            return;
        }
    }

    private void sendMessage(String s) {
        StringBuilder sb = new StringBuilder();
        if (this.greentext.getValue().booleanValue()) {
            sb.append("> ");
        }
        sb.append(s);
        mc.player.connection.sendPacket((Packet)new CPacketChatMessage(sb.toString().replaceAll("\u00a7", "")));
    }

    private void clearTickData() {
        float health;
        Vec3d pos;
        lastTickPos = pos = mc.player.getPositionVector();
        thisTickPos = pos;
        distanceTraveled = 0.0;
        lastTickHealth = health = mc.player.getHealth() + mc.player.getAbsorptionAmount();
        thisTickHealth = health;
        lostHealth = 0.0f;
        gainedHealth = 0.0f;
    }

    private Block getBlock(BlockPos pos) {
        return mc.world.getBlockState(pos).getBlock();
    }

    private void queueMessage(String message) {
        if (messageQueue.size() > this.queuesize.getValue()) {
            return;
        }
        messageQueue.add(message);
    }

    public static double round(double unrounded, int precision) {
    BigDecimal bd = new BigDecimal(unrounded);
    BigDecimal rounded = bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
    return rounded.doubleValue();
    }

    static {
        lastmessage = "";
    }

    public static enum ChatSetting {
        Lata , aha
    }

    public static enum DistanceSetting {
        Feet , Meters, Yards, Inches
    }

}

