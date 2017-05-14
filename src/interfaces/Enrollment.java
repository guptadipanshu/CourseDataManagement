package interfaces;

public enum Enrollment {
	MISSING_PREREQ("missing pre-requiste"),
	ENROLLED("enrolled"),
	NO_SEATS("no seats");
    private String value;

    Enrollment(String value) {
	    this.value = value;
    }

    public String getValue() {
        return value;
    }
}
