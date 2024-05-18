package us.dev.direkt.gui.tab.tab.tabs;

import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Point;
import us.dev.api.property.Property;
import us.dev.direkt.Wrapper;
import us.dev.direkt.gui.color.ClientColors;
import us.dev.direkt.gui.tab.TabHandler;
import us.dev.direkt.gui.tab.block.TabBlock;
import us.dev.direkt.gui.tab.cursor.cursors.QueuedTabCursor;
import us.dev.direkt.gui.tab.handling.Tabs;
import us.dev.direkt.gui.tab.tab.AbstractTab;
import us.dev.direkt.gui.tab.tab.Tab;
import us.dev.direkt.util.render.OGLRender;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Foundry
 */
public class MultiPropertyTab extends AbstractTab<Property<Map<String, Property>>> {

    private boolean selected;

    private final TabHandler innerHandler = TabHandler.builder()
            .renderCoordinates(0, 0)
            .tabDimensions(92, 12)
            .tabSpacing(2, 0)
            .tabCursor(new QueuedTabCursor())
            .build();

    private final TabBlock properties = stateObject.getValue().values().stream().collect(Tabs.toTabBlock(innerHandler));

    private final Map<Tab<?>, Point> previousTabPositions = new HashMap<>();

    public MultiPropertyTab(TabHandler handler, Property<Map<String, Property>> stateObject, Tab<?> parent, TabBlock children, TabBlock container) {
        super(handler, stateObject, parent, children, container);
    }

    @Override
    public void doInvocation() {
        selected = true;
        handler.disableDefaultKeyListening();

        innerHandler.resetScissorBox();

        int upperOffsets = 0, lowerOffsets = 0,
                initialPos = this.getPosY(),
                requiredOffsets, totalOffsets;
        requiredOffsets = totalOffsets = properties.sizeOf();

        for (Tab<?> curr : this.container) {
            previousTabPositions.put(curr, new Point(curr.getPosX(), curr.getPosY()));

            if (initialPos >= curr.getPosY()) {
                final TabBlock parentBlock = this.findParent().get().getContainerTabBlock();
                int numOffFirst;
                if (totalOffsets < (numOffFirst = (initialPos - parentBlock.getFirst().getPosY()) / this.getHeight())) {
                    curr.setPosY(this.findParent().get().getContainerTabBlock().getFirst().getPosY() + this.getHeight() * ((parentBlock.sizeOf() - numOffFirst - totalOffsets + 1) + ++upperOffsets));
                } else {
                    curr.setPosY(this.findParent().get().getContainerTabBlock().getFirst().getPosY() + this.getHeight() * upperOffsets++);
                }
                requiredOffsets--;
            } else {
                if (requiredOffsets > 0) {
                    curr.setPosY(this.getPosY() + this.getHeight() * (totalOffsets + lowerOffsets++ + 1) + 2);
                } else {
                    curr.setPosY(curr.getPosY() + 2);
                }
            }
        }

        handler.subscribeActionToKey(this, Keyboard.KEY_LEFT, (key, tab) -> {
            if (innerHandler.isDefaultKeyListening()) {
                selected = false;

                for (Tab<?> curr : this.container) {
                    curr.setPosX(previousTabPositions.get(curr).getX());
                    curr.setPosY(previousTabPositions.get(curr).getY());
                }

                handler.unsubscribeSpecificActions(this);
                handler.unsubscribeTabActionFromAll(this);
                handler.enableDefaultKeyListening();
            }
        });

        handler.subscribeTabActionToAll(this, (key, tab) -> innerHandler.doKeyInput(key));
    }

    @Override
    public void renderTabBack() {
        OGLRender.drawRect(this.getPosX(), this.getPosY(), this.getPosX() + this.getWidth(), this.getPosY() + this.getHeight(), 0x95050505);
        if (selected) {
            OGLRender.drawRect(this.getPosX() - 0.5f, this.getPosY() + this.getHeight() * (properties.sizeOf() + 1), this.getPosX() + this.getWidth() + 0.5f, this.getPosY() + this.getHeight() * (properties.sizeOf() + 1) + 2, ClientColors.FADING_GREEN.getColor());
            OGLRender.drawRect(this.getPosX() + 1, this.getPosY() + this.getHeight() * (properties.sizeOf() + 1), this.getPosX() + this.getWidth() - 1, this.getPosY() + this.getHeight() * (properties.sizeOf() + 1) + 2, 0xFF000000);
        }
    }

    @Override
    public void renderTabFront() {
        final FontRenderer font = Wrapper.getFontRenderer();
        String chevron = "\u23CE";
        int chevronColor = 0xCCFFFFFF, labelColor = 0xFFDDDDDD;
        if (selected) {
            OGLRender.drawRect(this.getPosX() + 1f, this.getPosY(), this.getPosX() + this.getWidth() - 1f, this.getPosY() + this.getHeight(), 0xFF050505);
            chevron = "<";
            chevronColor = ClientColors.FADING_GREEN.getColor();
            labelColor = 0xFFFFFFFF;

            innerHandler.setCurrentTabs(properties);
            innerHandler.translateTabs(this.getPosX(), this.getPosY() + this.getHeight());
            innerHandler.doTabRendering();
        }
        font.drawStringWithShadow(this.stateObject.getLabel(), this.getPosX() + 3, this.getPosY() + 2f, labelColor);
        font.drawStringWithShadow(chevron, this.getPosX() + this.getWidth() - font.getStringWidth(chevron) - 3, this.getPosY() + 2.25f, chevronColor);
    }
}
