package com.client.glowclient;

import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.entity.*;
import com.client.glowclient.modules.server.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.fluids.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.inventory.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.util.*;

public class wA
{
    public final Minecraft G;
    public boolean d;
    public boolean L;
    public HashMap<BlockPos, Integer> A;
    public XA B;
    public static final wA b;
    
    public void M(final XA b) {
        this.L = false;
        this.B = b;
        this.M();
    }
    
    public boolean M(final WorldClient worldClient, final EntityPlayerSP entityPlayerSP) {
        final double n = eb.l.A - this.B.L.b;
        final double n2 = eb.l.b - this.B.L.A;
        final double n3 = eb.l.L - this.B.L.B;
        final int n4 = (int)Math.floor(n);
        final int n5 = (int)Math.floor(n2);
        final int n6 = (int)Math.floor(n3);
        final int m = SchematicaPrinter.range.M();
        final int max = Math.max(0, n4 - m);
        final int min = Math.min(this.B.getWidth() - 1, n4 + m);
        final int max2 = Math.max(0, n5 - m);
        int n7 = Math.min(this.B.getHeight() - 1, n5 + m);
        final int max3 = Math.max(0, n6 - m);
        final int min2 = Math.min(this.B.getLength() - 1, n6 + m);
        if (max > min || max2 > n7 || max3 > min2) {
            y.M(ModuleManager.M("SchematicaPrinter"));
            return false;
        }
        final int currentItem = entityPlayerSP.inventory.currentItem;
        final boolean sneaking = entityPlayerSP.isSneaking();
        switch (gc.b[this.B.A.ordinal()]) {
            case 2: {
                while (false) {}
                if (this.B.b > n7) {
                    return false;
                }
                final int b = this.B.b;
            }
            case 3: {
                if (this.B.b < max2) {
                    return false;
                }
                n7 = this.B.b;
                break;
            }
        }
        this.M(entityPlayerSP, true);
        final double pow = Math.pow(SchematicaPrinter.range.k(), 2.0);
        final Iterator<wc> iterator = LB.D(max, max2, max3, min, n7, min2).iterator();
    Label_0364:
        while (true) {
            Iterator<wc> iterator2 = iterator;
            while (iterator2.hasNext()) {
                final wc wc;
                if ((wc = iterator.next()).distanceSqToCenter(n, n2, n3) <= pow) {
                    try {
                        if (this.M(worldClient, entityPlayerSP, wc)) {
                            return this.M(entityPlayerSP, currentItem, sneaking, true);
                        }
                        continue Label_0364;
                    }
                    catch (Exception ex) {
                        ld.H.error("Could not place block!", (Throwable)ex);
                        return this.M(entityPlayerSP, currentItem, sneaking, false);
                    }
                    break;
                }
                iterator2 = iterator;
            }
            break;
        }
        return this.M(entityPlayerSP, currentItem, sneaking, true);
    }
    
    public boolean A() {
        return this.d;
    }
    
    public boolean D() {
        return this.L = (!this.L && this.B != null);
    }
    
    public boolean M() {
        return this.L;
    }
    
    public boolean M(final InventoryPlayer inventoryPlayer, final ItemStack itemStack, final boolean b) {
        final int m = this.M(inventoryPlayer, itemStack);
        if (this.G.playerController.isInCreativeMode() && (m < 0 || m >= 9) && SC.J.size() > 0) {
            final boolean b2 = true;
            inventoryPlayer.setInventorySlotContents(inventoryPlayer.currentItem = this.M(), itemStack.copy());
            this.G.playerController.sendSlotPacket(inventoryPlayer.getStackInSlot(inventoryPlayer.currentItem), 36 + inventoryPlayer.currentItem);
            return b2;
        }
        if (m >= 0 && m < 9) {
            final boolean b3 = true;
            inventoryPlayer.currentItem = m;
            return b3;
        }
        return b && m >= 9 && m < 36 && this.M(inventoryPlayer, m) && this.M(inventoryPlayer, itemStack, false);
    }
    
