import React, { useEffect, useState } from "react";
import axios from "axios";

const ClientList = () => {
  const [clients, setClients] = useState([]);

  useEffect(() => {
    fetchClients();
  }, []);

  const fetchClients = async () => {
    try {
        const response = await axios.get('/api/clients');
        console.log('Dados recebidos:', response.data); // Adicione este log
        setClients(response.data);
    } catch (error) {
        console.error('Error fetching clients:', error);
    }
};


  const generateReport = async () => {
    const clientIds = clients.map((client) => client.cpf); // Ajuste conforme necessário
    try {
      await axios.post(
        "http://localhost:8080/api/clients/generate-report",
        clientIds
      );
      alert("Relatório gerado com sucesso!");
    } catch (error) {
      console.error("Error generating report:", error);
    }
  };


  const deleteClient = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/clients/${id}`);
      fetchClients(); // Atualiza a lista de clientes
      alert("Cliente excluído com sucesso!");
    } catch (error) {
      console.error("Error deleting client:", error);
    }
  };

  return (
    <div>
      <h1>Lista de Clientes</h1>
      <button onClick={generateReport}>Gerar Relatório</button>
      <table>
        <thead>
          <tr>
            <th>CNPJ/CPF</th>
            <th>Razão Social</th>
            <th>Ação</th>
          </tr>
        </thead>
        <tbody>
    {clients.map(client => (
        <tr key={client.cnpj}>
            <td>{client.cnpj}</td>
            <td>{client.razaoSocial}</td>
            <td>
                <button onClick={() => deleteClient(client.cnpj)}>Excluir</button>
            </td>
        </tr>
    ))}
</tbody>
      </table>
    </div>
  );
};

export default ClientList;
