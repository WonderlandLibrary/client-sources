using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x020000A2 RID: 162
public class GControl35 : Control
{
	// Token: 0x17000221 RID: 545
	// (get) Token: 0x0600077F RID: 1919 RVA: 0x00023444 File Offset: 0x00021644
	// (set) Token: 0x06000780 RID: 1920 RVA: 0x0002345C File Offset: 0x0002165C
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

	// Token: 0x17000222 RID: 546
	// (get) Token: 0x06000781 RID: 1921 RVA: 0x00023468 File Offset: 0x00021668
	// (set) Token: 0x06000782 RID: 1922 RVA: 0x00023480 File Offset: 0x00021680
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

	// Token: 0x17000223 RID: 547
	// (get) Token: 0x06000783 RID: 1923 RVA: 0x0002348C File Offset: 0x0002168C
	// (set) Token: 0x06000784 RID: 1924 RVA: 0x000234A4 File Offset: 0x000216A4
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

	// Token: 0x17000224 RID: 548
	// (get) Token: 0x06000785 RID: 1925 RVA: 0x000234B0 File Offset: 0x000216B0
	// (set) Token: 0x06000786 RID: 1926 RVA: 0x000234C8 File Offset: 0x000216C8
	[Category("Control")]
	public GControl35.GEnum6 GEnum6_0
	{
		get
		{
			return this.genum6_0;
		}
		set
		{
			this.genum6_0 = value;
		}
	}

	// Token: 0x17000225 RID: 549
	// (get) Token: 0x06000787 RID: 1927 RVA: 0x000234D4 File Offset: 0x000216D4
	// (set) Token: 0x06000788 RID: 1928 RVA: 0x000234EC File Offset: 0x000216EC
	[Category("Control")]
	public GControl35.GEnum5 GEnum5_0
	{
		get
		{
			return this.genum5_0;
		}
		set
		{
			this.genum5_0 = value;
		}
	}

	// Token: 0x17000226 RID: 550
	// (get) Token: 0x06000789 RID: 1929 RVA: 0x000234F8 File Offset: 0x000216F8
	// (set) Token: 0x0600078A RID: 1930 RVA: 0x00023500 File Offset: 0x00021700
	public bool Boolean_0
	{
		get
		{
			return this.bool_1;
		}
		set
		{
			this.bool_1 = value;
		}
	}

	// Token: 0x0600078B RID: 1931 RVA: 0x0002350C File Offset: 0x0002170C
	protected virtual void CreateHandle()
	{
		base.CreateHandle();
		this.Dock = DockStyle.Bottom;
	}

