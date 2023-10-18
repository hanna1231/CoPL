public class Token { // All possible options for tokens
    enum Options {
        PAROPEN,
        PARCLOSE,
        LAMBDA,
        VAR,
        APPLY,
        DOT  // MISSCHIEN DEZE WEGHALEN
    }

    Options type;
    private String value;

    public Token(String newValue) { // Check what character the token is
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
            case "@":
                type = Options.APPLY;
                this.value = newValue;
                System.out.println("Token constructor apply: " + this.value);
                break;
            default:
                type = Options.VAR;
                this.value = newValue;
                System.out.println("Token constructor var: " + this.value);
                break;
        }
    }

    // Check the type of a character 
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

    public boolean isApply() {
        return (type == Options.APPLY);
    }

    public String getValue() {
        return value;
    }

    // public boolean isDot() {
    //     return (type == Options.DOT);
    // }
    
}
