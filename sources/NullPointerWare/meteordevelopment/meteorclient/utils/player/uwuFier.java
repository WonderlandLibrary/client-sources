package meteordevelopment.meteorclient.utils.player;

import java.util.Random;

public class uwuFier {

    private static final String[] facesArray = new String[] {
        " (・`ω´・) ",
        " ;;w;; ",
        " OwO ",
        " UwU ",
        " >w< ",
        " ^w^ ",
        " ÚwÚ ",
        " ^-^ ",
        " :3 ",
        " x3 ",
    };
    private static final String[] expressionArray = new String[] {
        " nyaa~~ ",
        " mya ",
        " rawr ",
        " rawr x3 ",
        " rawr XD",
        " XD ",
        " meow ",
        " meow~ ",
    };
    private static final String[] actionArray = new  String[] {
        " *blushes* ",
        " *whispers to self* ",
        " *cries* ",
        " *screams* ",
        " *sweats* ",
        " *twerks* ",
        " *runs away* ",
        " *screeches* ",
        " *walks away* ",
        " *sees bulge* ",
        " *looks at you* ",
        " *notices buldge* ",
        " *starts twerking* ",
        " *huggles tightly* ",
        " *boops your nose* ",
        " *notices bulge* ",
        " *plays with hair* "
    };

    public static String uwuify(String message, boolean faces, boolean expressions, boolean actions) {
        String[] words = message.split("\\s+");
        StringBuilder uwuText = new StringBuilder();
        Random random = new Random();

        double probability = 0.30 / 4;

        for (String word : words) {
            String replaced = word.replaceAll("[rl]", "w").replaceAll("[RL]", "W");

            if (random.nextDouble() <= probability) {
                uwuText.append(addStutter(replaced)).append(" ");
            } else if (random.nextDouble() <= probability && faces) {
                uwuText.append(replaced).append(facesArray[random.nextInt(facesArray.length)]);
            } else if (random.nextDouble() <= probability && expressions) {
                uwuText.append(replaced).append(expressionArray[random.nextInt(expressionArray.length)]);
            } else if (random.nextDouble() <= probability && actions) {
                uwuText.append(replaced).append(actionArray[random.nextInt(actionArray.length)]);
            } else {
                uwuText.append(replaced).append(" ");
            }
        }

        return uwuText.toString().replaceAll(" {2}", " ");
    }

    private static String addStutter(String word) {
        Random random = new Random();
        StringBuilder output = new StringBuilder();

        int stutterLength = random.nextInt(2) + 1;
        for (int i = 0; i < stutterLength; i++) {
            output.append(word.charAt(0)).append("-");
        }
        output.append(word);

        return output.toString();
    }
}
