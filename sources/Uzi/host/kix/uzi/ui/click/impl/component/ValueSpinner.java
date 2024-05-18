package host.kix.uzi.ui.click.impl.component;

import java.util.ArrayList;
import java.util.List;

import host.kix.uzi.ui.click.api.component.Component;
import host.kix.uzi.ui.click.impl.GuiClick;
import host.kix.uzi.utilities.value.Value;

/**
 * @author Marc
 * @since 7/26/2016
 */
public class ValueSpinner extends Component {

	/**
	 * The value of the spinner.
	 */
	private Value value;

	/**
	 * The current enum of the spinner.
	 */
	private Enum e;

	/**
	 * The index of the spinner.
	 */
	private int index;

	/**
	 * The list of enums.
	 */
	private List<Enum> enums = new ArrayList<>();

	public ValueSpinner(GuiClick parent, Value value, Enum e, int x, int y, int width, int height) {
		super(parent, e.name(), x, y, width, height);
		this.e = e;
		this.value = value;

		for (Enum en : this.e.getClass().getEnumConstants()) {
			if (!enums.contains(en))
				enums.add(en);
		}
	}

	@Override
	public void draw(int x, int y) {
		parent.getTheme().drawComponent(this, x, y);
	}

	@Override
	public void mouseClicked(int x, int y, int button) {
		if (isHovering(x, y)) {
			if (isVisible()) {
				if (enums.size() > 0) {
					switch (button) {
					case 0:
						if (index < enums.size() - 1)
							index++;
						else
							index = 0;
						break;
					case 1:
						if (index - 1 >= 0)
							index--;
						else
							index = enums.size() - 1;
						break;
					}

					this.e = enums.get(index);
					this.value.setValue(enums.get(index));
					this.setLabel(e.name());
				}
			}
		}
	}

	@Override
	public void mouseReleased(int x, int y, int button) {
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) {
	}

	/**
	 * Returns the current value of the spinner.
	 *
	 * @return current value of the spinner
	 */
	public Value getValue() {
		return value;
	}

	/**
	 * Returns the current enum of the spinner.
	 *
	 * @return current enum of the spinner
	 */
	public Enum getEnum() {
		return e;
	}
}
