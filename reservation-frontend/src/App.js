import { useState } from "react";
import { BrowserRouter } from "react-router-dom";

import {
  readDestinationInput,
  readLoggedUser,
  saveLoggedUser
} from "./persistentState";

import PageHeader from "./components/screen/PageHeader";
import MainSection from "./components/screen/MainSection";

import './App.scss';

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
          <PageHeader user={user} />
        </header>
        <section>
          <MainSection
            destinationId={destinationId}
            user={user}
            storeUser={storeUser} />
        </section>
      </BrowserRouter>
    </div>
  );
}