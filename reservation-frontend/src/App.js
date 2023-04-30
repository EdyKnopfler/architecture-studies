import { useState } from "react";
import { slide as Menu } from 'react-burger-menu';
import {
  Navigate,
  BrowserRouter,
  Link,
  Routes,
  Route
} from "react-router-dom";

import {
  readDestinationInput,
  readLoggedUser,
  saveLoggedUser
} from "./persistentState";

import Login from './components/login/Login';
import Flight from './components/flight/Flight';
import Hotel from './components/hotel/Hotel';
import Payment from './components/payment/Payment';
import Logout from "./components/login/Logout";

import './App.scss';
import './Sidebar.scss';

function Redirector({ user, children }) {
  return (
    user
      ? children
      : <Navigate to="/login" replace={true} />
  );
}

export default function App() {
  const destinationId = readDestinationInput();
  const [user, setUser] = useState(readLoggedUser());
  
  function storeUser(user) {
    saveLoggedUser(user);
    setUser(user);
  }

  return (
    <div className="ReservationArea">
      <BrowserRouter>
        <header>
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
        <section>
          <Routes>
            <Route exact path="/" element={
              <Redirector user={user}>
                <Navigate to="/flight" replace={true} />
              </Redirector>
            }/>
            <Route path="/login" element={
              <Login onAuthenticated={storeUser} />
            }/>
            <Route path="/flight" element={
              <Redirector user={user}>
                <Flight destinationId={destinationId} />
              </Redirector>
            }/>
            <Route path="/hotel" element={
              <Redirector user={user}>
                <Hotel />
              </Redirector>
            }/>
            <Route path="/payment" element={
              <Redirector user={user}>
                <Payment />
              </Redirector>
            }/>
            <Route path="/logout" element={
              <Logout onUnauthenticated={() => storeUser(null)} />
            }/>
          </Routes>
        </section>
      </BrowserRouter>
    </div>
  );
}