    public void D(final boolean l) {
        this.L = l;
    }
    
    public void M(final boolean d) {
        this.d = d;
    }
    
    public wA() {
        final XA b = null;
        final boolean l = false;
        final boolean d = true;
        super();
        this.G = Minecraft.getMinecraft();
        this.d = d;
        this.L = l;
        this.B = b;
        this.A = new HashMap<BlockPos, Integer>();
    }
    
    public boolean M(final EntityPlayerSP entityPlayerSP, final BlockPos blockPos, final int n) {
        final ItemStack heldItem = entityPlayerSP.getHeldItem(EnumHand.MAIN_HAND);
        if (!this.G.playerController.isInCreativeMode() && !heldItem.isEmpty() && heldItem.getCount() <= n) {
            return false;
        }
        boolean b;
        if (!SchematicaPrinter.silent.M()) {
            y.M(ModuleManager.M("SchematicaPrinter"));
            b = fa.M(blockPos, true);
        }
        else {
            b = fa.M(blockPos, true, ModuleManager.M("SchematicaPrinter"));
        }
        int n2 = 0;
        boolean b2 = b;
        while (b2 && n2 < n) {
            if (!SchematicaPrinter.silent.M()) {
                y.M(ModuleManager.M("SchematicaPrinter"));
                b = fa.M(blockPos, true);
            }
            else {
                b = fa.M(blockPos, true, ModuleManager.M("SchematicaPrinter"));
            }
            ++n2;
            b2 = b;
        }
        if (heldItem.getCount() == 0 && b) {
            entityPlayerSP.inventory.mainInventory.set(entityPlayerSP.inventory.currentItem, (Object)ItemStack.EMPTY);
        }
        return b;
    }
    
    static {
        b = new wA();
    }
    
    public int M() {
        final int n = SC.J.poll() % 9;
        SC.J.offer(n);
        return n;
    }
    
