public class Token { // All possible options for tokens
    enum Options {
        PAROPEN,
        PARCLOSE,
        LAMBDA,
        VAR,
        APPLY
    }

    Options type;
    private String value;

    // Check what character the token is
    public Token(String newValue) { 
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
            case "@":
                type = Options.APPLY;
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

    public boolean isApply() {
        return (type == Options.APPLY);
    }

    public String getValue() {
        return value;
    }

    public void setTokenValue(String newValue) {
        this.value = newValue;
    }
    
}
