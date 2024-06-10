using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x02000020 RID: 32
internal class Control10 : TabControl
{
	// Token: 0x06000269 RID: 617 RVA: 0x0000AA20 File Offset: 0x00008C20
	public Control10()
	{
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.Selectable, false);
		base.SizeMode = TabSizeMode.Fixed;
		base.Alignment = TabAlignment.Left;
		base.ItemSize = new Size(28, 115);
		base.DrawMode = TabDrawMode.OwnerDrawFixed;
		this.pen_0 = new Pen(Color.FromArgb(55, 55, 55));
		this.pen_1 = new Pen(Color.FromArgb(24, 24, 24));
		this.pen_2 = new Pen(Color.FromArgb(45, 45, 45), 2f);
		this.solidBrush_0 = new SolidBrush(Color.FromArgb(60, 60, 60));
		this.solidBrush_1 = new SolidBrush(Color.FromArgb(24, 24, 24));
		this.solidBrush_2 = new SolidBrush(Color.FromArgb(135, 169, 193));
		this.solidBrush_3 = new SolidBrush(Color.FromArgb(65, 65, 65));
		this.stringFormat_0 = new StringFormat();
		this.stringFormat_0.LineAlignment = StringAlignment.Center;
	}

	// Token: 0x0600026A RID: 618 RVA: 0x0000AB30 File Offset: 0x00008D30
	protected virtual void OnControlAdded(ControlEventArgs e)
	{
		if (e.Control is TabPage)
		{
			e.Control.BackColor = Color.FromArgb(68, 68, 68);
		}
		base.OnControlAdded(e);
	}

	// Token: 0x0600026B RID: 619 RVA: 0x0000AB60 File Offset: 0x00008D60
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class9.graphics_0 = e.Graphics;
		Class9.graphics_0.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		Class9.graphics_0.Clear(Color.FromArgb(60, 60, 60));
		Class9.graphics_0.SmoothingMode = SmoothingMode.AntiAlias;
		checked
		{
			this.int_1 = base.ItemSize.Height + 2;
			this.graphicsPath_0 = Class9.smethod_2(0, 0, this.int_1 + 3, base.Height - 1, 7);
			this.graphicsPath_1 = Class9.smethod_2(1, 1, this.int_1 + 3, base.Height - 3, 7);
			this.pathGradientBrush_0 = new PathGradientBrush(this.graphicsPath_0);
			this.pathGradientBrush_0.CenterColor = Color.FromArgb(60, 60, 60);
			this.pathGradientBrush_0.SurroundColors = new Color[]
			{
				Color.FromArgb(45, 45, 45)
			};
			this.pathGradientBrush_0.FocusScales = new PointF(0.8f, 0.95f);
			Class9.graphics_0.FillPath(this.pathGradientBrush_0, this.graphicsPath_0);
			Class9.graphics_0.DrawPath(this.pen_0, this.graphicsPath_0);
			Class9.graphics_0.DrawPath(this.pen_1, this.graphicsPath_1);
			int num = base.TabCount - 1;
			for (int i = 0; i <= num; i++)
			{
				this.rectangle_0 = base.GetTabRect(i);
				ref Rectangle ptr = ref this.rectangle_0;
				this.rectangle_0.Y = ptr.Y + 2;
				ptr = ref this.rectangle_0;
				this.rectangle_0.Height = ptr.Height - 3;
				ptr = ref this.rectangle_0;
				this.rectangle_0.Width = ptr.Width + 1;
				ptr = ref this.rectangle_0;
				this.rectangle_0.X = ptr.X - 1;
				this.tabPage_0 = base.TabPages[i];
				this.int_0 = 0;
				if (base.SelectedIndex == i)
				{
					Class9.graphics_0.FillRectangle(this.solidBrush_0, this.rectangle_0);
					int num2 = 0;
					do
					{
						Class9.graphics_0.FillRectangle(this.solidBrush_1, this.rectangle_0.X + 5 + num2 * 5, this.rectangle_0.Y + 6, 2, this.rectangle_0.Height - 9);
						Class9.graphics_0.SmoothingMode = SmoothingMode.None;
						Class9.graphics_0.FillRectangle(this.solidBrush_2, this.rectangle_0.X + 5 + num2 * 5, this.rectangle_0.Y + 5, 2, this.rectangle_0.Height - 9);
						Class9.graphics_0.SmoothingMode = SmoothingMode.AntiAlias;
						ref int ptr2 = ref this.int_0;
						this.int_0 = ptr2 + 5;
						num2++;
					}
					while (num2 <= 1);
					Class9.graphics_0.DrawRectangle(this.pen_2, this.rectangle_0.X + 1, this.rectangle_0.Y - 1, this.rectangle_0.Width, this.rectangle_0.Height + 2);
					Class9.graphics_0.DrawRectangle(this.pen_0, this.rectangle_0.X + 1, this.rectangle_0.Y + 1, this.rectangle_0.Width - 2, this.rectangle_0.Height - 2);
					Class9.graphics_0.DrawRectangle(this.pen_1, this.rectangle_0);
				}
				else
				{
					int num3 = 0;
					do
					{
						Class9.graphics_0.FillRectangle(this.solidBrush_1, this.rectangle_0.X + 5 + num3 * 5, this.rectangle_0.Y + 6, 2, this.rectangle_0.Height - 9);
						Class9.graphics_0.SmoothingMode = SmoothingMode.None;
						Class9.graphics_0.FillRectangle(this.solidBrush_3, this.rectangle_0.X + 5 + num3 * 5, this.rectangle_0.Y + 5, 2, this.rectangle_0.Height - 9);
						Class9.graphics_0.SmoothingMode = SmoothingMode.AntiAlias;
						ref int ptr2 = ref this.int_0;
						this.int_0 = ptr2 + 5;
						num3++;
					}
					while (num3 <= 1);
				}
				ptr = ref this.rectangle_0;
				this.rectangle_0.X = ptr.X + (5 + this.int_0);
				this.rectangle_1 = this.rectangle_0;
				ptr = ref this.rectangle_1;
				this.rectangle_1.Y = ptr.Y + 1;
				ptr = ref this.rectangle_1;
				this.rectangle_1.X = ptr.X + 1;
				Class9.graphics_0.DrawString(this.tabPage_0.Text, this.Font, Brushes.Black, this.rectangle_1, this.stringFormat_0);
				Class9.graphics_0.DrawString(this.tabPage_0.Text, this.Font, Brushes.WhiteSmoke, this.rectangle_0, this.stringFormat_0);
			}
			this.graphicsPath_2 = Class9.smethod_2(this.int_1, 0, base.Width - this.int_1 - 1, base.Height - 1, 7);
			this.graphicsPath_3 = Class9.smethod_2(this.int_1 + 1, 1, base.Width - this.int_1 - 3, base.Height - 3, 7);
			Class9.graphics_0.DrawPath(this.pen_1, this.graphicsPath_2);
			Class9.graphics_0.DrawPath(this.pen_0, this.graphicsPath_3);
		}
	}

	// Token: 0x040000DA RID: 218
	private GraphicsPath graphicsPath_0;

	// Token: 0x040000DB RID: 219
	private GraphicsPath graphicsPath_1;

	// Token: 0x040000DC RID: 220
	private GraphicsPath graphicsPath_2;

	// Token: 0x040000DD RID: 221
	private GraphicsPath graphicsPath_3;

	// Token: 0x040000DE RID: 222
	private Rectangle rectangle_0;

	// Token: 0x040000DF RID: 223
	private Rectangle rectangle_1;

	// Token: 0x040000E0 RID: 224
	private Pen pen_0;

	// Token: 0x040000E1 RID: 225
	private Pen pen_1;

	// Token: 0x040000E2 RID: 226
	private Pen pen_2;

	// Token: 0x040000E3 RID: 227
	private SolidBrush solidBrush_0;

	// Token: 0x040000E4 RID: 228
	private SolidBrush solidBrush_1;

	// Token: 0x040000E5 RID: 229
	private SolidBrush solidBrush_2;

	// Token: 0x040000E6 RID: 230
	private SolidBrush solidBrush_3;

	// Token: 0x040000E7 RID: 231
	private PathGradientBrush pathGradientBrush_0;

	// Token: 0x040000E8 RID: 232
	private TabPage tabPage_0;

	// Token: 0x040000E9 RID: 233
	private StringFormat stringFormat_0;

	// Token: 0x040000EA RID: 234
	private int int_0;

	// Token: 0x040000EB RID: 235
	private int int_1;
}
