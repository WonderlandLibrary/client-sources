package net.silentclient.client.cosmetics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.cosmetics.model.ModelBuffer;
import net.silentclient.client.mixin.ducks.AbstractClientPlayerExt;
import net.silentclient.client.mods.settings.CosmeticsMod;
import net.silentclient.client.utils.Players;
import net.silentclient.client.utils.Requests;
import net.silentclient.client.utils.types.PlayerResponse;
import net.silentclient.client.utils.types.PlayerResponse.Account.Cosmetics.CosmeticItem;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cosmetics {
	public Map<Number, AnimatedResourceLocation> capes = new HashMap<>();
	public Map<Number, StaticResourceLocation> capesShoulders = new HashMap<>();
	public Map<Number, AnimatedResourceLocation> wings = new HashMap<>();
	public Map<Number, AnimatedResourceLocation> bandanas = new HashMap<>();
	public Map<Number, HatData> hats = new HashMap<>();
	public Map<Number, ShieldData> shields = new HashMap<>();
	public Map<Number, StaticResourceLocation> icons = new HashMap<>();
	public Map<Number, CosmeticItem> emotes = new HashMap<>();
		
	public ArrayList<CosmeticItem> myIcons = new ArrayList<CosmeticItem>();
	public ArrayList<CosmeticItem> myWings = new ArrayList<CosmeticItem>();
	public ArrayList<CosmeticItem> myCapes = new ArrayList<CosmeticItem>();
	public ArrayList<CosmeticItem> myBandanas = new ArrayList<CosmeticItem>();
	public ArrayList<CosmeticItem> myHats = new ArrayList<CosmeticItem>();
	public ArrayList<CosmeticItem> myShields = new ArrayList<CosmeticItem>();
	public ArrayList<CosmeticItem> myEmotes = new ArrayList<CosmeticItem>();
	

	public StaticResourceLocation defaultIcon;
	private ModelBuffer bandana;

	public Map<String, ModelBuffer> hatModels = new HashMap<>();
	public Map<String, ModelBuffer> shieldModels = new HashMap<>();

	public void init() {
		init(true);
	}
	
	public void init(boolean cosmeticInit) {
		if(cosmeticInit) {
			Client.logger.info("STARTING > cosmetics > bandana_model");
			try {
				bandana = new ModelBuffer(new ResourceLocation("silentclient/models/bandana.obj"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			Client.logger.info("STARTING > cosmetics > default_icon");
			defaultIcon = new StaticResourceLocation("silentclient/icons/player_icon.png");
			PlayerResponse.Account.Cosmetics allCosmetics = getAllCosmetics();
			Client.logger.info("STARTING > cosmetics > capes");
			capes.clear();
			capesShoulders.clear();

			if(allCosmetics != null && allCosmetics.getCapes() != null) {
				allCosmetics.getCapes().forEach((cape) -> {
					capes.put(cape.getId(), new AnimatedResourceLocation("silentclient/cosmetics/capes/"+cape.getId(), cape.getFrames(), cape.getFrameDelay(), false, cape.getId() == Client.getInstance().getAccount().getSelectedCape()));
					capesShoulders.put(cape.getId(), new StaticResourceLocation("silentclient/cosmetics/capes/"+cape.getId()+"/shoulders.png"));
				});
			}

			Client.logger.info("STARTING > cosmetics > wings");
			wings.clear();
			if(allCosmetics != null && allCosmetics.getWings() != null) {
				allCosmetics.getWings().forEach((wing) -> {
					wings.put(wing.getId(), new AnimatedResourceLocation("silentclient/cosmetics/wings/"+ wing.getId(), wing.getFrames(), wing.getFrameDelay(), false, wing.getId() == Client.getInstance().getAccount().getSelectedWings()));
				});
			}

			Client.logger.info("STARTING > cosmetics > bandanas");
			bandanas.clear();
			if(allCosmetics != null && allCosmetics.getBandanas() != null) {
				allCosmetics.getBandanas().forEach((bandana) -> {
					bandanas.put(bandana.getId(), new AnimatedResourceLocation("silentclient/cosmetics/bandanas/"+bandana.getId(), bandana.getFrames(), bandana.getFrameDelay(), false, bandana.getId() == Client.getInstance().getAccount().getSelectedBandana()));
				});
			}

			Client.logger.info("STARTING > cosmetics > hats");
			hats.clear();
			if(allCosmetics != null && allCosmetics.getHats() != null) {
				allCosmetics.getHats().forEach((hat) -> {
					hats.put(hat.getId(), new HatData(new AnimatedResourceLocation("silentclient/cosmetics/hats/"+hat.getId(), hat.getFrames(), hat.getFrameDelay(), false, hat.getId() == Client.getInstance().getAccount().getSelectedHat()), hat.getModel()));
					if(!hatModels.containsKey(hat.getModel())) {
						try {
							Client.logger.info("STARTING > cosmetics > hats > model > " + hat.getModel());
							ModelBuffer model = new ModelBuffer(new ResourceLocation("silentclient/models/"+ hat.getModel() + ".obj"));
							if(model != null) {
								hatModels.put(hat.getModel(), model);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
			Client.logger.info("STARTING > cosmetics > shields");
			shields.clear();
			if(allCosmetics != null && allCosmetics.getShields() != null) {
				allCosmetics.getShields().forEach((shield) -> {
					shields.put(shield.getId(), new ShieldData(new AnimatedResourceLocation("silentclient/cosmetics/shields/"+shield.getId(), shield.getFrames(), shield.getFrameDelay(), false, shield.getId() == Client.getInstance().getAccount().getSelectedShield()), shield.getModel()));
					if(!hatModels.containsKey(shield.getModel())) {
						try {
							Client.logger.info("STARTING > cosmetics > shields > model > " + shield.getModel());
							ModelBuffer model = new ModelBuffer(new ResourceLocation("silentclient/models/"+ shield.getModel() + ".obj"));
							if(model != null) {
								shieldModels.put(shield.getModel(), model);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}

			Client.logger.info("STARTING > cosmetics > icons");
			icons.clear();
			if(allCosmetics != null && allCosmetics.getIcons() != null) {
				allCosmetics.getIcons().forEach((icon) -> {
					icons.put(icon.getId(), new StaticResourceLocation("silentclient/cosmetics/icons/"+ icon.getId() + "/0.png"));
				});
			}

			Client.logger.info("STARTING > cosmetics > emotes");
			emotes.clear();
			if(allCosmetics != null && allCosmetics.getEmotes() != null) {
				allCosmetics.getEmotes().forEach((emote) -> {
					emotes.put(emote.getId(), emote);
				});
			}

			Client.logger.info("STARTING > cosmetics > outfits");
			Outfits.loadOutfits();
		}
		
		update(false);
	}
	
	public ModelBuffer getBandana() {
		return bandana;
	}

	public void update(boolean async) {
		Client.logger.info("Loading Player Information");
		if(!async) {
			PlayerResponse cosmetics = getCosmetics();
    		
    		if(cosmetics != null) {
    			Client.getInstance().setAccount(cosmetics.getAccount());
    			
    			Client.getInstance().getCosmetics().setMyCapes(cosmetics.getAccount().getCosmetics().getCapes());
    			Client.getInstance().getCosmetics().setMyWings(cosmetics.getAccount().getCosmetics().getWings());
    			Client.getInstance().getCosmetics().setMyIcons(cosmetics.getAccount().getCosmetics().getIcons());
    			Client.getInstance().getCosmetics().setMyBandanas(cosmetics.getAccount().getCosmetics().getBandanas());
				Client.getInstance().getCosmetics().setMyHats(cosmetics.getAccount().getCosmetics().getHats());
				Client.getInstance().getCosmetics().setMyShields(cosmetics.getAccount().getCosmetics().getShields());
				Client.getInstance().getCosmetics().setMyEmotes(cosmetics.getAccount().getCosmetics().getEmotes());
				Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Cape Shoulders").setValBoolean(cosmetics.getAccount().getCapeShoulders());
    			Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Cape Type").setValString(cosmetics.getAccount().getCapeType().equals("dynamic_curved") ? "Dynamic Curved" : cosmetics.getAccount().getCapeType().equals("curved_rectangle") ? "Curved Rectangle" : "Rectangle");
    			if(Minecraft.getMinecraft().thePlayer != null) {
    				((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$setCapeType(cosmetics.getAccount().getCapeType());
					((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$setShoulders(cosmetics.getAccount().getCapeShoulders());
    			}
    		}
		} else {
			(new Thread("update") {
	            public void run() {
	            	PlayerResponse cosmetics = getCosmetics();
	        		
	        		if(cosmetics != null) {
	        			Client.getInstance().setAccount(cosmetics.getAccount());
	        			
	        			Client.getInstance().getCosmetics().setMyCapes(cosmetics.getAccount().getCosmetics().getCapes());
	        			Client.getInstance().getCosmetics().setMyWings(cosmetics.getAccount().getCosmetics().getWings());
	        			Client.getInstance().getCosmetics().setMyIcons(cosmetics.getAccount().getCosmetics().getIcons());
	        			Client.getInstance().getCosmetics().setMyBandanas(cosmetics.getAccount().getCosmetics().getBandanas());
	        			Client.getInstance().getCosmetics().setMyHats(cosmetics.getAccount().getCosmetics().getHats());
	        			Client.getInstance().getCosmetics().setMyShields(cosmetics.getAccount().getCosmetics().getShields());
						Client.getInstance().getCosmetics().setMyEmotes(cosmetics.getAccount().getCosmetics().getEmotes());
	        			Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Cape Shoulders").setValBoolean(cosmetics.getAccount().getCapeShoulders());
	        			Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Cape Type").setValString(cosmetics.getAccount().getCapeType().equals("dynamic_curved") ? "Dynamic Curved" : cosmetics.getAccount().getCapeType().equals("curved_rectangle") ? "Curved Rectangle" : "Rectangle");
	        			if(Minecraft.getMinecraft().thePlayer != null) {
							((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$setCapeType(cosmetics.getAccount().getCapeType());
							((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$setShoulders(cosmetics.getAccount().getCapeShoulders());
	        			}
	        		}
	            }
			}).start();
		}
	}
	
	public void setMyCapes(ArrayList<CosmeticItem> myCapes) {
		this.myCapes = myCapes;
	}
	
	public void setMyIcons(ArrayList<CosmeticItem> myIcons) {
		this.myIcons = myIcons;
	}
	
	public void setMyWings(ArrayList<CosmeticItem> myWings) {
		this.myWings = myWings;
	}
	
	public void setMyBandanas(ArrayList<CosmeticItem> myBandanas) {
		this.myBandanas = myBandanas;
	}

	public void setMyHats(ArrayList<CosmeticItem> myHats) {
		this.myHats = myHats;
	}

	public ArrayList<CosmeticItem> getMyHats() {
		return myHats;
	}
	
	public void setMyShields(ArrayList<CosmeticItem> myShields) {
		this.myShields = myShields;
	}
	
	public ArrayList<CosmeticItem> getMyShields() {
		return myShields;
	}

	public ArrayList<CosmeticItem> getMyEmotes() {
		return myEmotes;
	}

	public void setMyEmotes(ArrayList<CosmeticItem> myEmotes) {
		this.myEmotes = myEmotes;
	}

	public static PlayerResponse.Account.Cosmetics getAllCosmetics() {
		try {
			InputStream in = Client.getInstance().getClass().getResourceAsStream("/assets/minecraft/silentclient/cosmetics.json");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuffer content = new StringBuffer();
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				content.append(inputLine);
			}
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();

			in.close();

			return gson.fromJson(content.toString(), PlayerResponse.Account.Cosmetics.class);
		} catch (Exception e1) {
			Client.logger.catching(e1);
			return null;
		}
	}
	
	public static PlayerResponse getCosmetics() {
		try {
			String content = Requests.get("https://api.silentclient.net/account");
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
						
			PlayerResponse response = gson.fromJson(content.toString(), PlayerResponse.class);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public AnimatedResourceLocation getWingsById(int id) {
		return wings.get(id);
	}
	
	public AnimatedResourceLocation getCapeById(int id) {
		return capes.get(id);
	}
	
	public AnimatedResourceLocation getBandanaById(int id) {
		return bandanas.get(id);
	}

	public HatData getHatById(int id) {;
		return hats.get(id);
	}
	
	public ShieldData getShieldById(int id) {;
		return shields.get(id);
	}
	
	public StaticResourceLocation getCapeShoulders(int id) {
		return capesShoulders.get(id);
	}
	
	public StaticResourceLocation getIconById(int id) {
		StaticResourceLocation icon = icons.get(id);
		return icon != null ? icon : getDefaultIcon();
	}
	
	public StaticResourceLocation getDefaultIcon() {
		return defaultIcon;
	}

	
	public ArrayList<CosmeticItem> getMyCapes() {
		return myCapes;
	}
	
	public ArrayList<CosmeticItem> getMyWings() {
		return myWings;
	}
	
	public ArrayList<CosmeticItem> getMyIcons() {
		return myIcons;
	}
	
	public ArrayList<CosmeticItem> getMyBandanas() {
		return myBandanas;
	}
	
	public static void reload(final AbstractClientPlayer player) {
		Minecraft.getMinecraft().refreshResources();
		Client.getInstance().getCosmetics().update(true);
		Players.reload();
		Players.getPlayerStatus(false, ((AbstractClientPlayerExt) player).silent$getNameClear(), EntityPlayer.getUUID(player.getGameProfile()), player);
	}
}
