public class Employees {
    String id;
    String firstName;
    String lastName;
    String auth;
    String email;
    String phone;
    String problemsSub;
    String problemsSol;
    String password;

    public Employees(String firstName, String lastName, String auth, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.auth = auth;
        this.email = email;
    }

    public Employees() {
    }

    public Employees(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProblemsSub() {
        return problemsSub;
    }

    public void setProblemsSub(String problemsSub) {
        this.problemsSub = problemsSub;
    }

    public String getProblemsSol() {
        return problemsSol;
    }

    public void setProblemsSol(String problemsSol) {
        this.problemsSol = problemsSol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Employees{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", auth='" + auth + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", problemsSub='" + problemsSub + '\'' +
                ", problemsSol='" + problemsSol + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
