package com.mycompany.contratos.util;

public class TelefoneUtil {
    public static String formatarTelefone(String numero) {
        // Remove caracteres não numéricos
        String cleanNumber = numero.replaceAll("[^\\d]", "");

        // Verifica se o número tem DDD (exatamente 11 dígitos)
        if (cleanNumber.length() == 11) {
            return String.format("(%s) %s-%s",
                    cleanNumber.substring(0, 2), // DDD
                    cleanNumber.substring(2, 7), // Parte do número
                    cleanNumber.substring(7));    // Parte do número
        } 
        // Verifica se o número tem apenas 9 dígitos (sem DDD)
        else if (cleanNumber.length() == 9) {
            return String.format("%s-%s",
                    cleanNumber.substring(0, 5), // Parte do número
                    cleanNumber.substring(5));    // Parte do número
        }
        
        // Retorna o número original se não for um formato válido
        return numero;
    }
}
