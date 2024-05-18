package us.dev.direkt.module.internal.core.autocheat.check;

import us.dev.api.interfaces.Labeled;
import us.dev.direkt.module.internal.core.autocheat.anticheat.AntiCheat;

import java.util.concurrent.Callable;

/**
 * @author Foundry
 */
public abstract class Check implements Callable<CheckStatus>, Labeled {
    private final String label;
    private final Class<? extends AntiCheat> result;

    public Check() {
        if (!this.getClass().isAnnotationPresent(CheckData.class)) {
            throw new RuntimeException("Check " + this.getClass().getSimpleName() + "is malformed, no CheckData annotation found");
        }
        final CheckData data = this.getClass().getDeclaredAnnotation(CheckData.class);
        this.label = data.label();
        this.result = data.result();
    }

    public Class<? extends AntiCheat> getPassConclusion() {
        return this.result;
    }

    public String getLabel() {
        return this.label;
    }
}
