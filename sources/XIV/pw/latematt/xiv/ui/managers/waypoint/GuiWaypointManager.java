package pw.latematt.xiv.ui.managers.waypoint;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.mod.mods.render.waypoints.Waypoints;
import pw.latematt.xiv.mod.mods.render.waypoints.base.Waypoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GuiWaypointManager extends GuiScreen {
    private GuiScreen parent;
    private WaypointSlot slot;

    public GuiTextField search;

    public GuiWaypointManager(GuiScreen parent) {
        this.parent = parent;
    }

    public List<Waypoint> getWaypoints() {
        Waypoints waypoints = (Waypoints) XIV.getInstance().getModManager().find("waypoints");
        if (search != null && search.getText().length() > 0 && waypoints != null) {
            if (search.getText().startsWith("!")) {
                String text = search.getText().substring(1);

                // Search for opposite of text given
                return waypoints.getPoints().stream().filter(waypoint -> !waypoint.getName().toLowerCase().contains(text.toLowerCase())).collect(Collectors.toCollection(ArrayList::new));
            }
            return waypoints.getPoints().stream().filter(waypoint -> waypoint.getName().toLowerCase().contains(search.getText().toLowerCase())).collect(Collectors.toCollection(ArrayList::new));
        } else if (waypoints != null) {
            return waypoints.getPoints().stream().collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);

        this.slot = new WaypointSlot(this, mc, width, height, 25, height - 98, 40);
        this.slot.registerScrollButtons(7, 8);

        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height - 26, 200, 20, "Cancel"));
        this.buttonList.add(new GuiButton(1, width / 2 - 48, height - 48, 98, 20, "Remove"));

        this.search = new GuiTextField(3, mc.fontRendererObj, width - 182, height - 52, 150, 20);
        this.search.setVisible(true);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if (button.enabled) {
            if (button.id == 0) {
                mc.displayGuiScreen(parent);
            } else {
                Waypoints waypoints = (Waypoints) XIV.getInstance().getModManager().find("waypoints");

                if (waypoints != null) {
                    waypoints.getPoints().remove(slot.getWaypoint());
                }
            }
        }
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        XIV.getInstance().getFileManager().saveFile("waypoints");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.slot.drawScreen(mouseX, mouseY, partialTicks);
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (this.slot.getWaypoint() != null) {
            Waypoint waypoint = this.slot.getWaypoint();
            mc.fontRendererObj.drawStringWithShadow(waypoint.getName(), 2, 2, 0xFFFFFFFF);

            this.buttonList.get(1).enabled = true;
        } else {
            this.buttonList.get(1).enabled = false;
        }

        String filters = "Custom Filters: '!'";
        mc.fontRendererObj.drawStringWithShadow(filters, width - 105 - (mc.fontRendererObj.getStringWidth(filters) / 2), height - 28, 0xFFFFFFFF);

        mc.fontRendererObj.drawStringWithShadow("Search:", width - 182, height - 62, 0xFFFFFFFF);
        search.drawTextBox();

        Waypoints waypoints = (Waypoints) XIV.getInstance().getModManager().find("waypoints");
        if (waypoints != null) {
            drawCenteredString(mc.fontRendererObj, String.format("Waypoints: §a%s§f/§c%s§f/§e%s§f", getWaypoints().size(), waypoints.getPoints().size() - getWaypoints().size(), waypoints.getPoints().size()), width / 2, 2, 0xFFFFFFFF);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (!search.isFocused()) {
            search.setFocused(!search.isFocused());
        }

        search.textboxKeyTyped(typedChar, keyCode);

        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(parent);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        search.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        search.updateCursorCounter();
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        this.slot.func_178039_p();
    }
}
