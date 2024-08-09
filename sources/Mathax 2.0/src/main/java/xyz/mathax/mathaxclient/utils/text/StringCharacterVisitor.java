package xyz.mathax.mathaxclient.utils.text;

import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.Style;

public class StringCharacterVisitor implements CharacterVisitor {

    public StringBuilder result = new StringBuilder();

    @Override
    public boolean accept(int index, Style style, int j) {
        result.append((char)j);
        return true;
    }
}
