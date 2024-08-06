package club.strifeclient.setting;

import java.util.function.Supplier;

public interface MultiSelectEnum extends SerializableEnum {
    Supplier<Boolean> getDependency();
}
