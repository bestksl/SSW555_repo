package ssw555_refectory.utils;

import ssw555_refectory.bean.Family;
import ssw555_refectory.bean.Individual;

import java.text.SimpleDateFormat;
import java.util.*;

public class Checker {
    private static Map<String, Individual> individuals;
    private Map<String, Family> families;
    private static List<String> errList = new ArrayList<>();

    public Checker(Map<String, Individual> individuals, Map<String, Family> familys) {
        this.individuals = individuals;
        this.families = familys;
    }


    public boolean checkAll(int num) throws Exception {
        switch(num) {
            case 1:
                checkSprint1();
                break;
            case 2:
                checkSprint2();
                break;
            case 3:
                checkSprint3();
                break;
        }
        return errList.size() == 0;
    }
    public static void printErr() {
        for (String err : errList) {
            System.out.println(err);
        }
    }

    private boolean checkSprint1() throws Exception {

        for (Individual i : individuals.values()) {
            US22_uniqueIdINDI(i);
            US03_checkBirthBeforeDeath(i);
            US07_ageOld(i);
        }

        for (Family f : families.values()) {
            US22_uniqueIdFAM(f);
            US25_uniqueFirstname(f);
            US04_checkMarrige(f);
            US08_birthAfterParentsMarriges(f);
            US12_parentsNotTooOld(f);
            US16_familyMaleLastName(f);
        }
        return errList.size() == 0;
    }
    public boolean checkSprint2() throws Exception {
        // In order to check the listing ages for each member
        individuals.get("I11").setAge("");
        US23_UniqueNameAndBirthdate();
        for (Individual i : individuals.values()) {
            US31_Listlivingsingle(i);
            US35_Listrecentbirths(i);
            US36_recentdeath(i);
            US27_IncludeIndividualAges(i);
        }
        for (Family f : families.values()) {
            US09_BirthBeforeDeathOfParents(f);
            US10_MarriageAfter14(f);
            US34_Listlargeagedifferences(f);
        }
        return errList.size() == 0;
    }
    private boolean checkSprint3() throws Exception {
        US29_Listdecrease();
        for (Individual i : individuals.values()) {
            US30_Listlivingmarried(i);
            US02_Birthbeforemarriage(i);
            US38_ListUpcomingBirthdays(i);

        }
        for (Family f : families.values()) {
            US01_DatesBeforeCurrentDate(f);
            US06_DivorceBeforeDeath(f);
            US05_MarriageBeforeDeath(f);
            US28_Ordersibelingbyage(f);
        }
        return errList.size() == 0;
    }

