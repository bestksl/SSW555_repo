package sprint2;

import org.junit.Test;
import ssw555_refectory.bean.Family;
import ssw555_refectory.bean.Individual;
import ssw555_refectory.core.Finder;
import ssw555_refectory.utils.Checker;
import ssw555_refectory.utils.TimeUtils;

import java.util.Map;

import static org.junit.Assert.*;


/**
 * @author HaoxuanLi  Github:bestksl
 * @version created date：2019-09-28 18:49
 */
public class Sprint2Test {
    private Finder finder = new Finder("src/main/java/ssw555_refectory/Sprint2_test.ged");
    private Map<String, Individual> individuals = finder.getIndividuals();
    private Map<String, Family> families = finder.getFamilies();
    private Checker checker = new Checker(individuals, families);

    @Test
    public void testUS23() throws Exception {
        assertEquals("ERROR: INDIVIDUAL: US23: name and birth Jeff Liu 9-MAR-1997 appear in I1 I10 ||",
                checker.US23_UniqueNameAndBirthdate());
    }

    @Test
    public void testUS27() throws Exception {
        assertNull(checker.US27_IncludeIndividualAges(individuals.get("I7")));
        //hard code set the age to empty
        individuals.get("I11").setAge("");
        assertEquals("ERROR: INDIVIDUAL: US27: Jack Liu with ID: I11 has no age",
                checker.US27_IncludeIndividualAges(individuals.get("I11")));
    }

    @Test
    public void testUS31() throws Exception {
        assertEquals("LIST: INDIVIDUAL: US31: NAME:Rui Liu ID:I6 is over 30 and has never been married", checker.US31_Listlivingsingle(individuals.get("I6")));
        assertNull(checker.US31_Listlivingsingle(individuals.get("I7")));
    }

    @Test
    public void testUS35() throws Exception {
        assertEquals("LIST: INDIVIDUAL: US35: NAME:Han Li ID:I4 was born in the last 30 days", checker.US35_Listrecentbirths((individuals.get("I4"))));
        assertNull(checker.US35_Listrecentbirths(individuals.get("I6")));
    }

    @Test
    public void testListrecentdeath_US36() throws Exception {
        assertEquals("LIST: INDIVIDUAL: US36: NAME:Han Li ID:I4 has dead in 30 days", Checker.US36_recentdeath(individuals.get("I4")));
        assertNull(checker.US36_recentdeath(individuals.get("I3")));
    }

    @Test
    public void testListlargeagedifferences_US34() throws Exception {
        assertEquals("LIST: FAMILY: US34: The couple in family: F3 has large age different", Checker.US34_Listlargeagedifferences(families.get("F3")));
        assertEquals("LIST: FAMILY: US34: The couple in family: F2 has large age different", Checker.US34_Listlargeagedifferences(families.get("F2")));
        //assertNull(checker.checkBirthBeforeDeath(individuals.get("F1")));
    }

    @Test
    public void testUS09() throws Exception {
        assertEquals("ERROR: FAMILY: US09: F2 child: I2 birth: 14-NOV-2016 after parents death date wife I7 14-OCT-2010", checker.US09_BirthBeforeDeathOfParents((families.get("F2"))));
        assertNull(checker.US09_BirthBeforeDeathOfParents(families.get("F1")));
    }

    @Test
    public void testUS10() throws Exception {
        assertEquals("ERROR: FAMILY: US10: F1: wife Marriage before 14, birthday: 14-APR-1990 Marriage date: 5-MAR-1992", checker.US10_MarriageAfter14((families.get("F1"))));
        assertNull(checker.US10_MarriageAfter14(families.get("F2")));
    }

}
