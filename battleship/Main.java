package battleship;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Field Field1 = new Field(10);
        Field Field2 = new Field(10);
        Scanner scanner = new Scanner(System.in);
        putShipsPlayerN(1, Field1, scanner);
        putShipsPlayerN(2, Field2, scanner);
        System.out.println("The game starts!");
        int currPlayer = 1;
        boolean smbWon = false;
        while (!smbWon) {
            smbWon = playersTurn(currPlayer, Field1, Field2, scanner);
            if (currPlayer == 1) {
                currPlayer = 2;
            } else {
                currPlayer = 1;
            }
        }
    }

    public static boolean playersTurn (int player, Field Field1, Field Field2, Scanner scanner) {
        Field myField = Field1;
        Field oppField = Field2;
        if (player == 2) {
            myField = Field2;
            oppField = Field1;
        }
        oppField.printFieldFogged();
        for (int i = 0; i < 21; i++) {
            System.out.print("-");
        }
        System.out.println("");
        myField.printField();
        System.out.println("");
        System.out.printf("Player %d, it's your turn:\n\n", player);
        boolean res = shootingEvent(oppField, scanner);
        System.out.println("Press Enter and pass the move to another player");
        System.out.println("...");
        String input = scanner.nextLine();
        return res;

    }

    public static void putShipsPlayerN (int n, Field currField, Scanner scanner) {
        System.out.printf("Player %d, place your ships on the game field\n", n);
        currField.printField();
        putShips(currField, scanner);
        System.out.println("Press Enter and pass the move to another player");
        System.out.println("...");
        String input = scanner.nextLine();
    }

    public static void putShips (Field currField, Scanner scanner) {
        putSpecificShip("Aircraft Carrier", 5, currField, scanner);
        System.out.println("");
        currField.printField();
        putSpecificShip("Battleship", 4, currField, scanner);
        currField.printField();
        putSpecificShip("Submarine", 3, currField, scanner);
        currField.printField();
        putSpecificShip("Cruiser", 3, currField, scanner);
        currField.printField();
        putSpecificShip("Destroyer", 2, currField, scanner);
        currField.printField();
    }

    public static void putSpecificShip(String type, int size, Field currField, Scanner scanner) {
        System.out.printf("Enter the coordinates of the %s (%d cells):\n", type, size);
        boolean done = false;
        while (!done) {
            String inputLine = scanner.nextLine();
            String[] inputSplits = inputLine.split(" ");
            try {
                Ship testShip = new Ship(inputSplits[0], inputSplits[1]);
                if (testShip.getLength() != size) {
                    System.out.printf("Error! Wrong length of the %s! Try again:\n", type);
                } else if (!currField.checkShip(testShip)) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                } else {
                    currField.putShip(testShip);
                    done = true;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }






    public static boolean shootingEvent (Field currField, Scanner scanner) {
        boolean correctCoordinate = false;
        boolean win = false;
        while (!correctCoordinate) {
            String input = scanner.nextLine();
            if (!Ship.checkCorrectnessCoordinate(input)) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
            } else {
                correctCoordinate = true;
                if (currField.shoot(input)) {
                    //currField.printFieldFogged();
                    System.out.println("");
                    if (currField.sunkShips()) {
                        if (currField.anyShips()) {
                            System.out.println("You sank a ship!");
                            return win;
                        } else {
                            System.out.println("You sank the last ship. You won. Congratulations!");
                            win = true;
                            return win;
                        }
                    } else {
                        System.out.println("You hit a ship!");
                        return win;
                    }
                } else {
                    //currField.printFieldFogged();
                    System.out.println("");
                    System.out.println("You missed!");
                    return win;
                }
            }
        }
        return win;
    }




}


