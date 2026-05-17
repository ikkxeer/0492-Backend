package com.example.backend.web.dto;

public class DashboardStatsDTO {
    // Atributs de la classe
    private long palesActives;
    private int palesActivesPercent;
    private long ordresAvui;
    private int ordresAvuiPercent;
    private long incidencies;
    private int incidenciesPercent;
    private long entregades;
    private int entregadesPercent;

    // Constructor de la classe
    public DashboardStatsDTO() {
    }

    // Getters i Setters de la classe
    public long getPalesActives() {
        return palesActives;
    }

    public void setPalesActives(long palesActives) {
        this.palesActives = palesActives;
    }

    public int getPalesActivesPercent() {
        return palesActivesPercent;
    }

    public void setPalesActivesPercent(int palesActivesPercent) {
        this.palesActivesPercent = palesActivesPercent;
    }

    public long getOrdresAvui() {
        return ordresAvui;
    }

    public void setOrdresAvui(long ordresAvui) {
        this.ordresAvui = ordresAvui;
    }

    public int getOrdresAvuiPercent() {
        return ordresAvuiPercent;
    }

    public void setOrdresAvuiPercent(int ordresAvuiPercent) {
        this.ordresAvuiPercent = ordresAvuiPercent;
    }

    public long getIncidencies() {
        return incidencies;
    }

    public void setIncidencies(long incidencies) {
        this.incidencies = incidencies;
    }

    public int getIncidenciesPercent() {
        return incidenciesPercent;
    }

    public void setIncidenciesPercent(int incidenciesPercent) {
        this.incidenciesPercent = incidenciesPercent;
    }

    public long getEntregades() {
        return entregades;
    }

    public void setEntregades(long entregades) {
        this.entregades = entregades;
    }

    public int getEntregadesPercent() {
        return entregadesPercent;
    }

    public void setEntregadesPercent(int entregadesPercent) {
        this.entregadesPercent = entregadesPercent;
    }
}
