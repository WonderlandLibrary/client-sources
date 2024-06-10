using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace svchost
{
	// Token: 0x02000023 RID: 35
	public class LogInNumeric : Control
	{
		// Token: 0x170000C5 RID: 197
		// (get) Token: 0x0600023A RID: 570 RVA: 0x0000E1F0 File Offset: 0x0000C3F0
		// (set) Token: 0x0600023B RID: 571 RVA: 0x0000E208 File Offset: 0x0000C408
		public long Value
		{
			get
			{
				return this._Value;
			}
			set
			{
				bool flag = value <= this._Maximum & value >= this._Minimum;
				if (flag)
				{
					this._Value = value;
				}
				base.Invalidate();
			}
		}

		// Token: 0x170000C6 RID: 198
		// (get) Token: 0x0600023C RID: 572 RVA: 0x0000E244 File Offset: 0x0000C444
		// (set) Token: 0x0600023D RID: 573 RVA: 0x0000E25C File Offset: 0x0000C45C
		public long Maximum
		{
			get
			{
				return this._Maximum;
			}
			set
			{
				bool flag = value > this._Minimum;
				if (flag)
				{
					this._Maximum = value;
				}
				bool flag2 = this._Value > this._Maximum;
				if (flag2)
				{
					this._Value = this._Maximum;
				}
				base.Invalidate();
			}
		}

		// Token: 0x170000C7 RID: 199
		// (get) Token: 0x0600023E RID: 574 RVA: 0x0000E2A4 File Offset: 0x0000C4A4
		// (set) Token: 0x0600023F RID: 575 RVA: 0x0000E2BC File Offset: 0x0000C4BC
		public long Minimum
		{
			get
			{
				return this._Minimum;
			}
			set
			{
				bool flag = value < this._Maximum;
				if (flag)
				{
					this._Minimum = value;
				}
				bool flag2 = this._Value < this._Minimum;
				if (flag2)
				{
					this._Value = this.Minimum;
				}
				base.Invalidate();
			}
		}

		// Token: 0x06000240 RID: 576 RVA: 0x0000E304 File Offset: 0x0000C504
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			this.MouseXLoc = e.Location.X;
			this.MouseYLoc = e.Location.Y;
			base.Invalidate();
			bool flag = e.X < checked(base.Width - 47);
			if (flag)
			{
				this.Cursor = Cursors.IBeam;
			}
			else
			{
				this.Cursor = Cursors.Hand;
			}
		}

		// Token: 0x06000241 RID: 577 RVA: 0x0000E37C File Offset: 0x0000C57C
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			checked
			{
				bool flag = this.MouseXLoc > base.Width - 47 && this.MouseXLoc < base.Width - 3;
				if (flag)
				{
					bool flag2 = this.MouseXLoc < base.Width - 23;
					if (flag2)
					{
						bool flag3 = this.Value + 1L <= this._Maximum;
						if (flag3)
						{
							ref long ptr = ref this._Value;
							this._Value = ptr + 1L;
						}
					}
					else
					{
						bool flag4 = this.Value - 1L >= this._Minimum;
						if (flag4)
						{
							ref long ptr = ref this._Value;
							this._Value = ptr - 1L;
						}
					}
				}
				else
				{
					this.BoolValue = !this.BoolValue;
					base.Focus();
				}
				base.Invalidate();
			}
		}

		// Token: 0x06000242 RID: 578 RVA: 0x0000E444 File Offset: 0x0000C644
		protected override void OnKeyPress(KeyPressEventArgs e)
		{
			base.OnKeyPress(e);
			try
			{
				bool boolValue = this.BoolValue;
				if (boolValue)
				{
					this._Value = Conversions.ToLong(Conversions.ToString(this._Value) + e.KeyChar.ToString());
				}
				bool flag = this._Value > this._Maximum;
				if (flag)
				{
					this._Value = this._Maximum;
				}
				base.Invalidate();
			}
			catch (Exception ex)
			{
			}
		}

		// Token: 0x06000243 RID: 579 RVA: 0x0000E4D4 File Offset: 0x0000C6D4
		protected override void OnKeyDown(KeyEventArgs e)
		{
			base.OnKeyDown(e);
			bool flag = e.KeyCode == Keys.Back;
			if (flag)
			{
				this.Value = 0L;
			}
		}

		// Token: 0x06000244 RID: 580 RVA: 0x00002FCF File Offset: 0x000011CF
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 24;
		}

		// Token: 0x170000C8 RID: 200
		// (get) Token: 0x06000245 RID: 581 RVA: 0x0000E504 File Offset: 0x0000C704
		// (set) Token: 0x06000246 RID: 582 RVA: 0x00002FE3 File Offset: 0x000011E3
		[Category("Colours")]
		public Color BaseColour
		{
			get
			{
				return this._BaseColour;
			}
			set
			{
				this._BaseColour = value;
			}
		}

		// Token: 0x170000C9 RID: 201
		// (get) Token: 0x06000247 RID: 583 RVA: 0x0000E51C File Offset: 0x0000C71C
		// (set) Token: 0x06000248 RID: 584 RVA: 0x00002FED File Offset: 0x000011ED
		[Category("Colours")]
		public Color ButtonColour
		{
			get
			{
				return this._ButtonColour;
			}
			set
			{
				this._ButtonColour = value;
			}
		}

		// Token: 0x170000CA RID: 202
		// (get) Token: 0x06000249 RID: 585 RVA: 0x0000E534 File Offset: 0x0000C734
		// (set) Token: 0x0600024A RID: 586 RVA: 0x00002FF7 File Offset: 0x000011F7
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

		// Token: 0x170000CB RID: 203
		// (get) Token: 0x0600024B RID: 587 RVA: 0x0000E54C File Offset: 0x0000C74C
		// (set) Token: 0x0600024C RID: 588 RVA: 0x00003001 File Offset: 0x00001201
		[Category("Colours")]
		public Color SecondBorderColour
		{
			get
			{
				return this._SecondBorderColour;
			}
			set
			{
				this._SecondBorderColour = value;
			}
		}

		// Token: 0x170000CC RID: 204
		// (get) Token: 0x0600024D RID: 589 RVA: 0x0000E564 File Offset: 0x0000C764
		// (set) Token: 0x0600024E RID: 590 RVA: 0x0000300B File Offset: 0x0000120B
		[Category("Colours")]
		public Color FontColour
		{
			get
			{
				return this._FontColour;
			}
			set
			{
				this._FontColour = value;
			}
		}

		// Token: 0x0600024F RID: 591 RVA: 0x0000E57C File Offset: 0x0000C77C
		public LogInNumeric()
		{
			this.State = DrawHelpers.MouseState.None;
			this._Minimum = 0L;
			this._Maximum = 9999999L;
			this._BaseColour = Color.FromArgb(42, 42, 42);
			this._ButtonColour = Color.FromArgb(47, 47, 47);
			this._BorderColour = Color.FromArgb(35, 35, 35);
			this._SecondBorderColour = Color.FromArgb(0, 191, 255);
			this._FontColour = Color.FromArgb(255, 255, 255);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.Font = new Font("Segoe UI", 10f);
		}

		// Token: 0x06000250 RID: 592 RVA: 0x0000E63C File Offset: 0x0000C83C
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics g = e.Graphics;
			Rectangle Base = new Rectangle(0, 0, base.Width, base.Height);
			StringFormat stringFormat = new StringFormat();
			stringFormat.LineAlignment = StringAlignment.Center;
			stringFormat.Alignment = StringAlignment.Center;
			Graphics graphics = g;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.Clear(this.BackColor);
			graphics.FillRectangle(new SolidBrush(this._BaseColour), Base);
			checked
			{
				graphics.FillRectangle(new SolidBrush(this._ButtonColour), new Rectangle(base.Width - 48, 0, 48, base.Height));
				graphics.DrawRectangle(new Pen(this._BorderColour, 2f), Base);
				graphics.DrawLine(new Pen(this._SecondBorderColour), new Point(base.Width - 48, 1), new Point(base.Width - 48, base.Height - 2));
				graphics.DrawLine(new Pen(this._BorderColour), new Point(base.Width - 24, 1), new Point(base.Width - 24, base.Height - 2));
				graphics.DrawLine(new Pen(this._FontColour), new Point(base.Width - 36, 7), new Point(base.Width - 36, 17));
				graphics.DrawLine(new Pen(this._FontColour), new Point(base.Width - 31, 12), new Point(base.Width - 41, 12));
				graphics.DrawLine(new Pen(this._FontColour), new Point(base.Width - 17, 13), new Point(base.Width - 7, 13));
				graphics.DrawString(Conversions.ToString(this.Value), this.Font, new SolidBrush(this._FontColour), new Rectangle(5, 1, base.Width, base.Height), new StringFormat
				{
					LineAlignment = StringAlignment.Center
				});
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			}
		}

		// Token: 0x040000E6 RID: 230
		private DrawHelpers.MouseState State;

		// Token: 0x040000E7 RID: 231
		private int MouseXLoc;

		// Token: 0x040000E8 RID: 232
		private int MouseYLoc;

		// Token: 0x040000E9 RID: 233
		private long _Value;

		// Token: 0x040000EA RID: 234
		private long _Minimum;

		// Token: 0x040000EB RID: 235
		private long _Maximum;

		// Token: 0x040000EC RID: 236
		private bool BoolValue;

		// Token: 0x040000ED RID: 237
		private Color _BaseColour;

		// Token: 0x040000EE RID: 238
		private Color _ButtonColour;

		// Token: 0x040000EF RID: 239
		private Color _BorderColour;

		// Token: 0x040000F0 RID: 240
		private Color _SecondBorderColour;

		// Token: 0x040000F1 RID: 241
		private Color _FontColour;
	}
}
