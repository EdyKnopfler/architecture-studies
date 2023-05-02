import { useState } from 'react';
import { Navigate, Routes, Route } from 'react-router-dom';

import Login from '../login/Login';
import Flight from '../flight/Flight';
import Hotel from '../hotel/Hotel';
import Payment from '../payment/Payment';
import Logout from "../login/Logout";

function Redirector({ user, children }) {
  return (
    user
      ? children
      : <Navigate to="/login" replace={true} />
  );
}

export default function MainSection({ destinationData, user, storeUser }) {
  const [reservationState, setReservationState] = useState({
    
  });

  return (
    <>
      <Routes>
        <Route exact path="/" element={
          <Redirector user={user}>
            <Navigate to="/flightGoing" replace={true} />
          </Redirector>
        }/>
        <Route path="/login" element={
          <Login onAuthenticated={storeUser} />
        }/>
        <Route path="/flightGoing" element={
          <Redirector user={user}>
            <Flight destinationData={destinationData} step="going" />
          </Redirector>
        }/>
        <Route path="/flightReturning" element={
          <Redirector user={user}>
            <Flight destinationData={destinationData} step="returning" />
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
    </>
  );
}