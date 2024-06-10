#include "Autoclicker.h"
#include "../ui/ui.hh"
#include <Windows.h>
#include <random>


float Randomization()
{

	//try to make your own randomization.
	//This one is shity as hell

	return static_cast<float>(Random::GenerateNum(260, 880) / clicker::CPS);
}
void clicker_event()
{   
	if (clicker::C_InMC)
	{
		if (GetForegroundWindow() == FindWindowA(("LWJGL"), NULL))
			if (clicker::C_toggle)
				if (mouseDown)
				{
					PostMessage(GetForegroundWindow(), WM_LBUTTONDOWN, MK_RBUTTON, MAKELPARAM(0, 0));
					Sleep(23);
					PostMessage(GetForegroundWindow(), WM_LBUTTONUP, MK_RBUTTON, MAKELPARAM(0, 0));

					Sleep(Randomization() * 1.23);
				}
	}
	else if (clicker::C_InMC == false)
	{
		if (clicker::C_toggle)
			if (mouseDown)
			{
				PostMessage(GetForegroundWindow(), WM_LBUTTONDOWN, MK_RBUTTON, MAKELPARAM(0, 0));
				Sleep(23);
				PostMessage(GetForegroundWindow(), WM_LBUTTONUP, MK_RBUTTON, MAKELPARAM(0, 0));

				Sleep(Randomization() * 1.23);
			}
	}

}
void autoclicker()
{
	while (true)
		clicker_event();

}

void clickerthread()
{
	CreateThread(0, 0, (LPTHREAD_START_ROUTINE)hook::hookmouse,0,0,0);
	CreateThread(0, 0, (LPTHREAD_START_ROUTINE)autoclicker, 0, 0, 0);
}