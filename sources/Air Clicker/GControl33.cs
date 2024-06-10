using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x020000A0 RID: 160
public class GControl33 : Control
{
	// Token: 0x17000214 RID: 532
	// (get) Token: 0x06000758 RID: 1880 RVA: 0x00022BA4 File Offset: 0x00020DA4
	// (set) Token: 0x06000759 RID: 1881 RVA: 0x00022BBC File Offset: 0x00020DBC
	public Color Color_0
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

	// Token: 0x17000215 RID: 533
	// (get) Token: 0x0600075A RID: 1882 RVA: 0x00022BC8 File Offset: 0x00020DC8
	// (set) Token: 0x0600075B RID: 1883 RVA: 0x00022BD0 File Offset: 0x00020DD0
	[Category("Control")]
	public bool Boolean_0
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

	// Token: 0x17000216 RID: 534
	// (get) Token: 0x0600075C RID: 1884 RVA: 0x00022BDC File Offset: 0x00020DDC
	// (set) Token: 0x0600075D RID: 1885 RVA: 0x00022BF4 File Offset: 0x00020DF4
	[Category("Control")]
	public int Int32_0
	{
		get
		{
			return this.int_1;
		}
		set
		{
			if (value < this.int_0)
			{
				this.int_0 = value;
			}
			this.int_1 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000217 RID: 535
	// (get) Token: 0x0600075E RID: 1886 RVA: 0x00022C24 File Offset: 0x00020E24
	// (set) Token: 0x0600075F RID: 1887 RVA: 0x00022C48 File Offset: 0x00020E48
	[Category("Control")]
	public int Int32_1
	{
		get
		{
			int num = this.int_0;
			int result;
			if (num != 0)
			{
				result = this.int_0;
			}
			else
			{
				result = 0;
			}
			return result;
		}
		set
		{
			int num = value;
			if (num > this.int_1)
			{
				value = this.int_1;
				base.Invalidate();
			}
			this.int_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000218 RID: 536
	// (get) Token: 0x06000760 RID: 1888 RVA: 0x00022C80 File Offset: 0x00020E80
	// (set) Token: 0x06000761 RID: 1889 RVA: 0x00022C98 File Offset: 0x00020E98
	[Category("Colours")]
	public Color Color_1
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

	// Token: 0x17000219 RID: 537
	// (get) Token: 0x06000762 RID: 1890 RVA: 0x00022CA4 File Offset: 0x00020EA4
	// (set) Token: 0x06000763 RID: 1891 RVA: 0x00022CBC File Offset: 0x00020EBC
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

	// Token: 0x1700021A RID: 538
	// (get) Token: 0x06000764 RID: 1892 RVA: 0x00022CC8 File Offset: 0x00020EC8
	// (set) Token: 0x06000765 RID: 1893 RVA: 0x00022CE0 File Offset: 0x00020EE0
	[Category("Colours")]
	public Color Color_3
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

	// Token: 0x1700021B RID: 539
	// (get) Token: 0x06000766 RID: 1894 RVA: 0x00022CEC File Offset: 0x00020EEC
	// (set) Token: 0x06000767 RID: 1895 RVA: 0x00022D04 File Offset: 0x00020F04
	[Category("Colours")]
	public Color Color_4
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

	// Token: 0x06000768 RID: 1896 RVA: 0x00022D10 File Offset: 0x00020F10
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Height = 25;
	}

	// Token: 0x06000769 RID: 1897 RVA: 0x00022D24 File Offset: 0x00020F24
	protected virtual void CreateHandle()
	{
		base.CreateHandle();
		base.Height = 25;
	}

	// Token: 0x0600076A RID: 1898 RVA: 0x00022D34 File Offset: 0x00020F34
	public void method_0(int int_2)
	{
		checked
		{
			this.Int32_1 += int_2;
		}
	}

	// Token: 0x0600076B RID: 1899 RVA: 0x00022D44 File Offset: 0x00020F44
	public GControl33()
	{
		this.color_0 = Color.FromArgb(0, 160, 199);
		this.color_1 = Color.FromArgb(35, 35, 35);
		this.color_2 = Color.FromArgb(42, 42, 42);
		this.color_3 = Color.FromArgb(50, 50, 50);
		this.color_4 = Color.FromArgb(0, 145, 184);
		this.int_0 = 0;
		this.int_1 = 100;
		this.bool_0 = true;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
	}

	// Token: 0x0600076C RID: 1900 RVA: 0x00022DE0 File Offset: 0x00020FE0
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Rectangle rect = new Rectangle(0, 0, base.Width, base.Height);
		Graphics graphics2 = graphics;
		graphics2.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		graphics2.SmoothingMode = SmoothingMode.HighQuality;
		graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics2.Clear(this.BackColor);
		checked
		{
			int num = (int)Math.Round(unchecked((double)this.int_0 / (double)this.int_1 * (double)base.Width));
			int int32_ = this.Int32_1;
			if (int32_ == 0)
			{
				graphics2.FillRectangle(new SolidBrush(this.color_2), rect);
				graphics2.FillRectangle(new SolidBrush(this.color_0), new Rectangle(0, 0, num - 1, base.Height));
				graphics2.DrawRectangle(new Pen(this.color_1, 3f), rect);
			}
			else if (int32_ == this.int_1)
			{
				graphics2.FillRectangle(new SolidBrush(this.color_2), rect);
				graphics2.FillRectangle(new SolidBrush(this.color_0), new Rectangle(0, 0, num - 1, base.Height));
				if (this.bool_0)
				{
					graphics2.SetClip(new Rectangle(0, 0, (int)Math.Round(unchecked((double)(checked(base.Width * this.int_0)) / (double)this.int_1 - 1.0)), base.Height - 1));
					double num2 = (double)((base.Width - 1) * this.int_1) / (double)this.int_0;
					for (double num3 = 0.0; num3 <= num2; num3 = unchecked(num3 + 25.0))
					{
						graphics2.DrawLine(new Pen(new SolidBrush(this.color_4), 7f), new Point((int)Math.Round(num3), 0), new Point((int)Math.Round(unchecked(num3 - 10.0)), base.Height));
					}
					graphics2.ResetClip();
				}
				graphics2.DrawRectangle(new Pen(this.color_1, 3f), rect);
			}
			else
			{
				graphics2.FillRectangle(new SolidBrush(this.color_2), rect);
				graphics2.FillRectangle(new SolidBrush(this.color_0), new Rectangle(0, 0, num - 1, base.Height));
				if (this.bool_0)
				{
					graphics2.SetClip(new Rectangle(0, 0, (int)Math.Round(unchecked((double)(checked(base.Width * this.int_0)) / (double)this.int_1 - 1.0)), base.Height - 1));
					double num4 = (double)((base.Width - 1) * this.int_1) / (double)this.int_0;
					for (double num5 = 0.0; num5 <= num4; num5 = unchecked(num5 + 25.0))
					{
						graphics2.DrawLine(new Pen(new SolidBrush(this.color_4), 7f), new Point((int)Math.Round(num5), 0), new Point((int)Math.Round(unchecked(num5 - 10.0)), base.Height));
					}
					graphics2.ResetClip();
				}
				graphics2.DrawRectangle(new Pen(this.color_1, 3f), rect);
			}
			base.OnPaint(e);
			graphics.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}
	}

	// Token: 0x04000376 RID: 886
	private Color color_0;

	// Token: 0x04000377 RID: 887
	private Color color_1;

	// Token: 0x04000378 RID: 888
	private Color color_2;

	// Token: 0x04000379 RID: 889
	private Color color_3;

	// Token: 0x0400037A RID: 890
	private Color color_4;

	// Token: 0x0400037B RID: 891
	private int int_0;

	// Token: 0x0400037C RID: 892
	private int int_1;

	// Token: 0x0400037D RID: 893
	private bool bool_0;
}
