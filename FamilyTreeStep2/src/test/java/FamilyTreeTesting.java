import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class FamilyTreeTesting {

    @Test
    public void shouldThrowAlreadyHasPartnerException(){
        FamilyTree familyTree = new FamilyTree("Ancestor", 1);
        try{
            familyTree.addPartner("Partner1", 2);
        } catch (FamilyTree.AlreadyHasPartnerException e) {
            System.out.println("Already Has Partner");
        }
        Assertions.assertThrows(FamilyTree.AlreadyHasPartnerException.class, () -> {
           familyTree.addPartner("Partner2", 3);
        });
    }

    @Test
    public void shouldThrowChildWithoutPartnerException(){
        FamilyTree familyTree = new FamilyTree("Ancestor", 1);
        Assertions.assertThrows(FamilyTree.ChildWithoutPartnerException.class, () -> {
            familyTree.addChild("ChildName", 2);
        });
    }

    @Test
    public void shouldThrowFamilyMemberNotFoundException(){
        FamilyTree familyTree = new FamilyTree("Ancestor", 1);
        Assertions.assertThrows(FamilyTree.FamilyMemberNotFoundException.class, () -> {
           familyTree.findFamilyMember("NameThatsNotInTree");
        });
    }

    @Test
    public void shouldThrowIdentifierNotFoundException(){
        FamilyTree familyTree = new FamilyTree("Ancestor", 1);
        Assertions.assertThrows(FamilyTree.IdentifierNotFoundException.class, () -> {
           familyTree.getFamilyMember(999); // An identifier not in the tree
        });
    }

    @Test
    public void shouldThrowNonUniqueNameException(){
        FamilyTree familyTree = new FamilyTree("Ancestor", 1);
        try{
            familyTree.addPartner("Partner1", 2);
        } catch (FamilyTree.AlreadyHasPartnerException e) {
            System.out.println("Already Has Partner");
        }
        List names = new ArrayList();
        names.add("NonUniqueName");
        Assertions.assertThrows(FamilyTree.NonUniqueNameException.class, () -> {
           if(!names.contains("NonUniqueName")){
               familyTree.addChild("NonUniqueName", 3);
           } else {
               throw new FamilyTree.NonUniqueNameException();
           }
        });
    }

    @Test
    public void  shouldAddChild(){
        FamilyTree familyTree = new FamilyTree("Ancestor", 1);
        try{
            familyTree.addPartner("Partner1", 2);
        } catch (FamilyTree.AlreadyHasPartnerException e) {
            System.out.println("Already Has Partner");
        }
        try{
            familyTree.addChild("Child", 3);
        } catch (FamilyTree.ChildWithoutPartnerException e){
            System.out.println("Child Without Partner");
        }
        Assertions.assertTrue(!familyTree.getCurrent().contains("has no children"));
    }

    @Test
    public void shouldAddPartner(){
        FamilyTree familyTree = new FamilyTree("Ancestor", 1);
        try{
            familyTree.addPartner("Partner", 2);
        } catch (FamilyTree.AlreadyHasPartnerException e) {
            System.out.println("Already Has Partner");
        }
        Assertions.assertTrue(familyTree.getCurrent().contains("has no partner"));
    }
}