package wtf.diablo.client.gui.clickgui.material.impl.main;

import com.mojang.realmsclient.gui.ChatFormatting;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.font.FontRepository;
import wtf.diablo.client.gui.clickgui.material.impl.module.ModuleContainer;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.impl.render.ClickGuiModule;
import wtf.diablo.client.util.render.ColorUtil;
import wtf.diablo.client.util.render.RenderUtil;
import wtf.diablo.client.util.render.gl.GLUtils;
import wtf.diablo.client.util.system.mouse.MouseUtils;
import java.awt.*;

public final class MainPanel {
    private int oldX, oldY;
    private double x = oldX, y = oldY, width = 500, height = 350;
    private boolean dragging, canDrag;
    private ModuleCategoryEnum moduleCategoryEnum = ModuleCategoryEnum.COMBAT;
    private ModuleContainer moduleContainer;
    private final ClickGuiModule clickGuiModule = Diablo.getInstance().getModuleRepository().getModuleInstance(ClickGuiModule.class);

    public MainPanel() {
        this.moduleContainer = new ModuleContainer(this, moduleCategoryEnum);
    }

    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (!this.canDrag) {
            this.setDragging(false);
        }
        if (this.dragging) {
            this.setX(this.getOldX() + mouseX);
            this.setY(this.getOldY() + mouseY);
        }
        this.canDrag = true;

        final String clientName = "Diablo X";


        final String finalName = clientName.charAt(0) + ChatFormatting.WHITE.toString() + clientName.substring(1);

        final ClickGuiModule clickGuiModule = Diablo.getInstance().getModuleRepository().getModuleInstance(ClickGuiModule.class);

        RenderUtil.drawOutlineRoundedRectangle(this.x, this.y, this.width, this.height, 0, 0.1f, clickGuiModule.getOutline().getValue() ? ColorUtil.PRIMARY_MAIN_COLOR.getRGB() : new Color(12, 12, 12).getRGB(), new Color(12, 12, 12).getRGB());

        this.moduleContainer.drawScreen(mouseX, mouseY, partialTicks);

        GLUtils.enableBlend();
        FontRepository.OUTFIT_BOLD_28.drawString(finalName, this.x + 5, this.y + 5, ColorUtil.PRIMARY_MAIN_COLOR.getRGB());
        GLUtils.disableBlend();

        int stringWidth = 0;

        for (final ModuleCategoryEnum categoryEnum : ModuleCategoryEnum.values()) {
            GLUtils.enableBlend();
            FontRepository.SFREG22.drawString(categoryEnum.toString(), x + FontRepository.SFREG22.getWidth(finalName) + stringWidth + 40, this.y + 8, this.moduleCategoryEnum == categoryEnum ? -1 : new Color(255, 255, 255, 185).getRGB());
            GLUtils.disableBlend();

            if (this.moduleCategoryEnum == categoryEnum) {
                RenderUtil.drawRoundedRectangle(x + FontRepository.SFREG22.getWidth(finalName) + stringWidth + 38.5f, this.y + 23, FontRepository.SFREG22.getWidth(categoryEnum.toString()) + 5, 1, 1, ColorUtil.PRIMARY_MAIN_COLOR.getRGB());
            }

            stringWidth += FontRepository.SFREG22.getWidth(categoryEnum.toString()) + 16;
        }
    }

    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (MouseUtils.isHoveringCoords(this.x, this.y, this.width, this.height, mouseX, mouseY)) {
            this.setDragging(true);
            this.setOldX((int) (this.getX() - mouseX));
            this.setOldY((int) (this.getY() - mouseY));

            int stringWidth = 0;

            final String clientName = "Diablo X";

            final String finalName = clientName.charAt(0) + ChatFormatting.WHITE.toString() + clientName.substring(1);


            for (final ModuleCategoryEnum value : ModuleCategoryEnum.values()) {
                if (MouseUtils.isHoveringCoords((float) x + FontRepository.SFREG22.getWidth(finalName) + stringWidth + 40, (float) (this.y + 8), FontRepository.SFREG22.getWidth(value.toString()) + 5, 16, mouseX, mouseY)) {
                    this.setCanDrag(false);
                    this.setModuleCategoryEnum(value);
                    this.moduleContainer = new ModuleContainer(this, value);
                }
                stringWidth += FontRepository.SFREG22.getWidth(value.toString()) + 14;
            }
            this.moduleContainer.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        this.setDragging(false);
        this.moduleContainer.mouseReleased(mouseX, mouseY, state);
    }

    public void onGuiClosed() {
        this.setOldX((int) this.getX());
        this.setOldY((int) this.getY());
        this.moduleContainer.onGuiClosed();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getOldX() {
        return oldX;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public boolean isCanDrag() {
        return canDrag;
    }

    public void setCanDrag(boolean canDrag) {
        this.canDrag = canDrag;
    }

    public void setModuleCategoryEnum(final ModuleCategoryEnum moduleCategoryEnum) {
        this.moduleCategoryEnum = moduleCategoryEnum;
    }
}
