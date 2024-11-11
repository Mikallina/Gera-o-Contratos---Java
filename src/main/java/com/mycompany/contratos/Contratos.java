/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.contratos;

/**
 *
 * @author mi_bo
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.mycompany.contratos.telas.ConsultaCliente;

public class Contratos {
    public static void main(String[] args) {
        // Cria uma nova janela (JFrame)
        JFrame frame = new JFrame("Consulta de Clientes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha o aplicativo ao fechar a janela
        frame.setSize(1200, 600); // Define o tamanho da janela

        // Cria um painel (JPanel) para adicionar à janela
        JPanel panel = new ConsultaCliente(); // Supondo que ConsultaCliente estenda JPanel

        // Adiciona o painel à janela
        frame.add(panel);

        // Torna a janela visível
        frame.setVisible(true);
    }
}
