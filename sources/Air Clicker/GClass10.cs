using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x020000DB RID: 219
public class GClass10 : ComboBox
{
	// Token: 0x17000293 RID: 659
	// (get) Token: 0x0600095B RID: 2395 RVA: 0x0002BDC8 File Offset: 0x00029FC8
	// (set) Token: 0x0600095C RID: 2396 RVA: 0x0002BDE0 File Offset: 0x00029FE0
	public GClass10.GEnum15 GEnum15_0
	{
		get
		{
			return this.genum15_0;
		}
		set
		{
			this.genum15_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000294 RID: 660
	// (get) Token: 0x0600095D RID: 2397 RVA: 0x0002BDF0 File Offset: 0x00029FF0
	// (set) Token: 0x0600095E RID: 2398 RVA: 0x0002BE08 File Offset: 0x0002A008
	public Color Color_0
	{
		get
		{
			return this.color_0;
		}
		set
		{
			this.color_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000295 RID: 661
	// (get) Token: 0x0600095F RID: 2399 RVA: 0x0002BE18 File Offset: 0x0002A018
	// (set) Token: 0x06000960 RID: 2400 RVA: 0x0002BE30 File Offset: 0x0002A030
	private int Int32_0
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
				this.method_1(value);
			}
			catch (Exception ex)
			{
			}
			base.Invalidate();
		}
	}

	// Token: 0x06000961 RID: 2401 RVA: 0x0002BE70 File Offset: 0x0002A070
	public void GClass10_DrawItem(object sender, DrawItemEventArgs e)
	{
		e.DrawBackground();
		try
		{
			if ((e.State & DrawItemState.Selected) != DrawItemState.Selected)
			{
				GClass10.GEnum15 genum = this.GEnum15_0;
				if (genum != GClass10.GEnum15.Light)
				{
					if (genum == GClass10.GEnum15.Dark)
					{
						e.Graphics.FillRectangle(new SolidBrush(Color.FromArgb(35, 35, 35)), e.Bounds);
					}
				}
				else
				{
					e.Graphics.FillRectangle(new SolidBrush(Color.White), e.Bounds);
				}
			}
			else
			{
				e.Graphics.FillRectangle(new SolidBrush(this.color_0), e.Bounds);
			}
			GClass10.GEnum15 genum2 = this.GEnum15_0;
			if (genum2 != GClass10.GEnum15.Light)
			{
				if (genum2 == GClass10.GEnum15.Dark)
				{
					e.Graphics.DrawString(base.GetItemText(RuntimeHelpers.GetObjectValue(base.Items[e.Index])), e.Font, Brushes.White, e.Bounds);
				}
			}
			else
			{
				e.Graphics.DrawString(base.GetItemText(RuntimeHelpers.GetObjectValue(base.Items[e.Index])), e.Font, Brushes.Black, e.Bounds);
			}
		}
		catch (Exception ex)
		{
		}
	}

	// Token: 0x06000962 RID: 2402 RVA: 0x0002BFB0 File Offset: 0x0002A1B0
	protected void method_0(Color color_1, Point point_0, Point point_1, Point point_2, Graphics graphics_0)
	{
		List<Point> list = new List<Point>();
		list.Add(point_0);
		list.Add(point_1);
		list.Add(point_2);
		graphics_0.FillPolygon(new SolidBrush(color_1), list.ToArray());
	}

	// Token: 0x06000963 RID: 2403 RVA: 0x0002BFEC File Offset: 0x0002A1EC
	public GClass10()
	{
		base.DrawItem += this.GClass10_DrawItem;
		this.int_0 = 0;
		base.SetStyle(ControlStyles.AllPaintingInWmPaint, true);
		base.SetStyle(ControlStyles.ResizeRedraw, true);
		base.SetStyle(ControlStyles.UserPaint, true);
		base.SetStyle(ControlStyles.DoubleBuffer, true);
		base.SetStyle(ControlStyles.SupportsTransparentBackColor, true);
		base.DrawMode = DrawMode.OwnerDrawFixed;
		this.BackColor = Color.FromArgb(38, 38, 38);
		this.ForeColor = Color.White;
		this.Color_0 = Color.DodgerBlue;
		this.GEnum15_0 = GClass10.GEnum15.Dark;
		base.DropDownStyle = ComboBoxStyle.DropDownList;
		this.Font = new Font("Segoe UI Semilight", 9.75f);
		this.Int32_0 = 0;
		this.DoubleBuffered = true;
	}

	// Token: 0x06000964 RID: 2404 RVA: 0x0002C0AC File Offset: 0x0002A2AC
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		graphics.SmoothingMode = SmoothingMode.HighQuality;
		GClass10.GEnum15 genum = this.GEnum15_0;
		checked
		{
			if (genum != GClass10.GEnum15.Light)
			{
				if (genum == GClass10.GEnum15.Dark)
				{
					graphics.Clear(Color.FromArgb(38, 38, 38));
					graphics.DrawLine(new Pen(Color.White, 2f), new Point(base.Width - 18, 10), new Point(base.Width - 14, 14));
					graphics.DrawLine(new Pen(Color.White, 2f), new Point(base.Width - 14, 14), new Point(base.Width - 10, 10));
					graphics.DrawLine(new Pen(Color.White), new Point(base.Width - 14, 15), new Point(base.Width - 14, 14));
				}
			}
			else
			{
				graphics.Clear(Color.White);
				graphics.DrawLine(new Pen(Color.FromArgb(38, 38, 38), 2f), new Point(base.Width - 18, 10), new Point(base.Width - 14, 14));
				graphics.DrawLine(new Pen(Color.FromArgb(38, 38, 38), 2f), new Point(base.Width - 14, 14), new Point(base.Width - 10, 10));
				graphics.DrawLine(new Pen(Color.FromArgb(38, 38, 38)), new Point(base.Width - 14, 15), new Point(base.Width - 14, 14));
			}
			graphics.DrawRectangle(new Pen(Color.FromArgb(38, 38, 38)), new Rectangle(0, 0, base.Width - 1, base.Height - 1));
			try
			{
				GClass10.GEnum15 genum2 = this.GEnum15_0;
				if (genum2 != GClass10.GEnum15.Light)
				{
					if (genum2 == GClass10.GEnum15.Dark)
					{
						graphics.DrawString(this.Text, this.Font, Brushes.White, new Rectangle(7, 0, base.Width - 1, base.Height - 1), new StringFormat
						{
							LineAlignment = StringAlignment.Center,
							Alignment = StringAlignment.Near
						});
					}
				}
				else
				{
					graphics.DrawString(this.Text, this.Font, Brushes.Black, new Rectangle(7, 0, base.Width - 1, base.Height - 1), new StringFormat
					{
						LineAlignment = StringAlignment.Center,
						Alignment = StringAlignment.Near
					});
				}
			}
			catch (Exception ex)
			{
			}
			NewLateBinding.LateCall(e.Graphics, null, "DrawImage", new object[]
			{
				bitmap.Clone(),
				0,
				0
			}, null, null, null, true);
			graphics.Dispose();
			bitmap.Dispose();
		}
	}

	// Token: 0x06000965 RID: 2405 RVA: 0x0002C384 File Offset: 0x0002A584
	void method_1(int int_1)
	{
		base.SelectedIndex = int_1;
	}

	// Token: 0x04000488 RID: 1160
	private GClass10.GEnum15 genum15_0;

	// Token: 0x04000489 RID: 1161
	private Color color_0;

	// Token: 0x0400048A RID: 1162
	private int int_0;

	// Token: 0x020000DC RID: 220
	public enum GEnum15
	{
		// Token: 0x0400048C RID: 1164
		Light,
		// Token: 0x0400048D RID: 1165
		Dark
	}
}
