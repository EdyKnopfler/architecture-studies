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
  return (
    <>
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
            <Flight destinationData={destinationData} />
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