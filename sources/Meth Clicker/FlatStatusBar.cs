using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace Meth
{
	// Token: 0x0200002B RID: 43
	internal class FlatStatusBar : Control
	{
		// Token: 0x0600030B RID: 779 RVA: 0x0001505B File Offset: 0x0001325B
		protected override void CreateHandle()
		{
			base.CreateHandle();
			this.Dock = DockStyle.Bottom;
		}

		// Token: 0x0600030C RID: 780 RVA: 0x0000D7C2 File Offset: 0x0000B9C2
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x170000E0 RID: 224
		// (get) Token: 0x0600030D RID: 781 RVA: 0x0001506A File Offset: 0x0001326A
		// (set) Token: 0x0600030E RID: 782 RVA: 0x00015072 File Offset: 0x00013272
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

		// Token: 0x170000E1 RID: 225
		// (get) Token: 0x0600030F RID: 783 RVA: 0x0001507B File Offset: 0x0001327B
		// (set) Token: 0x06000310 RID: 784 RVA: 0x00015083 File Offset: 0x00013283
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

		// Token: 0x170000E2 RID: 226
		// (get) Token: 0x06000311 RID: 785 RVA: 0x0001508C File Offset: 0x0001328C
		// (set) Token: 0x06000312 RID: 786 RVA: 0x00015094 File Offset: 0x00013294
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

		// Token: 0x170000E3 RID: 227
		// (get) Token: 0x06000313 RID: 787 RVA: 0x0001509D File Offset: 0x0001329D
		// (set) Token: 0x06000314 RID: 788 RVA: 0x000150A5 File Offset: 0x000132A5
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

		// Token: 0x06000315 RID: 789 RVA: 0x000150B0 File Offset: 0x000132B0
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

		// Token: 0x06000316 RID: 790 RVA: 0x00015118 File Offset: 0x00013318
		public FlatStatusBar()
		{
			this._ShowTimeDate = false;
			this._BaseColor = Color.FromArgb(45, 47, 49);
			this._TextColor = Color.White;
			this._RectColor = Helpers._FlatColor;
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.Font = new Font("Segoe UI", 8f);
			this.ForeColor = Color.White;
			base.Size = new Size(base.Width, 20);
		}

		// Token: 0x06000317 RID: 791 RVA: 0x000151A0 File Offset: 0x000133A0
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

		// Token: 0x04000192 RID: 402
		private int W;

		// Token: 0x04000193 RID: 403
		private int H;

		// Token: 0x04000194 RID: 404
		private bool _ShowTimeDate;

		// Token: 0x04000195 RID: 405
		private Color _BaseColor;

		// Token: 0x04000196 RID: 406
		private Color _TextColor;

		// Token: 0x04000197 RID: 407
		private Color _RectColor;
	}
}
