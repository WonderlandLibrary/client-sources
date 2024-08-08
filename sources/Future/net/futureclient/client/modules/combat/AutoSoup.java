package net.futureclient.client.modules.combat;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemAir;
import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.combat.autosoup.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.utils.Value;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Ea;

public class AutoSoup extends Ea
{
    private NumberValue health;
    private Value<Boolean> 19Fix;
    private Value<Boolean> dropsoup;
    private Timer a;
    private Timer D;
    public boolean k;
    
    public AutoSoup() {
        super("AutoSoup", new String[] { "AutoSoup", "autosp" }, true, -4598640, Category.COMBAT);
        this.a = new Timer();
        this.D = new Timer();
        this.dropsoup = new Value<Boolean>(false, new String[] { "Dropsoup", "drop", "throw" });
        this.19Fix = new Value<Boolean>(true, new String[] { "1.9Fix", "handfix", "1.10handfix", "fix" });
        this.health = new NumberValue(14.0f, 1.0f, 20.0f, 0.0, new String[] { "Health", "h", "hp" });
        final int n = 3;
        this.k = false;
        final Value[] array = new Value[n];
        array[0] = this.dropsoup;
        array[1] = this.health;
        array[2] = this.19Fix;
        this.M(array);
        this.M(new n[] { new Listener1(this) });
    }
    
    public static Minecraft getMinecraft() {
        return AutoSoup.D;
    }
    
    private void C() {
        if (this.a.e(125L) && AutoSoup.D.player.isEntityAlive()) {
            final int currentItem = AutoSoup.D.player.inventory.currentItem;
            int i = 44;
            int n = 44;
            while (i >= 9) {
                final ItemStack stack;
                if (!((stack = AutoSoup.D.player.inventoryContainer.getSlot(n).getStack()).getItem() instanceof ItemAir) && stack.getItem() instanceof ItemSoup) {
                    if (n >= 36 && n <= 44) {
                        AutoSoup.D.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n - 36));
                        if (this.19Fix.M()) {
                            AutoSoup.D.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                        }
                        else {
                            AutoSoup.D.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.OFF_HAND));
                        }
                        AutoSoup.D.player.connection.sendPacket((Packet)new CPacketHeldItemChange(currentItem));
                        this.a.e();
                        return;
                    }
                    final PlayerControllerMP playerController = AutoSoup.D.playerController;
                    final int n2 = n;
                    final int n3 = 0;
                    playerController.windowClick(n3, n2, n3, ClickType.PICKUP, (EntityPlayer)AutoSoup.D.player);
                    final PlayerControllerMP playerController2 = AutoSoup.D.playerController;
                    final int n4 = 41;
                    final int n5 = 0;
                    playerController2.windowClick(n5, n4, n5, ClickType.PICKUP, (EntityPlayer)AutoSoup.D.player);
                    this.a.e();
                }
                else {
                    i = --n;
                }
            }
        }
    }
    
    public void b() {
        this.k = false;
        super.b();
    }
    
    public static Minecraft getMinecraft1() {
        return AutoSoup.D;
    }
    
    public int e() {
        int n = 0;
        final ItemStack[] array;
        final int length = (array = (ItemStack[])AutoSoup.D.player.inventory.mainInventory.toArray((Object[])new ItemStack[n])).length;
        int i = 0;
        int n2 = 0;
        while (i < length) {
            final ItemStack itemStack;
            if ((itemStack = array[n2]) != null && itemStack.getItem() instanceof ItemSoup) {
                ++n;
            }
            i = ++n2;
        }
        return n;
    }
    
    public static Minecraft getMinecraft2() {
        return AutoSoup.D;
    }
    
    public static void M(final AutoSoup autoSoup) {
        autoSoup.C();
    }
    
    public static Minecraft getMinecraft3() {
        return AutoSoup.D;
    }
    
    public static Timer M(final AutoSoup autoSoup) {
        return autoSoup.D;
    }
    
    public static Value M(final AutoSoup autoSoup) {
        return autoSoup.dropsoup;
    }
    
    public static NumberValue M(final AutoSoup autoSoup) {
        return autoSoup.health;
    }
}
