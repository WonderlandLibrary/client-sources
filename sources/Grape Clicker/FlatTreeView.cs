using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;
using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Xh0kO1ZCmA
{
	internal class FlatTreeView : TreeView
	{
		private TreeNodeStates State;

		private Color _BaseColor;

		private Color _LineColor;

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

		protected override void OnDrawNode(DrawTreeNodeEventArgs e)
		{
			try
			{
				Point location = e.Bounds.Location;
				int x = location.X;
				location = e.Bounds.Location;
				int y = location.Y;
				int width = e.Bounds.Width;
				Rectangle bounds = e.Bounds;
				Rectangle rectangle = new Rectangle(x, y, width, bounds.Height);
				TreeNodeStates state = this.State;
				if (state == TreeNodeStates.Selected)
				{
					e.Graphics.FillRectangle(Brushes.Green, rectangle);
					e.Graphics.DrawString(e.Node.Text, new System.Drawing.Font("Segoe UI", 8f), Brushes.Black, new Rectangle(checked(rectangle.X + 2), checked(rectangle.Y + 2), rectangle.Width, rectangle.Height), Helpers.NearSF);
					base.Invalidate();
				}
				else if (state == TreeNodeStates.Checked)
				{
					e.Graphics.FillRectangle(Brushes.Green, rectangle);
					e.Graphics.DrawString(e.Node.Text, new System.Drawing.Font("Segoe UI", 8f), Brushes.Black, new Rectangle(checked(rectangle.X + 2), checked(rectangle.Y + 2), rectangle.Width, rectangle.Height), Helpers.NearSF);
					base.Invalidate();
				}
				else if (state == TreeNodeStates.Default)
				{
					e.Graphics.FillRectangle(Brushes.Red, rectangle);
					e.Graphics.DrawString(e.Node.Text, new System.Drawing.Font("Segoe UI", 8f), Brushes.LimeGreen, new Rectangle(checked(rectangle.X + 2), checked(rectangle.Y + 2), rectangle.Width, rectangle.Height), Helpers.NearSF);
					base.Invalidate();
				}
			}
			catch (Exception exception)
			{
				ProjectData.SetProjectError(exception);
				Interaction.MsgBox(exception.Message, MsgBoxStyle.OkOnly, null);
				ProjectData.ClearProjectError();
			}
			base.OnDrawNode(e);
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			Rectangle rectangle = new Rectangle(0, 0, base.Width, base.Height);
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			g.FillRectangle(new SolidBrush(this._BaseColor), rectangle);
			string text = this.Text;
			System.Drawing.Font font = new System.Drawing.Font("Segoe UI", 8f);
			Brush black = Brushes.Black;
			Rectangle bounds = base.Bounds;
			int x = checked(bounds.X + 2);
			bounds = base.Bounds;
			int y = checked(bounds.Y + 2);
			int width = base.Bounds.Width;
			bounds = base.Bounds;
			g.DrawString(text, font, black, new Rectangle(x, y, width, bounds.Height), Helpers.NearSF);
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}
	}
}