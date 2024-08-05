package fr.dog.module.impl.player;

import fr.dog.Dog;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerNetworkTickEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.event.impl.render.SetMouseTargetEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.BooleanProperty;
import fr.dog.property.impl.ModeProperty;
import fr.dog.property.impl.NumberProperty;
import fr.dog.util.BlockUtil;
import fr.dog.util.PosFace;
import fr.dog.util.packet.PacketUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBed;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;

import java.util.ArrayList;
import java.util.Arrays;

public class BreakerModule extends Module {
    public ModeProperty switchMode = ModeProperty.newInstance("Item Switch Mode", new String[]{"Full", "Switch"}, "Switch");
    public ModeProperty breakMode = ModeProperty.newInstance("Break Mode", new String[]{"Packet", "Safe"}, "Packet");
    private final NumberProperty breakrange = NumberProperty.newInstance("Breaker Range", 3f, 6f, 10f, 0.5f);
    private final BooleanProperty testbreaker = BooleanProperty.newInstance("Breaker Speed (Unsafe)", false);
    private final NumberProperty breakspeed = NumberProperty.newInstance("Breaker Speed", 1f, 1f, 5f, 0.5f, testbreaker::getValue);

    public BlockPos bedPos = null;
    public BlockPos breakPos = null;
    public BlockPos playerBreakPosition = null;
    public float yaw, pitch;
    public float blockDamage;
    public int blockDamageCD = 0;
    public int oldSlot = 0;

    public BreakerModule() {
        super("Breaker", ModuleCategory.PLAYER);
        this.registerProperties(switchMode, breakMode, breakrange, testbreaker, breakspeed);
    }

    public void onEnable() {
        blockDamage = 0;
        blockDamageCD = 0;
        bedPos = null;
        breakPos = null;
        playerBreakPosition = null;
    }

