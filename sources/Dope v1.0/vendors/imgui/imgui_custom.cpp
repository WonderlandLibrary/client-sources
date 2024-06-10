#include "imgui_custom.hpp"
#define IMGUI_DEFINE_MATH_OPERATORS
#ifdef IMGUI_DISABLE
#undef IMGUI_DISABLE
#endif
#include "imgui_internal.h"

#include <map>
#include <vector>
#include <string>

static std::map<ImGuiID, float> combo_times{};
static std::map<ImGuiID, float> combo_draggings{};
bool ImGui::CustomBeginCombo(const char* label, const char* preview_value, ImGuiComboFlags flags)
{
	// Always consume the SetNextWindowSizeConstraint() call in our early return paths
	ImGuiContext& g = *GImGui;
	bool has_window_size_constraint = (g.NextWindowData.Flags & ImGuiNextWindowDataFlags_HasSizeConstraint) != 0;
	g.NextWindowData.Flags &= ~ImGuiNextWindowDataFlags_HasSizeConstraint;

	ImGuiWindow* window = GetCurrentWindow();
	if (window->SkipItems)
		return false;

	IM_ASSERT((flags & (ImGuiComboFlags_NoArrowButton | ImGuiComboFlags_NoPreview)) != (ImGuiComboFlags_NoArrowButton | ImGuiComboFlags_NoPreview)); // Can't use both flags together

	const ImGuiStyle& style = g.Style;
	const ImGuiID id = window->GetID(label);

	const float arrow_size = (flags & ImGuiComboFlags_NoArrowButton) ? 0.0f : GetFrameHeight();
	const ImVec2 label_size = CalcTextSize(label, NULL, true);
	const float expected_w = CalcItemWidth() * 1.45f;
	const float w = (flags & ImGuiComboFlags_NoPreview) ? arrow_size : expected_w;
	const ImRect frame_bb(window->DC.CursorPos, window->DC.CursorPos + ImVec2(w, (label_size.y + style.FramePadding.y) * 1.15f));
	const ImRect total_bb(frame_bb.Min, frame_bb.Max + ImVec2(label_size.x > 0.0f ? style.ItemInnerSpacing.x + label_size.x : 0.0f, 0.0f));
	ItemSize(total_bb, style.FramePadding.y);
	if (!ItemAdd(total_bb, id, &frame_bb))
		return false;

	bool hovered, held;
	bool pressed = ButtonBehavior(frame_bb, id, &hovered, &held);
	bool popup_open = IsPopupOpen(id, ImGuiPopupFlags_None);

	const ImU32 frame_col = GetColorU32(ImGuiCol_FrameBg);
	const float value_x2 = ImMax(frame_bb.Min.x, frame_bb.Max.x - arrow_size);
	RenderNavHighlight(frame_bb, id);
	if (!(flags & ImGuiComboFlags_NoPreview))
		window->DrawList->AddRectFilled(frame_bb.Min, ImVec2(value_x2, frame_bb.Max.y), frame_col, style.FrameRounding, (flags & ImGuiComboFlags_NoArrowButton) ? ImDrawCornerFlags_All : ImDrawCornerFlags_Left);

	if (!(flags & ImGuiComboFlags_NoArrowButton))
	{
		ImU32 bg_col = GetColorU32(ImGuiCol_FrameBg);
		ImU32 text_col = GetColorU32(ImVec4(ImColor(174, 171, 192)));
		window->DrawList->AddRectFilled(ImVec2(value_x2, frame_bb.Min.y), frame_bb.Max, bg_col, style.FrameRounding, (w <= arrow_size) ? ImDrawCornerFlags_All : ImDrawCornerFlags_Right);
		if (value_x2 + arrow_size - style.FramePadding.x <= frame_bb.Max.x)
			RenderArrow(window->DrawList, ImVec2(value_x2 + style.FramePadding.y, frame_bb.Min.y + style.FramePadding.y), text_col, ImGuiDir_Down, 1.0f);
	}

	RenderFrameBorder(frame_bb.Min, frame_bb.Max, style.FrameRounding);
	PushStyleColor(ImGuiCol_Text, ImVec4(ImColor(174, 171, 192)));
	if (preview_value != NULL && !(flags & ImGuiComboFlags_NoPreview))
		RenderTextClipped(ImVec2(frame_bb.Min.x, frame_bb.Min.y) + style.FramePadding, ImVec2(value_x2, frame_bb.Max.y), preview_value, NULL, NULL, ImVec2(0.0f, 0.0f));
	PopStyleColor(1);

	//if (label_size.x > 0)
		//RenderText(ImVec2(frame_bb.Max.x + style.ItemInnerSpacing.x, frame_bb.Min.y + style.FramePadding.y), label);

	constexpr auto easeInOutSine = [](float t)
	{
		return (float)(0.5f * (1.f + sinf(3.1415926f * (t - 0.5f))));
	};

	if (combo_times.find(id) == combo_times.end())
		combo_times.emplace(id, 0.f);

	if (combo_draggings.find(id) == combo_draggings.end())
		combo_draggings.emplace(id, false);

	auto& actualTime = combo_times.at(id);
	auto& actualDragging = combo_draggings.at(id);

	if (actualTime >= 2.f)
	{
		actualTime = 0.f;
		popup_open = false;
		actualDragging = false;
	}

	if ((pressed || popup_open) && actualTime < 1.f)
	{
		actualDragging = true;
		actualTime += 0.03f;
		if (actualTime > 1.f)
			actualTime = 1.f;
	}
	if (!popup_open && actualTime >= 1.f)
	{
		actualTime = 0.f;
		actualDragging = false;
	}

	if (((pressed || g.NavActivateId == id) && !popup_open) || actualDragging)
	{
		if (window->DC.NavLayerCurrent == 0)
			window->NavLastIds[0] = id;
		OpenPopupEx(id, ImGuiPopupFlags_None);
		popup_open = true;
	}

	if (actualTime <= 0.f)
		return false;

	if (has_window_size_constraint)
	{
		g.NextWindowData.Flags |= ImGuiNextWindowDataFlags_HasSizeConstraint;
		g.NextWindowData.SizeConstraintRect.Min.x = ImMax(g.NextWindowData.SizeConstraintRect.Min.x, w);
	}
	else
	{
		auto CalcMaxPopupHeightFromItemCount = [](int items_count)
		{
			ImGuiContext& g = *GImGui;
			if (items_count <= 0)
				return FLT_MAX;
			return (g.FontSize + g.Style.ItemSpacing.y) * items_count - g.Style.ItemSpacing.y + (g.Style.WindowPadding.y * 2);
		};



		if ((flags & ImGuiComboFlags_HeightMask_) == 0)
			flags |= ImGuiComboFlags_HeightRegular;
		IM_ASSERT(ImIsPowerOfTwo(flags & ImGuiComboFlags_HeightMask_));    // Only one
		int popup_max_height_in_items = -1;
		if (flags & ImGuiComboFlags_HeightRegular)     popup_max_height_in_items = 8;
		else if (flags & ImGuiComboFlags_HeightSmall)  popup_max_height_in_items = 4;
		else if (flags & ImGuiComboFlags_HeightLarge)  popup_max_height_in_items = 20;
		SetNextWindowSizeConstraints(ImVec2(w, 0.0f), /*ImVec2{ FLT_MAX, CalcMaxPopupHeightFromItemCount(popup_max_height_in_items) } -*/ ImVec2{ FLT_MAX, CalcMaxPopupHeightFromItemCount(popup_max_height_in_items) * easeInOutSine(actualTime) });
	}

	char name[16];
	ImFormatString(name, IM_ARRAYSIZE(name), "##Combo_%02d", g.BeginPopupStack.Size); // Recycle windows based on depth

	// Peak into expected window size so we can position it
	if (ImGuiWindow* popup_window = FindWindowByName(name))
		if (popup_window->WasActive)
		{
			ImVec2 size_expected = CalcWindowNextAutoFitSize(popup_window);
			if (flags & ImGuiComboFlags_PopupAlignLeft)
				popup_window->AutoPosLastDirection = ImGuiDir_Left;
			ImRect r_outer = GetPopupAllowedExtentRect(popup_window);
			ImVec2 pos = FindBestWindowPosForPopupEx(frame_bb.GetBL(), size_expected, &popup_window->AutoPosLastDirection, r_outer, frame_bb, ImGuiPopupPositionPolicy_ComboBox);
			SetNextWindowPos(pos);
		}

	// We don't use BeginPopupEx() solely because we have a custom name string, which we could make an argument to BeginPopupEx()
	ImGuiWindowFlags window_flags = ImGuiWindowFlags_AlwaysAutoResize | ImGuiWindowFlags_Popup | ImGuiWindowFlags_NoTitleBar | ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoSavedSettings | ImGuiWindowFlags_NoMove | ImGuiWindowFlags_NoScrollbar;

	// Horizontally align ourselves with the framed text
	PushStyleVar(ImGuiStyleVar_WindowPadding, ImVec2(/*style.FramePadding.x, style.WindowPadding.y*/4.f, 0.f));
	PushStyleColor(ImGuiCol_PopupBg, style.Colors[ImGuiCol_FrameBg]);
	bool ret = Begin(name, NULL, window_flags);
	PopStyleVar(1);
	PopStyleColor(1);
	if (!ret)
	{
		EndPopup();
		IM_ASSERT(0);   // This should never happen as we tested for IsPopupOpen() above
		return false;
	}
	return true;
}

