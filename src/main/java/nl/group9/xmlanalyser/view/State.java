package nl.group9.xmlanalyser.view;

public enum State {
    ANALYZING("ANALYZING"),
    FINISHED("FINISHED"),
    FAILED("FAILED"),
    DELETED("DELETED");

    private String value;

    State(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
