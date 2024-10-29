package org.example;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeWorkTest {

    HomeWork homeWork = new HomeWork();

    @Test
    void checkFirst() {
        TestCase1 testCase = generateTestCase1();

        assertEquals(testCase.expected, homeWork.getOriginalDoorNumbers(testCase.maxDoors, testCase.actionList));
    }

    @Test
    void checkExtended() {
        TestCase1 testCase = generateTestCase2();

        assertEquals(testCase.expected, homeWork.getOriginalDoorNumbers(testCase.maxDoors, testCase.actionList));
    }

    @Test
    void checkSecond() {
        assertEquals(List.of(3, 1, 5, 2, 4), homeWork.getLeaveOrder(5, 3));
    }


    private TestCase1 generateTestCase1() {
        TestCase1 testCase = new TestCase1();
        testCase.parseExpected("5\n" +
                "4\n" +
                "6\n" +
                "4\n" +
                "7");
        testCase.parseInput("20 7\n" +
                "L 5\n" +
                "D 5\n" +
                "L 4\n" +
                "L 5\n" +
                "D 5\n" +
                "L 4\n" +
                "L 5");

        return testCase;
    }

    private TestCase1 generateTestCase2() {
        TestCase1 testCase = new TestCase1();
        testCase.parseExpected(
                "6\n" +
                "9\n" +
                "10\n" +
                "6\n" +
                "14\n" +
                "18\n" +
                "10\n" +
                "3\n" +
                "6\n" +
                "11\n" +
                "14\n" +
                "16\n" +
                "17\n" +
                "18\n" +
                "20"
        );
        testCase.parseInput("20 27\n" +
                "D 5\n" +
                "L 5\n" +
                "D 7\n" +
                "L 7\n" +
                "D 6\n" +
                "L 7\n" +
                "D 1\n" +
                "L 4\n" +
                "D 11\n" +
                "L 10\n" +
                "D 14\n" +
                "L 13\n" +
                "D 5\n" +
                "L 5\n" +
                "D 1\n" +
                "D 2\n" +
                "D 3\n" +
                "D 5\n" +
                "D 4\n" +
                "L 1\n" +
                "L 2\n" +
                "L 3\n" +
                "L 4\n" +
                "L 5\n" +
                "L 6\n" +
                "L 7\n" +
                "L 8"
        );

        return testCase;
    }


    @RequiredArgsConstructor
    static class TestCase1 {
        int maxDoors;
        List<Action> actionList = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();

        public void parseInput(String input) {
            String[] lines = input.split("(\n|\r|\r\n)");
            maxDoors = Integer.valueOf(lines[0].split(" ")[0]);
            Arrays.stream(lines)
                    .skip(1)
                    .map(Action::parse)
                    .forEach(actionList::add);

        }


        public void parseExpected(String output) {
            String[] lines = output.split("(\n|\r|\r\n)");
            Arrays.stream(lines)
                    .map(Integer::parseInt)
                    .forEach(expected::add);
        }
    }

}