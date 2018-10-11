package org.rdtif.zaxslackbot.userinterface;

public class Extent {
    private final int rows;
    private final int columns;

    public Extent(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
