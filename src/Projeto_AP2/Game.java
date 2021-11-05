package Projeto_AP2;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.URL;
import java.util.Random;


/**
 * Núcleo principal: A Classe Game sera responsável por controlar a maioria dos métodos, outras classes e
 * a transição entre "telas".
 */
public class Game extends Main {

    //variáveis do tipo booleans globais
    boolean menuPrincipal = false;
    boolean AreaDoJogo = true;
    boolean começar = false;

    int pontos = 0;
    boolean pontuar = true;

    Container GameEmAndamento = new Container();
    Colisao colisao = new Colisao();

    // variáveis globais width e heigth do player
    private int playerWidth = 95;
    private int payerHeigth = 85 + 10;

    // vari globais width e heigth do terreno
    private int floorWidth = 700;
    private int floorHeigth = 112;
    private int floorY = 600;

    private boolean pressionado = false;


    //======================================== Area do Jogo ========================================

    //Inicializando a label do jogador
    JLabel player = new JLabel(new ImageIcon(getClass().getResource("resources\\passaro.gif")));

    // Inicializando a label do terreno
    JLabel floor1 = new JLabel(new ImageIcon(getClass().getResource("resources\\terreno1.png")));
    JLabel floor2 = new JLabel(new ImageIcon(getClass().getResource("resources\\terreno2.png")));

    // Inicializando a label dos cones
    JLabel cano1 = new JLabel(new ImageIcon(getClass().getResource("resources\\cano1.png")));
    JLabel cano2 = new JLabel(new ImageIcon(getClass().getResource("resources\\cano2.png")));

    JLabel espaco = new JLabel();

    // inicializando a label do background
    JLabel background1 = new JLabel(new ImageIcon(getClass().getResource("resources\\fundo.png")));
    JLabel background2 = new JLabel(new ImageIcon(getClass().getResource("resources\\fundo.png")));
    JLabel tutorial = new JLabel(new ImageIcon(getClass().getResource("resources\\tutor.png")));

    // inicializando a label do score
    JLabel score = new JLabel("0");
    JLabel score2 = new JLabel("0");
    JLabel contador = new JLabel();

    // Botão para reiniciar o jogo
    JButton reiniciar = new JButton(new ImageIcon(getClass().getResource("resources\\reload.png")));




    //======================================== Area do Jogo ========================================



    /**
     * Método construtor: Este Método sera responsável por controlar a interfase gráfica do programa
     */
    public Game() {

        // fonte que sera utilizada no jogo

        URL caminho = getClass().getResource("font.ttf");

        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(caminho.getPath())).deriveFont(Font.PLAIN, 80);
            score.setFont(font);

