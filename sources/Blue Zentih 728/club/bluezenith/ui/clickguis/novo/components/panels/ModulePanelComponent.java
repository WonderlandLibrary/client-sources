package club.bluezenith.ui.clickguis.novo.components.panels;

import club.bluezenith.module.Module;
import club.bluezenith.module.modules.render.ClickGUI;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.ui.clickguis.novo.AncientNovoGUI;
import club.bluezenith.ui.clickguis.novo.components.Component;
import club.bluezenith.ui.clickguis.novo.components.modules.ModuleComponent;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.font.TFontRenderer;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.ui.clickguis.novo.AncientNovoGUI.TITLE_RECT_HEIGHT;
import static java.lang.Boolean.compare;
import static java.util.stream.Collectors.toList;

public class ModulePanelComponent extends Component {
    private static final TFontRenderer title = FontUtil.createFont("posaytightposaycleanposayfresh2", 38);

    private final List<ModuleComponent> moduleComponentList;

    private final AncientNovoGUI ancientNovoGUI;

    private ModuleComponent expandedComponent;

    public ModulePanelComponent(AncientNovoGUI ancientNovoGUI, List<Module> moduleList, float x, float y) {
        super(x, y);

        final AtomicReference<Float> height = new AtomicReference<>(y + TITLE_RECT_HEIGHT); //can't change the y variable in a lambda expression

        this.moduleComponentList = moduleList.stream().map(module -> { //convert a list of modules into a list of ModuleComponents (drawable modules)
            final ModuleComponent component = new ModuleComponent(this, ancientNovoGUI, module, x, TITLE_RECT_HEIGHT);
            component.ownedBy(module);
            component.setWidth(100);
            component.setHeight(15);
            height.set(height.get() + component.getHeight());
            return component;
        }).sorted((before, after) -> -compare(before.getOwner() instanceof HUD, after.getOwner() instanceof HUD)).collect(toList());

        this.width = 100;
        this.height = height.get();

        this.targetX = x;
        this.targetY = y;

        this.ancientNovoGUI = ancientNovoGUI;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void draw(int mouseX, int mouseY, ScaledResolution scaledResolution) {
        GL11.glPushMatrix();

        float prevX = this.x, prevY = this.y;

        if(this.isDragging) {
            if (!setPrevXY) {
                this.prevX = mouseX - x;
                this.prevY = mouseY - y;
                this.setPrevXY = true;
            } else {
                this.x = mouseX - this.prevX;
                this.y = mouseY - this.prevY;
            }
        }

        RenderUtil.rect(x, y, x + width, y + TITLE_RECT_HEIGHT, ancientNovoGUI.getGuiAccentColor());

        updateSize();

        float moduleHeight = y + TITLE_RECT_HEIGHT;

        for (ModuleComponent component : this.moduleComponentList) {
            component.addToPosition(x - prevX, y - prevY);
            component.moveTo(component.getX(), moduleHeight);

            if(shown) {
                component.draw(mouseX, mouseY, scaledResolution);
                moduleHeight += component.getHeight();
            }
        }

        title.drawString(identifier, x + 2, y + 2, -1, true);

        GL11.glPopMatrix();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 15) {
            if(mouseButton == 0)
             isDragging = true;
            else if(mouseButton == 1)
                this.shown = !this.shown;
        }

        if(this.shown) {
            for (ModuleComponent moduleComponent : this.moduleComponentList) {
                if (moduleComponent.isMouseOver(mouseX, mouseY)) {
                    moduleComponent.mouseClicked(mouseX, mouseY, mouseButton);
                    break;
                }
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        isDragging = false;
        setPrevXY = false;
        if(this.shown) {
            for (ModuleComponent moduleComponent : this.moduleComponentList) {
                moduleComponent.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

    private float prevX, prevY, targetX, targetY;
    private boolean isDragging, setPrevXY;

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if(this.shown) {
            for (ModuleComponent moduleComponent : this.moduleComponentList) {
                if (moduleComponent.shouldLockDragging()) {
                    moduleComponent.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
                }
            }
        }
    }

    public void updateSize() {
        final AtomicReference<Float> height = new AtomicReference<>(y + TITLE_RECT_HEIGHT);

        this.moduleComponentList.forEach(module ->
            height.set(height.get() + module.getHeight())
        );

        this.height = height.get();
    }

    @Override
    public boolean shouldLockDragging() {
        return isDragging;
    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return isMouseInBounds(mouseX, mouseY) || isDragging;
    }

    @Override
    public void keyTyped(char key, int keyCode) {
        if(this.shown) {
            for (ModuleComponent component : this.moduleComponentList) {
                if (component.isAcceptingKeypresses()) {
                    component.keyTyped(key, keyCode);
                }
            }
        }
    }

    @Override
    public boolean isAcceptingKeypresses() {
        return true;
    }

    @Override
    public void onGuiClosed() {
        for (ModuleComponent component : moduleComponentList) {
            component.onGuiClosed();
        }
    }

    @Override
    public void moveTo(float x, float y) {
        super.moveTo(x, y);

        for (ModuleComponent moduleComponent : this.moduleComponentList) {
            moduleComponent.moveTo(x, moduleComponent.getY());
        }
    }

    public void componentExpanded(ModuleComponent expandedComponent) {
        if(this.expandedComponent != null && getBlueZenith().getModuleManager().getAndCast(ClickGUI.class).closePrevious.get())
            this.expandedComponent.collapse();
        this.expandedComponent = expandedComponent;
    }

    public void componentCollapsed(ModuleComponent collapsedComponent) {
        if(this.expandedComponent == collapsedComponent)
            this.expandedComponent = null;
    }
}
