// src/Relatorio.js
import React, { useState } from 'react';
import jsPDF from 'jspdf';

const Relatorio = () => {
    const [dados, setDados] = useState({
        cpf: '',
        cnpj: '',
        razaoSocial: '',
        email: '',
        endereco: '',
        bairro: '',
        cidade: '',
        estado: '',
        cep: '',
        telefone: '',
        data: '',
        // adicione outros campos conforme necessário
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setDados((prev) => ({ ...prev, [name]: value }));
    };

    const gerarPDF = () => {
        const doc = new jsPDF();
        doc.text(`SCORE CPF: ${dados.cpf}`, 10, 10);
        doc.text(`INFORMAÇÕES CONFIDENCIAIS - ${dados.cidade}, ${dados.data}`, 10, 20);
        doc.text(`CPF: ${dados.cpf}`, 10, 30);
        doc.text(`CNPJ: ${dados.cnpj}`, 10, 40);
        doc.text(`Nome: ${dados.razaoSocial}`, 10, 50);
        doc.text(`E-mail: ${dados.email}`, 10, 60);
        // Adicione mais informações conforme o modelo
        doc.save('relatorio.pdf');
    };

    return (
        <div>
            <h1>Gerar Relatório</h1>
           
            <input
                type="text"
                name="cnpj"
                placeholder="CNPJ/CPF"
                value={dados.cnpj}
                onChange={handleChange}
            />
            <input
                type="text"
                name="razaoSocial"
                placeholder="Nome / Razão Social"
                value={dados.razaoSocial}
                onChange={handleChange}
            />
            <input
                type="text"
                name="email"
                placeholder="E-mail"
                value={dados.email}
                onChange={handleChange}
            />
            <input
                type="text"
                name="endereco"
                placeholder="Endereço"
                value={dados.endereco}
                onChange={handleChange}
            />
            <input
                type="text"
                name="bairro"
                placeholder="Bairro"
                value={dados.bairro}
                onChange={handleChange}
            />
            <input
                type="text"
                name="cidade"
                placeholder="Cidade"
                value={dados.cidade}
                onChange={handleChange}
            />
            <input
                type="text"
                name="estado"
                placeholder="Estado"
                value={dados.estado}
                onChange={handleChange}
            />
            <input
                type="text"
                name="cep"
                placeholder="CEP"
                value={dados.cep}
                onChange={handleChange}
            />
            <input
                type="text"
                name="telefone"
                placeholder="Telefone"
                value={dados.telefone}
                onChange={handleChange}
            />
            <input
                type="text"
                name="data"
                placeholder="Data"
                value={dados.data}
                onChange={handleChange}
            />
            <button onClick={gerarPDF}>Gerar PDF</button>
        </div>
    );
};

export default Relatorio;
