package zamorozka.ui;

import net.minecraft.entity.Entity;

import java.util.HashSet;
import java.util.Set;

public class EntityValidator {
    private final static Set<ICheck> checks = new HashSet<>();

    public final boolean validate(Entity entity) {
        for (ICheck check : this.checks) {
            if (check.validate(entity))
                continue;
            return false;
        }
        return true;
    }

    public static void add(ICheck check) {
        checks.add(check);
    }
}
