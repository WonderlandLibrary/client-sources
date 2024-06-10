//hayden was here

#pragma once
#include "includes.hpp"
#include "xorstr.hpp"
#include "bind.hpp"
#include "config.hpp"
#include <d3d9.h>
#define DIRECTINPUT_VERSION 0x0800
#include <dinput.h>
#include <tchar.h>
#include <Windows.h>
#include <stdio.h>
#include <conio.h>
#include <iostream>
#include <time.h>
#include <random>
#include <tchar.h>
#include <functional>
#include <cstdio>
#include <windows.h>
#include <tlhelp32.h>
#include <wchar.h>
int key;
int keyt;
int keyc;
int keyts;
int keycR;
bool deleteonexit = true;
static bool thp1 = false;
static bool thp2 = false;
static bool thp3 = true;
static bool thp4 = true;
static bool thp5 = true;
static bool thp6 = true;
static bool thp7 = true;
static bool thp8 = true;
static bool thp9 = true;
float rc = 100;
int bar = 1;
char asciiChar = keyc;
char asciiChar3 = keycR;
char asciiChar1 = keyt;
char asciiChar2 = keyts;

static bool enable_7m = false;
static bool enable_7m1 = false;
static bool enable_7m2 = false;
static bool enable_7m3 = false;
// UNSUSED CODE LMAO XD HAHA
void throwpot()
{
    HWND hWnd = FindWindow("LWJGL", NULL);
    if (hWnd == GetForegroundWindow())
    {
        if (bar == 1)
        {
            if (thp1)
            {

                INPUT input;
                WORD vkey = 0x31; // see link below
                input.type = INPUT_KEYBOARD;
                input.ki.wScan = MapVirtualKey(vkey, MAPVK_VK_TO_VSC);
                input.ki.time = 0;
                input.ki.dwExtraInfo = 0;
                input.ki.wVk = vkey;
                input.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
                SendInput(1, &input, sizeof(INPUT));
                input.ki.dwFlags = KEYEVENTF_KEYUP;
                SendInput(1, &input, sizeof(INPUT));
                Sleep((rc - 20) / 2);
                mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, NULL, NULL);
                Sleep(40);
                mouse_event(MOUSEEVENTF_RIGHTUP, 0, 0, NULL, NULL);
                Sleep((rc - 20) / 2);
            }
            else
            {

                bar += 1;
            }
        }
        if (bar == 2)
        {
            if (thp2)
            {
                INPUT input;
                WORD vkey = 0x32; // see link below
                input.type = INPUT_KEYBOARD;
                input.ki.wScan = MapVirtualKey(vkey, MAPVK_VK_TO_VSC);
                input.ki.time = 0;
                input.ki.dwExtraInfo = 0;
                input.ki.wVk = vkey;
                input.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
                SendInput(1, &input, sizeof(INPUT));
                input.ki.dwFlags = KEYEVENTF_KEYUP;
                SendInput(1, &input, sizeof(INPUT));
                Sleep((rc - 20) / 2);
                mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, NULL, NULL);
                Sleep(40);
                mouse_event(MOUSEEVENTF_RIGHTUP, 0, 0, NULL, NULL);
                Sleep((rc - 20) / 2);
                INPUT input1;
                WORD vkey1 = 0x31; // see link below
                input1.type = INPUT_KEYBOARD;
                input1.ki.wScan = MapVirtualKey(vkey1, MAPVK_VK_TO_VSC);
                input1.ki.time = 0;
                input1.ki.dwExtraInfo = 0;
                input1.ki.wVk = vkey1;
                input1.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
                SendInput(1, &input1, sizeof(INPUT));
                input1.ki.dwFlags = KEYEVENTF_KEYUP;
                SendInput(1, &input1, sizeof(INPUT));
            }
            else
            {
                bar += 1;
            }
        }
        if (bar == 3)
        {
            if (thp3)
            {
                INPUT input;
                WORD vkey = 0x33; // see link below
                input.type = INPUT_KEYBOARD;
                input.ki.wScan = MapVirtualKey(vkey, MAPVK_VK_TO_VSC);
                input.ki.time = 0;
                input.ki.dwExtraInfo = 0;
                input.ki.wVk = vkey;
                input.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
                SendInput(1, &input, sizeof(INPUT));
                input.ki.dwFlags = KEYEVENTF_KEYUP;
                SendInput(1, &input, sizeof(INPUT));
                Sleep((rc - 20) / 2);
                mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, NULL, NULL);
                Sleep(40);
                mouse_event(MOUSEEVENTF_RIGHTUP, 0, 0, NULL, NULL);
                Sleep((rc - 20) / 2);
                INPUT input1;
                WORD vkey1 = 0x31; // see link below
                input1.type = INPUT_KEYBOARD;
                input1.ki.wScan = MapVirtualKey(vkey1, MAPVK_VK_TO_VSC);
                input1.ki.time = 0;
                input1.ki.dwExtraInfo = 0;
                input1.ki.wVk = vkey1;
                input1.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
                SendInput(1, &input1, sizeof(INPUT));
                input1.ki.dwFlags = KEYEVENTF_KEYUP;
                SendInput(1, &input1, sizeof(INPUT));
            }
            else
            {
                bar += 1;
            }
        }
        if (bar == 4)
        {
            if (thp4)
            {
                INPUT input;
                WORD vkey = 0x34; // see link below
                input.type = INPUT_KEYBOARD;
                input.ki.wScan = MapVirtualKey(vkey, MAPVK_VK_TO_VSC);
                input.ki.time = 0;
                input.ki.dwExtraInfo = 0;
                input.ki.wVk = vkey;
                input.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
                SendInput(1, &input, sizeof(INPUT));
                input.ki.dwFlags = KEYEVENTF_KEYUP;
                SendInput(1, &input, sizeof(INPUT));
                Sleep((rc - 20) / 2);
                mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, NULL, NULL);
                Sleep(40);
                mouse_event(MOUSEEVENTF_RIGHTUP, 0, 0, NULL, NULL);
                Sleep((rc - 20) / 2);
                INPUT input1;
                WORD vkey1 = 0x31; // see link below
                input1.type = INPUT_KEYBOARD;
                input1.ki.wScan = MapVirtualKey(vkey1, MAPVK_VK_TO_VSC);
                input1.ki.time = 0;
                input1.ki.dwExtraInfo = 0;
                input1.ki.wVk = vkey1;
                input1.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
                SendInput(1, &input1, sizeof(INPUT));
                input1.ki.dwFlags = KEYEVENTF_KEYUP;
                SendInput(1, &input1, sizeof(INPUT));
            }
            else
            {
                bar += 1;
            }
        }
        if (bar == 5)
        {
            if (thp5)
            {
                INPUT input;
                WORD vkey = 0x35; // see link below
                input.type = INPUT_KEYBOARD;
                input.ki.wScan = MapVirtualKey(vkey, MAPVK_VK_TO_VSC);
                input.ki.time = 0;
                input.ki.dwExtraInfo = 0;
                input.ki.wVk = vkey;
                input.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
                SendInput(1, &input, sizeof(INPUT));
                input.ki.dwFlags = KEYEVENTF_KEYUP;
                SendInput(1, &input, sizeof(INPUT));
                Sleep((rc - 20) / 2);
                mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, NULL, NULL);
                Sleep(40);
                mouse_event(MOUSEEVENTF_RIGHTUP, 0, 0, NULL, NULL);
                Sleep((rc - 20) / 2);
                INPUT input1;
                WORD vkey1 = 0x31; // see link below
                input1.type = INPUT_KEYBOARD;
                input1.ki.wScan = MapVirtualKey(vkey1, MAPVK_VK_TO_VSC);
                input1.ki.time = 0;
                input1.ki.dwExtraInfo = 0;
                input1.ki.wVk = vkey1;
                input1.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
                SendInput(1, &input1, sizeof(INPUT));
                input1.ki.dwFlags = KEYEVENTF_KEYUP;
                SendInput(1, &input1, sizeof(INPUT));
            }
            else
            {
                bar += 1;
            }
        }
        if (bar == 6)
        {
            if (thp6)
            {
                INPUT input;
                WORD vkey = 0x36; // see link below
                input.type = INPUT_KEYBOARD;
                input.ki.wScan = MapVirtualKey(vkey, MAPVK_VK_TO_VSC);
                input.ki.time = 0;
                input.ki.dwExtraInfo = 0;
                input.ki.wVk = vkey;
                input.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
                SendInput(1, &input, sizeof(INPUT));
                input.ki.dwFlags = KEYEVENTF_KEYUP;
                SendInput(1, &input, sizeof(INPUT));
                Sleep((rc - 20) / 2);
                mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, NULL, NULL);
                Sleep(40);
                mouse_event(MOUSEEVENTF_RIGHTUP, 0, 0, NULL, NULL);
                Sleep((rc - 20) / 2);
                INPUT input1;
                WORD vkey1 = 0x31; // see link below
                input1.type = INPUT_KEYBOARD;
                input1.ki.wScan = MapVirtualKey(vkey1, MAPVK_VK_TO_VSC);
                input1.ki.time = 0;
                input1.ki.dwExtraInfo = 0;
                input1.ki.wVk = vkey1;
                input1.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
                SendInput(1, &input1, sizeof(INPUT));
                input1.ki.dwFlags = KEYEVENTF_KEYUP;
                SendInput(1, &input1, sizeof(INPUT));
            }
            else
            {
                bar += 1;
            }
        }
        if (bar == 7)
        {
            if (thp7)
            {
                INPUT input;
                WORD vkey = 0x37; // see link below
                input.type = INPUT_KEYBOARD;
                input.ki.wScan = MapVirtualKey(vkey, MAPVK_VK_TO_VSC);
                input.ki.time = 0;
                input.ki.dwExtraInfo = 0;
                input.ki.wVk = vkey;
                input.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
                SendInput(1, &input, sizeof(INPUT));
                input.ki.dwFlags = KEYEVENTF_KEYUP;
                SendInput(1, &input, sizeof(INPUT));
                Sleep((rc - 20) / 2);
                mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, NULL, NULL);
                Sleep(40);
                mouse_event(MOUSEEVENTF_RIGHTUP, 0, 0, NULL, NULL);
                Sleep((rc - 20) / 2);
                INPUT input1;
                WORD vkey1 = 0x31; // see link below
                input1.type = INPUT_KEYBOARD;
                input1.ki.wScan = MapVirtualKey(vkey1, MAPVK_VK_TO_VSC);
                input1.ki.time = 0;
                input1.ki.dwExtraInfo = 0;
                input1.ki.wVk = vkey1;
                input1.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
                SendInput(1, &input1, sizeof(INPUT));
                input1.ki.dwFlags = KEYEVENTF_KEYUP;
                SendInput(1, &input1, sizeof(INPUT));
            }
            else
            {
                bar += 1;
            }
        }
        if (bar == 8)
        {
            if (thp8)
            {
                INPUT input;
                WORD vkey = 0x38; // see link below
                input.type = INPUT_KEYBOARD;
                input.ki.wScan = MapVirtualKey(vkey, MAPVK_VK_TO_VSC);
                input.ki.time = 0;
                input.ki.dwExtraInfo = 0;
                input.ki.wVk = vkey;
                input.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
                SendInput(1, &input, sizeof(INPUT));
                input.ki.dwFlags = KEYEVENTF_KEYUP;
                SendInput(1, &input, sizeof(INPUT));
                Sleep((rc - 20) / 2);
                mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, NULL, NULL);
                Sleep(40);
                mouse_event(MOUSEEVENTF_RIGHTUP, 0, 0, NULL, NULL);
                Sleep((rc - 20) / 2);
                INPUT input1;
                WORD vkey1 = 0x31; // see link below
                input1.type = INPUT_KEYBOARD;
                input1.ki.wScan = MapVirtualKey(vkey1, MAPVK_VK_TO_VSC);
                input1.ki.time = 0;
                input1.ki.dwExtraInfo = 0;
                input1.ki.wVk = vkey1;
                input1.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
                SendInput(1, &input1, sizeof(INPUT));
                input1.ki.dwFlags = KEYEVENTF_KEYUP;
                SendInput(1, &input1, sizeof(INPUT));
            }
            else
            {
                bar = bar + 1;
            }
        }
        if (bar == 9)
        {
            if (thp9)
            {
                INPUT input;
                WORD vkey = 0x39; // see link below
                input.type = INPUT_KEYBOARD;
                input.ki.wScan = MapVirtualKey(vkey, MAPVK_VK_TO_VSC);
                input.ki.time = 0;
                input.ki.dwExtraInfo = 0;
                input.ki.wVk = vkey;
                input.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
                SendInput(1, &input, sizeof(INPUT));
                input.ki.dwFlags = KEYEVENTF_KEYUP;
                SendInput(1, &input, sizeof(INPUT));
                Sleep((rc - 20) / 2);
                mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, NULL, NULL);
                Sleep(40);
                mouse_event(MOUSEEVENTF_RIGHTUP, 0, 0, NULL, NULL);
                Sleep((rc - 20) / 2);
                INPUT input1;
                WORD vkey1 = 0x31; // see link below
                input1.type = INPUT_KEYBOARD;
                input1.ki.wScan = MapVirtualKey(vkey1, MAPVK_VK_TO_VSC);
                input1.ki.time = 0;
                input1.ki.dwExtraInfo = 0;
                input1.ki.wVk = vkey1;
                input1.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
                SendInput(1, &input1, sizeof(INPUT));
                input1.ki.dwFlags = KEYEVENTF_KEYUP;
                SendInput(1, &input1, sizeof(INPUT));
                bar = 0;
            }
            else
            {
                bar = 0;
            }
        }
        bar = bar + 1;
    }

}
int tlbr;