    public void onDisable() {
        if (blockDamage > 0 && blockDamage < 1) {
            PacketUtil.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, playerBreakPosition, EnumFacing.UP));
        }

        blockDamage = 0;
        blockDamageCD = 0;
        bedPos = null;
        breakPos = null;
        playerBreakPosition = null;
        mc.gameSettings.keyBindAttack.pressed = false;
    }

    public void sendBlockBreak(BlockPos pos, EnumFacing face) {
        Block block = mc.theWorld.getBlockState(pos).getBlock();
        if (block instanceof BlockAir) {
            this.blockDamage = 0;
            this.blockDamageCD = 5;
            return;
        }

        int spoofslot = getSlotFromBlock(block);
        ItemStack stack = mc.thePlayer.getHeldItem();
        if (spoofslot == -1) {
            spoofslot = mc.thePlayer.inventory.currentItem;
        } else {
            stack = mc.thePlayer.inventory.getStackInSlot(spoofslot);
        }

        if (switchMode.is("Switch")) {
            if (oldSlot != -1) {
                mc.thePlayer.inventory.currentItem = oldSlot;
                oldSlot = -1;
            }
        }

        if (blockDamage == 0) {
            PacketUtil.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, face));
            mc.thePlayer.swingItem();
        }

        float addyDMG = mc.thePlayer.getToolDigEfficiency(block, stack) / block.getBlockHardness(mc.theWorld, pos) / 30.0F;
        if (mc.thePlayer.isPotionActive(Potion.digSpeed)) {
            addyDMG *= (1.0F + (float)(mc.thePlayer.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F);
        }

        if (testbreaker.getValue()) addyDMG *= (breakspeed.getValue());

        blockDamage += addyDMG;
        mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), pos, (int) (this.blockDamage * 10.0F) - 1);

        if (blockDamage >= 1) {
            oldSlot = mc.thePlayer.inventory.currentItem;
            mc.thePlayer.inventory.currentItem = spoofslot;
            mc.playerController.syncCurrentPlayItem();
            mc.thePlayer.swingItem();
            PacketUtil.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, face));
            this.blockDamage = 0;
            this.blockDamageCD = 5;
            mc.playerController.onPlayerDestroyBlock(pos, EnumFacing.UP);
            if (switchMode.is("Full")) {
                if (oldSlot != -1) {
                    mc.thePlayer.inventory.currentItem = oldSlot;
                    oldSlot = -1;
                }
            }
        }
    }

    @SubscribeEvent
    private void onPlayerTick(PlayerTickEvent event) {
        if (Dog.getInstance().getModuleManager().getModule(NoFall.class).blinking) {
            return;
        }

        if (breakMode.is("Packet")) {
            if (playerBreakPosition != null) {
                sendBlockBreak(playerBreakPosition, EnumFacing.UP);
                playerBreakPosition = null;
            } else {
                mc.gameSettings.keyBindAttack.pressed = false;
            }
            if (checkPosValidity(breakPos) && blockDamageCD == 0) {
                playerBreakPosition = breakPos;
            } else {
                playerBreakPosition = null;

                blockDamageCD = MathHelper.clamp_int(--blockDamageCD, 0, 5);
                blockDamage = 0;
                bedPos = findBed(breakrange.getValue());

                if (isBedOpen(bedPos)) {
                    breakPos = bedPos;
                } else {
                    breakPos = getNearestBlock(bedPos);
                }
            }
        } else {
            bedPos = findBed(breakrange.getValue());
            if (isBedOpen(bedPos)) {
                breakPos = bedPos;
            } else {
                breakPos = getNearestBlock(bedPos);
            }
            Block block = mc.theWorld.getBlockState(breakPos).getBlock();
            int spoofslot = getSlotFromBlock(block);
            if (spoofslot != -1) {
                mc.thePlayer.inventory.currentItem = spoofslot;
            }
            mc.gameSettings.keyBindAttack.pressed = breakPos != null;
        }
    }

    @SubscribeEvent
    private void onMouseOver(SetMouseTargetEvent event) {
        if (breakPos != null && breakMode.is("Safe")) {
            event.setMove(new MovingObjectPosition(new Vec3(breakPos.getX() + Math.random(), breakPos.getY() + Math.random(), breakPos.getZ() + Math.random()), EnumFacing.UP, breakPos));
        }
    }

    @SubscribeEvent
    private void onNetworkTick(PlayerNetworkTickEvent event) {
        if (breakMode.is("Packet")) {
            if (playerBreakPosition != null && !(mc.theWorld.getBlockState(playerBreakPosition).getBlock() instanceof BlockAir)) {
                if ((blockDamage == 0 || (blockDamage + mc.theWorld.getBlockState(playerBreakPosition).getBlock().getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.thePlayer.worldObj, playerBreakPosition)) >= 1)) {
                    float[] rotations = BlockUtil.getRotationToBlockDirect(new PosFace(playerBreakPosition, EnumFacing.DOWN));
                    event.setYaw(rotations[0]);
                    event.setPitch(rotations[1]);
                    mc.thePlayer.rotationYawHead = rotations[0];
                    mc.thePlayer.renderPitchHead = rotations[1];
                }
            }
        } else if (breakPos != null && !(mc.theWorld.getBlockState(breakPos).getBlock() instanceof BlockAir)) {
            float[] rotations = BlockUtil.getRotationToBlockDirect(new PosFace(breakPos, EnumFacing.DOWN));
            event.setYaw(rotations[0]);
            event.setPitch(rotations[1]);
            mc.thePlayer.rotationYawHead = rotations[0];
            mc.thePlayer.renderPitchHead = rotations[1];
        }
    }

    private boolean checkPosValidity(BlockPos pos) {
        if (pos == null) {
            return false;
        }
        return !(mc.thePlayer.getDistance(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) > 6);
    }

    private BlockPos findBed(float distance) {
        float bedDist = 69420;
        BlockPos bedPos = null;

        for (float x = -distance; x < distance; x++) {
            for (float z = -distance; z < distance; z++) {
                for (float y = -distance; y < distance; y++) {
                    BlockPos cPos = new BlockPos(x + mc.thePlayer.posX, y + mc.thePlayer.posY, z + mc.thePlayer.posZ);
                    if (mc.theWorld.getBlockState(cPos).getBlock() instanceof BlockBed) {
                        double bcd = mc.thePlayer.getDistance(cPos.getX() + 0.5, cPos.getY() + 0.5, cPos.getZ() + 0.5);
                        if (bcd < bedDist) {
                            bedDist = (float) bcd;
                            bedPos = cPos;
                        }
                    }
                }
            }
        }

        return bedPos;
    }

    private boolean isBedOpen(BlockPos pos) {
        BlockPos bed_pos2 = null;
        for (BlockPos adjacentPos : new BlockPos[]{pos.north(), pos.south(), pos.east(), pos.west()}) {
            if (mc.theWorld.getBlockState(adjacentPos).getBlock() instanceof BlockBed) {
                bed_pos2 = adjacentPos;
                break;
            }
        }
        if (bed_pos2 == null) return false;
        return isNearbyAir(pos) || isNearbyAir(bed_pos2);
    }

    public boolean isNearbyAir(BlockPos pos) {
        for (BlockPos adjacentPos : new BlockPos[]{pos.up(), pos.south(), pos.east(), pos.west(), pos.north()}) {
            if (mc.theWorld.getBlockState(adjacentPos).getBlock() instanceof BlockAir) {
                return true;
            }
        }
        return false;
    }

    public BlockPos getNearestBlock(BlockPos pos) {
        double distance = 69;
        BlockPos lasb = null;

        for (BlockPos p : new ArrayList<>(Arrays.asList(pos.up(), pos.west(), pos.south(), pos.east(), pos.north()))) {
            if (!(mc.theWorld.getBlockState(p).getBlock() instanceof BlockBed) && mc.thePlayer.getDistance(p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5) < distance) {
                lasb = p;
                distance = mc.thePlayer.getDistance(p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5);
            }
        }
        return lasb;
    }

    public int getSlotFromBlock(Block block) {
        int slot = -1;
        float breakspeed = 0;

        for (int i = 0; i < 9; i++) {
            if (mc.thePlayer.inventory.getStackInSlot(i) != null && mc.thePlayer.inventory.getStackInSlot(i).getStrVsBlock(block) > breakspeed) {
                breakspeed = mc.thePlayer.inventory.getStackInSlot(i).getStrVsBlock(block);
                slot = i;
            }
        }
        return slot;
    }
}
