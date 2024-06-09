using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Xh0kO1ZCmA
{
	internal class NSTabControl : TabControl
	{
		private GraphicsPath CreateRoundPath;

		private Rectangle CreateRoundRectangle;

		private GraphicsPath GP1;

		private GraphicsPath GP2;

		private GraphicsPath GP3;

		private GraphicsPath GP4;

		private Rectangle R1;

		private Rectangle R2;

		private Pen P1;

		private Pen P2;

		private Pen P3;

		private SolidBrush B1;

		private SolidBrush B2;

		private SolidBrush B3;

		private SolidBrush B4;

		private PathGradientBrush PB1;

		private TabPage TP1;

		private StringFormat SF1;

		private int Offset;

		private int ItemHeight;

		public NSTabControl()
		{
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			base.SetStyle(ControlStyles.Selectable, false);
			base.SizeMode = TabSizeMode.Fixed;
			base.Alignment = TabAlignment.Left;
			base.ItemSize = new System.Drawing.Size(28, 115);
			base.DrawMode = TabDrawMode.OwnerDrawFixed;
			this.P1 = new Pen(Color.FromArgb(55, 55, 55));
			this.P2 = new Pen(Color.FromArgb(24, 24, 24));
			this.P3 = new Pen(Color.FromArgb(45, 45, 45), 2f);
			this.B1 = new SolidBrush(Color.FromArgb(50, 50, 50));
			this.B2 = new SolidBrush(Color.FromArgb(24, 24, 24));
			this.B3 = new SolidBrush(Color.FromArgb(51, 181, 229));
			this.B4 = new SolidBrush(Color.FromArgb(65, 65, 65));
			this.SF1 = new StringFormat()
			{
				LineAlignment = StringAlignment.Center
			};
		}

		public GraphicsPath CreateRound(int x, int y, int width, int height, int slope)
		{
			this.CreateRoundRectangle = new Rectangle(x, y, width, height);
			return this.CreateRound(this.CreateRoundRectangle, slope);
		}

		public GraphicsPath CreateRound(Rectangle r, int slope)
		{
			this.CreateRoundPath = new GraphicsPath(FillMode.Winding);
			this.CreateRoundPath.AddArc(r.X, r.Y, slope, slope, 180f, 90f);
			this.CreateRoundPath.AddArc(checked(r.Right - slope), r.Y, slope, slope, 270f, 90f);
			this.CreateRoundPath.AddArc(checked(r.Right - slope), checked(r.Bottom - slope), slope, slope, 0f, 90f);
			this.CreateRoundPath.AddArc(r.X, checked(r.Bottom - slope), slope, slope, 90f, 90f);
			this.CreateRoundPath.CloseFigure();
			return this.CreateRoundPath;
		}

		protected override void OnControlAdded(ControlEventArgs e)
		{
			if (e.Control is TabPage)
			{
				e.Control.BackColor = Color.FromArgb(50, 50, 50);
			}
			base.OnControlAdded(e);
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.G = e.Graphics;
			Helpers.G.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			Helpers.G.Clear(Color.FromArgb(50, 50, 50));
			Helpers.G.SmoothingMode = SmoothingMode.AntiAlias;
			this.ItemHeight = checked(base.ItemSize.Height + 2);
			this.GP1 = this.CreateRound(0, 0, checked(this.ItemHeight + 3), checked(base.Height - 1), 7);
			this.GP2 = this.CreateRound(1, 1, checked(this.ItemHeight + 3), checked(base.Height - 3), 7);
			this.PB1 = new PathGradientBrush(this.GP1)
			{
				CenterColor = Color.FromArgb(50, 50, 50),
				SurroundColors = new Color[] { Color.FromArgb(45, 45, 45) },
				FocusScales = new PointF(0.8f, 0.95f)
			};
			Helpers.G.FillPath(this.PB1, this.GP1);
			Helpers.G.DrawPath(this.P1, this.GP1);
			Helpers.G.DrawPath(this.P2, this.GP2);
			int tabCount = checked(base.TabCount - 1);
			for (int i = 0; i <= tabCount; i = checked(i + 1))
			{
				this.R1 = base.GetTabRect(i);
				ref Rectangle r1 = ref this.R1;
				r1.Y = checked(r1.Y + 2);
				ref Rectangle height = ref this.R1;
				height.Height = checked(height.Height - 3);
				ref Rectangle width = ref this.R1;
				width.Width = checked(width.Width + 1);
				ref Rectangle x = ref this.R1;
				x.X = checked(x.X - 1);
				this.TP1 = base.TabPages[i];
				this.Offset = 0;
				if (base.SelectedIndex != i)
				{
					int num = 0;
					do
					{
						Helpers.G.FillRectangle(this.B2, checked(checked(this.R1.X + 5) + checked(num * 5)), checked(this.R1.Y + 6), 2, checked(this.R1.Height - 9));
						Helpers.G.SmoothingMode = SmoothingMode.None;
						Helpers.G.FillRectangle(this.B4, checked(checked(this.R1.X + 5) + checked(num * 5)), checked(this.R1.Y + 5), 2, checked(this.R1.Height - 9));
						Helpers.G.SmoothingMode = SmoothingMode.AntiAlias;
						ref int offset = ref this.Offset;
						offset = checked(offset + 5);
						num = checked(num + 1);
					}
					while (num <= 1);
				}
				else
				{
					Helpers.G.FillRectangle(this.B1, this.R1);
					int num1 = 0;
					do
					{
						Helpers.G.FillRectangle(this.B2, checked(checked(this.R1.X + 5) + checked(num1 * 5)), checked(this.R1.Y + 6), 2, checked(this.R1.Height - 9));
						Helpers.G.SmoothingMode = SmoothingMode.None;
						Helpers.G.FillRectangle(this.B3, checked(checked(this.R1.X + 5) + checked(num1 * 5)), checked(this.R1.Y + 5), 2, checked(this.R1.Height - 9));
						Helpers.G.SmoothingMode = SmoothingMode.AntiAlias;
						ref int numPointer = ref this.Offset;
						numPointer = checked(numPointer + 5);
						num1 = checked(num1 + 1);
					}
					while (num1 <= 1);
					Helpers.G.DrawRectangle(this.P3, checked(this.R1.X + 1), checked(this.R1.Y - 1), this.R1.Width, checked(this.R1.Height + 2));
					Helpers.G.DrawRectangle(this.P1, checked(this.R1.X + 1), checked(this.R1.Y + 1), checked(this.R1.Width - 2), checked(this.R1.Height - 2));
					Helpers.G.DrawRectangle(this.P2, this.R1);
				}
				ref Rectangle rectanglePointer = ref this.R1;
				rectanglePointer.X = checked(rectanglePointer.X + checked(5 + this.Offset));
				this.R2 = this.R1;
				ref Rectangle r2 = ref this.R2;
				r2.Y = checked(r2.Y + 1);
				ref Rectangle r21 = ref this.R2;
				r21.X = checked(r21.X + 1);
				Helpers.G.DrawString(this.TP1.Text, this.Font, Brushes.Black, this.R2, this.SF1);
				Helpers.G.DrawString(this.TP1.Text, this.Font, Brushes.WhiteSmoke, this.R1, this.SF1);
			}
			this.GP3 = this.CreateRound(this.ItemHeight, 0, checked(checked(base.Width - this.ItemHeight) - 1), checked(base.Height - 1), 7);
			this.GP4 = this.CreateRound(checked(this.ItemHeight + 1), 1, checked(checked(base.Width - this.ItemHeight) - 3), checked(base.Height - 3), 7);
			Helpers.G.DrawPath(this.P2, this.GP3);
			Helpers.G.DrawPath(this.P1, this.GP4);
		}
	}
}