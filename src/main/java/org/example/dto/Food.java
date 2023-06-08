package org.example.dto;

import lombok.Getter;

@Getter
public class Food {
    private int x;
    private int y;
    private final int calories = 5;

    public Food(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
