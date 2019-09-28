package ssw555_refectory;

import ssw555_refectory.core.Finder;
import ssw555_refectory.utils.CheckAll;

/**
 * @author SSW555 group  Github:https://github.com/bestksl/SSW555_repo
 * @version created date：2019-09-23 21:15
 */
public class main {


    public static void main(String[] args) throws Exception {
        Finder finder = new Finder("src/main/java/ssw555_refectory/my.ged");
        CheckAll checker = new CheckAll(finder.getIndividuals(), finder.getFamilies());
        if (checker.check()) {
            finder.printFamilies();
            finder.printIndividuals();
        }
    }

}
