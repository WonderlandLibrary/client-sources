package Squad.Modules.Player;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;
import com.darkmagician6.eventapi.events.Event;

import Squad.Events.EventUpdate;
import Squad.Utils.TimeHelper;
import Squad.base.Category;
import Squad.base.Module;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;



public class AutoArmor extends Module{
	private ArrayList<Integer> hmIds;
    private ArrayList<Integer> cpIds;
    private ArrayList<Integer> lgIds;
    private ArrayList<Integer> btIds;
    private TimeHelper timer;
    private double wait;


	public AutoArmor() {
		super("AutoArmor", Keyboard.KEY_I, 0xffffffff, Category.Player);
        this.hmIds = new ArrayList<Integer>();
        this.cpIds = new ArrayList<Integer>();
        this.lgIds = new ArrayList<Integer>();
        this.btIds = new ArrayList<Integer>();
        this.timer = new TimeHelper();
        this.wait = 50.0;
    }
    
    public void preInit() {
        this.hmIds.add(298);
        this.hmIds.add(302);
        this.hmIds.add(306);
        this.hmIds.add(310);
        this.hmIds.add(314);
        this.cpIds.add(303);
        this.cpIds.add(307);
        this.cpIds.add(311);
        this.cpIds.add(299);
        this.cpIds.add(315);
        this.lgIds.add(304);
        this.lgIds.add(300);
        this.lgIds.add(308);
        this.lgIds.add(312);
        this.lgIds.add(316);
        this.btIds.add(301);
        this.btIds.add(305);
        this.btIds.add(309);
        this.btIds.add(313);
        this.btIds.add(317);
    }

	@EventTarget
	public void onEvent(EventUpdate e) {
		if (!this.timer.delay((float)this.wait)) {
            return;
        }
        this.timer.reset();
        if (mc.thePlayer != null && (mc.currentScreen == null || mc.currentScreen instanceof GuiInventory || !mc.currentScreen.getClass().getName().contains("inventory"))) {
            int slotID = -1;
            double maxProt = -1.0;
            try {
                for (int i = 9; i < 45; ++i) {
                    final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                    if (stack != null) {
                    	if(mc.currentScreen instanceof GuiInventory) {
                        final double protValue = this.getProtectionValue(stack);
                        if (this.canEquip(stack) && protValue >= maxProt) {
                            slotID = i;
                            maxProt = protValue;
                        }
                        }
                    }
                }
                if (slotID != -1) {
                	if(mc.currentScreen instanceof GuiInventory) {
                	mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slotID, 0, 1, mc.thePlayer);
                	}
                }
            }
            catch (Exception e213) {
                e213.printStackTrace();
            }
            for (int x = 9; x < 45; ++x) {
                try {
                    final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(x).getStack();
                    if (stack != null) {
                    	if(mc.currentScreen instanceof GuiInventory) {
                        if (this.hmIds.contains(Item.getIdFromItem(stack.getItem())) && mc.thePlayer.inventoryContainer.getSlot(5).getStack() != null && this.getProtectionValue(stack) > this.getProtectionValue(mc.thePlayer.inventoryContainer.getSlot(5).getStack())) {
                        	mc.playerController.windowClick(0, 5, 0, 0, mc.thePlayer);
                            AutoArmor.mc.playerController.windowClick(0, -999, 0, 0, AutoArmor.mc.thePlayer);
                            this.timer.reset();
                            break;
                        }
                        if (this.cpIds.contains(Item.getIdFromItem(stack.getItem())) && mc.thePlayer.inventoryContainer.getSlot(6).getStack() != null && this.getProtectionValue(stack) > this.getProtectionValue(mc.thePlayer.inventoryContainer.getSlot(6).getStack())) {
                            mc.playerController.windowClick(0, 6, 0, 0, mc.thePlayer);
                            AutoArmor.mc.playerController.windowClick(0, -999, 0, 0, AutoArmor.mc.thePlayer);
                            this.timer.reset();
                            break;
                        }
                        if (this.lgIds.contains(Item.getIdFromItem(stack.getItem())) && mc.thePlayer.inventoryContainer.getSlot(7).getStack() != null && this.getProtectionValue(stack) > this.getProtectionValue(mc.thePlayer.inventoryContainer.getSlot(7).getStack())) {
                            mc.playerController.windowClick(0, 7, 0, 0, mc.thePlayer);
                            AutoArmor.mc.playerController.windowClick(0, -999, 0, 0, AutoArmor.mc.thePlayer);
                            this.timer.reset();
                            break;
                        }
                        if (this.btIds.contains(Item.getIdFromItem(stack.getItem())) && mc.thePlayer.inventoryContainer.getSlot(8).getStack() != null && this.getProtectionValue(stack) > this.getProtectionValue(mc.thePlayer.inventoryContainer.getSlot(8).getStack())) {
                            mc.playerController.windowClick(0, 8, 0, 0, mc.thePlayer);
                            AutoArmor.mc.playerController.windowClick(0, -999, 0, 0, AutoArmor.mc.thePlayer);
                            this.timer.reset();
                            break;
                        }
                    	}
                        
                    }
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
	}
        private boolean canEquip(final ItemStack stack) {
            return (mc.thePlayer.getEquipmentInSlot(1) == null && stack.getUnlocalizedName().contains("boots")) || (mc.thePlayer.getEquipmentInSlot(2) == null && stack.getUnlocalizedName().contains("leggings")) || (mc.thePlayer.getEquipmentInSlot(3) == null && stack.getUnlocalizedName().contains("chestplate")) || (mc.thePlayer.getEquipmentInSlot(4) == null && stack.getUnlocalizedName().contains("helmet"));
        }
        
        private double getProtectionValue(final ItemStack stack) {
            if (!(stack.getItem() instanceof ItemArmor)) {
                return 0.0;
            }
            return ((ItemArmor)stack.getItem()).damageReduceAmount + (100 - ((ItemArmor)stack.getItem()).damageReduceAmount * 4) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 4 * 0.0075;
        }

}
