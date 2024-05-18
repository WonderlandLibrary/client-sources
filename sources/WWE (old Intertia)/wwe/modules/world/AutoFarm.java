package wwe.modules.world;

import wwe.modules.*;
import java.util.*;
import net.minecraft.util.text.*;
import wwe.utils.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import javax.vecmath.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import wwe.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;
import net.minecraft.network.play.client.*;

public class AutoFarm extends qMIe
{
    public int mLMb;
    public BlockPos hEIf;
    public sbfc Xijd;
    public ModeValue mode;
    public NumberValue delay;
    public NumberValue radius;
    
    public AutoFarm() {
        super("Auto Farm", "Automatically farms for you", 0, Category.WORLD);
        this.Xijd = new sbfc();
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Packet");
        list.add("Legit");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Packet", list);
        this.delay = new NumberValue("Delay", this, "Delay that the task is done, seconds", 0.3, 0.0, 1.0);
        this.radius = new NumberValue("Radius", this, "Radius of how far tasks will be done", 4.0, 1.0, 6.0);
    }
    
    @oqrr
    public void MtRB(final omqH omqH) {
        if (this.mode.OnGi().equalsIgnoreCase("Packet")) {
            this.owgW("Auto Farm " + TextFormatting.DARK_GRAY + "Packet");
        }
        if (this.mode.OnGi().equalsIgnoreCase("Legit")) {
            this.owgW("Auto Farm " + TextFormatting.DARK_GRAY + "Legit");
        }
        if (Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemHoe || Wrapper.mc.player.getHeldItemMainhand().getItem().equals(Items.REEDS)) {
            this.mLMb = 1;
        }
        if (Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemSeeds || Wrapper.mc.player.getHeldItemMainhand().getItem().equals(Items.CARROT) || Wrapper.mc.player.getHeldItemMainhand().getItem().equals(Items.POTATO) || Wrapper.mc.player.getHeldItemMainhand().getItem().equals(Items.BEETROOT_SEEDS)) {
            this.mLMb = 2;
        }
        if (Wrapper.mc.player.getHeldItemMainhand().getItem().equals(Items.DYE) && Wrapper.mc.player.getHeldItemMainhand().getMetadata() == 15) {
            this.mLMb = 3;
        }
        if (!(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemHoe) && !Wrapper.mc.player.getHeldItemMainhand().getItem().equals(Items.REEDS) && !(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemSeeds) && !Wrapper.mc.player.getHeldItemMainhand().getItem().equals(Items.CARROT) && !Wrapper.mc.player.getHeldItemMainhand().getItem().equals(Items.POTATO) && !Wrapper.mc.player.getHeldItemMainhand().getItem().equals(Items.BEETROOT_SEEDS) && !Wrapper.mc.player.getHeldItemMainhand().getItem().equals(Items.NETHER_WART) && (!Wrapper.mc.player.getHeldItemMainhand().getItem().equals(Items.DYE) || Wrapper.mc.player.getHeldItemMainhand().getMetadata() != 15)) {
            this.mLMb = 4;
        }
        if (Wrapper.mc.player.getHeldItemMainhand().getItem().equals(Items.NETHER_WART)) {
            this.mLMb = 5;
        }
        if (this.mLMb == 1) {
            final Vector3f msMk = this.mSMk(this.radius.OnGi(), (Block)Blocks.GRASS, Blocks.DIRT);
            if (msMk != null) {
                this.Xijd.gCgh();
                if (this.Xijd.QOXG(this.delay.OnGi() * 1000.0)) {
                    if (this.mode.OnGi().equalsIgnoreCase("Packet")) {
                        omqH.prFd = this.YiAl((int)msMk.getX() + 0.5f, (int)msMk.getY(), (int)msMk.getZ() + 0.5f)[0];
                        omqH.OjyD = this.YiAl((int)msMk.getX() + 0.5f, (int)msMk.getY(), (int)msMk.getZ() + 0.5f)[1];
                    }
                    if (this.mode.OnGi().equalsIgnoreCase("Legit")) {
                        tmGI((int)msMk.x + 0.5f, (int)msMk.y, (int)msMk.z + 0.5f);
                    }
                    this.hEIf = new BlockPos((int)msMk.x, (int)msMk.y - 1, (int)msMk.z);
                }
            }
        }
        if (this.mLMb == 2) {
            final Vector3f hlLd = this.HlLd(this.radius.OnGi(), Blocks.FARMLAND);
            if (hlLd != null) {
                this.Xijd.gCgh();
                if (this.Xijd.QOXG(this.delay.OnGi() * 1000.0)) {
                    if (this.mode.OnGi().equalsIgnoreCase("Packet")) {
                        omqH.prFd = this.YiAl((int)hlLd.getX() + 0.5f, (int)hlLd.getY(), (int)hlLd.getZ() + 0.5f)[0];
                        omqH.OjyD = this.YiAl((int)hlLd.getX() + 0.5f, (int)hlLd.getY(), (int)hlLd.getZ() + 0.5f)[1];
                    }
                    if (this.mode.OnGi().equalsIgnoreCase("Legit")) {
                        tmGI((int)hlLd.x + 0.5f, (int)hlLd.y, (int)hlLd.z + 0.5f);
                    }
                    this.hEIf = new BlockPos((int)hlLd.getX(), (int)hlLd.getY() - 1, (int)hlLd.getZ());
                }
            }
        }
        if (this.mLMb == 3) {
            final Vector3f wiDk = this.WiDk(this.radius.OnGi());
            if (wiDk != null) {
                this.Xijd.gCgh();
                if (this.Xijd.QOXG(this.delay.OnGi() * 1000.0)) {
                    if (this.mode.OnGi().equalsIgnoreCase("Packet")) {
                        omqH.prFd = this.YiAl((int)wiDk.getX() + 0.5f, (int)wiDk.getY(), (int)wiDk.getZ() + 0.5f)[0];
                        omqH.OjyD = this.YiAl((int)wiDk.getX() + 0.5f, (int)wiDk.getY(), (int)wiDk.getZ() + 0.5f)[1];
                    }
                    if (this.mode.OnGi().equalsIgnoreCase("Legit")) {
                        tmGI((int)wiDk.x + 0.5f, (int)wiDk.y, (int)wiDk.z + 0.5f);
                    }
                    this.hEIf = new BlockPos((int)wiDk.getX(), (int)wiDk.getY() - 1, (int)wiDk.getZ());
                }
            }
        }
        if (this.mLMb == 4) {
            final Vector3f zfAq = this.ZfAq(this.radius.OnGi());
            if (zfAq != null) {
                this.Xijd.gCgh();
                if (this.Xijd.QOXG(this.delay.OnGi() * 1000.0)) {
                    if (this.mode.OnGi().equalsIgnoreCase("Packet")) {
                        omqH.prFd = this.YiAl((int)zfAq.getX() + 0.5f, (int)zfAq.getY(), (int)zfAq.getZ() + 0.5f)[0];
                        omqH.OjyD = this.YiAl((int)zfAq.getX() + 0.5f, (int)zfAq.getY(), (int)zfAq.getZ() + 0.5f)[1];
                    }
                    if (this.mode.OnGi().equalsIgnoreCase("Legit")) {
                        tmGI((int)zfAq.x + 0.5f, (int)zfAq.y, (int)zfAq.z + 0.5f);
                    }
                    this.hEIf = new BlockPos((int)zfAq.getX(), (int)zfAq.getY() - 1, (int)zfAq.getZ());
                }
            }
        }
        if (this.mLMb == 5) {
            final Vector3f hlLd2 = this.HlLd(this.radius.OnGi(), Blocks.SOUL_SAND);
            if (hlLd2 != null) {
                this.Xijd.gCgh();
                if (this.Xijd.QOXG(this.delay.OnGi() * 1000.0)) {
                    if (this.mode.OnGi().equalsIgnoreCase("Packet")) {
                        omqH.prFd = this.YiAl((int)hlLd2.getX() + 0.5f, (int)hlLd2.getY(), (int)hlLd2.getZ() + 0.5f)[0];
                        omqH.OjyD = this.YiAl((int)hlLd2.getX() + 0.5f, (int)hlLd2.getY(), (int)hlLd2.getZ() + 0.5f)[1];
                    }
                    if (this.mode.OnGi().equalsIgnoreCase("Legit")) {
                        tmGI((int)hlLd2.x + 0.5f, (int)hlLd2.y, (int)hlLd2.z + 0.5f);
                    }
                    this.hEIf = new BlockPos((int)hlLd2.getX(), (int)hlLd2.getY() - 1, (int)hlLd2.getZ());
                }
            }
        }
    }
    
