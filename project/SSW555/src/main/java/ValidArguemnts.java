import java.util.HashSet;
import java.util.Set;

/**
 * @author HaoxuanLi  Github:bestksl
 * @version created date：2019-09-17 19:30
 */
class ValidArguemnts {
    enum ValidArguemnt {
        INDI, NAME, SEX, BIRT, DEAT, FAMC, FAMS, FAM, MARR, HUSB, WIFE, CHIL, DIV, DATE, HEAD, TRLR, NOTE
    }

    static boolean contains(String str) {
        for (ValidArguemnt value : ValidArguemnt.values()) {
            if (value.toString().equals(str)) {
                return true;
            }
        }
        return false;
    }

}

