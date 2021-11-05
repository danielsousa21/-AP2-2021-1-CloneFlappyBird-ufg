package Projeto_AP2;

import javax.swing.*;
import java.awt.*;

/**
 * Classe responsável por identificar se ouve colisão entre dois objetos do tipo Component.
 */
public class Colisao extends JFrame {

    /**
     * Método responsável por tratar a colisão de dois Component
     */
    public boolean colisao (Component a, Component b) {


        //pega a posição X e Y da tela do Componente a
        int aX = a.getX();
        int aY = a.getY();

        int ladoDireitoA = aX + a.getWidth();
        int ladoEsquerdoA = aX;
        int ladoBaixoA = aY + a.getHeight();
        int ladoCimaA = aY;

        int bX = b.getX();
        int bY = b.getY();
        int ladoDireitoB = bX + b.getWidth();
        int ladoEsquerdoB = bX;
        int ladoBaixoB = bY + b.getHeight();
        int ladoCimaB = bY;

        //=======================//
        boolean colisao = false;
        //=======================//

        boolean cDireita = false;
        boolean cCima = false;
        boolean cBaixo = false;
        boolean cEsquerda = false;

        // verifica se a colisão ocorreu pela direita
        if ( ladoDireitoA >= ladoEsquerdoB) {
            cDireita = true;
        }

        // verifica se a colisão ocorreu por cima
        if ( ladoCimaA <= ladoBaixoB) {
            cCima = true;
        }

        // verifica se a colisão ocorreu por baixo
        if ( ladoBaixoA >= ladoCimaB) {
            cBaixo = true;
        }

        // verifica se a colisão correu pela esquerda
        if ( ladoEsquerdoA <= ladoDireitoB) {
            cEsquerda = true;
        }

        // verifica se ouve colisão
        if (cDireita && cEsquerda && cBaixo && cCima) {
            colisao = true;
        }

        return colisao;

    }


}
