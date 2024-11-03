package vestige.util.accounts;

import java.util.Random;

public class GenerateRandNicks {
   public static String GenNick() {
      String[] nickbase = new String[]{"Phortus", "Arellinho", "Luizzg", "DaddyCr4z", "Fafuuhw", "Loritoo", "Ticinha", "thales7", "Rakay", "yBanana", "lipxzg", "enicio0", "WinkeB", "pardalduc", "Mickae", "noogueira", "Z0V6FM", "m4cpp", "ubito3", "12kxWt", "Dig0Game", "MegaVoltOf", "nicoolasht", "cr7dedeus", "Munguasao9", "Pedroparas", "Nottdwz", "Rnrjj", "0clickzw", "fsmdasi", "victorsaul", "Viniciusxr", "pepezinho9", "atsdki", "_mecmecc", "yurizazzz", "gui1ff", "Minipulgf", "velhodalanc", "rinegaduz", "marina10h", "MAICON"};
      String randomNick = getRandomNickname(nickbase);
      return randomNick;
   }

   public static String getRandomNickname(String[] nickbase) {
      Random random = new Random();
      String baseNick = nickbase[random.nextInt(nickbase.length)];
      int num1 = random.nextInt(90) + 10;
      int num2 = random.nextInt(90) + 10;
      return baseNick + num1 + num2;
   }
}
