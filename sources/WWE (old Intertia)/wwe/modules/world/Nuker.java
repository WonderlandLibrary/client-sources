package wwe.modules.world;

import wwe.modules.*;
import net.minecraft.util.text.*;
import wwe.utils.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import wwe.modules.exploits.*;
import net.minecraft.util.*;
import java.awt.*;
import wwe.*;

public class Nuker extends qMIe
{
    public ArrayList<BlockPos> bGhe;
    public ArrayList<BlockPos> xqPg;
    public ModeValue mode;
    public ModeValue break;
    public BooleanValue allBlocks;
    public NumberValue left;
    public NumberValue right;
    public NumberValue forward;
    public NumberValue backward;
    public NumberValue up;
    public NumberValue down;
    public BooleanValue esp;
    public BooleanValue grasses;
    public BooleanValue hopper;
    public BooleanValue bed;
    public BooleanValue drawSelectionBox;
    public static ArrayList<Block> mTio;
    
    public Nuker() {
        super("Nuker", "Breaks blocks around you", 0, Category.WORLD);
        this.bGhe = new ArrayList<BlockPos>();
        this.xqPg = new ArrayList<BlockPos>();
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Creative");
        list.add("Survival");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Creative", list);
        final ArrayList<String> list2 = new ArrayList<String>();
        list2.add("Client");
        list2.add("Packet");
        this.break = new ModeValue("Break", this, "How to break the block", "Client", list2);
        this.allBlocks = new BooleanValue("All Blocks", this, "Target all blocks, else target nuker blocks, .nuker", true);
        this.left = new NumberValue("Left", this, "How many blocks to the left to target(relative to the player)", 2.0, 0.0, 4.0);
        this.right = new NumberValue("Right", this, "How many blocks to the right to target(relative to the player)", 2.0, 0.0, 4.0);
        this.forward = new NumberValue("Forward", this, "How many blocks to the forward to target(relative to the player)", 2.0, 0.0, 4.0);
        this.backward = new NumberValue("Backward", this, "How many blocks to the backawards to target(relative to the player)", 2.0, 0.0, 4.0);
        this.up = new NumberValue("Up", this, "How many blocks up to target(from the players pants)", 2.0, 0.0, 4.0);
        this.down = new NumberValue("Down", this, "How many blocks down to target(under the players feet)", 2.0, 0.0, 4.0);
        this.esp = new BooleanValue("ESP", this, "Draws a box around the targeted block", false);
        this.grasses = new BooleanValue("Grasses", this, "Targets grasses and flowers only if All Block is off", false);
        this.hopper = new BooleanValue("Hopper", this, "Targets hoppers & shulkers only if All Block is off", false);
        this.bed = new BooleanValue("Bed", this, "Target beds", false);
        this.drawSelectionBox = new BooleanValue("Draw Selection Box", this, "Shows all the blocks that will be targeted", false);
    }
    
