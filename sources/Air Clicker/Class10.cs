using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

// Token: 0x0200001F RID: 31
internal class Class10 : ComboBox
{
	// Token: 0x06000266 RID: 614 RVA: 0x0000A57C File Offset: 0x0000877C
	public Class10()
	{
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.Selectable, false);
		base.DrawMode = DrawMode.OwnerDrawFixed;
		base.DropDownStyle = ComboBoxStyle.DropDownList;
		this.BackColor = Color.FromArgb(50, 50, 50);
		this.ForeColor = Color.FromArgb(235, 235, 235);
		this.pen_0 = new Pen(Color.FromArgb(24, 24, 24));
		this.pen_1 = new Pen(Color.FromArgb(235, 235, 235), 2f);
		this.pen_2 = new Pen(Brushes.Black, 2f);
		this.pen_3 = new Pen(Color.FromArgb(65, 65, 65));
		this.solidBrush_0 = new SolidBrush(Color.FromArgb(65, 65, 65));
		this.solidBrush_1 = new SolidBrush(Color.FromArgb(55, 55, 55));
	}

	// Token: 0x06000267 RID: 615 RVA: 0x0000A674 File Offset: 0x00008874
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class9.graphics_0 = e.Graphics;
		Class9.graphics_0.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		Class9.graphics_0.Clear(this.BackColor);
		Class9.graphics_0.SmoothingMode = SmoothingMode.AntiAlias;
		checked
		{
			this.graphicsPath_0 = Class9.smethod_2(0, 0, base.Width - 1, base.Height - 1, 7);
			this.graphicsPath_1 = Class9.smethod_2(1, 1, base.Width - 3, base.Height - 3, 7);
			this.linearGradientBrush_0 = new LinearGradientBrush(base.ClientRectangle, Color.FromArgb(60, 60, 60), Color.FromArgb(55, 55, 55), 90f);
			Class9.graphics_0.SetClip(this.graphicsPath_0);
			Class9.graphics_0.FillRectangle(this.linearGradientBrush_0, base.ClientRectangle);
			Class9.graphics_0.ResetClip();
			Class9.graphics_0.DrawPath(this.pen_0, this.graphicsPath_0);
			Class9.graphics_0.DrawPath(this.pen_3, this.graphicsPath_1);
			this.sizeF_0 = Class9.graphics_0.MeasureString(this.Text, this.Font);
		}
		this.pointF_0 = new PointF(5f, (float)(base.Height / 2) - this.sizeF_0.Height / 2f);
		Class9.graphics_0.DrawString(this.Text, this.Font, Brushes.Black, this.pointF_0.X + 1f, this.pointF_0.Y + 1f);
		Class9.graphics_0.DrawString(this.Text, this.Font, Brushes.WhiteSmoke, this.pointF_0);
		checked
		{
			Class9.graphics_0.DrawLine(this.pen_2, base.Width - 15, 10, base.Width - 11, 13);
			Class9.graphics_0.DrawLine(this.pen_2, base.Width - 7, 10, base.Width - 11, 13);
			Class9.graphics_0.DrawLine(Pens.Black, base.Width - 11, 13, base.Width - 11, 14);
			Class9.graphics_0.DrawLine(this.pen_1, base.Width - 16, 9, base.Width - 12, 12);
			Class9.graphics_0.DrawLine(this.pen_1, base.Width - 8, 9, base.Width - 12, 12);
			Class9.graphics_0.DrawLine(Pens.WhiteSmoke, base.Width - 12, 12, base.Width - 12, 13);
			Class9.graphics_0.DrawLine(this.pen_0, base.Width - 22, 0, base.Width - 22, base.Height);
			Class9.graphics_0.DrawLine(this.pen_3, base.Width - 23, 1, base.Width - 23, base.Height - 2);
			Class9.graphics_0.DrawLine(this.pen_3, base.Width - 21, 1, base.Width - 21, base.Height - 2);
		}
	}

	// Token: 0x06000268 RID: 616 RVA: 0x0000A97C File Offset: 0x00008B7C
	protected virtual void OnDrawItem(DrawItemEventArgs e)
	{
		e.Graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		if ((e.State & DrawItemState.Selected) == DrawItemState.Selected)
		{
			e.Graphics.FillRectangle(this.solidBrush_0, e.Bounds);
		}
		else
		{
			e.Graphics.FillRectangle(this.solidBrush_1, e.Bounds);
		}
		if (e.Index != -1)
		{
			e.Graphics.DrawString(base.GetItemText(RuntimeHelpers.GetObjectValue(base.Items[e.Index])), e.Font, Brushes.WhiteSmoke, e.Bounds);
		}
	}

	// Token: 0x040000CF RID: 207
	private GraphicsPath graphicsPath_0;

	// Token: 0x040000D0 RID: 208
	private GraphicsPath graphicsPath_1;

	// Token: 0x040000D1 RID: 209
	private SizeF sizeF_0;

	// Token: 0x040000D2 RID: 210
	private PointF pointF_0;

	// Token: 0x040000D3 RID: 211
	private Pen pen_0;

	// Token: 0x040000D4 RID: 212
	private Pen pen_1;

	// Token: 0x040000D5 RID: 213
	private Pen pen_2;

	// Token: 0x040000D6 RID: 214
	private Pen pen_3;

	// Token: 0x040000D7 RID: 215
	private SolidBrush solidBrush_0;

	// Token: 0x040000D8 RID: 216
	private SolidBrush solidBrush_1;

	// Token: 0x040000D9 RID: 217
	private LinearGradientBrush linearGradientBrush_0;
}
