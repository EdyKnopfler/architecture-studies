import { useState, useEffect } from "react";
import { BrowserRouter } from "react-router-dom";

import { fetchDestinationData } from "./destinationService";

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

  const [destinationData, setDestinationData] = useState({
    id: destinationId,
    name: '',
    descritpion: '',
    imageUrl: ''
  });

  const [user, setUser] = useState(readLoggedUser());

  useEffect(() => {
    async function getData() {
      try {
        const data = await fetchDestinationData(destinationId);
        setDestinationData(data);
      } catch (error) {
        console.log('Erro ao carregar dados:', error);
      }
    }

    getData();
  }, [destinationId]);

  function storeUser(user) {
    saveLoggedUser(user);
    setUser(user);
  }

  return (
    <div className="ReservationArea">
      <BrowserRouter>
        <PageHeader destinationData={destinationData} user={user} />
        <section>
          <MainSection
            destinationData={destinationData}
            user={user}
            storeUser={storeUser} />
        </section>
      </BrowserRouter>
    </div>
  );
}