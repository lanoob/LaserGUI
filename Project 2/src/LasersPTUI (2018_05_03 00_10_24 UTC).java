import javax.security.auth.login.Configuration;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *  @author Devon Campbell
 *  @author Oscar Chacon
 */
public class LasersPTUI {

    private int rows;
    private int columns;
    private char [] [] grid;
    private int [] rowCount;
    private int [] columnCount;
    int xPos;
    int yPos;
    private final char LASER = 'L';
    private final char BEAM = '*';
    private final char EMPTY = '.';

    private char b = 'b';
    private char c = 'c';
    private char d = 'd';

    public LasersPTUI(String filename) throws FileNotFoundException {
        Scanner in = new Scanner(new File(filename));
        this.rows = in.nextInt();
        this.columns = in.nextInt();
        this.grid = new char[this.rows] [this.columns];
        this.rowCount = new int[rows];
        this.columnCount = new int[columns];
        this.xPos = -1;
        this.yPos = -1;
        for(int i = 0; i < rows; i++){
            rowCount[i] = i % 10;
        }
        for(int j = 0; j < columns; j++){
            columnCount[j] = j % 10;
        }
        for(int r = 0; r <= rows - 1; r++){
            for(int c = 0; c <= columns - 1; c++){
                grid[r][c] = in.next().charAt(0);
            }
        }
        in.close();
    }

    @Override
    public String toString() {
        String x = "  ";
        for (int i = 0; i < rows; i++){
            x += rowCount[i];
            x += " ";
        }
        x += "\n";
        x += "  ";
        int width = 2 * this.columns - 1;
        for(int i = 0; i < width; i++){
            x += "-";
        }
        x += "\n";
        for(int j = 0; j < columns; j++){
            x += columnCount[j];
            x += "|";
            for(int k = 0; k < columns; k++){
                x += this.grid[j][k];
                x += " ";
            }
                x += "\n";
        }
        x += "> ";
        return x;
    }

    /**
     * Adds a laser to the specified point on the grid.
     * @param x the x coordinate on the grid.
     * @param y the y coordinate on the grid.
     */
    public void addCom(int x, int y){
        if((x < 0) || (x > rows - 1) || (y < 0) || (y > columns - 1)){
            System.out.print("Error adding laser at: (" + x + ", " + y + ")" + "\n");
            displayCom();
        }
        else {
            if (grid[x][y] == EMPTY || grid[x][y] == BEAM) {
                grid[x][y] = LASER;
                for (int i = 1; i < columns; i++) {
                    if ((y + i < columns) && (grid[x][y + i] == EMPTY)) {
                        grid[x][y + i] = BEAM;
                    } else if ((y + i < columns) && (grid[x][y + i] == BEAM)) {
                        grid[x][y + i] = b;
                    } else if ((y + i < columns) && (grid[x][y + i] == b)) {
                        grid[x][y + i] = c;
                    } else if ((y + i < columns) && (grid[x][y + i] == c)) {
                        grid[x][y + i] = d;
                    } else {
                        break;
                    }
                }
                for (int i = 1; i < columns; i++) {
                    if ((y - i >= 0) && (grid[x][y - i] == EMPTY)) {
                        grid[x][y - i] = BEAM;
                    } else if ((y - i >= 0) && (grid[x][y - i] == BEAM)) {
                        grid[x][y - i] = b;
                    } else if ((y - i >= 0) && (grid[x][y - i] == b)) {
                        grid[x][y - i] = c;
                    } else if ((y - i >= 0) && (grid[x][y - i] == c)) {
                        grid[x][y - i] = d;
                    } else {
                        break;
                    }
                }
                for (int i = 1; i < rows; i++) {
                    if ((x + i < rows) && (grid[x + i][y] == EMPTY)) {
                        grid[x + i][y] = BEAM;
                    } else if ((x + i < rows) && (grid[x + i][y] == BEAM)) {
                        grid[x + i][y] = b;
                    } else if ((x + i < rows) && (grid[x + i][y] == b)) {
                        grid[x + i][y] = c;
                    } else if ((x + i < rows) && (grid[x + i][y] == c)) {
                        grid[x + i][y] = d;
                    } else {
                        break;
                    }
                }
                for (int i = 1; i < rows; i++) {
                    if ((x - i >= 0) && (grid[x - i][y] == EMPTY)) {
                        grid[x - i][y] = BEAM;
                    } else if ((x - i >= 0) && (grid[x - i][y] == BEAM)) {
                        grid[x - i][y] = b;
                    } else if ((x - i >= 0) && (grid[x - i][y] == b)) {
                        grid[x - i][y] = c;
                    } else if ((x - i >= 0) && (grid[x - i][y] == c)) {
                        grid[x - i][y] = d;
                    } else {
                        break;
                    }
                }
                System.out.print("Laser added at: (" + x + ", " + y + ")" + "\n");
                displayCom();
            } else {
                System.out.print("Error adding laser at: (" + x + ", " + y + ")" + "\n");
                displayCom();
            }
        }
    }

