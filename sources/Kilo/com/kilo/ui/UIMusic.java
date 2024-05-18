package com.kilo.ui;

import java.util.List;
import java.util.concurrent.TimeUnit;

import net.minecraft.client.renderer.GlStateManager;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.Kilo;
import com.kilo.manager.DatabaseManager;
import com.kilo.music.MusicHandler;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.render.TextureImage;
import com.kilo.ui.inter.ButtonMusic;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.TextBox;
import com.kilo.ui.inter.VolumeSlider;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.ui.inter.slotlist.part.Playlist;
import com.kilo.ui.inter.slotlist.part.Song;
import com.kilo.ui.inter.slotlist.slot.PlaylistSlot;
import com.kilo.ui.inter.slotlist.slot.Slot;
import com.kilo.ui.inter.slotlist.slot.SongSlot;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.ServerUtil;
import com.kilo.util.Util;

public class UIMusic extends UI {

	private enum Mode {
		home,
		charts,
		starred,
		search,
		searchEmpty,
		playlist
	}
	
	public UI popup, popupTo;
	private boolean popupFade, loading, loadCharts, loadStars, setVolume;
	private float fX, fY, fW, fH, popupOpacity;
	private String searchTags, displayTags;

	public SlotList psl, ssl;
	
	private Playlist activePlaylist;
	private TextureImage homeImage;
	
	private Mode mode;
	
	public UIMusic() {
		super(null);
	}
	
	@Override
	public void init() {
		title = "Music";
		
		homeImage = Resources.downloadTexture(DatabaseManager.musicHomeImage);
		
		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2);
		fW = 900;
		fH = 578;

		mode = Mode.home;
		
		psl = new SlotList(4f);
		ssl = new SlotList(7f);
		
		inters.clear();
		
		inters.add(new IconButton(this, fX+(fW/2)-44, fY-(fH/2)+12, 24, 24, 0x00000000, Resources.iconClose[2]));
		inters.add(new IconButton(this, fX-(fW/2)+200-32, fY-(fH/2)+16, 16, 16, Colors.GREEN.c, Resources.iconSearch[1]));
		
		inters.add(new IconButton(this, fX-(fW/2)+200-16, fY-(fH/2)+132+32+4, 8, 8, Colors.GREEN.c, Resources.iconPlus[0]));
		
		inters.add(new TextBox(this, "Search...", fX-(fW/2)+16, fY-(fH/2)+12, 200-52, 24, Fonts.ttfRoundedBold14, Colors.DARKGREY.c, Align.L, Align.C));
		
		inters.add(new ButtonMusic(this, "Home", fX-(fW/2)+12, fY-(fH/2)+60, 200-24, 24, Fonts.ttfRoundedBold12, 0x00000000, Colors.GREY.c, Resources.iconHome[0], 8));
		inters.add(new ButtonMusic(this, "Charts", fX-(fW/2)+12, fY-(fH/2)+84, 200-24, 24, Fonts.ttfRoundedBold12, 0x00000000, Colors.GREY.c, Resources.iconCharts[0], 8));
		inters.add(new ButtonMusic(this, "Starred", fX-(fW/2)+12, fY-(fH/2)+132, 200-24, 24, Fonts.ttfRoundedBold12, 0x00000000, Colors.GREY.c, Resources.iconStar[0], 8));

		inters.add(new IconButton(this, fX-(fW/2)+80+150+48, fY+(fH/2)-24-8, 16, 16, Colors.WHITE.c, Resources.iconPrev[1]));
		inters.add(new IconButton(this, fX-(fW/2)+80+150, fY+(fH/2)-24-16, 32, 32, Colors.GREEN.c, Resources.iconPlay[3]));
		inters.add(new IconButton(this, fX-(fW/2)+80+150+48+24, fY+(fH/2)-24-8, 16, 16, Colors.WHITE.c, Resources.iconNext[1]));
		