    @oqrr
    public void MtRB(final omqH omqH) {
        if (this.mode.OnGi().equalsIgnoreCase("Survival")) {
            this.owgW("Nuker " + TextFormatting.DARK_GRAY + "Survival");
        }
        if (this.mode.OnGi().equalsIgnoreCase("Creative")) {
            this.owgW("Nuker " + TextFormatting.DARK_GRAY + "Creative");
        }
        this.bGhe.clear();
        this.xqPg.clear();
        final ArrayList<BlockPos> list = new ArrayList<BlockPos>();
        for (final BlockPos blockPos : BlockPos.getAllInBox(new BlockPos(Wrapper.mc.player.posX, Wrapper.mc.player.posY + this.up.OnGi().intValue(), Wrapper.mc.player.posZ).offset(Wrapper.mc.player.getHorizontalFacing(), this.forward.OnGi().intValue()).offset(Wrapper.mc.player.getHorizontalFacing().rotateYCCW(), this.left.OnGi().intValue()).offset(Wrapper.mc.player.getHorizontalFacing(), new Vec3i(0, 1, 0).getX()), new BlockPos(Wrapper.mc.player.posX, Wrapper.mc.player.posY - this.down.OnGi().intValue(), Wrapper.mc.player.posZ).offset(Wrapper.mc.player.getHorizontalFacing(), -this.backward.OnGi().intValue()).offset(Wrapper.mc.player.getHorizontalFacing().rotateY(), this.right.OnGi().intValue()).offset(Wrapper.mc.player.getHorizontalFacing(), new Vec3i(0, 0, 0).getX()))) {
            this.xqPg.add(blockPos);
            if (!(Wrapper.mc.world.getBlockState(blockPos).getBlock() instanceof BlockAir) && !Wrapper.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.BEDROCK) && !Wrapper.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.BARRIER) && !(Wrapper.mc.world.getBlockState(blockPos).getBlock() instanceof BlockLiquid)) {
                if (!this.allBlocks.OnGi()) {
                    if ((this.grasses.OnGi() && Wrapper.mc.world.getBlockState(blockPos).getBlock() instanceof BlockFlower) || Wrapper.mc.world.getBlockState(blockPos).getBlock() instanceof BlockBush) {
                        list.add(blockPos);
                    }
                    if ((this.hopper.OnGi() && Wrapper.mc.world.getBlockState(blockPos).getBlock() instanceof BlockHopper) || Wrapper.mc.world.getBlockState(blockPos).getBlock() instanceof BlockShulkerBox) {
                        list.add(blockPos);
                    }
                    if (this.bed.OnGi() && Wrapper.mc.world.getBlockState(blockPos).getBlock() instanceof BlockBed) {
                        list.add(blockPos);
                    }
                }
                if (!this.allBlocks.OnGi() && !Nuker.mTio.contains(Wrapper.mc.world.getBlockState(blockPos).getBlock())) {
                    continue;
                }
                list.add(blockPos);
            }
            if (Nuker.\u200c) {
                throw null;
            }
        }
        double distance = 100.0;
        BlockPos blockPos2 = null;
        for (final BlockPos blockPos3 : list) {
            if (blockPos3.getX() == MathHelper.floor(Wrapper.mc.player.posX) && blockPos3.getY() <= MathHelper.floor(Wrapper.mc.player.posY) && blockPos3.getZ() == MathHelper.floor(Wrapper.mc.player.posZ) && list.size() > this.down.OnGi()) {
                if (Nuker.\u200c) {
                    throw null;
                }
                continue;
            }
            else {
                Label_1304: {
                    if (Wrapper.mc.player.getDistance((double)blockPos3.getX(), (double)blockPos3.getY(), (double)blockPos3.getZ()) <= distance) {
                        distance = Wrapper.mc.player.getDistance((double)blockPos3.getX(), (double)blockPos3.getY(), (double)blockPos3.getZ());
                        if (this.allBlocks.OnGi()) {
                            if (this.mode.OnGi().equalsIgnoreCase("Creative")) {
                                this.bGhe.add(blockPos3);
                                if (Nuker.\u200c) {
                                    throw null;
                                }
                            }
                            else {
                                blockPos2 = blockPos3;
                                if (Nuker.\u200c) {
                                    throw null;
                                }
                            }
                        }
                        else if (!this.allBlocks.OnGi()) {
                            Label_1259: {
                                if (this.grasses.OnGi()) {
                                    if (Wrapper.mc.world.getBlockState(blockPos3).getBlock() instanceof BlockFlower) {
                                        break Label_1259;
                                    }
                                    if (Wrapper.mc.world.getBlockState(blockPos3).getBlock() instanceof BlockBush) {
                                        if (Nuker.\u200c) {
                                            throw null;
                                        }
                                        break Label_1259;
                                    }
                                }
                                if (this.hopper.OnGi()) {
                                    if (Wrapper.mc.world.getBlockState(blockPos3).getBlock() instanceof BlockHopper) {
                                        break Label_1259;
                                    }
                                    if (Wrapper.mc.world.getBlockState(blockPos3).getBlock() instanceof BlockShulkerBox) {
                                        if (Nuker.\u200c) {
                                            throw null;
                                        }
                                        break Label_1259;
                                    }
                                }
                                if (this.bed.OnGi() && Wrapper.mc.world.getBlockState(blockPos3).getBlock() instanceof BlockBed) {
                                    if (Nuker.\u200c) {
                                        throw null;
                                    }
                                }
                                else if (!Nuker.mTio.contains(Wrapper.mc.world.getBlockState(blockPos3).getBlock())) {
                                    break Label_1304;
                                }
                            }
                            if (this.mode.OnGi().equalsIgnoreCase("Creative")) {
                                this.bGhe.add(blockPos3);
                                if (Nuker.\u200c) {
                                    throw null;
                                }
                            }
                            else {
                                blockPos2 = blockPos3;
                            }
                        }
                    }
                }
                if (Nuker.\u200c) {
                    throw null;
                }
                continue;
            }
        }
        this.bGhe.add(blockPos2);
        if (this.bGhe.isEmpty()) {
            if (this.mode.OnGi().equalsIgnoreCase("Survival")) {
                Wrapper.mc.playerController.resetBlockRemoving();
                if (Nuker.\u200c) {
                    throw null;
                }
            }
        }
        else {
            omqH.prFd = WFux.Goyn(this.bGhe.get(0))[0];
            omqH.OjyD = WFux.Goyn(this.bGhe.get(0))[1];
        }
    }
    
    @oqrr
    public void ooHs(final ILBH ilbh) {
        if (!this.bGhe.isEmpty()) {
            if (this.mode.OnGi().equalsIgnoreCase("Survival")) {
                final EnumFacing fWlE = FIwW.FWlE(this.bGhe.get(0));
                if (this.break.OnGi().equalsIgnoreCase("Packet")) {
                    Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, (BlockPos)this.bGhe.get(0), fWlE));
                    Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, (BlockPos)this.bGhe.get(0), fWlE));
                    OffHand.LInL();
                }
                if (this.break.OnGi().equalsIgnoreCase("Client")) {
                    Wrapper.mc.playerController.onPlayerDamageBlock(new BlockPos(this.bGhe.get(0).getX(), this.bGhe.get(0).getY(), this.bGhe.get(0).getZ()), fWlE);
                    OffHand.LInL();
                }
                if (Nuker.\u200c) {
                    throw null;
                }
            }
            else if (this.mode.OnGi().equalsIgnoreCase("Creative") && !this.bGhe.isEmpty()) {
                for (final BlockPos blockPos : this.bGhe) {
                    final EnumFacing fWlE2 = FIwW.FWlE(blockPos);
                    if (this.break.OnGi().equalsIgnoreCase("Packet")) {
                        Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, fWlE2));
                        Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, fWlE2));
                        OffHand.LInL();
                    }
                    if (this.break.OnGi().equalsIgnoreCase("Client")) {
                        Wrapper.mc.playerController.onPlayerDamageBlock(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()), fWlE2);
                        OffHand.LInL();
                    }
                    if (Nuker.\u200c) {
                        throw null;
                    }
                }
            }
        }
    }
    
    @oqrr
    public void efgw(final RTIo rtIo) {
        if (this.drawSelectionBox.OnGi()) {
            BIIV.gDGd(this.xqPg.get(0), new BlockPos((Vec3i)this.xqPg.get(this.xqPg.size() - 1)).add(1, 1, 1), Color.blue);
        }
        if (!this.esp.OnGi()) {
            return;
        }
        if (this.mode.OnGi().equalsIgnoreCase("Survival") && !this.bGhe.isEmpty()) {
            BIIV.TGTO(this.bGhe.get(0), Color.RED, 1.0, 1.0);
        }
        if (this.mode.OnGi().equalsIgnoreCase("Creative") && !this.bGhe.isEmpty()) {
            final Iterator<BlockPos> iterator = this.bGhe.iterator();
            while (iterator.hasNext()) {
                BIIV.TGTO(iterator.next(), Color.RED, 1.0, 1.0);
                if (Nuker.\u200c) {
                    throw null;
                }
            }
        }
    }
    
    private BlockPos TWkw(final BlockPos blockPos, final Vec3i vec3i) {
        return blockPos.offset(Wrapper.mc.player.getHorizontalFacing(), vec3i.getX()).up(vec3i.getY());
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
    }
    
    static {
        Nuker.mTio = new ArrayList<Block>();
    }
}
