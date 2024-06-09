package me.finz0.osiris.module.modules.chat;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.event.events.DestroyBlockEvent;
import me.finz0.osiris.event.events.PacketEvent;
import me.finz0.osiris.event.events.PlayerJumpEvent;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

public class Announcer extends Module {
    public Announcer() {
        super("Announcer", Category.CHAT, "Announces what you do in chat");
    }
    public static int blockBrokeDelay = 0;
    static int blockPlacedDelay = 0;
    static int jumpDelay = 0;
    static int attackDelay = 0;
    static int eattingDelay = 0;

    static long lastPositionUpdate;
    static double lastPositionX;
    static double lastPositionY;
    static double lastPositionZ;
    private static double speed;
    String heldItem = "";

    int blocksPlaced = 0;
    int blocksBroken = 0;
    int eaten = 0;

    public Setting clientSide;
    Setting walk;
    Setting place;
    Setting jump;
    Setting breaking;
    Setting attack;
    Setting eat;
    public Setting clickGui;
    Setting delay;

    public static String walkMessage = "I just walked {blocks} blocks thanks to Aurora!";
    public static String placeMessage = "I just placed {amount} {name} thanks to Aurora!";
    public static String jumpMessage = "I just jumped thanks to Aurora!";
    public static String breakMessage = "I just broke {amount} {name} thanks to Aurora!";
    public static String attackMessage = "I just attacked {name} with a {item} thanks to Aurora!";
    public static String eatMessage = "I just ate {amount} {name} thanks to Aurora!";
    public static String guiMessage = "I just opened my ClickGUI thanks to Aurora!";

    public void setup(){
        AuroraMod.getInstance().settingsManager.rSetting(clientSide = new Setting("ClientSide", this, true, "AnnouncerClientSide"));
        AuroraMod.getInstance().settingsManager.rSetting(walk = new Setting("Walk", this, true, "AnnouncerWalk"));
        AuroraMod.getInstance().settingsManager.rSetting(place = new Setting("BlockPlace", this, false, "AnnouncerBlockPlace"));
        AuroraMod.getInstance().settingsManager.rSetting(jump = new Setting("Jump", this, false, "AnnouncerJump"));
        AuroraMod.getInstance().settingsManager.rSetting(breaking = new Setting("BlockBreak", this, false, "AnnouncerBlockBreak"));
        AuroraMod.getInstance().settingsManager.rSetting(attack = new Setting("AttackEntity", this, false, "AnnouncerAttackEntity"));
        AuroraMod.getInstance().settingsManager.rSetting(eat = new Setting("Eat", this, false, "AnnouncerEat"));
        AuroraMod.getInstance().settingsManager.rSetting(clickGui = new Setting("ClickGui", this, false, "AnnouncerClickGui"));
        AuroraMod.getInstance().settingsManager.rSetting(delay = new Setting("DelayMultiplier", this, 1, 1, 20, true, "AnnouncerDelayMultiplier"));
    }


    public void onUpdate() {
        blockBrokeDelay++;
        blockPlacedDelay++;
        jumpDelay++;
        attackDelay++;
        eattingDelay++;
        heldItem = mc.player.getHeldItemMainhand().getDisplayName();

        if (walk.getValBoolean()) {
            if (lastPositionUpdate + (5000L * delay.getValDouble()) < System.currentTimeMillis()) {

                double d0 = lastPositionX - mc.player.lastTickPosX;
                double d2 = lastPositionY - mc.player.lastTickPosY;
                double d3 = lastPositionZ - mc.player.lastTickPosZ;

                speed = Math.sqrt(d0 * d0 + d2 * d2 + d3 * d3);

                if(!(speed <= 1) && !(speed > 5000)) {
                    String walkAmount = new DecimalFormat("0").format(speed);

                    if (clientSide.getValBoolean()) {
                        Command.sendClientMessage(walkMessage.replace("{blocks}", walkAmount));
                    } else {
                        mc.player.sendChatMessage(walkMessage.replace("{blocks}", walkAmount));
                    }
                    lastPositionUpdate = System.currentTimeMillis();
                    lastPositionX = mc.player.lastTickPosX;
                    lastPositionY = mc.player.lastTickPosY;
                    lastPositionZ = mc.player.lastTickPosZ;
                }
            }
        }

    }

