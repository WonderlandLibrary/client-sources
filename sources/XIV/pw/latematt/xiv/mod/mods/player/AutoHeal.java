package pw.latematt.xiv.mod.mods.player;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.potion.Potion;
import org.lwjgl.input.Keyboard;
import pw.latematt.timer.Timer;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.event.events.ReadPacketEvent;
import pw.latematt.xiv.event.events.SendPacketEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.BlockUtils;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.value.ClampedValue;
import pw.latematt.xiv.value.Value;

import java.util.Objects;

import static pw.latematt.xiv.utils.InventoryUtils.*;

/**
 * @author Matthew
 */
public class AutoHeal extends Mod implements CommandHandler {
    private final ClampedValue<Long> delay = new ClampedValue<>("autoheal_delay", 200L, 0L, 1000L);
    private final ClampedValue<Float> health = new ClampedValue<>("autoheal_health", 12.0F, 1.0F, 20.0F);
    private final Value<Mode> mode = new Value<>("autoheal_mode", Mode.POTION);
    private final Listener sendPacketListener, readPacketListener, motionUpdateListener;
    private final Timer timer = new Timer();
    private boolean needsToHeal;
    private boolean healing;

    public AutoHeal() {
        super("AutoHeal", ModType.PLAYER, Keyboard.KEY_NONE, 0xFF85E0E0);
        Command.newCommand().cmd("autoheal").aliases("aheal", "autoh", "ah").description("Base command for the AutoHeal mod.").arguments("<action>").handler(this).build();

        motionUpdateListener = new Listener<MotionUpdateEvent>() {
            @Override
            public void onEventCalled(MotionUpdateEvent event) {
                if (Objects.equals(event.getCurrentState(), MotionUpdateEvent.State.PRE)) {
                    updateCounter();
                    if (needsToHeal && timer.hasReached(delay.getValue()) && mode.getValue() == Mode.POTION) {
                        if (canSafelyThrowPot()) {
                            if (!hotbarHasPotion(Potion.INSTANT_HEALTH, true) && !hotbarIsFull())
                                shiftClickPotion(Potion.INSTANT_HEALTH, true);

                            if (hotbarHasPotion(Potion.INSTANT_HEALTH, true)) {
                                healing = true;
                                event.setYaw(-event.getYaw());
                                event.setPitch(85);
                            }
                        }
                    }
                } else if (Objects.equals(event.getCurrentState(), MotionUpdateEvent.State.POST)) {
                    if (healing) {
                        if (needsToHeal) {
                            useFirstPotion(Potion.INSTANT_HEALTH, true);
                            needsToHeal = false;
                        }
                        healing = false;
                        timer.reset();
                    }
                }
            }
        };

        sendPacketListener = new Listener<SendPacketEvent>() {
            @Override
            public void onEventCalled(SendPacketEvent event) {
                if (event.getPacket() instanceof C03PacketPlayer) {
                    C03PacketPlayer player = (C03PacketPlayer) event.getPacket();
                    if (healing) {
                        player.setYaw(-player.getYaw());
                        player.setPitch(85);
                    }
                }
            }
        };

        readPacketListener = new Listener<ReadPacketEvent>() {
            @Override
            public void onEventCalled(ReadPacketEvent event) {
                if (event.getPacket() instanceof S06PacketUpdateHealth) {
                    S06PacketUpdateHealth updateHealth = (S06PacketUpdateHealth) event.getPacket();
                    needsToHeal = updateHealth.getHealth() <= health.getValue();
                    if (mode.getValue() == Mode.HEAD)
                        needsToHeal = needsToHeal || teammateNeedsToHeal();
                    if (needsToHeal && timer.hasReached(delay.getValue())) {
                        switch (mode.getValue()) {
                            case SOUP:
                                dropFirst(Items.bowl);
                                if (!hotbarHas(Items.mushroom_stew) && !hotbarIsFull())
                                    shiftClick(Items.mushroom_stew);

                                useFirst(Items.mushroom_stew);
                                needsToHeal = false;
                                timer.reset();
                                break;
                            case HEAD:
                                if (!hotbarHas(Items.skull) && !hotbarIsFull())
                                    shiftClick(Items.skull);

                                useFirst(Items.skull);
                                needsToHeal = false;
                                timer.reset();
                                break;
                            case COOKIE:
                                if (!hotbarHas(Items.cookie) && !hotbarIsFull())
                                    shiftClick(Items.cookie);

                                useFirst(Items.cookie);
                                needsToHeal = false;
                                timer.reset();
                                break;
                        }
                    }
                }
            }
        };
    }

    private boolean teammateNeedsToHeal() {
        return mc.theWorld.playerEntities.stream().filter(player -> mc.thePlayer.isOnSameTeam(player) || XIV.getInstance().getFriendManager().isFriend(player.getName())).filter(player -> player.getHealth() <= health.getValue()).findAny().isPresent();
    }

