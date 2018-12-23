package com.company.talha.vote;

/**
 * The type Election object.
 */
public class ElectionObject {//Election Objesi
    /**
     * The Name.
     */
    String name;
    /**
     * The Des.
     */
    String des;
    /**
     * The Image.
     */
    int image;
    /**
     * The Id.
     */
    String id;

    /**
     * Instantiates a new Election object.
     *
     * @param name  the name
     * @param des   the des
     * @param image the image
     * @param id    the id
     */
    ElectionObject (String name, String des,int image,String id){
        this.name=name;
        this.des=des;
        this.image=image;
        this.id=id;
    }
}
