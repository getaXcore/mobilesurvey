package surveyor.id.com.mobilesurvey.modal;

/**
 * Created by Vigaz on 9/17/2017.
 */


public class Hitung {
    private String titleachievement, descriptionachievement, pointachievement, activeachievement,
            numbergoals, target, totalsurvey;
    public Hitung() {
    }

    public Hitung(String titleachievement, String descriptionachievement,String pointachievement,String activeachievement,String numbergoals, String target, String totalsurvey) {
        this.titleachievement = titleachievement;
        this.descriptionachievement = descriptionachievement;
        this.pointachievement = pointachievement;
        this.activeachievement = activeachievement;
        this.numbergoals = numbergoals;
        this.target = target;
        this.totalsurvey = totalsurvey;
    }

    public String getTitleAchievement() {

        return titleachievement;
    }

    public void setTitleAchievement(String titleachievement) {
        this.titleachievement = titleachievement;
    }

    public String getDescriptionAchievement() {

        return descriptionachievement;
    }

    public void setDescriptionAchievement(String descriptionachievement) {
        this.descriptionachievement = descriptionachievement;
    }

    public String getPointAchievement() {
        return pointachievement;
    }

    public void setPointAchievement(String pointachievement) {
        this.pointachievement = pointachievement;
    }

    public String getActiveAchievement() {
        return activeachievement;
    }

    public void setActiveAchievement(String activeachievement) {
        this.activeachievement = activeachievement;
    }

    public String getNumberGoals() {
        return numbergoals;
    }

    public void setNumberGoals(String numbergoals) {
        this.numbergoals = numbergoals;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTotalsurvey() {
        return totalsurvey;
    }

    public void setTotalSurvey(String totalsurvey) {
        this.totalsurvey = totalsurvey;
    }
}

