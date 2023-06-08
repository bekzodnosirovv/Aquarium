package org.example.Factory;

import org.example.Aquarium;
import org.example.dto.Food;
import org.example.RandomUtil;

import java.util.LinkedList;
import java.util.List;

public class FoodFactory {

    public static List<Food> getFood() {
        List<Food> foodList = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            int x = RandomUtil.getNumber(Aquarium.X);
            int y = RandomUtil.getNumber(Aquarium.Y);
            foodList.add(new Food(x, y));
        }
        return foodList;
    }
}
