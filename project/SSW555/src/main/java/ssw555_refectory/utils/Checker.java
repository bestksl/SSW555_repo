package ssw555_refectory.utils;

import ssw555_refectory.bean.Family;
import ssw555_refectory.bean.Individual;

import java.util.*;

public class Checker {
    private Map<String, Individual> individuals;
    private Map<String, Family> families;
    private List<String> errList = new ArrayList<>();

    public Checker(Map<String, Individual> individuals, Map<String, Family> familys) {
        this.individuals = individuals;
        this.families = familys;
    }


    public List<String> getErrList() {
        return errList;
    }

    public boolean check() throws Exception {
        //如果有不通过的项会将err信息加入到errList
        for (Individual i : individuals.values()) {
            checkBirthBeforeDeath(i);
        }

        for (Family f : families.values()) {
            uniqueFirstname(f);
        }


        return errList.size() == 0;
    }

    //Haoxuan Li
    private void checkBirthBeforeDeath(Individual i) throws Exception {
        if (i.getBirt() != null && i.getDeath() != null) {
            if (!(TimeUtils.getAge(i.getBirt()) - TimeUtils.getAge(i.getDeath()) >= 0)) {
                errList.add(i.getId() + "birth date should earlier than death dates");
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
            errList.add(f.getId() + "family member's first name should be unique!");
        }
    }

    private void checkMarrige(Family F) throws Exception {
        int DivorceData = TimeUtils.getAge(F.getDivorced());
        int MarriageData = TimeUtils.getAge(F.getMarried());
        if(DivorceData > MarriageData){
            errList.add(F.getId()+"Divorce before Marriage");
        }
    }

    // Jeff
    private void birthAfterParentsMarriges(Family F) throws Exception {
        ArrayList<String> children = F.getChildren();
        int marriagePeriod = TimeUtils.getAge(F.getMarried());
        for(String child: children){
            Individual eachChild = individuals.get(child);
            int age = TimeUtils.getAge(eachChild.getBirt());
            if(age >= marriagePeriod){
                errList.add(eachChild.getName()+" is born before Family "+F.getId());
            }
        }
    }

    private void ageOld() {

    }


    private void familyMaleLastName() {

    }

    private void uniqueId() {

    }
}
