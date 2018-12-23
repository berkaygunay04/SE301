package com.company.talha.vote;

/**
 * The type View my vote.
 */
public class ViewMyVote {
    private int image;
    private int status;
    private String name;
    private String Option1;
    private String Option2;
    private String Option3;
    private String UsersChoice;

    /**
     * Instantiates a new View my vote.
     *
     * @param image       the image
     * @param name        the name
     * @param option1     the option 1
     * @param option2     the option 2
     * @param option3     the option 3
     * @param usersChoice the users choice
     * @param status      the status
     */
    public ViewMyVote(int image, String name, String option1, String option2, String option3, String usersChoice,int status) {
        this.image = image;
        this.name = name;
        Option1 = option1;
        Option2 = option2;
        Option3 = option3;
        this.status=status;
        UsersChoice = usersChoice;
    }

    /**
     * Gets tur.
     *
     * @return the tur
     */
    public int getTur() {
        return image;
    }

    /**
     * Sets tur.
     *
     * @param tur the tur
     */
    public void setTur(int tur) {
        image = tur;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets option 1.
     *
     * @return the option 1
     */
    public String getOption1() {
        return Option1;
    }

    /**
     * Sets option 1.
     *
     * @param option1 the option 1
     */
    public void setOption1(String option1) {
        Option1 = option1;
    }

    /**
     * Gets option 2.
     *
     * @return the option 2
     */
    public String getOption2() {
        return Option2;
    }

    /**
     * Sets option 2.
     *
     * @param option2 the option 2
     */
    public void setOption2(String option2) {
        Option2 = option2;
    }

    /**
     * Gets option 3.
     *
     * @return the option 3
     */
    public String getOption3() {
        return Option3;
    }

    /**
     * Sets option 3.
     *
     * @param option3 the option 3
     */
    public void setOption3(String option3) {
        Option3 = option3;
    }

    /**
     * Gets users choice.
     *
     * @return the users choice
     */
    public String getUsersChoice() {
        return UsersChoice;
    }

    /**
     * Sets users choice.
     *
     * @param usersChoice the users choice
     */
    public void setUsersChoice(String usersChoice) {
        UsersChoice = usersChoice;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(int status) {
        this.status = status;
    }

}