    private boolean canSafelyThrowPot() {
        /* air and water check, to prevent wasting pots */
        Block blockUnder = BlockUtils.getBlock(mc.thePlayer, -0.1);
        Block block2Under = BlockUtils.getBlock(mc.thePlayer, -1.1);
        boolean airCheck = false;
        boolean waterCheck = false;
        if (blockUnder instanceof BlockAir && block2Under instanceof BlockAir)
            airCheck = true;
        else if (blockUnder instanceof BlockLiquid && block2Under instanceof BlockLiquid)
            waterCheck = true;

        return countPotion(Potion.INSTANT_HEALTH, true) > 0 && !airCheck && !waterCheck;
    }

    private void updateCounter() {
        String displayName = getName();

        switch (mode.getValue()) {
            case SOUP:
                int soups = countItem(Items.mushroom_stew);
                if (soups > 0)
                    displayName += " \2476" + soups;
                break;
            case HEAD:
                int heads = countItem(Items.skull);
                if (heads > 0)
                    displayName += " \2477" + heads;
                break;
            case COOKIE:
                int cookies = countItem(Items.cookie);
                if (cookies > 0)
                    displayName += " \2475" + cookies;
                break;
            case POTION:
                int potions = countPotion(Potion.INSTANT_HEALTH, true);
                if (potions > 0)
                    displayName += " \247c" + potions;
                break;
        }

        setDisplayName(displayName);
    }

    public boolean isHealing() {
        return healing;
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "delay":
                case "d":
                    if (arguments.length >= 3) {
                        String newDelayString = arguments[2];
                        try {
                            if (arguments[2].equalsIgnoreCase("-d")) {
                                delay.setValue(delay.getDefault());
                            } else {
                                long newDelay = arguments[2].equalsIgnoreCase("-d") ? delay.getDefault() : Long.parseLong(newDelayString);
                                delay.setValue(newDelay);
                                if (delay.getValue() > delay.getMax())
                                    delay.setValue(delay.getMax());
                                else if (delay.getValue() < delay.getMin())
                                    delay.setValue(delay.getMin());
                            }
                            ChatLogger.print(String.format("AutoHeal Delay set to %sms", delay.getValue()));
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", newDelayString));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: autoheal delay <number>");
                    }
                    break;
                case "health":
                    if (arguments.length >= 3) {
                        String newHealthString = arguments[2];
                        try {
                            Float newHealth = arguments[2].equalsIgnoreCase("-d") ? health.getDefault() : Float.parseFloat(newHealthString);
                            health.setValue(newHealth);
                            if (health.getValue() > health.getMax())
                                health.setValue(health.getMax());
                            else if (health.getValue() < health.getMin())
                                health.setValue(health.getMin());

                            ChatLogger.print(String.format("AutoHeal Health set to %s", health.getValue()));
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", newHealthString));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: autoheal health <number>");
                    }
                    break;
                case "mode":
                    if (arguments.length >= 3) {
                        String mode = arguments[2];
                        switch (mode.toLowerCase()) {
                            case "soup":
                                this.mode.setValue(Mode.SOUP);
                                ChatLogger.print(String.format("AutoHeal Mode set to: %s", this.mode.getValue().getName()));
                                break;
                            case "head":
                                this.mode.setValue(Mode.HEAD);
                                ChatLogger.print(String.format("AutoHeal Mode set to: %s", this.mode.getValue().getName()));
                                break;
                            case "cookie":
                                this.mode.setValue(Mode.COOKIE);
                                ChatLogger.print(String.format("AutoHeal Mode set to: %s", this.mode.getValue().getName()));
                                break;
                            case "potion":
                            case "pot":
                                this.mode.setValue(Mode.POTION);
                                ChatLogger.print(String.format("AutoHeal Mode set to: %s", this.mode.getValue().getName()));
                                break;
                            case "-d":
                                this.mode.setValue(this.mode.getDefault());
                                ChatLogger.print(String.format("AutoHeal Mode set to: %s", this.mode.getValue().getName()));
                                break;
                            default:
                                ChatLogger.print("Invalid mode, valid: soup, head, cookie, potion");
                                break;
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: autoheal mode <mode>");
                    }
                    break;
                case "heal":
                    switch (mode.getValue()) {
                        case SOUP:
                            if (hotbarHas(Items.bowl))
                                dropFirst(Items.bowl);
                            useFirst(Items.mushroom_stew);
                            break;
                        case HEAD:
                            useFirst(Items.skull);
                            break;
                        case COOKIE:
                            useFirst(Items.cookie);
                            break;
                        case POTION:
                            useFirstPotion(Potion.INSTANT_HEALTH, true);
                            break;
                    }
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: delay, health, mode, heal");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: autoheal <action>");
        }
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(sendPacketListener, readPacketListener, motionUpdateListener);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(sendPacketListener, readPacketListener, motionUpdateListener);
        healing = false;
    }

    private enum Mode {
        SOUP, HEAD, COOKIE, POTION;

        public String getName() {
            String prettyName = "";
            String[] actualNameSplit = name().split("_");
            if (actualNameSplit.length > 0) {
                for (String arg : actualNameSplit) {
                    arg = arg.substring(0, 1).toUpperCase() + arg.substring(1, arg.length()).toLowerCase();
                    prettyName += arg + " ";
                }
            } else {
                prettyName = actualNameSplit[0].substring(0, 1).toUpperCase() + actualNameSplit[0].substring(1, actualNameSplit[0].length()).toLowerCase();
            }
            return prettyName.trim();
        }
    }
}
