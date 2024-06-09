package exhibition.management.spotify.othershit;

import com.google.common.base.Charsets;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exhibition.management.spotify.Callback;
import exhibition.management.spotify.HttpResponseCallback;
import exhibition.management.spotify.SpotifyManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;

public class SpotifyWebAPI {
   private static final String BASE_URL = "https://api.spotify.com/v1";
   private static final int TIMEOUT = 10000;
   private static final Pattern JS_PATTERN = Pattern.compile("\\s+Spotify\\.Entity = (.*);");
   private final Cache TRACK_IMAGE_LOOKUP = CacheBuilder.newBuilder().maximumSize(500L).build();
   private final Cache SPOTIFY_SEARCH_RESULTS = CacheBuilder.newBuilder().maximumSize(500L).build();

   public void resolveTrackImage(SpotifyTrack track) {
      String id = track.getTrackInformation().getId();
      if (id != null) {
         String optional = (String)this.TRACK_IMAGE_LOOKUP.getIfPresent(id);
         if (optional != null) {
            track.setImage(optional);
         } else {
            try {
               String response = IOUtils.toString(new URL(track.getTrackInformation().getLocation().getOg()));
               String[] var5 = response.split("\n");
               int var6 = var5.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  String s = var5[var7];
                  Matcher matcher = JS_PATTERN.matcher(s);
                  if (matcher.matches()) {
                     if (!this.resolveTrackImage(track, id, (new JsonParser()).parse(matcher.group(1)))) {
                        System.out.println("Could not resolve image for track " + id + "!");
                     }
                     break;
                  }
               }
            } catch (Exception var10) {
               System.out.println("Could not resolve image for track " + id + "! " + var10);
            }
         }
      }

   }

   public void searchSpotify(final String text, final Callback callback) {
      SpotifyTrack optional = (SpotifyTrack)this.SPOTIFY_SEARCH_RESULTS.getIfPresent(text);
      if (optional != null) {
         callback.call(optional);
      } else {
         try {
            SpotifyHttpClient.get("https://api.spotify.com/v1/search?q=" + URLEncoder.encode(text, "UTF-8") + "&type=track&limit=1", Collections.emptyMap(), 5000, false, SpotifyManager.CLIENT_NIO_EVENTLOOP, new HttpResponseCallback() {
               public void call(String response, int responseCode, Throwable throwable) {
                  if (throwable != null) {
                     System.out.println("Could not execute search query for text \"" + text + "\"! " + throwable);
                  } else if (response != null) {
                     JsonElement element = (new JsonParser()).parse(response);
                     if (element.isJsonObject() && element.getAsJsonObject().has("tracks") && element.getAsJsonObject().get("tracks").isJsonObject()) {
                        JsonObject tracksObject = element.getAsJsonObject().getAsJsonObject("tracks");
                        if (tracksObject.has("items") && tracksObject.get("items").isJsonArray()) {
                           JsonArray itemsArray = tracksObject.getAsJsonArray("items");
                           if (itemsArray.size() == 0) {
                              SpotifyTrack trackx = new SpotifyTrack(new SpotifyResource(text, (String)null, (SpotifyResource.Location)null), (SpotifyResource)null, (SpotifyResource)null, 0, SpotifyTrack.Type.ad);
                              SpotifyWebAPI.this.SPOTIFY_SEARCH_RESULTS.put(text, trackx);
                              callback.call(trackx);
                              return;
                           }

                           JsonElement itemElement = itemsArray.get(0);
                           if (itemElement.isJsonObject()) {
                              JsonObject itemObject = itemElement.getAsJsonObject();
                              if (itemObject.has("id") && itemObject.get("id").isJsonPrimitive() && itemObject.has("name") && itemObject.get("name").isJsonPrimitive() && itemObject.has("duration_ms") && itemObject.get("duration_ms").isJsonPrimitive() && itemObject.get("duration_ms").getAsJsonPrimitive().isNumber() && itemObject.has("artists") && itemObject.get("artists").isJsonArray() && itemObject.getAsJsonArray("artists").size() > 0 && itemObject.getAsJsonArray("artists").get(0).isJsonObject() && itemObject.getAsJsonArray("artists").get(0).getAsJsonObject().has("name") && itemObject.has("album") && itemObject.get("album").isJsonObject() && itemObject.getAsJsonObject("album").has("name") && itemObject.getAsJsonObject("album").get("name").isJsonPrimitive()) {
                                 String trackId = itemObject.getAsJsonPrimitive("id").getAsString();
                                 String trackName = itemObject.getAsJsonPrimitive("name").getAsString();
                                 long durationMillis = itemObject.getAsJsonPrimitive("duration_ms").getAsLong();
                                 String artistName = itemObject.getAsJsonArray("artists").get(0).getAsJsonObject().getAsJsonPrimitive("name").getAsString();
                                 String albumName = itemObject.getAsJsonObject("album").getAsJsonPrimitive("name").getAsString();
                                 SpotifyTrack track = new SpotifyTrack(new SpotifyResource(trackName, "spotify:track:" + trackId, (SpotifyResource.Location)null), new SpotifyResource(artistName, (String)null, (SpotifyResource.Location)null), new SpotifyResource(albumName, (String)null, (SpotifyResource.Location)null), (int)(durationMillis / 1000L), SpotifyTrack.Type.normal);
                                 SpotifyWebAPI.this.SPOTIFY_SEARCH_RESULTS.put(text, track);
                                 SpotifyWebAPI.this.resolveTrackImage(track, trackId, itemObject);
                                 callback.call(track);
                              }
                           }
                        }
                     }
                  }

               }
            });
         } catch (UnsupportedEncodingException var5) {
            System.out.println("Could not encode \"" + text + "\" to URL! " + var5);
         }

      }
   }

   private boolean resolveTrackImage(SpotifyTrack track, String trackId, JsonElement element) {
      if (element.isJsonObject() && element.getAsJsonObject().has("album") && element.getAsJsonObject().get("album").isJsonObject()) {
         JsonObject album = element.getAsJsonObject().get("album").getAsJsonObject();
         if (album.has("images") && album.get("images").isJsonArray()) {
            JsonArray images = album.get("images").getAsJsonArray();
            int smallestSize = Integer.MAX_VALUE;
            String smallestURL = null;
            Iterator var8 = images.iterator();

            label194:
            while(true) {
               int height;
               int width;
               String url;
               do {
                  JsonObject image;
                  do {
                     do {
                        do {
                           do {
                              do {
                                 do {
                                    do {
                                       do {
                                          JsonElement imageElement;
                                          do {
                                             if (!var8.hasNext()) {
                                                break label194;
                                             }

                                             imageElement = (JsonElement)var8.next();
                                          } while(!imageElement.isJsonObject());

                                          image = imageElement.getAsJsonObject();
                                       } while(!image.has("height"));
                                    } while(!image.get("height").isJsonPrimitive());
                                 } while(!image.get("height").getAsJsonPrimitive().isNumber());
                              } while(!image.has("width"));
                           } while(!image.get("width").isJsonPrimitive());
                        } while(!image.get("width").getAsJsonPrimitive().isNumber());
                     } while(!image.has("url"));
                  } while(!image.get("url").isJsonPrimitive());

                  height = image.get("height").getAsInt();
                  width = image.get("width").getAsInt();
                  url = image.get("url").getAsString();
                  if (width == 300 && height == 300) {
                     smallestURL = url;
                     break label194;
                  }
               } while(width >= smallestSize && height >= smallestSize);

               smallestSize = Math.max(width, height);
               smallestURL = url;
            }

            if (smallestURL != null) {
               try {
                  URL url = new URL(smallestURL);
                  BufferedImage image = ImageIO.read(url);
                  BufferedImage image1 = new BufferedImage(128, 128, image.getType());
                  Graphics graphics = image1.getGraphics();

                  try {
                     graphics.drawImage(image, 0, 0, image1.getWidth(), image1.getHeight(), (ImageObserver)null);
                  } finally {
                     graphics.dispose();
                  }

                  ByteBuf localByteBuf1 = Unpooled.buffer();
                  ImageIO.write(image1, "PNG", new ByteBufOutputStream(localByteBuf1));
                  ByteBuf var24 = Base64.encode(localByteBuf1);
                  String imageDataString = var24.toString(Charsets.UTF_8);
                  track.setImage(imageDataString);
                  this.TRACK_IMAGE_LOOKUP.put(trackId, imageDataString);
               } catch (Exception var18) {
                  System.out.println("Could not resolve image for track " + trackId + "!");
               }

               return true;
            }
         }
      }

      return false;
   }
}
