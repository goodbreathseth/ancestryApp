package com.example.goodb.bighugeproject.dataHolders;

import android.graphics.Color;

public class SettingsData {

    // Member data to hold information for the settings
    boolean showLifeStoryLines, showFamilyTreeLines, showSpouseLines;
    private int mapType;

    int lifeStoryLineColor, familyTreeLineColor, spouseLineColor;

    public SettingsData() {
        showLifeStoryLines = true;
        showFamilyTreeLines = true;
        showSpouseLines = true;
        lifeStoryLineColor = 1;
        familyTreeLineColor = 2;
        spouseLineColor = 0;
        mapType = 0;
    }

    public void updateMapType(int i) {
        mapType = i;
    }

    public int getMapType() {
        return mapType;
    }

    public boolean isShowLifeStoryLines() {
        return showLifeStoryLines;
    }

    public void setShowLifeStoryLines(boolean showLifeStoryLines) {
        this.showLifeStoryLines = showLifeStoryLines;
    }

    public boolean isShowFamilyTreeLines() {
        return showFamilyTreeLines;
    }

    public void setShowFamilyTreeLines(boolean showFamilyTreeLines) {
        this.showFamilyTreeLines = showFamilyTreeLines;
    }

    public boolean isShowSpouseLines() {
        return showSpouseLines;
    }

    public void setShowSpouseLines(boolean showSpouseLines) {
        this.showSpouseLines = showSpouseLines;
    }

    public int getLifeStoryLineColor() {
        return lifeStoryLineColor;
    }

    public void setLifeStoryLineColor(int lifeStoryLineColor) {
        this.lifeStoryLineColor = lifeStoryLineColor;
    }

    public int getFamilyTreeLineColor() {
        return familyTreeLineColor;
    }

    public void setFamilyTreeLineColor(int familyTreeLineColor) {
        this.familyTreeLineColor = familyTreeLineColor;
    }

    public int getSpouseLineColor() {
        return spouseLineColor;
    }

    public void setSpouseLineColor(int spouseLineColor) {
        this.spouseLineColor = spouseLineColor;
    }

}
