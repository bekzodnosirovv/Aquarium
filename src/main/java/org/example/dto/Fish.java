package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.Aquarium;
import org.example.Enum.FishType;
import org.example.Enum.Gender;
import org.example.RandomUtil;

@Getter
@Setter
@AllArgsConstructor
public class Fish extends Thread {

    private int x;
    private int y;
    private int lifetime;
    private Gender gender;
    private FishType type;
    private long collisionTime;
    private int childCount;
    private boolean stop;

    @Override
    public void run() {
        try {
            process();
        } catch (Exception e) {
            stop = false;
        }
    }

    private void process() throws InterruptedException {
        while (lifetime > 0) {
            Thread.sleep(2000);
            swing();
            Aquarium.collision(this);
            lifetime--;
            Aquarium.showDetail();
        }
        if (stop) Aquarium.remove(this);
    }

    private void swing() {
        int direction = RandomUtil.getNumber(3); // 0,1,2,3
        switch (direction) {
            case 0:
                if (y < Aquarium.Y) {
                    y++;
                }
                break;
            case 1: {
                if (x < Aquarium.X) x++;

                break;
            }
            case 2:
                if (y > 0) {
                    y--;
                }
                break;
            case 3:
                if (x > 0) {
                    x--;
                }
                break;
        }
    }

    @Override
    public String toString() {
        return "Fish{" + "Name : " + getName() +
                ", x=" + x +
                ", y=" + y +
                ", lifetime=" + lifetime +
                ", gender=" + gender +
                ", type=" + type +
                '}';
    }
}
