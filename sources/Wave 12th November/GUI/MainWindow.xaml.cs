using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Runtime.InteropServices;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Interop;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Navigation;
using System.Windows.Shapes;
using Wave.Cmr;
using Wave.Cmr.Win32API;

namespace WaveClient.GUI
{
	// Token: 0x02000008 RID: 8
	public partial class MainWindow : Window
	{
		// Token: 0x17000004 RID: 4
		// (get) Token: 0x06000022 RID: 34 RVA: 0x00002A97 File Offset: 0x00000C97
		// (set) Token: 0x06000023 RID: 35 RVA: 0x00002A9F File Offset: 0x00000C9F
		private CompositionTarget WindowCompositionTarget { get; set; }

		// Token: 0x17000005 RID: 5
		// (get) Token: 0x06000024 RID: 36 RVA: 0x00002AA8 File Offset: 0x00000CA8
		// (set) Token: 0x06000025 RID: 37 RVA: 0x00002AB0 File Offset: 0x00000CB0
		private double CachedMinWidth { get; set; }

		// Token: 0x17000006 RID: 6
		// (get) Token: 0x06000026 RID: 38 RVA: 0x00002AB9 File Offset: 0x00000CB9
		// (set) Token: 0x06000027 RID: 39 RVA: 0x00002AC1 File Offset: 0x00000CC1
		private double CachedMinHeight { get; set; }

		// Token: 0x17000007 RID: 7
		// (get) Token: 0x06000028 RID: 40 RVA: 0x00002ACA File Offset: 0x00000CCA
		// (set) Token: 0x06000029 RID: 41 RVA: 0x00002AD2 File Offset: 0x00000CD2
		private Win32.POINT CachedMinTrackSize { get; set; }

		// Token: 0x0600002A RID: 42 RVA: 0x00002ADC File Offset: 0x00000CDC
		private IntPtr WindowProc(IntPtr hwnd, int msg, IntPtr wParam, IntPtr lParam, ref bool handled)
		{
			if (msg == 36)
			{
				Win32.MINMAXINFO minmaxinfo = (Win32.MINMAXINFO)Marshal.PtrToStructure(lParam, typeof(Win32.MINMAXINFO));
				IntPtr intPtr = Win32.MonitorFromWindow(hwnd, 2);
				if (intPtr != IntPtr.Zero)
				{
					Win32.MONITORINFO monitorinfo = new Win32.MONITORINFO();
					Win32.GetMonitorInfo(intPtr, monitorinfo);
					Win32.RECT rcWork = monitorinfo.rcWork;
					Win32.RECT rcMonitor = monitorinfo.rcMonitor;
					minmaxinfo.ptMaxPosition.x = Math.Abs(rcWork.left - rcMonitor.left);
					minmaxinfo.ptMaxPosition.y = Math.Abs(rcWork.top - rcMonitor.top);
					minmaxinfo.ptMaxSize.x = Math.Abs(rcWork.right - rcWork.left);
					minmaxinfo.ptMaxSize.y = Math.Abs(rcWork.bottom - rcWork.top);
					if (!this.CachedMinTrackSize.Equals(minmaxinfo.ptMinTrackSize) || (this.CachedMinHeight != base.MinHeight && this.CachedMinWidth != base.MinWidth))
					{
						minmaxinfo.ptMinTrackSize.x = (int)((this.CachedMinWidth = base.MinWidth) * this.WindowCompositionTarget.TransformToDevice.M11);
						minmaxinfo.ptMinTrackSize.y = (int)((this.CachedMinHeight = base.MinHeight) * this.WindowCompositionTarget.TransformToDevice.M22);
						this.CachedMinTrackSize = minmaxinfo.ptMinTrackSize;
					}
				}
				Marshal.StructureToPtr<Win32.MINMAXINFO>(minmaxinfo, lParam, true);
				handled = true;
			}
			return IntPtr.Zero;
		}

		// Token: 0x0600002B RID: 43 RVA: 0x00002C7C File Offset: 0x00000E7C
		public MainWindow()
		{
			this.InitializeComponent();
			base.SourceInitialized += delegate(object s, EventArgs e)
			{
				this.WindowCompositionTarget = PresentationSource.FromVisual(this).CompositionTarget;
				HwndSource.FromHwnd(new WindowInteropHelper(this).Handle).AddHook(new HwndSourceHook(this.WindowProc));
			};
			this.WelcomeScreen();
		}

		// Token: 0x0600002C RID: 44 RVA: 0x00002CA2 File Offset: 0x00000EA2
		private void Control_Close_MouseUp(object sender, MouseButtonEventArgs e)
		{
			base.Close();
			cmr.ExitApplication();
		}

		// Token: 0x0600002D RID: 45 RVA: 0x00002CAF File Offset: 0x00000EAF
		private void Control_Maximize_MouseUp(object sender, MouseButtonEventArgs e)
		{
			if (base.WindowState != WindowState.Maximized)
			{
				base.WindowState = WindowState.Maximized;
				return;
			}
			base.WindowState = WindowState.Normal;
		}

		// Token: 0x0600002E RID: 46 RVA: 0x00002CC9 File Offset: 0x00000EC9
		private void Control_Minimize_MouseUp(object sender, MouseButtonEventArgs e)
		{
			base.WindowState = WindowState.Minimized;
		}

		// Token: 0x0600002F RID: 47 RVA: 0x00002CD4 File Offset: 0x00000ED4
		public void WelcomeScreen()
		{
			DoubleAnimation animation = new DoubleAnimation(0.0, 1.0, new Duration(TimeSpan.FromMilliseconds(2500.0)));
			this.Content.BeginAnimation(UIElement.OpacityProperty, animation);
		}

		// Token: 0x06000030 RID: 48 RVA: 0x00002D20 File Offset: 0x00000F20
		private void NavigationFrame_Navigated(object sender, NavigatingCancelEventArgs e)
		{
			ThicknessAnimation thicknessAnimation = new ThicknessAnimation();
			thicknessAnimation.Duration = TimeSpan.FromSeconds(1.0);
			thicknessAnimation.EasingFunction = new QuadraticEase
			{
				EasingMode = EasingMode.EaseOut
			};
			thicknessAnimation.DecelerationRatio = 0.7;
			thicknessAnimation.To = new Thickness?(new Thickness(0.0, 0.0, 0.0, 0.0));
			if (e.NavigationMode == NavigationMode.New)
			{
				thicknessAnimation.From = new Thickness?(new Thickness(500.0, 500.0, 0.0, 0.0));
			}
			else if (e.NavigationMode == NavigationMode.Back)
			{
				thicknessAnimation.From = new Thickness?(new Thickness(0.0, 0.0, 500.0, 500.0));
			}
			DoubleAnimation doubleAnimation = new DoubleAnimation();
			doubleAnimation.To = new double?((double)1);
			doubleAnimation.From = new double?(0.0);
			thicknessAnimation.EasingFunction = new QuadraticEase
			{
				EasingMode = EasingMode.EaseOut
			};
			this.NavigationFrame.BeginAnimation(FrameworkElement.MarginProperty, thicknessAnimation);
			this.NavigationFrame.BeginAnimation(UIElement.OpacityProperty, doubleAnimation);
		}
	}
}
