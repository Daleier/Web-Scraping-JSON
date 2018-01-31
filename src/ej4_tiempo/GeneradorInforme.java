/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ej4_tiempo;

/**
 *
 * @author dam203
 */
public class GeneradorInforme {

    int tempMax, tempMin;   /* ºC */
    int temp08, temp14, temp20;   /* ºC */
    int velocidadViento;    /* km/h */
    float lluvia;             /* mm */
    String horaAlba, horaOcaso;

    String informe() {
        String resultado = "";
        resultado = resultado + "Temperatura máxima: " + this.tempMax + "ºC\n";
        resultado = resultado + "Temperatura mínima: " + this.tempMin + "ºC\n";
        resultado = resultado + "Temperatura 08:00: " + this.temp08 + "ºC\n";
        resultado = resultado + "Temperatura 14:00: " + this.temp14 + "ºC\n";
        resultado = resultado + "Temperatura 20:00: " + this.temp20 + "ºC\n";
        resultado = resultado + "Hora alba: " + this.horaAlba + "H\n";
        resultado = resultado + "Hora ocaso: " + this.horaOcaso + "H\n";
        resultado = resultado + "Velocidad viento: " + this.velocidadViento + "km/h\n";
        resultado = resultado + "Lluvia: " + this.lluvia + "mm\n";
        return resultado;
    }
}