    public boolean M(final WorldClient worldClient, final EntityPlayerSP entityPlayerSP, final BlockPos blockPos, final IBlockState blockState, final ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemBucket) {
            return false;
        }
        final PB m;
        if ((m = FB.A.M(blockState, itemStack)) != null && !m.M(blockState, (EntityPlayer)entityPlayerSP, blockPos, (World)worldClient)) {
            y.M(ModuleManager.M("SchematicaPrinter"));
            return false;
        }
        if (this.M((World)worldClient, blockPos).size() == 0) {
            return false;
        }
        wA wa;
        int i;
        if (m != null) {
            wa = this;
            i = m.M(blockState);
        }
        else {
            i = 0;
            wa = this;
        }
        if (!wa.M(entityPlayerSP.inventory, itemStack)) {
            y.M(ModuleManager.M("SchematicaPrinter"));
            return false;
        }
        return this.M(entityPlayerSP, blockPos, i);
    }
    
    public boolean M(final World world, BlockPos offset, final EnumFacing enumFacing) {
        offset = offset.offset(enumFacing);
        final IBlockState blockState;
        final Block block;
        return (block = (blockState = world.getBlockState(offset)).getBlock()) != null && !block.isAir(blockState, (IBlockAccess)world, offset) && !(block instanceof BlockFluidBase) && !block.isReplaceable((IBlockAccess)world, offset);
    }
    
    public boolean M(final InventoryPlayer inventoryPlayer, final int n) {
        if (SC.J.size() > 0) {
            this.M(n, this.M());
            return true;
        }
        return false;
    }
    
    public boolean M(final WorldClient worldClient, final EntityPlayerSP entityPlayerSP, final BlockPos blockPos) {
        final BlockPos blockPos2 = new BlockPos(this.B.L.b + blockPos.getX(), this.B.L.A + blockPos.getY(), this.B.L.B + blockPos.getZ());
        final IBlockState blockState = this.B.getBlockState(blockPos);
        final IBlockState blockState2;
        final Block block = (blockState2 = worldClient.getBlockState(blockPos2)).getBlock();
        if (Ib.M(blockState, blockState2)) {
            final gA m;
            if ((m = gC.b.M(block)) != null) {
                Integer value;
                if ((value = this.A.get(blockPos2)) == null) {
                    value = 0;
                }
                else if (value >= 10) {}
                ld.H.trace("Trying to sync block at {} {}", (Object)blockPos2, (Object)value);
                final boolean i;
                if (i = m.M((EntityPlayer)entityPlayerSP, (World)this.B, blockPos, (World)worldClient, blockPos2)) {
                    this.A.put(blockPos2, value + 1);
                }
                return i;
            }
            y.M(ModuleManager.M("SchematicaPrinter"));
            return false;
        }
        else {
            if (SC.S && !worldClient.isAirBlock(blockPos2) && this.G.playerController.isInCreativeMode()) {
                this.G.playerController.clickBlock(blockPos2, EnumFacing.DOWN);
                return !SC.ca;
            }
            if (this.B.isAirBlock(blockPos)) {
                y.M(ModuleManager.M("SchematicaPrinter"));
                return false;
            }
            if (!SchematicaPrinter.rotationalBlocks.M() && fa.M(blockPos, (World)this.B)) {
                return false;
            }
            if (!block.isReplaceable((IBlockAccess)worldClient, blockPos2)) {
                y.M(ModuleManager.M("SchematicaPrinter"));
                return false;
            }
            final ItemStack j;
            if ((j = EA.M(blockState, new RayTraceResult((Entity)entityPlayerSP), this.B, blockPos, (EntityPlayer)entityPlayerSP)).isEmpty()) {
                ld.H.debug("{} is missing a mapping!", (Object)blockState);
                y.M(ModuleManager.M("SchematicaPrinter"));
                return false;
            }
            return this.M(worldClient, entityPlayerSP, blockPos2, blockState, j) && !SC.A;
        }
    }
    
    public boolean M(final int n, final int n2) {
        return this.G.playerController.windowClick(this.G.player.inventoryContainer.windowId, n, n2, ClickType.SWAP, (EntityPlayer)this.G.player) == ItemStack.EMPTY;
    }
    
    public int M(final InventoryPlayer inventoryPlayer, final ItemStack itemStack) {
        int n;
        int i = n = 0;
        while (i < inventoryPlayer.mainInventory.size()) {
            if (((ItemStack)inventoryPlayer.mainInventory.get(n)).isItemEqual(itemStack)) {
                return n;
            }
            i = ++n;
        }
        return -1;
    }
    
    public boolean M(final EntityPlayerSP entityPlayerSP, final int currentItem, final boolean b, final boolean b2) {
        entityPlayerSP.inventory.currentItem = currentItem;
        this.M(entityPlayerSP, b);
        return b2;
    }
    
    public boolean M(final InventoryPlayer inventoryPlayer, final ItemStack itemStack) {
        return this.M(inventoryPlayer, itemStack, true);
    }
    
    public void M(final EntityPlayerSP entityPlayerSP, final boolean sneaking) {
        entityPlayerSP.setSneaking(sneaking);
        entityPlayerSP.connection.sendPacket((Packet)new CPacketEntityAction((Entity)entityPlayerSP, sneaking ? CPacketEntityAction$Action.START_SNEAKING : CPacketEntityAction$Action.STOP_SNEAKING));
    }
    
    public List<EnumFacing> M(final World world, final BlockPos blockPos) {
        if (!SC.Ka) {
            return Arrays.asList(EnumFacing.VALUES);
        }
        final ArrayList<EnumFacing> list = new ArrayList<EnumFacing>();
        final EnumFacing[] values;
        final int length = (values = EnumFacing.VALUES).length;
        int n;
        int i = n = 0;
        while (i < length) {
            final EnumFacing enumFacing = values[n];
            if (this.M(world, blockPos, enumFacing)) {
                list.add(enumFacing);
            }
            i = ++n;
        }
        return list;
    }
    
    public XA M() {
        return this.B;
    }
    
    public void M() {
        this.A.clear();
    }
}
