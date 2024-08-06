package club.strifeclient.module;

import club.strifeclient.module.implementations.visual.HUD;
import club.strifeclient.ui.implementations.FontAwesomeIcons;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {
	COMBAT(FontAwesomeIcons.Sword, "Combat"),
	MOVEMENT(FontAwesomeIcons.Walking, " Movement"),
	PLAYER(FontAwesomeIcons.User, " Player"),
	VISUAL(FontAwesomeIcons.Eye, " Visual"),
	EXPLOIT(FontAwesomeIcons.ArrowsAlt, " Exploit"),
	MISC(FontAwesomeIcons.Wrench, " Misc");

	final String icon, text;
}
