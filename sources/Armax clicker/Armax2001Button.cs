using System;
using System.Drawing;
using System.Windows.Forms;

namespace WindowsApplication1
{
	// Token: 0x0200000E RID: 14
	internal class Armax2001Button : ThemeControl151
	{
		// Token: 0x060000A9 RID: 169 RVA: 0x0000C7EC File Offset: 0x0000ABEC
		public Armax2001Button()
		{
			this.SetColor("BackColor", Color.Black);
		}

		// Token: 0x060000AA RID: 170 RVA: 0x0000C804 File Offset: 0x0000AC04
		protected override void ColorHook()
		{
			this.C1 = this.GetColor("BackColor");
			this.C2 = Color.FromArgb(230, 230, 230);
			this.C3 = Color.FromArgb(239, 239, 239);
			this.P1 = new Pen(Color.FromArgb(172, 172, 172));
		}

		// Token: 0x060000AB RID: 171 RVA: 0x0000C878 File Offset: 0x0000AC78
		protected override void PaintHook()
		{
			this.G.Clear(this.C1);
			if (this.State == MouseState.Over)
			{
				this.DrawGradient(this.C2, this.C3, 0, 0, this.Width, this.Height);
			}
			else if (this.State == MouseState.Down)
			{
				this.DrawGradient(this.C3, this.C2, 0, 0, this.Width, this.Height);
			}
			else
			{
				this.DrawGradient(this.C3, this.C2, 0, 0, this.Width, this.Height);
			}
			this.DrawBorders(this.P1, this.ClientRectangle);
			this.DrawText(Brushes.Black, HorizontalAlignment.Center, 0, 0);
		}

		// Token: 0x04000049 RID: 73
		private Color C1;

		// Token: 0x0400004A RID: 74
		private Color C2;

		// Token: 0x0400004B RID: 75
		private Color C3;

		// Token: 0x0400004C RID: 76
		private Pen P1;
	}
}
