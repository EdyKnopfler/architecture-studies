import { slide as Menu } from 'react-burger-menu';
import { Link } from 'react-router-dom';

import './Sidebar.scss';
import './PageHeader.scss';

export default function PageHeader({ user, destinationData }) {
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
          <h2>{destinationData.name}</h2>
          <p>{destinationData.description}</p>
        </section>
        <img
          alt={destinationData.name}
          src={destinationData.imageUrl} />
      </section>
    </>
  );
}