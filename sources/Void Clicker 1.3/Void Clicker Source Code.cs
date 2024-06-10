::Void Discord: https://discord.gg/n9RaDpFbkM
::Also sub to my channel: https://www.youtube.com/channel/UCQmSdPYWvkXJHs0e6nHA3ww

          <# :
@echo off

::                             Like my code?

set bind_UP=W
set bind_DOWN=S
set bind_LEFT=A
set bind_RIGHT=D
set bind_SWAPBUTTON=Q
set bind_TOGGLEBUTTON=E
set bind_START=V

set "bind_STOP=2E"
set "bind_TOGGLE=2D"
set "bind_HIDE=23"

setlocal enableDelayedExpansion
set "debug=false"
set "version=1.3"
set title= 
set minABS=5
set maxABS=24
set jitterMAX=10
set left_cps[1]=8
set left_cps[2]=11
set right_cps[1]=8
set right_cps[2]=11
set left_jitter=0
set right_jitter=0
set leftMouse=1
set rightMouse=0
set blockhitChance=0
set "leftMCOnly=false"
set "rightMCOnly=false"
set sel=1
set col=1
title Void Clicker 1.3  ^|  www.cheat.ga
if "%debug%" NEQ "true" mode 61, 25

set "slid[5]=[40;35m  {[40;37m>>>>>___________________[40;35m}    [40;37m"
set "slid[6]=[40;35m  {[40;37m>>>>>>__________________[40;35m}    [40;37m"
set "slid[7]=[40;35m  {[40;37m>>>>>>>_________________[40;35m}    [40;37m"
set "slid[8]=[40;35m  {[40;37m>>>>>>>>________________[40;35m}    [40;37m"
set "slid[9]=[40;35m  {[40;37m>>>>>>>>>_______________[40;35m}    [40;37m"
set "slid[10]=[40;35m  {[40;37m>>>>>>>>>>______________[40;35m}    [40;37m"
set "slid[11]=[40;35m  {[40;37m>>>>>>>>>>>_____________[40;35m}    [40;37m"
set "slid[12]=[40;35m  {[40;37m>>>>>>>>>>>>____________[40;35m}    [40;37m"
set "slid[13]=[40;35m  {[40;37m>>>>>>>>>>>>>___________[40;35m}    [40;37m"
set "slid[14]=[40;35m  {[40;37m>>>>>>>>>>>>>>__________[40;35m}    [40;37m"
set "slid[15]=[40;35m  {[40;37m>>>>>>>>>>>>>>>_________[40;35m}    [40;37m"
set "slid[16]=[40;35m  {[40;37m>>>>>>>>>>>>>>>>________[40;35m}    [40;37m"
set "slid[17]=[40;35m  {[40;37m>>>>>>>>>>>>>>>>>_______[40;35m}    [40;37m"
set "slid[18]=[40;35m  {[40;37m>>>>>>>>>>>>>>>>>>______[40;35m}    [40;37m"
set "slid[19]=[40;35m  {[40;37m>>>>>>>>>>>>>>>>>>>_____[40;35m}    [40;37m"
set "slid[20]=[40;35m  {[40;37m>>>>>>>>>>>>>>>>>>>>____[40;35m}    [40;37m"
set "slid[21]=[40;35m  {[40;37m>>>>>>>>>>>>>>>>>>>>>___[40;35m}    [40;37m"
set "slid[22]=[40;35m  {[40;37m>>>>>>>>>>>>>>>>>>>>>>__[40;35m}    [40;37m"
set "slid[23]=[40;35m  {[40;37m>>>>>>>>>>>>>>>>>>>>>>>_[40;35m}    [40;37m"
set "slid[24]=[40;35m  {[40;37m>>>>>>>>>>>>>>>>>>>>>>>>[40;35m}    [40;37m"
set "slid[25]=[40;35m  {[40;37m>>>>>>>>>>>>>>>>>>>>>>>>[40;35m}    [40;37m"

