package exhibition.management.spotify.othershit;

public class SpotifyResource {
   private String name;
   private String uri;
   private SpotifyResource.Location location;

   public SpotifyResource() {
   }

   public SpotifyResource(String name, String uri, SpotifyResource.Location location) {
      this.name = name;
      this.uri = uri;
      this.location = location;
   }

   public String getName() {
      return this.name;
   }

   public String getId() {
      if (this.uri != null && this.uri.startsWith("spotify:")) {
         String[] split = this.uri.split(":");
         return split.length != 3 ? null : split[2];
      } else {
         return null;
      }
   }

   public String getUri() {
      return this.uri;
   }

   public SpotifyResource.Location getLocation() {
      return this.location;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         SpotifyResource that;
         label41: {
            that = (SpotifyResource)o;
            if (this.name != null) {
               if (this.name.equals(that.name)) {
                  break label41;
               }
            } else if (that.name == null) {
               break label41;
            }

            return false;
         }

         if (this.uri != null) {
            if (this.uri.equals(that.uri)) {
               return that.location == null ? true : (this.location != null ? this.location.equals(that.location) : false);
            }
         } else if (that.uri == null) {
            return that.location == null ? true : (this.location != null ? this.location.equals(that.location) : false);
         }

         return false;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.name != null ? this.name.hashCode() : 0;
      result = 31 * result + (this.uri != null ? this.uri.hashCode() : 0);
      result = 31 * result + (this.location != null ? this.location.hashCode() : 0);
      return result;
   }

   public class Location {
      private String og;

      public String getOg() {
         return this.og;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            SpotifyResource.Location location = (SpotifyResource.Location)o;
            return location.og == null ? true : (this.og != null ? this.og.equals(location.og) : false);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.og != null ? this.og.hashCode() : 0;
      }
   }
}