namespace ui
{
	namespace text
	{
		int change( int num )
		{
			switch ( num )
			{
				case 1:
					for ( int i = 0; i < 3; i++ )
					{
						cfg::utils::rgb[ i ] = cfg::utils::orange[ i ];
						cfg::utils::rgb_2[ i ] = 252;
					}
					return cfg::utils::switchTab = 1;
					break;
				case 2:
					for ( int i = 0; i < 3; i++ )
					{
						cfg::utils::rgb_2[ i ] = cfg::utils::orange[ i ];
						cfg::utils::rgb[ i ] = 252;
					}
					return cfg::utils::switchTab = 2;
					break;
			}
		}
	}


	HANDLE pHandle = NULL;
	DWORD pID = NULL; // ID of our Game

	float reachunlegit = 3;
	float reachin;
	float reachlegit = 3;
	std::vector<DWORD> reachlist;
	static float rech = 3; // 
	bool reachon = false;
	void valsfinder()
	{
		while (1)
		{
			HWND mc_hWnd = FindWindow(xorstr("LWJGL"), nullptr);
			GetWindowThreadProcessId(mc_hWnd, &pID);
			pHandle = OpenProcess(THREAD_QUERY_INFORMATION | PROCESS_VM_OPERATION | PROCESS_VM_READ | PROCESS_VM_WRITE, FALSE, pID);

			constexpr unsigned CHUNK_SIZE = 0x10000; //minimum
			constexpr unsigned MAX_ADDRESS = 0x05FFFFFF; //maximum
			//remember to make sure stack is big enough or allocate it on heap
			char buffer[CHUNK_SIZE];
			for (unsigned i = 0; i < MAX_ADDRESS; i += CHUNK_SIZE) {
				if (ReadProcessMemory(pHandle, (LPVOID)i, buffer, sizeof(buffer), nullptr)) {
					for (int j = 0; j <= CHUNK_SIZE - sizeof(float); ++j) {
						float something;
						memcpy(&something, buffer + j, sizeof(float));
						int address1 = i + j;
						int address2 = i + j;
						if (i + j >= 0x02A0000C)
						{
							if (something == reachlegit)
							{
								for (int lki = 0; lki < 15; lki++)
								{
									BYTE checks2;
									address1 = address1 - 0x4;
									ReadProcessMemory(pHandle, (void*)address1, &checks2, sizeof(BYTE), 0);
									if (219 == checks2)
									{
										for (int ki = 0; ki < 15; ki++)
										{
											BYTE checks1;
											address2 = address2 - 0x4;
											ReadProcessMemory(pHandle, (void*)address2, &checks1, sizeof(BYTE), 0);
											if (133 == checks1)
											{
												reachlist.push_back(i + j);
												//WriteProcessMemory(pHandle, (void*)(i + j), &reachunlegit, sizeof(float), 0);
											}
										}
									}
								}
							}
						}
					}
				}
			}
			Sleep(5000);
		}
	}
	void valschanger()
	{
		while (1)
		{
			DWORD hex;
			float value;
			HWND mc_hWnd = FindWindow(xorstr("LWJGL"), nullptr);
			GetWindowThreadProcessId(mc_hWnd, &pID);
			pHandle = OpenProcess(THREAD_QUERY_INFORMATION | PROCESS_VM_OPERATION | PROCESS_VM_READ | PROCESS_VM_WRITE, FALSE, pID);
			for (int i = 0; i < reachlist.size(); i++)
			{
				ReadProcessMemory(pHandle, (LPVOID)(reachlist.at(i)), &value, sizeof(value), NULL);
				if (value <= 6)
				{
					if (value >= 3)
					{
						WriteProcessMemory(pHandle, (void*)(reachlist.at(i)), &reachunlegit, sizeof(float), 0);
					}

				}
			}

			Sleep(100);
		}
	}

