using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace FlatUI
{
	// Token: 0x02000008 RID: 8
	[DefaultEvent("Scroll")]
	public class FlatTrackBar : Control
	{
		// Token: 0x06000035 RID: 53 RVA: 0x00002D10 File Offset: 0x00000F10
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			if (e.Button == MouseButtons.Left)
			{
				this.Val = Convert.ToInt32((float)(this._Value - this._Minimum) / (float)(this._Maximum - this._Minimum) * (float)(base.Width - 11));
				this.Track = new Rectangle(this.Val, 0, 10, 20);
				this.Bool = this.Track.Contains(e.Location);
			}
		}

		// Token: 0x06000036 RID: 54 RVA: 0x00002D94 File Offset: 0x00000F94
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			if (this.Bool && e.X > -1 && e.X < base.Width + 1)
			{
				this.Value = this._Minimum + Convert.ToInt32((float)(this._Maximum - this._Minimum) * ((float)e.X / (float)base.Width));
			}
		}

		// Token: 0x06000037 RID: 55 RVA: 0x00002DF9 File Offset: 0x00000FF9
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.Bool = false;
		}

		// Token: 0x1700000A RID: 10
		// (get) Token: 0x06000038 RID: 56 RVA: 0x00002E09 File Offset: 0x00001009
		// (set) Token: 0x06000039 RID: 57 RVA: 0x00002E11 File Offset: 0x00001011
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

		// Token: 0x1700000B RID: 11
		// (get) Token: 0x0600003A RID: 58 RVA: 0x00002E1A File Offset: 0x0000101A
		// (set) Token: 0x0600003B RID: 59 RVA: 0x00002E22 File Offset: 0x00001022
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

		// Token: 0x1700000C RID: 12
		// (get) Token: 0x0600003C RID: 60 RVA: 0x00002E2B File Offset: 0x0000102B
		// (set) Token: 0x0600003D RID: 61 RVA: 0x00002E33 File Offset: 0x00001033
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

		// Token: 0x14000002 RID: 2
		// (add) Token: 0x0600003E RID: 62 RVA: 0x00002E3C File Offset: 0x0000103C
		// (remove) Token: 0x0600003F RID: 63 RVA: 0x00002E74 File Offset: 0x00001074
		public event FlatTrackBar.ScrollEventHandler Scroll;

		// Token: 0x1700000D RID: 13
		// (get) Token: 0x06000040 RID: 64 RVA: 0x00002EAC File Offset: 0x000010AC
		// (set) Token: 0x06000041 RID: 65 RVA: 0x00002EBC File Offset: 0x000010BC
		public int Minimum
		{
			get
			{
				return 0;
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

		// Token: 0x1700000E RID: 14
		// (get) Token: 0x06000042 RID: 66 RVA: 0x00002EEF File Offset: 0x000010EF
		// (set) Token: 0x06000043 RID: 67 RVA: 0x00002EF7 File Offset: 0x000010F7
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

		// Token: 0x1700000F RID: 15
		// (get) Token: 0x06000044 RID: 68 RVA: 0x00002F2A File Offset: 0x0000112A
		// (set) Token: 0x06000045 RID: 69 RVA: 0x00002F32 File Offset: 0x00001132
		public int Value
		{
			get
			{
				return this._Value;
			}
			set
			{
				if (value == this._Value)
				{
					return;
				}
				if (value <= this._Maximum)
				{
					int minimum = this._Minimum;
				}
				this._Value = value;
				base.Invalidate();
				if (this.Scroll != null)
				{
					this.Scroll(this);
				}
			}
		}

		// Token: 0x17000010 RID: 16
		// (get) Token: 0x06000046 RID: 70 RVA: 0x00002F71 File Offset: 0x00001171
		// (set) Token: 0x06000047 RID: 71 RVA: 0x00002F79 File Offset: 0x00001179
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

		// Token: 0x06000048 RID: 72 RVA: 0x00002F84 File Offset: 0x00001184
		protected override void OnKeyDown(KeyEventArgs e)
		{
			base.OnKeyDown(e);
			if (e.KeyCode != Keys.Subtract)
			{
				if (e.KeyCode == Keys.Add)
				{
					if (this.Value == this._Maximum)
					{
						return;
					}
					this.Value++;
				}
				return;
			}
			if (this.Value == 0)
			{
				return;
			}
			this.Value--;
		}

		// Token: 0x06000049 RID: 73 RVA: 0x00002FE1 File Offset: 0x000011E1
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x0600004A RID: 74 RVA: 0x00002FF0 File Offset: 0x000011F0
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 24;
		}

		// Token: 0x0600004B RID: 75 RVA: 0x00003004 File Offset: 0x00001204
		public FlatTrackBar()
		{
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Height = 18;
			this.BackColor = Color.FromArgb(45, 49, 57);
		}

		// Token: 0x0600004C RID: 76 RVA: 0x00003078 File Offset: 0x00001278
		protected override void OnPaint(PaintEventArgs e)
		{
			this.UpdateColors();
			Bitmap bitmap = new Bitmap(base.Width, base.Height);
			Graphics graphics = Graphics.FromImage(bitmap);
			this.W = base.Width - 1;
			this.H = base.Height - 1;
			Rectangle rect = new Rectangle(1, 6, this.W - 2, 8);
			GraphicsPath graphicsPath = new GraphicsPath();
			GraphicsPath graphicsPath2 = new GraphicsPath();
			Graphics graphics2 = graphics;
			graphics2.SmoothingMode = SmoothingMode.HighQuality;
			graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics2.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics2.Clear(this.BackColor);
			this.Val = Convert.ToInt32((float)(this._Value - this._Minimum) / (float)(this._Maximum - this._Minimum) * (float)(this.W - 10));
			this.Track = new Rectangle(this.Val + 2, 0, 17, 20);
			this.Knob = new Rectangle(this.Val, 4, 14, 14);
			graphicsPath.AddRectangle(rect);
			graphics2.SetClip(graphicsPath);
			graphics2.FillRectangle(new SolidBrush(this.BaseColor), new Rectangle(0, 7, this.W, 8));
			graphics2.FillRectangle(new SolidBrush(this.BaseColor), new Rectangle(0, 7, this.Track.X + this.Track.Width, 8));
			graphics2.ResetClip();
			HatchBrush brush = new HatchBrush(HatchStyle.Plaid, this.BaseColor, this.BaseColor);
			graphics2.FillRectangle(brush, new Rectangle(-10, 7, this.Track.X + this.Track.Width, 8));
			FlatTrackBar._Style style = this.Style;
			if (style != FlatTrackBar._Style.Slider)
			{
				if (style == FlatTrackBar._Style.Knob)
				{
					graphicsPath2.AddEllipse(this.Knob);
					graphics2.FillPath(new SolidBrush(this.SliderColor), graphicsPath2);
				}
			}
			else
			{
				graphicsPath2.AddRectangle(this.Track);
				graphics2.FillPath(new SolidBrush(this.SliderColor), graphicsPath2);
			}
			if (this.ShowValue)
			{
				graphics2.DrawString(this.Value.ToString(), new Font("Segoe UI", 8f), Brushes.White, new Rectangle(1, 6, this.W, this.H), new StringFormat
				{
					Alignment = StringAlignment.Far,
					LineAlignment = StringAlignment.Far
				});
			}
			base.OnPaint(e);
			graphics.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}

		// Token: 0x0600004D RID: 77 RVA: 0x000032EE File Offset: 0x000014EE
		private void UpdateColors()
		{
			this._TrackColor = Color.Firebrick;
		}

		// Token: 0x04000022 RID: 34
		private int W;

		// Token: 0x04000023 RID: 35
		private int H;

		// Token: 0x04000024 RID: 36
		private int Val;

		// Token: 0x04000025 RID: 37
		private bool Bool;

		// Token: 0x04000026 RID: 38
		private Rectangle Track;

		// Token: 0x04000027 RID: 39
		private Rectangle Knob;

		// Token: 0x04000028 RID: 40
		private FlatTrackBar._Style Style_;

		// Token: 0x0400002A RID: 42
		private int _Minimum;

		// Token: 0x0400002B RID: 43
		private int _Maximum = 10;

		// Token: 0x0400002C RID: 44
		private int _Value;

		// Token: 0x0400002D RID: 45
		private bool _ShowValue;

		// Token: 0x0400002E RID: 46
		private Color BaseColor = Color.DarkGray;

		// Token: 0x0400002F RID: 47
		private Color _TrackColor = Color.DarkGray;

		// Token: 0x04000030 RID: 48
		private Color SliderColor = Helpers.FlatColor;

		// Token: 0x04000031 RID: 49
		private Color _HatchColor = Color.DarkGray;

		// Token: 0x02000014 RID: 20
		[Flags]
		public enum _Style
		{
			// Token: 0x040000D7 RID: 215
			Slider = 0,
			// Token: 0x040000D8 RID: 216
			Knob = 1
		}

		// Token: 0x02000015 RID: 21
		// (Invoke) Token: 0x060000AD RID: 173
		public delegate void ScrollEventHandler(object sender);
	}
}
