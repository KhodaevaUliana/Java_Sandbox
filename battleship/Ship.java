package battleship;

import java.util.ArrayList;

public class Ship {
    private String start;
    private String end;
    private boolean vertical; //true if vertical; false if horizontal
    private ArrayList<String> parts;



    private void setPartsForHorizontal(char row, int start, int end) {
        if (start < end) {
            for (int i = start; i <= end; i++) {
                String part = String.valueOf(row) + i;
                this.parts.add(part);
            }
        }  else {
            for (int i = start; i >= end; i--) {
                String part = String.valueOf(row) + i;
                this.parts.add(part);
            }
        }
        //System.out.println(parts);
    }

    private void setPartsForVertical(char rowStart, char rowEnd, int column) {
        if (rowStart < rowEnd) {
            for (char c = rowStart; c <= rowEnd; c++) {
                String part = String.valueOf(c) + column;
                this.parts.add(part);
            }
        } else {
            for (char c = rowStart; c >= rowEnd; c--) {
                String part = String.valueOf(c) + column;
                this.parts.add(part);
            }
        }
        //System.out.println(parts);
    }
    public static boolean checkCorrectnessCoordinate(String input) {
        char letter = input.charAt(0);
        int number = Integer.valueOf(input.substring(1, input.length()));
        if ((number < 1) || (number > 10)) {
            return false;
        }
        if ((letter < 'A') || (letter > 'J')) {
            return false;
        }

        return true;
    }

    private boolean checkAndSetConfig() {
        int numStart = Integer.valueOf(this.start.substring(1, this.start.length()));
        int numEnd = Integer.valueOf(this.end.substring(1, this.end.length()));

        if (this.start.charAt(0) == this.end.charAt(0)) {
            this.setPartsForHorizontal(this.start.charAt(0), numStart, numEnd);
            return true;
        }


        if (numStart == numEnd) {
            this.vertical = true;
            this.setPartsForVertical(this.start.charAt(0), this.end.charAt(0), numStart);
            return true;
        }
        return false;
    }

    public Ship (String start, String end) throws Exception {
        if (checkCorrectnessCoordinate(start) && checkCorrectnessCoordinate(end)) {
            this.start = start;
            this.end = end;
            this.parts = new ArrayList<>();
        } else {
            throw new Exception("Error!");
        }

        if (!checkAndSetConfig()) {
            throw new Exception ("Error! Wrong ship location! Try again:");
        }
    }

    public int getLength() {
        return this.parts.size();
    }

    public ArrayList<String> getParts() {
        return this.parts;
    }

    public void getShot (String coordinate) {
        this.parts.remove(coordinate);
    }


}
