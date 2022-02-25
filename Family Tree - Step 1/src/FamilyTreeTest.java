import java.util.ArrayList;
import java.util.List;

public class FamilyTreeTest {

    public static void main(String[] args){
        // Ask user to input our first ancestor, who will be the root of our tree
        String name = Input.getString("input the family member's name: ");
        // Initialising our list of names. This will be used in the future to check if a name is unique or not
        List names = new ArrayList();

        FamilyTree familyTree = new FamilyTree(name);
        Integer option;

        // A simple interface for the program
        do {
            System.out.println("0: quit");
            System.out.println("1: add child to current");
            System.out.println("2: add partner to current");
            System.out.println("3: set current family member");
            System.out.println("4: display current");
            System.out.println("5: display family tree");
            option = Input.getInteger("input option: ");
            switch (option){
                case 1:
                        name = Input.getString("input the child's name: ");
                        // We check if we can add a child, if we can the program continues...
                        try{
                            if(names.contains(name)){
                                throw new FamilyTree.NonUniqueNameException();
                            } else {
                                familyTree.addChild(name);
                                names.add(name);
                            }
                        // ... otherwise we throw an exception
                        } catch(FamilyTree.NonUniqueNameException e){
                            System.out.println("ERROR: CHILD'S NAME IS NOT UNIQUE");
                        }
                    break;

                case 2:
                    // We ask the user for the partner's name before passing this name string to our addPartner method
                    name = Input.getString("input the partner's name: ");
                    familyTree.addPartner(name);

                    break;

                case 3:
                    name = Input.getString("input the family member's name: ");
                    // We try to search our tree using the family member's name...
                    try{
                        familyTree.findFamilyMember(name);
                        System.out.println(familyTree.getCurrent());
                    // ... but if we can't find them, we throw an exception
                    } catch(FamilyTree.FamilyMemberNotFoundException e){
                        System.out.println("ERROR: FAMILY MEMBER NOT FOUND");
                    }
                    break;

                case 4:
                    // Displays the details of the current family member
                    System.out.println(familyTree.getCurrent());
                    break;

                case 5:
                    // Displays the family tree in its entirety
                    System.out.println(familyTree);
                    break;

                default:
                    // Anything except the number 0-5 results in an invalid command
                    System.out.println("Invalid Command!");
                    break;

            }
        } while (option != 0);
    }
}
