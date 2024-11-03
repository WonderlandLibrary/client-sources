package net.silentclient.client.utils.enhancement.hash;

import net.silentclient.client.utils.enhancement.hash.impl.AbstractHash;

public class StringHash extends AbstractHash {
	public StringHash(String text, float red, float green, float blue, float alpha, boolean shadow) {
        super(text, red, green, blue, alpha, shadow);
    }
}
