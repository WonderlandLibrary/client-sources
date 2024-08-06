package club.strifeclient.ui.implementations.hud.clickgui;

import club.strifeclient.util.callback.VariableCallback;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class ExtendableComponent<T> extends Component<T> implements GuiExtendable {
    private boolean extended;
    private final List<VariableCallback<Boolean>> extendCallbacks;
    public ExtendableComponent(T object, Theme theme, Component<?> parent, float x, float y, float width, float height) {
        super(object, theme, parent, x, y, width, height);
        extendCallbacks = new ArrayList<>();
    }

    public void addExtendCallback(VariableCallback<Boolean> extendCallback) {
        if(!extendCallbacks.contains(extendCallback))
            extendCallbacks.add(extendCallback);
    }

    public void setExtended(boolean extended) {
        if(this.extended == extended) return;
        this.extended = extended;
        runCallbacks(extended);
    }

    public void runCallbacks(boolean extended) {
        extendCallbacks.forEach(callback -> callback.callback(extended));
    }

    @Override
    public boolean isExtended() {
        return extended;
    }
}
