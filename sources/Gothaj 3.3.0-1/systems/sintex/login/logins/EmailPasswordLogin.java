package systems.sintex.login.logins;

import java.io.IOException;
import systems.sintex.auth.MicrosoftAuth;
import systems.sintex.exception.MicrosoftException;
import systems.sintex.exception.MinecraftException;
import systems.sintex.login.LoginMethod;
import systems.sintex.models.MinecraftSession;
import systems.sintex.models.response.AccessTokenResponse;

public class EmailPasswordLogin extends LoginMethod {
   private String email;
   private String password;

   public MinecraftSession login() throws IOException, MicrosoftException, MinecraftException {
      AccessTokenResponse response = MicrosoftAuth.getAccessTokenWithCredentials(this.email, this.password);
      return this.getSessionWithAccessToken(response.getAccessToken(), false, null);
   }

   public EmailPasswordLogin(String email, String password) {
      this.email = email;
      this.password = password;
   }
}
