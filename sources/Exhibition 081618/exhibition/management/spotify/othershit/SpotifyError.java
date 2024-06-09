package exhibition.management.spotify.othershit;

public enum SpotifyError {
   INVALID_OAUTH_TOKEN(4102),
   EXPIRED_OAUTH_TOKEN(4103),
   INVALID_CSRF_TOKEN(4107),
   OAUTH_TOKEN_INVALID_FOR_USER(4108),
   NO_USER_LOGGED_IN(4110),
   UNKNOWN(-1);

   private int id;

   private SpotifyError(int id) {
      this.id = id;
   }

   public int getId() {
      return this.id;
   }

   public static SpotifyError byId(int id) {
      SpotifyError[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         SpotifyError spotifyError = var1[var3];
         if (spotifyError.getId() == id) {
            return spotifyError;
         }
      }

      return null;
   }
}
