public enum CellType {
    ASEXUATE("Asexuate Cell"),
    SEXUATE("Sexuate Cell");

    private final String displayName;

    CellType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
