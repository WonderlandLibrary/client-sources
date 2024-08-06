package com.shroomclient.shroomclientnextgen.listeners;

import com.shroomclient.shroomclientnextgen.annotations.RegisterListeners;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.ClientTickEvent;
import com.shroomclient.shroomclientnextgen.modules.impl.client.ClickGUI;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;

@RegisterListeners
public class FontChangeListener {

    @SubscribeEvent
    public void onGUIRender(ClientTickEvent e) {
        switch (ClickGUI.fontType) {
            case Sans_Serif -> RenderUtil.funnyFont = RenderUtil.Sans_Serif;
            case Serif -> RenderUtil.funnyFont = RenderUtil.Serif;
            case Monospaced -> RenderUtil.funnyFont = RenderUtil.Monospaced;
            case Wingdings -> RenderUtil.funnyFont = RenderUtil.Wingdings;
            case Comic_Sans -> RenderUtil.funnyFont = RenderUtil.Comic_Sans;
            case Times_New_Roman -> RenderUtil.funnyFont =
                RenderUtil.Times_New_Roman;
        }
    }
}
