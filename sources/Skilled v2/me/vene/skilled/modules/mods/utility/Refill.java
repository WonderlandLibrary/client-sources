 package me.vene.skilled.modules.mods.utility;

 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.MathUtil;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.values.BooleanValue;
 import me.vene.skilled.values.NumberValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.gui.GuiScreen;
 import net.minecraft.client.gui.inventory.GuiInventory;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.init.Items;
 import net.minecraft.inventory.Slot;
 import net.minecraft.item.ItemPotion;
 import net.minecraft.item.ItemStack;
 import net.minecraft.network.Packet;
 import net.minecraft.network.play.client.C16PacketClientStatus;
 import net.minecraft.potion.Potion;
 import net.minecraft.potion.PotionEffect;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 
public class Refill extends Module
{
    private static Minecraft mc;
    private NumberValue minDelay;
    private NumberValue maxDelay;
    private BooleanValue pots;
    private BooleanValue soup;
    private long nextDelay;
    protected long lastMS;
    
    public Refill() {
        super(StringRegistry.register("Refill"), 0, Category.U);
        this.minDelay = new NumberValue(StringRegistry.register("Min Delay"), 75.0, 0.0, 200.0);
        this.maxDelay = new NumberValue(StringRegistry.register("Max Delay"), 125.0, 0.0, 200.0);
        this.pots = new BooleanValue("Pots", true);
        this.soup = new BooleanValue("Soup", true);
        this.addOption(this.pots);
        this.addOption(this.soup);
        this.addValue(this.maxDelay);
        this.addValue(this.minDelay);
    }
    
    public void newDelay() {
        this.nextDelay = (long)(MathUtil.random.nextFloat() * (this.maxDelay.getValue() - this.minDelay.getValue()) + this.minDelay.getValue());
    }
    
    private boolean isSoup(final ItemStack stack) {
        return stack != null && stack.getItem() == Items.mushroom_stew;
    }
    
    private boolean isPot(final ItemStack stack) {
        if (stack != null && stack.getItem() == Items.potionitem && ItemPotion.isSplash(stack.getItemDamage())) {
            for (final Object o : ((ItemPotion)stack.getItem()).getEffects(stack)) {
                if (((PotionEffect)o).getPotionID() == Potion.heal.id) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isHotbarFull() {
        for (int i = 36; i < 45; ++i) {
            final Slot slot = Refill.mc.thePlayer.inventoryContainer.getSlot(i);
            if (slot == null) {
                return false;
            }
            final ItemStack stack = slot.getStack();
            if (stack == null) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void onEnable() {
        Refill.mc.getNetHandler().addToSendQueue((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
        Refill.mc.displayGuiScreen((GuiScreen)new GuiInventory((EntityPlayer)Refill.mc.thePlayer));
        this.updatebefore();
    }
    
    @Override
    public void onDisable() {
        if (Refill.mc.currentScreen != null) {
            Refill.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
    
    public boolean hasTimePassedMS(final long MS) {
        return this.getCurrentMS() >= this.lastMS + MS;
    }
    
    public void updatebefore() {
        this.lastMS = this.getCurrentMS();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent e) {
        if (!this.getState()) {
            return;
        }
        if (this.isHotbarFull()) {
            this.setState(false);
            return;
        }
        if (Refill.mc.thePlayer.openContainer == null || Refill.mc.thePlayer.inventoryContainer == null || Refill.mc.thePlayer.inventoryContainer.getInventory() == null || Refill.mc.playerController == null) {
            this.setState(false);
            return;
        }
        if (Refill.mc.currentScreen == null || !(Refill.mc.currentScreen instanceof GuiInventory)) {
            this.setState(false);
            return;
        }
        for (int i = 0; i < Refill.mc.thePlayer.inventoryContainer.getInventory().size(); ++i) {
            final Slot slot = Refill.mc.thePlayer.inventoryContainer.getSlot(i);
            if (slot != null) {
                final ItemStack stack = slot.getStack();
                if (stack != null && i < 36 && (!this.pots.getState() || this.soup.getState() || this.isPot(stack)) && (this.pots.getState() || !this.soup.getState() || this.isSoup(stack)) && (!this.pots.getState() || !this.soup.getState() || this.isPot(stack) || this.isSoup(stack)) && (this.pots.getState() || this.soup.getState())) {
                    if (this.hasTimePassedMS(this.nextDelay)) {
                        final ItemStack newstack = Refill.mc.playerController.windowClick(Refill.mc.thePlayer.inventoryContainer.windowId, i, 0, 1, (EntityPlayer)Refill.mc.thePlayer);
                        this.newDelay();
                        this.updatebefore();
                    }
                    return;
                }
            }
        }
        this.setState(false);
    }
    
    static {
        Refill.mc = Minecraft.getMinecraft();
    }
}