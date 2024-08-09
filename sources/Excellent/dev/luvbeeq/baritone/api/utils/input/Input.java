package dev.luvbeeq.baritone.api.utils.input;

/**
 * An {@link Enum} representing the inputs that control the player's
 * behavior. This includes moving, interacting with blocks, jumping,
 * sneaking, and sprinting.
 */
public enum Input {

    /**
     * The move forward input
     */
    MOVE_FORWARD,

    /**
     * The move back input
     */
    MOVE_BACK,

    /**
     * The move left input
     */
    MOVE_LEFT,

    /**
     * The move right input
     */
    MOVE_RIGHT,

    /**
     * The attack input
     */
    CLICK_LEFT,

    /**
     * The use item input
     */
    CLICK_RIGHT,

    /**
     * The jump input
     */
    JUMP,

    /**
     * The sneak input
     */
    SNEAK,

    /**
     * The sprint input
     */
    SPRINT
}
