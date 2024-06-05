package gq.vapu.czfclient.Module.Modules.Movement;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventMove;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.API.Value.Mode;
import gq.vapu.czfclient.API.Value.Numbers;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.Client;
import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Module.Modules.Combat.Velocity;
import gq.vapu.czfclient.UI.ClientNotification;
import gq.vapu.czfclient.Util.ClientUtil;
import gq.vapu.czfclient.Util.TimerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;

import java.awt.*;

public class HypixelLongJump extends Module {

    public final static TimerUtil timer = new TimerUtil();
    public static Mode<Enum> mode;
    private static String[] JumpMode;

    static {
        HypixelLongJump.JumpMode = new String[]{"Damage"};
        HypixelLongJump.mode = new Mode<Enum>("Mode", "Mode", LongjumpMode.values(), LongjumpMode.Hypixel);
    }

    public int cs;
    public int kz = 0;
    Velocity AntiVelocity = (Velocity) ModuleManager.getModuleByClass(Velocity.class);
    private double stage;
    private double moveSpeed;
    private double lastDist;
    private int end;
    private final Option<Boolean> autoToggle;
    private boolean shouldDisable;
    private final Numbers<Double> TakeofftheTimer = new Numbers<Double>("Long", "Long", 13.0, 5.0, 50.0, 1.0);
    private int health;//(int) mc.thePlayer.getHealth();
    private Boolean qj = false;
    //--------------------------------------扣血
//选择最好的

    public HypixelLongJump() {
        super("LongJump", new String[]{"lj", "jumpMan", "jump"}, ModuleType.Movement);
        this.autoToggle = new Option<Boolean>("AutoToggle          ", "AutoToggle          ", false);
        this.addValues(HypixelLongJump.mode, this.autoToggle, this.TakeofftheTimer);
        this.setColor(new Color(76, 67, 216).getRGB());
    }