	void tab_1( ImFont *icons )
	{
		ImGui::PushStyleColor( ImGuiCol_Border, ImColor( 63, 63, 63 ) );
		ImGui::SetNextWindowPos( ImVec2( 0, 0 ) );
		ImGui::BeginChild( xorstr( "##header" ), ImVec2( 80, 450 ), true, ImGuiWindowFlags_NoScrollbar );
		{
			ImGui::PushFont( icons );
			ImGui::SpacingInt( 35 );
			ImGui::SameLine( 18.f );

			ImGui::PushStyleColor( ImGuiCol_Text, ImColor( cfg::utils::rgb[ 0 ], cfg::utils::rgb[ 1 ], cfg::utils::rgb[ 2 ] ) );
			ImGui::Text( xorstr( "G" ) );
			if ( ImGui::IsItemClicked( ) )
				text::change( 1 );
			ImGui::PopStyleColor( );

			ImGui::SpacingInt( 25 );
			ImGui::SameLine( 18.f );

			ImGui::PushStyleColor( ImGuiCol_Text, ImColor( cfg::utils::rgb_2[ 0 ], cfg::utils::rgb_2[ 1 ], cfg::utils::rgb_2[ 2 ] ) );
			ImGui::Text( xorstr( "Q" ) );
			if ( ImGui::IsItemClicked( ) )
				text::change( 2 );
			ImGui::PopStyleColor( );

			ImGui::PopFont( );
		}
		ImGui::PopStyleColor( );
		ImGui::EndChild( );
	}

