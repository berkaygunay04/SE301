package com.company.talha.vote;

public class ViewMyVote {
    private int image;
    private int status;
    private String name;
    private String Option1;
    private String Option2;
    private String Option3;
    private String UsersChoice;

    public ViewMyVote(int image, String name, String option1, String option2, String option3, String usersChoice,int status) {
        this.image = image;
        this.name = name;
        Option1 = option1;
        Option2 = option2;
        Option3 = option3;
        this.status=status;
        UsersChoice = usersChoice;
    }

    public int getTur() {
        return image;
    }

    public void setTur(int tur) {
        image = tur;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOption1() {
        return Option1;
    }

    public void setOption1(String option1) {
        Option1 = option1;
    }

    public String getOption2() {
        return Option2;
    }

    public void setOption2(String option2) {
        Option2 = option2;
    }

    public String getOption3() {
        return Option3;
    }

    public void setOption3(String option3) {
        Option3 = option3;
    }

    public String getUsersChoice() {
        return UsersChoice;
    }

    public void setUsersChoice(String usersChoice) {
        UsersChoice = usersChoice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