    @oqrr
    public void ooHs(final ILBH ilbh) {
        if (this.hEIf != null && this.Xijd.QOXG(this.delay.OnGi() * 1000.0)) {
            if (this.mLMb == 1 || this.mLMb == 2 || this.mLMb == 3 || this.mLMb == 5) {
                LKsL.LInL();
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.hEIf, EnumFacing.UP, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                this.Xijd.BBwj();
            }
            if (this.mLMb == 4) {
                Wrapper.mc.playerController.clickBlock(this.hEIf, EnumFacing.UP);
                LKsL.LInL();
                this.Xijd.BBwj();
            }
        }
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
    }
    
    public Vector3f HlLd(final double n, final Block block) {
        Vector3f vector3f = null;
        double n2 = n;
        double n3 = n;
        while (n3 >= -n) {
            double n4 = -n;
            while (n4 <= n) {
                double n5 = n;
                while (n5 >= -n) {
                    final int n6 = (int)(Wrapper.mc.player.posX + n3);
                    final int n7 = (int)(Wrapper.mc.player.posY + n4);
                    final int n8 = (int)(Wrapper.mc.player.posZ + n5);
                    final float n9 = (float)Wrapper.mc.player.getDistance((double)n6, (double)n7, (double)n8);
                    if (Wrapper.mc.world.getBlockState(new BlockPos(n6, n7 - 1, n8)).getBlock() == block && Wrapper.mc.world.getBlockState(new BlockPos(n6, n7, n8)).getBlock() instanceof BlockAir && n9 <= n2) {
                        n2 = n9;
                        vector3f = new Vector3f((float)n6, (float)n7, (float)n8);
                    }
                    --n5;
                    if (AutoFarm.\u200e\u2004\u2000) {
                        throw null;
                    }
                }
                ++n4;
                if (AutoFarm.\u200e\u2004\u2000) {
                    throw null;
                }
            }
            --n3;
            if (AutoFarm.\u200e\u2004\u2000) {
                throw null;
            }
        }
        return vector3f;
    }
    
