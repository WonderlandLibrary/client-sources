using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x0200009B RID: 155
public class GControl30 : Control
{
	// Token: 0x170001E9 RID: 489
	// (get) Token: 0x060006F8 RID: 1784 RVA: 0x000214C8 File Offset: 0x0001F6C8
	// (set) Token: 0x060006F9 RID: 1785 RVA: 0x000214E0 File Offset: 0x0001F6E0
	public long Int64_0
	{
		get
		{
			return this.long_0;
		}
		set
		{
			if (value <= this.long_2 & value >= this.long_1)
			{
				this.long_0 = value;
			}
			base.Invalidate();
		}
	}

	// Token: 0x170001EA RID: 490
	// (get) Token: 0x060006FA RID: 1786 RVA: 0x0002150C File Offset: 0x0001F70C
	// (set) Token: 0x060006FB RID: 1787 RVA: 0x00021524 File Offset: 0x0001F724
	public long Int64_1
	{
		get
		{
			return this.long_2;
		}
		set
		{
			if (value > this.long_1)
			{
				this.long_2 = value;
			}
			if (this.long_0 > this.long_2)
			{
				this.long_0 = this.long_2;
			}
			base.Invalidate();
		}
	}

	// Token: 0x170001EB RID: 491
	// (get) Token: 0x060006FC RID: 1788 RVA: 0x0002155C File Offset: 0x0001F75C
	// (set) Token: 0x060006FD RID: 1789 RVA: 0x00021574 File Offset: 0x0001F774
	public long Int64_2
	{
		get
		{
			return this.long_1;
		}
		set
		{
			if (value < this.long_2)
			{
				this.long_1 = value;
			}
			if (this.long_0 < this.long_1)
			{
				this.long_0 = this.Int64_2;
			}
			base.Invalidate();
		}
	}

