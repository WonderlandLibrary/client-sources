package dev.excellent.impl.font;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Icon {

    SEARCH("a"),
    TAG("b"),
    TAGS("c"),
    BOOKMARK("d"),
    BOOKMARK_EMPTY("e"),
    HEART("f"),
    HEART_EMPTY("g"),
    STAR("h"),
    STAR_EMPTY("i"),
    CHECKMARK("j"),
    CANCEL("k"),
    PLUS("l"),
    MINUS("m"),
    UP("n"),
    LEFT("o"),
    RIGHT("p"),
    DOWN("q"),
    PAW("r"),
    SETTING("s"),
    FLASH("t"),
    FLASH_EMPTY("u"),
    DOWN_OPEN("v"),
    LEFT_OPEN("w"),
    UP_OPEN("x"),
    RIGHT_OPEN("y"),
    YOUTUBE("z"),
    LOCATION("A");
    private final String character;

}