	void tab_3(ImFont* font)
	{
		

		ImGui::SetNextWindowPos(ImVec2(90, 30));
		ImGui::PushStyleColor(ImGuiCol_Border, ImColor(63, 63, 63));
		ImGui::PushStyleColor(ImGuiCol_CheckMark, ImColor(255, 0, 0));
		ImGui::PushStyleColor(ImGuiCol_SliderGrab, ImColor(255, 0, 0));
		ImGui::PushStyleColor(ImGuiCol_SliderGrabActive, ImColor(223, 0, 0));
		ImGui::BeginChild(xorstr("##tab2"), ImVec2(350, 250), true, ImGuiWindowFlags_NoScrollbar);
		{
			ImGui::SpacingInt(5);
			ImGui::SameLine(15.f);
			
			{
				ImGui::Checkbox(xorstr(" Minecraft Only"), &cfg::ac::onlymc);
				ImGui::SpacingInt(5);
				ImGui::InputText(xorstr("##window"), cfg::ac::windowname, IM_ARRAYSIZE(cfg::ac::windowname));
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip(xorstr("Put your minecraft window name.\n\nExample: Lunar")); 


				
			}
		}
		ImGui::PopStyleColor(4);
		ImGui::EndChild();

		//ImGui::BeginChild(xorstr("###tab3"), ImVec2(351, 251), true, ImGuiWindowFlags_NoScrollbar);
		//{
		//	ImGui::Checkbox(xorstr(" only minecraft"), &cfg::ac::onlymc);
		//	ImGui::InputText(xorstr("##window"), cfg::ac::windowname, IM_ARRAYSIZE(cfg::ac::windowname));
		//	if (ImGui::IsItemHovered())
		//		ImGui::SetTooltip(xorstr("Put your minecraft window name.\n\nExample: Lunar"));
		}



	

