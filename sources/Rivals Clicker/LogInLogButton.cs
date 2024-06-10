using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x02000015 RID: 21
	public class LogInLogButton : Control
	{
		// Token: 0x0600017E RID: 382 RVA: 0x00002AE6 File Offset: 0x00000CE6
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = DrawHelpers.MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x0600017F RID: 383 RVA: 0x00002AFF File Offset: 0x00000CFF
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = DrawHelpers.MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000180 RID: 384 RVA: 0x00002B18 File Offset: 0x00000D18
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = DrawHelpers.MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000181 RID: 385 RVA: 0x00002B31 File Offset: 0x00000D31
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = DrawHelpers.MouseState.None;
			base.Invalidate();
		}

		// Token: 0x1700008A RID: 138
		// (get) Token: 0x06000182 RID: 386 RVA: 0x0000B910 File Offset: 0x00009B10
		// (set) Token: 0x06000183 RID: 387 RVA: 0x00002B4A File Offset: 0x00000D4A
		[Category("Colours")]
		public Color ArcColour
		{
			get
			{
				return this._ArcColour;
			}
			set
			{
				this._ArcColour = value;
			}
		}

		// Token: 0x1700008B RID: 139
		// (get) Token: 0x06000184 RID: 388 RVA: 0x0000B928 File Offset: 0x00009B28
		// (set) Token: 0x06000185 RID: 389 RVA: 0x00002B54 File Offset: 0x00000D54
		[Category("Colours")]
		public Color BorderColour
		{
			get
			{
				return this._BorderColour;
			}
			set
			{
				this._BorderColour = value;
			}
		}

		// Token: 0x1700008C RID: 140
		// (get) Token: 0x06000186 RID: 390 RVA: 0x0000B940 File Offset: 0x00009B40
		// (set) Token: 0x06000187 RID: 391 RVA: 0x00002B5E File Offset: 0x00000D5E
		[Category("Colours")]
		public Color ArrowColour
		{
			get
			{
				return this._ArrowColour;
			}
			set
			{
				this._ArrowColour = value;
			}
		}

		// Token: 0x1700008D RID: 141
		// (get) Token: 0x06000188 RID: 392 RVA: 0x0000B958 File Offset: 0x00009B58
		// (set) Token: 0x06000189 RID: 393 RVA: 0x00002B68 File Offset: 0x00000D68
		[Category("Colours")]
		public Color ArrowBorderColour
		{
			get
			{
				return this._ArrowBorderColour;
			}
			set
			{
				this._ArrowBorderColour = value;
			}
		}

		// Token: 0x1700008E RID: 142
		// (get) Token: 0x0600018A RID: 394 RVA: 0x0000B970 File Offset: 0x00009B70
		// (set) Token: 0x0600018B RID: 395 RVA: 0x00002B72 File Offset: 0x00000D72
		[Category("Colours")]
		public Color HoverColour
		{
			get
			{
				return this._HoverColour;
			}
			set
			{
				this._HoverColour = value;
			}
		}

		// Token: 0x1700008F RID: 143
		// (get) Token: 0x0600018C RID: 396 RVA: 0x0000B988 File Offset: 0x00009B88
		// (set) Token: 0x0600018D RID: 397 RVA: 0x00002B7C File Offset: 0x00000D7C
		[Category("Colours")]
		public Color PressedColour
		{
			get
			{
				return this._PressedColour;
			}
			set
			{
				this._PressedColour = value;
			}
		}

		// Token: 0x17000090 RID: 144
		// (get) Token: 0x0600018E RID: 398 RVA: 0x0000B9A0 File Offset: 0x00009BA0
		// (set) Token: 0x0600018F RID: 399 RVA: 0x00002B86 File Offset: 0x00000D86
		[Category("Colours")]
		public Color NormalColour
		{
			get
			{
				return this._NormalColour;
			}
			set
			{
				this._NormalColour = value;
			}
		}

		// Token: 0x06000190 RID: 400 RVA: 0x00002B90 File Offset: 0x00000D90
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Size = new Size(50, 50);
		}

		// Token: 0x06000191 RID: 401 RVA: 0x0000B9B8 File Offset: 0x00009BB8
		public LogInLogButton()
		{
			this.State = DrawHelpers.MouseState.None;
			this._ArcColour = Color.FromArgb(43, 43, 43);
			this._ArrowColour = Color.FromArgb(235, 233, 234);
			this._ArrowBorderColour = Color.FromArgb(170, 170, 170);
			this._BorderColour = Color.FromArgb(35, 35, 35);
			this._HoverColour = Color.FromArgb(0, 130, 169);
			this._PressedColour = Color.FromArgb(0, 145, 184);
			this._NormalColour = Color.FromArgb(0, 160, 199);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Size = new Size(50, 50);
			this.BackColor = Color.FromArgb(54, 54, 54);
		}

		// Token: 0x06000192 RID: 402 RVA: 0x0000BAA4 File Offset: 0x00009CA4
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics G = e.Graphics;
			GraphicsPath GP = new GraphicsPath();
			GraphicsPath GP2 = new GraphicsPath();
			Graphics graphics = G;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.Clear(this.BackColor);
			Point[] P = new Point[]
			{
				new Point(18, 22),
				new Point(28, 22),
				new Point(28, 18),
				new Point(34, 25),
				new Point(28, 32),
				new Point(28, 28),
				new Point(18, 28)
			};
			checked
			{
				switch (this.State)
				{
				case DrawHelpers.MouseState.None:
					graphics.FillEllipse(new SolidBrush(Color.FromArgb(56, 56, 56)), new Rectangle(3, 3, base.Width - 3 - 3, base.Height - 3 - 3));
					graphics.DrawArc(new Pen(new SolidBrush(this._ArcColour), 4f), 3, 3, base.Width - 3 - 3, base.Height - 3 - 3, -90, 360);
					graphics.DrawEllipse(new Pen(this._BorderColour), new Rectangle(1, 1, base.Height - 3, base.Height - 3));
					graphics.FillEllipse(new SolidBrush(this._NormalColour), new Rectangle(5, 5, base.Height - 11, base.Height - 11));
					graphics.FillPolygon(new SolidBrush(this._ArrowColour), P);
					graphics.DrawPolygon(new Pen(this._ArrowBorderColour), P);
					break;
				case DrawHelpers.MouseState.Over:
					graphics.DrawArc(new Pen(new SolidBrush(this._ArcColour), 4f), 3, 3, base.Width - 3 - 3, base.Height - 3 - 3, -90, 360);
					graphics.DrawEllipse(new Pen(this._BorderColour), new Rectangle(1, 1, base.Height - 3, base.Height - 3));
					graphics.FillEllipse(new SolidBrush(this._HoverColour), new Rectangle(6, 6, base.Height - 13, base.Height - 13));
					graphics.FillPolygon(new SolidBrush(this._ArrowColour), P);
					graphics.DrawPolygon(new Pen(this._ArrowBorderColour), P);
					break;
				case DrawHelpers.MouseState.Down:
					graphics.DrawArc(new Pen(new SolidBrush(this._ArcColour), 4f), 3, 3, base.Width - 3 - 3, base.Height - 3 - 3, -90, 360);
					graphics.DrawEllipse(new Pen(this._BorderColour), new Rectangle(1, 1, base.Height - 3, base.Height - 3));
					graphics.FillEllipse(new SolidBrush(this._PressedColour), new Rectangle(6, 6, base.Height - 13, base.Height - 13));
					graphics.FillPolygon(new SolidBrush(this._ArrowColour), P);
					graphics.DrawPolygon(new Pen(this._ArrowBorderColour), P);
					break;
				}
				GP.Dispose();
				GP2.Dispose();
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			}
		}

		// Token: 0x0400009C RID: 156
		private DrawHelpers.MouseState State;

		// Token: 0x0400009D RID: 157
		private Color _ArcColour;

		// Token: 0x0400009E RID: 158
		private Color _ArrowColour;

		// Token: 0x0400009F RID: 159
		private Color _ArrowBorderColour;

		// Token: 0x040000A0 RID: 160
		private Color _BorderColour;

		// Token: 0x040000A1 RID: 161
		private Color _HoverColour;

		// Token: 0x040000A2 RID: 162
		private Color _PressedColour;

		// Token: 0x040000A3 RID: 163
		private Color _NormalColour;
	}
}
