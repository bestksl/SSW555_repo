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
    // Jeff
    public void testUS23() throws Exception {

    }

    @Test
    //Jeff
    public void testUS27() throws Exception {

    }

    @Test
    public void testTimeUtil() throws Exception {
        System.out.println(TimeUtils.getDaysFromDate("19-OCT-2000"));
    }
    @Test
    public void testListrecentdeath_US36() throws Exception {
        assertEquals("ERROR: INDIVIDUAL: US36: Jie Deng ID:I7 has dead in 30 days", checker.US_36Listrecentdeath(individuals.get("I7")));
        assertNull(checker.US_36Listrecentdeath(individuals.get("I3")));
    }
    @Test
    public void testListlargeagedifferences_US34() throws Exception {
        assertEquals("ERROR: FAMILY: US34: The couple in family: F3 has large age different", checker.US_34Listlargeagedifferences(families.get("F3")));
        assertEquals("ERROR: FAMILY: US34: The couple in family: F2 has large age different", checker.US_34Listlargeagedifferences(families.get("F2")));
        //assertNull(checker.checkBirthBeforeDeath(individuals.get("F1")));
    }

}
