package com.possible_triangle.brazier.logic;

public record PreventionRules(int distance) {

    public static final PreventionRules NONE = new PreventionRules(0);

}
