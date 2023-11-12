package classes.accountClasses;

public class CurrentAccountSaveClass {
    public boolean isSigned;
    public String login, password;

    public CurrentAccountSaveClass(String login, String password, boolean isSigned) {
        this.isSigned = isSigned;
        this.login = login;
        this.password = password;
    }

    public boolean getSigned() {
        return isSigned;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
