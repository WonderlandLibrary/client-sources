using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x02000021 RID: 33
	public class LogInSeperator : Control
	{
		// Token: 0x170000C2 RID: 194
		// (get) Token: 0x06000232 RID: 562 RVA: 0x0000E024 File Offset: 0x0000C224
		// (set) Token: 0x06000233 RID: 563 RVA: 0x00002FB1 File Offset: 0x000011B1
		[Category("Control")]
		public float Thickness
		{
			get
			{
				return this._Thickness;
			}
			set
			{
				this._Thickness = value;
			}
		}

		// Token: 0x170000C3 RID: 195
		// (get) Token: 0x06000234 RID: 564 RVA: 0x0000E03C File Offset: 0x0000C23C
		// (set) Token: 0x06000235 RID: 565 RVA: 0x00002FBB File Offset: 0x000011BB
		[Category("Control")]
		public LogInSeperator.Style Alignment
		{
			get
			{
				return this._Alignment;
			}
			set
			{
				this._Alignment = value;
			}
		}

		// Token: 0x170000C4 RID: 196
		// (get) Token: 0x06000236 RID: 566 RVA: 0x0000E054 File Offset: 0x0000C254
		// (set) Token: 0x06000237 RID: 567 RVA: 0x00002FC5 File Offset: 0x000011C5
		[Category("Colours")]
		public Color SeperatorColour
		{
			get
			{
				return this._SeperatorColour;
			}
			set
			{
				this._SeperatorColour = value;
			}
		}

		// Token: 0x06000238 RID: 568 RVA: 0x0000E06C File Offset: 0x0000C26C
		public LogInSeperator()
		{
			this._SeperatorColour = Color.FromArgb(35, 35, 35);
			this._Alignment = LogInSeperator.Style.Horizontal;
			this._Thickness = 1f;
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.Transparent;
			base.Size = new Size(20, 20);
		}

		// Token: 0x06000239 RID: 569 RVA: 0x0000E0D8 File Offset: 0x0000C2D8
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics G = e.Graphics;
			checked
			{
				Rectangle Base = new Rectangle(0, 0, base.Width - 1, base.Height - 1);
				Graphics graphics = G;
				graphics.SmoothingMode = SmoothingMode.HighQuality;
				graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
				LogInSeperator.Style alignment = this._Alignment;
				if (alignment != LogInSeperator.Style.Horizontal)
				{
					if (alignment == LogInSeperator.Style.Verticle)
					{
						graphics.DrawLine(new Pen(this._SeperatorColour, this._Thickness), new Point((int)Math.Round((double)base.Width / 2.0), 0), new Point((int)Math.Round((double)base.Width / 2.0), base.Height));
					}
				}
				else
				{
					graphics.DrawLine(new Pen(this._SeperatorColour, this._Thickness), new Point(0, (int)Math.Round((double)base.Height / 2.0)), new Point(base.Width, (int)Math.Round((double)base.Height / 2.0)));
				}
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			}
		}

		// Token: 0x040000E0 RID: 224
		private Color _SeperatorColour;

		// Token: 0x040000E1 RID: 225
		private LogInSeperator.Style _Alignment;

		// Token: 0x040000E2 RID: 226
		private float _Thickness;

		// Token: 0x02000022 RID: 34
		public enum Style
		{
			// Token: 0x040000E4 RID: 228
			Horizontal,
			// Token: 0x040000E5 RID: 229
			Verticle
		}
	}
}
