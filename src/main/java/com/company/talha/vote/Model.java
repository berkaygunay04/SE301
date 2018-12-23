package com.company.talha.vote;

/**
 * The type Model.
 */
public class Model {
    private int icon;
    private String title;
    private String counter;

    private boolean isGroupHeader = false;

    /**
     * Instantiates a new Model.
     *
     * @param title the title
     */
    public Model(String title) {
        this(-1,title,null);
        isGroupHeader = true;
    }

    /**
     * Instantiates a new Model.
     *
     * @param icon    the icon
     * @param title   the title
     * @param counter the counter
     */
    public Model(int icon, String title, String counter) {
        super();
        this.icon = icon;
        this.title = title;
        this.counter = counter;
    }

    /**
     * Gets ıcon.
     *
     * @return the ıcon
     */
    public int getIcon() {
        return icon;
    }

    /**
     * Sets ıcon.
     *
     * @param icon the icon
     */
    public void setIcon(int icon) {
        this.icon = icon;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets counter.
     *
     * @return the counter
     */
    public String getCounter() {
        return counter;
    }

    /**
     * Sets counter.
     *
     * @param counter the counter
     */
    public void setCounter(String counter) {
        this.counter = counter;
    }

    /**
     * Is group header boolean.
     *
     * @return the boolean
     */
    public boolean isGroupHeader() {
        return isGroupHeader;
    }

    /**
     * Sets group header.
     *
     * @param groupHeader the group header
     */
    public void setGroupHeader(boolean groupHeader) {
        isGroupHeader = groupHeader;
    }
}
