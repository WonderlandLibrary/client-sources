using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic;

namespace Meth
{
	// Token: 0x0200002D RID: 45
	internal class FlatTreeView : TreeView
	{
		// Token: 0x0600031A RID: 794 RVA: 0x0001537C File Offset: 0x0001357C
		protected override void OnDrawNode(DrawTreeNodeEventArgs e)
		{
			try
			{
				Rectangle rect = new Rectangle(e.Bounds.Location.X, e.Bounds.Location.Y, e.Bounds.Width, e.Bounds.Height);
				TreeNodeStates state = this.State;
				if (state != TreeNodeStates.Selected)
				{
					if (state != TreeNodeStates.Checked)
					{
						if (state == TreeNodeStates.Default)
						{
							e.Graphics.FillRectangle(Brushes.Red, rect);
							e.Graphics.DrawString(e.Node.Text, new Font("Segoe UI", 8f), Brushes.LimeGreen, new Rectangle(rect.X + 2, rect.Y + 2, rect.Width, rect.Height), Helpers.NearSF);
							base.Invalidate();
						}
					}
					else
					{
						e.Graphics.FillRectangle(Brushes.Green, rect);
						e.Graphics.DrawString(e.Node.Text, new Font("Segoe UI", 8f), Brushes.Black, new Rectangle(rect.X + 2, rect.Y + 2, rect.Width, rect.Height), Helpers.NearSF);
						base.Invalidate();
					}
				}
				else
				{
					e.Graphics.FillRectangle(Brushes.Green, rect);
					e.Graphics.DrawString(e.Node.Text, new Font("Segoe UI", 8f), Brushes.Black, new Rectangle(rect.X + 2, rect.Y + 2, rect.Width, rect.Height), Helpers.NearSF);
					base.Invalidate();
				}
			}
			catch (Exception ex)
			{
				Interaction.MsgBox(ex.Message, MsgBoxStyle.OkOnly, null);
			}
			base.OnDrawNode(e);
		}

		// Token: 0x0600031B RID: 795 RVA: 0x0001558C File Offset: 0x0001378C
		public FlatTreeView()
		{
			this._BaseColor = Color.FromArgb(45, 47, 49);
			this._LineColor = Color.FromArgb(25, 27, 29);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = this._BaseColor;
			this.ForeColor = Color.White;
			base.LineColor = this._LineColor;
			base.DrawMode = TreeViewDrawMode.OwnerDrawAll;
		}

		// Token: 0x0600031C RID: 796 RVA: 0x00015600 File Offset: 0x00013800
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			Rectangle rect = new Rectangle(0, 0, base.Width, base.Height);
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			g.FillRectangle(new SolidBrush(this._BaseColor), rect);
			g.DrawString(this.Text, new Font("Segoe UI", 8f), Brushes.Black, new Rectangle(base.Bounds.X + 2, base.Bounds.Y + 2, base.Bounds.Width, base.Bounds.Height), Helpers.NearSF);
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		// Token: 0x04000198 RID: 408
		private TreeNodeStates State;

		// Token: 0x04000199 RID: 409
		private Color _BaseColor;

		// Token: 0x0400019A RID: 410
		private Color _LineColor;
	}
}
