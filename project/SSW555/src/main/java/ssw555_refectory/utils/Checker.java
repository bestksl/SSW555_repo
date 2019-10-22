package ssw555_refectory.utils;

import ssw555_refectory.bean.Family;
import ssw555_refectory.bean.Individual;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.*;

public class Checker {
    private Map<String, Individual> individuals;
    private Map<String, Family> families;
    private static List<String> errList = new ArrayList<>();

    public Checker(Map<String, Individual> individuals, Map<String, Family> familys) {
        this.individuals = individuals;
        this.families = familys;
    }


    public static void addErr(String errMsg) {
        errList.add(errMsg);
    }

    public boolean checkAll() throws Exception {
        checkSprint1();
        checkSprint2();
        return errList.size() == 0;
    }

    public boolean checkSprint1() throws Exception {
        //如果有不通过的项会将err信息加入到errList

        // whole Individuals map test
        UniqueNameAndBirthdate();


        for (Individual i : individuals.values()) {
            uniqueIdINDI(i);
            US03_checkBirthBeforeDeath(i);
            ageOld(i);
            US_36Listrecentdeath(i);
        }

        for (Family f : families.values()) {
            uniqueIdFAM(f);
            US25_uniqueFirstname(f);
            checkMarrige(f);
            birthAfterParentsMarriges(f);
            parentsNotTooOld(f);
            familyMaleLastName(f);
            US_34Listlargeagedifferences(f);
        }


        return errList.size() == 0;
    }

    public boolean checkSprint2() throws Exception {
        //如果有不通过的项会将err信息加入到errList

        // whole Individuals map test
        for (Individual i : individuals.values()) {

        }

        for (Family f : families.values()) {
            US09_BirthBeforeDeathOfParents(f);
            US10_MarriageAfter14(f);

        }


        return errList.size() == 0;
    }

    // Haoxuan Li
    public String US03_checkBirthBeforeDeath(Individual i) throws Exception {
        if (i.getBirt() != null && i.getDeath() != null) {
            if (!(TimeUtils.getAge(i.getBirt()) - TimeUtils.getAge(i.getDeath()) >= 0)) {
                errList.add("ERROR: INDIVIDUAL: US03: " + i.getId() + " " + i.getName() +
                        "'s birth date " + i.getBirt() + " occurs after death dates " + i.getDeath());
                return "ERROR: INDIVIDUAL: US03: " + i.getId() + " " + i.getName() +
                        "'s birth date " + i.getBirt() + " occurs after death dates " + i.getDeath();
            }
        }
        return null;
    }

    // Haoxuan Li
    public String US25_uniqueFirstname(Family f) {
        Set<String> nameSet = new HashSet<>();
        List<String> idList = new ArrayList<>();

        String husId = f.getHusbandID();
        if (husId != null) {
            idList.add(husId);
        }
        String wifeId = f.getWifeID();
        if (wifeId != null) {
            idList.add(wifeId);
        }
        if (f.getChildren().size() != 0) {
            idList.addAll(f.getChildren());
        }
        for (String id : idList) {
            if (individuals.get(id).getName() != null) {
                nameSet.add(individuals.get(id).getFirstName());
            }
        }
        if (!(nameSet.size() == idList.size())) {
            errList.add("ERROR: FAMILY: US03: " + f.getId() + ":  family member's first name should be unique!");
            return "ERROR: FAMILY: US03: " + f.getId() + ":  family member's first name should be unique!";
        }
        return null;
    }

    // Haoxuan Li
    public String US09_BirthBeforeDeathOfParents(Family f) throws Exception {

        Individual husband = individuals.get(f.getHusbandID());
        Individual wife = individuals.get(f.getWifeID());

        if (husband.getDeath() == null && wife.getDeath() == null) {
            return null;
        }
        for (String childId : f.getChildren()) {
            Individual child = individuals.get(childId);
            if (child == null || child.getBirt() == null) {
                return null;
            }
            int years = 0;
            String result = "";
            if (husband.getDeath() != null) {
                years = TimeUtils.getAge(husband.getDeath());
                result = "husband " + husband.getId() + " " + husband.getDeath();
            }
            if (wife.getDeath() != null) {
                if (years == 0) {
                    years = TimeUtils.getAge(child.getBirt()) - TimeUtils.getAge(wife.getDeath()) < 0 ? TimeUtils.getAge(wife.getDeath()) : 0;
                    result = "wife " + wife.getId() + " " + wife.getDeath();
                } else {
                    years = (TimeUtils.getAge(wife.getDeath()) - years) > 0 ? TimeUtils.getAge(wife.getDeath()) : years;
                    result = (TimeUtils.getAge(wife.getDeath()) - years) < 0 ? result = "husband " + husband.getId() + husband.getDeath() : "wife" + wife.getId() + "  " + wife.getDeath();
                }
            }
            if (years != 0) {
                errList.add("ERROR: FAMILY: US09: child: " + child.getId() + " birth: " + child.getBirt() + " after parents death date " + result);
                return "ERROR: FAMILY: US09: child: " + child.getId() + " birth: " + child.getBirt() + " after parents death date " + result;
            }
        }

        return null;
    }

