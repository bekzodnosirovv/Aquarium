package org.example;

import org.example.Enum.FishType;
import org.example.Enum.Gender;
import org.example.Factory.FishFactory;
import org.example.Factory.FoodFactory;
import org.example.dto.Fish;
import org.example.dto.Food;

import java.util.LinkedList;
import java.util.List;

public class Aquarium {
    public static final int X = 5;
    public static final int Y = 10;

    private static int totalSize = (X + 1) * (Y + 1);

    private static List<Fish> fishList = new LinkedList<>();

    private static List<Food> foodList = new LinkedList<>();

    private static Fish shark;

    private static long createFoodTime;

    public synchronized static void start() {
        System.out.println("  <<<<<<<<<<<<<<<<< HELLO >>>>>>>>>>>>>>>");
        System.out.println("\uD83D\uDC1F \uD83D\uDC1F \uD83D\uDC1F START GAME  \uD83D\uDC1F \uD83D\uDC1F \uD83D\uDC1F");
        int n = RandomUtil.getNumber(10);
        for (int i = 0; i < n; i++) {
            Fish fish = FishFactory.getFish();
            fish.start();
            fishList.add(fish);
        }
        Fish sharkA = FishFactory.getShark();
        sharkA.start();
        shark = sharkA;
    }

    public synchronized static void remove(Fish fish) {
        if (fish == shark) {
            shark = null;
            System.out.println("*********** \uD83E\uDD88 SHARK DEAD *************");
            System.out.println(fish + "\n");
            return;
        }
        fishList.remove(fish);
        System.out.println("********** \uD83D\uDC1F FISH DEAD ************");
        System.out.println(fish + "\n");

    }

    public synchronized static void collision(Fish fish) {
        if (collisionShark(fish)) return;
        collisionFood(fish);
        if (!fish.getType().equals(FishType.SHARK)) collisionLove(fish);
    }

    public static synchronized void collisionLove(Fish fish) {
        if (fishList.size() >= totalSize) {
            System.out.println("\uD83D\uDE45\u200D♂️ NO SPACE  \uD83D\uDE45\u200D♂️\n");
            return;
        }
        for (Fish f : fishList) {
            if (fish.getType().equals(f.getType()) && !fish.getGender().equals(f.getGender())) {
                long nowTime = System.currentTimeMillis();
                if (fish.getGender().equals(Gender.FEMALE) && fish.getChildCount() < 5 && (nowTime - fish.getCollisionTime()) / 1000 > 3) {
                    fish.setCollisionTime(nowTime);
                    fish.setChildCount(fish.getChildCount() + 1);
                    System.out.println("************ Love Collision ❤️❤️❤️");
                    Fish babyFish = FishFactory.getFish();
                    babyFish.setType(fish.getType());
                    fishList.add(babyFish);
                    babyFish.start();
                    System.out.println("Parent : " + f.getName() + " " + fish.getName() + " new child : " + babyFish.getName() + "\n");
                    return;
                } else if (f.getGender().equals(Gender.FEMALE) && f.getChildCount() < 5 && (nowTime - f.getCollisionTime()) / 1000 > 3) {
                    f.setCollisionTime(nowTime);
                    f.setChildCount(f.getChildCount() + 1);
                    System.out.println("************ Love Collision ❤️❤️❤️");
                    Fish babyFish = FishFactory.getFish();
                    babyFish.setType(fish.getType());
                    fishList.add(babyFish);
                    babyFish.start();
                    System.out.println("Parent : " + f.getName() + " " + fish.getName() + " new child : " + babyFish.getName() + "\n");
                    return;
                }
            }
        }


    }

    public static synchronized boolean collisionShark(Fish fish) {
        if (fishList.isEmpty() || shark == null) return false;
        if (!fish.getType().equals(FishType.SHARK)) {
            if (fish.getY() == shark.getY() && fish.getX() == shark.getX()) {
                shark.setLifetime(shark.getLifetime() + fish.getLifetime());
                fish.setLifetime(0);
                fishList.remove(fish);
                fish.interrupt();
                System.out.println("*********** Collision Fish and Shark  \uD83D\uDC1F  \uD83E\uDD88*************");
                System.out.println(shark.getName() + " and " + fish.getName() + "\n");
                return true;
            }
            return false;
        }
        for (Fish f : fishList) {
            if (f.getX() == fish.getX() && f.getY() == fish.getY()) {
                fish.setLifetime(fish.getLifetime() + f.getLifetime());
                f.setLifetime(0);
                fishList.remove(f);
                f.interrupt();
                System.out.println("*********** Collision Fish and Shark  \uD83D\uDC1F  \uD83E\uDD88*************");
                System.out.println(fish.getName() + " and " + f.getName() + "\n");
                return true;
            }
        }
        return false;
    }

    public static synchronized void collisionFood(Fish fish) {
        createFood();
        if (foodList.isEmpty() || fish.getType().equals(FishType.SHARK)) return;
        for (Food food : foodList) {
            if (food.getX() == fish.getX() && food.getY() == fish.getY()) {
                fish.setLifetime(fish.getLifetime() + food.getCalories());
                foodList.remove(food);
                System.out.println("*********** Collision food \uD83E\uDDC0\uD83C\uDF54\uD83C\uDF2E\uD83C\uDF55**************");
                System.out.println(fish + "\n");
            }
        }
    }

    public static void createFood() {
        if ((System.currentTimeMillis() - createFoodTime) / 1000 > 10) {
            foodList = FoodFactory.getFood();
            createFoodTime = System.currentTimeMillis();
        }
    }

    public synchronized static void showDetail() {
        System.out.println("#########################################");
        int maleCountF1 = 0, femaleCountF1 = 0, maleCountF2 = 0, femaleCountF2 = 0;
        int carpFishCount = 0, catFishCount = 0;
        int sharkCount = 0;
        int total = fishList.size();
        for (Fish fish : fishList) {
            switch (fish.getType()) {
                case CARP_FISH -> {
                    if (fish.getGender().equals(Gender.MALE)) maleCountF1++;
                    else femaleCountF1++;
                    carpFishCount++;
                }
                case CATFISH -> {
                    if (fish.getGender().equals(Gender.MALE)) maleCountF2++;
                    else femaleCountF2++;
                    catFishCount++;
                }
            }
           // System.out.println(fish);
        }
        if (shark != null) {
            sharkCount = 1;
            total++;
           // System.out.println(shark);
        }
        System.out.println("\uD83D\uDC1F Total : " + total);
        System.out.println("Shark : " + sharkCount);
        System.out.println("Carp Fish : " + carpFishCount);
        System.out.println("CatFish : " + catFishCount);
        System.out.println("Carp Fish Male : " + maleCountF1);
        System.out.println("Carp Fish Female : " + femaleCountF1);
        System.out.println("CatFish Male : " + maleCountF2);
        System.out.println("CatFish Female : " + femaleCountF2);
        System.out.println("##########################################\n");

    }


}
