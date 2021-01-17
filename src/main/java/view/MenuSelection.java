package view;

public enum MenuSelection {
    CLASSIC("CLASSIC"), ENHANCED("ENHANCED"), QUIT("QUIT");

    private final String value;
    private MenuSelection(String value) {
        this.value = value;
    }

    public String getStr() {
        return value;
    }

    public MenuSelection getNext() {
        if (this == CLASSIC) return ENHANCED;
        if (this == ENHANCED) return QUIT;
        else return CLASSIC;
    }

    public MenuSelection getPrevious() {
        if (this == CLASSIC) return QUIT;
        if (this == QUIT) return ENHANCED;
        else return CLASSIC;
    }
}
