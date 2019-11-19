package Sprint4;

import org.junit.Test;
import ssw555_refectory.bean.Family;
import ssw555_refectory.bean.Individual;
import ssw555_refectory.core.Finder;
import ssw555_refectory.utils.Checker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author HaoxuanLi  Github:bestksl
 * @version created date：2019-10-21 22:09
 */
public class Sprint4Test {
    private Finder finder = new Finder("src/main/java/ssw555_refectory/Sprint4_test.ged");
    private Map<String, Individual> individuals = finder.getIndividuals();
    private Map<String, Family> families = finder.getFamilies();
    private Checker checker = new Checker(individuals, families);

    @Test
    public void testUS17() throws Exception {
        assertEquals("ERROR: FAMILY: US_17: In family F2 GrandFather I6 married her grandchildren I8 in family F3", checker.US17_Nomarriagetodecendent(families.get("F2")));
        assertNull(checker.US17_Nomarriagetodecendent(families.get("F1")));
    }
    @Test
    public void testUS18() throws Exception {
        assertEquals("ERROR: FAMILY: US_18: In family F3 the sibiling I4 and I7 cannot marry!", checker.US18_Sibilingsshouldnotmarried(families.get("F3")));
        assertNull(checker.US18_Sibilingsshouldnotmarried(families.get("F1")));
    }

    // Jeff
    @Test
    public void testUS14() throws Exception {
        assertEquals("ERROR: FAMILY: US14: FAMILY: F1 ID: have more than five siblings with same date of bith", checker.US14_MutipleBirth(families.get("F1")));
        assertNull(checker.US14_MutipleBirth(families.get("F3")));
    }
    // Jeff
    @Test
    public void testUS15() throws Exception {
        assertEquals("ERROR: FAMILY: US15: FAMILY: F1 ID: have more than 15 siblings", checker.US15_FeverThan15(families.get("F1")));
        assertNull(checker.US15_FeverThan15(families.get("F3")));
    }

}
