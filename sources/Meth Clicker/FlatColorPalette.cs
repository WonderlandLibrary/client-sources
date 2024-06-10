using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x0200001B RID: 27
	internal class FlatColorPalette : Control
	{
		// Token: 0x06000211 RID: 529 RVA: 0x00010262 File Offset: 0x0000E462
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Width = 180;
			base.Height = 80;
		}

		// Token: 0x1700009F RID: 159
		// (get) Token: 0x06000212 RID: 530 RVA: 0x0001027E File Offset: 0x0000E47E
		// (set) Token: 0x06000213 RID: 531 RVA: 0x00010286 File Offset: 0x0000E486
		[Category("Colors")]
		public Color Red
		{
			get
			{
				return this._Red;
			}
			set
			{
				this._Red = value;
			}
		}

		// Token: 0x170000A0 RID: 160
		// (get) Token: 0x06000214 RID: 532 RVA: 0x0001028F File Offset: 0x0000E48F
		// (set) Token: 0x06000215 RID: 533 RVA: 0x00010297 File Offset: 0x0000E497
		[Category("Colors")]
		public Color Cyan
		{
			get
			{
				return this._Cyan;
			}
			set
			{
				this._Cyan = value;
			}
		}

		// Token: 0x170000A1 RID: 161
		// (get) Token: 0x06000216 RID: 534 RVA: 0x000102A0 File Offset: 0x0000E4A0
		// (set) Token: 0x06000217 RID: 535 RVA: 0x000102A8 File Offset: 0x0000E4A8
		[Category("Colors")]
		public Color Blue
		{
			get
			{
				return this._Blue;
			}
			set
			{
				this._Blue = value;
			}
		}

		// Token: 0x170000A2 RID: 162
		// (get) Token: 0x06000218 RID: 536 RVA: 0x000102B1 File Offset: 0x0000E4B1
		// (set) Token: 0x06000219 RID: 537 RVA: 0x000102B9 File Offset: 0x0000E4B9
		[Category("Colors")]
		public Color LimeGreen
		{
			get
			{
				return this._LimeGreen;
			}
			set
			{
				this._LimeGreen = value;
			}
		}

		// Token: 0x170000A3 RID: 163
		// (get) Token: 0x0600021A RID: 538 RVA: 0x000102C2 File Offset: 0x0000E4C2
		// (set) Token: 0x0600021B RID: 539 RVA: 0x000102CA File Offset: 0x0000E4CA
		[Category("Colors")]
		public Color Orange
		{
			get
			{
				return this._Orange;
			}
			set
			{
				this._Orange = value;
			}
		}

		// Token: 0x170000A4 RID: 164
		// (get) Token: 0x0600021C RID: 540 RVA: 0x000102D3 File Offset: 0x0000E4D3
		// (set) Token: 0x0600021D RID: 541 RVA: 0x000102DB File Offset: 0x0000E4DB
		[Category("Colors")]
		public Color Purple
		{
			get
			{
				return this._Purple;
			}
			set
			{
				this._Purple = value;
			}
		}

		// Token: 0x170000A5 RID: 165
		// (get) Token: 0x0600021E RID: 542 RVA: 0x000102E4 File Offset: 0x0000E4E4
		// (set) Token: 0x0600021F RID: 543 RVA: 0x000102EC File Offset: 0x0000E4EC
		[Category("Colors")]
		public Color Black
		{
			get
			{
				return this._Black;
			}
			set
			{
				this._Black = value;
			}
		}

		// Token: 0x170000A6 RID: 166
		// (get) Token: 0x06000220 RID: 544 RVA: 0x000102F5 File Offset: 0x0000E4F5
		// (set) Token: 0x06000221 RID: 545 RVA: 0x000102FD File Offset: 0x0000E4FD
		[Category("Colors")]
		public Color Gray
		{
			get
			{
				return this._Gray;
			}
			set
			{
				this._Gray = value;
			}
		}

		// Token: 0x170000A7 RID: 167
		// (get) Token: 0x06000222 RID: 546 RVA: 0x00010306 File Offset: 0x0000E506
		// (set) Token: 0x06000223 RID: 547 RVA: 0x0001030E File Offset: 0x0000E50E
		[Category("Colors")]
		public Color White
		{
			get
			{
				return this._White;
			}
			set
			{
				this._White = value;
			}
		}

		// Token: 0x06000224 RID: 548 RVA: 0x00010318 File Offset: 0x0000E518
		public FlatColorPalette()
		{
			this._Red = Color.FromArgb(220, 85, 96);
			this._Cyan = Color.FromArgb(10, 154, 157);
			this._Blue = Color.FromArgb(0, 128, 255);
			this._LimeGreen = Color.FromArgb(35, 168, 109);
			this._Orange = Color.FromArgb(253, 181, 63);
			this._Purple = Color.FromArgb(155, 88, 181);
			this._Black = Color.FromArgb(45, 47, 49);
			this._Gray = Color.FromArgb(63, 70, 73);
			this._White = Color.FromArgb(243, 243, 243);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.FromArgb(60, 70, 73);
			base.Size = new Size(160, 80);
			this.Font = new Font("Segoe UI", 12f);
		}

		// Token: 0x06000225 RID: 549 RVA: 0x00010438 File Offset: 0x0000E638
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = base.Width - 1;
			this.H = base.Height - 1;
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			g.FillRectangle(new SolidBrush(this._Red), new Rectangle(0, 0, 20, 40));
			g.FillRectangle(new SolidBrush(this._Cyan), new Rectangle(20, 0, 20, 40));
			g.FillRectangle(new SolidBrush(this._Blue), new Rectangle(40, 0, 20, 40));
			g.FillRectangle(new SolidBrush(this._LimeGreen), new Rectangle(60, 0, 20, 40));
			g.FillRectangle(new SolidBrush(this._Orange), new Rectangle(80, 0, 20, 40));
			g.FillRectangle(new SolidBrush(this._Purple), new Rectangle(100, 0, 20, 40));
			g.FillRectangle(new SolidBrush(this._Black), new Rectangle(120, 0, 20, 40));
			g.FillRectangle(new SolidBrush(this._Gray), new Rectangle(140, 0, 20, 40));
			g.FillRectangle(new SolidBrush(this._White), new Rectangle(160, 0, 20, 40));
			g.DrawString("Color Palette", this.Font, new SolidBrush(this._White), new Rectangle(0, 22, this.W, this.H), Helpers.CenterSF);
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		// Token: 0x0400010D RID: 269
		private int W;

		// Token: 0x0400010E RID: 270
		private int H;

		// Token: 0x0400010F RID: 271
		private Color _Red;

		// Token: 0x04000110 RID: 272
		private Color _Cyan;

		// Token: 0x04000111 RID: 273
		private Color _Blue;

		// Token: 0x04000112 RID: 274
		private Color _LimeGreen;

		// Token: 0x04000113 RID: 275
		private Color _Orange;

		// Token: 0x04000114 RID: 276
		private Color _Purple;

		// Token: 0x04000115 RID: 277
		private Color _Black;

		// Token: 0x04000116 RID: 278
		private Color _Gray;

		// Token: 0x04000117 RID: 279
		private Color _White;
	}
}
