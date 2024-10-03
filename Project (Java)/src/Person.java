public class Person {
    //Attributes
    private String name;
    private String surname;
    private String email;

    //Constructor
    public Person(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    //Getter for name attribute
    public String getName() {
        return name;
    }

    //Setter for name attribute
    public void setName(String name) {
        this.name = name;
    }

    //Getter for the surname attribute
    public String getSurname() {
        return surname;
    }

    //Setter for the surname attribute
    public void setSurname(String surname) {
        this.surname = surname;
    }

    //Getter for the email attribute
    public String getEmail() {
        return email;
    }

    //Setter for the email attribute
    public void setEmail(String email) {
        this.email = email;
    }

    //Method to print person information
    public void printInfo() {
        System.out.println("Name: " + name );
        System.out.println("Surname: " + surname);
        System.out.println("Email: " + email);
    }
}