    // Haoxuan Li
    public String US10_MarriageAfter14(Family f) throws Exception {
        Individual husband = individuals.get(f.getHusbandID());
        Individual wife = individuals.get(f.getWifeID());

        if (f.getMarried() == null) {
            return null;
        } else {
            if (husband.getBirt() != null) {
                if (TimeUtils.getAge(husband.getBirt()) - TimeUtils.getAge(f.getMarried()) > 0 && TimeUtils.getAge(husband.getBirt()) - TimeUtils.getAge(f.getMarried()) < 14) {
                    errList.add("ERROR: FAMILY: US10: " + f.getId() + ": husband Marriage before 14, birthday: " + husband.getBirt() + " Marriage date: " +
                            f.getMarried());
                    return "ERROR: FAMILY: US10: " + f.getId() + ": husband Marriage before 14, birthday: " + husband.getBirt() + " Marriage date: " +
                            f.getMarried();
                }
            }
            if (wife.getBirt() != null) {
                if (TimeUtils.getAge(wife.getBirt()) - TimeUtils.getAge(f.getMarried()) < 14) {
                    errList.add("ERROR: FAMILY: US10: " + f.getId() + ": wife Marriage before 14, birthday: " + wife.getBirt() + " Marriage date: "
                            + f.getMarried());
                    return "ERROR: FAMILY: US10: " + f.getId() + ": wife Marriage before 14, birthday: " + wife.getBirt() + " Marriage date: "
                            + f.getMarried();
                }
            }
        }

        return null;
    }

    // Jeff
    public String checkMarrige(Family f) throws Exception {
        if (f.getMarried() == null || f.getDivorced() == null) {
            return null;
        }
        int DivorceData = TimeUtils.getAge(f.getDivorced());
        int MarriageData = TimeUtils.getAge(f.getMarried());

        if (DivorceData > MarriageData) {
            errList.add("ERROR: FAMILY: US04: " + f.getId() + " Divorce:" + f.getDivorced() + " before Marriage " + f.getMarried());
            return "ERROR: FAMILY: US04: " + f.getId() + " Divorce:" + f.getDivorced() + " before Marriage " + f.getMarried();
        }
        return null;
    }

