package com.client.glowclient.modules.combat;

import net.minecraft.client.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.events.*;
import net.minecraft.util.math.*;
import com.client.glowclient.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.*;
import com.client.glowclient.modules.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.multiplayer.*;

public class BedAura extends ModuleContainer
{
    public int H;
    private int f;
    private double M;
    private static O G;
    public int d;
    private boolean L;
    public Timer A;
    public static final NumberValue range;
    private static final Minecraft b;
    
    static {
        range = ValueFactory.M("BedAura", "Range", "Range to destroy bed", 5.0, 0.5, 0.0, 10.0);
        b = Minecraft.getMinecraft();
        BedAura.G = GD::M;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (!this.A.hasBeenSet()) {
            this.A.reset();
        }
        if (this.A.delay(this.M) && (BedAura.b.player.dimension == -1 || BedAura.b.player.dimension == 1)) {
            final Iterator<BlockPos> iterator2;
            Iterator<BlockPos> iterator = iterator2 = HB.M(BedAura.range.k(), (boolean)(0 != 0), BedAura.G).iterator();
            while (iterator.hasNext()) {
                final BlockPos blockPos = iterator2.next();
                final Vec3d vec3d = new Vec3d((Vec3i)blockPos);
                iterator = iterator2;
                HB.e(blockPos);
                y.M(vec3d, this);
            }
        }
        else {
            y.M(this);
        }
        this.D(-1);
        if (this.L && this.d == 3) {
            if (this.f > 0) {
                --this.f;
                return;
            }
            final ItemStack heldItem = BedAura.b.player.getHeldItem(EnumHand.MAIN_HAND);
            final NonNullList mainInventory = BedAura.b.player.inventory.mainInventory;
            if (heldItem == null || heldItem.isEmpty()) {
                y.M(this);
                int n;
                int i = n = 0;
                while (i < mainInventory.size()) {
                    final ItemStack itemStack;
                    if (n != BedAura.b.player.inventory.currentItem && !(itemStack = (ItemStack)mainInventory.get(n)).isEmpty() && itemStack.getItem() instanceof ItemBed) {
                        this.D(n);
                        return;
                    }
                    ++n;
                    this.L = false;
                    i = n;
                }
            }
        }
    }
    
    private static boolean M(final BlockPos blockPos) {
        return BedAura.b.world.getBlockState(blockPos).getBlock() instanceof BlockBed;
    }
    
    @Override
    public void D() {
    }
    
    @Override
    public void E() {
        y.M(this);
    }
    
    public BedAura() {
        final boolean l = true;
        final int h = -1;
        final int d = 3;
        final double m = 0.0;
        super(Category.COMBAT, "BedAura", false, -1, "For Nether - Explode beds");
        this.M = m;
        this.A = new Timer();
        this.d = d;
        this.H = h;
        this.L = l;
    }
    
    @Override
    public String M() {
        return String.format("%.1f", BedAura.range.k());
    }
    
    public void D(int h) {
        int n;
        if (h == -1) {
            n = (h = this.H);
        }
        else {
            final int h2 = h;
            this.d = 0;
            this.H = h2;
            n = h;
        }
        if (n == -1) {
            return;
        }
        BedAura bedAura = null;
        BedAura bedAura2 = null;
        Label_0191: {
            switch (this.d) {
                case 0: {
                    final Minecraft b = BedAura.b;
                    while (false) {}
                    b.playerController.windowClick(0, (h < 9) ? (h + 36) : h, 0, ClickType.PICKUP, (EntityPlayer)BedAura.b.player);
                    bedAura = this;
                    bedAura2 = this;
                    break Label_0191;
                }
                case 1: {
                    final PlayerControllerMP playerController = BedAura.b.playerController;
                    final int n2 = 36 + BedAura.b.player.inventory.currentItem;
                    final int n3 = 0;
                    playerController.windowClick(n3, n2, n3, ClickType.PICKUP, (EntityPlayer)BedAura.b.player);
                    bedAura = this;
                    bedAura2 = this;
                    break Label_0191;
                }
                case 2: {
                    BedAura.b.playerController.windowClick(0, (h < 9) ? (h + 36) : h, 0, ClickType.PICKUP, (EntityPlayer)BedAura.b.player);
                    this.H = -1;
                    break;
                }
            }
            bedAura = this;
            bedAura2 = this;
        }
        bedAura.d = bedAura2.d + 1;
    }
}
