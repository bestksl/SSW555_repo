package sprint1;

import org.junit.Test;
import ssw555_refectory.bean.Family;
import ssw555_refectory.bean.Individual;
import ssw555_refectory.core.Finder;
import ssw555_refectory.utils.Checker;

import java.util.Map;

import static org.junit.Assert.*;


/**
 * @author HaoxuanLi  Github:bestksl
 * @version created date：2019-09-28 18:49
 */
public class Sprint1Test {
    private Finder finder = new Finder("src/main/java/ssw555_refectory/wrong.ged");
    private Map<String, Individual> individuals = finder.getIndividuals();
    private Map<String, Family> families = finder.getFamilies();
    private Checker checker = new Checker(individuals, families);

    @Test
    public void testCheckBirthBeforeDeath() throws Exception {
        assertEquals("I7:  birth date should earlier than death dates", checker.checkBirthBeforeDeath(individuals.get("I7")));
        assertNull(checker.checkBirthBeforeDeath(individuals.get("I6")));
    }

    @Test
    public void testCheckMarrige() throws Exception {
        assertEquals("F1:  Divorce before Marriage", checker.checkMarrige(families.get("F1")));
        assertNull(checker.checkMarrige(families.get("F2")));
        assertNull(checker.checkMarrige(families.get("F3")));
    }

    @Test
    // Jeff User story Test: Marriage Date
    public void TestBirthAfterParentsMarriges() throws Exception {

        assertEquals("F2:  Duan Xiao is born before Family F2", checker.birthAfterParentsMarriges(families.get("F2")).get(0));
        assertNull(checker.birthAfterParentsMarriges(families.get("F1")));
        assertNull(checker.birthAfterParentsMarriges(families.get("F3")));
    }
    public void TestUniqueID() throws Exception {
        assertEquals("I2:  individual ID should be unique", checker.uniqueIdINDI(families.get("I2")));
        assertEquals("F3:  family ID should be unique", checker.uniqueIdFAM(families.get("F3")));
        assertNull(checker.uniqueIdINDI(families.get("I1")));
        assertNull(checker.uniqueIdINDI(families.get("I3")));
        assertNull(checker.uniqueIdINDI(families.get("I4")));
        assertNull(checker.uniqueIdINDI(families.get("I5")));
        assertNull(checker.uniqueIdINDI(families.get("I6")));
        assertNull(checker.uniqueIdFAM(families.get("F1")));
        assertNull(checker.uniqueIdFAM(families.get("F2")));
    }
    
    public void TestMaleLastname() throws Exception {
        assertEquals("F1:  The last name for Male person in a family is not the same!", checker.familyMaleLastName(families.get("F1")));
        assertEquals("F3:  The last name for Male person in a family is not the same!", checker.familyMaleLastName(families.get("F3")));
        assertNull(checker.familyMaleLastName(families.get("F2")));
    }
}
