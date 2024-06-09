package ca.commencal.ware.module.modules.render;

import ca.commencal.ware.Main;
import ca.commencal.ware.module.Module;
import ca.commencal.ware.module.ModuleCategory;
import ca.commencal.ware.utils.system.Wrapper;
import ca.commencal.ware.utils.visual.ColorUtils;
import ca.commencal.ware.utils.visual.RenderUtils;
import ca.commencal.ware.value.BooleanValue;
import ca.commencal.ware.value.NumberValue;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import org.lwjgl.input.Keyboard;

public class ClickGui extends Module{

	public static BooleanValue rainbow;
	public static BooleanValue shadow;

	public static NumberValue red;
	public static NumberValue green;
	public static NumberValue blue;
	public static NumberValue alpha;

	public static int color;

	public ClickGui() {
		super("ClickGui", ModuleCategory.GUI);
		this.setKey(Keyboard.KEY_P);
		this.setShow(false);

		this.shadow = new BooleanValue("Shadow", true);
		this.rainbow = new BooleanValue("Rainbow", true);
		this.red = new NumberValue("Red", 170D, 0D, 255D);
		this.green = new NumberValue("Green", 170D, 0D, 255D);
		this.blue = new NumberValue("Blue", 170D, 0D, 255D);
		this.alpha = new NumberValue("Alpha", 170D, 0D, 255D);

		this.addValue(shadow, rainbow, red, green, blue, alpha);
		this.setColor();
	}

	public static int getColor() {
		return rainbow.getValue() ? ColorUtils.rainbow().getRGB() : color;
	}

	public static void setColor() {
		if(rainbow.getValue()) {
			color = ColorUtils.rainbow().getRGB();
		}
		else
		{
			color = ColorUtils.color(red.getValue().intValue(),
					green.getValue().intValue(),
					blue.getValue().intValue(),
					alpha.getValue().intValue());
		}
	}

	@Override
	public void onEnable() {
		Wrapper.INSTANCE.mc().displayGuiScreen(Main.moduleManager.getGui());
		super.onEnable();
	}

	@Override
	public void onClientTick(ClientTickEvent event) {
		this.setColor();
		super.onClientTick(event);
	}

	@Override
	public void onRenderGameOverlay(Text event) {
		if(shadow.getValue()) {
			ScaledResolution sr = new ScaledResolution(Wrapper.INSTANCE.mc());
			RenderUtils.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), ColorUtils.color(0.0F, 0.0F, 0.0F, 0.5F));
		}
		super.onRenderGameOverlay(event);
	}

}
