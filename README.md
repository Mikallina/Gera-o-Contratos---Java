# Projeto: Geração de Contratos em PDF

## Descrição do Projeto

Este projeto tem como objetivo automatizar a geração de contratos em PDF utilizando dados de clientes armazenados em uma planilha Excel. O programa criado em Java lê informações como CPF/CNPJ, telefone, e-mail e data de inclusão de uma planilha e preenche um modelo de contrato, gerando um arquivo PDF para cada cliente.

## Estrutura do Projeto

### 1. Arquivos Utilizados

- **Dados.xlsx**: Planilha contendo os dados dos clientes, incluindo:
  - CPF/CNPJ
  - Nome
  - Endereço
  - Bairro
  - Estado
  - CEP
  - Cidade
  - Telefone
  - E-mail
  - Data de Inclusão adicionando 15 dias a data do Registro

- **Modelo.xlsx**: Modelo do contrato que contém placeholders para os dados dos clientes:
  - `[CNPJ]`
  - `[TELEFONE]`
  - `[EMAIL]`
  - `[RAZAO_SOCIAL]`
  - `[ENDERECO]`
  - `[BAIRRO]`
  - `[ESTADO]`
  - `[CEP]`
  - `[CIDADE]`
  - `[DATA]`

### 2. Resultado Esperado

- Geração de arquivos PDF com o contrato preenchido para cada cliente, salvos na pasta `Contratos/`.

## Funcionalidades Java

`GerarContratos()` implementa as seguintes funcionalidades:

1. **Leitura de Dados**: Abre a planilha `Dados.xlsx` e lê as informações a partir da segunda linha (ignorando o cabeçalho).

2. **Substituição de Placeholders**: Para cada cliente, substitui os placeholders no modelo de contrato pelos dados correspondentes.

3. **Geração de Nome do Arquivo**: Gera o nome do arquivo PDF a partir do CPF/CNPJ, removendo caracteres inválidos.

4. **Verificação de Existência de Arquivos**: Antes de salvar, verifica se um arquivo com o mesmo nome já existe e modifica o nome, se necessário.

5. **Exportação para PDF**: Exporta a nova cópia do modelo como um arquivo PDF, utilizando o método `ExportAsFixedFormat`.

6. **Notificação**: Exibe uma mensagem informando que os contratos foram gerados com sucesso.

7. **Exclusão de Campo**: Se o campo "Cidade" estiver vazio, não exibe "N/D" no contrato.