set [reset]=[0m
set [inverse]=[45;37m

echo.
echo =============================================================
echo.
echo                      [40;35m _    __      _     __   
echo                    [40;35m  ^| ^|  / /___  (_)___/ /   
echo                    [40;35m  ^| ^| / / __ \/ / __  /    
echo                    [40;35m  ^| ^|/ / /_/ / / /_/ /     
echo               [40;35m  _____^|___/\____/_/\__,_/      
echo              [40;35m  / ____/ (_)____/ /_____  _____ 
echo              [40;35m / /   / / / ___/ //_/ _ \/ ___/ 
echo             [40;35m / /___/ / / /__/ ,\ /  __/ /     
echo            [40;35m  \____/_/_/\___/_/^|_^|\___/_/  version 1.3[40;37m     
echo.
echo =============================================================
echo.
echo                            Credits:
echo.
echo                Vylx/mp3#6239 - Creator / Developer
echo.
echo                    exro#4981 - Inspired GUI            
echo.
echo.
echo.

echo [45;37m
pause
echo [40;37m
 
:main
call :menu
choice /c %bind_UP%%bind_DOWN%%bind_LEFT%%bind_RIGHT%%bind_START%%bind_SWAPBUTTON%%bind_TOGGLEBUTTON% /n
echo %ERRORLEVEL%
if %ERRORLEVEL%==1 (
  if %col%==2 (if %sel%==5 (set /a sel-=2) else (if %sel% NEQ 1 set /a sel-=1)) else (if %sel% NEQ 1 set /a sel-=1)
)
if %ERRORLEVEL%==2 (
  if %col%==2 (if %sel%==3 (set /a sel+=2) else (if %sel% NEQ 5 set /a sel+=1)) else (if %sel% NEQ 5 set /a sel+=1)
)

if %ERRORLEVEL%==3 (
  if %sel%==1 (
    if %col%==1 (if !left_cps[%sel%]! NEQ %minABS% set /a left_cps[%sel%]-=1) else (if !right_cps[%sel%]! NEQ %minABS% set /a right_cps[%sel%]-=1)
  )
  if %sel%==2 (
    if %col%==1 (if %left_cps[2]% NEQ %left_cps[1]% (if !left_cps[%sel%]! NEQ %minABS% set /a left_cps[%sel%]-=1)) else (if %right_cps[2]% NEQ %right_cps[1]% (if !right_cps[%sel%]! NEQ %minABS% set /a right_cps[%sel%]-=1))
  )
  if %sel%==3 (
    if %col%==1 (if %left_jitter% NEQ 0 set /a left_jitter-=1) else (if %right_jitter% NEQ 0 set /a right_jitter-=1)
  )
  if %sel%==4 (
    if %blockhitChance% NEQ 0 set /a blockhitChance-=1
  )
  if %sel%==5 (
    if %col%==1 (if "%leftMCOnly%"=="true" (set "leftMCOnly=false") else (set "leftMCOnly=true")) else (if "%rightMCOnly%"=="true" (set "rightMCOnly=false") else (set "rightMCOnly=true"))
  )
)
if %ERRORLEVEL%==4 (
  if %sel%==1 (
    if %col%==1 (if %left_cps[2]% NEQ %left_cps[1]% (if !left_cps[%sel%]! NEQ %maxABS% set /a left_cps[%sel%]+=1)) else (if %right_cps[2]% NEQ %right_cps[1]% (if !right_cps[%sel%]! NEQ %maxABS% set /a right_cps[%sel%]+=1))
  )
  if %sel%==2 (
    if %col%==1 (if !left_cps[%sel%]! NEQ %maxABS% set /a left_cps[%sel%]+=1) else (if !right_cps[%sel%]! NEQ %maxABS% set /a right_cps[%sel%]+=1)
  )
  if %sel%==3 (
    if %col%==1 (if %left_jitter% NEQ %jitterMAX% set /a left_jitter+=1) else (if %right_jitter% NEQ %jitterMAX% set /a right_jitter+=1)
  )
  if %sel%==4 (
    if %blockhitChance% NEQ 20 set /a blockhitChance+=1
  )
  if %sel%==5 (
    if %col%==1 (if "%leftMCOnly%"=="true" (set "leftMCOnly=false") else (set "leftMCOnly=true")) else (if "%rightMCOnly%"=="true" (set "rightMCOnly=false") else (set "rightMCOnly=true"))
  )
)

if %ERRORLEVEL%==6 (
  if %col%==1 (if %sel%==4 (set col=2 && set sel=3) else (set col=2)) else (set col=1)
)
if %ERRORLEVEL%==7 (
  if %col%==1 (if %leftMouse%==0 (set leftMouse=1) else (set leftMouse=0)) else (if %rightMouse%==0 (set rightMouse=1) else (set rightMouse=0))
)

if %ERRORLEVEL%==5 (
  if %leftMouse%==1 (set "lmb=true") else (set "lmb=false")
  if %rightMouse%==1 (set "rmb=true") else (set "rmb=false")
)
if %ERRORLEVEL%==5 (
  call :startac %lmb% %rmb% %left_cps[1]% %left_cps[2]% %right_cps[1]% %right_cps[2]% %left_jitter% %right_jitter% %blockhitChance% %leftMCOnly% %rightMCOnly% %bind_STOP% %bind_TOGGLE% %bind_HIDE%
  if "%debug%"=="true" pause>nul
)
goto main

:menu
cls
       
echo              [45;37mVoid Clicker    ^|    version 1.3[40;37m
echo.
echo. 
if %col%==1 (if %leftMouse%==0 (echo                       LMB    ^|    DISABLED) else (echo                       LMB    ^|    %[inverse]%ENABLED%[reset]%)) else (if %rightMouse%==0 (echo                       RMB    ^|    DISABLED) else (echo                       RMB    ^|    %[inverse]%ENABLED%[reset]%))
echo.  
if %sel%==1 (echo            %[inverse]%MINIMUM CPS%[reset]%) else (echo            MINIMUM CPS)
if %col%==1 (echo  !slid[%left_cps[1]%]! %left_cps[1]% CPS) else (echo  !slid[%right_cps[1]%]! %right_cps[1]% CPS)
echo.
if %sel%==2 (echo            %[inverse]%MAXIMUM CPS%[reset]%) else (echo            MAXIMUM CPS)
if %col%==1 (echo  !slid[%left_cps[2]%]! %left_cps[2]% CPS) else (echo  !slid[%right_cps[2]%]! %right_cps[2]% CPS)
echo.
if %sel%==3 (echo            %[inverse]%JITTER EFFECT%[reset]%) else (echo            JITTER EFFECT)
if %col%==1 (
  set /a j=%left_jitter% * 2
  set /a j+=5
) else (
  set /a j=%right_jitter% * 2
  set /a j+=5
)
if %col%==1 (echo  !slid[%j%]! %left_jitter% STRENGTH) else (echo  !slid[%j%]! %right_jitter% STRENGTH)
echo.
if %col%==1 (
  if %sel%==4 (echo            %[inverse]%BLOCKHIT CHANCE%[reset]%) else (echo            BLOCKHIT CHANCE)
  set /a bh=%blockhitChance% + 5
  set /a bhc=%blockhitChance% * 5
)
if %col%==1 (echo  !slid[%bh%]! %bhc% %% && echo.)

if %sel%==5 (
  if %col%==1 (
    if "%leftMCOnly%"=="true" (echo  [40;35m  {[40;37mYes[40;35m}[40;37m    %[inverse]%MINECRAFT ONLY%[reset]%) else (echo  [40;35m  {[40;37mNo[40;35m}[40;37m    %[inverse]%MINECRAFT ONLY%[reset]%)
  ) else (
    if "%rightMCOnly%"=="true" (echo  [40;35m  {[40;37mYes[40;35m}[40;37m    %[inverse]%MINECRAFT ONLY%[reset]%) else (echo  [40;35m  {[40;37mNo[40;35m}[40;37m    %[inverse]%MINECRAFT ONLY%[reset]%)
  )
) else (
  if %col%==1 (
    if "%leftMCOnly%"=="true" (echo  [40;35m  {[40;37mYes[40;35m}[40;37m   MINECRAFT ONLY) else (echo  [40;35m  {[40;37mNo[40;35m}[40;37m    MINECRAFT ONLY)
  ) else (
    if "%rightMCOnly%"=="true" (echo  [40;35m  {[40;37mYes[40;35m}[40;37m    MINECRAFT ONLY) else (echo  [40;35m  {[40;37mNo[40;35m}[40;37m    MINECRAFT ONLY)
  )
)
echo.
echo.
echo        [45;37mPress "V" to start, and "E" to enable/disable[40;37m
echo.
echo.
echo [45;37mPress "Q" to switch between LMB or RMB and "WASD" to navigate[40;37m
goto :eof

:values
cls
echo            [45;37mVoid Clicker    ^|    version 1.2[40;37m
::echo      
::echo      
::echo      
echo.
echo.
if %leftMouse%==0 (
  if rightMouse==0 (echo                LEFT        RIGHT) else (echo                LEFT        %[inverse]%RIGHT%[reset]%)
) else (
  if rightMouse==0 (echo                %[inverse]%LEFT%[reset]%        RIGHT) else (echo                %[inverse]%LEFT%[reset]%        %[inverse]%RIGHT%[reset]%)
)
echo.
echo  MINIMUM CPS        %~3          %~5
echo.
echo  MAXIMUM CPS        %~4          %~6
echo.
echo  JITTER EFFECT         %~7           %~8
echo.
set /a bhc=%blockhitChance% * 5
echo  BLOCKHIT       %bhc%%%        N/A
echo.
if "%leftMCOnly%"=="false" (
  if "%rightMCOnly%"=="false" (echo  MC ONLY        DISABLED         OFF) else (echo  MINECRAFT ONLY       OFF         ON)
) else  
  if "%rightMCOnly%"=="false" (echo  MC ONLY        ENABLED          OFF) else (echo  MINECRAFT ONLY        ENABLED          ON)
)
goto :eof

:startac
cls
echo              [45;37mVoid Clicker    ^|    version 1.3[40;37m
::echo      
::echo      
::echo       
echo.
echo.
if %leftMouse%==0 (
  if rightMouse==0 (echo                     LMB        RMB) else (echo                       LMB        RMB)
) else (
  if rightMouse==0 (echo                     LMB        RMB) else (echo                       LMB        RMB)
)
:hi
echo.
echo  MINIMUM CPS :         %~3          %~5
echo [40;35m=============================================================[40;37m
echo  MAXIMUM CPS :         %~4         %~6
echo [40;35m=============================================================[40;37m
echo  JITTER EFFECT :       %~7          %~8
echo [40;35m=============================================================[40;37m
set /a bhc=%blockhitChance%  * 5
echo  BLOCKHIT :            %bhc%%%         N/A
echo [40;35m=============================================================[40;37m
if "%leftMCcOnly%"=="false" (
  if "%rightMCOnly%"=="false" (echo  MINECRAFT ONLY :      OFF        OFF) else (echo  MINECRAFT ONLY :      OFF        ON)
) else (
  if "%rightMCOnly%"=="false" (echo  MINECRAFT ONLY :      ON         OFF) else (echo  MINECRAFT ONLY :      ON         ON)
)
echo.
echo.
echo.
echo                     [40;35mVoid Clicker[40;37m Loading...
echo.
echo.                   Created by Vylx/mp3#6239            

setlocal
set "POWERSHELL_BAT_ARGS=%*"
if defined POWERSHELL_BAT_ARGS set "POWERSHELL_BAT_ARGS=%POWERSHELL_BAT_ARGS:"=\"%"
endlocal & powershell -NoLogo -NoProfile -Command "$_ = $input; Invoke-Expression $( '$input = $_; $_ = \"\"; $args = @( &{ $args } %POWERSHELL_BAT_ARGS% );' + [String]::Join( [char]10, $( Get-Content \"%~f0\" ) ) )"
goto :EOF
#>

$code = @"
using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Threading;
using System.Diagnostics;
using System.Globalization;
namespace Auto
{
	public class Program
	{
    [DllImport("user32.dll")]
    private static extern short GetAsyncKeyState(System.Int32 vKey);
    [DllImport("user32.dll", CharSet = CharSet.Auto, ExactSpelling = true)]
    private static extern IntPtr GetForegroundWindow();
    [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
    private static extern int GetWindowThreadProcessId(IntPtr handle, out int processId);
    [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
    static extern int GetWindowTextLength(IntPtr hWnd);
    [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
    static extern int GetWindowText(IntPtr hWnd, StringBuilder text, int count);
    [DllImport("user32.dll", SetLastError = true)]
    static extern IntPtr FindWindow(string lpClassName, string lpWindowName);
    [DllImport("user32.dll", EntryPoint="FindWindow", SetLastError = true)]
    static extern IntPtr FindWindowByCaption(IntPtr ZeroOnly, string lpWindowName);

    [Flags]
    public enum MouseEventFlags
    {
        LeftDown = 0x00000002,
        LeftUp = 0x00000004,
        MiddleDown = 0x00000020,
        MiddleUp = 0x00000040,
        Move = 0x00000001,
        Absolute = 0x00008000,
        RightDown = 0x00000008,
        RightUp = 0x00000010
    }

    [DllImport("user32.dll", EntryPoint = "SetCursorPos")]
    [return: MarshalAs(UnmanagedType.Bool)]
    private static extern bool SetCursorPos(int x, int y);

    [DllImport("user32.dll")]
    [return: MarshalAs(UnmanagedType.Bool)]
    private static extern bool GetCursorPos(out MousePoint lpMousePoint);

    [DllImport("user32.dll")]
    public static extern IntPtr SendMessage(IntPtr hWnd, uint wMsg, UIntPtr wParam, IntPtr lParam);

    [DllImport("user32.dll")]
    private static extern void mouse_event(int dwFlags, int dx, int dy, int dwData, int dwExtraInfo);

    [DllImport("kernel32.dll")]
    static extern IntPtr GetConsoleWindow();

    [DllImport("user32.dll")]
    static extern bool ShowWindow(IntPtr hWnd, int nCmdShow);

    public static void SetCursorPosition(int x, int y)
    {
        SetCursorPos(x, y);
    }

    public static void SetCursorPosition(MousePoint point)
    {
        SetCursorPos(point.X, point.Y);
    }

    public static MousePoint GetCursorPosition()
    {
        MousePoint currentMousePoint;
        var gotPoint = GetCursorPos(out currentMousePoint);
        if (!gotPoint) { currentMousePoint = new MousePoint(0, 0); }
        return currentMousePoint;
    }

    public static void MouseEvent(MouseEventFlags value)
    {
        MousePoint position = GetCursorPosition();

        mouse_event
            ((int)value,
             position.X,
             position.Y,
             0,
             0)
            ;
    }

    [StructLayout(LayoutKind.Sequential)]
    public struct MousePoint
    {
        public int X;
        public int Y;

        public MousePoint(int x, int y)
        {
            X = x;
            Y = y;
        }
    }

    public static IntPtr MAKELPARAM(int p, int p_2)
    {
        return (IntPtr) ((p_2 << 16) | (p & 0xFFFF));
    }

    public static string GetTopWindowText()
    {
        IntPtr hWnd = GetForegroundWindow();
        int length = GetWindowTextLength(hWnd);
        StringBuilder text = new StringBuilder(length + 1);
        GetWindowText(hWnd, text, text.Capacity);
        return text.ToString();
    }

		public static void Main() {
      Random rand = new Random();
      string arg="$args";
      string[] args = arg.Split(' ');
      bool leftMouse=Boolean.Parse(args[0]);
      bool rightMouse=Boolean.Parse(args[1]);
      int leftCpsMin=Int32.Parse(args[2]);
      int leftCpsMax=Int32.Parse(args[3]);
      int rightCpsMin=Int32.Parse(args[4]);
      int rightCpsMax=Int32.Parse(args[5]);
      int leftJitter=Int32.Parse(args[6]);
      int rightJitter=Int32.Parse(args[7]);
      int blockhitChance=Int32.Parse(args[8]) * 5;
      bool leftMCOnly=Boolean.Parse(args[9]);
      bool rightMCOnly=Boolean.Parse(args[10]);
      int closeButton=Int32.Parse(args[11], NumberStyles.AllowHexSpecifier);
      int toggleButton=Int32.Parse(args[12], NumberStyles.AllowHexSpecifier);
      int hideButton=Int32.Parse(args[13], NumberStyles.AllowHexSpecifier);
      bool toggled = true;
      bool toggledWindow = true;

      Console.WriteLine("                    Successfully Executed.");
      int toggledPosition=Console.CursorTop + 1;
      Console.SetCursorPosition(19, toggledPosition);
      Console.WriteLine("          [45;37mON[40;37m");
			while (true) {
        byte[] close = BitConverter.GetBytes(GetAsyncKeyState(closeButton));
        byte[] toggle = BitConverter.GetBytes(GetAsyncKeyState(toggleButton));
        byte[] toggleWindow = BitConverter.GetBytes(GetAsyncKeyState(hideButton));
        if (leftMouse) {
          byte[] leftButton = BitConverter.GetBytes(GetAsyncKeyState(0x01));
          if (leftButton[1] == 0x80) {
            if (toggled) {
              if (rand.Next(1, 6) == 2) {
                if (rand.Next(1, 6) <= 2) Thread.Sleep(rand.Next((1000 / leftCpsMax), (1000 / leftCpsMin)) - (rand.Next(8, 32)) >> 1);
                else Thread.Sleep(rand.Next((1000 / leftCpsMax), (1000 / leftCpsMin)) >> 1);
              } else {
                bool rbutton = false;
                if (leftMCOnly) {
                  if (GetForegroundWindow() != FindWindow("LWJGL", null)) continue;
                }
                int hWnd = (int)GetForegroundWindow();
                if (GetForegroundWindow() == GetConsoleWindow()) continue;
                SendMessage((IntPtr) hWnd, 0x0201, (UIntPtr) 0x0001, MAKELPARAM(Cursor.Position.X, Cursor.Position.Y));
                if (leftJitter > 0) {
                  SetCursorPos(Cursor.Position.X - (rand.Next(-leftJitter, leftJitter)), Cursor.Position.Y - (rand.Next(-leftJitter, leftJitter)));
                }
                if (blockhitChance > 0) {
                  if (rand.Next(0, 100) <= blockhitChance) {
                    SendMessage((IntPtr) hWnd, 0x0204, (UIntPtr) 0x0001, MAKELPARAM(Cursor.Position.X, Cursor.Position.Y));
                    rbutton = true;
                  }
                }
                //Console.WriteLine("left  down  |  " + GetTopWindowText() + "  |  " + leftCpsMin + ", " + leftCpsMax);
                if (rand.Next(1, 6) <= 2) Thread.Sleep(rand.Next((1000 / leftCpsMax), (1000 / leftCpsMin)) - (rand.Next(8, 32)) >> 1);
                else Thread.Sleep(rand.Next((1000 / leftCpsMax), (1000 / leftCpsMin)) >> 1);
                if (rbutton) {
                  SendMessage((IntPtr) hWnd, 0x0205, UIntPtr.Zero, MAKELPARAM(Cursor.Position.X, Cursor.Position.Y));
                }
                SendMessage((IntPtr) hWnd, 0x0202, UIntPtr.Zero, MAKELPARAM(Cursor.Position.X, Cursor.Position.Y));
                //Console.WriteLine("left  up    |  " + GetTopWindowText() + "  |  " + leftCpsMin + ", " + leftCpsMax);
                if (rand.Next(1, 6) <= 2) Thread.Sleep(rand.Next((1000 / leftCpsMax), (1000 / leftCpsMin)) - (rand.Next(8, 32)) >> 1);
                else Thread.Sleep(rand.Next((1000 / leftCpsMax), (1000 / leftCpsMin)) >> 1);
              }
            }
          }
        }
        if (rightMouse) {
          byte[] rightButton = BitConverter.GetBytes(GetAsyncKeyState(0x02));
          if (rightButton[1] == 0x80) {
            if (toggled) {
              if (rand.Next(1, 6) == 2) {
                if (rand.Next(1, 6) <= 2) Thread.Sleep(rand.Next((1000 / rightCpsMax), (1000 / rightCpsMin)) - (rand.Next(8, 32)) >> 1);
                else Thread.Sleep(rand.Next((1000 / rightCpsMax), (1000 / rightCpsMin)) >> 1);
              } else {
                if (rightMCOnly) {
                  if (GetForegroundWindow() != FindWindow("LWJGL", null)) continue;
                }
                int hWnd = (int)GetForegroundWindow();
                SendMessage((IntPtr) hWnd, 0x0204, (UIntPtr) 0x0002, MAKELPARAM(Cursor.Position.X, Cursor.Position.Y));
                if (rightJitter > 0) {
                  SetCursorPos(Cursor.Position.X - (rand.Next(-rightJitter, rightJitter)), Cursor.Position.Y - (rand.Next(-rightJitter, rightJitter)));
                }
                //Console.WriteLine("right down  |  " + GetTopWindowText() + "  |  " + rightCpsMin + ", " + rightCpsMax);
                if (rand.Next(1, 6) <= 2) Thread.Sleep(rand.Next((1000 / rightCpsMax), (1000 / rightCpsMin)) - (rand.Next(8, 32)) >> 1);
                else Thread.Sleep(rand.Next((1000 / rightCpsMax), (1000 / rightCpsMin)) >> 1);
                SendMessage((IntPtr) hWnd, 0x0205, UIntPtr.Zero, MAKELPARAM(Cursor.Position.X, Cursor.Position.Y));
                //Console.WriteLine("right up    |  " + GetTopWindowText() + "  |  " + rightCpsMin + ", " + rightCpsMax);
                if (rand.Next(1, 6) <= 2) Thread.Sleep(rand.Next((1000 / rightCpsMax), (1000 / rightCpsMin)) - (rand.Next(8, 32)) >> 1);
                else Thread.Sleep(rand.Next((1000 / rightCpsMax), (1000 / rightCpsMin)) >> 1);
              }
            }
          }
        }
        if (close[1] == 0x80) {
          var handle = GetConsoleWindow();
          ShowWindow(handle, 5);
          Console.SetCursorPosition(0, toggledPosition - 1);
          Console.WriteLine("            Clicker stopped.");
          System.Environment.Exit(1);
        }
        if (toggle[1] == 0x80) {
          if (toggled) {
            toggled = false;
            Console.SetCursorPosition(19, toggledPosition);
            Console.WriteLine("OFF");
            Thread.Sleep(150);
          } else {
            toggled = true;
            Console.SetCursorPosition(19, toggledPosition);
            Console.WriteLine("ON ");
            Thread.Sleep(150);
          }
        }
        if (toggleWindow[1] == 0x80) {
          if (toggledWindow) {
            toggledWindow = false;
            var handle = GetConsoleWindow();
            ShowWindow(handle, 0);
            Thread.Sleep(150);
          } else {
            toggledWindow = true;
            var handle = GetConsoleWindow();
            ShowWindow(handle, 5);
            Thread.Sleep(150);
          }
        }
      }
		}
	}
}
"@

$assemblies = ("System.Core","System.Xml.Linq","System.Data","System.Xml", "System.Data.DataSetExtensions", "Microsoft.CSharp", "System.Windows.Forms", "System.Drawing")
Add-Type -ReferencedAssemblies $assemblies -TypeDefinition $code -Language CSharp
iex "[Auto.Program]::Main()"
