public class Token {
    enum Options {
        PAROPEN,
        PARCLOSE,
        LAMBDA,
        VAR,
        SPACE,
        DOT  // MISSCHIEN DEZE WEGHALEN
    }

    Options type;
    String value;

    public Token(String newValue) {
        switch(newValue) {
            case " ":
                type = Options.SPACE;
                this.value = newValue;
                System.out.println("Token constructor space: " + this.value);
                break;
            case "(":
                type = Options.PAROPEN;
                this.value = newValue;
                System.out.println("Token constructor paropen: " + this.value);
                break;
            case ")":
                type = Options.PARCLOSE;
                this.value = newValue;
                System.out.println("Token constructor parclose: " + this.value);
                break;
            case "\\":
                type = Options.LAMBDA;
                this.value = newValue;
                System.out.println("Token constructor lambda: " + this.value);
                break;
            default:
                type = Options.VAR;
                this.value = newValue;
                System.out.println("Token constructor var: " + this.value);
                break;
        }
    }
}
