package org.example;


import java.util.ArrayList;
import java.util.List;

public class HomeWork {

    /**
     * <h1>Задание 1.</h1>
     * Решить задачу
     * <a href="https://acm.timus.ru/problem.aspx?space=1&num=1439">https://acm.timus.ru/problem.aspx?space=1&num=1439</a>
     */
    public List<Integer> getOriginalDoorNumbers(int maxDoors, List<Action> actionList) {
        var doors = new Treap<Integer>();
        var result = new ArrayList<Integer>();
        for (int i = 1; i <= maxDoors; i++) {
            doors.add(i);
        }

        var removedCount = 0;
        for (Action action : actionList) {
            var doorNumber = action.doorNumber;
            var node = doors.getSubSet(removedCount + doorNumber--).get(doorNumber);
            if (action.isLook()) {
                result.add(node.key);
            } else {
                doors.remove(node.key);
                removedCount++;
            }
        }

        return result;
    }

    /**
     * <h1>Задание 2.</h1>
     * Решить задачу <br/>
     * <a href="https://acm.timus.ru/problem.aspx?space=1&num=1521">https://acm.timus.ru/problem.aspx?space=1&num=1521</a><br/>
     * <h2>Пошагово</h2>
     * Для 5 3 входных данных:<br/>
     * _ -> 3 позиции<br/>
     * _ 1 2 <b>3</b> 4 5 => 3 <br/>
     * <b>1</b> 2 _ 4 5 => 1 <br/>
     * _ 2 4 <b>5</b> => 5 <br/>
     * <b>2</b> 4 _ => 2 <br/>
     * _ <b>4</b> => 4
     */
    public List<Integer> getLeaveOrder(int maxUnits, int leaveInterval) {
        var result = new ArrayList<Integer>();
        var solders = new Treap<Integer>();
        for (int i = 1; i < maxUnits + 1; i++){
            solders.add(i);
        }

        var order = leaveInterval - 1;
        for (int i = 0; i < maxUnits; i++, order += leaveInterval - 1) {
            var inorder = solders.inorder();
            order = order % inorder.size();
            var value = inorder.get(order);
            result.add(value);
            solders.remove(value);
        }

        return result;
    }

}
