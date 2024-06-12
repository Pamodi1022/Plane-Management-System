import java.util.InputMismatchException;
import java.util.Scanner;

public class w2053166_Planemanagement {
    public static Scanner input = new Scanner(System.in);
    //Define 2D array to represent the seating plan
    public static int [][] seat_plan = new int[4][];

    //Define an array to store ticket information with the size of 52
    private static final Ticket[] tickets = new Ticket[52];
    private static int ticketIndex = 0;

    public static void main(String[] args) {
        //Initialization of the seating arrangement for the each row of seats
        seat_plan[0] = new int[14];
        seat_plan[1] = new int[12];
        seat_plan[2] = new int[12];
        seat_plan[3] = new int[14];

        System.out.println("\n Welcome to the Plane Management application");

        //Menu
        do {
            System.out.println();
            System.out.println(num(50));
            System.out.println("*                  MENU OPTION                   *");
            System.out.println(num(50));
            System.out.println("""
                          1) Buy a seat\s
                          2) Cancel a seat
                          3) Find first available seat
                          4) Show seating plan
                          5) Print ticket information and total sales
                          6) Search ticket
                          0) Quit\s
                    """);
            System.out.println(num(50));


            //Main loop for prompt the options continuesly
            try {
                System.out.print("Please enter an option: ");
                int option = input.nextInt();

                switch (option) {
                    case 0:
                        System.out.println("Exit");
                        return;
                    case 1:
                        buy_seat(input);
                        break;
                    case 2:
                        cancel_seat(input);
                        break;
                    case 3:
                        findFirstAvailableSeat();
                        break;
                    case 4:
                        show_seating_plan();
                        break;
                    case 5:
                        print_tickets_info();
                        break;
                    case 6:
                        search_ticket(input);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Option must be a number.");
                input.nextLine();
            }
        } while (true);
    }

    private static String num(int count) {
        //Recurtion to generate "*" caharacters based on the given count
        if (count == 0) {
            return "";
        } else {
            return "*" + num(count - 1);
        }
    }

    private static void buy_seat(Scanner scanner) {
        //Method to handle the process of buying a seat by user
        char row;
        int seatNumber;
        int max_seat;
        String name, surname, email;

        //Loop to check whether the input row is valid or not
        do {
            System.out.println("Enter the row letter: ");
            row = scanner.next().toUpperCase().charAt(0);

            if (row < 'A' || row > 'D') {
                System.out.println("Invalid row letter. Please enter again.");
            }
        } while (row < 'A' || row > 'D');

        max_seat = switch (row) {
            case 'A', 'D' -> 14;
            case 'B', 'C' -> 12;
            default -> 0;
        };

        //Loop to check whether the input seat number is valid or not
        System.out.println("Enter the seat number: ");
        while (true) {
            try {
                seatNumber = scanner.nextInt();
                if (seatNumber < 1 || seatNumber > max_seat) {
                    System.out.println("Invalid seat number. Please enter again: ");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid seat number. Please enter again: ");
                scanner.next();
            }
        }

        //Seat booking process
        if (seat_plan[row - 'A'][seatNumber - 1] == 1) {
            System.out.println("Sorry, This seat is already booked.");
        } else {
            //Inputting customer details
            scanner.nextLine();
            System.out.println("Enter your name: ");
            name = scanner.nextLine();
            System.out.println("Enter surname: ");
            surname = scanner.nextLine();
            System.out.println("Enter email: ");
            email = scanner.nextLine();

            //Creating Person and Ticket objects
            Person person = new Person(name, surname, email);
            Ticket ticket = new Ticket(row, seatNumber, calculatePrice(seatNumber), person);
            tickets[ticketIndex++] = ticket;

            //Adding ticket to the array
            seat_plan[row - 'A'][seatNumber - 1] = 1;
            System.out.println("Seat " + row + seatNumber + " successfully booked");
            ticket.save();
        }
    }

    private static double calculatePrice(int seatNumber) {
        //Method to calculate the price of a seat according to the seat number
        int price;
        if (seatNumber < 6){
            price = 200;
        }
        else if (seatNumber<10){
            price = 150;
        }
        else {
            price = 180;
        }
        return price;
    }

    private static void cancel_seat(Scanner scanner) {
        char row;
        int seatNumber;
        int max_seat;

        //Loop to check whether the input row is valid or not
        do {
            System.out.println("Enter the row letter: ");
            row = scanner.next().toUpperCase().charAt(0);
            if (row < 'A' || row > 'D') {
                System.out.println("Invalid row letter. Please enter again.");
            }
        } while (row < 'A' || row > 'D');
        max_seat = switch (row) {
            //Idenfifing the maximum seats according the row
            case 'A', 'D' -> 14;
            case 'B', 'C' -> 12;
            default -> 0;
        };

        //Loop to check whether the input seat number is valid or not
        while (true) {
            System.out.println("Enter the seat number: ");
            while (true) {
                try {
                    seatNumber = scanner.nextInt();
                    if (seatNumber < 1 || seatNumber > max_seat) {
                        System.out.println("Invalid seat number. Please enter again: ");
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid seat number. Please enter again: ");
                    scanner.next();
                }
            }

            //Handling the seat cancelling process
            if (seat_plan[row - 'A'][seatNumber - 1] == 0) {
                System.out.println("This seat is already available. Please enter another seat number: ");
            } else {
                seat_plan[row - 'A'][seatNumber - 1] = 0;
                System.out.println("Seat " + row + seatNumber + " successfully canceled");

                // Removing the cancelled ticket from the array
                for (int i = 0; i < ticketIndex; i++) {
                    if (tickets[i].getRow() == row && tickets[i].getSeat() == seatNumber) {
                        tickets[i] = null;
                        break; // Exiting loop after removing the ticket
                    }
                }
                break; // Exit the loop if the ticket cancelled succesfully
            }
        }
    }

    //Method to find and display the first availabke seat in the seat plan
    public static void findFirstAvailableSeat() {
        char row = 'A'; //Start searching from row 'A'
        int seatNumber = findAvailableSeatInRow(row, 0);

        if (seatNumber == -1) {
            System.out.println("Sorry, all seats are taken.");
        } else {
            System.out.println("First available seat: " + row + seatNumber);
        }
    }


    private static int findAvailableSeatInRow(char row, int seatNumber) {
        //recurtion to find the first available seat in the specified row
        if (seatNumber >= seat_plan.length) {
            return -1;
        }
        if (seat_plan[row - 'A'][seatNumber] == 0) { // If seat is available it will represent as 0
            return seatNumber + 1;
        }
        return findAvailableSeatInRow(row, seatNumber + 1); //Continue the recursion
    }


    private static void show_seating_plan() {
        //Method to display the seat plan
        System.out.println("Seating Plan:");

        for (int[] ints : seat_plan) {
            for (int j = 0; j < ints.length; j++) {
                if (ints[j] == 0) {
                    System.out.print("O "); //Available seats are represented by 0
                } else {
                    System.out.print("X "); // unavailable seats are represented by X
                }
            }
            System.out.println(); //Move to next row
        }
    }

    private static void print_tickets_info() {
        //Method to display all information about sold tickets and calculate the total
        double totalSales = 0.0;

        for (Ticket ticket : tickets) {
            if (ticket != null) { // Check if the ticket is not null
                ticket.print_Ticket_Info();  //Print ticket information
                totalSales += ticket.getPrice(); //Adding ticket price to total sales
            }
        }
        System.out.println("Total Sales: £" + totalSales);
    }

    private static void search_ticket(Scanner scanner) {
        //Method to search for a ticket by row and seat number
        char row;
        int seatNumber;
        int max_seat;
        String name, surname, email;

        //Loop to check whether the input row is valid or not
        do {
            System.out.println("Enter the row letter: ");
            row = scanner.next().toUpperCase().charAt(0);

            if (row < 'A' || row > 'D') {
                System.out.println("Invalid row letter. Please enter again.");
            }
        } while (row < 'A' || row > 'D');

        max_seat = switch (row) {
            case 'A', 'D' -> 14;
            case 'B', 'C' -> 12;
            default -> 0;
        };

        //Loop to check whether the input seat number is valid or not
        System.out.println("Enter the seat number: ");
        while (true) {
            try {
                seatNumber = scanner.nextInt();
                if (seatNumber < 1 || seatNumber > max_seat) {
                    System.out.println("Invalid seat number. Please enter again: ");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid seat number. Please enter again: ");
                scanner.next();
            }
        }

        boolean found = false;
        for (int i = 0; i < ticketIndex; i++) {
            Ticket ticket = tickets[i];
            if (ticket != null && ticket.getRow() == row && ticket.getSeat() == seatNumber) {
                ticket.print_Ticket_Info(); //if found print ticket information
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("This seat is available."); //if ticket is not found, seat is available
        }
    }
}


