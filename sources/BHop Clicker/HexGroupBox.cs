using System;
using System.Drawing;
using System.Windows.Forms;

namespace shit_temple
{
	// Token: 0x0200000F RID: 15
	internal class HexGroupBox : ContainerControl
	{
		// Token: 0x06000047 RID: 71 RVA: 0x00002497 File Offset: 0x00000697
		public HexGroupBox()
		{
			base.Size = new Size(200, 100);
		}

		// Token: 0x06000048 RID: 72 RVA: 0x00003238 File Offset: 0x00001438
		protected override void OnPaint(PaintEventArgs e)
		{
			base.OnPaint(e);
			Graphics graphics = e.Graphics;
			graphics.Clear(Color.FromArgb(30, 33, 40));
			graphics.DrawPath(new Pen(Color.FromArgb(236, 95, 75)), Functions.RoundRec(checked(new Rectangle(0, 0, base.Width - 1, base.Height - 1)), 4));
			graphics.DrawString(this.Text, new Font("Segoe UI", 9f), Brushes.White, new Point(5, 5));
		}
	}
}
