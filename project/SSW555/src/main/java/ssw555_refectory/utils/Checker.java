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

    public boolean check() throws Exception {
        //如果有不通过的项会将err信息加入到errList
        for (Individual i : individuals.values()) {
            uniqueIdINDI(i);
            checkBirthBeforeDeath(i);
            ageOld(i);
        }

        for (Family f : families.values()) {
            uniqueIdFAM(f);
            uniqueFirstname(f);
            checkMarrige(f);
            birthAfterParentsMarriges(f);
            parentsNotTooOld(f);
            familyMaleLastName(f);

        }


        return errList.size() == 0;
    }

    //Haoxuan Li
    public String checkBirthBeforeDeath(Individual i) throws Exception {
        if (i.getBirt() != null && i.getDeath() != null) {
            if (!(TimeUtils.getAge(i.getBirt()) - TimeUtils.getAge(i.getDeath()) >= 0)) {
                errList.add("ERROR: INDIVIDUAL: US03: "+i.getId() + " "+i.getName()+
                        "'s birth date "+i.getBirt()+ " occurs after death dates "+i.getDeath());
                return "ERROR: INDIVIDUAL: US03: "+i.getId() + " "+i.getName()+
                        "'s birth date "+i.getBirt()+ " occurs after death dates "+i.getDeath();
            }
        }
        return null;
    }

    //Haoxuan Li
    private void uniqueFirstname(Family f) {
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
            errList.add(f.getId() + ":  family member's first name should be unique!");
        }
    }

    public String checkMarrige(Family f) throws Exception {
        if (f.getMarried() == null || f.getDivorced() == null) {
            return null;
        }
        int DivorceData = TimeUtils.getAge(f.getDivorced());
        int MarriageData = TimeUtils.getAge(f.getMarried());

        if (DivorceData > MarriageData) {
            errList.add("ERROR: FAMILY: US04: "+ f.getId() + " Divorce:"+f.getDivorced()+ " before Marriage "+f.getMarried());
            return "ERROR: FAMILY: US04: "+ f.getId() + " Divorce:"+f.getDivorced()+ " before Marriage "+f.getMarried();
        }
        return null;
    }

    // Jeff
    private StringBuilder sb = new StringBuilder();
    public String birthAfterParentsMarriges(Family f) throws Exception {
        ArrayList<String> children = f.getChildren();
        if (f.getMarried() == null || f.getChildren().size() == 0) {
            return null;
        }
        int marriagePeriod = TimeUtils.getAge(f.getMarried());
        for (String child : children) {
            Individual eachChild = individuals.get(child);
            int age = TimeUtils.getAge(eachChild.getBirt());
            if (age >= marriagePeriod) {
                errList.add("ERROR: FAMILY: US08: Child "+ eachChild.getName() + " born "+eachChild.getBirt()+" before Marriage on " + f.getMarried());
                sb.append("ERROR: FAMILY: US08: Child "+ eachChild.getName() + " born "+eachChild.getBirt()+" before Marriage on " + f.getMarried() + " || ");
            }
        }
        if(sb.length() == 0) return null;
        String res = sb.toString();
        sb.setLength(0);
        return res;

    }

    public static void printErr() {
        for (String err : errList) {
            System.out.println(err);
        }
    }


    public String familyMaleLastName(Family f)  throws Exception {
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
                if(individuals.getId().equals(Sname.get(j)))
                {
                    childLastname = individuals.getLastName();
                    childgender = individuals.getGender();
                    if(childgender.equals("M") && !childLastname.equals(fatherLastname))
                    {
                        String str = f.getId().replace(".","");
                        errList.add(str + ":  The last name for Male person in a family is not the same!");
                        return str + ":  The last name for Male person in a family is not the same!";
                    }
                }
            }
        }
        return null;
    }

    public String uniqueIdINDI(Individual i)  throws Exception {
        if (i.getId().startsWith(".")){
            String str = i.getId().replace(".","");
            errList.add( str + ":  individual ID is not unique");
            return str + ":  individual ID is not unique";
        }
        return null;
    }

    public String uniqueIdFAM(Family f)  throws Exception {
        if (f.getId().startsWith(".")){
            String str = f.getId().replace(".","");
            errList.add( str + ":  family ID is not unique");
            return  str + ":  family ID is not unique";
        }
        return null;
    }

    //Zihan Li
    public void ageOld(Individual i) throws Exception {
        if (i.getBirt() != null) {
            if ((i.getDeath() == null ? TimeUtils.getAge(i.getBirt()) : TimeUtils.getAge(i.getBirt()) - TimeUtils.getAge(i.getDeath())) >= 150) {
                errList.add("ERROR: INDIVIDUAL: US07: "+i.getId() + ":  "+i.getName()+" should less than 150 years old");
            }
        }
    }

    //Zihan Li
    public void parentsNotTooOld(Family f) throws Exception {
        ArrayList<String> children = f.getChildren();
        String father = f.getHusbandID();
        String mother = f.getWifeID();
        Individual dad = individuals.get(father);
        Individual mom = individuals.get(mother);
        for (String child : children) {
            Individual eachchildren = individuals.get(child);
            if (eachchildren.getBirt() != null) {
                if ((TimeUtils.getAge(dad.getBirt()) - TimeUtils.getAge(eachchildren.getBirt()) >= 80) || (TimeUtils.getAge(mom.getBirt()) - TimeUtils.getAge(eachchildren.getBirt()) >= 60)) {
                    errList.add("ERROR: FAMILY: US12: "+f.getId() + ":  Mother should be less than 60 years older than her children and father should be less than 80 years older than his children");
                }
            }
        }
    }
}
