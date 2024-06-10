using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace GHOSTBYTES
{
	// Token: 0x0200002C RID: 44
	[DesignerGenerated]
	public partial class Arraylist : Form
	{
		// Token: 0x06000229 RID: 553 RVA: 0x0000A6F4 File Offset: 0x000088F4
		public Arraylist()
		{
			base.MouseDown += this.Form1_MouseDown;
			base.MouseUp += this.Form1_MouseUp;
			base.MouseMove += this.Form1_MouseMove;
			base.Load += this.Arraylist_Load;
			this.IsFormBeingDragged = false;
			this.InitializeComponent();
		}

		// Token: 0x170000BC RID: 188
		// (get) Token: 0x0600022C RID: 556 RVA: 0x00003CFF File Offset: 0x00001EFF
		// (set) Token: 0x0600022D RID: 557 RVA: 0x00003D07 File Offset: 0x00001F07
		internal virtual Label Label1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000BD RID: 189
		// (get) Token: 0x0600022E RID: 558 RVA: 0x00003D10 File Offset: 0x00001F10
		// (set) Token: 0x0600022F RID: 559 RVA: 0x00003D18 File Offset: 0x00001F18
		internal virtual Label MisplaceOn { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000BE RID: 190
		// (get) Token: 0x06000230 RID: 560 RVA: 0x00003D21 File Offset: 0x00001F21
		// (set) Token: 0x06000231 RID: 561 RVA: 0x00003D29 File Offset: 0x00001F29
		internal virtual Label VelocityOn { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000BF RID: 191
		// (get) Token: 0x06000232 RID: 562 RVA: 0x00003D32 File Offset: 0x00001F32
		// (set) Token: 0x06000233 RID: 563 RVA: 0x00003D3A File Offset: 0x00001F3A
		internal virtual Label FastplaceOn { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000C0 RID: 192
		// (get) Token: 0x06000234 RID: 564 RVA: 0x00003D43 File Offset: 0x00001F43
		// (set) Token: 0x06000235 RID: 565 RVA: 0x00003D4B File Offset: 0x00001F4B
		internal virtual Label AutoclickerOn { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000C1 RID: 193
		// (get) Token: 0x06000236 RID: 566 RVA: 0x00003D54 File Offset: 0x00001F54
		// (set) Token: 0x06000237 RID: 567 RVA: 0x00003D5C File Offset: 0x00001F5C
		internal virtual Label StrafespeedOn { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000C2 RID: 194
		// (get) Token: 0x06000238 RID: 568 RVA: 0x00003D65 File Offset: 0x00001F65
		// (set) Token: 0x06000239 RID: 569 RVA: 0x00003D6D File Offset: 0x00001F6D
		internal virtual Label AimAssistOn { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x0600023A RID: 570 RVA: 0x00003D76 File Offset: 0x00001F76
		private void Form1_MouseDown(object sender, MouseEventArgs e)
		{
			if (e.Button == MouseButtons.Left)
			{
				this.IsFormBeingDragged = true;
				this.MouseDownX = e.X;
				this.MouseDownY = e.Y;
			}
		}

		// Token: 0x0600023B RID: 571 RVA: 0x00003DA4 File Offset: 0x00001FA4
		private void Form1_MouseUp(object sender, MouseEventArgs e)
		{
			if (e.Button == MouseButtons.Left)
			{
				this.IsFormBeingDragged = false;
			}
		}

		// Token: 0x0600023C RID: 572 RVA: 0x0000ACF8 File Offset: 0x00008EF8
		private void Form1_MouseMove(object sender, MouseEventArgs e)
		{
			if (this.IsFormBeingDragged)
			{
				base.Location = checked(new Point
				{
					X = base.Location.X + (e.X - this.MouseDownX),
					Y = base.Location.Y + (e.Y - this.MouseDownY)
				});
				Point point = default(Point);
			}
		}

		// Token: 0x0600023D RID: 573 RVA: 0x0000AD6C File Offset: 0x00008F6C
		private void Arraylist_Load(object sender, EventArgs e)
		{
			base.Location = new Point(0, 0);
			this.InitialStyle = Arraylist.GetWindowLong(base.Handle, Arraylist.GWL.ExStyle);
			this.PercentVisible = 0.8m;
			Arraylist.SetWindowLong(base.Handle, Arraylist.GWL.ExStyle, (Arraylist.WS_EX)(this.InitialStyle | 524288 | 32));
			Arraylist.SetLayeredWindowAttributes(base.Handle, 0, Convert.ToByte(decimal.Multiply(255m, this.PercentVisible)), Arraylist.LWA.Alpha);
			base.TopMost = true;
		}

		// Token: 0x0600023E RID: 574
		[DllImport("user32.dll")]
		public static extern int GetWindowLong(IntPtr hWnd, Arraylist.GWL nIndex);

		// Token: 0x0600023F RID: 575
		[DllImport("user32.dll")]
		public static extern int SetWindowLong(IntPtr hWnd, Arraylist.GWL nIndex, Arraylist.WS_EX dwNewLong);

		// Token: 0x06000240 RID: 576
		[DllImport("user32.dll")]
		public static extern bool SetLayeredWindowAttributes(IntPtr hWnd, int crKey, byte alpha, Arraylist.LWA dwFlags);

		// Token: 0x040000D4 RID: 212
		private int InitialStyle;

		// Token: 0x040000D5 RID: 213
		private decimal PercentVisible;

		// Token: 0x040000D6 RID: 214
		private bool IsFormBeingDragged;

		// Token: 0x040000D7 RID: 215
		private int MouseDownX;

		// Token: 0x040000D8 RID: 216
		private int MouseDownY;

		// Token: 0x0200002D RID: 45
		public enum GWL
		{
			// Token: 0x040000DA RID: 218
			ExStyle = -20
		}

		// Token: 0x0200002E RID: 46
		public enum WS_EX
		{
			// Token: 0x040000DC RID: 220
			Transparent = 32,
			// Token: 0x040000DD RID: 221
			Layered = 524288
		}

		// Token: 0x0200002F RID: 47
		public enum LWA
		{
			// Token: 0x040000DF RID: 223
			ColorKey = 1,
			// Token: 0x040000E0 RID: 224
			Alpha
		}
	}
}
