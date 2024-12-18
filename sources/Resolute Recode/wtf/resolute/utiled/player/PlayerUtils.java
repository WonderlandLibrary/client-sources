package wtf.resolute.utiled.player;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class PlayerUtils {

    private final Pattern NAME_REGEX = Pattern.compile("^[A-z�-�0-9_]{3,16}$");

    public boolean isNameValid(String name) {
        return NAME_REGEX.matcher(name).matches();
    }
}
