using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

namespace AutoClicker
{
	// Token: 0x0200000D RID: 13
	public partial class SelectionForm : Form
	{
		// Token: 0x17000012 RID: 18
		// (get) Token: 0x06000089 RID: 137 RVA: 0x0000969F File Offset: 0x0000789F
		// (set) Token: 0x0600008A RID: 138 RVA: 0x000096A7 File Offset: 0x000078A7
		public MainForm InstanceRef
		{
			get
			{
				return this.instanceRef;
			}
			set
			{
				this.instanceRef = value;
			}
		}

		// Token: 0x0600008B RID: 139 RVA: 0x000096B0 File Offset: 0x000078B0
		public SelectionForm(MainForm instanceRef)
		{
			this.InitializeComponent();
			this.InstanceRef = instanceRef;
			base.MouseDown += this.HandleMouseClick;
			base.MouseMove += this.HandleMouseMove;
			base.MouseUp += this.HandleMouseUp;
			base.Left = 0;
			base.Top = 0;
			int num = 0;
			int num2 = 0;
			foreach (Screen screen in Screen.AllScreens)
			{
				if (screen.Bounds.Height > num2)
				{
					num2 = screen.Bounds.Height;
				}
				num += screen.Bounds.Width;
			}
			base.Width = num;
			base.Height = num2;
			this.g = base.CreateGraphics();
		}

		// Token: 0x0600008C RID: 140 RVA: 0x000097E4 File Offset: 0x000079E4
		private void HandleMouseClick(object sender, MouseEventArgs e)
		{
			if (e.Button == MouseButtons.Left)
			{
				this.ClickPoint = new Point(Control.MousePosition.X, Control.MousePosition.Y);
				this.LeftButtonDown = true;
			}
		}

		// Token: 0x0600008D RID: 141 RVA: 0x0000982C File Offset: 0x00007A2C
		private void HandleMouseUp(object sender, MouseEventArgs e)
		{
			this.instanceRef.SendRectangle(this.CurrentTopLeft.X, this.CurrentTopLeft.Y, this.CurrentBottomRight.X - this.CurrentTopLeft.X, this.CurrentBottomRight.Y - this.CurrentTopLeft.Y);
			base.Close();
		}

		// Token: 0x0600008E RID: 142 RVA: 0x0000988E File Offset: 0x00007A8E
		private void HandleMouseMove(object sender, MouseEventArgs e)
		{
			if (this.LeftButtonDown)
			{
				this.DrawSelection();
			}
		}

		// Token: 0x0600008F RID: 143 RVA: 0x000098A0 File Offset: 0x00007AA0
		private void DrawSelection()
		{
			this.g.DrawRectangle(this.EraserPen, this.CurrentTopLeft.X, this.CurrentTopLeft.Y, this.CurrentBottomRight.X - this.CurrentTopLeft.X, this.CurrentBottomRight.Y - this.CurrentTopLeft.Y);
			if (Cursor.Position.X < this.ClickPoint.X)
			{
				this.CurrentTopLeft.X = Cursor.Position.X;
				this.CurrentBottomRight.X = this.ClickPoint.X;
			}
			else
			{
				this.CurrentTopLeft.X = this.ClickPoint.X;
				this.CurrentBottomRight.X = Cursor.Position.X;
			}
			if (Cursor.Position.Y < this.ClickPoint.Y)
			{
				this.CurrentTopLeft.Y = Cursor.Position.Y;
				this.CurrentBottomRight.Y = this.ClickPoint.Y;
			}
			else
			{
				this.CurrentTopLeft.Y = this.ClickPoint.Y;
				this.CurrentBottomRight.Y = Cursor.Position.Y;
			}
			this.g.DrawRectangle(this.BlackPen, this.CurrentTopLeft.X, this.CurrentTopLeft.Y, this.CurrentBottomRight.X - this.CurrentTopLeft.X, this.CurrentBottomRight.Y - this.CurrentTopLeft.Y);
		}

		// Token: 0x040000B8 RID: 184
		public bool LeftButtonDown;

		// Token: 0x040000B9 RID: 185
		public bool RectangleDrawn;

		// Token: 0x040000BA RID: 186
		public bool ReadyToDrag;

		// Token: 0x040000BB RID: 187
		public Point ClickPoint;

		// Token: 0x040000BC RID: 188
		public Point CurrentTopLeft;

		// Token: 0x040000BD RID: 189
		public Point CurrentBottomRight;

		// Token: 0x040000BE RID: 190
		public Point DragClickRelative;

		// Token: 0x040000BF RID: 191
		public int RectangleHeight;

		// Token: 0x040000C0 RID: 192
		public int RectangleWidth;

		// Token: 0x040000C1 RID: 193
		private Graphics g;

		// Token: 0x040000C2 RID: 194
		private Pen BlackPen = new Pen(Color.Black, 1f);

		// Token: 0x040000C3 RID: 195
		private SolidBrush TransparentBrush = new SolidBrush(Color.White);

		// Token: 0x040000C4 RID: 196
		private Pen EraserPen = new Pen(Color.FromArgb(51, 153, 255), 1f);

		// Token: 0x040000C5 RID: 197
		private SolidBrush EraserBrush = new SolidBrush(Color.FromArgb(51, 153, 255));

		// Token: 0x040000C6 RID: 198
		private MainForm instanceRef;
	}
}
