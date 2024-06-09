package exhibition.util.security;

import java.util.Arrays;
import java.util.function.ToIntFunction;

public class StringEcryption {
   public static String decrypt(String key, String encrypted) {
      int[] input = Arrays.stream(encrypted.split(",")).mapToInt(Integer::parseInt).toArray();
      StringBuilder output = new StringBuilder();

      for(int i = 0; i < input.length; ++i) {
         output.append((char)(input[i] - 48 ^ key.charAt(i % (key.length() - 1))));
      }

      return output.toString();
   }
}
