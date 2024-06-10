using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Forms;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Threading;
using Eternium_mcpe_client.Data;
using Eternium_mcpe_client.DllImports;
using Eternium_mcpe_client.Hotkeys;
using Eternium_mcpe_client.Rainbow;

namespace Eternium_mcpe_client.Threads.GUI.Minecraft_screen.Overlay
{
	// Token: 0x0200000C RID: 12
	public class OverlayUserControl : System.Windows.Controls.UserControl, IComponentConnector
	{
		// Token: 0x0600002C RID: 44 RVA: 0x00004E60 File Offset: 0x00003060
		public OverlayUserControl()
		{
			this.InitializeComponent();
			this.ColorRGBTimer = new DispatcherTimer();
			this.ColorRGBTimer.Interval = TimeSpan.FromMilliseconds(10.0);
			this.ColorRGBTimer.Tick += this.ColorRGBTimer_Tick;
			this.ColorRGBTimer.Start();
		}

		// Token: 0x0600002D RID: 45 RVA: 0x00004ED0 File Offset: 0x000030D0
		private void ColorRGBTimer_Tick(object sender, EventArgs e)
		{
			System.Windows.Media.Color color = Eternium_mcpe_client.Rainbow.Color.WindowsCounterToColor(CommunicationData.GUI.ColorRGBCounter);
			SolidColorBrush colorbrush = new SolidColorBrush(color);
			this.UpdateGUIColor(color, colorbrush);
		}

		// Token: 0x0600002E RID: 46 RVA: 0x00004EFC File Offset: 0x000030FC
		public void UpdateGUIColor(System.Windows.Media.Color color, SolidColorBrush colorbrush)
		{
			this.A.Foreground = colorbrush;
			this.S.Foreground = colorbrush;
			this.D.Foreground = colorbrush;
			this.W.Foreground = colorbrush;
			this.space.Foreground = colorbrush;
			this.RMBbutton.Foreground = colorbrush;
			this.RMB.Foreground = colorbrush;
			this.LMB.Foreground = colorbrush;
			bool flag = System.Windows.Forms.Control.MouseButtons != MouseButtons.Left;
			if (flag)
			{
				this.LMBbutton.Background = Brushes.Black;
				this.LMBbutton.Opacity = 0.6;
			}
			else
			{
				this.LMBbutton.Background = Brushes.White;
				this.LMBbutton.Opacity = 0.6;
			}
			bool flag2 = System.Windows.Forms.Control.MouseButtons != MouseButtons.Right;
			if (flag2)
			{
				this.RMBbutton.Background = Brushes.Black;
				this.RMBbutton.Opacity = 0.6;
			}
			else
			{
				this.RMBbutton.Background = Brushes.White;
				this.RMBbutton.Opacity = 0.6;
			}
			bool keyStateDown = Hotkeys.GetKeyStateDown(DllImports.VirtualKeys.A);
			if (keyStateDown)
			{
				this.Abutton.Background = Brushes.White;
				this.Abutton.Opacity = 0.6;
			}
			else
			{
				this.Abutton.Background = Brushes.Black;
				this.Abutton.Opacity = 0.6;
			}
			bool keyStateDown2 = Hotkeys.GetKeyStateDown(DllImports.VirtualKeys.S);
			if (keyStateDown2)
			{
				this.Sbutton.Background = Brushes.White;
				this.Sbutton.Opacity = 0.6;
			}
			else
			{
				this.Sbutton.Background = Brushes.Black;
				this.Sbutton.Opacity = 0.6;
			}
			bool keyStateDown3 = Hotkeys.GetKeyStateDown(DllImports.VirtualKeys.D);
			if (keyStateDown3)
			{
				this.Dbutton.Background = Brushes.White;
				this.Dbutton.Opacity = 0.6;
			}
			else
			{
				this.Dbutton.Background = Brushes.Black;
				this.Dbutton.Opacity = 0.6;
			}
			bool keyStateDown4 = Hotkeys.GetKeyStateDown(DllImports.VirtualKeys.W);
			if (keyStateDown4)
			{
				this.Wbutton.Background = Brushes.White;
				this.Wbutton.Opacity = 0.6;
			}
			else
			{
				this.Wbutton.Background = Brushes.Black;
				this.Wbutton.Opacity = 0.6;
			}
			bool keyStateDown5 = Hotkeys.GetKeyStateDown(DllImports.VirtualKeys.Space);
			if (keyStateDown5)
			{
				this.spacebutton.Background = Brushes.White;
				this.spacebutton.Opacity = 0.6;
			}
			else
			{
				this.spacebutton.Background = Brushes.Black;
				this.spacebutton.Opacity = 0.6;
			}
		}

