package com.krazzzzymonkey.catalyst.module.modules.player;

import com.krazzzzymonkey.catalyst.value.Value;
import com.krazzzzymonkey.catalyst.value.Mode;
import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.util.EnumHand;
import com.krazzzzymonkey.catalyst.utils.RobotUtils;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumFacing;
import com.krazzzzymonkey.catalyst.utils.visual.RenderUtils;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.item.ItemStack;
import com.krazzzzymonkey.catalyst.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.entity.Entity;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import com.krazzzzymonkey.catalyst.utils.TimerUtils;
import com.krazzzzymonkey.catalyst.utils.BlockData;
import net.minecraft.util.math.BlockPos;
import com.krazzzzymonkey.catalyst.value.ModeValue;
import com.krazzzzymonkey.catalyst.module.Modules;

public class Scaffold extends Modules
{
    public ModeValue mode;
    BlockPos blockDown;
    public TimerUtils timer;
    float startYaw;
    boolean isBridging;
    float startPitch;
    public static float[] facingCam;

    
    @SideOnly(Side.CLIENT)
    @Override
    public void onCameraSetup(final EntityViewRenderEvent.CameraSetup cameraSetup) {
        if ((this.mode.getMode("AAC").isToggled()) && (cameraSetup.getEntity() == Wrapper.INSTANCE.player())) {
            if ((fcmpl(this.startYaw, 0.0f)) || !(fcmpl(this.startPitch, 0.0f))) {
                return;
            }
            cameraSetup.setYaw(this.startYaw);
            cameraSetup.setPitch(this.startPitch - 70.0f);
            final float[] facingCam = new float[2];
            facingCam[0] = this.startYaw - 180.0f;
            facingCam[1] = this.startPitch - 70.0f;
            Scaffold.facingCam = facingCam;
        }
        super.onCameraSetup(cameraSetup);
    }

    private static int lIIIllII(final float n, final float n2) {
        return fcmpl(n, n2);
    }
    