		inters.add(new IconButton(this, fX+(fW/2)-16-24, fY+(fH/2)-24-12, 24, 24, Colors.WHITE.c, Resources.iconVolume[0][2]));
	}
	
	public void update(int mx, int my) {
		if (popup == null) {
			super.update(mx, my);
		}
		
		activePlaylist = null;
		for(Slot s : psl.slots) {
			if (s.active) {
				activePlaylist = Kilo.kilo().client.getPlaylist(((PlaylistSlot)s).index);
				break;
			}
		}
		
		if (activePlaylist != null && mode != Mode.playlist) {
			mode = Mode.playlist;
			ssl.slots.clear();
		}
		
		int icon = (int)(MusicHandler.vol*3f/100f);
		icon = Math.min(Math.max(0, icon), 2);
		((IconButton)inters.get(10)).icon = Resources.iconVolume[icon][2];
		
		psl.x = fX-(fW/2)+12;
		psl.y = fY-(fH/2)+132+56-psl.oy;
		psl.w = 176;
		psl.h = fH-(132+56)-12-48;
		
		ssl.x = fX-(fW/2)+200+24;
		ssl.y = fY-(fH/2)+48+24+32-ssl.oy;
		ssl.w = fW-248;
		ssl.h = fH-96-32-48;
		
		if (psl.slots.size() != Kilo.kilo().client.getPlaylistsSize()) {
			psl.slots.clear();
			int i = 0;
			for(Playlist f : Kilo.kilo().client.getPlaylists()) {
				psl.slots.add(new PlaylistSlot(psl, Kilo.kilo().client.getPlaylistIndex(f), psl.x, psl.y, psl.w, 24, 0, i*24));
				i++;
			}
		}
		
		switch(mode) {
		case search:
			if (loading && searchTags.length() > 0) {
				displayTags = searchTags;
				searchTags = "";
				new Thread() {
					@Override
					public void run() {
						List<Song> s = ServerUtil.getSearchSongs(Kilo.kilo().client.clientID, displayTags.toUpperCase());
						if (s.isEmpty()) {
							ssl.slots.clear();
							loading = false;
							mode = Mode.searchEmpty;
						} else {
							ssl.slots.clear();
							int i = 0;
							for(Song a : s) {
								ssl.slots.add(new SongSlot(ssl, a, ssl.x, ssl.y, ssl.w, 80, 0, i*80));
								i++;
							}
							loading = false;
						}
					}
				}.start();
			}
			break;
		case charts:
			if (loading && loadCharts) {
				loadCharts = false;
				new Thread() {
					@Override
					public void run() {
						List<Song> s = ServerUtil.getSongCharts(Kilo.kilo().client.clientID);
						if (s.isEmpty()) {
							ssl.slots.clear();
							loading = false;
							mode = Mode.home;
						} else {
							ssl.slots.clear();
							int i = 0;
							for(Song a : s) {
								ssl.slots.add(new SongSlot(ssl, a, ssl.x, ssl.y, ssl.w, 80, 0, i*80));
								i++;
							}
							loading = false;
						}
					}
				}.start();
			}
			break;
		case starred:
			if (loading && loadStars) {
				loadStars = false;
				new Thread() {
					@Override
					public void run() {
						List<Song> s = ServerUtil.getSongStars(Kilo.kilo().client.clientID);
						if (s.isEmpty()) {
							ssl.slots.clear();
							loading = false;
							mode = Mode.home;
						} else {
							ssl.slots.clear();
							int i = 0;
							for(Song a : s) {
								ssl.slots.add(new SongSlot(ssl, a, ssl.x, ssl.y, ssl.w, 80, 0, i*80));
								i++;
							}
							loading = false;
						}
					}
				}.start();
			}
			break;
		case playlist:
			if (activePlaylist != null) {
				if (ssl.slots.size() != activePlaylist.getSize()) {
					ssl.slots.clear();
					int i = 0;
					for(Song a : activePlaylist.getList()) {
						ssl.slots.add(new SongSlot(ssl, a, ssl.x, ssl.y, ssl.w, 80, 0, i*80));
						i++;
					}
				}
			} else {
				for(Slot s : ssl.slots) {
					s.active = false;
				}
				mode = Mode.home;
			}
			break;
		default:
			break;
		}
		
		if (MusicHandler.player != null && MusicHandler.isPlaying) {
			((IconButton)inters.get(8)).icon = Resources.iconPause[3];
		} else {
			((IconButton)inters.get(8)).icon = Resources.iconPlay[3];
		}
		
		if (MusicHandler.player == null || MusicHandler.currentSong == null || MusicHandler.currentSongList == null) {
			inters.get(7).enabled = false;
			inters.get(9).enabled = false;
		} else {
			inters.get(7).enabled = true;
			inters.get(9).enabled = true;
		}
		
		if (MusicHandler.currentSong == null && !MusicHandler.isPlaying && MusicHandler.player == null) {
			SongSlot selected = null;
			for(Slot s : ssl.slots) {
				if (s.active) {
					selected = (SongSlot)s;
					break;
				}
			}
			inters.get(8).enabled = selected != null;
		} else {
			inters.get(8).enabled = true;
		}
		
		psl.update(mx, my);
		ssl.update(mx, my);
		
		if (popup != null) {
			popup.update(mx, my);
		}
		popupOpacity+= popupFade?-0.2f:0.2f;//((uiFadeIn?1:0)-uiFade)/4f;
		popupOpacity = Math.min(Math.max(0f, popupOpacity), 1f);
		if (popupFade) {
			if (popupOpacity < 0.05f) {
				popup = popupTo;
				popupFade = false;
				if (popup != null) {
					popup.init();
				}
			}
		}
		
	}
	
	@Override
	public void interact(final Inter i) {
		switch(inters.indexOf(i)) {
		case 0:
			mc.displayGuiScreen(null);
			break;
		case 1:
			if (!loading) {
				searchTags = ((TextBox)inters.get(3)).text;
				if (searchTags.length() > 0) {
					mode = Mode.search;
					loading = true;
				}
				for(Slot s : psl.slots) {
					s.active = false;
				}
			}
			break;
		case 2:
			changePopup(new UIPopupPlaylistCreate(this));
			break;
		case 4:
			mode = Mode.home;
			for(Slot s : psl.slots) {
				s.active = false;
			}
			break;
		case 5:
			if (!loading) {
				mode = Mode.charts;
				loading = true;
				loadCharts = true;
				for(Slot s : psl.slots) {
					s.active = false;
				}
			}
			break;
		case 6:
			if (!loading) {
				mode = Mode.starred;
				loading = true;
				loadStars = true;
				for(Slot s : psl.slots) {
					s.active = false;
				}
			}
			break;
		case 7:
			MusicHandler.prev();
			break;
		case 8:
			if (MusicHandler.currentSong != null && MusicHandler.isPlaying) {
				MusicHandler.pause();
			} else if (MusicHandler.currentSong != null) {
				MusicHandler.resume();
			} else {
				SongSlot selected = null;
				for(Slot s : ssl.slots) {
					if (s.active) {
						selected = (SongSlot)s;
						break;
					}
				}
				if (selected != null) {
					selected.play();
				}
			}
			break;
		case 9:
			MusicHandler.next();
			break;
		case 10:
			inters.add(new VolumeSlider(this, fX+(fW/2)+4, fY+(fH/2)-100-10, 12, 100));
			break;
		}
	}
	
	public void mouseClick(int mx, int my, int b) {
		if (popup == null) {
			super.mouseClick(mx, my, b);
			psl.mouseClick(mx, my, b);
			ssl.mouseClick(mx, my, b);
		} else {
			popup.mouseClick(mx, my, b);
		}
	}
	
	public void mouseRelease(int mx, int my, int b) {
		if (popup == null) {
			super.mouseRelease(mx, my, b);
			psl.mouseRelease(mx, my, b);
			ssl.mouseRelease(mx, my, b);
		} else {
			popup.mouseRelease(mx, my, b);
		}
	}
	
	public void mouseScroll(int s) {
		if (popup == null) {
			super.mouseScroll(s);
			psl.mouseScroll(s);
			ssl.mouseScroll(s);
		} else {
			popup.mouseScroll(s);
		}
	}
	
	public void keyboardPress(int key) {
		if (popup == null) {
			super.keyboardPress(key);
			switch (key) {
			case Keyboard.KEY_RETURN:
				if (inters.get(3).active) {
					if (!loading) {
						searchTags = ((TextBox)inters.get(3)).text;
						if (searchTags.length() > 0) {
							mode = Mode.search;
							loading = true;
						}
						for(Slot s : psl.slots) {
							s.active = false;
						}
					}
				}
				break;
			case Keyboard.KEY_ESCAPE:
				mc.displayGuiScreen(null);
				break;
			}
		} else {
			popup.keyboardPress(key);
		}
	}
	
	public void keyTyped(int key, char keyChar) {
		if (popup == null) {
			super.keyTyped(key, keyChar);
		} else {
			popup.keyTyped(key, keyChar);
		}
	}
	
	public void changePopup(UI u) {
		popupTo = u;
		popupFade = true;
	}
	
	public void setVolume(int volume) {
		MusicHandler.setVolume(volume);
	}
	
	public void render(float opacity) {
		GlStateManager.disableDepth();
		Draw.rect(0, 0, Display.getWidth(), Display.getHeight(), Util.reAlpha(Colors.BLACK.c, 0.7f*opacity));
		
		Draw.rect(fX-(fW/2), fY-(fH/2)+48, fX-(fW/2)+200, fY+(fH/2)-48, Util.reAlpha(0xFF121212, 1f*opacity));
		Draw.rect(fX-(fW/2)+200, fY-(fH/2)+48, fX+(fW/2), fY+(fH/2)-48, Util.reAlpha(0xFF202020, 1f*opacity));
		Draw.rect(fX-(fW/2), fY+(fH/2)-48, fX+(fW/2), fY+(fH/2), Util.reAlpha(0xFF1A1A1A, 1f*opacity));

		Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2), fY-(fH/2)+48, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		
		Draw.string(Fonts.ttfRoundedBold25, fX, fY-(fH/2)+24, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		Draw.rect(fX-(fW/2)+12, fY-(fH/2)+12, fX-(fW/2)+200-12, fY-(fH/2)+48-12, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
		
		Draw.rect(fX-(fW/2)+12, fY-(fH/2)+48+12+24+24+12, fX-(fW/2)+200-12, fY-(fH/2)+48+12+24+24+13, Util.reAlpha(Colors.GREEN.c, 0.5f*opacity));
		
		Draw.string(Fonts.ttfRoundedBold12, fX-(fW/2)+16, fY-(fH/2)+132+32, "PLAYLISTS", Util.reAlpha(Colors.GREY.c, 0.5f*opacity));
		
		String timeFormat = "00:00:00";
		
		Draw.rect(fX-(fW/2)+80+150+48+24+38+Fonts.ttfRounded10.getWidth(timeFormat)+3, fY+(fH/2)-24-3, fX+(fW/2)-16-24-12-Fonts.ttfRounded10.getWidth(timeFormat)-3, fY+(fH/2)-24+3, Util.reAlpha(0xFF0A0A0A, 1f*opacity));
		Draw.circle(fX-(fW/2)+80+150+48+24+38+Fonts.ttfRounded10.getWidth(timeFormat)+3, fY+(fH/2)-24, 3, Util.reAlpha(0xFF0A0A0A, 1f*opacity));
		Draw.circle(fX+(fW/2)-16-24-12-Fonts.ttfRounded10.getWidth(timeFormat)-3, fY+(fH/2)-24, 3, Util.reAlpha(0xFF0A0A0A, 1f*opacity));
		if (MusicHandler.player != null && MusicHandler.currentSong != null) {
			Song song = MusicHandler.currentSong;
			if (song != null) {

				int pos = MusicHandler.player.getPosition();
				float barPos = (1f/(float)song.duration)*(float)(pos+1);
				float barWidth = (fX+(fW/2)-16-24-12-Fonts.ttfRounded10.getWidth(timeFormat)-3)-(fX-(fW/2)+80+150+48+24+32+Fonts.ttfRounded10.getWidth(timeFormat)+3);
				
				Draw.rect(fX-(fW/2)+80+150+48+24+38+Fonts.ttfRounded10.getWidth(timeFormat)+3, fY+(fH/2)-24-3, fX-(fW/2)+80+150+48+24+38+Fonts.ttfRounded10.getWidth(timeFormat)+3+(barWidth*barPos), fY+(fH/2)-24+3, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
				Draw.circle(fX-(fW/2)+80+150+48+24+38+Fonts.ttfRounded10.getWidth(timeFormat)+3, fY+(fH/2)-24, 3, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
				Draw.circle(fX-(fW/2)+80+150+48+24+38+Fonts.ttfRounded10.getWidth(timeFormat)+3+(barWidth*barPos), fY+(fH/2)-24, 3, Util.reAlpha(Colors.GREEN.c, 1f*opacity));

				String time = "0:00";
				if (pos >= 3600000) {
					String format = "%2d:%02d:%02d";
					time = String.format(format,
							TimeUnit.MILLISECONDS.toHours(pos),
						    TimeUnit.MILLISECONDS.toMinutes(pos) % TimeUnit.HOURS.toMinutes(1),
						    TimeUnit.MILLISECONDS.toSeconds(pos) % TimeUnit.MINUTES.toSeconds(1));
				} else if (pos >= 60000) {
					String format = "%2d:%02d";
					time = String.format(format,
							TimeUnit.MILLISECONDS.toMinutes(pos) % TimeUnit.HOURS.toMinutes(1),
						    TimeUnit.MILLISECONDS.toSeconds(pos) % TimeUnit.MINUTES.toSeconds(1));
				} else if (pos >= 1000) {
					String format = "0:%02d";
					time = String.format(format,
						    TimeUnit.MILLISECONDS.toSeconds(pos) % TimeUnit.MINUTES.toSeconds(1));
				}
				Draw.string(Fonts.ttfRounded10, fX-(fW/2)+80+150+48+24+32+Fonts.ttfRounded10.getWidth(timeFormat), fY+(fH/2)-24, time, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.R, Align.C);
				
				String duration = "0:00";
				if (song.duration >= 3600000) {
					String format = "%2d:%02d:%02d";
					duration = String.format(format,
							TimeUnit.MILLISECONDS.toHours(song.duration),
						    TimeUnit.MILLISECONDS.toMinutes(song.duration) % TimeUnit.HOURS.toMinutes(1),
						    TimeUnit.MILLISECONDS.toSeconds(song.duration) % TimeUnit.MINUTES.toSeconds(1));
				} else if (song.duration >= 60000) {
					String format = "%2d:%02d";
					duration = String.format(format,
							TimeUnit.MILLISECONDS.toMinutes(song.duration) % TimeUnit.HOURS.toMinutes(1),
						    TimeUnit.MILLISECONDS.toSeconds(song.duration) % TimeUnit.MINUTES.toSeconds(1));
				} else if (song.duration >= 1000) {
					String format = "0:%02d";
					duration = String.format(format,
						    TimeUnit.MILLISECONDS.toSeconds(song.duration) % TimeUnit.MINUTES.toSeconds(1));
				}
				Draw.string(Fonts.ttfRounded10, fX+(fW/2)-16-24-2-Fonts.ttfRounded10.getWidth(timeFormat)-3, fY+(fH/2)-24, duration, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.L, Align.C);
				
				if (song.image != null && song.image.getTexture() != null) {
					Draw.rectTexture(fX-(fW/2), fY+(fH/2)-48, 48, 48, song.image.getTexture());
				} else {
					Draw.loader(fX-(fW/2)+24, fY+(fH/2)-24, 8, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
				}
				String title = song.title;
				for(int i = 0; i < title.length(); i++) {
					if (Fonts.ttfRoundedBold12.getWidth(title.substring(0, i)) > 150-Fonts.ttfRoundedBold12.getWidth("...")) {
						title = title.substring(0, i)+"...";
						break;
					}
				}
				Draw.string(Fonts.ttfRoundedBold12, fX-(fW/2)+64, fY+(fH/2)-24, title, Util.reAlpha(Colors.GREEN.c, 1f*opacity), Align.L, Align.C);
			} else {
				Draw.loader(fX-(fW/2)+24, fY+(fH/2)-24, 8, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
			}
		}
		
		switch(mode) {
		case charts:
			if (!loading) {
				Draw.rect(fX-(fW/2)+224, fY-(fH/2)+48+24+32-2, fX+(fW/2)-24, fY-(fH/2)+48+24+32, Util.reAlpha(Colors.GREY.c, 0.5f*opacity));
				Draw.string(Fonts.ttfRoundedBold16, fX-(fW/2)+200+24, fY-(fH/2)+48+24+16, "Charts", Util.reAlpha(Colors.GREEN.c, 1f*opacity), Align.L, Align.C);
				Draw.startClip(fX-(fW/2)+224, fY-(fH/2)+48+24+32, fX+(fW/2)-24+ssl.sbw, fY+(fH/2)-48-24);
				ssl.render(opacity);
				Draw.endClip();
			} else {
				Draw.loader(fX+100, fY, 32, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
			}
			break;
		case home:
			if (homeImage != null && homeImage.getTexture() != null) {
				Draw.rectTexture(fX-(fW/2)+200, fY-(fH/2)+48, fW-200, fH-96, homeImage.getTexture());
			}
			break;
		case playlist:
			if (activePlaylist != null) {
				Draw.rect(fX-(fW/2)+224, fY-(fH/2)+48+24+32-2, fX+(fW/2)-24, fY-(fH/2)+48+24+32, Util.reAlpha(Colors.GREY.c, 0.5f*opacity));
				String title = activePlaylist.name;
				for(int i = 0; i < title.length(); i++) {
					if (Fonts.ttfRoundedBold12.getWidth(title.substring(0, i)) > (fW-296)-128-Fonts.ttfRoundedBold12.getWidth("...")) {
						title = title.substring(0, i)+"...";
						break;
					}
				}
				Draw.string(Fonts.ttfRoundedBold16, fX-(fW/2)+200+24, fY-(fH/2)+48+24+16, title, Util.reAlpha(Colors.GREEN.c, 1f*opacity), Align.L, Align.C);
				Draw.startClip(fX-(fW/2)+224, fY-(fH/2)+48+24+32, fX+(fW/2)-24+ssl.sbw, fY+(fH/2)-48-24);
				ssl.render(opacity);
				Draw.endClip();
			}
			break;
		case search:
			if (!loading) {
				Draw.rect(fX-(fW/2)+224, fY-(fH/2)+48+24+32-2, fX+(fW/2)-24, fY-(fH/2)+48+24+32, Util.reAlpha(Colors.GREY.c, 0.5f*opacity));
				String title = "Search - "+displayTags;
				for(int i = 0; i < title.length(); i++) {
					if (Fonts.ttfRoundedBold12.getWidth(title.substring(0, i)) > (fW-296)-128-Fonts.ttfRoundedBold12.getWidth("...")) {
						title = title.substring(0, i)+"...";
						break;
					}
				}
				Draw.string(Fonts.ttfRoundedBold16, fX-(fW/2)+200+24, fY-(fH/2)+48+24+16, title, Util.reAlpha(Colors.GREEN.c, 1f*opacity), Align.L, Align.C);
				Draw.startClip(fX-(fW/2)+224, fY-(fH/2)+48+24+32, fX+(fW/2)-24+ssl.sbw, fY+(fH/2)-48-24);
				ssl.render(opacity);
				Draw.endClip();
			} else {
				Draw.loader(fX+100, fY, 32, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
			}
			break;
		case searchEmpty:
			Draw.rect(fX-(fW/2)+224, fY-(fH/2)+48+24+32-2, fX+(fW/2)-24, fY-(fH/2)+48+24+32, Util.reAlpha(Colors.GREY.c, 1f*opacity));
			Draw.string(Fonts.ttfRoundedBold16, fX-(fW/2)+200+24, fY-(fH/2)+48+24+16, "No results found", Util.reAlpha(Colors.GREEN.c, 1f*opacity), Align.L, Align.C);
			break;
		case starred:
			if (!loading) {
				Draw.rect(fX-(fW/2)+224, fY-(fH/2)+48+24+32-2, fX+(fW/2)-24, fY-(fH/2)+48+24+32, Util.reAlpha(Colors.GREY.c, 0.5f*opacity));
				Draw.string(Fonts.ttfRoundedBold16, fX-(fW/2)+200+24, fY-(fH/2)+48+24+16, "Starred", Util.reAlpha(Colors.GREEN.c, 1f*opacity), Align.L, Align.C);
				Draw.startClip(fX-(fW/2)+224, fY-(fH/2)+48+24+32, fX+(fW/2)-24+ssl.sbw, fY+(fH/2)-48-24);
				ssl.render(opacity);
				Draw.endClip();
			} else {
				Draw.loader(fX+100, fY, 32, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
			}
			break;
		}
		
		Draw.startClip(fX-(fW/2)+8, fY-(fH/2)+132+56, fX-(fW/2)+192+psl.sbw, fY+(fH/2)-48-12);
		psl.render(opacity);
		Draw.endClip();

		super.render(opacity);

		Draw.rectGradient(fX-(fW/2)+200, fY+(fH/2)-48-32, fX+(fW/2), fY+(fH/2)-48, Util.reAlpha(Colors.BLACK.c, 0f), Util.reAlpha(Colors.BLACK.c, 0.4f*opacity));
		
		if (popup != null) {
			popup.render(opacity*(Math.max(popupOpacity, 0.05f)));
		}
		GlStateManager.enableDepth();
	}
}