    // Sprint 1, eight user stories
    public String US12_parentsNotTooOld(Family f) throws Exception {
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
    public String US04_checkMarrige(Family f) throws Exception {
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
    public String US08_birthAfterParentsMarriges(Family f) throws Exception {
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
    public String US07_ageOld(Individual i) throws Exception {
        if (i.getBirt() != null) {
            if ((i.getDeath() == null ? TimeUtils.getAge(i.getBirt()) : TimeUtils.getAge(i.getBirt()) - TimeUtils.getAge(i.getDeath())) >= 150) {
                errList.add("ERROR: INDIVIDUAL: US07: " + i.getId() + ":  " + i.getName() + " should less than 150 years old");
                return "ERROR: INDIVIDUAL: US07: " + i.getId() + ":  " + i.getName() + " should less than 150 years old";
            }
            return null;
        }
        return null;
    }
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
            errList.add("ERROR: FAMILY: US25: " + f.getId() + ":  family member's first name should be unique!");
            return "ERROR: FAMILY: US25: " + f.getId() + ":  family member's first name should be unique!";
        }
        return null;
    }
    public String US16_familyMaleLastName(Family f) throws Exception {
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
    public String US22_uniqueIdINDI(Individual i) throws Exception {
        if (i.getId().startsWith(".")) {
            String str = i.getId().replace(".", "");
            errList.add("ERROR: INDIVIDUAL: US22: " + str + "  individual ID is not unique");
            return "ERROR: INDIVIDUAL: US22: " + str + "  individual ID is not unique";
        }
        return null;
    }
    public String US22_uniqueIdFAM(Family f) throws Exception {
        if (f.getId().startsWith(".")) {
            String str = f.getId().replace(".", "");
            errList.add("ERROR: FAMILY: US22 " + str + "  family ID is not unique");
            return "ERROR: FAMILY: US22: " + str + "  family ID is not unique";
        }
        return null;
    }


    // Sprint 2, eight user stories
    public String US23_UniqueNameAndBirthdate() {
        Map<String, List<Individual>> map = new HashMap<>();
        StringBuilder sbs = new StringBuilder();
        for (Map.Entry<String, Individual> entry : individuals.entrySet()) {
            Individual each = entry.getValue();
            String nameAndBirth = entry.getValue().getName() + " " + entry.getValue().getBirt();
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
                errList.add("ERROR: INDIVIDUAL: US23: name and birth " + entry.getKey() + " appear in " + sb.toString());
                sbs.append("ERROR: INDIVIDUAL: US23: name and birth " + entry.getKey() + " appear in " + sb.toString() + "||");
            }
        }

        return sbs.toString();
    }
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
                errList.add("ERROR: FAMILY: US09: " + f.getId() + ": child: " + child.getId() + " birth: " + child.getBirt() + " after parents death date " + result);
                return "ERROR: FAMILY: US09: " + f.getId() + " child: " + child.getId() + " birth: " + child.getBirt() + " after parents death date " + result;
            }
        }

        return null;
    }
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
    public String US27_IncludeIndividualAges(Individual i) throws Exception{
        if(i.getBirt() == null || i.getAge().length() == 0){
            errList.add("ERROR: INDIVIDUAL: US27: "+i.getName()+" with ID: "+i.getId()+" has no age");
            return "ERROR: INDIVIDUAL: US27: "+i.getName()+" with ID: "+i.getId()+" has no age";
        }
        return null;
    }
    public String US31_Listlivingsingle(Individual i) throws Exception {
        String listlivingsingle;
        long day = 0;
        if (i.getDeath() == null && i.getBirt() != null) {
            for (Family families : families.values()) {
                if (families.getHusbandID().equals(i.getId())) {
                    day = 0;
                } else if (families.getWifeID().equals(i.getId())) {
                    day = 0;
                } else {
                    String age = i.getBirt();
                    day = TimeUtils.getAge(age);
                }
            }
        }
        if (day >= 30) {
            listlivingsingle = "LIST: INDIVIDUAL: US31: NAME:" + i.getName() + " ID:" + i.getId() + " is over 30 and has never been married";
            errList.add("LIST: INDIVIDUAL: US31: NAME:" + i.getName() + " ID:" + i.getId() + " is over 30 and has never been married");
            return listlivingsingle;
        }
        return null;
    }
    public String US35_Listrecentbirths(Individual i) throws Exception {
        String recentbirths = "";
        long day = 0;
        if (i.getBirt() != null) {
            String birthdate = i.getBirt();
            day = TimeUtils.getDaysFromDate(birthdate);
        }
        if (day <= 30 && day != 0) {
            recentbirths = "LIST: INDIVIDUAL: US35: NAME:" + i.getName() + " ID:" + i.getId() + " was born in the last 30 days";
            errList.add("LIST: INDIVIDUAL: US35: NAME:" + i.getName() + " ID:" + i.getId() + " was born in the last 30 days");
            return recentbirths;
        }
        return null;
    }
    public static String US36_recentdeath(Individual i) throws Exception {
        String deathlist = "";
        long days = 0;
        if (i.getDeath() != null) {
            String deathdate = i.getDeath();
            days = TimeUtils.getDaysFromDate(deathdate);
        }
        if (days <= 30 && days != 0) {
            deathlist = "LIST: INDIVIDUAL: US36: NAME:" + i.getName() + " ID:" + i.getId() + " has dead in 30 days";
            errList.add("LIST: INDIVIDUAL: US36: NAME:" + i.getName() + " ID:" + i.getId() + " has dead in 30 days");
            return deathlist;
        }
        return null;
    }
    public static String US34_Listlargeagedifferences(Family f) throws Exception {
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
                errList.add("LIST: FAMILY: US34: The couple in family: " + str + " has large age different");
                ret = "LIST: FAMILY: US34: The couple in family: " + str + " has large age different";
                return ret;
            }
        }
        return null;
    }


    // Sprint 3, eight user stories
    public List<String> US01_DatesBeforeCurrentDate(Family f) throws Exception {
        Individual husband, wife;
        Individual indiTemp = null;
        List<String> errList1 = new ArrayList<>();
        List<String> tempList = f.getChildren();
        tempList.add(f.getHusbandID());
        tempList.add(f.getWifeID());
        for (String id : tempList) {
            indiTemp = individuals.get(id);
            if (indiTemp == null) {
                continue;
            }
            if (null != indiTemp.getBirt() && TimeUtils.getAge(indiTemp.getBirt()) < 0) {
                errList1.add("ERROR: INDIVIDUAL: US01: " + id +" "+ indiTemp.getBirt()+ ": Birth Dates after CurrentDate");
            }
            if (null != indiTemp.getDeath() && TimeUtils.getAge(indiTemp.getDeath()) <0) {
                errList1.add("ERROR: INDIVIDUAL: US01: " + id +" "+ indiTemp.getDeath()+  ": Death Dates after CurrentDate");
            }
        }

        //test Divorced date and Marr date
        if (null != f.getDivorced() && TimeUtils.getAge(f.getDivorced()) == -1) {
            errList1.add("ERROR: FAMILY: US01: " + f.getId() + ":Divorced  Dates after CurrentDate");
        }
        if (null != f.getMarried() && TimeUtils.getAge(f.getMarried()) == -1) {
            errList1.add("ERROR: FAMILY: US01: " + f.getId() + ":Married  Dates after CurrentDate");
        }
        if (errList1.size() > 0) {
            System.out.println(errList1);
            errList.addAll(errList1);
            return errList1;
        } else {
            return null;
        }
    }
    public List<String> US06_DivorceBeforeDeath(Family f) throws Exception {
        if (f.getDivorced() == null)
            return null;
        List<String> errList1 = new ArrayList<>();
        Individual husb = individuals.get(f.getHusbandID());
        Individual wife = individuals.get(f.getWifeID());
        if (null != husb.getDeath() && TimeUtils.getAge(husb.getDeath()) - TimeUtils.getAge(f.getDivorced()) > 0) {
            errList1.add("ERROR: FAMILY: US06: " + f.getId() + " Divorced after husband death");
        }
        if (null != wife.getDeath() && TimeUtils.getAge(wife.getDeath()) - TimeUtils.getAge(f.getDivorced()) > 0) {
            errList1.add("ERROR: FAMILY: US06: " + f.getId() + " Divorced after wife death");
        }
        if (errList1.size() > 0) {
            System.out.println(errList1);
            errList.addAll(errList1);
            return errList1;
        } else {
            return null;
        }

    }
    public String US05_MarriageBeforeDeath(Family f) throws Exception {
        Individual husband = individuals.get(f.getHusbandID());
        Individual wife = individuals.get(f.getWifeID());

        if (husband != null && husband.getDeath() != null) {
            if (TimeUtils.getAge(f.getMarried()) < TimeUtils.getAge(husband.getDeath())) {
                errList.add("ERROR: FAMILY: US05: " + f.getId() + " Married:" + f.getMarried() + " after Death of " + husband.getName());
                return "ERROR: FAMILY: US05: " + f.getId() + " Married:" + f.getMarried() + " after Death of " + husband.getName();
            }
        }

        if (wife != null && wife.getDeath() != null) {
            if (TimeUtils.getAge(f.getMarried()) < TimeUtils.getAge(wife.getDeath())) {
                errList.add("ERROR: FAMILY: US05: " + f.getId() + " Married:" + f.getMarried() + " after Death of " + wife.getName());
                return "ERROR: FAMILY: US05: " + f.getId() + " Married:" + f.getMarried() + " after Death of " + wife.getName();
            }
        }
        return null;
    }
    public String US38_ListUpcomingBirthdays(Individual i) throws Exception {
        String birth = i.getBirt();

        long num = TimeUtils.getDaysInfuture(birth);
        if(num < 30 && num > 0 ){
//        String[] birthArray = birth.split("-");
//        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
//
//        Date now = new Date();
//        String strDate = sdfDate.format(now);
//
//        String[] nowArray = strDate.split("-");
//        System.out.println(Arrays.toString(nowArray));

        //if (birthArray[1].equalsIgnoreCase(nowArray[1]) && birthArray[2].equalsIgnoreCase(nowArray[2])) {
            errList.add("LIST: INDIVIDUAL: US38: NAME:" + i.getName() + " ID:" + i.getId() + " will born in this Month");
            return "LIST: INDIVIDUAL: US38: NAME:" + i.getName() + " ID:" + i.getId() + " will born in this Month";
        }
        System.out.println("User story 38 birth day = " + birth);
        return null;
    }
    public String US30_Listlivingmarried(Individual i) throws Exception {
        String listlivingmarried;
        long day = 0;
        if (i.getDeath() == null && i.getBirt() != null) {
            for (Family families : families.values()) {
                if (families.getHusbandID().equals(i.getId())) {
                    String age = i.getBirt();
                    day = TimeUtils.getAge(age);
                } else if (families.getWifeID().equals(i.getId())) {
                    String age = i.getBirt();
                    day = TimeUtils.getAge(age);
                } else {
                    day = 0;
                }
            }
        }
        if (day > 0) {
            listlivingmarried = "LIST: INDIVIDUAL: US30: NAME:" + i.getName() + " ID: " + i.getId() + " is alive and married";
            errList.add("LIST: INDIVIDUAL: US30: NAME:" + i.getName() + " ID: " + i.getId() + " is alive and married");
            return listlivingmarried;
        }
        return null;
    }
    public String US02_Birthbeforemarriage(Individual i) throws Exception {
        String birthbeforemarriage;
        int birth = 0;
        int marriageperiod = 0;
        if (i.getBirt() != null) {
            birth = TimeUtils.getAge(i.getBirt());
            for (Family families : families.values()) {
                if (families.getHusbandID().equals(i.getId()) || families.getWifeID().equals(i.getId())) {
                    marriageperiod = TimeUtils.getAge(families.getMarried());
                }
            }
        }
        if (birth < marriageperiod) {
            birthbeforemarriage = "ERROR: INDIVIDUAL: US02: NAME:" + i.getName() + " ID: " + i.getId() + " birth should occur before marriage";
            errList.add("ERROR: INDIVIDUAL: US02: NAME:" + i.getName() + " ID: " + i.getId() + " birth should occur before marriage");
            return birthbeforemarriage;
        }
        return null;
    }
    //Shiwei Ding
    public String US28_Ordersibelingbyage(Family f) throws Exception {
        ArrayList<String> Sname = new ArrayList<>();
        ArrayList<Integer> Agelist = new ArrayList<>();
        ArrayList<String> Result = new ArrayList<>();
        String orderlist = "";
        Sname = f.getChildren();
        for (Individual individuals : individuals.values()) {
            for (int i = 0; i < Sname.size(); i++) {
                if (individuals.getId().equals(Sname.get(i))) {
                    if (Agelist.size() == 0) {
                        Agelist.add(Integer.valueOf(individuals.getAge()));
                        Result.add(individuals.getId());
                    } else {
                        for (int j = 0; j < Agelist.size(); j++) {
                            if (Agelist.get(j) > Integer.valueOf(individuals.getAge())) {
                                continue;
                            } else {
                                Agelist.add(j, Integer.valueOf(individuals.getAge()));
                                Result.add(j, individuals.getId());
                                break;
                            }
                        }
                    }
                }
            }
        }
        orderlist = "LIST: FAMILY: US_28: In family " + f.getId() + " the age order for child is ";
        for(int i = 0; i < Result.size(); i++){
            orderlist = orderlist + Result.get(i) + " age: " + Agelist.get(i) + " ";
        }
        errList.add(orderlist);
        return orderlist;
    }

    public String US29_Listdecrease() throws Exception {
        String declist = "LIST: INDIVIDUAL: US29:";
        for (Individual i : individuals.values()) {
            if (i.getDeath() != null) {
                declist = declist + " Name: " + i.getName() + " ID: " + i.getId();
            }
        }
        declist = declist + " has been decreaced";
        errList.add(declist);
        return declist;
    }
}
