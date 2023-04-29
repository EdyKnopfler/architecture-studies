import { Navigate, BrowserRouter, Link, Routes, Route } from "react-router-dom";
import { useState } from "react";

import Login from './login/Login';
import Flight from './flight/Flight';
import Hotel from './hotel/Hotel';
import Payment from './payment/Payment';
import './Reservation.scss'

function Redirector({ user, children }) {
  return (
    user
      ? children
      : <Navigate to="/login" replace={true} />
  );
}

export default function Reservation() {
  const [user, setUser] = useState(null);

  return (
    <div className="Reservation">
      <BrowserRouter>
        { user &&
          <nav className="menu">
            <ul>
              <li><Link to="/flight">Voo</Link></li>
              <li><Link to="/hotel">Hotel</Link></li>
              <li><Link to="/payment">Pagamento</Link></li>
            </ul>
          </nav>
        }
        <section>
          <Routes>
            <Route exact path="/" element={
              <Redirector user={user}>
                <Flight />
              </Redirector>
            }/>
            <Route path="/login" element={
              <Login onAuthenticated={setUser} />
            }/>
            <Route path="/flight" element={
              <Redirector user={user}>
                <Flight />
              </Redirector>
            }/>
            <Route path="/hotel" element={
              <Redirector user={user}>
                <Hotel />
              </Redirector>
            }/>
            <Route path="/payment" element={
              <Redirector user={user}><Payment /></Redirector>
            }/>
            </Routes>
        </section>
      </BrowserRouter>
    </div>
  )
}