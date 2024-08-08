package net.futureclient.client.modules.combat;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.futureclient.client.PG;
import net.futureclient.client.UF;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemElytra;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.futureclient.client.modules.combat.autoarmor.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.utils.Value;
import net.futureclient.client.ke;
import net.futureclient.client.R;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.Ea;

public class AutoArmor extends Ea
{
    private Timer K;
    private NumberValue delay;
    private boolean d;
    private R<ke.Uf> priority;
    private Value<Boolean> elytraPriority;
    private Value<Boolean> openInventory;
    
    public AutoArmor() {
        super("AutoArmor", new String[] { "AutoArmor", "aa", "armor" }, true, -5385072, Category.COMBAT);
        this.openInventory = new Value<Boolean>(false, new String[] { "Open Inventory", "OpenInventory", "Open", "Inv", "Inventory", "OpenInv", "OE", "I" });
        this.elytraPriority = new Value<Boolean>(false, new String[] { "Elytra Priority", "ElytraPriority", "Elytra", "EP" });
        this.delay = new NumberValue(0.35f, 0.0f, 1.0f, 5.941588215E-315, new String[] { "Delay", "D" });
        this.priority = new R<ke.Uf>(ke.Uf.a, new String[] { "Priority", "Prior", "P" });
        final int n = 4;
        final boolean d = false;
        this.K = new Timer();
        this.d = d;
        final Value[] array = new Value[n];
        array[0] = this.priority;
        array[1] = this.elytraPriority;
        array[2] = this.openInventory;
        array[3] = this.delay;
        this.M(array);
        this.M(new n[] { new Listener1(this) });
    }
    
    private boolean B(final ItemArmor itemArmor) {
        return itemArmor.armorType == EntityEquipmentSlot.CHEST;
    }
    
    public static Minecraft getMinecraft() {
        return AutoArmor.D;
    }
    
    public static Minecraft getMinecraft1() {
        return AutoArmor.D;
    }
    
    private boolean b(final ItemArmor itemArmor) {
        return itemArmor.armorType == EntityEquipmentSlot.LEGS;
    }
    
    public static Minecraft getMinecraft2() {
        return AutoArmor.D;
    }
    
    private boolean e(final ItemArmor itemArmor) {
        return itemArmor.armorType == EntityEquipmentSlot.FEET;
    }
    
    public static Value e(final AutoArmor autoArmor) {
        return autoArmor.openInventory;
    }
    
    public static Minecraft getMinecraft3() {
        return AutoArmor.D;
    }
    
    public static Minecraft getMinecraft4() {
        return AutoArmor.D;
    }
    
    public static Minecraft getMinecraft5() {
        return AutoArmor.D;
    }
    
    private void M(final int n, final boolean b) {
        AutoArmor.D.playerController.windowClick(AutoArmor.D.player.inventoryContainer.windowId, n, 0, b ? ClickType.QUICK_MOVE : ClickType.PICKUP, (EntityPlayer)AutoArmor.D.player);
    }
    
    private boolean M(final byte b) {
        if (this.d) {
            return false;
        }
        if (this.elytraPriority.M() && b == 6) {
            if (AutoArmor.D.player.inventoryContainer.getSlot((int)b).getStack().getItem() instanceof ItemElytra) {
                return false;
            }
            int i = 9;
            int n = 9;
            while (i <= 44) {
                final ItemStack stack;
                if ((stack = AutoArmor.D.player.inventoryContainer.getSlot(n).getStack()) != ItemStack.EMPTY && stack.getItem() instanceof ItemElytra && stack.getCount() == 1 && stack.getMaxDamage() - stack.getItemDamage() > 5) {
                    final boolean b2 = AutoArmor.D.player.inventoryContainer.getSlot((int)b).getStack() == ItemStack.EMPTY;
                    if (!b2) {
                        this.M(b, false);
                    }
                    this.M(n, true);
                    if (!b2) {
                        this.M(n, false);
                    }
                    return true;
                }
                i = (n = (byte)(n + 1));
            }
        }
        int n2 = -1;
        int n3 = -1;
        ItemArmor itemArmor = null;
        String m = null;
        switch (UF.k[this.priority.M().ordinal()]) {
            case 1:
                m = PG.M("\u001b\u0016\u0018\t\r%\t\b\u0016\u000e\u001c\u0019\r\u0013\u0016\u0014");
                break;
            default:
                m = "protection";
                break;
        }
        if (AutoArmor.D.player.inventoryContainer.getSlot((int)b).getStack() != ItemStack.EMPTY && AutoArmor.D.player.inventoryContainer.getSlot((int)b).getStack().getItem() instanceof ItemArmor) {
            n2 = (itemArmor = (ItemArmor)AutoArmor.D.player.inventoryContainer.getSlot((int)b).getStack().getItem()).damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation(m), AutoArmor.D.player.inventoryContainer.getSlot((int)b).getStack());
        }
        int j = 9;
        int n4 = 9;
        while (j <= 44) {
            final ItemStack stack2;
            if ((stack2 = AutoArmor.D.player.inventoryContainer.getSlot(n4).getStack()) != ItemStack.EMPTY && stack2.getItem() instanceof ItemArmor && stack2.getCount() == 1) {
                final ItemArmor itemArmor2 = (ItemArmor)stack2.getItem();
                final int n5 = itemArmor2.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation(m), stack2);
                if (this.M(itemArmor2, b) && (itemArmor == null || n2 < n5)) {
                    n2 = n5;
                    itemArmor = itemArmor2;
                    n3 = n4;
                }
            }
            j = (n4 = (byte)(n4 + 1));
        }
        if (n3 != -1) {
            final boolean b3 = AutoArmor.D.player.inventoryContainer.getSlot((int)b).getStack() == ItemStack.EMPTY;
            if (!b3) {
                this.M(b, false);
            }
            this.M(n3, true);
            if (!b3) {
                this.M(n3, false);
            }
            return true;
        }
        return false;
    }
    
    public static Minecraft getMinecraft6() {
        return AutoArmor.D;
    }
    
    public static boolean M(final AutoArmor autoArmor, final boolean d) {
        return autoArmor.d = d;
    }
    
    private boolean M(final ItemArmor itemArmor, final byte b) {
        return (b == 5 && this.M(itemArmor)) || (b == 6 && this.B(itemArmor)) || (b == 7 && this.b(itemArmor)) || (b == 8 && this.e(itemArmor));
    }
    
    public static void M(final AutoArmor autoArmor, final int n, final boolean b) {
        autoArmor.M(n, b);
    }
    
    public static Timer M(final AutoArmor autoArmor) {
        return autoArmor.K;
    }
    
    public static boolean M(final AutoArmor autoArmor, final byte b) {
        return autoArmor.M(b);
    }
    
    private boolean M(final ItemArmor itemArmor) {
        return itemArmor.armorType == EntityEquipmentSlot.HEAD;
    }
    
    public static Value M(final AutoArmor autoArmor) {
        return autoArmor.elytraPriority;
    }
    
    public static NumberValue M(final AutoArmor autoArmor) {
        return autoArmor.delay;
    }
    
    public static Minecraft getMinecraft7() {
        return AutoArmor.D;
    }
}
