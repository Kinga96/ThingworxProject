/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thingworxagentmygreenhouse.thing;

/**
 *
 * @author Kinga
 */
public enum MyGreenHouse {
    MyGreenHouse1("MyGreenHouse1"),
    MyGreenHouse2("MyGreenHouse2"),
    MyGreenHouse3("MyGreenHouse3"),
    MyGreenHouse4("MyGreenHouse4");

    public String name;

    private MyGreenHouse(String name) {
        this.name = name;
    }
}