    //--------------------------------------扣血
    //------------------------判断方块
    public static boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1; x++) {
            for (int y = MathHelper.floor_double(mc.thePlayer.boundingBox.minY); y < MathHelper.floor_double(mc.thePlayer.boundingBox.maxY) + 1; y++) {
                for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; z++) {
                    Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if ((block != null) && (!(block instanceof BlockAir))) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(mc.theWorld, new BlockPos(x, y, z), mc.theWorld.getBlockState(new BlockPos(x, y, z)));
                        if ((block instanceof BlockHopper)) {
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        }
                        if (boundingBox != null) {
                            if (mc.thePlayer.boundingBox.intersectsWith(boundingBox)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    //------------------------扣血
    private void damagePlayer() {
        if (HypixelLongJump.mc.thePlayer.onGround) {
//            final double offset = 0.060100000351667404;
//            final NetHandlerPlayClient netHandler = HypixelLongJump.mc.getNetHandler();
//            final EntityPlayerSP player = HypixelLongJump.mc.thePlayer;
//            final double x = player.posX;
//            final double y = player.posY;
//            final double z2 = player.posZ;
//            for (int i = 0; i < 1.2132436245776346; ++i) {
//                netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.060100000351667404, z2, false));
//                netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 5.000000237487257E-4, z2, false));
//                netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.004999999888241291 + 6.01000003516674E-8, z2, false));
//            }
//            netHandler.addToSendQueue(new C03PacketPlayer(true));
//            int currentSlot = this.mc.thePlayer.inventory.currentItem;
            mc.thePlayer.inventory.currentItem = 1;
            Minecraft.playerController.updateController();
            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(1));
        }
    }

    //------------------------扣血
    @Override
    public void onEnable() {
        kz = 0;
        qj = false;
//         if (HypixelLongJump.mode.getValue() == LongjumpMode.Hypixel) {
//        this.damagePlayer();
//         }
        this.shouldDisable = this.autoToggle.getValue();
    }

    @Override
    public void onDisable() {
        AntiVelocity.setEnabled(true);
        final Timer timer = mc.timer;
        Timer.timerSpeed = 1.0f;
        Timer.timerSpeed = 1.0f;
        if (HypixelLongJump.mc.thePlayer != null) {
            this.moveSpeed = this.getBaseMoveSpeed();
        }
        this.lastDist = 0.0;
        this.stage = 0;
    }

    //选择你要的
    private void getBestPickaxe() {
        int i = 9;
        ItemStack is;
        if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(36).getHasStack()
                && this.isBestWeapon(is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(36).getStack())
                && this.getDamage(is) > 0.0f
                && is.getItem() instanceof ItemBow) {
            return;
        } else {
            while (i < 45) {
                if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack()
                        && this.isBestWeapon(is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack())
                        && this.getDamage(is) > 0.0f
                        && is.getItem() instanceof ItemBow) {
                    //对比武器伤害,伤害大的取用且返回数值取用
                    ItemStack s = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(36).getStack();
                    if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(36).getHasStack() != false && s.getItem() instanceof ItemSword) {
                        Minecraft.playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, 36, 1, 2, Minecraft.getMinecraft().thePlayer);
                        Minecraft.playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, i, 0, 2, Minecraft.getMinecraft().thePlayer);
                        timer.reset();
                        break;
                    } else {
                        Minecraft.playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, i, 0, 2, Minecraft.getMinecraft().thePlayer);
                        timer.reset();
                        break;
                    }
                }
                i++;
            }
            Boolean kz = false;
            for (int ii = 44; ii >= 36; ii--) {
                if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(ii).getHasStack()
                        && this.isBestWeapon(is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(ii).getStack())
                        && this.getDamage(is) > 0.0f
                        && is.getItem() instanceof ItemBow) {
                    kz = true;
                    damagePlayer();
                }
            }
            if (kz == false) {
                HypixelLongJump Longjump = (HypixelLongJump) ModuleManager.getModuleByClass(HypixelLongJump.class);
                if (Longjump.isEnabled()) {
                    Longjump.setEnabled(false);
                    //NotificationManager.show(new Notification(NotificationType.DISABLE,"It cannot be started!","Longjump.",1));
                    ClientUtil.sendClientMessage("SomeThing Happen.", ClientNotification.Type.ERROR);
                }
            }
        }
    }

    public boolean isBestWeapon(ItemStack stack) {
        float damage = this.getDamage(stack);
        int i = 9;
        while (i < 45) {
            ItemStack is;
            if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack() && this.getDamage(is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack()) > damage && is.getItem() instanceof ItemBow) {
                return false;
            }
            ++i;
        }
        return stack.getItem() instanceof ItemBow;
    }
