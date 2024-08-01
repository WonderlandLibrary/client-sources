package wtf.diablo.client.spotify;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
public class SpotifyAPI {
    public static String AUTH_CODE = "NOT-SET";
    private final SpotifyApi spotifyApi;
    private final String clientId, clientSecret;
    private final URI redirect;

    public SpotifyAPI(String clientId, String clientSecret, URI redirect){
        this.spotifyApi = new SpotifyApi.Builder().setClientId(clientId).setClientSecret(clientSecret).setRedirectUri(redirect).build();
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirect = redirect;
    }

    public URI getAuthCodeURI(){
        final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri().scope("user-read-currently-playing").build();
        final CompletableFuture<URI> uriFuture = authorizationCodeUriRequest.executeAsync();

        return uriFuture.join();
    }

    public void setAuthCode(){
        final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(AUTH_CODE).build();
        final CompletableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = authorizationCodeRequest.executeAsync();

        final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.join();

        spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
        spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

        SpotifyAPI.AUTH_CODE = "NOT-SET";
    }

    public CurrentlyPlaying getCurrentSong() {
        CurrentlyPlaying currentlyPlaying = null;
        try {
            final GetUsersCurrentlyPlayingTrackRequest getUsersCurrentlyPlayingTrackRequest = spotifyApi.getUsersCurrentlyPlayingTrack().build();
            currentlyPlaying = getUsersCurrentlyPlayingTrackRequest.execute();
        } catch (Exception ignored) {}

        return currentlyPlaying;
    }

    public void updateAccessToken(){
        final AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh().build();
        final CompletableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = authorizationCodeRefreshRequest.executeAsync();
        final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.join();

        spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
    }



    public SpotifyApi getSpotifyApi(){
        return spotifyApi;
    }
}
