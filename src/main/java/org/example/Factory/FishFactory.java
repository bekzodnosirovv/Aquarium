package org.example.Factory;

import org.example.Aquarium;
import org.example.Enum.FishType;
import org.example.Enum.Gender;
import org.example.dto.Fish;
import org.example.RandomUtil;

public class FishFactory {

    public static Fish getFish() {
        int x = RandomUtil.getNumber(Aquarium.X);
        int y = RandomUtil.getNumber(Aquarium.Y);
        int lifetime = RandomUtil.getNumber(30);
        Gender gender = RandomUtil.nextBoolean() ? Gender.MALE : Gender.FEMALE;
        FishType type = RandomUtil.nextBoolean() ? FishType.CARP_FISH : FishType.CATFISH;

        return new Fish(x, y, lifetime, gender, type, 0, 0,true);
    }

    public static Fish getShark() {
        int x = RandomUtil.getNumber(Aquarium.X);
        int y = RandomUtil.getNumber(Aquarium.Y);
        int lifetime = RandomUtil.getNumber(30);
        Gender gender = RandomUtil.nextBoolean() ? Gender.MALE : Gender.FEMALE;
        FishType type = FishType.SHARK;

        return new Fish(x, y, lifetime, gender, type, 0, 0,true);
    }
}
