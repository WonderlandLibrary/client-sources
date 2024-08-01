package wtf.diablo.client.gui.draggable;

import best.azura.eventbus.core.EventBus;
import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import wtf.diablo.client.event.impl.client.renderering.EventShader;
import wtf.diablo.client.event.impl.client.renderering.OverlayEvent;

import java.util.ArrayList;
import java.util.List;

public final class DraggableElementHandler {
    private final List<AbstractDraggableElement> abstractDraggableElements = new ArrayList<>();

    public DraggableElementHandler(final EventBus eventBus) {
        eventBus.register(this);
    }

    public void registerDraggableElement(final AbstractDraggableElement abstractDraggableElement) {
        this.abstractDraggableElements.add(abstractDraggableElement);
    }

    public List<AbstractDraggableElement> getDraggableElements() {
        return this.abstractDraggableElements;
    }

    public AbstractDraggableElement getDraggableElementByName(final String name) {
        return this.abstractDraggableElements.stream().filter(abstractDraggableElement -> abstractDraggableElement.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @EventHandler
    private final Listener<OverlayEvent> overlayEventListener = event -> this.abstractDraggableElements.stream().filter(abstractDraggableElement -> abstractDraggableElement.getModule().isEnabled()).forEach(AbstractDraggableElement::onDraw);

    @EventHandler
    private final Listener<EventShader> eventShaderListener = eventShader -> this.abstractDraggableElements.stream().filter(abstractDraggableElement -> abstractDraggableElement.getModule().isEnabled()).forEach(AbstractDraggableElement::shader);

}