    // Jeff
    public String birthAfterParentsMarriges(Family f) throws Exception {
        ArrayList<String> children = f.getChildren();
        if (f.getMarried() == null || f.getChildren().size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int marriagePeriod = TimeUtils.getAge(f.getMarried());
        for (String child : children) {
            Individual eachChild = individuals.get(child);
            int age = TimeUtils.getAge(eachChild.getBirt());
            if (age >= marriagePeriod) {
                errList.add("ERROR: FAMILY: US08: Child " + eachChild.getName() + " born " + eachChild.getBirt() + " before Marriage on " + f.getMarried());
                sb.append("ERROR: FAMILY: US08: Child " + eachChild.getName() + " born " + eachChild.getBirt() + " before Marriage on " + f.getMarried() + " || ");
            }
        }
        if (sb.length() == 0) return null;
        String res = sb.toString();
        sb.setLength(0);
        return res;

    }

    // Jeff
    public String UniqueNameAndBirthdate() {
        Map<String, List<Individual>> map = new HashMap<>();
        StringBuilder sbs = new StringBuilder();
        for (Map.Entry<String, Individual> entry : individuals.entrySet()) {
            Individual each = entry.getValue();
            String nameAndBirth = entry.getValue().getName() + "" + entry.getValue().getBirt();
            List<Individual> cur = map.get(nameAndBirth);
            if (map.containsKey(nameAndBirth)) {
                cur.add(each);
                map.put(nameAndBirth, cur);
            } else {
                cur = new ArrayList<Individual>();
                cur.add(each);
                map.put(nameAndBirth, cur);
            }
        }
        for (Map.Entry<String, List<Individual>> entry : map.entrySet()) {
            if (entry.getValue().size() > 1) {

                StringBuilder sb = new StringBuilder();
                for (Individual each : entry.getValue()) {
                    sb.append(each.getId() + " ");
                }
                errList.add("ERROR: INDIVIDUAL: US23: name and birth " + entry.getKey() + "appear in " + sb.toString());
                sbs.append("ERROR: INDIVIDUAL: US23: name and birth " + entry.getKey() + "appear in " + sb.toString() + "||");
            }
        }

        return sbs.toString();
    }

    public static void printErr() {
        for (String err : errList) {
            System.out.println(err);
        }
    }


    public String familyMaleLastName(Family f) throws Exception {
        HashMap<String, String> hash_setINDI = new HashMap<>();
        HashMap<String, String> hash_setSEX = new HashMap<>();
        String Fname;
        ArrayList<String> Sname = new ArrayList<>();
        Fname = f.getHusbandID();
        Sname = f.getChildren();
        String fatherLastname = null, childLastname = null, childgender = null;
        for (Individual individuals : individuals.values()) {
            if (individuals.getId().equals(Fname)) {
                fatherLastname = individuals.getLastName();
            }
        }
        for (Individual individuals : individuals.values()) {
            for (int j = 0; j < Sname.size(); j++) {
                if (individuals.getId().equals(Sname.get(j))) {
                    childLastname = individuals.getLastName();
                    childgender = individuals.getGender();
                    if (childgender.equals("M") && !childLastname.equals(fatherLastname)) {
                        String str = f.getId().replace(".", "");
                        errList.add("ERROR: FAMILY: US16: " + str + "  The last name for Male person in a family " + Fname + " and " + individuals.getId() + " is not the same!");
                        return "ERROR: FAMILY: US16: " + str + "  The last name for Male person in a family " + Fname + " and " + individuals.getId() + " is not the same!";
                    }
                }
            }
        }
        return null;
    }

    public String uniqueIdINDI(Individual i) throws Exception {
        if (i.getId().startsWith(".")) {
            String str = i.getId().replace(".", "");
            errList.add("ERROR: INDIVIDUAL: US22: " + str + "  individual ID is not unique");
            return "ERROR: INDIVIDUAL: US22: " + str + "  individual ID is not unique";
        }
        return null;
    }

    public String uniqueIdFAM(Family f) throws Exception {
        if (f.getId().startsWith(".")) {
            String str = f.getId().replace(".", "");
            errList.add("ERROR: FAMILY: US22 " + str + "  family ID is not unique");
            return "ERROR: FAMILY: US22: " + str + "  family ID is not unique";
        }
        return null;
    }

    //Zihan Li
    public String ageOld(Individual i) throws Exception {
        if (i.getBirt() != null) {
            if ((i.getDeath() == null ? TimeUtils.getAge(i.getBirt()) : TimeUtils.getAge(i.getBirt()) - TimeUtils.getAge(i.getDeath())) >= 150) {
                errList.add("ERROR: INDIVIDUAL: US07: " + i.getId() + ":  " + i.getName() + " should less than 150 years old");
                return "ERROR: INDIVIDUAL: US07: " + i.getId() + ":  " + i.getName() + " should less than 150 years old";
            }
            return null;
        }
        return null;
    }

    //Zihan Li
    public String parentsNotTooOld(Family f) throws Exception {
        ArrayList<String> children = f.getChildren();
        String father = f.getHusbandID();
        String mother = f.getWifeID();
        Individual dad = individuals.get(father);
        Individual mom = individuals.get(mother);
        for (String child : children) {
            Individual eachchildren = individuals.get(child);
            if (eachchildren.getBirt() != null) {
                if ((TimeUtils.getAge(dad.getBirt()) - TimeUtils.getAge(eachchildren.getBirt()) >= 80) || (TimeUtils.getAge(mom.getBirt()) - TimeUtils.getAge(eachchildren.getBirt()) >= 60)) {
                    errList.add("ERROR: FAMILY: US12: " + f.getId() + ":  Mother should be less than 60 years older than her children and father should be less than 80 years older than his children");
                    return "ERROR: FAMILY: US12: " + f.getId() + ":  Mother should be less than 60 years older than her children and father should be less than 80 years older than his children";
                }

            }

        }
        return null;
    }

    public String US_36Listrecentdeath(Individual i) throws Exception {
        String deathlist = "";
        long days = 0;
        if (i.getDeath() != null) {
            String deathdate = i.getDeath();
            days = TimeUtils.getDaysFromDate(deathdate);
        }
        System.out.println(days);
        if (days <= 30 && days != 0) {
            deathlist = "ERROR: INDIVIDUAL: US36: " + i.getName() + " ID:" + i.getId() + " has dead in 30 days";
            errList.add("ERROR: INDIVIDUAL: US36: " + i.getName() + " ID:" + i.getId() + " has dead in 30 days");
            return deathlist;
        }
        return null;
    }

    public String US_34Listlargeagedifferences(Family f) throws Exception {
        String ret = "";
        if (f.getDivorced() == null && f.getHusbandID() != null && f.getWifeID() != null) {
            int Husbandage = 0, Wifeage = 0;
            for (Individual individuals : individuals.values()) {
                if (individuals.getId().equals(f.getHusbandID())) {
                    Husbandage = individuals.getDeath() == null ? TimeUtils.getAge(individuals.getBirt()) : TimeUtils.getAge(individuals.getDeath()) - TimeUtils.getAge(individuals.getBirt());
                }
                if (individuals.getId().equals(f.getWifeID())) {
                    Wifeage = individuals.getDeath() == null ? TimeUtils.getAge(individuals.getBirt()) : TimeUtils.getAge(individuals.getDeath()) - TimeUtils.getAge(individuals.getBirt());
                }
            }
            if (Wifeage * 2 <= Husbandage || Husbandage * 2 <= Wifeage) {
                String str = f.getId().replace(".", "");
                errList.add("ERROR: FAMILY: US34: The couple in family: " + str + " has large age different");
                ret = "ERROR: FAMILY: US34: The couple in family: " + str + " has large age different";
                return ret;
            }
        }
        return null;
    }

}