bool ImGui::CustomCombo1(const char* label, std::vector<std::string>& items, int items_size, int* selectedItem, int height_in_items)
{
	ImGuiContext& g = *GImGui;
	ImGuiID id = GetCurrentWindow()->GetID(label);

	const float w = CalcItemWidth() * 1.45f;
	const ImVec2 label_size = CalcTextSize(label, NULL, true);

	if (label_size.x > 0.0f)
	{
		PushStyleColor(ImGuiCol_Text, ImVec4(ImColor(174, 171, 192)));

		SetCursorPosX(12.f);
		Text(label);
		SetCursorPosX(10.f);
		PopStyleColor(1);
	}

	if (!CustomBeginCombo(label, *selectedItem != -1 && *selectedItem < items.size() ? items[*selectedItem].c_str() : "", ImGuiComboFlags_NoArrowButton))
		return false;

	for (size_t i = 0; i < items_size; i++)
		if (Selectable(items[i].c_str(), i == *selectedItem))
			*selectedItem = i;

	EndCombo();
}

void ImGui::CustomColorPicker(const char* name, float color[3]) noexcept
{
	ImGui::PushID(name);

	bool openPopup = ImGui::ColorButton(name, ImVec4{ color[0], color[1], color[2], 1.0f }, ImGuiColorEditFlags_NoTooltip | ImGuiColorEditFlags_AlphaPreview);
	if (ImGui::BeginDragDropTarget()) {
		if (const auto payload = ImGui::AcceptDragDropPayload(IMGUI_PAYLOAD_TYPE_COLOR_3F))
			std::copy((float*)payload->Data, (float*)payload->Data + 3, color);
		if (const auto payload = ImGui::AcceptDragDropPayload(IMGUI_PAYLOAD_TYPE_COLOR_4F))
			std::copy((float*)payload->Data, (float*)payload->Data + 3, color);

		ImGui::EndDragDropTarget();
	}
	//ImGui::SameLine(0.0f, 5.0f);
	//ImGui::Text(name);

	if (openPopup)
		ImGui::OpenPopup("##popup");

	if (ImGui::BeginPopup("##popup")) {
		ImGui::ColorPicker3("##picker", color, ImGuiColorEditFlags_NoTooltip | ImGuiColorEditFlags_DisplayRGB | ImGuiColorEditFlags_NoSidePreview);
		ImGui::EndPopup();
	}
	ImGui::PopID();
}

