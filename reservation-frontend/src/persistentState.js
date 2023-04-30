/*
  O destino da viagem deve vir via query string a partir
  de link na página do catálogo.
  
  Estados que devem sobreviver a refreshes na página são
  guardados em localStorage: o próprio destino e o usuário
  logado.
*/

import queryString from 'query-string';

export function readDestinationInput() {
  const query = queryString.parse(window.location.search);

  if (query.destinationId) {
    let destinationId = query.destinationId;
    localStorage.setItem('destinationId', destinationId);
    return destinationId;
  } else {
    return localStorage.getItem('destinationId') || -1;
  }
}

export function readLoggedUser() {
  try {
    return JSON.parse(localStorage.getItem('user'));
  } catch (error) {
    console.log('Erro ao ler usuário:', error);
    return null;
  }
}

export function saveLoggedUser(user) {
  localStorage.setItem('user', JSON.stringify(user));
}