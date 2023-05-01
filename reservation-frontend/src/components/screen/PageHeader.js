import { slide as Menu } from 'react-burger-menu';
import { Link } from 'react-router-dom';

import './Sidebar.scss';

export default function PageHeader({ user }) {
  return (
    <>
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
    </>
  );
}