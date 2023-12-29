public class Token { // All possible options for tokens
    enum Options {
        PAROPEN,
        PARCLOSE,
        LVAR,
        UVAR,
        APPLY,
        LAMBDA,
        COLON,
        ARROW,
        CARET,
    }

    Options type;
    private String value;

    public Token(String newValue) { // Check what character the token is
      char firstChar = newValue.charAt(0);
        switch(newValue) { // Voeg nog twee tokens toe die onderscheidt maken tussen [a-z].* en [A-Z].*
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
            case ":":
                type = Options.COLON;
                this.value = newValue;
                break;
            case "->":
                type = Options.ARROW;
                this.value = newValue;
                break;
            case "^":
                type = Options.CARET;
                this.value = newValue;
                break;
            default:
                this.value = newValue;
                if(firstChar >= 'a' && firstChar <= 'z'){
                    type = Options.LVAR;
                }
                else if(firstChar >= 'A' && firstChar <= 'Z'){
                    type = Options.UVAR;
                }
                else{
                    System.err.println("Check your input, it's not a valid token");
                }
                break;
        }
    }

    
    // Check the type of a character 
    public boolean isVar() {
        return(type == Options.LVAR || type == Options.UVAR);
    }

    public boolean isLVar() {
        return (type == Options.LVAR);
    }

    public boolean isUVar() {
        return (type == Options.UVAR);
    }

    public boolean isParClose() {
        return (type == Options.PARCLOSE);
    }

    public boolean isParOpen() {
        return (type == Options.PAROPEN);
    }

    public boolean isLambda() {
         return (type == Options.LAMBDA);
    }

    public boolean isApply() {
        return (type == Options.APPLY);
    }

    public boolean isColon() {
        return (type == Options.COLON);
    }

    public boolean isArrow() {
        return (type == Options.ARROW);
    }

    public boolean isCaret() {
        return (type == Options.CARET);
    }

    public String getValue() {
        return value;
    }

    public void setTokenValue(String newValue) {
        this.value = newValue;
    }
    
}
