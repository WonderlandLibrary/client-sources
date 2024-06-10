using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

// Token: 0x020000AC RID: 172
[DefaultEvent("Scroll")]
public class GControl39 : Control
{
	// Token: 0x17000245 RID: 581
	// (get) Token: 0x060007EF RID: 2031 RVA: 0x00025424 File Offset: 0x00023624
	// (set) Token: 0x060007F0 RID: 2032 RVA: 0x0002543C File Offset: 0x0002363C
	[Category("Colours")]
	public Color Color_0
	{
		get
		{
			return this.color_0;
		}
		set
		{
			this.color_0 = value;
		}
	}

	// Token: 0x17000246 RID: 582
	// (get) Token: 0x060007F1 RID: 2033 RVA: 0x00025448 File Offset: 0x00023648
	// (set) Token: 0x060007F2 RID: 2034 RVA: 0x00025460 File Offset: 0x00023660
	[Category("Colours")]
	public Color Color_1
	{
		get
		{
			return this.color_1;
		}
		set
		{
			this.color_1 = value;
		}
	}

	// Token: 0x17000247 RID: 583
	// (get) Token: 0x060007F3 RID: 2035 RVA: 0x0002546C File Offset: 0x0002366C
	// (set) Token: 0x060007F4 RID: 2036 RVA: 0x00025484 File Offset: 0x00023684
	[Category("Colours")]
	public Color Color_2
	{
		get
		{
			return this.color_2;
		}
		set
		{
			this.color_2 = value;
		}
	}

	// Token: 0x17000248 RID: 584
	// (get) Token: 0x060007F5 RID: 2037 RVA: 0x00025490 File Offset: 0x00023690
	// (set) Token: 0x060007F6 RID: 2038 RVA: 0x000254A8 File Offset: 0x000236A8
	[Category("Colours")]
	public Color Color_3
	{
		get
		{
			return this.color_3;
		}
		set
		{
			this.color_3 = value;
		}
	}

	// Token: 0x17000249 RID: 585
	// (get) Token: 0x060007F7 RID: 2039 RVA: 0x000254B4 File Offset: 0x000236B4
	// (set) Token: 0x060007F8 RID: 2040 RVA: 0x000254CC File Offset: 0x000236CC
	[Category("Colours")]
	public Color Color_4
	{
		get
		{
			return this.color_4;
		}
		set
		{
			this.color_4 = value;
		}
	}

	// Token: 0x1700024A RID: 586
	// (get) Token: 0x060007F9 RID: 2041 RVA: 0x000254D8 File Offset: 0x000236D8
	// (set) Token: 0x060007FA RID: 2042 RVA: 0x000254F0 File Offset: 0x000236F0
	[Category("Colours")]
	public Color Color_5
	{
		get
		{
			return this.color_5;
		}
		set
		{
			this.color_5 = value;
		}
	}

	// Token: 0x1700024B RID: 587
	// (get) Token: 0x060007FB RID: 2043 RVA: 0x000254FC File Offset: 0x000236FC
	// (set) Token: 0x060007FC RID: 2044 RVA: 0x00025514 File Offset: 0x00023714
	[Category("Colours")]
	public Color Color_6
	{
		get
		{
			return this.color_6;
		}
		set
		{
			this.color_6 = value;
		}
	}

	// Token: 0x1700024C RID: 588
	// (get) Token: 0x060007FD RID: 2045 RVA: 0x00025520 File Offset: 0x00023720
	// (set) Token: 0x060007FE RID: 2046 RVA: 0x00025538 File Offset: 0x00023738
	[Category("Colours")]
	public Color Color_7
	{
		get
		{
			return this.color_7;
		}
		set
		{
			this.color_7 = value;
		}
	}