    /**
     * Quits the program.
     */
    public void quitCom(){
        System.exit(0);
    }

    /**
     * Displays current grid.
     */
    public void displayCom(){
        System.out.print(this.toString());
    }

    /**
     * Displays all available commands.
     */
    public void helpCom(){
        System.out.println("a|add r c: Add laser to (r,c)");
        System.out.println("d|display: Display safe");
        System.out.println("h|help: Print this help message");
        System.out.println("q|quit: Exit program");
        System.out.println("r|remove r c: Remove laser from (r,c)");
        System.out.println("v|verify: Verify safe correctness");
        System.out.print("> ");
    }

    /**
     * Removes a laser at the given point on the grid.
     * @param x x coordinate on the grid.
     * @param y y coordinate on the grid.
     */
    public void removeCom(int x, int y){
        if((x < 0) || (x > rows - 1) || (y < 0) || (y > columns - 1)){
            System.out.print("Error removing laser at: (" + x + ", " + y + ")" + "\n");
            displayCom();
        }
        else {
            if (grid[x][y] == LASER) {
                grid[x][y] = EMPTY;
                for (int i = 1; i < columns; i++) {
                    if ((y + i < columns) && (grid[x][y + i] == BEAM)) {
                        grid[x][y + i] = EMPTY;
                    } else if ((y + i < columns) && (grid[x][y + i] == b)) {
                        grid[x][y + i] = BEAM;
                    } else if ((y + i < columns) && (grid[x][y + i] == c)) {
                        grid[x][y + i] = b;
                    } else if ((y + i < columns) && (grid[x][y + i] == d)) {
                        grid[x][y + i] = c;
                    } else {
                        break;
                    }
                }
                for (int i = 1; i < columns; i++) {
                    if ((y - i >= 0) && (grid[x][y - i] == BEAM)) {
                        grid[x][y - i] = EMPTY;
                    } else if ((y - i >= 0) && (grid[x][y - i] == b)) {
                        grid[x][y - i] = BEAM;
                    } else if ((y - i >= 0) && (grid[x][y - i] == c)) {
                        grid[x][y - i] = b;
                    } else if ((y - i >= 0) && (grid[x][y - i] == d)) {
                        grid[x][y - i] = c;
                    } else {
                        break;
                    }
                }
                for (int i = 1; i < rows; i++) {
                    if ((x + i < rows) && (grid[x + i][y] == BEAM)) {
                        grid[x + i][y] = EMPTY;
                    } else if ((x + i < rows) && (grid[x + i][y] == b)) {
                        grid[x + i][y] = BEAM;
                    } else if ((x + i < rows) && (grid[x + i][y] == c)) {
                        grid[x + i][y] = b;
                    } else if ((x + i < rows) && (grid[x + i][y] == d)) {
                        grid[x + i][y] = c;
                    } else {
                        break;
                    }
                }
                for (int i = 1; i < rows; i++) {
                    if ((x - i >= 0) && (grid[x - i][y] == BEAM)) {
                        grid[x - i][y] = EMPTY;
                    } else if ((x - i >= 0) && (grid[x - i][y] == b)) {
                        grid[x - i][y] = BEAM;
                    } else if ((x - i >= 0) && (grid[x - i][y] == c)) {
                        grid[x - i][y] = d;
                    } else if ((x - i >= 0) && (grid[x - i][y] == d)) {
                        grid[x - i][y] = c;
                    } else {
                        break;
                    }
                }
                System.out.print("Laser removed at: (" + x + ", " + y + ")" + "\n");
                for (int r = 0; r <= rows - 1; r++) {
                    for (int c = 0; c <= columns - 1; c++) {
                        char ch = grid[r][c];
                        if (ch == LASER) {
                            fixLaser(r, c);
                        }
                    }
                }
                displayCom();
            } else {
                System.out.print("Error removing laser at: (" + x + ", " + y + ")" + "\n");
                displayCom();
            }
        }
    }

