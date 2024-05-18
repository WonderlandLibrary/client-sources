package wwe.modules.world;

import net.minecraft.client.renderer.*;
import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.exploits.*;
import net.minecraft.network.play.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import wwe.*;
import net.minecraft.entity.*;

public class Scaffold extends qMIe
{
    public sbfc Xijd;
    public HashMap<BlockPos, EnumFacing> hnRr;
    public ModeValue mode;
    public BooleanValue noSwing;
    public BooleanValue tower;
    public BooleanValue extend;
    public BooleanValue safeWalk;
    private Vector3d eAPT;
    
    public Scaffold() {
        super("Scaffold", "Places block under you", 0, Category.WORLD);
        this.Xijd = new sbfc();
        this.eAPT = new Vector3d();
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Normal");
        list.add("All");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "All", list);
        this.noSwing = new BooleanValue("No Swing", this, "Doesnt swing when placing block, client side", true);
        this.tower = new BooleanValue("Tower", this, "Go faster while going upwards", true);
        this.extend = new BooleanValue("Extend", this, "Place a block farther then normal", false);
        this.safeWalk = new BooleanValue("Safe Walk", this, "Makes you not fall off blocks", true);
    }
    
    @oqrr
    public void Pjsa(final omqH omqH) {
        if (this.mode.OnGi().equalsIgnoreCase("Normal")) {
            int n = -1;
            int i = 0;
            while (i < 9) {
                final ItemStack stackInSlot = Wrapper.mc.player.inventory.getStackInSlot(i);
                if (!cKMx(stackInSlot) && (stackInSlot.getItem() instanceof ItemBlock || stackInSlot.getItem() instanceof ItemSlab)) {
                    n = i;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++i;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                    continue;
                }
            }
            if (n == -1) {
                return;
            }
            this.hnRr = null;
            this.Xijd.gCgh();
            double n2;
            if (Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock && !(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemSlab)) {
                n2 = 0.0;
                if (Scaffold.\u2004\u2005\u2007) {
                    throw null;
                }
            }
            else if (Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemSlab) {
                n2 = 0.5;
                if (Scaffold.\u2004\u2005\u2007) {
                    throw null;
                }
            }
            else if (!cKMx(Wrapper.mc.player.inventory.getStackInSlot(n)) && Wrapper.mc.player.inventory.getStackInSlot(n).getItem() instanceof ItemSlab) {
                n2 = 0.5;
                if (Scaffold.\u2004\u2005\u2007) {
                    throw null;
                }
            }
            else {
                n2 = 0.0;
            }
            final double n3 = n2;
            final double cos = Math.cos(Math.toRadians(Wrapper.mc.player.rotationYaw + 90.0f));
            final double sin = Math.sin(Math.toRadians(Wrapper.mc.player.rotationYaw + 90.0f));
            final double n4 = Wrapper.mc.player.movementInput.moveForward * 1.0 * cos + Wrapper.mc.player.movementInput.moveStrafe * 1.0 * sin;
            final double n5 = Wrapper.mc.player.movementInput.moveForward * 1.0 * sin - Wrapper.mc.player.movementInput.moveStrafe * 1.0 * cos;
            double n6;
            if (Wrapper.mc.player.getRidingEntity() != null) {
                n6 = Wrapper.mc.player.getRidingEntity().posX + n4;
                if (Scaffold.\u2004\u2005\u2007) {
                    throw null;
                }
            }
            else {
                n6 = Wrapper.mc.player.posX + n4;
            }
            final double n7 = n6;
            double n8;
            if (Wrapper.mc.player.getRidingEntity() != null) {
                n8 = Wrapper.mc.player.getRidingEntity().posY + n3;
                if (Scaffold.\u2004\u2005\u2007) {
                    throw null;
                }
            }
            else {
                n8 = Wrapper.mc.player.posY + n3;
            }
            final double n9 = n8;
            double n10;
            if (Wrapper.mc.player.getRidingEntity() != null) {
                n10 = Wrapper.mc.player.getRidingEntity().posZ + n5;
                if (Scaffold.\u2004\u2005\u2007) {
                    throw null;
                }
            }
            else {
                n10 = Wrapper.mc.player.posZ + n5;
            }
            final double n11 = n10;
            final BlockPos blockPos = new BlockPos(0, 0, 0);
            BlockPos blockPos2;
            if (this.extend.OnGi()) {
                blockPos2 = new BlockPos(n7, n9, n11);
                if (Scaffold.\u2004\u2005\u2007) {
                    throw null;
                }
            }
            else {
                double n12;
                if (Wrapper.mc.player.getRidingEntity() != null) {
                    n12 = Wrapper.mc.player.getRidingEntity().posX;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                }
                else {
                    n12 = Wrapper.mc.player.posX;
                }
                double n13;
                if (Wrapper.mc.player.getRidingEntity() != null) {
                    n13 = Wrapper.mc.player.getRidingEntity().posY + n3;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                }
                else {
                    n13 = Wrapper.mc.player.posY + n3;
                }
                double n14;
                if (Wrapper.mc.player.getRidingEntity() != null) {
                    n14 = Wrapper.mc.player.getRidingEntity().posZ;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                }
                else {
                    n14 = Wrapper.mc.player.posZ;
                }
                blockPos2 = new BlockPos(n12, n13, n14);
            }
            if (Wrapper.mc.world.isAirBlock(blockPos2.add(0, -1, 0)) || Wrapper.mc.world.getBlockState(blockPos2.add(0, -1, 0)).getBlock() instanceof BlockLiquid || Wrapper.mc.world.getBlockState(blockPos2.add(0, -1, 0)).getBlock() == Blocks.SNOW_LAYER || Wrapper.mc.world.getBlockState(blockPos2.add(0, -1, 0)).getBlock() == Blocks.TALLGRASS) {
                this.hnRr = this.Fwms(blockPos2);
                if (this.hnRr != null) {
                    final Map.Entry<BlockPos, EnumFacing> entry = this.hnRr.entrySet().iterator().next();
                    BlockPos blockPos3 = entry.getKey();
                    final EnumFacing enumFacing = entry.getValue();
                    if (enumFacing == EnumFacing.UP) {
                        blockPos3 = blockPos3.add(0, -1, 0);
                        if (Scaffold.\u2004\u2005\u2007) {
                            throw null;
                        }
                    }
                    else if (enumFacing == EnumFacing.NORTH) {
                        blockPos3 = blockPos3.add(0, 0, 1);
                        if (Scaffold.\u2004\u2005\u2007) {
                            throw null;
                        }
                    }
                    else if (enumFacing == EnumFacing.SOUTH) {
                        blockPos3 = blockPos3.add(0, 0, -1);
                        if (Scaffold.\u2004\u2005\u2007) {
                            throw null;
                        }
                    }
                    else if (enumFacing == EnumFacing.EAST) {
                        blockPos3 = blockPos3.add(-1, 0, 0);
                        if (Scaffold.\u2004\u2005\u2007) {
                            throw null;
                        }
                    }
                    else if (enumFacing == EnumFacing.WEST) {
                        blockPos3 = blockPos3.add(1, 0, 0);
                    }
                    omqH.prFd = WFux.Goyn(blockPos3)[0];
                    omqH.OjyD = WFux.Goyn(blockPos3)[1];
                }
            }
        }
        if (this.mode.OnGi().equalsIgnoreCase("All")) {
            int n15 = -1;
            int j = 0;
            while (j < 9) {
                final ItemStack stackInSlot2 = Wrapper.mc.player.inventory.getStackInSlot(j);
                if (!cKMx(stackInSlot2) && (stackInSlot2.getItem() instanceof ItemBlock || stackInSlot2.getItem() instanceof ItemSlab)) {
                    n15 = j;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++j;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                    continue;
                }
            }
            if (n15 == -1) {
                return;
            }
            this.Xijd.gCgh();
            this.hnRr = null;
            double n16;
            if (Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock && !(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemSlab)) {
                n16 = 1.0;
                if (Scaffold.\u2004\u2005\u2007) {
                    throw null;
                }
            }
            else if (Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemSlab) {
                n16 = 0.5;
                if (Scaffold.\u2004\u2005\u2007) {
                    throw null;
                }
            }
            else if (!cKMx(Wrapper.mc.player.inventory.getStackInSlot(n15)) && Wrapper.mc.player.inventory.getStackInSlot(n15).getItem() instanceof ItemSlab) {
                n16 = 0.5;
                if (Scaffold.\u2004\u2005\u2007) {
                    throw null;
                }
            }
            else {
                n16 = 1.0;
            }
            final double n17 = n16;
            final double cos2 = Math.cos(Math.toRadians(Wrapper.mc.player.rotationYaw + 90.0f));
            final double sin2 = Math.sin(Math.toRadians(Wrapper.mc.player.rotationYaw + 90.0f));
            final double n18 = Wrapper.mc.player.movementInput.moveForward * 1.0 * cos2 + Wrapper.mc.player.movementInput.moveStrafe * 1.0 * sin2;
            final double n19 = Wrapper.mc.player.movementInput.moveForward * 1.0 * sin2 - Wrapper.mc.player.movementInput.moveStrafe * 1.0 * cos2;
            double n20;
            if (Wrapper.mc.player.getRidingEntity() != null) {
                n20 = Wrapper.mc.player.getRidingEntity().posX + n18;
                if (Scaffold.\u2004\u2005\u2007) {
                    throw null;
                }
            }
            else {
                n20 = Wrapper.mc.player.posX + n18;
            }
            final double n21 = n20;
            double n22;
            if (Wrapper.mc.player.getRidingEntity() != null) {
                n22 = Wrapper.mc.player.getRidingEntity().posY - n17;
                if (Scaffold.\u2004\u2005\u2007) {
                    throw null;
                }
            }
            else {
                n22 = Wrapper.mc.player.posY - n17;
            }
            final double n23 = n22;
            double n24;
            if (Wrapper.mc.player.getRidingEntity() != null) {
                n24 = Wrapper.mc.player.getRidingEntity().posZ + n19;
                if (Scaffold.\u2004\u2005\u2007) {
                    throw null;
                }
            }
            else {
                n24 = Wrapper.mc.player.posZ + n19;
            }
            final double n25 = n24;
            final BlockPos blockPos4 = new BlockPos(0, 0, 0);
            BlockPos blockPos5;
            if (this.extend.OnGi()) {
                blockPos5 = new BlockPos(n21, n23, n25);
                if (Scaffold.\u2004\u2005\u2007) {
                    throw null;
                }
            }
            else {
                double n26;
                if (Wrapper.mc.player.getRidingEntity() != null) {
                    n26 = Wrapper.mc.player.getRidingEntity().posX;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                }
                else {
                    n26 = Wrapper.mc.player.posX;
                }
                double n27;
                if (Wrapper.mc.player.getRidingEntity() != null) {
                    n27 = Wrapper.mc.player.getRidingEntity().posY - n17;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                }
                else {
                    n27 = Wrapper.mc.player.posY - n17;
                }
                double n28;
                if (Wrapper.mc.player.getRidingEntity() != null) {
                    n28 = Wrapper.mc.player.getRidingEntity().posZ;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                }
                else {
                    n28 = Wrapper.mc.player.posZ;
                }
                blockPos5 = new BlockPos(n26, n27, n28);
            }
            if (Wrapper.mc.world.isAirBlock(blockPos5) || Wrapper.mc.world.getBlockState(blockPos5).getBlock() instanceof BlockLiquid || Wrapper.mc.world.getBlockState(blockPos5).getBlock() == Blocks.SNOW_LAYER || Wrapper.mc.world.getBlockState(blockPos5).getBlock() == Blocks.TALLGRASS) {
                this.hnRr = this.NNfj(blockPos5);
                if (this.hnRr != null) {
                    final Map.Entry<BlockPos, EnumFacing> entry2 = this.hnRr.entrySet().iterator().next();
                    omqH.prFd = WFux.Goyn(entry2.getKey())[0];
                    omqH.OjyD = WFux.Goyn(entry2.getKey())[1];
                }
            }
        }
    }
    
    @oqrr
    public void plBY(final ILBH ilbh) {
        if (this.mode.OnGi().equalsIgnoreCase("Normal") && this.hnRr != null) {
            final Map.Entry<BlockPos, EnumFacing> entry = this.hnRr.entrySet().iterator().next();
            BlockPos blockPos = entry.getKey();
            if (entry.getValue() == EnumFacing.UP) {
                blockPos = blockPos.add(0, -1, 0);
                if (Scaffold.\u2004\u2005\u2007) {
                    throw null;
                }
            }
            else if (entry.getValue() == EnumFacing.NORTH) {
                blockPos = blockPos.add(0, 0, 1);
                if (Scaffold.\u2004\u2005\u2007) {
                    throw null;
                }
            }
            else if (entry.getValue() == EnumFacing.SOUTH) {
                blockPos = blockPos.add(0, 0, -1);
                if (Scaffold.\u2004\u2005\u2007) {
                    throw null;
                }
            }
            else if (entry.getValue() == EnumFacing.EAST) {
                blockPos = blockPos.add(-1, 0, 0);
                if (Scaffold.\u2004\u2005\u2007) {
                    throw null;
                }
            }
            else if (entry.getValue() == EnumFacing.WEST) {
                blockPos = blockPos.add(1, 0, 0);
            }
            final int currentItem = Wrapper.mc.player.inventory.currentItem;
            int currentItem2 = -1;
            int i = 0;
            while (i < 9) {
                final ItemStack stackInSlot = Wrapper.mc.player.inventory.getStackInSlot(i);
                if (!cKMx(stackInSlot) && (stackInSlot.getItem() instanceof ItemBlock || stackInSlot.getItem() instanceof ItemSlab)) {
                    currentItem2 = i;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++i;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                    continue;
                }
            }
            if (currentItem2 == -1) {
                return;
            }
            if (!(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock) && !(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemSlab)) {
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(currentItem2));
                Wrapper.mc.player.inventory.currentItem = currentItem2;
                Wrapper.mc.playerController.updateController();
            }
            if (yYGD.dgWY(Wrapper.mc.gameSettings.keyBindJump) && this.tower.OnGi()) {
                final EntityPlayerSP player = Wrapper.mc.player;
                player.motionX *= 0.3;
                final EntityPlayerSP player2 = Wrapper.mc.player;
                player2.motionZ *= 0.3;
                Wrapper.mc.player.jump();
                if (this.Xijd.QOXG(1500.0) && yYGD.dgWY(Wrapper.mc.gameSettings.keyBindJump)) {
                    Wrapper.mc.player.motionY = -0.28;
                    this.Xijd.BBwj();
                }
                if (!yYGD.dgWY(Wrapper.mc.gameSettings.keyBindJump)) {
                    this.Xijd.BBwj();
                }
            }
            if (Wrapper.mc.player.getRidingEntity() != null) {
                WFux.FayH(blockPos);
            }
            Wrapper.mc.playerController.processRightClickBlock(Wrapper.mc.player, Wrapper.mc.world, blockPos, (EnumFacing)entry.getValue(), new Vec3d(0.5, 0.5, 0.5), EnumHand.MAIN_HAND);
            if (this.noSwing.OnGi()) {
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketAnimation(OffHand.txLa()));
                if (Scaffold.\u2004\u2005\u2007) {
                    throw null;
                }
            }
            else {
                OffHand.LInL();
            }
            Wrapper.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(currentItem));
            Wrapper.mc.player.inventory.currentItem = currentItem;
            Wrapper.mc.playerController.updateController();
        }
        if (this.mode.OnGi().equalsIgnoreCase("All") && this.hnRr != null) {
            final Map.Entry<BlockPos, EnumFacing> entry2 = this.hnRr.entrySet().iterator().next();
            final int currentItem3 = Wrapper.mc.player.inventory.currentItem;
            int currentItem4 = -1;
            int j = 0;
            while (j < 9) {
                final ItemStack stackInSlot2 = Wrapper.mc.player.inventory.getStackInSlot(j);
                if (!cKMx(stackInSlot2) && (stackInSlot2.getItem() instanceof ItemBlock || stackInSlot2.getItem() instanceof ItemSlab)) {
                    currentItem4 = j;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++j;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                    continue;
                }
            }
            if (currentItem4 == -1) {
                return;
            }
            if (!(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock) && !(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemSlab)) {
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(currentItem4));
                Wrapper.mc.player.inventory.currentItem = currentItem4;
                Wrapper.mc.playerController.updateController();
            }
            if (yYGD.dgWY(Wrapper.mc.gameSettings.keyBindJump) && this.tower.OnGi()) {
                final EntityPlayerSP player3 = Wrapper.mc.player;
                player3.motionX *= 0.3;
                final EntityPlayerSP player4 = Wrapper.mc.player;
                player4.motionZ *= 0.3;
                Wrapper.mc.player.jump();
                if (this.Xijd.QOXG(1500.0) && yYGD.dgWY(Wrapper.mc.gameSettings.keyBindJump)) {
                    Wrapper.mc.player.motionY = -0.28;
                    this.Xijd.BBwj();
                }
                if (!yYGD.dgWY(Wrapper.mc.gameSettings.keyBindJump)) {
                    this.Xijd.BBwj();
                }
            }
            if (Wrapper.mc.player.getRidingEntity() != null) {
                WFux.FayH(entry2.getKey());
            }
            Wrapper.mc.playerController.processRightClickBlock(Wrapper.mc.player, Wrapper.mc.world, (BlockPos)entry2.getKey(), (EnumFacing)entry2.getValue(), new Vec3d((double)entry2.getKey().getX(), (double)entry2.getKey().getY(), (double)entry2.getKey().getZ()), EnumHand.MAIN_HAND);
            if (this.noSwing.OnGi()) {
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketAnimation(OffHand.txLa()));
                if (Scaffold.\u2004\u2005\u2007) {
                    throw null;
                }
            }
            else {
                OffHand.LInL();
            }
            Wrapper.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(currentItem3));
            Wrapper.mc.player.inventory.currentItem = currentItem3;
            Wrapper.mc.playerController.updateController();
        }
    }
    
    private boolean GCGW(final BlockPos blockPos) {
        final Block block = Wrapper.mc.world.getBlockState(blockPos).getBlock();
        return !(block instanceof BlockLiquid) && !(block instanceof BlockChest) && !(block instanceof BlockAnvil) && !(block instanceof BlockNote) && block.getMaterial((IBlockState)null) != Material.AIR;
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        super.qBmy();
    }
    
    public static boolean cKMx(final ItemStack itemStack) {
        boolean b;
        if (itemStack == null || itemStack.getItem() instanceof ItemAir) {
            b = true;
            if (Scaffold.\u2004\u2005\u2007) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    private HashMap<BlockPos, EnumFacing> Fwms(final BlockPos blockPos) {
        class dGZy extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos uTkC;
            final Scaffold xMCL;
            
            dGZy(final Scaffold xmcl, final BlockPos uTkC) {
                this.xMCL = xmcl;
                this.uTkC = uTkC;
                super();
                this.put(this.uTkC.add(0, -1, 0), EnumFacing.UP);
            }
        }
        class Hccw extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos PAhL;
            final Scaffold ezsQ;
            
            Hccw(final Scaffold ezsQ, final BlockPos pAhL) {
                this.ezsQ = ezsQ;
                this.PAhL = pAhL;
                super();
                this.put(this.PAhL.add(0, -1, 0), EnumFacing.EAST);
            }
        }
        class HVHa extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos PSfc;
            final Scaffold YYNW;
            
            HVHa(final Scaffold yynw, final BlockPos pSfc) {
                this.YYNW = yynw;
                this.PSfc = pSfc;
                super();
                this.put(this.PSfc.add(0, -1, 0), EnumFacing.WEST);
            }
        }
        class YHwT extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos axvC;
            final Scaffold wInt;
            
            YHwT(final Scaffold wInt, final BlockPos axvC) {
                this.wInt = wInt;
                this.axvC = axvC;
                super();
                this.put(this.axvC.add(0, -1, 0), EnumFacing.SOUTH);
            }
        }
        class gGOF extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos LwqJ;
            final Scaffold NpTt;
            
            gGOF(final Scaffold npTt, final BlockPos lwqJ) {
                this.NpTt = npTt;
                this.LwqJ = lwqJ;
                super();
                this.put(this.LwqJ.add(0, -1, 0), EnumFacing.NORTH);
            }
        }
        class BeCY extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos mALP;
            final Scaffold WdrE;
            
            BeCY(final Scaffold wdrE, final BlockPos malp) {
                this.WdrE = wdrE;
                this.mALP = malp;
                super();
                this.put(this.mALP.add(0, -1, 1), EnumFacing.NORTH);
            }
        }
        class FVrM extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos gxTU;
            final Scaffold korV;
            
            FVrM(final Scaffold korV, final BlockPos gxTU) {
                this.korV = korV;
                this.gxTU = gxTU;
                super();
                this.put(this.gxTU.add(1, -1, 1), EnumFacing.EAST);
            }
        }
        class MpJx extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos BPlk;
            final Scaffold RjUc;
            
            MpJx(final Scaffold rjUc, final BlockPos bPlk) {
                this.RjUc = rjUc;
                this.BPlk = bPlk;
                super();
                this.put(this.BPlk.add(0, -1, 1), EnumFacing.WEST);
            }
        }
        class XlUN extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos mCxS;
            final Scaffold qvJV;
            
            XlUN(final Scaffold qvJV, final BlockPos mCxS) {
                this.qvJV = qvJV;
                this.mCxS = mCxS;
                super();
                this.put(this.mCxS.add(-1, -1, 1), EnumFacing.SOUTH);
            }
        }
        class FERn extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos WFbw;
            final Scaffold Dmjx;
            
            FERn(final Scaffold dmjx, final BlockPos wFbw) {
                this.Dmjx = dmjx;
                this.WFbw = wFbw;
                super();
                this.put(this.WFbw.add(0, -1, 1), EnumFacing.SOUTH);
            }
        }
        class cTFE extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos ofgy;
            final Scaffold uXyZ;
            
            cTFE(final Scaffold uXyZ, final BlockPos ofgy) {
                this.uXyZ = uXyZ;
                this.ofgy = ofgy;
                super();
                this.put(this.ofgy.add(1, -1, 1), EnumFacing.WEST);
            }
        }
        class tKGX extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos vtTt;
            final Scaffold UviG;
            
            tKGX(final Scaffold uviG, final BlockPos vtTt) {
                this.UviG = uviG;
                this.vtTt = vtTt;
                super();
                this.put(this.vtTt.add(0, -1, 1), EnumFacing.EAST);
            }
        }
        class tuiL extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos QgAd;
            final Scaffold suRZ;
            
            tuiL(final Scaffold suRZ, final BlockPos qgAd) {
                this.suRZ = suRZ;
                this.QgAd = qgAd;
                super();
                this.put(this.QgAd.add(1, -1, 1), EnumFacing.NORTH);
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: aconst_null    
        //     4: areturn        
        //     5: aload_0        
        //     6: aload_1        
        //     7: ldc             0
        //     9: ldc_w           -2
        //    12: ldc             0
        //    14: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //    17: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //    20: ifeq            37
        //    23: new             Lwwe/dGZy;
        //    26: dup            
        //    27: aload_0        
        //    28: aload_1        
        //    29: invokespecial   wwe/dGZy.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //    32: areturn        
        //    33: nop            
        //    34: nop            
        //    35: nop            
        //    36: athrow         
        //    37: aload_0        
        //    38: aload_1        
        //    39: ldc             -1
        //    41: ldc             -1
        //    43: ldc             0
        //    45: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //    48: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //    51: ifeq            68
        //    54: new             Lwwe/Hccw;
        //    57: dup            
        //    58: aload_0        
        //    59: aload_1        
        //    60: invokespecial   wwe/Hccw.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //    63: areturn        
        //    64: nop            
        //    65: nop            
        //    66: nop            
        //    67: athrow         
        //    68: aload_0        
        //    69: aload_1        
        //    70: ldc             1
        //    72: ldc             -1
        //    74: ldc             0
        //    76: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //    79: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //    82: ifeq            99
        //    85: new             Lwwe/HVHa;
        //    88: dup            
        //    89: aload_0        
        //    90: aload_1        
        //    91: invokespecial   wwe/HVHa.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //    94: areturn        
        //    95: nop            
        //    96: nop            
        //    97: nop            
        //    98: athrow         
        //    99: aload_0        
        //   100: aload_1        
        //   101: ldc             0
        //   103: ldc             -1
        //   105: ldc             -1
        //   107: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   110: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   113: ifeq            130
        //   116: new             Lwwe/YHwT;
        //   119: dup            
        //   120: aload_0        
        //   121: aload_1        
        //   122: invokespecial   wwe/YHwT.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   125: areturn        
        //   126: nop            
        //   127: nop            
        //   128: nop            
        //   129: athrow         
        //   130: aload_0        
        //   131: aload_1        
        //   132: ldc             0
        //   134: ldc             -1
        //   136: ldc             1
        //   138: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   141: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   144: ifeq            161
        //   147: new             Lwwe/gGOF;
        //   150: dup            
        //   151: aload_0        
        //   152: aload_1        
        //   153: invokespecial   wwe/gGOF.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   156: areturn        
        //   157: nop            
        //   158: nop            
        //   159: nop            
        //   160: athrow         
        //   161: aload_0        
        //   162: aload_1        
        //   163: ldc             1
        //   165: ldc             -1
        //   167: ldc             1
        //   169: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   172: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   175: ifeq            223
        //   178: aload_0        
        //   179: aload_1        
        //   180: ldc             0
        //   182: ldc             -1
        //   184: ldc             1
        //   186: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   189: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   192: ifeq            209
        //   195: new             Lwwe/BeCY;
        //   198: dup            
        //   199: aload_0        
        //   200: aload_1        
        //   201: invokespecial   wwe/BeCY.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   204: areturn        
        //   205: nop            
        //   206: nop            
        //   207: nop            
        //   208: athrow         
        //   209: new             Lwwe/FVrM;
        //   212: dup            
        //   213: aload_0        
        //   214: aload_1        
        //   215: invokespecial   wwe/FVrM.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   218: areturn        
        //   219: nop            
        //   220: nop            
        //   221: nop            
        //   222: athrow         
        //   223: aload_0        
        //   224: aload_1        
        //   225: ldc             -1
        //   227: ldc             -1
        //   229: ldc             1
        //   231: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   234: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   237: ifeq            285
        //   240: aload_0        
        //   241: aload_1        
        //   242: ldc             -1
        //   244: ldc             -1
        //   246: ldc             0
        //   248: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   251: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   254: ifeq            271
        //   257: new             Lwwe/MpJx;
        //   260: dup            
        //   261: aload_0        
        //   262: aload_1        
        //   263: invokespecial   wwe/MpJx.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   266: areturn        
        //   267: nop            
        //   268: nop            
        //   269: nop            
        //   270: athrow         
        //   271: new             Lwwe/XlUN;
        //   274: dup            
        //   275: aload_0        
        //   276: aload_1        
        //   277: invokespecial   wwe/XlUN.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   280: areturn        
        //   281: nop            
        //   282: nop            
        //   283: nop            
        //   284: athrow         
        //   285: aload_0        
        //   286: aload_1        
        //   287: ldc             1
        //   289: ldc             -1
        //   291: ldc             1
        //   293: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   296: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   299: ifeq            347
        //   302: aload_0        
        //   303: aload_1        
        //   304: ldc             0
        //   306: ldc             -1
        //   308: ldc             1
        //   310: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   313: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   316: ifeq            333
        //   319: new             Lwwe/FERn;
        //   322: dup            
        //   323: aload_0        
        //   324: aload_1        
        //   325: invokespecial   wwe/FERn.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   328: areturn        
        //   329: nop            
        //   330: nop            
        //   331: nop            
        //   332: athrow         
        //   333: new             Lwwe/cTFE;
        //   336: dup            
        //   337: aload_0        
        //   338: aload_1        
        //   339: invokespecial   wwe/cTFE.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   342: areturn        
        //   343: nop            
        //   344: nop            
        //   345: nop            
        //   346: athrow         
        //   347: aload_0        
        //   348: aload_1        
        //   349: ldc             1
        //   351: ldc             -1
        //   353: ldc             1
        //   355: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   358: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   361: ifeq            409
        //   364: aload_0        
        //   365: aload_1        
        //   366: ldc             0
        //   368: ldc             -1
        //   370: ldc             1
        //   372: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   375: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   378: ifeq            395
        //   381: new             Lwwe/tKGX;
        //   384: dup            
        //   385: aload_0        
        //   386: aload_1        
        //   387: invokespecial   wwe/tKGX.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   390: areturn        
        //   391: nop            
        //   392: nop            
        //   393: nop            
        //   394: athrow         
        //   395: new             Lwwe/tuiL;
        //   398: dup            
        //   399: aload_0        
        //   400: aload_1        
        //   401: invokespecial   wwe/tuiL.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   404: areturn        
        //   405: nop            
        //   406: nop            
        //   407: nop            
        //   408: athrow         
        //   409: aconst_null    
        //   410: areturn        
        //   411: nop            
        //   412: nop            
        //   413: nop            
        //   414: athrow         
        //    Signature:
        //  (Lnet/minecraft/util/math/BlockPos;)Ljava/util/HashMap<Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;>;
        //    StackMapTable: 00 1D FF 00 03 00 03 07 00 7B 07 01 27 01 00 00 01 FF 00 1B 00 00 00 01 07 00 60 FE 00 03 07 00 7B 07 01 27 01 FF 00 1A 00 00 00 01 07 00 60 FE 00 03 07 00 7B 07 01 27 01 FF 00 1A 00 00 00 01 07 00 60 FE 00 03 07 00 7B 07 01 27 01 FF 00 1A 00 00 00 01 07 00 60 FE 00 03 07 00 7B 07 01 27 01 FF 00 1A 00 00 00 01 07 00 60 FE 00 03 07 00 7B 07 01 27 01 FF 00 2B 00 00 00 01 07 00 60 FE 00 03 07 00 7B 07 01 27 01 FF 00 09 00 00 00 01 07 00 60 FE 00 03 07 00 7B 07 01 27 01 FF 00 2B 00 00 00 01 07 00 60 FE 00 03 07 00 7B 07 01 27 01 FF 00 09 00 00 00 01 07 00 60 FE 00 03 07 00 7B 07 01 27 01 FF 00 2B 00 00 00 01 07 00 60 FE 00 03 07 00 7B 07 01 27 01 FF 00 09 00 00 00 01 07 00 60 FE 00 03 07 00 7B 07 01 27 01 FF 00 2B 00 00 00 01 07 00 60 FE 00 03 07 00 7B 07 01 27 01 FF 00 09 00 00 00 01 07 00 60 FE 00 03 07 00 7B 07 01 27 01 FF 00 01 00 00 00 01 07 00 60
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private HashMap<BlockPos, EnumFacing> NNfj(final BlockPos blockPos) {
        class PSdT extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos NyAD;
            final Scaffold vugz;
            
            PSdT(final Scaffold vugz, final BlockPos nyAD) {
                this.vugz = vugz;
                this.NyAD = nyAD;
                super();
                this.put(this.NyAD.add(0, 0, -1), EnumFacing.SOUTH);
            }
        }
        class rUbB extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos nmVK;
            final Scaffold Hpzy;
            
            rUbB(final Scaffold hpzy, final BlockPos nmVK) {
                this.Hpzy = hpzy;
                this.nmVK = nmVK;
                super();
                this.put(this.nmVK.add(0, -1, 0), EnumFacing.UP);
            }
        }
        class Vwmm extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos jSqo;
            final Scaffold rkHl;
            
            Vwmm(final Scaffold rkHl, final BlockPos jSqo) {
                this.rkHl = rkHl;
                this.jSqo = jSqo;
                super();
                this.put(this.jSqo.add(-1, 0, 0), EnumFacing.EAST);
            }
        }
        class BvXS extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos ATPo;
            final Scaffold iAaJ;
            
            BvXS(final Scaffold iAaJ, final BlockPos atPo) {
                this.iAaJ = iAaJ;
                this.ATPo = atPo;
                super();
                this.put(this.ATPo.add(1, 0, 0), EnumFacing.WEST);
            }
        }
        class iRHS extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos uyWw;
            final Scaffold ryBM;
            
            iRHS(final Scaffold ryBM, final BlockPos uyWw) {
                this.ryBM = ryBM;
                this.uyWw = uyWw;
                super();
                this.put(this.uyWw.add(0, 0, 1), EnumFacing.NORTH);
            }
        }
        class wWzY extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos WwMb;
            final Scaffold jBhS;
            
            wWzY(final Scaffold jBhS, final BlockPos wwMb) {
                this.jBhS = jBhS;
                this.WwMb = wwMb;
                super();
                this.put(this.WwMb.add(-1, 0, 0), EnumFacing.EAST);
            }
        }
        class BIgi extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos eJwQ;
            final Scaffold dEiQ;
            
            BIgi(final Scaffold dEiQ, final BlockPos eJwQ) {
                this.dEiQ = dEiQ;
                this.eJwQ = eJwQ;
                super();
                this.put(this.eJwQ.add(1, 0, 0), EnumFacing.WEST);
            }
        }
        class aXTR extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos DYrn;
            final Scaffold kYfs;
            
            aXTR(final Scaffold kYfs, final BlockPos dYrn) {
                this.kYfs = kYfs;
                this.DYrn = dYrn;
                super();
                this.put(this.DYrn.add(0, 0, -1), EnumFacing.SOUTH);
            }
        }
        class jLDW extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos rjYD;
            final Scaffold Zfqz;
            
            jLDW(final Scaffold zfqz, final BlockPos rjYD) {
                this.Zfqz = zfqz;
                this.rjYD = rjYD;
                super();
                this.put(this.rjYD.add(0, 0, 1), EnumFacing.NORTH);
            }
        }
        class MBau extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos NtNp;
            final Scaffold bbgG;
            
            MBau(final Scaffold bbgG, final BlockPos ntNp) {
                this.bbgG = bbgG;
                this.NtNp = ntNp;
                super();
                this.put(this.NtNp.add(-1, 0, 0), EnumFacing.EAST);
            }
        }
        class JrnP extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos EtUV;
            final Scaffold rxFU;
            
            JrnP(final Scaffold rxFU, final BlockPos etUV) {
                this.rxFU = rxFU;
                this.EtUV = etUV;
                super();
                this.put(this.EtUV.add(1, 0, 0), EnumFacing.WEST);
            }
        }
        class vkJi extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos GnDu;
            final Scaffold UkDC;
            
            vkJi(final Scaffold ukDC, final BlockPos gnDu) {
                this.UkDC = ukDC;
                this.GnDu = gnDu;
                super();
                this.put(this.GnDu.add(0, 0, -1), EnumFacing.SOUTH);
            }
        }
        class gtbS extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos WJBJ;
            final Scaffold cdLk;
            
            gtbS(final Scaffold cdLk, final BlockPos wjbj) {
                this.cdLk = cdLk;
                this.WJBJ = wjbj;
                super();
                this.put(this.WJBJ.add(0, 0, 1), EnumFacing.NORTH);
            }
        }
        class gGye extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos easy;
            final Scaffold Pdmb;
            
            gGye(final Scaffold pdmb, final BlockPos easy) {
                this.Pdmb = pdmb;
                this.easy = easy;
                super();
                this.put(this.easy.add(-1, 0, 0), EnumFacing.EAST);
            }
        }
        class qeOH extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos PEvh;
            final Scaffold iLmS;
            
            qeOH(final Scaffold iLmS, final BlockPos pEvh) {
                this.iLmS = iLmS;
                this.PEvh = pEvh;
                super();
                this.put(this.PEvh.add(1, 0, 0), EnumFacing.WEST);
            }
        }
        class lGcu extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos BwPT;
            final Scaffold ufEF;
            
            lGcu(final Scaffold ufEF, final BlockPos bwPT) {
                this.ufEF = ufEF;
                this.BwPT = bwPT;
                super();
                this.put(this.BwPT.add(0, 0, -1), EnumFacing.SOUTH);
            }
        }
        class nMOa extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos NmOn;
            final Scaffold ZVgq;
            
            nMOa(final Scaffold zVgq, final BlockPos nmOn) {
                this.ZVgq = zVgq;
                this.NmOn = nmOn;
                super();
                this.put(this.NmOn.add(0, 0, 1), EnumFacing.NORTH);
            }
        }
        class OTtY extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos VRLR;
            final Scaffold AIcd;
            
            OTtY(final Scaffold aIcd, final BlockPos vrlr) {
                this.AIcd = aIcd;
                this.VRLR = vrlr;
                super();
                this.put(this.VRLR.add(-1, 0, 0), EnumFacing.EAST);
            }
        }
        class DeKZ extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos mlUR;
            final Scaffold iCBF;
            
            DeKZ(final Scaffold icbf, final BlockPos mlUR) {
                this.iCBF = icbf;
                this.mlUR = mlUR;
                super();
                this.put(this.mlUR.add(1, 0, 0), EnumFacing.WEST);
            }
        }
        class dDeO extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos gXrF;
            final Scaffold dHVf;
            
            dDeO(final Scaffold dhVf, final BlockPos gXrF) {
                this.dHVf = dhVf;
                this.gXrF = gXrF;
                super();
                this.put(this.gXrF.add(0, 0, -1), EnumFacing.SOUTH);
            }
        }
        class IIZu extends HashMap<BlockPos, EnumFacing>
        {
            final BlockPos bgol;
            final Scaffold yCpS;
            
            IIZu(final Scaffold yCpS, final BlockPos bgol) {
                this.yCpS = yCpS;
                this.bgol = bgol;
                super();
                this.put(this.bgol.add(0, 0, 1), EnumFacing.NORTH);
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: aconst_null    
        //     4: areturn        
        //     5: aload_0        
        //     6: aload_1        
        //     7: ldc             0
        //     9: ldc             -1
        //    11: ldc             0
        //    13: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //    16: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //    19: ifeq            37
        //    22: new             Lwwe/rUbB;
        //    25: dup            
        //    26: aload_0        
        //    27: aload_1        
        //    28: invokespecial   wwe/rUbB.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //    31: areturn        
        //    32: nop            
        //    33: nop            
        //    34: nop            
        //    35: nop            
        //    36: athrow         
        //    37: aload_0        
        //    38: aload_1        
        //    39: ldc             -1
        //    41: ldc             0
        //    43: ldc             0
        //    45: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //    48: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //    51: ifeq            69
        //    54: new             Lwwe/Vwmm;
        //    57: dup            
        //    58: aload_0        
        //    59: aload_1        
        //    60: invokespecial   wwe/Vwmm.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //    63: areturn        
        //    64: nop            
        //    65: nop            
        //    66: nop            
        //    67: nop            
        //    68: athrow         
        //    69: aload_0        
        //    70: aload_1        
        //    71: ldc             1
        //    73: ldc             0
        //    75: ldc             0
        //    77: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //    80: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //    83: ifeq            101
        //    86: new             Lwwe/BvXS;
        //    89: dup            
        //    90: aload_0        
        //    91: aload_1        
        //    92: invokespecial   wwe/BvXS.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //    95: areturn        
        //    96: nop            
        //    97: nop            
        //    98: nop            
        //    99: nop            
        //   100: athrow         
        //   101: aload_0        
        //   102: aload_1        
        //   103: ldc             0
        //   105: ldc             0
        //   107: ldc             -1
        //   109: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   112: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   115: ifeq            133
        //   118: new             Lwwe/PSdT;
        //   121: dup            
        //   122: aload_0        
        //   123: aload_1        
        //   124: invokespecial   wwe/PSdT.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   127: areturn        
        //   128: nop            
        //   129: nop            
        //   130: nop            
        //   131: nop            
        //   132: athrow         
        //   133: aload_0        
        //   134: aload_1        
        //   135: ldc             0
        //   137: ldc             0
        //   139: ldc             1
        //   141: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   144: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   147: ifeq            165
        //   150: new             Lwwe/iRHS;
        //   153: dup            
        //   154: aload_0        
        //   155: aload_1        
        //   156: invokespecial   wwe/iRHS.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   159: areturn        
        //   160: nop            
        //   161: nop            
        //   162: nop            
        //   163: nop            
        //   164: athrow         
        //   165: aload_1        
        //   166: ldc             -1
        //   168: ldc             0
        //   170: ldc             0
        //   172: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   175: astore_2       
        //   176: aload_0        
        //   177: aload_2        
        //   178: ldc             -1
        //   180: ldc             0
        //   182: ldc             0
        //   184: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   187: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   190: ifeq            208
        //   193: new             Lwwe/wWzY;
        //   196: dup            
        //   197: aload_0        
        //   198: aload_2        
        //   199: invokespecial   wwe/wWzY.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   202: areturn        
        //   203: nop            
        //   204: nop            
        //   205: nop            
        //   206: nop            
        //   207: athrow         
        //   208: aload_0        
        //   209: aload_2        
        //   210: ldc             1
        //   212: ldc             0
        //   214: ldc             0
        //   216: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   219: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   222: ifeq            240
        //   225: new             Lwwe/BIgi;
        //   228: dup            
        //   229: aload_0        
        //   230: aload_2        
        //   231: invokespecial   wwe/BIgi.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   234: areturn        
        //   235: nop            
        //   236: nop            
        //   237: nop            
        //   238: nop            
        //   239: athrow         
        //   240: aload_0        
        //   241: aload_2        
        //   242: ldc             0
        //   244: ldc             0
        //   246: ldc             -1
        //   248: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   251: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   254: ifeq            272
        //   257: new             Lwwe/aXTR;
        //   260: dup            
        //   261: aload_0        
        //   262: aload_2        
        //   263: invokespecial   wwe/aXTR.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   266: areturn        
        //   267: nop            
        //   268: nop            
        //   269: nop            
        //   270: nop            
        //   271: athrow         
        //   272: aload_0        
        //   273: aload_2        
        //   274: ldc             0
        //   276: ldc             0
        //   278: ldc             1
        //   280: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   283: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   286: ifeq            304
        //   289: new             Lwwe/jLDW;
        //   292: dup            
        //   293: aload_0        
        //   294: aload_2        
        //   295: invokespecial   wwe/jLDW.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   298: areturn        
        //   299: nop            
        //   300: nop            
        //   301: nop            
        //   302: nop            
        //   303: athrow         
        //   304: aload_1        
        //   305: ldc             1
        //   307: ldc             0
        //   309: ldc             0
        //   311: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   314: astore_3       
        //   315: aload_0        
        //   316: aload_3        
        //   317: ldc             -1
        //   319: ldc             0
        //   321: ldc             0
        //   323: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   326: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   329: ifeq            347
        //   332: new             Lwwe/MBau;
        //   335: dup            
        //   336: aload_0        
        //   337: aload_3        
        //   338: invokespecial   wwe/MBau.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   341: areturn        
        //   342: nop            
        //   343: nop            
        //   344: nop            
        //   345: nop            
        //   346: athrow         
        //   347: aload_0        
        //   348: aload_3        
        //   349: ldc             1
        //   351: ldc             0
        //   353: ldc             0
        //   355: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   358: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   361: ifeq            379
        //   364: new             Lwwe/JrnP;
        //   367: dup            
        //   368: aload_0        
        //   369: aload_3        
        //   370: invokespecial   wwe/JrnP.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   373: areturn        
        //   374: nop            
        //   375: nop            
        //   376: nop            
        //   377: nop            
        //   378: athrow         
        //   379: aload_0        
        //   380: aload_3        
        //   381: ldc             0
        //   383: ldc             0
        //   385: ldc             -1
        //   387: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   390: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   393: ifeq            411
        //   396: new             Lwwe/vkJi;
        //   399: dup            
        //   400: aload_0        
        //   401: aload_3        
        //   402: invokespecial   wwe/vkJi.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   405: areturn        
        //   406: nop            
        //   407: nop            
        //   408: nop            
        //   409: nop            
        //   410: athrow         
        //   411: aload_0        
        //   412: aload_3        
        //   413: ldc             0
        //   415: ldc             0
        //   417: ldc             1
        //   419: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   422: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   425: ifeq            443
        //   428: new             Lwwe/gtbS;
        //   431: dup            
        //   432: aload_0        
        //   433: aload_3        
        //   434: invokespecial   wwe/gtbS.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   437: areturn        
        //   438: nop            
        //   439: nop            
        //   440: nop            
        //   441: nop            
        //   442: athrow         
        //   443: aload_1        
        //   444: ldc             0
        //   446: ldc             0
        //   448: ldc             -1
        //   450: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   453: astore          4
        //   455: aload_0        
        //   456: aload           4
        //   458: ldc             -1
        //   460: ldc             0
        //   462: ldc             0
        //   464: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   467: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   470: ifeq            489
        //   473: new             Lwwe/gGye;
        //   476: dup            
        //   477: aload_0        
        //   478: aload           4
        //   480: invokespecial   wwe/gGye.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   483: areturn        
        //   484: nop            
        //   485: nop            
        //   486: nop            
        //   487: nop            
        //   488: athrow         
        //   489: aload_0        
        //   490: aload           4
        //   492: ldc             1
        //   494: ldc             0
        //   496: ldc             0
        //   498: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   501: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   504: ifeq            523
        //   507: new             Lwwe/qeOH;
        //   510: dup            
        //   511: aload_0        
        //   512: aload           4
        //   514: invokespecial   wwe/qeOH.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   517: areturn        
        //   518: nop            
        //   519: nop            
        //   520: nop            
        //   521: nop            
        //   522: athrow         
        //   523: aload_0        
        //   524: aload           4
        //   526: ldc             0
        //   528: ldc             0
        //   530: ldc             -1
        //   532: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   535: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   538: ifeq            557
        //   541: new             Lwwe/lGcu;
        //   544: dup            
        //   545: aload_0        
        //   546: aload           4
        //   548: invokespecial   wwe/lGcu.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   551: areturn        
        //   552: nop            
        //   553: nop            
        //   554: nop            
        //   555: nop            
        //   556: athrow         
        //   557: aload_0        
        //   558: aload           4
        //   560: ldc             0
        //   562: ldc             0
        //   564: ldc             1
        //   566: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   569: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   572: ifeq            591
        //   575: new             Lwwe/nMOa;
        //   578: dup            
        //   579: aload_0        
        //   580: aload           4
        //   582: invokespecial   wwe/nMOa.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   585: areturn        
        //   586: nop            
        //   587: nop            
        //   588: nop            
        //   589: nop            
        //   590: athrow         
        //   591: aload_1        
        //   592: ldc             0
        //   594: ldc             0
        //   596: ldc             1
        //   598: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   601: astore          5
        //   603: aload_0        
        //   604: aload           5
        //   606: ldc             -1
        //   608: ldc             0
        //   610: ldc             0
        //   612: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   615: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   618: ifeq            637
        //   621: new             Lwwe/OTtY;
        //   624: dup            
        //   625: aload_0        
        //   626: aload           5
        //   628: invokespecial   wwe/OTtY.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   631: areturn        
        //   632: nop            
        //   633: nop            
        //   634: nop            
        //   635: nop            
        //   636: athrow         
        //   637: aload_0        
        //   638: aload           5
        //   640: ldc             1
        //   642: ldc             0
        //   644: ldc             0
        //   646: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   649: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   652: ifeq            671
        //   655: new             Lwwe/DeKZ;
        //   658: dup            
        //   659: aload_0        
        //   660: aload           5
        //   662: invokespecial   wwe/DeKZ.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   665: areturn        
        //   666: nop            
        //   667: nop            
        //   668: nop            
        //   669: nop            
        //   670: athrow         
        //   671: aload_0        
        //   672: aload           5
        //   674: ldc             0
        //   676: ldc             0
        //   678: ldc             -1
        //   680: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   683: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   686: ifeq            705
        //   689: new             Lwwe/dDeO;
        //   692: dup            
        //   693: aload_0        
        //   694: aload           5
        //   696: invokespecial   wwe/dDeO.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   699: areturn        
        //   700: nop            
        //   701: nop            
        //   702: nop            
        //   703: nop            
        //   704: athrow         
        //   705: aload_0        
        //   706: aload           5
        //   708: ldc             0
        //   710: ldc             0
        //   712: ldc             1
        //   714: invokevirtual   net/minecraft/util/math/BlockPos.add:(III)Lnet/minecraft/util/math/BlockPos;
        //   717: invokespecial   wwe/modules/world/Scaffold.GCGW:(Lnet/minecraft/util/math/BlockPos;)Z
        //   720: ifeq            739
        //   723: new             Lwwe/IIZu;
        //   726: dup            
        //   727: aload_0        
        //   728: aload           5
        //   730: invokespecial   wwe/IIZu.<init>:(Lwwe/modules/world/Scaffold;Lnet/minecraft/util/math/BlockPos;)V
        //   733: areturn        
        //   734: nop            
        //   735: nop            
        //   736: nop            
        //   737: nop            
        //   738: athrow         
        //   739: aconst_null    
        //   740: areturn        
        //   741: nop            
        //   742: nop            
        //   743: nop            
        //   744: nop            
        //   745: athrow         
        //    Signature:
        //  (Lnet/minecraft/util/math/BlockPos;)Ljava/util/HashMap<Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;>;
        //    StackMapTable: 00 2D FF 00 03 00 07 07 00 7B 07 01 27 00 00 00 00 01 00 00 01 FF 00 1A 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 00 00 00 00 01 00 00 FF 00 1A 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 00 00 00 00 01 00 00 FF 00 1A 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 00 00 00 00 01 00 00 FF 00 1A 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 00 00 00 00 01 00 00 FF 00 1A 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 00 00 00 00 01 00 00 FF 00 25 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 07 01 27 00 00 00 01 00 00 FF 00 1A 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 07 01 27 00 00 00 01 00 00 FF 00 1A 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 07 01 27 00 00 00 01 00 00 FF 00 1A 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 07 01 27 00 00 00 01 00 00 FF 00 25 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 07 01 27 07 01 27 00 00 01 00 00 FF 00 1A 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 07 01 27 07 01 27 00 00 01 00 00 FF 00 1A 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 07 01 27 07 01 27 00 00 01 00 00 FF 00 1A 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 07 01 27 07 01 27 00 00 01 00 00 FF 00 28 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 07 01 27 07 01 27 07 01 27 00 01 00 00 FF 00 1C 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 07 01 27 07 01 27 07 01 27 00 01 00 00 FF 00 1C 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 07 01 27 07 01 27 07 01 27 00 01 00 00 FF 00 1C 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 07 01 27 07 01 27 07 01 27 00 01 00 00 FF 00 28 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 07 01 27 07 01 27 07 01 27 07 01 27 01 00 00 FF 00 1C 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 07 01 27 07 01 27 07 01 27 07 01 27 01 00 00 FF 00 1C 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 07 01 27 07 01 27 07 01 27 07 01 27 01 00 00 FF 00 1C 00 00 00 01 07 00 60 FF 00 04 00 07 07 00 7B 07 01 27 07 01 27 07 01 27 07 01 27 07 01 27 01 00 00 FF 00 01 00 00 00 01 07 00 60
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @oqrr
    public void QIFV(final sFUx sfUx) {
        double vsNw = sfUx.VsNw;
        final double xNxu = sfUx.XNxu;
        double lcNE = sfUx.lcNE;
        if (Wrapper.mc.player.onGround) {
            final double n = 0.05;
            while (vsNw != 0.0 && this.TypB(vsNw, -1.0, 0.0)) {
                if (vsNw < n && vsNw >= -n) {
                    vsNw = 0.0;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                    continue;
                }
                else if (vsNw > 0.0) {
                    vsNw -= n;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                    continue;
                }
                else {
                    vsNw += n;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                    continue;
                }
            }
            while (lcNE != 0.0 && this.TypB(0.0, -1.0, lcNE)) {
                if (lcNE < n && lcNE >= -n) {
                    lcNE = 0.0;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                    continue;
                }
                else if (lcNE > 0.0) {
                    lcNE -= n;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                    continue;
                }
                else {
                    lcNE += n;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                    continue;
                }
            }
            while (vsNw != 0.0 && lcNE != 0.0 && this.TypB(vsNw, -1.0, lcNE)) {
                if (vsNw < n && vsNw >= -n) {
                    vsNw = 0.0;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                }
                else if (vsNw > 0.0) {
                    vsNw -= n;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                }
                else {
                    vsNw += n;
                }
                if (lcNE < n && lcNE >= -n) {
                    lcNE = 0.0;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                    continue;
                }
                else if (lcNE > 0.0) {
                    lcNE -= n;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                    continue;
                }
                else {
                    lcNE += n;
                    if (Scaffold.\u2004\u2005\u2007) {
                        throw null;
                    }
                    continue;
                }
            }
        }
        sfUx.VsNw = vsNw;
        sfUx.XNxu = xNxu;
        sfUx.lcNE = lcNE;
    }
    
    public boolean TypB(final double x, final double y, final double z) {
        this.eAPT.x = x;
        this.eAPT.y = y;
        this.eAPT.z = z;
        return Wrapper.mc.world.getCollisionBoxes((Entity)Wrapper.mc.player, Wrapper.mc.player.getEntityBoundingBox().offset(this.eAPT.x, this.eAPT.y, this.eAPT.z)).isEmpty();
    }
}