	// Token: 0x060006FE RID: 1790 RVA: 0x000215AC File Offset: 0x0001F7AC
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		this.int_0 = e.Location.X;
		this.int_1 = e.Location.Y;
		base.Invalidate();
		if (e.X >= checked(base.Width - 47))
		{
			this.Cursor = Cursors.Hand;
		}
		else
		{
			this.Cursor = Cursors.IBeam;
		}
	}

	// Token: 0x060006FF RID: 1791 RVA: 0x0002161C File Offset: 0x0001F81C
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		checked
		{
			if (this.int_0 > base.Width - 47 && this.int_0 < base.Width - 3)
			{
				if (this.int_0 >= base.Width - 23)
				{
					if (this.Int64_0 - 1L >= this.long_1)
					{
						ref long ptr = ref this.long_0;
						this.long_0 = ptr - 1L;
					}
				}
				else if (this.Int64_0 + 1L <= this.long_2)
				{
					ref long ptr = ref this.long_0;
					this.long_0 = ptr + 1L;
				}
			}
			else
			{
				this.bool_0 = !this.bool_0;
				base.Focus();
			}
			base.Invalidate();
		}
	}

	// Token: 0x06000700 RID: 1792 RVA: 0x000216F0 File Offset: 0x0001F8F0
	protected virtual void OnKeyPress(KeyPressEventArgs e)
	{
		base.OnKeyPress(e);
		try
		{
			if (this.bool_0)
			{
				this.long_0 = Conversions.ToLong(Conversions.ToString(this.long_0) + e.KeyChar.ToString());
			}
			if (this.long_0 > this.long_2)
			{
				this.long_0 = this.long_2;
			}
			base.Invalidate();
		}
		catch (Exception ex)
		{
		}
	}

	// Token: 0x06000701 RID: 1793 RVA: 0x00021778 File Offset: 0x0001F978
	protected virtual void OnKeyDown(KeyEventArgs e)
	{
		base.OnKeyDown(e);
		if (e.KeyCode == Keys.Back)
		{
			this.Int64_0 = 0L;
		}
	}

	// Token: 0x06000702 RID: 1794 RVA: 0x0002179C File Offset: 0x0001F99C
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Height = 24;
	}

	// Token: 0x170001EC RID: 492
	// (get) Token: 0x06000703 RID: 1795 RVA: 0x000217B0 File Offset: 0x0001F9B0
	// (set) Token: 0x06000704 RID: 1796 RVA: 0x000217C8 File Offset: 0x0001F9C8
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

	// Token: 0x170001ED RID: 493
	// (get) Token: 0x06000705 RID: 1797 RVA: 0x000217D4 File Offset: 0x0001F9D4
	// (set) Token: 0x06000706 RID: 1798 RVA: 0x000217EC File Offset: 0x0001F9EC
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

	// Token: 0x170001EE RID: 494
	// (get) Token: 0x06000707 RID: 1799 RVA: 0x000217F8 File Offset: 0x0001F9F8
	// (set) Token: 0x06000708 RID: 1800 RVA: 0x00021810 File Offset: 0x0001FA10
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

	// Token: 0x170001EF RID: 495
	// (get) Token: 0x06000709 RID: 1801 RVA: 0x0002181C File Offset: 0x0001FA1C
	// (set) Token: 0x0600070A RID: 1802 RVA: 0x00021834 File Offset: 0x0001FA34
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

	// Token: 0x170001F0 RID: 496
	// (get) Token: 0x0600070B RID: 1803 RVA: 0x00021840 File Offset: 0x0001FA40
	// (set) Token: 0x0600070C RID: 1804 RVA: 0x00021858 File Offset: 0x0001FA58
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

	// Token: 0x0600070D RID: 1805 RVA: 0x00021864 File Offset: 0x0001FA64
	public GControl30()
	{
		this.enum0_0 = Enum0.None;
		this.long_1 = 0L;
		this.long_2 = 9999999L;
		this.color_0 = Color.FromArgb(42, 42, 42);
		this.color_1 = Color.FromArgb(47, 47, 47);
		this.color_2 = Color.FromArgb(35, 35, 35);
		this.color_3 = Color.FromArgb(0, 191, 255);
		this.color_4 = Color.FromArgb(255, 255, 255);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.Font = new Font("Segoe UI", 10f);
	}

	// Token: 0x0600070E RID: 1806 RVA: 0x00021928 File Offset: 0x0001FB28
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Rectangle rect = new Rectangle(0, 0, base.Width, base.Height);
		StringFormat stringFormat = new StringFormat();
		stringFormat.LineAlignment = StringAlignment.Center;
		stringFormat.Alignment = StringAlignment.Center;
		Graphics graphics2 = graphics;
		graphics2.SmoothingMode = SmoothingMode.HighQuality;
		graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics2.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		graphics2.Clear(this.BackColor);
		graphics2.FillRectangle(new SolidBrush(this.color_0), rect);
		checked
		{
			graphics2.FillRectangle(new SolidBrush(this.color_1), new Rectangle(base.Width - 48, 0, 48, base.Height));
			graphics2.DrawRectangle(new Pen(this.color_2, 2f), rect);
			graphics2.DrawLine(new Pen(this.color_3), new Point(base.Width - 48, 1), new Point(base.Width - 48, base.Height - 2));
			graphics2.DrawLine(new Pen(this.color_2), new Point(base.Width - 24, 1), new Point(base.Width - 24, base.Height - 2));
			graphics2.DrawLine(new Pen(this.color_4), new Point(base.Width - 36, 7), new Point(base.Width - 36, 17));
			graphics2.DrawLine(new Pen(this.color_4), new Point(base.Width - 31, 12), new Point(base.Width - 41, 12));
			graphics2.DrawLine(new Pen(this.color_4), new Point(base.Width - 17, 13), new Point(base.Width - 7, 13));
			graphics2.DrawString(Conversions.ToString(this.Int64_0), this.Font, new SolidBrush(this.color_4), new Rectangle(5, 1, base.Width, base.Height), new StringFormat
			{
				LineAlignment = StringAlignment.Center
			});
			base.OnPaint(e);
			graphics.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}
	}

	// Token: 0x04000357 RID: 855
	private Enum0 enum0_0;

	// Token: 0x04000358 RID: 856
	private int int_0;

	// Token: 0x04000359 RID: 857
	private int int_1;

	// Token: 0x0400035A RID: 858
	private long long_0;

	// Token: 0x0400035B RID: 859
	private long long_1;

	// Token: 0x0400035C RID: 860
	private long long_2;

	// Token: 0x0400035D RID: 861
	private bool bool_0;

	// Token: 0x0400035E RID: 862
	private Color color_0;

	// Token: 0x0400035F RID: 863
	private Color color_1;

	// Token: 0x04000360 RID: 864
	private Color color_2;

	// Token: 0x04000361 RID: 865
	private Color color_3;

	// Token: 0x04000362 RID: 866
	private Color color_4;
}
