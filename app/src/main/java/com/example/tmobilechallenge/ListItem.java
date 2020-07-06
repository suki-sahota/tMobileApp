package com.example.tmobilechallenge;

public class ListItem {

    // Instance variables start
    // ------------------------
    // text
    private String tValue;
    private String tColor;
    private float tSize;

    // title_description
    private String dValue;
    private String dColor;
    private float dSize;

    // image_title_description
    private String url;
    private int width;
    private int height;

    private int size;
    // ------------------------
    // Instance variables end

    // Constructors start
    // ------------------------
    // text constructor
    public ListItem(final String tValue, final String tColor, final float tSize) {
        this.tValue = tValue;
        this.tColor = tColor;
        this.tSize = tSize;
        this.size = 3;
    }

    // title_description constructor
    public ListItem(final String tValue, final String tColor, final float tSize,
                    final String dValue, final String dColor, final float dSize) {
        this.tValue = tValue;
        this.tColor = tColor;
        this.tSize = tSize;
        this.dValue = dValue;
        this.dColor = dColor;
        this.dSize = dSize;
        this.size = 6;
    }

    // image_title_description constructor
    public ListItem(final String tValue, final String tColor, final float tSize,
                    final String dValue, final String dColor, final float dSize,
                    final String url, final int width, final int height) {
        this.tValue = tValue;
        this.tColor = tColor;
        this.tSize = tSize;
        this.dValue = dValue;
        this.dColor = dColor;
        this.dSize = dSize;
        this.url = url;
        this.width = width;
        this.height = height;
        this.size = 9;
    }
    // ------------------------
    // Constructors end

    // Getters start
    // ------------------------
    public String gettValue() {
        return tValue;
    }

    public String gettColor() {
        return tColor;
    }

    public float gettSize() {
        return tSize;
    }

    public String getdValue() {
        return dValue;
    }

    public String getdColor() {
        return dColor;
    }

    public float getdSize() {
        return dSize;
    }

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSize() {
        return size;
    }
    // ------------------------
    // Getters end
}
