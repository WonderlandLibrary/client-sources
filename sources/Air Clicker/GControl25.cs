using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x02000093 RID: 147
public class GControl25 : Control
{
	// Token: 0x170001CC RID: 460
	// (get) Token: 0x0600069E RID: 1694 RVA: 0x0001FCBC File Offset: 0x0001DEBC
	// (set) Token: 0x0600069F RID: 1695 RVA: 0x0001FCD4 File Offset: 0x0001DED4
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

	// Token: 0x170001CD RID: 461
	// (get) Token: 0x060006A0 RID: 1696 RVA: 0x0001FD04 File Offset: 0x0001DF04
	// (set) Token: 0x060006A1 RID: 1697 RVA: 0x0001FD28 File Offset: 0x0001DF28
	[Category("Control")]
	public int Int32_1
	{
		get
		{
			int result;
			if (this.int_0 == 0)
			{
				result = 0;
			}
			else
			{
				result = this.int_0;
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

	// Token: 0x060006A2 RID: 1698 RVA: 0x0001FD60 File Offset: 0x0001DF60
	public void method_0(int int_4)
	{
		checked
		{
			this.Int32_1 += int_4;
		}
	}

	// Token: 0x170001CE RID: 462
	// (get) Token: 0x060006A3 RID: 1699 RVA: 0x0001FD70 File Offset: 0x0001DF70
	// (set) Token: 0x060006A4 RID: 1700 RVA: 0x0001FD88 File Offset: 0x0001DF88
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

	// Token: 0x170001CF RID: 463
	// (get) Token: 0x060006A5 RID: 1701 RVA: 0x0001FD94 File Offset: 0x0001DF94
	// (set) Token: 0x060006A6 RID: 1702 RVA: 0x0001FDAC File Offset: 0x0001DFAC
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

	// Token: 0x170001D0 RID: 464
	// (get) Token: 0x060006A7 RID: 1703 RVA: 0x0001FDB8 File Offset: 0x0001DFB8
	// (set) Token: 0x060006A8 RID: 1704 RVA: 0x0001FDD0 File Offset: 0x0001DFD0
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

	// Token: 0x170001D1 RID: 465
	// (get) Token: 0x060006A9 RID: 1705 RVA: 0x0001FDDC File Offset: 0x0001DFDC
	// (set) Token: 0x060006AA RID: 1706 RVA: 0x0001FDF4 File Offset: 0x0001DFF4
	[Category("Control")]
	public int Int32_2
	{
		get
		{
			return this.int_2;
		}
		set
		{
			this.int_2 = value;
		}
	}

	// Token: 0x170001D2 RID: 466
	// (get) Token: 0x060006AB RID: 1707 RVA: 0x0001FE00 File Offset: 0x0001E000
	// (set) Token: 0x060006AC RID: 1708 RVA: 0x0001FE18 File Offset: 0x0001E018
	[Category("Control")]
	public int Int32_3
	{
		get
		{
			return this.int_3;
		}
		set
		{
			this.int_3 = value;
		}
	}

	// Token: 0x060006AD RID: 1709 RVA: 0x0001FE24 File Offset: 0x0001E024
	public GControl25()
	{
		this.color_0 = Color.FromArgb(35, 35, 35);
		this.color_1 = Color.FromArgb(42, 42, 42);
		this.color_2 = Color.FromArgb(0, 160, 199);
		this.int_0 = 0;
		this.int_1 = 100;
		this.int_2 = 110;
		this.int_3 = 255;
		this.font_0 = new Font("Segoe UI", 20f);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		base.Size = new Size(78, 78);
		this.BackColor = Color.Transparent;
	}

	// Token: 0x060006AE RID: 1710 RVA: 0x0001FED4 File Offset: 0x0001E0D4
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Graphics graphics2 = graphics;
		graphics2.TextRenderingHint = TextRenderingHint.AntiAliasGridFit;
		graphics2.SmoothingMode = SmoothingMode.HighQuality;
		graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics2.Clear(this.BackColor);
		int num = this.int_0;
		checked
		{
			if (num != 0)
			{
				if (num != this.int_1)
				{
					graphics2.DrawArc(new Pen(new SolidBrush(this.color_0), 6f), 3, 3, base.Width - 3 - 4, base.Height - 3 - 3, this.int_2 - 3, this.int_3 + 5);
					graphics2.DrawArc(new Pen(new SolidBrush(this.color_1), 4f), 3, 3, base.Width - 3 - 4, base.Height - 3 - 3, this.int_2, this.int_3);
					graphics2.DrawArc(new Pen(new SolidBrush(this.color_2), 4f), 3, 3, base.Width - 3 - 4, base.Height - 3 - 3, this.int_2, (int)Math.Round(unchecked((double)this.int_3 / (double)this.int_1 * (double)this.int_0)));
					graphics2.DrawString(Conversions.ToString(this.int_0), this.font_0, Brushes.White, new Point((int)Math.Round((double)base.Width / 2.0), (int)Math.Round(unchecked((double)base.Height / 2.0 - 1.0))), new StringFormat
					{
						Alignment = StringAlignment.Center,
						LineAlignment = StringAlignment.Center
					});
				}
				else
				{
					graphics2.DrawArc(new Pen(new SolidBrush(this.color_0), 6f), 3, 3, base.Width - 3 - 4, base.Height - 3 - 3, this.int_2 - 3, this.int_3 + 5);
					graphics2.DrawArc(new Pen(new SolidBrush(this.color_1), 4f), 3, 3, base.Width - 3 - 4, base.Height - 3 - 3, this.int_2, this.int_3);
					graphics2.DrawArc(new Pen(new SolidBrush(this.color_2), 4f), 3, 3, base.Width - 3 - 4, base.Height - 3 - 3, this.int_2, this.int_3);
					graphics2.DrawString(Conversions.ToString(this.int_0), this.font_0, Brushes.White, new Point((int)Math.Round((double)base.Width / 2.0), (int)Math.Round(unchecked((double)base.Height / 2.0 - 1.0))), new StringFormat
					{
						Alignment = StringAlignment.Center,
						LineAlignment = StringAlignment.Center
					});
				}
			}
			else
			{
				graphics2.DrawArc(new Pen(new SolidBrush(this.color_0), 6f), 3, 3, base.Width - 3 - 4, base.Height - 3 - 3, this.int_2 - 3, this.int_3 + 5);
				graphics2.DrawArc(new Pen(new SolidBrush(this.color_1), 4f), 3, 3, base.Width - 3 - 4, base.Height - 3 - 3, this.int_2, this.int_3);
				graphics2.DrawString(Conversions.ToString(this.int_0), this.font_0, Brushes.White, new Point((int)Math.Round((double)base.Width / 2.0), (int)Math.Round(unchecked((double)base.Height / 2.0 - 1.0))), new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
			}
			base.OnPaint(e);
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}
	}

	// Token: 0x04000332 RID: 818
	private Color color_0;

	// Token: 0x04000333 RID: 819
	private Color color_1;

	// Token: 0x04000334 RID: 820
	private Color color_2;

	// Token: 0x04000335 RID: 821
	private int int_0;

	// Token: 0x04000336 RID: 822
	private int int_1;

	// Token: 0x04000337 RID: 823
	private int int_2;

	// Token: 0x04000338 RID: 824
	private int int_3;

	// Token: 0x04000339 RID: 825
	private Font font_0;
}
