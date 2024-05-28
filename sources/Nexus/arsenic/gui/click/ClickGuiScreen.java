package arsenic.gui.click;

import arsenic.gui.click.impl.ModuleCategoryComponent;
import arsenic.gui.click.impl.UICategoryComponent;
import arsenic.main.Nexus;
import arsenic.module.impl.visual.ClickGui;
import arsenic.utils.font.FontRendererExtension;
import arsenic.utils.interfaces.IAlwaysClickable;
import arsenic.utils.interfaces.IAlwaysKeyboardInput;
import arsenic.utils.interfaces.IFontRenderer;
import arsenic.utils.render.*;
import arsenic.utils.timer.AnimationTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// allow escape to bind to none

public class ClickGuiScreen extends CustomGuiScreen {
    private ClickGui module;
    private List<UICategoryComponent> components;
    private final List<Runnable> renderLastList = new ArrayList<>();
    private ModuleCategoryComponent cmcc;
    private IAlwaysClickable alwaysClickedComponent;
    private IAlwaysKeyboardInput alwaysKeyboardInput;
    private final AnimationTimer blurTimer = new AnimationTimer(500, () -> true);
    private int vLineX, hLineY, x1, y1;

    //called once
    public void init(ClickGui clickGui) {
        components = Arrays.stream(UICategory.values()).map(UICategoryComponent::new).distinct()
                .collect(Collectors.toList());
        cmcc = (ModuleCategoryComponent) components.get(0).getContents().toArray()[0];
        cmcc.setCurrentCategory(true);
        this.module = clickGui;
    }

    //called every time the ui is created
    @Override
    public void doInit() {
        super.doInit();
        blurTimer.setElapsedMs(0);
    }

    @Override
    public void drawScr(int mouseX, int mouseY, float partialTicks) {
        RenderInfo ri = new RenderInfo(mouseX, mouseY, getFontRenderer(), this);
        getFontRenderer().setScale(height / 450f);
        DrawUtils.drawRect(0, 0, width, height, 0x35000000);

        int x = width / 8;
        int y = height / 6;
        x1 = width - x;
        y1 = height - y;
        int enabledColor = Nexus.getNexus().getThemeManager().getCurrentTheme().getMainColor();
        ResourceLocation logoPath = Nexus.getNexus().getThemeManager().getCurrentTheme().getLogoPath();

        // main container
        RenderUtils.resetColor();
        DrawUtils.drawBorderedRoundedRect(x, y, x1, y1, 1f, 1f, enabledColor, 0xDD0C0C0C);

        vLineX = 2 * x;
        hLineY = (int) (1.5 * y);

        // vertical line
        DrawUtils.drawRect(vLineX, y, vLineX + 1.0f, y1, enabledColor);
        // horizontal line
        DrawUtils.drawRect(x, hLineY, x1, hLineY + 1.0f, enabledColor);

        //logo
        mc.getTextureManager().bindTexture(logoPath);
        int tempExpand = (int) (x * 0.1f);
        Gui.drawModalRectWithCustomSizedTexture(x + tempExpand, y + tempExpand, 0, 0, vLineX - x - (tempExpand * 2), hLineY - y - (tempExpand * 2), vLineX - x - (tempExpand * 2), hLineY - y - (tempExpand * 2));

        // draws each module category component
        PosInfo pi = new PosInfo(x + 5, hLineY + 5);
        components.forEach(component -> pi.moveY(component.updateComponent(pi, ri)));

        // makes the currently selected category component draw its modules
        ScissorUtils.subScissor(vLineX + 1, hLineY, x1, y1, 2);
        PosInfo piL = new PosInfo(vLineX + 5, hLineY);
        cmcc.drawLeft(piL, ri);
        PosInfo piR = new PosInfo(vLineX + (x1 - vLineX) / 2f, hLineY);
        cmcc.drawRight(piR, ri);
        cmcc.subtractFromMaxScrollHeight(y1 - hLineY);

        renderLastList.forEach(Runnable::run);
        renderLastList.clear();

        ScissorUtils.endSubScissor();
        ScissorUtils.resetScissor();
        getFontRenderer().resetScale();
    }

    @Override
    public void mouseClick(int mouseX, int mouseY, int mouseButton) {
        if (alwaysClickedComponent != null) {
            if (alwaysClickedComponent.clickAlwaysClickable(mouseX, mouseY, mouseButton)) {
                return;
            }
        }
        components.forEach(panel -> panel.handleClick(mouseX, mouseY, mouseButton));
        if (mouseX > vLineX && mouseX < x1 && mouseY > hLineY && mouseY < y1) {
            cmcc.clickChildren(mouseX, mouseY, mouseButton);
        }
    }

    public void setCmcc(ModuleCategoryComponent mcc) {
        cmcc.setCurrentCategory(false);
        mcc.setCurrentCategory(true);
        cmcc = mcc;
    }

    public <T extends Component & IAlwaysClickable> void setAlwaysClickedComponent(T component) {
        if (alwaysClickedComponent != null) {
            alwaysClickedComponent.setNotAlwaysClickable();
        }
        this.alwaysClickedComponent = component;
    }

    public <T extends Component & IAlwaysKeyboardInput> void setAlwaysInputComponent(T component) {
        if (alwaysKeyboardInput != null) {
            alwaysKeyboardInput.setNotAlwaysRecieveInput();
        }
        this.alwaysKeyboardInput = component;
    }

    public final FontRendererExtension<?> getFontRenderer() {
        try {
            return module.customFont.getValue() ?
                    Nexus.getInstance().getFonts().MEDIUM_FR.getFontRendererExtension() :
                    ((IFontRenderer) mc.fontRendererObj).getFontRendererExtension();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void addToRenderLastList(Runnable v) {
        renderLastList.add(v);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();
        i = Integer.compare(i, 0);
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            cmcc.scroll(i * 40);
        } else {
            cmcc.scroll(i * 20);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (alwaysKeyboardInput != null) {
            if (alwaysKeyboardInput.recieveInput(keyCode)) {
                return;
            }
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void mouseRelease(int mouseX, int mouseY, int state) {
        components.forEach(component -> component.handleRelease(mouseX, mouseY, state));
        super.mouseRelease(mouseX, mouseY, state);
    }

    @Override
    public void onResize(Minecraft mcIn, int p_175273_2_, int p_175273_3_) {
        super.onResize(mcIn, p_175273_2_, p_175273_3_);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}
