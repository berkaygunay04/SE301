package com.company.talha.vote;

/**
 * The type Results.
 */
public class Results {
    private String name;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private int option1C;
    private int option2C;
    private int option3C;
    private int image;

    /**
     * Instantiates a new Results.
     *
     * @param name     the name
     * @param question the question
     * @param option1  the option 1
     * @param option2  the option 2
     * @param option3  the option 3
     * @param option1C the option 1 c
     * @param option2C the option 2 c
     * @param option3C the option 3 c
     * @param image    the image
     */
    public Results(String name, String question, String option1, String option2, String option3, int option1C, int option2C, int option3C,int image) {
        this.name = name;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option1C = option1C;
        this.option2C = option2C;
        this.option3C = option3C;
        this.image = image;
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
     * Gets question.
     *
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets question.
     *
     * @param question the question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Gets option 1.
     *
     * @return the option 1
     */
    public String getOption1() {
        return option1;
    }

    /**
     * Sets option 1.
     *
     * @param option1 the option 1
     */
    public void setOption1(String option1) {
        this.option1 = option1;
    }

    /**
     * Gets option 2.
     *
     * @return the option 2
     */
    public String getOption2() {
        return option2;
    }

    /**
     * Sets option 2.
     *
     * @param option2 the option 2
     */
    public void setOption2(String option2) {
        this.option2 = option2;
    }

    /**
     * Gets option 3.
     *
     * @return the option 3
     */
    public String getOption3() {
        return option3;
    }

    /**
     * Sets option 3.
     *
     * @param option3 the option 3
     */
    public void setOption3(String option3) {
        this.option3 = option3;
    }

    /**
     * Gets option 1 c.
     *
     * @return the option 1 c
     */
    public int getOption1C() {
        return option1C;
    }

    /**
     * Sets option 1 c.
     *
     * @param option1C the option 1 c
     */
    public void setOption1C(int option1C) {
        this.option1C = option1C;
    }

    /**
     * Gets option 2 c.
     *
     * @return the option 2 c
     */
    public int getOption2C() {
        return option2C;
    }

    /**
     * Sets option 2 c.
     *
     * @param option2C the option 2 c
     */
    public void setOption2C(int option2C) {
        this.option2C = option2C;
    }

    /**
     * Gets option 3 c.
     *
     * @return the option 3 c
     */
    public int getOption3C() {
        return option3C;
    }

    /**
     * Sets option 3 c.
     *
     * @param option3C the option 3 c
     */
    public void setOption3C(int option3C) {
        this.option3C = option3C;
    }

    /**
     * Gets ımage.
     *
     * @return the ımage
     */
    public int getImage() {
        return image;
    }

    /**
     * Sets ımage.
     *
     * @param image the image
     */
    public void setImage(int image) {
        this.image = image;
    }
}
