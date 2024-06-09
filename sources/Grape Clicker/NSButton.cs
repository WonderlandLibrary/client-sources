using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Xh0kO1ZCmA
{
	internal class NSButton : Control
	{
		private GraphicsPath CreateRoundPath;

		private Rectangle CreateRoundRectangle;

		private bool IsMouseDown;

		private GraphicsPath GP1;

		private GraphicsPath GP2;

		private SizeF SZ1;

		private PointF PT1;

		private Pen P1;

		private Pen P2;

		private PathGradientBrush PB1;

		private LinearGradientBrush GB1;

		public NSButton()
		{
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			base.SetStyle(ControlStyles.Selectable, false);
			this.P1 = new Pen(Color.FromArgb(24, 24, 24));
			this.P2 = new Pen(Color.FromArgb(65, 65, 65));
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

		protected override void OnMouseDown(MouseEventArgs e)
		{
			this.IsMouseDown = true;
			base.Invalidate();
		}

		protected override void OnMouseUp(MouseEventArgs e)
		{
			this.IsMouseDown = false;
			base.Invalidate();
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.G = e.Graphics;
			Helpers.G.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			Helpers.G.Clear(this.BackColor);
			Helpers.G.SmoothingMode = SmoothingMode.AntiAlias;
			this.GP1 = this.CreateRound(0, 0, checked(base.Width - 1), checked(base.Height - 1), 7);
			this.GP2 = this.CreateRound(1, 1, checked(base.Width - 3), checked(base.Height - 3), 7);
			if (!this.IsMouseDown)
			{
				this.GB1 = new LinearGradientBrush(base.ClientRectangle, Color.FromArgb(60, 60, 60), Color.FromArgb(55, 55, 55), 90f);
				Helpers.G.FillPath(this.GB1, this.GP1);
			}
			else
			{
				this.PB1 = new PathGradientBrush(this.GP1)
				{
					CenterColor = Color.FromArgb(60, 60, 60),
					SurroundColors = new Color[] { Color.FromArgb(55, 55, 55) },
					FocusScales = new PointF(0.8f, 0.5f)
				};
				Helpers.G.FillPath(this.PB1, this.GP1);
			}
			Helpers.G.DrawPath(this.P1, this.GP1);
			Helpers.G.DrawPath(this.P2, this.GP2);
			this.SZ1 = Helpers.G.MeasureString(this.Text, this.Font);
			this.PT1 = new PointF(5f, (float)(base.Height / 2) - this.SZ1.Height / 2f);
			if (this.IsMouseDown)
			{
				ref PointF pT1 = ref this.PT1;
				pT1.X = pT1.X + 1f;
				ref PointF y = ref this.PT1;
				y.Y = y.Y + 1f;
			}
			Helpers.G.DrawString(this.Text, this.Font, Brushes.Black, this.PT1.X + 1f, this.PT1.Y + 1f);
			Helpers.G.DrawString(this.Text, this.Font, Brushes.WhiteSmoke, this.PT1);
		}
	}
}