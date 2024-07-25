package club.bluezenith.ui.draggables;

import club.bluezenith.events.impl.Render2DEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static com.google.common.collect.Lists.reverse;
import static org.lwjgl.input.Mouse.isButtonDown;

public class DraggableRenderer implements IDraggableRenderer {
    private final List<Draggable> elementsToDraw = new ArrayList<>(24);
    private List<Draggable> elementsToDrawReversed;

    private Draggable currentlyDragging;
    private boolean hasPressedLMB, reset;

    private float previousMouseX, previousMouseY;

    public DraggableRenderer() {
        getBlueZenith().getModuleManager()
                .getModules()
                .stream()
                .filter(module -> module instanceof Draggable)
                .map(module -> (Draggable) module)
                .forEach(this::addDraggable);
    }

    @Override
    public void draw(Render2DEvent event) {
        for (Draggable draggable : elementsToDraw) {
            if(!draggable.shouldBeRendered()) continue;

            draggable.draw(event);
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_R) && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            if(!reset) {
                reset = true;
                elementsToDraw.forEach(Draggable::resetPosition);
                getBlueZenith().getNotificationPublisher().postSuccess("Draggables", "Reset positions to default", 2500);
            }
        } else reset = false;
    }

    @Override
    public void doDragging(int mouseX, int mouseY) { //this should be only called in GuiChat
        final boolean isLMBDown = isButtonDown(LMB);

        // we reverse the list because if we don't, when you try to drag one of x elements that are overlapping each other
        // it will drag the bottom element instead of the top one (and we need to drag the top one)

        if(elementsToDrawReversed == null || elementsToDrawReversed.size() != elementsToDraw.size())
            elementsToDrawReversed = reverse(elementsToDraw); //reverse the list if needed

        for (Draggable draggable : elementsToDrawReversed) {
            if(!draggable.shouldBeRendered()) continue;

            if(isLMBDown) {
                if (draggable.isMouseOver(mouseX, mouseY) || currentlyDragging != null) {
                    if(currentlyDragging == null) currentlyDragging = draggable;

                    if(currentlyDragging == draggable) //don't drag 2 elements at the same time
                        handlePositionChange(draggable, mouseX, mouseY);
                }
            } else {
                hasPressedLMB = false;
                currentlyDragging = null;
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (Draggable draggable : elementsToDrawReversed) {
            if(!draggable.shouldBeRendered()) continue;

            if(draggable.isMouseOver(mouseX, mouseY)) {
                if(mouseButton == MMB)
                    draggable.resetPosition();
                else
                    draggable.mouseClicked(mouseX, mouseY, mouseButton);
                break;
            }
        }
    }

    @Override
    public void addDraggable(Draggable draggable) {
        this.elementsToDraw.add(draggable);
    }

    @Override
    public void removeDraggable(Draggable draggable) {
        this.elementsToDraw.remove(draggable);
    }

    private void handlePositionChange(Draggable draggable, int mouseX, int mouseY) {
        float x = draggable.getX(),
                    y = draggable.getY();

        if(!hasPressedLMB) {
            previousMouseX = mouseX - x;
            previousMouseY = mouseY - y;
            hasPressedLMB = true;
        } else {
            x = mouseX - previousMouseX;
            y = mouseY - previousMouseY;

            if(y < -1)
                y = 0;

            if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                draggable.moveTo(draggable.getX(), y);
            else if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
                draggable.moveTo(x, draggable.getY());
            else draggable.moveTo(x, y);
        }
    }

    private static final int LMB = 0, RMB = 1, MMB = 2;
}
