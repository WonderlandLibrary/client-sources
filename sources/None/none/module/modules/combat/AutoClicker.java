package none.module.modules.combat;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.valuesystem.BooleanValue;
import none.valuesystem.NumberValue;

public class AutoClicker extends Module{

	public AutoClicker() {
		super("AutoClicker", "AutoClicker", Category.COMBAT, Keyboard.KEY_NONE);
	}
	
	public NumberValue<Integer> cps = new NumberValue<>("CPS", 8, 1, 20);
	
	private BooleanValue random = new BooleanValue("Random", true);
	
//	private TimeHelper timer = new TimeHelper();
	private int timer = 0;
	
	public static int randomNumber(int min, int max) {
        int ii = -min + (int) (Math.random() * ((max - (-min)) + 1));
        return ii;
    }
	
	@Override
	protected void onEnable() {
		super.onEnable();

		timer = 20;
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
	}
	
	@Override
	@RegisterEvent(events = EventPreMotionUpdate.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			if (e.isPre() && mc.currentScreen == null) {
				timer++;
				if (Mouse.isButtonDown(0) && !Mouse.isButtonDown(1)) {
					if (mc.thePlayer.rayTrace(3.7, mc.timer.renderPartialTicks).typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
						return;
					}
					//Left Click
					int cps = this.cps.getInteger();
					boolean random = this.random.getObject();
					int rand = random ? randomNumber(cps - 1, cps + 1) : 0;
					int cpsdel = cps+rand <= 0? 1 : cps+rand;
					if (timer >= 20/cpsdel) {
						mc.clickMouse();
						timer = 0;
					}
                }else if (Mouse.isButtonDown(1)) {
                	ItemStack stack = mc.thePlayer.getCurrentEquippedItem();
                	if (stack != null) {
                		Item item = mc.thePlayer.getCurrentEquippedItem().getItem();
	                	if (item != null) {
	                		if (item instanceof ItemBow || item instanceof ItemFood || item instanceof ItemBucket || item instanceof ItemBucketMilk) {
	                			return;
	                		}
	                	}
                	}
                	//Right Click
                	int cps = this.cps.getInteger();
					boolean random = this.random.getObject();
					int rand = random ? randomNumber(cps - 1, cps + 1) : 0;
					int cpsdel = cps+rand <= 0? 1:cps+rand;
					if (timer >= 20/cpsdel) {
						mc.rightClickMouse();
						timer = 0;
					}
                }
			}
		}
	}
}
