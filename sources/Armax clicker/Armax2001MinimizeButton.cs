using System;
using System.Drawing;
using System.Windows.Forms;

namespace WindowsApplication1
{
	// Token: 0x02000010 RID: 16
	internal class Armax2001MinimizeButton : ThemeControl151
	{
		// Token: 0x060000AF RID: 175 RVA: 0x0000CA20 File Offset: 0x0000AE20
		public Armax2001MinimizeButton()
		{
			this.SetColor("BackColor", Color.Black);
		}

		// Token: 0x060000B0 RID: 176 RVA: 0x0000CA38 File Offset: 0x0000AE38
		protected override void ColorHook()
		{
			this.C1 = this.GetColor("BackColor");
			this.C2 = Color.FromArgb(255, 255, 255);
			this.C3 = Color.FromArgb(30, 127, 203);
		}

		// Token: 0x060000B1 RID: 177 RVA: 0x0000CA84 File Offset: 0x0000AE84
		protected override void PaintHook()
		{
			this.G.Clear(this.C1);
			if (this.State == MouseState.Over)
			{
				this.DrawGradient(this.C3, this.C3, 0, 0, this.Width, this.Height);
			}
			else if (this.State == MouseState.Down)
			{
				this.DrawGradient(this.C2, this.C2, 0, 0, this.Width, this.Height);
			}
			else
			{
				this.DrawGradient(this.C2, this.C2, 0, 0, this.Width, this.Height);
			}
			this.DrawText(Brushes.Black, HorizontalAlignment.Center, 0, 0);
		}

		// Token: 0x04000050 RID: 80
		private Color C1;

		// Token: 0x04000051 RID: 81
		private Color C2;

		// Token: 0x04000052 RID: 82
		private Color C3;
	}
}
