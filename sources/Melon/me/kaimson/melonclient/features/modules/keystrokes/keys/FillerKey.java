package me.kaimson.melonclient.features.modules.keystrokes.keys;

import me.kaimson.melonclient.features.modules.keystrokes.*;

public class FillerKey extends Key
{
    public FillerKey(final int gapSize, final KeystrokesModule keystrokesModule) {
        super(gapSize, null, keystrokesModule);
    }
    
    @Override
    public void render() {
    }
}
