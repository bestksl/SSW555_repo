package ssw555_refectory.utils;

import ssw555_refectory.bean.Family;
import ssw555_refectory.bean.Individual;

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
            checkBirthBeforeDeath(i);
        }

        for (Family f : families.values()) {
            uniqueFirstname(f);
            checkMarrige(f);
            birthAfterParentsMarriges(f);
        }


        return errList.size() == 0;
    }

    //Haoxuan Li
    private void checkBirthBeforeDeath(Individual i) throws Exception {
        if (i.getBirt() != null && i.getDeath() != null) {
            if (!(TimeUtils.getAge(i.getBirt()) - TimeUtils.getAge(i.getDeath()) >= 0)) {
                errList.add(i.getId() + ":  birth date should earlier than death dates");
            }
        }
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

    private void checkMarrige(Family f) throws Exception {
        if (f.getMarried() == null || f.getDivorced() == null) {
            return;
        }
        int DivorceData = TimeUtils.getAge(f.getDivorced());
        int MarriageData = TimeUtils.getAge(f.getMarried());

        if (DivorceData > MarriageData) {
            errList.add(f.getId() + ":  Divorce before Marriage");
        }
    }

    // Jeff
    private void birthAfterParentsMarriges(Family f) throws Exception {
        ArrayList<String> children = f.getChildren();
        if (f.getMarried() == null) {
            return;
        }
        int marriagePeriod = TimeUtils.getAge(f.getMarried());
        for (String child : children) {
            Individual eachChild = individuals.get(child);
            int age = TimeUtils.getAge(eachChild.getBirt());
            if (age >= marriagePeriod) {
                errList.add(eachChild.getName() + " is born before Family " + f.getId());
            }
        }
    }

    public static void printErr() {
        for (String err : errList) {
            System.out.println(err);
        }
    }

    private void ageOld() {

    }


    private void familyMaleLastName() {

    }

    private void uniqueId() {

    }
}
