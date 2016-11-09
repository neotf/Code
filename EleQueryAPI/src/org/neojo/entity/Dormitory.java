package org.neojo.entity;

import java.io.Serializable;

public final class Dormitory implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public static final Integer[][] ROOMS = {
            {42, 35, 35, 35, 35, 35},
            {31, 33, 33, 33, 33, 33},
            {23, 25, 27, 27, 27, 27},
            {25, 26, 29, 29, 29, 29},
            {28, 28, 29, 29, 29, 29},
            {23, 26, 29, 29, 29, 29},
            {30, 30, 31, 31, 31, 31},
            {30, 30, 31, 31, 31, 31},
            {23, 24, 27, 27, 27, 27},
            {26, 27, 27, 27, 27, 27},
            {28, 29, 29, 29, 29, 29},
            {33, 33, 33, 33, 33, 33},
            {33, 34, 35, 35, 35, 35},
            {34, 34, 35, 35, 35, 35},
            {33, 34, 35, 35, 35, 35},
            {31, 34, 35, 35, 35, 35},
            {35, 37, 38, 38, 38, 38},
            {54, 57, 60, 60, 60, 60, 60, 60, 60, 60, 60},
            {16, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24}
    };
    
    public static String format(Dormitory dor) {
        return "1-" + formatBuild(dor.build) + "-" + formatFloor(dor.floor) + formatRoom(dor.room);
    }

    private Integer build;
    private Integer floor;
    private Integer room;

    public Dormitory() {
    }

    public Integer getBuild() {
        return build;
    }

    public void setBuild(Integer build) {
        this.build = build;
    }

    public Dormitory(Integer build, Integer floor, Integer room) {
		super();
		this.build = build;
		this.floor = floor;
		this.room = room;
	}

	public static String formatBuild(Integer build) {
        return build < 10 ? "0" + build : "" + build;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public static String formatFloor(Integer floor) {
        return floor < 10 ? "0" + floor : "" + floor;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public static String formatRoom(Integer room) {
        return room < 10 ? "0" + room : "" + room;
    }

    @Override
    public String toString() {
        return format(this);
    }

}
