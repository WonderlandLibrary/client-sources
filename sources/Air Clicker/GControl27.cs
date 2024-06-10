using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x02000097 RID: 151
public class GControl27 : Control
{
	// Token: 0x060006CF RID: 1743 RVA: 0x00020880 File Offset: 0x0001EA80
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		this.enum0_0 = Enum0.Down;
		base.Invalidate();
	}

	// Token: 0x060006D0 RID: 1744 RVA: 0x00020898 File Offset: 0x0001EA98
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.OnMouseUp(e);
		this.enum0_0 = Enum0.Over;
		base.Invalidate();
	}

	// Token: 0x060006D1 RID: 1745 RVA: 0x000208B0 File Offset: 0x0001EAB0
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum0_0 = Enum0.Over;
		base.Invalidate();
	}

	// Token: 0x060006D2 RID: 1746 RVA: 0x000208C8 File Offset: 0x0001EAC8
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum0_0 = Enum0.None;
		base.Invalidate();
	}

	// Token: 0x170001DA RID: 474
	// (get) Token: 0x060006D3 RID: 1747 RVA: 0x000208E0 File Offset: 0x0001EAE0
	// (set) Token: 0x060006D4 RID: 1748 RVA: 0x000208F8 File Offset: 0x0001EAF8
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

	// Token: 0x170001DB RID: 475
	// (get) Token: 0x060006D5 RID: 1749 RVA: 0x00020904 File Offset: 0x0001EB04
	// (set) Token: 0x060006D6 RID: 1750 RVA: 0x0002091C File Offset: 0x0001EB1C
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

	// Token: 0x170001DC RID: 476
	// (get) Token: 0x060006D7 RID: 1751 RVA: 0x00020928 File Offset: 0x0001EB28
	// (set) Token: 0x060006D8 RID: 1752 RVA: 0x00020940 File Offset: 0x0001EB40
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

	// Token: 0x170001DD RID: 477
	// (get) Token: 0x060006D9 RID: 1753 RVA: 0x0002094C File Offset: 0x0001EB4C
	// (set) Token: 0x060006DA RID: 1754 RVA: 0x00020964 File Offset: 0x0001EB64
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

	// Token: 0x170001DE RID: 478
	// (get) Token: 0x060006DB RID: 1755 RVA: 0x00020970 File Offset: 0x0001EB70
	// (set) Token: 0x060006DC RID: 1756 RVA: 0x00020988 File Offset: 0x0001EB88
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

	// Token: 0x170001DF RID: 479
	// (get) Token: 0x060006DD RID: 1757 RVA: 0x00020994 File Offset: 0x0001EB94
	// (set) Token: 0x060006DE RID: 1758 RVA: 0x000209AC File Offset: 0x0001EBAC
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

	// Token: 0x170001E0 RID: 480
	// (get) Token: 0x060006DF RID: 1759 RVA: 0x000209B8 File Offset: 0x0001EBB8
	// (set) Token: 0x060006E0 RID: 1760 RVA: 0x000209D0 File Offset: 0x0001EBD0
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

	// Token: 0x170001E1 RID: 481
	// (get) Token: 0x060006E1 RID: 1761 RVA: 0x00020A00 File Offset: 0x0001EC00
	// (set) Token: 0x060006E2 RID: 1762 RVA: 0x00020A24 File Offset: 0x0001EC24
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

	// Token: 0x060006E3 RID: 1763 RVA: 0x00020A5C File Offset: 0x0001EC5C
	public void method_0(int int_2)
	{
		checked
		{
			this.Int32_1 += int_2;
		}
	}

	// Token: 0x060006E4 RID: 1764 RVA: 0x00020A6C File Offset: 0x0001EC6C
	public GControl27()
	{
		this.int_0 = 0;
		this.int_1 = 100;
		this.font_0 = new Font("Segoe UI", 9f);
		this.color_0 = Color.FromArgb(0, 191, 255);
		this.color_1 = Color.FromArgb(25, 25, 25);
		this.color_2 = Color.FromArgb(255, 255, 255);
		this.color_3 = Color.FromArgb(42, 42, 42);
		this.color_4 = Color.FromArgb(52, 52, 52);
		this.color_5 = Color.FromArgb(47, 47, 47);
		this.enum0_0 = Enum0.None;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		base.Size = new Size(75, 30);
		this.BackColor = Color.Transparent;
	}

	// Token: 0x060006E5 RID: 1765 RVA: 0x00020B50 File Offset: 0x0001ED50
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		new GraphicsPath();
		new GraphicsPath();
		Graphics graphics2 = graphics;
		graphics2.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		graphics2.SmoothingMode = SmoothingMode.HighQuality;
		graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics2.Clear(this.BackColor);
		checked
		{
			switch (this.enum0_0)
			{
			case Enum0.None:
				graphics2.FillRectangle(new SolidBrush(this.color_3), new Rectangle(0, 0, base.Width, base.Height - 4));
				graphics2.DrawRectangle(new Pen(this.color_1, 2f), new Rectangle(0, 0, base.Width, base.Height - 4));
				graphics2.DrawString(this.Text, this.font_0, Brushes.White, new Point((int)Math.Round((double)base.Width / 2.0), (int)Math.Round(unchecked((double)base.Height / 2.0 - 2.0))), new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				break;
			case Enum0.Over:
				graphics2.FillRectangle(new SolidBrush(this.color_4), new Rectangle(0, 0, base.Width, base.Height - 4));
				graphics2.DrawRectangle(new Pen(this.color_1, 1f), new Rectangle(1, 1, base.Width - 2, base.Height - 5));
				graphics2.DrawString(this.Text, this.font_0, Brushes.White, new Point((int)Math.Round((double)base.Width / 2.0), (int)Math.Round(unchecked((double)base.Height / 2.0 - 2.0))), new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				break;
			case Enum0.Down:
				graphics2.FillRectangle(new SolidBrush(this.color_5), new Rectangle(0, 0, base.Width, base.Height - 4));
				graphics2.DrawRectangle(new Pen(this.color_1, 1f), new Rectangle(1, 1, base.Width - 2, base.Height - 5));
				graphics2.DrawString(this.Text, this.font_0, Brushes.White, new Point((int)Math.Round((double)base.Width / 2.0), (int)Math.Round(unchecked((double)base.Height / 2.0 - 2.0))), new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				break;
			}
			int num = this.int_0;
			if (num != 0)
			{
				if (num != this.int_1)
				{
					graphics2.FillRectangle(new SolidBrush(this.color_0), new Rectangle(0, base.Height - 4, (int)Math.Round(unchecked((double)base.Width / (double)this.int_1 * (double)this.int_0)), base.Height - 4));
					graphics2.DrawRectangle(new Pen(this.color_1, 2f), new Rectangle(0, 0, base.Width, base.Height));
				}
				else
				{
					graphics2.FillRectangle(new SolidBrush(this.color_0), new Rectangle(0, base.Height - 4, base.Width, base.Height - 4));
					graphics2.DrawRectangle(new Pen(this.color_1, 2f), new Rectangle(0, 0, base.Width, base.Height));
				}
			}
			base.OnPaint(e);
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}
	}

	// Token: 0x04000343 RID: 835
	private int int_0;

	// Token: 0x04000344 RID: 836
	private int int_1;

	// Token: 0x04000345 RID: 837
	private Font font_0;

	// Token: 0x04000346 RID: 838
	private Color color_0;

	// Token: 0x04000347 RID: 839
	private Color color_1;

	// Token: 0x04000348 RID: 840
	private Color color_2;

	// Token: 0x04000349 RID: 841
	private Color color_3;

	// Token: 0x0400034A RID: 842
	private Color color_4;

	// Token: 0x0400034B RID: 843
	private Color color_5;

	// Token: 0x0400034C RID: 844
	private Enum0 enum0_0;
}
