package ptui;

import model.LasersModel;
import java.util.Scanner;

/**
 * This class represents the controller portion of the plain text UI.
 * It takes the model from the view (LasersPTUI) so that it can perform
 * the operations that are input in the run method.
 *
 * @author Sean Strout @ RIT CS
 * @author Oscar Chacon (orc2815@rit.edu)
 * @author Devon Campbell (dmc3133@rit.edu)
 */
public class ControllerPTUI  {
    /** The UI's connection to the model */
    private LasersModel model;

    /**
     * Construct the PTUI.  Create the model and initialize the view.
     * @param model The laser model
     */
    public ControllerPTUI(LasersModel model) {
        this.model = model;
    }

    /**
     * Run the main loop.  This is the entry point for the controller
     * @param inputFile The name of the input command file, if specified
     */
    public void run(String inputFile) {
        if (inputFile == null) {
            Scanner reader = new Scanner(System.in);
            this.model.displayCom();
            while (true) {
                String[] input = reader.nextLine().split(" ");
                char command = ' ';
                try {
                    command = input[0].charAt(0);
                } catch (StringIndexOutOfBoundsException e) {
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
                        this.model.addCom(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
                        this.model.notifyObservers();

                        break;

                    case 'd':
                        this.model.displayCom();
                        break;

                    case 'h':
                        this.model.helpCom();
                        break;

                    case 'q':
                        this.model.quitCom();
                        break;

                    case 'r':
                        if (input.length != 3) {
                            System.out.println("Incorrect coordinates");
                            System.out.print("> ");
                            break;
                        }
                        this.model.removeCom(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
                        this.model.notifyObservers();
                        break;

                    case 'v':
                        this.model.verifyCom();
                        this.model.notifyObservers();
                        break;

                    default:
                        System.out.println("Unrecognized command: " + input[0]);
                        System.out.print("> ");
                }
            }
        }
        else{
            Scanner in = new Scanner(inputFile);
            this.model.displayCom();
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
                        this.model.addCom(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
                        this.model.notifyObservers();
                        break;

                    case 'd':
                        this.model.displayCom();
                        break;

                    case 'h':
                        this.model.helpCom();
                        break;

                    case 'q':
                        this.model.quitCom();
                        break;

                    case 'r':
                        if (input.length != 3) {
                            System.out.println("Incorrect coordinates");
                            System.out.print("> ");
                            break;
                        }
                        System.out.println(input[0] + " " + input[1] + " " + input[2]);
                        this.model.removeCom(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
                        this.model.notifyObservers();
                        break;

                    case 'v':
                        this.model.verifyCom();
                        this.model.notifyObservers();
                        break;

                    default:
                        System.out.println("Unrecognized command: " + input[0]);
                        System.out.print("> ");
                }
            }
            this.model.verifyCom();
        }
    }
}
