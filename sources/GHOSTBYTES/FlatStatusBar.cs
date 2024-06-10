using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace GHOSTBYTES
{
	// Token: 0x0200002A RID: 42
	internal class FlatStatusBar : Control
	{
		// Token: 0x0600021A RID: 538 RVA: 0x00003C9D File Offset: 0x00001E9D
		protected override void CreateHandle()
		{
			base.CreateHandle();
			this.Dock = DockStyle.Bottom;
		}

		// Token: 0x0600021B RID: 539 RVA: 0x00003271 File Offset: 0x00001471
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x170000B8 RID: 184
		// (get) Token: 0x0600021C RID: 540 RVA: 0x00003CAC File Offset: 0x00001EAC
		// (set) Token: 0x0600021D RID: 541 RVA: 0x00003CB4 File Offset: 0x00001EB4
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

		// Token: 0x170000B9 RID: 185
		// (get) Token: 0x0600021E RID: 542 RVA: 0x00003CBD File Offset: 0x00001EBD
		// (set) Token: 0x0600021F RID: 543 RVA: 0x00003CC5 File Offset: 0x00001EC5
		[Category("Colors")]
		public Color TextColor
		{
			get
			{
				return this._TextColor;
			}
			set
			{
				this._TextColor = value;
			}
		}

		// Token: 0x170000BA RID: 186
		// (get) Token: 0x06000220 RID: 544 RVA: 0x00003CCE File Offset: 0x00001ECE
		// (set) Token: 0x06000221 RID: 545 RVA: 0x00003CD6 File Offset: 0x00001ED6
		[Category("Colors")]
		public Color RectColor
		{
			get
			{
				return this._RectColor;
			}
			set
			{
				this._RectColor = value;
			}
		}

		// Token: 0x170000BB RID: 187
		// (get) Token: 0x06000222 RID: 546 RVA: 0x00003CDF File Offset: 0x00001EDF
		// (set) Token: 0x06000223 RID: 547 RVA: 0x00003CE7 File Offset: 0x00001EE7
		public bool ShowTimeDate
		{
			get
			{
				return this._ShowTimeDate;
			}
			set
			{
				this._ShowTimeDate = value;
			}
		}

		// Token: 0x06000224 RID: 548 RVA: 0x0000A42C File Offset: 0x0000862C
		public string GetTimeDate()
		{
			return string.Concat(new string[]
			{
				Conversions.ToString(DateTime.Now.Date),
				" ",
				Conversions.ToString(DateTime.Now.Hour),
				":",
				Conversions.ToString(DateTime.Now.Minute)
			});
		}

		// Token: 0x06000225 RID: 549 RVA: 0x0000A494 File Offset: 0x00008694
		public FlatStatusBar()
		{
			this._ShowTimeDate = false;
			this._BaseColor = Color.FromArgb(50, 50, 50);
			this._TextColor = Color.White;
			this._RectColor = Color.FromArgb(0, 170, 220);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.Font = new Font("Segoe UI", 8f);
			this.ForeColor = Color.White;
			base.Size = new Size(base.Width, 20);
		}

		// Token: 0x06000226 RID: 550 RVA: 0x0000A528 File Offset: 0x00008728
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = base.Width;
			this.H = base.Height;
			Rectangle rect = new Rectangle(0, 0, this.W, this.H);
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BaseColor);
			g.FillRectangle(new SolidBrush(this.BaseColor), rect);
			g.DrawString(this.Text, this.Font, Brushes.White, new Rectangle(10, 4, this.W, this.H), Helpers.NearSF);
			g.FillRectangle(new SolidBrush(this._RectColor), new Rectangle(4, 4, 4, 14));
			if (this.ShowTimeDate)
			{
				g.DrawString(this.GetTimeDate(), this.Font, new SolidBrush(this._TextColor), new Rectangle(-4, 2, this.W, this.H), new StringFormat
				{
					Alignment = StringAlignment.Far,
					LineAlignment = StringAlignment.Center
				});
			}
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		// Token: 0x040000C6 RID: 198
		private int W;

		// Token: 0x040000C7 RID: 199
		private int H;

		// Token: 0x040000C8 RID: 200
		private bool _ShowTimeDate;

		// Token: 0x040000C9 RID: 201
		private Color _BaseColor;

		// Token: 0x040000CA RID: 202
		private Color _TextColor;

		// Token: 0x040000CB RID: 203
		private Color _RectColor;
	}
}
