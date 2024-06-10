using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x02000028 RID: 40
	public class LogInProgressBar : Control
	{
		// Token: 0x170000F0 RID: 240
		// (get) Token: 0x0600029A RID: 666 RVA: 0x0000F570 File Offset: 0x0000D770
		// (set) Token: 0x0600029B RID: 667 RVA: 0x00003259 File Offset: 0x00001459
		public Color SecondColour
		{
			get
			{
				return this._SecondColour;
			}
			set
			{
				this._SecondColour = value;
			}
		}

		// Token: 0x170000F1 RID: 241
		// (get) Token: 0x0600029C RID: 668 RVA: 0x0000F588 File Offset: 0x0000D788
		// (set) Token: 0x0600029D RID: 669 RVA: 0x00003263 File Offset: 0x00001463
		[Category("Control")]
		public bool TwoColour
		{
			get
			{
				return this._TwoColour;
			}
			set
			{
				this._TwoColour = value;
			}
		}

		// Token: 0x170000F2 RID: 242
		// (get) Token: 0x0600029E RID: 670 RVA: 0x0000F5A0 File Offset: 0x0000D7A0
		// (set) Token: 0x0600029F RID: 671 RVA: 0x0000F5B8 File Offset: 0x0000D7B8
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

		// Token: 0x170000F3 RID: 243
		// (get) Token: 0x060002A0 RID: 672 RVA: 0x0000F5EC File Offset: 0x0000D7EC
		// (set) Token: 0x060002A1 RID: 673 RVA: 0x0000F618 File Offset: 0x0000D818
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

		// Token: 0x170000F4 RID: 244
		// (get) Token: 0x060002A2 RID: 674 RVA: 0x0000F654 File Offset: 0x0000D854
		// (set) Token: 0x060002A3 RID: 675 RVA: 0x0000326D File Offset: 0x0000146D
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

		// Token: 0x170000F5 RID: 245
		// (get) Token: 0x060002A4 RID: 676 RVA: 0x0000F66C File Offset: 0x0000D86C
		// (set) Token: 0x060002A5 RID: 677 RVA: 0x00003277 File Offset: 0x00001477
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

		// Token: 0x170000F6 RID: 246
		// (get) Token: 0x060002A6 RID: 678 RVA: 0x0000F684 File Offset: 0x0000D884
		// (set) Token: 0x060002A7 RID: 679 RVA: 0x00003281 File Offset: 0x00001481
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

		// Token: 0x170000F7 RID: 247
		// (get) Token: 0x060002A8 RID: 680 RVA: 0x0000F69C File Offset: 0x0000D89C
		// (set) Token: 0x060002A9 RID: 681 RVA: 0x0000328B File Offset: 0x0000148B
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

		// Token: 0x060002AA RID: 682 RVA: 0x00003295 File Offset: 0x00001495
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 25;
		}

		// Token: 0x060002AB RID: 683 RVA: 0x000032A9 File Offset: 0x000014A9
		protected override void CreateHandle()
		{
			base.CreateHandle();
			base.Height = 25;
		}

		// Token: 0x060002AC RID: 684 RVA: 0x000032BC File Offset: 0x000014BC
		public void Increment(int Amount)
		{
			checked
			{
				this.Value += Amount;
			}
		}

		// Token: 0x060002AD RID: 685 RVA: 0x0000F6B4 File Offset: 0x0000D8B4
		public LogInProgressBar()
		{
			this._ProgressColour = Color.FromArgb(0, 160, 199);
			this._BorderColour = Color.FromArgb(35, 35, 35);
			this._BaseColour = Color.FromArgb(42, 42, 42);
			this._FontColour = Color.FromArgb(50, 50, 50);
			this._SecondColour = Color.FromArgb(0, 145, 184);
			this._Value = 0;
			this._Maximum = 100;
			this._TwoColour = true;
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
		}

		// Token: 0x060002AE RID: 686 RVA: 0x0000F754 File Offset: 0x0000D954
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics G = e.Graphics;
			Rectangle Base = new Rectangle(0, 0, base.Width, base.Height);
			Graphics graphics = G;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.Clear(this.BackColor);
			checked
			{
				int ProgVal = (int)Math.Round(unchecked((double)this._Value / (double)this._Maximum * (double)base.Width));
				int value = this.Value;
				bool flag = value == 0;
				if (flag)
				{
					graphics.FillRectangle(new SolidBrush(this._BaseColour), Base);
					graphics.FillRectangle(new SolidBrush(this._ProgressColour), new Rectangle(0, 0, ProgVal - 1, base.Height));
					graphics.DrawRectangle(new Pen(this._BorderColour, 3f), Base);
				}
				else
				{
					flag = (value == this._Maximum);
					if (flag)
					{
						graphics.FillRectangle(new SolidBrush(this._BaseColour), Base);
						graphics.FillRectangle(new SolidBrush(this._ProgressColour), new Rectangle(0, 0, ProgVal - 1, base.Height));
						bool twoColour = this._TwoColour;
						if (twoColour)
						{
							G.SetClip(new Rectangle(0, -10, (int)Math.Round(unchecked((double)(checked(base.Width * this._Value)) / (double)this._Maximum - 1.0)), base.Height - 5));
							double num = (double)((base.Width - 1) * this._Maximum) / (double)this._Value;
							for (double i = 0.0; i <= num; i = unchecked(i + 25.0))
							{
								G.DrawLine(new Pen(new SolidBrush(this._SecondColour), 7f), new Point((int)Math.Round(i), 0), new Point((int)Math.Round(unchecked(i - 15.0)), base.Height));
							}
							G.ResetClip();
						}
						graphics.DrawRectangle(new Pen(this._BorderColour, 3f), Base);
					}
					else
					{
						graphics.FillRectangle(new SolidBrush(this._BaseColour), Base);
						graphics.FillRectangle(new SolidBrush(this._ProgressColour), new Rectangle(0, 0, ProgVal - 1, base.Height));
						bool twoColour2 = this._TwoColour;
						if (twoColour2)
						{
							graphics.SetClip(new Rectangle(0, 0, (int)Math.Round(unchecked((double)(checked(base.Width * this._Value)) / (double)this._Maximum - 1.0)), base.Height - 1));
							double num2 = (double)((base.Width - 1) * this._Maximum) / (double)this._Value;
							for (double j = 0.0; j <= num2; j = unchecked(j + 25.0))
							{
								graphics.DrawLine(new Pen(new SolidBrush(this._SecondColour), 7f), new Point((int)Math.Round(j), 0), new Point((int)Math.Round(unchecked(j - 10.0)), base.Height));
							}
							graphics.ResetClip();
						}
						graphics.DrawRectangle(new Pen(this._BorderColour, 3f), Base);
					}
				}
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			}
		}

		// Token: 0x04000105 RID: 261
		private Color _ProgressColour;

		// Token: 0x04000106 RID: 262
		private Color _BorderColour;

		// Token: 0x04000107 RID: 263
		private Color _BaseColour;

		// Token: 0x04000108 RID: 264
		private Color _FontColour;

		// Token: 0x04000109 RID: 265
		private Color _SecondColour;

		// Token: 0x0400010A RID: 266
		private int _Value;

		// Token: 0x0400010B RID: 267
		private int _Maximum;

		// Token: 0x0400010C RID: 268
		private bool _TwoColour;
	}
}