#ifndef WIN32_LEAN_AND_MEAN
#define WIN32_LEAN_AND_MEAN
#endif // !WIN32_LEAN_AND_MEAN
#include <windows.h>

const char* const KeyNames[] = {
	"Unknown",
	"LBUTTON",
	"RBUTTON",
	"CANCEL",
	"MBUTTON",
	"XBUTTON1",
	"XBUTTON2",
	"Unknown",
	"BACK",
	"TAB",
	"Unknown",
	"Unknown",
	"CLEAR",
	"RETURN",
	"Unknown",
	"Unknown",
	"SHIFT",
	"CONTROL",
	"MENU",
	"PAUSE",
	"CAPITAL",
	"KANA",
	"Unknown",
	"JUNJA",
	"FINAL",
	"KANJI",
	"Unknown",
	"ESCAPE",
	"CONVERT",
	"NONCONVERT",
	"ACCEPT",
	"MODECHANGE",
	"SPACE",
	"PRIOR",
	"NEXT",
	"END",
	"HOME",
	"LEFT",
	"UP",
	"RIGHT",
	"DOWN",
	"SELECT",
	"PRINT",
	"EXECUTE",
	"SNAPSHOT",
	"INSERT",
	"DELETE",
	"HELP",
	"0",
	"1",
	"2",
	"3",
	"4",
	"5",
	"6",
	"7",
	"8",
	"9",
	"N/A",
	"N/A",
	"N/A",
	"N/A",
	"N/A",
	"N/A",
	"N/A",
	"A",
	"B",
	"C",
	"D",
	"E",
	"F",
	"G",
	"H",
	"I",
	"J",
	"K",
	"L",
	"M",
	"N",
	"O",
	"P",
	"Q",
	"R",
	"S",
	"T",
	"U",
	"V",
	"W",
	"X",
	"Y",
	"Z",
	"LWIN",
	"RWIN",
	"APPS",
	"N/A",
	"SLEEP",
	"N0",
	"N1",
	"N2",
	"N3",
	"N4",
	"N5",
	"N6",
	"N7",
	"N8",
	"N9",
	"MULTIPLY",
	"ADD",
	"SEPARATOR",
	"SUBTRACT",
	"DECIMAL",
	"DIVIDE",
	"F1",
	"F2",
	"F3",
	"F4",
	"F5",
	"F6",
	"F7",
	"F8",
	"F9",
	"F10",
	"F11",
	"F12",
	"F13",
	"F14",
	"F15",
	"F16",
	"F17",
	"F18",
	"F19",
	"F20",
	"F21",
	"F22",
	"F23",
	"F24",
	"N/A",
	"N/A",
	"N/A",
	"N/A",
	"N/A",
	"N/A",
	"N/A",
	"N/A",
	"NUMLOCK",
	"SCROLL",
	"OEM_NEC_EQUAL",
	"OEM_FJ_MASSHOU",
	"OEM_FJ_TOUROKU",
	"OEM_FJ_LOYA",
	"OEM_FJ_ROYA",
	"N/A",
	"N/A",
	"N/A",
	"N/A",
	"N/A",
	"N/A",
	"N/A",
	"N/A",
	"N/A",
	"LSHIFT",
	"RSHIFT",
	"LCONTROL",
	"RCONTROL",
	"LMENU",
	"RMENU"
};

