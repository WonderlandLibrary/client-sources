package Squad.base;


import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.events.Event;

import Squad.Events.EventUpdate;
import Squad.commands.Command;
import Squad.info.ModuleInfo;
import net.minecraft.client.Minecraft;

/**
 * Created by vel on 05.03.2017.
 */
public class Module {
	public static final Category Category = null;
	public static net.minecraft.entity.Entity player = Minecraft.getMinecraft().thePlayer; 
	public static Minecraft mc = Minecraft.getMinecraft();
	public String name;
	public String suffix;
	public boolean toggled;
	private Category category;
	private int color;
	private int keyBind;
	private String displayname;
	private int fadeAmount;
	private boolean fading = false;
	private int slidieamount;
	private boolean slidie = false;

	public Module(String name, int keyBind, int color, Category category) {
		this.name = name;
		this.displayname = name;
		this.keyBind = keyBind;
		this.color = color;
		this.category = category;
		this.slidieamount = (-Minecraft.getMinecraft().fontRendererObj.getStringWidth(name));
		this.fadeAmount = (-Minecraft.getMinecraft().fontRendererObj.getStringWidth(name));
		setup();
	}

	public Module(String name, String displayname, int keyBind, int color, Category category) {
		this.name = name;
		this.displayname = name;
		this.keyBind = keyBind;
		this.color = color;
		this.category = category;
		this.fadeAmount = (-Minecraft.getMinecraft().fontRendererObj.getStringWidth(name));
		this.slidieamount = (-Minecraft.getMinecraft().fontRendererObj.getStringWidth(name));
		
		setup();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean isCategory(Category category) {
		return this.category == category;
	}

	public boolean getState() {
		return isEnabled();

	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getKeyBind() {
		return keyBind;
	}

	public void setKeyBind(int keyBind) {
		this.keyBind = keyBind;
	}

	public boolean isEnabled() {
		return toggled;
	}

	public String getDisplayName() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public void toggle() {
		{
			this.toggled = (!this.toggled);
			setFading(true);
			setslidie(true);
			
			if (this.toggled) {
				EventManager.register(this);
                mc.thePlayer.playSound("random.click", 500F, 1F);
                mc.thePlayer.playSound("random.pop", 500F, 1F);
        //        mc.thePlayer.playSound("note.bassattack", 500F, 1F);
				onEnable();

			} else {
				EventManager.unregister(this);
				onDisable();

			}
		}
	}

	public void setToggled(boolean toggled) {
		this.toggled = toggled;
		setFading(true);

		if (this.toggled) {


			onEnable();
		} else {
			onDisable();
		}

	}
	public int getFadeAmount()
	{
		return this.fadeAmount;
	}

	public void setFadeAmount(int fadeAmount)
	{
		this.fadeAmount = fadeAmount;
	}

	public boolean isFading()
	{
		return this.fading;
	}

	public void setFading(boolean fading)
	{
		this.fading = fading;
	}

	public void onEnable() {
		ModuleInfo.pushMessage("Module " + getName(), "toggled");
	}
	public int getSlidieAmount()
	{
		return this.fadeAmount;
	}

	public void setSlidieAmount(int fadeAmount)
	{
		this.fadeAmount = fadeAmount;
	}

	public boolean isslidie()
	{
		return this.fading;
	}

	public void setslidie(boolean fading)
	{
		this.fading = fading;
	}


	public void onDisable() {
	}

	public void onToggle() {
	}

	public void setup() {
	}


	public void onEvent(Event event) {
	}

	public void onUpdate() {
		// TODO Auto-generated method stub
		
	}

	public void onVelocity() {
		
	}



}
