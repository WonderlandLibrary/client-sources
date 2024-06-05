package Focus.Beta.IMPL.Module.impl.render;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.wrapper.spotify.SpotifyApi;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.render.EventRender2D;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.UTILS.Callback;

public class Spotify extends Module{
	// For all requests an access token is needed
	

	public Spotify() {
		super("Spotify", new String[0], Type.RENDER, "No");
	}
	
	@EventHandler
	public void e(EventRender2D e) {

	}
	
	@Override
	public void onEnable() {
		
	}
	
	

}
