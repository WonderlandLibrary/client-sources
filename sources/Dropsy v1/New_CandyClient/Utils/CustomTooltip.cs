using System;
using System.Drawing;
using System.Windows.Forms;

namespace New_CandyClient.Utils
{
	// Token: 0x0200002C RID: 44
	internal class CustomTooltip : ToolTip
	{
		// Token: 0x06000161 RID: 353 RVA: 0x000128E8 File Offset: 0x00010CE8
		public CustomTooltip()
		{
			base.BackColor = Color.FromArgb(20, 20, 20);
			base.ForeColor = Color.White;
			base.UseAnimation = true;
			base.UseFading = true;
			base.OwnerDraw = true;
			base.Draw += this.CustomTooltip_Draw;
		}

		// Token: 0x06000162 RID: 354 RVA: 0x0001293E File Offset: 0x00010D3E
		private void CustomTooltip_Draw(object sender, DrawToolTipEventArgs e)
		{
			e.DrawBackground();
			e.DrawBorder();
			e.DrawText();
		}
	}
}
