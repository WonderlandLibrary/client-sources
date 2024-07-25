package club.bluezenith.ui.clickguis.novo.components.modules;

import club.bluezenith.module.Module;
import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.types.*;
import club.bluezenith.ui.clickguis.novo.AncientNovoGUI;
import club.bluezenith.ui.clickguis.novo.components.Component;
import club.bluezenith.ui.clickguis.novo.components.modules.values.*;
import club.bluezenith.ui.clickguis.novo.components.modules.values.list.ListComponent;
import club.bluezenith.ui.clickguis.novo.components.panels.ModulePanelComponent;
import club.bluezenith.ui.clickgui.ClickGui;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.font.TFontRenderer;
import club.bluezenith.util.render.ColorUtil;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.awt.Color;
import java.util.List;

import static club.bluezenith.util.render.ColorUtil.transitionBetween;
import static club.bluezenith.util.render.RenderUtil.*;
import static java.lang.Math.abs;
import static java.util.stream.Collectors.toList;
import static net.minecraft.client.renderer.GlStateManager.*;
import static org.lwjgl.opengl.GL11.*;

public class ModuleComponent extends Component {
    private static final int BACKGROUND_COLOR = new Color(0, 0, 0, 200).getRGB();

    private static final TFontRenderer nameFont = FontUtil.createFont("posaytightposaycleanposayfresh2", 36);

    private final Module module;

    private boolean expanded;

    private final List<Component> valueComponents;

    private float expandProgress;

    private final AncientNovoGUI ancientNovoGUI;
    private final ModulePanelComponent parent;

    private boolean listeningForKey;

    public ModuleComponent(ModulePanelComponent parent, AncientNovoGUI ancientNovoGUI, Module module, float x, float y) {
        super(x, y);
        this.parent = parent;
        this.module = module;

        valueComponents = module.getValues().stream().map(value -> {
            Component theComponent;
            
            if(value instanceof ModeValue) {
                theComponent = new ModeComponent(value, x, y);
                theComponent.setHeight(15);
            }
            else if(value instanceof ActionValue) {
                theComponent = new ModeComponent(value, x, y);
                theComponent.setHeight(15);
            }
            else if(value instanceof BooleanValue) {
                theComponent = new BooleanComponent(this, (BooleanValue) value, x, y);
                theComponent.setHeight(10);
                theComponent.setWidth(10);
            }
            else if(value instanceof ColorValue) {
                theComponent = new ColorPickerComponent((ColorValue) value, x, y);
                theComponent.setHeight(12);
            }
            else if(value instanceof ExtendedModeValue) {
                theComponent = new ModeComponent(value, x, y);
                theComponent.setHeight(15);
            }
            else if(value instanceof FloatValue) {
                theComponent = new SliderComponent(value, x, y);
                theComponent.setHeight(17);
                theComponent.setWidth(width);
            }
            else if(value instanceof IntegerValue) {
                theComponent = new SliderComponent(value, x, y);
                theComponent.setHeight(17);
                theComponent.setWidth(width);
            }
            else if(value instanceof FontValue) {
                theComponent = new ModeComponent(value, x, y);
                theComponent.setHeight(15);
            }
            else if(value instanceof ListValue) {
                theComponent = new ListComponent((ListValue) value, x, y);
                theComponent.setHeight(17);
            }
            else if(value instanceof StringValue) {
                theComponent = new StringComponent((StringValue) value, x, y);
                theComponent.setHeight(15);
            }
            else throw new IllegalStateException("[Click GUI] Unknown value type: " + value.getClass());

            theComponent.ownedBy(value);

            return theComponent;
        }).peek(value ->
            value.moveTo(x, y + height)
        ).collect(toList());

        this.ancientNovoGUI = ancientNovoGUI;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);

