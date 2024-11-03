package vestige.util.misc;

import java.util.Arrays;

public enum GPTModel {
   GPT_3_5_TURBO("GPT-3.5 Turbo", "gpt-3.5-turbo"),
   GPT_3_MINI("GPT-3 mini", "ada"),
   GPT_4("GPT-4", "gpt-4"),
   GPT_4_32K("GPT-4 (32k)", "gpt-4-32k");

   private final String prettyName;
   private final String apiName;
   public static final String[] MODELS = (String[])Arrays.stream(values()).map(GPTModel::getPrettyName).toArray((x$0) -> {
      return new String[x$0];
   });

   public String getPrettyName() {
      return this.prettyName;
   }

   public String getApiName() {
      return this.apiName;
   }

   private GPTModel(final String param3, final String param4) {
      this.prettyName = param3;
      this.apiName = param4;
   }

   // $FF: synthetic method
   private static GPTModel[] $values() {
      return new GPTModel[]{GPT_3_5_TURBO, GPT_3_MINI, GPT_4, GPT_4_32K};
   }
}
