//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public enum CellType {
    ASEXUATE("Asexuate Cell"),
    SEXUATE("Sexuate Cell");

    private final String displayName;

    private CellType(String displayName) {
        this.displayName = displayName;
    }

    public String toString() {
        return this.displayName;
    }
}
