import { slide as Menu } from 'react-burger-menu';
import { Navigate, BrowserRouter, Link, Routes, Route } from "react-router-dom";
import { useState } from "react";
import queryString from 'query-string';

import Login from './components/login/Login';
import Flight from './components/flight/Flight';
import Hotel from './components/hotel/Hotel';
import Payment from './components/payment/Payment';
import Logout from "./components/login/Logout";

import './App.scss';
import './Sidebar.scss';

/*
  O destino da viagem deve vir via query string a partir
  de link na página do catálogo.
  
  Estados que devem sobreviver a refreshes na página são
  guardados em localStorage: o próprio destino e o usuário
  logado.
*/

function readDestinationInput() {
  const query = queryString.parse(window.location.search);

  if (query.destinationId) {
    let destinationId = query.destinationId;
    localStorage.setItem('destinationId', destinationId);
    return destinationId;
  } else {
    return localStorage.getItem('destinationId') || -1;
  }
}

function checkLoggedUser() {
  return JSON.parse(localStorage.getItem('user'));
}

function Redirector({ user, children }) {
  return (
    user
      ? children
      : <Navigate to="/login" replace={true} />
  );
}

export default function App() {
  const destinationId = readDestinationInput();
  const [user, setUser] = useState(checkLoggedUser());
  
  function storeUser(user) {
    localStorage.setItem('user', JSON.stringify(user));
    setUser(user);
  }

  return (
    <div className="ReservationArea">
      <BrowserRouter>
        <header>
          {user &&
            <Menu>
              <Link to="/flight">Voo</Link>
              <Link to="/hotel">Hotel</Link>
              <Link to="/payment">Pagamento</Link>
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