package dev.excellent.impl.value.mode;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SubMode extends Mode<SubMode> {
    public SubMode(String name) {
        super(name, null);
    }

    public static Mode<?>[] of(String... modes) {
        List<Mode<SubMode>> modeList = new ArrayList<>();
        for (String modeName : modes) {
            modeList.add(new SubMode(modeName));
        }
        return modeList.toArray(new Mode<?>[0]);
    }
}