//------------------------判断方块

    private float getDamage(ItemStack stack) {
        float damage = 0.0f;
        Item item = stack.getItem();
        if (item instanceof ItemBow) {
            ItemBow sword = (ItemBow) item;
            damage += sword.getMaxDamage();
        }
        return damage += (float) EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25f + (float) EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 0.01f;
    }

    @EventHandler
    private void onUpdate(final EventPreUpdate e) {
        double Timer = (this.TakeofftheTimer.getValue());
        this.getBestPickaxe();
        if (health != (int) mc.thePlayer.getHealth()) {
            kz = kz + 1;
            if (kz >= 2) {
                kz = 0;
                qj = true;
                //AntiVelocity.setEnabled(false);
            }
        }
        health = (int) mc.thePlayer.getHealth();

        if (qj == true) {

            mc.gameSettings.keyBindForward.pressed = true;

        }

        if (qj == true) {
            cs = cs + 1;
            if (cs == (int) Timer || mc.thePlayer.isCollidedHorizontally && !isInsideBlock()) {
                cs = 0;
                kz = 0;
                HypixelLongJump ee = (HypixelLongJump) ModuleManager.getModuleByClass(HypixelLongJump.class);
                ee.setEnabled(false);
                qj = false;
                mc.gameSettings.keyBindForward.pressed = false; //自动行走
            }

            this.setSuffix(HypixelLongJump.mode.getValue());
            if (e.getType() == 0) {
                final double xDist = HypixelLongJump.mc.thePlayer.posX - HypixelLongJump.mc.thePlayer.prevPosX;
                final double zDist = HypixelLongJump.mc.thePlayer.posZ - HypixelLongJump.mc.thePlayer.prevPosZ;
                this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
            }
        }
    }

    @EventHandler
    private void onMove(final EventMove e) {
        if (qj == true) {
            if (HypixelLongJump.mc.thePlayer.moveStrafing <= 0.0f && HypixelLongJump.mc.thePlayer.moveForward <= 0.0f) {
                this.stage = 1.0;
            }
            if (this.stage == 1.0 && (HypixelLongJump.mc.thePlayer.moveForward != 0.0f || HypixelLongJump.mc.thePlayer.moveStrafing != 0.0f)) {
                this.stage = 1.97777777;
                this.moveSpeed = 3.0 * this.getBaseMoveSpeed() - 0.01;
            } else if (this.stage == 1.97777777) {
                this.stage = 2.87777777;
                HypixelLongJump.mc.thePlayer.motionY = 0.424;
                EventMove.y = 0.424;
                this.moveSpeed *= 1.39;
            } else if (this.stage == 2.87777777) {
                this.stage = 3.76666;
                final double difference = 0.66 * (this.lastDist - this.getBaseMoveSpeed());
                this.moveSpeed = this.lastDist - difference;
            } else if (this.stage == 3.76666) {
                if (HypixelLongJump.mc.theWorld.getCollidingBoundingBoxes(HypixelLongJump.mc.thePlayer, HypixelLongJump.mc.thePlayer.getEntityBoundingBox().offset(0.0, HypixelLongJump.mc.thePlayer.motionY, 0.0)).size() > 0 || HypixelLongJump.mc.thePlayer.isCollidedVertically) {
                    this.stage = 4.155555;
                }
                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
            } else if (this.stage == 4.155555) {
                if (this.shouldDisable) {
                }
                this.stage = 1.0;
            } else if (this.stage == 0) {
                this.stage = 1.0;
            }
            this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
            final MovementInput movementInput = HypixelLongJump.mc.thePlayer.movementInput;
            float forward = MovementInput.moveForward;
            final MovementInput movementInput2 = HypixelLongJump.mc.thePlayer.movementInput;
            float strafe = MovementInput.moveStrafe;
            float yaw = HypixelLongJump.mc.thePlayer.rotationYaw;
            if (forward == 0.0f && strafe == 0.0f) {
                EventMove.x = 0.0;
                EventMove.z = 0.0;
            } else if (forward != 0.0f) {
                if (strafe >= 1.0f) {
                    yaw += ((forward > 0.0f) ? -45 : 45);
                    strafe = 0.0f;
                } else if (strafe <= -1.0f) {
                    yaw += ((forward > 0.0f) ? 45 : -45);
                    strafe = 0.0f;
                }
                if (forward > 0.0f) {
                    forward = 1.0f;
                } else if (forward < 0.0f) {
                    forward = -1.0f;
                }
            }
            final double mx = Math.cos(Math.toRadians(yaw + 90.0f));
            final double mz = Math.sin(Math.toRadians(yaw + 90.0f));
            EventMove.x = forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz;
            EventMove.z = forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx;
            if (forward == 0.0f && strafe == 0.0f) {
                EventMove.x = 0.0;
                EventMove.z = 0.0;
            }
        }
    }

    private double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (HypixelLongJump.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = HypixelLongJump.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }

    enum LongjumpMode {
        Hypixel,
        Normal;

        private static final /* synthetic */ LongjumpMode[] $VALUES;
//
//        public static LongjumpMode[] values() {
//            return LongjumpMode.$VALUES.clone();
//        }
//
//        public static LongjumpMode valueOf(final String name) {
//            return Enum.valueOf(LongjumpMode.class, name);
//        }

        static {
            $VALUES = new LongjumpMode[]{LongjumpMode.Hypixel,};
        }
    }
}
