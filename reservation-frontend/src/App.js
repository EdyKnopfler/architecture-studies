import { Navigate, BrowserRouter, Link, Routes, Route } from "react-router-dom";
import { useState } from "react";

import Login from './components/login/Login';
import Flight from './components/flight/Flight';
import Hotel from './components/hotel/Hotel';
import Payment from './components/payment/Payment';
import './App.scss'

function Redirector({ user, children }) {
  return (
    user
      ? children
      : <Navigate to="/login" replace={true} />
  );
}

export default function App() {
  const [user, setUser] = useState(null);

  return (
    <div className="ReservationArea">
      <BrowserRouter>
        {user &&
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
                <Navigate to="/flight" replace={true} />
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