    public Vector3f mSMk(final double n, final Block... array) {
        Vector3f vector3f = null;
        double n2 = n;
        double n3 = n;
        while (n3 >= -n) {
            double n4 = -n;
            while (n4 <= n) {
                double n5 = n;
                while (n5 >= -n) {
                    final int n6 = (int)(Wrapper.mc.player.posX + n3);
                    final int n7 = (int)(Wrapper.mc.player.posY + n4);
                    final int n8 = (int)(Wrapper.mc.player.posZ + n5);
                    final float n9 = (float)Wrapper.mc.player.getDistance((double)n6, (double)n7, (double)n8);
                    if ((Wrapper.mc.world.getBlockState(new BlockPos(n6, n7 - 1, n8)).getBlock() == array[0] || Wrapper.mc.world.getBlockState(new BlockPos(n6, n7 - 1, n8)).getBlock() == array[1]) && Wrapper.mc.world.getBlockState(new BlockPos(n6, n7, n8)).getBlock() instanceof BlockAir && n9 <= n2) {
                        n2 = n9;
                        vector3f = new Vector3f((float)n6, (float)n7, (float)n8);
                    }
                    --n5;
                    if (AutoFarm.\u200e\u2004\u2000) {
                        throw null;
                    }
                }
                ++n4;
                if (AutoFarm.\u200e\u2004\u2000) {
                    throw null;
                }
            }
            --n3;
            if (AutoFarm.\u200e\u2004\u2000) {
                throw null;
            }
        }
        return vector3f;
    }
    
