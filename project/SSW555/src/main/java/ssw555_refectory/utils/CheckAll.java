package ssw555_refectory.utils;

import ssw555_refectory.bean.Family;
import ssw555_refectory.bean.Individual;

import java.util.*;

public class CheckAll {
    private Map<String, Individual> individuals;
    private Map<String, Family> familys;


    public CheckAll(Map<String, Individual> individuals, Map<String, Family> familys) {
        this.individuals = individuals;
        this.familys = familys;
    }


    public boolean CheckMain() throws Exception {

        boolean result = true;
        for (Individual i : individuals.values()) {
            checkBirthBeforeDeath(i);
        }

        for (Family f : familys.values()) {
            uniqueFirstname(f);
        }

        return result;
    }

    public void checkMarrige() {

    }


    //Haoxuan Li
    private boolean checkBirthBeforeDeath(Individual i) throws Exception {
        if (i.getBirt() != null && i.getDeath() != null) {
            return TimeUtils.getAge(i.getBirt()) - TimeUtils.getAge(i.getDeath()) >= 0;
        }
        return true;
    }

    public void birthAfterParentsMarriges() {

    }

    public void ageOld() {

    }

    private boolean uniqueFirstname(Family f) {
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
        return nameSet.size() == idList.size();
    }

    public void familyMaleLastName() {

    }

    public void uniqueId() {

    }
}
