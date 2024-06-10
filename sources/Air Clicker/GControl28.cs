using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x02000098 RID: 152
public class GControl28 : ContainerControl
{
	// Token: 0x170001E2 RID: 482
	// (get) Token: 0x060006E6 RID: 1766 RVA: 0x00020F14 File Offset: 0x0001F114
	// (set) Token: 0x060006E7 RID: 1767 RVA: 0x00020F2C File Offset: 0x0001F12C
	[Category("Colours")]
	public Color Color_0
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

	// Token: 0x170001E3 RID: 483
	// (get) Token: 0x060006E8 RID: 1768 RVA: 0x00020F38 File Offset: 0x0001F138
	// (set) Token: 0x060006E9 RID: 1769 RVA: 0x00020F50 File Offset: 0x0001F150
	[Category("Colours")]
	public Color Color_1
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

	// Token: 0x170001E4 RID: 484
	// (get) Token: 0x060006EA RID: 1770 RVA: 0x00020F5C File Offset: 0x0001F15C
	// (set) Token: 0x060006EB RID: 1771 RVA: 0x00020F74 File Offset: 0x0001F174
	[Category("Colours")]
	public Color Color_2
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

	// Token: 0x170001E5 RID: 485
	// (get) Token: 0x060006EC RID: 1772 RVA: 0x00020F80 File Offset: 0x0001F180
	// (set) Token: 0x060006ED RID: 1773 RVA: 0x00020F98 File Offset: 0x0001F198
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

	// Token: 0x060006EE RID: 1774 RVA: 0x00020FA4 File Offset: 0x0001F1A4
	public GControl28()
	{
		this.color_0 = Color.FromArgb(47, 47, 47);
		this.color_1 = Color.FromArgb(42, 42, 42);
		this.color_2 = Color.FromArgb(255, 255, 255);
		this.color_3 = Color.FromArgb(35, 35, 35);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		base.Size = new Size(160, 110);
		this.Font = new Font("Segoe UI", 10f, FontStyle.Bold);
	}

	// Token: 0x060006EF RID: 1775 RVA: 0x00021040 File Offset: 0x0001F240
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		checked
		{
			Rectangle rectangle = new Rectangle(0, 0, base.Width - 1, base.Height - 1);
			Rectangle rectangle2 = new Rectangle(8, 8, 10, 10);
			Graphics graphics2 = graphics;
			graphics2.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics2.SmoothingMode = SmoothingMode.HighQuality;
			graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics2.Clear(this.BackColor);
			graphics2.FillRectangle(new SolidBrush(this.color_0), new Rectangle(0, 28, base.Width, base.Height));
			graphics2.FillRectangle(new SolidBrush(this.color_1), new Rectangle(0, 0, (int)Math.Round((double)(unchecked(graphics2.MeasureString(this.Text, this.Font).Width + 7f))), 28));
			graphics2.DrawString(this.Text, this.Font, new SolidBrush(this.color_2), new Point(5, 5));
			Point[] points = new Point[]
			{
				new Point(0, 0),
				new Point((int)Math.Round((double)(unchecked(graphics2.MeasureString(this.Text, this.Font).Width + 7f))), 0),
				new Point((int)Math.Round((double)(unchecked(graphics2.MeasureString(this.Text, this.Font).Width + 7f))), 28),
				new Point(base.Width - 1, 28),
				new Point(base.Width - 1, base.Height - 1),
				new Point(1, base.Height - 1),
				new Point(1, 1)
			};
			graphics2.DrawLines(new Pen(this.color_3), points);
			graphics2.DrawLine(new Pen(this.color_3, 2f), new Point(0, 28), new Point((int)Math.Round((double)(unchecked(graphics2.MeasureString(this.Text, this.Font).Width + 7f))), 28));
			base.OnPaint(e);
			graphics.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}
	}

	// Token: 0x0400034D RID: 845
	private Color color_0;

	// Token: 0x0400034E RID: 846
	private Color color_1;

	// Token: 0x0400034F RID: 847
	private Color color_2;

	// Token: 0x04000350 RID: 848
	private Color color_3;
}
