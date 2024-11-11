package com.mycompany.contratos.util;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import javax.swing.JTextField;

public class CepUtil {
    public static void buscarEnderecoPorCep(String cep, JTextField endereco, JTextField bairro, JTextField cidade, JTextField uf, JFrame frame) {
        try {
            String urlString = "https://viacep.com.br/ws/" + cep + "/json/";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Analisar o JSON retornado
            String jsonResponse = response.toString();
            JSONObject json = new JSONObject(jsonResponse);

            // Verificar se o CEP retornou um erro
            if (!json.has("erro")) {
                // Preencher os campos de endereço
                endereco.setText(json.getString("logradouro"));
                bairro.setText(json.getString("bairro"));
                cidade.setText(json.getString("localidade"));
                uf.setText(json.getString("uf"));
            } else {
                JOptionPane.showMessageDialog(frame, "CEP não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erro ao buscar o endereço. Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static String formatarCep(String cep) {
        return cep.replaceAll("-", "").trim(); // Remove traços e espaços
    }
}