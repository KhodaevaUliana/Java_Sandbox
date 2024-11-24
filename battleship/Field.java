package battleship;

import java.util.ArrayList;

public class Field {
    private int size;
    private String[][] cells;
    private ArrayList<String> letters;
    private ArrayList<Ship> ships;

    private void setLetters() {
        char current = 'A';
        this.letters = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            this.letters.add(String.valueOf(current));
            char next = (char)((int)current + 1);
            current = next;
        }
    }


    public Field (int size) {
        this.size = size;
        this.cells = new String[this.size][this.size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.cells[i][j] = "~";
            }
        }
        this.setLetters();
        this.ships = new ArrayList<>();
    }

    public boolean checkShip (Ship watercraft) {
        for (String cell : watercraft.getParts()) {
            int row = this.letters.indexOf(cell.substring(0, 1));
            int column = Integer.valueOf(cell.substring(1, cell.length())) - 1;
            if (!this.checkLocation(row, column)) {
                return false;
            }
        }
        return true;
    }

    public void putShip (Ship watercraft) {
        for (String cell : watercraft.getParts()) {
            int row = this.letters.indexOf(cell.substring(0, 1));
            int column = Integer.valueOf(cell.substring(1, cell.length())) - 1;
            this.cells[row][column] = "O";
        }
        this.ships.add(watercraft);
    }

    private boolean checkLocation(int row, int column) {
        //check left
        if (row > 0) {
            if (this.cells[row - 1][column].equals("O")) {
                return false;
            }
        }
        //check up
        if (column > 0) {
            if (this.cells[row][column - 1].equals("O")) {
                return false;
            }
        }
        //check right
        if (row < (size - 1)) {
            if (this.cells[row + 1][column].equals("O")) {
                return false;
            }
        }
        //check down
        if (column < (size - 1)) {
            if (this.cells[row][column + 1].equals("O")) {
                return false;
            }
        }
        return true;
    }

    public boolean shoot (String coordinate) {
        int row = this.letters.indexOf(coordinate.substring(0, 1));
        int column = Integer.valueOf(coordinate.substring(1, coordinate.length())) - 1;
        if (this.cells[row][column].equals("O")) {
            this.cells[row][column] = "X";
            findAndUpdateShip(coordinate);
            return true;
        }
        if (this.cells[row][column].equals("X")) {
            return true;
        }
        if (this.cells[row][column].equals("~") || this.cells[row][column].equals("M")) {
            this.cells[row][column] = "M";
            return false;
        }
        return false;
    }

    public void findAndUpdateShip(String coordinate) {
        for (Ship ship : this.ships) {
            if (ship.getParts().contains(coordinate)) {
                ship.getShot(coordinate);
            }
        }
    }

    public boolean sunkShips() {
        for (Ship ship : this.ships) {
            if (ship.getLength() == 0) {
                this.ships.remove(ship);
                return true;
            }
        }
        return false;
    }

    public boolean anyShips() {
        if (this.ships.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }



    private void printFirstLine() {
        System.out.print("  ");
        for (int i = 0; i < this.size; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println("");
    }

    private void printIthLine(int i) {
        System.out.print(letters.get(i) + " ");
        for (int j = 0; j < this.cells[0].length; j++) {
            System.out.print(this.cells[i][j] + " ");
        }
        System.out.println("");
    }

    public void printField() {
        this.printFirstLine();
        for (int i = 0; i < this.size; i++) {
            this.printIthLine(i);
        }

    }

    private void printIthLineFogged(int i) {
        System.out.print(letters.get(i) + " ");
        for (int j = 0; j < this.cells[0].length; j++) {
            if (this.cells[i][j].equals("M") || this.cells[i][j].equals("X")) {
                System.out.print(this.cells[i][j] + " ");
            } else {
                System.out.print("~ ");
            }
        }
        System.out.println("");
    }

    public void printFieldFogged() {
        this.printFirstLine();
        for (int i = 0; i < this.size; i++) {
            this.printIthLineFogged(i);
        }
    }
}
