using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace Meth
{
	// Token: 0x0200002A RID: 42
	[DefaultEvent("Scroll")]
	internal class FlatTrackBar : Control
	{
		// Token: 0x060002F3 RID: 755 RVA: 0x00014A6C File Offset: 0x00012C6C
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			if (e.Button == MouseButtons.Left)
			{
				this.Val = (int)Math.Round((double)(this._Value - this._Minimum) / (double)(this._Maximum - this._Minimum) * (double)(base.Width - 11));
				this.Track = new Rectangle(this.Val, 0, 10, 20);
				this.Bool = this.Track.Contains(e.Location);
			}
		}

		// Token: 0x060002F4 RID: 756 RVA: 0x00014AF0 File Offset: 0x00012CF0
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			if (this.Bool && e.X > -1 && e.X < base.Width + 1)
			{
				this.Value = this._Minimum + (int)Math.Round((double)(this._Maximum - this._Minimum) * ((double)e.X / (double)base.Width));
			}
		}

		// Token: 0x060002F5 RID: 757 RVA: 0x00014B56 File Offset: 0x00012D56
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.Bool = false;
		}

		// Token: 0x170000D9 RID: 217
		// (get) Token: 0x060002F6 RID: 758 RVA: 0x00014B66 File Offset: 0x00012D66
		// (set) Token: 0x060002F7 RID: 759 RVA: 0x00014B6E File Offset: 0x00012D6E
		public FlatTrackBar._Style Style
		{
			get
			{
				return this.Style_;
			}
			set
			{
				this.Style_ = value;
			}
		}

		// Token: 0x170000DA RID: 218
		// (get) Token: 0x060002F8 RID: 760 RVA: 0x00014B77 File Offset: 0x00012D77
		// (set) Token: 0x060002F9 RID: 761 RVA: 0x00014B7F File Offset: 0x00012D7F
		[Category("Colors")]
		public Color TrackColor
		{
			get
			{
				return this._TrackColor;
			}
			set
			{
				this._TrackColor = value;
			}
		}

		// Token: 0x170000DB RID: 219
		// (get) Token: 0x060002FA RID: 762 RVA: 0x00014B88 File Offset: 0x00012D88
		// (set) Token: 0x060002FB RID: 763 RVA: 0x00014B90 File Offset: 0x00012D90
		[Category("Colors")]
		public Color HatchColor
		{
			get
			{
				return this._HatchColor;
			}
			set
			{
				this._HatchColor = value;
			}
		}

		// Token: 0x14000017 RID: 23
		// (add) Token: 0x060002FC RID: 764 RVA: 0x00014B9C File Offset: 0x00012D9C
		// (remove) Token: 0x060002FD RID: 765 RVA: 0x00014BD4 File Offset: 0x00012DD4
		public event FlatTrackBar.ScrollEventHandler Scroll;

		// Token: 0x170000DC RID: 220
		// (get) Token: 0x060002FE RID: 766 RVA: 0x00014C09 File Offset: 0x00012E09
		// (set) Token: 0x060002FF RID: 767 RVA: 0x00014C11 File Offset: 0x00012E11
		public int Minimum
		{
			get
			{
				return this._Minimum;
			}
			set
			{
				this._Minimum = value;
				if (value > this._Value)
				{
					this._Value = value;
				}
				if (value > this._Maximum)
				{
					this._Maximum = value;
				}
				base.Invalidate();
			}
		}

		// Token: 0x170000DD RID: 221
		// (get) Token: 0x06000300 RID: 768 RVA: 0x00014C44 File Offset: 0x00012E44
		// (set) Token: 0x06000301 RID: 769 RVA: 0x00014C4C File Offset: 0x00012E4C
		public int Maximum
		{
			get
			{
				return this._Maximum;
			}
			set
			{
				this._Maximum = value;
				if (value < this._Value)
				{
					this._Value = value;
				}
				if (value < this._Minimum)
				{
					this._Minimum = value;
				}
				base.Invalidate();
			}
		}

		// Token: 0x170000DE RID: 222
		// (get) Token: 0x06000302 RID: 770 RVA: 0x00014C7F File Offset: 0x00012E7F
		// (set) Token: 0x06000303 RID: 771 RVA: 0x00014C88 File Offset: 0x00012E88
		public int Value
		{
			get
			{
				return this._Value;
			}
			set
			{
				if (value != this._Value)
				{
					if (value <= this._Maximum)
					{
						int minimum = this._Minimum;
					}
					this._Value = value;
					base.Invalidate();
					FlatTrackBar.ScrollEventHandler scrollEvent = this.ScrollEvent;
					if (scrollEvent != null)
					{
						scrollEvent(this);
					}
				}
			}
		}

		// Token: 0x170000DF RID: 223
		// (get) Token: 0x06000304 RID: 772 RVA: 0x00014CCE File Offset: 0x00012ECE
		// (set) Token: 0x06000305 RID: 773 RVA: 0x00014CD6 File Offset: 0x00012ED6
		public bool ShowValue
		{
			get
			{
				return this._ShowValue;
			}
			set
			{
				this._ShowValue = value;
			}
		}

		// Token: 0x06000306 RID: 774 RVA: 0x00014CE0 File Offset: 0x00012EE0
		protected override void OnKeyDown(KeyEventArgs e)
		{
			base.OnKeyDown(e);
			if (e.KeyCode == Keys.Subtract)
			{
				if (this.Value != 0)
				{
					this.Value--;
					return;
				}
			}
			else if (e.KeyCode == Keys.Add && this.Value != this._Maximum)
			{
				this.Value++;
			}
		}

		// Token: 0x06000307 RID: 775 RVA: 0x0000D7C2 File Offset: 0x0000B9C2
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x06000308 RID: 776 RVA: 0x00014D3B File Offset: 0x00012F3B
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 23;
		}

		// Token: 0x06000309 RID: 777 RVA: 0x00014D4C File Offset: 0x00012F4C
		public FlatTrackBar()
		{
			this._Minimum = 1;
			this._Maximum = 10;
			this._ShowValue = false;
			this.BaseColor = Color.FromArgb(45, 47, 49);
			this._TrackColor = Helpers._FlatColor;
			this.SliderColor = Color.FromArgb(25, 27, 29);
			this._HatchColor = Color.FromArgb(23, 148, 92);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Height = 18;
			this.BackColor = Color.FromArgb(60, 70, 73);
		}

		// Token: 0x0600030A RID: 778 RVA: 0x00014DE4 File Offset: 0x00012FE4
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = base.Width - 1;
			this.H = base.Height - 1;
			Rectangle rect = new Rectangle(1, 6, this.W - 2, 8);
			GraphicsPath graphicsPath = new GraphicsPath();
			GraphicsPath graphicsPath2 = new GraphicsPath();
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			this.Val = (int)Math.Round((double)(this._Value - this._Minimum) / (double)(this._Maximum - this._Minimum) * (double)(this.W - 10));
			this.Track = new Rectangle(this.Val, 0, 10, 20);
			this.Knob = new Rectangle(this.Val, 4, 11, 14);
			graphicsPath.AddRectangle(rect);
			g.SetClip(graphicsPath);
			g.FillRectangle(new SolidBrush(this.BaseColor), new Rectangle(0, 7, this.W, 8));
			g.FillRectangle(new SolidBrush(this._TrackColor), new Rectangle(0, 7, this.Track.X + this.Track.Width, 8));
			g.ResetClip();
			HatchBrush brush = new HatchBrush(HatchStyle.Plaid, this.HatchColor, this._TrackColor);
			g.FillRectangle(brush, new Rectangle(-10, 7, this.Track.X + this.Track.Width, 8));
			FlatTrackBar._Style style = this.Style;
			if (style != FlatTrackBar._Style.Slider)
			{
				if (style == FlatTrackBar._Style.Knob)
				{
					graphicsPath2.AddEllipse(this.Knob);
					g.FillPath(new SolidBrush(this.SliderColor), graphicsPath2);
				}
			}
			else
			{
				graphicsPath2.AddRectangle(this.Track);
				g.FillPath(new SolidBrush(this.SliderColor), graphicsPath2);
			}
			if (this.ShowValue)
			{
				g.DrawString(Conversions.ToString(this.Value), new Font("Segoe UI", 8f), Brushes.White, new Rectangle(1, 6, this.W, this.H), new StringFormat
				{
					Alignment = StringAlignment.Far,
					LineAlignment = StringAlignment.Far
				});
			}
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		// Token: 0x04000182 RID: 386
		private int W;

		// Token: 0x04000183 RID: 387
		private int H;

		// Token: 0x04000184 RID: 388
		private int Val;

		// Token: 0x04000185 RID: 389
		private bool Bool;

		// Token: 0x04000186 RID: 390
		private Rectangle Track;

		// Token: 0x04000187 RID: 391
		private Rectangle Knob;

		// Token: 0x04000188 RID: 392
		private FlatTrackBar._Style Style_;

		// Token: 0x0400018A RID: 394
		private int _Minimum;

		// Token: 0x0400018B RID: 395
		private int _Maximum;

		// Token: 0x0400018C RID: 396
		private int _Value;

		// Token: 0x0400018D RID: 397
		private bool _ShowValue;

		// Token: 0x0400018E RID: 398
		private Color BaseColor;

		// Token: 0x0400018F RID: 399
		private Color _TrackColor;

		// Token: 0x04000190 RID: 400
		private Color SliderColor;

		// Token: 0x04000191 RID: 401
		private Color _HatchColor;

		// Token: 0x02000061 RID: 97
		[Flags]
		public enum _Style
		{
			// Token: 0x040001EB RID: 491
			Slider = 0,
			// Token: 0x040001EC RID: 492
			Knob = 1
		}

		// Token: 0x02000062 RID: 98
		// (Invoke) Token: 0x060003B2 RID: 946
		public delegate void ScrollEventHandler(object sender);
	}
}
