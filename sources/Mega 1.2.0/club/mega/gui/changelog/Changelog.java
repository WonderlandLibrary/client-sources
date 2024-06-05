package club.mega.gui.changelog;

import club.mega.Mega;
import club.mega.util.AnimationUtil;
import club.mega.util.ColorUtil;
import club.mega.util.MouseUtil;
import club.mega.util.RenderUtil;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Changelog {

    private final ArrayList<Change> changes = new ArrayList<>();
    private double scrollOffset;
    private double current;
    private double x = RenderUtil.getScaledResolution().getScaledWidth() - 300;
    private double y = RenderUtil.getScaledResolution().getScaledHeight() / 2D - 180;
    private final double width = 200;
    private double dragX;
    private double dragY;
    private boolean dragging;

    public Changelog() {
        current = 0;
        scrollOffset = 0;
        changes.clear();
        add(
                // 1.2.0
                new Change("1.2.0"),
                new Change("Config system", Type.UPDATED),
                new Change("ClickGUI", Type.UPDATED),
                new Change("ESP", Type.UPDATED),
                new Change("ModuleList", Type.UPDATED),

                new Change(),


                new Change("Intave autoblock", Type.ADDED),
                new Change("Intave velocity", Type.ADDED),
                new Change("2D ESP", Type.ADDED),
                new Change("Toggle sounds", Type.ADDED),

                // 1.1.0
                new Change("1.1.0"),
                new Change("ChangeLog", Type.UPDATED),
                new Change("Scaffold", Type.UPDATED),
                new Change("ClickGui", Type.UPDATED),
                new Change("Scaffold", Type.UPDATED),
                new Change("KillAura", Type.UPDATED),
                new Change("Design", Type.UPDATED),

                new Change(),

                new Change("STap", Type.ADDED),
                new Change("NoBob", Type.ADDED),
                new Change("TargetHud", Type.ADDED),
                new Change("Login", Type.ADDED),
                new Change("Animations", Type.ADDED),
                new Change("ConfigSystem", Type.ADDED),
                new Change("Velocity", Type.ADDED),
                new Change("InvMove", Type.ADDED),
                new Change("Step", Type.ADDED),
                new Change("Animations", Type.ADDED),

                // 1.0.0
                new Change("1.0.0"),
                new Change("Changelog", Type.ADDED),
                new Change("TabGui", Type.ADDED),
                new Change("Basic modules", Type.ADDED)
        );
    }

    private void add(final Change... modules) {
        this.changes.addAll(Arrays.asList(modules));
    }

    private void handleScrolling(final int mouseX, final int mouseY, final double x, final double y, final double width, final double height) {
        if (Mouse.hasWheel() && MouseUtil.isInside(mouseX, mouseY, x, y, width, height)) {
            int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                current += 10;
                if (this.current < 0) {
                    this.current = 0;
                }
            } else if (wheel > 0) {
                this.current -= 10;
                if (this.current < 0) {
                    this.current = 0;
                }
            }
            this.scrollOffset = AnimationUtil.animate(scrollOffset, current, 1);
        }
    }

    public final void render(final int mouseX, final int mouseY, final double aDouble) {
        if (dragging) {
            x = mouseX - dragX;
            y = mouseY - dragY;
        }

        double offset = y + 1 - scrollOffset;
        double height = 300;
        handleScrolling(mouseX, mouseY, x, y, width, height);

        RenderUtil.drawRoundedRect(x, y, width, height, 3, new Color(22,22,22));
        RenderUtil.drawRoundedRect(x + width / 2 - 29, y - 16, 60, 14, 3, ColorUtil.getMainColor());
        RenderUtil.drawRoundedRect(x + width / 2 - 30, y - 17, 60, 14, 3, new Color(22,22,2));
        Mega.INSTANCE.getFontManager().getFont("Roboto bold 20").drawCenteredString("Changelog", x + width / 2,y - 15,-1);

        GL11.glPushMatrix();
        RenderUtil.prepareScissorBox(0, y + 2, RenderUtil.getScaledResolution().getScaledWidth(), height - 4);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        for (final Change change : changes)
        {
            switch (change.getType())
            {
                case EMPTY:
                    RenderUtil.drawRect(x + 1, offset + 1, width - 2, 12, new Color(14, 14, 14));
                    offset += aDouble;
                    break;
                case VERSION:
                    if (changes.get(0) != change) {
                        RenderUtil.drawRect(x + 1, offset + 1, width - 2, 12, new Color(14, 14, 14));
                        offset += aDouble - 1;
                    }
                    RenderUtil.drawRect(x + 1, offset + 1, width - 2, 12, new Color(14, 14, 14));
                    Mega.INSTANCE.getFontManager().getFont("Roboto bold 20").drawCenteredString("- " + change.getChange() + " -", x + width / 2D, offset + 2, change.getType().getColor());
                    offset += aDouble + 1;
                    break;
                default:
                    RenderUtil.drawFullCircle(x + 8, offset + 8, 2, change.getType().getColor().getRGB());
                    Mega.INSTANCE.getFontManager().getFont("Arial 19").drawString(change.getType().name().toLowerCase() + ":", x + 13, offset + 3, change.getType().getColor());
                    Mega.INSTANCE.getFontManager().getFont("Arial 19").drawCenteredString(change.getChange(), x + width / 2D, offset + 3, -1);
                    RenderUtil.drawRect(x + 13, offset + 13, width - 13, 2, new Color(14, 14, 14));
                    offset += aDouble;
                    break;
            }
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }

    public final void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (MouseUtil.isInside(mouseX, mouseY, x + width / 2 - 30, y - 17, 60, 14) && button == 0) {
            dragging = true;
            dragX = mouseX - x;
            dragY = mouseY - y;
        }
    }

    public void mouseReleased(final int button) {
        if (button == 0 && dragging)
            dragging = false;
    }


    public enum Type {

        ADDED(Color.GREEN), REMOVED(Color.RED), UPDATED(Color.ORANGE), VERSION(Color.WHITE), EMPTY(Color.WHITE);

        private final Color color;

        Type(final Color color) {
            this.color = color;
        }

        public final Color getColor() {
            return color;
        }

    }

}
