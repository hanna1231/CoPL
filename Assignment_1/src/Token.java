public class Token { // All possible options for tokens
    enum Options {
        PAROPEN,
        PARCLOSE,
        LAMBDA,
        VAR
    }

    Options type;
    String value;

    public Token(String newValue) { // Check what character the token is
        switch(newValue) {
            case "(":
                type = Options.PAROPEN;
                this.value = newValue;
                break;
            case ")":
                type = Options.PARCLOSE;
                this.value = newValue;
                break;
            case "\\":
                type = Options.LAMBDA;
                this.value = newValue;
                break;
            case "λ":
                type = Options.LAMBDA;
                this.value = newValue;
                break;
            default:
                type = Options.VAR;
                this.value = newValue;
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
}