	// Token: 0x1400001C RID: 28
	// (add) Token: 0x060007FF RID: 2047 RVA: 0x00025544 File Offset: 0x00023744
	// (remove) Token: 0x06000800 RID: 2048 RVA: 0x00025580 File Offset: 0x00023780
	public event GControl39.GDelegate15 Event_0
	{
		[CompilerGenerated]
		add
		{
			GControl39.GDelegate15 gdelegate = this.gdelegate15_0;
			GControl39.GDelegate15 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl39.GDelegate15 value2 = (GControl39.GDelegate15)Delegate.Combine(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl39.GDelegate15>(ref this.gdelegate15_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
		[CompilerGenerated]
		remove
		{
			GControl39.GDelegate15 gdelegate = this.gdelegate15_0;
			GControl39.GDelegate15 gdelegate2;
			do
			{
				gdelegate2 = gdelegate;
				GControl39.GDelegate15 value2 = (GControl39.GDelegate15)Delegate.Remove(gdelegate2, value);
				gdelegate = Interlocked.CompareExchange<GControl39.GDelegate15>(ref this.gdelegate15_0, value2, gdelegate2);
			}
			while (gdelegate != gdelegate2);
		}
	}

	// Token: 0x1700024D RID: 589
	// (get) Token: 0x06000801 RID: 2049 RVA: 0x000255B8 File Offset: 0x000237B8
	// (set) Token: 0x06000802 RID: 2050 RVA: 0x000255D0 File Offset: 0x000237D0
	public int Int32_0
	{
		get
		{
			return this.int_2;
		}
		set
		{
			this.int_2 = value;
			if (value > this.int_4)
			{
				this.int_4 = value;
			}
			if (value > this.int_3)
			{
				this.int_3 = value;
			}
			this.method_0();
		}
	}

	// Token: 0x1700024E RID: 590
	// (get) Token: 0x06000803 RID: 2051 RVA: 0x00025604 File Offset: 0x00023804
	// (set) Token: 0x06000804 RID: 2052 RVA: 0x0002561C File Offset: 0x0002381C
	public int Int32_1
	{
		get
		{
			return this.int_3;
		}
		set
		{
			if (value < this.int_4)
			{
				this.int_4 = value;
			}
			if (value < this.int_2)
			{
				this.int_2 = value;
			}
		}
	}

	// Token: 0x1700024F RID: 591
	// (get) Token: 0x06000805 RID: 2053 RVA: 0x00025644 File Offset: 0x00023844
	// (set) Token: 0x06000806 RID: 2054 RVA: 0x0002565C File Offset: 0x0002385C
	public int Int32_2
	{
		get
		{
			return this.int_4;
		}
		set
		{
			if (value != this.int_4)
			{
				if (value < this.int_2)
				{
					this.int_4 = this.int_2;
				}
				else if (value <= this.int_3)
				{
					this.int_4 = value;
				}
				else
				{
					this.int_4 = this.int_3;
				}
				this.method_1();
				GControl39.GDelegate15 gdelegate = this.gdelegate15_0;
				if (gdelegate != null)
				{
					gdelegate(this);
				}
			}
		}
	}

	// Token: 0x17000250 RID: 592
	// (get) Token: 0x06000807 RID: 2055 RVA: 0x000256C8 File Offset: 0x000238C8
	// (set) Token: 0x06000808 RID: 2056 RVA: 0x000256E0 File Offset: 0x000238E0
	public int Int32_3
	{
		get
		{
			return this.int_5;
		}
		set
		{
			if (value < 1 || value > ((-(((this.int_5 == value) > false) ? 1 : 0)) ? 1 : 0))
			{
			}
		}
	}

	// Token: 0x17000251 RID: 593
	// (get) Token: 0x06000809 RID: 2057 RVA: 0x00025708 File Offset: 0x00023908
	// (set) Token: 0x0600080A RID: 2058 RVA: 0x00025720 File Offset: 0x00023920
	public int Int32_4
	{
		get
		{
			return this.int_7;
		}
		set
		{
			if (value >= 1)
			{
				this.int_7 = value;
			}
		}
	}

	// Token: 0x17000252 RID: 594
	// (get) Token: 0x0600080B RID: 2059 RVA: 0x0002573C File Offset: 0x0002393C
	// (set) Token: 0x0600080C RID: 2060 RVA: 0x00025754 File Offset: 0x00023954
	public int Int32_5
	{
		get
		{
			return this.int_6;
		}
		set
		{
			if (value >= 16)
			{
				this.int_6 = value;
			}
			else
			{
				this.int_6 = 16;
			}
		}
	}

	// Token: 0x0600080D RID: 2061 RVA: 0x0002577C File Offset: 0x0002397C
	protected virtual void OnSizeChanged(EventArgs e)
	{
		this.method_0();
	}

	// Token: 0x0600080E RID: 2062 RVA: 0x00025784 File Offset: 0x00023984
	private void method_0()
	{
		this.rectangle_0 = new Rectangle(0, 1, base.Width, 0);
		checked
		{
			this.rectangle_2 = new Rectangle(0, this.rectangle_0.Bottom - 1, base.Width, base.Height - 3);
			this.bool_0 = (this.int_3 - this.int_2 != 0);
			if (this.bool_0)
			{
				this.rectangle_3 = new Rectangle(1, 0, base.Width - 3, this.int_1);
			}
			GControl39.GDelegate15 gdelegate = this.gdelegate15_0;
			if (gdelegate != null)
			{
				gdelegate(this);
			}
			this.method_1();
		}
	}

	// Token: 0x0600080F RID: 2063 RVA: 0x0002581C File Offset: 0x00023A1C
	private void method_1()
	{
		this.rectangle_3.Y = checked((int)Math.Round(unchecked(checked((double)(this.int_4 - this.int_2) / (double)(this.int_3 - this.int_2)) * (double)(checked(this.rectangle_2.Height - this.int_1)) + 1.0)));
		base.Invalidate();
	}

	// Token: 0x06000810 RID: 2064 RVA: 0x0002587C File Offset: 0x00023A7C
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		checked
		{
			if (e.Button == MouseButtons.Left && this.bool_0)
			{
				if (!this.rectangle_0.Contains(e.Location))
				{
					if (!this.rectangle_1.Contains(e.Location))
					{
						if (this.rectangle_3.Contains(e.Location))
						{
							this.bool_1 = true;
							return;
						}
						if (e.Y >= this.rectangle_3.Y)
						{
							this.int_0 = this.int_4 + this.int_7;
						}
						else
						{
							this.int_0 = this.int_4 - this.int_7;
						}
					}
					else
					{
						this.int_0 = this.int_4 + this.int_5;
					}
				}
				else
				{
					this.int_0 = this.int_4 - this.int_5;
				}
				this.Int32_2 = Math.Min(Math.Max(this.int_0, this.int_2), this.int_3);
				this.method_1();
			}
		}
	}

	// Token: 0x06000811 RID: 2065 RVA: 0x0002597C File Offset: 0x00023B7C
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		checked
		{
			if (this.bool_1 && this.bool_0)
			{
				int num = e.Y - this.rectangle_0.Height - this.int_1 / 2;
				int num2 = this.rectangle_2.Height - this.int_1;
				this.int_0 = (int)Math.Round(unchecked((double)num / (double)num2 * (double)(checked(this.int_3 - this.int_2)))) + this.int_2;
				this.Int32_2 = Math.Min(Math.Max(this.int_0, this.int_2), this.int_3);
				this.method_1();
			}
		}
	}

	// Token: 0x06000812 RID: 2066 RVA: 0x00025A1C File Offset: 0x00023C1C
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		this.bool_1 = false;
	}

