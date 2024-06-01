package best.actinium.module.impl.visual;

import best.actinium.event.api.Callback;
import best.actinium.event.impl.render.Render2DEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.util.render.RenderEngine2D;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.maltaisn.msdfgdx.FontStyle;
import com.maltaisn.msdfgdx.MsdfFont;
import com.maltaisn.msdfgdx.MsdfShader;
import com.maltaisn.msdfgdx.widget.MsdfLabel;

@ModuleInfo(
        name = "Font Test",
        description = "Used to test new insane scale afhofnt idiakdsawduasd",
        category = ModuleCategory.VISUAL
)
public class FontRenderTestModule extends Module {

    @Callback
    public void onrRender(Render2DEvent event) {
        RenderEngine2D.begin();
        RenderEngine2D.drawString();
        RenderEngine2D.end();
        //Skin skin = new Skin();
       // skin.add("default", new MsdfShader());
       // skin.add("roboto", new MsdfFont(Gdx.files.internal("roboto.fnt"), 32f, 5f));

     //   FontStyle fontStyle = new FontStyle()
      //          .setFontName("roboto")
       //         .setColor(Color.WHITE)
      //          .setSize(48f);

      //  MsdfLabel label = new MsdfLabel("My text", skin, fontStyle);
       // label.getFont()
        //stage.addActor(label);
    }
}
