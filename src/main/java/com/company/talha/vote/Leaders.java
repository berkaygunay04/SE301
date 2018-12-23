package com.company.talha.vote;

/**
 * The type Leaders.
 */
public class Leaders {
    /**
     * The Name.
     */
    public   String name;
    /**
     * The Surname.
     */
    public  String surname;
    /**
     * The Vote count.
     */
    public int voteCount;

    /**
     * Instantiates a new Leaders.
     *
     * @param name      the name
     * @param surname   the surname
     * @param voteCount the vote count
     */
    public Leaders( String name, String surname, int voteCount) {
        this.name = name;
        this.surname = surname;
        this.voteCount = voteCount;
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
     * Gets surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets surname.
     *
     * @param surname the surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets vote count.
     *
     * @return the vote count
     */
    public int getVoteCount() {
        return voteCount;
    }

    /**
     * Sets vote count.
     *
     * @param voteCount the vote count
     */
    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

}
