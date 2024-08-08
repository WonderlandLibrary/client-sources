package in.momin5.cookieclient.client.modules.player;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.event.events.OnUpdateWalkingPlayerEvent;
import in.momin5.cookieclient.api.event.util.Phase;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingBoolean;
import in.momin5.cookieclient.api.util.utils.misc.TimerUtil;
import in.momin5.cookieclient.api.util.utils.rotation.RotationUtils;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;

import static in.momin5.cookieclient.api.util.utils.player.InventoryUtils.isNull;

public class Scaffold extends Module {

    public SettingBoolean rotation = register(new SettingBoolean("Rotate",this,false));
    public TimerUtil timer = new TimerUtil();

    public Scaffold(){
        super("Scaffold", Category.PLAYER);
    }


    @Override
    public void onEnable(){
        MinecraftForge.EVENT_BUS.register(this);
        CookieClient.EVENT_BUS.subscribe(this);
        timer.reset();
    }

    public void onDisable(){
        MinecraftForge.EVENT_BUS.unregister(this);
        CookieClient.EVENT_BUS.unsubscribe(this);
    }

    @EventHandler
    private final Listener<OnUpdateWalkingPlayerEvent> iHaveAReallyHugePenis = new Listener<>(event -> {

        if(event.getPhase() != Phase.PRE)
            return;
        timer.setCurrentMS();
        BlockPos playerBlock = new BlockPos(
                mc.player.getRidingEntity() != null ? mc.player.getRidingEntity().posX
                        : mc.player.posX,
                mc.player.getRidingEntity() != null ? mc.player.getRidingEntity().posY
                        : mc.player.posY,
                mc.player.getRidingEntity() != null ? mc.player.getRidingEntity().posZ
                        : mc.player.posZ);

        // timer.setCurrentMS();
        if (mc.world.isAirBlock(playerBlock.add(0, -1, 0))
                || mc.world.getBlockState(playerBlock.add(0, -1, 0)).getBlock() == Blocks.SNOW_LAYER
                || mc.world.getBlockState(playerBlock.add(0, -1, 0)).getBlock() == Blocks.TALLGRASS
                || mc.world.getBlockState(playerBlock.add(0, -1, 0)).getBlock() instanceof BlockLiquid) {
            if (this.isValidBlock(playerBlock.add(0, -2, 0))) {
                this.place(playerBlock.add(0, -1, 0), EnumFacing.UP);
            } else if (this.isValidBlock(playerBlock.add(-1, -1, 0))) {
                this.place(playerBlock.add(0, -1, 0), EnumFacing.EAST);
            } else if (this.isValidBlock(playerBlock.add(1, -1, 0))) {
                this.place(playerBlock.add(0, -1, 0), EnumFacing.WEST);
            } else if (this.isValidBlock(playerBlock.add(0, -1, -1))) {
                this.place(playerBlock.add(0, -1, 0), EnumFacing.SOUTH);
            } else if (this.isValidBlock(playerBlock.add(0, -1, 1))) {
                this.place(playerBlock.add(0, -1, 0), EnumFacing.NORTH);
            } else if (this.isValidBlock(playerBlock.add(1, -1, 1))) {
                if (this.isValidBlock(playerBlock.add(0, -1, 1))) {
                    this.place(playerBlock.add(0, -1, 1), EnumFacing.NORTH);
                }
                this.place(playerBlock.add(1, -1, 1), EnumFacing.EAST);
            } else if (this.isValidBlock(playerBlock.add(-1, -1, 1))) {
                if (this.isValidBlock(playerBlock.add(-1, -1, 0))) {
                    this.place(playerBlock.add(0, -1, 1), EnumFacing.WEST);
                }
                this.place(playerBlock.add(-1, -1, 1), EnumFacing.SOUTH);
            } else if (this.isValidBlock(playerBlock.add(1, -1, 1))) {
                if (this.isValidBlock(playerBlock.add(0, -1, 1))) {
                    this.place(playerBlock.add(0, -1, 1), EnumFacing.SOUTH);
                }
                this.place(playerBlock.add(1, -1, 1), EnumFacing.WEST);
            } else if (this.isValidBlock(playerBlock.add(1, -1, 1))) {
                if (this.isValidBlock(playerBlock.add(0, -1, 1))) {
                    this.place(playerBlock.add(0, -1, 1), EnumFacing.EAST);
                }
                this.place(playerBlock.add(1, -1, 1), EnumFacing.NORTH);
            }
        }

    });

    @SuppressWarnings("deprecation")
    private boolean isValidBlock(BlockPos pos) {
        Block b = mc.world.getBlockState(pos).getBlock();
        if (!(b instanceof BlockLiquid) && b.getMaterial(null) != Material.AIR) {
            return true;
        }
        return false;
    }


    public void place(BlockPos pos, EnumFacing face) {
        if (face == EnumFacing.UP) {
            pos = pos.add(0, -1, 0);
        } else if (face == EnumFacing.NORTH) {
            pos = pos.add(0, 0, 1);
        } else if (face == EnumFacing.SOUTH) {
            pos = pos.add(0, 0, -1);
        } else if (face == EnumFacing.EAST) {
            pos = pos.add(-1, 0, 0);
        } else if (face == EnumFacing.WEST) {
            pos = pos.add(1, 0, 0);
        }
        final int oldSlot = mc.player.inventory.currentItem;
        int newSlot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (!isNull(stack) && stack.getItem() instanceof ItemBlock
                    && Block.getBlockFromItem(stack.getItem()).getDefaultState().isFullBlock()) {
                newSlot = i;
                break;
            }
        }
        if (newSlot == -1) {
            return;
        }

        if (mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock) {

        } else {
            mc.player.connection.sendPacket(new CPacketHeldItemChange(newSlot));
            mc.player.inventory.currentItem = newSlot;
            mc.playerController.updateController();
        }
        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motionX *= 0.3;
            mc.player.motionZ *= 0.3;
            mc.player.jump();
            if (timer.hasDelayRun(1500) && mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.player.motionY = -0.28;
                timer.setLastMS();
            }
            if (!mc.gameSettings.keyBindJump.isKeyDown()) {
                timer.setLastMS();
            }
        }

        RotationUtils.faceBlockPacket(pos);
        mc.playerController.processRightClickBlock(mc.player, mc.world, pos, face,
                new Vec3d(0.5, 0.5, 0.5), EnumHand.MAIN_HAND);
        mc.player.swingArm(EnumHand.MAIN_HAND);
        mc.player.connection.sendPacket(new CPacketHeldItemChange(oldSlot));
        mc.player.inventory.currentItem = oldSlot;
        mc.playerController.updateController();
        // this.delay = 0;
    }
}
