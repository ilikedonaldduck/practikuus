package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Random;
//import java.util.Timer;

public class tennis {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tennis");
        ball ball = new ball();
        frame.setSize(600, 400);
        JLabel label = new JLabel("Player1: " + ball.Player1s + " | Player2: " + ball.Player2s);
        JButton button = new JButton("restart");
        button.setBounds(250,300,100,50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.add(button);
        frame.add(label);
        frame.add(ball);

        label.setBounds(10, 10, 200, 30);
        ball.setBounds(0, 0, 600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        ball.move();
        Timer timer = new Timer(10, (ActionEvent e) -> {
            label.setText("Player1: " + ball.Player1s + " | Player2: " + ball.Player2s);
        });
        button.addActionListener(e -> {
            ball.restart();
        });
        timer.start();
    }

    static class ball extends JComponent {
        Random random = new Random();
        private int Player1s = 0;
        private int Player2s = 0;
        private int x1 = 50;
        private int x2 = 500;
        private int y1 = 200;
        private int y2 = 200;
        private int ovalx = 280;
        private int ovaly = 200;
        private int maxY = 0;
        private int minY = 305;
        private int maxX = 570;
        private int minX = 0;
        private Rectangle player1Rect = new Rectangle(x1, y1, 45, 60);
        private Rectangle player2Rect = new Rectangle(x2 - 15, y2, 30, 60);
        private Rectangle squareRect = new Rectangle(ovalx, ovaly, 20, 20);
        private int speed = 9;
        private boolean doit;
        private int speedforoval = 5;
        private boolean moveup;
        private boolean moveforward;
        private float hue = 0.0f;
        private boolean pause = false;
        private Timer timer;
        public void move() {
            setFocusable(true);
            InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = getActionMap();
            moveforward = random.nextBoolean();
            moveup = random.nextBoolean();
            System.out.println(moveforward);
            System.out.println("check: " + moveup);
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "moveSPACE");
            actionMap.put("moveSPACE", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!pause){timer.stop(); pause = !pause;}
                    else if (pause) {timer.start(); pause = !pause;}
                }
            });
            if (!pause) {
                inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_K, 0), "moveK");
                actionMap.put("moveK", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        player2Rect.setLocation(x2, y2);
                        if (player2Rect.y < minY) {
                            y2 += speed;
                        }
                        else if (player2Rect.y >= minY) {
                            System.out.println(player2Rect.y + " " + minY);
                        }
                    }
                });
                inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0), "moveI");
                actionMap.put("moveI", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        player2Rect.setLocation(x2, y2);
                        if (player2Rect.y > maxY) {
                            y2 -= speed;
                        }
                        else if (player2Rect.y <= maxY) {
                            System.out.println(player2Rect.y + " " + maxY);
                        }
                    }
                });
                inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "moveW");
                actionMap.put("moveW", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        player1Rect.setLocation(x1, y1);
                        if (player1Rect.y > maxY) {
                            y1 -= speed;
                        }
                        else {
                            System.out.println(player1Rect.y + " " + maxY);
                        }
                    }
                });
                inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "moveS");
                actionMap.put("moveS", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        player1Rect.setLocation(x1, y1);
                        if (player1Rect.y < minY) {
                            y1 += speed;
                        }
                        else {
                            System.out.println(player1Rect.y + " " + minY);
                        }
                    }
                });
                timer = new Timer(20, e -> {

                    if (moveforward == true) {ovalx += speedforoval; squareRect.setLocation(ovalx, ovaly);}
                    else if (moveforward == false) {ovalx -= speedforoval; squareRect.setLocation(ovalx, ovaly);}
                    if (moveup == true) {ovaly -= speedforoval; squareRect.setLocation(ovalx, ovaly);
//                    System.out.println("check: " + moveup);
                    }
                    else if (moveup == false) {ovaly += speedforoval; squareRect.setLocation(ovalx,ovaly);
//                    System.out.println("check: " + moveup);
                    }

                    if (collision(squareRect,player2Rect)) {
                        if (moveup) {
                            doit = random.nextBoolean();
                            if (!doit) {moveup = true;}
                            else {moveup = false;}}
                        else if (!moveup) {
                            doit = random.nextBoolean();
                            if (!doit) {moveup = true;}
                            else {moveup = false;}}
                        if (speedforoval < 20) {speedforoval += 1;}
                        hue = random.nextFloat(1) * 360;
                        moveforward = false;
                    }
                    if (collision(squareRect,player1Rect)) {
                        if (moveup) {
                            doit = random.nextBoolean();
                            if (!doit) {moveup = true;}
                            else {moveup = false;}}
                        else if (!moveup) {
                            doit = random.nextBoolean();
                            if (!doit) {moveup = true;}
                            else {moveup = false;}}
                        if (speedforoval < 20) {speedforoval += 1;}
                        hue = random.nextFloat(1) * 360;
                        moveforward = true;
                    }
                    if (ovaly > minY){
                        moveup = true;
                        System.out.println(ovaly +" "+ squareRect.y +" "+ maxY);
                    }

                    else if (ovaly < maxY) {
                        moveup = false;
                        System.out.println(ovaly +" "+ squareRect.y +" "+ maxY);}
                    if (squareRect.x > maxX) {
                        System.out.println("player 1 win");
                        Player1s++;
                        stop(timer);
                    }
                    else if (squareRect.x < minX) {
                        System.out.println("player 2 win");
                        Player2s++;
                        stop(timer);
                    }
                    repaint();
                });
            }
            timer.start();


        }
        public void stop(Timer timer) {
            timer.stop();
            x1 = 50;
            x2 = 500;
            y1 = 200;
            y2 = 200;
            ovalx = 280;
            ovaly = 200;
            maxY = 0;
            minY = 305;
            maxX = 570;
            minX = 0;
            player1Rect = new Rectangle(x1, y1, 45, 60);
            player2Rect = new Rectangle(x2 - 15, y2, 30, 60);
            squareRect = new Rectangle(ovalx, ovaly, 20, 20);
            speed = 9;
            speedforoval = 5;
            hue = 0.0f;
            timer.start();
        }
        public void restart() {
            timer.stop();
            x1 = 50;
            x2 = 500;
            y1 = 200;
            y2 = 200;
            ovalx = 280;
            ovaly = 200;
            maxY = 0;
            minY = 305;
            maxX = 570;
            minX = 0;
            player1Rect = new Rectangle(x1, y1, 45, 60);
            player2Rect = new Rectangle(x2 - 15, y2, 30, 60);
            squareRect = new Rectangle(ovalx, ovaly, 20, 20);
            speed = 9;
            speedforoval = 5;
            hue = 0.0f;
            Player1s = 0;
            Player2s = 0;
            timer.start();
        }
        private boolean collision(Rectangle r1, Rectangle r2) {
            return r1.intersects(r2);
        }
        @Override
        public void paint(Graphics g) {

            super.paint(g);
            Color color = Color.getHSBColor(hue, 1.0f, 1.0f);
            g.setColor(color);
            g.fillOval(squareRect.x, squareRect.y, 20, 20);

            g.setColor(Color.BLACK);
            g.fillRect(x1,y1, 30, 60);
            g.fillRect(x2,y2, 30, 60);
        }
    }
}
