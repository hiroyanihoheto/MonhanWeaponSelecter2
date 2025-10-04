[Weapon.java](https://github.com/user-attachments/files/22695808/Weapon.java)
package com.example.demo;

public class Weapon {
    private String name;
    private double probability;

    public Weapon(String name, double probability) {
        this.name = name;
        this.probability = probability;
    }

    public String getName() {
        return name;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
