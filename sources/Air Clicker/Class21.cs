using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic;

// Token: 0x0200005A RID: 90
internal class Class21 : TreeView
{
	// Token: 0x060004A9 RID: 1193 RVA: 0x00016620 File Offset: 0x00014820
	protected virtual void OnDrawNode(DrawTreeNodeEventArgs e)
	{
		checked
		{
			try
			{
				Rectangle rect = new Rectangle(e.Bounds.Location.X, e.Bounds.Location.Y, e.Bounds.Width, e.Bounds.Height);
				TreeNodeStates treeNodeStates = this.treeNodeStates_0;
				if (treeNodeStates == TreeNodeStates.Selected)
				{
					e.Graphics.FillRectangle(Brushes.Green, rect);
					e.Graphics.DrawString(e.Node.Text, new Font("Segoe UI", 8f), Brushes.Black, new Rectangle(rect.X + 2, rect.Y + 2, rect.Width, rect.Height), Class16.stringFormat_0);
					base.Invalidate();
				}
				else if (treeNodeStates == TreeNodeStates.Checked)
				{
					e.Graphics.FillRectangle(Brushes.Green, rect);
					e.Graphics.DrawString(e.Node.Text, new Font("Segoe UI", 8f), Brushes.Black, new Rectangle(rect.X + 2, rect.Y + 2, rect.Width, rect.Height), Class16.stringFormat_0);
					base.Invalidate();
				}
				else if (treeNodeStates == TreeNodeStates.Default)
				{
					e.Graphics.FillRectangle(Brushes.Red, rect);
					e.Graphics.DrawString(e.Node.Text, new Font("Segoe UI", 8f), Brushes.LimeGreen, new Rectangle(rect.X + 2, rect.Y + 2, rect.Width, rect.Height), Class16.stringFormat_0);
					base.Invalidate();
				}
			}
			catch (Exception ex)
			{
				Interaction.MsgBox(ex.Message, MsgBoxStyle.OkOnly, null);
			}
			base.OnDrawNode(e);
		}
	}

	// Token: 0x060004AA RID: 1194 RVA: 0x0001682C File Offset: 0x00014A2C
	public Class21()
	{
		this.color_0 = Color.FromArgb(45, 47, 49);
		this.color_1 = Color.FromArgb(25, 27, 29);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = this.color_0;
		this.ForeColor = Color.White;
		base.LineColor = this.color_1;
		base.DrawMode = TreeViewDrawMode.OwnerDrawAll;
	}

	// Token: 0x060004AB RID: 1195 RVA: 0x000168A0 File Offset: 0x00014AA0
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class16.bitmap_0 = new Bitmap(base.Width, base.Height);
		Class16.graphics_0 = Graphics.FromImage(Class16.bitmap_0);
		Rectangle rect = new Rectangle(0, 0, base.Width, base.Height);
		Graphics graphics_ = Class16.graphics_0;
		graphics_.SmoothingMode = SmoothingMode.HighQuality;
		graphics_.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics_.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		graphics_.Clear(this.BackColor);
		graphics_.FillRectangle(new SolidBrush(this.color_0), rect);
		graphics_.DrawString(this.Text, new Font("Segoe UI", 8f), Brushes.Black, checked(new Rectangle(this.method_0().X + 2, base.Bounds.Y + 2, base.Bounds.Width, base.Bounds.Height)), Class16.stringFormat_0);
		base.OnPaint(e);
		Class16.graphics_0.Dispose();
		e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
		e.Graphics.DrawImageUnscaled(Class16.bitmap_0, 0, 0);
		Class16.bitmap_0.Dispose();
	}

	// Token: 0x060004AC RID: 1196 RVA: 0x000169C8 File Offset: 0x00014BC8
	Rectangle method_0()
	{
		return base.Bounds;
	}

	// Token: 0x04000273 RID: 627
	private TreeNodeStates treeNodeStates_0;

	// Token: 0x04000274 RID: 628
	private Color color_0;

	// Token: 0x04000275 RID: 629
	private Color color_1;
}
