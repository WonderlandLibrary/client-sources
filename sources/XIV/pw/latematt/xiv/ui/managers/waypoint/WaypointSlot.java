package pw.latematt.xiv.ui.managers.waypoint;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import org.lwjgl.opengl.GL11;
import pw.latematt.xiv.mod.mods.render.waypoints.base.Waypoint;

public class WaypointSlot extends GuiSlot {
    private GuiWaypointManager screen;
    private int selected;

    public WaypointSlot(GuiWaypointManager screen, Minecraft mcIn, int width, int height, int p_i1052_4_, int p_i1052_5_, int p_i1052_6_) {
        super(mcIn, width, height, p_i1052_4_, p_i1052_5_, p_i1052_6_);

        this.screen = screen;
    }

    @Override
    protected int getSize() {
        return screen.getWaypoints().size();
    }

    @Override
    public int getSlotHeight() {
        return super.getSlotHeight();
    }

    @Override
    protected void elementClicked(int slot, boolean var2, int var3, int var4) {
        this.selected = slot;
    }

    @Override
    protected boolean isSelected(int slot) {
        return selected == slot;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int slot) {
        this.selected = slot;
    }

    public Waypoint getWaypoint() {
        return getWaypoint(getSelected());
    }

    @Override
    protected void drawBackground() {

    }

    @Override
    protected void drawSlot(int slot, int x, int y, int var4, int var5, int var6) {
        Waypoint waypoint = getWaypoint(slot);

        if (waypoint != null) {
            mc.fontRendererObj.drawStringWithShadow(waypoint.getName(), x + 1, y + 1, 0xFFDD88DD);

            GL11.glPushMatrix();
            GL11.glScaled(0.5, 0.5, 0.5);
            mc.fontRendererObj.drawStringWithShadow(waypoint.getX() + ", " + waypoint.getY() + ", " + waypoint.getZ(), (x + 1) * 2, (y + 12) * 2, 0xFFFFFFFF);
            mc.fontRendererObj.drawStringWithShadow("Dimension: " + waypoint.getDimension(), (x + 1) * 2, (y + 18) * 2, 0xFFFFFFFF);
            mc.fontRendererObj.drawStringWithShadow("Temporary: " + waypoint.isTemporary(), (x + 1) * 2, (y + 24) * 2, 0xFFFFFFFF);
            mc.fontRendererObj.drawStringWithShadow("Server: " + waypoint.getServer(), (x + 1) * 2, (y + 30) * 2, 0xFFFFFFFF);
            GL11.glScaled(2, 2, 2);
            GL11.glPopMatrix();
        }
    }

    public Waypoint getWaypoint(int slot) {
        int count = 0;

        for (Waypoint waypoint : screen.getWaypoints()) {
            if (count == slot) {
                return waypoint;
            }
            count++;
        }

        return null;
    }
}
