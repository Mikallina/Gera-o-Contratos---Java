package com.mycompany.contratos.telas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.mycompany.contratos.dao.Cliente;
import com.mycompany.contratos.model.GerarContratos;
import com.mycompany.contratos.util.ExcelUtil;
import com.mycompany.contratos.util.TelefoneUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ConsultaCliente extends JPanel {

    private JTable table;
    private List<Cliente> clientes; // Lista para armazenar todos os clientes

    public ConsultaCliente() {
        initialize();
        carregarDados(); // Carregar dados ao inicializar
    }

    public void carregarDados() {
        clientes = new ArrayList<>(); // Inicializa a lista
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Limpa a tabela antes de adicionar novos dados

        try (FileInputStream file = new FileInputStream(new File("Dados.xlsx")); 
             Workbook workbook = new XSSFWorkbook(file)) {
             
            Sheet sheet = workbook.getSheetAt(0); // Primeira aba

            for (Row row : sheet) {
                if (row.getRowNum() == 0 || row.getRowNum() == 1) {
                    continue; // Ignora o cabeçalho
                }
                String cpf = getCellValue(row, 0);
                String nome = getCellValue(row, 1);
                String telefone = TelefoneUtil.formatarTelefone(getCellValue(row, 5));
                String email = getCellValue(row, 6);
                String data = getCellValue(row, 10);
                String cep = getCellValue(row, 7);
                String bairro = getCellValue(row, 2); 
                String cidade = getCellValue(row, 3);
                String estado = getCellValue(row, 4); 
                String endereco = getCellValue(row, 8); 

                // Cria um objeto Cliente com todos os dados
                Cliente cliente = new Cliente(cpf, nome, bairro, cidade, estado, telefone, email, cep, endereco, data);
                clientes.add(cliente); // Adiciona à lista de clientes

                // Adiciona os dados à tabela (apenas os campos que deseja exibir)
                model.addRow(new Object[]{cpf, nome, telefone, email, data}); // Exibindo apenas alguns campos
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage());
        }
    }

    private String getCellValue(Row row, int cellIndex) {
        return ExcelUtil.getCellValue(row, cellIndex);
    }

    private void initialize() {
        setLayout(null); // Configura o layout do JPanel

        JLabel lblNewLabel = new JLabel("Consulta de Clientes");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel.setBounds(412, 10, 193, 25);
        add(lblNewLabel);

        // Criação e configuração da tabela
        table = new JTable();
        table.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "CPF/CNPJ", "Razão Social / Nome", "Telefone", "Email", "Data"
            }
        ));

        // Cria um JScrollPane para a tabela
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(25, 115, 969, 249);
        add(scrollPane);

        JButton imprimir = new JButton("Gerar Contratos");
        imprimir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow(); 
                if (selectedRow != -1) {
                    // Recupera o cliente da lista usando o índice selecionado
                    Cliente cliente = clientes.get(selectedRow);

                    GerarContratos gerarContratos = new GerarContratos();
                    try {
                        gerarContratos.gerarContratos(cliente); // Passa o cliente com todos os dados
                        JOptionPane.showMessageDialog(ConsultaCliente.this, "Contrato gerado com sucesso.");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(ConsultaCliente.this, "Erro ao gerar contrato: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(ConsultaCliente.this, "Selecione uma linha para gerar o contrato.");
                }
            }
        });
        imprimir.setFont(new Font("Tahoma", Font.BOLD, 14));
        imprimir.setBounds(25, 374, 178, 32);
        add(imprimir);
        
        JButton editar = new JButton("EDITAR");
        editar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	int selectedRow = table.getSelectedRow();
        	if (selectedRow != 1) {
        		String cpf = (String) table.getValueAt(selectedRow,0);
        		ContratoPrinter contrato = new ContratoPrinter();
        		contrato.carregarDadosContrato(cpf);
        	}else {
        		JOptionPane.showMessageDialog(ConsultaCliente.this, "Selecione uma linha para editar");
        	}
        		
        	}
        });
        editar.setFont(new Font("Tahoma", Font.BOLD, 14));
        editar.setBounds(628, 374, 178, 32);
        add(editar);
        
        JButton excluir = new JButton("EXCLUIR");
        excluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow(); // Obtém a linha selecionada
                if (selectedRow != -1) {
                    // Obtém o CPF/CNPJ do cliente a ser excluído
                    String cpfCnpj = (String) table.getValueAt(selectedRow, 0);
                    
                    // Exclui o registro do Excel
                    excluirRegistro(cpfCnpj);
                    
                    // Atualiza a tabela após a exclusão
                    carregarDados(); // Recarrega os dados
                } else {
                    JOptionPane.showMessageDialog(ConsultaCliente.this, "Selecione uma linha para excluir.");
                }
            }

            private void excluirRegistro(String cpfCnpj) {
                String caminho = "C:/Users/mi_bo/OneDrive/Documentos/NetBeansProjects/Contratos/Dados.xlsx";

                try (Workbook workbook = new XSSFWorkbook(new FileInputStream(caminho))) {
                    Sheet sheet = workbook.getSheetAt(0);
                    int linhaParaExcluir = -1;

                    // Encontra a linha correspondente ao CPF/CNPJ
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);
                        if (row != null && getCellValue(row, 0).equals(cpfCnpj)) {
                            linhaParaExcluir = i;
                            break; // Sai do loop após encontrar a linha
                        }
                    }

                    // Se a linha foi encontrada, exclua-a
                    if (linhaParaExcluir != -1) {
                        // Remove a linha
                        sheet.removeRow(sheet.getRow(linhaParaExcluir));

                        // Reorganiza as linhas abaixo
                        int lastRowNum = sheet.getLastRowNum();
                        for (int i = linhaParaExcluir + 1; i <= lastRowNum; i++) {
                            Row rowToShift = sheet.getRow(i);
                            if (rowToShift != null) {
                                // Mover a linha uma posição para cima
                                sheet.shiftRows(i, i, -1);
                            }
                        }

                        // Limpa a última linha, que agora está duplicada
                        Row lastRow = sheet.getRow(lastRowNum);
                        if (lastRow != null) {
                            sheet.removeRow(lastRow); // Remove a última linha, se necessário
                        }
                    }

                    // Salva as alterações no arquivo Excel
                    try (FileOutputStream outputStream = new FileOutputStream(caminho)) {
                        workbook.write(outputStream);
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(ConsultaCliente.this, "Erro ao salvar os dados: " + ioException.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }

                    JOptionPane.showMessageDialog(ConsultaCliente.this, "Registro excluído com sucesso.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(ConsultaCliente.this, "Erro ao abrir o arquivo Excel: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        excluir.setFont(new Font("Tahoma", Font.BOLD, 14));
        excluir.setBounds(816, 374, 178, 32);
        add(excluir);
    }
}