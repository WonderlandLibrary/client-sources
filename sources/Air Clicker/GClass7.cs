using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

// Token: 0x020000A8 RID: 168
public class GClass7 : ComboBox
{
	// Token: 0x1700022F RID: 559
	// (get) Token: 0x060007AA RID: 1962 RVA: 0x0002410C File Offset: 0x0002230C
	// (set) Token: 0x060007AB RID: 1963 RVA: 0x00024124 File Offset: 0x00022324
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

	// Token: 0x17000230 RID: 560
	// (get) Token: 0x060007AC RID: 1964 RVA: 0x00024130 File Offset: 0x00022330
	// (set) Token: 0x060007AD RID: 1965 RVA: 0x00024148 File Offset: 0x00022348
	[Category("Colours")]
	public Color Color_1
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

	// Token: 0x17000231 RID: 561
	// (get) Token: 0x060007AE RID: 1966 RVA: 0x00024154 File Offset: 0x00022354
	// (set) Token: 0x060007AF RID: 1967 RVA: 0x0002416C File Offset: 0x0002236C
	[Category("Colours")]
	public Color Color_2
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

	// Token: 0x17000232 RID: 562
	// (get) Token: 0x060007B0 RID: 1968 RVA: 0x00024178 File Offset: 0x00022378
	// (set) Token: 0x060007B1 RID: 1969 RVA: 0x00024190 File Offset: 0x00022390
	[Category("Colours")]
	public Color Color_3
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

	// Token: 0x060007B2 RID: 1970 RVA: 0x0002419C File Offset: 0x0002239C
	protected virtual void OnMouseEnter(EventArgs e)
	{
		base.OnMouseEnter(e);
		this.enum0_0 = Enum0.Over;
		base.Invalidate();
	}

	// Token: 0x060007B3 RID: 1971 RVA: 0x000241B4 File Offset: 0x000223B4
	protected virtual void OnMouseLeave(EventArgs e)
	{
		base.OnMouseLeave(e);
		this.enum0_0 = Enum0.None;
		base.Invalidate();
	}

	// Token: 0x17000233 RID: 563
	// (get) Token: 0x060007B4 RID: 1972 RVA: 0x000241CC File Offset: 0x000223CC
	// (set) Token: 0x060007B5 RID: 1973 RVA: 0x000241E4 File Offset: 0x000223E4
	[Category("Colours")]
	public Color Color_4
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

	// Token: 0x17000234 RID: 564
	// (get) Token: 0x060007B6 RID: 1974 RVA: 0x000241F0 File Offset: 0x000223F0
	// (set) Token: 0x060007B7 RID: 1975 RVA: 0x00024208 File Offset: 0x00022408
	[Category("Colours")]
	public Color Color_5
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

	// Token: 0x17000235 RID: 565
	// (get) Token: 0x060007B8 RID: 1976 RVA: 0x00024214 File Offset: 0x00022414
	// (set) Token: 0x060007B9 RID: 1977 RVA: 0x0002422C File Offset: 0x0002242C
	[Category("Colours")]
	public Color Color_6
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

	// Token: 0x17000236 RID: 566
	// (get) Token: 0x060007BA RID: 1978 RVA: 0x00024238 File Offset: 0x00022438
	// (set) Token: 0x060007BB RID: 1979 RVA: 0x00024250 File Offset: 0x00022450
	public int Int32_0
	{
		get
		{
			return this.int_0;
		}
		set
		{
			this.int_0 = value;
			try
			{
				this.method_0(value);
			}
			catch (Exception ex)
			{
			}
			base.Invalidate();
		}
	}

