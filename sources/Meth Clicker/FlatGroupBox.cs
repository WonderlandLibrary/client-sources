using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x0200001C RID: 28
	internal class FlatGroupBox : ContainerControl
	{
		// Token: 0x170000A8 RID: 168
		// (get) Token: 0x06000226 RID: 550 RVA: 0x0001062A File Offset: 0x0000E82A
		// (set) Token: 0x06000227 RID: 551 RVA: 0x00010632 File Offset: 0x0000E832
		[Category("Colors")]
		public Color BaseColor
		{
			get
			{
				return this._BaseColor;
			}
			set
			{
				this._BaseColor = value;
			}
		}

		// Token: 0x170000A9 RID: 169
		// (get) Token: 0x06000228 RID: 552 RVA: 0x0001063B File Offset: 0x0000E83B
		// (set) Token: 0x06000229 RID: 553 RVA: 0x00010643 File Offset: 0x0000E843
		public bool ShowText
		{
			get
			{
				return this._ShowText;
			}
			set
			{
				this._ShowText = value;
			}
		}

		// Token: 0x0600022A RID: 554 RVA: 0x0001064C File Offset: 0x0000E84C
		public FlatGroupBox()
		{
			this._ShowText = true;
			this._BaseColor = Color.FromArgb(60, 70, 73);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.Transparent;
			base.Size = new Size(240, 180);
			this.Font = new Font("Segoe ui", 10f);
		}

		// Token: 0x0600022B RID: 555 RVA: 0x000106C0 File Offset: 0x0000E8C0
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = base.Width - 1;
			this.H = base.Height - 1;
			GraphicsPath path = new GraphicsPath();
			GraphicsPath path2 = new GraphicsPath();
			GraphicsPath path3 = new GraphicsPath();
			Rectangle rectangle = new Rectangle(8, 8, this.W - 16, this.H - 16);
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			path = Helpers.RoundRec(rectangle, 8);
			g.FillPath(new SolidBrush(this._BaseColor), path);
			path2 = Helpers.DrawArrow(28, 2, false);
			g.FillPath(new SolidBrush(this._BaseColor), path2);
			path3 = Helpers.DrawArrow(28, 8, true);
			g.FillPath(new SolidBrush(Color.FromArgb(60, 70, 73)), path3);
			if (this.ShowText)
			{
				g.DrawString(this.Text, this.Font, new SolidBrush(Helpers._FlatColor), new Rectangle(16, 16, this.W, this.H), Helpers.NearSF);
			}
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		// Token: 0x04000118 RID: 280
		private int W;

		// Token: 0x04000119 RID: 281
		private int H;

		// Token: 0x0400011A RID: 282
		private bool _ShowText;

		// Token: 0x0400011B RID: 283
		private Color _BaseColor;
	}
}
