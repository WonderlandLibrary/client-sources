using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace GHOSTBYTES
{
	// Token: 0x02000027 RID: 39
	[DefaultEvent("Scroll")]
	internal class FlatTrackBar : Control
	{
		// Token: 0x060001FE RID: 510 RVA: 0x00009F08 File Offset: 0x00008108
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			if (e.Button == MouseButtons.Left)
			{
				this.Val = checked((int)Math.Round(unchecked(checked((double)(this._Value - this._Minimum) / (double)(this._Maximum - this._Minimum)) * (double)(checked(base.Width - 11)))));
				this.Track = new Rectangle(this.Val, 0, 10, 20);
				this.Bool = this.Track.Contains(e.Location);
			}
		}

		// Token: 0x060001FF RID: 511 RVA: 0x00009F8C File Offset: 0x0000818C
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			checked
			{
				if (this.Bool && e.X > -1 && e.X < base.Width + 1)
				{
					this.Value = this._Minimum + (int)Math.Round(unchecked((double)(checked(this._Maximum - this._Minimum)) * ((double)e.X / (double)base.Width)));
				}
			}
		}

		// Token: 0x06000200 RID: 512 RVA: 0x00003BC2 File Offset: 0x00001DC2
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.Bool = false;
		}

		// Token: 0x170000B1 RID: 177
		// (get) Token: 0x06000201 RID: 513 RVA: 0x00003BD2 File Offset: 0x00001DD2
		// (set) Token: 0x06000202 RID: 514 RVA: 0x00003BDA File Offset: 0x00001DDA
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

		// Token: 0x170000B2 RID: 178
		// (get) Token: 0x06000203 RID: 515 RVA: 0x00003BE3 File Offset: 0x00001DE3
		// (set) Token: 0x06000204 RID: 516 RVA: 0x00003BEB File Offset: 0x00001DEB
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

		// Token: 0x170000B3 RID: 179
		// (get) Token: 0x06000205 RID: 517 RVA: 0x00003BF4 File Offset: 0x00001DF4
		// (set) Token: 0x06000206 RID: 518 RVA: 0x00003BFC File Offset: 0x00001DFC
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

		// Token: 0x14000004 RID: 4
		// (add) Token: 0x06000207 RID: 519 RVA: 0x00009FF4 File Offset: 0x000081F4
		// (remove) Token: 0x06000208 RID: 520 RVA: 0x0000A02C File Offset: 0x0000822C
		public event FlatTrackBar.ScrollEventHandler Scroll;

		// Token: 0x170000B4 RID: 180
		// (get) Token: 0x06000209 RID: 521 RVA: 0x0000A064 File Offset: 0x00008264
		// (set) Token: 0x0600020A RID: 522 RVA: 0x00003C05 File Offset: 0x00001E05
		public int Minimum
		{
			get
			{
				int num = 0;
				return num;
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

		// Token: 0x170000B5 RID: 181
		// (get) Token: 0x0600020B RID: 523 RVA: 0x00003C38 File Offset: 0x00001E38
		// (set) Token: 0x0600020C RID: 524 RVA: 0x00003C40 File Offset: 0x00001E40
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

		// Token: 0x170000B6 RID: 182
		// (get) Token: 0x0600020D RID: 525 RVA: 0x00003C73 File Offset: 0x00001E73
		// (set) Token: 0x0600020E RID: 526 RVA: 0x0000A074 File Offset: 0x00008274
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

        private void ScrollEvent(object sender)
        {
            throw new NotImplementedException();
        }

        // Token: 0x170000B7 RID: 183
        // (get) Token: 0x0600020F RID: 527 RVA: 0x00003C7B File Offset: 0x00001E7B
        // (set) Token: 0x06000210 RID: 528 RVA: 0x00003C83 File Offset: 0x00001E83
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

		// Token: 0x06000211 RID: 529 RVA: 0x0000A0BC File Offset: 0x000082BC
		protected override void OnKeyDown(KeyEventArgs e)
		{
			base.OnKeyDown(e);
			checked
			{
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
		}

		// Token: 0x06000212 RID: 530 RVA: 0x00003271 File Offset: 0x00001471
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x06000213 RID: 531 RVA: 0x00003C8C File Offset: 0x00001E8C
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 23;
		}

		// Token: 0x06000214 RID: 532 RVA: 0x0000A118 File Offset: 0x00008318
		public FlatTrackBar()
		{
			this._Maximum = 10;
			this._ShowValue = false;
			this.BaseColor = Color.FromArgb(60, 60, 60);
			this._TrackColor = Color.FromArgb(0, 170, 220);
			this.SliderColor = Color.FromArgb(75, 75, 75);
			this._HatchColor = Color.FromArgb(0, 170, 220);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Height = 18;
			this.BackColor = Color.FromArgb(50, 50, 50);
		}

		// Token: 0x06000215 RID: 533 RVA: 0x0000A1B4 File Offset: 0x000083B4
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			checked
			{
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
				this.Val = (int)Math.Round(unchecked(checked((double)(this._Value - this._Minimum) / (double)(this._Maximum - this._Minimum)) * (double)(checked(this.W - 10))));
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
		}

		// Token: 0x040000B3 RID: 179
		private int W;

		// Token: 0x040000B4 RID: 180
		private int H;

		// Token: 0x040000B5 RID: 181
		private int Val;

		// Token: 0x040000B6 RID: 182
		private bool Bool;

		// Token: 0x040000B7 RID: 183
		private Rectangle Track;

		// Token: 0x040000B8 RID: 184
		private Rectangle Knob;

		// Token: 0x040000B9 RID: 185
		private FlatTrackBar._Style Style_;

		// Token: 0x040000BB RID: 187
		private int _Minimum;

		// Token: 0x040000BC RID: 188
		private int _Maximum;

		// Token: 0x040000BD RID: 189
		private int _Value;

		// Token: 0x040000BE RID: 190
		private bool _ShowValue;

		// Token: 0x040000BF RID: 191
		private Color BaseColor;

		// Token: 0x040000C0 RID: 192
		private Color _TrackColor;

		// Token: 0x040000C1 RID: 193
		private Color SliderColor;

		// Token: 0x040000C2 RID: 194
		private Color _HatchColor;

		// Token: 0x02000028 RID: 40
		[Flags]
		public enum _Style
		{
			// Token: 0x040000C4 RID: 196
			Slider = 0,
			// Token: 0x040000C5 RID: 197
			Knob = 1
		}

		// Token: 0x02000029 RID: 41
		// (Invoke) Token: 0x06000219 RID: 537
		public delegate void ScrollEventHandler(object sender);
	}
}
