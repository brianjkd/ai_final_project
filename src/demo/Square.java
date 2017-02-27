package demo;

public enum Square {
    X(-1), O(1), EMPTY(2);
    private int value;

    Square(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }
}
