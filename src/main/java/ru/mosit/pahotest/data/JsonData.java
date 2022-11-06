package ru.mosit.pahotest.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

// Data-class для хранения информации в JSON
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JsonData {
    private Float light;
    private Float co2;
    private Float temperature;
    private Float sound;
    private int ip;
    private Date date;

    public JsonData(JsonData another) {
        this.light = another.getLight();
        this.co2 = another.getCo2();
        this.temperature = another.getTemperature();
        this.sound = another.getSound();
        this.ip = another.ip;
        this.date = another.date;
    }
}
