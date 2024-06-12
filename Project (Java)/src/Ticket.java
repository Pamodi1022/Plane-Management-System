import java.io.FileWriter;
import java.io.IOException;

//Attributes
public class Ticket {
    private char row;
    private int seat;
    private double price;
    private Person person;

    //Constructor
    public Ticket(char row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    //Getter for the row attribute
    public char getRow() {
        return row;
    }

    //Setter for the row attribute
    public void setRow(char row) {
        this.row = row;
    }

    //Getter for the seat attribute
    public int getSeat() {
        return seat;
    }

    //Setter for the seat attribute
    public void setSeat(int seat) {
        this.seat = seat;
    }

    //Getter for the price attribute
    public double getPrice() {
        return price;
    }

    //Setter for the price attribute
    public void setPrice(double price) {
        this.price = price;
    }

    //Getter for the person attribute
    public Person getPerson() {
        return person;
    }

    //Setter for the person attribute
    public void setPerson(Person person) {
        this.person = person;
    }

    //Method to print the ticket information
    public void print_Ticket_Info() {
        System.out.println("\nTicket Information: ");
        System.out.println("Row: " + getRow());
        System.out.println("Seat: " + getSeat());
        System.out.println("Price: £" + getPrice());
        System.out.print("\nPerson Information: \n");
        person.printInfo();
    }

    //Method to save ticket information
    public void save() {
        String fileName = getRow() + "" + getSeat() + ".txt";
        try (FileWriter writer = new FileWriter(row + "" + seat + ".txt")) {
            writer.write("Ticket Information of seats" + "\n");

            writer.write("\t Row " + getRow() + "\n");
            writer.write("\t Seat " + getSeat() + "\n");
            writer.write ("\t Price £ " + getPrice() + "\n");

            writer.write("\t Person Information: " + "\n");

            writer.write("\t First name: " + person.getName() + "\n");
            writer.write("\t Surname: " + person.getSurname() + "\n");
            writer.write("\t Email: " + person.getEmail() + "\n");

            writer.close(); //close the file writer
        } catch (IOException e) {
            e.printStackTrace(); //handling IO exception
        }
    }
}

