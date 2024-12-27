package dao;

public enum Level {
    GOLD(3, null),
    SILVER(2, GOLD),
    BASIC(1, SILVER);

    private final int value;
    private final Level next;

    Level(int value, Level level) {
        this.value = value;
        this.next = level;
    }

    public int getValue() {
        return value;
    }

    public Level getNext() {
        return next;
    }

    public static Level valueOf(int value) {
        switch (value) {
            case 1: return BASIC;
            case 2: return SILVER;
            case 3: return GOLD;
            default: throw new AssertionError("unknown value: " + value);
        }
    }
}
