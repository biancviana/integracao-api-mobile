package com.example.servicos.model;

public class Feriado {

    private String date;
    private String name;
    private String type;

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String formatar() {
        String emoji = "🎉";
        if (this.type.equalsIgnoreCase("facultativo")) {
            emoji = "🟡";
        }

        return String.format(
                "%s %s\n%s\n%s",
                emoji,
                this.date,
                this.name,
                this.type.toUpperCase()
        );
    }
}
