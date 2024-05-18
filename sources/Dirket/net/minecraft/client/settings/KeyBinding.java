package net.minecraft.client.settings;

import java.util.List;
import java.util.Set;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.IntHashMap;

public class KeyBinding implements Comparable<KeyBinding> {
	private static final List<KeyBinding> KEYBIND_ARRAY = Lists.<KeyBinding> newArrayList();
	private static final IntHashMap<KeyBinding> HASH = new IntHashMap();
	private static final Set<String> KEYBIND_SET = Sets.<String> newHashSet();
	private final String keyDescription;
	private final int keyCodeDefault;
	private final String keyCategory;
	private int keyCode;

	/** Is the key held down? */
	private boolean pressed;
	private int pressTime;

	public static void onTick(int keyCode) {
		if (keyCode != 0) {
			KeyBinding keybinding = HASH.lookup(keyCode);

			if (keybinding != null) {
				++keybinding.pressTime;
			}
		}
	}

	public static void setKeyBindState(int keyCode, boolean pressed) {
		if (keyCode != 0) {
			KeyBinding keybinding = HASH.lookup(keyCode);

			if (keybinding != null) {
				keybinding.pressed = pressed;
			}
		}
	}

	public static void updateKeyBindState() {
		for (KeyBinding keybinding : KEYBIND_ARRAY) {
			try {
				setKeyBindState(keybinding.keyCode, (keybinding.keyCode < 256) && Keyboard.isKeyDown(keybinding.keyCode));
			} catch (IndexOutOfBoundsException var3) {
				;
			}
		}
	}

	public static void unPressAllKeys() {
		for (KeyBinding keybinding : KEYBIND_ARRAY) {
			keybinding.unpressKey();
		}
	}

	public static void resetKeyBindingArrayAndHash() {
		HASH.clearMap();

		for (KeyBinding keybinding : KEYBIND_ARRAY) {
			HASH.addKey(keybinding.keyCode, keybinding);
		}
	}

	public static Set<String> getKeybinds() {
		return KEYBIND_SET;
	}

	public KeyBinding(String description, int keyCode, String category) {
		this.keyDescription = description;
		this.keyCode = keyCode;
		this.keyCodeDefault = keyCode;
		this.keyCategory = category;
		KEYBIND_ARRAY.add(this);
		HASH.addKey(keyCode, this);
		KEYBIND_SET.add(category);
	}

	/**
	 * Returns true if the key is pressed (used for continuous querying). Should be used in tickers.
	 */
	public boolean isKeyDown() {
		return this.pressed;
	}

	public String getKeyCategory() {
		return this.keyCategory;
	}

	/**
	 * Returns true on the initial key press. For continuous querying use {@link isKeyDown()}. Should be used in key events.
	 */
	public boolean isPressed() {
		if (this.pressTime == 0) {
			return false;
		} else {
			--this.pressTime;
			return true;
		}
	}

	private void unpressKey() {
		this.pressTime = 0;
		this.pressed = false;
	}

	public String getKeyDescription() {
		return this.keyDescription;
	}

	public int getKeyCodeDefault() {
		return this.keyCodeDefault;
	}

	public int getKeyCode() {
		return this.keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	@Override
	public int compareTo(KeyBinding p_compareTo_1_) {
		int i = I18n.format(this.keyCategory, new Object[0]).compareTo(I18n.format(p_compareTo_1_.keyCategory, new Object[0]));

		if (i == 0) {
			i = I18n.format(this.keyDescription, new Object[0]).compareTo(I18n.format(p_compareTo_1_.keyDescription, new Object[0]));
		}

		return i;
	}
}
