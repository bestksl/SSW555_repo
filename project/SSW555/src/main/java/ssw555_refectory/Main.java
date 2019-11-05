package ssw555_refectory;

import ssw555_refectory.bean.Individual;
import ssw555_refectory.core.Finder;
import ssw555_refectory.utils.Checker;

/**
 * @author SSW555 group  Github:https://github.com/bestksl/SSW555_repo
 * @version created date：2019-09-23 21:15
 */
public class Main {


    public static void main(String[] args) throws Exception {
        Finder finder = new Finder("src/main/java/ssw555_refectory/Sprint3_test.ged");//src/main/java/ssw555_refectory/my.ged
        Checker checker = new Checker(finder.getIndividuals(), finder.getFamilies());
        checker.checkAll(3);
        finder.printFamilies();
        finder.printIndividuals();
        Checker.printErr();
        }


}
