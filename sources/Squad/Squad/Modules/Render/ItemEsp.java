package Squad.Modules.Render;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventRender3D;
import Squad.base.Module;
import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;

public class ItemEsp extends Module{
	public ItemEsp() {
		super("ItemESP", Keyboard.KEY_NONE, 0x88, Category.Render);
		// TODO Auto-generated constructor stub
	}
	

    @Override
    public void setup() {
        ArrayList<String> modes = new ArrayList<String>();
        modes.add("Boxy");
        Squad.Squad.setmgr.rSetting(new Setting("ItemESPMode", this, "Boxy", modes));
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        super.onDisable();
    }

    @EventTarget
    public void onUpdate(EventRender3D e) {
        for (Object o : mc.theWorld.loadedEntityList) {
            if (!(o instanceof EntityItem)) continue;
            EntityItem item = (EntityItem)o;
            GlStateManager.pushMatrix();
            GlStateManager.translate((double)((float)item.lastTickPosX) + (item.posX - item.lastTickPosX) * (double)Minecraft.getMinecraft().timer.renderPartialTicks - RenderManager.renderPosX, item.lastTickPosY + (item.posY - item.lastTickPosY) * (double)Minecraft.getMinecraft().timer.renderPartialTicks - RenderManager.renderPosY, item.lastTickPosZ + (item.posZ - item.lastTickPosZ) * (double)Minecraft.getMinecraft().timer.renderPartialTicks - RenderManager.renderPosZ);
            GlStateManager.rotate(- Minecraft.getMinecraft().renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
            GlStateManager.scale(-0.02666667f, -0.02666667f, 0.02666667f);
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            if (Squad.Squad.setmgr.getSettingByName("ItemESPMode").getValString().equalsIgnoreCase("Boxy")) {
            	setDisplayname("ItemESP §7Box");
                Gui.drawRect(9.0, -25.0, 13.0, -17.0, -16777216);
                Gui.drawRect(5.0, -21.0, 12.0, -25.0, -16777216);
                Gui.drawRect(10.0, -22.0, 12.0, -18.0, -16711697);
                Gui.drawRect(6.0, -22.0, 12.0, -24.0, -16711697);
                Gui.drawRect(-9.0, -25.0, -13.0, -17.0, -16777216);
                Gui.drawRect(-5.0, -21.0, -12.0, -25.0, -16777216);
                Gui.drawRect(-10.0, -22.0, -12.0, -18.0, -16711697);
                Gui.drawRect(-6.0, -22.0, -12.0, -24.0, -16711697);
                Gui.drawRect(9.0, -7.0, 13.0, 1.0, -16777216);
                Gui.drawRect(9.0, -3.0, 5.0, 1.0, -16777216);
                Gui.drawRect(10.0, -6.0, 12.0, 0.0, -16711697);
                Gui.drawRect(10.0, -2.0, 6.0, 0.0, -16711697);
                Gui.drawRect(-9.0, -7.0, -13.0, 1.0, -16777216);
                Gui.drawRect(-10.0, -3.0, -5.0, 1.0, -16777216);
                Gui.drawRect(-10.0, -6.0, -12.0, 0.0, -16711697);
                Gui.drawRect(-10.0, -2.0, -6.0, 0.0, -16711697);
            }
            GlStateManager.enableDepth();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
        }
    }
}