            Font font2 = Font.createFont(Font.TRUETYPE_FONT, new File(caminho.getPath())).deriveFont(Font.PLAIN, 90);
            score2.setFont(font2);


        }catch (Exception erro) {
            System.out.println("Fonte não encontrada");
        }

        //gerando a tela do jogo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 750);
        setVisible(true);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setFocusable(true);



        //======================================== Area do Jogo ========================================

        // Métodos do player
        addMovimento();

        // inclui o player na tela do jogo
        player.setBounds(140, 340, playerWidth, payerHeigth);

        // inclui o terreno na tela do jogo
        floor1.setBounds(0, floorY, floorWidth, floorHeigth );
        floor2.setBounds(700, floorY, floorWidth, floorHeigth );

        // inclui o cano na area do jogo
        espaco.setBounds(900, 210, 164, 400);
        contador.setBounds(espaco.getX() + 164, 210, 164, 400);

        cano2.setBounds(espaco.getX(), espaco.getY() - 788, 164, 788);
        cano1.setBounds(espaco.getX(), espaco.getY() + 300, 164, 788);

        // inclui o background do jogo
        background1.setBounds(0, -400, 864, 1152);
        background2.setBounds(860, -400, 864, 1152);

        // incluindo o score do jogo
        score.setBounds(300, 0, 100, 100);
        score2.setBounds(300, 0, 100, 100);
        score.setForeground(Color.WHITE);
        score2.setForeground(Color.BLACK);

        // tutorial
        tutorial.setBounds(300, 200, 152, 284);

        // botão reiniciar
        reiniciar.setBounds(230, 300, 232, 128);
        reiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game reiniciar = new Game();
            }
        });
        reiniciar.setVisible(false);



        MovimentoDoCenario terreno = new MovimentoDoCenario();
        Relogio relogio = new Relogio();

        relogio.start();

        terreno.start();

        GameEmAndamento.add(reiniciar);
        GameEmAndamento.add(score);
        GameEmAndamento.add(score2);
        GameEmAndamento.add(tutorial);
        GameEmAndamento.add(player);
        GameEmAndamento.setLayout(null);
        GameEmAndamento.setFocusable(true);
        GameEmAndamento.setSize(700, 750);
        GameEmAndamento.setVisible(true);
        GameEmAndamento.add(floor1);
        GameEmAndamento.add(floor2);
        GameEmAndamento.add(cano2);
        GameEmAndamento.add(cano1);
        GameEmAndamento.add(contador);
        GameEmAndamento.add(background1);
        GameEmAndamento.add(background2);
        add(GameEmAndamento);

        //======================================== Area do Jogo ========================================


    }


    // adiciona o movimento ao personagem
    public void addMovimento() {

        addKeyListener(new KeyListener() {



            @Override
            public void keyTyped (KeyEvent e){

            }

            // ao pressionar a tecla SPACE gera um evento
            @Override
            public void keyPressed (KeyEvent e){

                if (AreaDoJogo && colisao.colisao(player, floor1) == false && colisao.colisao(player, floor2) == false && colisao.colisao(player, cano1) == false && colisao.colisao(player, cano2) == false ) {

                    if (e.getKeyCode() == 32) {

                        pressionado = true;
                        Subir movimento = new Subir();
                        tutorial.setVisible(false);
                        começar = true;
                        movimento.start();


                    }
                }
            }

            @Override
            public void keyReleased (KeyEvent e){

                if (AreaDoJogo) {

                    pressionado = false;
                    Cair movimento = new Cair();
                    movimento.start();
                }

            }


        });

    }

    /**
     * Thread responsável por realizar o movimento de subida do player
     */
    public class Subir extends Thread {

        int pixel =  15;

        public void run() {



                    for (int time = 10; time >= 1; time--) {

                        try {
                            Thread.sleep(10);
                        } catch (Exception erro) {
                        }

                        player.setBounds(player.getX(), player.getY() - pixel, playerWidth, payerHeigth);

                    }


        }
    }

    /**
     * Thread responsável por simular a gravidade
     */
    public class Cair extends Thread {

        int pixel = 10;

        public void run() {

            while (pressionado == false && colisao.colisao(player, floor1) == false && colisao.colisao(player, floor2) == false) {

                try {Thread.sleep(10);} catch(Exception erro) {}

                player.setBounds(player.getX(), player.getY() + pixel, playerWidth, payerHeigth);

            }
        }

    }

    /**
     * Thread responsável pelo movimento do cenário
     */
    public class MovimentoDoCenario extends Thread {

        int pixelX = 5;
        int pixelY = 180;

        int posisao1;
        int posisao2;
        int posisao3;
        int posisao4;
        int posisao5;


        File file  = new File("src\\Projeto_AP2\\resources\\coin.wav");

        Random random = new Random();

        int numero;

        public void run() {




            while(AreaDoJogo && colisao.colisao(player, floor1) == false && colisao.colisao(player, floor2) == false && colisao.colisao(player, cano1) == false && colisao.colisao(player, cano2) == false) {



                try { sleep(20);} catch(Exception erro) {}

                //======================================== Terrenos ========================================



                posisao1 = (floor1.getX() <= -690) ? 700 : floor1.getX() - pixelX;
                posisao2 = (floor2.getX() <= -690) ? 700 : floor2.getX() - pixelX;

                floor1.setBounds(posisao1, floorY, floorWidth, floorHeigth);
                floor2.setBounds(posisao2, floorY, floorWidth, floorHeigth);


                //======================================== Terrenos ========================================

                //======================================== Canos ========================================

                if (começar) {


                    if (espaco.getX() == -690) {
                        numero = random.nextInt(100);
                    }

                    posisao3 = (espaco.getX() <= -690) ? 710 : espaco.getX() - pixelX;

                    espaco.setBounds(posisao3, numero + 180, 164, 500);
                    contador.setBounds(posisao3 + 164, numero + 180, 164, 350);

                    cano2.setBounds(espaco.getX(), espaco.getY() - 788, 164, 788);
                    cano1.setBounds(espaco.getX(), espaco.getY() + 300, 164, 788);

                }


                //======================================== Canos ========================================

                //======================================== fundo ========================================

                posisao4 = (background1.getX() <= -864) ? 700 : background1.getX() - pixelX;
                posisao5 = (background2.getX() <= -864) ? 700 : background2.getX() - pixelX;

                background1.setBounds(posisao4, -400, 864, 1152);
                background2.setBounds(posisao5, -400, 864, 1152);

                //======================================== Canos ========================================

                if ( colisao.colisao(player, contador) && pontuar == true) {

                    try {
                        AudioInputStream audio = AudioSystem.getAudioInputStream(new File(file.getAbsolutePath()).getAbsoluteFile());
                        Clip clip = AudioSystem.getClip();
                        clip.open(audio);
                        clip.start();
                    } catch (Exception erro) {

                    }

                    pontos++;
                    score.setText(String.valueOf(pontos));
                    score2.setText(String.valueOf(pontos));
                    pontuar = false;
                }

                if (AreaDoJogo && colisao.colisao(player, floor1) == true || colisao.colisao(player, floor2) == true || colisao.colisao(player, cano1) == true || colisao.colisao(player, cano2) == true) {

                    reiniciar.setVisible(true);

                }



            }

        }


    }

    /**
     * Acrescenta pontos quando o player colide com o contador
     */
    public class Relogio extends Thread {



        public void run() {  while (AreaDoJogo) { try { sleep(10000);  pontuar = (pontuar == false) ? true : true; } catch (Exception erro) {  } } }
    }




}
