using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x02000037 RID: 55
	[DefaultEvent("Scroll")]
	public class LogInHorizontalScrollBar : Control
	{
		// Token: 0x1700012F RID: 303
		// (get) Token: 0x06000360 RID: 864 RVA: 0x000126F8 File Offset: 0x000108F8
		// (set) Token: 0x06000361 RID: 865 RVA: 0x00003671 File Offset: 0x00001871
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

		// Token: 0x17000130 RID: 304
		// (get) Token: 0x06000362 RID: 866 RVA: 0x00012710 File Offset: 0x00010910
		// (set) Token: 0x06000363 RID: 867 RVA: 0x0000367B File Offset: 0x0000187B
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

		// Token: 0x17000131 RID: 305
		// (get) Token: 0x06000364 RID: 868 RVA: 0x00012728 File Offset: 0x00010928
		// (set) Token: 0x06000365 RID: 869 RVA: 0x00003685 File Offset: 0x00001885
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

		// Token: 0x17000132 RID: 306
		// (get) Token: 0x06000366 RID: 870 RVA: 0x00012740 File Offset: 0x00010940
		// (set) Token: 0x06000367 RID: 871 RVA: 0x0000368F File Offset: 0x0000188F
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

		// Token: 0x17000133 RID: 307
		// (get) Token: 0x06000368 RID: 872 RVA: 0x00012758 File Offset: 0x00010958
		// (set) Token: 0x06000369 RID: 873 RVA: 0x00003699 File Offset: 0x00001899
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

		// Token: 0x17000134 RID: 308
		// (get) Token: 0x0600036A RID: 874 RVA: 0x00012770 File Offset: 0x00010970
		// (set) Token: 0x0600036B RID: 875 RVA: 0x000036A3 File Offset: 0x000018A3
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

		// Token: 0x17000135 RID: 309
		// (get) Token: 0x0600036C RID: 876 RVA: 0x00012788 File Offset: 0x00010988
		// (set) Token: 0x0600036D RID: 877 RVA: 0x000036AD File Offset: 0x000018AD
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

		// Token: 0x17000136 RID: 310
		// (get) Token: 0x0600036E RID: 878 RVA: 0x000127A0 File Offset: 0x000109A0
		// (set) Token: 0x0600036F RID: 879 RVA: 0x000036B7 File Offset: 0x000018B7
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

		// Token: 0x14000007 RID: 7
		// (add) Token: 0x06000370 RID: 880 RVA: 0x000127B8 File Offset: 0x000109B8
		// (remove) Token: 0x06000371 RID: 881 RVA: 0x000127F0 File Offset: 0x000109F0
		public event LogInHorizontalScrollBar.ScrollEventHandler Scroll;

		// Token: 0x17000137 RID: 311
		// (get) Token: 0x06000372 RID: 882 RVA: 0x00012828 File Offset: 0x00010A28
		// (set) Token: 0x06000373 RID: 883 RVA: 0x00012840 File Offset: 0x00010A40
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

		// Token: 0x17000138 RID: 312
		// (get) Token: 0x06000374 RID: 884 RVA: 0x00012884 File Offset: 0x00010A84
		// (set) Token: 0x06000375 RID: 885 RVA: 0x0001289C File Offset: 0x00010A9C
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

		// Token: 0x17000139 RID: 313
		// (get) Token: 0x06000376 RID: 886 RVA: 0x000128D4 File Offset: 0x00010AD4
		// (set) Token: 0x06000377 RID: 887 RVA: 0x000128EC File Offset: 0x00010AEC
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
					LogInHorizontalScrollBar.ScrollEventHandler scrollEvent = this.ScrollEvent;
					if (scrollEvent != null)
					{
						scrollEvent(this);
					}
				}
			}
		}

		// Token: 0x1700013A RID: 314
		// (get) Token: 0x06000378 RID: 888 RVA: 0x00012964 File Offset: 0x00010B64
		// (set) Token: 0x06000379 RID: 889 RVA: 0x0001297C File Offset: 0x00010B7C
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

		// Token: 0x1700013B RID: 315
		// (get) Token: 0x0600037A RID: 890 RVA: 0x000129AC File Offset: 0x00010BAC
		// (set) Token: 0x0600037B RID: 891 RVA: 0x000129C4 File Offset: 0x00010BC4
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

		// Token: 0x1700013C RID: 316
		// (get) Token: 0x0600037C RID: 892 RVA: 0x000129E8 File Offset: 0x00010BE8
		// (set) Token: 0x0600037D RID: 893 RVA: 0x00012A00 File Offset: 0x00010C00
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

		// Token: 0x0600037E RID: 894 RVA: 0x000036C1 File Offset: 0x000018C1
		protected override void OnSizeChanged(EventArgs e)
		{
			this.InvalidateLayout();
		}

		// Token: 0x0600037F RID: 895 RVA: 0x00012A2C File Offset: 0x00010C2C
		private void InvalidateLayout()
		{
			this.LSA = new Rectangle(0, 1, 0, base.Height);
			checked
			{
				this.Shaft = new Rectangle(this.LSA.Right + 1, 0, base.Width - 3, base.Height);
				this.ShowThumb = (this._Maximum - this._Minimum != 0);
				this.Thumb = new Rectangle(0, 1, this._ThumbSize, base.Height - 3);
				LogInHorizontalScrollBar.ScrollEventHandler scrollEvent = this.ScrollEvent;
				if (scrollEvent != null)
				{
					scrollEvent(this);
				}
				this.InvalidatePosition();
			}
		}

		// Token: 0x06000380 RID: 896 RVA: 0x00012AC0 File Offset: 0x00010CC0
		private void InvalidatePosition()
		{
			this.Thumb.X = checked((int)Math.Round(unchecked(checked((double)(this._Value - this._Minimum) / (double)(this._Maximum - this._Minimum)) * (double)(checked(this.Shaft.Width - this._ThumbSize)) + 1.0)));
			base.Invalidate();
		}

		// Token: 0x06000381 RID: 897 RVA: 0x00012B24 File Offset: 0x00010D24
		protected override void OnMouseDown(MouseEventArgs e)
		{
			bool flag = e.Button == MouseButtons.Left && this.ShowThumb;
			checked
			{
				if (flag)
				{
					bool flag2 = this.LSA.Contains(e.Location);
					if (flag2)
					{
						this.ThumbMovement = this._Value - this._SmallChange;
					}
					else
					{
						bool flag3 = this.RSA.Contains(e.Location);
						if (flag3)
						{
							this.ThumbMovement = this._Value + this._SmallChange;
						}
						else
						{
							bool flag4 = this.Thumb.Contains(e.Location);
							if (flag4)
							{
								this.ThumbDown = true;
								return;
							}
							bool flag5 = e.X < this.Thumb.X;
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

		// Token: 0x06000382 RID: 898 RVA: 0x00012C3C File Offset: 0x00010E3C
		protected override void OnMouseMove(MouseEventArgs e)
		{
			bool flag = this.ThumbDown && this.ShowThumb;
			checked
			{
				if (flag)
				{
					int ThumbPosition = e.X - this.LSA.Width - this._ThumbSize / 2;
					int ThumbBounds = this.Shaft.Width - this._ThumbSize;
					this.ThumbMovement = (int)Math.Round(unchecked((double)ThumbPosition / (double)ThumbBounds * (double)(checked(this._Maximum - this._Minimum)))) + this._Minimum;
					this.Value = Math.Min(Math.Max(this.ThumbMovement, this._Minimum), this._Maximum);
					this.InvalidatePosition();
				}
			}
		}

		// Token: 0x06000383 RID: 899 RVA: 0x000036CB File Offset: 0x000018CB
		protected override void OnMouseUp(MouseEventArgs e)
		{
			this.ThumbDown = false;
		}

		// Token: 0x06000384 RID: 900 RVA: 0x00012CE4 File Offset: 0x00010EE4
		public LogInHorizontalScrollBar()
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
			this.ThumbDown = false;
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.Selectable | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Height = 18;
		}

		// Token: 0x06000385 RID: 901 RVA: 0x00012DDC File Offset: 0x00010FDC
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics g = e.Graphics;
			Graphics graphics = g;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.Clear(Color.FromArgb(47, 47, 47));
			checked
			{
				Point[] P = new Point[]
				{
					new Point(5, (int)Math.Round((double)base.Height / 2.0)),
					new Point(13, (int)Math.Round((double)base.Height / 4.0)),
					new Point(13, (int)Math.Round(unchecked((double)base.Height / 2.0 - 2.0))),
					new Point(base.Width - 13, (int)Math.Round(unchecked((double)base.Height / 2.0 - 2.0))),
					new Point(base.Width - 13, (int)Math.Round((double)base.Height / 4.0)),
					new Point(base.Width - 5, (int)Math.Round((double)base.Height / 2.0)),
					new Point(base.Width - 13, (int)Math.Round(unchecked((double)base.Height - (double)base.Height / 4.0 - 1.0))),
					new Point(base.Width - 13, (int)Math.Round(unchecked((double)base.Height / 2.0 + 2.0))),
					new Point(13, (int)Math.Round(unchecked((double)base.Height / 2.0 + 2.0))),
					new Point(13, (int)Math.Round(unchecked((double)base.Height - (double)base.Height / 4.0 - 1.0)))
				};
				graphics.FillPolygon(new SolidBrush(this._ArrowColour), P);
				graphics.FillRectangle(new SolidBrush(this._ThumbColour), this.Thumb);
				graphics.DrawRectangle(new Pen(this._ThumbBorder), this.Thumb);
				graphics.DrawRectangle(new Pen(this._ThumbSecondBorder), this.Thumb.X + 1, this.Thumb.Y + 1, this.Thumb.Width - 2, this.Thumb.Height - 2);
				graphics.DrawLine(new Pen(this._LineColour, 2f), new Point(this.Thumb.X + 4, (int)Math.Round(unchecked((double)this.Thumb.Height / 2.0 + 1.0))), new Point(this.Thumb.Right - 4, (int)Math.Round(unchecked((double)this.Thumb.Height / 2.0 + 1.0))));
				graphics.DrawRectangle(new Pen(this._FirstBorder), 0, 0, base.Width - 1, base.Height - 1);
				graphics.DrawRectangle(new Pen(this._SecondBorder), 1, 1, base.Width - 3, base.Height - 3);
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			}
		}

		// Token: 0x04000160 RID: 352
		private int ThumbMovement;

		// Token: 0x04000161 RID: 353
		private Rectangle LSA;

		// Token: 0x04000162 RID: 354
		private Rectangle RSA;

		// Token: 0x04000163 RID: 355
		private Rectangle Shaft;

		// Token: 0x04000164 RID: 356
		private Rectangle Thumb;

		// Token: 0x04000165 RID: 357
		private bool ShowThumb;

		// Token: 0x04000166 RID: 358
		private bool ThumbPressed;

		// Token: 0x04000167 RID: 359
		private int _ThumbSize;

		// Token: 0x04000168 RID: 360
		private int _Minimum;

		// Token: 0x04000169 RID: 361
		private int _Maximum;

		// Token: 0x0400016A RID: 362
		private int _Value;

		// Token: 0x0400016B RID: 363
		private int _SmallChange;

		// Token: 0x0400016C RID: 364
		private int _ButtonSize;

		// Token: 0x0400016D RID: 365
		private int _LargeChange;

		// Token: 0x0400016E RID: 366
		private Color _ThumbBorder;

		// Token: 0x0400016F RID: 367
		private Color _LineColour;

		// Token: 0x04000170 RID: 368
		private Color _ArrowColour;

		// Token: 0x04000171 RID: 369
		private Color _BaseColour;

		// Token: 0x04000172 RID: 370
		private Color _ThumbColour;

		// Token: 0x04000173 RID: 371
		private Color _ThumbSecondBorder;

		// Token: 0x04000174 RID: 372
		private Color _FirstBorder;

		// Token: 0x04000175 RID: 373
		private Color _SecondBorder;

		// Token: 0x04000176 RID: 374
		private bool ThumbDown;

		// Token: 0x02000038 RID: 56
		// (Invoke) Token: 0x06000389 RID: 905
		public delegate void ScrollEventHandler(object sender);
	}
}
