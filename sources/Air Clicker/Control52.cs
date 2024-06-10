using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic;

// Token: 0x020000C7 RID: 199
internal class Control52 : Control
{
	// Token: 0x17000284 RID: 644
	// (get) Token: 0x060008F7 RID: 2295 RVA: 0x0002A4F8 File Offset: 0x000286F8
	// (set) Token: 0x060008F8 RID: 2296 RVA: 0x0002A510 File Offset: 0x00028710
	public Control52.Enum14 Enum14_0
	{
		get
		{
			return this.enum14_0;
		}
		set
		{
			this.enum14_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000285 RID: 645
	// (get) Token: 0x060008F9 RID: 2297 RVA: 0x0002A520 File Offset: 0x00028720
	// (set) Token: 0x060008FA RID: 2298 RVA: 0x0002A528 File Offset: 0x00028728
	public bool Boolean_0
	{
		get
		{
			return this.bool_1;
		}
		set
		{
			this.bool_1 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000286 RID: 646
	// (get) Token: 0x060008FB RID: 2299 RVA: 0x0002A538 File Offset: 0x00028738
	// (set) Token: 0x060008FC RID: 2300 RVA: 0x0002A540 File Offset: 0x00028740
	public bool Boolean_1
	{
		get
		{
			return this.bool_2;
		}
		set
		{
			this.bool_2 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000287 RID: 647
	// (get) Token: 0x060008FD RID: 2301 RVA: 0x0002A550 File Offset: 0x00028750
	// (set) Token: 0x060008FE RID: 2302 RVA: 0x0002A568 File Offset: 0x00028768
	public int Int32_0
	{
		get
		{
			return this.int_0;
		}
		set
		{
			this.int_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000288 RID: 648
	// (get) Token: 0x060008FF RID: 2303 RVA: 0x0002A578 File Offset: 0x00028778
	// (set) Token: 0x06000900 RID: 2304 RVA: 0x0002A590 File Offset: 0x00028790
	public Image Image_0
	{
		get
		{
			return this.image_0;
		}
		set
		{
			if (value != null)
			{
				this.size_0 = value.Size;
			}
			else
			{
				this.size_0 = Size.Empty;
			}
			this.image_0 = value;
			base.Invalidate();
		}
	}

	// Token: 0x17000289 RID: 649
	// (get) Token: 0x06000901 RID: 2305 RVA: 0x0002A5C0 File Offset: 0x000287C0
	protected Size Size_0
	{
		get
		{
			return this.size_0;
		}
	}

	// Token: 0x06000902 RID: 2306 RVA: 0x0002A5D8 File Offset: 0x000287D8
	protected virtual void OnMouseMove(MouseEventArgs e)
	{
		base.OnMouseMove(e);
		if (checked(e.X >= base.Width - 19 && e.X <= base.Width - 10 && e.Y > this.point_0.Y && e.Y < this.point_0.Y + 12))
		{
			this.bool_0 = true;
		}
		else
		{
			this.bool_0 = false;
		}
		base.Invalidate();
	}

	// Token: 0x06000903 RID: 2307 RVA: 0x0002A654 File Offset: 0x00028854
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		base.OnMouseDown(e);
		if (this.bool_2 && this.bool_0)
		{
			this.method_1();
		}
	}

	// Token: 0x06000904 RID: 2308 RVA: 0x0002A674 File Offset: 0x00028874
	internal GraphicsPath method_0(Rectangle rectangle_0, int int_1)
	{
		checked
		{
			try
			{
				this.graphicsPath_0 = new GraphicsPath(FillMode.Winding);
				this.graphicsPath_0.AddArc(rectangle_0.X, rectangle_0.Y, int_1, int_1, 180f, 90f);
				this.graphicsPath_0.AddArc(rectangle_0.Right - int_1, rectangle_0.Y, int_1, int_1, 270f, 90f);
				this.graphicsPath_0.AddArc(rectangle_0.Right - int_1, rectangle_0.Bottom - int_1, int_1, int_1, 0f, 90f);
				this.graphicsPath_0.AddArc(rectangle_0.X, rectangle_0.Bottom - int_1, int_1, int_1, 90f, 90f);
				this.graphicsPath_0.CloseFigure();
			}
			catch (Exception ex)
			{
				MessageBox.Show(ex.Message + "\r\n\r\nValue must be either '1' or higher", "Invalid Integer", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
				this.int_0 = 8;
				this.Int32_0 = 8;
			}
			return this.graphicsPath_0;
		}
	}

	// Token: 0x06000905 RID: 2309 RVA: 0x0002A78C File Offset: 0x0002898C
	public Control52()
	{
		this.int_0 = 8;
		this.string_0 = null;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.Font = new Font("Tahoma", 9f);
		this.MinimumSize = new Size(100, 40);
		this.Boolean_0 = false;
		this.Boolean_1 = true;
	}

	// Token: 0x06000906 RID: 2310 RVA: 0x0002A7EC File Offset: 0x000289EC
	protected virtual void OnPaint(PaintEventArgs e)
	{
		base.OnPaint(e);
		Graphics graphics = e.Graphics;
		Font font = new Font(this.Font.FontFamily, this.Font.Size, FontStyle.Bold);
		checked
		{
			Rectangle rectangle = new Rectangle(0, 0, base.Width - 1, base.Height - 1);
			GraphicsPath graphicsPath = this.method_0(rectangle, this.int_0);
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.Clear(base.Parent.BackColor);
			Color color;
			Color color2;
			Color color3;
			switch (this.enum14_0)
			{
			case Control52.Enum14.Notice:
				color = Color.FromArgb(111, 177, 199);
				color2 = Color.FromArgb(111, 177, 199);
				color3 = Color.White;
				break;
			case Control52.Enum14.Success:
				color = Color.FromArgb(91, 195, 162);
				color2 = Color.FromArgb(91, 195, 162);
				color3 = Color.White;
				break;
			case Control52.Enum14.Warning:
				color = Color.FromArgb(254, 209, 108);
				color2 = Color.FromArgb(254, 209, 108);
				color3 = Color.DimGray;
				break;
			case Control52.Enum14.Error:
				color = Color.FromArgb(217, 103, 93);
				color2 = Color.FromArgb(217, 103, 93);
				color3 = Color.White;
				break;
			}
			if (this.bool_1)
			{
				graphics.FillPath(new SolidBrush(color), graphicsPath);
				graphics.DrawPath(new Pen(color2), graphicsPath);
			}
			else
			{
				graphics.FillRectangle(new SolidBrush(color), rectangle);
				graphics.DrawRectangle(new Pen(color2), rectangle);
			}
			switch (this.enum14_0)
			{
			case Control52.Enum14.Notice:
				this.string_0 = "NOTICE";
				break;
			case Control52.Enum14.Success:
				this.string_0 = "SUCCESS";
				break;
			case Control52.Enum14.Warning:
				this.string_0 = "WARNING";
				break;
			case Control52.Enum14.Error:
				this.string_0 = "ERROR";
				break;
			}
			if (Information.IsNothing(this.Image_0))
			{
				graphics.DrawString(this.string_0, font, new SolidBrush(color3), new Point(10, 5));
				graphics.DrawString(this.Text, this.Font, new SolidBrush(color3), new Rectangle(10, 21, base.Width - 17, base.Height - 5));
			}
			else
			{
				graphics.DrawImage(this.image_0, 12, 4, 16, 16);
				graphics.DrawString(this.string_0, font, new SolidBrush(color3), new Point(30, 5));
				graphics.DrawString(this.Text, this.Font, new SolidBrush(color3), new Rectangle(10, 21, base.Width - 17, base.Height - 5));
			}
			this.point_0 = new Point(base.Width - 26, 4);
			if (this.bool_2)
			{
				graphics.DrawString("r", new Font("Marlett", 7f, FontStyle.Regular), new SolidBrush(Color.FromArgb(130, 130, 130)), new Rectangle(base.Width - 20, 10, base.Width, base.Height), new StringFormat
				{
					Alignment = StringAlignment.Near,
					LineAlignment = StringAlignment.Near
				});
			}
			graphicsPath.Dispose();
		}
	}

	// Token: 0x06000907 RID: 2311 RVA: 0x0002AB3C File Offset: 0x00028D3C
	void method_1()
	{
		base.Dispose();
	}

	// Token: 0x0400044A RID: 1098
	private Point point_0;

	// Token: 0x0400044B RID: 1099
	private bool bool_0;

	// Token: 0x0400044C RID: 1100
	private int int_0;

	// Token: 0x0400044D RID: 1101
	private GraphicsPath graphicsPath_0;

	// Token: 0x0400044E RID: 1102
	private string string_0;

	// Token: 0x0400044F RID: 1103
	private Control52.Enum14 enum14_0;

	// Token: 0x04000450 RID: 1104
	private bool bool_1;

	// Token: 0x04000451 RID: 1105
	private bool bool_2;

	// Token: 0x04000452 RID: 1106
	private Image image_0;

	// Token: 0x04000453 RID: 1107
	private Size size_0;

	// Token: 0x020000C8 RID: 200
	public enum Enum14
	{
		// Token: 0x04000455 RID: 1109
		Notice,
		// Token: 0x04000456 RID: 1110
		Success,
		// Token: 0x04000457 RID: 1111
		Warning,
		// Token: 0x04000458 RID: 1112
		Error
	}
}
