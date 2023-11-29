// create type for the cell using enum
public enum CellType {
    ASEXUATE("Asexuate Cell"),
    SEXUATE("Sexuate Cell");

    public final String displayName;

    CellType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
