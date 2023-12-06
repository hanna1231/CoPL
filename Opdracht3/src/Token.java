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
            case ":":
                type = Options.COLON;
                this.value = newValue;
                System.out.println("Token constructor colon: " + this.value);
                break;
            case "->":
                type = Options.ARROW;
                this.value = newValue;
                System.out.println("Token constructor arrow: " + this.value);
                break;
            case "^":
                type = Options.CARET;
                this.value = newValue;
                System.out.println("Token constructor caret: " + this.value);
            default:
               if(firstChar >= 'a' && firstChar <= 'z'){
                  type = Options.LVAR;
               }
               else if(firstChar >= 'A' && firstChar <= 'Z'){
                  type = Options.UVAR;
               }
                this.value = newValue;
                System.out.println("Token constructor var: " + this.value);
                break;
        }
    }

    // Check the type of a character 
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
