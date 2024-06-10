using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x0200001F RID: 31
	public class LogInButtonWithProgress : Control
	{
		// Token: 0x06000211 RID: 529 RVA: 0x00002ED7 File Offset: 0x000010D7
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = DrawHelpers.MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x06000212 RID: 530 RVA: 0x00002EF0 File Offset: 0x000010F0
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = DrawHelpers.MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000213 RID: 531 RVA: 0x00002F09 File Offset: 0x00001109
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = DrawHelpers.MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000214 RID: 532 RVA: 0x00002F22 File Offset: 0x00001122
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = DrawHelpers.MouseState.None;
			base.Invalidate();
		}

		// Token: 0x170000B6 RID: 182
		// (get) Token: 0x06000215 RID: 533 RVA: 0x0000D728 File Offset: 0x0000B928
		// (set) Token: 0x06000216 RID: 534 RVA: 0x00002F3B File Offset: 0x0000113B
		[Category("Colours")]
		public Color ProgressColour
		{
			get
			{
				return this._ProgressColour;
			}
			set
			{
				this._ProgressColour = value;
			}
		}

		// Token: 0x170000B7 RID: 183
		// (get) Token: 0x06000217 RID: 535 RVA: 0x0000D740 File Offset: 0x0000B940
		// (set) Token: 0x06000218 RID: 536 RVA: 0x00002F45 File Offset: 0x00001145
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

		// Token: 0x170000B8 RID: 184
		// (get) Token: 0x06000219 RID: 537 RVA: 0x0000D758 File Offset: 0x0000B958
		// (set) Token: 0x0600021A RID: 538 RVA: 0x00002F4F File Offset: 0x0000114F
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

		// Token: 0x170000B9 RID: 185
		// (get) Token: 0x0600021B RID: 539 RVA: 0x0000D770 File Offset: 0x0000B970
		// (set) Token: 0x0600021C RID: 540 RVA: 0x00002F59 File Offset: 0x00001159
		[Category("Colours")]
		public Color BaseColour
		{
			get
			{
				return this._MainColour;
			}
			set
			{
				this._MainColour = value;
			}
		}

		// Token: 0x170000BA RID: 186
		// (get) Token: 0x0600021D RID: 541 RVA: 0x0000D788 File Offset: 0x0000B988
		// (set) Token: 0x0600021E RID: 542 RVA: 0x00002F63 File Offset: 0x00001163
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

		// Token: 0x170000BB RID: 187
		// (get) Token: 0x0600021F RID: 543 RVA: 0x0000D7A0 File Offset: 0x0000B9A0
		// (set) Token: 0x06000220 RID: 544 RVA: 0x00002F6D File Offset: 0x0000116D
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

		// Token: 0x170000BC RID: 188
		// (get) Token: 0x06000221 RID: 545 RVA: 0x0000D7B8 File Offset: 0x0000B9B8
		// (set) Token: 0x06000222 RID: 546 RVA: 0x0000D7D0 File Offset: 0x0000B9D0
		[Category("Control")]
		public int Maximum
		{
			get
			{
				return this._Maximum;
			}
			set
			{
				bool flag = value < this._Value;
				if (flag)
				{
					this._Value = value;
				}
				this._Maximum = value;
				base.Invalidate();
			}
		}

		// Token: 0x170000BD RID: 189
		// (get) Token: 0x06000223 RID: 547 RVA: 0x0000D804 File Offset: 0x0000BA04
		// (set) Token: 0x06000224 RID: 548 RVA: 0x0000D830 File Offset: 0x0000BA30
		[Category("Control")]
		public int Value
		{
			get
			{
				int value = this._Value;
				int Value;
				if (value != 0)
				{
					Value = this._Value;
				}
				else
				{
					Value = 0;
				}
				return Value;
			}
			set
			{
				int num = value;
				bool flag = num > this._Maximum;
				if (flag)
				{
					value = this._Maximum;
					base.Invalidate();
				}
				this._Value = value;
				base.Invalidate();
			}
		}

		// Token: 0x06000225 RID: 549 RVA: 0x00002F77 File Offset: 0x00001177
		public void Increment(int Amount)
		{
			checked
			{
				this.Value += Amount;
			}
		}

		// Token: 0x06000226 RID: 550 RVA: 0x0000D86C File Offset: 0x0000BA6C
		public LogInButtonWithProgress()
		{
			this._Value = 0;
			this._Maximum = 100;
			this._Font = new Font("Segoe UI", 9f);
			this._ProgressColour = Color.FromArgb(0, 191, 255);
			this._BorderColour = Color.FromArgb(25, 25, 25);
			this._FontColour = Color.FromArgb(255, 255, 255);
			this._MainColour = Color.FromArgb(42, 42, 42);
			this._HoverColour = Color.FromArgb(52, 52, 52);
			this._PressedColour = Color.FromArgb(47, 47, 47);
			this.State = DrawHelpers.MouseState.None;
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Size = new Size(75, 30);
			this.BackColor = Color.Transparent;
		}

		// Token: 0x06000227 RID: 551 RVA: 0x0000D958 File Offset: 0x0000BB58
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics g = e.Graphics;
			Graphics graphics = g;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.Clear(this.BackColor);
			checked
			{
				switch (this.State)
				{
				case DrawHelpers.MouseState.None:
					graphics.FillRectangle(new SolidBrush(this._MainColour), new Rectangle(0, 0, base.Width, base.Height - 4));
					graphics.DrawRectangle(new Pen(this._BorderColour, 2f), new Rectangle(0, 0, base.Width, base.Height - 4));
					graphics.DrawString(this.Text, this._Font, Brushes.White, new Point((int)Math.Round((double)base.Width / 2.0), (int)Math.Round(unchecked((double)base.Height / 2.0 - 2.0))), new StringFormat
					{
						Alignment = StringAlignment.Center,
						LineAlignment = StringAlignment.Center
					});
					break;
				case DrawHelpers.MouseState.Over:
					graphics.FillRectangle(new SolidBrush(this._HoverColour), new Rectangle(0, 0, base.Width, base.Height - 4));
					graphics.DrawRectangle(new Pen(this._BorderColour, 1f), new Rectangle(1, 1, base.Width - 2, base.Height - 5));
					graphics.DrawString(this.Text, this._Font, Brushes.White, new Point((int)Math.Round((double)base.Width / 2.0), (int)Math.Round(unchecked((double)base.Height / 2.0 - 2.0))), new StringFormat
					{
						Alignment = StringAlignment.Center,
						LineAlignment = StringAlignment.Center
					});
					break;
				case DrawHelpers.MouseState.Down:
					graphics.FillRectangle(new SolidBrush(this._PressedColour), new Rectangle(0, 0, base.Width, base.Height - 4));
					graphics.DrawRectangle(new Pen(this._BorderColour, 1f), new Rectangle(1, 1, base.Width - 2, base.Height - 5));
					graphics.DrawString(this.Text, this._Font, Brushes.White, new Point((int)Math.Round((double)base.Width / 2.0), (int)Math.Round(unchecked((double)base.Height / 2.0 - 2.0))), new StringFormat
					{
						Alignment = StringAlignment.Center,
						LineAlignment = StringAlignment.Center
					});
					break;
				}
				int value = this._Value;
				bool flag = value == 0;
				if (!flag)
				{
					flag = (value == this._Maximum);
					if (flag)
					{
						graphics.FillRectangle(new SolidBrush(this._ProgressColour), new Rectangle(0, base.Height - 4, base.Width, base.Height - 4));
						graphics.DrawRectangle(new Pen(this._BorderColour, 2f), new Rectangle(0, 0, base.Width, base.Height));
					}
					else
					{
						graphics.FillRectangle(new SolidBrush(this._ProgressColour), new Rectangle(0, base.Height - 4, (int)Math.Round(unchecked((double)base.Width / (double)this._Maximum * (double)this._Value)), base.Height - 4));
						graphics.DrawRectangle(new Pen(this._BorderColour, 2f), new Rectangle(0, 0, base.Width, base.Height));
					}
				}
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			}
		}

		// Token: 0x040000D2 RID: 210
		private int _Value;

		// Token: 0x040000D3 RID: 211
		private int _Maximum;

		// Token: 0x040000D4 RID: 212
		private Font _Font;

		// Token: 0x040000D5 RID: 213
		private Color _ProgressColour;

		// Token: 0x040000D6 RID: 214
		private Color _BorderColour;

		// Token: 0x040000D7 RID: 215
		private Color _FontColour;

		// Token: 0x040000D8 RID: 216
		private Color _MainColour;

		// Token: 0x040000D9 RID: 217
		private Color _HoverColour;

		// Token: 0x040000DA RID: 218
		private Color _PressedColour;

		// Token: 0x040000DB RID: 219
		private DrawHelpers.MouseState State;
	}
}
