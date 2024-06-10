using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x020000A9 RID: 169
public class GControl37 : TabControl
{
	// Token: 0x17000237 RID: 567
	// (get) Token: 0x060007C3 RID: 1987 RVA: 0x00024804 File Offset: 0x00022A04
	// (set) Token: 0x060007C4 RID: 1988 RVA: 0x0002481C File Offset: 0x00022A1C
	[Category("Colours")]
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

	// Token: 0x17000238 RID: 568
	// (get) Token: 0x060007C5 RID: 1989 RVA: 0x00024828 File Offset: 0x00022A28
	// (set) Token: 0x060007C6 RID: 1990 RVA: 0x00024840 File Offset: 0x00022A40
	[Category("Colours")]
	public Color Color_1
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

	// Token: 0x17000239 RID: 569
	// (get) Token: 0x060007C7 RID: 1991 RVA: 0x0002484C File Offset: 0x00022A4C
	// (set) Token: 0x060007C8 RID: 1992 RVA: 0x00024864 File Offset: 0x00022A64
	[Category("Colours")]
	public Color Color_2
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

	// Token: 0x1700023A RID: 570
	// (get) Token: 0x060007C9 RID: 1993 RVA: 0x00024870 File Offset: 0x00022A70
	// (set) Token: 0x060007CA RID: 1994 RVA: 0x00024888 File Offset: 0x00022A88
	[Category("Colours")]
	public Color Color_3
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

	// Token: 0x1700023B RID: 571
	// (get) Token: 0x060007CB RID: 1995 RVA: 0x00024894 File Offset: 0x00022A94
	// (set) Token: 0x060007CC RID: 1996 RVA: 0x000248AC File Offset: 0x00022AAC
	[Category("Colours")]
	public Color Color_4
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

	// Token: 0x1700023C RID: 572
	// (get) Token: 0x060007CD RID: 1997 RVA: 0x000248B8 File Offset: 0x00022AB8
	// (set) Token: 0x060007CE RID: 1998 RVA: 0x000248D0 File Offset: 0x00022AD0
	[Category("Colours")]
	public Color Color_5
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

	// Token: 0x1700023D RID: 573
	// (get) Token: 0x060007CF RID: 1999 RVA: 0x000248DC File Offset: 0x00022ADC
	// (set) Token: 0x060007D0 RID: 2000 RVA: 0x000248F4 File Offset: 0x00022AF4
	[Category("Colours")]
	public Color Color_6
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

	// Token: 0x060007D1 RID: 2001 RVA: 0x00024900 File Offset: 0x00022B00
	protected virtual void CreateHandle()
	{
		base.CreateHandle();
		base.Alignment = TabAlignment.Top;
	}

	// Token: 0x060007D2 RID: 2002 RVA: 0x00024910 File Offset: 0x00022B10
	public GControl37()
	{
		this.color_0 = Color.FromArgb(255, 255, 255);
		this.color_1 = Color.FromArgb(54, 54, 54);
		this.color_2 = Color.FromArgb(35, 35, 35);
		this.color_3 = Color.FromArgb(47, 47, 47);
		this.color_4 = Color.FromArgb(30, 30, 30);
		this.color_5 = Color.FromArgb(0, 160, 199);
		this.color_6 = Color.FromArgb(23, 119, 151);
		this.stringFormat_0 = new StringFormat
		{
			Alignment = StringAlignment.Center,
			LineAlignment = StringAlignment.Center
		};
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.Font = new Font("Segoe UI", 10f);
		base.SizeMode = TabSizeMode.Normal;
		base.ItemSize = new Size(240, 32);
	}

	// Token: 0x060007D3 RID: 2003 RVA: 0x00024A08 File Offset: 0x00022C08
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Graphics graphics2 = graphics;
		graphics2.SmoothingMode = SmoothingMode.HighQuality;
		graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics2.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		graphics2.Clear(this.color_2);
		try
		{
			base.SelectedTab.BackColor = this.color_1;
		}
		catch (Exception ex)
		{
		}
		try
		{
			base.SelectedTab.BorderStyle = BorderStyle.FixedSingle;
		}
		catch (Exception ex2)
		{
		}
		graphics2.DrawRectangle(new Pen(this.color_4, 2f), new Rectangle(0, 0, base.Width, base.Height));
		checked
		{
			int num = base.TabCount - 1;
			for (int i = 0; i <= num; i++)
			{
				Rectangle rectangle = new Rectangle(new Point(base.GetTabRect(i).Location.X, base.GetTabRect(i).Location.Y), new Size(base.GetTabRect(i).Width, base.GetTabRect(i).Height));
				Rectangle rectangle2 = new Rectangle(rectangle.Location, new Size(rectangle.Width, rectangle.Height));
				if (i != base.SelectedIndex)
				{
					graphics2.DrawString(base.TabPages[i].Text, this.Font, new SolidBrush(this.color_0), rectangle2, this.stringFormat_0);
				}
				else
				{
					graphics2.FillRectangle(new SolidBrush(this.color_2), rectangle2);
					graphics2.FillRectangle(new SolidBrush(this.color_3), new Rectangle(rectangle.X + 1, rectangle.Y - 3, rectangle.Width, rectangle.Height + 5));
					graphics2.DrawString(base.TabPages[i].Text, this.Font, new SolidBrush(this.color_0), new Rectangle(rectangle.X + 7, rectangle.Y, rectangle.Width - 3, rectangle.Height), this.stringFormat_0);
					graphics2.DrawLine(new Pen(this.color_6, 2f), new Point(rectangle.X + 3, (int)Math.Round(unchecked((double)rectangle.Height / 2.0 + 2.0))), new Point(rectangle.X + 9, (int)Math.Round(unchecked((double)rectangle.Height / 2.0 + 2.0))));
					graphics2.DrawLine(new Pen(this.color_5, 2f), new Point(rectangle.X + 3, rectangle.Y - 3), new Point(rectangle.X + 3, rectangle.Height + 5));
				}
			}
			graphics2 = null;
			base.OnPaint(e);
			graphics.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}
	}

	// Token: 0x040003A6 RID: 934
	private Color color_0;

	// Token: 0x040003A7 RID: 935
	private Color color_1;

	// Token: 0x040003A8 RID: 936
	private Color color_2;

	// Token: 0x040003A9 RID: 937
	private Color color_3;

	// Token: 0x040003AA RID: 938
	private Color color_4;

	// Token: 0x040003AB RID: 939
	private Color color_5;

	// Token: 0x040003AC RID: 940
	private Color color_6;

	// Token: 0x040003AD RID: 941
	private StringFormat stringFormat_0;
}
