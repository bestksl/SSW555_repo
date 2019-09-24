package ssw555_refectory;

import ssw555_refectory.utils.Finder;

/**
 * @author HaoxuanLi  Github:bestksl
 * @version created date：2019-09-23 21:15
 */
public class main {


    public static void main(String[] args) throws Exception {
        Finder finder = new Finder("src/main/java/ssw555_refectory/my.ged");
        finder.printFamilies();
        finder.printIndividuals();
    }

}
