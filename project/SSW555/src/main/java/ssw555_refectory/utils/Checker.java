package ssw555_refectory.utils;

import ssw555_refectory.bean.Family;
import ssw555_refectory.bean.Individual;
import sun.nio.cs.ext.MacArabic;

import java.util.*;

public class Checker {
    private Map<String, Individual> individuals;
    private Map<String, Family> familys;


    public Checker(Map<String, Individual> individuals, Map<String, Family> familys) {
        this.individuals = individuals;
        this.familys = familys;
    }


    public boolean check() throws Exception {
        //如果有1项不通过就会被throw的异常打断进程
        for (Individual i : individuals.values()) {
            checkBirthBeforeDeath(i);
        }

        for (Family f : familys.values()) {
            uniqueFirstname(f);
            checkMarrige(f);
            birthAfterParentsMarriges(f);


        }
        return true;
    }

    //Haoxuan Li
    private void checkBirthBeforeDeath(Individual i) throws Exception {
        if (i.getBirt() != null && i.getDeath() != null) {
            if (!(TimeUtils.getAge(i.getBirt()) - TimeUtils.getAge(i.getDeath()) >= 0)) {
                throw new IllegalArgumentException("Birth date should earlier than death date!");
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
                nameSet.add(individuals.get(id).getName());
            }
        }
        if (!(nameSet.size() == idList.size())) {
            throw new IllegalArgumentException("First name should be same in one family");
        }
    }

    // Jeff
    private void checkMarrige(Family F) throws Exception {
        int DivorceData = TimeUtils.getAge(F.getDivorced());
        int MarriageData = TimeUtils.getAge(F.getMarried());
        if(DivorceData > MarriageData){
            throw new IllegalArgumentException("Family "+F.getId()+"Divorce before Marriage");
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
                throw new IllegalArgumentException(eachChild.getName()+" is born before Family "+F.getId());
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
