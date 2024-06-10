using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace Meth
{
	// Token: 0x02000027 RID: 39
	internal class FlatNumeric : Control
	{
		// Token: 0x170000CF RID: 207
		// (get) Token: 0x060002CF RID: 719 RVA: 0x00013FB7 File Offset: 0x000121B7
		// (set) Token: 0x060002D0 RID: 720 RVA: 0x00013FBF File Offset: 0x000121BF
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

		// Token: 0x170000D0 RID: 208
		// (get) Token: 0x060002D1 RID: 721 RVA: 0x00013FE9 File Offset: 0x000121E9
		// (set) Token: 0x060002D2 RID: 722 RVA: 0x00013FF1 File Offset: 0x000121F1
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

		// Token: 0x170000D1 RID: 209
		// (get) Token: 0x060002D3 RID: 723 RVA: 0x00014023 File Offset: 0x00012223
		// (set) Token: 0x060002D4 RID: 724 RVA: 0x0001402B File Offset: 0x0001222B
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

		// Token: 0x060002D5 RID: 725 RVA: 0x00014060 File Offset: 0x00012260
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			this.x = e.Location.X;
			this.y = e.Location.Y;
			base.Invalidate();
			if (e.X < base.Width - 23)
			{
				this.Cursor = Cursors.IBeam;
				return;
			}
			this.Cursor = Cursors.Hand;
		}

		// Token: 0x060002D6 RID: 726 RVA: 0x000140CC File Offset: 0x000122CC
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
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

		// Token: 0x060002D7 RID: 727 RVA: 0x0001416C File Offset: 0x0001236C
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

		// Token: 0x060002D8 RID: 728 RVA: 0x000141F0 File Offset: 0x000123F0
		protected override void OnKeyDown(KeyEventArgs e)
		{
			base.OnKeyDown(e);
			if (e.KeyCode == Keys.Back)
			{
				this.Value = 0L;
			}
		}

		// Token: 0x060002D9 RID: 729 RVA: 0x0001420A File Offset: 0x0001240A
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 30;
		}

		// Token: 0x170000D2 RID: 210
		// (get) Token: 0x060002DA RID: 730 RVA: 0x0001421B File Offset: 0x0001241B
		// (set) Token: 0x060002DB RID: 731 RVA: 0x00014223 File Offset: 0x00012423
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

		// Token: 0x170000D3 RID: 211
		// (get) Token: 0x060002DC RID: 732 RVA: 0x0001422C File Offset: 0x0001242C
		// (set) Token: 0x060002DD RID: 733 RVA: 0x00014234 File Offset: 0x00012434
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

		// Token: 0x060002DE RID: 734 RVA: 0x00014240 File Offset: 0x00012440
		public FlatNumeric()
		{
			this.State = MouseState.None;
			this._BaseColor = Color.FromArgb(45, 47, 49);
			this._ButtonColor = Helpers._FlatColor;
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.Font = new Font("Segoe UI", 10f);
			this.BackColor = Color.FromArgb(60, 70, 73);
			this.ForeColor = Color.White;
			this._Min = 0L;
			this._Max = 9999999L;
		}

		// Token: 0x060002DF RID: 735 RVA: 0x000142D0 File Offset: 0x000124D0
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

		// Token: 0x04000173 RID: 371
		private int W;

		// Token: 0x04000174 RID: 372
		private int H;

		// Token: 0x04000175 RID: 373
		private MouseState State;

		// Token: 0x04000176 RID: 374
		private int x;

		// Token: 0x04000177 RID: 375
		private int y;

		// Token: 0x04000178 RID: 376
		private long _Value;

		// Token: 0x04000179 RID: 377
		private long _Min;

		// Token: 0x0400017A RID: 378
		private long _Max;

		// Token: 0x0400017B RID: 379
		private bool Bool;

		// Token: 0x0400017C RID: 380
		private Color _BaseColor;

		// Token: 0x0400017D RID: 381
		private Color _ButtonColor;
	}
}
