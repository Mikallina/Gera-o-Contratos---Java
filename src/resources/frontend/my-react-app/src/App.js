import "./App.css";
import Relatorio from "./components/Relatorio";
import Clientes from "./components/Clientes";
import ClientList from "./components/Clientes";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <Relatorio />
        <Clientes />
        <ClientList />
      </header>
    </div>
  );
}

export default App;
