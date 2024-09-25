package none.module.modules.combat;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.PotionEffect;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.utils.ClickType;
import none.utils.PlayerUtil;
import none.utils.RotationUtils;
import none.utils.TimeHelper;
import none.valuesystem.BooleanValue;
import none.valuesystem.NumberValue;

public class AutoPot extends Module{

	public AutoPot() {
		super("AutoPot", "AutoPot", Category.COMBAT, Keyboard.KEY_NONE);
	}
	
	public BooleanValue SPEED = new BooleanValue("Speed-Potion", true);
	public BooleanValue REGEN = new BooleanValue("Regen-Potion", true);
	public BooleanValue FIRE = new BooleanValue("Fire-Potion", true);
	public BooleanValue PREDICT = new BooleanValue("Predict", false);
	public NumberValue<Integer> Health = new NumberValue<>("Health", 12, 4, 20);
	
	public static boolean potting;
    TimeHelper timer = new TimeHelper();
	
    @Override
    protected void onEnable() {
    	super.onEnable();
    	timer.setLastMS();
    }
    
    public static boolean isPotting(){
    	return potting;
    }
    
	@Override
	@RegisterEvent(events = {EventPreMotionUpdate.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			
			boolean speed = SPEED.getObject();
            boolean regen = REGEN.getObject();
            boolean fire = FIRE.getObject();
            if(!e.isPre())
            	return;
            if(timer.hasTimeReached(200)){
            	if(isPotting())
            		potting = false;
            }
            
            int spoofSlot = getBestSpoofSlot();
            int pots[] = {6,-1,-1,-1};
            if(regen)
            	pots[1] = 10;
            if(speed)
            	pots[2] = 1;
            if (fire)
            	pots[3] = 12;
  		  	
            for(int i = 0; i < pots.length; i++){
            	if(pots[i] == -1)
            		continue;
            	if(pots[i] == 6 || pots[i] == 10){
            		if(timer.hasTimeReached(900) && !mc.thePlayer.isPotionActive(pots[i])){
                		if((int)mc.thePlayer.getHealth() < Health.getInteger()){
                			getBestPot(spoofSlot, pots[i]);
                		}
            		}
            	}else if (pots[i] == 12) {
            		if (mc.thePlayer.fire > 0) {
	            		if(timer.hasTimeReached(1000) && !mc.thePlayer.isPotionActive(pots[i])){
	                		getBestPot(spoofSlot, pots[i]);
	                	}
            		}
            	}else if (pots[i] == 1) {
	            	if(timer.hasTimeReached(1000) && !mc.thePlayer.isPotionActive(pots[i])){
	            		getBestPot(spoofSlot, pots[i]);               		
	            	}
            	}
            }
		}
	}
	
	public void swap(int slot1, int hotbarSlot){
    	mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot1, hotbarSlot, ClickType.SWAP, mc.thePlayer);
    }
	
	public float[] getRotations(){    	
        double movedPosX = mc.thePlayer.posX + mc.thePlayer.motionX * 26.0D;
        double movedPosY = mc.thePlayer.boundingBox.minY - 3.6D;
        double movedPosZ = mc.thePlayer.posZ + mc.thePlayer.motionZ * 26.0D;	
        if(PREDICT.getObject() && PlayerUtil.isMoving2())
        	return RotationUtils.getRotationFromPosition(movedPosX, movedPosZ, movedPosY);
        else
        	return new float[]{mc.thePlayer.rotationYaw, 90};
    }
	
	int getBestSpoofSlot(){  	
    	int spoofSlot = 5;
    	for (int i = 36; i < 45; i++) {       		
    		if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
     			spoofSlot = i - 36;
     			break;
            }else if(mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemPotion) {
            	spoofSlot = i - 36;
     			break;
            }
        }
    	return spoofSlot;
    }
    void getBestPot(int hotbarSlot, int potID){
    	for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() &&(mc.currentScreen == null || mc.currentScreen instanceof GuiInventory)) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if(is.getItem() instanceof ItemPotion){
              	  ItemPotion pot = (ItemPotion)is.getItem();
              	  if(pot.getEffects(is).isEmpty())
              		  return;
              	  PotionEffect effect = (PotionEffect) pot.getEffects(is).get(0);              	  
                  int potionID = effect.getPotionID();
                  if(potionID == potID)
              	  if(ItemPotion.isSplash(is.getItemDamage()) && isBestPot(pot, is)){
              		  if(36 + hotbarSlot != i)
              			  swap(i, hotbarSlot);
              		  timer.setLastMS();
              		  boolean canpot = true;
              		  int oldSlot = mc.thePlayer.inventory.currentItem;
              		  mc.thePlayer.connection.sendPacket(new C09PacketHeldItemChange(hotbarSlot));
          			  mc.thePlayer.connection.sendPacket(new C03PacketPlayer.C05PacketPlayerLook(getRotations()[0], getRotations()[1], mc.thePlayer.onGround));
          			  mc.thePlayer.connection.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
          			  mc.thePlayer.connection.sendPacket(new C09PacketHeldItemChange(oldSlot));
          			  potting = true;
          			  break;
              	  }               	  
                }              
            }
        }
    }
    
    boolean isBestPot(ItemPotion potion, ItemStack stack){
    	if(potion.getEffects(stack) == null || potion.getEffects(stack).size() != 1)
    		return false;
        PotionEffect effect = (PotionEffect) potion.getEffects(stack).get(0);
        int potionID = effect.getPotionID();
        int amplifier = effect.getAmplifier(); 
        int duration = effect.getDuration();
    	for (int i = 9; i < 45; i++) {    		
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {           	
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if(is.getItem() instanceof ItemPotion){
                	ItemPotion pot = (ItemPotion)is.getItem();
                	 if (pot.getEffects(is) != null) {
                         for (Object o : pot.getEffects(is)) {
                             PotionEffect effects = (PotionEffect) o;
                             int id = effects.getPotionID();
                             int ampl = effects.getAmplifier(); 
                             int dur = effects.getDuration();
                             if (id == potionID && ItemPotion.isSplash(is.getItemDamage())){
                            	 if(ampl > amplifier){
                            		 return false;
                            	 }else if (ampl == amplifier && dur > duration){
                            		 return false;
                            	 }
                             }                            
                         }
                     }
                }
            }
        }
    	return true;
    }
}
