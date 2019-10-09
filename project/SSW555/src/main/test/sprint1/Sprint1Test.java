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
        assertEquals("ERROR: INDIVIDUAL: US03: I7 Jie Deng's birth date 5-MAR-2006 occurs after death dates 14-JUL-1985", checker.checkBirthBeforeDeath(individuals.get("I7")));
        assertNull(checker.checkBirthBeforeDeath(individuals.get("I6")));
    }

    @Test
    public void testCheckMarrige() throws Exception {
        assertEquals("ERROR: FAMILY: US04: F1 Divorce:5-MAR-1990 before Marriage 5-MAR-1992", checker.checkMarrige(families.get("F1")));
        assertNull(checker.checkMarrige(families.get("F2")));
        assertNull(checker.checkMarrige(families.get("F3")));
    }

    @Test
    public void testUniqueFirstName(){
        assertEquals("ERROR: FAMILY: US03: F1:  family member's first name should be unique!",checker.uniqueFirstname(families.get("F1")));
        assertNull(checker.uniqueFirstname(families.get("F2")));
        assertNull(checker.uniqueFirstname(families.get("F3")));
    }

    @Test
    // Jeff User story Test: Marriage Date
    public void TestBirthAfterParentsMarriges() throws Exception {
        assertEquals("ERROR: FAMILY: US08: Child Duan Xiao born 14-APR-1938 before Marriage on 6-MAY-2016 || ", checker.birthAfterParentsMarriges(families.get("F2")));
        assertNull(checker.birthAfterParentsMarriges(families.get("F1")));
        assertNull(checker.birthAfterParentsMarriges(families.get("F3")));
    }

    //Shiwei Ding
    @Test
    public void TestUniqueID() throws Exception {
        assertEquals("ERROR: INDIVIDUAL: US22: I2  individual ID is not unique", checker.uniqueIdINDI(individuals.get(".I2")));
        assertEquals("ERROR: FAMILY: US22: F3  family ID is not unique", checker.uniqueIdFAM(families.get(".F3")));
        assertNull(checker.uniqueIdINDI(individuals.get("I3")));
        assertNull(checker.uniqueIdINDI(individuals.get("I4")));
        assertNull(checker.uniqueIdINDI(individuals.get("I5")));
        assertNull(checker.uniqueIdINDI(individuals.get("I6")));
        assertNull(checker.uniqueIdFAM(families.get("F1")));
        assertNull(checker.uniqueIdFAM(families.get("F2")));
    }

    // Shiwei Ding
    @Test
    public void TestMaleLastname() throws Exception {
        assertEquals("ERROR: FAMILY: US16: F1  The last name for Male person in a family I2 and I4 is not the same!", checker.familyMaleLastName(families.get("F1")));
        assertEquals("ERROR: FAMILY: US16: F3  The last name for Male person in a family I2 and I4 is not the same!", checker.familyMaleLastName(families.get("F3")));
        assertNull(checker.familyMaleLastName(families.get("F2")));
    }


    @Test
    public void ageOld()throws Exception{
        assertEquals("ERROR: INDIVIDUAL: US07: I6:  Rui Liu should less than 150 years old",checker.ageOld(individuals.get("I6")));
        assertNull(checker.ageOld(individuals.get("I7")));
        // assertNull(checker.uniqueFirstname(individuals.get("F3")));
    }

    @Test
    public void parentsNotTooOld()throws Exception{
        assertEquals("ERROR: FAMILY: US12: F1:  Mother should be less than 60 years older than her children and father should be less than 80 years older than his children",checker.parentsNotTooOld(families.get("F1")));
        assertEquals("ERROR: FAMILY: US12: F2:  Mother should be less than 60 years older than her children and father should be less than 80 years older than his children",checker.parentsNotTooOld(families.get("F2")));
        assertNull(checker.parentsNotTooOld(families.get("F3")));
    }

}
