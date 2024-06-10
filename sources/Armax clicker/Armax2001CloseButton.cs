using System;
using System.Drawing;
using System.Windows.Forms;

namespace WindowsApplication1
{
	// Token: 0x0200000F RID: 15
	internal class Armax2001CloseButton : ThemeControl151
	{
		// Token: 0x060000AC RID: 172 RVA: 0x0000C92C File Offset: 0x0000AD2C
		public Armax2001CloseButton()
		{
			this.SetColor("BackColor", Color.Black);
		}

		// Token: 0x060000AD RID: 173 RVA: 0x0000C944 File Offset: 0x0000AD44
		protected override void ColorHook()
		{
			this.C1 = this.GetColor("BackColor");
			this.C2 = Color.FromArgb(237, 0, 0);
			this.C3 = Color.FromArgb(198, 8, 0);
		}

		// Token: 0x060000AE RID: 174 RVA: 0x0000C97C File Offset: 0x0000AD7C
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
			this.DrawText(Brushes.White, HorizontalAlignment.Center, 0, 0);
		}

		// Token: 0x0400004D RID: 77
		private Color C1;

		// Token: 0x0400004E RID: 78
		private Color C2;

		// Token: 0x0400004F RID: 79
		private Color C3;
	}
}
