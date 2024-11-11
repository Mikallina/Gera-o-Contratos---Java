package com.mycompany.contratos.telas;

import java.awt.EventQueue;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mycompany.contratos.util.CepUtil;
import com.mycompany.contratos.util.TelefoneUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import static com.mycompany.contratos.util.ExcelUtil.getCellValue;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ContratoPrinter {

    private JFrame frame;
    private JTextField cpf;
    private JTextField data;
    private JTextField nome;
    private JTextField email;
    private JTextField cpfRepetido;
    private JTextField cep;
    private JTextField endereco;
    private JTextField numero;
    private JTextField bairro;
    private JTextField cidade;
    private JTextField uf;
    private JTextField telefone;
    private JLabel labelData;
    private JLabel labelCidade;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ContratoPrinter window = new ContratoPrinter();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ContratoPrinter() {
        initialize();
        frame.setVisible(true);
    }

    public void carregarDadosContrato(String cnpj) {
        String caminho = "C:/Users/mi_bo/OneDrive/Documentos/NetBeansProjects/Contratos/";
        String wbDadosPath = caminho + "Dados.xlsx";

        try (Workbook wbDados = new XSSFWorkbook(new FileInputStream(wbDadosPath))) {
            Sheet wsDados = wbDados.getSheetAt(0);

            for (int i = 1; i <= wsDados.getLastRowNum(); i++) {
                Row rowDados = wsDados.getRow(i);
                if (rowDados == null || getCellValue(rowDados, 0).isEmpty()) {
                    continue; // Pula linhas vazias
                }

                String clienteCNPJ = getCellValue(rowDados, 0).trim();

                // Verifique se o CNPJ corresponde
                if (clienteCNPJ.equals(cnpj.trim())) {
                    // Acesse os dados
                    cpf.setText(getCellValue(rowDados, 0));
                    nome.setText(getCellValue(rowDados, 1));
                    bairro.setText(getCellValue(rowDados, 2));
                    cidade.setText(getCellValue(rowDados, 3));
                    uf.setText(getCellValue(rowDados, 4));
                    telefone.setText(getCellValue(rowDados, 5));

                    // Formate o telefone
                    String telefoneSemFormato = telefone.getText();
                    String telefoneFormatado = TelefoneUtil.formatarTelefone(telefoneSemFormato);
                    telefone.setText(telefoneFormatado);

                    email.setText(getCellValue(rowDados, 6));
                    cep.setText(getCellValue(rowDados, 7));
                    endereco.setText(getCellValue(rowDados, 9));
                    String dataOriginal = getCellValue(rowDados, 10); // Obtém o valor da data
                    data.setText(getCellValue(rowDados, 8));
                    if (dataOriginal != null && !dataOriginal.isEmpty()) {
                        LocalDate dataLocal = LocalDate.parse(dataOriginal, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        LocalDate dataFutura = dataLocal.plusDays(15);
                        labelData.setText(dataFutura.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))); // Exibe a data formatada
                    } else {
                        labelData.setText("Data não disponível");
                    }

                    cpfRepetido.setText(getCellValue(rowDados, 0)); // Repete o CPF
                    labelCidade.setText(getCellValue(rowDados, 3)); // Atualiza a label da cidade

                    return; // Termina a execução se o cliente for encontrado
                }
            }

            JOptionPane.showMessageDialog(nome, this, "Cliente não encontrado.", 0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(203, 218, 249));
        frame.setBounds(100, 100, 722, 830);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel panel1 = new JPanel();
        panel1.setBounds(10, 90, 688, 76);
        frame.getContentPane().add(panel1);
        panel1.setLayout(null);

        cpf = new JTextField();
        cpf.setEditable(false);
        cpf.setBounds(10, 42, 96, 19);
        panel1.add(cpf);
        cpf.setColumns(10);

        data = new JTextField();
        data.setBounds(557, 42, 96, 19);
        panel1.add(data);
        data.setColumns(10);

        JLabel lblNewLabel = new JLabel("CPF");
        lblNewLabel.setBounds(10, 19, 45, 13);
        panel1.add(lblNewLabel);

        JLabel lblData = new JLabel("Data");
        lblData.setBounds(557, 19, 45, 13);
        panel1.add(lblData);

        JPanel panel2 = new JPanel();
        panel2.setBounds(10, 198, 688, 164);
        frame.getContentPane().add(panel2);
        panel2.setLayout(null);

        nome = new JTextField();
        nome.setBounds(10, 39, 307, 19);
        panel2.add(nome);
        nome.setColumns(10);

        email = new JTextField();
        email.setColumns(10);
        email.setBounds(371, 39, 307, 19);
        panel2.add(email);

        cpfRepetido = new JTextField();
        cpfRepetido.setEditable(false);
        cpfRepetido.setColumns(10);
        cpfRepetido.setBounds(10, 95, 189, 19);
        panel2.add(cpfRepetido);

        JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("Nome");
        lblNewLabel_1_1_1_1_1_1.setBounds(10, 16, 45, 13);
        panel2.add(lblNewLabel_1_1_1_1_1_1);

        JLabel lblNewLabel_1_1_1_1_1_1_1 = new JLabel("Email");
        lblNewLabel_1_1_1_1_1_1_1.setBounds(371, 16, 45, 13);
        panel2.add(lblNewLabel_1_1_1_1_1_1_1);

        JLabel lblNewLabel_1_1_1_1_1_1_1_1 = new JLabel("CPF / CNPJ");
        lblNewLabel_1_1_1_1_1_1_1_1.setBounds(10, 72, 86, 13);
        panel2.add(lblNewLabel_1_1_1_1_1_1_1_1);

        JPanel panel3 = new JPanel();
        panel3.setBounds(10, 391, 688, 195);
        frame.getContentPane().add(panel3);
        panel3.setLayout(null);

        cep = new JTextField();
        cep.setBounds(10, 47, 122, 19);
        panel3.add(cep);
        cep.setColumns(10);

        endereco = new JTextField();
        endereco.setColumns(10);
        endereco.setBounds(245, 47, 300, 19);
        panel3.add(endereco);

        numero = new JTextField();
        numero.setColumns(10);
        numero.setBounds(582, 47, 96, 19);
        panel3.add(numero);

        JLabel lblNewLabel_1 = new JLabel("CEP");
        lblNewLabel_1.setBounds(10, 24, 45, 13);
        panel3.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Endereco");
        lblNewLabel_1_1.setBounds(245, 24, 82, 13);
        panel3.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_1_1 = new JLabel("Numero");
        lblNewLabel_1_1_1.setBounds(582, 24, 45, 13);
        panel3.add(lblNewLabel_1_1_1);

        bairro = new JTextField();
        bairro.setColumns(10);
        bairro.setBounds(10, 105, 122, 19);
        panel3.add(bairro);

        JLabel lblNewLabel_1_1_1_1 = new JLabel("Cidade");
        lblNewLabel_1_1_1_1.setBounds(245, 82, 45, 13);
        panel3.add(lblNewLabel_1_1_1_1);

        cidade = new JTextField();
        cidade.setColumns(10);
        cidade.setBounds(244, 105, 301, 19);
        panel3.add(cidade);

        JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Bairro");
        lblNewLabel_1_1_1_1_1.setBounds(10, 82, 45, 13);
        panel3.add(lblNewLabel_1_1_1_1_1);

        uf = new JTextField();
        uf.setColumns(10);
        uf.setBounds(582, 105, 52, 19);
        panel3.add(uf);

        JLabel lblNewLabel_1_1_1_1_2 = new JLabel("Estado");
        lblNewLabel_1_1_1_1_2.setBounds(582, 82, 45, 13);
        panel3.add(lblNewLabel_1_1_1_1_2);

        telefone = new JTextField();
        telefone.setColumns(10);
        telefone.setBounds(10, 166, 122, 19);
        panel3.add(telefone);

        JLabel lblNewLabel_1_1_1_1_1_2 = new JLabel("Telefone");
        lblNewLabel_1_1_1_1_1_2.setBounds(10, 143, 82, 13);
        panel3.add(lblNewLabel_1_1_1_1_1_2);

        JButton pesquisar = new JButton("Pesquisar");
            	pesquisar.addActionListener(new ActionListener() {
        	    public void actionPerformed(ActionEvent e) {
        	        String cepDigitado = CepUtil.formatarCep(cep.getText());
        	        if (!cepDigitado.isEmpty()) {
        	            CepUtil.buscarEnderecoPorCep(cepDigitado, endereco, bairro, cidade, uf, frame);
        	        } else {
        	            JOptionPane.showMessageDialog(frame, "Por favor, insira um CEP válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        	        }
        	    }
        	});

          

        pesquisar.setBounds(142, 46, 93, 21);
        panel3.add(pesquisar);

        // Label para exibir a data
        labelData = new JLabel("Data");
        labelData.setForeground(new Color(192, 192, 192));
        labelData.setBounds(593, 74, 105, 13);
        frame.getContentPane().add(labelData);
        
        labelCidade = new JLabel("Cidade");
        labelCidade.setForeground(Color.LIGHT_GRAY);
        labelCidade.setBounds(449, 74, 105, 13);
        frame.getContentPane().add(labelCidade);
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Dados a serem salvos
                String novoEndereco = endereco.getText();
                String novoBairro = bairro.getText();
                String novoCep = cep.getText();
                String novaCidade = cidade.getText();
                String novoUf = uf.getText();
                
                // Caminho para a planilha Excel
                String caminho = "C:/Users/mi_bo/OneDrive/Documentos/NetBeansProjects/Contratos/Dados.xlsx";

                try (Workbook workbook = new XSSFWorkbook(new FileInputStream(caminho))) {
                    Sheet sheet = workbook.getSheetAt(0);
                    Row row = sheet.getRow(2); // Ajuste a linha conforme necessário
                    
                    if (row == null) {
                        row = sheet.createRow(2); // Cria uma nova linha se não existir
                    }

                    // Atualiza ou cria as células necessárias
                    Cell cellEndereco = row.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    Cell cellBairro = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    Cell cellCep = row.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    Cell cellCidade = row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    Cell cellUf = row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    
                    cellEndereco.setCellValue(novoEndereco);
                    cellBairro.setCellValue(novoBairro);
                    cellCep.setCellValue(novoCep);
                    cellCidade.setCellValue(novaCidade);
                    cellUf.setCellValue(novoUf);

                    // Salva as alterações no arquivo Excel
                    try (FileOutputStream outputStream = new FileOutputStream(caminho)) {
                        workbook.write(outputStream);
                        JOptionPane.showMessageDialog(frame, "Dados salvos com sucesso!");
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao abrir o arquivo Excel: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        btnSalvar.setBounds(518, 607, 85, 21);
        frame.getContentPane().add(btnSalvar);
        
        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        	}
        });
        btnSair.setBounds(613, 607, 85, 21);
        frame.getContentPane().add(btnSair);
        
        
    }
}
