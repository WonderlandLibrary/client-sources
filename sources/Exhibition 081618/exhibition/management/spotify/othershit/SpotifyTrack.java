package exhibition.management.spotify.othershit;

import com.google.gson.annotations.SerializedName;

public class SpotifyTrack {
   @SerializedName("track_resource")
   private SpotifyResource trackInformation;
   @SerializedName("artist_resource")
   private SpotifyResource artistInformation;
   @SerializedName("album_resource")
   private SpotifyResource albumInformation;
   private int length;
   @SerializedName("track_type")
   private SpotifyTrack.Type type;
   private String image;

   public SpotifyTrack() {
   }

   public SpotifyTrack(SpotifyResource trackInformation, SpotifyResource artistInformation, SpotifyResource albumInformation, int length, SpotifyTrack.Type type) {
      this.trackInformation = trackInformation;
      this.artistInformation = artistInformation;
      this.albumInformation = albumInformation;
      this.length = length;
      this.type = type;
   }

   public SpotifyResource getTrackInformation() {
      return this.trackInformation;
   }

   public SpotifyResource getArtistInformation() {
      return this.artistInformation;
   }

   public SpotifyResource getAlbumInformation() {
      return this.albumInformation;
   }

   public int getLength() {
      return this.length;
   }

   public SpotifyTrack.Type getType() {
      return this.type;
   }

   public String getImage() {
      return this.image;
   }

   public void setImage(String image) {
      this.image = image;
   }

   public boolean hasTrackInformation() {
      return this.getTrackInformation() != null && this.getAlbumInformation() != null && this.getArtistInformation() != null;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         SpotifyTrack that = (SpotifyTrack)o;
         if (this.length != that.length) {
            return false;
         } else {
            if (this.trackInformation != null) {
               if (!this.trackInformation.equals(that.trackInformation)) {
                  return false;
               }
            } else if (that.trackInformation != null) {
               return false;
            }

            if (this.artistInformation != null) {
               if (!this.artistInformation.equals(that.artistInformation)) {
                  return false;
               }
            } else if (that.artistInformation != null) {
               return false;
            }

            if (this.albumInformation != null) {
               if (!this.albumInformation.equals(that.albumInformation)) {
                  return false;
               }
            } else if (that.albumInformation != null) {
               return false;
            }

            return this.type == that.type;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.trackInformation != null ? this.trackInformation.hashCode() : 0;
      result = 31 * result + (this.artistInformation != null ? this.artistInformation.hashCode() : 0);
      result = 31 * result + (this.albumInformation != null ? this.albumInformation.hashCode() : 0);
      result = 31 * result + this.length;
      result = 31 * result + (this.type != null ? this.type.hashCode() : 0);
      return result;
   }

   public static enum Type {
      normal,
      ad;
   }
}