		// Token: 0x0600002F RID: 47 RVA: 0x00005214 File Offset: 0x00003414
		[DebuggerNonUserCode]
		[GeneratedCode("PresentationBuildTasks", "4.0.0.0")]
		public void InitializeComponent()
		{
			bool contentLoaded = this._contentLoaded;
			if (!contentLoaded)
			{
				this._contentLoaded = true;
				Uri resourceLocator = new Uri("/Eternium;component/gui/minecraft%20screen/overlay/minecraftscreen.xaml", UriKind.Relative);
				System.Windows.Application.LoadComponent(this, resourceLocator);
			}
		}

		// Token: 0x06000030 RID: 48 RVA: 0x0000524C File Offset: 0x0000344C
		[DebuggerNonUserCode]
		[GeneratedCode("PresentationBuildTasks", "4.0.0.0")]
		[EditorBrowsable(EditorBrowsableState.Never)]
		void IComponentConnector.Connect(int connectionId, object target)
		{
			switch (connectionId)
			{
			case 1:
				this.Text = (TextBlock)target;
				break;
			case 2:
				this.Abutton = (System.Windows.Controls.Button)target;
				break;
			case 3:
				this.A = (TextBlock)target;
				break;
			case 4:
				this.Sbutton = (System.Windows.Controls.Button)target;
				break;
			case 5:
				this.S = (TextBlock)target;
				break;
			case 6:
				this.Dbutton = (System.Windows.Controls.Button)target;
				break;
			case 7:
				this.D = (TextBlock)target;
				break;
			case 8:
				this.Wbutton = (System.Windows.Controls.Button)target;
				break;
			case 9:
				this.W = (TextBlock)target;
				break;
			case 10:
				this.spacebutton = (System.Windows.Controls.Button)target;
				break;
			case 11:
				this.space = (TextBlock)target;
				break;
			case 12:
				this.LMBbutton = (System.Windows.Controls.Button)target;
				break;
			case 13:
				this.LMB = (TextBlock)target;
				break;
			case 14:
				this.RMBbutton = (System.Windows.Controls.Button)target;
				break;
			case 15:
				this.RMB = (TextBlock)target;
				break;
			case 16:
				this.ModuleList = (TextBlock)target;
				break;
			default:
				this._contentLoaded = true;
				break;
			}
		}

		// Token: 0x0400002A RID: 42
		private DispatcherTimer ColorRGBTimer;

		// Token: 0x0400002B RID: 43
		private DispatcherTimer dispatcherTimer = new DispatcherTimer();

		// Token: 0x0400002C RID: 44
		internal TextBlock Text;

		// Token: 0x0400002D RID: 45
		internal System.Windows.Controls.Button Abutton;

		// Token: 0x0400002E RID: 46
		internal TextBlock A;

		// Token: 0x0400002F RID: 47
		internal System.Windows.Controls.Button Sbutton;

		// Token: 0x04000030 RID: 48
		internal TextBlock S;

		// Token: 0x04000031 RID: 49
		internal System.Windows.Controls.Button Dbutton;

		// Token: 0x04000032 RID: 50
		internal TextBlock D;

		// Token: 0x04000033 RID: 51
		internal System.Windows.Controls.Button Wbutton;

		// Token: 0x04000034 RID: 52
		internal TextBlock W;

		// Token: 0x04000035 RID: 53
		internal System.Windows.Controls.Button spacebutton;

		// Token: 0x04000036 RID: 54
		internal TextBlock space;

		// Token: 0x04000037 RID: 55
		internal System.Windows.Controls.Button LMBbutton;

		// Token: 0x04000038 RID: 56
		internal TextBlock LMB;

		// Token: 0x04000039 RID: 57
		internal System.Windows.Controls.Button RMBbutton;

		// Token: 0x0400003A RID: 58
		internal TextBlock RMB;

		// Token: 0x0400003B RID: 59
		internal TextBlock ModuleList;

		// Token: 0x0400003C RID: 60
		private bool _contentLoaded;
	}
}
