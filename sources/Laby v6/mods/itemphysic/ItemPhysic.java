package mods.itemphysic;

import de.labystudio.modapi.ModAPI;
import de.labystudio.modapi.Module;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import mods.itemphysic.config.Configuration;
import mods.itemphysic.config.Property;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class ItemPhysic extends Module
{
    public static float rotationSpeed = 1.0F;
    private static Configuration config;

    public void onEnable()
    {
        Map<String, Object> map = new HashMap();
        map.put("rotationSpeed", Float.valueOf(rotationSpeed));
        config = new Configuration("ItemPhysics_LabyMod", map);
        rotationSpeed = config.get("rotationSpeed").getFloat();
        ModAPI.addSettingsButton("ItemPhysic", new ItemPhysic.ItemPhysicGUI());
    }

    public static class ItemPhysicGUI extends GuiScreen
    {
        private GuiSlideControl rotationSpeedControl;

        public void initGui()
        {
            this.buttonList.clear();
            this.buttonList.add(this.rotationSpeedControl = new GuiSlideControl(1, this.width / 2 - 50, this.height / 2 - 10, 100, 20, "Rotation Speed: ", 0.0F, 100.0F, ItemPhysic.rotationSpeed, true));
            this.buttonList.add(new GuiButton(2, this.width / 2 - 30, this.height / 2 + 20, 60, 20, "Save"));
            super.initGui();
        }

        protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
        {
            super.mouseClicked(mouseX, mouseY, mouseButton);
            ItemPhysic.rotationSpeed = this.rotationSpeedControl.GetValueAsFloat();
            ItemPhysic.config.set("rotationSpeed", new Property(String.valueOf(ItemPhysic.rotationSpeed)));
            ItemPhysic.config.save();
        }

        public void drawScreen(int mouseX, int mouseY, float partialTicks)
        {
            super.drawDefaultBackground();
            super.drawScreen(mouseX, mouseY, partialTicks);
        }

        protected void mouseReleased(int mouseX, int mouseY, int state)
        {
            super.mouseReleased(mouseX, mouseY, state);
            ItemPhysic.rotationSpeed = this.rotationSpeedControl.GetValueAsFloat();
            ItemPhysic.config.set("rotationSpeed", new Property(String.valueOf(ItemPhysic.rotationSpeed)));
            ItemPhysic.config.save();
        }

        protected void actionPerformed(GuiButton button) throws IOException
        {
            if (button.id == 2)
            {
                this.mc.displayGuiScreen(ModAPI.getLastScreen());
            }

            super.actionPerformed(button);
        }
    }
}