	// Token: 0x06000813 RID: 2067 RVA: 0x00025A28 File Offset: 0x00023C28
	public GControl39()
	{
		this.int_1 = 24;
		this.int_2 = 0;
		this.int_3 = 100;
		this.int_4 = 0;
		this.int_5 = 1;
		this.int_6 = 16;
		this.int_7 = 10;
		this.color_0 = Color.FromArgb(35, 35, 35);
		this.color_1 = Color.FromArgb(23, 119, 151);
		this.color_2 = Color.FromArgb(37, 37, 37);
		this.color_3 = Color.FromArgb(47, 47, 47);
		this.color_4 = Color.FromArgb(55, 55, 55);
		this.color_5 = Color.FromArgb(65, 65, 65);
		this.color_6 = Color.FromArgb(55, 55, 55);
		this.color_7 = Color.FromArgb(35, 35, 35);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.Selectable | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		base.Size = new Size(24, 50);
	}

	// Token: 0x06000814 RID: 2068 RVA: 0x00025B20 File Offset: 0x00023D20
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Height, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Graphics graphics2 = graphics;
		graphics2.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		graphics2.SmoothingMode = SmoothingMode.HighQuality;
		graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics2.Clear(this.color_3);
		checked
		{
			Point[] points = new Point[]
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
			graphics2.FillPolygon(new SolidBrush(this.color_2), points);
			graphics2.FillRectangle(new SolidBrush(this.color_4), this.rectangle_3);
			graphics2.DrawRectangle(new Pen(this.color_0), this.rectangle_3);
			graphics2.DrawRectangle(new Pen(this.color_5), this.rectangle_3.X + 1, this.rectangle_3.Y + 1, this.rectangle_3.Width - 2, this.rectangle_3.Height - 2);
			graphics2.DrawLine(new Pen(this.color_1, 2f), new Point((int)Math.Round(unchecked((double)this.rectangle_3.Width / 2.0 + 1.0)), this.rectangle_3.Y + 4), new Point((int)Math.Round(unchecked((double)this.rectangle_3.Width / 2.0 + 1.0)), this.rectangle_3.Bottom - 4));
			graphics2.DrawRectangle(new Pen(this.color_6), 0, 0, base.Width - 1, base.Height - 1);
			graphics2.DrawRectangle(new Pen(this.color_7), 1, 1, base.Width - 3, base.Height - 3);
			base.OnPaint(e);
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}
	}

	// Token: 0x040003B9 RID: 953
	private int int_0;

	// Token: 0x040003BA RID: 954
	private Rectangle rectangle_0;

	// Token: 0x040003BB RID: 955
	private Rectangle rectangle_1;

	// Token: 0x040003BC RID: 956
	private Rectangle rectangle_2;

	// Token: 0x040003BD RID: 957
	private Rectangle rectangle_3;

	// Token: 0x040003BE RID: 958
	private bool bool_0;

	// Token: 0x040003BF RID: 959
	private bool bool_1;

	// Token: 0x040003C0 RID: 960
	private int int_1;

	// Token: 0x040003C1 RID: 961
	private int int_2;

	// Token: 0x040003C2 RID: 962
	private int int_3;

	// Token: 0x040003C3 RID: 963
	private int int_4;

	// Token: 0x040003C4 RID: 964
	private int int_5;

	// Token: 0x040003C5 RID: 965
	private int int_6;

	// Token: 0x040003C6 RID: 966
	private int int_7;

	// Token: 0x040003C7 RID: 967
	private Color color_0;

	// Token: 0x040003C8 RID: 968
	private Color color_1;

	// Token: 0x040003C9 RID: 969
	private Color color_2;

	// Token: 0x040003CA RID: 970
	private Color color_3;

	// Token: 0x040003CB RID: 971
	private Color color_4;

	// Token: 0x040003CC RID: 972
	private Color color_5;

	// Token: 0x040003CD RID: 973
	private Color color_6;

	// Token: 0x040003CE RID: 974
	private Color color_7;

	// Token: 0x040003CF RID: 975
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private GControl39.GDelegate15 gdelegate15_0;

	// Token: 0x020000AD RID: 173
	// (Invoke) Token: 0x06000818 RID: 2072
	public delegate void GDelegate15(object sender);
}
