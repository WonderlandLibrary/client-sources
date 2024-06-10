using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace FlatUI
{
	// Token: 0x02000007 RID: 7
	public class FlatNumeric : Control
	{
		// Token: 0x17000005 RID: 5
		// (get) Token: 0x06000023 RID: 35 RVA: 0x00002866 File Offset: 0x00000A66
		// (set) Token: 0x06000024 RID: 36 RVA: 0x0000286E File Offset: 0x00000A6E
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

		// Token: 0x17000006 RID: 6
		// (get) Token: 0x06000025 RID: 37 RVA: 0x00002898 File Offset: 0x00000A98
		// (set) Token: 0x06000026 RID: 38 RVA: 0x000028A0 File Offset: 0x00000AA0
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

		// Token: 0x17000007 RID: 7
		// (get) Token: 0x06000027 RID: 39 RVA: 0x000028D2 File Offset: 0x00000AD2
		// (set) Token: 0x06000028 RID: 40 RVA: 0x000028DA File Offset: 0x00000ADA
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

		// Token: 0x06000029 RID: 41 RVA: 0x0000290C File Offset: 0x00000B0C
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

		// Token: 0x0600002A RID: 42 RVA: 0x00002978 File Offset: 0x00000B78
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			if (this.x > base.Width - 21 && this.x < base.Width - 3)
			{
				if (this.y < 15)
				{
					if (this.Value + 1L <= this._Max)
					{
						this._Value += 5L;
					}
				}
				else if (this.Value - 1L >= this._Min)
				{
					this._Value -= 5L;
				}
			}
			else
			{
				this.Bool = !this.Bool;
				base.Focus();
			}
			base.Invalidate();
		}

		// Token: 0x0600002B RID: 43 RVA: 0x00002A18 File Offset: 0x00000C18
		protected override void OnKeyPress(KeyPressEventArgs e)
		{
			base.OnKeyPress(e);
			try
			{
				if (this.Bool)
				{
					this._Value = Convert.ToInt64(this._Value.ToString() + e.KeyChar.ToString());
				}
				if (this._Value > this._Max)
				{
					this._Value = this._Max;
				}
				base.Invalidate();
			}
			catch
			{
			}
		}

		// Token: 0x0600002C RID: 44 RVA: 0x00002A94 File Offset: 0x00000C94
		protected override void OnKeyDown(KeyEventArgs e)
		{
			base.OnKeyDown(e);
			if (e.KeyCode == Keys.Back)
			{
				this.Value = 0L;
			}
		}

		// Token: 0x0600002D RID: 45 RVA: 0x00002AAE File Offset: 0x00000CAE
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 30;
		}

		// Token: 0x17000008 RID: 8
		// (get) Token: 0x0600002E RID: 46 RVA: 0x00002ABF File Offset: 0x00000CBF
		// (set) Token: 0x0600002F RID: 47 RVA: 0x00002AC7 File Offset: 0x00000CC7
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

		// Token: 0x17000009 RID: 9
		// (get) Token: 0x06000030 RID: 48 RVA: 0x00002AD0 File Offset: 0x00000CD0
		// (set) Token: 0x06000031 RID: 49 RVA: 0x00002AD8 File Offset: 0x00000CD8
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

		// Token: 0x06000032 RID: 50 RVA: 0x00002AE4 File Offset: 0x00000CE4
		public FlatNumeric()
		{
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.Font = new Font("Segoe UI", 10f);
			this.BackColor = Color.FromArgb(60, 70, 73);
			this.ForeColor = Color.White;
			this._Min = 0L;
			this._Max = 9999999L;
		}

		// Token: 0x06000033 RID: 51 RVA: 0x00002B6C File Offset: 0x00000D6C
		protected override void OnPaint(PaintEventArgs e)
		{
			this.UpdateColors();
			Bitmap bitmap = new Bitmap(base.Width, base.Height);
			Graphics graphics = Graphics.FromImage(bitmap);
			this.W = base.Width;
			this.H = base.Height;
			Rectangle rect = new Rectangle(0, 0, this.W, this.H);
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.Clear(this.BackColor);
			graphics.FillRectangle(new SolidBrush(this._BaseColor), rect);
			graphics.FillRectangle(new SolidBrush(this._ButtonColor), new Rectangle(base.Width - 24, 0, 24, this.H));
			graphics.DrawString("+", new Font("Segoe UI", 12f), Brushes.White, new Point(base.Width - 12, 8), Helpers.CenterSF);
			graphics.DrawString("-", new Font("Segoe UI", 10f, FontStyle.Bold), Brushes.White, new Point(base.Width - 12, 22), Helpers.CenterSF);
			graphics.DrawString(this.Value.ToString(), this.Font, Brushes.White, new Rectangle(5, 1, this.W, this.H), new StringFormat
			{
				LineAlignment = StringAlignment.Center
			});
			base.OnPaint(e);
			graphics.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}

		// Token: 0x06000034 RID: 52 RVA: 0x00002D00 File Offset: 0x00000F00
		private void UpdateColors()
		{
			this._ButtonColor = Color.Firebrick;
		}

		// Token: 0x04000017 RID: 23
		private int W;

		// Token: 0x04000018 RID: 24
		private int H;

		// Token: 0x04000019 RID: 25
		private MouseState State;

		// Token: 0x0400001A RID: 26
		private int x;

		// Token: 0x0400001B RID: 27
		private int y;

		// Token: 0x0400001C RID: 28
		private long _Value;

		// Token: 0x0400001D RID: 29
		private long _Min;

		// Token: 0x0400001E RID: 30
		private long _Max;

		// Token: 0x0400001F RID: 31
		private bool Bool;

		// Token: 0x04000020 RID: 32
		private Color _BaseColor = Color.FromArgb(45, 47, 49);

		// Token: 0x04000021 RID: 33
		private Color _ButtonColor = Helpers.FlatColor;
	}
}