    /**
     * Fixes the laser beams after removing any invalid lasers.
     * @param x x coordinate on the grid.
     * @param y y coordinate on the grid.
     */
    public void fixLaser(int x, int y){
        grid [x] [y] = EMPTY;
        for(int i = 1; i < columns; i++) {
            if ((y  + i < columns) && (grid[x] [y + i] == BEAM)){
                grid[x] [y + i] = EMPTY;
            }
            else if ((y + i < columns) && (grid[x] [y + i] == b)){
                grid[x] [y + i] = BEAM;
            }
            else if ((y + i < columns) && (grid[x] [y + i] == c)){
                grid[x] [y + i] = b;
            }
            else if ((y + i < columns) && (grid[x] [y + i] == d)){
                grid[x] [y + i] = c;
            }
            else {
                break;
            }
        }
        for(int i = 1; i < columns; i++) {
            if ((y - i >= 0) && (grid[x] [y - i] == BEAM)){
                grid[x] [y - i] = EMPTY;
            }
            else if ((y - i >= 0) && (grid[x] [y - i] == b)){
                grid[x] [y - i] = BEAM;
            }
            else if ((y - i >= 0) && (grid[x] [y - i] == c)){
                grid[x] [y - i] = b;
            }
            else if ((y - i >= 0) && (grid[x] [y - i] == d)){
                grid[x] [y - i] = c;
            }
            else {
                break;
            }
        }
        for(int i = 1; i < rows; i++) {
            if ((x + i < rows) && (grid[x + i] [y] == BEAM)){
                grid[x + i] [y] = EMPTY;
            }
            else if ((x + i < rows) && (grid[x + i] [y] == b)){
                grid[x + i] [y] = BEAM;
            }
            else if ((x + i < rows) && (grid[x + i] [y] == c)){
                grid[x + i] [y] = b;
            }
            else if ((x + i < rows) && (grid[x + i] [y] == d)){
                grid[x + i] [y] = c;
            }
            else {
                break;
            }
        }
        for(int i = 1; i < rows; i++) {
            if ((x - i >= 0) && (grid[x - i] [y] == BEAM)){
                grid[x - i] [y] = EMPTY;
            }
            else if ((x - i >= 0) && (grid[x - i] [y] == b)){
                grid[x - i] [y] = BEAM;
            }
            else if ((x - i >= 0) && (grid[x - i] [y] == c)){
                grid[x - i] [y] = d;
            }
            else if ((x - i >= 0) && (grid[x - i] [y] == d)){
                grid[x - i] [y] = c;
            }
            else {
                break;
            }
        }
        grid [x] [y] = LASER;
        for(int i = 1; i < columns; i++) {
            if ((y + i < columns) && (grid[x] [y + i] == EMPTY)){
                grid[x] [y + i] = BEAM;
            }
            else if ((y + i < columns) && (grid[x] [y + i] == BEAM)){
                grid[x] [y + i] = b;
            }
            else if ((y + i < columns) && (grid[x] [y + i] == b)){
                grid[x] [y + i] = c;
            }
            else if ((y + i < columns) && (grid[x] [y + i] == c)){
                grid[x] [y + i] = d;
            }
            else {
                break;
            }
        }
        for(int i = 1; i < columns; i++) {
            if ((y - i >= 0) && (grid[x] [y - i] == EMPTY)){
                grid[x] [y - i] = BEAM;
            }
            else if ((y - i >= 0) && (grid[x] [y - i] == BEAM)){
                grid[x] [y - i] = b;
            }
            else if ((y - i >= 0) && (grid[x] [y - i] == b)){
                grid[x] [y - i] = c;
            }
            else if ((y - i >= 0) && (grid[x] [y - i] == c)){
                grid[x] [y - i] = d;
            }
            else {
                break;
            }
        }
        for(int i = 1; i < rows; i++) {
            if ((x + i < rows) && (grid[x + i] [y] == EMPTY)){
                grid[x + i] [y] = BEAM;
            }
            else if ((x + i < rows) && (grid[x + i] [y] == BEAM)){
                grid[x + i] [y] = b;
            }
            else if ((x + i < rows) && (grid[x + i] [y] == b)){
                grid[x + i] [y] = c;
            }
            else if ((x + i < rows) && (grid[x + i] [y] == c)){
                grid[x + i] [y] = d;
            }
            else {
                break;
            }
        }
        for(int i = 1; i < rows; i++) {
            if ((x - i >= 0) && (grid[x - i] [y] == EMPTY)){
                grid[x - i] [y] = BEAM;
            }
            else if ((x - i >= 0) && (grid[x - i] [y] == BEAM)){
                grid[x - i] [y] = b;
            }
            else if ((x - i >= 0) && (grid[x - i] [y] == b)){
                grid[x - i] [y] = c;
            }
            else if ((x - i >= 0) && (grid[x - i] [y] == c)){
                grid[x - i] [y] = d;
            }
            else {
                break;
            }
        }
    }

