package systems.sintex.login.logins;

import java.io.IOException;
import systems.sintex.auth.MicrosoftAuth;
import systems.sintex.exception.MicrosoftException;
import systems.sintex.exception.MinecraftException;
import systems.sintex.login.LoginMethod;
import systems.sintex.models.MinecraftSession;
import systems.sintex.models.response.AccessTokenResponse;

public class OAuth2Login extends LoginMethod {
   private MicrosoftAuth microsoftAuth;

   public MinecraftSession login(String code) throws IOException, MicrosoftException, MinecraftException {
      AccessTokenResponse response = this.microsoftAuth.getAccessToken(code);
      return this.getSessionWithAccessToken(response.getAccessToken(), true, response.getRefreshToken());
   }

   public MinecraftSession refreshLogin(String refreshToken) throws IOException, MicrosoftException, MinecraftException {
      AccessTokenResponse response = this.microsoftAuth.refreshAccessToken(refreshToken);
      return this.getSessionWithAccessToken(response.getAccessToken(), true, response.getRefreshToken());
   }

   public OAuth2Login(MicrosoftAuth microsoftAuth) {
      this.microsoftAuth = microsoftAuth;
   }
}
