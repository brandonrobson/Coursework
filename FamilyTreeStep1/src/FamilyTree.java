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
    public static class NonUniqueNameException extends Exception {}

    private FamilyTreeNode ancestor;
    private FamilyTreeNode current;

    // The constructor
    public FamilyTree(String ancestorName, String partnerName){
        this.ancestor = new FamilyTreeNode();
        this.ancestor.name = ancestorName;
        this.current = this.ancestor;
        addPartner(partnerName);
    }

    // Adds a child
    public void addChild(String name) {
        FamilyTreeNode familyMember = new FamilyTreeNode();
        familyMember.name = name;
        familyMember.ancestor = this.current;
        // If there are no children add a child
        if(this.current.child == null){
            // Add to current parent
            this.current.child = familyMember;
            // Add to second parent
            if(this.current.partner != null){
                this.current = this.current.partner;
                this.current.child = familyMember;
            }
        // Otherwise, loop through each child until we find a null sibling. Then add a child there.
        } else {
            FamilyTreeNode next = this.current.child;
            while (next.sibling != null) {
                next = next.sibling;
            }
            next.sibling = familyMember;
            }

    }

    // Adds a partner
    public void addPartner(String name) {
        FamilyTreeNode familyMember = new FamilyTreeNode();
        familyMember.name = name;
        familyMember.partner = this.current;
        this.current.partner = familyMember;
    }

    // Formats our information into string form
    public String toString() {
        String familyTreeDetails = new String();
        familyTreeDetails += this.ancestor.name + getPartner(this.ancestor);
        FamilyTreeNode familyMember = this.ancestor.child;
        if (familyMember == null){
            familyTreeDetails += "   has no children\n";
        } else {
            while (familyMember != null) {
                familyTreeDetails += " " + familyMember.name +  getPartner(familyMember);
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
            childDetails += "   has no children\n";
        } else {
        // Otherwise, they have children. Loop through and add each one to our string of details.
            while (familyMember != null){
                childDetails += "   " + familyMember.name + getPartner(familyMember) + "\n";
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
            partnerDetails += " partner " + familyMember.name + "\n";
        }
        return partnerDetails;
    }

    // Obtains and returns the details of the current family member
    public String getCurrent(){
        String currentDetails = new String();
        // Get their partner
        currentDetails += this.current.name + getPartner(this.current);
        if (this.current.ancestor != null) {
            currentDetails += " child of: " + this.current.ancestor.name + " and " + this.current.ancestor.partner.name + "\n";
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
            if (this.ancestor.child == null && this.ancestor.partner == null){
                throw new FamilyMemberNotFoundException();
            }
            familyMember = this.checkPartner(name, this.ancestor);
            if(familyMember == null){
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


        }
        this.current = familyMember;
        getCurrent();
    }

    // Used to check the children for the findFamilyMember method
    private FamilyTreeNode checkChildren(String name, FamilyTreeNode familyMember){
        familyMember = familyMember.child;
        Boolean searching = familyMember != null;
        while (searching) {
            if(familyMember.partner != null && name.equalsIgnoreCase(familyMember.partner.name)){
                familyMember = familyMember.partner;
                searching = false;
            }
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

    // Used to check the partner for the findFamilyMember method
    private FamilyTreeNode checkPartner(String name, FamilyTreeNode familyMember){
        familyMember = familyMember.partner;
        Boolean searching = familyMember != null;
        while (searching) {
            if (name.equalsIgnoreCase(familyMember.name)){
                searching = false;
            } else {
                return null;
            }
        }
        return familyMember;
    }

}