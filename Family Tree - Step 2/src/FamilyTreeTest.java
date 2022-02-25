import java.util.ArrayList;
import java.util.List;

public class FamilyTreeTest {

    public static void main(String[] args){
        // Ask user to input our first ancestor, who will be the root of our tree
        String name = Input.getString("input the family member's name: ");
        // Initialising our list of names. This will be used in the future to check if a name is unique or not
        List names = new ArrayList();

        // Initialising our identifier
        Integer identifier = 1;
        Integer inputId;

        FamilyTree familyTree = new FamilyTree(name, identifier);
        // We increment the identifier anytime we add a new node. This ensures each ID is unique.
        identifier++;
        Integer option;
        // A simple interface for the program
        do {
            System.out.println("0: quit");
            System.out.println("1: add child to current");
            System.out.println("2: add partner to current");
            System.out.println("3: set current family member");
            System.out.println("4: display current");
            System.out.println("5: display family tree");
            System.out.println("6: get family member via identifier");
            option = Input.getInteger("input option: ");
            switch (option){
                case 1:
                    // We check if we can add a child, if we can the program continues...
                    try {
                        name = Input.getString("input the child's name: ");
                        try{
                            if(names.contains(name)){
                                throw new FamilyTree.NonUniqueNameException();
                            } else {
                                familyTree.addChild(name, identifier);
                                names.add(name);
                            }
                    // ... otherwise we throw an exception here
                        } catch(FamilyTree.NonUniqueNameException e){
                            System.out.println("ERROR: CHILD'S NAME IS NOT UNIQUE");
                        }
                        // We increment the identifier anytime we add a new node. This ensures each ID is unique.
                        identifier++;
                    // ... or we throw and exception here
                    } catch(FamilyTree.ChildWithoutPartnerException e){
                        System.out.println("ERROR: CAN'T ADD CHILD WITHOUT PARTNER");
                    }
                    break;

                case 2:
                    // We try to collect the partner's name before adding them
                    try{
                        name = Input.getString("input the partner's name: ");
                        familyTree.addPartner(name, identifier);
                        // We increment the identifier anytime we add a new node. This ensures each ID is unique.
                        identifier++;
                    // but if the person already has a partner, we don't add it and throw an exception
                    } catch(FamilyTree.AlreadyHasPartnerException e){
                        System.out.println("ERROR: ALREADY HAS PARTNER");
                    }
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

                case 6:
                    // We take in an ID from the user and if it's in our tree, we return the information associated with the ID
                    inputId = Input.getInteger("enter the family member's id: ");
                    try {
                        System.out.println(familyTree.getFamilyMember(inputId));
                    // otherwise, we throw and exception
                    } catch(FamilyTree.IdentifierNotFoundException e){
                        System.out.println("ERROR: IDENTIFIER NOT FOUND");
                    }
                    break;

                default:
                    // Anything except the number 0-5 results in an invalid command
                    System.out.println("Invalid Command!");
                    break;

            }
        } while (option != 0);
    }
}
