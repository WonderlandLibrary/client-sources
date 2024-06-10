using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x02000035 RID: 53
	[DefaultEvent("Scroll")]
	public class LogInVerticalScrollBar : Control
	{
		// Token: 0x17000121 RID: 289
		// (get) Token: 0x06000336 RID: 822 RVA: 0x00011C80 File Offset: 0x0000FE80
		// (set) Token: 0x06000337 RID: 823 RVA: 0x0000360D File Offset: 0x0000180D
		[Category("Colours")]
		public Color ThumbBorder
		{
			get
			{
				return this._ThumbBorder;
			}
			set
			{
				this._ThumbBorder = value;
			}
		}

		// Token: 0x17000122 RID: 290
		// (get) Token: 0x06000338 RID: 824 RVA: 0x00011C98 File Offset: 0x0000FE98
		// (set) Token: 0x06000339 RID: 825 RVA: 0x00003617 File Offset: 0x00001817
		[Category("Colours")]
		public Color LineColour
		{
			get
			{
				return this._LineColour;
			}
			set
			{
				this._LineColour = value;
			}
		}

		// Token: 0x17000123 RID: 291
		// (get) Token: 0x0600033A RID: 826 RVA: 0x00011CB0 File Offset: 0x0000FEB0
		// (set) Token: 0x0600033B RID: 827 RVA: 0x00003621 File Offset: 0x00001821
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

		// Token: 0x17000124 RID: 292
		// (get) Token: 0x0600033C RID: 828 RVA: 0x00011CC8 File Offset: 0x0000FEC8
		// (set) Token: 0x0600033D RID: 829 RVA: 0x0000362B File Offset: 0x0000182B
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

		// Token: 0x17000125 RID: 293
		// (get) Token: 0x0600033E RID: 830 RVA: 0x00011CE0 File Offset: 0x0000FEE0
		// (set) Token: 0x0600033F RID: 831 RVA: 0x00003635 File Offset: 0x00001835
		[Category("Colours")]
		public Color ThumbColour
		{
			get
			{
				return this._ThumbColour;
			}
			set
			{
				this._ThumbColour = value;
			}
		}

		// Token: 0x17000126 RID: 294
		// (get) Token: 0x06000340 RID: 832 RVA: 0x00011CF8 File Offset: 0x0000FEF8
		// (set) Token: 0x06000341 RID: 833 RVA: 0x0000363F File Offset: 0x0000183F
		[Category("Colours")]
		public Color ThumbSecondBorder
		{
			get
			{
				return this._ThumbSecondBorder;
			}
			set
			{
				this._ThumbSecondBorder = value;
			}
		}

		// Token: 0x17000127 RID: 295
		// (get) Token: 0x06000342 RID: 834 RVA: 0x00011D10 File Offset: 0x0000FF10
		// (set) Token: 0x06000343 RID: 835 RVA: 0x00003649 File Offset: 0x00001849
		[Category("Colours")]
		public Color FirstBorder
		{
			get
			{
				return this._FirstBorder;
			}
			set
			{
				this._FirstBorder = value;
			}
		}

		// Token: 0x17000128 RID: 296
		// (get) Token: 0x06000344 RID: 836 RVA: 0x00011D28 File Offset: 0x0000FF28
		// (set) Token: 0x06000345 RID: 837 RVA: 0x00003653 File Offset: 0x00001853
		[Category("Colours")]
		public Color SecondBorder
		{
			get
			{
				return this._SecondBorder;
			}
			set
			{
				this._SecondBorder = value;
			}
		}

		// Token: 0x14000006 RID: 6
		// (add) Token: 0x06000346 RID: 838 RVA: 0x00011D40 File Offset: 0x0000FF40
		// (remove) Token: 0x06000347 RID: 839 RVA: 0x00011D78 File Offset: 0x0000FF78
		public event LogInVerticalScrollBar.ScrollEventHandler Scroll;

		// Token: 0x17000129 RID: 297
		// (get) Token: 0x06000348 RID: 840 RVA: 0x00011DB0 File Offset: 0x0000FFB0
		// (set) Token: 0x06000349 RID: 841 RVA: 0x00011DC8 File Offset: 0x0000FFC8
		public int Minimum
		{
			get
			{
				return this._Minimum;
			}
			set
			{
				this._Minimum = value;
				bool flag = value > this._Value;
				if (flag)
				{
					this._Value = value;
				}
				bool flag2 = value > this._Maximum;
				if (flag2)
				{
					this._Maximum = value;
				}
				this.InvalidateLayout();
			}
		}

		// Token: 0x1700012A RID: 298
		// (get) Token: 0x0600034A RID: 842 RVA: 0x00011E0C File Offset: 0x0001000C
		// (set) Token: 0x0600034B RID: 843 RVA: 0x00011E24 File Offset: 0x00010024
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
				bool flag2 = value < this._Minimum;
				if (flag2)
				{
					this._Minimum = value;
				}
			}
		}

		// Token: 0x1700012B RID: 299
		// (get) Token: 0x0600034C RID: 844 RVA: 0x00011E5C File Offset: 0x0001005C
		// (set) Token: 0x0600034D RID: 845 RVA: 0x00011E74 File Offset: 0x00010074
		public int Value
		{
			get
			{
				return this._Value;
			}
			set
			{
				bool flag = value == this._Value;
				if (!flag)
				{
					flag = (value < this._Minimum);
					if (flag)
					{
						this._Value = this._Minimum;
					}
					else
					{
						flag = (value > this._Maximum);
						if (flag)
						{
							this._Value = this._Maximum;
						}
						else
						{
							this._Value = value;
						}
					}
					this.InvalidatePosition();
					LogInVerticalScrollBar.ScrollEventHandler scrollEvent = this.ScrollEvent;
					if (scrollEvent != null)
					{
						scrollEvent(this);
					}
				}
			}
		}

		// Token: 0x1700012C RID: 300
		// (get) Token: 0x0600034E RID: 846 RVA: 0x00011EEC File Offset: 0x000100EC
		// (set) Token: 0x0600034F RID: 847 RVA: 0x00011F04 File Offset: 0x00010104
		public int SmallChange
		{
			get
			{
				return this._SmallChange;
			}
			set
			{
				bool flag = value < 1;
				if (!flag)
				{
					flag = (value > ((-(((this._SmallChange == value) > false) ? 1 : 0)) ? 1 : 0));
					if (flag)
					{
					}
				}
			}
		}

		// Token: 0x1700012D RID: 301
		// (get) Token: 0x06000350 RID: 848 RVA: 0x00011F34 File Offset: 0x00010134
		// (set) Token: 0x06000351 RID: 849 RVA: 0x00011F4C File Offset: 0x0001014C
		public int LargeChange
		{
			get
			{
				return this._LargeChange;
			}
			set
			{
				bool flag = value < 1;
				if (!flag)
				{
					this._LargeChange = value;
				}
			}
		}

		// Token: 0x1700012E RID: 302
		// (get) Token: 0x06000352 RID: 850 RVA: 0x00011F70 File Offset: 0x00010170
		// (set) Token: 0x06000353 RID: 851 RVA: 0x00011F88 File Offset: 0x00010188
		public int ButtonSize
		{
			get
			{
				return this._ButtonSize;
			}
			set
			{
				bool flag = value < 16;
				if (flag)
				{
					this._ButtonSize = 16;
				}
				else
				{
					this._ButtonSize = value;
				}
			}
		}

		// Token: 0x06000354 RID: 852 RVA: 0x0000365D File Offset: 0x0000185D
		protected override void OnSizeChanged(EventArgs e)
		{
			this.InvalidateLayout();
		}

		// Token: 0x06000355 RID: 853 RVA: 0x00011FB4 File Offset: 0x000101B4
		private void InvalidateLayout()
		{
			this.TSA = new Rectangle(0, 1, base.Width, 0);
			checked
			{
				this.Shaft = new Rectangle(0, this.TSA.Bottom - 1, base.Width, base.Height - 3);
				this.ShowThumb = (this._Maximum - this._Minimum != 0);
				bool showThumb = this.ShowThumb;
				if (showThumb)
				{
					this.Thumb = new Rectangle(1, 0, base.Width - 3, this._ThumbSize);
				}
				LogInVerticalScrollBar.ScrollEventHandler scrollEvent = this.ScrollEvent;
				if (scrollEvent != null)
				{
					scrollEvent(this);
				}
				this.InvalidatePosition();
			}
		}

		// Token: 0x06000356 RID: 854 RVA: 0x00012054 File Offset: 0x00010254
		private void InvalidatePosition()
		{
			this.Thumb.Y = checked((int)Math.Round(unchecked(checked((double)(this._Value - this._Minimum) / (double)(this._Maximum - this._Minimum)) * (double)(checked(this.Shaft.Height - this._ThumbSize)) + 1.0)));
			base.Invalidate();
		}

		// Token: 0x06000357 RID: 855 RVA: 0x000120B8 File Offset: 0x000102B8
		protected override void OnMouseDown(MouseEventArgs e)
		{
			bool flag = e.Button == MouseButtons.Left && this.ShowThumb;
			checked
			{
				if (flag)
				{
					bool flag2 = this.TSA.Contains(e.Location);
					if (flag2)
					{
						this.ThumbMovement = this._Value - this._SmallChange;
					}
					else
					{
						bool flag3 = this.BSA.Contains(e.Location);
						if (flag3)
						{
							this.ThumbMovement = this._Value + this._SmallChange;
						}
						else
						{
							bool flag4 = this.Thumb.Contains(e.Location);
							if (flag4)
							{
								this.ThumbPressed = true;
								return;
							}
							bool flag5 = e.Y < this.Thumb.Y;
							if (flag5)
							{
								this.ThumbMovement = this._Value - this._LargeChange;
							}
							else
							{
								this.ThumbMovement = this._Value + this._LargeChange;
							}
						}
					}
					this.Value = Math.Min(Math.Max(this.ThumbMovement, this._Minimum), this._Maximum);
					this.InvalidatePosition();
				}
			}
		}

		// Token: 0x06000358 RID: 856 RVA: 0x000121D0 File Offset: 0x000103D0
		protected override void OnMouseMove(MouseEventArgs e)
		{
			bool flag = this.ThumbPressed && this.ShowThumb;
			checked
			{
				if (flag)
				{
					int ThumbPosition = e.Y - this.TSA.Height - this._ThumbSize / 2;
					int ThumbBounds = this.Shaft.Height - this._ThumbSize;
					this.ThumbMovement = (int)Math.Round(unchecked((double)ThumbPosition / (double)ThumbBounds * (double)(checked(this._Maximum - this._Minimum)))) + this._Minimum;
					this.Value = Math.Min(Math.Max(this.ThumbMovement, this._Minimum), this._Maximum);
					this.InvalidatePosition();
				}
			}
		}

		// Token: 0x06000359 RID: 857 RVA: 0x00003667 File Offset: 0x00001867
		protected override void OnMouseUp(MouseEventArgs e)
		{
			this.ThumbPressed = false;
		}

		// Token: 0x0600035A RID: 858 RVA: 0x00012278 File Offset: 0x00010478
		public LogInVerticalScrollBar()
		{
			this._ThumbSize = 24;
			this._Minimum = 0;
			this._Maximum = 100;
			this._Value = 0;
			this._SmallChange = 1;
			this._ButtonSize = 16;
			this._LargeChange = 10;
			this._ThumbBorder = Color.FromArgb(35, 35, 35);
			this._LineColour = Color.FromArgb(255, 0, 0);
			this._ArrowColour = Color.FromArgb(37, 37, 37);
			this._BaseColour = Color.FromArgb(47, 47, 47);
			this._ThumbColour = Color.FromArgb(55, 55, 55);
			this._ThumbSecondBorder = Color.FromArgb(65, 65, 65);
			this._FirstBorder = Color.FromArgb(55, 55, 55);
			this._SecondBorder = Color.FromArgb(35, 35, 35);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.Selectable | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Size = new Size(24, 50);
		}

		// Token: 0x0600035B RID: 859 RVA: 0x00012370 File Offset: 0x00010570
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics g = e.Graphics;
			Graphics graphics = g;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.Clear(this._BaseColour);
			checked
			{
				Point[] P = new Point[]
				{
					new Point((int)Math.Round((double)base.Width / 2.0), 5),
					new Point((int)Math.Round((double)base.Width / 4.0), 13),
					new Point((int)Math.Round(unchecked((double)base.Width / 2.0 - 2.0)), 13),
					new Point((int)Math.Round(unchecked((double)base.Width / 2.0 - 2.0)), base.Height - 13),
					new Point((int)Math.Round((double)base.Width / 4.0), base.Height - 13),
					new Point((int)Math.Round((double)base.Width / 2.0), base.Height - 5),
					new Point((int)Math.Round(unchecked((double)base.Width - (double)base.Width / 4.0 - 1.0)), base.Height - 13),
					new Point((int)Math.Round(unchecked((double)base.Width / 2.0 + 2.0)), base.Height - 13),
					new Point((int)Math.Round(unchecked((double)base.Width / 2.0 + 2.0)), 13),
					new Point((int)Math.Round(unchecked((double)base.Width - (double)base.Width / 4.0 - 1.0)), 13)
				};
				graphics.FillPolygon(new SolidBrush(this._ArrowColour), P);
				graphics.FillRectangle(new SolidBrush(this._ThumbColour), this.Thumb);
				graphics.DrawRectangle(new Pen(this._ThumbBorder), this.Thumb);
				graphics.DrawRectangle(new Pen(this._ThumbSecondBorder), this.Thumb.X + 1, this.Thumb.Y + 1, this.Thumb.Width - 2, this.Thumb.Height - 2);
				graphics.DrawLine(new Pen(this._LineColour, 2f), new Point((int)Math.Round(unchecked((double)this.Thumb.Width / 2.0 + 1.0)), this.Thumb.Y + 4), new Point((int)Math.Round(unchecked((double)this.Thumb.Width / 2.0 + 1.0)), this.Thumb.Bottom - 4));
				graphics.DrawRectangle(new Pen(this._FirstBorder), 0, 0, base.Width - 1, base.Height - 1);
				graphics.DrawRectangle(new Pen(this._SecondBorder), 1, 1, base.Width - 3, base.Height - 3);
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			}
		}

		// Token: 0x04000149 RID: 329
		private int ThumbMovement;

		// Token: 0x0400014A RID: 330
		private Rectangle TSA;

		// Token: 0x0400014B RID: 331
		private Rectangle BSA;

		// Token: 0x0400014C RID: 332
		private Rectangle Shaft;

		// Token: 0x0400014D RID: 333
		private Rectangle Thumb;

		// Token: 0x0400014E RID: 334
		private bool ShowThumb;

		// Token: 0x0400014F RID: 335
		private bool ThumbPressed;

		// Token: 0x04000150 RID: 336
		private int _ThumbSize;

		// Token: 0x04000151 RID: 337
		public int _Minimum;

		// Token: 0x04000152 RID: 338
		public int _Maximum;

		// Token: 0x04000153 RID: 339
		public int _Value;

		// Token: 0x04000154 RID: 340
		public int _SmallChange;

		// Token: 0x04000155 RID: 341
		private int _ButtonSize;

		// Token: 0x04000156 RID: 342
		public int _LargeChange;

		// Token: 0x04000157 RID: 343
		private Color _ThumbBorder;

		// Token: 0x04000158 RID: 344
		private Color _LineColour;

		// Token: 0x04000159 RID: 345
		private Color _ArrowColour;

		// Token: 0x0400015A RID: 346
		private Color _BaseColour;

		// Token: 0x0400015B RID: 347
		private Color _ThumbColour;

		// Token: 0x0400015C RID: 348
		private Color _ThumbSecondBorder;

		// Token: 0x0400015D RID: 349
		private Color _FirstBorder;

		// Token: 0x0400015E RID: 350
		private Color _SecondBorder;

		// Token: 0x02000036 RID: 54
		// (Invoke) Token: 0x0600035F RID: 863
		public delegate void ScrollEventHandler(object sender);
	}
}
