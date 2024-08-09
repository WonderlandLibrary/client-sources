package dev.excellent.impl.util.render.text;

import dev.excellent.impl.font.Font;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@UtilityClass
public class TextUtils {

    public final String ALLOWED_TO_SESSION = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
            + "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя"
            + "0123456789_";
    public final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    public final String NUMBERS = "1234567890";
    public final String EMPTY = "";

    public String removeForbiddenCharacters(String input, String allowedCharacters) {
        StringBuilder result = new StringBuilder();

        for (char ch : input.toCharArray()) {
            if (allowedCharacters.indexOf(ch) != -1) {
                result.append(ch);
            }
        }

        return result.toString();
    }

    public <T> boolean containsAll(List<T> mainList, List<T> findList) {
        return new HashSet<>(mainList).containsAll(findList);
    }

    public List<String> splitLine(String longLine, Font font, float maxLineWidth, String splitter) {
        List<String> splitLines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        float currentLineWidth = 0;

        String[] words = longLine.trim().split(":");

        for (String word : words) {
            float wordWidth = font.getWidth(word);

            if (currentLineWidth + wordWidth <= maxLineWidth) {
                currentLine.append(word).append(splitter);
                currentLineWidth += wordWidth + font.getWidth(splitter);
            } else {
                splitLines.add(currentLine.toString().trim());
                currentLine = new StringBuilder(word + splitter);
                currentLineWidth = wordWidth + font.getWidth(splitter);
            }
        }

        if (!currentLine.isEmpty()) {
            splitLines.add(currentLine.toString().trim());
        }

        return splitLines;
    }
}