    @EventHandler
    private Listener<LivingEntityUseItemEvent.Finish> eatListener = new Listener<>(event -> {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
        if(event.getEntity() == mc.player){
            if(event.getItem().getItem() instanceof ItemFood || event.getItem().getItem() instanceof ItemAppleGold){
                eaten++;
                if(eattingDelay >= 300 * delay.getValDouble()) {
                    if (eat.getValBoolean() && eaten > randomNum) {
                        if(clientSide.getValBoolean()){
                            Command.sendClientMessage
                                    (eatMessage.replace("{amount}", eaten + "").replace("{name}",  mc.player.getHeldItemMainhand().getDisplayName()));
                        } else {
                            mc.player.sendChatMessage
                                    (eatMessage.replace("{amount}", eaten + "").replace("{name}",  mc.player.getHeldItemMainhand().getDisplayName()));
                        }
                        eaten = 0;
                        eattingDelay = 0;
                    }
                }
            }
        }
    });

    @EventHandler
    private Listener<PacketEvent.Send> sendListener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBlock) {
            blocksPlaced++;
            int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
            if (blockPlacedDelay >= 150 * delay.getValDouble()) {
                if (place.getValBoolean() && blocksPlaced > randomNum){
                    String msg = placeMessage.replace("{amount}", blocksPlaced + "").replace("{name}", mc.player.getHeldItemMainhand().getDisplayName());
                    if(clientSide.getValBoolean()){
                        Command.sendClientMessage(msg);
                    } else {
                        mc.player.sendChatMessage(msg);
                    }
                    blocksPlaced = 0;
                    blockPlacedDelay = 0;
                }
            }
        }
    });

    @EventHandler
    private Listener<DestroyBlockEvent> destroyListener = new Listener<>(event -> {
        blocksBroken++;
        int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
            if (blockBrokeDelay >= 300 * delay.getValDouble()) {
                if (breaking.getValBoolean() && blocksBroken > randomNum) {
                String msg = breakMessage
                        .replace("{amount}", blocksBroken + "")
                        .replace("{name}", mc.world.getBlockState(event.getBlockPos()).getBlock().getLocalizedName());
                if(clientSide.getValBoolean()){
                    Command.sendClientMessage(msg);
                } else {
                    mc.player.sendChatMessage(msg);
                }
                blocksBroken = 0;
                blockBrokeDelay = 0;
            }
        }
    });

    @EventHandler
    private Listener<AttackEntityEvent> attackListener = new Listener<>(event -> {
        if (attack.getValBoolean() && !(event.getTarget() instanceof EntityEnderCrystal)) {
            if (attackDelay >= 300 * delay.getValDouble()) {
                String msg = attackMessage.replace("{name}", event.getTarget().getName()).replace("{item}", mc.player.getHeldItemMainhand().getDisplayName());
                if(clientSide.getValBoolean()){
                    Command.sendClientMessage(msg);
                } else {
                    mc.player.sendChatMessage(msg);
                }
                attackDelay = 0;
            }
        }
    });

    @EventHandler
    private Listener<PlayerJumpEvent> jumpListener = new Listener<>(event -> {
        if (jump.getValBoolean()) {
            if (jumpDelay >= 300 * delay.getValDouble()) {
                if(clientSide.getValBoolean()){
                    Command.sendClientMessage(jumpMessage);
                } else {
                    mc.player.sendChatMessage(jumpMessage);
                }
                jumpDelay = 0;
            }
        }
    });

    public void onEnable(){
        AuroraMod.EVENT_BUS.subscribe(this);
        blocksPlaced = 0;
        blocksBroken = 0;
        eaten = 0;
        speed = 0;
        blockBrokeDelay = 0;
        blockPlacedDelay = 0;
        jumpDelay = 0;
        attackDelay = 0;
        eattingDelay = 0;
    }

    public void onDisable(){
        AuroraMod.EVENT_BUS.unsubscribe(this);
    }

}
