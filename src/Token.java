public class Token {
    enum Options {
        PAROPEN,
        PARCLOSE,
        LAMBDA,
        VAR,
        DOT  // MISSCHIEN DEZE WEGHALEN
    }

    Options type;
    String value;

    public Token(String newValue) {
        switch(newValue) {
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

    public boolean isLambda() {
        return (type == Options.LAMBDA);
    }

    public boolean isVar() {
        return (type == Options.VAR);
    }

    public boolean isParClose() {
        return (type == Options.PARCLOSE);
    }

    public boolean isParOpen() {
        return (type == Options.PAROPEN);
    }

    // public boolean isDot() {
    //     return (type == Options.DOT);
    // }
    
}
