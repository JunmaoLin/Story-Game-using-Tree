import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.zip.DataFormatException;

/**
 * This class represents a Zork game.
*/
public class Zork {
    public static Scanner scan = new Scanner(System.in);
    public static PrintWriter fileOut;
    public static void main(String[] args){
        System.out.println("Hello and Welcome to Zork!\n");
        System.out.print("Please enter the file name: ");
        StoryTree storyTree = new StoryTree();
        String fileName = scan.nextLine();
        System.out.println("\nLoading game from file...\n");
        try{
            // StoryTree.saveTree(fileName, storyTree);
            storyTree = StoryTree.readTree(fileName);
        }catch(Exception e){
            System.out.println("The file name you entered is invalid. An empty tree will be used.\n");
        }
        System.out.println("File loaded!\n");
        storyTree.resetCursor();
        System.out.print("Would you like to edit (E), play (P) or quit (Q)?  ");
        String userInput = scan.nextLine().trim().toUpperCase();

        while(!userInput.equals("Q")){
            switch(userInput){
                case "E":
                    editTree(storyTree);
                    break;
                case "P":
                    playTree(storyTree);
                    break;
                default:
                    System.out.println("Invalid option.");
                    System.out.println("Try again.");
                    break;
            }
            System.out.print("Would you like to edit (E), play (P) or quit (Q)?  ");
            userInput = scan.nextLine().trim().toUpperCase();
        }

        System.out.println("\nGame being saved to " + fileName + "...");
        try {
            fileOut = new PrintWriter(fileName);
            storyTree.resetCursor();
            try{
                storyTree.selectChild("1");
            }catch(Exception e){
                System.out.println("Error: missing root.");
            }
            
            preOrderTraversalForPrintWriter(storyTree.getCursor());
            
            fileOut.close();
        
        } catch (FileNotFoundException fnfe) {
            System.out.println("No such file: " + fnfe);
        }

        System.out.println("\nSave successful!");
        System.out.println("\nProgram terminating normally.");
    }

    /**
     * This is a helper method for preOrder traversal.
     * @param root
     *  StoryTreeNode
     */
    public static void preOrderTraversalForPrintWriter(StoryTreeNode root){
        if(root == null){
            return;
        }
        fileOut.println(root.getPosition() + " | " + root.getOption() + " | " + root.getMessage());
        preOrderTraversalForPrintWriter(root.getLeftChild());
        preOrderTraversalForPrintWriter(root.getMiddleChild());
        preOrderTraversalForPrintWriter(root.getRightChild());

    }

