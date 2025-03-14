package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class testing {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Лабиринт");
        MazeComponent component = new MazeComponent();
        JLabel scoreLabel = new JLabel("Player 1: 0, Player 2: 0");
        JButton pauseButton = new JButton("Pause");
        frame.setLayout(new BorderLayout());
        frame.add(scoreLabel, BorderLayout.NORTH);
        frame.add(component, BorderLayout.CENTER);
        frame.add(pauseButton, BorderLayout.SOUTH);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        component.startGame();
        pauseButton.addActionListener(e -> component.togglePause());
    }

    static class MazeComponent extends JComponent {
        Random random = new Random();
        private String message;
        private int player1X = 50, player1Y = 30;
        private int player2X = 550, player2Y = 30;
        private int player1Score = 0, player2Score = 0;
        private boolean timeto = random.nextBoolean();
//        private int endx = 290, endy = 180;
        private Rectangle player1Rect = new Rectangle(player1X, player1Y, 20, 20);
        private Rectangle player2Rect = new Rectangle(player2X, player2Y, 20, 20);
//        private Rectangle endRect = new Rectangle(endx, endy, 20, 20);
        private boolean paused = false;
        private SwingWorker<Void, Void> worker;
        private ArrayList<Rectangle> walls = new ArrayList<>();
        private ArrayList<Rectangle> blocks = new ArrayList<>();
        private boolean whatplayer;
        private int vsego = 3;

        public MazeComponent() {
            walls.add(new Rectangle(0, 10, 600, 10));
            walls.add(new Rectangle(0, 60, 250, 10));
            walls.add(new Rectangle(350, 60, 250, 10));
            walls.add(new Rectangle(350, 60, 10, 100));
            walls.add(new Rectangle(250, 60, 10, 100));
            walls.add(new Rectangle(250, 160, 30, 10));
            walls.add(new Rectangle(330, 160, 30, 10));
            walls.add(new Rectangle(290, 60, 30, 80));
            walls.add(new Rectangle(320, 160, 10, 80));
            walls.add(new Rectangle(280, 160, 10, 80));
            walls.add(new Rectangle(320, 240, 80, 10));
            walls.add(new Rectangle(220, 280, 220, 10));
            walls.add(new Rectangle(400, 110, 10, 140));
            walls.add(new Rectangle(400, 110, 500, 10));
            walls.add(new Rectangle(440, 150, 500, 10));
            walls.add(new Rectangle(440, 150, 10, 140));
            walls.add(new Rectangle(180, 240, 110, 10));
            walls.add(new Rectangle(180, 240, 10, 120));
            walls.add(new Rectangle(220, 280, 10, 100));
            Blockis();
        }
        public void Blockis() {
            blocks.add(new Rectangle(290, 180, 20, 20));
            blocks.add(new Rectangle(200, 300, 20, 20));
            blocks.add(new Rectangle(550, 120, 20, 20));
        }
        public void resetmove(int plxcord, int plycord) {
            if (whatplayer) {
                player1X = plxcord;
                player1Y = plycord;
                player1Rect.x = plxcord;
                player1Rect.y = plycord;
                timeto = false;
                whatplayer = false;
            }
            else {
                player2X = plxcord;
                player2Y = plycord;
                player2Rect.x = plxcord;
                player2Rect.y = plycord;
                timeto = true;
                whatplayer = true;
            }
        }
        private void removeCollidedBlock() {
            // Check for player 1 collisions with blocks
            for (int i = 0; i < blocks.size(); i++) {
                Rectangle block = blocks.get(i);
                if (collision(player1Rect, block)) {
                    blocks.remove(i);
                    player1Score++;
                    vsego -= 1;
                    updateScoreLabel();
                    return;
                }
            }

            // Check for player 2 collisions with blocks
            for (int i = 0; i < blocks.size(); i++) {
                Rectangle block = blocks.get(i);
                if (collision(player2Rect, block)) {
                    blocks.remove(i);
                    player2Score++;
                    vsego -= 1;
                    updateScoreLabel();
                    return;
                }
            }
        }

        private void updateScoreLabel() {
            SwingUtilities.invokeLater(() -> {
                Container parent = getParent();
                if (parent != null) {
                    for (Component comp : parent.getComponents()) {
                        if (comp instanceof JLabel) {
                            ((JLabel) comp).setText("Player 1: " + player1Score + ", Player 2: " + player2Score);
                            break;
                        }
                    }
                }
            });
        }

        public void startGame() {

            setFocusable(true);
            InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = getActionMap();
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "moveSPACE");
//            actionMap.put("moveSPACE", new AbstractAction() {};
            worker = new SwingWorker<>() {
                private int time = 0;

                @Override
                protected Void doInBackground() {
                    while (true) {
                            if (0 >= vsego) {
                                if (player1Score > player2Score) {
                                    JOptionPane.showMessageDialog(null, "Player 1 wins!");
                                    System.exit(0);
                                }
                                if (player1Score < player2Score) {
                                    JOptionPane.showMessageDialog(null, "Player 2 wins!");
                                    System.exit(0);
                                }
                            }
                            if (collisionWithBlocks(player1Rect,blocks) || collisionWithBlocks(player2Rect,blocks)) {
                                removeCollidedBlock();
                            }
                            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "moveW");
                            actionMap.put("moveW", new AbstractAction() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.out.println("W");
                                    if (!paused && timeto ) {
                                    player1Rect.y -= 10;
                                    if (!collisionWithWalls(player1Rect, walls)) {
                                        player1Y -= 10;
                                    } else {
                                        whatplayer = true;
                                        resetmove(50, 30);
                                    }
                                }}
                            });
                            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "moveS");
                            actionMap.put("moveS", new AbstractAction() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.out.println("S");
                                    if (!paused && timeto) {
                                    player1Rect.y += 10;
                                    if (!collisionWithWalls(player1Rect, walls)) {
                                        player1Y += 10;
                                    } else {
                                        whatplayer = true;
                                        resetmove(50, 30);
                                    }
                                }}
                            });
                        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "moveD");
                        actionMap.put("moveD", new AbstractAction() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("D");
                                if (!paused && timeto) {
                                    player1Rect.x += 10;
                                    if (!collisionWithWalls(player1Rect, walls)) {
                                        player1X += 10;
                                    } else {
                                        whatplayer = true;
                                        resetmove(50, 30);
                                    }
                                }
                            }
                        });

                        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "moveA");
                        actionMap.put("moveA", new AbstractAction() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("A");
                                if (!paused && timeto) {
                                    player1Rect.x -= 10;
                                    if (!collisionWithWalls(player1Rect, walls)) {
                                        player1X -= 10;
                                    } else {
                                        whatplayer = true;
                                        resetmove(50, 30);
                                    }
                                }
                            }
                        });
                        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0), "moveI");
                        actionMap.put("moveI", new AbstractAction() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("I");
                                if (!paused && !timeto) {
                                    player2Rect.y -= 10;
                                    if (!collisionWithWalls(player2Rect, walls)) {
                                        player2Y -= 10;
                                    } else {
                                        whatplayer = false;
                                        resetmove(550, 30);
                                    }
                                }
                            }
                        });

                        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_K, 0), "moveK");
                        actionMap.put("moveK", new AbstractAction() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("K");
                                if (!paused && !timeto) {
                                    player2Rect.y += 10;
                                    if (!collisionWithWalls(player2Rect, walls)) {
                                        player2Y += 10;
                                    } else {
                                        whatplayer = false;
                                        resetmove(550, 30);
                                    }
                                }
                            }
                        });

                        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_L, 0), "moveL");
                        actionMap.put("moveL", new AbstractAction() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("L");
                                if (!paused && !timeto) {
                                    player2Rect.x += 10;
                                    if (!collisionWithWalls(player2Rect, walls)) {
                                        player2X += 10;
                                    } else {
                                        whatplayer = false;
                                        resetmove(550, 30);
                                    }
                                }
                            }
                        });

                        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_J, 0), "moveJ");
                        actionMap.put("moveJ", new AbstractAction() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("J");
                                if (!paused && !timeto) {
                                    player2Rect.x -= 10;
                                    if (!collisionWithWalls(player2Rect, walls)) {
                                        player2X -= 10;
                                    } else {
                                        whatplayer = false;
                                        resetmove(550, 30);
                                    }
                                }
                            }
                        });
                            SwingUtilities.invokeLater(() -> repaint());

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            worker.execute();
        }

        public void togglePause() {
            paused = !paused;
        }
        private boolean collision(Rectangle r1, Rectangle r2) {
            return r1.intersects(r2);
        }
        private boolean collisionWithWalls(Rectangle player, ArrayList<Rectangle> walls) {
            for (Rectangle wall : walls) {
                if (collision(player, wall)) {
                    return true;
                }
            }
            return false;
        }
        private boolean collisionWithBlocks(Rectangle player, ArrayList<Rectangle> blocks) {
            for (Rectangle block : blocks) {
                if (collision(player, block)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (timeto) {
                message = "time to player 1";
            }
            else { message = "time to player 2"; }
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLACK);
            g2d.drawString(message, 10, 200);
            g2d.setColor(Color.GRAY);
            for (Rectangle wall : walls) {
                g2d.fillRect(wall.x, wall.y, wall.width, wall.height);
            }
            g2d.setColor(Color.CYAN);
            for (Rectangle block : blocks) {
                g2d.fillRect(block.x, block.y, block.width, block.height);
            }
//            g2d.fillRect(endRect.x, endRect.y, endRect.width, endRect.height);
            g2d.setColor(Color.BLUE);
            g2d.fillRect(player1Rect.x, player1Rect.y, player1Rect.width, player1Rect.height);
            g2d.setColor(Color.RED);
            g2d.fillRect(player2Rect.x, player2Rect.y, player2Rect.width, player2Rect.height);

        }
    }
}