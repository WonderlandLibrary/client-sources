using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x02000016 RID: 22
internal class Control3 : Control
{
	// Token: 0x0600021B RID: 539 RVA: 0x00008CB4 File Offset: 0x00006EB4
	public Control3()
	{
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.Selectable, false);
		this.pen_0 = new Pen(Color.FromArgb(24, 24, 24));
		this.pen_1 = new Pen(Color.FromArgb(65, 65, 65));
	}

	// Token: 0x0600021C RID: 540 RVA: 0x00008D0C File Offset: 0x00006F0C
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
			if (this.bool_0)
			{
				this.pathGradientBrush_0 = new PathGradientBrush(this.graphicsPath_0);
				this.pathGradientBrush_0.CenterColor = Color.FromArgb(60, 60, 60);
				this.pathGradientBrush_0.SurroundColors = new Color[]
				{
					Color.FromArgb(55, 55, 55)
				};
				this.pathGradientBrush_0.FocusScales = new PointF(0.8f, 0.5f);
				Class9.graphics_0.FillPath(this.pathGradientBrush_0, this.graphicsPath_0);
			}
			else
			{
				this.linearGradientBrush_0 = new LinearGradientBrush(base.ClientRectangle, Color.FromArgb(60, 60, 60), Color.FromArgb(55, 55, 55), 90f);
				Class9.graphics_0.FillPath(this.linearGradientBrush_0, this.graphicsPath_0);
			}
			Class9.graphics_0.DrawPath(this.pen_0, this.graphicsPath_0);
			Class9.graphics_0.DrawPath(this.pen_1, this.graphicsPath_1);
			this.sizeF_0 = Class9.graphics_0.MeasureString(this.Text, this.Font);
		}
		this.pointF_0 = new PointF(5f, (float)(base.Height / 2) - this.sizeF_0.Height / 2f);
		if (this.bool_0)
		{
			ref PointF ptr = ref this.pointF_0;
			this.pointF_0.X = ptr.X + 1f;
			ptr = ref this.pointF_0;
			this.pointF_0.Y = ptr.Y + 1f;
		}
		Class9.graphics_0.DrawString(this.Text, this.Font, Brushes.Black, this.pointF_0.X + 1f, this.pointF_0.Y + 1f);
		Class9.graphics_0.DrawString(this.Text, this.Font, Brushes.WhiteSmoke, this.pointF_0);
	}

	// Token: 0x0600021D RID: 541 RVA: 0x00008F58 File Offset: 0x00007158
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		this.bool_0 = true;
		base.Invalidate();
	}

	// Token: 0x0600021E RID: 542 RVA: 0x00008F68 File Offset: 0x00007168
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		this.bool_0 = false;
		base.Invalidate();
	}

	// Token: 0x0400008C RID: 140
	private bool bool_0;

	// Token: 0x0400008D RID: 141
	private GraphicsPath graphicsPath_0;

	// Token: 0x0400008E RID: 142
	private GraphicsPath graphicsPath_1;

	// Token: 0x0400008F RID: 143
	private SizeF sizeF_0;

	// Token: 0x04000090 RID: 144
	private PointF pointF_0;

	// Token: 0x04000091 RID: 145
	private Pen pen_0;

	// Token: 0x04000092 RID: 146
	private Pen pen_1;

	// Token: 0x04000093 RID: 147
	private PathGradientBrush pathGradientBrush_0;

	// Token: 0x04000094 RID: 148
	private LinearGradientBrush linearGradientBrush_0;
}
