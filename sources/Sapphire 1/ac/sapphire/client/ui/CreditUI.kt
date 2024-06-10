package ac.sapphire.client.ui

import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.ScaledResolution
import java.awt.Color

class CreditUI : GuiScreen() {
    private val credits = arrayOf("https://github.com/Vextrall" to "Alt Manager", "Lemon" to "Click GUI")

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()

        var y = 20
        val centerX = ScaledResolution(mc).scaledWidth / 2

        for (credit in credits) {
            drawCenteredString(mc.fontRendererObj, credit.first, centerX, y, Color.WHITE.rgb)
            drawCenteredString(
                mc.fontRendererObj, credit.second, centerX, y + mc.fontRendererObj.FONT_HEIGHT + 2, Color.WHITE.rgb
            )

            drawHorizontalLine(0, mc.displayWidth, y + mc.fontRendererObj.FONT_HEIGHT*2 + 2, Color.WHITE.rgb)
            y += mc.fontRendererObj.FONT_HEIGHT*2 + 8
        }
    }
}