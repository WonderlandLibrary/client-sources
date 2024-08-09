/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.callback;

import imgui.ImGuiInputTextCallbackData;
import java.util.function.Consumer;

public abstract class ImGuiInputTextCallback
implements Consumer<ImGuiInputTextCallbackData> {
    @Override
    public final void accept(long l) {
        this.accept(new ImGuiInputTextCallbackData(l));
    }
}