    /**
     * Verifies if current board is a valid solution.
     */
    public void verifyCom(){
        char ch;
        boolean isCorrect = false;
        for(int r = 0; r <= rows - 1; r++) {
            if (isCorrect){
                displayCom();
                break;
            }
            for (int c = 0; c <= columns - 1; c++) {
                ch = grid[r] [c];
                if (ch == '0' || ch == '1' || ch == '2' || ch == '3' || ch == '4'){
                    if(!columnCheck(r, c, ch)){
                        System.out.println("Error verifying at: (" + r + ", " + c + ")");
                        isCorrect = true;
                        displayCom();
                        break;
                    }
                }
                else if (ch == EMPTY){
                    System.out.println("Error verifying at: (" + r + ", " + c + ")");
                    isCorrect = true;
                    break;
                }
                else if (ch == LASER) {
                    for (int i = 1; i < columns; i++) {
                        if ((c + i < columns) && (grid[r] [c + i] == LASER)) {
                            System.out.println("Error verifying at: (" + r + ", " + c + ")");
                            isCorrect = true;
                            break;
                        }
                    }
                    for (int i = 1; i < columns; i++){
                        if ((c - i >= 0) && (grid[r] [c - i] == LASER)){
                            System.out.println("Error verifying at: (" + r + ", " + c + ")");
                            isCorrect = true;
                            break;
                        }
                    }
                    for (int i = 1; i < rows; i++) {
                        if ((r + i < rows) && (grid[r + i] [c] == LASER)) {
                            System.out.println("Error verifying at: (" + r + ", " + c + ")");
                            isCorrect = true;
                            break;
                        }
                    }
                    for (int i = 1; i < rows; i++) {
                        if ((r - i >= 0) && (grid[r - i] [c] == LASER)) {
                            System.out.println("Error verifying at: (" + r + ", " + c + ")");
                            isCorrect = true;
                            break;
                        }
                    }
                }
                else if (ch == BEAM || ch == 'X' || ch == 'b' || ch == 'c' || ch == 'd'){
                }
            }
        }
        if(!isCorrect) {
            System.out.println("Safe is fully verified!");
            displayCom();
        }
    }