	// Token: 0x060007BC RID: 1980 RVA: 0x00024290 File Offset: 0x00022490
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		base.Invalidate();
	}

	// Token: 0x060007BD RID: 1981 RVA: 0x000242A0 File Offset: 0x000224A0
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.Invalidate();
		base.OnMouseClick(e);
	}

	// Token: 0x060007BE RID: 1982 RVA: 0x000242B0 File Offset: 0x000224B0
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		base.Invalidate();
		base.OnMouseUp(e);
	}

	// Token: 0x060007BF RID: 1983 RVA: 0x000242C0 File Offset: 0x000224C0
	public void GClass7_DrawItem(object sender, DrawItemEventArgs e)
	{
		e.DrawBackground();
		e.Graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		checked
		{
			Rectangle rect = new Rectangle(e.Bounds.X, e.Bounds.Y, e.Bounds.Width + 1, e.Bounds.Height + 1);
			try
			{
				Graphics graphics = e.Graphics;
				if ((e.State & DrawItemState.Selected) != DrawItemState.Selected)
				{
					graphics.FillRectangle(new SolidBrush(this.color_1), rect);
					graphics.DrawString(base.GetItemText(RuntimeHelpers.GetObjectValue(base.Items[e.Index])), this.Font, new SolidBrush(this.color_2), 1f, (float)(e.Bounds.Top + 2));
				}
				else
				{
					graphics.FillRectangle(new SolidBrush(this.color_4), rect);
					graphics.DrawString(base.GetItemText(RuntimeHelpers.GetObjectValue(base.Items[e.Index])), this.Font, new SolidBrush(this.color_2), 1f, (float)(e.Bounds.Top + 2));
				}
			}
			catch (Exception ex)
			{
			}
			e.DrawFocusRectangle();
			base.Invalidate();
		}
	}

	// Token: 0x060007C0 RID: 1984 RVA: 0x0002441C File Offset: 0x0002261C
	public GClass7()
	{
		base.DrawItem += this.GClass7_DrawItem;
		this.int_0 = 0;
		this.color_0 = Color.FromArgb(35, 35, 35);
		this.color_1 = Color.FromArgb(42, 42, 42);
		this.color_2 = Color.FromArgb(255, 255, 255);
		this.color_3 = Color.FromArgb(23, 119, 151);
		this.color_4 = Color.FromArgb(47, 47, 47);
		this.color_5 = Color.FromArgb(30, 30, 30);
		this.color_6 = Color.FromArgb(52, 52, 52);
		this.enum0_0 = Enum0.None;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = Color.Transparent;
		base.DrawMode = DrawMode.OwnerDrawFixed;
		base.DropDownStyle = ComboBoxStyle.DropDownList;
		base.Width = 163;
		this.Font = new Font("Segoe UI", 10f);
	}

	// Token: 0x060007C1 RID: 1985 RVA: 0x00024520 File Offset: 0x00022720
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Graphics graphics2 = graphics;
		graphics2.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		graphics2.SmoothingMode = SmoothingMode.HighQuality;
		graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics2.Clear(this.BackColor);
		checked
		{
			try
			{
				Rectangle rect = new Rectangle(base.Width - 25, 0, base.Width, base.Height);
				graphics2.FillRectangle(new SolidBrush(this.color_1), new Rectangle(0, 0, base.Width - 25, base.Height));
				Enum0 @enum = this.enum0_0;
				if (@enum == Enum0.None)
				{
					graphics2.FillRectangle(new SolidBrush(this.color_4), rect);
				}
				else if (@enum == Enum0.Over)
				{
					graphics2.FillRectangle(new SolidBrush(this.color_6), rect);
				}
				graphics2.DrawLine(new Pen(this.color_3, 2f), new Point(base.Width - 26, 1), new Point(base.Width - 26, base.Height - 1));
				try
				{
					graphics2.DrawString(this.Text, this.Font, new SolidBrush(this.color_2), new Rectangle(3, 0, base.Width - 20, base.Height), new StringFormat
					{
						LineAlignment = StringAlignment.Center,
						Alignment = StringAlignment.Near
					});
				}
				catch (Exception ex)
				{
				}
				graphics2.DrawRectangle(new Pen(this.color_0, 2f), new Rectangle(0, 0, base.Width, base.Height));
				Point[] points = new Point[]
				{
					new Point(base.Width - 17, 11),
					new Point(base.Width - 13, 5),
					new Point(base.Width - 9, 11)
				};
				graphics2.FillPolygon(new SolidBrush(this.color_0), points);
				graphics2.DrawPolygon(new Pen(this.color_5), points);
				Point[] points2 = new Point[]
				{
					new Point(base.Width - 17, 15),
					new Point(base.Width - 13, 21),
					new Point(base.Width - 9, 15)
				};
				graphics2.FillPolygon(new SolidBrush(this.color_0), points2);
				graphics2.DrawPolygon(new Pen(this.color_5), points2);
			}
			catch (Exception ex2)
			{
			}
			graphics2 = null;
			base.OnPaint(e);
			graphics.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}
	}

	// Token: 0x060007C2 RID: 1986 RVA: 0x000247F8 File Offset: 0x000229F8
	void method_0(int int_1)
	{
		base.SelectedIndex = int_1;
	}

	// Token: 0x0400039D RID: 925
	private int int_0;

	// Token: 0x0400039E RID: 926
	private Color color_0;

	// Token: 0x0400039F RID: 927
	private Color color_1;

	// Token: 0x040003A0 RID: 928
	private Color color_2;

	// Token: 0x040003A1 RID: 929
	private Color color_3;

	// Token: 0x040003A2 RID: 930
	private Color color_4;

	// Token: 0x040003A3 RID: 931
	private Color color_5;

	// Token: 0x040003A4 RID: 932
	private Color color_6;

	// Token: 0x040003A5 RID: 933
	private Enum0 enum0_0;
}
