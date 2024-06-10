using System;
using System.Drawing;
using System.Windows.Forms;

namespace WindowsApplication1
{
	// Token: 0x0200000D RID: 13
	internal class Armax2001Theme : ThemeContainer151
	{
		// Token: 0x060000A6 RID: 166 RVA: 0x0000C5C0 File Offset: 0x0000A9C0
		public Armax2001Theme()
		{
			this.MoveHeight = 20;
			this.SetColor("BackColor", Color.White);
			this.TransparencyKey = Color.Fuchsia;
		}

		// Token: 0x060000A7 RID: 167 RVA: 0x0000C5F8 File Offset: 0x0000A9F8
		protected override void ColorHook()
		{
			this.C1 = this.GetColor("BackColor");
			this.C2 = Color.FromArgb(255, 255, 255);
			this.C3 = Color.FromArgb(255, 255, 255);
			this.P1 = new Pen(Color.FromArgb(90, 90, 50));
			this.P2 = new Pen(Color.FromArgb(90, 90, 90));
		}

		// Token: 0x060000A8 RID: 168 RVA: 0x0000C678 File Offset: 0x0000AA78
		protected override void PaintHook()
		{
			this.G.Clear(this.C1);
			this.DrawGradient(Color.FromArgb(15, 15, 15), Color.FromArgb(30, 30, 30), 0, 0, this.Width, this.Height, 90f);
			this.DrawGradient(this.C2, this.C3, 0, 0, this.Width, this.Height);
			this.G.DrawLine(this.P1, 0, 0, 0, 20);
			checked
			{
				this.G.DrawLine(this.P1, this.Width - 1, 0, this.Width - 1, 25);
				this.G.DrawLine(this.P2, 0, 0, 0, this.Height);
				this.G.DrawLine(this.P2, this.Width - 1, 0, this.Width - 1, this.Height);
				this.G.DrawLine(this.P2, 0, this.Height - 1, this.Width, this.Height - 1);
				this.G.FillRectangle(new SolidBrush(Color.FromArgb(240, 240, 240)), 10, 20, this.Width - 20, this.Height - 30);
				this.G.DrawLine(this.P2, 0, 0, this.Width, 0);
				this.DrawText(Brushes.Black, HorizontalAlignment.Center, 0, 0);
			}
		}

		// Token: 0x04000043 RID: 67
		private Color C1;

		// Token: 0x04000044 RID: 68
		private Color C2;

		// Token: 0x04000045 RID: 69
		private Color C3;

		// Token: 0x04000046 RID: 70
		private SolidBrush B1;

		// Token: 0x04000047 RID: 71
		private Pen P1;

		// Token: 0x04000048 RID: 72
		private Pen P2;
	}
}
