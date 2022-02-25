public class FamilyTree {
    // The structure of each node in the tree
    private class FamilyTreeNode {
        private String name;
        private Integer identifier;
        private FamilyTreeNode ancestor;
        private FamilyTreeNode partner;
        private FamilyTreeNode sibling;
        private FamilyTreeNode child;
    }

    // Initialising our exceptions
    public class FamilyMemberNotFoundException extends Exception{}
    public class IdentifierNotFoundException extends Exception {}
    public class AlreadyHasPartnerException extends Exception {}
    public class ChildWithoutPartnerException extends Exception {}
    public static class NonUniqueNameException extends Exception {}

    private FamilyTreeNode ancestor;
    private FamilyTreeNode current;
    // The constructor
    public FamilyTree(String ancestorName, Integer identifier){
        this.ancestor = new FamilyTreeNode();
        this.ancestor.name = ancestorName;
        this.ancestor.identifier = identifier;
        this.current = this.ancestor;
    }

    // Adds a child
    public void addChild(String name, Integer identifier) throws ChildWithoutPartnerException{
        FamilyTreeNode familyMember = new FamilyTreeNode();
        familyMember.name = name;
        familyMember.identifier = identifier;
        familyMember.ancestor = this.current;
        // As long as the parent has a partner...
        if((this.current.partner) != null){
            // we search for a null child node and add a child there
            if(this.current.child == null){
                this.current.child = familyMember;
            } else {
            // each non null child node is looped through until we find a space
                FamilyTreeNode next = this.current.child;
                while (next.sibling != null) {
                    next = next.sibling;
                }
                next.sibling = familyMember;
            }
        // but if they don't have a partner, they can't have a child. So we throw an exception
        } else {
            throw new ChildWithoutPartnerException();
        }

    }

    // Adds a partner
    public void addPartner(String name, Integer identifier) throws AlreadyHasPartnerException{
        FamilyTreeNode familyMember = new FamilyTreeNode();
        familyMember.name = name;
        familyMember.identifier = identifier;
        familyMember.partner = this.current;
        // We only add a partner if the person already doesn't have a partner
        if((this.current.partner) == null){
            this.current.partner = familyMember;
        } else {
        // If they do have a partner, we have to throw an exception
            throw new AlreadyHasPartnerException();
        }
    }

    // Formats our information into string form
    public String toString() {
        String familyTreeDetails = new String();
        familyTreeDetails += this.ancestor.name + "(identifier " + this.ancestor.identifier.toString() + ")" + getPartner(this.ancestor);
        FamilyTreeNode familyMember = this.ancestor.child;
        if (familyMember == null){
            familyTreeDetails += " has no children\n";
        } else {
            while (familyMember != null) {
                familyTreeDetails += " " + familyMember.name + "(identifier " + familyMember.identifier.toString() + ")" +  getPartner(familyMember);
                familyTreeDetails += this.getChildren(familyMember);
                familyMember = familyMember.sibling;
            }
        }
        return familyTreeDetails;
    }

    // Obtains any given family member's children in string form
    private String getChildren(FamilyTreeNode familyMember){
        String childDetails = new String();
        familyMember = familyMember.child;
        // If the children part of the node is null, there are no children.
        if (familyMember == null){
            childDetails += " has no children\n";
        } else {
        // Otherwise, they have children. Loop through and add each one to our string of details.
            while (familyMember != null){
                childDetails += "   " + familyMember.name + "(identifier " + familyMember.identifier.toString() + ")" + getPartner(familyMember) + "\n";
                familyMember = familyMember.sibling;
            }
        }
        return childDetails;
    }

    // Identical to the getChildren method except we don't need to loop as we either have a partner, or we don't
    public static String getPartner(FamilyTreeNode familyMember){
        String partnerDetails = new String();
        familyMember = familyMember.partner;
        if(familyMember == null){
            partnerDetails += " has no partner\n";
        } else {
            partnerDetails += " partner " + familyMember.name + "(identifier " + familyMember.identifier.toString() + ")" + "\n";
        }
        return partnerDetails;
    }

    // Obtains and returns the details of the current family member
    public String getCurrent(){
        String currentDetails = new String();
        // Get their partner
        currentDetails += this.current.name + "(identifier " + this.current.identifier.toString() + ")" + getPartner(this.current);
        if (this.current.ancestor != null){
            currentDetails += " child of: " + this.current.ancestor.name + "\n";
        } else {
            currentDetails += " is the ancestor\n";
        }
        // Get their children
        currentDetails += this.getChildren(this.current);
        return currentDetails;
    }

    // Locates a family member using their name
    public void findFamilyMember(String name) throws FamilyMemberNotFoundException{
        FamilyTreeNode familyMember;
        FamilyTreeNode child;
        // If the name is the root, send the root
        if (name.equalsIgnoreCase(this.ancestor.name)){
            familyMember = this.ancestor;
        } else {
            // Otherwise, search through every node of the tree and compare the names until either
            // we find it or report that the name isn't in the tree
            if (this.ancestor.child == null){
                throw new FamilyMemberNotFoundException();
            }
            familyMember = this.checkChildren(name, this.ancestor);
            if (familyMember == null){
                child = this.ancestor.child;
                while (familyMember == null){
                    familyMember = this.checkChildren(name, child);
                    if (familyMember == null){
                        child = child.sibling;
                        if(child == null){
                            throw new FamilyMemberNotFoundException();
                        }
                    }
                }
            }
        }
        this.current = familyMember;
        getCurrent();
    }

    // Does the same as findFamilyMember, but uses ID instead of a name
    public String getFamilyMember(Integer inputId) throws IdentifierNotFoundException{
        String familyMemberDetails = new String();
        FamilyTreeNode familyMember;
        FamilyTreeNode child;
        if (inputId == this.ancestor.identifier){
            familyMember = this.ancestor;
        } else {
            if (this.ancestor.child == null){
                throw new IdentifierNotFoundException();
            }
            familyMember = this.checkChildrenID(inputId, this.ancestor);
            if (familyMember == null){
                child = this.ancestor.child;
                while (familyMember == null){
                    familyMember = this.checkChildrenID(inputId, child);
                    if (familyMember == null){
                        child = child.sibling;
                        if(child == null){
                            throw new IdentifierNotFoundException();
                        }
                    }
                }
            }
        }
        this.current = familyMember;
        familyMemberDetails = getCurrent();
        // We also display the details of the family member
        return familyMemberDetails;
    }

    // Used to check the children for the findFamilyMember method
    private FamilyTreeNode checkChildren(String name, FamilyTreeNode familyMember){
        familyMember = familyMember.child;
        Boolean searching = familyMember != null;
        while (searching) {
            if (name.equalsIgnoreCase(familyMember.name)){
                searching = false;
            } else {
                familyMember = familyMember.sibling;
                if(familyMember == null) {
                    searching = false;
                }
            }
        }
        return familyMember;
    }

    // Used to check the children for the getFamilyMember method (uses the ID instead of a name)
    private FamilyTreeNode checkChildrenID(Integer inputID, FamilyTreeNode familyMember){
        familyMember = familyMember.child;
        Boolean searching = familyMember != null;
        while (searching) {
            if (inputID == familyMember.identifier){
                searching = false;
            } else {
                familyMember = familyMember.sibling;
                if(familyMember == null) {
                    searching = false;
                }
            }
        }
        return familyMember;
    }

}