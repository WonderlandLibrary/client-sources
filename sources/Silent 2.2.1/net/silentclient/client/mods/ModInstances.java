package net.silentclient.client.mods;

import net.silentclient.client.Client;
import net.silentclient.client.emotes.EmotesMod;
import net.silentclient.client.mods.hud.*;
import net.silentclient.client.mods.hypixel.AutoGGMod;
import net.silentclient.client.mods.hypixel.AutoTipMod;
import net.silentclient.client.mods.hypixel.LevelHeadMod;
import net.silentclient.client.mods.hypixel.QuickPlayMod;
import net.silentclient.client.mods.hypixel.togglechat.ToggleChatMod;
import net.silentclient.client.mods.player.*;
import net.silentclient.client.mods.render.*;
import net.silentclient.client.mods.render.crosshair.CrosshairMod;
import net.silentclient.client.mods.render.skins.SkinsMod;
import net.silentclient.client.mods.settings.CosmeticsMod;
import net.silentclient.client.mods.settings.FPSBoostMod;
import net.silentclient.client.mods.settings.GeneralMod;
import net.silentclient.client.mods.settings.RenderMod;
import net.silentclient.client.mods.staff.*;
import net.silentclient.client.mods.world.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ModInstances {
	private ArrayList<Mod> mods = new ArrayList<Mod>();
	
	private FPSMod fpsMod;
	private CPSMod cpsMod;
	private AutoSprintMod autoSprintMod;
	private KeystrokesMod keystrokesMod;
	private PerspectiveMod snaplookMod;
	private AnimationsMod oldAnimationsMod;
	private ArmorStatusMod armorStatusMod;
	private MemoryMod memoryMod;
	private AutoGGMod autoGGMod;
	private PotionHudMod potionHudMod;
	private PingMod pingMod;
	private ItemPhysicsMod itemPhysicsMod;
	private PackDisplayMod packDisplayMod;
	private FullBrightMod fullBrightMod;
	private ClockMod clockMod;
	private SpeedometerMod speedometerMod;
	private ReachDisplayMod reachDisplayMod;
	private DayCounterMod dayCounterMod;
	private ServerAddressMod serverAddressMod;
	private TNTTimerMod tntTimerMod;
	private AutoTipMod autoTipMod;
	private LevelHeadMod levelHeadMod;
	private NickHiderMod nickHiderMod;
	private TimeChangerMod timeChangerMod;
	private ZoomMod zoomMod;
	private UhcOverlayMod uhcOverlayMod;
	private ColorSaturationMod colorSaturation;
	private AutoTextMod autoText;
	
	private GeneralMod generalMod;
	private RenderMod renderMod;
	private CosmeticsMod cosmeticsMod;
	private FPSBoostMod fpsBoostMod;
	private EmotesMod emotesMod;
	
	public ModInstances() {
		fpsMod = new FPSMod();
		mods.add(fpsMod);
		
		cpsMod = new CPSMod();
		mods.add(cpsMod);
		
		autoSprintMod = new AutoSprintMod();
		mods.add(autoSprintMod);

		emotesMod = new EmotesMod();
		mods.add(emotesMod);
		
		keystrokesMod = new KeystrokesMod();
		mods.add(keystrokesMod);
		
		snaplookMod = new PerspectiveMod();
		mods.add(snaplookMod);
		
		oldAnimationsMod = new AnimationsMod();
		mods.add(oldAnimationsMod);
		
		armorStatusMod = new ArmorStatusMod();
		mods.add(armorStatusMod);
		
		memoryMod = new MemoryMod();
		mods.add(memoryMod);
		
		autoGGMod = new AutoGGMod();
		mods.add(autoGGMod);
		
		potionHudMod = new PotionHudMod();
		mods.add(potionHudMod);
		
		pingMod = new PingMod();
		mods.add(pingMod);
		
		itemPhysicsMod = new ItemPhysicsMod();
		mods.add(itemPhysicsMod);
		
		packDisplayMod = new PackDisplayMod();
		mods.add(packDisplayMod);
		
		fullBrightMod = new FullBrightMod();
		mods.add(fullBrightMod);
		
		clockMod = new ClockMod();
		mods.add(clockMod);
		
		speedometerMod = new SpeedometerMod();
		mods.add(speedometerMod);
		
		reachDisplayMod = new ReachDisplayMod();
		mods.add(reachDisplayMod);
		
		dayCounterMod = new DayCounterMod();
		mods.add(dayCounterMod);
		
		serverAddressMod = new ServerAddressMod();
		mods.add(serverAddressMod);
		
		tntTimerMod = new TNTTimerMod();
		mods.add(tntTimerMod);
		
		autoTipMod = new AutoTipMod();
		mods.add(autoTipMod);
		
		levelHeadMod = new LevelHeadMod();
		mods.add(levelHeadMod);
		
		nickHiderMod = new NickHiderMod();
		mods.add(nickHiderMod);
		
		timeChangerMod = new TimeChangerMod();
		mods.add(timeChangerMod);
		
		zoomMod = new ZoomMod();
		mods.add(zoomMod);
		
		mods.add(new ComboCounterMod());
		mods.add(new HitColorMod());
		
		uhcOverlayMod = new UhcOverlayMod();
		mods.add(uhcOverlayMod);
				
		fpsBoostMod = new FPSBoostMod();
		mods.add(fpsBoostMod);
		
		mods.add(new ClearGlassMod());
		mods.add(new CrosshairMod());
		
		cosmeticsMod = new CosmeticsMod();
		mods.add(cosmeticsMod);
		mods.add(generalMod = new GeneralMod());
		mods.add(renderMod = new RenderMod());
		
		mods.add(new WeatherChangerMod());
		mods.add(new TabMod());
		mods.add(new ChatMod());
		mods.add(new InventoryBlurMod());
		mods.add(new CoordinatesMod());	
		mods.add(new ChunkBordersMod());
		mods.add(new HitboxesMod());
		mods.add(new NametagsMod());
		mods.add(new ScoreboardMod());
		mods.add(new BlockOverlayMod());
		mods.add(new ParticlesMod());
		mods.add(new DamageTintMod());
		mods.add(new TitlesMod());
		mods.add(colorSaturation = new ColorSaturationMod());
		mods.add(autoText = new AutoTextMod());
		mods.add(new BossBarMod());
		mods.add(new ToggleChatMod());
		mods.add(new PackTweaksMod());
		mods.add(new PlayerCounterMod());
		mods.add(new BlockInfoMod());
		if(Client.getInstance().isDebug() || Client.getInstance().isTest()) {
			mods.add(new TestMod());
			mods.add(new DebugNpcMod());
			mods.add(new HitDelayFixMod());
			mods.add(new FPSSpoofer());
		}
		mods.add(new NewMotionBlurMod());
		if(Client.getInstance().getAccount().getUsername().equalsIgnoreCase("fushka")) {
			mods.add(new DonationsAlertsMod());
		}
		mods.add(new QuickPlayMod());
		mods.add(new SoundsMod());
		mods.add(new SkinsMod());
	}
	
	public void postInit() {
		
	}
	
	public RenderMod getRenderMod() {
		return renderMod;
	}
	
	public ArrayList<Mod> getModByCategory(ModCategory c) {
        ArrayList<Mod> customMods = new ArrayList<Mod>();
        if(c == ModCategory.SETTINGS) {
        	customMods.add(generalMod);
        	customMods.add(renderMod);
        	customMods.add(fpsBoostMod);
        	customMods.add(cosmeticsMod);
			customMods.add(emotesMod);
        	
        	return customMods;
        }
        for(Mod m : mods) {
	            if (m.getCategory() == c) {
	            	customMods.add(m);
	            }
        }
        
        Collections.sort((List<Mod>) customMods, new Comparator<Mod>() {
            @Override
            public int compare(Mod item, Mod t1) {
                String s1 = item.getName();
                String s2 = t1.getName();
                return s1.compareToIgnoreCase(s2);
            }

        });
        
        return customMods;
    }
	
	public ArrayList<Mod> getMods() {
        return mods;
    }
	
	public int getEnabledHudModsSize() {
		ArrayList<Mod> mds = new ArrayList<Mod>();
		
		mods.forEach(mod -> {
			if((mod instanceof ModDraggable || mod instanceof CrosshairMod) && mod.isEnabled()) {
				mds.add(mod);
			}
		});
		
		return mds.size();
	}
	
	public Mod getModByName(String name) {
        return mods.stream().filter(mod -> mod.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

	public ArrayList<Mod> searchMods(String name) {
		ArrayList<Mod> mods1 = new ArrayList<>();
		for(Mod mod : getModByCategory(ModCategory.MODS)) {
			if(mod.getName().trim().toLowerCase().contains(name.toLowerCase().trim())) {
				mods1.add(mod);
			}
		}
		return mods1;
	}
	
	public Mod getModByClass(Class<?> modClass) {
        return mods.stream().filter(mod -> mod.getClass().equals(modClass)).findFirst().orElse(null);
    }
	
	public TNTTimerMod getTntTimerMod() {
		return tntTimerMod;
	}
	
	public AnimationsMod getOldAnimationsMod() {
		return oldAnimationsMod;
	}
	
	public PotionHudMod getPotionHudMod() {
		return potionHudMod;
	}
	
	public ColorSaturationMod getColorSaturation() {
		return colorSaturation;
	}
	
	public ItemPhysicsMod getItemPhysicsMod() {
		return itemPhysicsMod;
	}
	
	public FullBrightMod getFullBrightMod() {
		return fullBrightMod;
	}
	
	public PerspectiveMod getSnaplookMod() {
		return snaplookMod;
	}
	
	public SpeedometerMod getSpeedometerMod() {
		return speedometerMod;
	}
	
	public LevelHeadMod getLevelHeadMod() {
		return levelHeadMod;
	}
	
	public TimeChangerMod getTimeChangerMod() {
		return timeChangerMod;
	}
	
	public ZoomMod getZoomMod() {
		return zoomMod;
	}
	
	public UhcOverlayMod getUhcOverlayMod() {
		return uhcOverlayMod;
	}
	
	public NickHiderMod getNickHiderMod() {
		return nickHiderMod;
	}
	
	public CosmeticsMod getCosmeticsMod() {
		return cosmeticsMod;
	}
	
	public FPSBoostMod getFpsBoostMod() {
		return fpsBoostMod;
	}
	
	public AutoTextMod getAutoText() {
		return autoText;
	}
}
