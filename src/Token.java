public class Token {
    enum Type {
        PAROPEN,
        PARCLOSE,
        LAMBDA,
        VAR,
        SPACE,
        DOT  // MISSCHIEN DEZE WEGHALEN
    }

    String value;
}