    void Simple() {
        this.blockDown = new BlockPos((Entity)Wrapper.INSTANCE.player()).down();
        if (!(Wrapper.INSTANCE.world().getBlockState(this.blockDown).getBlock().getMaterial(Wrapper.INSTANCE.world().getBlockState(this.blockDown).getBlock().getDefaultState()).isReplaceable())) {
            return;
        }
        int num1 = -1;
        int i = 0;
        while (i < 9) {
            final ItemStack itemStack = Wrapper.INSTANCE.player().inventory.getStackInSlot(i);
            if ((itemStack != null) && (itemStack.getItem() instanceof ItemBlock) && (Block.getBlockFromItem(itemStack.getItem()).getDefaultState().getBlock().isFullBlock(Wrapper.INSTANCE.world().getBlockState(this.blockDown).getBlock().getDefaultState()))) {
                num1 = i;
                break;
            }
            else {
                ++i;
                continue;
            }
        }
        if (num1 == -1) {
            return;
        }
        final int currentItem = Wrapper.INSTANCE.player().inventory.currentItem;
        Wrapper.INSTANCE.player().inventory.currentItem = num1;
        Utils.placeBlockScaffold(this.blockDown);
        Wrapper.INSTANCE.player().inventory.currentItem = currentItem;
    }

    
    boolean check() {
        final RayTraceResult raytraceResult = Wrapper.INSTANCE.mc().objectMouseOver;
        final EntityPlayerSP entityPlayerSP = Wrapper.INSTANCE.player();
        final ItemStack itemStack = entityPlayerSP.inventory.getCurrentItem();
        if ((raytraceResult == null) || (itemStack == null)) {
            return false;
        }
        if (raytraceResult.typeOfHit != RayTraceResult.Type.BLOCK) {
            return false;
        }
        if (!(fcmpg(entityPlayerSP.rotationPitch, 70.0f) > 0) || !(entityPlayerSP.onGround) || (entityPlayerSP.isOnLadder()) || (entityPlayerSP.isInLava()) || (entityPlayerSP.isInWater())) {
            return false;
        }
        if (!(Wrapper.INSTANCE.mcSettings().keyBindBack.isKeyDown())) {
            return false;
        }
        return true;
    }
    
    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        if (this.blockDown != null) {
            RenderUtils.drawBlockESP(this.blockDown, 1.0f, 1.0f, 1.0f);
            if (this.mode.getMode("AAC").isToggled()) {
                BlockPos blockpos1 = new BlockPos((Entity)Wrapper.INSTANCE.player()).down();
                BlockPos blockpos2 = new BlockPos((Entity)Wrapper.INSTANCE.player()).down();
                if (Wrapper.INSTANCE.player().getHorizontalFacing() == EnumFacing.EAST) {
                    blockpos1 = new BlockPos((Entity)Wrapper.INSTANCE.player()).down().west();
                    blockpos2 = new BlockPos((Entity)Wrapper.INSTANCE.player()).down().west(2);
                }
                else if (Wrapper.INSTANCE.player().getHorizontalFacing() == EnumFacing.NORTH) {
                    blockpos1 = new BlockPos((Entity)Wrapper.INSTANCE.player()).down().south();
                    blockpos2 = new BlockPos((Entity)Wrapper.INSTANCE.player()).down().south(2);
                }
                else if (Wrapper.INSTANCE.player().getHorizontalFacing() == EnumFacing.SOUTH) {
                    blockpos1 = new BlockPos((Entity)Wrapper.INSTANCE.player()).down().north();
                    blockpos2 = new BlockPos((Entity)Wrapper.INSTANCE.player()).down().north(2);

                }
                else if (Wrapper.INSTANCE.player().getHorizontalFacing() == EnumFacing.WEST) {
                    blockpos1 = new BlockPos((Entity)Wrapper.INSTANCE.player()).down().east();
                    blockpos2 = new BlockPos((Entity)Wrapper.INSTANCE.player()).down().east(2);
                }
                RenderUtils.drawBlockESP(blockpos1, 1.0f, 0.0f, 0.0f);
                RenderUtils.drawBlockESP(blockpos2, 1.0f, 0.0f, 0.0f);
            }
        }
        super.onRenderWorldLast(event);
    }

    
    static {
        Scaffold.facingCam = null;
    }
    

    
    @Override
    public void onDisable() {
        Scaffold.facingCam = null;
        super.onDisable();
    }
    
    @Override
    public void onEnable() {
        this.blockDown = null;
        Scaffold.facingCam = null;
        this.isBridging = (0 != 0);
        this.startYaw = 0.0f;
        this.startPitch = 0.0f;
        if ((this.mode.getMode("AAC").isToggled()) && (Wrapper.INSTANCE.mcSettings().keyBindBack.isKeyDown())) {
            KeyBinding.setKeyBindState(Wrapper.INSTANCE.mcSettings().keyBindBack.getKeyCode(), false);
        }
        super.onEnable();
    }

    
    void AAC() {
        final EntityPlayerSP entityPlayerSP = Wrapper.INSTANCE.player();
        int num2 = -1;
        if (!(this.check())) {
            if (this.isBridging) {
                KeyBinding.setKeyBindState(Wrapper.INSTANCE.mcSettings().keyBindSneak.getKeyCode(), Utils.isBlockMaterial(new BlockPos((Entity)entityPlayerSP).down(), Blocks.AIR));
                this.isBridging = false;
                if (num2 != -1) {
                    entityPlayerSP.inventory.currentItem = num2;
                }
            }
            this.startYaw = 0.0f;
            this.startPitch = 0.0f;
            Scaffold.facingCam = null;
            this.blockDown = null;
            return;
        }
        this.startYaw = Wrapper.INSTANCE.player().rotationYaw;
        this.startPitch = Wrapper.INSTANCE.player().rotationPitch;
        KeyBinding.setKeyBindState(Wrapper.INSTANCE.mcSettings().keyBindRight.getKeyCode(), false);
        KeyBinding.setKeyBindState(Wrapper.INSTANCE.mcSettings().keyBindLeft.getKeyCode(), false);
        this.blockDown = new BlockPos((Entity)entityPlayerSP).down();
        float randomnum = new Random().nextFloat();
        if ((fcmpl(randomnum, 1.0f)) == 0) {
            --randomnum;
        }
        int int1 = -1;
        int int2 = 0;
        while (int2 < 9) {
            final ItemStack itemStack = entityPlayerSP.inventory.getStackInSlot(int2);
            if ((itemStack != null) && ((itemStack.getItem() instanceof ItemBlock)) && (Block.getBlockFromItem(itemStack.getItem()).getDefaultState().getBlock().isFullBlock(Wrapper.INSTANCE.world().getBlockState(this.blockDown).getBlock().getDefaultState()))) {
                int1 = int2;
                break;
            }
            else {
                ++int2;
                continue;
            }
        }
        if ((int1 == -1)) {
            return;
        }
        num2 = entityPlayerSP.inventory.currentItem;
        entityPlayerSP.inventory.currentItem = int1;
        entityPlayerSP.rotationPitch = Utils.updateRotation(entityPlayerSP.rotationPitch, 82.0f - randomnum, 15.0f);
        final int int3 = Utils.random(3, 4);
        if (this.timer.isDelay(1000 / int3)) {
            RobotUtils.clickMouse(1);
            Wrapper.INSTANCE.player().swingArm(EnumHand.MAIN_HAND);
            this.timer.setLastMS();
        }
        this.isBridging = true;
        KeyBinding.setKeyBindState(Wrapper.INSTANCE.mcSettings().keyBindSneak.getKeyCode(), Utils.isBlockMaterial(new BlockPos((Entity)entityPlayerSP).down(), Blocks.AIR));
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mode.getMode("AAC").isToggled()) {
            this.AAC();
        }
        else if (this.mode.getMode("Simple").isToggled()) {
            this.Simple();
        }
        super.onClientTick(event);
    }
    
    public Scaffold() {
        super("Scaffold", ModuleCategory.PLAYER);
        this.isBridging = (0 != 0);
        this.blockDown = null;
        this.startYaw = 0.0f;
        this.startPitch = 0.0f;
        final String mode = "Mode";
        final Mode[] setting = new Mode[2];
        setting[0] = new Mode("AAC", (boolean)(0 != 0));
        setting[1] = new Mode("Simple", (boolean)(1 != 0));
        this.mode = new ModeValue(mode, setting);
        final Value[] value = new Value[1];
        value[0] = this.mode;
        this.addValue(value);
        this.timer = new TimerUtils();
    }

}