        for (Component valueComponent : valueComponents) {
            if(valueComponent.shouldUpdateWidth())
                valueComponent.setWidth(width);
        }
    }

    @Override
    public float getHeight() {
        if(this.expanded) {
            float height = this.height + 9;

            for (Component valueComponent : this.valueComponents) {
                if(valueComponent.getOwner() instanceof Value<?> && ((Value<?>) valueComponent.getOwner()).isVisible())
                  height += valueComponent.getHeight();
            }

            final Component lastComponent = valueComponents.get(valueComponents.size() - 1);

            float reducer = 1.7F;

            if(lastComponent instanceof ListComponent) {
                ListComponent listComponent = (ListComponent) lastComponent;
                if(listComponent.expanded)
                    reducer = 3F + listComponent.parent.getOptions().size();
            } else if(lastComponent instanceof StringComponent) {
                reducer = 2.2F;
            }
            height -= lastComponent.getHeight() / reducer;
            return height;
        }
        return this.height;
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
        RenderUtil.rect(x, y, x + getWidth(), y + getHeight(), BACKGROUND_COLOR);

        final boolean isHovering = isMouseOverName(mouseX, mouseY);

        Color color = Color.WHITE;

        final boolean doToggledColor = module.getState() || module.toggleAlpha > 0.05F;

        if(doToggledColor) {
            module.toggleAlpha = animate(module.getState() ? .8F : 0F, module.toggleAlpha, 0.1F);

            final float[] activeColor = ColorUtil.get(ancientNovoGUI.getGuiAccentColor()),
                    normalColor = new float[] { 1F, 1F, 1F };

            color = transitionBetween(module.toggleAlpha, normalColor, activeColor);
        }

        if(isHovering || module.hoverAlpha > 0.08F) {
            module.hoverAlpha = animate(isHovering ? 0.6F : 0F, module.hoverAlpha, isHovering ? 0.1F : 0.25F);

            final float[] rect_rgba = ColorUtil.get(ancientNovoGUI.getGuiAccentColor());

            rect_rgba[3] *= module.hoverAlpha;


            int rectColor = 0;

            try {
                rectColor = new Color(
                        (int) (abs(rect_rgba[0]) * 255F),
                        (int) (abs(rect_rgba[1]) * 255F),
                        (int) (abs(rect_rgba[2]) * 255F),
                        (int) (abs(rect_rgba[3]) * 255F)
                ).getRGB();
            } catch (Exception exception) {
                System.err.println("novo gui color error");
            }

            RenderUtil.rect(x, y, x + getWidth(), y + height, rectColor);
        }

        nameFont.drawString(
                module.getName() + (listeningForKey ? " [...]" : ""),
                2 + x,
                y + (height/2F - nameFont.getHeight(module.getName())/2F),
                color.getRGB(),
                true
        );

        if(!module.getValues().isEmpty()) {
            expandProgress = this.expanded ? animate(-3, expandProgress, 0.1F)
                        : animate(1, expandProgress, 0.1F);


            pushMatrix();

            translate(x, y + 1, 0);
            translate(0, -expandProgress, 0);

            start2D(GL_LINE_STRIP);

            if(doToggledColor) {
               glColor3f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
            }

            final float mid = height / 2F;

            glVertex2d(width - 9, mid - 2);
            glVertex2d(width - 6, mid + (2 * expandProgress));
            glVertex2d(width - 3, mid - 2);

            end2D();
            popMatrix();
        }

        if(this.expanded) {
            for (Component valueComponent : this.valueComponents) {
                if(valueComponent.getOwner() instanceof Value<?>) {
                    if(((Value<?>) valueComponent.getOwner()).isVisible())
                        valueComponent.draw(mouseX, mouseY, scaledResolution);
                }
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(isMouseOverName(mouseX, mouseY)) {
            if (mouseButton == 0)
                module.toggle();
            else if (mouseButton == 1) {
                if(!listeningForKey && !this.valueComponents.isEmpty()) {
                    expanded = !expanded;
                    if (expanded)
                        parent.componentExpanded(this);
                    else parent.componentCollapsed(this);
                } else if(listeningForKey) {
                    module.setKeybind(0);
                    listeningForKey = false;
                }
            } else if(mouseButton == 2)
                listeningForKey = !listeningForKey;
        }

        if(this.expanded) {
            for (Component valueComponent : this.valueComponents) {
                if(valueComponent.isMouseOver(mouseX, mouseY)) {
                    valueComponent.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if(this.expanded) {
            for (Component valueComponent : this.valueComponents) {
                valueComponent.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if(this.expanded) {
            for (Component valueComponent : this.valueComponents) {
                if(valueComponent.shouldLockDragging()) {
                    valueComponent.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
                }
            }
        }
    }

    @Override
    public void keyTyped(char key, int keyCode) {
        if(listeningForKey) {
            if(keyCode != Keyboard.KEY_BACK)
                module.setKeybind(keyCode);
            listeningForKey = false;
            return;
        }
        if(this.expanded) {
            for (Component valueComponent : this.valueComponents) {
                if(valueComponent.isAcceptingKeypresses()) {
                    valueComponent.keyTyped(key, keyCode);
                }
            }
        }
    }

    @Override
    public boolean isAcceptingKeypresses() {
        return true;
    }

    @Override
    public boolean shouldLockDragging() {
        for (Component valueComponent : this.valueComponents) {
            if(valueComponent.shouldLockDragging())
                return true;
        }

        return false;
    }

    private boolean isMouseOverName(int mouseX, int mouseY) {
        return ClickGui.i(mouseX, mouseY, getX(), getY(), getX() + getWidth(), getY() + height);
    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return isMouseInBounds(mouseX, mouseY);
    }

    @Override
    public void onGuiClosed() {
        for (Component valueComponent : this.valueComponents) {
            valueComponent.onGuiClosed();
        }
        listeningForKey = false;
    }

    @Override
    public void moveTo(float x, float y) {
        super.moveTo(x, y);

        y += height + 2.5F;

        for (Component valueComponent : this.valueComponents) {
            if(valueComponent.getOwner() instanceof Value<?> && ((Value<?>) valueComponent.getOwner()).isVisible()) {
                valueComponent.moveTo(x, y);
                y += valueComponent.getHeight();
            }
        }
    }

    public void collapse() {
        this.expanded = false;
    }
}