	namespace ac
	{
		void tab_2( ImFont *font )
		{
			ImGui::SetNextWindowPos( ImVec2( 90, 30 ) );
			ImGui::PushStyleColor( ImGuiCol_Border, ImColor( 63, 63, 63 ) );
            ImGui::PushStyleColor(ImGuiCol_CheckMark, ImColor(255, 0, 0));
            ImGui::PushStyleColor(ImGuiCol_SliderGrab, ImColor(255, 0, 0));
            ImGui::PushStyleColor(ImGuiCol_SliderGrabActive, ImColor(223, 0, 0));
			ImGui::BeginChild( xorstr( "##tab2" ), ImVec2( 350, 250 ), true, ImGuiWindowFlags_NoScrollbar );
			{
				ImGui::SpacingInt( 5 );
				ImGui::SameLine( 15.f );
				ImGui::TextColored( ImColor( 255, 255, 255 ), xorstr( "Auto Clicker" ) );
				ImGui::SpacingInt( 10 );
				ImGui::Checkbox( xorstr( " enabled" ), &cfg::ac::enabled );
				if (ImGui::IsItemHovered())
				       ImGui::SetTooltip(xorstr("Autoclicker, hold left click."));
				ImGui::SameLine( 90.f );
				keybind::key_bind( cfg::ac::acbind, 150, 20 );
				if ( ImGui::IsItemHovered( ) )
					ImGui::SetTooltip( xorstr( "Keybinds, if your keybind doesnt work,\njust try to tap it for the shortest amount of time" ) );
				ImGui::SpacingInt( 5 );
                ImGui::PushItemWidth(150.f);
                ImGui::SliderInt(xorstr("##min"), &cfg::ac::min_cps, 1.0f, 20.0f, xorstr("Min %.1f"));
				ImGui::SpacingInt( 5 );
				ImGui::PushItemWidth( 150.f );
				ImGui::SliderInt( xorstr( "##max" ), &cfg::ac::max_cps, 1.0f, 20.0f, xorstr( "Max %.1f" ) );
				ImGui::SpacingInt(50);
				ImGui::Text("Reach");
				ImGui::Checkbox("###", &reachon);
				if (reachon == false)
				{
					reachunlegit = 3;
				}
				else
				{
					reachunlegit = rech;
				}
				if (ImGui::IsItemHovered())
					       ImGui::SetTooltip(xorstr("this makes your arm feel long just like my penis"));
				ImGui::SpacingInt(5);
				ImGui::SliderFloat("##", &rech, 3, 3.5);
			}
			ImGui::PopStyleColor( 4 );
			ImGui::EndChild( );
		}
	}

	

}