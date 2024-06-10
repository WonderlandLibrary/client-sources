using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace GHOSTBYTES
{
	// Token: 0x02000023 RID: 35
	internal class FlatNumeric : Control
	{
		// Token: 0x17000098 RID: 152
		// (get) Token: 0x060001C9 RID: 457 RVA: 0x00003956 File Offset: 0x00001B56
		// (set) Token: 0x060001CA RID: 458 RVA: 0x0000395E File Offset: 0x00001B5E
		public long Value
		{
			get
			{
				return this._Value;
			}
			set
			{
				if (value <= this._Max & value >= this._Min)
				{
					this._Value = value;
				}
				base.Invalidate();
			}
		}

		// Token: 0x17000099 RID: 153
		// (get) Token: 0x060001CB RID: 459 RVA: 0x00003988 File Offset: 0x00001B88
		// (set) Token: 0x060001CC RID: 460 RVA: 0x00003990 File Offset: 0x00001B90
		public long Maximum
		{
			get
			{
				return this._Max;
			}
			set
			{
				if (value > this._Min)
				{
					this._Max = value;
				}
				if (this._Value > this._Max)
				{
					this._Value = this._Max;
				}
				base.Invalidate();
			}
		}

		// Token: 0x1700009A RID: 154
		// (get) Token: 0x060001CD RID: 461 RVA: 0x000039C2 File Offset: 0x00001BC2
		// (set) Token: 0x060001CE RID: 462 RVA: 0x000039CA File Offset: 0x00001BCA
		public long Minimum
		{
			get
			{
				return this._Min;
			}
			set
			{
				if (value < this._Max)
				{
					this._Min = value;
				}
				if (this._Value < this._Min)
				{
					this._Value = this.Minimum;
				}
				base.Invalidate();
			}
		}

		// Token: 0x060001CF RID: 463 RVA: 0x00009690 File Offset: 0x00007890
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			this.x = e.Location.X;
			this.y = e.Location.Y;
			base.Invalidate();
			if (e.X < checked(base.Width - 23))
			{
				this.Cursor = Cursors.IBeam;
				return;
			}
			this.Cursor = Cursors.Hand;
		}

		// Token: 0x060001D0 RID: 464 RVA: 0x000096FC File Offset: 0x000078FC
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			checked
			{
				if (this.x > base.Width - 21 && this.x < base.Width - 3)
				{
					if (this.y < 15)
					{
						if (this.Value + 1L <= this._Max)
						{
							ref long ptr = ref this._Value;
							this._Value = ptr + 1L;
						}
					}
					else if (this.Value - 1L >= this._Min)
					{
						ref long ptr = ref this._Value;
						this._Value = ptr - 1L;
					}
				}
				else
				{
					this.Bool = !this.Bool;
					base.Focus();
				}
				base.Invalidate();
			}
		}

		// Token: 0x060001D1 RID: 465 RVA: 0x0000979C File Offset: 0x0000799C
		protected override void OnKeyPress(KeyPressEventArgs e)
		{
			base.OnKeyPress(e);
			try
			{
				if (this.Bool)
				{
					this._Value = Conversions.ToLong(Conversions.ToString(this._Value) + e.KeyChar.ToString());
				}
				if (this._Value > this._Max)
				{
					this._Value = this._Max;
				}
				base.Invalidate();
			}
			catch (Exception ex)
			{
			}
		}

		// Token: 0x060001D2 RID: 466 RVA: 0x000039FC File Offset: 0x00001BFC
		protected override void OnKeyDown(KeyEventArgs e)
		{
			base.OnKeyDown(e);
			if (e.KeyCode == Keys.Back)
			{
				this.Value = 0L;
			}
		}

		// Token: 0x060001D3 RID: 467 RVA: 0x00003A16 File Offset: 0x00001C16
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 30;
		}

		// Token: 0x1700009B RID: 155
		// (get) Token: 0x060001D4 RID: 468 RVA: 0x00003A27 File Offset: 0x00001C27
		// (set) Token: 0x060001D5 RID: 469 RVA: 0x00003A2F File Offset: 0x00001C2F
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

		// Token: 0x1700009C RID: 156
		// (get) Token: 0x060001D6 RID: 470 RVA: 0x00003A38 File Offset: 0x00001C38
		// (set) Token: 0x060001D7 RID: 471 RVA: 0x00003A40 File Offset: 0x00001C40
		[Category("Colors")]
		public Color ButtonColor
		{
			get
			{
				return this._ButtonColor;
			}
			set
			{
				this._ButtonColor = value;
			}
		}

		// Token: 0x060001D8 RID: 472 RVA: 0x00009820 File Offset: 0x00007A20
		public FlatNumeric()
		{
			this.State = MouseState.None;
			this._BaseColor = Color.FromArgb(60, 60, 60);
			this._ButtonColor = Color.FromArgb(0, 170, 220);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.Font = new Font("Segoe UI", 10f);
			this.BackColor = Color.FromArgb(60, 70, 73);
			this.ForeColor = Color.White;
			this._Min = 0L;
			this._Max = 9999999L;
		}

		// Token: 0x060001D9 RID: 473 RVA: 0x000098BC File Offset: 0x00007ABC
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
			g.Clear(this.BackColor);
			g.FillRectangle(new SolidBrush(this._BaseColor), rect);
			checked
			{
				g.FillRectangle(new SolidBrush(this._ButtonColor), new Rectangle(base.Width - 24, 0, 24, this.H));
				g.DrawString("+", new Font("Segoe UI", 12f), Brushes.White, new Point(base.Width - 12, 8), Helpers.CenterSF);
				g.DrawString("-", new Font("Segoe UI", 10f, FontStyle.Bold), Brushes.White, new Point(base.Width - 12, 22), Helpers.CenterSF);
				g.DrawString(Conversions.ToString(this.Value), this.Font, Brushes.White, new Rectangle(5, 1, this.W, this.H), new StringFormat
				{
					LineAlignment = StringAlignment.Center
				});
				base.OnPaint(e);
				Helpers.G.Dispose();
				e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
				e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
				Helpers.B.Dispose();
			}
		}

		// Token: 0x040000A1 RID: 161
		private int W;

		// Token: 0x040000A2 RID: 162
		private int H;

		// Token: 0x040000A3 RID: 163
		private MouseState State;

		// Token: 0x040000A4 RID: 164
		private int x;

		// Token: 0x040000A5 RID: 165
		private int y;

		// Token: 0x040000A6 RID: 166
		private long _Value;

		// Token: 0x040000A7 RID: 167
		private long _Min;

		// Token: 0x040000A8 RID: 168
		private long _Max;

		// Token: 0x040000A9 RID: 169
		private bool Bool;

		// Token: 0x040000AA RID: 170
		private Color _BaseColor;

		// Token: 0x040000AB RID: 171
		private Color _ButtonColor;
	}
}
