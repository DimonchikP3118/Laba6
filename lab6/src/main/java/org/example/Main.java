package org.example;


import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Box box = new Box<>("привет");
        Box box1 = new Box<>(135);
        Box box2 = new Box<>(new ArrayList<String>());
        Box boxBox = new Box<>(new Box<>("сюрприз"));
    }
}