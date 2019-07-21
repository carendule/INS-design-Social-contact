package com.hicoach.caren.hicoach.Array;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExerciseList implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    public static List<String> histroyInfos = new ArrayList<String>();

    public ExerciseList(){
    }

    public ExerciseList(String name){
        super();
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
