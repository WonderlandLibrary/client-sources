#pragma once

#include "imgui.h"
#include <vector>
#include <string>

namespace ImGui {

	bool CustomBeginCombo(const char* label, const char* preview_value, ImGuiComboFlags flags);

	bool CustomCombo1(const char* label, std::vector<std::string>& items, int items_size, int* selectedItem, int height_in_items = -1);

	void CustomColorPicker(const char* name, float color[3]) noexcept;

	bool Hotkey(const char* label, int* k, const ImVec2& size_arg);
}