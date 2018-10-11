package org.rdtif.zaxslackbot.userinterface;

import java.awt.*;
import java.util.Vector;

import com.zaxsoft.zax.zmachine.ZUserInterface;

public class SlackZUserInterface implements ZUserInterface {
    private final SlackTextScreen textScreen;

    public SlackZUserInterface(SlackTextScreen textScreen) {
        this.textScreen = textScreen;
    }

    @Override
    public void fatal(String message) {

    }

    @Override
    public void initialize(int version) {
        textScreen.initialize();
    }

    @Override
    public void setTerminatingCharacters(Vector characters) {

    }

    @Override
    public boolean hasStatusLine() {
        return false;
    }

    @Override
    public boolean hasUpperWindow() {
        return false;
    }

    @Override
    public boolean defaultFontProportional() {
        return false;
    }

    @Override
    public boolean hasColors() {
        return false;
    }

    @Override
    public boolean hasBoldface() {
        return false;
    }

    @Override
    public boolean hasItalic() {
        return false;
    }

    @Override
    public boolean hasFixedWidth() {
        return false;
    }

    @Override
    public boolean hasTimedInput() {
        return false;
    }

    @Override
    public Dimension getScreenCharacters() {
        return null;
    }

    @Override
    public Dimension getScreenUnits() {
        return null;
    }

    @Override
    public Dimension getFontSize() {
        return null;
    }

    @Override
    public Dimension getWindowSize(int window) {
        return null;
    }

    @Override
    public int getDefaultForeground() {
        return 0;
    }

    @Override
    public int getDefaultBackground() {
        return 0;
    }

    @Override
    public Point getCursorPosition() {
        return null;
    }

    @Override
    public void showStatusBar(String s, int a, int b, boolean flag) {

    }

    @Override
    public void splitScreen(int lines) {

    }

    @Override
    public void setCurrentWindow(int window) {

    }

    @Override
    public void setCursorPosition(int x, int y) {

    }

    @Override
    public void setColor(int foreground, int background) {

    }

    @Override
    public void setTextStyle(int style) {

    }

    @Override
    public void setFont(int font) {

    }

    @Override
    public int readLine(StringBuffer buffer, int time) {
        return 0;
    }

    @Override
    public int readChar(int time) {
        return 0;
    }

    @Override
    public void showString(String string) {

    }

    @Override
    public void scrollWindow(int lines) {

    }

    @Override
    public void eraseLine(int size) {

    }

    @Override
    public void eraseWindow(int window) {

    }

    @Override
    public String getFilename(String title, String suggested, boolean saveFlag) {
        return null;
    }

    @Override
    public void quit() {

    }

    @Override
    public void restart() {

    }
}
