package us.dev.direkt.module.internal.core.autocheat.anticheat;

import us.dev.api.factory.ClassFactory;
import us.dev.api.interfaces.Aliased;
import us.dev.api.interfaces.Labeled;
import us.dev.direkt.module.internal.core.autocheat.check.Check;

import java.util.Arrays;

/**
 * @author Foundry
 */
public abstract class AntiCheat implements Labeled, Aliased {
    private Check[] checks;
    private final String label;
    private final String[] aliases;

    public AntiCheat() {
        if (!this.getClass().isAnnotationPresent(AntiCheatData.class)) {
            throw new RuntimeException("AntiCheat " + this.getClass().getSimpleName() + "is malformed, no AntiCheatData annotation found");
        }
        final AntiCheatData data = this.getClass().getDeclaredAnnotation(AntiCheatData.class);
        this.label = data.label();
        this.aliases = data.aliases();

        if (this.getClass().isAnnotationPresent(AntiCheatChecks.class)) {
            final AntiCheatChecks checkClasses = this.getClass().getDeclaredAnnotation(AntiCheatChecks.class);
            checks = Arrays.stream(checkClasses.value())
                    .map(clazz -> ClassFactory.create(clazz))
                    .toArray(Check[]::new);
        } else {
            checks = new Check[0];
        }
    }

    public Check[] getChecks() {
        return this.checks;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String[] getAliases() {
        return this.aliases;
    }
}