    public Vector3f ZfAq(final double n) {
        Vector3f vector3f = null;
        double n2 = n;
        double n3 = n;
        while (n3 >= -n) {
            double n4 = -n;
            while (n4 <= n) {
                double n5 = n;
                while (n5 >= -n) {
                    final int n6 = (int)(Wrapper.mc.player.posX + n3);
                    final int n7 = (int)(Wrapper.mc.player.posY + n4);
                    final int n8 = (int)(Wrapper.mc.player.posZ + n5);
                    final float n9 = (float)Wrapper.mc.player.getDistance((double)n6, (double)n7, (double)n8);
                    final IBlockState blockState = Wrapper.mc.world.getBlockState(new BlockPos(n6, n7 - 1, n8));
                    if (this.Esib(blockState)) {
                        final BlockCrops blockCrops = (BlockCrops)blockState.getBlock();
                        if (blockCrops.getMetaFromState(blockState) >= blockCrops.getMaxAge() && n9 <= n2) {
                            n2 = n9;
                            vector3f = new Vector3f((float)n6, (float)n7, (float)n8);
                        }
                        if (AutoFarm.\u200e\u2004\u2000) {
                            throw null;
                        }
                    }
                    else if (blockState.getBlock() instanceof BlockReed) {
                        if (Wrapper.mc.world.getBlockState(new BlockPos(n6, n7 + 1, n8)).getBlock() instanceof BlockReed && !(Wrapper.mc.world.getBlockState(new BlockPos(n6, n7 - 2, n8)).getBlock() instanceof BlockReed)) {
                            n2 = n9;
                            vector3f = new Vector3f((float)n6, n7 + 1.0f, (float)n8);
                            if (AutoFarm.\u200e\u2004\u2000) {
                                throw null;
                            }
                        }
                    }
                    else if (blockState.getBlock() instanceof BlockNetherWart) {
                        final IBlockState blockState2 = blockState;
                        final BlockNetherWart blockNetherWart = (BlockNetherWart)blockState.getBlock();
                        if ((int)blockState2.getValue((IProperty)BlockNetherWart.AGE) >= 3) {
                            n2 = n9;
                            vector3f = new Vector3f((float)n6, (float)n7, (float)n8);
                        }
                    }
                    --n5;
                    if (AutoFarm.\u200e\u2004\u2000) {
                        throw null;
                    }
                }
                ++n4;
                if (AutoFarm.\u200e\u2004\u2000) {
                    throw null;
                }
            }
            --n3;
            if (AutoFarm.\u200e\u2004\u2000) {
                throw null;
            }
        }
        return vector3f;
    }
    