    /**
     * Checks to see if all columns have appropriate lasers attached.
     * @param x x coordinate on the grid.
     * @param y y coordinate on the grid.
     * @param ch column being checked.
     * @return boolean value true or false.
     */
    public boolean columnCheck(int x, int y, char ch){
        boolean isCorrect = false;
        int count = 0;
        int outletNum = Character.getNumericValue(ch);

        if ((y + 1 < columns) && (grid[x] [y + 1] == LASER)){
            count += 1;
        }
        if ((y - 1 >= 0) && (grid[x] [y - 1] == LASER)){
            count += 1;
        }
        if ((x + 1 < rows) && (grid[x + 1] [y] == LASER)){
            count += 1;
        }
        if ((x - 1 >= 0) && (grid[x - 1] [y] == LASER)){
            count += 1;
        }
        if(count == outletNum){
            isCorrect = true;
        }

        return isCorrect;
    }

    /**
     * Runs the program.
     * @param args either one or two files that run the program.
     * @throws FileNotFoundException throws if no file is given or if it is not found.
     */
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length == 1) {
            LasersPTUI l = new LasersPTUI(args[0]);
            l.displayCom();
            Scanner reader = new Scanner(System.in);
            while (true) {
                String [] input = reader.nextLine().split(" ");
                char command = ' ';
                try {
                    command = input[0].charAt(0);
                }
                catch(StringIndexOutOfBoundsException e){
                    System.out.print("> ");
                }
                    switch (command) {

                        case ' ':
                            break;

                        case 'a':
                            if (input.length != 3) {
                                System.out.println("Incorrect coordinates");
                                System.out.print("> ");
                                break;
                            }
                            l.addCom(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
                            break;

                        case 'd':
                            l.displayCom();
                            break;

                        case 'h':
                            l.helpCom();
                            break;

                        case 'q':
                            l.quitCom();
                            break;

                        case 'r':
                            if (input.length != 3) {
                                System.out.println("Incorrect coordinates");
                                System.out.print("> ");
                                break;
                            }
                            l.removeCom(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
                            break;

                        case 'v':
                            l.verifyCom();
                            break;

                        default:
                            System.out.println("Unrecognized command: " + input[0]);
                            System.out.print("> ");
                    }
                }
            }
        else if(args.length == 2) {
            LasersPTUI l = new LasersPTUI(args[0]);
            l.displayCom();
            Scanner in = new Scanner(new File(args[1]));
            while (in.hasNextLine()) {
                String[] input = in.nextLine().split(" ");
                char command = input[0].charAt(0);
                switch (command) {

                    case 'a':
                        if (input.length != 3) {
                            System.out.println("Incorrect coordinates");
                            System.out.print("> ");
                            break;
                        }
                        System.out.println(input[0] + " " + input[1] + " " + input[2]);
                        l.addCom(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
                        break;

                    case 'd':
                        l.displayCom();
                        break;

                    case 'h':
                        l.helpCom();
                        break;

                    case 'q':
                        l.quitCom();
                        break;

                    case 'r':
                        if (input.length != 3) {
                            System.out.println("Incorrect coordinates");
                            System.out.print("> ");
                            break;
                        }
                        System.out.println(input[0] + " " + input[1] + " " + input[2]);
                        l.removeCom(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
                        break;

                    case 'v':
                        l.verifyCom();
                        break;

                    default:
                        System.out.println("Unrecognized command: " + input[0]);
                        System.out.print("> ");
                }
            }
            l.verifyCom();
        }
        else{
            System.out.println("Usage: java LasersPTUI safe-file [input]");
            System.exit(1);
        }

    }
}
