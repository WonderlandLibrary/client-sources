using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

// Token: 0x02000099 RID: 153
public class GControl29 : Control
{
	// Token: 0x170001E6 RID: 486
	// (get) Token: 0x060006F0 RID: 1776 RVA: 0x000212B4 File Offset: 0x0001F4B4
	// (set) Token: 0x060006F1 RID: 1777 RVA: 0x000212CC File Offset: 0x0001F4CC
	[Category("Control")]
	public float Single_0
	{
		get
		{
			return this.float_0;
		}
		set
		{
			this.float_0 = value;
		}
	}

	// Token: 0x170001E7 RID: 487
	// (get) Token: 0x060006F2 RID: 1778 RVA: 0x000212D8 File Offset: 0x0001F4D8
	// (set) Token: 0x060006F3 RID: 1779 RVA: 0x000212F0 File Offset: 0x0001F4F0
	[Category("Control")]
	public GControl29.GEnum4 GEnum4_0
	{
		get
		{
			return this.genum4_0;
		}
		set
		{
			this.genum4_0 = value;
		}
	}

	// Token: 0x170001E8 RID: 488
	// (get) Token: 0x060006F4 RID: 1780 RVA: 0x000212FC File Offset: 0x0001F4FC
	// (set) Token: 0x060006F5 RID: 1781 RVA: 0x00021314 File Offset: 0x0001F514
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

	// Token: 0x060006F6 RID: 1782 RVA: 0x00021320 File Offset: 0x0001F520
	public GControl29()
	{
		this.color_0 = Color.FromArgb(35, 35, 35);
		this.genum4_0 = GControl29.GEnum4.Horizontal;
		this.float_0 = 1f;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = Color.Transparent;
		base.Size = new Size(20, 20);
	}

	// Token: 0x060006F7 RID: 1783 RVA: 0x00021384 File Offset: 0x0001F584
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		checked
		{
			Rectangle rectangle = new Rectangle(0, 0, base.Width - 1, base.Height - 1);
			Graphics graphics2 = graphics;
			graphics2.SmoothingMode = SmoothingMode.HighQuality;
			graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
			GControl29.GEnum4 genum = this.genum4_0;
			if (genum != GControl29.GEnum4.Horizontal)
			{
				if (genum == GControl29.GEnum4.Verticle)
				{
					graphics2.DrawLine(new Pen(this.color_0, this.float_0), new Point((int)Math.Round((double)base.Width / 2.0), 0), new Point((int)Math.Round((double)base.Width / 2.0), base.Height));
				}
			}
			else
			{
				graphics2.DrawLine(new Pen(this.color_0, this.float_0), new Point(0, (int)Math.Round((double)base.Height / 2.0)), new Point(base.Width, (int)Math.Round((double)base.Height / 2.0)));
			}
			base.OnPaint(e);
			graphics.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}
	}

	// Token: 0x04000351 RID: 849
	private Color color_0;

	// Token: 0x04000352 RID: 850
	private GControl29.GEnum4 genum4_0;

	// Token: 0x04000353 RID: 851
	private float float_0;

	// Token: 0x0200009A RID: 154
	public enum GEnum4
	{
		// Token: 0x04000355 RID: 853
		Horizontal,
		// Token: 0x04000356 RID: 854
		Verticle
	}
}
