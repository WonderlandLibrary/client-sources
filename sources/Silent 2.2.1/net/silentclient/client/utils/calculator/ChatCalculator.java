package net.silentclient.client.utils.calculator;

import net.minecraft.client.gui.GuiTextField;

import java.text.DecimalFormat;

public class ChatCalculator {
    private static final DecimalFormat df = new DecimalFormat("#,##0.##");

    public static void changeDecimalFormat(String format) {
        df.applyPattern(format);
    }

    public static boolean runExpression(GuiTextField field) {
        return (runExprReplace(field) || runExprAdd(field));
    }

    private static boolean runExprReplace(GuiTextField field) {
        String originalText = field.getText();
        int cursor = field.getCursorPosition();
        try {
            String word = ChatHelper.getWord(originalText, cursor);
            double solution = MathEngine.eval(word);
            String solStr = df.format(solution);
            return ChatHelper.replaceWord(field, solStr);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return false;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private static boolean runExprAdd(GuiTextField field) {
        String originalText = field.getText();
        int cursor = field.getCursorPosition();
        try {
            String word = ChatHelper.getWord(originalText, cursor);
            if (!word.endsWith("="))
                return false;
            word = word.substring(0, word.length() - 1);
            double solution = MathEngine.eval(word);
            String solStr = df.format(solution);
            return ChatHelper.addWordAfterIndex(field, ChatHelper.getEndOfWord(originalText, cursor), solStr);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return false;
        } catch (RuntimeException e) {
            return false;
        }
    }
}
