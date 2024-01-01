package dev.hyyrokkin.util;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import dev.hyyrokkin.T3D;

public class IsKeyPressed {
    private static volatile boolean wPressed = false;
    private static volatile boolean sPressed = false;
    private static volatile boolean aPressed = false;
    private static volatile boolean dPressed = false;
    private static volatile boolean qPressed = false;
    private static volatile boolean ePressed = false;
    private static volatile boolean spacePressed = false;
    private static volatile boolean controlPressed = false;
    public static boolean isWPressed() {
        synchronized (IsKeyPressed.class) {
            return wPressed;
        }
    }
    public static boolean isSPressed() {
        synchronized (IsKeyPressed.class) {
            return sPressed;
        }
    }
    public static boolean isAPressed() {
        synchronized (IsKeyPressed.class) {
            return aPressed;
        }
    }
    public static boolean isDPressed() {
        synchronized (IsKeyPressed.class) {
            return dPressed;
        }
    }
    public static boolean isQPressed() {
        synchronized (IsKeyPressed.class) {
            return qPressed;
        }
    }
    public static boolean isEPressed() {
        synchronized (IsKeyPressed.class) {
            return ePressed;
        }
    }
    public static boolean isSpacePressed() {
        synchronized (IsKeyPressed.class) {
            return spacePressed;
        }
    }
    public static boolean isControlPressed() {
        synchronized (IsKeyPressed.class) {
            return controlPressed;
        }
    }

    public static void main(String[] args) {
    	new T3D(args);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                synchronized (IsKeyPressed.class) {
                    switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        if (ke.getKeyCode() == KeyEvent.VK_W) {
                            wPressed = true;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_S) {
                            sPressed = true;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_A) {
                            aPressed = true;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_D) {
                            dPressed = true;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_Q) {
                            qPressed = true;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_E) {
                            ePressed = true;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                            spacePressed = true;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_CONTROL) {
                            controlPressed = true;
                        }
                        break;
                        

                    case KeyEvent.KEY_RELEASED:
                    	if (ke.getKeyCode() == KeyEvent.VK_W) {
                            wPressed = false;
                        }
                    	if (ke.getKeyCode() == KeyEvent.VK_S) {
                            sPressed = false;
                        }
                    	if (ke.getKeyCode() == KeyEvent.VK_A) {
                            aPressed = false;
                        }
                    	if (ke.getKeyCode() == KeyEvent.VK_D) {
                            dPressed = false;
                        }
                    	if (ke.getKeyCode() == KeyEvent.VK_Q) {
                            qPressed = false;
                        }
                    	if (ke.getKeyCode() == KeyEvent.VK_E) {
                            ePressed = false;
                        }
                    	if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                            spacePressed = false;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_CONTROL) {
                            controlPressed = false;
                        }
                        break;
                    }
                    return false;
                }
            }
        });
    }
}