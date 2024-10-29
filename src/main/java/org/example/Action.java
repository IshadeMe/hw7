package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Действия для задачи 1
 */
@Getter
@AllArgsConstructor
public class Action {

    /**
     * Look = true
     * Destroy = false
     */
    boolean isLook;

    int doorNumber;

    public static Action look(int i) {
        return new Action(true, i);
    }

    public static Action destroy(int i) {
        return new Action(false, i);
    }

    @Override
    public String toString() {
        return (isLook ? "L" : "D") + " " + doorNumber;
    }

    public static Action parse(String input) {
        String[] part = input.split(" ");
        int num = Integer.parseInt(part[1]);
        if (part[0].equals("L")) {
            return look(num);
        } else {
            return destroy(num);
        }
    }
}
