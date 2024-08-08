package net.futureclient.client.modules.miscellaneous;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.client.gui.inventory.GuiChest;
import net.futureclient.client.modules.miscellaneous.chestaura.Listener2;
import net.futureclient.client.modules.miscellaneous.chestaura.Listener1;
import net.futureclient.client.n;
import java.util.ArrayList;
import net.futureclient.client.Category;
import net.futureclient.client.mh;
import net.minecraft.client.Minecraft;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.utils.Value;
import net.minecraft.util.math.BlockPos;
import java.util.List;
import net.futureclient.client.Ea;

public class ChestAura extends Ea
{
    private int A;
    private List<BlockPos> j;
    private boolean K;
    private BlockPos M;
    private Value<Boolean> onground;
    private NumberValue a;
    private int D;
    private NumberValue k;
    
    public static Minecraft getMinecraft() {
        return ChestAura.D;
    }
    
    public ChestAura() {
        super("ChestAura", new String[] { mh.M("\u001d(;3*\u0001+2?"), "CA" }, true, -357009, Category.MISCELLANEOUS);
        this.k = new NumberValue(4.0f, 1.0f, 6.0f, 1.273197475E-314, new String[] { mh.M("\f!0';"), "Reach", mh.M("\u0012?.9") });
        this.onground = new Value<Boolean>(true, new String[] { "Onground", mh.M("\u000f0\u0007,/+.:\u000f0,'"), "NeedsOnGround" });
        this.j = new ArrayList<BlockPos>();
        this.a = new NumberValue(1.0f, 0.0f, 100.0f, 1, new String[] { mh.M("\u00031/2$170"), "CD", mh.M("\u001a%2!'") });
        final int n = 3;
        this.K = false;
        final Value[] array = new Value[n];
        array[0] = this.k;
        array[1] = this.onground;
        array[2] = this.a;
        this.M(array);
        this.M(new n[] { new Listener1(this), new Listener2(this) });
    }
    
    public void B() {
        this.j.clear();
        super.B();
    }
    
    public static Minecraft getMinecraft1() {
        return ChestAura.D;
    }
    
    private void C() {
        if (this.A > 0) {
            --this.A;
            return;
        }
        if (ChestAura.D.currentScreen instanceof GuiChest) {
            final boolean b = false;
            int n;
            int i = n = ChestAura.D.player.openContainer.inventorySlots.size() - 36;
            while (true) {
                while (i < ChestAura.D.player.openContainer.inventorySlots.size()) {
                    if (!ChestAura.D.player.openContainer.getSlot(n).getHasStack()) {
                        final boolean b2 = true;
                        if (!b2) {
                            return;
                        }
                    Label_0116:
                        while (this.A == 0) {
                            final boolean b3 = false;
                            int j = 0;
                            int n2 = 0;
                            while (true) {
                                while (j < ChestAura.D.player.openContainer.inventorySlots.size() - 36) {
                                    if (ChestAura.D.player.openContainer.getSlot(n2).getHasStack()) {
                                        ChestAura.D.playerController.windowClick(ChestAura.D.player.openContainer.windowId, n2, 0, ClickType.QUICK_MOVE, (EntityPlayer)ChestAura.D.player);
                                        final boolean b4 = true;
                                        this.A = this.a.B().intValue();
                                        final boolean b5 = b4;
                                        if (!b5) {
                                            ChestAura.D.displayGuiScreen((GuiScreen)null);
                                            ChestAura.D.player.connection.sendPacket((Packet)new CPacketCloseWindow(ChestAura.D.player.openContainer.windowId));
                                            this.K = true;
                                            return;
                                        }
                                        final boolean b6 = false;
                                        int n3;
                                        int k = n3 = ChestAura.D.player.openContainer.inventorySlots.size() - 36;
                                        while (true) {
                                            while (k < ChestAura.D.player.openContainer.inventorySlots.size()) {
                                                if (!ChestAura.D.player.openContainer.getSlot(n3).getHasStack()) {
                                                    final boolean b7 = true;
                                                    if (!b7) {
                                                        return;
                                                    }
                                                    continue Label_0116;
                                                }
                                                else {
                                                    k = ++n3;
                                                }
                                            }
                                            final boolean b7 = b6;
                                            continue;
                                        }
                                    }
                                    else {
                                        j = ++n2;
                                    }
                                }
                                final boolean b5 = b3;
                                continue;
                            }
                        }
                        return;
                    }
                    else {
                        i = ++n;
                    }
                }
                final boolean b2 = b;
                continue;
            }
        }
    }
    
    public static Minecraft getMinecraft2() {
        return ChestAura.D;
    }
    
    public static Minecraft getMinecraft3() {
        return ChestAura.D;
    }
    
    public static Minecraft getMinecraft4() {
        return ChestAura.D;
    }
    
    public static Minecraft getMinecraft5() {
        return ChestAura.D;
    }
    
    public static Minecraft getMinecraft6() {
        return ChestAura.D;
    }
    
    public static Minecraft getMinecraft7() {
        return ChestAura.D;
    }
    
    public static Minecraft getMinecraft8() {
        return ChestAura.D;
    }
    
    public static Minecraft getMinecraft9() {
        return ChestAura.D;
    }
    
    public static int e(final ChestAura chestAura) {
        return --chestAura.D;
    }
    
    public static Minecraft getMinecraft10() {
        return ChestAura.D;
    }
    
    public static Minecraft getMinecraft11() {
        return ChestAura.D;
    }
    
    public static Minecraft getMinecraft12() {
        return ChestAura.D;
    }
    
    public static NumberValue M(final ChestAura chestAura) {
        return chestAura.k;
    }
    
    public static int M(final ChestAura chestAura, final int d) {
        return chestAura.D = d;
    }
    
    public static void M(final ChestAura chestAura) {
        chestAura.C();
    }
    
    public static boolean M(final ChestAura chestAura, final boolean k) {
        return chestAura.K = k;
    }
    
    public static Minecraft getMinecraft13() {
        return ChestAura.D;
    }
    
    public static List M(final ChestAura chestAura) {
        return chestAura.j;
    }
    
    public static BlockPos M(final ChestAura chestAura, final BlockPos m) {
        return chestAura.M = m;
    }
    
    public static boolean M(final ChestAura chestAura) {
        return chestAura.K;
    }
    
    public static BlockPos M(final ChestAura chestAura) {
        return chestAura.M;
    }
    
    public static Value M(final ChestAura chestAura) {
        return chestAura.onground;
    }
    
    public static int M(final ChestAura chestAura) {
        return chestAura.D;
    }
    
    public static Minecraft getMinecraft14() {
        return ChestAura.D;
    }
}