    public Vector3f WiDk(final double n) {
        Vector3f vector3f = null;
        double n2 = n;
        double n3 = n;
        while (n3 >= -n) {
            double n4 = -n;
            while (n4 <= n) {
                double n5 = n;
                while (n5 >= -n) {
                    final int n6 = (int)(Wrapper.mc.player.posX + n3);
                    final int n7 = (int)(Wrapper.mc.player.posY + n4);
                    final int n8 = (int)(Wrapper.mc.player.posZ + n5);
                    final double distance = Wrapper.mc.player.getDistance((double)n6, (double)n7, (double)n8);
                    final IBlockState blockState = Wrapper.mc.world.getBlockState(new BlockPos(n6, n7 - 1, n8));
                    if (this.Esib(blockState)) {
                        final BlockCrops blockCrops = (BlockCrops)blockState.getBlock();
                        if (blockCrops.getMetaFromState(blockState) < blockCrops.getMaxAge() && distance <= n2) {
                            n2 = distance;
                            vector3f = new Vector3f((float)n6, (float)n7, (float)n8);
                        }
                    }
                    --n5;
                    if (AutoFarm.\u200e\u2004\u2000) {
                        throw null;
                    }
                }
                ++n4;
                if (AutoFarm.\u200e\u2004\u2000) {
                    throw null;
                }
            }
            --n3;
            if (AutoFarm.\u200e\u2004\u2000) {
                throw null;
            }
        }
        return vector3f;
    }
    
    public static void tmGI(final double n, final double n2, final double n3) {
        final double n4 = n - Wrapper.mc.player.posX;
        final double n5 = n3 - Wrapper.mc.player.posZ;
        final double n6 = n2 - (Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight());
        final double n7 = MathHelper.sqrt(n4 * n4 + n5 * n5);
        final float n8 = (float)(Math.atan2(n5, n4) * 180.0 / 3.141592653589793) - 90.0f;
        final float n9 = (float)(-(Math.atan2(n6, n7) * 180.0 / 3.141592653589793));
        final EntityPlayerSP player = Wrapper.mc.player;
        player.rotationPitch += MathHelper.wrapDegrees(n9 - Wrapper.mc.player.rotationPitch);
        final EntityPlayerSP player2 = Wrapper.mc.player;
        player2.rotationYaw += MathHelper.wrapDegrees(n8 - Wrapper.mc.player.rotationYaw);
    }
    
    public static void MInv(final double n, final double n2, final double n3) {
        final double n4 = n - Wrapper.mc.player.posX;
        final double n5 = n3 - Wrapper.mc.player.posZ;
        Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(Wrapper.mc.player.posX, Wrapper.mc.player.posY, Wrapper.mc.player.posZ, MathHelper.wrapDegrees((float)(Math.atan2(n5, n4) * 180.0 / 3.141592653589793) - 90.0f - Wrapper.mc.player.rotationYaw), MathHelper.wrapDegrees((float)(-(Math.atan2(n2 - (Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight()), MathHelper.sqrt(n4 * n4 + n5 * n5)) * 180.0 / 3.141592653589793)) - Wrapper.mc.player.rotationPitch), Wrapper.mc.player.onGround));
    }
    
    public float[] YiAl(final double n, final double n2, final double n3) {
        final double n4 = n - Wrapper.mc.player.posX;
        final double n5 = n3 - Wrapper.mc.player.posZ;
        final double n6 = n2 - (Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight());
        final double n7 = MathHelper.sqrt(n4 * n4 + n5 * n5);
        final float n8 = (float)(Math.atan2(n5, n4) * 180.0 / 3.141592653589793) - 90.0f;
        final float n9 = (float)(-(Math.atan2(n6, n7) * 180.0 / 3.141592653589793));
        final EntityPlayerSP player = Wrapper.mc.player;
        player.rotationYawHead += MathHelper.wrapDegrees(n8 - Wrapper.mc.player.rotationYawHead);
        return new float[] { Wrapper.mc.player.rotationYaw + MathHelper.wrapDegrees(n8 - Wrapper.mc.player.rotationYaw), Wrapper.mc.player.rotationPitch + MathHelper.wrapDegrees(n9 - Wrapper.mc.player.rotationPitch) };
    }
    
    private boolean Esib(final IBlockState blockState) {
        boolean b;
        if (blockState.getBlock() != Blocks.AIR && blockState.getBlock() instanceof BlockCrops) {
            b = true;
            if (AutoFarm.\u200e\u2004\u2000) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
}
