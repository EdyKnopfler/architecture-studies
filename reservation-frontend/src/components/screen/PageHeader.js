import { slide as Menu } from 'react-burger-menu';
import { Link } from 'react-router-dom';

import './Sidebar.scss';
import './PageHeader.scss';

export default function PageHeader({ user }) {
  return (
    <>
      <header className="PageHeader">
        {user &&
          <Menu>
            {
              // eslint-disable-next-line
              <a>{user.name}</a>
            }
            <Link to="/logout">Sair</Link>
          </Menu>
        }
        <h1>Faça sua reserva!</h1>
      </header>
      <section className="destinationSection clearFix">
        <section className="textContent">
          <h2>Nome do destino!</h2>
          <p>
          Pequeno resumo Pequeno resumo Pequeno resumo Pequeno resumo Pequeno resumo
          Pequeno resumo Pequeno resumo Pequeno resumo Pequeno resumo Pequeno resumo
          </p>
        </section>
        <img
          alt="Nome do destino!"
          src="https://ichef.bbci.co.uk/news/640/cpsprodpb/cffb/live/f5d7e3a0-b770-11ed-89f4-f3657d2bfa3b.jpg" />
      </section>
    </>
  );
}