bool ImGui::Hotkey(const char* label, int* k, const ImVec2& size_arg)
{
	ImGuiWindow* window = ImGui::GetCurrentWindow();
	if (window->SkipItems)
		return false;

	ImGuiContext& g = *GImGui;
	ImGuiIO& io = g.IO;
	ImGuiStyle& style = g.Style;
	float lastborder = style.FrameBorderSize;
	style.FrameBorderSize = 0.f;

	const ImGuiID id = window->GetID(label);
	const ImVec2 label_size = CalcTextSize(label, NULL, true);
	ImVec2 size = CalcItemSize(size_arg, CalcItemWidth(), label_size.y + style.FramePadding.y * 2.0f);
	const ImRect frame_bb(window->DC.CursorPos, window->DC.CursorPos + size);
	const ImRect total_bb(window->DC.CursorPos, frame_bb.Max);

	ItemSize(total_bb, style.FramePadding.y);
	if (!ItemAdd(total_bb, id, NULL, 1 << 0))
		return false;

	
	const bool focus_requested = (GetItemStatusFlags() & ImGuiItemStatusFlags_FocusedByTabbing) != 0;
	const bool focus_requested_by_code = focus_requested;
	const bool focus_requested_by_tab = focus_requested && !focus_requested_by_code;

	const bool hovered = ItemHoverable(frame_bb, id);

	if (hovered) {
		SetHoveredID(id);
		g.MouseCursor = ImGuiMouseCursor_TextInput;
	}

	const bool user_clicked = hovered && io.MouseClicked[0];

	if (focus_requested || user_clicked) {
		if (g.ActiveId != id) {
			// Start edition
			memset(io.MouseDown, 0, sizeof(io.MouseDown));
			memset(io.KeysDown, 0, sizeof(io.KeysDown));
			*k = 0;
		}
		SetActiveID(id, window);
		FocusWindow(window);
	}
	else if (io.MouseClicked[0]) {
		// Release focus when we click outside
		if (g.ActiveId == id)
			ClearActiveID();
	}

	bool value_changed = false;
	int key = *k;

	if (g.ActiveId == id) {
		for (auto i = 0; i < 5; i++) {
			if (io.MouseDown[i]) {
				/*switch (i) {
				case 0:
					key = VK_LBUTTON;
					break;
				case 1:
					key = VK_RBUTTON;
					break;
				case 2:
					key = VK_MBUTTON;
					break;
				}*/
				value_changed = true;
				ImGui::ClearActiveID();
			}
		}
		if (!value_changed) {
			for (auto i = VK_BACK; i <= VK_RMENU; i++) {
				if (io.KeysDown[i]) {
					key = i;
					value_changed = true;
					ClearActiveID();
				}
			}
		}

		if (IsKeyPressedMap(ImGuiKey_Escape)) {
			*k = 0;
			ClearActiveID();
		}
		else {
			*k = key;
		}
	}

	// Render
	// Select which buffer we are going to display. When ImGuiInputTextFlags_NoLiveEdit is Set 'buf' might still be the old value. We Set buf to NULL to prevent accidental usage from now on.

	char buf_display[64] = "[?]";

	bool typing = g.ActiveId == id;


	RenderFrame(frame_bb.Min, frame_bb.Max, GetColorU32(ImGuiCol_ChildBg), false, style.FrameRounding);
	PushStyleColor(ImGuiCol_Text, ImVec4(ImColor(87, 85, 94, 255)));

	if (*k != 0 && g.ActiveId != id)
	{
		char test[500];
		sprintf_s(test, "[%s]", KeyNames[*k]);
		strcpy_s(buf_display, test);
	}
	else if (typing)
		strcpy_s(buf_display, "[...]");

	const ImRect clip_rect(frame_bb.Min.x, frame_bb.Min.y, frame_bb.Min.x + size.x, frame_bb.Min.y + size.y); // Not using frame_bb.Max because we have adjusted size
	ImVec2 render_pos = frame_bb.Min + style.FramePadding;
	PushStyleVar(ImGuiStyleVar_ButtonTextAlign, ImVec2(0.f, 0.5f));
	RenderTextClipped(frame_bb.Min + style.FramePadding, style.FramePadding - style.FramePadding, buf_display, NULL, NULL, style.ButtonTextAlign, &clip_rect);
	PopStyleVar(1);

	if (label_size.x > 0)
		if (IsItemHovered())
			SetTooltip(label);

	PopStyleColor(1);
	style.FrameBorderSize = lastborder;
	return value_changed;

}