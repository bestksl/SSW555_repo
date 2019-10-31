package Sprint3;

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
public class Sprint3Test {
    private Finder finder = new Finder("src/main/java/ssw555_refectory/Sprint3_test.ged");
    private Map<String, Individual> individuals = finder.getIndividuals();
    private Map<String, Family> families = finder.getFamilies();
    private Checker checker = new Checker(individuals, families);

    //Zihan Li
    @Test
    public void testUS02() throws Exception {
        assertEquals("ERROR: INDIVIDUAL: US02: NAME:Si Li ID: I10 birth should occur before marriage", checker.US02_Birthbeforemarriage(individuals.get("I10")));
        assertNull(checker.US02_Birthbeforemarriage(individuals.get("I4")));
    }

    //Zihan Li
    @Test
    public void testUS30() throws Exception {
        assertEquals("LIST: INDIVIDUAL: US30: NAME:WU Wang ID: I11 is alive and married", checker.US30_Listlivingmarried(individuals.get("I11")));
        assertNull(checker.US30_Listlivingmarried(individuals.get("I7")));
    }

    // Haoxuan Li
    @Test
    public void testUS06() throws Exception {
        List<String> l = new ArrayList<>();
        l.add("ERROR: FAMILY: US06: F1 Divorced after wife death");
        assertEquals(l, checker.US06_DivorceBeforeDeath(families.get("F1")));
        assertNull(checker.US06_DivorceBeforeDeath(families.get("F3")));
    }


    // Haoxuan Li
    @Test
    public void testUS01() throws Exception {
        List<String> l = new ArrayList<>();
        l.add("ERROR: INDIVIDUAL: US01: I1 9-MAR-2997: Birth Dates after CurrentDate");
        assertEquals(l, checker.US01_DatesBeforeCurrentDate(families.get("F1")));
        assertNull(checker.US01_DatesBeforeCurrentDate(families.get("F2")));
    }
}
