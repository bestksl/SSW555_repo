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