    /**
     * This method is used for editing the StoryTree
     * @param tree
     *  StoryTree
     */
    public static void editTree(StoryTree tree){
        zorkEditorMenu();
        System.out.print("Please select an option: "); 
        String userInput = scan.nextLine().trim().toUpperCase();
        // tree.resetCursor();
        // try{
        //     tree.selectChild("1");
        // }catch(Exception e){
        //     System.out.println("Error: the root contains nothing.");
        // }
        while(!userInput.equals("Q")){
            switch(userInput){
                case "V":
                    System.out.println();
                    System.out.println("Position: " + tree.getCursorPosition());
                    System.out.println("Option: " + tree.getCursor().getOption());
                    System.out.println("Message: " + tree.getCursorMessage());
                    break;
                case "S":
                    System.out.println();
                    String[][] options = tree.getOptions();
                    String children = "";
                    for(int j = 0; j < options.length; j++){
                        if(j == 0){
                            children += "[" + (j+1);
                        }
                        else{
                            children += "," + (j+1);
                        }
                        if(j == options.length - 1){
                            children += "]";
                        }
                    }
                    if(tree.getCursor().isLosingNode() || tree.getCursor().isWinningNode()){
                        System.out.println("no child ");
                        break;
                    }
                    System.out.print("Please select a child: " + children + " ");
                    int child = scan.nextInt();
                    scan.nextLine();
                    try{
                        tree.selectChild(tree.getCursorPosition() + "-" + child);
                    }catch(Exception e){
                        System.out.println();
                        System.out.println("Error. No child " + child + " for the current node.");
                    }
                    break;
                case "O":
                    System.out.print("Please enter a new option: ");
                    String newOption = scan.nextLine();
                    tree.setCursorOption(newOption);
                    System.out.println("\nOption set.");
                    break;
                case "M":
                    System.out.print("Please enter a new Message: ");
                    String newMessage = scan.nextLine();
                    tree.setCursorMessage(newMessage);
                    System.out.println("\nMessage set.");
                    break;
                case "A":
                    System.out.print("Enter an option: ");
                    String childOption = scan.nextLine();
                    System.out.print("Enter a message: ");
                    String childMessage = scan.nextLine();
                    try{
                        tree.addChild(childOption, childMessage);
                    }catch(TreeFullException e){
                        System.out.println("Error: tree is full.");
                        break;
                    }catch(InvalidArgumentException e){
                        System.out.println("Error: invalid argument");
                    }
                    System.out.println("\nChild added.");
                    break;
                case "D":
                    System.out.println();
                    if(tree.getCursor().isLosingNode() || tree.getCursor().isWinningNode()){
                        System.out.println("no child ");
                        break;
                    }
                    String[][] option = tree.getOptions();
                    String childrens = "";
                    for(int i = 0; i < option.length; i++){
                        if(i == 0){
                            childrens += "[" + (i+1);
                        }
                        else{
                            childrens += "," + (i+1);
                        }
                        if(i == option.length - 1){
                            childrens += "]";
                        }
                    }
                    System.out.print("Please select a child: " + childrens + " ");
                    String childToBeDeleted = scan.nextLine();
                    try{
                        tree.removeChild(tree.getCursorPosition() + "-" + childToBeDeleted);
                    }catch(Exception e){
                        System.out.println("Error. No child " + childToBeDeleted + " for the current node.");
                        break;
                    }
                    System.out.println("Subtree deleted.");
                    break;
                case "R":
                    tree.resetCursor();
                    try{
                        tree.selectChild("1");
                    }catch(Exception e){
                        System.out.println("Error: the root contains nothing.");
                    }
                    System.out.println("Cursor moved to root.");
                    break;
                default:
                    System.out.println();
                    System.out.println("Invalid option.");
                    System.out.println("Try again.");
                    break;
            }
            zorkEditorMenu();
            System.out.print("Please select an option: "); 
            userInput = scan.nextLine().trim().toUpperCase();
        }
        System.out.println();
    }

    public static void zorkEditorMenu(){
        System.out.println();
        System.out.println("Zork Editor:");
        System.out.println("    V: View the cursor's position, option and message.");
        System.out.println("    S: Select a child of this cursor (options are 1, 2, and 3).");
        System.out.println("    O: Set the option of the cursor.");
        System.out.println("    M: Set the message of the cursor.");
        System.out.println("    A: Add a child StoryNode to the cursor.");
        System.out.println("    D: Delete one of the cursor's children and all its descendants.");
        System.out.println("    R: Move the cursor to the root of the tree.");
        System.out.println("    Q: Quit editing and return to main menu.");
        System.out.println();
    }

    /**
     * This method is used to play the story tree
     * @param tree
     *  StoryTree
     */
    public static void playTree(StoryTree tree){
        try{
            tree.selectChild("1");
        }catch(Exception e){
            System.out.println("Missing root.");
        }
        System.out.println();
        System.out.println(tree.getCursor().getOption());
        System.out.println();
        while(!tree.getCursor().isLeaf()){
            System.out.println(tree.getCursorMessage());
            String[][] messages = tree.getOptions();
            for(int i = 0; i < messages.length; i++){
                System.out.println((i+1) + ") " + messages[i][1]);
            }
            System.out.print("Please make a choice. ");
            String decision = scan.nextLine();
            //scan.nextLine();
            while(!decision.equals("1") && !decision.equals("2") && !decision.equals("3")){
                System.out.print("Error. Please try again (options are 1, 2, and 3): ");
                decision = scan.nextLine();
                //scan.nextLine();
            }
            try{
                if(decision.equals("1")){
                    tree.selectChild(tree.getCursorPosition() + "-" + 1);
                }
                else if(decision.equals("2")){
                    tree.selectChild(tree.getCursorPosition() + "-" + 2);
                }
                else if(decision.equals("3")){
                    tree.selectChild(tree.getCursorPosition() + "-" + 3);
                }
                System.out.println();
            }catch(Exception e){
                System.out.println("Error when selecting child.");
            }
        }
        System.out.println(tree.getCursorMessage());
        System.out.println("\nThanks for playing.\n");
    }
}
