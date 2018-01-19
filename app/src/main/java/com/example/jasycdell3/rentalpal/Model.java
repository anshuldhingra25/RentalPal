package com.example.jasycdell3.rentalpal;

/**
 * Created by JASYC DELL 3 on 6/20/2016.
 */
public class Model {

    private String car_direction;
    private boolean Selected = false;
    private Integer stat;
    private Integer car_images;
    int listItemPosition;


    Model(String car_direction, boolean Selected, Integer stat, Integer car_images) {
        this.car_direction = car_direction;
        this.Selected = Selected;
        this.stat = stat;
        this.car_images = car_images;
    }


    public int getListItemPosition() {
        return listItemPosition;
    }

    public void setListItemPosition(int listItemPosition) {
        this.listItemPosition = listItemPosition;
    }

    public Integer getCar_images() {
        return car_images;
    }


    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    public String getCar_direction() {
        return car_direction;
    }


}