	// Token: 0x0600078C RID: 1932 RVA: 0x0002351C File Offset: 0x0002171C
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		base.Invalidate();
	}

	// Token: 0x17000227 RID: 551
	// (get) Token: 0x0600078D RID: 1933 RVA: 0x0002352C File Offset: 0x0002172C
	// (set) Token: 0x0600078E RID: 1934 RVA: 0x00023544 File Offset: 0x00021744
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

	// Token: 0x17000228 RID: 552
	// (get) Token: 0x0600078F RID: 1935 RVA: 0x00023550 File Offset: 0x00021750
	// (set) Token: 0x06000790 RID: 1936 RVA: 0x00023558 File Offset: 0x00021758
	public bool Boolean_1
	{
		get
		{
			return this.bool_0;
		}
		set
		{
			this.bool_0 = value;
		}
	}

	// Token: 0x06000791 RID: 1937 RVA: 0x00023564 File Offset: 0x00021764
	public GControl35()
	{
		this.color_0 = Color.FromArgb(42, 42, 42);
		this.color_1 = Color.FromArgb(35, 35, 35);
		this.color_2 = Color.White;
		this.color_3 = Color.FromArgb(21, 117, 149);
		this.bool_0 = true;
		this.genum5_0 = GControl35.GEnum5.One;
		this.genum6_0 = GControl35.GEnum6.Left;
		this.bool_1 = true;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.Font = new Font("Segoe UI", 9f);
		this.ForeColor = Color.White;
		base.Size = new Size(base.Width, 20);
	}

	// Token: 0x06000792 RID: 1938 RVA: 0x0002361C File Offset: 0x0002181C
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Rectangle rect = new Rectangle(0, 0, base.Width, base.Height);
		Graphics graphics2 = graphics;
		graphics2.SmoothingMode = SmoothingMode.HighQuality;
		graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics2.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		graphics2.Clear(this.Color_0);
		graphics2.FillRectangle(new SolidBrush(this.Color_0), rect);
		checked
		{
			if (!this.bool_0)
			{
				graphics2.DrawString(this.Text, this.Font, Brushes.White, new Rectangle(5, 2, base.Width, base.Height), new StringFormat
				{
					Alignment = StringAlignment.Near,
					LineAlignment = StringAlignment.Near
				});
			}
			else
			{
				GControl35.GEnum5 genum = this.genum5_0;
				if (genum == GControl35.GEnum5.One)
				{
					if (this.genum6_0 != GControl35.GEnum6.Left)
					{
						if (this.genum6_0 != GControl35.GEnum6.Center)
						{
							graphics2.DrawString(this.Text, this.Font, new SolidBrush(this.color_2), new Rectangle(0, 0, base.Width - 5, base.Height), new StringFormat
							{
								Alignment = StringAlignment.Far,
								LineAlignment = StringAlignment.Center
							});
						}
						else
						{
							graphics2.DrawString(this.Text, this.Font, new SolidBrush(this.color_2), new Rectangle(0, 0, base.Width, base.Height), new StringFormat
							{
								Alignment = StringAlignment.Center,
								LineAlignment = StringAlignment.Center
							});
						}
					}
					else
					{
						graphics2.DrawString(this.Text, this.Font, new SolidBrush(this.color_2), new Rectangle(22, 2, base.Width, base.Height), new StringFormat
						{
							Alignment = StringAlignment.Near,
							LineAlignment = StringAlignment.Near
						});
					}
					graphics2.FillRectangle(new SolidBrush(this.color_3), new Rectangle(5, 9, 14, 3));
				}
				else if (genum == GControl35.GEnum5.Two)
				{
					if (this.genum6_0 == GControl35.GEnum6.Left)
					{
						graphics2.DrawString(this.Text, this.Font, new SolidBrush(this.color_2), new Rectangle(22, 2, base.Width, base.Height), new StringFormat
						{
							Alignment = StringAlignment.Near,
							LineAlignment = StringAlignment.Near
						});
					}
					else if (this.genum6_0 == GControl35.GEnum6.Center)
					{
						graphics2.DrawString(this.Text, this.Font, new SolidBrush(this.color_2), new Rectangle(0, 0, base.Width, base.Height), new StringFormat
						{
							Alignment = StringAlignment.Center,
							LineAlignment = StringAlignment.Center
						});
					}
					else
					{
						graphics2.DrawString(this.Text, this.Font, new SolidBrush(this.color_2), new Rectangle(0, 0, base.Width - 22, base.Height), new StringFormat
						{
							Alignment = StringAlignment.Far,
							LineAlignment = StringAlignment.Center
						});
					}
					graphics2.FillRectangle(new SolidBrush(this.color_3), new Rectangle(5, 9, 14, 3));
					graphics2.FillRectangle(new SolidBrush(this.color_3), new Rectangle(base.Width - 20, 9, 14, 3));
				}
			}
			if (this.bool_1)
			{
				graphics2.DrawLine(new Pen(this.color_1, 2f), new Point(0, 0), new Point(base.Width, 0));
			}
			base.OnPaint(e);
			graphics.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}
	}

	// Token: 0x04000382 RID: 898
	private Color color_0;

	// Token: 0x04000383 RID: 899
	private Color color_1;

	// Token: 0x04000384 RID: 900
	private Color color_2;

	// Token: 0x04000385 RID: 901
	private Color color_3;

	// Token: 0x04000386 RID: 902
	private bool bool_0;

	// Token: 0x04000387 RID: 903
	private GControl35.GEnum5 genum5_0;

	// Token: 0x04000388 RID: 904
	private GControl35.GEnum6 genum6_0;

	// Token: 0x04000389 RID: 905
	private bool bool_1;

	// Token: 0x020000A3 RID: 163
	public enum GEnum5
	{
		// Token: 0x0400038B RID: 907
		One = 1,
		// Token: 0x0400038C RID: 908
		Two
	}

	// Token: 0x020000A4 RID: 164
	public enum GEnum6
	{
		// Token: 0x0400038E RID: 910
		Left,
		// Token: 0x0400038F RID: 911
		Center,
		// Token: 0x04000390 RID: 912
		Right
	}
}
