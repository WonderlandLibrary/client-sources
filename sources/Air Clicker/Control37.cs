using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x02000051 RID: 81
internal class Control37 : Control
{
	// Token: 0x1700014A RID: 330
	// (get) Token: 0x06000447 RID: 1095 RVA: 0x00014EA4 File Offset: 0x000130A4
	// (set) Token: 0x06000448 RID: 1096 RVA: 0x00014EBC File Offset: 0x000130BC
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

	// Token: 0x1700014B RID: 331
	// (get) Token: 0x06000449 RID: 1097 RVA: 0x00014EE8 File Offset: 0x000130E8
	// (set) Token: 0x0600044A RID: 1098 RVA: 0x00014F00 File Offset: 0x00013100
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

	// Token: 0x1700014C RID: 332
	// (get) Token: 0x0600044B RID: 1099 RVA: 0x00014F38 File Offset: 0x00013138
	// (set) Token: 0x0600044C RID: 1100 RVA: 0x00014F50 File Offset: 0x00013150
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

	// Token: 0x0600044D RID: 1101 RVA: 0x00014F88 File Offset: 0x00013188
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		this.int_2 = e.Location.X;
		this.int_3 = e.Location.Y;
		base.Invalidate();
		if (e.X < checked(base.Width - 23))
		{
			this.Cursor = Cursors.IBeam;
		}
		else
		{
			this.Cursor = Cursors.Hand;
		}
	}

	// Token: 0x0600044E RID: 1102 RVA: 0x00014FF8 File Offset: 0x000131F8
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		checked
		{
			if (this.int_2 > base.Width - 21 && this.int_2 < base.Width - 3)
			{
				if (this.int_3 >= 15)
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

	// Token: 0x0600044F RID: 1103 RVA: 0x000150C4 File Offset: 0x000132C4
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

	// Token: 0x06000450 RID: 1104 RVA: 0x0001514C File Offset: 0x0001334C
	protected virtual void OnKeyDown(KeyEventArgs e)
	{
		base.OnKeyDown(e);
		if (e.KeyCode == Keys.Back)
		{
			this.Int64_0 = 0L;
		}
	}

	// Token: 0x06000451 RID: 1105 RVA: 0x00015170 File Offset: 0x00013370
	protected virtual void OnResize(EventArgs e)
	{
		base.OnResize(e);
		base.Height = 30;
	}

	// Token: 0x1700014D RID: 333
	// (get) Token: 0x06000452 RID: 1106 RVA: 0x00015184 File Offset: 0x00013384
	// (set) Token: 0x06000453 RID: 1107 RVA: 0x0001519C File Offset: 0x0001339C
	[Category("Colors")]
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

	// Token: 0x1700014E RID: 334
	// (get) Token: 0x06000454 RID: 1108 RVA: 0x000151A8 File Offset: 0x000133A8
	// (set) Token: 0x06000455 RID: 1109 RVA: 0x000151C0 File Offset: 0x000133C0
	[Category("Colors")]
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

	// Token: 0x06000456 RID: 1110 RVA: 0x000151CC File Offset: 0x000133CC
	public Control37()
	{
		this.enum2_0 = Enum2.None;
		this.color_0 = Color.FromArgb(45, 47, 49);
		this.color_1 = Class16.color_0;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.Font = new Font("Segoe UI", 10f);
		this.BackColor = Color.FromArgb(60, 70, 73);
		this.ForeColor = Color.White;
		this.long_1 = 0L;
		this.long_2 = 9999999L;
	}

	// Token: 0x06000457 RID: 1111 RVA: 0x00015264 File Offset: 0x00013464
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class16.bitmap_0 = new Bitmap(base.Width, base.Height);
		Class16.graphics_0 = Graphics.FromImage(Class16.bitmap_0);
		this.int_0 = base.Width;
		this.int_1 = base.Height;
		Rectangle rect = new Rectangle(0, 0, this.int_0, this.int_1);
		Graphics graphics_ = Class16.graphics_0;
		graphics_.SmoothingMode = SmoothingMode.HighQuality;
		graphics_.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics_.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		graphics_.Clear(this.BackColor);
		graphics_.FillRectangle(new SolidBrush(this.color_0), rect);
		checked
		{
			graphics_.FillRectangle(new SolidBrush(this.color_1), new Rectangle(base.Width - 24, 0, 24, this.int_1));
			graphics_.DrawString("+", new Font("Segoe UI", 12f), Brushes.White, new Point(base.Width - 12, 8), Class16.stringFormat_1);
			graphics_.DrawString("-", new Font("Segoe UI", 10f, FontStyle.Bold), Brushes.White, new Point(base.Width - 12, 22), Class16.stringFormat_1);
			graphics_.DrawString(Conversions.ToString(this.Int64_0), this.Font, Brushes.White, new Rectangle(5, 1, this.int_0, this.int_1), new StringFormat
			{
				LineAlignment = StringAlignment.Center
			});
			base.OnPaint(e);
			Class16.graphics_0.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Class16.bitmap_0, 0, 0);
			Class16.bitmap_0.Dispose();
		}
	}

	// Token: 0x04000248 RID: 584
	private int int_0;

	// Token: 0x04000249 RID: 585
	private int int_1;

	// Token: 0x0400024A RID: 586
	private Enum2 enum2_0;

	// Token: 0x0400024B RID: 587
	private int int_2;

	// Token: 0x0400024C RID: 588
	private int int_3;

	// Token: 0x0400024D RID: 589
	private long long_0;

	// Token: 0x0400024E RID: 590
	private long long_1;

	// Token: 0x0400024F RID: 591
	private long long_2;

	// Token: 0x04000250 RID: 592
	private bool bool_0;

	// Token: 0x04000251 RID: 593
	private Color color_0;

	// Token: 0x04000252 RID: 594
	private Color color_1;
}
