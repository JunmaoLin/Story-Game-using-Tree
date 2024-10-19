/**
 * This class represents a segment of the story.
*/

public class StoryTreeNode{
    static final String WIN_MESSAGE = "YOU WIN";
    static final String LOSE_MESSAGE = "YOU LOSE";
    private String position = "";
    private String option = "";
    private String message = "";

    private StoryTreeNode leftChild;
    private StoryTreeNode middleChild;
    private StoryTreeNode rightChild;

    /**
     * This is the constructor for this class.
     */
    public StoryTreeNode(String position, String option, String message){
        this.position = position;
        this.option = option;
        this.message = message;
    }

    /**
     * Checks if this node has any children.
     * @return
     * return true if this node has any children
     */
    public boolean isLeaf(){
        if(leftChild == null && middleChild == null && rightChild == null){
            return true;
        }
        return false;
    }

    /**
     * Checks if this is a winning node.
     * @return
     */
    public boolean isWinningNode(){
        if(isLeaf() && message.contains(WIN_MESSAGE)){
            return true;
        }
        return false;
    }

    /**
     * Checks if this is a losing node
     * @return
     */
    public boolean isLosingNode(){
        if(isLeaf() && message.contains(LOSE_MESSAGE)){
            return true;
        }
        return false;
    }

    /**
     * this is the setter for position
     * @param position
     *  a string
     */
    public void setPosition(String position){
        this.position = position;
    }

    /**
     * this is the setter for option
     * @param option
     *  a string
     */
    public void setOption(String option){
        this.option = option;
    }

    /**
     * this is the setter for message
     * @param message
     *  a string
     */
    public void setMessage(String message){
        this.message = message;
    }

    /**
     * This is the setter for the leftChild
     * @param leftChild
     *  A StoryTreeNode
     */
    public void setLeftChild(StoryTreeNode leftChild){
        this.leftChild = leftChild;
    }

    /**
     * This is the setter for the middleChild
     * @param middleChild
     *  A StoryTreeNode
     */
    public void setMiddleChild(StoryTreeNode middleChild){
        this.middleChild = middleChild;
    }

    /**
     * This is the setter for the rightChild
     * @param rightChild
     *  A StoryTreeNode
     */
    public void setRightChild(StoryTreeNode rightChild){
        this.rightChild = rightChild;
    }

    /**
     * This is the getter for position
     * @return
     *  A String
     */
    public String getPosition(){
        return this.position;
    }

    /**
     * This is the getter for option
     * @return
     *  a String
     */
    public String getOption(){
        return this.option;
    }

    /**
     * This is the getter for message
     * @return
     *  a String
     */
    public String getMessage(){
        return this.message;
    }

    /**
     * This is the getter for the leftChild
     * @return
     *  a StoryTreeNode
     */
    public StoryTreeNode getLeftChild(){
        return this.leftChild;
    }

    /**
     * This is the getter for the middleChild
     * @return
     *  a StoryTreeNode
     */
    public StoryTreeNode getMiddleChild(){
        return this.middleChild;
    }

    /**
     * This is the getter for the rightChild
     * @return
     *  a StoryTreeNode
     */
    public StoryTreeNode getRightChild(){
        return this.rightChild;
    }

}