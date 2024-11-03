package systems.sintex.login;

import java.io.IOException;
import systems.sintex.auth.MicrosoftAuth;
import systems.sintex.auth.MinecraftAuth;
import systems.sintex.exception.MicrosoftException;
import systems.sintex.exception.MinecraftException;
import systems.sintex.models.MinecraftSession;
import systems.sintex.models.response.MinecraftProfileResponse;
import systems.sintex.models.response.XSTSResponse;
import systems.sintex.models.response.XboxLiveAuthResponse;

public class LoginMethod {
   public MinecraftSession getSessionWithAccessToken(String accessToken, boolean oAuth2, String refreshToken) throws IOException, MicrosoftException, MinecraftException {
      XboxLiveAuthResponse xboxLiveAuthResponse = MicrosoftAuth.getXboxLiveAuthResponse(accessToken, oAuth2);
      XSTSResponse xstsResponse = MicrosoftAuth.getXSTSResponse(xboxLiveAuthResponse.getAccessToken());
      String bearerToken = MinecraftAuth.getMinecraftBearerToken(xboxLiveAuthResponse.getUserHash(), xstsResponse.getAccessToken());
      MinecraftProfileResponse minecraftProfile = MinecraftAuth.getMinecraftProfile(bearerToken);
      return new MinecraftSession(minecraftProfile.getName(), minecraftProfile.getUuid(), bearerToken, refreshToken);
   }
}
